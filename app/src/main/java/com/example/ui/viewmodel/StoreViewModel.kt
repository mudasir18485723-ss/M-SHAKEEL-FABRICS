package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.CartItem
import com.example.data.model.OrderEntity
import com.example.data.model.Product
import com.example.data.repository.FabricStoreRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    object Home : Screen()
    object Shop : Screen()
    data class ProductDetail(val productId: Long) : Screen()
    object Cart : Screen()
    object Checkout : Screen()
    data class OrderConfirmation(val orderId: String) : Screen()
    object OrderTracking : Screen()
    object About : Screen()
    object Contact : Screen()
    object Admin : Screen()
    object WebStore : Screen()
}

enum class SortOption(val title: String) {
    FEATURED("Featured"),
    PRICE_LOW_HIGH("Price: Low to High"),
    PRICE_HIGH_LOW("Price: High to Low"),
    RATING("Highest Rated")
}

data class CheckoutFormState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val city: String = "Lahore",
    val notes: String = "",
    val nameError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val addressError: String? = null,
    val isSubmitting: Boolean = false,
    val submitError: String? = null
)

class StoreViewModel(
    private val repository: FabricStoreRepository
) : ViewModel() {

    // Screen navigation stack
    private val _screenStack = MutableStateFlow<List<Screen>>(listOf(Screen.Home))
    val currentScreen: StateFlow<Screen> = _screenStack.combine(MutableStateFlow(Unit)) { stack, _ ->
        stack.lastOrNull() ?: Screen.Home
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Screen.Home)

    fun navigateTo(screen: Screen) {
        val current = _screenStack.value
        // Avoid duplicate push if top matches
        if (current.lastOrNull() != screen) {
            _screenStack.value = current + screen
        }
    }

    fun navigateBack(): Boolean {
        val current = _screenStack.value
        return if (current.size > 1) {
            _screenStack.value = current.dropLast(1)
            true
        } else {
            false
        }
    }

    // Repository Flows
    val allProducts: StateFlow<List<Product>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val featuredProducts: StateFlow<List<Product>> = repository.featuredProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartItems: StateFlow<List<CartItem>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allOrders: StateFlow<List<OrderEntity>> = repository.allOrders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Shop Filters
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedSort = MutableStateFlow(SortOption.FEATURED)
    val selectedSort: StateFlow<SortOption> = _selectedSort.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSelectedSort(sort: SortOption) {
        _selectedSort.value = sort
    }

    // Filtered Products
    val filteredProducts: StateFlow<List<Product>> = combine(
        allProducts,
        _searchQuery,
        _selectedCategory,
        _selectedSort
    ) { products, query, category, sort ->
        var list = products

        if (category != "All") {
            list = list.filter { it.category.equals(category, ignoreCase = true) }
        }

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.name.lowercase().contains(q) ||
                it.fabricType.lowercase().contains(q) ||
                it.category.lowercase().contains(q) ||
                it.description.lowercase().contains(q)
            }
        }

        when (sort) {
            SortOption.FEATURED -> list.sortedByDescending { it.isFeatured }
            SortOption.PRICE_LOW_HIGH -> list.sortedBy { it.price }
            SortOption.PRICE_HIGH_LOW -> list.sortedByDescending { it.price }
            SortOption.RATING -> list.sortedByDescending { it.rating }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Cart calculations
    val cartSubtotal: StateFlow<Double> = cartItems.combine(MutableStateFlow(Unit)) { items, _ ->
        items.sumOf { it.productPrice * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    private val _promoCode = MutableStateFlow("")
    val promoCode: StateFlow<String> = _promoCode.asStateFlow()

    private val _discountPercent = MutableStateFlow(0.0)
    val discountPercent: StateFlow<Double> = _discountPercent.asStateFlow()

    fun applyPromoCode(code: String): Boolean {
        val trimmed = code.trim().uppercase()
        _promoCode.value = trimmed
        return if (trimmed == "VIP10") {
            _discountPercent.value = 0.10 // 10% off
            true
        } else if (trimmed == "MSF15") {
            _discountPercent.value = 0.15 // 15% off
            true
        } else {
            _discountPercent.value = 0.0
            false
        }
    }

    // Toast / Snackbar notification message
    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    fun showMessage(msg: String) {
        viewModelScope.launch {
            _snackbarMessage.emit(msg)
        }
    }

    // Cart actions
    fun addToCart(product: Product, option: String, quantity: Int = 1) {
        viewModelScope.launch {
            if (product.stockQuantity <= 0) {
                showMessage("Sorry, ${product.name} is currently out of stock.")
                return@launch
            }
            repository.addToCart(product, option, quantity)
            showMessage("Added to bag: ${product.name}")
        }
    }

    fun updateCartQuantity(cartId: Long, newQuantity: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(cartId, newQuantity)
        }
    }

    fun removeCartItem(cartId: Long) {
        viewModelScope.launch {
            repository.removeCartItem(cartId)
            showMessage("Item removed from bag")
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    // Checkout State
    private val _checkoutForm = MutableStateFlow(CheckoutFormState())
    val checkoutForm: StateFlow<CheckoutFormState> = _checkoutForm.asStateFlow()

    fun updateCheckoutForm(
        name: String? = null,
        phone: String? = null,
        email: String? = null,
        address: String? = null,
        city: String? = null,
        notes: String? = null
    ) {
        val current = _checkoutForm.value
        _checkoutForm.value = current.copy(
            name = name ?: current.name,
            phone = phone ?: current.phone,
            email = email ?: current.email,
            address = address ?: current.address,
            city = city ?: current.city,
            notes = notes ?: current.notes,
            nameError = if (name != null) null else current.nameError,
            phoneError = if (phone != null) null else current.phoneError,
            emailError = if (email != null) null else current.emailError,
            addressError = if (address != null) null else current.addressError
        )
    }

    fun submitOrder(onSuccess: (OrderEntity) -> Unit) {
        val form = _checkoutForm.value
        var hasError = false

        val nameError = if (form.name.isBlank()) "Full name is required" else null
        val phoneError = if (form.phone.isBlank()) "Phone number is required" else if (form.phone.length < 10) "Enter valid phone number" else null
        val emailError = if (form.email.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(form.email).matches()) "Enter valid email" else null
        val addressError = if (form.address.isBlank()) "Delivery address is required" else null

        if (nameError != null || phoneError != null || emailError != null || addressError != null) {
            hasError = true
            _checkoutForm.value = form.copy(
                nameError = nameError,
                phoneError = phoneError,
                emailError = emailError,
                addressError = addressError
            )
            return
        }

        val items = cartItems.value
        if (items.isEmpty()) {
            showMessage("Your shopping bag is empty.")
            return
        }

        val subtotal = cartSubtotal.value
        val discount = subtotal * _discountPercent.value
        val afterDiscount = subtotal - discount
        val deliveryFee = if (afterDiscount >= 5000.0) 0.0 else 250.0
        val total = afterDiscount + deliveryFee

        _checkoutForm.value = form.copy(isSubmitting = true, submitError = null)

        viewModelScope.launch {
            val result = repository.placeOrder(
                cartList = items,
                customerName = form.name.trim(),
                customerPhone = form.phone.trim(),
                customerEmail = form.email.trim(),
                deliveryAddress = form.address.trim(),
                city = form.city.trim(),
                orderNotes = form.notes.trim(),
                subtotal = subtotal,
                deliveryFee = deliveryFee,
                total = total
            )

            result.onSuccess { order ->
                _checkoutForm.value = CheckoutFormState() // Reset
                _discountPercent.value = 0.0
                _promoCode.value = ""
                onSuccess(order)
            }.onFailure { err ->
                _checkoutForm.value = _checkoutForm.value.copy(
                    isSubmitting = false,
                    submitError = err.message ?: "Failed to place order. Please try again."
                )
                showMessage(err.message ?: "Error processing order")
            }
        }
    }

    // Order Tracking Lookup
    private val _trackingQuery = MutableStateFlow("")
    val trackingQuery: StateFlow<String> = _trackingQuery.asStateFlow()

    fun setTrackingQuery(query: String) {
        _trackingQuery.value = query
    }

    val trackedOrders: StateFlow<List<OrderEntity>> = combine(
        allOrders,
        _trackingQuery
    ) { orders, query ->
        if (query.isBlank()) {
            orders
        } else {
            val q = query.trim().lowercase()
            orders.filter {
                it.orderId.lowercase().contains(q) ||
                it.customerPhone.lowercase().contains(q) ||
                it.customerEmail.lowercase().contains(q)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Admin State
    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin.asStateFlow()

    fun authenticateAdmin(pin: String): Boolean {
        return if (pin == "1234" || pin == "MSF2026") {
            _isAdmin.value = true
            showMessage("Admin mode unlocked")
            true
        } else {
            showMessage("Invalid Admin PIN")
            false
        }
    }

    fun logoutAdmin() {
        _isAdmin.value = false
        showMessage("Logged out of Admin mode")
    }

    fun updateOrderStatus(orderId: String, newStatus: String) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, newStatus)
            showMessage("Order $orderId status updated to $newStatus")
        }
    }

    fun addOrUpdateProduct(product: Product) {
        viewModelScope.launch {
            if (product.id == 0L) {
                repository.insertProduct(product)
                showMessage("New fabric product added")
            } else {
                repository.updateProduct(product)
                showMessage("Product updated")
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
            showMessage("Product removed from catalog")
        }
    }

    fun updateStock(productId: Long, newStock: Int) {
        viewModelScope.launch {
            repository.updateStock(productId, newStock)
            showMessage("Stock quantity updated to $newStock")
        }
    }

    // Contact Form State
    private val _contactName = MutableStateFlow("")
    val contactName: StateFlow<String> = _contactName.asStateFlow()

    private val _contactEmail = MutableStateFlow("")
    val contactEmail: StateFlow<String> = _contactEmail.asStateFlow()

    private val _contactMessage = MutableStateFlow("")
    val contactMessage: StateFlow<String> = _contactMessage.asStateFlow()

    private val _contactSent = MutableStateFlow(false)
    val contactSent: StateFlow<Boolean> = _contactSent.asStateFlow()

    fun updateContact(name: String? = null, email: String? = null, message: String? = null) {
        name?.let { _contactName.value = it }
        email?.let { _contactEmail.value = it }
        message?.let { _contactMessage.value = it }
    }

    fun submitContactMessage(): Boolean {
        if (_contactName.value.isBlank() || _contactEmail.value.isBlank() || _contactMessage.value.isBlank()) {
            showMessage("Please fill all required fields")
            return false
        }
        _contactSent.value = true
        showMessage("Thank you! Your message has been sent to M Shakeel Fabrics team.")
        return true
    }

    fun resetContact() {
        _contactName.value = ""
        _contactEmail.value = ""
        _contactMessage.value = ""
        _contactSent.value = false
    }
}

class StoreViewModelFactory(
    private val repository: FabricStoreRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
            return StoreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
