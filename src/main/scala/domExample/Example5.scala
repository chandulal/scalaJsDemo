package domExample

import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.annotation.JSExport

@JSExport
object Example5 {
   @JSExport
   def main(in: html.Input, box: html.Div)={
     val key = "my-key"

     in.value= dom.localStorage.getItem(key)
     in.onkeyup = (e: dom.Event) => {
       dom.localStorage.setItem(key, in.value)
       box.textContent = "Saved! " + in.value
     }
   }
 }
