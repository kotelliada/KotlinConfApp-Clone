package io.kotelliada.kotlinconf.storage

import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class InMemoryYearlyStorageTest {
    private lateinit var sut: InMemoryYearlyStorage

    @BeforeTest
    fun setUp() {
        sut = InMemoryYearlyStorage()
    }

    @Test
    fun `getAsset returns null if there is no asset by passed name`() = runTest {
        val actual = sut.getAsset("some_asset")
        assertNull(actual)
    }

    @Test
    fun `setAsset saves asset by name and getAsset returns a correct asset by name`() = runTest {
        val asset1Name = "asset_1"
        val asset1Body = "asset_1_body"
        val asset2Name = "asset_2"
        sut.setAsset(asset1Name, asset1Body)

        val actualAsset1 = sut.getAsset(asset1Name)
        val actualAsset2 = sut.getAsset(asset2Name)

        assertEquals(asset1Body, actualAsset1)
        assertNull(actualAsset2)
    }

    @Test
    fun `isPolicySigned returns false by default`() = runTest {
        val actual = sut.isPolicySigned()

        assertFalse(actual)
    }

    @Test
    fun `setPolicySigned stores passed value (case 1)`() = runTest {
        sut.setPolicySigned(true)

        val actual = sut.isPolicySigned()

        assertTrue(actual)
    }

    @Test
    fun `setPolicySigned stores passed value (case 2)`() = runTest {
        sut.setPolicySigned(false)

        val actual = sut.isPolicySigned()

        assertFalse(actual)
    }
}
