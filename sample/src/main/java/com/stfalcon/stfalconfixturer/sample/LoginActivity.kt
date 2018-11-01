package com.stfalcon.stfalconfixturer.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.stfalcon.stfalconfixturer.extensions.setFixtureTag
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
        }

        loginEmailEt.setFixtureTag("email")
        loginPasswordEt.setFixtureTag("password")
    }
}
