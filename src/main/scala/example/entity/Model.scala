package example.entity

import cats.implicits.none
import example.entity.Model.Entity.Id

import java.time.LocalDateTime

object Model {
  sealed trait Entity extends Product with Serializable

  final case class Card(id: Option[Id] = none,
                        userId: Option[Id] = none,
                        value: Double = 0,
                        blocked: Boolean = false) extends Entity

  final case class User(id: Option[Id] = none,
                        userName: String = "default-user-name",
                        password: String) extends Entity

  final case class Transaction(id: Option[Id] = none,
                               fromId: Id,
                               toId: Id,
                               value: Double,
                               date: LocalDateTime = LocalDateTime.now) extends Entity

  object Entity {
    type Id = Int

    implicit def cardOrdering: Ordering[Card] = Ordering.by(_.value)
  }

}


