package example.service

import Storage.CardInfo
import cats.implicits.catsSyntaxOptionId
import example.App
import example.entity.Model.{Card, User}
import example.service.AuthService.{UserAlreadyExistsException, WrongPasswordException, timeout}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

class AuthService {


  private def processLogin(login: String, password: String) = {
    for {
      userOpt <- App.repository.findByUsername(login)
      result = userOpt match {
        case None => throw new NoSuchElementException(s"User: $login not found")
        case Some(user) =>
          if (App.auth.authenticate(password.toCharArray, user.password)) {
            user
          } else throw new WrongPasswordException()
      }
      userId = result.id.get
      card <- App.repository.findByUserId(userId)
      _ = {
        CardInfo.setCard(card)
      }
    } yield result
  }

  def login(login: String, password: String) = Await.result(processLogin(login, password), timeout)


  private def processRegister(userName: String, password: String) = {
    val user = User(userName = userName, password = password)
    for {
      userOpt <- App.repository.findByUsername(user.userName)
      cryptedUser = userOpt match {
        case Some(user) => throw new UserAlreadyExistsException(s"User: ${user.userName} already exists")
        case None => user.copy(password = App.auth.hash(user.password.toCharArray))
      }
      userId <- App.repository.insert(cryptedUser)
      _ <- App.repository.insert(Card(userId = userId.some))
    } yield userId
  }

  def register(userName: String, password: String) = Await.result(processRegister(userName, password), timeout)
}

object AuthService {
  val timeout = 10 seconds

  class WrongPasswordException() extends RuntimeException

  class UserAlreadyExistsException(message: String) extends RuntimeException(message)
}