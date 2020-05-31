package at.florianschuster.loco

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * Retrieves the [LifecycleOwner] of this [View] via [View.findViewTreeLifecycleOwner] and
 * launches [block] every time the [LifecycleOwner.getLifecycle] state reaches
 * [Lifecycle.Event.ON_CREATE]. The coroutines launched in [block] are cancelled when
 * the [LifecycleOwner.getLifecycle] state reaches [Lifecycle.Event.ON_DESTROY].
 *
 *
 * Example:
 *
 * ```
 * class CustomView @JvmOverloads constructor(
 *     context: Context,
 *     attrs: AttributeSet? = null,
 *     defStyleAttr: Int = 0
 * ) : FrameLayout(context, attrs, defStyleAttr) {
 *
 *     override fun onAttachedToWindow() {
 *         super.onAttachedToWindow()
 *         launchOnLifecycleCreateCancelOnLifecycleDestroy {
 *             emptyFlow().launchIn(this)
 *             launch { suspendingFunction() }
 *         }
 *     }
 * }
 * ```
 */
fun <V : View> V.launchOnLifecycleCreateCancelOnLifecycleDestroy(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    requireViewTreeLifecycleOwner.lifecycle.launchOnCreateCancelOnDestroy(coroutineContext, block)
}

/**
 * Retrieves the [LifecycleOwner] of this [View] via [View.findViewTreeLifecycleOwner] and
 * launches [block] every time the [LifecycleOwner.getLifecycle] state reaches
 * [Lifecycle.Event.ON_START]. The coroutines launched in [block] are cancelled when
 * the [LifecycleOwner.getLifecycle] state reaches [Lifecycle.Event.ON_STOP].
 *
 *
 * Example:
 *
 * ```
 * class CustomView @JvmOverloads constructor(
 *     context: Context,
 *     attrs: AttributeSet? = null,
 *     defStyleAttr: Int = 0
 * ) : FrameLayout(context, attrs, defStyleAttr) {
 *
 *     override fun onAttachedToWindow() {
 *         super.onAttachedToWindow()
 *         launchOnLifecycleStartCancelOnLifecycleStop {
 *             emptyFlow().launchIn(this)
 *             launch { suspendingFunction() }
 *         }
 *     }
 * }
 * ```
 */
fun <V : View> V.launchOnLifecycleStartCancelOnLifecycleStop(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    requireViewTreeLifecycleOwner.lifecycle.launchOnStartCancelOnStop(coroutineContext, block)
}

/**
 * Retrieves the [LifecycleOwner] of this [View] via [View.findViewTreeLifecycleOwner] and
 * launches [block] every time the [LifecycleOwner.getLifecycle] state reaches
 * [Lifecycle.Event.ON_RESUME]. The coroutines launched in [block] are cancelled when
 * the [LifecycleOwner.getLifecycle] state reaches [Lifecycle.Event.ON_PAUSE].
 *
 *
 * Example:
 *
 * ```
 * class CustomView @JvmOverloads constructor(
 *     context: Context,
 *     attrs: AttributeSet? = null,
 *     defStyleAttr: Int = 0
 * ) : FrameLayout(context, attrs, defStyleAttr) {
 *
 *     override fun onAttachedToWindow() {
 *         super.onAttachedToWindow()
 *         launchOnLifecycleResumeCancelOnLifecyclePause {
 *             emptyFlow().launchIn(this)
 *             launch { suspendingFunction() }
 *         }
 *     }
 * }
 * ```
 */
fun <V : View> V.launchOnLifecycleResumeCancelOnLifecyclePause(
    coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate,
    block: suspend CoroutineScope.() -> Unit
) {
    requireViewTreeLifecycleOwner.lifecycle.launchOnResumeCancelOnPause(coroutineContext, block)
}

private val <V : View> V.requireViewTreeLifecycleOwner: LifecycleOwner
    get() = checkNotNull(findViewTreeLifecycleOwner()) { "No ViewTree LifecycleOwner attached" }