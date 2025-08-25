package com.example.bkashclone.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.graphicsLayer
import com.example.bkashclone.R
import com.example.bkashclone.ui.theme.BkashPink
import com.example.bkashclone.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val uiState by homeViewModel.uiState.collectAsState()
    var showBalance by remember { mutableStateOf(false) }
    var currentBannerIndex by remember { mutableStateOf(0) }
    
    val bannerImages = listOf(
        R.drawable.roller_image_1,
        R.drawable.roller_image_2,
        R.drawable.roller_image_3
    )
    
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            currentBannerIndex = (currentBannerIndex + 1) % bannerImages.size
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Top Header
            item {
                TopHeader(
                    showBalance = showBalance,
                    onBalanceTap = { showBalance = !showBalance },
                    userBalance = uiState.homeData?.balance ?: "৳0",
                    userName = uiState.user?.name ?: "User"
                )
            }
            
            // Services Grid
            item {
                ServicesGrid()
            }
            
            // Banner Carousel
            item {
                BannerCarousel(
                    images = bannerImages,
                    currentIndex = currentBannerIndex,
                    onIndexChange = { currentBannerIndex = it }
                )
            }
            
            // My bKash Section
            item {
                MyBkashSection()
            }
            
            // bKash Bundle Section
            item {
                BkashBundleSection()
            }
            
            // Suggestions Section
            item {
                SuggestionsSection()
            }
            
            // Offers Section
            item {
                OffersSection()
            }
            
            // More Services Section
            item {
                MoreServicesSection()
            }
        }
        
        // Bottom Navigation
        BottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TopHeader(
    showBalance: Boolean,
    onBalanceTap: () -> Unit,
    userBalance: String,
    userName: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = Color(0xFFE51D74),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Picture
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Name and Balance Button Column
            Column {
                Text(
                    text = userName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Balance Button
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(32.dp)
                        .background(Color.White, RoundedCornerShape(24.dp))
                        .clickable { onBalanceTap() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Taka symbol that slides from left to right
                        val takaOffset by animateFloatAsState(
                            targetValue = if (showBalance) 0f else -20f,
                            animationSpec = tween(300),
                            label = "taka_slide"
                        )
                        
                        Text(
                            text = "৳",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE51D74),
                            modifier = Modifier.graphicsLayer(translationX = takaOffset)
                        )
                        
                        Spacer(modifier = Modifier.width(4.dp))
                        
                        // Balance or "Tap for Balance" text
                        val textOffset by animateFloatAsState(
                            targetValue = if (showBalance) 0f else 20f,
                            animationSpec = tween(300),
                            label = "text_slide"
                        )
                        
                        Text(
                            text = if (showBalance) userBalance.replace("৳", "") else "Tap for Balance",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE51D74),
                            modifier = Modifier.graphicsLayer(translationX = textOffset)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Right Icons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.points),
                    contentDescription = "Trophy",
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit
                )
                Image(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Menu",
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun ServicesGrid() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.height(400.dp)
        ) {
            items(servicesList) { service ->
                ServiceItem(service = service)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // See More Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = { /* Navigate to see more */ }
            ) {
                Text(
                    text = "See More",
                    color = Color(0xFFE51D74),
                    fontSize = 14.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "See More",
                    tint = Color(0xFFE51D74),
                    modifier = Modifier
                        .size(16.dp)
                        .graphicsLayer(rotationZ = 180f)
                )
            }
        }
    }
}

@Composable
fun ServiceItem(service: ServiceItem) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Navigate to service */ }
    ) {
        Box {
            Image(
                painter = painterResource(id = service.icon),
                contentDescription = service.name,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit
            )
            if (service.hasBadge) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(Color.Red, CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = service.name,
            fontSize = 11.sp,
            color = Color(0xFF757575),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BannerCarousel(
    images: List<Int>,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { /* Handle banner tap */ }
        ) {
            Image(
                painter = painterResource(id = images[currentIndex]),
                contentDescription = "Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .background(
                            color = if (index == currentIndex) Color(0xFFE51D74) else Color(0xFF757575),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
fun MyBkashSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "My bKash",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(160.dp)
        ) {
            items(myBkashItems) { item ->
                MyBkashCard(item = item)
            }
        }
    }
}

@Composable
fun MyBkashCard(item: MyBkashItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clickable { /* Navigate to item */ },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = item.name,
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = item.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                if (item.subtext != null) {
                    Text(
                        text = item.subtext,
                        fontSize = 10.sp,
                        color = Color(0xFF757575)
                    )
                }
            }
        }
    }
}

@Composable
fun BkashBundleSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "bKash Bundle",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )
            TextButton(onClick = { /* See all bundles */ }) {
                Text(
                    text = "See All",
                    color = Color(0xFFE51D74),
                    fontSize = 14.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(bundleItems) { bundle ->
                BundleCard(bundle = bundle)
            }
        }
    }
}

