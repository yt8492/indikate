import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

private val content = """
    # indikate
    [ ![Download](https://api.bintray.com/packages/yt8492/maven/indikate/images/download.svg?version=${Packages.version}) ](https://bintray.com/yt8492/maven/indikate/${Packages.version}/link)

    Simple Server-side Framework for Kotlin/JS

    ## Installation

    ### Gradle

    ```groovy
    repositories {
        maven {
            url  "https://dl.bintray.com/yt8492/maven" 
        }
    }

    dependencies {
        implementation "com.yt8492:indikate:${Packages.version}"
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
            val contentType = request.headers["content-type"]
            val body = request.body
            response.message = ""${'"'}
                contentType: ${"$"}contentType
                body: ${"$"}body
            ""${'"'}.trimIndent()
            response.statusCode = 200
        }
        server.listen(8080) {
            println("server start")
        }
    }
    ```

    ## License

    This project is licensed under the terms of the [MIT license](/LICENSE).
""".trimIndent()

open class GenerateReadme : DefaultTask() {
    @TaskAction
    fun generate() {
        val readme = File("${project.rootDir}/Readme.md")
        readme.deleteOnExit()
        readme.createNewFile()
        readme.setWritable(true)
        readme.writeText(content)
    }
}
