package controllers
import play.api.mvc._
import models.Person
import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.LocalDate

object Persons extends Controller {

  implicit val productWrites: Writes[Person] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "name").write[String] and
    (JsPath \ "age").write[Int] and
    (JsPath \ "birthday").write[LocalDate])(unlift(Person.unapply))
    
  implicit val personFormat = Json.format[Person]

  def list = Action { implicit request =>
    val persons = Person.findAll
    
    Ok(views.html.persons.list(persons))
  }

  
  def persondetails(id: Long) = Action {implicit request => 
    Person.findById(id).map { p =>
      Ok(Json.toJson(p))
    }.getOrElse(NotFound)
  }
  
  
  
  def getpersonlist = Action { implicit request => 
    println("hee")
    val persons = Person.findAll
    val data = Json.obj("data" -> persons)
    Ok(Json.toJson(data))
  }
  
}