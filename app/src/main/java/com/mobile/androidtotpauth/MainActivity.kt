package com.mobile.androidtotpauth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAdapter: AccountListAdapter? = null
    var globalList: ArrayList<AuthAccount>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val _recyclerView: RecyclerView = findViewById(R.id.list_items)
        _recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = AccountListAdapter(getLists())
        _recyclerView.adapter = mAdapter
        add_btn.setOnClickListener { onClickAddBtn() }
    }

    private fun getLists(): ArrayList<AuthAccount> {

        var lists = ArrayList<AuthAccount>()
        for (i in 1..10000) {
            lists.add(AuthAccount(i))
        }
        globalList = lists
        return lists
    }

    private fun onClickAddBtn() {
        var lists = ArrayList<AuthAccount>()
        lists.addAll(this.globalList!!)
        val item = AuthAccount(((Math.random() * 1000).toInt()))
        lists.add(item)
        mAdapter!!.swap(lists)
    }
}

