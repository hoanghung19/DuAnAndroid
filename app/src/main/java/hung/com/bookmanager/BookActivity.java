package hung.com.bookmanager;

import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hung.com.bookmanager.SQLiteDAO.BookDAO;
import hung.com.bookmanager.adapter.BookAdapter;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.Book;

public class BookActivity extends AppCompatActivity implements Constant {
    private RecyclerView lvListBook;
    private DatabaseHelper databaseHelper;
    private BookDAO bookDAO;
    private BookAdapter bookAdapter;
    private List<Book> books;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản lý sách");

        initView();
        initData();
        addRvBook();
    }

    public void addBook(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Add Book");
        dialog.setContentView(R.layout.dialog_add_book);

        final EditText edtMaSach;
        Spinner spnType;
        final EditText edtTenSach;
        final EditText edtTacGia;
        final EditText edtNXB;
        final EditText edtGiaBia;
        final EditText edtSoLuong;
        final Button btnAddBook;
        Button btnCancel;

        edtMaSach = dialog.findViewById(R.id.edtMaSach);
        spnType = dialog.findViewById(R.id.spnType);
        edtTenSach = dialog.findViewById(R.id.edtTenSach);
        edtTacGia = dialog.findViewById(R.id.edtTacGia);
        edtNXB = dialog.findViewById(R.id.edtNXB);
        edtGiaBia = dialog.findViewById(R.id.edtGiaBia);
        edtSoLuong = dialog.findViewById(R.id.edtSoLuong);
        btnAddBook = dialog.findViewById(R.id.btnAddBook);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        Cursor cursor = bookDAO.getData("SELECT * FROM TypeBook");

        final ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.clear();
        if (cursor.moveToFirst() && cursor != null) {

            do {
                String matheloai = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                stringArrayList.add(matheloai);

            } while (cursor.moveToNext());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stringArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(arrayAdapter);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                                                  btnAddBook.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          String maSach = edtMaSach.getText().toString().trim();

                                                          if (maSach.equals("")) {
                                                              edtMaSach.setError(getString(R.string.data_null));
                                                              return;
                                                          }
                                                          if (maSach.length() > 5) {
                                                              edtMaSach.setError(getString(R.string.maSach_ngan));
                                                              return;
                                                          }

                                                          String tenSach = edtTenSach.getText().toString().trim();

                                                          if (tenSach.equals("")) {
                                                              edtTenSach.setError(getString(R.string.data_null));
                                                              return;
                                                          }

                                                          String tacGia = edtTacGia.getText().toString().trim();

                                                          if (tacGia.equals("")) {
                                                              edtTacGia.setError(getString(R.string.data_null));
                                                              return;
                                                          }

                                                          String nxb = edtNXB.getText().toString().trim();

                                                          if (nxb.equals("")) {
                                                              edtNXB.setError(getString(R.string.data_null));
                                                              return;
                                                          }

                                                          Float giaBia;
                                                          if (edtGiaBia.getText().toString().trim().equals("")) {
                                                              edtGiaBia.setError(getString(R.string.data_null));
                                                              return;
                                                          } else {
                                                              giaBia = Float.parseFloat(edtGiaBia.getText().toString().trim());
                                                          }

                                                          String a = edtSoLuong.getText().toString().trim();
                                                          final int soluong = !a.equals("") ? Integer.parseInt(a) : 0;


                                                          Book book = new Book(maSach, stringArrayList.get(position), tenSach, tacGia, nxb, giaBia, soluong);
                                                          long result = bookDAO.insertBook(book);
                                                          if (result < 0) {
                                                              Toast.makeText(BookActivity.this, "Mã sách đã tồn tại!!!", Toast.LENGTH_SHORT).show();
                                                          } else {
                                                              books.add(0, book);
                                                              bookAdapter.notifyDataSetChanged();
                                                              Toast.makeText(BookActivity.this, "Thêm loại sách thành công!!!", Toast.LENGTH_SHORT).show();
                                                              dialog.cancel();
                                                          }


                                                      }
                                                  });

                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          }
        );
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
        bookDAO = new BookDAO(databaseHelper);
    }

    private void initView() {
        lvListBook = findViewById(R.id.rvBook);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        books = new ArrayList<>();
        books.clear();
    }

    private void addRvBook() {
        books = bookDAO.getAllBook();
        bookAdapter = new BookAdapter(this, books, bookDAO);

        lvListBook.setLayoutManager(llm);
        lvListBook.setHasFixedSize(true);
        lvListBook.setAdapter(bookAdapter);
    }

}
