package ir.alidev.hesabdar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class CreateActivity extends AppCompatActivity {

    User user;
    MaterialButton create;
    TextInputEditText name, balance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);

        setup();

        create.setOnClickListener(v -> {
            if (name.getText().toString().length() > 0 && balance.getText().toString().length() > 0) {
                if (!user.hasAccount(name.getText().toString())) {
                    user.getAccounts().add(new Account(name.getText().toString(), Integer.parseInt(balance.getText().toString())));
                    Toast.makeText(this, "حساب با موفقیت ساخته شد", Toast.LENGTH_SHORT).show();
                    save();
                    name.setText("");
                    balance.setText("");
                }else {
                    Toast.makeText(this, "حساب با این نام قبلا ثبت شده است", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "لطفا اطلاعات را کامل وارد کنید", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setup() {
        create = findViewById(R.id.create_acc);
        name = findViewById(R.id.name);
        balance = findViewById(R.id.balance);
        load();
    }

    private void load() {
        SharedPreferences shared = getSharedPreferences("hesabdar", MODE_PRIVATE);
        if (shared.contains("user")) {
            String coinjson = shared.getString("user", "");
            user = new Gson().fromJson(coinjson, User.class);
        }else{
            user = new User();
        }
    }

    private void save() {
        SharedPreferences  mPrefs = getSharedPreferences("hesabdar", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        String coinjson = new Gson().toJson(user);
        editor.putString("user", coinjson);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(i);
    }
}
