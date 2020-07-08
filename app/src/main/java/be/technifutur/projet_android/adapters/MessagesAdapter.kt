package be.technifutur.projet_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.User
import com.bumptech.glide.Glide

class MessagesAdapter(context: Context, userList: MutableList<User>): RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    private var mMessageFriendList: MutableList<User> = userList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessagesAdapter.MessagesViewHolder {
        val itemView: View = mInflater.inflate(R.layout.message_list_item, parent, false)
        return MessagesViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return mMessageFriendList.size
    }

    override fun onBindViewHolder(holder: MessagesAdapter.MessagesViewHolder, position: Int) {
        when (position) {
            0 -> holder.backgroundView.setBackgroundResource(R.drawable.first_item_background)
            mMessageFriendList.size - 1 -> {
                holder.backgroundView.setBackgroundResource(R.drawable.last_item_background)
            }
            else -> holder.backgroundView.setBackgroundResource(R.color.lightGray)
        }
        val mCurrentUsername: String = mMessageFriendList[position].mUserName
        val mCurrentMessage: String = "Hey, this is a sample text, you know what I mean ?"
        val mCurrentPictureResource: Int = mMessageFriendList[position].mProfilePicture
        holder.usernameTextView.text = mCurrentUsername
        holder.messageTextView.text = mCurrentMessage
        Glide.with(holder.itemView.context)
            .load(mCurrentPictureResource)
            .centerCrop()
            .into(holder.pictureImageView)
    }

    inner class MessagesViewHolder(@NonNull itemView: View, adapter: MessagesAdapter): RecyclerView.ViewHolder(itemView) {

        val pictureImageView: ImageView = itemView.findViewById(R.id.message_friend_picture_item)
        val usernameTextView: TextView = itemView.findViewById(R.id.message_friend_username_item)
        val messageTextView: TextView = itemView.findViewById(R.id.message_friend_message_item)
        val backgroundView: View = itemView.findViewById(R.id.message_item_background_view)
    }
}