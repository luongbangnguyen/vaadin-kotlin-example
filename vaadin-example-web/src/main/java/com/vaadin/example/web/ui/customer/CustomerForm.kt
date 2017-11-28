package com.vaadin.example.web.ui.customer

import com.vaadin.data.Binder
import com.vaadin.data.validator.EmailValidator
import com.vaadin.data.validator.StringLengthValidator
import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.enums.CustomerStatus
import com.vaadin.example.domain.exeption.BusinessException
import com.vaadin.example.domain.service.CustomerService
import com.vaadin.example.web.ui.util.ErrorMessage
import com.vaadin.server.Sizeable
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired


@SpringComponent
@UIScope
class CustomerForm @Autowired constructor(private val customerService: CustomerService,
                                          val errorMessage: ErrorMessage)  : VerticalLayout() {

    private final val form = FormLayout().apply { setSizeUndefined() }
    private final val firstName = TextField("First Name").apply {setWidth(400f, Sizeable.Unit.PIXELS)}
    private final val lastName = TextField("Last Name").apply { setWidth(400f, Sizeable.Unit.PIXELS) }
    private final val email = TextField("Email").apply { setWidth(400f, Sizeable.Unit.PIXELS) }
    private final val status = NativeSelect<CustomerStatus>("Status")
    private final val birthDate = DateField("Birthday").apply { setWidth(400f, Sizeable.Unit.PIXELS) }
    private final val description = TextArea("Description").apply { setWidth(400f, Sizeable.Unit.PIXELS) }
    private final val save = Button("Save")
    private final val delete = Button("Delete")
    private final val binder = Binder<Customer>(Customer::class.java)

    private var customer: Customer? = null
    private lateinit var afterSaveCustomerEvent: () -> Unit

    init {
        this.isSpacing = false
        val buttons = HorizontalLayout(save, delete)

        birthDate.dateFormat = "dd-MM-yyyy"
        status.setItems(CustomerStatus.values().toList())

        save.styleName = ValoTheme.BUTTON_PRIMARY
        save.addClickListener({ this.save() })

        delete.addClickListener { this.delete() }

        form.addComponents(firstName, lastName, email, status, birthDate, description, buttons)
        form.styleName = "customer-form"

        this.addComponents(errorMessage,form)
        this.setValidateBinder()
    }

    private fun delete() {
        customerService.delete(customer!!)
        afterSaveCustomerEvent()
    }

    private fun save() {
        try {
            if (binder.writeBeanIfValid(customer)) {
                customerService.save(customer!!)
                afterSaveCustomerEvent()
            }
        } catch (e: BusinessException) {
            errorMessage.setMessage(e.message!!)
            errorMessage.isVisible = true
        }

    }

    private infix fun Customer.copy(source: Customer) {
        BeanUtils.copyProperties(source, this)
    }

    fun setCustomer(value: Customer) {
        val clone = Customer()
        clone copy value

        this.customer = clone
        binder.bean = clone

        firstName.selectAll()
        delete.isVisible = clone.id != null
        errorMessage.isVisible = false
    }

    fun setAfterSaveCustomerEvent(event: () -> Unit) {
        this.afterSaveCustomerEvent = event
    }

    fun setValidateBinder() : Unit = with(binder) {
        forField(firstName)
                .asRequired("Fist name is required")
                .withValidator(StringLengthValidator("Fist name must be between 2 and 20 character", 2, 20))
                .bind(Customer::getFirstName, Customer::setFirstName)

        forField(lastName)
                .asRequired("Last name is required")
                .withValidator(StringLengthValidator("Last name must be between 2 and 50 character", 2, 20))
                .bind(Customer::getLastName, Customer::setLastName)

        forField(email).asRequired("Email is required")
                .withValidator(EmailValidator("Email invalid"))
                .bind(Customer::getEmail, Customer::setEmail)

        forField(status).asRequired("Status is required")
                .bind(Customer::getStatus, Customer::setStatus)

        forField(birthDate).asRequired("Birthday is required")
                .bind(Customer::getBirthDate, Customer::setBirthDate)

        forField(description).bind(Customer::getDescription, Customer::setDescription)
    }
}
