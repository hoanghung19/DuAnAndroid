package hung.com.bookmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hung.com.bookmanager.SQLiteDAO.BillDAO;
import hung.com.bookmanager.adapter.BillAdapter;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.Bill;

public class BillActivity extends AppCompatActivity {
    private RecyclerView rvBill;
    private LinearLayoutManager llm;
    private BillAdapter billAdapter;
    private List<Bill> bills;
    private BillDAO billDAO;
    private long datePicker = -1;
    private Calendar calendar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hóa đơn");
        billDAO = new BillDAO(new DatabaseHelper(this));

        initView();
        initData();
        addRvBill();
    }

    private void initView() {
        rvBill = findViewById(R.id.rvBill);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        bills = new ArrayList<>();
        billAdapter = new BillAdapter(bills, this);

    }

    private void addRvBill() {
        bills = billDAO.getAllBill();
        billAdapter = new BillAdapter(bills, this);

        rvBill.setLayoutManager(llm);
        rvBill.setHasFixedSize(true);
        rvBill.setAdapter(billAdapter);
    }

    private void initData() {
        databaseHelper = new DatabaseHelper(this);
        billDAO = new BillDAO(databaseHelper);
    }


    private void addBill() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Add Bill");
        dialog.setContentView(R.layout.dialog_add_bill);

        final EditText edtIDbill;
        final Button btndatePicker;
        Button btnSaveBill;

        edtIDbill = dialog.findViewById(R.id.edtIDbill);
        btndatePicker = dialog.findViewById(R.id.btndatePicker);
        btnSaveBill = dialog.findViewById(R.id.btnSaveBill);

        btndatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(btndatePicker);
            }
        });

        btnSaveBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahoadon = edtIDbill.getText().toString().trim();
                if (mahoadon.equals("")) {
                    edtIDbill.setError(getString(R.string.data_null));
                    return;
                }
                if (mahoadon.length() > 7) {
                    edtIDbill.setError(getString(R.string.idBill_dai));
                    return;
                }
                boolean test = false;
                for (int i = 0; i < bills.size(); i++) {
                    Bill bill = bills.get(i);

                    if (mahoadon.equals(bill.getId())) {
                        edtIDbill.setError("Mã hóa đơn đã tồn tại");
                        test = true;
                        break;
                    }

                }

                if (!test) {
                    Bill bill = new Bill(mahoadon, calendar.getTimeInMillis());
                    bills.add(0, bill);
                    billDAO.insertBill(bill);
                    addRvBill();
                    Intent intent = new Intent(BillActivity.this, HoaDonChiTietActivity.class);
                    intent.putExtra("mahoadon", mahoadon);
                    startActivity(intent);
                    dialog.dismiss();
                }


            }
        });

        dialog.show();
    }

    private void showDatePicker(final Button btnPicker) {
        calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                BillActivity.this.datePicker = calendar.getTimeInMillis();
                btnPicker.setText(new Date(calendar.getTimeInMillis()).toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void addBill(View view) {
        addBill();
    }

    public void startBillDetail(String mahoadon, int i) {
        Intent intent = new Intent(BillActivity.this, HoaDonChiTietActivity.class);
        intent.putExtra("mahoadon1", mahoadon);
        intent.putExtra("vitri", i);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
