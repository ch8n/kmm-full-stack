package data.remote

import data.models.network.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.core.*
import utils.ResultOf
import kotlin.use

class ApiManager(
    val httpClient: HttpClient
) {
    suspend inline fun <reified Res> getRequest(
        url: String,
        headers: Map<String, Any> = emptyMap(),
        params: Map<String, Any> = emptyMap(),
    ): ResultOf<Response<Res>> {
        return ResultOf.build {
            val response: HttpResponse = httpClient.use { client ->
                client.get(urlString = url) {
                    headers.onEach { (key, value) -> header(key, value) }
                    params.onEach { (key, value) -> parameter(key, value) }
                }
            }
            response.body<Response<Res>>()
        }
    }

    suspend inline fun <reified Req, Res> postRequest(
        url: String,
        headers: Map<String, Any> = emptyMap(),
        params: Map<String, Any> = emptyMap(),
        body: Req? = null
    ): ResultOf<Response<Res>> {
        return ResultOf.build {
            val response: HttpResponse = httpClient.use { client ->
                client.post(urlString = url) {
                    headers.onEach { (key, value) -> header(key, value) }
                    params.onEach { (key, value) -> parameter(key, value) }
                    if (body != null) setBody(body)
                }
            }
            response.body<Response<Res>>()
        }
    }
}