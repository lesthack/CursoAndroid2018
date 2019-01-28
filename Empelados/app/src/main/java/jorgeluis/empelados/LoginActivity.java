package jorgeluis.empelados;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUser, edtPwd;
    Button btnLogin;
    CheckBox chkSesion;
    SharedPreferences objSP;

    BDEmpleados objDBemp;
    SQLiteDatabase objSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUser);
        edtPwd = findViewById(R.id.edtPwd);
        btnLogin = findViewById(R.id.btnLogin);
        chkSesion = findViewById(R.id.chkSesion);
        btnLogin.setOnClickListener(this);

        objDBemp = new BDEmpleados(this, "DBEMPLEADOS", null, 1);
        objSQL = objDBemp.getReadableDatabase();

        objSP = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String token = objSP.getString("token", "sesion_no_iniciada");

        //edtUser.setText(token);

        if (!token.equals("sesion_no_iniciada")) {

            Bundle datos = new Bundle();
            Intent intListado = new Intent(this, ListadoActivity.class);
            datos.putString("token", token);
            intListado.putExtras(datos);
            startActivity(intListado);
            finish();

        }
    }

    @Override
    public void onClick(View v) {
        String user = edtUser.getText().toString();
        String pass = edtPwd.getText().toString();
        Cursor c = objSQL.rawQuery("SELECT * FROM usuarios WHERE nomuser='"+ user+"' AND pwduser='" + pass + "'", null);
        if(c.moveToFirst()){
            if( chkSesion.isChecked() ){
                SharedPreferences.Editor editorSP = objSP.edit();
                editorSP.putString("token","mitokengenerado");
                editorSP.commit();
            }
            Bundle datos = new Bundle();
            Intent intListado = new Intent(this, ListadoActivity.class);
            datos.putString("token", user);
            intListado.putExtras(datos);
            startActivity(intListado);
        }
        else{
            Snackbar.make(v, "Datos incorrectos", Snackbar.LENGTH_LONG).show();
        }
    }
}
