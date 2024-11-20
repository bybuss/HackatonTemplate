package bob.colbaskin.hackatontemplate.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * @author bybuss
 */
enum class ProfileTab { Activity, Inventory, Achievements }

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(ProfileTab.Activity) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f),
            contentAlignment = Alignment.Center
        ) {
            ProfileHeader(onClick)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileTabButton(
                        "Активность",
                        selectedTab == ProfileTab.Activity
                    ) {
                        selectedTab = ProfileTab.Activity
                    }

                    ProfileTabButton(
                        "Инвентарь",
                        selectedTab == ProfileTab.Inventory
                    ) {
                        selectedTab = ProfileTab.Inventory
                    }

                    ProfileTabButton(
                        "Достижения",
                        selectedTab == ProfileTab.Achievements
                    ) {
                        selectedTab = ProfileTab.Achievements
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Black
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    when (selectedTab) {
                        ProfileTab.Activity -> ActivityContent()
                        ProfileTab.Inventory -> InventoryContent()
                        ProfileTab.Achievements -> AchievementsContent()
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Filled.Person,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = Color.Gray,
                    shape = RoundedCornerShape(50.dp)
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Иванчик Зольчик",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onClick() }
        )
        Text(
            text = "г. Ростов-на-Дону.",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Composable
fun ProfileTabButton(title: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        text = title,
        color = if (selected) Color.Black else Color.Gray,
        fontSize = 16.sp,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    )
}

@Composable
fun ActivityContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "ActivityContent",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun InventoryContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "InventoryContent",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AchievementsContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "AchievementsContent",
            textAlign = TextAlign.Center
        )
    }
}