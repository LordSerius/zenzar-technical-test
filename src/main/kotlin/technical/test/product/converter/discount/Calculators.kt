package technical.test.product.converter.discount

import org.springframework.stereotype.Component
import technical.test.entity.Price
import technical.test.product.converter.Currency
import java.text.DecimalFormat

@Component
class Calculator {

    companion object {
        private val DECIMAL_FORMAT = DecimalFormat("#.##")
    }

    fun calculate(price : Price) : String {
        if (price.currency == null || price.now == null) return ""
        val nowPriceLabel = calculatePriceLabel(price.currency, price.now)

        return calculate(price.currency, nowPriceLabel, price)
    }

    protected fun calculatePriceLabel(currency: Currency, price : Double?) =
        if (price != null) {
            currency.symbol + "" + if (price >= 10.0) price.toInt() else DECIMAL_FORMAT.format(price)
        } else {
            ""
        }

    protected fun calculate(currency: Currency, nowPriceLabel: String, price: Price) : String {
        return nowPriceLabel
    }
}

@Component
class ShowWasNowCalculator : Calculator() {
    override fun calculate(currency: Currency, nowPriceLabel: String, price: Price) : String {
        return if (price.was != null) {
            val wasPriceLabel = calculatePriceLabel(currency, price.was)
            "Was $wasPriceLabel, now $nowPriceLabel"
        } else {
            ""
        }
    }
}

@Component
class ShowWasThenNowCalculator : Calculator() {
    override fun calculate(currency: Currency, nowPriceLabel: String, price: Price): String {

        return if(price.was != null) {
            val wasPriceLabel = calculatePriceLabel(currency, price.was)
            val thenLabel = calculateThenPriceLabel(currency, price)
            "Was $wasPriceLabel${thenLabel}now $nowPriceLabel"
        } else {
            ""
        }
    }

    private fun calculateThenPriceLabel(currency : Currency, price : Price) =
            when {
                price.then2 != null -> ", then " + calculatePriceLabel(currency, price.then2) + ", "
                price.then1 != null -> ", then " + calculatePriceLabel(currency, price.then1) + ", "
                else -> ", "
            }

}

@Component
class ShowPercDiscountCalculator : Calculator() {
    override fun calculate(currency: Currency, nowPriceLabel: String, price: Price): String {
        val percent = calculatePercent(price)
        return if (percent != null) {
            "$percent% off - now $nowPriceLabel"
        } else {
            ""
        }
    }

    private fun calculatePercent(price: Price) : Int? {
        return when {
            price.now == null -> null
            price.then2 != null -> (price.now / price.then2 * 100).toInt()
            price.then1 != null -> (price.now / price.then1 * 100).toInt()
            price.was != null -> (price.now / price.was * 100).toInt()
            else -> null
        }
    }

}