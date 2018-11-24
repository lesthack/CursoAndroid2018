package jorgeluis.mapascurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class CoordenadasActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText text_lat;
    private EditText text_lon;
    private Button button_changepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordenadas);

        text_lat = findViewById(R.id.edittext_lat);
        text_lon = findViewById(R.id.edittext_lon);
        button_changepos = findViewById(R.id.button_changepos);

        button_changepos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        LatLng latlng = new LatLng(Float.parseFloat(text_lat.getText().toString()), Float.parseFloat(text_lon.getText().toString()));
        Intent intent = new Intent(this, MapaActivity.class);
        intent.putExtra("latlng", latlng);
        startActivity(intent);
        finish();
    }
}
