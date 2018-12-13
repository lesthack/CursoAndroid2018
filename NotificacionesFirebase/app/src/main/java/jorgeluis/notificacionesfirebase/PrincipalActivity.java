package jorgeluis.notificacionesfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class PrincipalActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    private String token;
    private RequestQueue qRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        qRequest = Volley.newRequestQueue(this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.i("Dev", "Token: " + token);
                //RegistraToken();
            }
        });
    }

    private void RegistraToken(){
        String url = "http://192.168.43.26/~isctorres/curso/registrar_token.php?token="+token;
        StringRequest sReq = new StringRequest(Request.Method.GET, url,this, this);
        qRequest.add(sReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Log.i("Dev", response);
    }
}
