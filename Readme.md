# indikate
[ ![Download](https://api.bintray.com/packages/yt8492/maven/indikate/images/download.svg?version=0.0.1) ](https://bintray.com/yt8492/maven/indikate/0.0.1/link)

Simple Server-side Framework for Kotlin/JS

## installation

### Gradle

```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/yt8492/maven" 
    }
}

dependencies {
    implementation "com.yt8492:indikate:0.0.1"
}
```

## Usage

```kotlin
import com.yt8492.indikate.Server

fun main() {
    val server = Server()
    server.get("/") { request, response ->
        response.message = "Hello indikate!"
    }
    server.post("/") { request, response ->
        val contentType = request.headers["Content-Type"]
        val body = request.body
        response.message = """
            contentType: $contentType
            body: $body
        """.trimIndent()
        response.statusCode = 200
    }
    server.listen(8080) {
        println("server start")
    }
}
```

## License

This project is licensed under the terms of the
[MIT license](/LICENSE).

