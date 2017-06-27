package tutorial

import tutorial.Actions._

import com.jme3.animation.AnimChannel
import com.jme3.animation.AnimChannelWrap
import com.jme3.animation.AnimControl
import com.jme3.animation.AnimEventListener
import com.jme3.animation.LoopMode
import com.jme3.app.SimpleApplication
import com.jme3.input.KeyInput
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.KeyTrigger
import com.jme3.light.DirectionalLight
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.{Node, SpatialWrap}
import com.jme3.scene.debug.SkeletonDebugger
import com.jme3.material.Material
import com.jme3.syntax._

import scala.collection.JavaConverters._

//import cats._
//import cats.instances.all._
//import cats.syntax.eq._

/** Sample 7 - how to load an OgreXML model and play an animation,
 * using channels, a controller, and an AnimEventListener. */
class HelloAnimation extends SimpleApplication with AnimEventListener {

  lazy val player: Node = assetManager.loadModel("Models/Oto/Oto.mesh.xml")
    .toNode.right.toOption match {
      case Some(node) => node
      case None => Node("Player Fallback")
    }

  private lazy val control: AnimControl = 
    player.getControlMaybe(classOf[AnimControl]) match {
      case Some(ctrl) => ctrl 
      case None => 
        println("Couldin't find a controller!") //FIXME: logging?
        new AnimControl()
  }
  private lazy val channel: AnimChannel = control.createChannel()

  override def simpleInitApp: Unit = {
    viewPort.setBackgroundColor(ColorRGBA.LightGray)
    initKeys()
    val dl = new DirectionalLight()
    dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal())
    rootNode.addLight(dl)
    player.setLocalScale(0.5f)
    discard[Int]{ rootNode.attachChild(player) }
    control.addListener(this)
    channel.setAnim(Stand)

    /* Skeleton debugger */
    val skeletonDebug = SkeletonDebugger("skeleton", control.getSkeleton)
    val mat = Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
    mat.setColor("Color", ColorRGBA.Green)
    mat.getAdditionalRenderState.setDepthTest(false)
    skeletonDebug.setMaterial(mat)
    discard[Int]{ player.attachChild(skeletonDebug) }

    /* List of animation names*/
    println("Available animations:")
    control.getAnimationNames.asScala.foreach(nm => println(nm))
  }

  def onAnimCycleDone(control: AnimControl, channel: AnimChannel, animName: String): Unit = {
    if (animName == Walk.name) {
      channel.setAnim(Stand, 0.50f)
      channel.setLoopMode(LoopMode.DontLoop)
      channel.setSpeed(1f)
    }
  }
 
  def onAnimChange(control: AnimControl, channel: AnimChannel, animName: String): Unit = {
    // unused
  }
 
  /** Custom Keybinding: Map named actions to inputs. */
  private def initKeys(): Unit = {
    inputManager.addMapping(Walk, new KeyTrigger(KeyInput.KEY_SPACE))
    inputManager.addListener(actionListener, Walk)
  }

  val actionListener: ActionListener = new ActionListener {
    def onAction(name: String, keyPressed: Boolean, tpf: Float): Unit = {
      if (name == Walk.name && !keyPressed) {
        if (channel.getAnimationName  !=  Walk.name) {
          channel.setAnim(Walk, 0.50f)
          channel.setLoopMode(LoopMode.Loop)
        }
      }
    }
  }
}

object HelloAnimation {
  def main(args:Array[String]): Unit = {

    import java.util.logging.{Logger,Level}
    Logger.getLogger("").setLevel(Level.WARNING)

    val app = new HelloAnimation
    app.start()
  }
}
