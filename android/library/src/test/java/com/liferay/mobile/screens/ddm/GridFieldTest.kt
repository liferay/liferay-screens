package com.liferay.mobile.screens.ddm

import com.liferay.mobile.screens.ddl.model.Option
import com.liferay.mobile.screens.ddm.form.model.Grid
import com.liferay.mobile.screens.ddm.form.model.GridField
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

/**
 * @author Paulo Cruz
 */
@RunWith(Enclosed::class)
class GridFieldTest {

    @RunWith(RobolectricTestRunner::class)
    class WhenPerformingIsValid {

        @Test
        fun shouldReturnTrueWhenTheFieldIsRequiredAndRowsAreCompleted() {
            createGridField(required = true).apply {
                currentValue = Grid(mutableMapOf(
                    "item1" to "medium",
                    "item2" to "medium",
                    "item3" to "high"
                ))
            }.also {
                assertTrue(it.isValid)
            }
        }

        @Test
        fun shouldReturnFalseWhenTheFieldIsRequiredAndRowsAreNotCompleted() {
            createGridField(required = true).apply {
                currentValue = Grid(mutableMapOf(
                    "item1" to "medium",
                    "item2" to "low"
                ))
            }.also {
                assertFalse(it.isValid)
            }
        }

        @Test
        fun shouldReturnTrueWhenTheFieldIsNotRequiredAndRowsAreCompleted() {
            createGridField(required = false).apply {
                currentValue = Grid(mutableMapOf(
                    "item1" to "medium",
                    "item2" to "medium",
                    "item3" to "high"
                ))
            }.also {
                assertTrue(it.isValid)
            }
        }

        @Test
        fun shouldReturnTrueWhenTheFieldIsNotRequiredAndRowsAreNotCompleted() {
            createGridField(required = false).apply {
                currentValue = Grid(mutableMapOf(
                    "item1" to "medium"
                ))
            }.also {
                assertTrue(it.isValid)
            }
        }
    }

    @Ignore
    companion object {
        private fun createGridField(required: Boolean): GridField {
            return GridField().apply {
                columns = listOf(
                    Option("Low", "low", "low"),
                    Option("Medium", "medium", "medium"),
                    Option("High", "high", "high")
                )

                rows = listOf(
                    Option("Item 1", "item1", "item1"),
                    Option("Item 2", "item2", "item2"),
                    Option("Item 3", "item3", "item3")
                )

                isRequired = required
            }
        }
    }
}