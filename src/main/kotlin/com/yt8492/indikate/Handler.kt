package com.yt8492.indikate

data class Handler(
    val path: String,
    val method: String,
    val handleFunc: (request: Request, response: Response) -> Unit
)
