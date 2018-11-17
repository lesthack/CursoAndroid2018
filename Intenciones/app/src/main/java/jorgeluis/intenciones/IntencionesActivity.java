package jorgeluis.intenciones;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntencionesActivity extends AppCompatActivity {

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

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private String telefono = "4612279093";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intenciones);

        ButterKnife.bind(this);

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
}
