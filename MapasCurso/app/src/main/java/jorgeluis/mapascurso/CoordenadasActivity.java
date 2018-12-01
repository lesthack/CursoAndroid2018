package jorgeluis.mapascurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoordenadasActivity extends AppCompatActivity {

    /*private EditText text_lat;
    private EditText text_lon;
    private Button button_changepos;*/
    @BindView(R.id.edittext_lat) EditText text_lat;
    @BindView(R.id.edittext_lon) EditText text_lon;
    @BindView(R.id.button_changepos) Button button_changepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordenadas);

        /*text_lat = findViewById(R.id.edittext_lat);
        text_lon = findViewById(R.id.edittext_lon);
        button_changepos = findViewById(R.id.button_changepos);

        button_changepos.setOnClickListener(this);*/

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_changepos)
    public void abrirMap(){
        Intent intent = new Intent(this, MapaActivity.class);
        try{
            LatLng latlng = new LatLng(Float.parseFloat(text_lat.getText().toString()), Float.parseFloat(text_lon.getText().toString()));
            intent.putExtra("latlng", latlng);
        }
        catch(NumberFormatException e){
            Log.i("Dev", "Sin coordenada");
        }

        startActivity(intent);
        finish();
    }
}
