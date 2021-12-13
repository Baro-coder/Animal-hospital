package pl.wat.animal_hospital.android_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import pl.wat.animal_hospital.android_app.BASEURL
import pl.wat.animal_hospital.android_app.R
import pl.wat.animal_hospital.android_app.ui.client.ClientDashboard
import pl.wat.animal_hospital.android_app.ui.doctor.DoctorDashboard
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.Map as CollectionsMap

class Register : AppCompatActivity() {

    private lateinit var button: Button
    var radioGroup: RadioGroup? = null
    lateinit var radioButtonSelected: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        radioGroup = findViewById(R.id.radioGroup_accountTypes)
        button = findViewById(R.id.button_toRegister)
        button.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { view ->
        when (view.id){
            R.id.button_toRegister -> {
                val mailBox: EditText = findViewById(R.id.edit_mail)
                val nameBox: EditText = findViewById(R.id.edit_name)
                val surnameBox: EditText = findViewById(R.id.edit_surname)
                val passwdBox: EditText = findViewById(R.id.edit_passwd)
                val passwdConfirmBox: EditText = findViewById(R.id.edit_passwdConfirm)
                val selectedType: Int? = radioGroup?.checkedRadioButtonId

                if(mailBox.text.isEmpty() || nameBox.text.isEmpty() || surnameBox.text.isEmpty() || passwdBox.text.isEmpty() || passwdConfirmBox.text.isEmpty() || selectedType == -1)
                {
                    mailBox.setText("")
                    nameBox.setText("")
                    surnameBox.setText("")
                    passwdBox.setText("")
                    passwdConfirmBox.setText("")
                    radioGroup!!.clearCheck()
                    Toast.makeText(this, resources.getString(R.string._fillTheGapsPl), Toast.LENGTH_SHORT).show()
                }
                else
                {
                    radioButtonSelected = selectedType?.let { findViewById(it) }!!
                    val mail = mailBox.text.toString()
                    val name = nameBox.text.toString()
                    val surname = surnameBox.text.toString()
                    val passwd = passwdBox.text.toString()
                    val passwdConfirm = passwdConfirmBox.text.toString()
                    val accountType = radioButtonSelected.text.toString()

                    if (passwd != passwdConfirm)
                    {
                        mailBox.setText("")
                        nameBox.setText("")
                        surnameBox.setText("")
                        passwdBox.setText("")
                        passwdConfirmBox.setText("")
                        radioGroup!!.clearCheck()
                        Toast.makeText(this, resources.getString(R.string._passwdConfirmErrorPl), Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val url = "$BASEURL/users/register"
                        val queue = Volley.newRequestQueue(this)
                        val params = HashMap<String, String>()
                        params["email"] = mail
                        params["name"] = name
                        params["surname"] = surname
                        params["password"] = passwd
                        params["accountType"] = accountType
                        val jsonObject = JSONObject(params as kotlin.collections.Map<*, *>)

                        val request = JsonObjectRequest(Request.Method.POST,url,jsonObject,
                                { response ->
                                    Toast.makeText(this, resources.getString(R.string._registeredPL), Toast.LENGTH_SHORT).show()
                                    if(accountType == "KLIENT")
                                    {
                                        val intent = Intent(this, ClientDashboard::class.java)
                                        intent.putExtra("name", name)
                                        intent.putExtra("surname", surname)
                                        intent.putExtra("email", mail)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else
                                    {
                                        val intent = Intent(this, DoctorDashboard::class.java)
                                        intent.putExtra("name", name)
                                        intent.putExtra("surname", surname)
                                        intent.putExtra("email", mail)
                                        startActivity(intent)
                                        finish()
                                    }
                                }, { error ->
                            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                        })
                        queue.add(request)
                    }
                }
            }
        }
    }
}
