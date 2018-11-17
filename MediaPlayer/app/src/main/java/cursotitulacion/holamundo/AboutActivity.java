package cursotitulacion.holamundo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.label_email) TextView label_email;
    @BindView(R.id.label_website) TextView label_webiste;
    @BindView(R.id.label_phone) TextView label_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        Linkify.addLinks(label_email, Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(label_webiste, Linkify.WEB_URLS);
        Linkify.addLinks(label_phone, Linkify.PHONE_NUMBERS);
    }
}
