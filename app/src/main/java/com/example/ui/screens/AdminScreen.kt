package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderEntity
import com.example.data.model.Product
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.LowStockOrange
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SilkWhite
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarmCream
import com.example.ui.viewmodel.StoreViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminScreen(
    viewModel: StoreViewModel,
    onBack: () -> Unit
) {
    val isAdmin by viewModel.isAdmin.collectAsState()
    var pinInput by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf(false) }

    if (!isAdmin) {
        // PIN Authentication Screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WarmCream)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = NavyDark)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(GoldAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = NavyDark,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "ADMINISTRATOR PORTAL",
                        color = GoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "M Shakeel Fabrics Staff Access",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily.Serif,
                            color = SilkWhite,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Enter master authorization PIN (Default: 1234)",
                        color = GoldLight.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = {
                            pinInput = it
                            pinError = false
                        },
                        placeholder = { Text("Enter 4-digit PIN", color = TextMuted) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        singleLine = true,
                        isError = pinError,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldAccent,
                            unfocusedBorderColor = GoldPrimary.copy(alpha = 0.5f),
                            focusedContainerColor = NavyLight,
                            unfocusedContainerColor = NavyLight,
                            focusedTextColor = SilkWhite,
                            unfocusedTextColor = SilkWhite
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_pin_input")
                    )

                    if (pinError) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Incorrect PIN. Default is 1234",
                            color = ErrorRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val ok = viewModel.authenticateAdmin(pinInput)
                            if (!ok) pinError = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldAccent,
                            contentColor = NavyDark
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("admin_login_btn")
                    ) {
                        Text("Unlock Dashboard", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = onBack,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cancel & Return")
                    }
                }
            }
        }
        return
    }

    // Authenticated Admin Dashboard
    var currentTab by remember { mutableIntStateOf(0) }
    val adminTabs = listOf("Orders Fulfillment", "Products & Stock", "Store Analytics")

    var showAddProductDialog by remember { mutableStateOf(false) }
    var editingProduct by remember { mutableStateOf<Product?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Admin Bar
        Surface(
            color = NavyDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(top = 12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBack) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = GoldLight)
                        }
                        Column {
                            Text(
                                text = "ADMIN MANAGEMENT PORTAL",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLight,
                                    letterSpacing = 1.sp
                                )
                            )
                            Text(
                                text = "M Shakeel Fabrics Live Operations",
                                style = MaterialTheme.typography.bodySmall.copy(color = GoldPrimary)
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.logoutAdmin() },
                        colors = ButtonDefaults.buttonColors(containerColor = NavyLight, contentColor = GoldLight),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.testTag("admin_lock_btn")
                    ) {
                        Text("Lock", fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TabRow(
                    selectedTabIndex = currentTab,
                    containerColor = NavyDark,
                    contentColor = GoldLight,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[currentTab]),
                            color = GoldAccent,
                            height = 3.dp
                        )
                    }
                ) {
                    adminTabs.forEachIndexed { index, title ->
                        Tab(
                            selected = currentTab == index,
                            onClick = { currentTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 11.sp,
                                    fontWeight = if (currentTab == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (currentTab == index) GoldLight else GoldPrimary.copy(alpha = 0.6f)
                                )
                            }
                        )
                    }
                }
            }
        }

        // Tab Content
        when (currentTab) {
            0 -> AdminOrdersTab(viewModel = viewModel)
            1 -> AdminProductsTab(
                viewModel = viewModel,
                onAddProduct = { showAddProductDialog = true },
                onEditProduct = { editingProduct = it }
            )
            2 -> AdminAnalyticsTab(viewModel = viewModel)
        }
    }

    // Add / Edit Product Dialog
    if (showAddProductDialog || editingProduct != null) {
        ProductEditDialog(
            product = editingProduct,
            onDismiss = {
                showAddProductDialog = false
                editingProduct = null
            },
            onSave = { product ->
                viewModel.addOrUpdateProduct(product)
                showAddProductDialog = false
                editingProduct = null
            }
        )
    }
}

