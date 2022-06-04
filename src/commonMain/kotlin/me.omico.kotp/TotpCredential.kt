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

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

data class TotpCredential(
    val algorithm: HotpAlgorithm,
    val secretKey: HotpSecretKey,
    val digits: Int = 6,
    val period: Duration = 30.seconds,
) {
    init {
        require(digits in 6..10) { "Digits must be in range 6..10." }
    }
}

fun TotpCredential.generateCode(time: Instant = Clock.System.now()): String = generateCode(time.epochSeconds)

fun TotpCredential.generateCode(timeInSeconds: Long): String =
    secretKey.generateCode(
        algorithm = algorithm,
        digits = digits,
        counter = (timeInSeconds - 0L) / period.toLong(DurationUnit.SECONDS),
    )
