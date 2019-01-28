package jorgeluis.empelados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDEmpleados extends SQLiteOpenHelper {

    String query1 =
            "CREATE TABLE usuarios(" +
                "iduser INTEGER," +
                "nomuser VARCHAR(15)," +
                "pwduser VARCHAR(32)," +
                "PRIMARY KEY(iduser)" +
            ")";

    String query2 =
            "CREATE TABLE empleados(" +
                "idemp INTEGER," +
                "nomemp VARCHAR(25)," +
                "apepat VARCHAR(25)," +
                "apemat VARCHAR(25)," +
                "telemp VARCHAR(10)," +
                "email VARCHAR(35)," +
                "iddepto INTEGER," +
                "PRIMARY KEY (idemp)," +
                "FOREIGN KEY (iddepto) REFERENCES departamentos(iddepto)" +
            ")";

    String query3 =
            "CREATE TABLE departamentos(" +
                "iddepto INTEGER," +
                "nomdepto VARCHAR(30)," +
                "PRIMARY KEY (iddepto)" +
            ")";

    public BDEmpleados(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.beginTransaction();
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL("INSERT INTO usuarios(nomuser, pwduser) VALUES('jorge','luis')");
        db.execSQL("INSERT INTO departamentos(nomdepto) VALUES('Sistemas')");
        //db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
