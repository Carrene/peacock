package de.netalic.peacock.util

import de.netalic.peacock.base.BaseTest
import de.netalic.peacock.data.model.EmailVerificationModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidatorUtilsTest : BaseTest() {

    private lateinit var mValidatorUtils: ValidatorUtils

    companion object {
        private val sEmail = EmailVerificationModel("ab@bc.cd")
        private val sWrongEmail = EmailVerificationModel("ab")
    }

    @Before
    fun setup() {
        mValidatorUtils = ValidatorUtils()
    }

    @Test
    fun emailValidator_invalidEmail() {

        Assert.assertEquals(mValidatorUtils.emailValidator(sWrongEmail.mEmail), false)
    }

    @Test
    fun emailValidator_validEmail() {

        Assert.assertEquals(mValidatorUtils.emailValidator(sEmail.mEmail), true)
    }
}