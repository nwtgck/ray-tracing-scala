import java.awt.Color
import javax.swing.JFrame

object SphareRayTracingMain {
  def main(args: Array[String]): Unit = {
    val width: Int = 512
    val height: Int = 512

    val frame = new JFrame()
    val animatedPanel = new AnimatedPanel(calcPixelColor)

    frame.setSize(width, height)
    frame.getContentPane.add(animatedPanel)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setVisible(true)
  }

  /**
    * Distance function
    * @param p
    * @return
    */
  def distanceFunc(p: Vector3D): Float = {
    val radius: Float = 1.0f
    p.norm - radius
  }

  /**
    * Calculate pixel color
    * @param width
    * @param height
    * @param time
    * @param fragCoord
    * @return
    */
  def calcPixelColor(width: Int, height: Int, time: Float, fragCoord: Vector2D): Color = {

    // (highly referenced from: https://wgld.org/d/glsl/g009.html)

    // The number of marching-loop
    val NMarchingLoop: Int   = 16
    // Small number for collision detection
    val Epsilon      : Float = 0.001f

    // fragment position
    val p: Vector2D = (fragCoord * 2.0f - Vector2D(width, height)) / Math.min(height, width)

    // Camera position
    val cPos : Vector3D = Vector3D(0.0f, 0.0f, 2.0f)
    // Camera direction
    val cDir : Vector3D = Vector3D(0.0f, 0.0f, -1.0f)
    // Camera head
    val cUp  : Vector3D  = Vector3D(0.0f, 1.0f, 0.0f)
    // Camera side
    val cSide: Vector3D  = cDir.cross(cUp)
    // Depth of target
    val targetDepth: Float = 1.0f

    // Ray
    val ray: Vector3D = (cSide * p.x + cUp * p.y + cDir * targetDepth).normalize


    // Distance between the ray and the nearest object
    var distance: Float    = 0.0f
    // Length of current ray
    var rLen    : Float    = 0.0f
    // Position of current ray
    var rPos    : Vector3D = cPos
    for(i <- 0 until NMarchingLoop){
      distance = distanceFunc(rPos)
      rLen += distance
      rPos = cPos + ray * rLen
    }

    if(distance.abs < Epsilon){
      Color.white
    } else {
      Color.black
    }
  }
}
