package utils

import models.User
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization

object JsonUtils
{
def serializeUser(result:User ) = {
  implicit val formats = org.json4s.DefaultFormats
  Serialization.write(result)
}

def serializeUserList(result:Seq[User] ) = {
implicit val formats = org.json4s.DefaultFormats
Serialization.write(result)
}
}
