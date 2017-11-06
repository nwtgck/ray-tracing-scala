import java.awt.image.RenderedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame



object Main {
  def main(args: Array[String]): Unit = {

    // Save as a image
    if(false){
      // Make rendered image
      val image: RenderedImage = Util.makeRenderedImage(width = 512, height = 512, time = 0.0f, calcPixelColor = RayTracing.calcPixelColor _)

      // Write the image to a file
      // (from: https://www.javadrive.jp/java2d/bufferedImage/index2.html)
      ImageIO.write(image, "png", new File("images/output.png"))
    }


    // Show a window
    if(true) {
      val width: Int = 512
      val height: Int = 512

      val frame = new JFrame()
      val animatedPanel = new AnimatedPanel(RayTracing.calcPixelColor)

      frame.setSize(width, height)
      frame.getContentPane.add(animatedPanel)
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
      frame.setVisible(true)
    }
  }
}
