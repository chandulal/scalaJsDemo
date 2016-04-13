package demo

import org.scalajs.dom.html._
import org.scalajs.dom.raw.Element
import scala.scalajs.js

case class Attributes(lon: Double, lat: Double, pixelX: Double, pixelY: Double) {

  def updateRectangle(rectangle: Element) = {
    rectangle.setAttributeNS(null, "x", pixelX.toString)
    rectangle.setAttributeNS(null, "y", pixelY.toString)
    rectangle.setAttributeNS("mynamespace", "lon", lon.toString)
    rectangle.setAttributeNS("mynamespace", "lat", lat.toString)
    rectangle.setAttributeNS(null, "height", "4")
    rectangle.setAttributeNS(null, "width", "4")
    rectangle.setAttributeNS(null, "fill", "#000000")
    rectangle
  }
}

object Attributes {

  def getAttributes(canvas: Canvas, json: js.Dynamic): Attributes = {
    val lon = json.coord.lon.asInstanceOf[Double]
    val lat = json.coord.lat.asInstanceOf[Double]
    val pixelX = (lon + 180) / 360 * canvas.width
    val pixelY = canvas.height - (lat + 90) / 180 * canvas.height
    Attributes(lon, lat, pixelX, pixelY)
  }
}

