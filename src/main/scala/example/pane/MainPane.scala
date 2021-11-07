package example.pane

import example.dialog.LoginDialog
import example.repository.Repository
import scalafx.scene.layout.VBox

class MainPane extends VBox {
  self: Repository =>
  //  @tailrec
  def login(i: Int): String = {
    val res = new LoginDialog().showAndWait() match {
      case Some(pass: String) => pass
      case _ => ""
    }
    res
  }


}
