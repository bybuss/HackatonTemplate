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
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * @author bybuss
 */
@Composable
fun ProfileScreen(
    onDetailedClick: () -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val selectedTab by viewModel.selectedTab.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f),
            contentAlignment = Alignment.Center
        ) {
            ProfileHeader(
                onDetailedClick = onDetailedClick,
                onAccountExitClick = { viewModel.accountExit() }
            )
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
                    ) { viewModel.updateSelectedTab(ProfileTab.Activity) }

                    ProfileTabButton(
                        "Инвентарь",
                        selectedTab == ProfileTab.Inventory
                    ) { viewModel.updateSelectedTab(ProfileTab.Inventory) }

                    ProfileTabButton(
                        "Достижения",
                        selectedTab == ProfileTab.Achievements
                    ) { viewModel.updateSelectedTab(ProfileTab.Achievements) }
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
fun ProfileHeader(
    onDetailedClick: () -> Unit,
    onAccountExitClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onAccountExitClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 8.dp, vertical = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Output,
                contentDescription = "Выход",
            )
        }

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
                modifier = Modifier.clickable { onDetailedClick() }
            )
            Text(
                text = "г. Ростов-на-Дону.",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onDetailedClick() }
            )
        }
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen({})
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader({}, {})
}

@Preview(showBackground = true)
@Composable
fun ActivityTabButtonPreview() {
    ProfileTabButton("Активность", true, {})
}
@Preview(showBackground = true)
@Composable
fun InventoryTabButtonPreview() {
    ProfileTabButton("Инвентарь", true, {})
}
@Preview(showBackground = true)
@Composable
fun AchievementsTabButtonPreview() {
    ProfileTabButton("Достижения", true, {})
}

@Preview(showBackground = true)
@Composable
fun ActivityContentPreview() {
    ActivityContent()
}

@Preview(showBackground = true)
@Composable
fun InventoryContentPreview() {
    InventoryContent()
}

@Preview(showBackground = true)
@Composable
fun AchievementsContentPreview() {
    AchievementsContent()
}