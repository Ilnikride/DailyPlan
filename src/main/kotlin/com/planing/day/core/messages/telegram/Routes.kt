package com.planing.day.core.messages.telegram

import com.planing.day.core.messages.telegram.entities.Message
import com.planing.day.core.messages.telegram.entities.Update
import com.planing.day.core.messages.telegram.entities.UpdateResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "telegram", url = "https://api.telegram.org")
interface Routes {
    @GetMapping("/bot\${telegram.token}/getUpdates")
    fun getUpdates( @PathVariable offset: Int?): UpdateResponse

    @PostMapping(
        value = arrayOf("/bot\${telegram.token}/sendMessage"),
        headers = arrayOf("Content-Type", "application/json")
    )
    fun sendMessage(
        @PathVariable text: String,
        @PathVariable chat_id: Int
    ): Message
}