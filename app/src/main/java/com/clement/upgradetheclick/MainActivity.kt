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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image

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
    var count by remember { mutableIntStateOf(100000) }
    var increment by remember { mutableIntStateOf(1) }
    var ClickerShopOpen by remember { mutableStateOf(false) }
    var GraphicShopOpen by remember { mutableStateOf(false) }
    var upgradeCost by remember { mutableIntStateOf(10) }
    var alignedCenter by remember { mutableStateOf(false) }
    var alignPurchased by remember { mutableStateOf(false) }
    var squareLevel by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = if (alignedCenter) Alignment.CenterHorizontally else Alignment.Start,
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
                    .clickable { count += increment },
                contentAlignment = Alignment.Center
            ) {
                when (squareLevel) {
                    0 -> Box(modifier = Modifier.fillMaxSize().border(3.dp, Color.Black).background(Color.White))
                    1 -> Box(modifier = Modifier.fillMaxSize().border(3.dp, Color.Black).background(Color.Yellow))
                    2 -> Image(
                        painter = painterResource(R.drawable.pixel_cookie),
                        contentDescription = "Pixel Cookie",
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> Image(
                        painter = painterResource(R.drawable.real_cookie),
                        contentDescription = "Real Cookie",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.padding(top = 30.dp))

            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp)
                    .border(2.dp, Color.Black)
                    .clickable { ClickerShopOpen = !ClickerShopOpen },
                contentAlignment = Alignment.Center
            ) {
                Text("Bouton 1")
            }

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp)
                    .border(2.dp, Color.Black)
                    .clickable { GraphicShopOpen = !GraphicShopOpen },
                contentAlignment = Alignment.Center
            ) {
                Text("Bouton 2")
            }

            if (ClickerShopOpen) {
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

            if (GraphicShopOpen) {
                GraphicShop(
                    count = count,
                    alignPurchased = alignPurchased,
                    squareLevel = squareLevel,
                    onAlignCenter = { newCount ->
                        alignedCenter = true
                        count = newCount
                        alignPurchased = true
                    },
                    onUpgradeSquare = { newCount, newLevel ->
                        count = newCount
                        squareLevel = newLevel
                    })
            }
        }
    }

}
