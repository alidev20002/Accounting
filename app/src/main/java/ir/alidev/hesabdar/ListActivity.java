package ir.alidev.hesabdar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class ListActivity extends AppCompatActivity {

    User user;
    ListView list_acc;
    AccountAdapter adapter;
    ArrayList<Account> accounts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        setup();
    }

    private void setup() {
        list_acc = findViewById(R.id.list_acc);
        accounts = new ArrayList<>();
        load();
    }

    private void load() {
        SharedPreferences shared = getSharedPreferences("hesabdar", MODE_PRIVATE);
        if (shared.contains("user")) {
            String coinjson = shared.getString("user", "");
            user = new Gson().fromJson(coinjson, User.class);
            if (user.getAccounts().size() > 0) {
                accounts = user.getAccounts();
                adapter = new AccountAdapter(accounts, this);
                list_acc.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
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
