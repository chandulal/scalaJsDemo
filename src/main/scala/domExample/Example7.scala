package domExample

import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.html
import scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js.annotation.JSExport

@JSExport
object Example7 {
  @JSExport
  def main(pre: html.Pre)={
    val url ="http://api.openweathermap.org/data/2.5/weather?q=Pune"
    Ajax.get(url).onSuccess{ case xhr =>
      pre.textContent = xhr.responseText
    }
  }
}
