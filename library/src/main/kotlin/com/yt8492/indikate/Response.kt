package com.yt8492.indikate

data class Response(
    val headers: Headers = Headers(),
    var statusCode: Int = 200,
    var message: String = ""
)