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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CreamBorder
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
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

@Composable
fun AboutAndContactScreen(
    viewModel: StoreViewModel,
    initialTab: Int = 0
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) }
    val tabs = listOf("Brand Story", "Showrooms & Contact", "VIP FAQs")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        // Navy Header with Tabs
        Surface(
            color = NavyDark,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    text = "M SHAKEEL FABRICS",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        letterSpacing = 2.sp
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = "A Legacy of Pure Weaving, Comfort, and Timeless Elegance",
                    style = MaterialTheme.typography.bodySmall.copy(color = GoldPrimary),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = NavyDark,
                    contentColor = GoldLight,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = GoldAccent,
                            height = 3.dp
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == index) GoldLight else GoldPrimary.copy(alpha = 0.6f)
                                )
                            },
                            modifier = Modifier.testTag("tab_$index")
                        )
                    }
                }
            }
        }

        // Tab Content
        when (selectedTab) {
            0 -> AboutTab()
            1 -> ContactTab(viewModel = viewModel)
            2 -> FaqsTab()
        }
    }
}

@Composable
fun AboutTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CreamBorder, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = SilkWhite)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.HistoryEdu,
                        contentDescription = null,
                        tint = GoldDark,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "THE SARTORIAL GENESIS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark,
                        letterSpacing = 1.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Rooted in Tradition, Tailored for Royalty",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Founded with a profound reverence for the master weavers of the subcontinent, M SHAKEEL FABRICS has remained the definitive destination for patrons seeking uncompromising fabric purity. We believe clothing is an extension of character—crafted to be felt, admired, and cherished.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextPrimary,
                        lineHeight = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "From our celebrated Chinese Silk 7-pound Royal Boski to extra-long staple Egyptian Giza cottons and handspun winter Karandi, every single bolt is spun with strict yarn count controls and organic finishing treatments that preserve fabric softness through years of wearing.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextPrimary,
                        lineHeight = 22.sp
                    )
                )
            }
        }

        // Three Pillars Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CreamBorder, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = SilkWhite)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "OUR CORE WEAVING PROMISES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldDark,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                val pillars = listOf(
                    "Pure Yarn Integrity" to "Zero synthetic adulteration in our pure silk, latha, and fine wool lines.",
                    "Color Fastness Guarantee" to "Reactive vat dyeing resistant to intense summer heat and repeated laundering.",
                    "Pre-Shrunk & Mercerized" to "Engineered dimensional stability so your bespoke tailoring fits right out of the box."
                )

                pillars.forEach { (title, desc) ->
                    Row(modifier = Modifier.padding(vertical = 6.dp)) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = GoldDark,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyPrimary)
                            Text(desc, fontSize = 12.sp, color = TextSecondary)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun ContactTab(viewModel: StoreViewModel) {
    val name by viewModel.contactName.collectAsState()
    val email by viewModel.contactEmail.collectAsState()
    val message by viewModel.contactMessage.collectAsState()
    val isSent by viewModel.contactSent.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Showrooms & Contact Info Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CreamBorder, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = SilkWhite)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "FLAGSHIP BOUTIQUES & CONCIERGE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldDark,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = GoldDark, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Helpline & WhatsApp Orders", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NavyPrimary)
                        Text("+92 300 1234567 • +92 42 35789000", fontSize = 12.sp, color = TextSecondary)
                    }
                }

                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = GoldDark, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Customer Support & Corporate Inquiries", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NavyPrimary)
                        Text("support@mshakeelfabrics.com", fontSize = 12.sp, color = TextSecondary)
                    }
                }

                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = GoldDark, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Lahore Flagship", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NavyPrimary)
                        Text("Shop 14-16, Liberty Commercial Complex, Gulberg III, Lahore", fontSize = 12.sp, color = TextSecondary)
                    }
                }

                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Icon(imageVector = Icons.Default.Business, contentDescription = null, tint = GoldDark, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Karachi Experience Lounge", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NavyPrimary)
                        Text("Plot 8-C, Zamzama Boulevard, Phase V, DHA, Karachi", fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }
        }

        // Interactive Contact Form Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CreamBorder, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = SilkWhite)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "SEND US AN INQUIRY",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldDark,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Have questions regarding fabric meterage, seasonal suitability, or bridal couture orders? Send our master tailors a direct message.",
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(14.dp))

                if (isSent) {
                    Surface(
                        color = SuccessGreen.copy(alpha = 0.1f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Message Received!", fontWeight = FontWeight.Bold, color = SuccessGreen, fontSize = 14.sp)
                            Text(
                                "Our fabric concierge will contact your email shortly.",
                                fontSize = 12.sp,
                                color = TextPrimary,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = { viewModel.resetContact() },
                                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary, contentColor = GoldLight)
                            ) {
                                Text("Send Another Message")
                            }
                        }
                    }
                } else {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { viewModel.updateContact(name = it) },
                        label = { Text("Your Name *") },
                        placeholder = { Text("e.g. Tariq Mehmood") },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldDark,
                            unfocusedBorderColor = CreamBorder
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("contact_name_input")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { viewModel.updateContact(email = it) },
                        label = { Text("Email Address *") },
                        placeholder = { Text("tariq@example.com") },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldDark,
                            unfocusedBorderColor = CreamBorder
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("contact_email_input")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange = { viewModel.updateContact(message = it) },
                        label = { Text("Your Message / Tailoring Inquiry *") },
                        placeholder = { Text("Write your query regarding fabric texture or delivery...") },
                        minLines = 3,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GoldDark,
                            unfocusedBorderColor = CreamBorder
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("contact_message_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { viewModel.submitContactMessage() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavyPrimary,
                            contentColor = GoldLight
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("contact_submit_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Submit Message", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun FaqsTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val faqs = listOf(
            "What is the standard unstitched cut size for Men's Wear?" to "Our standard unstitched suits are cut to a generous 4.5 meters (1.5 yards width / double arha) which easily accommodates tall gentlemen up to 6'4\" for a full kurta and shalwar. Double suit options are 7 meters.",
            "How does Cash on Delivery (COD) work?" to "We dispatch via express courier (TCS/Leopard/Trax) with zero advance payment needed. When the parcel arrives at your doorstep, simply pay the courier rider in cash.",
            "What is the genuine definition of Royal 7-Pound Boski?" to "7-Pound refers to the traditional Chinese weight measure (taels/pounds) of raw silk filaments per bolt. It denotes a dense, heavy silk with exceptional fall and zero transparency.",
            "Can I exchange or return fabric if I am not satisfied?" to "Yes! M Shakeel Fabrics offers a 7-day hassle-free return and exchange policy on all unstitched fabrics as long as the bolt has not been cut or washed."
        )

        faqs.forEach { (q, a) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CreamBorder, RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(containerColor = SilkWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = null,
                            tint = GoldDark,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = q,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = a,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary,
                            lineHeight = 18.sp
                        ),
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }
        }
    }
}
