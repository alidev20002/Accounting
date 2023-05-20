package ir.alidev.hesabdar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AddTransActivity extends AppCompatActivity {

    User user;
    MaterialButton add;
    TextInputEditText amount, detail;
    Spinner trans_acc, trans_type;
    ArrayList<String> accounts;
    ArrayAdapter<String> acc_adapter, type_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtrans_layout);

        setup();

        add.setOnClickListener(v -> {
            if (amount.getText().toString().length() > 0 && detail.getText().toString().length() > 0) {
                Account account = user.getAccount(trans_acc.getSelectedItem().toString());
                String type = trans_type.getSelectedItem().toString();
                account.getTransactions().add(new Transaction(type, Integer.parseInt(amount.getText().toString()), detail.getText().toString()));
                if (type.equals("هزینه")) {
                    account.setBalance(account.getBalance() - Integer.parseInt(amount.getText().toString()));
                }else{
                    account.setBalance(account.getBalance() + Integer.parseInt(amount.getText().toString()));
                }
                save();
                amount.setText("");
                detail.setText("");
                Toast.makeText(this, "تراکنش با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "لطفا اطلاعات را کامل وارد کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setup() {
        add = findViewById(R.id.trans_add);
        amount = findViewById(R.id.trans_amount);
        detail = findViewById(R.id.trans_detail);
        trans_acc = findViewById(R.id.trans_acc);
        trans_type = findViewById(R.id.trans_type);
        accounts = new ArrayList<>();
        acc_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accounts);
        trans_acc.setAdapter(acc_adapter);
        ArrayList<String> types = new ArrayList<>();
        types.add("هزینه");
        types.add("درآمد");
        type_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, types);
        trans_type.setAdapter(type_adapter);
        load();
    }

    private void load() {
        SharedPreferences shared = getSharedPreferences("hesabdar", MODE_PRIVATE);
        if (shared.contains("user")) {
            String coinjson = shared.getString("user", "");
            user = new Gson().fromJson(coinjson, User.class);
            if (user.getAccounts().size() > 0) {
                for (Account a: user.getAccounts()) {
                    accounts.add(a.getName());
                }
                acc_adapter.notifyDataSetChanged();
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
