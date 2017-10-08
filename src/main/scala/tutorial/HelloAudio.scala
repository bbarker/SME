package tutorial

import tutorial.Actions._

import com.jme3.app.SimpleApplication
import com.jme3.audio.AudioNode
import com.jme3.audio.AudioData.DataType
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.MouseButtonTrigger
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box

import com.jme3.syntax._
 
/** Sample 11 - playing 3D audio. */
class HelloAudio extends SimpleApplication {

  private lazy val audio_gun: AudioNode = 
    new AudioNode(assetManager, "Sound/Effects/Gun.wav", DataType.Buffer)
  private lazy val audio_nature:AudioNode = 
    new AudioNode(assetManager, "Sound/Environment/Ocean Waves.ogg", DataType.Stream)
  private lazy val player: Geometry = Geometry("Player", Box(1, 1, 1))

  override def simpleInitApp: Unit = {
    flyCam.setMoveSpeed(40)
 
    /** just a blue box floating in space */
    val mat1 = Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
    mat1.setColor("Color", ColorRGBA.Blue)
    player.setMaterial(mat1)
    discard{ rootNode.attachChild(player) }
 
    /** custom init methods, see below */
    initKeys()
    initAudio()
  }

  /** We create two audio nodes. */
  private def initAudio(): Unit =  {
    /* gun shot sound is to be triggered by a mouse click. */
    audio_gun.setLooping(false)
    audio_gun.setVolume(2)
    discard{ rootNode.attachChild(audio_gun) }
 
    /* nature sound - keeps playing in a loop. */
    audio_nature.setLooping(true)  // activate continuous playing
    audio_nature.setPositional(true)
    audio_nature.setLocalTranslation(Vector3f.ZERO.clone())
    audio_nature.setVolume(3)
    discard{ rootNode.attachChild(audio_nature) }
    audio_nature.play() // play continuously!
  }
 
  /** Declaring "Shoot" action, mapping it to a trigger (mouse click). */
  private def initKeys(): Unit = {
    inputManager.addMapping(Shoot, new MouseButtonTrigger(0))
    inputManager.addListener(actionListener, Shoot)
  }
 
  /** Defining the "Shoot" action: Play a gun sound. */
  protected val actionListener = new ActionListener {
    override def onAction(name: String, keyPressed: Boolean, tpf: Float): Unit = {
      if (name == Shoot.name && !keyPressed) {
        audio_gun.playInstance() // play each instance once!
      }
    }
  }
 
  /** Move the listener with the a camera - for 3D audio. */
  override def simpleUpdate(tpf: Float): Unit = {
    listener.setLocation(cam.getLocation)
    listener.setRotation(cam.getRotation)
  }
}

object HelloAudio {
  def main(args:Array[String]): Unit = {

    import java.util.logging.{Logger,Level}
    Logger.getLogger("").setLevel(Level.WARNING);

    val app = new HelloAudio
    app.start
  }
}
