package hung.com.bookmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DoiMatKhauActivity extends AppCompatActivity {
    private EditText edtPasscu;
    private EditText edtPass1;
    private EditText edtEditPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        khaibao();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đổi mật khẩu");
    }

    public void Edit(View view) {
        String passcu = edtPasscu.getText().toString().trim();
        if (passcu.equals("")) {
            edtPasscu.setError(getString(R.string.error_PassWord));
            return;
        }

        String pass1 = edtPass1.getText().toString().trim();
        if (pass1.equals("")) {
            edtPass1.setError(getString(R.string.error_PassWord));
            return;
        } else if (pass1.length() < 6) {
            edtPass1.setError(getString(R.string.error_password_ngan));
            return;
        }

        String editpass = edtEditPass.getText().toString().trim();
        if (editpass.equals("")) {
            edtEditPass.setError(getString(R.string.error_PassWord));
            return;
        } else if (!editpass.equals(pass1)) {
            edtEditPass.setError(getString(R.string.passagain));
            return;
        }
        Intent intent = new Intent(DoiMatKhauActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void Huy(View view) {
        edtPasscu.setText(null);
        edtPass1.setText(null);
        edtEditPass.setText(null);
    }

    private void khaibao() {
        edtPasscu = (EditText) findViewById(R.id.edtPasscu);
        edtPass1 = (EditText) findViewById(R.id.edtPass1);
        edtEditPass = (EditText) findViewById(R.id.edtEditPass);
    }
}
