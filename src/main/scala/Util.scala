import java.awt.Color
import java.awt.image.BufferedImage

object Util {
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

  /**
    * Multiply for Color
    * @param color
    * @param f
    * @return
    */
  def colorTimes(color: Color, f: Float): Color =
    new Color(clamp(color.getRed * f, 0, 255).toInt, clamp(color.getGreen * f, 0, 255).toInt, clamp(color.getBlue * f, 0, 255).toInt)


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
    * @param time
    * @return
    */
  def makeRenderedImage(width: Int, height: Int, time: Float, calcPixelColor: (Int, Int, Float, Vector2D) => Color): BufferedImage = {
    val image: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    for{
      x <- 0 until image.getWidth
      y <- 0 until image.getHeight
    } {
      val color: Color = calcPixelColor(
        image.getWidth,
        image.getHeight,
        time,
        Vector2D(x, image.getHeight - y /* NOTE: Change coordinate system */ )
      )
      image.setRGB(x, y, color.getRGB) // (Color#getRGB from: https://www.javamex.com/tutorials/graphics/bufferedimage_setrgb.shtml)
    }

    image
  }
}
