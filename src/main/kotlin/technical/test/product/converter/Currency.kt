package technical.test.product.converter

enum class Currency(
        val abbreviation : String,
        val symbol : Char
) {
    EUR("EUR", '€'),
    GBP("GBP", '£'),
    USD("USD", '$')
}