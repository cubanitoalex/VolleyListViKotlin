package cu.sld.hluciavolleylistvikotlin

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import cu.sld.hluciavolleylistvikotlin.R
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val URLstring = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php"
    private var listView: ListView? = null
    internal lateinit var dataModelArrayList: ArrayList<DataModel>
    private var listAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lv)

        retrieveJSON()

    }

    private fun retrieveJSON() {

        showSimpleProgressDialog(
            this,
            "Cargando...",
            "Conectando al api",
            false
        )

        val stringRequest = StringRequest(Request.Method.GET, URLstring,
            Response.Listener { response ->
                Log.d("strrrrr", ">>$response")

                try {

                    val obj = JSONObject(response)
                    if (obj.optString("status") == "true") {

                        dataModelArrayList = ArrayList()
                        val dataArray = obj.getJSONArray("data")

                        for (i in 0 until dataArray.length()) {

                            val playerModel = DataModel()
                            val dataobj = dataArray.getJSONObject(i)

                            playerModel.setNames(dataobj.getString("name"))
                            playerModel.setCountrys(dataobj.getString("country"))
                            playerModel.setCitys(dataobj.getString("city"))
                            playerModel.setimgURLs(dataobj.getString("imgURL"))

                            dataModelArrayList.add(playerModel)

                        }

                        setupListview()

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                //displaying the error in toast if occurrs
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            })

        // request queue
        val requestQueue = Volley.newRequestQueue(this)

        requestQueue.add(stringRequest)


    }

    private fun setupListview() {
        removeSimpleProgressDialog()  //will remove progress dialog
        listAdapter = ListAdapter(this, dataModelArrayList)
        listView!!.adapter = listAdapter
    }

    companion object {
        private var mProgressDialog: ProgressDialog? = null

        fun removeSimpleProgressDialog() {
            try {
                if (mProgressDialog != null) {
                    if (mProgressDialog!!.isShowing) {
                        mProgressDialog!!.dismiss()
                        mProgressDialog = null
                    }
                }
            } catch (ie: IllegalArgumentException) {
                ie.printStackTrace()

            } catch (re: RuntimeException) {
                re.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun showSimpleProgressDialog(
            context: Context, title: String,
            msg: String, isCancelable: Boolean
        ) {
            try {
                if (mProgressDialog == null) {
                    mProgressDialog = ProgressDialog.show(context, title, msg)
                    mProgressDialog!!.setCancelable(isCancelable)
                }

                if (!mProgressDialog!!.isShowing) {
                    mProgressDialog!!.show()
                }

            } catch (ie: IllegalArgumentException) {
                ie.printStackTrace()
            } catch (re: RuntimeException) {
                re.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}