package example

import example.repository.Repository
import example.view.View
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage

object App extends JFXApp3 with Repository {

  override val repository = Repository("db.conf")

  override def start(): Unit = {

    val view = View()

    stage = new PrimaryStage {
      scene = view.sceneGraph
      title = "title"
      //      minHeight = conf.getInt().toDouble
      //      minWidth = conf.getInt("width").toDouble
    }
    val _ = sys.addShutdownHook {
      repository.close()
    }
  }

}
