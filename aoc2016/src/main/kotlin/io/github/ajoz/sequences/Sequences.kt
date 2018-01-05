package io.github.ajoz.sequences

/**
 * Returns a sequence containing the results of applying the given [transform] function to each element in the original
 * sequence and the result of previous application. For the case of first element in the sequence the [initial] value
 * will be used as the "previous result" and passed to [transform] function.
 * @param initial Initial element from which the scan should take place.
 * @param transform Two argument function used to perform scan operation.
 */
fun <T, R> Sequence<T>.scan(initial: R, transform: (R, T) -> R): Sequence<R> {
    return TransformingScanSequence(this, initial, transform)
}

/**
 * A sequence which returns the results of applying the given [transformer] function to the values in the underlying
 * [sequence]. Each [transformer] is given a previously calculated value and a new value. In case of the first element
 * of the given [sequence] the [transformer] function will use [initial] as the "previously calculated value".
 */
internal class TransformingScanSequence<T, R>
constructor(private val sequence: Sequence<T>,
            private val initial: R,
            private val transformer: (R, T) -> R) : Sequence<R> {
    override fun iterator(): Iterator<R> = object : Iterator<R> {
        val iterator = sequence.iterator()
        var previous = initial
        override fun next(): R {
            val mapped = transformer(previous, iterator.next())
            previous = mapped
            return mapped
        }

        override fun hasNext(): Boolean {
            return iterator.hasNext()
        }
    }
}

/**
 * Returns first repeated element in the [Sequence]. Can throw a [NoSuchElementException] if the [Sequence] doesn't have
 * elements or there are no repeating elements
 */
fun <T> Sequence<T>.firstRepeated(): T {
    val set = HashSet<T>()
    for (element in this) {
        if (set.contains(element)) return element
        else set.add(element)
    }

    throw NoSuchElementException("Sequence contains no repeating elements")
}

