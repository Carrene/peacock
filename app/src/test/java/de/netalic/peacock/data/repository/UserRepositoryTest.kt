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

    val sUser = UserModel(

        mName = "salimi",
        mPhone = "+989211499302",
        mUdid = "D89707AC55BAED9E8F23B826FB2A28E96095A190",
        mFirebaseToken = "eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJSUzI1NiIsICJraWQiOiAiODk0NTkyNzQzMzlkMzNlZmNmNTE3MDc" +
                "4NGM5ZGU1MjUzMjEyOWVmZiJ9.eyJpc3MiOiAiZmlyZWJhc2UtYWRtaW5zZGstaXp1MTNAYWxwaGEtZDY0ZTQuaWFtLmd" +
                "zZXJ2aWNlYWNjb3VudC5jb20iLCAic3ViIjogImZpcmViYXNlLWFkbWluc2RrLWl6dTEzQGFscGhhLWQ2NGU0LmlhbS5nc" +
                "2VydmljZWFjY291bnQuY29tIiwgImF1ZCI6ICJodHRwczovL2lkZW50aXR5dG9vbGtpdC5nb29nbGVhcGlzLmNvbS9nb" +
                "29nbGUuaWRlbnRpdHkuaWRlbnRpdHl0b29sa2l0LnYxLklkZW50aXR5VG9vbGtpdCIsICJ1aWQiOiAiKzk4OTIxMTQ5OTM" +
                "wMiIsICJpYXQiOiAxNTYzOTYwNDU3LCAiZXhwIjogMTU2Mzk2NDA1N30.HOUVBzwbmGwsglQHukGwrijlUuSZ241KdN2Eo" +
                "l3Gy80mmd4Kxoc58m3VhL71AWv3WS99eE7uz6xctl--yLPilhN3WJ_z2nxySqkhxiZ9OtaH_U8sTek63SJgfINeTFzJFp" +
                "WHkT_DlQNPTVoH_AqbXjh0gZwdpVdMyoLmmuJf-WIqx2y7BdwudCTiAqY_RoK7DdDwS8Jf28J-czpWi7Q4neUo1pC0WLi" +
                "986u9n0mZcfIhWoVB_fV0A2-fWRV6yhT647sfHntC2eSg-OJZKO-MAyBsgKDIZm_ubX7m3LHD6rahpnUHtY8m33eJyD-" +
                "EfZcKboRWalJkmje69abirvep1A",
        mActivateToken = "082016",
        mDeviceType = "android"
    )


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
        mUserRepository.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(apiInterface).claim(sUser.mPhone, sUser.mUdid)
    }

    @Test
    fun bindUser_bindToApi() {

        mUserRepository.bind(sUser)
        Mockito.verify(apiInterface).bind(
            sUser.mPhone, sUser.mUdid, sUser.mName!!, sUser.mDeviceType,
            sUser.mFirebaseToken, sUser.mActivateToken
        )

    }

}
