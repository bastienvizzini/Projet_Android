package be.technifutur.projet_android.mockdata

import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.MyGame
import be.technifutur.projet_android.models.Platform

class MockGames {
    companion object {
        fun createGames(): MutableList<MyGame> {

            val gameList = mutableListOf<MyGame>()

            gameList.add(
                MyGame(
                    "Apex Legends",
                    Platform.PS4,
                    R.drawable.apex_legends
                )
            )
            gameList.add(
                MyGame(
                    "Fortnite",
                    Platform.XBOXONE,
                    R.drawable.fortnite
                )
            )
            gameList.add(
                MyGame(
                    "Overwatch",
                    Platform.SWITCH,
                    R.drawable.overwatch
                )
            )

            gameList.add(
                MyGame(
                    "The Last of Us: Part II",
                    Platform.PS4,
                    R.drawable.theltasofuspartii
                )
            )
            gameList.add(
                MyGame(
                    "Call of Duty",
                    Platform.XBOXONE,
                    R.drawable.callofduty
                )
            )

            gameList.add(
                MyGame(
                    "Slay the Spire",
                    Platform.PC,
                    R.drawable.slaythespire
                )
            )
            gameList.add(
                MyGame(
                    "Uno",
                    Platform.XBOXONE,
                    R.drawable.uno
                )
            )

            gameList.add(
                MyGame(
                    "Farming Simulator",
                    Platform.XBOXONE,
                    R.drawable.farmingsimulator
                )
            )
            gameList.add(
                MyGame(
                    "League of Legends",
                    Platform.PC,
                    R.drawable.league_of_legends
                )
            )

            return gameList
        }
    }
}