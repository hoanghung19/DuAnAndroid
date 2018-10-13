package hung.com.bookmanager;

import android.app.Dialog;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hung.com.bookmanager.SQLiteDAO.UserDAO;
import hung.com.bookmanager.adapter.UserAdapter;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.User;

public class UserActivity extends AppCompatActivity {
    private UserAdapter userAdapter;
    private RecyclerView rvUser;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fabAddUser;
    private List<User> users;
    private Cursor cursor;
    private LinearLayoutManager llm;

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Người dùng");

        anhXa();
        databaseHelper = new DatabaseHelper(this);
        userDAO = new UserDAO(databaseHelper);

        fabAddUser = findViewById(R.id.fabAddUser);
        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        addRvUser();

    }

    private void addUser() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Add User");

        dialog.setContentView(R.layout.dialog_add_user);

        final EditText edtPassWord;
        final EditText edtConfirmPassword;
        final EditText edtName;
        final EditText edtPhone;
        final EditText edtUserName;

        edtUserName = dialog.findViewById(R.id.edtUsername);
        edtPassWord = dialog.findViewById(R.id.edtPassword);
        edtConfirmPassword = dialog.findViewById(R.id.edtPassAgain);
        edtName = dialog.findViewById(R.id.edtHoten);
        edtPhone = dialog.findViewById(R.id.edtphonenumber);


        dialog.findViewById(R.id.btnAddUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //
                    String usernamenew = edtUserName.getText().toString().trim();
                    String passwordnew = edtPassWord.getText().toString().trim();
                    String passagainnew = edtConfirmPassword.getText().toString().trim();
                    String phonenew = edtPhone.getText().toString().trim();
                    String hotennew = edtName.getText().toString().trim();
                    if (usernamenew.equals("") || passwordnew.equals("") || passagainnew.equals("") || phonenew.equals("") || hotennew.equals("")) {
                        edtUserName.setError(getString(R.string.error_UserName));
                        edtPassWord.setError(getString(R.string.error_PassWord));
                        edtConfirmPassword.setError(getString(R.string.error_PassWord));
                        edtPhone.setError(getString(R.string.error_phone));
                        edtName.setError(getString(R.string.error_hoTen));
                        return;
                    } else if (usernamenew.length() < 5 || passwordnew.length() < 6) {
                        edtUserName.setError(getString(R.string.error_UserName_ngan));
                        edtPassWord.setError(getString(R.string.error_password_ngan));
                        return;
                    } else if (!passagainnew.equals(passwordnew)) {
                        edtConfirmPassword.setError(getString(R.string.passagain));
                        return;
                    } else {

                        User user = new User();
                        user.setUserName(edtUserName.getText().toString().trim());
                        user.setHoTen(edtName.getText().toString().trim());
                        user.setPhone(edtPhone.getText().toString().trim());
                        user.setPassword(edtPassWord.getText().toString().trim());

                        userDAO.insertUser(user);

                        // cap nhat len giao dien
                        // add vao vi tri dau tien
                        users.add(0, user);
                        userAdapter.notifyDataSetChanged();


                        Toast.makeText(UserActivity.this,
                                getString(R.string.notify_add_successful), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }catch (Exception e){
                    Log.e("Error", "" + e);
                }

            }
        });
        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addRvUser() {
        users = userDAO.getAllUser();
        userAdapter = new UserAdapter(this, users, userDAO);

        rvUser.setLayoutManager(llm);
        rvUser.setHasFixedSize(true);
        rvUser.setAdapter(userAdapter);
    }

    private void anhXa() {

        rvUser = findViewById(R.id.rvUser);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        users = new ArrayList<>();
        users.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_them:
                addUser();
                break;
            case R.id.action_doi_mat_khau:
                break;
            case R.id.action_dang_xuat:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
