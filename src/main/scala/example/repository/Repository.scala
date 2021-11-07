package example.repository

import com.typesafe.config.ConfigFactory
import example.entity.{Card, User}
import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

import java.sql.Date
import java.time.LocalDate
import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

trait Repository {

  val repository: Repository

  object Repository {
    def apply(configFile: String): Repository = {
      val config = DatabaseConfig.forConfig[JdbcProfile]("repository", ConfigFactory.load(configFile))
      val repository = new Repository(config, H2Profile)
      try {
        //      await( students.list() ).length
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
    val schema = cards.schema ++ users.schema //students.schema ++ grades.schema ++ courses.schema ++ assignments.schema
    val db = config.db

    def await[T](action: DBIO[T]): T = Await.result(db.run(action), awaitDuration)

    def exec[T](action: DBIO[T]): Future[T] = db.run(action)

    def close() = db.close()

    def createSchema() = await(DBIO.seq(schema.create))

    def dropSchema() = await(DBIO.seq(schema.drop))

    class Cards(tag: Tag) extends Table[Card](tag, "cards") {
      def * = (id ?, userId ?, currency, value, credit) <> (Card.tupled, Card.unapply)

      def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

      def userId = column[Int]("userId")

      def currency = column[String]("currency")

      def value = column[BigDecimal]("value")

      def credit = column[BigDecimal]("credit")

      def userFK = foreignKey("userFK", userId, TableQuery[Users])(_.userId)
    }

    object cards extends TableQuery(new Cards(_)) {
      val compiledList = Compiled { userId: Rep[Int] =>
        filter(_.userId === userId).sortBy(_.value.asc)
      }

      def upsert(card: Card) = (this returning this.map(_.userId)).insertOrUpdate(card)
    }

    class Users(tag: Tag) extends Table[User](tag, "users") {
      def userId = column[Int]("userId", O.PrimaryKey, O.AutoInc)

      def name = column[String]("name")

      def surname = column[String]("surname")

      def * = (userId ?, name, surname) <> (User.tupled, User.unapply)
    }

    object users extends TableQuery(new Users(_)) {
      val compiledList = Compiled {
        sortBy(_.surname.asc)
      }


    }
  }
}
