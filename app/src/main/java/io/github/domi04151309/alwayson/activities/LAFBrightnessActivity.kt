package io.github.domi04151309.alwayson.activities

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import io.github.domi04151309.alwayson.R
import io.github.domi04151309.alwayson.actions.alwayson.AlwaysOn
import io.github.domi04151309.alwayson.helpers.P
import io.github.domi04151309.alwayson.helpers.Theme

class LAFBrightnessActivity : AppCompatActivity() {
    private lateinit var brightnessSwitch: SwitchCompat
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.set(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brightness)
        brightnessSwitch = findViewById(R.id.brightnessSwitch)
        seekBar = findViewById(R.id.seekBar)
        seekBar.progress = P.FORCE_BRIGHTNESS_VALUE_DEFAULT

        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    window.attributes.screenBrightness = progress / 255.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // Do nothing.
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // Do nothing.
                }
            },
        )
    }

    override fun onStart() {
        super.onStart()
        brightnessSwitch.isChecked = P.getPreferences(this).getBoolean(P.FORCE_BRIGHTNESS, P.FORCE_BRIGHTNESS_DEFAULT)
        seekBar.progress = P.getPreferences(this).getInt(P.FORCE_BRIGHTNESS_VALUE, P.FORCE_BRIGHTNESS_VALUE_DEFAULT)
    }

    override fun onStop() {
        super.onStop()
        P.getPreferences(this)
            .edit()
            .putBoolean(P.FORCE_BRIGHTNESS, brightnessSwitch.isChecked)
            .putInt(P.FORCE_BRIGHTNESS_VALUE, seekBar.progress)
            .apply()
        AlwaysOn.finish()
    }
}