@Composable
fun AdminOrdersTab(viewModel: StoreViewModel) {
    val orders by viewModel.allOrders.collectAsState()
    val currencyFormatter = NumberFormat.getNumberInstance(Locale.US)
    val dateFormatter = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())

    val statusOptions = listOf("Pending", "Confirmed", "Shipped", "Delivered", "Cancelled")

    if (orders.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No customer orders placed yet.", color = TextSecondary)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(orders, key = { it.orderId }) { order ->
                var menuExpanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                    colors = CardDefaults.cardColors(containerColor = SilkWhite)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = order.orderId,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = NavyDark
                                )
                                Text(
                                    text = dateFormatter.format(Date(order.timestamp)),
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }

                            // Status Dropdown trigger
                            Box {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = when (order.status.lowercase()) {
                                        "delivered" -> SuccessGreen
                                        "shipped" -> NavyPrimary
                                        "confirmed" -> GoldDark
                                        "cancelled" -> ErrorRed
                                        else -> LowStockOrange
                                    },
                                    modifier = Modifier.clickable { menuExpanded = true }
                                ) {
                                    Text(
                                        text = "${order.status.uppercase()} ▼",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }

                                DropdownMenu(
                                    expanded = menuExpanded,
                                    onDismissRequest = { menuExpanded = false },
                                    modifier = Modifier.background(SilkWhite)
                                ) {
                                    statusOptions.forEach { status ->
                                        DropdownMenuItem(
                                            text = { Text(status, fontWeight = FontWeight.SemiBold) },
                                            onClick = {
                                                viewModel.updateOrderStatus(order.orderId, status)
                                                menuExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = CreamBorder, thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Items: ${order.itemsSummary}", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Customer: ${order.customerName} (${order.customerPhone})", fontSize = 12.sp, color = TextSecondary)
                        Text("Address: ${order.deliveryAddress}, ${order.city}", fontSize = 12.sp, color = TextSecondary)
                        if (order.orderNotes.isNotBlank()) {
                            Text("Notes: ${order.orderNotes}", fontSize = 11.sp, color = GoldDark, fontWeight = FontWeight.SemiBold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Payment: ${order.paymentMethod}", fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                            Text(
                                text = "Rs. ${currencyFormatter.format(order.totalAmount.toInt())}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = NavyPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminProductsTab(
    viewModel: StoreViewModel,
    onAddProduct: () -> Unit,
    onEditProduct: (Product) -> Unit
) {
    val products by viewModel.allProducts.collectAsState()
    val currencyFormatter = NumberFormat.getNumberInstance(Locale.US)

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Button(
            onClick = onAddProduct,
            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary, contentColor = GoldLight),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().height(44.dp).testTag("admin_add_product_btn")
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Add New Fabric / Attire Product", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products, key = { it.id }) { p ->
                Card(
                    modifier = Modifier.fillMaxWidth().border(1.dp, CreamBorder, RoundedCornerShape(8.dp)),
                    colors = CardDefaults.cardColors(containerColor = SilkWhite)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(p.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyDark)
                            Text("Category: ${p.category} • ${p.fabricType}", fontSize = 11.sp, color = TextSecondary)
                            Text(
                                "Price: Rs. ${currencyFormatter.format(p.price.toInt())} | Stock: ${p.stockQuantity} units",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (p.stockQuantity <= 5) LowStockOrange else SuccessGreen
                            )
                        }

                        IconButton(onClick = { onEditProduct(p) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = NavyPrimary)
                        }

                        IconButton(onClick = { viewModel.deleteProduct(p) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminAnalyticsTab(viewModel: StoreViewModel) {
    val orders by viewModel.allOrders.collectAsState()
    val products by viewModel.allProducts.collectAsState()
    val currencyFormatter = NumberFormat.getNumberInstance(Locale.US)

    val totalRevenue = orders.filter { it.status.lowercase() != "cancelled" }.sumOf { it.totalAmount }
    val pendingCount = orders.count { it.status.equals("Pending", ignoreCase = true) }
    val deliveredCount = orders.count { it.status.equals("Delivered", ignoreCase = true) }
    val lowStockCount = products.count { it.stockQuantity <= 5 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Metric 1: Revenue
        Card(
            modifier = Modifier.fillMaxWidth().border(1.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(10.dp)),
            colors = CardDefaults.cardColors(containerColor = NavyDark)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("TOTAL STORE REVENUE", color = GoldLight, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Rs. ${currencyFormatter.format(totalRevenue.toInt())}",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )
                )
                Text("From ${orders.size} customer orders", color = SilkWhite.copy(alpha = 0.7f), fontSize = 11.sp)
            }
        }

        // Metric 2: Order Status Grid
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Card(
                modifier = Modifier.weight(1f).border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(containerColor = SilkWhite)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Pending Orders", fontSize = 11.sp, color = TextSecondary)
                    Text(pendingCount.toString(), fontWeight = FontWeight.Bold, fontSize = 24.sp, color = LowStockOrange)
                    Text("Awaiting packaging", fontSize = 10.sp, color = TextMuted)
                }
            }

            Card(
                modifier = Modifier.weight(1f).border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(containerColor = SilkWhite)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Delivered Orders", fontSize = 11.sp, color = TextSecondary)
                    Text(deliveredCount.toString(), fontWeight = FontWeight.Bold, fontSize = 24.sp, color = SuccessGreen)
                    Text("Completed COD", fontSize = 10.sp, color = TextMuted)
                }
            }
        }

        // Metric 3: Low Stock Alerts
        Card(
            modifier = Modifier.fillMaxWidth().border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
            colors = CardDefaults.cardColors(containerColor = SilkWhite)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = LowStockOrange, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Low Inventory Alerts ($lowStockCount items)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyPrimary)
                }

                Spacer(modifier = Modifier.height(10.dp))

                val lowStockProducts = products.filter { it.stockQuantity <= 5 }
                if (lowStockProducts.isEmpty()) {
                    Text("All fabric rolls and catalog suits are adequately stocked.", fontSize = 12.sp, color = SuccessGreen)
                } else {
                    lowStockProducts.forEach { p ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(p.name, fontSize = 12.sp, color = TextPrimary, modifier = Modifier.weight(1f), maxLines = 1)
                            Text(
                                text = if (p.stockQuantity <= 0) "SOLD OUT" else "${p.stockQuantity} remaining",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = if (p.stockQuantity <= 0) ErrorRed else LowStockOrange
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductEditDialog(
    product: Product?,
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var category by remember { mutableStateOf(product?.category ?: "Men's Wear") }
    var priceStr by remember { mutableStateOf(product?.price?.toInt()?.toString() ?: "5000") }
    var fabricType by remember { mutableStateOf(product?.fabricType ?: "100% Pure Cotton") }
    var description by remember { mutableStateOf(product?.description ?: "Handcrafted luxury fabric.") }
    var optionsStr by remember { mutableStateOf(product?.options ?: "4.5 Meters (Full Suit),7 Meters") }
    var stockStr by remember { mutableStateOf(product?.stockQuantity?.toString() ?: "15") }
    var imageUrl by remember {
        mutableStateOf(product?.imageUrl ?: "https://images.unsplash.com/photo-1594938298603-c8148c4dae35?w=800")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (product == null) "Add New Luxury Fabric" else "Edit Product",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Product Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category (Men's Wear, Women's Wear, Unstitched Fabrics, New Arrivals)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = priceStr,
                        onValueChange = { priceStr = it },
                        label = { Text("Price (Rs)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = stockStr,
                        onValueChange = { stockStr = it },
                        label = { Text("Stock") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = fabricType,
                    onValueChange = { fabricType = it },
                    label = { Text("Fabric Type (e.g. 100% Pure Silk)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = optionsStr,
                    onValueChange = { optionsStr = it },
                    label = { Text("Options (comma separated)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val price = priceStr.toDoubleOrNull() ?: 5000.0
                    val stock = stockStr.toIntOrNull() ?: 10
                    val newProduct = Product(
                        id = product?.id ?: 0L,
                        name = name.ifBlank { "M Shakeel Fabric" },
                        category = category.ifBlank { "Men's Wear" },
                        price = price,
                        originalPrice = price * 1.15,
                        imageUrl = imageUrl,
                        description = description,
                        fabricType = fabricType,
                        options = optionsStr,
                        stockQuantity = stock,
                        isFeatured = true,
                        rating = 4.8f,
                        reviewCount = 20
                    )
                    onSave(newProduct)
                },
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary, contentColor = GoldLight)
            ) {
                Text("Save Product")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
