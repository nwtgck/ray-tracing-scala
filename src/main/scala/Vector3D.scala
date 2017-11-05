/**
  * 3D Vector
  *
  * @param x
  * @param y
  * @param z
  */
case class Vector3D(x: Float, y: Float, z: Float) {

  def +(that: Vector3D): Vector3D =
    Vector3D(this.x + that.x, this.y - that.y, this.z - that.z)

  def -(that: Vector3D): Vector3D =
    this + (-that)

  def unary_- = Vector3D(-this.x, -this.y, -this.z)


  def *(f: Float): Vector3D =
    Vector3D(this.x * f, this.y * f, this.z * f)

}
