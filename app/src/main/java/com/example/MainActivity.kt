package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.AppDatabase
import com.example.data.repository.FabricStoreRepository
import com.example.ui.components.VipBottomNav
import com.example.ui.components.VipTopBar
import com.example.ui.screens.AboutAndContactScreen
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CheckoutScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderConfirmationScreen
import com.example.ui.screens.OrderTrackingScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ShopScreen
import com.example.ui.screens.WebViewScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.StoreViewModel
import com.example.ui.viewmodel.StoreViewModelFactory
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext, lifecycleScope)
        val repository = FabricStoreRepository(database)
        val viewModelFactory = StoreViewModelFactory(repository)

        setContent {
            MyApplicationTheme {
                val storeViewModel: StoreViewModel = viewModel(factory = viewModelFactory)
                MainAppContent(viewModel = storeViewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: StoreViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val allProducts by viewModel.allProducts.collectAsStateWithLifecycle()
    val featuredProducts by viewModel.featuredProducts.collectAsStateWithLifecycle()
    val filteredProducts by viewModel.filteredProducts.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedSort by viewModel.selectedSort.collectAsStateWithLifecycle()
    val cartSubtotal by viewModel.cartSubtotal.collectAsStateWithLifecycle()
    val discountPercent by viewModel.discountPercent.collectAsStateWithLifecycle()
    val isAdmin by viewModel.isAdmin.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collectLatest { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    // Hardware Back Button Handler
    BackHandler(enabled = currentScreen !is Screen.Home) {
        val popped = viewModel.navigateBack()
        if (!popped) {
            viewModel.navigateTo(Screen.Home)
        }
    }

    val totalCartCount = cartItems.sumOf { it.quantity }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentScreen !is Screen.Admin) {
                VipTopBar(
                    cartCount = totalCartCount,
                    isAdmin = isAdmin,
                    onSearchClick = {
                        viewModel.navigateTo(Screen.Shop)
                    },
                    onCartClick = {
                        viewModel.navigateTo(Screen.Cart)
                    },
                    onWebClick = {
                        viewModel.navigateTo(Screen.WebStore)
                    },
                    onAdminClick = {
                        viewModel.navigateTo(Screen.Admin)
                    },
                    onBrandClick = {
                        viewModel.navigateTo(Screen.Home)
                    }
                )
            }
        },
        bottomBar = {
            if (currentScreen !is Screen.Admin && currentScreen !is Screen.Checkout && currentScreen !is Screen.OrderConfirmation) {
                VipBottomNav(
                    currentScreen = currentScreen,
                    cartCount = totalCartCount,
                    onNavigate = { screen ->
                        viewModel.navigateTo(screen)
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (val screen = currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        viewModel = viewModel,
                        featuredProducts = featuredProducts,
                        onProductClick = { productId ->
                            viewModel.navigateTo(Screen.ProductDetail(productId))
                        },
                        onNavigateShop = { category ->
                            viewModel.setSelectedCategory(category)
                            viewModel.navigateTo(Screen.Shop)
                        },
                        onNavigateAbout = {
                            viewModel.navigateTo(Screen.About)
                        },
                        onNavigateContact = {
                            viewModel.navigateTo(Screen.Contact)
                        }
                    )
                }

                is Screen.Shop -> {
                    ShopScreen(
                        viewModel = viewModel,
                        products = filteredProducts,
                        searchQuery = searchQuery,
                        selectedCategory = selectedCategory,
                        selectedSort = selectedSort,
                        onProductClick = { productId ->
                            viewModel.navigateTo(Screen.ProductDetail(productId))
                        }
                    )
                }

                is Screen.ProductDetail -> {
                    val product = allProducts.firstOrNull { it.id == screen.productId }
                    ProductDetailScreen(
                        product = product,
                        viewModel = viewModel,
                        onBack = { viewModel.navigateBack() },
                        onNavigateCheckout = { viewModel.navigateTo(Screen.Checkout) }
                    )
                }

                is Screen.Cart -> {
                    CartScreen(
                        viewModel = viewModel,
                        cartItems = cartItems,
                        subtotal = cartSubtotal,
                        discountPercent = discountPercent,
                        onNavigateShop = { viewModel.navigateTo(Screen.Shop) },
                        onNavigateCheckout = { viewModel.navigateTo(Screen.Checkout) }
                    )
                }

                is Screen.Checkout -> {
                    CheckoutScreen(
                        viewModel = viewModel,
                        cartItems = cartItems,
                        subtotal = cartSubtotal,
                        discountPercent = discountPercent,
                        onBack = { viewModel.navigateBack() },
                        onOrderSuccess = { order ->
                            viewModel.navigateTo(Screen.OrderConfirmation(order.orderId))
                        }
                    )
                }

                is Screen.OrderConfirmation -> {
                    OrderConfirmationScreen(
                        orderId = screen.orderId,
                        viewModel = viewModel,
                        onTrackOrder = {
                            viewModel.setTrackingQuery(screen.orderId)
                            viewModel.navigateTo(Screen.OrderTracking)
                        },
                        onContinueShopping = {
                            viewModel.navigateTo(Screen.Home)
                        }
                    )
                }

                is Screen.OrderTracking -> {
                    OrderTrackingScreen(
                        viewModel = viewModel,
                        onNavigateShop = { viewModel.navigateTo(Screen.Shop) }
                    )
                }

                is Screen.About -> {
                    AboutAndContactScreen(
                        viewModel = viewModel,
                        initialTab = 0
                    )
                }

                is Screen.Contact -> {
                    AboutAndContactScreen(
                        viewModel = viewModel,
                        initialTab = 1
                    )
                }

                is Screen.Admin -> {
                    AdminScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.navigateBack() }
                    )
                }

                is Screen.WebStore -> {
                    WebViewScreen(
                        onBack = { viewModel.navigateBack() }
                    )
                }
            }
        }
    }
}
