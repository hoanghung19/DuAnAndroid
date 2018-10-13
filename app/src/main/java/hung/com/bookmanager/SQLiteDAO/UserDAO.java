package hung.com.bookmanager.SQLiteDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hung.com.bookmanager.Constant;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.User;

public class UserDAO implements Constant {

    private final DatabaseHelper databaseHelper;

    public UserDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public User getUserByUsername(String username) {

        User user = null;

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_USER,
                new String[]{COLUMN_USER, COLUMN_PASSWORD, COLUMN_PHONE, COLUMN_HOTEN},
                COLUMN_USER + "=?",
                new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            String user_name = cursor.getString(cursor.getColumnIndex(COLUMN_USER));

            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_HOTEN));

            user = new User();
            user.setUserName(user_name);
            user.setPassword(password);
            user.setHoTen(name);
            user.setPhone(phone);

        }

        return user;
    }

    public void insertUser(User nguoiDung) {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, nguoiDung.getUserName());
        values.put(COLUMN_PASSWORD, nguoiDung.getPassword());
        values.put(COLUMN_PHONE, nguoiDung.getPhone());
        values.put(COLUMN_HOTEN, nguoiDung.getHoTen());

        //Tạo câu lệnh INSERT

        long id = database.insert(TABLE_USER, null, values);
        Log.e("insertUser", "insertUser" + id);
        Log.e("CREATE_TABLE_USER", CREATE_TABLE_USER);
        database.close();


    }


    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();

        String SELECT_ALL_USER = "SELECT * FROM " + TABLE_USER;

        Log.e("getAllUser", SELECT_ALL_USER);

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery(SELECT_ALL_USER, null);

        cursor.moveToFirst();
        do {

            String user_name = cursor.getString(cursor.getColumnIndex(COLUMN_USER));
            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            String hoten = cursor.getString(cursor.getColumnIndex(COLUMN_HOTEN));
            User user = new User();
            user.setUserName(user_name);
            user.setPassword(password);
            user.setPhone(phone);
            user.setHoTen(hoten);

            userList.add(user);

        } while (cursor.moveToNext());
        cursor.close();
        database.close();

        return userList;
    }

    public void deleteUser(String username) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        database.delete(TABLE_USER, COLUMN_USER + "=?", new String[]{username});
    }

    public void updateUser(User user) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_HOTEN, user.getHoTen());

        database.update(TABLE_USER, values, COLUMN_USER + "=?", new String[]{user.getUserName()});
    }
}
