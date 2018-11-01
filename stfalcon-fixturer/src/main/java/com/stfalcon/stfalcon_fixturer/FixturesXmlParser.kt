/*******************************************************************************
 * Copyright 2018 stfalcon.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.stfalcon.stfalcon_fixturer

import android.util.Xml
import com.stfalcon.stfalcon_fixturer.models.Fixture
import com.stfalcon.stfalcon_fixturer.models.Item
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

/*
 * Created by Anton Bevza on 10/29/18.
 */
internal class FixturesXmlParser {

    private val ns: String? = null

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Fixture> {
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            return readFixtures(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFixtures(parser: XmlPullParser): List<Fixture> {
        val fixtures = mutableListOf<Fixture>()

        parser.require(XmlPullParser.START_TAG, ns, "fixtures")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag
            if (parser.name == "fixture") {
                fixtures.add(readFixture(parser))
            } else {
                skip(parser)
            }
        }
        return fixtures
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFixture(parser: XmlPullParser): Fixture {
        parser.require(XmlPullParser.START_TAG, ns, "fixture")
        val tag = parser.getAttributeValue(null, "tag")
        val group = parser.getAttributeValue(null, "group")
        val itemsList = mutableListOf<Item>()
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "item" -> itemsList.add(readItem(parser))
                else -> skip(parser)
            }
        }
        return Fixture(tag, group, itemsList)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readItem(parser: XmlPullParser): Item {
        parser.require(XmlPullParser.START_TAG, ns, "item")
        val itemText = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "item")
        return Item(itemText)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }


}