package com.demo.appko

import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.equalToIgnoringCase
import org.hamcrest.Matchers.equalToIgnoringWhiteSpace
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.isEmptyOrNullString
import org.hamcrest.Matchers.isEmptyString
import org.hamcrest.Matchers.stringContainsInOrder
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.util.Arrays

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
    assertNotEquals(4,2+3);
    assertTrue(true)
    assertFalse(false)
    assertNull(null)
    assertNotNull(Object())
    val a = Object()
    assertSame(a,a)
    assertNotSame(Object(),Object())
    assertArrayEquals(arrayOf(1,2,3),arrayOf(1,2,3))
//    assertThat(10, Matcher)
//    assertThat()
    fail()
  }

  @Test
  fun hamcrest_check() {
    // Text matcher
    var testStr : String? = ""
    assertThat(testStr, isEmptyString())

    testStr = null
    assertThat(testStr, isEmptyOrNullString())

    testStr = "text"
    var compareStr = "TEXt"
    assertThat(testStr, equalToIgnoringCase(compareStr))

    testStr = " text "
    compareStr = "text"
    assertThat(testStr, equalToIgnoringWhiteSpace(compareStr))

    var collection: List<String> = listOf()
    assertThat(collection, empty()) // false, 用于检查集合类型
    collection = listOf("ab", "cd", "ef")
    assertThat(collection, hasItem("cd"))

    testStr = "calligraphy"
    assertThat(testStr, stringContainsInOrder(Arrays.asList("call", "graph")))
  }


}
