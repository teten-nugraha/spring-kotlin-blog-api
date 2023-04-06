package id.ten.springkotlinblogapi.exceptions

import java.lang.RuntimeException

class ResourceNotFoundException(override val message: String): RuntimeException() {}