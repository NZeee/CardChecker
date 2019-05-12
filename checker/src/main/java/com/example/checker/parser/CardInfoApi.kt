package com.example.checker.parser

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CardInfoApi(private val apiEndpoint: String) {

    /**
     * @param[cardNumber] a credit card number
     * @return json string contains info about the credit card
     *
     * @throws[java.io.IOException] while getting response from API
     */
    fun getCardInfo(cardNumber: String): String {
        val url = URL("$apiEndpoint$cardNumber")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "application/json")
            connect()
        }

        val stream = url.openStream()
        val br = BufferedReader(InputStreamReader(stream))
        val sb = StringBuilder()

        var line: String? = null
        while ({ line = br.readLine(); line }() != null) {
            sb.append(line + "\n")
        }
        val jsonString = sb.toString()

        connection.disconnect()
        stream.close()

        return jsonString
    }
}
