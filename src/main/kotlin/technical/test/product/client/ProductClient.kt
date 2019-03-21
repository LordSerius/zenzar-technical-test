package technical.test.product.client

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import technical.test.product.converter.ProductConverter

@Service
class ProductClient {

    companion object {
        private val LOG = LoggerFactory.getLogger(ProductClient::class.java)
    }

    @Value("\${jl.server.url}")
    private lateinit var serverUrl : String

    @Value("\${jl.product.key}")
    private lateinit var apiKey : String

    @Autowired
    private lateinit var productConverter : ProductConverter

    fun getProductCategory(categoryId : String) : String {

        LOG.info("[categoryId:$categoryId] Querying product categories")

//        var output = ""
//
//        ClassPathResource("example.json").file.forEachLine { output += it }
//
//        return output

        return RestTemplate().getForEntity(
                UriComponentsBuilder.fromHttpUrl("https://$serverUrl/v1/categories/$categoryId/products")
                        .queryParam("key", apiKey)
                        .toUriString(),
                String::class.java
                )
                .takeIf { it.statusCode == HttpStatus.OK }
                ?.let { it.body ?: "" }
            ?: throw RuntimeException("Error during calling categories endpoint")

    }
}