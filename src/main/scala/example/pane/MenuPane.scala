package example.pane

import example.App
import scalafx.application.Platform
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._

class MenuPane extends MenuBar {
  val aboutDialog = new Alert(AlertType.Information) {
    initOwner(App.stage)
    title = "about"
    headerText = "developer"
    contentText = "app"
  }

  val aboutMenuItem = new MenuItem("about") {
    onAction = { _ =>
      aboutDialog.showAndWait()
      ()
    }
  }

  val separator = new SeparatorMenuItem()

  val exitMenuItem = new MenuItem("exit") {
    onAction = { _ => Platform.exit() }
  }

  val menu = new Menu("menu") {
    items = List(aboutMenuItem, separator, exitMenuItem)
  }

  menus = List(menu)
  useSystemMenuBar = false
}