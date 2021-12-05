package example.service

import Storage.CardInfo
import example.App
import example.service.BalanceManager.timeout

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

class BalanceManager {

  private def processUpdateBalance(value: Double) = {
    val cardId = CardInfo.getCard.id.get
    for {
      _ <- App.repository.updateBalance(cardId, value)
      cardUpdated <- App.repository.findById(cardId)
      _ = {
        CardInfo.setCard(cardUpdated)
      }
    } yield cardUpdated
  }

  def updateBalance(value: Double) = Await.result(processUpdateBalance(value), timeout)


}

object BalanceManager {
  val timeout = 5 seconds
}