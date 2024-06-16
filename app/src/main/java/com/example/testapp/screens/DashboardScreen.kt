package com.example.testapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testapp.components.Greeting
import com.example.testapp.components.SearchBar
import com.example.testapp.components.Topbar
import com.example.testapp.ui.theme.BabyBuyAppTheme

@Preview(showBackground = true, widthDp = 320, heightDp = 700)
@Composable
fun DashboardScreenPreview() {
    val listOfItems = listOf(
        mapOf("subtitle" to "BabyBuy", "title" to "Android App"),
        mapOf("subtitle" to "ChatApp", "title" to "FullStack App")
    )

    var presses by remember { mutableIntStateOf(0) }
    var searchInput by remember { mutableStateOf("") }

    BabyBuyAppTheme {
        Scaffold(topBar = {
            Topbar(color = MaterialTheme.colorScheme.primary)
        }, bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary
            ) {
                Text(
                    "Copyright@ 2024, Made by Linus Hahs.",
                    Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }, floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    SearchBar(searchInput, onChange = { value -> searchInput = value })

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (item in listOfItems) {
                            Greeting(
                                title = item["title"],
                                subtitle = item["subtitle"],
                                qty = presses
                            )
                        }
                    }
                }
            }
        }
    }

}
