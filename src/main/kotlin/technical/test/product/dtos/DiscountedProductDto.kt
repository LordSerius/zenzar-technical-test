package technical.test.product.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class DiscountedProductDto @JsonCreator constructor (
        @JsonProperty("productId") val productId: String,
        @JsonProperty("title") val title: String,
        @JsonProperty("colorSwatches") val colorSwatches: List<ColorSwatchDto>,
        @JsonProperty("nowPrice") val nowPrice: String,
        @JsonProperty("priceLabel") val priceLabel: String
)

data class ColorSwatchDto @JsonCreator constructor(
        @JsonProperty("color") val color: String,
        @JsonProperty("rgbColor") val rgbColor: String,
        @JsonProperty("skuid") val skuid: String
)