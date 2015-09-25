package domExample

import org.scalajs.dom.html

import scala.scalajs.js.annotation.JSExport

@JSExport
object Example8 {
@JSExport
  def main(div: html.Div) = {

    val colors = Seq(
      "red", "green", "blue"
    )
    val index =
      util.Random.nextInt(colors.length)
    div.style.color = colors(index)
  }
}
