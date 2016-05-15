package org.example

fun main(args: Array<String>) {

    fun toNull(text: String): String? = text + null
    fun toNone(text: String): Option<String?> = Option.None

    val text: Option<String?> = Option.ofNullable("I'm a text")
    val length = text.flatMap { toNone(it!!) }.map { toNull(it!!) }.map { it!!.length } orElse 0

    val aList = List.of(1,2,3,4).flatMap { List.of(it) }

    println("fin")
}
