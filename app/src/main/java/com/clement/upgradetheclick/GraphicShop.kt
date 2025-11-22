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

@Composable
fun GraphicShop( count: Int, alignPurchased: Boolean, squareLevel: Int, GraphicMenuLevel: Int,
                 onAlignCenter:  (newCount: Int) -> Unit,
                 onUpgradeSquare: (newCount: Int, newLevel: Int) -> Unit,
                 onUpgradeMenu: (newCount: Int, newMenuLevel: Int) -> Unit) {
    val alignCost = 1000
    val upgradeCosts = listOf(5000, 10000, 15000)
    val GraphicMenuCosts = listOf(2000, 5000)

    val GraphicMenuModifier = when (GraphicMenuLevel) {
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
            .background(Color(0xFFEFEFEF))
            .padding(16.dp)
    }
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color(0x88000000)),
            contentAlignment = Alignment.Center
    ) {
        when (GraphicMenuLevel) {
            0 -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = GraphicMenuModifier,
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
                    val graphicMenuCost = GraphicMenuCosts[GraphicMenuLevel]
                    ShopButton("Améliorer menu : $graphicMenuCost") {
                        if (count >= graphicMenuCost) onUpgradeMenu(count - graphicMenuCost, GraphicMenuLevel + 1)
                    }
                }
            }
            1 -> {
                Column(
                    modifier = GraphicMenuModifier.fillMaxHeight(),
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
                        val graphicMenuCost = GraphicMenuCosts[GraphicMenuLevel]
                        ShopButton("Améliorer menu : $graphicMenuCost") {
                            if (count >= graphicMenuCost) onUpgradeMenu(count - graphicMenuCost, GraphicMenuLevel + 1)
                        }
                    }
                }
            }
            else -> {
                Column(
                    modifier = GraphicMenuModifier.fillMaxHeight(),
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
                        val graphicMenuCost = GraphicMenuCosts.getOrNull(GraphicMenuLevel)
                        if (graphicMenuCost != null) {
                            ShopButtonModern("Améliorer menu : $graphicMenuCost") {
                                if (count >= graphicMenuCost) onUpgradeMenu(graphicMenuCost, GraphicMenuLevel + 1)
                            }
                        }
                    }
                }
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
            .border(1.dp, Color.Gray)
            .background(Color.White)
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.DarkGray)
    }
}