package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val category: String, // "Men's Wear", "Women's Wear", "Unstitched Fabrics", "New Arrivals"
    val price: Double,
    val originalPrice: Double? = null,
    val imageUrl: String,
    val description: String,
    val fabricType: String,
    val options: String, // Comma separated: e.g. "4.5 Meters (Full Suit),7 Meters (Double Suit),Stitched Medium,Stitched Large"
    val stockQuantity: Int,
    val isFeatured: Boolean = false,
    val rating: Float = 4.8f,
    val reviewCount: Int = 18
)

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val cartId: Long = 0,
    val productId: Long,
    val productName: String,
    val productPrice: Double,
    val productImage: String,
    val fabricType: String,
    val selectedOption: String,
    val quantity: Int
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val orderId: String, // e.g., "MSF-74291"
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val deliveryAddress: String,
    val city: String,
    val orderNotes: String,
    val paymentMethod: String = "Cash on Delivery",
    val subtotal: Double,
    val deliveryFee: Double,
    val totalAmount: Double,
    val status: String, // "Pending", "Confirmed", "Shipped", "Delivered", "Cancelled"
    val itemsSummary: String, // "Royal Boski Silk x 1, Giza Cotton x 2"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val isLoggedIn: Boolean = false
)
