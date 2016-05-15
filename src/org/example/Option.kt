package org.example

import java.util.*

sealed class Option<out T> {

    abstract val isPresent: Boolean
    abstract val value: T
    abstract fun <U> map(f: (T) -> U): Option<U>
    abstract fun <U> flatMap(f: (T) -> Option<U>): Option<U>

    class Some<T>(override val value: T) : Option<T>() {
        override val isPresent: Boolean
            get() = true

        override fun <U> map(f: (T) -> U): Option<U> = ofNullable(f(value))

        override fun <U> flatMap(f: (T) -> Option<U>): Option<U> = f(value)

        override fun toString(): String = "Some($value)"
    }

    object None : Option<Nothing>() {
        override val isPresent: Boolean
            get() = false

        override val value: Nothing
            get() = throw NoSuchElementException()

        override fun <U> map(f: (Nothing) -> U): Option<U> = None

        override fun <U> flatMap(f: (Nothing) -> Option<U>): Option<U> = None

        override fun toString(): String = "None"
    }

    companion object {
        @JvmStatic fun <T : Any> of(element: T): Option<T> = Some(element)
        @JvmStatic fun <T : Any?> ofNullable(element: T): Option<T> = if (element == null) None else Some(element)
    }
}

infix fun <T> Option<T>.orElse(default: T): T = when (this) {
    is Option.None -> default
    else -> value
}