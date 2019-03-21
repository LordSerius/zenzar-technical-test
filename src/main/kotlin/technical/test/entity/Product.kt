package technical.test.entity

import technical.test.product.converter.Currency

data class Product(
        val productId: String,
        val title: String,
        val colorSwatches: List<ColorSwatch>,
        val price: Price
)

data class ColorSwatch(
        val color: String,
        val rgbColor: String,
        val skuid: String
)

data class Price(
        val was: Double?,
        val then1: Double?,
        val then2: Double?,
        val now: Double?,
        val currency: Currency?
)