package com.yt8492.indikate

class Server {

    private val handlers = mutableListOf<Handler>()

    private val server = http.createServer { req, res ->
        val bodyBuilder = StringBuilder()
        val path = url.parse(req.url).pathname ?: "/"
        val handler = handlers.firstOrNull {
            it.path == path && it.method == req.method
        } ?: run {
            res.statusCode = 404
            res.end()
            return@createServer
        }
        req.on("data") { data: Any ->
            bodyBuilder.append(data.toString())
        }
        req.on("end") { ->
            val headers = Headers(req.headers)
            val queryParameters = QueryParameters(
                querystring.parse(url.parse(req.url).query ?: "")
            )
            val body = bodyBuilder.toString()
            val request = Request(
                req.url,
                req.method,
                headers,
                queryParameters,
                body
            )
            val response = Response()
            handler.handleFunc(request, response)
            res.statusCode = response.statusCode
            res.write(response.message)
            res.end()
        }
    }

    fun get(path: String, handleFunc: (Request, Response) -> Unit) {
        handle(path, "GET", handleFunc)
    }

    fun post(path: String, handleFunc: (Request, Response) -> Unit) {
        handle(path, "POST", handleFunc)
    }

    private fun handle(path: String, method: String, handleFunc: (Request, Response) -> Unit) {
        val handler = Handler(
            path,
            method,
            handleFunc
        )
        handlers.add(handler)
    }

    fun listen(port: Int, onStart: () -> Unit) {
        server.listen(port) {
            onStart()
        }
    }
}