package de.netalic.peacock.ui.emailVerification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.netalic.peacock.base.BaseTest
import de.netalic.peacock.data.exception.*
import de.netalic.peacock.data.model.EmailVerificationModel
import de.netalic.peacock.data.model.Status
import de.netalic.peacock.data.repository.EmailRepository
import de.netalic.peacock.util.LiveDataTestUtil
import de.netalic.peacock.util.ValidatorUtils
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class EmailVerificationViewModelTest : BaseTest() {

    companion object {
        private val sEmail = EmailVerificationModel("ab@bc.com")
        private const val TOKEN =
            "eyJhbGciOiJIUzI1NiIsImlhdCI6MTU2Mzk2Njk0OCwiZXhwIjoxNTcyNTY2OTQ4fQ.eyJpZCI6NCwiZGV2aWNlX2lkIjoxLCJwaG9uZSI6Iis5ODkzNTkzMjMxNzUiLCJyb2xlcyI6WyJjbGllbnQiXSwic2Vzc2lvbklkIjoiMTk5YmM4ZWItN2ExNC00YjBjLWI2YWMtNzQyZWQ1YTViNTk3IiwiZW1haWwiOiIiLCJpc0FjdGl2ZSI6ZmFsc2V9.sa0z7_2R-u94DlUQ0JEoaCHXi-ULaU5mJFy2KDjm_oM"
        private val sWrongEmail = EmailVerificationModel("abc")
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mEmailRepository: EmailRepository

    @Mock
    private lateinit var mValidatorUtils: ValidatorUtils

    private lateinit var mEmailVerificationViewModel: EmailVerificationViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mEmailVerificationViewModel = EmailVerificationViewModel(mEmailRepository, mValidatorUtils)
    }

    @After
    fun tearDown() {
        Mockito.reset(mEmailRepository)
        Mockito.reset(mValidatorUtils)
    }

    @Test
    fun setEmail_showSuccess() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.success(200, sEmail)
        ).delaySubscription(delayer)
        Mockito.`when`(
            mEmailRepository.setEmail(Mockito.anyString(), Mockito.anyString())
        ).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.emailValidator(Mockito.anyString())).thenReturn(true)
        mEmailVerificationViewModel.setEmail(TOKEN, sEmail.mEmail)
        Mockito.verify(mEmailRepository).setEmail(Mockito.anyString(), Mockito.anyString())
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.SUCCESS
        )
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData())
                .data, sEmail
        )

    }


    @Test
    fun setEmail_showEmailMissingException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<EmailVerificationModel>(
                400,
                ResponseBody.create(MediaType.parse("text/plain"), ""))).delaySubscription(delayer)
        Mockito.`when`(mEmailRepository.setEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.emailValidator(Mockito.anyString())).thenReturn(true)
        mEmailVerificationViewModel.setEmail(TOKEN, sEmail.mEmail)
        Mockito.verify(mEmailRepository).setEmail(Mockito.anyString(), Mockito.anyString())
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.FAILED
        )
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData())
                .throwable!!::class.java, EmailMissingException()::class.java
        )

    }

    @Test
    fun setEmail_showUnauthorizedException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<EmailVerificationModel>(
                401,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        ).delaySubscription(delayer)
        Mockito.`when`(
            mEmailRepository.setEmail(Mockito.anyString(), Mockito.anyString())
        ).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.emailValidator(Mockito.anyString())).thenReturn(true)
        mEmailVerificationViewModel.setEmail(
            TOKEN, sEmail.mEmail
        )
        Mockito.verify(mEmailRepository).setEmail(Mockito.anyString(), Mockito.anyString())
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.FAILED
        )
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData())
                .throwable!!::class.java, UnauthorizedException()::class.java
        )
    }

    @Test
    fun setEmail_InvalidEmailException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<EmailVerificationModel>(
                712,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        ).delaySubscription(delayer)
        Mockito.`when`(mEmailRepository.setEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.emailValidator(Mockito.anyString())).thenReturn(true)
        mEmailVerificationViewModel.setEmail(
            TOKEN, sEmail.mEmail
        )
        Mockito.verify(mEmailRepository).setEmail(Mockito.anyString(), Mockito.anyString())
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.FAILED
        )
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData())
                .throwable!!::class.java, InvalidEmailException()::class.java
        )
    }

    @Test
    fun setEmail_EmailAlreadyActivatedException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<EmailVerificationModel>(
                717,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        ).delaySubscription(delayer)
        Mockito.`when`(mEmailRepository.setEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.emailValidator(Mockito.anyString())).thenReturn(true)
        mEmailVerificationViewModel.setEmail(TOKEN, sEmail.mEmail)
        Mockito.verify(mEmailRepository).setEmail(Mockito.anyString(), Mockito.anyString())
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.FAILED
        )
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData())
                .throwable!!::class.java, EmailAlreadyActivatedException()::class.java
        )
    }

    @Test
    fun setEmail_EmailAlreadyExistException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<EmailVerificationModel>(
                718,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        ).delaySubscription(delayer)
        Mockito.`when`(mEmailRepository.setEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.emailValidator(Mockito.anyString())).thenReturn(true)
        mEmailVerificationViewModel.setEmail(TOKEN, sEmail.mEmail)
        Mockito.verify(mEmailRepository).setEmail(Mockito.anyString(), Mockito.anyString())
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.FAILED
        )
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData())
                .throwable!!::class.java, EmailAlreadyExistException()::class.java
        )
    }


    @Test
    fun claimUser_throwException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single
            .error<Response<EmailVerificationModel>>(Exception())
            .delaySubscription(delayer)
        Mockito.`when`(mEmailRepository.setEmail(Mockito.anyString(), Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.emailValidator(Mockito.anyString())).thenReturn(true)
        mEmailVerificationViewModel.setEmail(TOKEN, sEmail.mEmail)
        Mockito.verify(mEmailRepository).setEmail(Mockito.anyString(), Mockito.anyString())
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.FAILED
        )
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData())
                .throwable!!::class.java, Exception::class.java
        )
    }

    @Test
    fun emailValidator_invalidEmail() {

        Mockito.`when`(mValidatorUtils.emailValidator(Mockito.anyString())).thenReturn(false)
        mEmailVerificationViewModel.setEmail(TOKEN, sWrongEmail.mEmail)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).status,
            Status.FAILED
        )
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).throwable!!::class.java,
            InvalidEmailException::class.java
        )
        Assert.assertNull(LiveDataTestUtil.getValue(mEmailVerificationViewModel.getSetEmailLiveData()).data)
    }
}