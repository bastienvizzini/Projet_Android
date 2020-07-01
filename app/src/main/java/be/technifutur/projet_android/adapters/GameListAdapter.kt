package be.technifutur.projet_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.Game
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.net.URL

class GameListAdapter (context: Context, gameList: MutableList<Game>): RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mGameList: MutableList<Game> = gameList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameListAdapter.GameViewHolder {
        val mItemView: View = mInflater.inflate(R.layout.user_profile_gamelist_item, parent, false)
        return GameViewHolder(mItemView, this)
    }

    override fun getItemCount(): Int {
        return mGameList.size
    }

    override fun onBindViewHolder(holder: GameListAdapter.GameViewHolder, position: Int) {
        val mCurrentGamePictureResource: Int = mGameList[position].mImageResource
        //holder.gamePictureImageView.setImageResource(mCurrentGamePictureResource)
        Glide.with(holder.itemView.context)
            .load(mCurrentGamePictureResource)
            .centerCrop()
            .into(holder.gamePictureImageView)
    }

    class GameViewHolder(@NonNull itemView: View, adapter: GameListAdapter): RecyclerView.ViewHolder(itemView) {
        val gamePictureImageView: ImageView = itemView.findViewById(R.id.game_picture_image)
    }
}