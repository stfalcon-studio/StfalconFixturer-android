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

package com.stfalcon.stfalconfixturer.utils

import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration

internal class TripleClickListener(val listener: () -> Unit) : View.OnTouchListener {
    var handler = Handler()

    var numberOfTaps = 0
    var lastTapTimeMs: Long = 0
    var touchDownMs: Long = 0

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDownMs = System.currentTimeMillis()
            MotionEvent.ACTION_UP -> {
                handler.removeCallbacksAndMessages(null)

                if (System.currentTimeMillis() - touchDownMs > ViewConfiguration.getTapTimeout()) {
                    //it was not a tap

                    numberOfTaps = 0
                    lastTapTimeMs = 0
                    return false
                }

                if (numberOfTaps > 0 &&
                    System.currentTimeMillis() - lastTapTimeMs < ViewConfiguration.getDoubleTapTimeout()
                ) {
                    numberOfTaps += 1
                } else {
                    numberOfTaps = 1
                }

                lastTapTimeMs = System.currentTimeMillis()

                if (numberOfTaps == 3) {
                    listener()
                    return true
                }

            }
        }

        return false
    }
}