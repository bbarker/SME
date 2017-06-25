import LWJGLPlugin._

val scalacMajor  = "2.11"
val dottyVersion = "0.2.0-bin-20170620-1d05796-NIGHTLY"
val lwjglVersion = "2.9.3"

lazy val root = (project in file("."))
  .settings(lwjglSettings: _*)
  .settings(
    name := "SME",
    version := "0.1",
    scalaVersion := dottyVersion,
    // scalaVersion := "2.11.11",
    LWJGLPlugin.lwjgl.version := lwjglVersion,
    //wartremoverWarnings ++= Warts.unsafe,
    //wartremoverErrors ++= Seq(Wart.Return),
    resolvers ++= Seq(
      "Sonatype OSS Snapshots" at
	"https://oss.sonatype.org/content/repositories/snapshots",
      "Sonatype OSS Releases" at
	"https://oss.sonatype.org/content/repositories/releases",
      "JCenter" at "http://jcenter.bintray.com",
      "Bintray" at "http://dl.bintray.com/jmonkeyengine/org.jmonkeyengine"
    )
    ,libraryDependencies ++= jmonkeyDeps
    ,libraryDependencies += ("org.typelevel" %% "cats" % "0.9.0").withDottyCompat()
    ,libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
    // ,wartremoverWarnings += Wart.Var
    // ,wartremoverErrors ++= Warts.allBut(
    //   Wart.Var, Wart.Nothing, Wart.ImplicitConversion,
    //   Wart.ExplicitImplicitTypes // Seems to not work correctly
    // )
          /* Tasks */
    ,fullRunTask(TaskKey[Unit]("run-hello-simple-application"), Test, "tutorial.HelloSimpleApplication")
    ,fullRunTask(TaskKey[Unit]("run-hello-node"), Test, "tutorial.HelloNode")
    ,fullRunTask(TaskKey[Unit]("run-hello-assets"), Test, "tutorial.HelloAssets")
    ,fullRunTask(TaskKey[Unit]("run-hello-loop"), Test, "tutorial.HelloLoop")
    ,fullRunTask(TaskKey[Unit]("run-hello-input"), Test, "tutorial.HelloInput")
    ,fullRunTask(TaskKey[Unit]("run-hello-material"), Test, "tutorial.HelloMaterial")
    ,fullRunTask(TaskKey[Unit]("run-hello-animation"), Test, "tutorial.HelloAnimation")
    ,fullRunTask(TaskKey[Unit]("run-hello-picking"), Test, "tutorial.HelloPicking")
    ,fullRunTask(TaskKey[Unit]("run-hello-collision"), Test, "tutorial.HelloCollision")
    ,fullRunTask(TaskKey[Unit]("run-hello-terrain"), Test, "tutorial.HelloTerrain")
    ,fullRunTask(TaskKey[Unit]("run-hello-audio"), Test, "tutorial.HelloAudio")
    ,fullRunTask(TaskKey[Unit]("run-hello-effects"), Test, "tutorial.HelloEffects")
    ,fullRunTask(TaskKey[Unit]("run-hello-physics"), Test, "tutorial.HelloPhysics")

  )
  .enablePlugins(LWJGLPlugin)


lazy val jmonkeyDeps = Seq(
      "org.jmonkeyengine" % "jme3-core" % "3.1.0-stable" exclude("org.jmonkeyengine", "jme3-testdata")
      ,"org.jmonkeyengine" % "jme3-examples" % "3.1.0-stable" exclude("org.jmonkeyengine", "jme3-testdata")
      ,"org.jmonkeyengine" % "jme3-bullet" % "3.1.0-stable" exclude("org.jmonkeyengine", "jme3-testdata")
      ,"org.jmonkeyengine" % "jme3-bullet-native" % "3.1.0-stable" exclude("org.jmonkeyengine", "jme3-testdata")
      ,"org.jmonkeyengine" % "jme3-jbullet" % "3.1.0-stable" exclude("org.jmonkeyengine", "jme3-testdata")
//    ,"org.jmonkeyengine" % "jme3-testdata" % "3.1.0-stable" from "file://lib/jme3-testdata-3.1.0.jar"
      ,"org.lwjgl.lwjgl" % "lwjgl_util" % "2.9.3"
//    ,"org.specs2" %% "specs2-core" % "3.8.9" % "it,test"
)
