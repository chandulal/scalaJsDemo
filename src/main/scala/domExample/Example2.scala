package domExample

import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.annotation.JSExport

@JSExport
object Example2 {
  @JSExport
  def main(div: html.Div)={
    val child = dom.document.createElement("div")
    child.textContent="this element is created from scalajs dom"
    div.appendChild(child)
  }
}
