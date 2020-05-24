package com.baszczyk.mercdream

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.baszczyk.mercdream.databinding.ActivityLoggingBinding
import kotlinx.android.synthetic.main.fragment_logging.*

class LoggingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoggingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = DataBindingUtil.setContentView( this , R.layout.activity_logging)
    }
}
