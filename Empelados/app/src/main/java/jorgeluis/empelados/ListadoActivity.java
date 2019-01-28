package jorgeluis.empelados;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListadoActivity extends AppCompatActivity {
    BDEmpleados objDBemp;
    SQLiteDatabase objSQL;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        Bundle datos = getIntent().getExtras();

        objDBemp = new BDEmpleados(this, "DBEMPLEADOS", null, 1);
        objSQL = objDBemp.getReadableDatabase();

        mRecyclerView = findViewById(R.id.lista_empleados);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        EmpleadoAdapter EmpleadosAdapter = new EmpleadoAdapter(this, getListaEmpleados());
        mRecyclerView.setAdapter(EmpleadosAdapter);
    }

    private List<Empleado> getListaEmpleados(){
        List<Empleado> tlst = new ArrayList<>();
        String query = "" +
                "SELECT " +
                "empleados.idemp," +
                "departamentos.iddepto," +
                "empleados.nomemp," +
                "empleados.apepat," +
                "empleados.apemat," +
                "empleados.telemp," +
                "empleados.email," +
                "departamentos.nomdepto" +
            " FROM empleados " +
            "   LEFT JOIN departamentos ON departamentos.iddepto = empleados.iddepto";
        Cursor c = objSQL.rawQuery(query, null);
        if(c.moveToFirst()){
            do {
                tlst.add(new Empleado(
                        c.getInt(0),
                        c.getInt(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7)
                ));
            }
            while (c.moveToNext());
        }
        return tlst;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add_empleado:
                Intent intent = new Intent(this, EmpleadoActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

}
