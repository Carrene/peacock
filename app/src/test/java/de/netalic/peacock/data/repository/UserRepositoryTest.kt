package de.netalic.peacock.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.netalic.peacock.base.BaseTest
import de.netalic.peacock.data.model.User
import de.netalic.peacock.data.webservice.InterfaceApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class UserRepositoryTest: BaseTest() {


    @get:Rule
    val instantExecutorRule=InstantTaskExecutorRule()

    companion object{

        val sUser=User(
            "+989211499302",
            "D89707AC55BAED9E8F23B826FB2A28E96095A190",
            "salimi",
            "android",
            "eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJSUzI1NiIsICJraWQiOiAiODk0NTkyNzQzMzlkMzNlZmNmNTE3MDc4NGM5Z" +
                    "GU1MjUzMjEyOWVmZiJ9.eyJpc3MiOiAiZmlyZWJhc2UtYWRtaW5zZGstaXp1MTNAYWxwaGEtZDY0ZTQuaWFtLmdzZXJ2aWNlYWN" +
                    "jb3VudC5jb20iLCAic3ViIjogImZpcmViYXNlLWFkbWluc2RrLWl6dTEzQGFscGhhLWQ2NGU0LmlhbS5nc2VydmljZWFjY291bn" +
                    "QuY29tIiwgImF1ZCI6ICJodHRwczovL2lkZW50aXR5dG9vbGtpdC5nb29nbGVhcGlzLmNvbS9nb29nbGUuaWRlbnRpdHkuaWRlb" +
                    "nRpdHl0b29sa2l0LnYxLklkZW50aXR5VG9vbGtpdCIsICJ1aWQiOiAiKzk4OTIxMTQ5OTMwMiIsICJpYXQiOiAxNTYzOTYwNDU3" +
                    "LCAiZXhwIjogMTU2Mzk2NDA1N30.HOUVBzwbmGwsglQHukGwrijlUuSZ241KdN2Eol3Gy80mmd4Kxoc58m3VhL71AWv3WS99eE7" +
                    "uz6xctl--yLPilhN3WJ_z2nxySqkhxiZ9OtaH_U8sTek63SJgfINeTFzJFpWHkT_DlQNPTVoH_AqbXjh0gZwdpVdMyoLmmuJf-W" +
                    "Iqx2y7BdwudCTiAqY_RoK7DdDwS8Jf28J-czpWi7Q4neUo1pC0WLi986u9n0mZcfIhWoVB_fV0A2-fWRV6yhT647sfHntC2eSg-" +
                    "OJZKO-MAyBsgKDIZm_ubX7m3LHD6rahpnUHtY8m33eJyD-EfZcKboRWalJkmje69abirvep1A",
            "082016"
        )
    }

    private lateinit var mUserRepository: UserRepository

    @Mock
    private lateinit var mInterfaceApi:InterfaceApi

    @Before
    fun setUp(){

        MockitoAnnotations.initMocks(this)
        mUserRepository=UserRepository(mInterfaceApi)
    }

    @Test
    fun bindUser_bindToApi(){

        mUserRepository.bind(sUser)
        Mockito.verify(mInterfaceApi).bind(sUser.phone, sUser.udid, sUser.deviceName, sUser.deviceType,
            sUser.firebaseRegistrationId, sUser.actionCode)

    }

}