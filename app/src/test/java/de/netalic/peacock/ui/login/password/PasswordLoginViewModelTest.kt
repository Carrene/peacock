package de.netalic.peacock.ui.login.password

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.netalic.peacock.LiveDataTestUtil
import de.netalic.peacock.common.Validator
import org.junit.*

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PasswordLoginViewModelTest {

    private lateinit var mPasswordLoginViewModel: PasswordLoginViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mValidator: Validator

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mPasswordLoginViewModel = PasswordLoginViewModel(mValidator)
    }

    /*@Test
    fun onPasswordEntered() {
        val password = "12345678A"
        mPasswordLoginViewModel.onPasswordEntered(password)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mPasswordLoginViewModel.getResponse()).data,
            ResponseStatus.PASSWORD_MATCH
        )
        mPasswordLoginViewModel.onPasswordEntered(password)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mPasswordLoginViewModel.getResponse()).data,
            ResponseStatus.PASSWORD_NOT_MATCH
        )
    }*/

    @Test
    fun onPasswordEntered_atLeast8Characters(){
        Mockito.`when`(mValidator.hasMinimumLength(Mockito.anyString(), Mockito.anyInt())).thenReturn(true)
        mPasswordLoginViewModel.onPasswordEntered(Mockito.anyString())
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mPasswordLoginViewModel.getResponse()).data,
            ResponseStatus.SUCCESS_MINIMUM_CHARS
        )
    }
}