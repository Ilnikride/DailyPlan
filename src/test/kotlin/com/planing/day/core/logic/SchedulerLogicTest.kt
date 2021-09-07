package com.planing.day.core.logic

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.planing.day.core.entities.PlannedMessage
import com.planing.day.core.messages.telegram.Routes
import com.planing.day.core.messages.telegram.entities.Chat
import com.planing.day.core.messages.telegram.entities.Message
import com.planing.day.core.messages.telegram.entities.Update
import com.planing.day.core.messages.telegram.entities.UpdateResponse
import com.planing.day.core.messages.telegram.entities.User
import com.planing.day.core.storage.Preserver
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.Date


class SchedulerLogicTest {
    private val telegramRoutes: Routes = mock()
    private val preserver: Preserver = mock()
    private val testable: SchedulerLogic = SchedulerLogic(telegramRoutes, preserver)

    @Test
    fun processUpdatesTest() {
        whenever(telegramRoutes.getUpdates(any())).thenReturn(UpdateResponse().also {
            it.ok = true
            it.result = listOf(buildUpdate("01.02.2022"))
        })
        testable.processUpdates()
        val expectedDate = SimpleDateFormat("dd.MM.yyyy").parse("01.02.2022")
        val expectedChat = com.planing.day.core.entities.Chat().also {
            it.id = CHAT_ID
            it.userName = USERNAME
            it.plannedMessage = PlannedMessage().also {
                it.sendDateTime = expectedDate
                it.message = "01.02.2022"
            }
        }
        verify(preserver, times(1)).saveChat(expectedChat)
    }

    @Test
    fun senderTest() {
        val currentDate = Date()
        val expectedChat = com.planing.day.core.entities.Chat().also {
            it.id = CHAT_ID
            it.userName = USERNAME
            it.plannedMessage = PlannedMessage().also {
                it.sendDateTime = currentDate
                it.message = "01.02.2022"
            }
        }
        whenever(preserver.getAllChats()).thenReturn(listOf(expectedChat))
        testable.sender()
        verify(telegramRoutes, times(1)).sendMessage(expectedChat.plannedMessage.message, expectedChat.id)
    }

    private fun buildUpdate(text: String): Update {
        return Update().also {
            it.update_id = UPDATE_ID
            it.message = Message().also {
                it.text = text
                it.from = User().also { it.username = USERNAME }
                it.chat = Chat().also {
                    it.id = CHAT_ID
                    it.username = USERNAME
                }
            }
        }
    }

    companion object {
        const val USERNAME = "username"
        const val CHAT_ID = 1
        const val UPDATE_ID = 1
    }
}
