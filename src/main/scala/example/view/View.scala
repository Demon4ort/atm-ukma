package example.view

import example.pane.MenuPane
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

class View {

  val menuPane = new MenuPane

  //  val

  val contentPane = new VBox {
    prefHeight = 600
    prefWidth = 800
    spacing = 6
    padding = Insets(6)
    children = List(menuPane) //List(menuPane, splitPane)
  }

  val sceneGraph = new Scene {
    root = contentPane
  }
}

object View {
  def apply() = new View
}

