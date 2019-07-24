package de.netalic.peacock.data.repository

import de.netalic.peacock.base.BaseTest
import de.netalic.peacock.data.model.UserModel
import de.netalic.peacock.data.webservice.ApiInterface
import org.junit.After
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserRepositoryTest : BaseTest() {

    companion object {

        val sUser = UserModel("98123", "123", "")
    }

    private lateinit var mUserRepository: UserRepository

    @Mock
    private lateinit var apiInterface: ApiInterface


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mUserRepository = UserRepository(apiInterface)
    }

    @After
    fun tearDown() {
        Mockito.reset(apiInterface)
    }

    @Test
    fun claimUser_claimToApi() {
        mUserRepository.claim(sUser.phone, sUser.udid)
        Mockito.verify(apiInterface).claim(sUser.phone, sUser.udid)
    }


}
