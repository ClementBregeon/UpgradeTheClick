package com.clement.upgradetheclick

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable
fun ClickerShop(onUpgrade: (newIncrement: Int, newCount: Int, newupgradeCost: Int) -> Unit,
                increment: Int, count: Int, upgradeCost : Int, ClickerShopLevel: Int ) {
    val ClickerShopModifier = when (ClickerShopLevel) {
        0 -> Modifier
            .size(300.dp, 260.dp)
            .background(Color.White)
            .border(3.dp, Color.Black)
            .padding(16.dp)
        1 -> Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
        else -> Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    }
    Box(
        modifier =
        Modifier,
        contentAlignment = Alignment.Center
    ) {
        when (ClickerShopLevel) {
            0 -> {
                ShopButton("Augmenter incrément : $upgradeCost") {
                    if (count >= upgradeCost) {
                        onUpgrade(increment + 1, count - upgradeCost, upgradeCost + 1)
                    }
                }
            }
            1 -> {
                Column(
                    modifier = ClickerShopModifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShopButton("Augmenter incrément : $upgradeCost") {
                        if (count >= upgradeCost) {
                            onUpgrade(increment + 1, count - upgradeCost, upgradeCost + 1)
                        }
                    }
                }
            }
            else -> {Column(
                modifier = ClickerShopModifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ShopButtonModern("Augmenter incrément : $upgradeCost") {
                    if (count >= upgradeCost) {
                        onUpgrade(increment + 1, count - upgradeCost, upgradeCost + 1)
                    }
                }
            }

            }
        }
    }
}