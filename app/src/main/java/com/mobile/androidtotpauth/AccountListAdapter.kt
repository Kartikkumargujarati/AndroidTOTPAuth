package com.mobile.androidtotpauth

import android.os.Handler
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.account_row.view.*


/**
 * Created by Kartik Kumar Gujarati on 2/19/18 3:55 PM
 * Copyright (c) 2018. All rights reserved.
 */


class AccountListAdapter(var list: ArrayList<AuthAccount>) : RecyclerView.Adapter<AccountListAdapter.ViewHolder>() {

    fun swap(newList: ArrayList<AuthAccount>) {
        updateItemsInternal(newList);
    }

    // This method pushes the work to the background thread
    fun updateItemsInternal(newItems: ArrayList<AuthAccount>) {
        val oldItems = ArrayList(this.list)
        val handler = Handler()
        Thread(Runnable {
            val diffResult = DiffUtil.calculateDiff(CustomDiffCallback(oldItems, newItems))
            handler.post { applyDiffResult(newItems, diffResult) }
        }).start()
    }

    // This method is called when the background work is done
    protected fun applyDiffResult(newItems: ArrayList<AuthAccount>,
                                  diffResult: DiffUtil.DiffResult) {
        dispatchUpdates(newItems, diffResult)
    }

    // This method does the work of actually updating the data and notifying the adapter
    protected fun dispatchUpdates(newItems: ArrayList<AuthAccount>,
                                  diffResult: DiffUtil.DiffResult) {
        this.list.clear()
        this.list.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AccountListAdapter.ViewHolder? {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.account_row, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: AccountListAdapter.ViewHolder, position: Int) {
        val account : AuthAccount = list[position]
        holder.accountUser.text = account.getProperPathName(account.path)
        holder.totpPin.text = OTPUtils.getOTPPin(account.secret).toString()
        holder.progressBar.visibility = View.VISIBLE

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            internal var previewsProgress = 0

            override fun run() {
                val currentProgress = ((System.currentTimeMillis() % (30 * 1000)) * holder.progressBar.max / 1000 / 30).toInt()
                holder.progressBar.progress = currentProgress
                val timeRemaining = (30 - ((System.currentTimeMillis() % (30 * 1000) ) / 1000))
                holder.progressBarCounter.text = timeRemaining.toString()
                if (timeRemaining.toInt() == 1) {
                    //do some stuff every 30 secs
                    holder.totpPin.text = OTPUtils.getOTPPin(account.secret).toString()
                }
                previewsProgress = currentProgress
                handler.postDelayed(this, 0)
            }
        }, (0).toLong())
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var accountUser: TextView
        internal var totpPin: TextView
        internal var progressBar: ProgressBar
        internal var progressBarCounter: TextView

        init {
            accountUser = itemView.account_user
            totpPin = itemView.totp_pin
            progressBar = itemView.timer_progress
            progressBarCounter = itemView.timer_counter_tv
        }
    }
}





