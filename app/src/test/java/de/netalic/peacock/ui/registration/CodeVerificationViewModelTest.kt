package de.netalic.peacock.ui.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.netalic.peacock.base.BaseTest
import de.netalic.peacock.data.exception.ActivationCodeIsNotValid
import de.netalic.peacock.data.exception.BadRequestException
import de.netalic.peacock.data.exception.InvalidDeviceName
import de.netalic.peacock.data.exception.InvalidUdidOrPhone
import de.netalic.peacock.data.model.Status
import de.netalic.peacock.data.model.UserModel
import de.netalic.peacock.data.repository.UserRepository
import de.netalic.peacock.util.LiveDataTestUtil
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.concurrent.TimeUnit


class CodeVerificationViewModelTest : BaseTest() {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mUserRepository: UserRepository

    private lateinit var mCodeVerificationViewModel: CodeVerificationViewModel

    private val mResponseBody = ResponseBody.create(
        MediaType.parse("text/plain"), ""
    )
    private val mUser = UserModel(

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

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)
        mCodeVerificationViewModel = CodeVerificationViewModel(mUserRepository)

    }

    @Test
    fun binUser_showSuccess() {

        Mockito.`when`(mUserRepository.bind(mUser)).thenReturn(Single.just(Response.success(mResponseBody)))
        mCodeVerificationViewModel.bind(mUser)
        Mockito.verify(mUserRepository).bind(mUser)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).status,
            Status.SUCCESS
        )

        Assert.assertEquals(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).data, mResponseBody)
        Assert.assertNull(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).throwable)
    }

    @Test
    fun bindUser_showBadRequest() {

        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<ResponseBody>(400, mResponseBody)
        ).delaySubscription(delayer)

        Mockito.`when`(mUserRepository.bind(mUser)).thenReturn(singleResponse)

        mCodeVerificationViewModel.bind(mUser)

        Mockito.verify(mUserRepository).bind(mUser)

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).status,
            Status.LOADING
        )

        delayer.onComplete()

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).status,
            Status.FAILED
        )

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData())
                .throwable!!::class.java, BadRequestException::class.java
        )

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).data,
            null
        )
    }

    @Test
    fun bindUser_invalidUdidOrPhone() {

        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<ResponseBody>(710, mResponseBody)
        ).delaySubscription(delayer)

        Mockito.`when`(mUserRepository.bind(mUser)).thenReturn(singleResponse)

        mCodeVerificationViewModel.bind(mUser)

        Mockito.verify(mUserRepository).bind(mUser)

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).status,
            Status.LOADING
        )

        delayer.onComplete()

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).status,
            Status.FAILED
        )

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData())
                .throwable!!::class.java, InvalidUdidOrPhone::class.java
        )

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).data,
            null
        )
    }

    @Test
    fun bindUser_invalidDeviceName() {

        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<ResponseBody>(716, mResponseBody)
        ).delaySubscription(delayer)

        Mockito.`when`(mUserRepository.bind(mUser)).thenReturn(singleResponse)

        mCodeVerificationViewModel.bind(mUser)

        Mockito.verify(mUserRepository).bind(mUser)

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).status,
            Status.LOADING
        )

        delayer.onComplete()

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).status,
            Status.FAILED
        )

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData())
                .throwable!!::class.java, InvalidDeviceName::class.java
        )

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).data,
            null
        )
    }

    @Test
    fun bindUser_activationCodeIsNotValid() {

        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<ResponseBody>(711, mResponseBody)
        ).delaySubscription(delayer)

        Mockito.`when`(mUserRepository.bind(mUser)).thenReturn(singleResponse)

        mCodeVerificationViewModel.bind(mUser)

        Mockito.verify(mUserRepository).bind(mUser)

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).status,
            Status.LOADING
        )

        delayer.onComplete()

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).status,
            Status.FAILED
        )

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData())
                .throwable!!::class.java, ActivationCodeIsNotValid::class.java
        )

        Assert.assertEquals(
            LiveDataTestUtil.getValue(mCodeVerificationViewModel.getBindLiveData()).data,
            null
        )
    }

    @Test
    fun setTimer() {

        mCodeVerificationViewModel.setTimer(3, testScheduler, testScheduler)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        Assert.assertEquals(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getTimerLiveData()).data.toString(), String.format("%02d:%02d ", 0, 3))
        Assert.assertEquals(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getTimerLiveData()).status, Status.SUCCESS)
        Assert.assertNull(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getTimerLiveData()).throwable)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        Assert.assertEquals(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getTimerLiveData()).data.toString(), String.format("%02d:%02d ", 0, 2))
        Assert.assertEquals(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getTimerLiveData()).status, Status.SUCCESS)
        Assert.assertNull(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getTimerLiveData()).throwable)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        Assert.assertEquals(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getTimerLiveData()).data.toString(), "RESEND")
        Assert.assertEquals(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getTimerLiveData()).status, Status.SUCCESS)
        Assert.assertNull(LiveDataTestUtil.getValue(mCodeVerificationViewModel.getTimerLiveData()).throwable)

    }

}