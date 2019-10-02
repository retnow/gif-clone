package org.gifLibrary

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.takeFrom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class GiphyAPI {
    // TODO: Remove developer API key.
    val apiKey: String = "plW8ZiYYmQHHk4lFDWHfPYKgXitCzWOT"

    private val client: HttpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict).apply {
                setMapper(GifResult::class, GifResult.serializer())
            }
        }
    }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom("https://api.giphy.com/")
            encodedPath = path
        }
    }

    suspend fun getCatGIFs(): GifResult = client.get {
            apiUrl(path = "v1/gifs/search?api_key=$apiKey&q=kitten&limit=25&offset=0&rating=G&lang=en")
    }

    fun getCatGIFLinks(callback: (List<String>) -> Unit) {
        GlobalScope.apply {
            launch(dispatcher) {
                val result: GifResult = getCatGIFs()
                val urls = result.data.map {
                    it.images.original.url
                }
                callback(urls)
            }
        }
    }
}
