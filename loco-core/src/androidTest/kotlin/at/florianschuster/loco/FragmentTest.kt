package at.florianschuster.loco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class FragmentTest {

    @Test
    fun create_destroy() {
        val scenario = launchFragmentWith<TestFragment>(TestType.CreateDestroy)

        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.onFragment { fragment -> assertEquals(1, fragment.callCounter.get()) }
    }

    @Test
    fun start_stop() {
        val scenario = launchFragmentWith<TestFragment>(TestType.StartStop)

        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onFragment { fragment -> assertFalse(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onFragment { fragment -> assertFalse(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.onFragment { fragment -> assertEquals(3, fragment.callCounter.get()) }
    }

    @Test
    fun resume_pause() {
        val scenario = launchFragmentWith<TestFragment>(TestType.ResumePause)

        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onFragment { fragment -> assertFalse(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onFragment { fragment -> assertFalse(assertNotNull(fragment.job).isActive) }

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onFragment { fragment -> assertTrue(assertNotNull(fragment.job).isActive) }

        scenario.onFragment { fragment -> assertEquals(3, fragment.callCounter.get()) }
    }

    class TestFragment : Fragment() {
        var job: Job? = null
        val callCounter = AtomicInteger(0)

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val block: suspend CoroutineScope.() -> Unit = {
                job = IndefiniteTestFlow.launchIn(this)
                callCounter.getAndIncrement()
            }

            when (requireTestTypeInBundle()) {
                TestType.CreateDestroy -> {
                    launchOnViewLifecycleCreateCancelOnViewLifecycleDestroy(block = block)
                }
                TestType.StartStop -> {
                    launchOnViewLifecycleStartCancelOnViewLifecycleStop(block = block)
                }
                TestType.ResumePause -> {
                    launchOnViewLifecycleResumeCancelOnViewLifecyclePause(block = block)
                }
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? = FrameLayout(requireContext())
    }
}