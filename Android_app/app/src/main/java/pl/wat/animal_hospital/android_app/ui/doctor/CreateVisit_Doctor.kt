package pl.wat.animal_hospital.android_app.ui.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import pl.wat.animal_hospital.android_app.BASEURL
import pl.wat.animal_hospital.android_app.R
import kotlin.math.min

class CreateVisit_Doctor : AppCompatActivity() {

    lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_visit__doctor)

        btnAdd = findViewById(R.id.button_add)
        btnAdd.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { view ->
        when (view.id){
            R.id.button_add -> {

                val dateBox: EditText = findViewById(R.id.edit_date)
                val hoursBox: EditText = findViewById(R.id.edit_hours)
                val minutesBox: EditText = findViewById(R.id.edit_minutes)

                if(dateBox.text.isEmpty() || hoursBox.text.isEmpty() || minutesBox.text.isEmpty())
                {
                    dateBox.setText("")
                    hoursBox.setText("")
                    minutesBox.setText("")
                    Toast.makeText(this, resources.getString(R.string._fillTheGapsPl), Toast.LENGTH_SHORT).show()
                }
                else
                {
                    val clientMail = intent.getStringExtra("clientMail")
                    val doctorMail = intent.getStringExtra("doctorMail")
                    var time = dateBox.text.toString()
                    time += "T${minutesBox.text}:${hoursBox.text}:00.000Z"

                    val url = "$BASEURL/visits"
                    val queue = Volley.newRequestQueue(this)
                    val params = HashMap<String, String>()
                    params["client"] = clientMail.toString()
                    params["doctor"] = doctorMail.toString()
                    params["animalAge"] = ""
                    params["animalBreed"] = ""
                    params["time"] = time
                    params["description"] = ""
                    val jsonObject = JSONObject(params as Map<*, *>)
                    val request = JsonObjectRequest(Request.Method.POST,url,jsonObject,
                            { response ->
                                Toast.makeText(this, resources.getString(R.string._doctorVisitAddedPL), Toast.LENGTH_SHORT).show()
                            }, { error ->
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                    })
                    queue.add(request)
                }
            }
        }
    }
}