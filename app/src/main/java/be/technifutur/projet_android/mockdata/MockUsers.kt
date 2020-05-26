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
                    "League of Legends",
                    Platform.PC,
                    R.drawable.league_of_legends
                )
            )
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
                R.drawable.boy_1,
                gameList,
                false
            )
            val user2 = User(
                "TechnoKiller",
                28,
                "Liège",
                R.drawable.boy_2,
                gameList,
                true
            )
            val user3 = User(
                "Didier Raoult",
                87,
                "Marseille",
                R.drawable.man_1,
                gameList,
                false
            )
            val user4 = User(
                "Corona",
                16,
                "Wuhan",
                R.drawable.man_2,
                gameList,
                true
            )
            val user5 = User(
                "PatrickBalkaneux",
                67,
                "Levallois",
                R.drawable.man_3,
                gameList,
                false
            )
            val user6 = User(
                "XHipster",
                25,
                "Bruxelles",
                R.drawable.man_4,
                gameList,
                true
            )
            val user7 = User(
                "Kevkev",
                17,
                "Namur",
                R.drawable.man_5,
                gameList,
                false
            )
            val user8 = User(
                "TwitchThot",
                22,
                "Anderlecht",
                R.drawable.girl_1,
                gameList,
                true
            )
            val user9 = User(
                "Diams",
                38,
                "Charleroi",
                R.drawable.girl_2,
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