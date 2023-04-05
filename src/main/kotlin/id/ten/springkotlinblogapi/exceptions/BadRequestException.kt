package id.ten.springkotlinblogapi.exceptions

import java.lang.RuntimeException

class BadRequestException(override val message: String): RuntimeException() {
}