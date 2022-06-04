/*
 * Copyright 2022 Omico
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.omico.kotp

import org.bouncycastle.util.encoders.Base32
import java.nio.ByteBuffer
import java.time.Instant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and
import kotlin.math.pow

actual fun HotpSecretKey.toBase32String(): String = Base32.toBase32String(value)

actual fun base32HotpSecretKey(value: String): HotpSecretKey = Base32.decode(value).toHotpSecretKey()

actual fun HotpSecretKey.generateCode(algorithm: HotpAlgorithm, digits: Int, counter: Long): String =
    generateBinary(algorithm, counter).int.mod(10.0.pow(digits).toLong()).toString().padStart(digits, '0')

fun TotpCredential.generateCode(time: Instant = Instant.now()): String = generateCode(time.epochSecond)

fun HotpSecretKey.generateBinary(algorithm: HotpAlgorithm, counter: Long): ByteBuffer =
    ByteBuffer.allocate(4).apply {
        val text = ByteBuffer.allocate(8).putLong(counter).array()
        val hash = Mac.getInstance(algorithm.name)
            .apply { init(SecretKeySpec(value, "RAW")) }
            .doFinal(text)
        val offset = hash.last().toInt() and 0xF
        repeat(4) { index ->
            val byte = hash[index + offset]
            when (index) {
                0 -> put(index, byte and 0x7F)
                else -> put(index, byte)
            }
        }
    }
