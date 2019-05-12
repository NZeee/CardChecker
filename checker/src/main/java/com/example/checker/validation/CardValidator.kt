package com.example.checker.validation

class CardValidator {

    companion object {
        /**
         * Minimum valid length of a credit card number
         */
        private const val MIN_VALID_LENGTH = 12

        /**
         * Maximum valid length of a credit card number
         */
        private const val MAX_VALID_LENGTH = 19
    }

    /**
     * @param[cardNumber] a credit card number without whitespaces
     * @return true if card number is valid, false otherwise
     */
    fun isValid(cardNumber: String): Boolean =
        checkNumberOnlyDigits(cardNumber)
            && checkNumberLeadingSymbol(cardNumber)
            && checkNumberLength(cardNumber)
            && checkNumberPassesLuhn(cardNumber)

    /**
     * @param[cardNumber] a credit card number
     * @return true if card number contains only digits, false otherwise
     */
    internal fun checkNumberOnlyDigits(cardNumber: String): Boolean =
        cardNumber.all { Character.isDigit(it) }

    /**
     * @param[cardNumber] a credit card number
     * @return true if card number has valid leading symbol, false otherwise
     */
    internal fun checkNumberLeadingSymbol(cardNumber: String): Boolean =
        !cardNumber.startsWith("0")

    /**
     * @param[cardNumber] a credit card number
     * @return true if card number has a valid length, false otherwise
     */
    internal fun checkNumberLength(cardNumber: String): Boolean =
        cardNumber.length in MIN_VALID_LENGTH..MAX_VALID_LENGTH

    /**
     * @param[cardNumber] a credit card number
     * @return true if card number passes Luhn check, false otherwise
     */
    internal fun checkNumberPassesLuhn(cardNumber: String): Boolean {
        val digits = cardNumber
            .map { Character.getNumericValue(it) }
            .toMutableList()

        for (i in (digits.size - 2) downTo 0 step 2) {
            var value = digits[i] * 2
            if (value > 9) {
                value = value % 10 + 1
            }
            digits[i] = value
        }

        return digits.sum() % 10 == 0
    }
}