@Composable
fun BundleCard(bundle: BundleItem) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clickable { /* Navigate to bundle */ },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = bundle.image),
                contentDescription = bundle.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Price badge
            Card(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE51D74))
            ) {
                Text(
                    text = bundle.price,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SuggestionsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Suggestions",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF757575)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(suggestionItems) { suggestion ->
                SuggestionItem(suggestion = suggestion)
            }
        }
    }
}

@Composable
fun SuggestionItem(suggestion: SuggestionItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { /* Navigate to suggestion */ }
    ) {
        Image(
            painter = painterResource(id = suggestion.icon),
            contentDescription = suggestion.name,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun OffersSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Offers",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF757575)
            )
            TextButton(onClick = { /* See all offers */ }) {
                Text(
                    text = "See All",
                    color = Color(0xFFE51D74),
                    fontSize = 14.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(offerItems) { offer ->
                OfferCard(offer = offer)
            }
        }
    }
}

@Composable
fun OfferCard(offer: OfferItem) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clickable { /* Navigate to offer */ },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = offer.image),
                contentDescription = offer.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Discount text
            Text(
                text = offer.discount,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun MoreServicesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "More Services",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF757575)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.height(160.dp)
        ) {
            items(moreServicesList) { service ->
                ServiceItem(service = service)
            }
        }
    }
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(4.dp),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = R.drawable.home,
                label = "Home",
                isSelected = true
            )
            BottomNavItem(
                icon = R.drawable.scan_qr,
                label = "Scan QR",
                isSelected = false
            )
            BottomNavItem(
                icon = R.drawable.search,
                label = "Search",
                isSelected = false
            )
            BottomNavItem(
                icon = R.drawable.inbox,
                label = "Inbox",
                isSelected = false
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: Int,
    label: String,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Navigate to tab */ }
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) Color(0xFFE51D74) else Color(0xFF757575)
        )
    }
}

// Data Classes
data class ServiceItem(
    val name: String,
    val icon: Int,
    val hasBadge: Boolean = false
)

data class MyBkashItem(
    val name: String,
    val icon: Int,
    val subtext: String? = null
)

data class BundleItem(
    val name: String,
    val image: Int,
    val price: String
)

data class SuggestionItem(
    val name: String,
    val icon: Int
)

data class OfferItem(
    val name: String,
    val image: Int,
    val discount: String
)

// Sample Data
val servicesList = listOf(
    ServiceItem("Send Money", R.drawable.send_money),
    ServiceItem("Mobile Recharge", R.drawable.mobile_recherge),
    ServiceItem("Cash Out", R.drawable.cash_out),
    ServiceItem("Make Payment", R.drawable.make_payment),
    ServiceItem("Add Money", R.drawable.add_money, true),
    ServiceItem("Pay Bill", R.drawable.pay_bill),
    ServiceItem("Savings", R.drawable.savings),
    ServiceItem("Loan", R.drawable.loan),
    ServiceItem("Insurance", R.drawable.insurance),
    ServiceItem("bKash to Bank", R.drawable.bkash_to_bank, true),
    ServiceItem("Education Fee", R.drawable.education_fee),
    ServiceItem("Microfinance", R.drawable.microfinance),
    ServiceItem("Toll", R.drawable.toll),
    ServiceItem("Request Money", R.drawable.request_money),
    ServiceItem("Remittance", R.drawable.remittance),
    ServiceItem("Donation", R.drawable.donation)
)

val myBkashItems = listOf(
    MyBkashItem("My Offers", R.drawable.my_offers, "Tap to explore"),
    MyBkashItem("Vai (Bro)", R.drawable.mobile_recherge),
    MyBkashItem("bKash Bundle", R.drawable.bkash_bundle),
    MyBkashItem("Saved Bills", R.drawable.pay_bill)
)

val bundleItems = listOf(
    BundleItem("100 Send Money 30 Days", R.drawable.bkash_bundle_1, "৳249.0"),
    BundleItem("50 Send Money 30 Days", R.drawable.bkash_bundle_1, "৳149.0")
)

val suggestionItems = listOf(
    SuggestionItem("Quizmaster", R.drawable.quizmaster),
    SuggestionItem("QuizGiri", R.drawable.quizgiri),
    SuggestionItem("Medha", R.drawable.medha),
    SuggestionItem("Shikho", R.drawable.shikho)
)

val offerItems = listOf(
    OfferItem("Pac Rush", R.drawable.offers_1, "BDT 6,750 bonus"),
    OfferItem("Bowling", R.drawable.offers_2, "BDT 300 Discount"),
    OfferItem("ShareTrip", R.drawable.offers_1, "80% Disc")
)

val moreServicesList = listOf(
    ServiceItem("Ticket & Travel", R.drawable.tickets_and_travels, true),
    ServiceItem("Recharge Plan", R.drawable.recharge_plan, true),
    ServiceItem("BDTax", R.drawable.bdtax),
    ServiceItem("Insurance Plan", R.drawable.insurance_plan, true),
    ServiceItem("Games", R.drawable.games, true),
    ServiceItem("Subscriptions", R.drawable.subscriptions),
    ServiceItem("Deen", R.drawable.deen),
    ServiceItem("Learn Investing", R.drawable.learn_investing)
)
