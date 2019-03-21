package technical.test.product.converter

enum class LabelType(
        val parameterValue : String
) {

    SHOW_WAS_NOW("showWasNow"),
    SHOW_WAS_THEN_NOW("showWasThenNow"),
    SHOW_PERC_DISCOUNT("showPercDiscount")
}