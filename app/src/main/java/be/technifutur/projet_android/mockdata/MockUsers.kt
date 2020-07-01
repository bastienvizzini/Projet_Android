package be.technifutur.projet_android.mockdata

import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.Platform
import be.technifutur.projet_android.models.User

class MockUsers {
    companion object {
        fun createUsers(): MutableList<User> {

            val gameList: MutableList<Game> = mutableListOf()
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

            val gameList2: MutableList<Game> = mutableListOf()
            gameList2.add(
                Game(
                    "The Last of Us: Part II",
                    Platform.PS4,
                    R.drawable.theltasofuspartii
                )
            )
            gameList2.add(
                Game(
                    "Call of Duty",
                    Platform.XBOXONE,
                    R.drawable.callofduty
                )
            )

            val gameList3: MutableList<Game> = mutableListOf()
            gameList3.add(
                Game(
                    "Slay the Spire",
                    Platform.PC,
                    R.drawable.slaythespire
                )
            )
            gameList3.add(
                Game(
                    "Uno",
                    Platform.XBOXONE,
                    R.drawable.uno
                )
            )

            val gameList4: MutableList<Game> = mutableListOf()
            gameList4.add(
                Game(
                    "Farming Simulator",
                    Platform.XBOXONE,
                    R.drawable.farmingsimulator
                )
            )
            gameList4.add(
                Game(
                    "League of Legends",
                    Platform.PC,
                    R.drawable.league_of_legends
                )
            )

            val users: MutableList<User> = mutableListOf()
            val user1 = User(
                "JeanClaude24",
                24,
                "Liège",
                R.drawable.user1,
                gameList,
                false
            )
            val user2 = User(
                "TechnoKiller",
                28,
                "Liège",
                R.drawable.user2,
                gameList2,
                true
            )
            val user3 = User(
                "Didier Raoult",
                87,
                "Marseille",
                R.drawable.user3,
                gameList3,
                false
            )
            val user4 = User(
                "Corona",
                16,
                "Wuhan",
                R.drawable.user4,
                gameList4,
                true
            )
            val user5 = User(
                "PatrickBalkaneux",
                67,
                "Levallois",
                R.drawable.user5,
                gameList,
                false
            )
            val user6 = User(
                "XHipster",
                25,
                "Bruxelles",
                R.drawable.user6,
                gameList2,
                true
            )
            val user7 = User(
                "Kevkev",
                17,
                "Namur",
                R.drawable.user7,
                gameList3,
                false
            )
            val user8 = User(
                "TwitchThot",
                22,
                "Anderlecht",
                R.drawable.user8,
                gameList4,
                true
            )
            val user9 = User(
                "Diams",
                38,
                "Charleroi",
                R.drawable.user9,
                gameList,
                false
            )

            users.add(user1)
            users.add(user2)
            users.add(user3)
            users.add(user4)
            users.add(user5)
            users.add(user6)
            users.add(user7)
            users.add(user8)
            users.add(user9)

            return users
        }
    }
}