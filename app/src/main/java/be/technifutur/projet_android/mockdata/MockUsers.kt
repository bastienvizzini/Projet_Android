package be.technifutur.projet_android.mockdata

import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.MyGame
import be.technifutur.projet_android.models.Platform
import be.technifutur.projet_android.models.MyUser

class MockUsers {
    companion object {

        fun gameList(): ArrayList<MyGame> {
            val gameList2: ArrayList<MyGame> = arrayListOf()
            gameList2.add(
                MyGame(
                    "The Last of Us: Part II",
                    Platform.PS4,
                    R.drawable.theltasofuspartii
                )
            )
            gameList2.add(
                MyGame(
                    "Call of Duty",
                    Platform.XBOXONE,
                    R.drawable.callofduty
                )
            )

            return gameList2
        }

        fun createUsers(): ArrayList<MyUser> {

            val gameList: ArrayList<MyGame> = arrayListOf()
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

            val gameList2: ArrayList<MyGame> = arrayListOf()
            gameList2.add(
                MyGame(
                    "The Last of Us: Part II",
                    Platform.PS4,
                    R.drawable.theltasofuspartii
                )
            )
            gameList2.add(
                MyGame(
                    "Call of Duty",
                    Platform.XBOXONE,
                    R.drawable.callofduty
                )
            )

            val gameList3: ArrayList<MyGame> = arrayListOf()
            gameList3.add(
                MyGame(
                    "Slay the Spire",
                    Platform.PC,
                    R.drawable.slaythespire
                )
            )
            gameList3.add(
                MyGame(
                    "Uno",
                    Platform.XBOXONE,
                    R.drawable.uno
                )
            )

            val gameList4: ArrayList<MyGame> = arrayListOf()
            gameList4.add(
                MyGame(
                    "Farming Simulator",
                    Platform.XBOXONE,
                    R.drawable.farmingsimulator
                )
            )
            gameList4.add(
                MyGame(
                    "League of Legends",
                    Platform.PC,
                    R.drawable.league_of_legends
                )
            )

            val users: ArrayList<MyUser> = arrayListOf()
            val user1 = MyUser(
                "JeanClaude24",
                24,
                "Liège",
                R.drawable.user1,
                gameList,
                false
            )
            val user2 = MyUser(
                "TechnoKiller",
                28,
                "Liège",
                R.drawable.user2,
                gameList2,
                true
            )
            val user4 = MyUser(
                "Corona",
                16,
                "Wuhan",
                R.drawable.user4,
                gameList4,
                true
            )
            val user6 = MyUser(
                "XHipster",
                25,
                "Bruxelles",
                R.drawable.user6,
                gameList2,
                true
            )
            val user7 = MyUser(
                "Kevkev",
                17,
                "Namur",
                R.drawable.user7,
                gameList3,
                false
            )

            users.add(user1)
            users.add(user2)
            users.add(user4)
            users.add(user6)
            users.add(user7)
            users.add(user1)
            users.add(user2)
            users.add(user4)
            users.add(user6)
            users.add(user7)

            return users
        }
    }
}