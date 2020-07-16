package be.technifutur.projet_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.projet_android.R
import be.technifutur.projet_android.adapters.MessagesAdapter
import be.technifutur.projet_android.mockdata.MockUsers
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.fragment_messages.*

/**
 * A simple [Fragment] subclass.
 */
class MessagesFragment : Fragment() {

    private var mUserList =
        MockUsers.createUsers()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mAdapter = MessagesAdapter(requireContext(), mUserList)
        messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        messagesRecyclerView.adapter = mAdapter

    }

}
