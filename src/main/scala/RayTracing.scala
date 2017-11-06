import java.awt.Color


/**
  * This is main part of RayTracing
  */
object RayTracing {

  /**
    * Ray
    * @param origin
    * @param direction
    */
  private case class Ray(origin: Vector3D, direction: Vector3D)

  /**
    * Sphere
    * @param radius
    * @param position
    * @param color
    */
  private case class Sphere(radius: Float, position: Vector3D, color: Color)


  /**
    * Intersection
    * @param hits     flag of hits
    * @param hitPoint coordinate of hit
    * @param normal   normal vector
    * @param color    color or hit
    */
  private case class Intersection(hits: Boolean, hitPoint: Vector3D, normal: Vector3D, color: Color)

  /**
    * Calculate pixel color
    * @param width
    * @param height
    * @param time
    * @param fragCoord
    * @return
    */
  def calcPixelColor(width: Int, height: Int, time: Float, fragCoord: Vector2D): Color = {

    // (highly referenced from: https://qiita.com/doxas/items/477fda867da467116f8d)

    // fragment position
    val p: Vector2D = (fragCoord * 2.0f - Vector2D(width, height)) / Math.min(height, width)

    // Create a ray
    val ray: Ray = Ray(
      origin    = Vector3D(0.0f, 0.0f, 5.0f),
      direction = Vector3D(p.x, p.y, -1.0f).normalize
    )

    // Make a sphere
    val sphere: Sphere = Sphere(
      radius   = 1.0f,
      position = Vector3D(Math.cos(time+Math.PI/2).toFloat, Math.sin(time).toFloat, 2*Math.sin(time).toFloat),
      color    = Color.white
    )

    // hit check
    val i: Intersection = intersectSphere(ray, sphere)

    i.color
  }

  private def intersectSphere(ray: Ray, sphere: Sphere): Intersection = {

    val notHitIntersection: Intersection = Intersection(
      hits     = false,
      hitPoint = Vector3D(0.0f, 0.0f, 0.0f),
      normal   = Vector3D(0.0f, 0.0f, 0.0f),
      color    = Color.black
    )

    val a: Vector3D = ray.origin - sphere.position
    val b: Float = a.dot(ray.direction)
    val c: Float = a.dot(a) - (sphere.radius * sphere.radius)
    val d: Float = b * b - c
    if(d > 0.0){
      val t: Float = -b - Math.sqrt(d).toFloat
      if(t > 0.0){
        val hitPoint: Vector3D = ray.origin + ray.direction * t
        val normal  : Vector3D = (hitPoint - sphere.position).normalize
        val d       : Float    = Util.clamp(Vector3D(1.0f, 1.0f, 1.0f).normalize.dot(normal), 0.1f, 1.0f)
        Intersection(
          hits     = true,
          hitPoint = hitPoint,
          normal   = normal,
          color    = Util.colorTimes(sphere.color, d)
        )
      } else {
        notHitIntersection
      }
    } else {
      notHitIntersection
    }
  }
}
