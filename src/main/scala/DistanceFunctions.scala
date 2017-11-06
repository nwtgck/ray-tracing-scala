object DistanceFunctions {

  // Sphere
  def sdSphere(p: Vector3D, radius: Float): Float =
    p.norm - radius

  // Torus
  def sdTorus(p: Vector3D, t: Vector2D): Float = {
    val q: Vector2D = Vector2D(Vector2D(p.x, p.y).norm-t.x, p.y)
    q.norm - t.y
  }

  def rotateX(p: Vector3D, angle: Float): Vector3D = {
    val ca: Float = Math.cos(-angle).toFloat
    val sa: Float = Math.sin(-angle).toFloat

    Vector3D(
      x = p.x,
      y = ca * p.y - sa * p.z,
      z = sa * p.y + ca * p.z
    )
  }

  def translateX(p: Vector3D, transX: Float): Vector3D =
    Vector3D(p.x - transX, p.y, p.z)


}
