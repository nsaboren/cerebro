name := "cerebro"

maintainer := "Leonardo Menezes <leonardo.menezes@xing.com>"

packageSummary := "Elasticsearch web admin tool"

packageDescription := """cerebro is an open source(MIT License) elasticsearch web admin tool built
  using Scala, Play Framework, AngularJS and Bootstrap."""

version := "0.9.5"

scalaVersion := "2.13.9"

rpmVendor := "lmenezes"

rpmLicense := Some("MIT")

rpmUrl := Some("http://github.com/lmenezes/cerebro")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play"                    % "2.9.9",
  "com.typesafe.play" %% "play-json"               % "2.9.4",
  "com.typesafe.play" %% "play-logback"            % "2.9.9",
  "com.typesafe.play" %% "play-ws"                 % "2.9.9",
  "com.typesafe.play" %% "play-ahc-ws"             % "2.9.9",
  "com.typesafe.play" %% "play-slick"              % "5.1.0",
  "com.typesafe.play" %% "play-slick-evolutions"   % "5.1.0",
  "org.xerial"        %  "sqlite-jdbc"             % "3.41.2.2",
  "org.specs2"        %% "specs2-junit"  % "4.10.0" % "test",
  "org.specs2"        %% "specs2-core"   % "4.10.0" % "test",
  "org.specs2"        %% "specs2-mock"   % "4.10.0" % "test"
)

dependencyOverrides ++= Seq(
  "com.typesafe" %% "ssl-config-core" % "0.4.2",
  "com.typesafe.akka" %% "akka-actor"                 % "2.8.8",
  "com.typesafe.akka" %% "akka-actor-typed"           % "2.8.8",
  "com.typesafe.akka" %% "akka-stream"                % "2.8.8",
  "com.typesafe.akka" %% "akka-slf4j"                 % "2.8.8",
  "com.typesafe.akka" %% "akka-protobuf-v3"           % "2.8.8",
  "com.typesafe.akka" %% "akka-serialization-jackson" % "2.8.8",
  "com.typesafe.akka" %% "akka-http"                  % "10.5.3",
  "com.typesafe.akka" %% "akka-http-core"             % "10.5.3",
  "com.fasterxml.jackson.core"       %  "jackson-core"            % "2.15.0",
  "com.fasterxml.jackson.core"       %  "jackson-databind"        % "2.15.0",
  "com.fasterxml.jackson.module"     %% "jackson-module-scala"    % "2.15.0",
  "com.fasterxml.jackson.core"       %  "jackson-annotations"     % "2.15.0",
  "com.fasterxml.jackson.dataformat" %  "jackson-dataformat-cbor" % "2.15.0"
)

libraryDependencies += filters
libraryDependencies += ws
libraryDependencies += guice

lazy val root = (project in file(".")).
  enablePlugins(PlayScala, BuildInfoPlugin, LauncherJarPlugin, JDebPackaging, RpmPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "models"
  )

sources in (Compile, doc) := Seq.empty

enablePlugins(JavaServerAppPackaging)
enablePlugins(SystemdPlugin)

pipelineStages := Seq(digest, gzip)

serverLoading := Some(ServerLoader.Systemd)
systemdSuccessExitStatus in Debian += "143"
systemdSuccessExitStatus in Rpm += "143"
linuxPackageMappings += packageTemplateMapping(s"/var/lib/${packageName.value}")() withUser((daemonUser in Linux).value) withGroup((daemonGroup in Linux).value)
