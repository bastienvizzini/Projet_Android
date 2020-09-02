package be.technifutur.projet_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import be.technifutur.projet_android.R
import be.technifutur.projet_android.adapters.friends.FriendsListAdapter
import be.technifutur.projet_android.mockdata.MockUsers
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
        val layoutManager = GridLayoutManager(activity, 1)
        layoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() { // à changer balec du gridlayoutmaintenant
            override fun getSpanSize(position: Int): Int {
                return if (mAdapter.isHeader(position)) {
                    layoutManager.spanCount
                } else {
                    1
                }
            }

        }
        friendsRecyclerView.layoutManager = layoutManager
        friendsRecyclerView.adapter = mAdapter

    }

}
