package models

import play.api.Logger
import play.api.libs.json.JsArray
import play.api.libs.ws
import play.api.libs.ws.{WS, WSRequest}
import sangria.schema.{Deferred, DeferredResolver}
import play.api.Play.current
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class DbPedia {

  private[this] def wrappedDbPediaSparql(sparql: String) = s"http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org" +
    s"&query=$sparql&format=application%2Fsparql-results%2Bjson&CXML_redir_for_subjs=121&CXML_redir_for_hrefs=&timeout=30000&debug=on"

  def getAllProgrammingLanguages: Future[Seq[ProgrammingLanguage]] = {

    val sparql = """SELECT DISTINCT ?lang ?label ?designer
                   |WHERE {
                   |    ?lang a dbo:ProgrammingLanguage;
                   |    dbp:name ?label .
                   |    ?lang dbo:designer _:designer .
                   |    _:designer foaf:name ?designer .
                   |}""".stripMargin

    val request: WSRequest = WS.url(wrappedDbPediaSparql(sparql))
    request.get().map(result => {
      val bindings = (result.json \ "results" \ "bindings").as[JsArray].value
      val mappedBindings = bindings.map(binding => {
        val name = (binding \ "label" \ "value").as[String]
        val uri = (binding \ "lang" \ "value").as[String]
        val designer = (binding \ "designer" \ "value").as[String]
        ProgrammingLanguage(uri, name, List(designer))
      })

      mappedBindings.groupBy(_.uri).map({
        case (_, lang :: Nil) => lang
        case (_, langs) => ProgrammingLanguage(langs.head.uri, langs.head.name, langs.flatMap(_.designers).toList)
        case x:Any => Logger.error(x.toString); ProgrammingLanguage("", "", List())
      }).toSeq
    })

  }
}

class DbPediaResolver extends DeferredResolver[Any] {
  override def resolve(deferred: Vector[Deferred[Any]], ctx: Any) = deferred map {
    _ => ??? // TODO
  }
}
