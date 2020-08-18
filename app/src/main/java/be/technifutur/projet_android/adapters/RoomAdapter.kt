package be.technifutur.projet_android.adapters

import android.content.Context
import android.text.TextUtils
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.Room
import com.bumptech.glide.Glide


class RoomAdapter(context: Context, roomList: ArrayList<Room>): RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val mRoomList = roomList
    private val mInflater = LayoutInflater.from(context)

    // To get window size
    private var wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var display: Display = wm.defaultDisplay
    // To use dp when width/height are set programmatically
    private val scale: Float = context.resources.displayMetrics.density
    var pixels = (20.0f * scale + 0.5f).toInt()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.RoomViewHolder {
        val itemView: View = mInflater.inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mRoomList.size
    }

    override fun onBindViewHolder(holder: RoomAdapter.RoomViewHolder, position: Int) {
        val currentRoom = mRoomList[position]
        val numberOfUsersString = "${currentRoom.users.size} / ${currentRoom.maxUsersInRoom}"

        //holder.roomCardView.layoutParams.width = (display.width/3)+(display.width/9)

        for (user in mRoomList[position].users) {

            val singleUserLinearLayout = LinearLayout(holder.itemView.context)
            holder.usersLinearLayout.addView(singleUserLinearLayout)
            singleUserLinearLayout.orientation = LinearLayout.HORIZONTAL
            singleUserLinearLayout.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            singleUserLinearLayout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            //singleUserLinearLayout.gravity = Gravity.CENTER

            val pictureImageView = ImageView(holder.itemView.context)
            singleUserLinearLayout.addView(pictureImageView)
            Glide.with(holder.itemView.context).load(user.profilePicture).circleCrop().into(pictureImageView)
            pictureImageView.layoutParams.width = pixels
            pictureImageView.layoutParams.height = pixels
            setMargins(pictureImageView, 0,0,8,4)

            val usernameTextView = TextView(holder.itemView.context)
            singleUserLinearLayout.addView(usernameTextView)
            //usernameTextView.width = ViewGroup.LayoutParams.MATCH_PARENT
            //usernameTextView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            //usernameTextView.layoutParams = TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            usernameTextView.text = user.userName
            usernameTextView.ellipsize = TextUtils.TruncateAt.END
            usernameTextView.maxLines = 1

        }

        holder.numberOfUsersTextView.text = numberOfUsersString
        holder.gameDurationTextView.text = currentRoom.gameDuration
        holder.gameIntensityTextView.text = currentRoom.gameIntensity
        holder.roomLanguageTextView.text = currentRoom.language
    }

    inner class RoomViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        val usersLinearLayout: LinearLayout = itemView.findViewById(R.id.roomUsersLinearLayout)
        val numberOfUsersTextView: TextView = itemView.findViewById(R.id.numberOfUsersTextView)
        val gameDurationTextView: TextView = itemView.findViewById(R.id.gameDurationTextView)
        val gameIntensityTextView: TextView = itemView.findViewById(R.id.gameIntensityTextView)
        val roomLanguageTextView: TextView = itemView.findViewById(R.id.roomLanguageTextView)
        val roomCardView: CardView = itemView.findViewById(R.id.roomCardView)
    }

    private fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int ) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams

            //val scale: Float = getBaseContext().getResources().getDisplayMetrics().density // défini en variable d'instance plus haut
            // convert the DP into pixel
            val l = (left * scale + 0.5f).toInt()
            val r = (right * scale + 0.5f).toInt()
            val t = (top * scale + 0.5f).toInt()
            val b = (bottom * scale + 0.5f).toInt()
            p.setMargins(l, t, r, b)
            view.requestLayout()
        }
    }
}