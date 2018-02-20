package com.mobile.androidtotpauth

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.account_row.view.*


/**
 * Created by Kartik Kumar Gujarati on 2/19/18 3:55 PM
 * Copyright (c) 2018. All rights reserved.
 */


class AccountListAdapter(var lists: ArrayList<AuthAccount>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun add() {
        val item = AuthAccount(((Math.random() * 1000).toInt()))
        lists.add(lists.size, item)
        notifyItemInserted(lists.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        var v = LayoutInflater.from(parent?.context).inflate(R.layout.account_row, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return lists.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bindData(lists[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(_list: AuthAccount) {
            itemView.account_user.text = _list.path
            itemView.totp_pin.text = _list.secret
        }
    }
}





