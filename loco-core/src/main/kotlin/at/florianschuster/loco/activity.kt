package at.florianschuster.loco

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * Launches [block] every time the [ComponentActivity.getLifecycle] state reaches
 * [Lifecycle.Event.ON_CREATE]. The coroutines launched in [block] are cancelled when
 * the [ComponentActivity.getLifecycle] state reaches [Lifecycle.Event.ON_DESTROY].
 *
 *
 * Example:
 *
 * ```
 * class MainActivity : Activity(R.layout.activity_main) {
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *
 *         launchOnLifecycleCreateCancelOnLifecycleDestroy {
 *             emptyFlow().launchIn(this)
 *             launch { suspendingFunction() }
 *         }
 *     }
 * }
 * ```
 */
fun <A : ComponentActivity> A.launchOnLifecycleCreateCancelOnLifecycleDestroy(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycle.launchOnCreateCancelOnDestroy(coroutineContext, block)
}

/**
 * Launches [block] every time the [ComponentActivity.getLifecycle] state reaches
 * [Lifecycle.Event.ON_START]. The coroutines launched in [block] are cancelled when
 * the [ComponentActivity.getLifecycle] state reaches [Lifecycle.Event.ON_STOP].
 *
 *
 * Example:
 *
 * ```
 * class MainActivity : Activity(R.layout.activity_main) {
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *
 *         launchOnLifecycleStartCancelOnLifecycleStop {
 *             emptyFlow().launchIn(this)
 *             launch { suspendingFunction() }
 *         }
 *     }
 * }
 * ```
 */
fun <A : ComponentActivity> A.launchOnLifecycleStartCancelOnLifecycleStop(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycle.launchOnStartCancelOnStop(coroutineContext, block)
}

/**
 * Launches [block] every time the [ComponentActivity.getLifecycle] state reaches
 * [Lifecycle.Event.ON_RESUME]. The coroutines launched in [block] are cancelled when
 * the [ComponentActivity.getLifecycle] state reaches [Lifecycle.Event.ON_PAUSE].
 *
 *
 * Example:
 *
 * ```
 * class MainActivity : Activity(R.layout.activity_main) {
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *
 *         launchOnLifecycleResumeCancelOnLifecyclePause {
 *             emptyFlow().launchIn(this)
 *             launch { suspendingFunction() }
 *         }
 *     }
 * }
 * ```
 */
fun <A : ComponentActivity> A.launchOnLifecycleResumeCancelOnLifecyclePause(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycle.launchOnResumeCancelOnPause(coroutineContext, block)
}