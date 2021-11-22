package example.repository

import com.typesafe.config.ConfigFactory
import example.entity.Model.Entity.Id
import example.entity.Model.{Card, User}
import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

import java.sql.Date
import java.time.LocalDate
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

trait Repository {

  val repository: Repository

  object Repository {
    def apply(configFile: String): Repository = {
      val config = DatabaseConfig.forConfig[JdbcProfile]("slick-h2", ConfigFactory.load(configFile))
      val repository = new Repository(config, H2Profile)
      try {

      } catch {
        case _: Throwable => repository.createSchema()
      }
      repository
    }
  }

  class Repository(val config: DatabaseConfig[JdbcProfile],
                   val profile: JdbcProfile,
                   val awaitDuration: Duration = 1 second) {

    import profile.api._

    implicit val dateMapper = MappedColumnType.base[LocalDate, Date](ld => Date.valueOf(ld), d => d.toLocalDate)


    val db = config.db

    def await[T](action: DBIO[T]): T = Await.result(db.run(action), awaitDuration)

    def exec[T](action: DBIO[T]): Future[T] = db.run(action)

    def close() = db.close()

    def createSchema() = await(DBIO.seq(schema.create))

    def dropSchema() = await(DBIO.seq(schema.drop))

    class Cards(tag: Tag) extends Table[Card](tag, "cards") {
      def * = (id ?, userId ?, password, currency, value, credit, blocked) <> (Card.tupled, Card.unapply)

      def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

      def userId = column[Int]("userId")

      def password = column[String]("password")

      def currency = column[String]("currency")

      def value = column[BigDecimal]("value")

      def credit = column[BigDecimal]("credit")

      def blocked = column[Boolean]("blocked")

      def userFK = foreignKey("userFK", userId, TableQuery[Users])(_.userId)
    }

    val cards = TableQuery[Cards]

    def findById(cardId: Id) = {
      exec(cards.filter(_.id === cardId).result.map(_.head))
    }

    class Users(tag: Tag) extends Table[User](tag, "users") {
      def userId = column[Int]("userId", O.PrimaryKey, O.AutoInc)

      def name = column[String]("name")

      def surname = column[String]("surname")

      def * = (userId ?, name, surname) <> (User.tupled, User.unapply)
    }

    val users = TableQuery[Users]


    def insert(user: User) = repository.exec(users += user)

    val schema = users.schema ++ cards.schema
  }
}
