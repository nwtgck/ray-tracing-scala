import java.awt.image.{BufferedImage, RenderedImage}
import java.io.File
import javax.imageio.ImageIO
import java.awt.{Color, Image}

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
    val a: Float = fragCoord.x / width
    val color: Color = new Color(a, a, a)

    color
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
