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

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.support.annotation.RawRes
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.EditText
import com.stfalcon.stfalcon_fixturer.models.Fixture
import com.stfalcon.stfalcon_fixturer.utils.TripleClickListener
import java.util.*


/*
 * Created by Anton Bevza on 10/30/18.
 */
class StfalconFixturer {

    companion object {
        private const val LOG_TAG = "Fixturer"

        private var fixtures: List<Fixture>? = null
        private var isEnable: Boolean? = false

        private val instance: StfalconFixturer by lazy {
            if (fixtures == null && isEnable == true) {
                throw NullPointerException("You should call init method before get instance of Fixturer")
            }
            StfalconFixturer()
        }

        fun init(context: Context, @RawRes resourceId: Int, isEnable: Boolean? = isDebug(context)) {
            this.isEnable = isEnable
            if (isEnable == true) {
                val parser = FixturesXmlParser()
                fixtures = parser.parse(context.resources.openRawResource(resourceId))
            }
        }

        fun get() = instance

        private fun isDebug(context: Context) = context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    private val binds = WeakHashMap<EditText, String>()

    @SuppressLint("ClickableViewAccessibility")
    fun bindEditText(field: EditText, tag: String) {
        if (isEnable == true) {
            binds[field] = tag
            field.setOnTouchListener(TripleClickListener { showDialog(field) })
        }
    }

    private fun showDialog(view: EditText) {
        val fixture = fixtures?.firstOrNull { it.tag == binds[view] }
        if (fixture != null) {
            val list = fixture.items
                ?.map { it.text }

            if (list?.isNotEmpty() == true) {
                val builder = AlertDialog.Builder(view.context)
                builder.setTitle("Select fixture")
                    .setItems(list.toTypedArray()) { _, which ->
                        view.setText(list[which])
                        insertInGroup(which, fixture)
                    }
                builder.show()
            }
        }
    }

    private fun insertInGroup(selectedIndex: Int, fixture: Fixture) {
        if (fixture.group?.isNotEmpty() == true) {
            val groupedFixtures = fixtures?.filter { it.group == fixture.group }
            groupedFixtures?.forEach { item ->
                binds.filterValues { it == item.tag }.forEach {
                    try {
                        it.key.setText(item.items?.get(selectedIndex)?.text)
                    } catch (e: IndexOutOfBoundsException) {
                        Log.e(LOG_TAG, "All grouped fixtures should have the same item count")
                    }
                }
            }
        }
    }
}

