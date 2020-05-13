package be.technifutur.projet_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.projet_android.mockdata.MockUsers
import be.technifutur.projet_android.R
import be.technifutur.projet_android.adapters.FriendsListAdapter
import kotlinx.android.synthetic.main.fragment_friends.*

/**
 * A simple [Fragment] subclass.
 */
class FriendsFragment : Fragment() {

    private var mUserList =
        MockUsers.createUsers()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mAdapter = FriendsListAdapter(requireContext(), mUserList)
        friends_recycler_view.adapter = mAdapter
        friends_recycler_view.layoutManager = LinearLayoutManager(activity)
    }

}
