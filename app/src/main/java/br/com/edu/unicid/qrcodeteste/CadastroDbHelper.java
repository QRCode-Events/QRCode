package br.com.edu.unicid.qrcodeteste;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CadastroDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cadastro.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "registros";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_DATA_NASCIMENTO = "data_nascimento";
    public static final String COLUMN_QR_CODE = "qr_code";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_SENHA = "senha";

    public CadastroDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES ="CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NOME + " TEXT," +
                COLUMN_DATA_NASCIMENTO + " TEXT," +
                COLUMN_QR_CODE + " TEXT," +
                COLUMN_EMAIL + " TEXT," + // Add email column
                COLUMN_SENHA + " TEXT)"; // Add senha column
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to insert a new registration
    public long inserirRegistro(String nome, String dataNascimento, String qrCode, String email, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_DATA_NASCIMENTO, dataNascimento);
        values.put(COLUMN_QR_CODE, qrCode);
        values.put(COLUMN_EMAIL, email); // Store email
        values.put(COLUMN_SENHA, senha); // Store senha
        return db.insert(TABLE_NAME, null, values);
    }

    // Method to get a registration by ID
    public Cursor getRegistroById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public Cursor getRegistroByNomeAndDataNascimento(String nome, String dataNascimento) {
        SQLiteDatabase db = this.getReadableDatabase();String selection = COLUMN_NOME + " = ? AND " + COLUMN_DATA_NASCIMENTO + " = ?";
        String[] selectionArgs = {nome, dataNascimento};
        return db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    // Method to get QR code data by email
    public String getQrCodeByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_QR_CODE};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        String qrCodeData = null;
        if (cursor != null && cursor.moveToFirst()) {
            qrCodeData = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QR_CODE));
            cursor.close();
        }

        return qrCodeData;
    }

    public Cursor getRegistroByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        return db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);}

    public Cursor getAllScannedPeople() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(
                TABLE_NAME,
                null, // Select all columns
                null, // No WHERE clause
                null, // No selection arguments
                null, // No GROUP BY
                null, // No HAVING
                null // NoORDER BY
        );
    }

    public void updateQrCode(long id, String qrCodeData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QR_CODE, qrCodeData);
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}