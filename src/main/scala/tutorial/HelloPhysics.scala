package tutorial

import tutorial.Actions._

import com.jme3.app.SimpleApplication
import com.jme3.asset.TextureKey
import com.jme3.bullet.BulletAppState
import com.jme3.bullet.control.RigidBodyControl
import com.jme3.font.BitmapText
import com.jme3.input.MouseInput
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.MouseButtonTrigger
import com.jme3.material.Material
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.scene.shape.Sphere
import com.jme3.scene.shape.Sphere.TextureMode
import com.jme3.texture.Texture
import com.jme3.texture.Texture.WrapMode
import com.jme3.syntax._

//import cats._
//import cats.instances.all._
//import cats.syntax.eq._

/**
 * Example 12 - how to give objects physical properties so they bounce and fall.
 */
class HelloPhysics extends SimpleApplication {

  /** Prepare the Physics Application State (jBullet) */
  protected lazy val bulletAppState: BulletAppState = new BulletAppState()

 /** Prepare Materials */
  protected lazy val wall_mat: Material =
    Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
  protected lazy val stone_mat: Material =
    Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
  protected lazy val floor_mat: Material =
    Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
 
  /** dimensions used for bricks and wall */
  private val brickLength = 0.48f
  private val brickWidth  = 0.24f
  private val brickHeight = 0.12f
 
  /** Initialize the cannon ball geometry */
  protected val sphere = new Sphere(32, 32, 0.4f, true, false)
  sphere.setTextureMode(TextureMode.Projected)
  /** Initialize the brick geometry */
  protected val box = Box(brickLength, brickHeight, brickWidth)
  box.scaleTextureCoordinates(new Vector2f(1f, .5f))
  /** Initialize the floor geometry */
  protected val floor = Box(10f, 0.1f, 5f)
  floor.scaleTextureCoordinates(new Vector2f(3, 6))

  override def simpleInitApp: Unit = {

    // For debugging, shows collision shape:
    // bulletAppState.setDebugEnabled(true);

    /** Set up Physics Game */
    discard{ stateManager.attach(bulletAppState) }
    //bulletAppState.getPhysicsSpace().enableDebug(assetManager)
 
    /** Configure cam to look at scene */
    cam.setLocation(new Vector3f(0, 4f, 6f))
    cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y)
    /** Add InputManager action: Left click triggers shooting. */
    inputManager.addMapping(Shoot, new MouseButtonTrigger(MouseInput.BUTTON_LEFT))
    inputManager.addListener(actionListener, Shoot)
    /** Initialize the scene, materials, and physics space */
    initMaterials()
    initWall()
    initFloor()
    initCrossHairs()
  }

  /**
  * Every time the shoot action is triggered, a new cannon ball is produced.
  * The ball is set up to fly from the camera position in the camera direction.
  */
  protected val actionListener = new ActionListener {
    def onAction(name: String, keyPressed:Boolean, tpf:Float):Unit = {
      if (name == Shoot.name && !keyPressed) {
        makeCannonBall()
      }
    }
  }
 
  /** Initialize the materials used in this scene. */
  def initMaterials():Unit =  {
    val key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg")
    key.setGenerateMips(true)
    val tex = assetManager.loadTexture(key)
    wall_mat.setTexture("ColorMap", tex)
 
    val key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG")
    key2.setGenerateMips(true)
    val tex2 = assetManager.loadTexture(key2)
    stone_mat.setTexture("ColorMap", tex2)
 
    val key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg")
    key3.setGenerateMips(true)
    val tex3 = assetManager.loadTexture(key3)
    tex3.setWrap(WrapMode.Repeat)
    floor_mat.setTexture("ColorMap", tex3)
  }
 
  /** Make a solid floor and add it to the scene. */
  def initFloor(): Unit =  {
    val floor_geo = Geometry("Floor", floor)
    floor_geo.setMaterial(floor_mat)
    floor_geo.setLocalTranslation(0, -0.1f, 0)
    discard{ rootNode.attachChild(floor_geo) }
    /* Make the floor physical with mass 0.0f! */
    val floor_phy: RigidBodyControl = new RigidBodyControl(0.0f)
    floor_geo.addControl(floor_phy)
    bulletAppState.getPhysicsSpace.add(floor_phy)
  }
 
  /** This loop builds a wall out of individual bricks. */
  def initWall(): Unit =  {
    var startpt = brickLength / 4
    var height = 0f
    for (jj <- 0 until 15) {
      for (ii <- 0 until 6) {
        val vt = new Vector3f(ii * brickLength * 2 + startpt, brickHeight + height, 0)
        makeBrick(vt)
      }
      startpt = -startpt
      height += 2 * brickHeight
    }
  }
 
  /** This method creates one individual physical brick. */
  def makeBrick(loc: Vector3f): Unit = {
    /** Create a brick geometry and attach to scene graph. */
    val brick_geo = Geometry("brick", box)
    brick_geo.setMaterial(wall_mat)
    discard{ rootNode.attachChild(brick_geo) }
    /** Position the brick geometry  */
    brick_geo.setLocalTranslation(loc)
    /** Make brick physical with a mass > 0.0f. */
    val brick_phy: RigidBodyControl = new RigidBodyControl(2f)
    /** Add physical brick to physics space. */
    brick_geo.addControl(brick_phy)
    bulletAppState.getPhysicsSpace.add(brick_phy)
  }
 
  /** This method creates one individual physical cannon ball.
   * By defaul, the ball is accelerated and flies
   * from the camera position in the camera direction.*/
  def makeCannonBall(): Unit = {
    /** Create a cannon ball geometry and attach to scene graph. */
    val ball_geo = Geometry("cannon ball", sphere)
    ball_geo.setMaterial(stone_mat)
    discard{ rootNode.attachChild(ball_geo) }
    /** Position the cannon ball  */
    ball_geo.setLocalTranslation(cam.getLocation)
    /** Make the ball physcial with a mass > 0.0f */
    val ball_phy: RigidBodyControl = new RigidBodyControl(1f)
    /** Add physical ball to physics space. */
    ball_geo.addControl(ball_phy)
    bulletAppState.getPhysicsSpace.add(ball_phy)
    /** Accelerate the physcial ball to shoot it. */
    ball_phy.setLinearVelocity(cam.getDirection().mult(25))
  }
 
  /** A plus sign used as crosshairs to help the player with aiming.*/
  protected def initCrossHairs(): Unit = {
    guiNode.detachAllChildren()
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt")
    val ch = new BitmapText(guiFont, false)
    ch.setSize(guiFont.getCharSet.getRenderedSize * 2)
    ch.setText("+")        // fake crosshairs :)
    ch.setLocalTranslation( // center
      settings.getWidth / 2 - guiFont.getCharSet.getRenderedSize / 3 * 2,
      settings.getHeight / 2 + ch.getLineHeight / 2, 0)
    discard{ guiNode.attachChild(ch) }
  }
}

object HelloPhysics {
  def main(args:Array[String]): Unit = {

    import java.util.logging.{Logger,Level}
    Logger.getLogger("").setLevel(Level.WARNING)

    val app = new HelloPhysics
    app.start()
  }
}
