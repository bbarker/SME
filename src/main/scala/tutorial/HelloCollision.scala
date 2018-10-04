package tutorial

import tutorial.Actions._

import com.jme3.app.SimpleApplication
import com.jme3.asset.plugins.ZipLocator
import com.jme3.bullet.BulletAppState
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape
import com.jme3.bullet.collision.shapes.CollisionShape
import com.jme3.bullet.control.CharacterControl
import com.jme3.bullet.control.RigidBodyControl
import com.jme3.bullet.util.CollisionShapeFactory
import com.jme3.input.KeyInput
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.KeyTrigger
import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.jme3.scene.{Spatial, SpatialWrap}
import com.jme3.syntax._
import com.jme3.input.Action
 
/**
 * Example 9 - How to make walls and floors solid.
 * This collision code uses Physics and a custom Action Listener.
 */
class HelloCollision extends SimpleApplication with ActionListener {

  private lazy val sceneModel: Spatial = assetManager.loadModel("main.scene")
  private lazy val bulletAppState: BulletAppState = new BulletAppState()

  // We set up collision detection for the scene by creating a
  // compound collision shape and a static RigidBodyControl with mass zero.
  private lazy val sceneShape = CollisionShapeFactory.createMeshShape(
    sceneModel.toNode match {
      case util.Right(node) => node
      case util.Left(ex) => throw new NotImplementedError("No fallback sceneshape")
    }
  )
  private lazy val landscape: RigidBodyControl = new RigidBodyControl(sceneShape, 0)

  // We set up collision detection for the player by creating
  // a capsule collision shape and a CharacterControl.
  // The CharacterControl offers extra settings for
  // size, stepheight, jumping, falling, and gravity.
  // We also put the player in its starting position.
  protected lazy val capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1)

  private lazy val  player: CharacterControl = new CharacterControl(capsuleShape, 0.05f)
  private var walkDirection = new Vector3f

  case class CardinalDirection(
    left: Boolean  = false, 
    right: Boolean = false, 
    up: Boolean    = false, 
    down: Boolean  = false
  )

  private var characterDirection = CardinalDirection()

  override def simpleInitApp: Unit = {
    /** Set up Physics */
    discard{ stateManager.attach(bulletAppState) }
    //bulletAppState.getPhysicsSpace().enableDebug(assetManager)
 
    // We re-use the flyby camera for rotation, while positioning is handled by physics
    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f))
    flyCam.setMoveSpeed(100)
    setUpKeys()
    setUpLight()
 
    // We load the scene from the zip file and adjust its size.
    assetManager.registerLocator("assets/town.zip", classOf[ZipLocator])
    sceneModel.setLocalScale(2f)

    sceneModel.addControl(landscape)

    player.setJumpSpeed(20)
    player.setFallSpeed(30)
    player.setGravity(new Vector3f(0,-30f,0))
    player.setPhysicsLocation(new Vector3f(0, 10, 0))
 
    // We attach the scene and the player to the rootNode and the physics space,
    // to make them appear in the game world.
    discard{ rootNode.attachChild(sceneModel) }
    bulletAppState.getPhysicsSpace.add(landscape)
    bulletAppState.getPhysicsSpace.add(player)
  }
  
  private def setUpLight(): Unit = {
    // We add light so we see the scene
    val al = new AmbientLight()
    al.setColor(ColorRGBA.White.mult(1.3f))
    rootNode.addLight(al)
 
    val dl = new DirectionalLight()
    dl.setColor(ColorRGBA.White)
    dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal())
    rootNode.addLight(dl)
  }
 
  /** We over-write some navigational key mappings here, so we can
   * add physics-controlled walking and jumping: */
  private def setUpKeys(): Unit = {
    inputManager.addMapping(Left, new KeyTrigger(KeyInput.KEY_A))
    inputManager.addMapping(Right, new KeyTrigger(KeyInput.KEY_D))
    inputManager.addMapping(Up, new KeyTrigger(KeyInput.KEY_W))
    inputManager.addMapping(Down, new KeyTrigger(KeyInput.KEY_S))
    inputManager.addMapping(Jump, new KeyTrigger(KeyInput.KEY_SPACE))
    inputManager.addListener(this, Left)
    inputManager.addListener(this, Right)
    inputManager.addListener(this, Up)
    inputManager.addListener(this, Down)
    inputManager.addListener(this, Jump)
  }
 
  /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed. */
  def onAction(binding: String, value: Boolean, tpf: Float): Unit = 
    Action(binding) match {
      case Left => characterDirection = characterDirection.copy(left = value)
      case Right => characterDirection = characterDirection.copy(right = value)
      case Up => characterDirection = characterDirection.copy(up = value)
      case Down => characterDirection = characterDirection.copy(down = value)
      case Jump =>  if (value) { player.jump(new Vector3f(0,20f,0));}
  }
 
  /**
   * This is the main event loop--walking happens here.
   * We check in which direction the player is walking by interpreting
   * the camera direction forward (camDir) and to the side (camLeft).
   * The setWalkDirection() command is what lets a physics-controlled player walk.
   * We also make sure here that the camera moves with player.
   */
  override def simpleUpdate(tpf:Float): Unit =  {
    val camDir = cam.getDirection().clone().multLocal(0.6f)
    val camLeft = cam.getLeft().clone().multLocal(0.4f)
    discard{ walkDirection.set(0, 0, 0) }
    if (characterDirection.left)  { discard{ walkDirection.addLocal(camLeft) } }
    if (characterDirection.right) { discard{ walkDirection.addLocal(camLeft.negate()) } }
    if (characterDirection.up)    { discard{ walkDirection.addLocal(camDir) } }
    if (characterDirection.down)  { discard{ walkDirection.addLocal(camDir.negate()) } }
    player.setWalkDirection(walkDirection)
    cam.setLocation(player.getPhysicsLocation())
  }

}

object HelloCollision {
  def main(args:Array[String]): Unit = {

    import java.util.logging.{Logger,Level}
    Logger.getLogger("").setLevel(Level.WARNING)

    val app = new HelloCollision
    app.start()
  }
}
