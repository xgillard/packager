//
// The MIT License (MIT)
//
// Copyright (c) 2015 Xavier Gillard
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

//
// This file is the SBT (Scala Build Tool) build file. 
//
// You might consider it a Makefile on steroids: not only does it lets you control the build of your application but
// also the testing, the dependencies to use, how to package the application and so on.
//
// It is also a very convenient way to distribute your program because thanks to the many existing plugins, you don't 
// need to bundle all dependencies with your source code nor is your team bound by a specific IDE. Thanks to this, each
// developer can work with its favourite IDE and keep the project consistent.
// 
// If you are not familiar with SBT, you can either trust us that this file is correct or get it installed and read the
// documentation on http://www.scala-sbt.org.
//
// NOTE: The 'project' folder contains additional information (ie the configuration of the plugins [import in eclipse])
//

//
// If you want to import this project in eclipse, perform the following steps: 
// 1. Get sbt installed
// 2. From the command prompt run 'sbt eclipse'
// 3. Import the project in eclipse
//

lazy val commonSettings = Seq (
  name         := "packager"  ,
  version      := "1.0"          ,
  scalaVersion := "2.11.0" 
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings{
    libraryDependencies ++= Seq (
	
    ) 
  }
