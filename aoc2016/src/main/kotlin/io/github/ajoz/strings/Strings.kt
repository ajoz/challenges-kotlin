package io.github.ajoz.strings

/**
 * Scala like property for the String to access the first char. I'm just more used to this kind of naming.
 */
val String.head: Char
    get() = get(0)

/**
 * Scala like property for the String to access rest of the string except the first char.
 */
val String.tail: String
    get() = drop(1)
