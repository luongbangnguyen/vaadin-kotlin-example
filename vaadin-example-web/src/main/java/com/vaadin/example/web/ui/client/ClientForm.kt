package com.vaadin.example.web.ui.client

import com.vaadin.data.Binder
import com.vaadin.data.converter.StringToIntegerConverter
import com.vaadin.data.validator.StringLengthValidator
import com.vaadin.example.domain.dto.ClientDto
import com.vaadin.example.domain.service.ClientService
import com.vaadin.server.Sizeable
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import org.apache.commons.lang.StringUtils
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.vaadin.ui.NumberField

@SpringComponent
@UIScope
class ClientForm @Autowired constructor(private val clientService: ClientService) : VerticalLayout(){

    private val clientIdText = TextField("Client Id")
    private val clientSecretText = PasswordField("Client Secret")
    private val clientNameText = TextField("Client Name")
    private val accessTokenValidityText = NumberField("Access Token Time")
    private val refreshTokenValidityText = NumberField("Refresh Token Time")
    private final val form = FormLayout()

    val save = Button("Save", Button.ClickListener { saveClient() }).apply { styleName = ValoTheme.BUTTON_FRIENDLY }
    val delete = Button("Delete", Button.ClickListener { deleteClient() })

    private val binder = Binder<ClientDto>(ClientDto::class.java)
    private var clientDto = ClientDto()
    private lateinit var afterExecuteEvent: () -> Unit

    init {
        this.setupTextFields()
        this.setupNumberFields()
        this.setupForm()
        this.addComponents(form)
        this.setupBinderClient()
    }

    private fun setupTextFields() {
        this.clientSecretText.setWidth(400f, Sizeable.Unit.PIXELS)
        this.clientIdText.setWidth(400f, Sizeable.Unit.PIXELS)
        this.clientNameText.setWidth(400f, Sizeable.Unit.PIXELS)
        this.accessTokenValidityText.setWidth(400f, Sizeable.Unit.PIXELS)
        this.refreshTokenValidityText.setWidth(400f, Sizeable.Unit.PIXELS)
    }

    private fun setupNumberFields() {
        this.accessTokenValidityText.apply {
            isNegativeAllowed = true
            isDecimalAllowed = true
        }

        this.refreshTokenValidityText.apply {
            isDecimalAllowed = true
            isDecimalAllowed = true
        }
    }

    private fun setupForm(){
        this.form.setSizeUndefined()
        val buttons = HorizontalLayout(save, delete)
        form.addComponents(clientIdText,
                clientSecretText,
                clientNameText,
                accessTokenValidityText,
                refreshTokenValidityText,
                buttons)
    }

    private fun saveClient()
    {
        if (binder.writeBeanIfValid(clientDto)) {
            clientService.createOrUpdate(clientDto)
            this.afterExecuteEvent()
        }
    }

    private fun deleteClient() {
        this.clientService.delete(clientDto.clientId!!)
        this.afterExecuteEvent()
    }

    private fun setupBinderClient() = binder.apply {
        forField(clientIdText)
                .asRequired("Client Id is required")
                .withValidator(StringLengthValidator("Client Id must be between 6 and 20 characters", 6, 20))
                .bind(ClientDto::clientId.name)

        forField(clientSecretText)
                .asRequired("Client secret is required")
                .withValidator(StringLengthValidator("Client Secret must be between 6 and 20 characters", 6, null))
                .bind(ClientDto::clientSecret.name)

        forField(clientNameText)
                .asRequired("Client Name is required")
                .bind(ClientDto::clientName.name)

        forField(accessTokenValidityText)
                .asRequired("Access Token is required")
                .withConverter(StringToIntegerConverter("Conversion error"))
                .bind(ClientDto::accessTokenValidity.name)

        forField(refreshTokenValidityText)
                .asRequired("Refresh Token is required")
                .withConverter(StringToIntegerConverter("Conversion error"))
                .bind(ClientDto::refreshTokenValidity.name)
    }

    fun setClient(clientDto: ClientDto) {
        val clone = ClientDto()
        BeanUtils.copyProperties(clientDto, clone)

        this.clientDto = clone
        binder.bean = clone
        if (clone.id != null) {
            delete.isVisible
            this.clientIdText.isReadOnly = true
            this.clientNameText.isReadOnly = true
            this.clientSecretText.isReadOnly = true
        }
    }

    fun setAfterExecuteEvent(event: () -> Unit) {
        this.afterExecuteEvent = event
    }
}