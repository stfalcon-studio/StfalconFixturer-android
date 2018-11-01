package com.stfalcon.stfalcon_fixturer.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.stfalcon.stfalcon_fixturer.extensions.setFixtureTag
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        secondNameEt.setFixtureTag("name")
        secondAddressEt.setFixtureTag("address")
    }
}
