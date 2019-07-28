package de.netalic.peacock.ui.login.pattern

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ehsanmashhadi.samplestructure.util.LiveDataTestUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PatternViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mPatternViewModel: PatternViewModel

    @Before
    fun setUp() {
        mPatternViewModel = PatternViewModel()
    }

    @Test
    fun patternViewModel_drawPatternSuccess() {

        val pattern = "123456"
        mPatternViewModel.onPatternListener(pattern)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mPatternViewModel.getResponse()).data,
            ResponseStatus.FIRST_SUCCESS
        )
        mPatternViewModel.onPatternListener(pattern)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mPatternViewModel.getResponse()).data,
            ResponseStatus.SECOND_SUCCESS
        )
    }

    @Test
    fun patternViewModel_drawPatternFailed() {

        val firstPattern = "123456"
        val secondPattern = "654321"

        mPatternViewModel.onPatternListener(firstPattern)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mPatternViewModel.getResponse()).data,
            ResponseStatus.FIRST_SUCCESS
        )
        mPatternViewModel.onPatternListener(secondPattern)
        Assert.assertEquals(
            LiveDataTestUtil.getValue(mPatternViewModel.getResponse()).data,
            ResponseStatus.FAILED
        )
    }
}