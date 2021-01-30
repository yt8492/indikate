package com.yt8492.indikate

class PathParameters(
    private val parametersMap: Map<String, String>
) {
    operator fun get(key: String): String? {
        return parametersMap[key]
    }

    fun require(key: String): String {
        return parametersMap.getOrElse(key) {
            throw NoSuchElementException("Missing path parameter $key")
        }
    }
}
