package demo

import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.annotation.JSExport

@JSExport
object Demo {

  @JSExport
  def main(canvas: html.Canvas): Unit = {
    canvas.width = dom.innerWidth
    canvas.height = dom.innerHeight
    val queriesItr = RandomStringGenerator.getRandomCombinationsOfString();
    dom.setInterval(() => Weather.getWeatherAndPlotOnUi(queriesItr.next(), canvas), 50)
  }
}