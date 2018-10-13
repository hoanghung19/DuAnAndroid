package hung.com.bookmanager;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import hung.com.bookmanager.SQLiteDAO.UserDAO;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.User;


public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private Button btnLogin;
    private DatabaseHelper databaseHelper;
    private UserDAO userDAO;
    private SharedPreferences sharedPreferences;
    private CheckBox cbkRemember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);
        userDAO = new UserDAO(databaseHelper);

        initView();

        sharedPreferences = getSharedPreferences("SaveUser", MODE_PRIVATE);
        edtUser.setText(sharedPreferences.getString("Username", ""));
        edtPass.setText(sharedPreferences.getString("Password", ""));
        cbkRemember.setChecked(sharedPreferences.getBoolean("check", false));

        //them user
        User nguoiDung = new User("admin", "admin123", "0974815770", "Pham Hung");
        userDAO.insertUser(nguoiDung);

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String username = edtUser.getText().toString().trim();
                    if (username.equals("")) {
                        edtUser.setError(getString(R.string.error_UserName));
                        return;
                    } else if (username.length() < 5) {
                        edtUser.setError(getString(R.string.error_UserName_ngan));
                        return;
                    }
                    String password = edtPass.getText().toString().trim();
                    if (password.equals("")) {
                        edtPass.setError(getString(R.string.error_PassWord));
                        return;
                    } else if (password.length() < 6) {
                        edtPass.setError(getString(R.string.error_password_ngan));
                        return;
                    }

                    User user1 = userDAO.getUserByUsername(username);
                    if (user1 == null) {
                        Toast.makeText(LoginActivity.this, getString(R.string.notify_wrong_username_or_password), Toast.LENGTH_SHORT).show();
                    } else {
                        //lay ra password tu DB cua User
                        String passwordInDB = user1.getPassword();

                        //so sanh 2 mat khau neu giong thi cho vao Home va nguoc lai
                        if (passwordInDB.equals(password)) {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();
                            setCheckbox();
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.notify_wrong_username_or_password), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Log.e("Error", "" + e);
                }

            }
        });
    }
    private void setCheckbox(){
        if(cbkRemember.isChecked()){
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("Username", edtUser.getText().toString().trim());
            editor.putString("Password", edtPass.getText().toString().trim());
            editor.putBoolean("check", true);
            editor.apply();
        }
    }
    private void initView(){
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edpassword);
        cbkRemember = findViewById(R.id.cbkRememberPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }
}