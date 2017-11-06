import javax.swing.JFrame

/**
  * An entry point for ray tracing animation
  */
object AnimationMain {
  def main(args: Array[String]): Unit = {

    // Show a window
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
