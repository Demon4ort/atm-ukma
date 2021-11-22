package example.entity

import cats.implicits.none
import example.entity.Model.Entity.Id

import java.util.UUID

object Model {
  sealed trait Entity extends Product with Serializable

  trait Command extends Product with Serializable

  final case class Block(cardId: Id) extends Command

  final case class Maintenance() extends Command


  final case class Card(id: Option[Id] = none,
                        userId: Option[Id] = none,
                        password: String = "test",
                        currency: String = UUID.randomUUID.toString,
                        value: BigDecimal = 10,
                        credit: BigDecimal = 0,
                        blocked: Boolean = false) extends Entity

  final case class User(userId: Option[Id] = none,
                        name: String = "default-user-name",
                        surname: String = "default-user-surname") extends Entity

  object Entity {
    type Id = Int

    implicit def cardOrdering: Ordering[Card] = Ordering.by(_.value)
  }

  object errors {
    case class ExceptionWrongPassword(cardId: Id) extends Throwable

    case class ApplicationException(msg: String) extends Throwable
  }
}


