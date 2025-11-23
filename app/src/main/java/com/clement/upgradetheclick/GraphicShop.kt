package com.clement.upgradetheclick

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GraphicShop( count: Int, alignPurchased: Boolean, squareLevel: Int, GraphicShopLevel: Int, ClickerShopLevel: Int,
                 onAlignCenter:  (newCount: Int) -> Unit,
                 onUpgradeSquare: (newCount: Int, newLevel: Int) -> Unit,
                 onUpgradeGraphicShop: (newCount: Int, newGraphicShopLevel: Int) -> Unit,
                 onUpgradeClickerShop: (newCount: Int, newClickerShopLevel: Int) -> Unit) {
    val alignCost = 1000
    val upgradeCosts = listOf(5000, 10000, 15000)
    val GraphicShopCosts = listOf(2000, 5000)
    val ClickerShopCosts = listOf(1500, 4000)

    val GraphicShopModifier = when (GraphicShopLevel) {
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
            Modifier
                .fillMaxSize()
                .background(Color(0x88000000)),
            contentAlignment = Alignment.Center
    ) {
            when (GraphicShopLevel) {
            0 -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = GraphicShopModifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!alignPurchased) {
                        ShopButton("Réaligner : $alignCost") {
                            if (count >= alignCost) onAlignCenter(count - alignCost)
                        }
                    }
                    if (squareLevel < 3) {
                        val cost = upgradeCosts[squareLevel]
                        ShopButton("Améliorer carré : $cost") {
                            if (count >= cost) onUpgradeSquare(count - cost, squareLevel + 1)
                        }
                    } else {
                        Text("Carré max amélioré")
                    }
                    val GraphicShopCost = GraphicShopCosts.getOrNull(GraphicShopLevel)
                    if (GraphicShopCost != null) {
                        ShopButton("Améliorer menu graphique : $GraphicShopCost") {
                            if (count >= GraphicShopCost) onUpgradeGraphicShop(count - GraphicShopCost, GraphicShopLevel + 1)
                        }
                    }
                    val ClickerShopCost = ClickerShopCosts.getOrNull(ClickerShopLevel)
                    if (ClickerShopCost != null) {
                        ShopButton("Améliorer menu clicker : $ClickerShopCost") {
                            if (count >= ClickerShopCost) onUpgradeClickerShop(count - ClickerShopCost, ClickerShopLevel + 1)
                        }
                    }
                }
            }
            1 -> {
                Column(
                    modifier = GraphicShopModifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        if (!alignPurchased) {
                            ShopButton("Réaligner : $alignCost") {
                                if (count >= alignCost) onAlignCenter(count - alignCost)
                        }
                            }

                        if (squareLevel < 3) {
                            val cost = upgradeCosts[squareLevel]
                            ShopButton("Améliorer carré : $cost") {
                                if (count >= cost) onUpgradeSquare(count - cost, squareLevel + 1)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        val GraphicShopCost = GraphicShopCosts.getOrNull(GraphicShopLevel)
                        if (GraphicShopCost != null) {
                            ShopButton("Améliorer menu graphique : $GraphicShopCost") {
                                if (count >= GraphicShopCost) onUpgradeGraphicShop(count - GraphicShopCost, GraphicShopLevel + 1)
                            }
                        }


                        val ClickerShopCost = ClickerShopCosts.getOrNull(ClickerShopLevel)
                        if (ClickerShopCost != null) {
                            ShopButton("Améliorer menu clicker : $ClickerShopCost") {
                                if (count >= ClickerShopCost) onUpgradeClickerShop(count - ClickerShopCost, ClickerShopLevel + 1)
                            }
                        }

                    }
                }
            }
            else -> {
                Column(
                    modifier = GraphicShopModifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        if (!alignPurchased) {
                            ShopButtonModern("Réaligner : $alignCost") {
                                if (count >= alignCost) onAlignCenter(count - alignCost)
                            }
                        }
                        if (squareLevel < 3) {
                            val cost = upgradeCosts[squareLevel]
                            ShopButtonModern("Améliorer carré : $cost") {
                                if (count >= cost) onUpgradeSquare(count - cost, squareLevel + 1)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        val GraphicShopCost = GraphicShopCosts.getOrNull(GraphicShopLevel)
                        if (GraphicShopCost != null) {
                            ShopButtonModern("Améliorer menu graphique : $GraphicShopCost") {
                                if (count >= GraphicShopCost) onUpgradeGraphicShop(count - GraphicShopCost, GraphicShopLevel + 1)
                            }
                        }

                        val ClickerShopCost = ClickerShopCosts.getOrNull(ClickerShopLevel)
                        if (ClickerShopCost != null) {
                            ShopButtonModern("Améliorer menu clicker : $ClickerShopCost") {
                                if (count >= ClickerShopCost) onUpgradeClickerShop(count - ClickerShopCost, ClickerShopLevel + 1)
                            }
                        }
                    }
                }
            }
        }
    }

}


