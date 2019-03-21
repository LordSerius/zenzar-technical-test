package technical.test.product.converter

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import technical.test.entity.ColorSwatch
import technical.test.entity.Price
import technical.test.entity.Product
import technical.test.product.service.RgbService

@Service
class ProductConverter {

    @Autowired
    private lateinit var rgbService : RgbService

    fun fromJsonNodeToList(jsonNodes : List<JsonNode>) =
            jsonNodes.map {
            Product(
                    productId = it.findValue("productId").asText(),
                    title = it.findValue("title").asText(),
                    colorSwatches = toColorSwatches(it),
                    price = toPriceNode(it.findValue("price"))
            )
        }

    private fun toColorSwatches(node : JsonNode) : List<ColorSwatch> {

        val colorSwatches = ArrayList<ColorSwatch>()

        node.findValue("colorSwatches")
                .elements()
                .forEach { colorSwatches.add(
                        ColorSwatch(
                                color = it.findValue("color").asText(),
                                rgbColor = rgbService.lookupColor(it.findValue("basicColor").asText()),
                                skuid = it.findValue("skuId").asText()
                        )
                ) }

        return colorSwatches
    }

    private fun toPriceNode(node : JsonNode) =
            Price(
                    was = getPrice(node,"was"),
                    then1 = getPrice(node,"then1"),
                    then2 =  getPrice(node,"then2"),
                    now =  getPrice(node,"now"),
                    currency = try {
                        Currency.valueOf(node.findValue("currency").asText())
                    } catch (e : Exception) {
                        null
                    }
            )

    private fun getPrice(node : JsonNode, fieldName : String) : Double? {
        return node.findValue(fieldName)
                .let { if (it.isValueNode) it.asText() else it.findValue("from").asText() }
                .takeIf { it.isNotBlank() }
                ?.let { it.toDouble() }
    }
}