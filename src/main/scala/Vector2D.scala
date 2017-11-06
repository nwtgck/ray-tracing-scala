/**
  * 2D Vector
  * @param x
  * @param y
  */
case class Vector2D(x: Float, y: Float) {

  def +(that: Vector2D): Vector2D =
    Vector2D(x + that.x, y + that.y)

  def -(that: Vector2D): Vector2D =
    this + (-that)

  def unary_- = Vector2D(-x, -y)


  def *(f: Float): Vector2D =
    Vector2D(x * f, y * f)

  def /(f: Float): Vector2D =
    Vector2D(x / f, y / f)

  def norm: Float =
    Math.sqrt(x*x + y*y).toFloat

  def normalize: Vector2D =
    this / norm

  def dot(that: Vector2D): Float =
    x*that.x + y*that.y


  def applyElem(f: Float => Float): Vector2D =
    Vector2D(f(x), f(y))

  def abs: Vector2D = this.applyElem(_.abs)

}
