package controllers
import play.api.mvc._
import javax.inject._
import models.User
import services.UserService
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.parsing.json._
import utils._

import scala.concurrent.Await
import scala.concurrent.duration._
import java.io._
import java.nio.charset.StandardCharsets
import akka.stream.scaladsl._
import akka.util.ByteString
import scala.concurrent.Future

class UserController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  val v=new UserService;

  def add(firstName:String,lastName:String,mobile:Long,email:String)=Action.async{
    implicit request=>
    val newUser=User(0,firstName,lastName,mobile,email)
    v.addUser(newUser).map(res =>Ok(res))
  }

  def read=Action.async{
  implicit request=>
   //v.listAllUsers.map(res=>Ok(JsonUtils.serializeUserList(res)))
   val r=Await.result( v.listAllUsers,5 seconds)
   val js=
     r match{
   case u:Seq[User]=>JsonUtils.serializeUserList(u)
   }
   val stream:InputStream = new ByteArrayInputStream(js.getBytes(StandardCharsets.UTF_8))
   val dataContent: Source[ByteString, _] = StreamConverters.fromInputStream(() => stream)
   Future(Ok.chunked(dataContent).as(JSON))
  }

  def get(id:Int)=Action.async
  {
     implicit request=>
     //v.getUser(id).map(res=>Ok(JsonUtils.serializeUser(res.getOrElse(null))))
     val r=Await.result( v.getUser(id),5 seconds)
     val js=
     r match{
      case Some(u)=>JsonUtils.serializeUser(u)
      case None=>null
     }
    val stream:InputStream = new ByteArrayInputStream(js.getBytes(StandardCharsets.UTF_8))
    val dataContent: Source[ByteString, _] = StreamConverters.fromInputStream(() => stream)
    Future(Ok.chunked(dataContent).as(JSON))
  }

  def delete(id:Int)=Action.async
  {
    implicit request=>
    v.deleteUser(id).map(res=>Ok("Deleted Successfully"))
  }

  def update(id:Long,firstName:String,lastName:String,mobile:Long,email:String)=Action.async{
    implicit request=>
    val newUser=User(id,firstName,lastName,mobile,email)
    v.update(newUser).map(res=>Ok(JsonUtils.serializeUser(res.getOrElse(null))))
  }
}
