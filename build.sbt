

name := "ruigo_checker"

version := "1.0"

scalaVersion := "2.11.4"

resolvers += "Maven Central" at "https://repo1.maven.org/maven2/"

libraryDependencies += "com.atilika.kuromoji" % "kuromoji-ipadic" % "0.9.0"

unmanagedBase := baseDirectory.value / "lib"
