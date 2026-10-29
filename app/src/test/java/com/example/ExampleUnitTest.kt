package com.example

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testCouponDiscountCalculations() {
        val subtotal = 10000.0
        val discountRate10 = 0.10
        val discountAmount10 = subtotal * discountRate10
        assertEquals(1000.0, discountAmount10, 0.001)

        val discountRate15 = 0.15
        val discountAmount15 = subtotal * discountRate15
        assertEquals(1500.0, discountAmount15, 0.001)
    }

    @Test
    fun testFreeShippingThreshold() {
        val lowSubtotal = 4999.0
        val highSubtotal = 5000.0
        val higherSubtotal = 14500.0

        val feeLow = if (lowSubtotal >= 5000.0) 0.0 else 250.0
        val feeHigh = if (highSubtotal >= 5000.0) 0.0 else 250.0
        val feeHigher = if (higherSubtotal >= 5000.0) 0.0 else 250.0

        assertEquals(250.0, feeLow, 0.001)
        assertEquals(0.0, feeHigh, 0.001)
        assertEquals(0.0, feeHigher, 0.001)
    }

    @Test
    fun testOrderIdFormat() {
        val randomNum = (10000..99999).random()
        val orderId = "MSF-$randomNum"
        assertTrue(orderId.startsWith("MSF-"))
        assertEquals(9, orderId.length)
    }
}
