package de.netalic.peacock.ui.login.password

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.netalic.peacock.LiveDataTestUtil
import de.netalic.peacock.common.Validator
import de.netalic.peacock.data.model.Status
import org.junit.*

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PasswordLoginViewModelTest {

    private lateinit var mPasswordLoginViewModel: PasswordLoginViewModel
    private lateinit var mValidator: Validator

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mValidator = Validator()
        mPasswordLoginViewModel = PasswordLoginViewModel(mValidator)
    }

    @Test
    fun `On password entered, success` () {
        //Mockito.`when`(mValidator.hasMinimumLength("1234567A?",8)).thenReturn(true)
        mPasswordLoginViewModel.onPasswordEntered("1234567?Aa#")
        Assert.assertEquals(
            Status.SUCCESS,
            LiveDataTestUtil.getValue(mPasswordLoginViewModel.getResponse()).status
        )
    }

    @Test
    fun `On password entered, fail` () {
        mPasswordLoginViewModel.onPasswordEntered("1234567Aaaa")
        Assert.assertEquals(
            Status.FAILED,
            LiveDataTestUtil.getValue(mPasswordLoginViewModel.getResponse()).status
        )
    }

}