package at.florianschuster.loco

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Launches [block] every time the [Lifecycle] state reaches [Lifecycle.Event.ON_CREATE].
 * The coroutines launched in [block] are cancelled when the [Lifecycle] state reaches
 * [Lifecycle.Event.ON_DESTROY].
 */
fun Lifecycle.launchOnCreateCancelOnDestroy(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    val observer = LaunchOnCancelOnObserver(
        launchEvent = Lifecycle.Event.ON_CREATE,
        cancelEvent = Lifecycle.Event.ON_DESTROY,
        coroutineContext = coroutineContext,
        block = block
    )
    addObserver(observer)
}

/**
 * Launches [block] every time the [Lifecycle] state reaches [Lifecycle.Event.ON_START].
 * The coroutines launched in [block] are cancelled when the [Lifecycle] state reaches
 * [Lifecycle.Event.ON_STOP].
 */
fun Lifecycle.launchOnStartCancelOnStop(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    val observer = LaunchOnCancelOnObserver(
        launchEvent = Lifecycle.Event.ON_START,
        cancelEvent = Lifecycle.Event.ON_STOP,
        coroutineContext = coroutineContext,
        block = block
    )
    addObserver(observer)
}

/**
 * Launches [block] every time the [Lifecycle] state reaches [Lifecycle.Event.ON_RESUME].
 * The coroutines launched in [block] are cancelled when the [Lifecycle] state reaches
 * [Lifecycle.Event.ON_PAUSE].
 */
fun Lifecycle.launchOnResumeCancelOnPause(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    val observer = LaunchOnCancelOnObserver(
        launchEvent = Lifecycle.Event.ON_RESUME,
        cancelEvent = Lifecycle.Event.ON_PAUSE,
        coroutineContext = coroutineContext,
        block = block
    )
    addObserver(observer)
}

private class LaunchOnCancelOnObserver(
    private val launchEvent: Lifecycle.Event,
    private val cancelEvent: Lifecycle.Event,
    coroutineContext: CoroutineContext,
    private val block: suspend CoroutineScope.() -> Unit
) : LifecycleEventObserver {

    private val scope = CoroutineScope(coroutineContext)
    private var job: Job? = null

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == cancelEvent) {
            job?.cancel()
            job = null
        } else if (event == launchEvent) {
            job = scope.launch(block = block)
        }
    }
}
