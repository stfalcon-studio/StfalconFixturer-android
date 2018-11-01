package com.stfalcon.stfalconfixturer.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.stfalcon.stfalconfixturer.extensions.setFixtureTag
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        secondNameEt.setFixtureTag("name")
        secondAddressEt.setFixtureTag("address")
    }
}
