package example
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

object SVGBlock {
  def drawRect(attr: Attributes) {
    val rectangle = dom.document.createElementNS("http://www.w3.org/2000/svg", "rect")
    val updatedRectangle = updateWithAttributes(attr, rectangle)
    rectangle.addEventListener("mouseover",mouseOver)
    dom.document.getElementById("svg").appendChild(updatedRectangle)
  }

  val mouseOver = { evt: Event =>
    val lat = evt.srcElement.getAttributeNS("mynamespace", "lat")
    val lon = evt.srcElement.getAttributeNS("mynamespace", "lon")
    dom.console.log("lat "+lat+"lon "+lon)
    Controls.viewWeather(lat.toDouble,lon.toDouble)
  }

  private def updateWithAttributes(attr: Attributes, rectangle: Element): Element = {
    rectangle.setAttributeNS(null, "x", attr.pixelX.toString)
    rectangle.setAttributeNS(null, "y", attr.pixelY.toString)
    rectangle.setAttributeNS("mynamespace", "lon", attr.lon.toString)
    rectangle.setAttributeNS("mynamespace", "lat", attr.lat.toString)
    rectangle.setAttributeNS(null, "height", "4")
    rectangle.setAttributeNS(null, "width", "4")
    rectangle.setAttributeNS(null, "fill", "#000000")
    rectangle
  }
}

object Controls {

  def setWeatherInfoInHTML(weatherInfo: WeatherInfo) = {

    val message = "<h1>Weather in &nbsp;" + weatherInfo.name +
      "</h1><ul><li><b>Weather:</b> &nbsp"+ weatherInfo.weather +
      "</li><li><b>Humidity:</b> &nbsp"+ weatherInfo.humid+
      "%</li><li><b>Temp:</b> &nbsp;"+ weatherInfo.min + "-" + weatherInfo.max +
      "</li></ul>"
    val weatherDiv = dom.document.getElementById("weather")
    weatherDiv.innerHTML = message
  }

  def viewWeather(lat: Double,lon: Double)={
    val url = "http://api.openweathermap.org/data/2.5/" +
      s"weather?lat=$lat&lon=$lon"

    Ajax.get(url).foreach { anyCity =>
      val parsed = js.JSON.parse(anyCity.responseText)
      val weatherInfo = getWeatherInfo(parsed)
      setWeatherInfoInHTML(weatherInfo)
    }
  }

  private def getWeatherInfo(parsed: js.Dynamic): WeatherInfo = {
    val name = parsed.name.toString
    val weather = parsed.weather.pop().main.toString
    val min = celsius(parsed.main.temp_min)
    val max = celsius(parsed.main.temp_max)
    val humid = parsed.main.humidity.toString
    WeatherInfo(name, weather, humid, min, max)
  }

  private def celsius(kelvins: js.Dynamic) = {
    kelvins.asInstanceOf[Double] - 273.15
  }.toInt

}

case class WeatherInfo(name: String, weather: String, humid: String, min: Int, max: Int)
