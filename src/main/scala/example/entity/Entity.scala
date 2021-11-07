package example.entity

import cats.implicits.none
import example.entity.Entity.Id

import java.util.UUID

sealed trait Entity extends Product with Serializable


final case class Card(id: Option[Id] = none,
                      userId: Option[Id] = none,
                      currency: String = UUID.randomUUID.toString,
                      value: BigDecimal = 10,
                      credit: BigDecimal = 0) extends Entity

final case class User(userId: Option[Id] = none,
                      name: String = "default-user-name",
                      surname: String = "default-user-surname") extends Entity

object Entity {
  type Id = Int

  implicit def cardOrdering: Ordering[Card] = Ordering.by(_.value)
}