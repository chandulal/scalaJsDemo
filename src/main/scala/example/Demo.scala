package example

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import org.scalajs.dom.html.Canvas
import scala.scalajs.js.annotation.JSExport
import concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

@JSExport
object Demo {

  @JSExport
  def main(canvas: html.Canvas): Unit = {
    canvas.width = dom.innerWidth
    canvas.height = dom.innerHeight
    val queriesItr = getRandomCombinations();
    dom.setInterval(() => getWeatherAndPlotOnUi(queriesItr.next(),canvas), 50)
  }

  private def getWeatherAndPlotOnUi(query: String, canvas: html.Canvas) = {
    val url = s"http://api.openweathermap.org/data/2.5/find?q=${query}&type=like&mode=json"
    Ajax.get(url).foreach { anyCity =>
      val parsed = js.JSON.parse(anyCity.responseText)
      parsed.list.map { (json: js.Dynamic) =>
        val attributes = getAttributes(canvas, json)
        SVGBlock.drawRect(attributes)
      }
    }
  }

  private def getAttributes(canvas: Canvas, json: js.Dynamic): Attributes = {
    val lon = json.coord.lon.asInstanceOf[Double]
    val lat = json.coord.lat.asInstanceOf[Double]
    val pixelX = (lon + 180) / 360 * canvas.width
    val pixelY = canvas.height - (lat + 90) / 180 * canvas.height
    Attributes(lon, lat, pixelX, pixelY)
  }

  private def getRandomCombinations(): Iterator[String] ={
    val aToV: String = "abcdefghijklmnopqrstuv"
    val queries = for {
      first <- aToV
      second <- aToV
      third <- aToV
    } yield s"$first$second$third"
    Iterator.continually(queries).flatten
  }
}

case class Attributes(lon: Double, lat: Double, pixelX: Double, pixelY: Double)