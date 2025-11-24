package com.clement.upgradetheclick

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    companion object {
        const val PREFS_NAME = "clicker_prefs"
        const val KEY_COUNT = "count"
        const val KEY_CPS = "perSecondIncrement"
        const val KEY_INCREMENT = "increment"
        const val KEY_ALIGN_PURCHASED = "alignPurchased"
        const val KEY_SQUARE_LEVEL = "squareLevel"
        const val KEY_GRAPHIC_SHOP_LEVEL = "GraphicShopLevel"
        const val KEY_CLICKER_SHOP_LEVEL = "ClickerShopLevel"
        const val KEY_SHOP_BUTTONS_UPGRADED = "shopButtonsUpgraded"
        const val KEY_COUNTER_LEVEL = "counterLevel"
        const val KEY_UPGRADE_COST = "upgradeCost"
        const val KEY_PER_SECOND_COST1 = "perSecondCost1"
        const val KEY_PER_SECOND_COST10 = "perSecondCost10"
        const val KEY_PER_SECOND_COST100 = "perSecondCost100"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            ClickerGame(prefs)
        }
    }
}


@Composable
fun ClickerGame(prefs: android.content.SharedPreferences) {
    var count by remember { mutableIntStateOf(prefs.getInt(MainActivity.KEY_COUNT, 1000000)) }
    var increment by remember { mutableIntStateOf(prefs.getInt(MainActivity.KEY_INCREMENT, 1)) }
    var upgradeCost by remember { mutableIntStateOf(prefs.getInt(MainActivity.KEY_UPGRADE_COST, 10)) }
    var perSecondIncrement by remember { mutableIntStateOf(prefs.getInt(MainActivity.KEY_CPS, 0)) }
    var perSecondCost1 by remember { mutableIntStateOf(prefs.getInt(MainActivity.KEY_PER_SECOND_COST1, 50)) }
    var perSecondCost10 by remember { mutableIntStateOf(prefs.getInt(MainActivity.KEY_PER_SECOND_COST10, 500)) }
    var perSecondCost100 by remember { mutableIntStateOf(prefs.getInt(MainActivity.KEY_PER_SECOND_COST100, 5000)) }
    var alignedCenter by remember { mutableStateOf(prefs.getBoolean(MainActivity.KEY_ALIGN_PURCHASED, false)) }
    var alignPurchased by remember { mutableStateOf(prefs.getBoolean(MainActivity.KEY_ALIGN_PURCHASED, false)) }
    var squareLevel by remember { mutableStateOf(prefs.getInt(MainActivity.KEY_SQUARE_LEVEL, 0)) }
    var GraphicShopLevel by remember { mutableStateOf(prefs.getInt(MainActivity.KEY_GRAPHIC_SHOP_LEVEL, 0)) }
    var ClickerShopLevel by remember { mutableStateOf(prefs.getInt(MainActivity.KEY_CLICKER_SHOP_LEVEL, 0)) }
    var shopButtonsUpgraded by remember { mutableStateOf(prefs.getBoolean(MainActivity.KEY_SHOP_BUTTONS_UPGRADED, false)) }
    var counterLevel by remember { mutableStateOf(prefs.getInt(MainActivity.KEY_COUNTER_LEVEL, 0)) }

    val displayedCount = remember { Animatable(count.toFloat()) }

    var ClickerShopOpen by remember { mutableStateOf(false) }
    var GraphicShopOpen by remember { mutableStateOf(false) }

    LaunchedEffect(perSecondIncrement, counterLevel) {
        while (true) {
            if (counterLevel >= 1) {
                val tickGain = perSecondIncrement / 10f
                count += tickGain.toInt()
                displayedCount.snapTo(displayedCount.value + tickGain)
                delay(100L)
                prefs.edit().putInt(MainActivity.KEY_COUNT, count).apply()
            } else {
                delay(1000L)
                count += perSecondIncrement
                prefs.edit().putInt(MainActivity.KEY_COUNT, count).apply()
            }
        }
    }

    LaunchedEffect(count, counterLevel) {
        if (counterLevel == 0) {
            displayedCount.animateTo(
                targetValue = count.toFloat(),
                animationSpec = tween(150)
            )
        } else {
            displayedCount.snapTo(count.toFloat())
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = if (alignedCenter) Alignment.CenterHorizontally else Alignment.Start,
            modifier = Modifier.fillMaxWidth()
                .padding(top = 50.dp)

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = displayedCount.value.toInt().toString(),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (counterLevel >= 2) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(${perSecondIncrement} cps)",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
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

            if (shopButtonsUpgraded) {
                ShopButtonModern("Shop Clicker") {
                    ClickerShopOpen = !ClickerShopOpen
                    GraphicShopOpen = false
                }
                ShopButtonModern("Shop Graphique") {
                    GraphicShopOpen = !GraphicShopOpen
                    ClickerShopOpen = false
                }
            } else {
                ShopButton("Shop Clicker") {
                    ClickerShopOpen = !ClickerShopOpen
                    GraphicShopOpen = false
                }
                ShopButton("Shop Graphique") {
                    GraphicShopOpen = !GraphicShopOpen
                    ClickerShopOpen = false
                }
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

                        prefs.edit()
                            .putInt(MainActivity.KEY_INCREMENT, newIncrement)
                            .putInt(MainActivity.KEY_UPGRADE_COST, newUpgradeCost)
                            .apply()
                    },
                    onUpgradePerSecond1 = { newPerSecondIncrement, newCount, newPerSecondCost1 ->
                        perSecondIncrement = newPerSecondIncrement
                        count = newCount
                        perSecondCost1 = newPerSecondCost1

                        prefs.edit()
                            .putInt(MainActivity.KEY_CPS, newPerSecondIncrement)
                            .putInt(MainActivity.KEY_PER_SECOND_COST1, newPerSecondCost1)
                            .apply()
                    },
                    onUpgradePerSecond10 = { newPerSecondIncrement, newCount, newPerSecondCost10 ->
                        perSecondIncrement = newPerSecondIncrement
                        count = newCount
                        perSecondCost10 = newPerSecondCost10

                        prefs.edit()
                            .putInt(MainActivity.KEY_CPS, newPerSecondIncrement)
                            .putInt(MainActivity.KEY_PER_SECOND_COST10, newPerSecondCost10)
                            .apply()
                    },
                    onUpgradePerSecond100 = { newPerSecondIncrement, newCount, newPerSecondCost100 ->
                        perSecondIncrement = newPerSecondIncrement
                        count = newCount
                        perSecondCost100 = newPerSecondCost100

                        prefs.edit()
                            .putInt(MainActivity.KEY_CPS, newPerSecondIncrement)
                            .putInt(MainActivity.KEY_PER_SECOND_COST100, newPerSecondCost100)
                            .apply()

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
                    shopButtonsUpgraded = shopButtonsUpgraded,
                    counterLevel = counterLevel,
                    onAlignCenter = { newCount ->
                        alignedCenter = true
                        count = newCount
                        alignPurchased = true
                        prefs.edit()
                            .putBoolean(MainActivity.KEY_ALIGN_PURCHASED, true)
                            .apply()
                    },
                    onUpgradeSquare = { newCount, newLevel ->
                        count = newCount
                        squareLevel = newLevel
                        prefs.edit()
                            .putInt(MainActivity.KEY_SQUARE_LEVEL, newLevel)
                            .apply()
                    } ,
                    onUpgradeGraphicShop = { newCount, newGraphicShopLevel ->
                        count = newCount
                        GraphicShopLevel = newGraphicShopLevel
                        prefs.edit()
                            .putInt(MainActivity.KEY_GRAPHIC_SHOP_LEVEL, newGraphicShopLevel)
                            .apply()
                    },
                    onUpgradeClickerShop = { newCount, newClickerShopLevel ->
                        count = newCount
                        ClickerShopLevel = newClickerShopLevel
                        prefs.edit()
                            .putInt(MainActivity.KEY_CLICKER_SHOP_LEVEL, newClickerShopLevel)
                            .apply()
                    },
                    onUpgradeShopButtons = { newCount ->
                        count = newCount
                        shopButtonsUpgraded = true
                        prefs.edit()
                            .putBoolean(MainActivity.KEY_SHOP_BUTTONS_UPGRADED, true)
                            .apply()
                    },
                    onUpgradeCounter = { newCount, newCounterLevel ->
                        count = newCount
                        counterLevel = newCounterLevel
                        prefs.edit()
                            .putInt(MainActivity.KEY_COUNTER_LEVEL, newCounterLevel)
                            .apply()
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