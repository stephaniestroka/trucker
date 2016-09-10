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
