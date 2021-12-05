package example.repository

import com.typesafe.config.ConfigFactory
import example.entity.Model.Entity.Id
import example.entity.Model.{Card, Transaction, User}
import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

import java.sql.Date
import java.time.{LocalDate, LocalDateTime}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, Future}
import scala.language.postfixOps



  object Repository {
    def apply(configFile: String = "application.conf"): Repository = {
      val config = DatabaseConfig.forConfig[JdbcProfile]("slick-h2", ConfigFactory.load(configFile))
      val repository = new Repository(config, H2Profile)
      try {
        repository.findNumberUser()
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

    def close() = {
      dropSchema()
      db.close()
    }

    def createSchema() = await(DBIO.seq(schema.create))

    def dropSchema() = await(DBIO.seq(schema.drop))

    class Cards(tag: Tag) extends Table[Card](tag, "cards") {
      //      def * = (id ?, userId ?, currency, value, credit, blocked) <> (Card.tupled, Card.unapply)
      def * = (id ?, userId ?, value, blocked) <> (Card.tupled, Card.unapply)

      def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

      def userId = column[Int]("userId")

      //      def currency = column[String]("currency")

      def value = column[Double]("value")

      def blocked = column[Boolean]("blocked")

      def userFK = foreignKey("userFK", userId, TableQuery[Users])(_.id)
    }

    val cards = TableQuery[Cards]

    def insert(card: Card) = {
      exec((cards returning cards.map(_.id)) += card)
    }

    def findById(cardId: Id) = {
      exec(cards.filter(_.id === cardId).result.map(_.head))
    }

    def findByIdOpt(cardId: Id) = {
      exec(cards.filter(_.id === cardId).result.map(_.headOption))
    }

    def findByUserId(userId: Int) = {
      exec(cards.filter(_.userId === userId).result.map(_.head))
    }

    def updateBalance(cardId: Int, value: Double) = {
      val a = for {c <- cards if c.id === cardId} yield c.value
      exec(a.update(value))
    }


    class Users(tag: Tag) extends Table[User](tag, "users") {
      def id = column[Int]("userId", O.PrimaryKey, O.AutoInc)

      def userName = column[String]("user_name")

      def password = column[String]("user_password")

      def * = (id ?, userName, password) <> (User.tupled, User.unapply)
    }

    val users = TableQuery[Users]


    def insert(user: User) = exec((users returning users.map(_.id)) += user)

    def findByUsername(userName: String) = {
      exec(users.filter(_.userName === userName).result.headOption)
    }

    def findNumberUser() = await(users.result).size

    class Transactions(tag: Tag) extends Table[Transaction](tag, "transactions") {
      def id = column[Int]("transaction_id", O.PrimaryKey, O.AutoInc)

      def fromId = column[Int]("from_id")

      def toId = column[Int]("to_id")

      def value = column[Double]("transaction_value")

      def date = column[LocalDateTime]("transaction_date")

      def * = (id ?, fromId, toId, value, date) <> (Transaction.tupled, Transaction.unapply)
    }

    val transactions = TableQuery[Transactions]

    def insert(transaction: Transaction) = {
      exec((transactions returning transactions.map(_.id)) += transaction)
    }

    val schema = users.schema ++ cards.schema ++ transactions.schema
  }

