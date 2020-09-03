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
import be.technifutur.projet_android.firebase_api.UserHelper
import be.technifutur.projet_android.models.Message
import be.technifutur.projet_android.models.User
import com.bumptech.glide.RequestManager
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

        private val imageViewProfile: ImageView = itemView.findViewById(R.id.activityRoomItemProfileImageCurrentUser)
        private val imageViewProfileReceiver: ImageView = itemView.findViewById(R.id.activityRoomItemProfileImageReceiverUser)
        private val textMessageContainer: LinearLayout = itemView.findViewById(R.id.activityRoomItemMessageContainerTextContainer)
        private val activityMessageContainer: LinearLayout = itemView.findViewById(R.id.activityRoomMessages)
        private val textViewMessage: TextView = itemView.findViewById(R.id.activityRoomItemMessageTextView)
        private val textViewDate: TextView = itemView.findViewById(R.id.activityRoomItemMessageTextViewDate)

        fun updateWithMessage(message: Message, currentUserId: String, glide: RequestManager) {

            activityMessageContainer.visibility = View.INVISIBLE
            // Update message TextView
            textViewMessage.text = message.message
            // Update date TextView
            if (message.dateCreated != null) {
                textViewDate.text = convertDateToHour(message.dateCreated)
            }

            UserHelper.getUser(message.userSenderUid!!).addOnSuccessListener { documentSnapshot ->
                val userSender = documentSnapshot.toObject(User::class.java)

                // Check if current user is the sender
                val isCurrentUser: Boolean = userSender?.uid.equals(currentUserId)

                if (isCurrentUser) {
                    // Update profile picture ImageView
                    imageViewProfile.visibility = View.VISIBLE
                    imageViewProfileReceiver.visibility = View.INVISIBLE
                    if (userSender
                            ?.urlPicture != "null"){ // null is stored as String somehow
                        glide.load(userSender?.urlPicture)
                            .circleCrop()
                            .into(imageViewProfile)
                    } else {
                        glide.load(R.drawable.default_profile_pic)
                            .circleCrop()
                            .into(imageViewProfile)
                    }
                    textViewMessage.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                    textMessageContainer.gravity = Gravity.END

                } else {
                    imageViewProfile.visibility = View.INVISIBLE
                    imageViewProfileReceiver.visibility = View.VISIBLE
                    if (userSender
                            ?.urlPicture != "null"){ // null is stored as String somehow
                        glide.load(userSender?.urlPicture)
                            .circleCrop()
                            .into(imageViewProfileReceiver)
                    } else {
                        glide.load(R.drawable.default_profile_pic)
                            .circleCrop()
                            .into(imageViewProfileReceiver)
                    }
                    textViewMessage.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                    textMessageContainer.gravity = Gravity.START
                }
                textViewMessage.setBackgroundResource(R.drawable.item_background_rounded_corner_dark)
                activityMessageContainer.visibility = View.VISIBLE
            }

        }

        // ---
        private fun convertDateToHour(date: Date): String? {
            val dfTime: DateFormat = SimpleDateFormat("HH:mm")
            return dfTime.format(date)
        }
    }
}