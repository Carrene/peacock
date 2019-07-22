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

    companion object {
        val sUser = UserModel("989359323175", "123456", "")
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
        Mockito.`when`(mUserRepository.claim(sUser.phone, sUser.udid)).thenReturn(singleResponse)
        mRegistrationViewModel.claim(sUser.phone, sUser.udid)
        Mockito.verify(mUserRepository).claim(sUser.phone, sUser.udid)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status,
            Status.LOADING
        )
        delayer.onComplete()
        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).status, Status.SUCCESS)
        Assert.assertEquals(LiveDataTestUtil.getValue(mRegistrationViewModel.getClaimLiveData()).data, sUser)

    }

    //
    @Test
    fun claimUser_showBadRequestException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<UserModel>(
                BadRequestException.code,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        )
            .delaySubscription(delayer)
        Mockito.`when`(mUserRepository.claim(sUser.phone, sUser.udid)).thenReturn(singleResponse)
        mRegistrationViewModel.claim(sUser.phone, sUser.udid)
        Mockito.verify(mUserRepository).claim(sUser.phone, sUser.udid)
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
    }

    //
    @Test
    fun claimUser_showServerException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<UserModel>(
                ServerException.code,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        )
            .delaySubscription(delayer)
        Mockito.`when`(mUserRepository.claim(sUser.phone, sUser.udid)).thenReturn(singleResponse)
        mRegistrationViewModel.claim(sUser.phone, sUser.udid)
        Mockito.verify(mUserRepository).claim(sUser.phone, sUser.udid)
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
    }

    //
    @Test
    fun claimUser_showInvalidUdidOrPhoneException() {
        val delayer = PublishSubject.create<Void>()

        val singleResponse = Single.just(
            Response.error<UserModel>(
                InvalidUdidOrPhoneException.code,
                ResponseBody.create(MediaType.parse("text/plain"), "")
            )
        )
            .delaySubscription(delayer)
        Mockito.`when`(mUserRepository.claim(sUser.phone, sUser.udid)).thenReturn(singleResponse)
        mRegistrationViewModel.claim(sUser.phone, sUser.udid)
        Mockito.verify(mUserRepository).claim(sUser.phone, sUser.udid)
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
    }

}