package com.vaadin.example.web.ui.client

import com.vaadin.example.domain.dto.ClientDto
import com.vaadin.example.domain.service.ClientService
import com.vaadin.icons.VaadinIcons
import com.vaadin.navigator.View
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

@SpringComponent
@SpringView(name = ClientList.NAME)
class ClientList @Autowired constructor(private val clientService: ClientService,
                                        private  val clientForm: ClientForm) : VerticalLayout(), View{

    companion object {
        const val NAME = "client"
    }

    private final val window = Window()
    private final val gridClient = Grid<ClientDto>()
    private final val addClientButton = Button()

    init {
        this.setMargin(false)
        this.setupGridClient()
        this.updateDataGridClient()
        this.setupAddClientButton()
        this.setupWindowClient()
        this.addComponents(addClientButton, gridClient)
    }

    private fun setupWindowClient() {
        this.window.isModal = true
        this.window.content = this.clientForm
        this.clientForm.setAfterExecuteEvent {
            this.updateDataGridClient()
            UI.getCurrent().removeWindow(window)
        }
    }

    private fun setupGridClient() {
        this.gridClient.setSizeFull()
        this.gridClient.addColumn(ClientDto::clientId).caption = "Client Name"
        this.gridClient.addColumn(ClientDto::accessTokenValidity).caption = "Access Token Validity"
        this.gridClient.addColumn(ClientDto::refreshTokenValidity).caption = "Refresh Token Validity"
        this.gridClient.addColumn(ClientDto::grants).caption = "Client Grants"
        this.gridClient.addColumn(ClientDto::scopes).caption = "Client Scopes"
        this.gridClient.addColumn(ClientDto::roles).caption = "Client Roles"
        this.gridClient.addColumn(ClientDto::redirectUris).caption = "Client Redirect Uri"
        this.gridClient.addComponentColumn { client ->
                HorizontalLayout(Button(VaadinIcons.EDIT).apply {
                addStyleNames(ValoTheme.BUTTON_LINK)
                addClickListener { updateClient(client) }
            }).apply { caption = "Action" }
        }
    }

    private fun updateClient(client: ClientDto) {
        this.window.caption = "Update Client"
        this.clientForm.setClient(client)
        UI.getCurrent().addWindow(this.window)
    }

    private fun updateDataGridClient() {
        val pageable = PageRequest.of(0, 10000)
        val pageClient = this.clientService.findAll("", pageable)
        this.gridClient.setItems(pageClient.content)
    }

    private fun setupAddClientButton() {
        this.addClientButton.apply {
            caption = "Add client"
            addClickListener { createClient() }
            styleName = ValoTheme.BUTTON_FRIENDLY
        }
    }

    private fun createClient() {
        this.window.caption = "Add Client"
        this.clientForm.setClient(ClientDto().apply {
            accessTokenValidity = 3600
            refreshTokenValidity = 3600
        })
        UI.getCurrent().addWindow(this.window)
    }
}