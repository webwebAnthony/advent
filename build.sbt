ThisBuild / scalaVersion := "3.2.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

import com.gilcloud.sbt.gitlab.{GitlabCredentials,GitlabPlugin}
GitlabPlugin.autoImport.gitlabDomain :=  "gitlab.boerse.intern"
ThisBuild / useCoursier := false

val gitlabBoerseInternResolver = "gitlab.boerse.intern" at "https://gitlab.boerse.intern/api/v4/projects/68/packages/maven"
val gitlabBoerseInternCred = Credentials(Path.userHome / ".sbt" / ".credentials.gitlab")

lazy val testDependencies = Seq(
  "dev.zio" %% "zio-test" % "2.0.4" % Test,
  "dev.zio" %% "zio-test-junit" % "2.0.4" % Test,
  "org.testcontainers" % "mysql" % "1.17.6" % Test,
)


lazy val root = (project in file("."))
  .settings(
    name := "advent",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.5",
      "dev.zio" %% "zio-json" % "0.3.0",
      "dev.zio" %% "zio-streams" % "2.0.4",
      "dev.zio" %% "zio-config" % "3.0.6",
      "dev.zio" %% "zio-prelude" % "1.0.0-RC16",
      "dev.zio" %% "zio-config-magnolia" % "3.0.6",
      "dev.zio" %% "zio-config-typesafe" % "3.0.2",
      "io.d11" %% "zhttp" % "2.0.0-RC11",
      "mysql" % "mysql-connector-java" % "8.0.30",
      "io.getquill" %% "quill-jdbc-zio" % "4.6.0",
      "com.scalawilliam" %% "xs4s-core" % "0.9.1",
      "com.scalawilliam" %% "xs4s-zio" % "0.9.1"
    ),
    libraryDependencies ++= testDependencies,
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
