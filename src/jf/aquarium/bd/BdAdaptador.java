package jf.aquarium.bd;
	
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
	
	public class BdAdaptador {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_FECHA = "fecha";
	public static final String KEY_PH = "ph";
	public static final String KEY_NO2 = "no2";
	public static final String KEY_NH3 = "nh3";
	public static final String KEY_GH = "gh";
	public static final String KEY_ABONO_PO = "abono_po";
	public static final String KEY_ABONO_NI = "abono_ni";
	public static final String KEY_ABONO_C = "abono_c";
	public static final String KEY_OBSERVACIONES = "observaciones";
	public static final String KEY_FOTO = "foto";
	public static final String KEY_CAMBIO = "cambio";
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "MyDB";
	private static final String DATABASE_TABLE = "parametros";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "CREATE TABLE parametros (_id INTEGER PRIMARY KEY, fecha TIMESTAMP DEFAULT (DATETIME('now'))," +
												   " ph TEXT, no2 TEXT, nh3 TEXT, gh TEXT, observaciones TEXT, abono_c INTEGER, abono_po INTEGER, abono_ni INTEGER, foto TEXT, cambio TEXT)";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public BdAdaptador(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
			e.printStackTrace();
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
			+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS parametros");
			onCreate(db);
		}
	}
	
	private String[] allColumns = { KEY_ROWID,KEY_FECHA, KEY_PH, KEY_NO2, KEY_NH3, KEY_GH, KEY_ABONO_PO, KEY_ABONO_NI, KEY_ABONO_C, KEY_OBSERVACIONES, KEY_FOTO, KEY_CAMBIO};
	
	//---opens the database---
	public BdAdaptador open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	//---closes the database---
	public void close()
	{
		DBHelper.close();
	}
	//---insert into the database---
	public long insertDatos( Acuario acuario)
	{	
		
		ContentValues initialValues = new ContentValues();
    	initialValues.put(KEY_PH , acuario.getPh());
		initialValues.put(KEY_NO2 , acuario.getNo2());
		initialValues.put(KEY_NH3 , acuario.getNh3());
		initialValues.put(KEY_GH , acuario.getGh());
		initialValues.put(KEY_ABONO_C , acuario.getAbonoC());
		initialValues.put(KEY_ABONO_PO , acuario.getAbonoP());
		initialValues.put(KEY_ABONO_NI , acuario.getAbonoN());
		initialValues.put(KEY_OBSERVACIONES , acuario.getObservaciones());
		initialValues.put(KEY_FOTO , acuario.getFoto());
		initialValues.put(KEY_CAMBIO , acuario.getCambioAgua());
		try {
			return db.insertOrThrow (DATABASE_TABLE, null, initialValues);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//---Borra una entrada---
	public boolean borraParametros(long rowId)
	{
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	//---devuelve todas las entradas informacion básica---
	public Cursor getHistorial()
	{
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_FECHA, KEY_PH}, null, null, null, null, null);
	}
	
	//---devuelve una entrada en concreto---
	public Cursor getParametros(long rowId) throws SQLException
	{
		Cursor mCursor =
		db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_FECHA, KEY_PH, KEY_NO2, KEY_NH3, KEY_GH, KEY_ABONO_C, KEY_ABONO_NI, KEY_ABONO_PO, KEY_OBSERVACIONES, KEY_FOTO, KEY_CAMBIO}, KEY_ROWID + "=" + rowId, null,
		null, null, null, null);
		if (mCursor != null) {
		mCursor.moveToFirst();
	}
	return mCursor;
	}
	
	public List<Acuario> getAllParametros() {
		List<Acuario> valores = new ArrayList<Acuario>();
		Cursor cursor = db.query( DATABASE_TABLE,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Acuario acuario = cursorToAcuario(cursor);
			cursor.moveToNext();
			valores.add(acuario);
			
		}
		// Make sure to close the cursor
		cursor.close();
		return valores;
	}
	
	public String[] getValoresParametro(String parametro){
		//db.query(DATABASE_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
		return null;
	}
	
	private Acuario cursorToAcuario(Cursor cursor) {
		Acuario acuario = new Acuario();
		acuario.setId(cursor.getLong(0));
		acuario.setFecha(cursor.getString(1));
		acuario.setPh(cursor.getString(2));
		acuario.setNo2(cursor.getString(3));
		acuario.setNh3(cursor.getString(4));
		acuario.setGh(cursor.getString(5));
		acuario.setAbonoC(cursor.getInt(6));
		acuario.setAbonoN(cursor.getInt(7));
		acuario.setAbonoP(cursor.getInt(8));
		acuario.setObservaciones(cursor.getString(9));
		acuario.setFoto(cursor.getString(10));
		acuario.setCambioAgua(cursor.getString(11));
		return acuario;
	}
	
}
