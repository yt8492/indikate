package com.yt8492.indikate

import Buffer

class Server {

    private val newHandlers = mutableListOf<Handler>()

    private val server = http.createServer { req, res ->
        val bodyBuilder = StringBuilder()
        val path = url.parse(req.url).pathname ?: "/"
        val (evaluateResult, newHandler) = newHandlers.associateBy {
            it.routingPath.evaluate(path)
        }.filter { (result, _) ->
            result.succeeded
        }.maxByOrNull { (result, _) ->
            result.quality
        } ?: run {
            res.statusCode = 404
            res.end()
            return@createServer
        }
        val pathParameters = PathParameters(evaluateResult.parameters)
        req.on("data") { data: Any ->
            data as Buffer
            bodyBuilder.append(data.toString(encoding = "UTF-8"))
        }
        req.on("end") { ->
            val headers = Headers.fromNodeRawHeaders(req.rawHeaders)
            val queryParameters = QueryParameters(
                querystring.parse(url.parse(req.url).query ?: "")
            )
            val body = bodyBuilder.toString()
            val newRequest = Request(
                req.url,
                req.method,
                headers,
                pathParameters,
                queryParameters,
                body
            )
            val response = Response()
            newHandler.handleFunc(newRequest, response)
            response.headers
                .groupBy({
                    it.name
                }, {
                    it.value
                })
                .forEach {
                    res.setHeader(it.key, it.value.toTypedArray())
                }
            res.statusCode = response.statusCode
            res.write(response.message)
            res.end()
        }
    }

    fun get(path: String, handleFunc: HandleFunc) {
        handle(path, "GET", handleFunc)
    }

    fun post(path: String, handleFunc: HandleFunc) {
        handle(path, "POST", handleFunc)
    }

    fun put(path: String, handleFunc: HandleFunc) {
        handle(path, "PUT", handleFunc)
    }

    fun head(path: String, handleFunc: HandleFunc) {
        handle(path, "HEAD", handleFunc)
    }

    fun delete(path: String, handleFunc: HandleFunc) {
        handle(path, "DELETE", handleFunc)
    }

    fun connect(path: String, handleFunc: HandleFunc) {
        handle(path, "CONNECT", handleFunc)
    }

    fun options(path: String, handleFunc: HandleFunc) {
        handle(path, "OPTIONS", handleFunc)
    }

    fun trace(path: String, handleFunc: HandleFunc) {
        handle(path, "TRACE", handleFunc)
    }

    fun patch(path: String, handleFunc: HandleFunc) {
        handle(path, "PATCH", handleFunc)
    }

    private fun handle(path: String, method: String, handleFunc: HandleFunc) {
        val newHandler = Handler(
            RoutingPath.parse(path),
            method,
            handleFunc
        )
        newHandlers.add(newHandler)
    }

    fun listen(port: Int, onStart: () -> Unit) {
        server.listen(port) {
            onStart()
        }
    }
}
