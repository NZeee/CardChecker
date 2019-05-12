package com.example.checker.parser

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.*

class CardInfoParserTest {

    private val mockApi = mock(CardInfoApi::class.java)
    private val parser = CardInfoParser(mockApi)
    private val cardNumber = "card number"

    @Test
    fun `get card info error`() {
        `when`(mockApi.getCardInfo(anyString())).thenThrow(RuntimeException())

        parser.parseInfo(cardNumber) { result ->
            assertTrue(result.isFailure)
        }
    }

    @Test
    fun `get card info success`() {
        val scheme = "visa"
        val country = "United States of America"
        val bank = "JPMORGAN CHASE BANK, N.A."
        val resolvedJson = """
            {
                "scheme": $scheme,
                "country": {
                    "name": $country
                },
                "bank": {
                    "name": $bank
                }
            }
        """

        `when`(mockApi.getCardInfo(anyString())).thenReturn(resolvedJson)

        val expectedCardInfo = CardInfo(
            scheme = scheme,
            type = null,
            countryName = country,
            bankName = bank
        )
        parser.parseInfo(cardNumber) { result ->
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull(), expectedCardInfo)
        }
    }
}
