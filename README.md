## SemanticGraphQL

Proof of concept to map from DbPedia's SPARQL endpoint to a GraphQL API using the Sangria Framework.


Example Query:

```graphql
query {
  programmingLanguages {
    uri,
    name,
    designers
  }
}
```

### Running the example

1. Clone this repo
1. From the root directory run `sbt run`
1. Open `http://localhost:9000/graphiql` and try the query from above.

![Example Graphiql](https://raw.githubusercontent.com/danistrebel/SemanticGraphQL/master/public/images/graphiql.png)
