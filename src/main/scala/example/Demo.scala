package example

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import scala.scalajs.js.annotation.JSExport
import concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

@JSExport
object Demo {

  @JSExport
  def main(canvas: html.Canvas): Unit = {

    canvas.width = dom.innerWidth
    canvas.height = dom.innerHeight

    val aToV: String = "abcdefghijklmnopqrstuv"

    val queries = for {
      first <- aToV
      second <- aToV
      third <- aToV
    } yield s"$first$second$third"

    val queriesItr = Iterator.continually(queries).flatten

    def getLocation(query: String) = {

      val url = "http://api.openweathermap.org/data/2.5/" +
        s"find?q=$query&type=like&mode=json"

      Ajax.get(url).foreach { anyCity =>
        val parsed = js.JSON.parse(anyCity.responseText)
        parsed.list.map { (json: js.Dynamic) =>

          val lon = json.coord.lon.asInstanceOf[Double]
          val lat = json.coord.lat.asInstanceOf[Double]

          val pixelX = (lon + 180) / 360 * canvas.width
          val pixelY = canvas.height - (lat + 90) / 180 * canvas.height
          SVGBlock.drawRect(lon,lat,pixelX, pixelY)
        }
      }
    }
    dom.setInterval(() => getLocation(queriesItr.next()), 50)
  }
}
