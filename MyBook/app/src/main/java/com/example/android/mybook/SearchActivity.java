package com.example.android.mybook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button search_button = (Button) findViewById(R.id.SearchButton);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView search_key = (TextView) findViewById(R.id.SearchText);
                String search =  search_key.getText().toString();
                Intent intent = new Intent(SearchActivity.this, BookList.class);
                intent.putExtra("SearchKey", search);
                startActivity(intent);
            }
        });
    }
}
