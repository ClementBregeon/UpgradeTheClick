package com.clement.upgradetheclick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickerGame()
        }
    }
}

@Composable
fun ClickerGame() {
    var count by remember { mutableStateOf(0) }
    var increment by remember { mutableStateOf(1) }
    var shopOpen by remember { mutableStateOf(false) }
    var upgradeCost by remember { mutableStateOf(10) }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(top = 50.dp)

        ) {
            Text(
                text = "$count",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .border(3.dp, Color.Black)
                    .background(Color.White)
                    .clickable { count = count + increment },
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))

            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp)
                    .border(2.dp, Color.Black)
                    .clickable { shopOpen = !shopOpen },
                contentAlignment = Alignment.Center
            ) {
                Text("Bouton 1")
            }

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp)
                    .border(2.dp, Color.Black)
                    .clickable { /* Action bouton 2 */ },
                contentAlignment = Alignment.Center
            ) {
                Text("Bouton 2")
            }

            if (shopOpen) {
                ClickerShop(
                    increment = increment,
                    count = count,
                    upgradeCost = upgradeCost,
                    onUpgrade = { newIncrement, newCount, newupgradeCost ->
                        increment = newIncrement
                        count = newCount
                        upgradeCost = newupgradeCost
                    }
                )
            }
        }
    }

}
