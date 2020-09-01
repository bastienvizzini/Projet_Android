package be.technifutur.projet_android.adapters.old

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.MyUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.friendlist_platform_child_item.view.*

class FriendsListChildAdapter(context: Context, userList: MutableList<MyUser>) :
    RecyclerView.Adapter<FriendsListChildAdapter.ChildViewHolder>() {

    private val mUserList = userList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildViewHolder {
        val mItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.friendlist_platform_child_item, parent, false)
        return ChildViewHolder(mItemView)
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val child = mUserList[position]
        holder.usernameTextView.text = child.userName
        Glide.with(holder.itemView.context).load(child.profilePicture).transform(RoundedCorners(20)).into(holder.pictureImageView)
    }

    inner class ChildViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.friendUsernameItem
        val pictureImageView: ImageView = itemView.friendPictureItem
    }

}