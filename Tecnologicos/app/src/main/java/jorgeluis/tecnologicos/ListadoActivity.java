package jorgeluis.tecnologicos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListadoActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {

    private String ws_url = "http://ws.itcelaya.edu.mx:8080/intertecs/apirest/institucion/listado";
    private String ws_user = "intertecs";
    private String ws_pass = "1nt3rt3c5";
    private RequestQueue qRequests;
    private List<Institucion> list_institucion = new ArrayList<Institucion>();
    private InstitucionAdapter adaptador;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        mRecyclerView = findViewById(R.id.recListado);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        qRequests = Volley.newRequestQueue(this);

        //JsonObjectRequest jRequest = new JsonObjectRequest(Request.Method.GET, ws_url, null,this, this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ws_url, this, this){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",ws_user,ws_pass);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        qRequests.add(stringRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        try {
            response = new String(response.getBytes("ISO-8859-1"));
            JSONObject json_data = new JSONObject(response);
            JSONArray items = json_data.getJSONArray("institucion");
            for(int i=0; i<items.length();i++){
                JSONObject item = items.optJSONObject(i);
                Institucion instituto = new Institucion();
                instituto.setId(item.getInt("id_institucion"));
                instituto.setNombre(item.getString("institucion"));
                instituto.setNombre_corto(item.getString("nombre_corto"));
                instituto.setLogo(item.getString("logotipo"));
                list_institucion.add(instituto);
            }

            adaptador = new InstitucionAdapter(this, list_institucion);
            mRecyclerView.setAdapter(adaptador);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
