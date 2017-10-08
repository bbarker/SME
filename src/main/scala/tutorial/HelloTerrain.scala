package tutorial

import com.jme3.app.SimpleApplication
import com.jme3.material.Material
import com.jme3.terrain.geomipmap.TerrainLodControl
import com.jme3.terrain.geomipmap.TerrainQuad
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator
import com.jme3.terrain.heightmap.HillHeightMap // for exercise 2
import com.jme3.terrain.heightmap.ImageBasedHeightMap
import com.jme3.texture.Texture
import com.jme3.texture.Texture.WrapMode
import jme3tools.converters.ImageToAwt
import com.jme3.syntax._

//import cats._
//import cats.instances.all._
//import cats.syntax.eq._

import scala.collection.JavaConverters._

class HelloTerrain extends SimpleApplication {

  /** 1. Create terrain material and load four textures into it. */
  protected lazy val mat_terrain: Material =
    Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md")

  /** 2. Create the height map */
  protected lazy val heightMapImage =
    assetManager.loadTexture("Textures/Terrain/splat/mountains512.png")
    protected lazy val heightmap = new ImageBasedHeightMap(heightMapImage.getImage)

  /** 3. We have prepared material and heightmap.
    * Now we create the actual terrain:
    * 3.1) Create a TerrainQuad and name it "my terrain".
    * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
    * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
    * 3.4) As LOD step scale we supply Vector3f(1,1,1).
    * 3.5) We supply the prepared heightmap itself.
    */
  protected val patchSize = 65

  protected lazy val terrain: TerrainQuad =
    new TerrainQuad("my terrain", patchSize, 513, heightmap.getHeightMap)

  override def simpleInitApp: Unit = {
    flyCam.setMoveSpeed(50)
 

    /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
    mat_terrain.setTexture("Alpha", assetManager.loadTexture("Textures/Terrain/splat/alphamap.png"))
 
    /** 1.2) Add GRASS texture into the red layer (Tex1). */
    val grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg")
    grass.setWrap(WrapMode.Repeat)
    mat_terrain.setTexture("Tex1", grass)
    mat_terrain.setFloat("Tex1Scale", 64f)
 
    /** 1.3) Add DIRT texture into the green layer (Tex2) */
    val dirt = assetManager.loadTexture( "Textures/Terrain/splat/dirt.jpg")
    dirt.setWrap(WrapMode.Repeat)
    mat_terrain.setTexture("Tex2", dirt)
    mat_terrain.setFloat("Tex2Scale", 32f)
 
    /** 1.4) Add ROAD texture into the blue layer (Tex3) */
    val rock = assetManager.loadTexture( "Textures/Terrain/splat/road.jpg")
    rock.setWrap(WrapMode.Repeat)
    mat_terrain.setTexture("Tex3", rock)
    mat_terrain.setFloat("Tex3Scale", 128f)

    discard{ heightmap.load() }

    /** 4. We give the terrain its material, position & scale it, and attach it. */
    terrain.setMaterial(mat_terrain)
    terrain.setLocalTranslation(0, -100, 0)
    terrain.setLocalScale(2f, 1f, 2f)
    discard{ rootNode.attachChild(terrain) }
 
    /** 5. The LOD (level of detail) depends on were the camera is: */
    val cameras = List(getCamera)
    val control = new TerrainLodControl(terrain, cameras.asJava)
    terrain.addControl(control)
  }
}

object HelloTerrain {
  def main(args:Array[String]): Unit = {

    import java.util.logging.{Logger,Level}
    Logger.getLogger("").setLevel(Level.WARNING)

    val app = new HelloTerrain
    app.start()
  }
}
