package de.netalic.peacock.data.repository

import de.netalic.peacock.base.BaseTest
import de.netalic.peacock.data.model.EmailVerificationModel
import de.netalic.peacock.data.webservice.ApiInterface
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EmailVerificationRepositoryTest : BaseTest() {

    companion object {

        val sEmail = EmailVerificationModel("tina.t2aq@gmail.com")
        const val token =
            "eyJhbGciOiJIUzI1NiIsImlhdCI6MTU2Mzk2Njk0OCwiZXhwIjoxNTcyNTY2OTQ4fQ.eyJpZCI6NCwiZGV2aWNlX2lkIjoxLCJwaG9uZSI6Iis5ODkzNTkzMjMxNzUiLCJyb2xlcyI6WyJjbGllbnQiXSwic2Vzc2lvbklkIjoiMTk5YmM4ZWItN2ExNC00YjBjLWI2YWMtNzQyZWQ1YTViNTk3IiwiZW1haWwiOiIiLCJpc0FjdGl2ZSI6ZmFsc2V9.sa0z7_2R-u94DlUQ0JEoaCHXi-ULaU5mJFy2KDjm_oM"
    }

    private lateinit var mEmailVerificationRepository: EmailRepository

    @Mock
    private lateinit var apiInterface: ApiInterface


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mEmailVerificationRepository = EmailRepository(apiInterface)
    }

    @After
    fun tearDown() {
        Mockito.reset(apiInterface)
    }

    @Test
    fun claimUser_claimToApi() {
        mEmailVerificationRepository.setEmail(token, sEmail.email)
        Mockito.verify(apiInterface).setEmail(token, sEmail.email)
    }

}