package com.diluv.api.jwt

import io.vertx.core.json.JsonObject
import java.util.*


class JWT() {
    constructor(token: String) : this() {
        val segment = token.split("\\.".toRegex())
        val header = JsonObject(String(Base64.getUrlDecoder().decode(segment[0])))
        val payload = JsonObject(String(Base64.getUrlDecoder().decode(segment[1])))

        this.text = token
        if (payload.containsKey("exp"))
            this.exp = payload.getLong("exp") - payload.getLong("iat")
        if (payload.containsKey("iss"))
            this.iss = payload.getString("iss")
        if (payload.containsKey("data"))
            this.data = payload.getJsonObject("data")
    }

    var text: String? = null
    var exp: Long = -1
    var iss: String? = null
    var iat: Long = System.currentTimeMillis() / 1000
    var data: JsonObject = JsonObject()

    fun setExpiresInSeconds(seconds: Long): JWT {
        this.exp = seconds
        return this
    }

    fun setExpiresInMinutes(minutes: Long): JWT {
        this.exp = minutes * 60
        return this
    }

    fun setExpiresInHours(hours: Long): JWT {
        this.setExpiresInMinutes(hours * 60)
        return this
    }

    fun setExpiresInDays(days: Long): JWT {
        this.setExpiresInHours(days * 24)
        return this
    }

    fun setIssuer(iss: String): JWT {
        this.iss = iss
        return this
    }

    fun setData(data: JsonObject): JWT {
        this.data = data
        return this
    }

    fun getExpires(): Long {
        return this.iat + this.exp
    }

    fun isExpired(): Boolean {
        if ((System.currentTimeMillis() / 1000) < this.getExpires())
            return false

        return true
    }

    override fun toString(): String {
        if (text != null)
            return text as String

        val header = JsonObject()
                .put("typ", "JWT")
                .put("alg", "none")

        val payload = JsonObject()
                .put("iat", this.iat)

        if (this.iss != null)
            payload.put("iss", this.iss)
        if (this.exp != -1L)
            payload.put("exp", this.getExpires())
        payload.put("data", this.data)

        val headerSegment = base64urlEncode(header.encode())
        val payloadSegment = base64urlEncode(payload.encode())

        return "$headerSegment.$payloadSegment."
    }

    private fun base64urlEncode(str: String): String {
        return base64urlEncode(str.toByteArray(Charsets.UTF_8))
    }

    private fun base64urlEncode(bytes: ByteArray): String {
        return Base64.getUrlEncoder().encodeToString(bytes)
    }
}