package at.florianschuster.loco

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class LaunchOnCancelOnLifecycleEventObserver(
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

internal class LaunchOnCancelOnViewAttachStateListener(
    coroutineContext: CoroutineContext,
    private val block: suspend CoroutineScope.() -> Unit
) : View.OnAttachStateChangeListener {

    private val scope = CoroutineScope(coroutineContext)
    private var job: Job? = null

    override fun onViewAttachedToWindow(v: View?) {
        job = scope.launch(block = block)
    }

    override fun onViewDetachedFromWindow(v: View?) {
        job?.cancel()
        job = null
    }
}
