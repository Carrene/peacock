package de.netalic.peacock.ui.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.netalic.peacock.base.BaseTest
import de.netalic.peacock.data.exception.BadRequestException
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

    //TODO-Tina Add tests for throwable

    companion object {
        val sUser = UserModel(mPhone = "989359323175", mUdid = "123456")
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
    fun phoneValidator_validPhoneNumber() {
        val phone = "123 456"
        mRegistrationViewModel.phoneValidator(phone)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getPhoneValidatorLiveData()).data,
            ResponseStatus.PHONE_VALID
        )
    }

    @Test
    fun phoneValidator_invalidPhoneNumber() {
        val phone = "12a456"
        mRegistrationViewModel.phoneValidator(phone)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getPhoneValidatorLiveData()).data,
            ResponseStatus.PHONE_INVALID
        )
    }

}