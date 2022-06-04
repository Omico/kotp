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

import kotlin.test.Test
import kotlin.test.assertEquals

class KotpTest {

    private val totpCredential = TotpCredential(
        algorithm = HotpAlgorithm.SHA1,
        secretKey = "3132333435363738393031323334353637383930".decodeHex().toHotpSecretKey(),
        digits = 8,
    )

    private val totpPairs = mapOf(
        59L to "94287082",
        1111111109L to "07081804",
        1111111111L to "14050471",
        1234567890L to "89005924",
        2000000000L to "69279037",
        20000000000L to "65353130",
    )

    @Test
    fun test() {
        totpPairs.forEach { (totpTime, totpCode) ->
            assertEquals(totpCredential.generateCode(totpTime), totpCode)
        }
    }
}
