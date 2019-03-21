package technical.test.product.converter

import com.google.common.base.CaseFormat
import org.springframework.core.convert.converter.Converter

class LabelTypeConverter : Converter<String, LabelType> {

    override fun convert(source: String): LabelType? {
        return source.let {
            try {
                LabelType.valueOf(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, source).toUpperCase())
            } catch (e : Exception) {
                LabelType.SHOW_WAS_NOW
            }
        }
    }

}