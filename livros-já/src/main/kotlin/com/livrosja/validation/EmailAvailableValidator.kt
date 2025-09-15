package com.livrosja.validation

import com.livrosja.service.CustomersService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext


class EmailAvailableValidator(var customerService: CustomersService): ConstraintValidator<EmailAvailable ,String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(value.isNullOrEmpty()){
            return false
        }
        return customerService.emailAvailable(value)
    }
}
