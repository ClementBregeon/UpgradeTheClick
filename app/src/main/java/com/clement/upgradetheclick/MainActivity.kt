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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect

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
    var perSecondIncrement by remember { mutableIntStateOf(0) }
    var perSecondCost1 by remember { mutableIntStateOf(50) }
    var perSecondCost10 by remember { mutableIntStateOf(500) }
    var perSecondCost100 by remember { mutableIntStateOf(5000) }
    var alignedCenter by remember { mutableStateOf(false) }
    var alignPurchased by remember { mutableStateOf(false) }
    var squareLevel by remember { mutableStateOf(0) }
    var GraphicShopLevel by remember { mutableStateOf(0) }
    var ClickerShopLevel by remember { mutableStateOf(0) }

    LaunchedEffect(perSecondIncrement) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            count += perSecondIncrement
        }
    }
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
                    .clickable { ClickerShopOpen = !ClickerShopOpen
                               GraphicShopOpen = false},
                contentAlignment = Alignment.Center
            ) {
                Text("Shop Clicker")
            }

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp)
                    .border(2.dp, Color.Black)
                    .clickable { GraphicShopOpen = !GraphicShopOpen
                        ClickerShopOpen = false},
                contentAlignment = Alignment.Center
            ) {
                Text("Shop Graphique")
            }

            if (ClickerShopOpen) {
                ClickerShop(
                    increment = increment,
                    count = count,
                    upgradeCost = upgradeCost,
                    ClickerShopLevel = ClickerShopLevel,
                    perSecondIncrement = perSecondIncrement,
                    perSecondCost1 = perSecondCost1,
                    perSecondCost10 = perSecondCost10,
                    perSecondCost100 = perSecondCost100,
                    onUpgrade = { newIncrement, newCount, newUpgradeCost ->
                        increment = newIncrement
                        count = newCount
                        upgradeCost = newUpgradeCost
                    },
                    onUpgradePerSecond1 = { newPerSecondIncrement, newCount, newPerSecondCost1 ->
                        perSecondIncrement = newPerSecondIncrement
                        count = newCount
                        perSecondCost1 = newPerSecondCost1
                    },
                    onUpgradePerSecond10 = { newPerSecondIncrement, newCount, newPerSecondCost10 ->
                        perSecondIncrement = newPerSecondIncrement
                        count = newCount
                        perSecondCost10 = newPerSecondCost10
                    },
                    onUpgradePerSecond100 = { newPerSecondIncrement, newCount, newPerSecondCost100 ->
                        perSecondIncrement = newPerSecondIncrement
                        count = newCount
                        perSecondCost100 = newPerSecondCost100
                    }
                )
            }

            if (GraphicShopOpen) {
                GraphicShop(
                    count = count,
                    alignPurchased = alignPurchased,
                    squareLevel = squareLevel,
                    GraphicShopLevel = GraphicShopLevel,
                    ClickerShopLevel = ClickerShopLevel,
                    onAlignCenter = { newCount ->
                        alignedCenter = true
                        count = newCount
                        alignPurchased = true
                    },
                    onUpgradeSquare = { newCount, newLevel ->
                        count = newCount
                        squareLevel = newLevel
                    } ,
                    onUpgradeGraphicShop = { newCount, newGraphicShopLevel ->
                        count = newCount
                        GraphicShopLevel = newGraphicShopLevel
                    },
                    onUpgradeClickerShop = { newCount, newClickerShopLevel ->
                        count = newCount
                        ClickerShopLevel = newClickerShopLevel
                    })
            }
        }
    }

}


@Composable
fun ShopButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 180.dp, height = 50.dp)
            .border(2.dp, Color.Black)
            .background(Color.LightGray)
            .clickable{onClick()},
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}

@Composable
fun ShopButtonModern(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 180.dp, height = 50.dp)
            .background(color = Color(0xFFEFEFEF), shape = RoundedCornerShape(12.dp)) // gris très clair et coins arrondis
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.DarkGray)
    }
}