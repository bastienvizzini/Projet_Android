package be.technifutur.projet_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.GameActivity
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.Game
import com.bumptech.glide.Glide

class SearchGameAdapter(context: Context, gameResultList: ArrayList<Game>): RecyclerView.Adapter<SearchGameAdapter.SearchGameViewHolder>() {

    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mGameResultList = gameResultList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchGameAdapter.SearchGameViewHolder {
        val itemView: View = mInflater.inflate(R.layout.resultlist_item, parent, false)
        return SearchGameViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return mGameResultList.size
    }

    override fun onBindViewHolder(holder: SearchGameAdapter.SearchGameViewHolder, position: Int) {
        val currentResult = mGameResultList[position]

        holder.resultTitleTextView.text = currentResult.name
        Glide.with(holder.itemView.context).load(currentResult.posterPath).centerCrop().into(holder.pictureResultImageView)

        holder.itemView.setOnClickListener {
            val gameDetailIntent = Intent(holder.itemView.context, GameActivity::class.java)
            gameDetailIntent.putExtra("GAME_SELECTED", currentResult)
            holder.itemView.context.startActivity(gameDetailIntent)
        }
    }

    class SearchGameViewHolder(@NonNull itemView: View, adapter: SearchGameAdapter): RecyclerView.ViewHolder(itemView) {
        val pictureResultImageView: ImageView = itemView.findViewById(R.id.result_picture_item)
        val resultTitleTextView: TextView = itemView.findViewById(R.id.result_title_item)
        val noResultTitleTextView: TextView = itemView.findViewById(R.id.no_result_title_item)
        val pictureResultCardView: CardView = itemView.findViewById(R.id.result_picture_cardview)
    }

}