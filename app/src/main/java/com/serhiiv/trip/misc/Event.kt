package com.serhiiv.trip.misc

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun consume(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peek(): T = content
}

inline fun <T> Event<T>.toConsume(action: (T) -> Unit) {
    if (hasBeenHandled.not()) {
        action(consume()!!)
    }
}
