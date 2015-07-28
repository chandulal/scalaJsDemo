package example

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import scala.scalajs.js.annotation.JSExport
import concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import js.Dynamic.{global => g}

@JSExport
object Demo extends {

  @JSExport
  def main(canvas: html.Canvas): Unit = {

    canvas.width = dom.innerWidth
    canvas.height = dom.innerHeight

    val queries = for {
      a <- "abcdefghijklmnopqrstuv"
      b <- "abcdefghijklmnopqrstuv"
      c <- "abcdefghijklmnopqrstuv"
    } yield s"$a$b$c"

    var i = 0

    def getStuff(query: String) = {

      val url = "http://api.openweathermap.org/data/2.5/" +
        s"find?q=$query&type=like&mode=json"

      Ajax.get(url).foreach { xhr =>
        val parsed = js.JSON.parse(xhr.responseText)
        parsed.list.map { (el: js.Dynamic) =>

          val name = el.name.toString
          val weather = el.weather.pop().main.toString

          def celsius(kelvins: js.Dynamic) = {
            kelvins.asInstanceOf[Double] - 273.15
          }.toInt

          val min = celsius(el.main.temp_min)
          val max = celsius(el.main.temp_max)
          val humid = el.main.humidity.toString
          val lon = el.coord.lon.asInstanceOf[Double]
          val lat = el.coord.lat.asInstanceOf[Double]

          val lon2 = (lon + 180) / 360 * canvas.width
          val lat2 = canvas.height - (lat + 90) / 180 * canvas.height
          g.drawRect(name, lon2, lat2, weather, humid, min, max)
        }
        dom.console.log(parsed)
      }
      i += 1
    }
    dom.setInterval(() => getStuff(queries(i % queries.length)), 50)
  }
}