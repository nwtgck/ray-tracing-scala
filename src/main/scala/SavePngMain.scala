import java.awt.image.RenderedImage
import java.io.File
import javax.imageio.ImageIO

/**
  * An entry point for saving as an image
  */
object SavePngMain {
  def main(args: Array[String]): Unit = {
    // Make rendered image
    val image: RenderedImage = Util.makeRenderedImage(width = 512, height = 512, time = 0.0f, calcPixelColor = RayTracing.calcPixelColor _)

    val fpath: String = "images/output.png"

    // Write the image to a file
    // (from: https://www.javadrive.jp/java2d/bufferedImage/index2.html)
    ImageIO.write(image, "png", new File(fpath))

    println(s"##### '${fpath}' saved! #####")
  }
}
