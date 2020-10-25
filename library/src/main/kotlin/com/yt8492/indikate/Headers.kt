package com.yt8492.indikate

class Headers : Iterable<Headers.Entry> {

    private val headers = mutableSetOf<Entry>()

    operator fun get(name: String): String? {
        return headers.firstOrNull {
            it.name.toLowerCase() == name.toLowerCase()
        }?.value
    }

    fun getAll(name: String): List<String> {
        return headers.asSequence()
            .filter { it.name.toLowerCase() == name.toLowerCase() }
            .map { it.value }
            .toList()
    }

    fun add(name: String, value: String) {
        headers.add(Entry(name, value))
    }

    fun add(name: String, values: List<String>) {
        headers.addAll(values.map { Entry(name, it) })
    }

    override fun iterator(): Iterator<Entry> {
        return headers.iterator()
    }

    data class Entry(val name: String, val value: String)

    companion object {
        fun fromNodeRawHeaders(rawHeaders: Array<String>): Headers {
            return Headers().apply {
                for (i in rawHeaders.indices step 2) {
                    add(rawHeaders[i], rawHeaders[i + 1])
                }
            }
        }
    }
}
