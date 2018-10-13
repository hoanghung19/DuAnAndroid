package hung.com.bookmanager;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hung.com.bookmanager.SQLiteDAO.TypeBookDAO;
import hung.com.bookmanager.adapter.TypeAdapter;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.TypeBook;

public class TypeActivity extends AppCompatActivity {
    private RecyclerView lvListTypeBook;
    private DatabaseHelper databaseHelper;
    private TypeBookDAO typeBookDAO;
    private TypeAdapter typeAdapter;
    private List<TypeBook> typeBooks;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thể loại");

        initView();
        initData();
        addRvType();
    }

    private void addType() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Add Type Book");
        dialog.setContentView(R.layout.dialog_add_type_book);

        final EditText edtMaLoai;
        final EditText edtTenLoai;
        final EditText edtMota;
        final EditText edtVitri;

        Button btnAddType;
        Button btnCancel;

        edtMaLoai = dialog.findViewById(R.id.edtMaLoai);
        edtTenLoai = dialog.findViewById(R.id.edtTenLoai);
        edtMota = dialog.findViewById(R.id.edtMota);
        edtVitri = dialog.findViewById(R.id.edtVitri);
        btnAddType = dialog.findViewById(R.id.btnAddType);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        btnAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id = edtMaLoai.getText().toString().trim();
                    String name = edtTenLoai.getText().toString().trim();
                    String des = edtMota.getText().toString().trim();
                    String pos = edtVitri.getText().toString().trim();

//
                    if (id.equals("")) {
                        edtMaLoai.setError(getString(R.string.data_maLoai_null));
                        return;
                    }
                    if (name.equals("")) {
                        edtTenLoai.setError(getString(R.string.data_tenLoai_null));
                        return;
                    }
                    if (id.length() > 5) {
                        edtMaLoai.setError(getString(R.string.maLoai_dai));
                        return;
                    }

                    TypeBook typeBook = new TypeBook(id, name, des, Integer.valueOf(pos));
                    long result = typeBookDAO.insertTypeBook(typeBook);
                    if (result < 0) {
                        Toast.makeText(TypeActivity.this, "Mã sách đã tồn tại!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        typeBooks.add(0, typeBook);
                        typeAdapter.notifyDataSetChanged();
                        Toast.makeText(TypeActivity.this, "Thêm loại sách thành công!!!", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }catch (Exception ex){
                    Log.e("Error", "" + ex);
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void initData() {
        databaseHelper = new DatabaseHelper(this);
        typeBookDAO = new TypeBookDAO(this);
    }

    private void initView() {
        lvListTypeBook = findViewById(R.id.rvType);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        typeBooks = new ArrayList<>();
        typeBooks.clear();
    }

    private void addRvType() {
        typeBooks = typeBookDAO.getAllTypeBook();
        typeAdapter = new TypeAdapter(this, typeBooks, typeBookDAO);

        lvListTypeBook.setLayoutManager(llm);
        lvListTypeBook.setHasFixedSize(true);
        lvListTypeBook.setAdapter(typeAdapter);
    }

    public void addType(View view) {
        addType();
    }
}
