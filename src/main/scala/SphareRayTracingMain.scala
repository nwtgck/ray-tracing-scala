import java.awt.Color
import javax.swing.JFrame

object SphareRayTracingMain {
  def main(args: Array[String]): Unit = {
    val width: Int  = 300
    val height: Int = 300

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
  def distanceFunc(p: Vector3D, time: Float): Float = {

    // Rotate X by time
    val p2 = DistanceFunctions.rotateX(p, time * 0.8f)

    // Calc distance of box
    val box: Float = (p2.abs - Vector3D(0.5f, 0.5f, 0.5f)).applyElem(_.max(0.0f)).norm

    // Calc distance of sphere
    val sphere: Float = DistanceFunctions.sdSphere(p2, radius = 0.6f)

    box `max` sphere
  }

  /**
    * Calculate normal vector of p
    * @param p
    * @param distanceFunc
    * @return
    */
  def calcNormal(p: Vector3D, distanceFunc: (Vector3D, Float) => Float, time: Float): Vector3D = {
    val d: Float = 0.0001f // Very small number
    Vector3D(
      distanceFunc(p + Vector3D(d,    0.0f, 0.0f), time) - distanceFunc(p + Vector3D(-d,    0.0f, 0.0f), time),
      distanceFunc(p + Vector3D(0.0f,    d, 0.0f), time) - distanceFunc(p + Vector3D(0.0f,    -d, 0.0f), time),
      distanceFunc(p + Vector3D(0.0f, 0.0f,    d), time) - distanceFunc(p + Vector3D(0.0f, 0.0f,    -d), time)
    ).normalize
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

    // (highly referenced from: https://wgld.org/d/glsl/g010.html)

    // The number of marching-loop
    val NMarchingLoop: Int      = 16
    // Very small number for collision detection
    val Epsilon      : Float    = 0.001f
    // Direction of light
    val lightDir     : Vector3D = Vector3D(-0.577f, 0.577f, 0.577f)

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
      distance = distanceFunc(rPos, time)
      rLen += distance
      rPos = cPos + ray * rLen
    }

    if(distance.abs < Epsilon){
      val normal: Vector3D = calcNormal(rPos, distanceFunc, time)
      val diff  : Float    = Util.clamp(lightDir.dot(normal), 0.1f, 1.0f)
      Util.makeColor(diff, diff, diff)
    } else {
      Color.black
    }
  }
}
