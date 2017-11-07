package com.diluv.api.utils;

import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;


public class Recaptcha {

    //TODO Look for better way of doing this
    public static void verify(WebClient client, String recaptchaSecretKey, String recaptchaResponse, Callback<AsyncResult<HttpResponse<Buffer>>> callback) {
        CaseInsensitiveHeaders form = new CaseInsensitiveHeaders();
        form.set("secret", recaptchaSecretKey);
        form.set("response", recaptchaResponse);
        form.set("remoteip", "");
        client.postAbs("https://www.google.com/recaptcha/api/siteverify").sendForm(form, callback::invoke);
    }
}
