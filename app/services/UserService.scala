package services

import models.{User, Users}
import scala.concurrent.Future

class UserService {

  def addUser(user: User): Future[String] = {
    Users.add(user)
  }

  def deleteUser(id: Long): Future[Long] = {
    Users.delete(id)
  }

  def getUser(id: Long): Future[Option[User]] = {
    Users.get(id)
  }

  def listAllUsers: Future[Seq[User]] = {
    Users.listAll
  }

  def update(user:User):Future[Option[User]]={
     Users.update(user)
  }
}
