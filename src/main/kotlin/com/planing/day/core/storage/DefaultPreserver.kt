package com.planing.day.core.storage

import com.planing.day.core.entities.Chat
import org.springframework.stereotype.Component

@Component
class DefaultPreserver : Preserver {
    private val chats = mutableListOf<Chat>()
    override fun saveChat(chat: Chat): Chat? {
        val chatIds = chats.map { it.id }
        return if (!chatIds.contains(chat.id)) {
            chats.add(chat)
            chat
        } else null
    }

    override fun getAllChats(): List<Chat> {
        return chats
    }
}