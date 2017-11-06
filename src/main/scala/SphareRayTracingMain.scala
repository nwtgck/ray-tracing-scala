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
    * Calculate pixel color
    * @param width
    * @param height
    * @param time
    * @param fragCoord
    * @return
    */
  def calcPixelColor(width: Int, height: Int, time: Float, fragCoord: Vector2D): Color = {

    // (highly referenced from: https://qiita.com/doxas/items/477fda867da467116f8d)

    // fragment position
    val p: Vector2D = (fragCoord * 2.0f - Vector2D(width, height)) / Math.min(height, width)

    // Camera position
    val cPos : Vector3D = Vector3D(0.0f, 0.0f, 3.0f)
    // Camera direction
    val cDir : Vector3D = Vector3D(0.0f, 0.0f, -1.0f)
    // Camera head
    val cUp  : Vector3D  = Vector3D(0.0f, 1.0f, 0.0f)
    // Camera side
    val cSide: Vector3D  = cDir.cross(cUp)
    // Depth of target
    val targetDepth: Float = 0.1f

    // Ray
    val ray: Vector3D = (cSide * p.x + cUp * p.y + cDir * targetDepth).normalize

    Util.makeColor(ray.x, ray.y, -ray.z)
  }
}
