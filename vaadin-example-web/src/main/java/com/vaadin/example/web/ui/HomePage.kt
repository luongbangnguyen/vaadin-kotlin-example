package com.vaadin.example.web.ui

import com.vaadin.annotations.Theme
import com.vaadin.annotations.Title
import com.vaadin.example.web.ui.client.ClientList
import com.vaadin.navigator.Navigator
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.spring.navigator.SpringViewProvider
import com.vaadin.ui.Button
import com.vaadin.ui.CssLayout
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import org.springframework.beans.factory.annotation.Autowired


@SpringUI
@Title("Vaadin And Kotlin Example")
@Theme("customtheme" )
class HomePage @Autowired constructor(private val viewProvider: SpringViewProvider) : UI() {

    override fun init(request: VaadinRequest?) {
        val root = VerticalLayout()
        root.setSizeFull()
        root.setMargin(true)
        root.isSpacing = true
        content = root

        val navigationBar = CssLayout()
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP)
        navigationBar.addComponent(Button("Customer", Button.ClickListener { ui.navigator.navigateTo("") }))
        navigationBar.addComponent(Button("Client", Button.ClickListener { ui.navigator.navigateTo(ClientList.NAME) }))
        root.addComponent(navigationBar)

        val viewContainer = VerticalLayout().apply {
            setMargin(false)
        }
        viewContainer.setSizeFull()
        root.addComponent(viewContainer)
        root.setExpandRatio(viewContainer, 1.0f)

        val navigator = Navigator(this, viewContainer)
        navigator.addProvider(viewProvider)
    }
}