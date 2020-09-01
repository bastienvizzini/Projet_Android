package be.technifutur.projet_android.mockdata

import be.technifutur.projet_android.models.MyRoom
import be.technifutur.projet_android.models.MyUser

class MockRooms {
    companion object {
        fun createRooms(): ArrayList<MyRoom> {

            val rooms =  arrayListOf<MyRoom>()
            val users = MockUsers.createUsers()
            val room1users = arrayListOf<MyUser>()
            room1users.add(users[0])
            room1users.add(users[4])
            room1users.add(users[2])

            val room2users = arrayListOf<MyUser>()
            room2users.add(users[1])
            room2users.add(users[3])

            val room3users = arrayListOf<MyUser>()
            room3users.add(users[8])
            room3users.add(users[7])
            room3users.add(users[6])

            val room1 = MyRoom(room1users, 4, "French", "Long sessions", "Tryhard")
            val room2 = MyRoom(room2users, 3, "English", "Few games", "Casual")
            val room3 = MyRoom(room3users, 4, "Estonian", "Long sessions", "Fun")
            val room4 = MyRoom(room1users, 4, "French", "Long sessions", "Tryhard")
            val room5 = MyRoom(room2users, 3, "English", "Few games", "Casual")
            val room6 = MyRoom(room3users, 4, "Estonian", "Long sessions", "Fun")

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