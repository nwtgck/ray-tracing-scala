import java.awt.{Color, Graphics, Graphics2D}
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.image.BufferedImage
import javax.swing.{JPanel, Timer}

/**
  * Panel for animation
  */
class AnimatedPanel(calcPixelColor: (Int, Int, Float, Vector2D) => Color) extends JPanel{

  // Time for animation
  private[this] var time: Float = 0.0f

  // Start timer
  new Timer(50, new ActionListener {
    override def actionPerformed(e: ActionEvent): Unit = {
      repaint()
      time += 0.1f
    }
  }).start()

  override def paintComponent(g: Graphics): Unit = {
    val g2: Graphics2D = g.asInstanceOf[Graphics2D]
    val image: BufferedImage = Util.makeRenderedImage(
      width  = getWidth,
      height = getHeight,
      time   = time,
      calcPixelColor =  calcPixelColor
    )
    g2.drawImage(image, 0, 0, this)
  }
}