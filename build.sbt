name := """SemanticGraphQL"""
description := "Prototype to Query a SPARQL endpoint using GraphQL"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  filters,
  ws,
  "org.sangria-graphql" %% "sangria" % "0.5.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

routesGenerator := InjectedRoutesGenerator

lazy val root = (project in file(".")).enablePlugins(PlayScala)

fork in run := true
