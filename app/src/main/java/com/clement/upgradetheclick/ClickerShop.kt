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
                onUpgradePerSecond1: (newPerSecondIncrement: Int, newCount: Int, newPerSecondCost1: Int) -> Unit,
                onUpgradePerSecond10: (newPerSecondIncrement: Int, newCount: Int, newPerSecondCost10: Int) -> Unit,
                onUpgradePerSecond100: (newPerSecondIncrement: Int, newCount: Int, newPerSecondCost100: Int) -> Unit,
                increment: Int, perSecondIncrement: Int, count: Int, upgradeCost : Int, perSecondCost1: Int, perSecondCost10: Int, perSecondCost100: Int,
                ClickerShopLevel: Int ) {
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
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        when (ClickerShopLevel) {
            0 -> {
                // Menu niveau 0
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Bouton Augmenter l'incrément
                    ShopButton("Augmenter incrément : $upgradeCost") {
                        if (count >= upgradeCost) {
                            onUpgrade(increment + 1, count - upgradeCost, upgradeCost + 1)
                        }
                    }
                    // Bouton Ajouter 1 par seconde
                    ShopButton("Augmenter l'incrément de 1 par seconde : $perSecondCost1") {
                        if (count >= perSecondCost1) {
                            onUpgradePerSecond1(perSecondIncrement + 1, count - perSecondCost1, perSecondCost1 + 5)
                        }
                    }
                    // Bouton Ajouter 10 par seconde
                    ShopButton("Augmenter l'incrément de 10 par secondes : $perSecondCost10") {
                        if (count >= perSecondCost10) {
                            onUpgradePerSecond10(perSecondIncrement + 10, count - perSecondCost10, perSecondCost10 + 50)
                        }
                    }
                    // Bouton Ajouter 100 par seconde
                    ShopButton("Augmenter l'incrément de 100 par secondes : $perSecondCost100") {
                        if (count >= perSecondCost100) {
                            onUpgradePerSecond100(perSecondIncrement + 100, count - perSecondCost100, perSecondCost100 + 500)
                        }
                    }
                }
            }
            1 -> {
                // Menu niveau 1
                Column(
                    modifier = ClickerShopModifier.fillMaxHeight()
                        .padding(top = 8.dp)
                        .padding(bottom = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Bouton Augmenter l'incrément
                        ShopButton("Augmenter incrément : $upgradeCost") {
                            if (count >= upgradeCost) {
                                onUpgrade(increment + 1, count - upgradeCost, upgradeCost + 1)
                            }
                        }
                        // Bouton Ajouter 1 par seconde
                        ShopButton("Augmenter l'incrément de 1 par seconde : $perSecondCost1") {
                            if (count >= perSecondCost1) {
                                onUpgradePerSecond1(perSecondIncrement + 1, count - perSecondCost1, perSecondCost1 + 5)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Bouton Ajouter 10 par seconde
                        ShopButton("Augmenter l'incrément de 10 par secondes : $perSecondCost10") {
                            if (count >= perSecondCost10) {
                                onUpgradePerSecond10(perSecondIncrement + 10, count - perSecondCost10, perSecondCost10 + 50)
                            }
                        }
                        // Bouton Ajouter 100 par seconde
                        ShopButton("Augmenter l'incrément de 100 par secondes : $perSecondCost100") {
                            if (count >= perSecondCost100) {
                                onUpgradePerSecond100(perSecondIncrement + 100, count - perSecondCost100, perSecondCost100 + 500)
                            }
                        }
                    }


                }
            }
            else ->
                // Menu niveau 2
                {
                    Column(
                    modifier = ClickerShopModifier.fillMaxHeight()
                        .padding(top = 8.dp)
                        .padding(bottom = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally)
                    {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Bouton Augmenter l'incrément
                        ShopButtonModern("Augmenter incrément : $upgradeCost") {
                            if (count >= upgradeCost) {
                                onUpgrade(increment + 1, count - upgradeCost, upgradeCost + 1)
                            }
                        }

                        // Bouton Ajouter 1 par seconde
                        ShopButtonModern("Ajout de 1 par seconde : $perSecondCost1") {
                            if (count >= perSecondCost1) {
                                onUpgradePerSecond1(perSecondIncrement + 1, count - perSecondCost1, perSecondCost1 + 5)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Bouton Ajouter 10 par seconde
                        ShopButtonModern("Ajout de 10 par secondes : $perSecondCost10") {
                            if (count >= perSecondCost10) {
                                onUpgradePerSecond10(perSecondIncrement + 10, count - perSecondCost10, perSecondCost10 + 50)
                            }
                        }
                        // Bouton Ajouter 100 par seconde
                        ShopButtonModern("Ajout de 100 par secondes : $perSecondCost100") {
                            if (count >= perSecondCost100) {
                                onUpgradePerSecond100(perSecondIncrement + 100, count - perSecondCost100, perSecondCost100 + 500)
                            }
                        }
                    }
                }
            }
        }
    }
}