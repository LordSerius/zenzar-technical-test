package technical.test.product.controller

import com.google.common.base.Preconditions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import technical.test.product.client.ProductClient
import technical.test.product.converter.LabelType
import technical.test.product.converter.ProductConverter
import technical.test.product.converter.discount.DiscountedProductConverter
import technical.test.product.dtos.DiscountedProductDto
import technical.test.product.service.PriceReductionServiceService

@RestController
@RequestMapping("/v1/reduction")
class PriceReductionController {

    companion object {
        private val LOG = LoggerFactory.getLogger(PriceReductionController::class.java)
    }

    @Autowired
    private lateinit var productClient : ProductClient

    @Autowired
    private lateinit var priceReductionService : PriceReductionServiceService

    @Autowired
    private lateinit var productConverter : ProductConverter

    @Autowired
    private lateinit var discountedProductConverter: DiscountedProductConverter

    @GetMapping("/{categoryId}")
    fun getProducts(
            @PathVariable categoryId : String,
            @RequestParam labelType : LabelType = LabelType.SHOW_WAS_NOW
    ) : ResponseEntity<List<DiscountedProductDto>> {

        Preconditions.checkArgument(categoryId.isNotBlank(), "Category ID should not be blank")

        LOG.info("[categoryId:$categoryId] Get price reduced items")

        return productClient.getProductCategory(categoryId)
                .let { priceReductionService.getPriceReductionProducts(it) }
                .let { productConverter.fromJsonNodeToList(it) }
                .let { discountedProductConverter.convertList(labelType, it) }
                .let { ResponseEntity.ok(it) }
    }
}