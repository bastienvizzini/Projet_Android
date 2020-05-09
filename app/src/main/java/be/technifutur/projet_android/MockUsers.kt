package be.technifutur.projet_android

class MockUsers {
    companion object {
        fun createUsers() : List<User> {

            val gameList: MutableList<MutableList<Game>> = mutableListOf()
            gameList[0].add(Game("Apex Legends", Platform.PS4))
            gameList[0].add(Game("Fortnite", Platform.XBOXONE))
            gameList[0].add(Game("League of Legends", Platform.PC))

            val users: MutableList<User> = mutableListOf()
            val user1 = User("JeanClaude24", 24, "Liège", R.drawable.boy_1, gameList[0], false)
            val user2 = User("TechnoKiller", 28, "Liège", R.drawable.boy_2, gameList[0], true)
            val user3 = User("Didier Raoult", 87, "Marseille", R.drawable.man_1, gameList[0], false)
            val user4 = User("Corona", 16, "Wuhan", R.drawable.man_2, gameList[0], true)
            val user5 = User("PatrickBalkaneux", 67, "Levallois", R.drawable.man_3, gameList[0], false)
            val user6 = User("XHipster", 25, "Bruxelles", R.drawable.man_4, gameList[0], true)
            val user7 = User("Kevkev", 17, "Namur", R.drawable.man_5, gameList[0], false)
            val user8 = User("TwitchThot", 22, "Anderlecht", R.drawable.girl_1, gameList[0], true)
            val user9 = User("Diams", 38, "Charleroi", R.drawable.girl_2, gameList[0], false)

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