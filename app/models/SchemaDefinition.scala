package models

import sangria.schema._

import scala.concurrent.Future

/**
 * Defines a GraphQL schema for the current project
 */
object SchemaDefinition {

  val ProgrammingLanguageType = ObjectType(
    "ProgrammingLanguage",
    "A Programming Language as defined in DB Pedia",
    fields[Unit, ProgrammingLanguage](
      Field("uri", StringType, None, resolve = _.value.uri),
      Field("name", StringType, None, resolve = _.value.name),
      Field("designers", ListType(StringType), None, resolve = _.value.designers)
    )
  )

  val URI = Argument("uri", StringType, description = "entity uri")

  val Query = ObjectType(
    "Query", fields[DbPedia, Unit](
      Field("programmingLanguages", ListType(ProgrammingLanguageType),
        arguments = Nil,
        resolve = (ctx) => ctx.ctx.getAllProgrammingLanguages)
    ))

  val DemoSchema = Schema(Query)
}
