package com.planing.day.core.logic

import com.planing.day.core.entities.Chat
import com.planing.day.core.messages.telegram.Routes
import com.planing.day.core.messages.telegram.entities.Message
import com.planing.day.core.messages.telegram.entities.Update
import com.planing.day.core.storage.Preserver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.Date

@Component
class SchedulerLogic(
    @Autowired
    private val telegramRoutes: Routes,
    @Autowired
    private val preserver: Preserver
) {
    @Value("\${messaging.text}")
    private var text: String? = null

    @Volatile
    private var offset: Int? = null

    @Scheduled(cron = "\${messaging.cron.expression}")
    fun sender() {
        val allChats = getAllChats()
        allChats.forEach {
            telegramRoutes.sendMessage("$text", it.id)
        }
    }

    @Scheduled(fixedDelay = 1000)
    fun processUpdates() {
        val allUpdates = getUpdates(offset)
        allUpdates.forEach { update ->
            saveChat(update.message.chat)
        }
        processOffset(allUpdates)
    }

    private fun processOffset(allUpdates: List<Update>) {
        if (allUpdates.isNotEmpty()) {
            offset = allUpdates.maxOf { it.update_id } + 1
        }
    }

    private fun getAllChats(): List<Chat> {
        return preserver.getAllChats()
    }

    private fun saveChat(telegramChat: com.planing.day.core.messages.telegram.entities.Chat) {
        val internalChat = Chat().also {
            it.id = telegramChat.id
            it.userName = telegramChat.username
        }
        preserver.saveChat(internalChat)
    }

    private fun getUpdates(offset: Int?): List<Update> {
        return telegramRoutes.getUpdates(offset).result
    }
}