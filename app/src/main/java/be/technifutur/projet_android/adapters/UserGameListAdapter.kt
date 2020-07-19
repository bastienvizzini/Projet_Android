package be.technifutur.projet_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.MyGame
import com.bumptech.glide.Glide

class UserGameListAdapter (context: Context, gameList: MutableList<MyGame>): RecyclerView.Adapter<UserGameListAdapter.UserGameViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mGameList: MutableList<MyGame> = gameList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserGameListAdapter.UserGameViewHolder {
        val mItemView: View = mInflater.inflate(R.layout.user_profile_gamelist_item, parent, false)
        return UserGameViewHolder(mItemView, this)
    }

    override fun getItemCount(): Int {
        return mGameList.size
    }

    override fun onBindViewHolder(holder: UserGameListAdapter.UserGameViewHolder, position: Int) {
        val mCurrentGamePictureResource: Int = mGameList[position].mImageResource
        //holder.gamePictureImageView.setImageResource(mCurrentGamePictureResource)
        Glide.with(holder.itemView.context)
            .load(mCurrentGamePictureResource)
            .centerCrop()
            .into(holder.gamePictureImageView)
    }

    class UserGameViewHolder(@NonNull itemView: View, adapter: UserGameListAdapter): RecyclerView.ViewHolder(itemView) {
        val gamePictureImageView: ImageView = itemView.findViewById(R.id.game_picture_image)
    }
}