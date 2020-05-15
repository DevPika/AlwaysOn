package io.github.domi04151309.alwayson.preferences

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import io.github.domi04151309.alwayson.*
import io.github.domi04151309.alwayson.objects.Theme

class LookAndFeelPreferences : AppCompatActivity(),
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.set(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, PreferenceLookAndFeel())
                .commit()
    }

    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
        val fragment = supportFragmentManager.fragmentFactory.instantiate(
                classLoader,
                pref.fragment)
        fragment.arguments = pref.extras
        fragment.setTargetFragment(caller, 0)
        supportFragmentManager.beginTransaction()
                .replace(R.id.settings, fragment)
                .addToBackStack(null)
                .commit()
        return true
    }

    class PreferenceLookAndFeel : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.pref_look_and_feel)
            findPreference<Preference>("pref_watch_face")!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                startActivity(Intent(context, LAFWatchFacePreferences::class.java))
                true
            }
            findPreference<Preference>("pref_background")!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                startActivity(Intent(context, LAFBackgroundPreferences::class.java))
                true
            }
            findPreference<Preference>("pref_behavior")!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                startActivity(Intent(context, LAFBehaviorPreferences::class.java))
                true
            }
            findPreference<Preference>("pref_other")!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                startActivity(Intent(context, LAFOtherPreferences::class.java))
                true
            }
        }
    }

}
