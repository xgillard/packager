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

/**
 * This class defines the structure of the arguments that can be passed through command line.
 */
class Arguments(
    val name:String,
    val site:String,
    val jarFile:String, 
    val icon:String, 
    val extensions:List[String])

/** 
 * this class serves basically the purpose of being an itermediary form between the cli array
 * of arguments and the Arguments object 
 */
class Param(val name:String, val value:String)

/**
 * This companion object is the factory that parses command line args and creates an Argument objects
 */
object Arguments {
  val usage = """
    | Usage : 
    |    java -jar packager --jar <path_to_jar_file>
    |                      [--name <name_of_your_app]
    |                      [--icon <icon_of_your_app]
    |                      [--site <site_of_your_app]
    |                      [--ext  <an_extension_managed_by_your_app>]*
    """.stripMargin('|')
  
  /** factory method that parses all arguments */
  def apply(args:Array[String]) : Arguments = {
    val params = toParameters(args)
    
    val jar    = params.find(_.name == "jar") match {
      case Some(p) => p.value
      case None    => throw new IllegalStateException("jar file is mandatory")
    } 
    val name   = params.find(_.name == "name") match {
      case Some(p) => p.value
      case None    => jar.split("""\.""")(0)
    }
    val site   = params.find(_.name == "site") match {
      case Some(p) => p.value
      case None    => jar.split("""\.""")(0)
    }
    val icon   = params.find(_.name == "icon") match {
      case Some(p) => p.value
      case None    => "default.icns"
    }
    val exts   = params.filter(_.name == "extension").map{_.value}
    
    return new Arguments(name, site, jar, icon, exts)
  }
  
  /**
   * Converts the String list to an equivalent list of Parameters
   */
  private def toParameters(args:Array[String]) : List[Param] = {
    var result = List.empty[Param]
    var list   = args.toList
    while(!list.isEmpty){
      opt(list) match {
        case Some((param, rest)) => {
          result  = param::result
          list    = rest
        }
        case None => list = Nil
      }
    }
    return result
  }
  
  /** consumes one argument and translate to the appropriate Param form */ 
  private def opt(args:List[String]) : Option[(Param, List[String])] = args match {
    case "--jar"  :: value :: rest => return Some(new Param("jar", value), rest)
    case "--name" :: value :: rest => return Some(new Param("name", value), rest)
    case "--icon" :: value :: rest => return Some(new Param("icon", value), rest)
    case "--site" :: value :: rest => return Some(new Param("site", value), rest)
    case "--ext"  :: value :: rest => return Some(new Param("extension", value), rest)
    case Nil                       => return None
    case other    :: tail          => {
      Console.err.println("Unknown option "+other) 
      return Some(new Param("unknown", other), tail)
    }
  }
}