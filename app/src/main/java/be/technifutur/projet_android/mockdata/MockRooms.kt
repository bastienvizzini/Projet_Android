package be.technifutur.projet_android.mockdata

import be.technifutur.projet_android.models.Room
import be.technifutur.projet_android.models.User

class MockRooms {
    companion object {
        fun createRooms(): ArrayList<Room> {

            val rooms =  arrayListOf<Room>()
            val users = MockUsers.createUsers()
            val room1users = arrayListOf<User>()
            room1users.add(users[0])
            room1users.add(users[4])
            room1users.add(users[2])

            val room2users = arrayListOf<User>()
            room2users.add(users[1])
            room2users.add(users[3])

            val room3users = arrayListOf<User>()
            room3users.add(users[8])
            room3users.add(users[7])
            room3users.add(users[7])

            val room1 = Room(room1users, 4, "French", "Long sessions", "Tryhard")
            val room2 = Room(room2users, 3, "English", "Few games", "Casual")
            val room3 = Room(room3users, 4, "Estonian", "Long sessions", "Fun")
            val room4 = Room(room1users, 4, "French", "Long sessions", "Tryhard")
            val room5 = Room(room2users, 3, "English", "Few games", "Casual")
            val room6 = Room(room3users, 4, "Estonian", "Long sessions", "Fun")

            rooms.add(room1)
            rooms.add(room2)
            rooms.add(room3)
            rooms.add(room4)
            rooms.add(room5)
            rooms.add(room6)

            return rooms
        }
    }
}