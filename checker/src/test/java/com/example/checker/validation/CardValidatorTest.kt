package com.example.checker.validation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class CardValidatorTest {

    private val cardValidator = CardValidator()

    @Test
    fun `is number in range 12-19 symbols`() {
        assertFalse(cardValidator.checkNumberLength("0".repeat(11)))
        assertFalse(cardValidator.checkNumberLength("0".repeat(20)))
        assertTrue(cardValidator.checkNumberLength("0".repeat(15)))
    }

    @Test
    fun `does number contain only digits`() {
        assertFalse(cardValidator.checkNumberOnlyDigits("1234567890z"))
        assertTrue(cardValidator.checkNumberOnlyDigits("1234567890"))
    }

    @Test
    fun `does number contain no leading 0`() {
        assertFalse(cardValidator.checkNumberLeadingSymbol("01234567890"))
        assertTrue(cardValidator.checkNumberLeadingSymbol("1234567890"))
    }

    @Test
    fun `does number pass Luhn check`() {
        assertFalse(cardValidator.checkNumberPassesLuhn("4111111111111112"))
        assertTrue(cardValidator.checkNumberPassesLuhn("4111111111111111"))
    }

    @Test
    fun `is task sample data valid`() {
        assertTrue(cardValidator.isValid("4929804463622139"))
        assertFalse(cardValidator.isValid("4929804463622138"))
        assertTrue(cardValidator.isValid("6762765696545485"))
        assertFalse(cardValidator.isValid("5212132012291762"))
        assertTrue(cardValidator.isValid("6210948000000029"))
    }
}
