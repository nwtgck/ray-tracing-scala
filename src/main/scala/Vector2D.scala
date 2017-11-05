/**
  * 2D Vector
  * @param x
  * @param y
  */
case class Vector2D(x: Float, y: Float) {

  def +(that: Vector2D): Vector2D =
    Vector2D(this.x + that.x, this.y - that.y)

  def -(that: Vector2D): Vector2D =
    this + (-that)

  def unary_- = Vector2D(-this.x, -this.y)


  def *(f: Float): Vector2D =
    Vector2D(this.x * f, this.y * f)

}
