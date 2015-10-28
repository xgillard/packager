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

import java.io._
import java.nio.file._

/**
 * This object contains a few IO related functions
 */
object IO {
  /** serves the same purpose as the overloaded toFile function */
  def toFile(file:String)(fn:(FileWriter)=>Unit):Unit = 
    toFile(new File(file))(fn)
  /** 
   * provides you with a context in which you can interact with a file without having to think about
   * the burden of opening/closing the streams 
   */
  def toFile(file:File)(fn:(FileWriter)=>Unit):Unit = {
    val out = new FileWriter(file)
    try{
      fn(out)
    } finally {
      out.close()
    }
  }
  /** copies the full content of the given input stream to the file denoted by the 'to' param */
  def copy(stream:InputStream)(to:String) = {
    Files.copy(stream, Paths.get(to), StandardCopyOption.REPLACE_EXISTING)
  }
}