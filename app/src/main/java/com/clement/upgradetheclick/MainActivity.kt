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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    //Nom des valeurs sauvegardées
    companion object {
        const val PREFS_NAME = "clicker_prefs"
        const val KEY_COUNT = "count"
        const val KEY_CPS = "perSecondIncrement"
        const val KEY_INCREMENT = "increment"
        const val KEY_ALIGN_PURCHASED = "alignPurchased"
        const val KEY_SQUARE_LEVEL = "squareLevel"
        const val KEY_GRAPHIC_SHOP_LEVEL = "GraphicShopLevel"
        const val KEY_CLICKER_SHOP_LEVEL = "ClickerShopLevel"
        const val KEY_SHOP_BUTTONS_UPGRADED = "mainButtonsUpgraded"
        const val KEY_COUNTER_LEVEL = "counterLevel"
        const val KEY_UPGRADE_COST = "upgradeCost"
        const val KEY_PER_SECOND_COST1 = "perSecondCost1"
        const val KEY_PER_SECOND_COST10 = "perSecondCost10"
        const val KEY_PER_SECOND_COST100 = "perSecondCost100"
    }
    // Chargement des valeurs sauvegardées et lancement de l'app
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
    //Mise des valeurs sauvegardées dans les variables en mémoire
    var count by remember { mutableIntStateOf(prefs.getInt(MainActivity.KEY_COUNT, 0)) }
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
    var mainButtonsUpgraded by remember { mutableStateOf(prefs.getBoolean(MainActivity.KEY_SHOP_BUTTONS_UPGRADED, false)) }
    var counterLevel by remember { mutableStateOf(prefs.getInt(MainActivity.KEY_COUNTER_LEVEL, 0)) }

    val displayedCount = remember { Animatable(count.toFloat()) }

    var ClickerShopOpen by remember { mutableStateOf(false) }
    var GraphicShopOpen by remember { mutableStateOf(false) }

    //Compteur qui s'actualise en contenu
    LaunchedEffect(perSecondIncrement, counterLevel) {
        while (true) {
            if (counterLevel >= 1) {
                //Compteur fluide
                val tickGain = perSecondIncrement / 10f
                count += tickGain.toInt()
                displayedCount.snapTo(displayedCount.value + tickGain)
                delay(100L)
                prefs.edit().putInt(MainActivity.KEY_COUNT, count).apply()
            } else {
                //Compteur pas fluide
                delay(1000L)
                count += perSecondIncrement
                prefs.edit().putInt(MainActivity.KEY_COUNT, count).apply()
            }
        }
    }
    //Rend le compteur plus fluide
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
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
            .padding(top = 16.dp)
            .padding(bottom = 16.dp)
    ) {

        if (mainButtonsUpgraded) {
            ShopButtonModern("Reset") {
                // Remise à zéro de toutes les valeurs
                count = 0
                increment = 1
                upgradeCost = 10
                perSecondIncrement = 0
                perSecondCost1 = 50
                perSecondCost10 = 500
                perSecondCost100 = 5000
                alignedCenter = false
                alignPurchased = false
                squareLevel = 0
                GraphicShopLevel = 0
                ClickerShopLevel = 0
                mainButtonsUpgraded = false
                counterLevel = 0

                // Sauvegarde
                prefs.edit()
                    .putInt(MainActivity.KEY_COUNT, count)
                    .putInt(MainActivity.KEY_INCREMENT, increment)
                    .putInt(MainActivity.KEY_UPGRADE_COST, upgradeCost)
                    .putInt(MainActivity.KEY_CPS, perSecondIncrement)
                    .putInt(MainActivity.KEY_PER_SECOND_COST1, perSecondCost1)
                    .putInt(MainActivity.KEY_PER_SECOND_COST10, perSecondCost10)
                    .putInt(MainActivity.KEY_PER_SECOND_COST100, perSecondCost100)
                    .putBoolean(MainActivity.KEY_ALIGN_PURCHASED, alignPurchased)
                    .putInt(MainActivity.KEY_SQUARE_LEVEL, squareLevel)
                    .putInt(MainActivity.KEY_GRAPHIC_SHOP_LEVEL, GraphicShopLevel)
                    .putInt(MainActivity.KEY_CLICKER_SHOP_LEVEL, ClickerShopLevel)
                    .putBoolean(MainActivity.KEY_SHOP_BUTTONS_UPGRADED, mainButtonsUpgraded)
                    .putInt(MainActivity.KEY_COUNTER_LEVEL, counterLevel)
                    .apply()
            }
        } else {
            ShopButton("Reset") {
                // Remise à zéro de toutes les valeurs
                count = 0
                increment = 1
                upgradeCost = 10
                perSecondIncrement = 0
                perSecondCost1 = 50
                perSecondCost10 = 500
                perSecondCost100 = 5000
                alignedCenter = false
                alignPurchased = false
                squareLevel = 0
                GraphicShopLevel = 0
                ClickerShopLevel = 0
                mainButtonsUpgraded = false
                counterLevel = 0

                // Sauvegarde
                prefs.edit()
                    .putInt(MainActivity.KEY_COUNT, count)
                    .putInt(MainActivity.KEY_INCREMENT, increment)
                    .putInt(MainActivity.KEY_UPGRADE_COST, upgradeCost)
                    .putInt(MainActivity.KEY_CPS, perSecondIncrement)
                    .putInt(MainActivity.KEY_PER_SECOND_COST1, perSecondCost1)
                    .putInt(MainActivity.KEY_PER_SECOND_COST10, perSecondCost10)
                    .putInt(MainActivity.KEY_PER_SECOND_COST100, perSecondCost100)
                    .putBoolean(MainActivity.KEY_ALIGN_PURCHASED, alignPurchased)
                    .putInt(MainActivity.KEY_SQUARE_LEVEL, squareLevel)
                    .putInt(MainActivity.KEY_GRAPHIC_SHOP_LEVEL, GraphicShopLevel)
                    .putInt(MainActivity.KEY_CLICKER_SHOP_LEVEL, ClickerShopLevel)
                    .putBoolean(MainActivity.KEY_SHOP_BUTTONS_UPGRADED, mainButtonsUpgraded)
                    .putInt(MainActivity.KEY_COUNTER_LEVEL, counterLevel)
                    .apply()
            }
        }



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
                //Affichage des Cps
                if (counterLevel >= 2) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(${perSecondIncrement} Cps)",
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
                // Affichage du carré à cliquer
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

            // Boutons des shops
            if (mainButtonsUpgraded) {
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
            //
            if (ClickerShopOpen) {
                ClickerShop(
                    increment = increment,                      // Valeur ajoutée par clic
                    count = count,                              // Nombre de cookies
                    upgradeCost = upgradeCost,                  // Prix d'amélioration du clic
                    ClickerShopLevel = ClickerShopLevel,        // Niveau graphique du shop
                    perSecondIncrement = perSecondIncrement,    // Nombre de Cps
                    perSecondCost1 = perSecondCost1,            // Prix de l'amélioration de +1 Cps
                    perSecondCost10 = perSecondCost10,          // Prix de l'amélioration de +10 Cps
                    perSecondCost100 = perSecondCost100,        // Prix de l'amélioration de +100 Cps

                    // On améliore le clic
                    onUpgrade = { newIncrement, newCount, newUpgradeCost ->
                        increment = newIncrement
                        count = newCount
                        upgradeCost = newUpgradeCost

                        prefs.edit()
                            .putInt(MainActivity.KEY_INCREMENT, newIncrement)
                            .putInt(MainActivity.KEY_UPGRADE_COST, newUpgradeCost)
                            .apply()
                    },

                    // On améliore de 1 le Cps
                    onUpgradePerSecond1 = { newPerSecondIncrement, newCount, newPerSecondCost1 ->
                        perSecondIncrement = newPerSecondIncrement
                        count = newCount
                        perSecondCost1 = newPerSecondCost1

                        prefs.edit()
                            .putInt(MainActivity.KEY_CPS, newPerSecondIncrement)
                            .putInt(MainActivity.KEY_PER_SECOND_COST1, newPerSecondCost1)
                            .apply()
                    },

                    // On améliore de 10 le Cps
                    onUpgradePerSecond10 = { newPerSecondIncrement, newCount, newPerSecondCost10 ->
                        perSecondIncrement = newPerSecondIncrement
                        count = newCount
                        perSecondCost10 = newPerSecondCost10

                        prefs.edit()
                            .putInt(MainActivity.KEY_CPS, newPerSecondIncrement)
                            .putInt(MainActivity.KEY_PER_SECOND_COST10, newPerSecondCost10)
                            .apply()
                    },

                    // On améliore de 100 le Cps
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
                    count = count,                                  // Nombre de cookies
                    alignPurchased = alignPurchased,                // Alignement acheté ?
                    squareLevel = squareLevel,                      // Niveau du carré/cookie cliquable
                    GraphicShopLevel = GraphicShopLevel,            // Niveau du shop graphique
                    ClickerShopLevel = ClickerShopLevel,            // Niveau du shop clicker
                    mainButtonsUpgraded = mainButtonsUpgraded,      // Niveau des boutons principaux
                    counterLevel = counterLevel,                    // Niveau du compteur

                    // On met au centre les éléments principaux
                    onAlignCenter = { newCount ->
                        alignedCenter = true
                        count = newCount
                        alignPurchased = true
                        prefs.edit()
                            .putBoolean(MainActivity.KEY_ALIGN_PURCHASED, true)
                            .apply()
                    },

                    // On améliore le carré/cookie cliquable
                    onUpgradeSquare = { newCount, newLevel ->
                        count = newCount
                        squareLevel = newLevel
                        prefs.edit()
                            .putInt(MainActivity.KEY_SQUARE_LEVEL, newLevel)
                            .apply()
                    } ,

                    // On améliore le shop graphique
                    onUpgradeGraphicShop = { newCount, newGraphicShopLevel ->
                        count = newCount
                        GraphicShopLevel = newGraphicShopLevel
                        prefs.edit()
                            .putInt(MainActivity.KEY_GRAPHIC_SHOP_LEVEL, newGraphicShopLevel)
                            .apply()
                    },

                    // On améliore le shop clicker
                    onUpgradeClickerShop = { newCount, newClickerShopLevel ->
                        count = newCount
                        ClickerShopLevel = newClickerShopLevel
                        prefs.edit()
                             .putInt(MainActivity.KEY_CLICKER_SHOP_LEVEL, newClickerShopLevel)
                            .apply()
                    },

                    // On améliore les boutons principaux
                    onUpgradeShopButtons = { newCount ->
                        count = newCount
                        mainButtonsUpgraded = true
                        prefs.edit()
                            .putBoolean(MainActivity.KEY_SHOP_BUTTONS_UPGRADED, true)
                            .apply()
                    },

                    // On améliore le compteur
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
// Bouton moins joli
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
// Bouton plus joli
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