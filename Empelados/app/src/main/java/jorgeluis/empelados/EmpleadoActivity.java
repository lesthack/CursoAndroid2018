package jorgeluis.empelados;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EmpleadoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nombre;
    EditText apellido_paterno;
    EditText apellido_materno;
    EditText telefono;
    EditText email;
    Spinner departamentos;
    Button btn_save;

    ArrayAdapter<String> adapter_departamentos;

    BDEmpleados objDBemp;
    SQLiteDatabase objSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);


        nombre = findViewById(R.id.edtEmpNom);
        apellido_paterno = findViewById(R.id.edtEmpApPat);
        apellido_materno = findViewById(R.id.edtEmpApMat);
        telefono = findViewById(R.id.edtEmpTel);
        email = findViewById(R.id.edtEmpEmail);
        departamentos = findViewById(R.id.spnDepartamentos);
        btn_save = findViewById(R.id.btnSaveEmp);
        btn_save.setOnClickListener(this);

        adapter_departamentos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        objDBemp = new BDEmpleados(this, "DBEMPLEADOS", null, 1);
        objSQL = objDBemp.getWritableDatabase();

        load_departamentos();

        departamentos.setAdapter(adapter_departamentos);
    }

    private void load_departamentos(){
        Cursor c = objSQL.rawQuery("SELECT iddepto, nomdepto FROM departamentos",null);
        if(c.moveToFirst())
        {
            do {
                adapter_departamentos.add(c.getString(1));
            }
            while (c.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSaveEmp){
            int iddepto = getDepto(departamentos.getSelectedItem().toString());
            //iddepto = -1;
            if(nombre.getText().length()==0){
                Toast.makeText(this, "Ingresa el nombre del empleado", Toast.LENGTH_LONG).show();
            }
            else{
                String query = "INSERT INTO empleados(nomemp, apepat, apemat, telemp, email, iddepto) " +
                        "VALUES(" +
                        "'" + nombre.getText().toString() + "'," +
                        "'" + apellido_materno.getText().toString() + "'," +
                        "'" + apellido_materno.getText().toString() + "'," +
                        "'" + telefono.getText().toString() + "'," +
                        "'" + email.getText().toString() + "'," +
                        String.valueOf(iddepto) +
                        ")";
                objSQL.execSQL("PRAGMA FOREIGN_KEYS=ON");
                objSQL.execSQL(query);
                objSQL.execSQL("PRAGMA FOREIGN_KEYS=OFF");
                Toast.makeText(this, "Datos guardados", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private int getDepto(String nombre){
        Cursor c = objSQL.rawQuery(
                "SELECT iddepto FROM departamentos " +
                "WHERE nomdepto='" +nombre+"'",null);
        if(c.moveToFirst()){
            return c.getInt(0);
        }
        return 0;
    }
}











