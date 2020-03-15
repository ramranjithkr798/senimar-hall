package com.example.ramra.seminarhall;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class Type_Hall  extends OptionMenuControl {

CardView next_mini,next_seminar,next_audi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type__hall);

        next_audi=findViewById(R.id.Auditorium);
        next_seminar=findViewById(R.id.Seminar);
        next_mini=findViewById(R.id.Mini_hall);
        next_audi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(OptionMenuControl.user){
                    Intent intent = new Intent(Type_Hall.this, Status_of_booking.class);
                    intent.putExtra("user",OptionMenuControl.userName);
                    startActivity(intent);
                    //Snackbar.make(v,"UNDER CONSTUCTION...",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                }
                else
                {
                    Snackbar.make(v,"LOGIN THE ACCOUNT...",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                }
            }
        });
        next_seminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Type_Hall.this,list_audtorium.class);
                intent.putExtra("name","SEMINAR HALL");
                startActivity(intent);
            }
        });
        next_mini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Type_Hall.this,list_audtorium.class);
                intent.putExtra("name","MINI-SEMINAR HALL");
                startActivity(intent);
            }
        });
    }
}
