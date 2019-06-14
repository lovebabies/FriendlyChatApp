package com.example.friendlychatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){

    private var mDataList = ArrayList<FriendlyMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun addItem(message: FriendlyMessage) {
        mDataList.add(message)
        notifyDataSetChanged()
    }

    fun setDataList(dataList: ArrayList<FriendlyMessage>) {
        mDataList = dataList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(mDataList[position])
    }

    inner class MessageViewHolder(view: View): RecyclerView.ViewHolder(view)  {

        var mName = view.findViewById<TextView>(R.id.nameTextView)
        var mContent = view.findViewById<TextView>(R.id.messageTextView)
        fun bind(friendlyMessage: FriendlyMessage) {
            val isPhoto = friendlyMessage.photoUrl != null
            if (!isPhoto) {
                mName.text = friendlyMessage.name
                mContent.text = friendlyMessage.text
            }
        }
    }
}

