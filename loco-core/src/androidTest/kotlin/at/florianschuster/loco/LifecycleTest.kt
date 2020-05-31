package at.florianschuster.loco

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.testing.TestLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class LifecycleTest {

    @Test
    fun create_destroy() {
        val lifecycleOwner = TestLifecycleOwner(initialState = Lifecycle.State.INITIALIZED)

        val scopes = mutableListOf<CoroutineScope>()
        var job: Job? = null
        lifecycleOwner.lifecycle.launchOnCreateCancelOnDestroy {
            scopes.add(this)
            job = IndefiniteTestFlow.launchIn(this)
        }
        assertNull(job)
        assertEquals(0, scopes.count())

        lifecycleOwner.currentState = Lifecycle.State.CREATED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)
        assertEquals(1, scopes.count())

        lifecycleOwner.currentState = Lifecycle.State.STARTED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.RESUMED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.DESTROYED
        assertFalse(assertNotNull(job).isActive)
        assertTrue(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.CREATED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)
        assertEquals(2, scopes.count())

        lifecycleOwner.currentState = Lifecycle.State.DESTROYED
        assertFalse(assertNotNull(job).isActive)
        assertTrue(assertNotNull(job).isCancelled)

        assertTrue(
            scopes.groupBy { it.hashCode() }.none { it.value.count() > 1 } // no duplicate scopes
        )
        assertTrue(
            scopes.all { assertNotNull(it.coroutineContext[Job]).isCancelled } // all scopes cancelled
        )
    }

    @Test
    fun start_stop() {
        val lifecycleOwner = TestLifecycleOwner(initialState = Lifecycle.State.CREATED)

        val scopes = mutableListOf<CoroutineScope>()
        var job: Job? = null
        lifecycleOwner.lifecycle.launchOnStartCancelOnStop {
            scopes.add(this)
            job = IndefiniteTestFlow.launchIn(this)
        }
        assertNull(job)
        assertEquals(0, scopes.count())

        lifecycleOwner.currentState = Lifecycle.State.STARTED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.RESUMED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.STARTED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)
        assertEquals(1, scopes.count())

        lifecycleOwner.currentState = Lifecycle.State.CREATED
        assertFalse(assertNotNull(job).isActive)
        assertTrue(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.STARTED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.CREATED
        assertFalse(assertNotNull(job).isActive)
        assertTrue(assertNotNull(job).isCancelled)

        assertEquals(2, scopes.count())

        assertTrue(
            scopes.groupBy { it.hashCode() }.none { it.value.count() > 1 } // no duplicate scopes
        )
        assertTrue(
            scopes.all { assertNotNull(it.coroutineContext[Job]).isCancelled } // all scopes cancelled
        )
    }

    @Test
    fun resume_pause() {
        val lifecycleOwner = TestLifecycleOwner(initialState = Lifecycle.State.CREATED)

        val scopes = mutableListOf<CoroutineScope>()
        var job: Job? = null
        lifecycleOwner.lifecycle.launchOnResumeCancelOnPause {
            scopes.add(this)
            job = IndefiniteTestFlow.launchIn(this)
        }
        assertNull(job)
        assertEquals(0, scopes.count())

        lifecycleOwner.currentState = Lifecycle.State.STARTED
        assertNull(job)

        lifecycleOwner.currentState = Lifecycle.State.RESUMED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)
        assertEquals(1, scopes.count())

        lifecycleOwner.currentState = Lifecycle.State.STARTED
        assertFalse(assertNotNull(job).isActive)
        assertTrue(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.CREATED
        assertFalse(assertNotNull(job).isActive)
        assertTrue(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.STARTED
        assertFalse(assertNotNull(job).isActive)
        assertTrue(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.RESUMED
        assertTrue(assertNotNull(job).isActive)
        assertFalse(assertNotNull(job).isCancelled)

        lifecycleOwner.currentState = Lifecycle.State.CREATED
        assertFalse(assertNotNull(job).isActive)
        assertTrue(assertNotNull(job).isCancelled)

        assertEquals(2, scopes.count())

        assertTrue(
            scopes.groupBy { it.hashCode() }.none { it.value.count() > 1 } // no duplicate scopes
        )
        assertTrue(
            scopes.all { assertNotNull(it.coroutineContext[Job]).isCancelled } // all scopes cancelled
        )
    }
}