package com.github.bleszerd.chatapp.chat.ui.view

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.bleszerd.chatapp.R
import com.github.bleszerd.chatapp.chat.ui.adapter.ChatAdapter
import com.github.bleszerd.chatapp.chat.ui.viewmodel.ChatViewModel
import com.github.bleszerd.chatapp.commom.model.Message
import com.github.bleszerd.chatapp.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatToolbar: Toolbar
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatMessageRecycler: RecyclerView

    private val chatViewModel : ChatViewModel by viewModels()

    private val chatScrollListener = object: RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            val lastPosition = (chatMessageRecycler.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            if (lastPosition == chatViewModel.messages.value!!.size - 1 && lastPosition > -1){
                chatViewModel.fetchMoreMessages()
                chatAdapter.notifyItemRangeInserted(chatViewModel.messages.value!!.size - chatViewModel.fetchedMessages.value!!.size, chatViewModel.messages.value!!.size - 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupUiListeners()
        setupObservers()
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

    private fun setupUiListeners() {
        binding.chatImageSendButton.setOnClickListener {
            val text = binding.chatEditTextMessage.text.toString()
            chatViewModel.sendMessage(text)
        }
    }

    private fun setupObservers() {
        chatViewModel.newMessage.observe(this, {
            chatAdapter.notifyItemInserted(0)
            chatMessageRecycler.scrollToPosition(chatViewModel.messages.value!!.indices.first)
        })

        chatViewModel.fetchedMessages.observe(this, {
            println(it.size)
        })
    }

    private fun setupMessageRecycler() {
        chatAdapter = ChatAdapter(chatViewModel.messages.value!!)
        chatMessageRecycler = binding.chatMessageRecycler

        chatMessageRecycler.adapter = chatAdapter
        chatMessageRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        chatMessageRecycler.addOnScrollListener(chatScrollListener)
    }

}
