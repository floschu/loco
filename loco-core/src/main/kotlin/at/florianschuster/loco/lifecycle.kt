package at.florianschuster.loco

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
    val observer = LaunchOnCancelOnLifecycleEventObserver(
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
    val observer = LaunchOnCancelOnLifecycleEventObserver(
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
    val observer = LaunchOnCancelOnLifecycleEventObserver(
        launchEvent = Lifecycle.Event.ON_RESUME,
        cancelEvent = Lifecycle.Event.ON_PAUSE,
        coroutineContext = coroutineContext,
        block = block
    )
    addObserver(observer)
}
