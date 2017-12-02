package com.jonnyhsia.storybook.kit

import android.util.Base64

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Random

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

@Suppress("MemberVisibilityCanPrivate")
object AlgorithmKit {

    fun md5(plainText: String): String {
        val hash: ByteArray
        try {
            hash = MessageDigest.getInstance("MD5").digest(plainText.toByteArray(charset("UTF-8")))
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Huh, MD5 should be supported?", e)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("Huh, UTF-8 should be supported?", e)
        }

        return AlgorithmKit.bin2Hex(hash)
    }

    fun bin2Hex(bytes: ByteArray): String {
        val hex = StringBuilder(bytes.size * 2)
        for (b in bytes) {
            if (b and 0xFF.toByte() < 0x10) {
                hex.append("0")
            }
            hex.append(Integer.toHexString((b and 0xFF.toByte()).toInt()))
        }

        return hex.toString()
    }

    /**
     * HMAC256 Hash
     *
     * @param plainText String 明文
     * @param secretKey String 密钥
     * @return String 可为 null
     */
    fun hmac256(plainText: String, secretKey: String): String? {
        try {
            val signingKey = SecretKeySpec(secretKey.toByteArray(charset("UTF8")), "HmacSHA256")

            val mac = Mac.getInstance("HmacSHA256")
            mac.init(signingKey)
            val cipherText = mac.doFinal(plainText.toByteArray(charset("utf-8")))
            val hexString = AlgorithmKit.bin2Hex(cipherText)

            return AlgorithmKit.base64Encode(hexString)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun randomString(length: Int): String {
        if (length < 1) {
            throw IllegalArgumentException("随机字符串长度不小于 1")
        }

        val base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val random = Random()

        val stringBuilder = StringBuilder()
        for (i in 0 until length) {
            stringBuilder.append(base[random.nextInt(base.length)])
        }

        return stringBuilder.toString()
    }

    fun base64Encode(plainText: String): String {
        return Base64.encodeToString(plainText.toByteArray(), Base64.NO_WRAP)
    }
}
