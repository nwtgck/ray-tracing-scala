import java.awt.image.{BufferedImage, RenderedImage}
import java.io.File
import javax.imageio.ImageIO
import java.awt.{Color, Image}

/**
  * Ray
  * @param origin
  * @param direction
  */
case class Ray(origin: Vector3D, direction: Vector3D)

object Main {
  def main(args: Array[String]): Unit = {


    // Make rendered image
    val image: RenderedImage = makeRenderedImage(width = 512, height = 512)

    // Write the image to a file
    // (from: https://www.javadrive.jp/java2d/bufferedImage/index2.html)
    ImageIO.write(image, "png", new File("output.png"))
  }

  /**
    * Calculate pixel color
    * @param width
    * @param height
    * @param fragCoord
    * @return
    */
  def calcPixelColor(width: Int, height: Int, fragCoord: Vector2D): Color = {

    // (highly referenced from: https://qiita.com/doxas/items/477fda867da467116f8d)

    val p: Vector2D = (fragCoord * 2.0f - Vector2D(width, height)) / Math.min(height, width)
    val ray: Ray = Ray(
      origin    = Vector3D(0.0f, 0.0f, 5.0f),
      direction = Vector3D(p.x, p.y, -1.0f).normalize
    )

    val color: Color = makeColor(ray.direction.x, ray.direction.y, ray.direction.z)

    color
  }

  /**
    * Making color utility
    * @param r
    * @param g
    * @param b
    * @return
    */
  def makeColor(r: Float, g: Float, b: Float): Color = {
    new Color(clamp(r, 0.0f, 1.0f), clamp(g, 0.0f, 1.0f), clamp(b, 0.0f, 1.0f))
  }

  def clamp(f: Float, min: Float, max: Float): Float =
    if(f < min){
      min
    } else if(f > max){
      max
    } else {
      f
    }



  /**
    * Make rendered image
    * @param width
    * @param height
    * @return
    */
  def makeRenderedImage(width: Int, height: Int): RenderedImage = {
    val image: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    for{
      x <- 0 until image.getWidth
      y <- 0 until image.getHeight
    } {
      val color: Color = calcPixelColor(
        image.getWidth,
        image.getHeight,
        fragCoord = Vector2D(x, image.getHeight - y /* NOTE: Change coordinate system */ )
      )
      image.setRGB(x, y, color.getRGB) // (Color#getRGB from: https://www.javamex.com/tutorials/graphics/bufferedimage_setrgb.shtml)
    }

    image
  }

}
