package ir.alidev.hesabdar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    MaterialButton create, myaccounts, add_trans, my_trans, search, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        create.setOnClickListener(v -> {
            Intent i = new Intent(this, CreateActivity.class);
            this.finish();
            startActivity(i);
        });

        myaccounts.setOnClickListener(v -> {
            Intent i = new Intent(this, ListActivity.class);
            this.finish();
            startActivity(i);
        });

        add_trans.setOnClickListener(v -> {
            Intent i = new Intent(this, AddTransActivity.class);
            this.finish();
            startActivity(i);
        });

        my_trans.setOnClickListener(v -> {
            Intent i = new Intent(this,ListTransActivity.class);
            this.finish();
            startActivity(i);
        });

        search.setOnClickListener(v -> {
            Intent i = new Intent(this,SearchActivity.class);
            this.finish();
            startActivity(i);
        });

        exit.setOnClickListener(v -> {
            this.finish();
        });

    }

    private void setup() {
        create = findViewById(R.id.create);
        myaccounts = findViewById(R.id.my_accounts);
        add_trans = findViewById(R.id.add_trans);
        my_trans = findViewById(R.id.my_trans);
        search = findViewById(R.id.search_trans);
        exit = findViewById(R.id.exit);
    }
}