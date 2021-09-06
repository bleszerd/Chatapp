package com.github.bleszerd.chatapp.chat.view.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.bleszerd.chatapp.R
import com.github.bleszerd.chatapp.chat.view.adapter.ChatAdapter
import com.github.bleszerd.chatapp.commom.model.Message
import com.github.bleszerd.chatapp.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatToolbar: Toolbar
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatMessageRecycler: RecyclerView

    private val fakeMessages: MutableList<Message> = listOf(
        Message("Primeira!", 0),
        Message("Segunda", 1),
        Message("Olá!", 0),
        Message("Oi, como voce tá?", 1),
        Message("Olá!", 0),
        Message("Oi, como voce tá?", 1),
        Message("Olá!", 0),
        Message("Oi, como voce tá?", 1),
        Message("Olá!", 0),
        Message("Oi, como voce tá?", 1),
        Message("Olá!", 0),
        Message("Penultima!", 0),
        Message("Ultima", 1),
    ).reversed().toMutableList()

    private val messagesToAdd = listOf(
        Message("Message 01", 0),
        Message("Message 02", 0),
        Message("Message 03", 1),
        Message("Message 04", 0),
        Message("Message 05", 1),
        Message("Message 06", 0),
        Message("Message 07", 0),
        Message("Message 08", 1),
        Message("Message 09", 1),
        Message("Message 10", 1),
        Message("Message 11", 0),
        Message("Message 12", 0),
        Message("Message 13", 1),
    )

    var timesAdded = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupMessageRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_menu, menu)
        return true
    }

    private fun setupToolbar() {
        chatToolbar = binding.includeToolbar.chatToolbar
        setSupportActionBar(chatToolbar)
    }

    private fun setupMessageRecycler() {
        chatAdapter = ChatAdapter(fakeMessages)
        chatMessageRecycler = binding.chatMessageRecycler
        binding.chatImageSendButton.setOnClickListener {
            fakeMessages.add(
                0, Message("Nova mensagem", 0)
            )
            chatAdapter.notifyItemInserted(0)
            chatMessageRecycler.scrollToPosition(fakeMessages.indices.first)
        }
        chatMessageRecycler = binding.chatMessageRecycler
        chatMessageRecycler.adapter = chatAdapter
        chatMessageRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        chatMessageRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val lastPos = (chatMessageRecycler.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if (lastPos == fakeMessages.size - 1){
                    fakeMessages.addAll(messagesToAdd)
                    timesAdded += 1
                    println(timesAdded)
                    chatAdapter.notifyItemRangeInserted(fakeMessages.size - messagesToAdd.size, fakeMessages.size - 1)
                }

                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

}
