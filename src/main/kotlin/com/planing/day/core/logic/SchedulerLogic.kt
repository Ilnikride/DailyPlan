package com.planing.day.core.logic

import com.planing.day.core.entities.Chat
import com.planing.day.core.entities.PlannedMessage
import com.planing.day.core.messages.telegram.Routes
import com.planing.day.core.messages.telegram.entities.Message
import com.planing.day.core.messages.telegram.entities.Update
import com.planing.day.core.storage.Preserver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
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
    private var offset: Int? = 0

    @Scheduled(cron = "\${messaging.cron.expression}")//everyDay in 10 am
    fun sender() {
        val allChats = getAllChats()
        allChats.forEach {
            val sysdate = Instant.now()
                .atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS)
            val date = it.plannedMessage.sendDateTime.toInstant()
                .atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS)
            val message = it.plannedMessage.message
            if (sysdate == date) {
                telegramRoutes.sendMessage(message, it.id)
            }
        }
    }

    @Scheduled(fixedDelay = 1000)
    fun processUpdates() {
        val allUpdates = getUpdates(offset)
        allUpdates.forEach { update ->
            processCommand(update.message)
        }
        processOffset(allUpdates)
    }

    private fun processCommand(message: Message?) {
        when (message?.text) {
            START -> telegramRoutes.sendMessage("Hi, ${message.from.username}", message.chat.id)
            PLANS -> {
            }
            else -> parseRawMessage(message)
        }
    }

    private fun parseRawMessage(message: Message?) {
        message ?: return
        val stringsFromMessage = message.text.lines()
        val dateFromFirstString = try {
            SimpleDateFormat(DATE_FORMAT).parse(stringsFromMessage[0])
        } catch (e: ParseException) {
            throw e
        }
        saveChat(message.chat, dateFromFirstString, message.text)
    }

    private fun processOffset(allUpdates: List<Update>) {
        if (allUpdates.isNotEmpty()) {
            offset = allUpdates.maxOf { it.update_id } + 1
        }
    }

    private fun getAllChats(): List<Chat> {
        return preserver.getAllChats()
    }

    private fun saveChat(
        telegramChat: com.planing.day.core.messages.telegram.entities.Chat,
        dateFromFirstString: Date,
        textMessage: String
    ) {
        val internalChat = Chat().also {
            it.id = telegramChat.id
            it.userName = telegramChat.username
            it.plannedMessage = PlannedMessage().also {
                it.sendDateTime = dateFromFirstString
                it.message = textMessage
            }
        }
        preserver.saveChat(internalChat)
    }

    private fun getUpdates(offset: Int?): List<Update> {
        return telegramRoutes.getUpdates(offset).result
    }

    companion object {
        val START = "/start"
        val PLANS = "/plans"
        val DATE_FORMAT = "dd.MM.yyyy"
    }
}