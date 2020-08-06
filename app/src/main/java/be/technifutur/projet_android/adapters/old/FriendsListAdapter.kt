package be.technifutur.projet_android.adapters.old

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.Platform
import be.technifutur.projet_android.models.User
import kotlinx.android.synthetic.main.friendlist_platform_item.view.*

class FriendsListAdapter(context: Context, userList: MutableList<User>) :
    RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder>() {

    private var mUserList: MutableList<User> = userList
    //private var mOnlineUserList: MutableList<User> = getOnlineUsers(mUserList)
    //private var mOfflineUserList: MutableList<User> = getOfflineUsers(mUserList)
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsViewHolder {
        /*val mItemView: View = when (viewType) {
            0 -> mInflater.inflate(R.layout.friendlist_online_title_item_two, parent, false)
            //mInflater.inflate(R.layout.friendlist_online_title_item, parent, false)
            // if first item, this layout
            1 -> mInflater.inflate(R.layout.last_friendlist_item, parent, false) // if last item, this layout
            2 -> mInflater.inflate(R.layout.friendlist_offline_title_item_two, parent, false)
            //mInflater.inflate(R.layout.friendlist_offline_title_item, parent, false)
            3 -> mInflater.inflate(R.layout.last_friendlist_item, parent, false)
            else -> mInflater.inflate(R.layout.friendlist_item_dark, parent, false) // otherwise default layout
        }*/
        val mItemView: View = mInflater.inflate(R.layout.friendlist_platform_item, parent, false)
        return FriendsViewHolder(
            mItemView,
            this
        )
    }

    private fun getPlatforms(users: MutableList<User>): MutableList<String> {
        val platformList = mutableListOf<String>()
        for (user in users) {
            for (game in user.games) {
                if (!platformList.contains(game.platform.platformName)){
                    platformList.add(game.platform.platformName)
                }
            }
        }
        return platformList
    }

    /*fun getOnlineUsers(users: MutableList<User>): MutableList<User> {
        val onlineUserList: MutableList<User> = mutableListOf()
        for (user in users) {
            if (user.mIsOnline) {
                onlineUserList.add(user)
            }
        }
        return onlineUserList
    }

    fun getOfflineUsers(users: MutableList<User>): MutableList<User> {
        val offlineUserList: MutableList<User> = mutableListOf()
        for (user in users) {
            if (!user.mIsOnline) {
                offlineUserList.add(user)
            }
        }
        return offlineUserList
    }

    override fun getItemViewType(position: Int): Int { // returns viewType used in onCreateViewHolder
        return when (position) {
            0 -> { // Online title cell + first user
                0
            }
            mOnlineUserList.size - 1 -> { // If last from online
                1
            }
            mOnlineUserList.size -> { // Offline title cell + first user
                2
            }
            mUserList.size - 1 -> { // If last from offline based on all users list
                3
            }
            else -> {
                4
            }
        }
    } */

    override fun getItemCount(): Int {
        //return mUserList.size
        return getPlatforms(mUserList).size
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {

        val mCurrentPlatform: String = getPlatforms(mUserList)[position]
        holder.platformTextView.text = mCurrentPlatform
        val childLayoutManager = LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.HORIZONTAL, false)

        //childLayoutManager.initialPrefetchItemCount = 4
        val mUserListByPlatform = mutableListOf<User>()
        // Lol ça marche pas, par contre plutôt faire un gridlayout avec tous les amis et garder ce layout là pour le discovery
        when (mCurrentPlatform) {
            Platform.SWITCH.platformName -> {
                for (user in mUserList) {
                    for (game in user.games) {
                        if (game.platform.platformName == Platform.SWITCH.platformName && !mUserListByPlatform.contains(user)) {
                            mUserListByPlatform.add(user)
                        }
                    }
                }
            }
            Platform.PC.platformName -> {
                for (user in mUserList) {
                    for (game in user.games) {
                        if (game.platform.platformName == Platform.PC.platformName && !mUserListByPlatform.contains(user)) {
                            mUserListByPlatform.add(user)
                        }
                    }
                }
            }
            Platform.PS4.platformName -> {
                for (user in mUserList) {
                    for (game in user.games) {
                        if (game.platform.platformName == Platform.PS4.platformName && !mUserListByPlatform.contains(user)) {
                            mUserListByPlatform.add(user)
                        }
                    }
                }
            }
            Platform.XBOXONE.platformName -> {
                for (user in mUserList) {
                    for (game in user.games) {
                        if (game.platform.platformName == Platform.XBOXONE.platformName && !mUserListByPlatform.contains(user)) {
                            mUserListByPlatform.add(user)
                        }
                    }
                }
            }
        }

        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter =
                FriendsListChildAdapter(
                    holder.recyclerView.context,
                    mUserListByPlatform
                )
            setRecycledViewPool(viewPool)
        }

        /*when {
            position < mOnlineUserList.size -> {
                val mCurrentUsername: String = mOnlineUserList[position].mUserName
                val mCurrentCurrentGame: String = mOnlineUserList[position].mGames[0].mTitle
                val otherGamesCount = mOnlineUserList[position].mGames.size - 1
                val mCurrentProfileResource: Int = mOnlineUserList[position].mProfilePicture
                holder.usernameTextView.text = mCurrentUsername
                holder.currentGameTextView.text = "$mCurrentCurrentGame and $otherGamesCount other games"
                //holder.profilePictureImageView.setImageResource(mCurrentProfileResource)
                Glide.with(holder.itemView.context)
                    .load(mCurrentProfileResource)
                    .transform(RoundedCorners(20))
                    .into(holder.profilePictureImageView)

                // Handle click
                holder.itemView.setOnClickListener {
                    val userProfileIntent = Intent(holder.itemView.context, UserProfileActivity::class.java)
                    UserProfileActivity.mUser = mOnlineUserList[position]
                    holder.itemView.context.startActivity(userProfileIntent)
                }
            }
            position >= mOnlineUserList.size -> {
                val mCurrentUsername: String = mOfflineUserList[position-(mOnlineUserList.size)].mUserName // Set index to 0 since it's another list
                val mCurrentCurrentGame: String = mOfflineUserList[position-(mOnlineUserList.size)].mGames[0].mTitle
                val otherGamesCount = mOfflineUserList[position-(mOnlineUserList.size)].mGames.size - 1
                val mCurrentProfileResource: Int = mOfflineUserList[position-(mOnlineUserList.size)].mProfilePicture
                holder.usernameTextView.text = mCurrentUsername
                holder.currentGameTextView.text = "$mCurrentCurrentGame and $otherGamesCount other games"
                //holder.profilePictureImageView.setImageResource(mCurrentProfileResource)
                Glide.with(holder.itemView.context)
                    .load(mCurrentProfileResource)
                    .transform(RoundedCorners(20))
                    .into(holder.profilePictureImageView)

                // Handle click
                holder.itemView.setOnClickListener {
                    val userProfileIntent = Intent(holder.itemView.context, UserProfileActivity::class.java)
                    UserProfileActivity.mUser = mOfflineUserList[position-(mOnlineUserList.size)]
                    holder.itemView.context.startActivity(userProfileIntent)
                }
            }
        }*/

    }

    class FriendsViewHolder(@NonNull itemView: View, adapter: FriendsListAdapter) :
        RecyclerView.ViewHolder(itemView) {

        val platformTextView: TextView = itemView.platformName
        val recyclerView : RecyclerView = itemView.friendListRecyclerView

        //val usernameTextView: TextView = itemView.findViewById(R.id.friend_username_item)
        //var currentGameTextView: TextView = itemView.findViewById(R.id.friend_current_game_item)
        //val profilePictureImageView: ImageView = itemView.findViewById(R.id.friend_picture_item)
    }
}