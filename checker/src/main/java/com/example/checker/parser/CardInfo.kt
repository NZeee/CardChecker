package com.example.checker.parser

/**
 * @property scheme credit card scheme (i.e. Visa, Mastercard)
 * @property type credit card type (debit or credit)
 * @property countryName name of the country where the credit card was issued in
 * @property bankName name of the bank issued the credit card
 */
data class CardInfo(
    val scheme: String? = null,
    val type: String? = null,
    val countryName: String? = null,
    val bankName: String? = null
)
