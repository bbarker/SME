package fr.hsyl20.sme.tutorial

import com.jme3.app.SimpleApplication
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
 
/** Sample 4 - how to trigger repeating actions from the main update loop.
 * In this example, we make the player character rotate. */
class HelloLoop extends SimpleApplication {
 
  protected lazy val player: Geometry = new Geometry("blue cube", new Box(1, 1, 1))

  override def simpleInitApp: Unit = {
    val mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
    mat.setColor("Color", ColorRGBA.Blue)
    player.setMaterial(mat)
    ;{ val _ = rootNode.attachChild(player)}
  }

  /* This is the update loop */
  override def simpleUpdate(tpf: Float): Unit = {
    // make the player rotate
    {val _ = player.rotate(0, 2*tpf, 0)}
  }
}

object HelloLoop {
  def main(args: Array[String]): Unit = {

    import java.util.logging.{Logger,Level}
    Logger.getLogger("").setLevel(Level.WARNING)

    val app = new HelloLoop
    app.start()
  }
}
