package be.technifutur.projet_android.mockdata

import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.Platform

class MockGames {
    companion object {
        fun createGames(): MutableList<Game> {

            val gameList = mutableListOf<Game>()

            gameList.add(
                Game(
                    "Apex Legends",
                    Platform.PS4,
                    R.drawable.apex_legends
                )
            )
            gameList.add(
                Game(
                    "Fortnite",
                    Platform.XBOXONE,
                    R.drawable.fortnite
                )
            )
            gameList.add(
                Game(
                    "Overwatch",
                    Platform.SWITCH,
                    R.drawable.overwatch
                )
            )

            gameList.add(
                Game(
                    "The Last of Us: Part II",
                    Platform.PS4,
                    R.drawable.theltasofuspartii
                )
            )
            gameList.add(
                Game(
                    "Call of Duty",
                    Platform.XBOXONE,
                    R.drawable.callofduty
                )
            )

            gameList.add(
                Game(
                    "Slay the Spire",
                    Platform.PC,
                    R.drawable.slaythespire
                )
            )
            gameList.add(
                Game(
                    "Uno",
                    Platform.XBOXONE,
                    R.drawable.uno
                )
            )

            gameList.add(
                Game(
                    "Farming Simulator",
                    Platform.XBOXONE,
                    R.drawable.farmingsimulator
                )
            )
            gameList.add(
                Game(
                    "League of Legends",
                    Platform.PC,
                    R.drawable.league_of_legends
                )
            )

            return gameList
        }
    }
}