package technical.test.product.converter.discount

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import technical.test.entity.Price
import technical.test.entity.Product
import technical.test.product.converter.LabelType
import technical.test.product.dtos.ColorSwatchDto
import technical.test.product.dtos.DiscountedProductDto

@Service
class DiscountedProductConverter {

    @Autowired
    private lateinit var calculator : Calculator

    @Autowired
    private lateinit var showWasNowCalculator : ShowWasNowCalculator

    @Autowired
    private lateinit var showWasThenNowCalculator : ShowWasThenNowCalculator

    @Autowired
    private lateinit var showPercDiscountCalculator : ShowPercDiscountCalculator

    fun convertList(labelType : LabelType, products : List<Product>) =
            products.sortedBy {
                val price = it.price
                if (price.was == null || price.now == null) Double.MIN_VALUE else price.now - price.was
            }
                    .map {
                        DiscountedProductDto(
                                productId = it.productId,
                                title = it.title,
                                colorSwatches = it.colorSwatches
                                        .map {
                                            colorSwatch -> ColorSwatchDto(
                                                color = colorSwatch.color,
                                                rgbColor = colorSwatch.rgbColor,
                                                skuid = colorSwatch.skuid
                                        )
                                        },
                                nowPrice = calculator.calculate(it.price),
                                priceLabel = if (it.price.now != null) createPriceLabel(labelType, it.price) else ""
                        )
                    }

    private fun createPriceLabel(
            labelType: LabelType,
            price : Price): String  =
        when (labelType) {
            LabelType.SHOW_WAS_NOW -> showWasNowCalculator.calculate(price)
            LabelType.SHOW_WAS_THEN_NOW -> showWasThenNowCalculator.calculate(price)
            LabelType.SHOW_PERC_DISCOUNT -> showPercDiscountCalculator.calculate(price)
        }

}