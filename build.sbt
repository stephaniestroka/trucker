name := """truck-simulator"""

version := "1.0-SNAPSHOT"

lazy val trucker = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate"          % "hibernate-core"       % "5.2.0.Final",
  "com.h2database"         % "h2"                   % "1.4.192"
)

// Running Play in development mode while using JPA will work fine, but in order to deploy the application you will need to add this to your build.sbt file.
PlayKeys.externalizeResources := false

