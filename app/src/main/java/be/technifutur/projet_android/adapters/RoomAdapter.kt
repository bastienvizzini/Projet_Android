package be.technifutur.projet_android.adapters

import android.app.ActionBar
import android.content.Context
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.Room

class RoomAdapter(context: Context, roomList: ArrayList<Room>): RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val mRoomList = roomList
    private val mInflater = LayoutInflater.from(context)

    // To get window size
    private var wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var display: Display = wm.defaultDisplay

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

        holder.roomCardView.layoutParams.width = (display.width/3)+(display.width/9)

        for (user in mRoomList[position].users) {
            val usernameTextView = TextView(holder.itemView.context)
            usernameTextView.width = ViewGroup.LayoutParams.MATCH_PARENT
            usernameTextView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            usernameTextView.text = user.userName
            holder.usersLinearLayout.addView(usernameTextView)
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
}