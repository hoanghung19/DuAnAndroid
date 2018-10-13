package hung.com.bookmanager.adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hung.com.bookmanager.R;
import hung.com.bookmanager.SQLiteDAO.BookDAO;
import hung.com.bookmanager.model.Book;

import static hung.com.bookmanager.Constant.COLUMN_NAME;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> implements AdapterView.OnItemSelectedListener {
    private final List<Book> books;
    private final Context context;
    private final BookDAO bookDAO;


    public BookAdapter(Context context, List<Book> books, BookDAO bookDAO) {
        this.context = context;
        this.books = books;
        this.bookDAO = bookDAO;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookHolder holder, int position) {
        final Book book = books.get(holder.getAdapterPosition());
        holder.imgBook.setImageResource(R.drawable.bookicon);
        holder.tvTenSach.setText(books.get(holder.getAdapterPosition()).getTieuDe());
        holder.tvSoLuong.setText(Integer.toString(books.get(holder.getAdapterPosition()).getSoLuong()));

        holder.imgdeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = bookDAO.deleteBook(book.getMaSach());

                if (result < 0) {
                    Toast.makeText(context, context.getString(R.string.delete_not_successful), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.delete_successful), Toast.LENGTH_SHORT).show();

                    books.remove(holder.getAdapterPosition());

                    notifyDataSetChanged();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setTitle(books.get(holder.getAdapterPosition()).getTieuDe());
                dialog.setContentView(R.layout.dialog_edit_book);

                final Spinner spnType;
                final EditText edtTenSach;
                final EditText edtTacGia;
                final EditText edtNXB;
                final EditText edtGiaBia;
                final EditText edtSoLuong;
                final Button btnAddBook;
                Button btnCancel;

                edtTenSach = dialog.findViewById(R.id.edtTenSach);
                edtTacGia = dialog.findViewById(R.id.edtTacGia);
                edtNXB = dialog.findViewById(R.id.edtNXB);
                edtGiaBia = dialog.findViewById(R.id.edtGiaBia);
                edtSoLuong = dialog.findViewById(R.id.edtSoLuong);
                btnAddBook = dialog.findViewById(R.id.btnAddBook);
                btnCancel = dialog.findViewById(R.id.btnCancel);
                spnType = dialog.findViewById(R.id.spnType);

                edtTenSach.setText(book.getTieuDe());
                edtTacGia.setText(book.getTacGia());
                edtNXB.setText(book.getnXB());
                edtGiaBia.setText(String.valueOf(book.getGiaBia()));
                edtSoLuong.setText(Integer.toString(book.getSoLuong()));
                Cursor cursor = bookDAO.getData("SELECT * FROM TypeBook");

                final ArrayList<String> stringArrayList = new ArrayList<>();
                stringArrayList.clear();
                if (cursor.moveToFirst() && cursor != null) {
                    do {
                        String matheloai = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                        stringArrayList.add(matheloai);

                    } while (cursor.moveToNext());
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, stringArrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnType.setAdapter(arrayAdapter);

                spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int p, long id) {
                        btnAddBook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String maSach = book.getMaSach();
                                String tenSach = edtTenSach.getText().toString().trim();
                                String tacGia = edtTacGia.getText().toString().trim();
                                String nxb = edtNXB.getText().toString().trim();
                                float giaBia = Float.parseFloat(edtGiaBia.getText().toString().trim());
                                int soLuong = Integer.parseInt(edtSoLuong.getText().toString().trim());
                                Book book1 = new Book(maSach, stringArrayList.get(p), tenSach, tacGia, nxb, giaBia, soLuong);

                                if (tenSach.equals("")) {
                                    edtTenSach.setError(context.getString(R.string.data_null));
                                    return;
                                }
                                if (tacGia.equals("")) {
                                    edtTacGia.setError(context.getString(R.string.data_null));
                                    return;
                                }
                                if (edtGiaBia.getText().toString().trim().equals("")) {
                                    edtGiaBia.setError(context.getString(R.string.data_null));
                                    return;
                                }
                                long result = bookDAO.updateBook(book1);
                                if (result < 0) {
                                    Toast.makeText(context, context.getString(R.string.update_not_successful), Toast.LENGTH_SHORT).show();
                                } else {
                                    Book bookList = new Book();
                                    bookList.setMaSach(books.get(holder.getAdapterPosition()).getMaSach());
                                    bookList.setTieuDe(edtTenSach.getText().toString().trim());
                                    bookList.setTacGia(edtTacGia.getText().toString().trim());
                                    bookList.setnXB(edtNXB.getText().toString().trim());
                                    bookList.setGiaBia(Float.parseFloat(edtGiaBia.getText().toString().trim()));
                                    bookList.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString().trim()));

                                    books.get(holder.getAdapterPosition()).setTieuDe(edtTenSach.getText().toString().trim());
                                    books.get(holder.getAdapterPosition()).setTacGia(edtTacGia.getText().toString().trim());
                                    books.get(holder.getAdapterPosition()).setnXB(edtNXB.getText().toString().trim());
                                    books.get(holder.getAdapterPosition()).setGiaBia(Float.parseFloat(edtGiaBia.getText().toString().trim()));
                                    books.get(holder.getAdapterPosition()).setSoLuong(Integer.parseInt(edtSoLuong.getText().toString().trim()));

                                    notifyDataSetChanged();

                                    Toast.makeText(context, context.getString(R.string.notify_save_successful), Toast.LENGTH_SHORT).show();
                                    dialog.cancel();

                                }
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

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
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class BookHolder extends RecyclerView.ViewHolder {
        private final CardView cvBook;
        private final TextView tvTenSach;
        private final TextView tvSoLuong;
        private final ImageView imgBook;
        private final ImageView imgdeleteBook;

        BookHolder(View itemView) {
            super(itemView);
            cvBook = itemView.findViewById(R.id.cvBook);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            imgBook = itemView.findViewById(R.id.imgBook);
            imgdeleteBook = itemView.findViewById(R.id.imgdeleteBook);
        }
    }
}
