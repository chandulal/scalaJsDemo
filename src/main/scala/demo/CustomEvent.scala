package demo

import org.scalajs.dom
import org.scalajs.dom._

object CustomEvent {

  def addEventListenersOn(rectangle: Element): Unit = {
    rectangle.addEventListener("mouseover", mouseOver)
    rectangle.addEventListener("mouseout", onMouseOut)
  }

  val mouseOver = { evt: Event =>
    evt.srcElement.setAttributeNS(null, "fill", "#FF0000")
    val lat = evt.srcElement.getAttributeNS("mynamespace", "lat")
    val lon = evt.srcElement.getAttributeNS("mynamespace", "lon")
    dom.console.log("lat " + lat + "lon " + lon)
    Weather.viewWeather(lat.toDouble, lon.toDouble)
  }

  val onMouseOut = { evt: Event =>
    evt.srcElement.setAttributeNS(null, "fill", "#000000")
  }

}
