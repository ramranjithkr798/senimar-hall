package com.example.ramra.seminarhall;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Status_of_booking extends OptionMenuControl {

    ListView StatusBOOKING;
    Bundle bundle;
    ArrayList Funcdate=new ArrayList();
    ArrayList FuncID=new ArrayList();
    ArrayList FuncName=new ArrayList();
    ArrayList FuncSession=new ArrayList();
    String HallType,HallName,user;
    TextView StatusName,StatusCode,StatusSession,StatusDate;
    Button StatusButtonOK;
    ScrollView Scroll;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_of_booking);
        StatusBOOKING=findViewById(R.id.StatusBookingId);
        StatusName=findViewById(R.id.statusName);
        StatusCode=findViewById(R.id.statusNameCode);
        StatusSession=findViewById(R.id.statusSession);
        StatusDate=findViewById(R.id.statusDate);
        bundle=getIntent().getExtras();
        user=bundle.getString("user");
        StatusButtonOK=findViewById(R.id.StatusButtonOk);
        Scroll=findViewById(R.id.scrollView);
        firebaseFirestore=FirebaseFirestore.getInstance();
        if(user.equals("admin")) {
            HallName=bundle.getString("hall_name");
            HallType=bundle.getString("hall_type");
            ItemNeedToBeDisplayedInTheListView("BOOKING", HallType, HallName);
        }
        else
            ItemNeedToBeDisplayedInTheListView("USERDETIALS",OptionMenuControl.userName,"HALLBOOKED");

    }

    void ItemNeedToBeDisplayedInTheListView(final String Collection1, final String document, final String Collection2)
    {
        firebaseFirestore.collection(Collection1).document(document).collection(Collection2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot documentSnapshot:task.getResult())
                {
                    FuncID.add(documentSnapshot.getId());
                    Funcdate.add(documentSnapshot.getString("DATE"));
                    FuncName.add(documentSnapshot.getString("EVENT NAME"));
                    FuncSession.add(documentSnapshot.getString("SESSION"));
                }

                ArrayAdapter arrayAdapter=new ArrayAdapter(Status_of_booking.this,android.R.layout.simple_spinner_dropdown_item,FuncName);
                StatusBOOKING.setAdapter(arrayAdapter);

                StatusButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Scroll.setVisibility(View.GONE);
                    }
                });
                StatusBOOKING.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected=FuncID.get(position).toString();
                        firebaseFirestore.collection(Collection1).document(document).collection(Collection2).document(selected).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                DocumentSnapshot doc =task.getResult();
                               // Toast.makeText(getApplicationContext(),doc.getData().toString(),Toast.LENGTH_SHORT).show();
                                StatusCode.setText(doc.getString("EVENT COORDINATOR"));
                                StatusDate.setText(doc.getString("DATE"));
                                StatusName.setText(doc.getString("EVENT NAME"));
                                StatusSession.setText(doc.getString("SESSION"));
                                Scroll.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
        });
    }
}
