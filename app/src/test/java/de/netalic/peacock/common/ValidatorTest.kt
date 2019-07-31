package de.netalic.peacock.common

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ValidatorTest {

    private lateinit var validator: Validator

    @Before
    fun setUp() {
        validator = Validator()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Has minimum length, true`() {
        val password = "abcdefgh"
        val result = validator.hasMinimumLength(password, 8)
        assertEquals(true, result)
    }

    @Test
    fun `Has minimum length, false`() {
        val password = "abcdefgh"
        val result = validator.hasMinimumLength(password, 8)
        assertEquals(true, result)
    }

    @Test
    fun `Has special character, true`() {
        val password = "aaaBbbb?12sA"
        assertEquals(true, validator.hasSpecialCharacters(password))
    }

    @Test
    fun `Has special character, false`() {
        val password = "abc2Agh"
        assertEquals(false, validator.hasSpecialCharacters(password))
    }

    @Test
    fun `Has capital letter, true`() {
        val password = "j1u9*Tyu"
        assertEquals(true, validator.hasCapitalLetter(password))
    }

    @Test
    fun `Has capital letter, false`() {
        val password = "j1u9*tyu"
        assertEquals(false, validator.hasCapitalLetter(password))
    }

    @Test
    fun `Has digit, true`() {
        val password = "Tu?88Sas0l"
        assertEquals(true, validator.hasDigit(password))
    }

    @Test
    fun `Has digit, false`() {
        val password = "Tu?AASasIl"
        assertEquals(false, validator.hasDigit(password))
    }

}