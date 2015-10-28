# packager
A simple tool to create OSX packages from jar files.
````
 Usage : 
    java -jar packager --jar <path_to_jar_file>
                      [--name <name_of_your_app]
                      [--icon <icon_of_your_app]
                      [--site <site_of_your_app]
                      [--ext  <an_extension_managed_by_your_app>]*
````

Note: icon MUST be in icns format if you want it to work properly.

## How to build
This project comes in the form of an [sbt](http://www.scala-sbt.org) project. In order to build it, you'll need to: 
1. Get SBT installed
2. Launch a terminal
3. Type ```sbt assembly```
4. That's it, your binary file is to be found under $PROJECT/target/scala-2.11/package-assembly-xxxxx.jar
