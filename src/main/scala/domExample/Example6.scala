package domExample

import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.annotation.JSExport

@JSExport
object Example6 {
  @JSExport
  def main(canvas: html.Canvas)={
    type Ctx2D = dom.CanvasRenderingContext2D
    val ctx = canvas.getContext("2d").asInstanceOf[Ctx2D]

    val w = 400
    canvas.width=w
    canvas.height=w
    ctx.strokeStyle="Red"
    ctx.lineWidth=3
    ctx.beginPath()
    ctx.arc(w/2,w/2,w/2,0,360)
    ctx.stroke()
    ctx.beginPath()
    ctx.arc(w/4,w/3,40,0,360)
    ctx.stroke()
    ctx.beginPath()
    ctx.arc(w-w/4,w/3,40,0,360)
    ctx.stroke()
    ctx.beginPath()
    ctx.moveTo(w/4,w-w/4)
    ctx.lineTo(w-w/4,w-w/4)
    ctx.stroke()
  }
}
