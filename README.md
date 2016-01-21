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