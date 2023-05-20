package ir.alidev.hesabdar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    User user;
    Spinner type, account;
    TextInputEditText detail;
    Button search;
    ListView list;
    ArrayList<String> types, accounts;
    ArrayList<Transaction> results;
    ArrayAdapter<String> type_adapter, acc_adapter;
    TransAdapter result_adapter;
    TextView sum_tv;
    DecimalFormat format;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setup();

        search.setOnClickListener(v -> {
            if (accounts.size() > 0) {
                String acc_name = account.getSelectedItem().toString();
                String ttype = type.getSelectedItem().toString();
                String dtl = "";
                if (detail.getText().toString().length() > 0)
                    dtl = detail.getText().toString();
                Account a = user.getAccount(acc_name);
                results.clear();
                if (ttype.equals("همه"))
                    results.addAll(a.search(dtl));
                else
                    results.addAll(a.search(dtl, ttype));
                Collections.reverse(results);
                result_adapter.notifyDataSetChanged();
                if (ttype.equals("هزینه") || ttype.equals("درآمد")) {
                    int sum = 0;
                    for (Transaction t: results)
                        sum += t.getAmount();
                    sum_tv.setText("مجموع: " + format.format(sum) + " تومان");
                    sum_tv.setVisibility(View.VISIBLE);
                }else {
                    sum_tv.setVisibility(View.INVISIBLE);
                }
            }else {
                Toast.makeText(this, "هیچ حسابی پیدا نشد", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setup() {
        type = findViewById(R.id.search_trans_type);
        account = findViewById(R.id.search_trans_account);
        detail = findViewById(R.id.search_trans_detail);
        search = findViewById(R.id.search_btn);
        list = findViewById(R.id.search_list_trans);
        sum_tv = findViewById(R.id.search_sum);
        format = new DecimalFormat("###,###,###,###", new DecimalFormatSymbols(Locale.US));
        types = new ArrayList<>();
        types.add("همه");
        types.add("هزینه");
        types.add("درآمد");
        accounts = new ArrayList<>();
        acc_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accounts);
        account.setAdapter(acc_adapter);
        results = new ArrayList<>();
        type_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, types);
        result_adapter = new TransAdapter(results, this);
        type.setAdapter(type_adapter);
        list.setAdapter(result_adapter);
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(i);
    }
}
