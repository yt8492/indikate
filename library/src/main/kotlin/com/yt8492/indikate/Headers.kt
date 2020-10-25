package com.yt8492.indikate

class Headers(
    rawHeaders: Array<String>
) : Iterable<Map.Entry<String, String>> {

    private val headers = mutableSetOf<Map.Entry<String, String>>()

    init {
        for (i in rawHeaders.indices step 2) {
            headers.add(Entry(rawHeaders[i], rawHeaders[i + 1]))
        }
    }

    operator fun get(name: String): String? {
        return headers.firstOrNull {
            it.key.toLowerCase() == name.toLowerCase()
        }?.value
    }

    fun getAll(name: String): List<String> {
        return headers.asSequence()
            .filter { it.key.toLowerCase() == name.toLowerCase() }
            .map { it.value }
            .toList()
    }

    override fun iterator(): Iterator<Map.Entry<String, String>> {
        return headers.iterator()
    }

    private data class Entry<K, V>(
        override val key: K,
        override val value: V
    ) : Map.Entry<K, V>
}