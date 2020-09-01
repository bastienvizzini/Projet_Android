package be.technifutur.projet_android.adapters

import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.UserProfileActivity
import be.technifutur.projet_android.models.MyUser
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.friendlist_item_final.view.*


class FriendsListAdapter(context: Context, userList: MutableList<MyUser>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mFriendsList: MutableList<MyUser> = userList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext = context
    private val itemScreenTitle = 0
    private val itemBody = 1

    // To get window size
    //private var wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    //private var display: Display = wm.defaultDisplay

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val mItemView: View = mInflater.inflate(R.layout.title_fragment_item, parent, false)
                ScreenTitleViewHolder(mItemView)
            }
            else -> {
                val mItemView: View = mInflater.inflate(R.layout.friendlist_item_final, parent, false)
                FriendsViewHolder(mItemView)
            }
        }

    }

    override fun getItemCount(): Int {
        return mFriendsList.size + 1 // +1 for header (screen title)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FriendsViewHolder -> {
                val currentFriend = mFriendsList[position-1] // -1 bc of header
                holder.usernameTextView.text = currentFriend.userName
                val games: String
                games = when (currentFriend.games.size) {
                    0 -> {
                        ""
                    }
                    1 -> {
                        currentFriend.games[0].title
                    }
                    2 -> {
                        "${currentFriend.games[0].title } and 1 other game"
                    }
                    else -> {
                        "${currentFriend.games[0].title } and ${currentFriend.games.size - 1} other games"
                    }
                }
                holder.gamesTextView.text = games
                // To set the image size programmatically
                //holder.userCardView.layoutParams.width = (display.width/4)+(display.width/18)
                //holder.userCardView.layoutParams.height = (display.width/3)
                Glide.with(holder.itemView.context).load(currentFriend.profilePicture).centerCrop().into(holder.pictureImageView)

                holder.itemView.setOnClickListener {
                    //val animation: Animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.zoom_in)
                    //holder.userCardView.startAnimation(animation)
                    val userProfileIntent = Intent(holder.itemView.context, UserProfileActivity::class.java)
                    userProfileIntent.putExtra("USER_SELECTED", currentFriend)
                    //UserProfileActivity.mUser = currentFriend
                    holder.itemView.context.startActivity(userProfileIntent)
                }
            }
            is ScreenTitleViewHolder -> {
                holder.screenTitleTextView.text = mContext.getString(R.string.friends_list_title)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> itemScreenTitle
            else -> itemBody
        }
    }

    fun isHeader(position: Int): Boolean {
        return position == 0
    }

    inner class FriendsViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        val usernameTextView: TextView = itemView.friendUsernameItemFinal
        val pictureImageView: ImageView = itemView.friendPictureItemFinal
        val gamesTextView: TextView = itemView.friendGamesItemFinal
        val userCardView: CardView = itemView.friendItemCardview
    }

    inner class ScreenTitleViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        val screenTitleTextView: TextView = itemView.findViewById(R.id.screenTitleItem)
    }
}