package at.florianschuster.loco

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
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

internal class ActivityTest {

    @Test
    fun create_destroy() {
        val scenario = launchActivityWith<TestActivity>(
            TestType.CreateDestroy
        )

        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.onActivity { activity -> assertEquals(1, activity.callCounter.get()) }
    }

    @Test
    fun start_stop() {
        val scenario = launchActivityWith<TestActivity>(
            TestType.StartStop
        )

        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity -> assertFalse(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity -> assertFalse(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.onActivity { activity -> assertEquals(3, activity.callCounter.get()) }
    }

    @Test
    fun resume_pause() {
        val scenario = launchActivityWith<TestActivity>(
            TestType.ResumePause
        )

        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity -> assertFalse(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity -> assertFalse(assertNotNull(activity.job).isActive) }

        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity -> assertTrue(assertNotNull(activity.job).isActive) }

        scenario.onActivity { activity -> assertEquals(3, activity.callCounter.get()) }
    }

    class TestActivity : ComponentActivity() {
        var job: Job? = null
        val callCounter = AtomicInteger(0)

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(FrameLayout(this))

            val block: suspend CoroutineScope.() -> Unit = {
                job = IndefiniteTestFlow.launchIn(this)
                callCounter.getAndIncrement()
            }

            when (requireTestTypeInIntent()) {
                TestType.CreateDestroy -> {
                    launchOnLifecycleCreateCancelOnLifecycleDestroy(block = block)
                }
                TestType.StartStop -> {
                    launchOnLifecycleStartCancelOnLifecycleStop(block = block)
                }
                TestType.ResumePause -> {
                    launchOnLifecycleResumeCancelOnLifecyclePause(block = block)
                }
            }
        }
    }
}