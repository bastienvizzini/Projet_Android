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

class MessagesAdapter(context: Context, userList: MutableList<User>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mMessageFriendList: MutableList<User> = userList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    // To set the screen title
    private val itemScreenTitle = 0
    private val itemBody = 1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            itemScreenTitle -> {
                val itemView: View = mInflater.inflate(R.layout.title_fragment_item, parent, false)
                ScreenTitleViewHolder(itemView)
            }
            else -> {
                val itemView: View = mInflater.inflate(R.layout.message_list_item_dark, parent, false)
                return MessagesViewHolder(itemView)
            }
        }
    }

    override fun getItemCount(): Int {
        return mMessageFriendList.size + 1 // +1 for header (screen title)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessagesViewHolder -> {
                when (position) {
                    1 ->  {

                        holder.backgroundView.setBackgroundResource(R.drawable.first_item_background_dark)

                    }
                    mMessageFriendList.size -> {
                        holder.backgroundView.setBackgroundResource(R.drawable.last_item_background_dark)
                    }
                    else -> holder.backgroundView.setBackgroundResource(R.color.listItemBackground)
                }
                val mCurrentUsername: String = mMessageFriendList[position-1].mUserName // -1 bc of header
                val mCurrentMessage = "Hey, this is a sample text, you know what I mean ?"
                val mCurrentPictureResource: Int = mMessageFriendList[position-1].mProfilePicture
                holder.usernameTextView.text = mCurrentUsername
                holder.messageTextView.text = mCurrentMessage
                Glide.with(holder.itemView.context)
                    .load(mCurrentPictureResource)
                    .centerCrop()
                    .into(holder.pictureImageView)
            }
            is ScreenTitleViewHolder -> {
                holder.screenTitleTextView.text = "Messages"
            }
            }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> itemScreenTitle
            else -> itemBody
        }
    }

    inner class MessagesViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {

        val pictureImageView: ImageView = itemView.findViewById(R.id.message_friend_picture_item)
        val usernameTextView: TextView = itemView.findViewById(R.id.message_friend_username_item)
        val messageTextView: TextView = itemView.findViewById(R.id.message_friend_message_item)
        val backgroundView: View = itemView.findViewById(R.id.message_item_background_view)
    }

    inner class ScreenTitleViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {

        val screenTitleTextView: TextView = itemView.findViewById(R.id.screenTitleItem)
    }
}