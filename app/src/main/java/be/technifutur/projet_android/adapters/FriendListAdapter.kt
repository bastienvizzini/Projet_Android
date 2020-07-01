package be.technifutur.projet_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.UserProfileActivity
import be.technifutur.projet_android.models.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.friendlist_item_final.view.*

class FriendsListAdapter(context: Context, userList: MutableList<User>) :
    RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder>() {

    private var mFriendsList: MutableList<User> = userList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsListAdapter.FriendsViewHolder {
        val mItemView: View = mInflater.inflate(R.layout.friendlist_item_final, parent, false)
        return FriendsViewHolder(mItemView, this)
    }

    override fun getItemCount(): Int {
        return mFriendsList.size
    }

    override fun onBindViewHolder(holder: FriendsListAdapter.FriendsViewHolder, position: Int) {
        val currentFriend = mFriendsList[position]
        holder.usernameTextView.text = currentFriend.mUserName
        Glide.with(holder.itemView.context).load(currentFriend.mProfilePicture).centerCrop().into(holder.pictureImageView)

        holder.itemView.setOnClickListener {
            val userProfileIntent = Intent(holder.itemView.context, UserProfileActivity::class.java)
            UserProfileActivity.mUser = currentFriend
            holder.itemView.context.startActivity(userProfileIntent)
        }
    }

    inner class FriendsViewHolder(@NonNull itemView: View, adapter: FriendsListAdapter) :
        RecyclerView.ViewHolder(itemView) {

        val usernameTextView: TextView = itemView.friendUsernameItemFinal
        val pictureImageView: ImageView = itemView.friendPictureItemFinal
    }
}