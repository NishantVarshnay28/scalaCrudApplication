package test

import org.scalatestplus.play._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._
import services.UserService
import models.User

class UserModelTest extends FunSuite with BeforeAndAfter with MockitoSugar
{
   test ("User get method"){
   val service = mock[UserService]
   when(service.getUser(6)).thenReturn(Future.successful(Some(User(6,"nishant","varshnay",8295148887L,"nishantvarshney28@gmail.com"))))
   when(service.getUser(11)).thenReturn(Future.successful(None))

    val v1 = Await.result(service.getUser(6),5 seconds)
    val v2 = Await.result(service.getUser(11),5 seconds)

    assert(v1.get ==User(6,"nishant","varshnay",8295148887L,"nishantvarshney28@gmail.com") )
    assert(v2 == None)
}

test ("User update method"){
val service = mock[UserService]
when(service.update(User(7,"Abhinav","Malik",11510582L,"malik@gmail.com"))).thenReturn(Future.successful(Some(User(7,"Abhinav","Malik",11510582L,"malik@gmail.com"))))
when(service.update(User(11,"Abhinav","Malik",11510582L,"malik@gmail.com"))).thenReturn(Future.successful(None))

 val v1 = Await.result(service.update(User(7,"Abhinav","Malik",11510582L,"malik@gmail.com")),5 seconds)
 val v2 = Await.result(service.update(User(11,"Abhinav","Malik",11510582L,"malik@gmail.com")),5 seconds)

 assert(v1.get == User(7,"Abhinav","Malik",11510582L,"malik@gmail.com"))
 assert(v2 ==None)
}

test ("User delete method"){
   val service = mock[UserService]
   when(service.deleteUser(8)).thenReturn(Future.successful(8L))
   val v1 = Await.result(service.deleteUser(8L),5 seconds)
    assert(v1 == 8L)
}

test ("User add method"){
   val service = mock[UserService]
   when(service.addUser(User(0,"Anmol","Gupta",11510218L,"anticboyanmol@gmail.com"))).thenReturn(Future.successful("User successfully added"))
   val v1 = Await.result(service.addUser(User(0,"Anmol","Gupta",11510218L,"anticboyanmol@gmail.com")),5 seconds)
   assert(v1 == "User successfully added")
}

}
