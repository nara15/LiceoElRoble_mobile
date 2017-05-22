package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
//

/**
 * Created by JoseR on 17/4/2017.
 *
 * https://developer.android.com/training/basics/data-storage/databases.html?hl=es-419
 */

public class BaseDeDatos extends SQLiteOpenHelper {
    private static BaseDeDatos bd;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "liceoElRoble.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EsquemaBaseDeDatos.FeedEntry.TABLE_NAME + " (" +
                    EsquemaBaseDeDatos.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EsquemaBaseDeDatos.FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE +  " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EsquemaBaseDeDatos.FeedEntry.TABLE_NAME;

    private BaseDeDatos(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static BaseDeDatos getInstancia(Context pContext){
        if (bd == null){
            bd = new BaseDeDatos(pContext);
        }
        return bd;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<String> SelectBD(BaseDeDatos pBase){
        SQLiteDatabase db = pBase.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                EsquemaBaseDeDatos.FeedEntry._ID,
                EsquemaBaseDeDatos.FeedEntry.COLUMN_NAME_TITLE
        };

// Filter results WHERE "title" = 'My Title'
        String selection = EsquemaBaseDeDatos.FeedEntry.COLUMN_NAME_TITLE ;//+ " = ?";
        //String[] selectionArgs = { pCondicion };

// How you want the results sorted in the resulting Cursor
        //String sortOrder =
        //        EsquemaBaseDeDatos.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor c = db.query(
                EsquemaBaseDeDatos.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        c.moveToFirst();
        ArrayList<String> suscritos = new ArrayList<>();
        for(int i=0;  i < c.getCount();i++){
            suscritos.add(c.getString(1));
            i++;
            c.moveToNext();

        }
        return suscritos;

    }

    public long insertarBD(BaseDeDatos pBase, String pSeccion, Context pVentana){
        try {
            SQLiteDatabase db = pBase.getWritableDatabase();

// Create a new map of values, where column names are the keys

            ContentValues values = new ContentValues();
            values.put(EsquemaBaseDeDatos.FeedEntry.COLUMN_NAME_TITLE, pSeccion);

// Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(EsquemaBaseDeDatos.FeedEntry.TABLE_NAME, null, values);
            return newRowId;
        }

        catch(Exception e){
            ObtencionDatosWeb.mostrarTexto(pVentana.getApplicationContext(),"Ha ocurrido un error en las sucripciones");
            return (long )-1;

        }


    }

    public void eliminarDato(BaseDeDatos pBase, String pSeccion){
        SQLiteDatabase db = pBase.getReadableDatabase();
        // Define 'where' part of query.
        String selection = EsquemaBaseDeDatos.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { pSeccion };
// Issue SQL statement.

        db.delete(EsquemaBaseDeDatos.FeedEntry.TABLE_NAME, selection, selectionArgs);


    }

}
