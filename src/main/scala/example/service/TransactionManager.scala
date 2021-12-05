package example.service

import Storage.CardInfo
import example.App
import example.entity.Model.Transaction

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class TransactionManager {

  private def processTransaction(toId: Int, value: Double) = {
    val cardFrom = CardInfo.getCard
    val fromId = cardFrom.id.get
    val res = for {
      toCardOpt <- App.repository.findByIdOpt(toId)
      cardTo = toCardOpt match {
        case None => throw new NoSuchElementException(s"card not found: $toId")
        case Some(card) => card
      }
      _ <- App.repository.updateBalance(fromId, cardFrom.value - value)
      _ <- App.repository.updateBalance(toId, cardTo.value + value)
      res <- App.repository.insert(Transaction(fromId = fromId, toId = toId, value = value))
    } yield res
    CardInfo.setCard(cardFrom.copy(value = cardFrom.value - value))
    res
  }

  def transaction(toId: Int, value: Double) = Await.result(processTransaction(toId, value), 10 seconds)

}
