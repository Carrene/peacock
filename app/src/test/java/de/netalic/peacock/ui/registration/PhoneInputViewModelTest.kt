package de.netalic.peacock.ui.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.netalic.peacock.base.BaseTest
import de.netalic.peacock.data.exception.BadRequestException
import de.netalic.peacock.data.exception.InvalidPhoneNumberException
import de.netalic.peacock.data.exception.InvalidUdidOrPhoneException
import de.netalic.peacock.data.exception.ServerException
import de.netalic.peacock.data.model.Status
import de.netalic.peacock.data.model.UserModel
import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.util.LiveDataTestUtil
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class PhoneInputViewModelTest : BaseTest() {


    companion object {
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
        val wrongUserPhone = UserModel(
            "",
            "salimi",
            "a89211499302",
            "D89707AC55BAED9E8F23B826FB2A28E96095A190",
            "eyJ0eXAiOiAiSldUIiwgImFsZyI6ICJSUzI1NiIsICJraWQiOiAiODk0NTkyNzQzMzlkMzNlZmNmNTE3MDc" +
                    "4NGM5ZGU1MjUzMjEyOWVmZiJ9.eyJpc3MiOiAiZmlyZWJhc2UtYWRtaW5zZGstaXp1MTNAYWxwaGEtZDY0ZTQuaWFtLmd" +
                    "zZXJ2aWNlYWNjb3VudC5jb20iLCAic3ViIjogImZpcmViYXNlLWFkbWluc2RrLWl6dTEzQGFscGhhLWQ2NGU0LmlhbS5nc" +
                    "2VydmljZWFjY291bnQuY29tIiwgImF1ZCI6ICJodHRwczovL2lkZW50aXR5dG9vbGtpdC5nb29nbGVhcGlzLmNvbS9nb" +
                    "29nbGUuaWRlbnRpdHkuaWRlbnRpdHl0b29sa2l0LnYxLklkZW50aXR5VG9vbGtpdCIsICJ1aWQiOiAiKzk4OTIxMTQ5OTM" +
                    "wMiIsICJpYXQiOiAxNTYzOTYwNDU3LCAiZXhwIjogMTU2Mzk2NDA1N30.HOUVBzwbmGwsglQHukGwrijlUuSZ241KdN2Eo" +
                    "l3Gy80mmd4Kxoc58m3VhL71AWv3WS99eE7uz6xctl--yLPilhN3WJ_z2nxySqkhxiZ9OtaH_U8sTek63SJgfINeTFzJFp" +
                    "WHkT_DlQNPTVoH_AqbXjh0gZwdpVdMyoLmmuJf-WIqx2y7BdwudCTiAqY_RoK7DdDwS8Jf28J-czpWi7Q4neUo1pC0WLi" +
                    "986u9n0mZcfIhWoVB_fV0A2-fWRV6yhT647sfHntC2eSg-OJZKO-MAyBsgKDIZm_ubX7m3LHD6rahpnUHtY8m33eJyD-" +
                    "EfZcKboRWalJkmje69abirvep1A",
            "082016",
            "android"
        )
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mUserRepository: UserRepository

    private lateinit var mRegistrationViewModel: RegistrationViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mRegistrationViewModel = RegistrationViewModel(mUserRepository)
    }

    @After
    fun tearDown() {
        Mockito.reset(mUserRepository)
    }

    @Test
    fun claim_showSuccess() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.success(200, sUser)
        ).delaySubscription(delayer)
        Mockito.`when`(mUserRepository.claim(sUser.mPhone, sUser.mUdid)).thenReturn(singleResponse)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(sUser.mPhone, sUser.mUdid)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status, Status.SUCCESS)
        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).data, sUser)
        Assert.assertNull(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).throwable)

    }

    @Test
    fun claimUser_showBadRequestException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<UserModel>(
                400,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        )
            .delaySubscription(delayer)
        Mockito.`when`(mUserRepository.claim(sUser.mPhone, sUser.mUdid)).thenReturn(singleResponse)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(sUser.mPhone, sUser.mUdid)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status, Status.FAILED)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).throwable!!::class.java,
            BadRequestException::class.java
        )
        Assert.assertNull(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).data)
    }

    @Test
    fun claimUser_showServerException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<UserModel>(
                500,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        )
            .delaySubscription(delayer)
        Mockito.`when`(mUserRepository.claim(sUser.mPhone, sUser.mUdid)).thenReturn(singleResponse)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(sUser.mPhone, sUser.mUdid)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status, Status.FAILED)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).throwable!!::class.java,
            ServerException::class.java
        )
        Assert.assertNull(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).data)
    }


    @Test
    fun claimUser_showInvalidmUdidOrmPhoneException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<UserModel>(
                710,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        )
            .delaySubscription(delayer)
        Mockito.`when`(mUserRepository.claim(sUser.mPhone, sUser.mUdid)).thenReturn(singleResponse)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(sUser.mPhone, sUser.mUdid)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status, Status.FAILED)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).throwable!!::class.java,
            InvalidUdidOrPhoneException::class.java
        )
        Assert.assertNull(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).data)
    }

    @Test
    fun claimUser_throwException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.error<Response<UserModel>>(Exception())
            .delaySubscription(delayer)

        Mockito.`when`(mUserRepository.claim(sUser.mPhone, sUser.mUdid)).thenReturn(singleResponse)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(sUser.mPhone, sUser.mUdid)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()

        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status, Status.FAILED)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).throwable!!::class.java,
            Exception::class.java
        )
        Assert.assertNull(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).data)
    }

    @Test
    fun phoneValidator_invalidPhoneNumber() {

        mRegistrationViewModel.claim(wrongUserPhone.mPhone, wrongUserPhone.mUdid)
        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status, Status.FAILED)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).throwable!!::class.java,
            InvalidPhoneNumberException::class.java
        )
        Assert.assertNull(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).data)
    }

}