import LWJGLPlugin._

val dottyVersion = "0.1.2-RC1"
val lwjglVersion = "2.9.3"

lazy val root = (project in file("."))
  .settings(lwjglSettings: _*)
  .settings(
    name := "SME",
    version := "0.1",
    scalaVersion := dottyVersion,
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
    ),
    libraryDependencies ++= jmonkeyDeps,      
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    
          /* Tasks */
    fullRunTask(TaskKey[Unit]("run-hello-simple-application"), Test, "fr.hsyl20.sme.tutorial.HelloSimpleApplication"),
    fullRunTask(TaskKey[Unit]("run-hello-node"), Test, "fr.hsyl20.sme.tutorial.HelloNode"),
    fullRunTask(TaskKey[Unit]("run-hello-assets"), Test, "fr.hsyl20.sme.tutorial.HelloAssets"),
    fullRunTask(TaskKey[Unit]("run-hello-loop"), Test, "fr.hsyl20.sme.tutorial.HelloLoop"),
    fullRunTask(TaskKey[Unit]("run-hello-input"), Test, "fr.hsyl20.sme.tutorial.HelloInput"),
    fullRunTask(TaskKey[Unit]("run-hello-material"), Test, "fr.hsyl20.sme.tutorial.HelloMaterial"),
    fullRunTask(TaskKey[Unit]("run-hello-animation"), Test, "fr.hsyl20.sme.tutorial.HelloAnimation"),
    fullRunTask(TaskKey[Unit]("run-hello-picking"), Test, "fr.hsyl20.sme.tutorial.HelloPicking"),
    fullRunTask(TaskKey[Unit]("run-hello-collision"), Test, "fr.hsyl20.sme.tutorial.HelloCollision"),
    fullRunTask(TaskKey[Unit]("run-hello-terrain"), Test, "fr.hsyl20.sme.tutorial.HelloTerrain"),
    fullRunTask(TaskKey[Unit]("run-hello-audio"), Test, "fr.hsyl20.sme.tutorial.HelloAudio"),
    fullRunTask(TaskKey[Unit]("run-hello-effects"), Test, "fr.hsyl20.sme.tutorial.HelloEffects"),
    fullRunTask(TaskKey[Unit]("run-hello-physics"), Test, "fr.hsyl20.sme.tutorial.HelloPhysics")
  ).enablePlugins(LWJGLPlugin)

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



