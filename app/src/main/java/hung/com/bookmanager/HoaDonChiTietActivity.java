package hung.com.bookmanager;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import hung.com.bookmanager.SQLiteDAO.HoaDonChiTietDAO;
import hung.com.bookmanager.adapter.BillDetailAdapter;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.Book;
import hung.com.bookmanager.model.HoaDonChiTiet;

import static hung.com.bookmanager.Constant.CL_ID;
import static hung.com.bookmanager.Constant.CL_MA_HOA_DON;
import static hung.com.bookmanager.Constant.CL_MA_SACH;
import static hung.com.bookmanager.Constant.CL_SO_LUONG;
import static hung.com.bookmanager.Constant.COLUMN_GIABIA;
import static hung.com.bookmanager.Constant.COLUMN_MASACH;
import static hung.com.bookmanager.Constant.COLUMN_SOLUONG;

public class HoaDonChiTietActivity extends AppCompatActivity {
    private EditText edtIDBill;
    private Spinner spMaSach;
    private EditText txtSoLuong;
    private Button btnAdd;
    private Button btnCancel;
    private TextView txtThanhTien;
    private RecyclerView recyclerviewBilldetail;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseHelper helper;
    private BillDetailAdapter billdetailAdapter;
    private ArrayList<HoaDonChiTiet> billdetailsArrayList, getBilldetailsArrayList1;
    private ArrayList<Book> bookArrayList;
    private HoaDonChiTietDAO nBilldetailsDAO;
    private int i;
    private int vitri;
    private String mahoadon2;
    private String mhoadon1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hóa đơn chi tiết");

        anhXa();
        getdata();
        getDataBilldetails1();

        getIDBook();
        addRecyclerview();

    }

    private void anhXa() {
        helper = new DatabaseHelper(this);
        nBilldetailsDAO = new HoaDonChiTietDAO(helper);
        edtIDBill = (EditText) findViewById(R.id.edtmaBillDetail);
        spMaSach = (Spinner) findViewById(R.id.spnMaSach);
        txtSoLuong = (EditText) findViewById(R.id.edtsoLuongmua);
        btnAdd = (Button) findViewById(R.id.btnSaveBillDetail);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        txtThanhTien = (TextView) findViewById(R.id.txtThanhTien);
        recyclerviewBilldetail = (RecyclerView) findViewById(R.id.rvBillDetail);
        billdetailsArrayList = new ArrayList<>();
        bookArrayList = new ArrayList<>();
        getBilldetailsArrayList1 = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void getDataBilldetails1() {
        billdetailAdapter = new BillDetailAdapter(getBilldetailsArrayList1, bookArrayList, this, i);
        Cursor cursor = helper.getData("SELECT * FROM HoaDonChiTiet");
        billdetailsArrayList.clear();
        HoaDonChiTiet billdetails;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maHDCT = cursor.getInt(cursor.getColumnIndex(CL_ID));
                String mahoadon = cursor.getString(cursor.getColumnIndex(CL_MA_HOA_DON));
                String masach = cursor.getString(cursor.getColumnIndex(CL_MA_SACH));
                int soluongmua = cursor.getInt(cursor.getColumnIndex(CL_SO_LUONG));
                billdetails = new HoaDonChiTiet();
                billdetails.setMaHDCT(maHDCT);
                billdetails.setMaHD(mahoadon);
                billdetails.setMaSach(masach);
                billdetails.setSoLuongMua(soluongmua);
                billdetailsArrayList.add(billdetails);

            } while (cursor.moveToNext());
            for (int i = 0; i < billdetailsArrayList.size(); i++) {
                if (billdetailsArrayList.get(i).getMaHD().equals(mhoadon1)) {
                    HoaDonChiTiet billd = billdetailsArrayList.get(i);
                    HoaDonChiTiet billdetails1 = new HoaDonChiTiet();
                    Log.e("pos", "" + i);
                    billdetails1.setMaHDCT(billd.getMaHDCT());
                    billdetails1.setMaHD(billd.getMaHD());
                    billdetails1.setMaSach(billd.getMaSach());
                    billdetails1.setSoLuongMua(billd.getSoLuongMua());
                    getBilldetailsArrayList1.clear();
                    getBilldetailsArrayList1.add(billdetails1);
                }
            }

            txtThanhTien.setVisibility(View.VISIBLE);
            billdetailAdapter.notifyDataSetChanged();

        }
    }


    private void getdata() {
        Intent intent = getIntent();
        mahoadon2 = intent.getStringExtra("mahoadon");
        mhoadon1 = intent.getStringExtra("mahoadon1");

        if (mahoadon2 == null) {
            vitri = intent.getIntExtra("vitri", 0);
            edtIDBill.setEnabled(false);
            edtIDBill.setText(mhoadon1);
            getDataBilldetails1();
        } else {
            edtIDBill.setEnabled(false);
            edtIDBill.setText(mahoadon2);
            getDataBilldetails1();
        }


    }

    private void getIDBook() {
        final ArrayList<String> strings = new ArrayList<>();
        final ArrayList<Float> floats = new ArrayList<>();
        final ArrayList<Integer> ints = new ArrayList<>();
        Cursor cursor = helper.getData("SELECT * FROM Sach");
        if (cursor != null && cursor.moveToNext()) {
            do {
                String masach = cursor.getString(cursor.getColumnIndex(COLUMN_MASACH));
                float giabia = cursor.getFloat(cursor.getColumnIndex(COLUMN_GIABIA));
                int soluong = cursor.getInt(cursor.getColumnIndex(COLUMN_SOLUONG));
                Book book = new Book();
                book.setMaSach(masach);
                book.setGiaBia(giabia);
                bookArrayList.add(book);
                strings.add(masach);
                floats.add(giabia);
                ints.add(soluong);
            } while (cursor.moveToNext());
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaSach.setAdapter(adapter);

        spMaSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (txtSoLuong.getText().toString().trim().equals("")) {
                            txtSoLuong.setError(getString(R.string.data_null));
                            return;
                        }
                        if (ints.get(position) < Integer.parseInt(txtSoLuong.getText().toString().trim())) {
                            txtSoLuong.setError("Error so luong!!!");
                            return;
                        }

                        HoaDonChiTiet billdetails1 = new HoaDonChiTiet();
                        billdetails1.setMaSach(strings.get(position));
                        billdetails1.setMaHD(edtIDBill.getText().toString());
                        billdetails1.setSoLuongMua(Integer.parseInt(txtSoLuong.getText().toString().trim()));
                        nBilldetailsDAO.insertHoaDonCT(billdetails1);
                        txtThanhTien.setVisibility(View.VISIBLE);
                        getBilldetailsArrayList1.add(0, billdetails1);
                        addRecyclerview();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void addRecyclerview() {
        recyclerviewBilldetail.setLayoutManager(linearLayoutManager);
        recyclerviewBilldetail.setHasFixedSize(true);
        recyclerviewBilldetail.setAdapter(billdetailAdapter);
    }

    public void delete(int i, int po) {
        nBilldetailsDAO.deleteHoaDonCT(String.valueOf(i));
        getBilldetailsArrayList1.remove(po);
        addRecyclerview();

    }

    public void Huy(View view) {
        txtSoLuong.setText("");
    }
}