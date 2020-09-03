package be.technifutur.projet_android.adapters.game_detail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.RoomMessagesActivity
import be.technifutur.projet_android.models.Room
import com.bumptech.glide.RequestManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class RoomAdapter(@NonNull options: FirestoreRecyclerOptions<Room>, val glide: RequestManager, private val callback: Listener, context: Context):
    FirestoreRecyclerAdapter<Room, RoomAdapter.RoomViewHolder>(options) {

    interface Listener {
        fun onDataChanged()
    }

    //private val mRoomList = roomList
    private val mInflater = LayoutInflater.from(context)

    // To get window size
    private var wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var display: Display = wm.defaultDisplay
    // To use dp when width/height are set programmatically
    private val scale: Float = context.resources.displayMetrics.density
    var pixels = (20.0f * scale + 0.5f).toInt()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val itemView: View = mInflater.inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int, model: Room) {
        holder.updateWithRoom(model, this.glide)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, RoomMessagesActivity::class.java)
            intent.putExtra("ROOM_UID", model.roomUid)
            intent.putExtra("GAME_ID", model.gameId)
            holder.itemView.context.startActivity(intent)
        }
    }

    class RoomViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        val usersLinearLayout: LinearLayout = itemView.findViewById(R.id.roomUsersLinearLayout)
        val numberOfUsersTextView: TextView = itemView.findViewById(R.id.numberOfUsersTextView)
        //val numberOfUsersLinearLayout: LinearLayout = itemView.findViewById(R.id.numberOfUsersLinearLayout)
        val gameDurationTextView: TextView = itemView.findViewById(R.id.gameDurationTextView)
        val gameIntensityTextView: TextView = itemView.findViewById(R.id.gameIntensityTextView)
        val roomLanguageTextView: TextView = itemView.findViewById(R.id.roomLanguageTextView)
        //val roomCardView: CardView = itemView.findViewById(R.id.roomCardView)

        fun updateWithRoom(room: Room, glide: RequestManager) {
            val numberOfUsersString = "Max : ${room.maxUsersInRoom} players"

            //holder.roomCardView.layoutParams.width = (display.width/3)+(display.width/9)

            /*for (user in mRoomList[position].users) {

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
                usernameTextView.setTypeface(usernameTextView.typeface, Typeface.BOLD)
                usernameTextView.ellipsize = TextUtils.TruncateAt.END
                usernameTextView.maxLines = 1

            }*/

            numberOfUsersTextView.text = numberOfUsersString
            Log.d("bite", numberOfUsersString)
            Log.d("bite", room.gameDuration)
            Log.d("bite", room.gameIntensity)
            Log.d("bite", room.language)

            gameDurationTextView.text = room.gameDuration
            gameIntensityTextView.text = room.gameIntensity
            roomLanguageTextView.text = room.language
        }
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