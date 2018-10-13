package hung.com.bookmanager.SQLiteDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hung.com.bookmanager.Constant;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.Bill;

public class BillDAO implements Constant {
    private final DatabaseHelper databaseHelper;

    public BillDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void insertBill(Bill bill) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(B_ID, bill.id);
        values.put(B_DATE, bill.date);
        long result = database.insert(TABLE_BILL, null, values);
        database.close();
        Log.e("Error", "" + result);
    }

    public long updateBill(Bill bill) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(B_DATE, bill.date);
        long result = database.update(TABLE_BILL, values, B_ID + "=?", new String[]{bill.id});
        database.close();
        return result;
    }

    public void deleteBill(String id) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        long result = database.delete(TABLE_BILL, B_ID + "=?", new String[]{id});
        database.close();
    }

    public List<Bill> getAllBill() {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<Bill> bills = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_BILL, null);

        //kiem tra cursor co du lieu hay khong
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                //thiet lap con tro o vi tri dau tien
                cursor.moveToFirst();
                do {
                    String id = cursor.getString(cursor.getColumnIndex(B_ID));
                    long date = cursor.getLong(cursor.getColumnIndex(B_DATE));

                    Bill bill = new Bill(id, date);

                    //them bill vua lay duoc vao arraylist Bills
                    bills.add(bill);
                } while (cursor.moveToNext());
            }
            database.close();
        }
        return bills;
    }

    public Bill getBillByID(String id) {
        Bill bill = null;
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.query(TABLE_BILL,
                new String[]{B_ID, B_DATE},
                B_ID + "=?",
                new String[]{id}, null, null, null);

        //kiem tra cursor co du lieu hay khong
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                //thiet lap con tro o vi tri dau tien
                cursor.moveToFirst();
                String bill_id = cursor.getString(cursor.getColumnIndex(B_ID));
                long date = cursor.getLong(cursor.getColumnIndex(B_DATE));

                bill = new Bill(bill_id, date);
                return bill;

            }
            database.close();

        }
        return null;

    }
}
