package jorgeluis.intenciones;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntencionesActivity extends AppCompatActivity implements OnBMClickListener {

    @BindView(R.id.button_web)
    TextView button_web;
    @BindView(R.id.button_telefono)
    TextView button_telefono;
    @BindView(R.id.button_sms)
    TextView button_sms;
    @BindView(R.id.button_email)
    TextView button_email;
    @BindView(R.id.button_foto)
    TextView button_foto;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.bmb)
    BoomMenuButton bmb;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private String telefono = "4612279093";
    private int[] arrButtons = {
        R.mipmap.flag_mexico,
        R.mipmap.flag_usa,
        R.mipmap.flag_esperanto
    };
    private String[] arrLangs = {
        "es",
        "en",
        "eo"
    };
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intenciones);

        if(savedInstanceState != null){
            index = savedInstanceState.getInt("index");
            changeLang();
        }

        ButterKnife.bind(this);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_3_1);

        for(int i=0; i<bmb.getPiecePlaceEnum().pieceNumber();i++){
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .normalImageRes(arrButtons[i])
                    .listener(this);

            bmb.addBuilder(builder);
        }
    }


    @OnClick(R.id.button_web)
    public void openWeb() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://jorgeluis.com.mx"));
        startActivity(intent);
    }

    @OnClick({R.id.button_telefono})
    public void llamarTelefono() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telefono));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
            //ActivityCompat.requestPermissions(this, new String{Manifest.permission.CALL_PHONE});
        }
        startActivity(intent);
    }

    @OnClick(R.id.button_sms)
    public void enviarSMS(){
        SmsManager sms = SmsManager.getDefault();
        //sms.sendTextMessage(telefono, null, "Que onda profe", null, null);
        ArrayList<String> arrSMS = sms.divideMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent quis accumsan metus, a pellentesque nunc. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam quis congue mauris. Phasellus convallis sed dolor et aliquet. Suspendisse vel viverra velit, et vulputate risus. Nullam non orci non tortor aliquam scelerisque. Vivamus posuere auctor sapien, sed lobortis libero consectetur sed. Nunc sapien lectus, pretium nec est vel, venenatis tristique nulla. Aenean hendrerit massa ut tellus dictum hendrerit. Suspendisse imperdiet porta elementum. Sed dignissim eu arcu venenatis tempus. Pellentesque a dolor pharetra, pretium elit non, finibus tortor. Suspendisse vel malesuada ligula. Quisque euismod metus ex, et bibendum sapien maximus eget.");
        //sms.sendMultipartTextMessage(telefono, null, arrSMS, null, null);
        sms.sendMultipartTextMessage("4612180833", null, arrSMS, null, null);
        Toast.makeText(this,"Mensaje enviado", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.button_email)
    public void enviarEmail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Prueba");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"yo@jorgeluis.com.mx"});
        intent.putExtra(Intent.EXTRA_TEXT, "Estimado muy amigo mio...");
        startActivity(intent);
    }

    @OnClick(R.id.button_foto)
    public void tomarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picture.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onBoomButtonClick(int index) {
        this.index = index;
        changeLang();
        recreate();
    }

    public void changeLang(){
        Locale local = new Locale(arrLangs[index]);
        Locale.setDefault(local);

        Configuration conf = new Configuration();
        conf.locale = local;
        getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", index);
    }

}
