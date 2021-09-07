package com.github.bleszerd.chatapp.chat.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.bleszerd.chatapp.chat.repository.MessageRepository
import com.github.bleszerd.chatapp.commom.model.Message

class ChatViewModel : ViewModel() {

    private lateinit var messageRepository: MessageRepository

    val messages = MutableLiveData<MutableList<Message>>(mutableListOf())
    val newMessage = MutableLiveData<Message>()
    private val timesUpdated = MutableLiveData<Int>(0)
    val fetchedMessages = MutableLiveData<MutableList<Message>>(mutableListOf())

    fun sendMessage(text: String) {
        val message = Message(text, 0)

        messages.value?.add(message)
        newMessage.postValue(message)
    }

    fun fetchMoreMessages() {
        timesUpdated.postValue(timesUpdated.value!! + 1)
        val fetchedData = mutableListOf(
            Message("Tinha essa no servidor com ${timesUpdated.value} scrolls", 1),
            Message("Caralho que foda", 0),
        )

        fetchedMessages.postValue(fetchedData)
        messages.value!!.addAll(fetchedMessages.value!!)

    }
}