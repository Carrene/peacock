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

class PhoneInputViewModelTest : BaseTest() {


    companion object {
        private val sUser = UserModel(mPhone = "9812345678", mUdid = "123456")
        private val sWrongUserPhone = UserModel(mPhone = "a12345", mUdid = "123456")
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mUserRepository: UserRepository

    @Mock
    private lateinit var mValidatorUtils: ValidatorUtils

    private lateinit var mRegistrationViewModel: RegistrationViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mRegistrationViewModel = RegistrationViewModel(mUserRepository,mValidatorUtils)
    }

    @After
    fun tearDown() {
        Mockito.reset(mUserRepository)
        Mockito.reset(mValidatorUtils)
    }

    @Test
    fun claim_showSuccess() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.success(200, sUser)
        ).delaySubscription(delayer)
        Mockito.`when`(mUserRepository.claim(Mockito.anyString(),Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.phoneValidator(Mockito.anyString())).thenReturn(true)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(Mockito.anyString(),Mockito.anyString())
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
        Mockito.`when`(mUserRepository.claim(Mockito.anyString(),Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.phoneValidator(Mockito.anyString())).thenReturn(true)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(Mockito.anyString(),Mockito.anyString())
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
        Mockito.`when`(mUserRepository.claim(Mockito.anyString(),Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.phoneValidator(Mockito.anyString())).thenReturn(true)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(Mockito.anyString(),Mockito.anyString())
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
        Mockito.`when`(mUserRepository.claim(Mockito.anyString(),Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.phoneValidator(Mockito.anyString())).thenReturn(true)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(Mockito.anyString(),Mockito.anyString())
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

        Mockito.`when`(mUserRepository.claim(Mockito.anyString(),Mockito.anyString())).thenReturn(singleResponse)
        Mockito.`when`(mValidatorUtils.phoneValidator(Mockito.anyString())).thenReturn(true)
        mRegistrationViewModel.claim(sUser.mPhone, sUser.mUdid)
        Mockito.verify(mUserRepository).claim(Mockito.anyString(),Mockito.anyString())
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

        Mockito.`when`(mValidatorUtils.phoneValidator(Mockito.anyString())).thenReturn(false)
        mRegistrationViewModel.claim(sWrongUserPhone.mPhone, sWrongUserPhone.mUdid)
        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status, Status.FAILED)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).throwable!!::class.java,
            InvalidPhoneNumberException::class.java
        )
        Assert.assertNull(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).data)
    }

}