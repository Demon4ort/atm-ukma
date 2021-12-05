package example

import example.repository.{Auth, Repository}
import javafx.fxml.FXMLLoader
import javafx.scene.layout.AnchorPane
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object App extends JFXApp {

  val repository: Repository = Repository()
  val auth: Auth = Auth()
  val root: AnchorPane = FXMLLoader.load[AnchorPane](getClass.getClassLoader.getResource("Frontend/auth.fxml"))
  stage = new PrimaryStage {
    scene = new Scene(root, 600, 400)
    title = "Hello world"
  }
  val _ = sys.addShutdownHook {
    repository.close()
  }
}
