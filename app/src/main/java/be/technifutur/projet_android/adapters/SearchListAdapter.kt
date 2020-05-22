package be.technifutur.projet_android.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.User

class SearchListAdapter(context: Context, resultList: MutableList<User>) : RecyclerView.Adapter<SearchListAdapter.SearchViewHolder>(), Filterable {

    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mAllResultList: MutableList<User> = resultList
    private var mResultList: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListAdapter.SearchViewHolder {
        val mItemView: View = mInflater.inflate(R.layout.resultlist_item, parent, false)
        return SearchViewHolder(mItemView, this)
    }

    override fun getItemCount(): Int {
        return if (mResultList.size == 0) {
            1
        } else {
            mResultList.size
        }
    }

    override fun onBindViewHolder(holder: SearchListAdapter.SearchViewHolder, position: Int) {
        if (mResultList.size == 0) {
            holder.resultTitleTextView.visibility = View.INVISIBLE
            holder.pictureResultImageView.visibility = View.INVISIBLE
            holder.noResultTitleTextView.visibility = View.VISIBLE
        } else {
            val mCurrentUsername: String = mResultList[position].mUserName
            val mCurrentPictureResource: Int = mResultList[position].mProfilePicture
            holder.resultTitleTextView.visibility = View.VISIBLE
            holder.pictureResultImageView.visibility = View.VISIBLE
            holder.noResultTitleTextView.visibility = View.INVISIBLE
            holder.resultTitleTextView.text = mCurrentUsername
            holder.pictureResultImageView.setImageResource(mCurrentPictureResource)
        }
    }

    override fun getFilter(): Filter {
        return mFilter
    }

    private var mFilter: Filter = object : Filter() {
        // Run on background thread
        override fun performFiltering(charSequence: CharSequence?): FilterResults {

            var filteredList: MutableList<User> = mutableListOf()

            if (!charSequence.toString().isEmpty()) {
                for (result in mAllResultList) {
                    if (result.mUserName.toLowerCase().contains(charSequence.toString().toLowerCase())) {
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
            mResultList.clear()
            mResultList.addAll(results?.values as Collection<User>)
            notifyDataSetChanged()
        }
    }

    class SearchViewHolder(@NonNull itemView: View, adapter: SearchListAdapter):
        RecyclerView.ViewHolder(itemView) {

        val pictureResultImageView: ImageView = itemView.findViewById(R.id.result_picture_item)
        val resultTitleTextView: TextView = itemView.findViewById(R.id.result_title_item)
        val noResultTitleTextView: TextView = itemView.findViewById(R.id.no_result_title_item)
    }

}