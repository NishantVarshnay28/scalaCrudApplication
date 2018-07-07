package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class User(id: Long, firstName: String, lastName: String, mobile: Long, email: String)


class UserTableDef(tag: Tag) extends Table[User](tag, "users")
{

   def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
   def firstName = column[String]("first_name")
   def lastName = column[String]("last_name")
   def mobile = column[Long]("mobile")
   def email = column[String]("email")

  override def * =
    (id, firstName, lastName, mobile, email) <>(User.tupled, User.unapply)
}

object Users {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val users = TableQuery[UserTableDef]

  def add(user: User): Future[String] = {
    dbConfig.db.run(users += user).map(res => "User successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Long] = Future{
    dbConfig.db.run(users.filter(_.id === id).delete)
    id
  }

  def get(id: Long): Future[Option[User]] = {
    dbConfig.db.run(users.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[User]] = {
    dbConfig.db.run(users.result)
  }

  def update(user: User): Future[Option[User]] = {
    dbConfig.db.run(users.filter(_.id === user.id).update(user)).map{
      case 0 => None
      case _ => Some(user)
    }
    }
  }
