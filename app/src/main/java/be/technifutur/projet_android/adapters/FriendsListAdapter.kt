package be.technifutur.projet_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.User

class FriendsListAdapter(context: Context, userList: MutableList<User>) :
    RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder>() {

    private var mUserList: MutableList<User> = userList
    private var mInflater: LayoutInflater = LayoutInflater.from(context);

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsListAdapter.FriendsViewHolder {
        val mItemView: View = when (viewType) {
            0 -> mInflater.inflate(R.layout.first_friendlist_item, parent, false) // if first item, this layout
            1 -> mInflater.inflate(R.layout.last_friendlist_item, parent, false) // if last item, this layout
            else -> mInflater.inflate(R.layout.friendlist_item, parent, false) // otherwise default layout
        }
        return FriendsViewHolder(mItemView, this)
    }

    override fun getItemViewType(position: Int): Int { // returns viewType used in onCreateViewHolder
        return when (position) {
            0 -> {
                0
            }
            mUserList.size - 1 -> {
                1
            }
            else -> {
                3
            }
        }
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    override fun onBindViewHolder(holder: FriendsListAdapter.FriendsViewHolder, position: Int) {
        val mCurrentUsername: String = mUserList[position].mUserName
        val mCurrentCurrentGame: String = mUserList[position].mGames[0].mTitle
        val mCurrentProfileResource: Int = mUserList[position].mProfilePicture
        holder.usernameTextView.text = mCurrentUsername
        holder.currentGameTextView.text = mCurrentCurrentGame
        holder.profilePictureImageView.setImageResource(mCurrentProfileResource)
    }

    class FriendsViewHolder(@NonNull itemView: View, adapter: FriendsListAdapter) :
        RecyclerView.ViewHolder(itemView) {

        val usernameTextView: TextView = itemView.findViewById(R.id.friend_username_item)
        var currentGameTextView: TextView = itemView.findViewById(R.id.friend_current_game_item)
        val profilePictureImageView: ImageView = itemView.findViewById(R.id.friend_picture_item)

    }
}