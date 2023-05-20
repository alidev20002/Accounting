package ir.alidev.hesabdar;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;

public class ListTransActivity extends AppCompatActivity {

    User user;
    ListView list_trans;
    ArrayList<String> acc_accounts;
    ArrayList<Transaction> transactions;
    ArrayAdapter<String> adapter_acc;
    TransAdapter adapter_trans;
    Spinner acc;
    MaterialButton show;
    String acc_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtrans_layout);

        setup();

        show.setOnClickListener(v -> {
            if (acc_accounts.size() > 0) {
                acc_name = acc.getSelectedItem().toString();
                Account account = user.getAccount(acc_name);
                transactions.clear();
                transactions.addAll(account.getTransactions());
                Collections.reverse(transactions);
                adapter_trans.notifyDataSetChanged();
            }else {
                Toast.makeText(this, "هیچ حسابی پیدا نشد", Toast.LENGTH_SHORT).show();
            }
        });

        list_trans.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("حذف آیتم")
                    .setMessage("آیا میخواهید این آیتم را حذف کنید؟")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        Transaction t = transactions.get(position);
                        Account account = user.getAccount(acc_name);
                        if (t.getType().equals("هزینه")) {
                            account.setBalance(account.getBalance() + t.getAmount());
                        }else {
                            account.setBalance(account.getBalance() - t.getAmount());
                        }
                        account.getTransactions().remove(account.getTransactions().size() - position - 1);
                        transactions.remove(position);
                        adapter_trans.notifyDataSetChanged();
                        save();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return false;
        });
    }

    private void setup() {
        list_trans = findViewById(R.id.list_trans);
        acc = findViewById(R.id.choose_acc_trans);
        show = findViewById(R.id.trans_show);
        transactions = new ArrayList<>();
        acc_accounts = new ArrayList<>();
        adapter_trans = new TransAdapter(transactions, this);
        list_trans.setAdapter(adapter_trans);
        adapter_acc = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, acc_accounts);
        acc.setAdapter(adapter_acc);
        load();
    }

    private void load() {
        SharedPreferences shared = getSharedPreferences("hesabdar", MODE_PRIVATE);
        if (shared.contains("user")) {
            String coinjson = shared.getString("user", "");
            user = new Gson().fromJson(coinjson, User.class);
            if (user.getAccounts().size() > 0) {
                for (Account a: user.getAccounts()) {
                    acc_accounts.add(a.getName());
                }
                adapter_acc.notifyDataSetChanged();
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