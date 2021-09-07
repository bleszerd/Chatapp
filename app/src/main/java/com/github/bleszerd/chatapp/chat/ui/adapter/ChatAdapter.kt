package com.github.bleszerd.chatapp.chat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.bleszerd.chatapp.R
import com.github.bleszerd.chatapp.commom.model.Message

//1 = Sender
//2 = Recipient

class ChatAdapter(private var messages: MutableList<Message>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return when(viewType){
            0 -> ChatSenderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_message_sender_item, parent, false))
            else -> ChatRecipientViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_message_recipient_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemViewType(position: Int): Int {
        return messages[position].type
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class ChatSenderViewHolder(itemView: View) : ChatViewHolder(itemView){
        override fun bind(message: Message) {
            val messageText = itemView.findViewById<TextView>(R.id.textItemSenderTextMessage)
            messageText.text = message.message
        }
    }

    class ChatRecipientViewHolder(itemView: View) : ChatViewHolder(itemView){
        override fun bind(message: Message) {
            val messageText = itemView.findViewById<TextView>(R.id.textItemRecipientTextMessage)
            messageText.text = message.message
        }
    }

    abstract class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(message: Message)
    }

}