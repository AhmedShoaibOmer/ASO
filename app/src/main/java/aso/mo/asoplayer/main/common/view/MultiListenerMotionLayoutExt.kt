package aso.mo.asoplayer.main.common.view

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.*
import androidx.constraintlayout.motion.widget.TransitionAdapter
import kotlinx.coroutines.*

/**
 * Wait for the transition to complete so that the given [transitionId] is fully displayed.
 *
 * @param transitionId The transition set to await the completion of
 * @param timeout Timeout for the transition to take place. Defaults to 5 seconds.
 */
@ExperimentalCoroutinesApi
suspend fun MultiListenerMotionLayout.awaitTransitionComplete(
    transitionId: Int,
    timeout: Long = 5000L
) {
    // If we're already at the specified state, return now
    if (currentState == transitionId) return

    var listener: TransitionListener? = null

    try {
        withTimeout(timeout) {
            suspendCancellableCoroutine<Unit> { continuation ->
                val l = object : TransitionAdapter() {
                    override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                        if (currentId == transitionId) {
                            removeTransitionListener(this)
                            continuation.resume(Unit, object : (Throwable) -> Unit {
                                override fun invoke(p1: Throwable) {
                                }
                            })
                        }
                    }
                }
                // If the coroutine is cancelled, remove the listener
                continuation.invokeOnCancellation {
                    removeTransitionListener(l)
                }
                // And finally add the listener
                addTransitionListener(l)
                listener = l
            }
        }
    } catch (tex: TimeoutCancellationException) {
        // Transition didn't happen in time. Remove our listener and throw a cancellation
        // exception to let the coroutine know
        listener?.let(::removeTransitionListener)
        throw CancellationException(
            "Transition to state with id: $transitionId did not" +
                    " complete in timeout.", tex
        )
    }
}