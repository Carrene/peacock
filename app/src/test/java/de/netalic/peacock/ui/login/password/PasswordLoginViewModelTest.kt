package de.netalic.peacock.ui.login.password

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.netalic.peacock.LiveDataTestUtil
import org.junit.*

import org.junit.Assert.*

class PasswordLoginViewModelTest {

    private lateinit var mPasswordLoginViewModel: PasswordLoginViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mPasswordLoginViewModel = PasswordLoginViewModel()
    }

    /*@Test
    fun onPasswordEntered() {

    }

    @Test
    fun onPasswordRepeated() {
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
}