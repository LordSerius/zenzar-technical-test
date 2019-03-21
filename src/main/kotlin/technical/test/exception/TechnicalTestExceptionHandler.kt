package technical.test.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class TechnicalTestExceptionHandler {

    companion object {
        private val LOG = LoggerFactory.getLogger(TechnicalTestExceptionHandler::class.java)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleIllegalArgumentException(exception: IllegalArgumentException) : String? {
        LOG.error("[IllegalArgumentException] $exception")
        return exception.message
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleException(exception: Exception) : String? {
        LOG.error("[Exception] $exception")
        return exception.message
    }
}