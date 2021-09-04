package com.planing.day.core.storage

import com.planing.day.core.entities.Chat
import org.springframework.stereotype.Service

@Service
interface Preserver {
    fun saveChat(chat: Chat): Chat?
    fun getAllChats(): List<Chat>
}