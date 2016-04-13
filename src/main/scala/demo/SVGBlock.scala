package demo

import org.scalajs.dom

object SVGBlock {

  def drawRect(attr: Attributes) {
    val rectangle = dom.document.createElementNS("http://www.w3.org/2000/svg", "rect")
    val updatedRectangle = attr.updateRectangle(rectangle)
    CustomEvent.addEventListenersOn(rectangle)
    dom.document.getElementById("svg").appendChild(updatedRectangle)
  }

}
