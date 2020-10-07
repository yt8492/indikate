package com.yt8492.indikate

import http.IncomingHttpHeaders
import http.get

class Headers(
    private val incomingHttpHeaders: IncomingHttpHeaders
) {
    operator fun get(name: String): String? {
        return incomingHttpHeaders[name] as? String
    }
}