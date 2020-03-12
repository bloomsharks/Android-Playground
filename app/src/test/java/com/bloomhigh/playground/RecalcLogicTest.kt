package com.bloomhigh.playground

import com.bloomhigh.datepicker.BHDatePicker
import com.bloomhigh.datepicker.RecalcLogic
import com.bloomhigh.datepicker.RecalcResult
import org.junit.Assert
import org.junit.Test

class RecalcLogicTest {
    @Test
    fun testMinDate() {
        val result = recalc("2011-03-12", "2016-03-12", "2000-03-12")
        Assert.assertEquals(
            RecalcResult(
                minMonth = RecalcLogic.START_MONTH,
                maxMonth = RecalcLogic.END_MONTH,
                minDay = RecalcLogic.START_DAY,
                maxDay = RecalcLogic.calcMaxDayForDate(BHDatePicker.CustomDate("2011-03-12"))
            ),
            result.also { println(it.toString()) }
        )
    }

    @Test
    fun testMaxDate() {
        val result = recalc("2016-03-12", "2016-03-12", "2000-03-12")
        Assert.assertEquals(
            RecalcResult(
                minMonth = 1,
                maxMonth = 3,
                minDay = 1,
                maxDay = 12
            ),
            result.also { println(it.toString()) }
        )
    }

    @Test
    fun testMidDate() {
        val result = recalc("2008-08-08", "2016-03-12", "2000-03-12")
        Assert.assertEquals(
            RecalcResult(
                minMonth = RecalcLogic.START_MONTH,
                maxMonth = RecalcLogic.END_MONTH,
                minDay = RecalcLogic.START_DAY,
                maxDay = RecalcLogic.calcMaxDayForDate(BHDatePicker.CustomDate("2008-08-08"))
            ),
            result.also { println(it.toString()) }
        )
    }

    private fun recalc(current: String, max: String, min: String): RecalcResult {
        return RecalcLogic.recalcMinMax(
            currentDate = BHDatePicker.CustomDate(current),
            maximumDate = BHDatePicker.CustomDate(max),
            minimumDate = BHDatePicker.CustomDate(min)
        )
    }
}