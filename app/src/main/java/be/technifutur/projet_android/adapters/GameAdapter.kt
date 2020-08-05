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
import be.technifutur.projet_android.models.Game
import com.bumptech.glide.Glide

class GameAdapter(context: Context, gameList: MutableList<Game>):
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private var mGameList = gameList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.GameViewHolder {
        val itemView: View = mInflater.inflate(R.layout.item_game_explore, parent, false)
        return GameViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mGameList.size
    }

    override fun onBindViewHolder(holder: GameAdapter.GameViewHolder, position: Int) {
        val currentGame = mGameList[position]
        val posterPath = currentGame.posterPath
        val name = currentGame.name
        val genres = currentGame.genres
        Glide.with(holder.itemView.context).load(posterPath).centerCrop().into(holder.gamePictureImageView)
        holder.gameNameTextView.text = name
        holder.gameGenreTextView.text = genres?.get(0)?.name ?: "" // dégueulasse

    }

    inner class GameViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        val gamePictureImageView: ImageView = itemView.findViewById(R.id.gamePictureImageView)
        val gameNameTextView: TextView = itemView.findViewById(R.id.gameNameTextView)
        val gameGenreTextView: TextView = itemView.findViewById(R.id.gameGenreTextView)
    }

}