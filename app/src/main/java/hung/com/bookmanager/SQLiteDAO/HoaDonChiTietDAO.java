package hung.com.bookmanager.SQLiteDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hung.com.bookmanager.Constant;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.HoaDonChiTiet;

public class HoaDonChiTietDAO implements Constant {
    private final DatabaseHelper databaseHelper;

    public HoaDonChiTietDAO(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    public void insertHoaDonCT(HoaDonChiTiet hoaDonChiTiet){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CL_MA_HOA_DON, hoaDonChiTiet.getMaHD());
        values.put(CL_MA_SACH, hoaDonChiTiet.getMaSach());
        values.put(CL_SO_LUONG, hoaDonChiTiet.getSoLuongMua());

        long result = database.insert(TABLE_HOA_DON_CHI_TIET, null, values);
        database.close();
    }

    public void deleteHoaDonCT(String id_hdct){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        long result = database.delete(TABLE_HOA_DON_CHI_TIET, CL_ID + "=?", new String[]{id_hdct});
        Log.e("hello", "" + result);
    }

    public List<HoaDonChiTiet> getAllHoaDonChiTiet(){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<HoaDonChiTiet> hoaDonChiTiets = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_HOA_DON_CHI_TIET, null);
        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    int id_hdct = cursor.getInt(cursor.getColumnIndex(CL_ID));
                    String maHD = cursor.getString(cursor.getColumnIndex(CL_MA_HOA_DON));
                    String maSach = cursor.getString(cursor.getColumnIndex(CL_MA_SACH));
                    int soLuongMua = cursor.getInt(cursor.getColumnIndex(CL_SO_LUONG));

                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(id_hdct, maHD, maSach, soLuongMua);
                    hoaDonChiTiets.add(hoaDonChiTiet);
                }while (cursor.moveToNext());
            }
            database.close();
        }
        return hoaDonChiTiets;
    }

    public HoaDonChiTiet getHoaDonChiTietByID(String billID){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        List<HoaDonChiTiet> billDetails = new ArrayList<>();

        String SELECT_ALL_BILL_DETAIL_BY_BILL_ID = "SELECT * FROM " + TABLE_HOA_DON_CHI_TIET +
                " WHERE " + CL_ID + " = " + "'" + billID + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_BILL_DETAIL_BY_BILL_ID, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(CL_ID));
                String book_id = cursor.getString(cursor.getColumnIndex(CL_MA_SACH));
                String bill_id = cursor.getString(cursor.getColumnIndex(CL_MA_HOA_DON));
                int quality = cursor.getInt(cursor.getColumnIndex(CL_SO_LUONG));

                HoaDonChiTiet billDetail = new HoaDonChiTiet();
                billDetail.maHDCT = id;
                billDetail.maHD = bill_id;
                billDetail.maSach = book_id;
                billDetail.soLuongMua = quality;
                billDetails.add(billDetail);

            } while (cursor.moveToNext());

        }
        sqLiteDatabase.close();
        return (HoaDonChiTiet) billDetails;
    }
}
