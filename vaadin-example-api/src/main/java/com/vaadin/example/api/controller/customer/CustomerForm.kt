package com.vaadin.example.api.controller.customer

import com.vaadin.example.api.validategroup.Update
import com.vaadin.example.domain.enums.CustomerStatus
import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CustomerForm {

    @NotNull(groups = arrayOf(Update::class))
    var id: Long? = null

    @NotBlank
    var firstName: String? = null

    @NotBlank
    var lastName: String? = null

    @NotNull
    var birthDate: LocalDate? = null

    var description: String? = null

    @NotNull
    var status: CustomerStatus? = null

    @NotBlank
    @Email
    var email: String? = null
}