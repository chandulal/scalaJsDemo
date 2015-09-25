package domExample

import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.annotation.JSExport

@JSExport
object Example3 {
  @JSExport
  def main(pre: html.Pre)={
    pre.onmousemove ={
      (event: dom.MouseEvent)=>
        pre.textContent =
        s"""|e.clientX ${event.clientX}
            |e.clientX ${event.clientY}
            |e.clientX ${event.pageX}
            |e.clientX ${event.pageY}
            |e.clientX ${event.screenX}
            |e.clientX ${event.screenY}
           """.stripMargin
    }
  }
}
