/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Xavier Gillard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package xgi.osx.packager

import java.io.File
import scala.io.Source
import java.io.FileWriter
import java.io.FileInputStream

/**
 * This object defines the 'core' of the packaging script
 */
object Package {
  // regexes to match the place holders from the template files
  val NAME       = """\{\{NAME\}\}"""
  val JAR        = """\{\{JAR\}\}"""
  val ICON       = """\{\{ICON\}\}"""
  val SITE       = """\{\{SITE\}\}"""
  val EXT        = """\{\{EXT\}\}"""
  val EXTENSIONS = """\{\{EXTENSIONS\}\}"""
  
  /**
   * The function you need to call when you want to create a new osx package based
   * on the args specified on the command line
   */
  def apply(args:Arguments) = {
    val contents = new File(args.name+".app/Contents")
    val binaries = new File(contents.getAbsolutePath+"/MacOS")
    val resources= new File(contents.getAbsolutePath+"/Resources")
    
    contents.mkdirs
    binaries.mkdirs
    resources.mkdirs
    
    mkPlist(contents, args)
    mkLauncher(contents, args)
   
    copyJar(contents, args)
    copyIcon(contents, args)
  }
  
  /**
   * Creates the launcher script at the appropriate location
   */
  private def mkLauncher(contents:File, args:Arguments) = {
    val template= Source.fromInputStream(getClass.getResourceAsStream("/launcher_template.txt")).getLines().mkString("\n")
    val content = template.replaceAll(JAR, args.jarFile)
                          .replaceAll(ICON, args.icon)
    
    val launcher = new File(contents.getAbsolutePath+"/MacOS/"+args.name)
    
    IO.toFile(launcher) {out =>
      out.write(content)
    }
    
    launcher.setExecutable(true)
  }
  
  /**
   * Copies the main jar file to the appropriate location
   */
  private def copyJar(contents:File, args:Arguments) = 
    IO.copy(new FileInputStream(args.jarFile))(contents.getAbsolutePath+"/MacOS/"+args.jarFile)
  
  /**
   * Copies the specified icon to the appropriate location
   */
  private def copyIcon(contents:File, args:Arguments) = {
    val is = if(args.icon=="default.icns") getClass.getResourceAsStream("/default.icns")
             else new FileInputStream(args.icon)
    IO.copy(is)(contents.getAbsolutePath+"/Resources/"+args.icon)
  }
  
  /**
   * Generates a plist file containing the minimal necessary information about the package
   */
  private def mkPlist(contents:File, args:Arguments) = {
    val extensions = args.extensions.map(mkExtension(args)).mkString("\n")
    
    val template= Source.fromInputStream(getClass.getResourceAsStream("/plist_template.txt")).getLines().mkString("\n")
    val content = template.replaceAll(NAME, args.name)
                          .replaceAll(ICON, args.icon)
                          .replaceAll(SITE, args.site)
                          .replaceAll(EXTENSIONS, extensions)
                          
    IO.toFile(contents.getAbsolutePath+"/Info.plist") { out =>
      out.write(content)
    }
  }
  
  /**
   * Generates the plist snippet corresponding to the handling of one given document extension
   */
  private def mkExtension(args:Arguments)(ext:String):String = {
    val template = """|<dict>
                      |  <key>CFBundleTypeName</key>
                      |  <string>{{NAME}} Document</string>
                      |  <key>CFBundleTypeExtensions</key>
                      |  <array>
                      |    <string>{{EXT}}</string>
                      |  </array>
                      |  <key>CFBundleTypeIconFile</key>
                      |  <string>{{ICON}}</string>
                      |</dict>""".stripMargin('|')
    
    return template.replaceAll(NAME, args.name)
                   .replaceAll(EXT, ext)
                   .replaceAll(ICON, args.icon)
  }
  
}