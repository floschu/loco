package at.florianschuster.loco

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * Waits for the [Fragment.getViewLifecycleOwner] to be present, then launches [block]
 * every time the [Fragment.getViewLifecycleOwner] [Lifecycle] state reaches
 * [Lifecycle.Event.ON_CREATE]. The coroutines launched in [block] are cancelled when
 * the [Fragment.getViewLifecycleOwner] [Lifecycle] state reaches [Lifecycle.Event.ON_DESTROY].
 *
 *
 * Example:
 *
 * ```
 * class SomeFragment : Fragment(R.layout.fragment_some) {
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *
 *         launchOnViewLifecycleCreateCancelOnViewLifecycleDestroy {
 *             emptyFlow().launchIn(this)
 *             launch { suspendingFunction() }
 *         }
 *     }
 * }
 * ```
 */
fun <F : Fragment> F.launchOnViewLifecycleCreateCancelOnViewLifecycleDestroy(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwnerLiveData.observe(this, Observer { viewLifecycleOwner ->
        viewLifecycleOwner.lifecycle.launchOnCreateCancelOnDestroy(coroutineContext, block)
    })
}

/**
 * Waits for the [Fragment.getViewLifecycleOwner] to be present, then launches [block]
 * every time the [Fragment.getViewLifecycleOwner] [Lifecycle] state reaches
 * [Lifecycle.Event.ON_START]. The coroutines launched in [block] are cancelled when
 * the [Fragment.getViewLifecycleOwner] [Lifecycle] state reaches [Lifecycle.Event.ON_STOP].
 *
 *
 * Example:
 *
 * ```
 * class SomeFragment : Fragment(R.layout.fragment_some) {
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *
 *         launchOnViewLifecycleStartCancelOnViewLifecycleStop {
 *             emptyFlow().launchIn(this)
 *             launch { suspendingFunction() }
 *         }
 *     }
 * }
 * ```
 */
fun <F : Fragment> F.launchOnViewLifecycleStartCancelOnViewLifecycleStop(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwnerLiveData.observe(this, Observer { viewLifecycleOwner ->
        viewLifecycleOwner.lifecycle.launchOnStartCancelOnStop(coroutineContext, block)
    })
}

/**
 * Waits for the [Fragment.getViewLifecycleOwner] to be present, then launches [block]
 * every time the [Fragment.getViewLifecycleOwner] [Lifecycle] state reaches
 * [Lifecycle.Event.ON_RESUME]. The coroutines launched in [block] are cancelled when
 * the [Fragment.getViewLifecycleOwner] [Lifecycle] state reaches [Lifecycle.Event.ON_PAUSE].
 *
 *
 * Example:
 *
 * ```
 * class SomeFragment : Fragment(R.layout.fragment_some) {
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *
 *         launchOnViewLifecycleResumeCancelOnViewLifecyclePause {
 *             emptyFlow().launchIn(this)
 *             launch { suspendingFunction() }
 *         }
 *     }
 * }
 * ```
 */
fun <F : Fragment> F.launchOnViewLifecycleResumeCancelOnViewLifecyclePause(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwnerLiveData.observe(this, Observer { viewLifecycleOwner ->
        viewLifecycleOwner.lifecycle.launchOnResumeCancelOnPause(coroutineContext, block)
    })
}