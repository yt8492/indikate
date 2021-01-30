package com.yt8492.indikate

data class Request(
    val path: String,
    val method: String,
    val headers: Headers,
    val pathParameters: PathParameters,
    val queryParameters: QueryParameters,
    val body: String
)
