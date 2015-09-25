package domExample

import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.annotation.JSExport

@JSExport
object Example4 {
  @JSExport
  def main(in: html.Input, out: html.Div)={
    in.onkeyup = { (e: dom.Event) =>
      out.textContent = dom.btoa(in.value)
    }
  }
}
