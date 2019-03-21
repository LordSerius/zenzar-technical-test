# zenzar-technical-test

How to run?
`./gradlew bootRun`

How to test?

`http://localhost:8080/v1/reduction/600001506?labelType=showWasNow`

`http://localhost:8080/v1/reduction/600001506?labelType=showWasThenNow`

`http://localhost:8080/v1/reduction/600001506?labelType=showercDiscount`

If the jl server is unavailable, please remove the comment lines in `ProductClient` and comment out the `RestTemplate` call. It will use the example file in the resource folder.
