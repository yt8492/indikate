package com.yt8492.indikate

data class Handler(
    val routingPath: RoutingPath,
    val method: String,
    val handleFunc: HandleFunc
)

typealias HandleFunc = (request: Request, response: Response) -> Unit
