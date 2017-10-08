package tutorial

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.syntax._

/** Sample 11 - how to create fire, water, and explosion effects. */
class HelloEffects extends SimpleApplication {

  protected lazy val fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30)
  protected lazy val mat_red = Material(assetManager, "Common/MatDefs/Misc/Particle.j3md")
  protected lazy val debris = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10)
  protected lazy val debris_mat = Material(assetManager, "Common/MatDefs/Misc/Particle.j3md")

  override def simpleInitApp: Unit = {
    mat_red.setTexture("Texture", assetManager.loadTexture( "Effects/Explosion/flame.png"))
    fire.setMaterial(mat_red)
    fire.setImagesX(2) 
    fire.setImagesY(2) // 2x2 texture animation
    fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f))   // red
    fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)) // yellow
    fire.getParticleInfluencer.setInitialVelocity(new Vector3f(0, 2, 0))
    fire.setStartSize(1.5f)
    fire.setEndSize(0.1f)
    fire.setGravity(0, 0, 0)
    fire.setLowLife(1f)
    fire.setHighLife(3f)
    fire.getParticleInfluencer.setVelocityVariation(0.3f)
    discard{ rootNode.attachChild(fire) }
 
    debris_mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/Debris.png"))
    debris.setMaterial(debris_mat)
    debris.setImagesX(3) 
    debris.setImagesY(3) // 3x3 texture animation
    debris.setRotateSpeed(4)
    debris.setSelectRandomImage(true)
    debris.getParticleInfluencer.setInitialVelocity(new Vector3f(0, 4, 0))
    debris.setStartColor(ColorRGBA.White)
    debris.setGravity(0, 6, 0)
    debris.getParticleInfluencer.setVelocityVariation(.60f)
    discard{ rootNode.attachChild(debris) }
    debris.emitAllParticles()
  }

}

object HelloEffects {
  def main(args:Array[String]): Unit = {

    import java.util.logging.{Logger,Level}
    Logger.getLogger("").setLevel(Level.WARNING)

    val app = new HelloEffects
    app.start()
  }
}
