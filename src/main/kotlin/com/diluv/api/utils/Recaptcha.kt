package com.diluv.api.utils

import io.vertx.core.AsyncResult
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.CaseInsensitiveHeaders
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient

fun verify(client: WebClient, recaptchaSecretKey: String, recaptchaResponse: String, callback: (response: AsyncResult<HttpResponse<Buffer>>) -> Any) {
    val form = CaseInsensitiveHeaders()
    form.set("secret", recaptchaSecretKey)
    form.set("response", recaptchaResponse)
    form.set("remoteip", "")

    client.postAbs("https://www.google.com/recaptcha/api/siteverify")
            .sendForm(form, { ar ->
                if (ar.succeeded()) {
                    callback(ar)
                }
            })
}
