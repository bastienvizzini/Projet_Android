package be.technifutur.projet_android.adapters.game_detail

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.projet_android.R
import be.technifutur.projet_android.models.Message
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class RoomMessageAdapter(@NonNull options: FirestoreRecyclerOptions<Message>, val glide: RequestManager, private val callback: Listener, private val idCurrentUser: String):
    FirestoreRecyclerAdapter<Message, RoomMessageAdapter.MessageViewHolder>(options) {

    interface Listener {
        fun onDataChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_room_message, parent, false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        holder.updateWithMessage(model, this.idCurrentUser, this.glide)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        this.callback.onDataChanged()
    }

    class MessageViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {

        val imageViewProfile: ImageView = itemView.findViewById(R.id.activityRoomItemProfileImageCurrentUser)
        val imageViewProfileReceiver: ImageView = itemView.findViewById(R.id.activityRoomItemProfileImageReceiverUser)
        val textMessageContainer: LinearLayout = itemView.findViewById(R.id.activityRoomItemMessageContainerTextContainer)
        val textViewMessage: TextView = itemView.findViewById(R.id.activityRoomItemMessageTextView)
        val textViewDate: TextView = itemView.findViewById(R.id.activityRoomItemMessageTextViewDate)

        fun updateWithMessage(message: Message, currentUserId: String, glide: RequestManager) {

            // Check if current user is the sender
            val isCurrentUser: Boolean = message.userSender?.uid.equals(currentUserId)

            // Update message TextView
            textViewMessage.text = message.message
            textViewMessage.textAlignment =
                if (isCurrentUser) {
                    View.TEXT_ALIGNMENT_TEXT_END
                } else View.TEXT_ALIGNMENT_TEXT_START

            // Update date TextView
            if (message.dateCreated != null) {
                textViewDate.text = convertDateToHour(message.dateCreated)
            }

            if (isCurrentUser) {
                // Update profile picture ImageView
                imageViewProfile.visibility = View.VISIBLE
                imageViewProfileReceiver.visibility = View.INVISIBLE
                glide.load(/*message.userSender
                        ?.urlPicture ?:*/ R.drawable.default_profile_pic)
                        .circleCrop()
                        .into(imageViewProfile)
                textMessageContainer.gravity = Gravity.END

            } else {
                imageViewProfile.visibility = View.INVISIBLE
                imageViewProfileReceiver.visibility = View.VISIBLE
                if (message.userSender
                        ?.urlPicture != null) {
                    glide.load(message.userSender.urlPicture)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageViewProfileReceiver)
                }
            }
        }

        // ---
        private fun convertDateToHour(date: Date): String? {
            val dfTime: DateFormat = SimpleDateFormat("HH:mm")
            return dfTime.format(date)
        }
    }
}