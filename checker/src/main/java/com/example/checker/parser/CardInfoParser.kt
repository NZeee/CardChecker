package com.example.checker.parser

import org.json.JSONObject
import java.util.concurrent.Executors
import java.util.concurrent.Future


class CardInfoParser(private val api: CardInfoApi) {

    private val service = Executors.newSingleThreadExecutor()
    private var currentFuture: Future<*>? = null

    /**
     * Get a credit card info from API
     *
     * @param[cardNumber] a credit card number
     * @param[callback] that will be invoke after response has been got
     */
    fun parseInfo(cardNumber: String, callback: (Result<CardInfo>) -> Unit) {
        currentFuture?.cancel(false)
        currentFuture = service.submit {
            try {
                val response = api.getCardInfo(cardNumber)
                val json = JSONObject(response)
                val cardInfo = CardInfo(
                    scheme = json.optString("scheme"),
                    type = json.optString("type"),
                    countryName = json.optJSONObject("country")?.optString("name"),
                    bankName = json.optJSONObject("bank")?.optString("name")
                )
                val result = Result.success(cardInfo)
                callback.invoke(result)
            } catch (e: Exception) {
                val failure = Result.failure<CardInfo>(e)
                callback.invoke(failure)
            }
        }
    }

    /**
     * Stop all executing tasks
     */
    fun release() {
        // ignore any scheduled tasks
        service.shutdownNow()
    }
}
