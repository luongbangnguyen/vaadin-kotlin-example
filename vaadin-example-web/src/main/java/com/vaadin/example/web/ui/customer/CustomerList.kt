package com.vaadin.example.web.ui.customer

import com.vaadin.data.provider.GridSortOrder
import com.vaadin.example.domain.criteria.CustomerCriteria
import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.entity.Customer_
import com.vaadin.example.domain.enums.CustomerStatus
import com.vaadin.example.domain.service.CustomerService
import com.vaadin.example.web.constants.WebConstants
import com.vaadin.example.web.ui.util.Paging
import com.vaadin.icons.VaadinIcons
import com.vaadin.navigator.View
import com.vaadin.shared.data.sort.SortDirection
import com.vaadin.shared.ui.ValueChangeMode
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.SpringView
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.*
import com.vaadin.ui.renderers.LocalDateRenderer
import com.vaadin.ui.themes.ValoTheme
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@SpringComponent
@SpringView(name = "")
class CustomerList @Autowired constructor(private val service: CustomerService,
                                          private val paging: Paging,
                                          private val customerForm: CustomerForm) : VerticalLayout(), View {

    private val filterText = TextField()
    private val dateBeginBirthday = DateField()
    private val dateEndBirthday = DateField()
    private val customerStatus = ComboBox<CustomerStatus>()
    private final val grid = Grid<Customer>()
    private final val window = Window()

    companion object {
        private val FIRST_NAME_LABEL = "First Name"
        private val LAST_NAME_LABEL = "Last Name"
        private val EMAIL_LABEL = "Email"
        private val BIRTHDAY_LABEL = "Birthday"
        private val STATUS_LABEL = "Status"
        private val DESCRIPTION_LABEL = "Description"
        private val ACTION_LABEl = "Action"
    }

    init {
        this.setMargin(false)
        this.addFormCustomer()
        this.createGridCustomer()
        this.addComponents(createToolbar(), grid, paging)
        this.updateList()
    }

    private fun createGridCustomer() {
        grid.setSizeFull()
        grid.setHeight("418")
        grid.addColumn(Customer::getFirstName).caption = FIRST_NAME_LABEL
        grid.addColumn(Customer::getLastName).caption = LAST_NAME_LABEL
        grid.addColumn(Customer::getEmail).caption = EMAIL_LABEL
        grid.addColumn(Customer::getBirthDate, LocalDateRenderer("dd-MM-yyyy")).caption = BIRTHDAY_LABEL
        grid.addColumn(Customer::getStatus).caption = STATUS_LABEL
        grid.addColumn(Customer::getDescription).apply { isSortable = false }.caption = DESCRIPTION_LABEL

        grid.addComponentColumn { cus ->
            HorizontalLayout(Button(VaadinIcons.EDIT).apply {
                addStyleNames(ValoTheme.BUTTON_LINK)
                addClickListener { updateCustomer(cus) }
            })
        }.apply { isSortable = false }.caption = ACTION_LABEl

        grid.addSortListener {
            setItemsForGridByPageIndex(paging.getPagingInfo().pageIndex)
        }
    }

    private fun addFormCustomer() {
        this.window.isModal = true
        this.window.content = this.customerForm
        customerForm.setAfterSaveCustomerEvent {
            updateList()
            UI.getCurrent().removeWindow(this.window)
        }
    }

    private fun createCustomer() {
        customerForm.setCustomer(Customer())
        window.caption = "Add customer"
        UI.getCurrent().addWindow(this.window)
    }

    private fun updateCustomer(customer: Customer) {
        window.caption = "Update customer"
        customerForm.setCustomer(customer)
        UI.getCurrent().addWindow(this.window)
    }

    private fun createToolbar(): HorizontalLayout {
        with(filterText){
            placeholder = "Search"
            valueChangeMode = ValueChangeMode.LAZY
            addValueChangeListener { updateList() }
        }

        val clearFilterTextBtn = Button(VaadinIcons.CLOSE)
        with(clearFilterTextBtn){
            description = "Clear the current filter"
            addClickListener { filterText.clear() }
        }

        val filtering = CssLayout()
        with(filtering) {
            addComponents(filterText, clearFilterTextBtn)
            styleName = ValoTheme.LAYOUT_COMPONENT_GROUP
        }

        val addCustomerBtn = Button("Add new customer").apply { styleName = ValoTheme.BUTTON_FRIENDLY }
        addCustomerBtn.addClickListener {
           createCustomer()
        }

        dateBeginBirthday.addValueChangeListener{ updateList() }
        dateEndBirthday.addValueChangeListener { updateList() }

        with(customerStatus) {
            emptySelectionCaption = "All"
            setItems((CustomerStatus.values()).toList())
            addSelectionListener { updateList() }
        }

        return HorizontalLayout(filtering, dateBeginBirthday, dateEndBirthday,customerStatus, addCustomerBtn)
    }

    private fun updateList() {
        val customerCriteria = CustomerCriteria(filterText.value, dateBeginBirthday.value, dateEndBirthday.value, customerStatus.value)
        val pageable = getPageable(paging.getPagingInfo().pageIndex)
        var page = service.findAll(customerCriteria, pageable)
        if(page.content.isEmpty() && page.totalElements > 0) {
            page = service.findAll(customerCriteria, getPageable(0))
        }
        grid.setItems(page.content)
        updatePaging(page)
    }

    private fun updatePaging(page: Page<Customer>) {
        paging.setTotalElement(page.totalElements)
        paging.setPageIndex(page.number)
        paging.addClickListener {
            setItemsForGridByPageIndex(it)
        }
        paging.build()
    }

    private fun setItemsForGridByPageIndex(pageIndex: Int) {
        val customerCriteria = CustomerCriteria(filterText.value, dateBeginBirthday.value, dateEndBirthday.value, customerStatus.value)
        val p = service.findAll(customerCriteria, getPageable(pageIndex))
        grid.setItems(p.content)
    }

    private fun getPageable(pageIndex: Int): Pageable {
        val orders = grid.getOrderList()
        var pageable = PageRequest.of(pageIndex, WebConstants.MAX_PAGE_SIZE, Sort.Direction.DESC, "id")
        if (orders.isNotEmpty()) {
            pageable = PageRequest.of(pageIndex, WebConstants.MAX_PAGE_SIZE, Sort.by(orders))
        }
        return pageable
    }

    fun Grid<Customer>.getOrderList() : MutableList<Sort.Order> {
        val orders: MutableList<Sort.Order> = mutableListOf()
        this.sortOrder.mapTo(orders) { it.getSortOrder() }
        return orders
    }

    private fun GridSortOrder<Customer>.getSortOrder() : Sort.Order {
        val fieldName = when (this.sorted.caption) {
            FIRST_NAME_LABEL -> Customer_.firstName.name
            LAST_NAME_LABEL ->  Customer_.lastName.name
            EMAIL_LABEL -> Customer_.email.name
            BIRTHDAY_LABEL -> Customer_.birthDate.name
            STATUS_LABEL -> Customer_.status.name
            else -> throw IllegalArgumentException("caption ${this.sorted.caption} is not existed on grid")
        }
        val direction = if (this.direction == SortDirection.DESCENDING) Sort.Direction.DESC  else Sort.Direction.ASC
        return Sort.Order(direction, fieldName)
    }
}

