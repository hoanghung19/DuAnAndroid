package hung.com.bookmanager.SQLiteDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hung.com.bookmanager.Constant;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.TypeBook;

public class TypeBookDAO implements Constant {
    private final DatabaseHelper databaseHelper;

    public TypeBookDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long insertTypeBook(TypeBook typeBook) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, typeBook.getMaLoai());
        values.put(COLUMN_NAME, typeBook.getTenLoai());
        values.put(COLUMN_DES, typeBook.getMoTa());
        values.put(COLUMN_POS, typeBook.getViTri());
        long result = database.insert(TABLE_TYPE_BOOK, null, values);
        Log.e("insertTypeBook", "insertTypeBook" + result);
        database.close();
        return result;
    }

    public long updateTypeBook(TypeBook typeBook) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, typeBook.getTenLoai());
        values.put(COLUMN_DES, typeBook.getMoTa());
        values.put(COLUMN_POS, typeBook.getViTri());

        long result = database.update(TABLE_TYPE_BOOK, values, COLUMN_ID + "=?", new String[]{typeBook.getMaLoai()});
        Log.e("updateTypeBook", "" + result);
        database.close();
        return result;
    }

    public long deleteTypeBook(String typeBookID) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        return database.delete(TABLE_TYPE_BOOK, COLUMN_ID + "=?", new String[]{typeBookID});
    }

    public List<TypeBook> getAllTypeBook() {
        List<TypeBook> typeList = new ArrayList<>();

        String SELECT_ALL_TYPE_BOOK = "SELECT * FROM " + TABLE_TYPE_BOOK;

        Log.e("getAllTypeBook", SELECT_ALL_TYPE_BOOK);

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery(SELECT_ALL_TYPE_BOOK, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            do {

                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String des = cursor.getString(cursor.getColumnIndex(COLUMN_DES));
                int pos = cursor.getInt(cursor.getColumnIndex(COLUMN_POS));
                TypeBook typeBook = new TypeBook();
                typeBook.setMaLoai(id);
                typeBook.setTenLoai(name);
                typeBook.setMoTa(des);
                typeBook.setViTri(pos);

                typeList.add(typeBook);

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return typeList;
    }

    public List<String> getListTypeBookName(){
        List<String> typeBookList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=databaseHelper.getWritableDatabase();
        String select="SELECT "+COLUMN_NAME+" FROM "+TABLE_TYPE_BOOK;
        Cursor cursor=sqLiteDatabase.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do {

                //add list
                typeBookList.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return typeBookList;
    }

    public TypeBook getTypeBookByID(String typeID) {

        TypeBook typeBook = null;

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_TYPE_BOOK,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DES, COLUMN_POS},
                COLUMN_ID + "=?",
                new String[]{typeID}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            String idType = cursor.getString(cursor.getColumnIndex(COLUMN_ID));

            String nameType = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String des = cursor.getString(cursor.getColumnIndex(COLUMN_DES));
            int pos = cursor.getInt(cursor.getColumnIndex(COLUMN_POS));

            typeBook = new TypeBook();
            typeBook.setMaLoai(idType);
            typeBook.setTenLoai(nameType);
            typeBook.setMoTa(des);
            typeBook.setViTri(pos);

        }

        return typeBook;
    }
}
