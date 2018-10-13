package hung.com.bookmanager.SQLiteDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hung.com.bookmanager.Constant;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.Book;

public class BookDAO implements Constant {
    private final DatabaseHelper databaseHelper;

    public BookDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long insertBook(Book book) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MASACH, book.getMaSach());
        values.put(COLUMN_MATHELOAI, book.getMaTheLoai());
        values.put(COLUMN_TIEUDE, book.getTieuDe());
        values.put(COLUMN_TACGIA, book.getTacGia());
        values.put(COLUMN_NXB, book.getnXB());
        values.put(COLUMN_GIABIA, book.getGiaBia());
        values.put(COLUMN_SOLUONG, book.getSoLuong());
        long result = database.insert(TABLE_BOOK, null, values);
        Log.e("insertBook", "insertBook" + result);
        database.close();
        return result;
    }

    public long updateBook(Book book) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MATHELOAI, book.getMaTheLoai());
        values.put(COLUMN_TIEUDE, book.getTieuDe());
        values.put(COLUMN_TACGIA, book.getTacGia());
        values.put(COLUMN_NXB, book.getnXB());
        values.put(COLUMN_GIABIA, book.getGiaBia());
        values.put(COLUMN_SOLUONG, book.getSoLuong());

        long result = database.update(TABLE_BOOK, values, COLUMN_MASACH + "=?", new String[]{book.getMaSach()});
        Log.e("updateBook", "" + result);
        database.close();
        return result;
    }

    public long deleteBook(String bookID) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        return database.delete(TABLE_BOOK, COLUMN_MASACH + "=?", new String[]{bookID});
    }

    public List<Book> getAllBook() {
        List<Book> bookList = new ArrayList<>();

        String SELECT_ALL_BOOK = "SELECT * FROM " + TABLE_BOOK;

        Log.e("getAllBook", SELECT_ALL_BOOK);

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery(SELECT_ALL_BOOK, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            do {

                String id = cursor.getString(cursor.getColumnIndex(COLUMN_MASACH));
                String id_type = cursor.getString(cursor.getColumnIndex(COLUMN_MATHELOAI));
                String tieu_de = cursor.getString(cursor.getColumnIndex(COLUMN_TIEUDE));
                String tac_gia = cursor.getString(cursor.getColumnIndex(COLUMN_TACGIA));
                String nxb = cursor.getString(cursor.getColumnIndex(COLUMN_NXB));
                Float gia_bia = cursor.getFloat(cursor.getColumnIndex(COLUMN_GIABIA));
                int so_luong = cursor.getInt(cursor.getColumnIndex(COLUMN_SOLUONG));
                Book book = new Book();
                book.setMaSach(id);
                book.setMaTheLoai(id_type);
                book.setTieuDe(tieu_de);
                book.setTacGia(tac_gia);
                book.setnXB(nxb);
                book.setGiaBia(gia_bia);
                book.setSoLuong(so_luong);

                bookList.add(book);

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return bookList;
    }

    public Book getBookByID(String bookID){
        Book book = null;

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_BOOK,
                new String[]{COLUMN_MASACH, COLUMN_MATHELOAI, COLUMN_TIEUDE, COLUMN_TACGIA, COLUMN_NXB, COLUMN_GIABIA, COLUMN_SOLUONG},
                COLUMN_MASACH + "=?",
                new String[]{bookID}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            String id = cursor.getString(cursor.getColumnIndex(COLUMN_MASACH));

            String idType = cursor.getString(cursor.getColumnIndex(COLUMN_MATHELOAI));
            String tieude = cursor.getString(cursor.getColumnIndex(COLUMN_TIEUDE));
            String tacgia = cursor.getString(cursor.getColumnIndex(COLUMN_TACGIA));
            String nxb = cursor.getString(cursor.getColumnIndex(COLUMN_NXB));
            Float giabia = cursor.getFloat(cursor.getColumnIndex(COLUMN_GIABIA));
            int soluong = cursor.getInt(cursor.getColumnIndex(COLUMN_SOLUONG));

            book = new Book();
            book.setMaSach(id);
            book.setMaTheLoai(idType);
            book.setTieuDe(tieude);
            book.setTacGia(tacgia);
            book.setnXB(nxb);
            book.setGiaBia(giabia);
            book.setSoLuong(soluong);

        }

        return book;
    }

    public Cursor getData(String sql){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }
}
