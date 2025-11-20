package com.clement.upgradetheclick

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ClickerShop(onUpgrade: (newIncrement: Int, newCount: Int, newupgradeCost: Int) -> Unit, increment: Int, count: Int, upgradeCost : Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x88000000)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(300.dp, 200.dp)
                .background(Color.White)
                .border(3.dp, Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 150.dp, height = 50.dp)
                    .border(2.dp, Color.Black)
                    .background(Color.LightGray)
                    .clickable {
                        if (count >= upgradeCost) {
                            onUpgrade(increment + 1, count - upgradeCost, upgradeCost + 1)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Augmenter incrément")


            }
            Spacer(modifier = Modifier.padding(start = 16.dp))

            Text("Incrément: $increment", fontSize = 18.sp)

            Text("Prix: $upgradeCost", fontSize = 18.sp)
        }
    }
}