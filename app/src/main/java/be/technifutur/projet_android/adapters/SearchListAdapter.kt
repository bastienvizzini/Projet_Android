package be.technifutur.projet_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.GameActivity
import be.technifutur.projet_android.R
import be.technifutur.projet_android.UserProfileActivity
import be.technifutur.projet_android.models.Game
import be.technifutur.projet_android.models.SearchResult
import be.technifutur.projet_android.models.User
import com.bumptech.glide.Glide

class SearchListAdapter(context: Context, resultList: SearchResult) : RecyclerView.Adapter<SearchListAdapter.SearchViewHolder>(), Filterable {

    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mAllUserResultList: ArrayList<User> = resultList.userResult
    private var mAllGameResultList: ArrayList<Game> = resultList.gameResults // on utilise ça comme search déjà fait via api
    private var mUserResultList: ArrayList<User> = arrayListOf()
    // private var mGameResultList: List<Game> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListAdapter.SearchViewHolder {
        val mItemView: View = mInflater.inflate(R.layout.resultlist_item, parent, false)
        return SearchViewHolder(mItemView, this)
    }

    override fun getItemCount(): Int {
        return if (mUserResultList.isEmpty() && mAllGameResultList.isEmpty()) {
            1
        } else {
            val count = mUserResultList.size + mAllGameResultList.size
            count
        }
    }

    override fun onBindViewHolder(holder: SearchListAdapter.SearchViewHolder, position: Int) {
        if (mUserResultList.isEmpty() && mAllGameResultList.isEmpty()) {
            holder.resultTitleTextView.visibility = View.INVISIBLE
            holder.pictureResultImageView.visibility = View.INVISIBLE
            holder.pictureResultCardView.visibility = View.INVISIBLE
            holder.noResultTitleTextView.visibility = View.VISIBLE
        } else if (position < mAllGameResultList.size) {
            val currentGame = mAllGameResultList[position]

            holder.resultTitleTextView.visibility = View.VISIBLE
            holder.pictureResultImageView.visibility = View.VISIBLE
            holder.pictureResultCardView.visibility = View.VISIBLE
            holder.noResultTitleTextView.visibility = View.INVISIBLE
            holder.resultTitleTextView.text = currentGame.name

            Glide.with(holder.itemView.context)
                .load(currentGame.posterPath)
                .centerCrop()
                .into(holder.pictureResultImageView)

            holder.itemView.setOnClickListener {
                val gameDetailIntent = Intent(holder.itemView.context, GameActivity::class.java)
                gameDetailIntent.putExtra("GAME_SELECTED", currentGame)
                holder.itemView.context.startActivity(gameDetailIntent)
            }
        } /*else {
            val currentUser = mUserResultList[position + mAllGameResultList.size]

            holder.resultTitleTextView.visibility = View.VISIBLE
            holder.pictureResultImageView.visibility = View.VISIBLE
            holder.pictureResultCardView.visibility = View.VISIBLE
            holder.noResultTitleTextView.visibility = View.INVISIBLE
            holder.resultTitleTextView.text = currentUser.userName

            Glide.with(holder.itemView.context)
                .load(currentUser.profilePicture)
                .centerCrop()
                .into(holder.pictureResultImageView)

            holder.itemView.setOnClickListener {
                val userProfileIntent = Intent(holder.itemView.context, UserProfileActivity::class.java)
                userProfileIntent.putExtra("USER_SELECTED", currentUser)
                holder.itemView.context.startActivity(userProfileIntent)
            }
        }*/
    }

    override fun getFilter(): Filter {
        return mFilter
    }

    private var mFilter: Filter = object : Filter() {
        // Run on background thread
        override fun performFiltering(charSequence: CharSequence?): FilterResults {

            var filteredList: MutableList<User> = mutableListOf()

            if (!charSequence.toString().isEmpty()) {
                for (result in mAllUserResultList) {
                    if (result.userName.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(result)
                    }
                }
            }

            val filterResults: FilterResults = FilterResults()
            filterResults.values = filteredList

            return filterResults
        }

        // Run on UI thread
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            mUserResultList.clear()
            mUserResultList.addAll(results?.values as Collection<User>)
            notifyDataSetChanged()
        }
    }

    class SearchViewHolder(@NonNull itemView: View, adapter: SearchListAdapter):
        RecyclerView.ViewHolder(itemView) {

        val pictureResultImageView: ImageView = itemView.findViewById(R.id.result_picture_item)
        val resultTitleTextView: TextView = itemView.findViewById(R.id.result_title_item)
        val noResultTitleTextView: TextView = itemView.findViewById(R.id.no_result_title_item)
        val pictureResultCardView: CardView = itemView.findViewById(R.id.result_picture_cardview)
    }

}