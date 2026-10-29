package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SilkWhite
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VipTopBar(
    cartCount: Int,
    isAdmin: Boolean,
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit,
    onWebClick: () -> Unit = {},
    onAdminClick: () -> Unit,
    onBrandClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(NavyDark, NavyPrimary)
                )
            )
            .border(width = 0.5.dp, color = GoldPrimary.copy(alpha = 0.3f))
    ) {
        Column {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier
                            .clickable(onClick = onBrandClick)
                            .testTag("brand_header_title")
                    ) {
                        Text(
                            text = "M SHAKEEL FABRICS",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp,
                                color = GoldLight
                            )
                        )
                        Text(
                            text = "LUXURY TEXTILES & COUTURE",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 9.sp,
                                letterSpacing = 1.5.sp,
                                color = GoldPrimary
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = GoldLight,
                    actionIconContentColor = GoldLight
                ),
                actions = {
                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier.testTag("topbar_search_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Catalog",
                            tint = GoldLight
                        )
                    }

                    IconButton(
                        onClick = onCartClick,
                        modifier = Modifier.testTag("topbar_cart_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (cartCount > 0) {
                                    Badge(
                                        containerColor = GoldAccent,
                                        contentColor = NavyDark
                                    ) {
                                        Text(
                                            text = cartCount.toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingBag,
                                contentDescription = "Shopping Bag",
                                tint = GoldLight
                            )
                        }
                    }

                    IconButton(
                        onClick = onWebClick,
                        modifier = Modifier.testTag("topbar_web_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = "Web Store Mode",
                            tint = GoldAccent
                        )
                    }

                    IconButton(
                        onClick = onAdminClick,
                        modifier = Modifier.testTag("topbar_admin_button")
                    ) {
                        Icon(
                            imageVector = if (isAdmin) Icons.Default.LockOpen else Icons.Default.Lock,
                            contentDescription = if (isAdmin) "Admin Active" else "Admin Portal",
                            tint = if (isAdmin) GoldAccent else GoldPrimary.copy(alpha = 0.7f)
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun VipBottomNav(
    currentScreen: Screen,
    cartCount: Int,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = NavyDark,
        contentColor = GoldLight,
        tonalElevation = 8.dp,
        modifier = Modifier.border(width = 0.5.dp, color = GoldPrimary.copy(alpha = 0.25f))
    ) {
        val isHome = currentScreen is Screen.Home
        NavigationBarItem(
            selected = isHome,
            onClick = { onNavigate(Screen.Home) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavyDark,
                selectedTextColor = GoldLight,
                indicatorColor = GoldAccent,
                unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                unselectedTextColor = GoldLight.copy(alpha = 0.6f)
            ),
            modifier = Modifier.testTag("nav_home")
        )

        val isShop = currentScreen is Screen.Shop || currentScreen is Screen.ProductDetail
        NavigationBarItem(
            selected = isShop,
            onClick = { onNavigate(Screen.Shop) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Storefront,
                    contentDescription = "Shop"
                )
            },
            label = { Text("Shop", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavyDark,
                selectedTextColor = GoldLight,
                indicatorColor = GoldAccent,
                unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                unselectedTextColor = GoldLight.copy(alpha = 0.6f)
            ),
            modifier = Modifier.testTag("nav_shop")
        )

        val isCart = currentScreen is Screen.Cart || currentScreen is Screen.Checkout
        NavigationBarItem(
            selected = isCart,
            onClick = { onNavigate(Screen.Cart) },
            icon = {
                BadgedBox(
                    badge = {
                        if (cartCount > 0) {
                            Badge(
                                containerColor = GoldAccent,
                                contentColor = NavyDark
                            ) {
                                Text(
                                    text = cartCount.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = "Bag"
                    )
                }
            },
            label = { Text("Bag", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavyDark,
                selectedTextColor = GoldLight,
                indicatorColor = GoldAccent,
                unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                unselectedTextColor = GoldLight.copy(alpha = 0.6f)
            ),
            modifier = Modifier.testTag("nav_bag")
        )

        val isTrack = currentScreen is Screen.OrderTracking || currentScreen is Screen.OrderConfirmation
        NavigationBarItem(
            selected = isTrack,
            onClick = { onNavigate(Screen.OrderTracking) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.LocalShipping,
                    contentDescription = "Track Order"
                )
            },
            label = { Text("Orders", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavyDark,
                selectedTextColor = GoldLight,
                indicatorColor = GoldAccent,
                unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                unselectedTextColor = GoldLight.copy(alpha = 0.6f)
            ),
            modifier = Modifier.testTag("nav_orders")
        )

        val isAbout = currentScreen is Screen.About || currentScreen is Screen.Contact
        NavigationBarItem(
            selected = isAbout,
            onClick = { onNavigate(Screen.About) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "About & Contact"
                )
            },
            label = { Text("Legacy", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavyDark,
                selectedTextColor = GoldLight,
                indicatorColor = GoldAccent,
                unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                unselectedTextColor = GoldLight.copy(alpha = 0.6f)
            ),
            modifier = Modifier.testTag("nav_about")
        )
    }
}
