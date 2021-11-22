package example.pane

import example.dialog.LoginDialog
import example.entity.Model.Entity.Id
import example.entity.Model.errors.ExceptionWrongPassword
import example.repository.{Auth, Repository}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.VBox

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait MainPane {
  self: Repository with Auth =>

  val mainPane: MainPane

  class MainPane extends VBox {


    def login: Future[(Id, String)] = {
      def check(id: Id, password: String) = {
        for {
          card <- repository.findById(id)
          a = auth.authenticate(password.toCharArray, card.password)
        } yield a
      }

      def log(): Future[(Id, String)] = {
        new LoginDialog().showAndWait() match {
          case Some((cardId: Int, password: String)) =>
            for {
              checked <- check(cardId, password)
              a <- if (checked) Future.successful(cardId -> password) else Future.failed(ExceptionWrongPassword(cardId))
            } yield a
          case None =>
            new Alert(AlertType.Error) {
              title = "Error"
              contentText = "No information provided"
            }
            Future.failed(new NoSuchElementException("No information provided"))
        }
      }

      log().fallbackTo(log().fallbackTo(log()))
    }

  }

}


