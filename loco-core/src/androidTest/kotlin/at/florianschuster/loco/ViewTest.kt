package at.florianschuster.loco

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
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
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ViewTest {

    @Test
    fun attach_detach() {
        launchFragmentWith<TestFragment>(TestType.CreateDestroy).onFragment { fragment ->
            val view = TestView(fragment.requireContext())
            assertNull(view.job)

            fragment.viewLayout.addView(view)
            assertTrue(assertNotNull(view.job).isActive)

            fragment.viewLayout.removeAllViews()
            assertFalse(assertNotNull(view.job).isActive)

            fragment.viewLayout.addView(view)
            assertTrue(assertNotNull(view.job).isActive)

            assertEquals(2, view.callCounter.get())
        }
    }

    class TestView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : FrameLayout(context, attrs, defStyleAttr) {
        var job: Job? = null
        val callCounter = AtomicInteger(0)

        init {
            launchOnViewAttachCancelOnViewDetach {
                job = IndefiniteTestFlow.launchIn(this)
                callCounter.getAndIncrement()
            }
        }
    }

    @Test
    fun lifecycle_create_destroy() {
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
    fun lifecycle_start_stop() {
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
    fun lifecycle_resume_pause() {
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
        lateinit var viewLayout: FrameLayout

        var job: Job? = null
        val callCounter = AtomicInteger(0)

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? = FrameLayout(requireContext()).also(::viewLayout::set)

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val block: suspend CoroutineScope.() -> Unit = {
                job = IndefiniteTestFlow.launchIn(this)
                callCounter.getAndIncrement()
            }

            when (requireTestTypeInBundle()) {
                TestType.CreateDestroy -> {
                    requireView().launchOnLifecycleCreateCancelOnLifecycleDestroy(block = block)
                }
                TestType.StartStop -> {
                    requireView().launchOnLifecycleStartCancelOnLifecycleStop(block = block)
                }
                TestType.ResumePause -> {
                    requireView().launchOnLifecycleResumeCancelOnLifecyclePause(block = block)
                }
            }
        }
    }
}