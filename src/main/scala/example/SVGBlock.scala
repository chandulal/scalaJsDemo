package example

import scala.concurrent.ExecutionContext.Implicits.global
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import scala.scalajs.js

object SVGBlock {
  def drawRect(lon: Double,lat: Double, x: Double, y: Double) {
    val rectangle = dom.document.createElementNS("http://www.w3.org/2000/svg", "rect")
    rectangle.setAttributeNS(null, "x", x.toString)
    rectangle.setAttributeNS(null, "y", y.toString)
    rectangle.setAttributeNS("mynamespace", "lon", lon.toString)
    rectangle.setAttributeNS("mynamespace", "lat", lat.toString)
    rectangle.setAttributeNS(null, "height", "4")
    rectangle.setAttributeNS(null, "width", "4")
    rectangle.setAttributeNS(null, "fill", "#000000")

    rectangle.addEventListener("mouseover",mouseOver)
    dom.document.getElementById("svg").appendChild(rectangle)
  }
  val mouseOver = { evt: Event =>
    val lat = evt.srcElement.getAttributeNS("mynamespace", "lat")
    val lon = evt.srcElement.getAttributeNS("mynamespace", "lon")
    dom.console.log("lat "+lat+"lon "+lon)
    Controls.viewWeather(lat.toDouble,lon.toDouble)
  }
}


object Controls {
  val weatherDiv = dom.document.getElementById("weather")

  def viewWeather(lat: Double,lon: Double)={
    val url = "http://api.openweathermap.org/data/2.5/" +
            s"weather?lat=$lat&lon=$lon"

    Ajax.get(url).foreach { anyCity =>
      val parsed = js.JSON.parse(anyCity.responseText)
        val name = parsed.name.toString
        val weather = parsed.weather.pop().main.toString

        def celsius(kelvins: js.Dynamic) = {
          kelvins.asInstanceOf[Double] - 273.15
        }.toInt

        val min = celsius(parsed.main.temp_min)
        val max = celsius(parsed.main.temp_max)
        val humid = parsed.main.humidity.toString

        weatherDiv.innerHTML = "<h1>Weather in &nbsp;" + name +
          "</h1><ul><li><b>Weather:</b> &nbsp"+ weather +
          "</li><li><b>Humidity:</b> &nbsp"+ humid+
          "%</li><li><b>Temp:</b> &nbsp;"+ min + "-" + max +
          "</li></ul>"
    }
  }
}

