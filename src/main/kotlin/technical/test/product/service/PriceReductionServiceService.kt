package technical.test.product.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class PriceReductionServiceService {

    private val objectMapper = ObjectMapper()

    fun getPriceReductionProducts(json : String) : List<JsonNode> {

        return filterDiscountedProducts(json)
    }

    private fun filterDiscountedProducts(json : String) =
            getProductList(json)
                    .filter {
                        it.findValue("price")
                                .findValue("was")
                                .asText()
                                .isNotBlank()
                    }

    private fun getProductList(json : String) : List<JsonNode> {
        val products = ArrayList<JsonNode>()

        objectMapper.readTree(json)
                .findValue("products")
                .elements()
                .forEach { products.add(it) }

        return products
    }
}