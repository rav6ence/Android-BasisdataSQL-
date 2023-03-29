package com.serafine.basisdatasql

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyFriendsFragment : Fragment() {
    private var fabAddFriend : FloatingActionButton ?= null
    private var listMyFriends: RecyclerView? = null
    private var listTeman: List<MyFriend>? = null
    private var db: AppDatabase? = null
    private var myFriendsDao: MyFriendsDao? = null

    companion object {
        fun newInstance() : MyFriendsFragment {
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_friends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDb()
        initView()
    }

    private fun initLocalDb(){
        db = AppDatabase.getAppDataBase(requireActivity())
        myFriendsDao = db?.myFriendsDao()
    }

    private fun initView() {
        fabAddFriend = activity?.findViewById(R.id.fabAddFriend)
        listMyFriends = activity?.findViewById(R.id.listMyFriends)

        fabAddFriend?.setOnClickListener { (activity as MainActivity).tampilMyFriendsAddFragment() }
        ambilDataTeman()
    }

    private fun ambilDataTeman(){
        listTeman = ArrayList()
        myFriendsDao?.ambilSemuaTeman()?.observe(requireActivity()) {r -> listTeman = r
        when {
            listTeman?.size == 0 -> tampilToast("Belum ada data teman")
            else -> {
                tampilTeman()
            }
        }}
    }

    private fun tampilToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        fabAddFriend = null
        listMyFriends = null
    }

    private fun simulasiDataTeman() {
        listTeman = ArrayList()
    }

    private fun tampilTeman() {
        listMyFriends?.layoutManager = LinearLayoutManager(activity)
        listMyFriends?.adapter = MyFriendsAdapter(requireActivity(),
        listTeman!! as ArrayList<MyFriend>)
    }

    //override fun onDestroy() {
    //    super.onDestroy()
    //    this.clearFindViewByIdCache()
    //}
}