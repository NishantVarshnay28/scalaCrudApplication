package test

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.test.Helpers._
import scala.util.parsing.json._
import utils._

class UserControllerTest extends PlaySpec with GuiceOneAppPerSuite
{
  "UseController #add Method" should {

   "should add the useer" in {
    val home = route(app, FakeRequest(GET, "/add/anmol/gupta/11510212/anticboyanmol")).get

    status(home) mustBe Status.OK
    contentType(home) mustBe Some("text/plain")
    contentAsString(home) must include ("User successfully added")
  }

}

"UseController #get Method" should {

 "should show the Details" in {
    contentAsString(route(app, FakeRequest(GET,  "/get/1")).get) mustBe """{"id":1,"firstName":"nishant","lastName":"varshney","mobile":8881092588,"email":"nishantvarshney28@gmail.com"}"""
}

}

"UseController #delete Method" should {

 "should delete the user" in {
  val home = route(app, FakeRequest(GET, "/delete/2")).get

  status(home) mustBe Status.OK
  contentType(home) mustBe Some("text/plain")
  contentAsString(home) must include ("Deleted Successfully")
}

}

"UserController #update Method" should {
"should update the user" in{
contentAsString(route(app,FakeRequest(GET, "/update/3/yash/gulani/8295177772/yash@gmail.com")).get) mustBe """{"id":3,"firstName":"yash","lastName":"gulani","mobile":8295177772,"email":"yash@gmail.com"}"""
}
}

}
