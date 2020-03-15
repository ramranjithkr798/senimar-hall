package com.example.ramra.seminarhall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class home extends OptionMenuControl {

    CardView list_next,regi_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        list_next =findViewById(R.id.home);
        regi_next=findViewById(R.id.registeration);
        list_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home.this,Type_Hall.class);
                startActivity(intent);
            }
        });
        regi_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home.this,admin_mainPage.class);
                startActivity(intent);
            }
        });
    }
}
