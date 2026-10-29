package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.model.CartItem
import com.example.data.model.OrderEntity
import com.example.data.model.Product
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class FabricStoreRepository(private val database: AppDatabase) {
    private val productDao = database.productDao()
    private val cartDao = database.cartDao()
    private val orderDao = database.orderDao()
    private val userDao = database.userDao()

    val allProducts: Flow<List<Product>> = productDao.getAllProducts()
    val featuredProducts: Flow<List<Product>> = productDao.getFeaturedProducts()
    val cartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()
    val allOrders: Flow<List<OrderEntity>> = orderDao.getAllOrders()
    val currentUser: Flow<UserProfile?> = userDao.getCurrentUser()

    fun getProductsByCategory(category: String): Flow<List<Product>> {
        return if (category == "All") {
            productDao.getAllProducts()
        } else {
            productDao.getProductsByCategory(category)
        }
    }

    fun getProductById(id: Long): Flow<Product?> = productDao.getProductById(id)

    suspend fun addToCart(product: Product, selectedOption: String, quantity: Int = 1) {
        val existing = cartDao.findCartItem(product.id, selectedOption)
        if (existing != null) {
            cartDao.updateQuantity(existing.cartId, existing.quantity + quantity)
        } else {
            cartDao.insertCartItem(
                CartItem(
                    productId = product.id,
                    productName = product.name,
                    productPrice = product.price,
                    productImage = product.imageUrl,
                    fabricType = product.fabricType,
                    selectedOption = selectedOption,
                    quantity = quantity
                )
            )
        }
    }

    suspend fun updateCartQuantity(cartId: Long, newQuantity: Int) {
        if (newQuantity <= 0) {
            cartDao.deleteCartItem(cartId)
        } else {
            cartDao.updateQuantity(cartId, newQuantity)
        }
    }

    suspend fun removeCartItem(cartId: Long) {
        cartDao.deleteCartItem(cartId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun placeOrder(
        cartList: List<CartItem>,
        customerName: String,
        customerPhone: String,
        customerEmail: String,
        deliveryAddress: String,
        city: String,
        orderNotes: String,
        subtotal: Double,
        deliveryFee: Double,
        total: Double
    ): Result<OrderEntity> {
        if (cartList.isEmpty()) {
            return Result.failure(Exception("Shopping cart is empty"))
        }

        // 1. Stock check
        for (item in cartList) {
            val product = productDao.getProductByIdSync(item.productId)
            if (product == null) {
                return Result.failure(Exception("Product '${item.productName}' is no longer available."))
            }
            if (product.stockQuantity < item.quantity) {
                return Result.failure(
                    Exception("Insufficient stock for '${item.productName}'. Only ${product.stockQuantity} remaining.")
                )
            }
        }

        // 2. Reduce stock for each product
        for (item in cartList) {
            productDao.reduceStock(item.productId, item.quantity)
        }

        // 3. Create Order
        val randomNum = (10000..99999).random()
        val orderId = "MSF-$randomNum"
        val itemsSummary = cartList.joinToString(separator = ", ") {
            "${it.productName} (${it.selectedOption}) x${it.quantity}"
        }

        val order = OrderEntity(
            orderId = orderId,
            customerName = customerName,
            customerPhone = customerPhone,
            customerEmail = customerEmail,
            deliveryAddress = deliveryAddress,
            city = city,
            orderNotes = orderNotes,
            paymentMethod = "Cash on Delivery",
            subtotal = subtotal,
            deliveryFee = deliveryFee,
            totalAmount = total,
            status = "Pending",
            itemsSummary = itemsSummary,
            timestamp = System.currentTimeMillis()
        )

        orderDao.insertOrder(order)

        // 4. Clear cart after successful checkout
        cartDao.clearCart()

        return Result.success(order)
    }

    fun getOrderById(orderId: String): Flow<OrderEntity?> = orderDao.getOrderById(orderId)

    fun searchOrders(query: String): Flow<List<OrderEntity>> = orderDao.searchOrders(query)

    suspend fun updateOrderStatus(orderId: String, newStatus: String) {
        orderDao.updateOrderStatus(orderId, newStatus)
    }

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    suspend fun updateStock(productId: Long, stock: Int) {
        productDao.updateStock(productId, stock)
    }

    suspend fun saveUserProfile(profile: UserProfile) {
        userDao.saveUser(profile)
    }
}
