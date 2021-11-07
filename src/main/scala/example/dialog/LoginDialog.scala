package example.dialog

import example.App
import example.pane.ControlPane
import javafx.scene.Node
import scalafx.Includes.{jfxDialogPane2sfx, jfxNode2sfx}
import scalafx.scene.control._
import scalafx.scene.layout.Region

class LoginDialog extends Dialog[String] {

  val cardNumberField = new TextField {
    headerText = "card number"
  }
  val passwordField = new PasswordField {
    promptText = "print your pin"
    headerText = "insert card and print your password"
  }

  val controls = List[(String, Region)]("passwordField" -> passwordField)
  val controlGridPane = new ControlPane(controls)

  val dialog = dialogPane()
  dialog.buttonTypes = List(ButtonType.OK, ButtonType.Cancel)
  dialog.content = controlGridPane

  val okButton: Node = dialog.lookupButton(ButtonType.OK)
  okButton.disable = passwordField.text.value.trim.isEmpty && cardNumberField.text.value.trim.isEmpty
  passwordField.text.onChange { (_, _, newValue) =>
    okButton.disable = newValue.trim.isEmpty
  }
  cardNumberField.text.onChange { (_, _, newValue) =>
    okButton.disable = newValue.trim.isEmpty
  }

  resultConverter = dialogButton => {
    if (dialogButton == ButtonType.OK)
      passwordField.text.value
    else null
  }

  initOwner(App.stage)
  title = "Auth"
  headerText = "login"
}
