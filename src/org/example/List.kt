package org.example

import java.util.*

sealed class List<out T> {

    abstract val isEmpty: Boolean
    abstract val head: T
    abstract val tail: List<T>

    abstract fun <U> map(transform: (T) -> U): List<U>
    abstract fun <U> flatMap(transform: (T) -> List<U>): List<U>

    class Cons<out T>(override val head: T, override val tail: List<T> = Nil) : List<T>() {
        override val isEmpty: Boolean
            get() = false

        override fun <U> map(transform: (T) -> U): List<U> = tail.map(transform).prepend(transform(head))

        override fun <U> flatMap(transform: (T) -> List<U>): List<U> = tail.flatMap(transform).prependAll(transform(head))

        override fun toString(): String = "$head :: $tail"
    }

    object Nil : List<Nothing>() {
        override val isEmpty: Boolean
            get() = true

        override val head: Nothing
            get() = throw NoSuchElementException()

        override val tail: List<Nothing>
            get() = throw NoSuchElementException()

        override fun <U> map(transform: (Nothing) -> U): List<U> = this

        override fun <U> flatMap(transform: (Nothing) -> List<U>): List<U> = this

        override fun toString(): String = "Nil"
    }

    companion object {
        fun <T> of(vararg elements: T): List<T> = elements.foldRight(Nil) { x: T, acc: List<T> -> acc.prepend(x) }
    }
}

fun <U> List<U>.prepend(element: U): List<U> = List.Cons(element, this)
fun <U> List<U>.prependAll(elements: List<U>): List<U> = when (elements) {
    is List.Nil -> this
    else -> prependAll(elements.tail).prepend(elements.head)
}
