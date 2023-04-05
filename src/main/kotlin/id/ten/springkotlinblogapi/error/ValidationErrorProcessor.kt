package id.ten.springkotlinblogapi.error

import org.springframework.validation.BindingResult

interface ValidationErrorProcessor {
    fun process(result:BindingResult): String
}