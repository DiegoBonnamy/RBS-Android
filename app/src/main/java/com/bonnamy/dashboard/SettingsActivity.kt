package com.bonnamy.dashboard

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.preference.PreferenceManager
import com.bonnamy.dashboard.bo.Athlete
import com.bonnamy.dashboard.helpers.ReseauHelper
import com.bonnamy.dashboard.ws.RetrofitSingleton
import com.bonnamy.dashboard.ws.WSInterface
import com.bonnamy.dashboard.ws.WSResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ATHLETE_ID = "athleteId2"
        const val CLE_PREFERENCES = "preferences"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)

        setTitle("Réglages")

        val athleteId = intent.getIntExtra(EXTRA_ATHLETE_ID,1)

        /* SPORT PRINCIPAL */
        val mainSport: Spinner = findViewById(R.id.mainSport)
        val items: MutableList<String> = ArrayList()
        items.add("Cyclisme")
        items.add("Running")
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.item_spinner, items)
        mainSport.setAdapter(adapter);
        val sportPreference: Int = preferences.getInt(CLE_PREFERENCES, 0)
        mainSport.setSelection(sportPreference)

        mainSport?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
                val editor = preferences.edit()
                editor.putInt(CLE_PREFERENCES, 0)
                editor.apply()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val editor = preferences.edit()
                editor.putInt(CLE_PREFERENCES, position)
                editor.apply()
            }

        }

        /* NOM + COULEUR + OBJECTIFS */

        val txtSettingsProfilName: TextView = findViewById(R.id.txt_settingsProfilName)
        val editColor: EditText = findViewById(R.id.edit_color)
        val colorView: TextView = findViewById(R.id.colorView)
        val editObjectifCyclisme: EditText = findViewById(R.id.edit_objectifCyclisme)
        val editObjectifRunning: EditText = findViewById(R.id.edit_objectifRunning)

        if (ReseauHelper.estConnecte(this)) {
            CoroutineScope(Dispatchers.IO).launch {
                val athlete: Athlete = service.getAthlete(athleteId)

                withContext(Dispatchers.Main) {

                    txtSettingsProfilName.text = athlete.prenom

                    editColor.setText(athlete.couleur)
                    colorView.setBackgroundColor(Color.parseColor(athlete.couleur))

                    editObjectifCyclisme.setText(athlete.objectifCyclisme)
                    editObjectifRunning.setText(athlete.objectifRunning)
                }
            }
        }

        if (ReseauHelper.estConnecte(this)) {
            editColor.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                    editColor.removeTextChangedListener(this);
                    var newColor: String = editColor.text.toString()
                    val pattern = Regex("^#([a-fA-F0-9]{6})$")
                    if (pattern.containsMatchIn(newColor)) {
                        newColor = newColor.replace("#", "")

                        CoroutineScope(Dispatchers.IO).launch {
                            val result: WSResponse = service.setColor(athleteId, newColor)

                            withContext(Dispatchers.Main) {
                                colorView.setBackgroundColor(Color.parseColor("#" + newColor))
                            }
                        }

                    }
                    editColor.addTextChangedListener(this);
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                }
            })

            editObjectifCyclisme.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                    editObjectifCyclisme.removeTextChangedListener(this);
                    val pattern = Regex("^\\+?\\d+\$")
                    if (pattern.containsMatchIn(editObjectifCyclisme.text.toString())) {
                        val newObjectif: Int = editObjectifCyclisme.text.toString().toInt()
                        if (newObjectif > 0) {
                            CoroutineScope(Dispatchers.IO).launch {
                                val result: WSResponse =
                                    service.setObjectifCyclisme(athleteId, newObjectif)
                            }
                        }
                    }
                    editObjectifCyclisme.addTextChangedListener(this);
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                }
            })

            editObjectifRunning.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                    editObjectifRunning.removeTextChangedListener(this);
                    val pattern = Regex("^\\+?\\d+\$")
                    if (pattern.containsMatchIn(editObjectifRunning.text.toString())) {
                        val newObjectif: Int = editObjectifRunning.text.toString().toInt()
                        if (newObjectif > 0) {
                            CoroutineScope(Dispatchers.IO).launch {
                                val result: WSResponse =
                                    service.setObjectifRunning(athleteId, newObjectif)
                            }
                        }
                    }
                    editObjectifRunning.addTextChangedListener(this);
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                }
            })
        }
    }
}