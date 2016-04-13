package demo

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

object Weather {

  def getWeatherAndPlotOnUi(query: String, canvas: html.Canvas) = {
    val url = s"http://api.openweathermap.org/data/2.5/find?q=$query&type=like&mode=json" +
      s"&APPID=e59499e71cae5e8de2730b3f21b5123d"
    Ajax.get(url).map { anyCity =>
      val parsed = js.JSON.parse(anyCity.responseText)
      parsed.list.map { (json: js.Dynamic) =>
        val attributes = Attributes.getAttributes(canvas, json)
        SVGBlock.drawRect(attributes)
      }
    }
  }

  def viewWeather(lat: Double, lon: Double) = {
    val url = "http://api.openweathermap.org/data/2.5/" +
      s"weather?lat=$lat&lon=$lon&APPID=e59499e71cae5e8de2730b3f21b5123d"

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

  private def setWeatherInfoInHTML(weatherInfo: WeatherInfo) = {

    val message = "<h1>Weather in &nbsp;" + weatherInfo.name +
      "</h1><ul><li><b>Weather:</b> &nbsp" + weatherInfo.weather +
      "</li><li><b>Humidity:</b> &nbsp" + weatherInfo.humid +
      "%</li><li><b>Temp:</b> &nbsp;" + weatherInfo.min + "-" + weatherInfo.max +
      "</li></ul>"
    val weatherDiv = dom.document.getElementById("weather")
    weatherDiv.innerHTML = message
  }

  private def celsius(kelvins: js.Dynamic) = {
    kelvins.asInstanceOf[Double] - 273.15
  }.toInt

}
