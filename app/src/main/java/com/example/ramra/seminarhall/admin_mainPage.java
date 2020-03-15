package com.example.ramra.seminarhall;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class admin_mainPage  extends OptionMenuControl {

    Spinner Hall_name1,Hall_Type1;
    Button NewHall,UpdateHall,SelectHall,DeleteHall,StatusHall;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
   StorageReference storageReference;
    ArrayList hallname=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        Hall_name1=findViewById(R.id.Hall_name_ad);
        Hall_Type1=findViewById(R.id.Hall_type_ad);
        NewHall=findViewById(R.id.NewHall);
        UpdateHall=findViewById(R.id.UpdateHall);
        SelectHall=findViewById(R.id.hallDet);
        DeleteHall=findViewById(R.id.deleteHall);
        StatusHall=findViewById(R.id.StatusButton);
        String halls[]={"SELECT THE HALL TYPE","MINI-SEMINAR HALL","SEMINAR HALL","AUDITORIUM"};
        ArrayAdapter Hall_Int=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,halls);
        Hall_Type1.setAdapter(Hall_Int);
        hallname.add("SELECT THE HALL NAME");
        Hall_Type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {}
                else{
                    firebaseFirestore.collection(Hall_Type1.getSelectedItem().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override

                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            hallname.clear();
                            hallname.add("SELECT THE HALL NAME");
                            for(DocumentSnapshot documentSnapshot:task.getResult()){
                                hallname.add(documentSnapshot.getId().toString());
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter Hall_name=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,hallname);
        Hall_name1.setAdapter(Hall_name);
        Hall_name1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        NewHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admin_mainPage.this,registeration.class);
                intent.putExtra("update", "");
                startActivity(intent);
            }
        });


        UpdateHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Hall_Type1.getSelectedItem().toString()=="SELECT THE HALL TYPE"))
                {
                    Toast.makeText(getApplicationContext(),"SELECT PROPERLY",Toast.LENGTH_SHORT).show();
                }else {
                    if ((Hall_name1.getSelectedItem().toString() == "SELECT THE HALL NAME"))
                        Toast.makeText(getApplicationContext(), "SELECT PROPERLY", Toast.LENGTH_SHORT).show();
                    else {
                        Intent intent = new Intent(admin_mainPage.this, registeration.class);
                        intent.putExtra("update", "update");
                        intent.putExtra("hall_type", Hall_Type1.getSelectedItem().toString());
                        intent.putExtra("hall_name", Hall_name1.getSelectedItem().toString());
                        startActivity(intent);
                    }
                }
            }
        });


        SelectHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Hall_Type1.getSelectedItem().toString()=="SELECT THE HALL TYPE"))
                {
                    Toast.makeText(getApplicationContext(),"SELECT PROPERLY",Toast.LENGTH_SHORT).show();
                }else {
                    if ((Hall_name1.getSelectedItem().toString() == "SELECT THE HALL NAME"))
                        Toast.makeText(getApplicationContext(), "SELECT PROPERLY", Toast.LENGTH_SHORT).show();
                    else {
                        Intent intent = new Intent(admin_mainPage.this, detials_of_auditorium.class);
                        intent.putExtra("hall_type", Hall_Type1.getSelectedItem().toString());
                        intent.putExtra("hall_name", Hall_name1.getSelectedItem().toString());
                        startActivity(intent);
                    }
                }
            }
        });
        DeleteHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(admin_mainPage.this);
                builder.setTitle("HALL DELETION")
                        .setMessage("ARE YOU SURE WANT TO DELETE"+" "+Hall_name1.getSelectedItem().toString()+" "+Hall_Type1.getSelectedItem().toString())
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if((Hall_Type1.getSelectedItem().toString()=="SELECT THE HALL TYPE"))
                                {
                                    Toast.makeText(getApplicationContext(),"SELECT PROPERLY",Toast.LENGTH_SHORT).show();
                                }else {
                                    if ((Hall_name1.getSelectedItem().toString() == "SELECT THE HALL NAME"))
                                        Toast.makeText(getApplicationContext(), "SELECT PROPERLY", Toast.LENGTH_SHORT).show();
                                    else {
                                        firebaseFirestore.collection(Hall_Type1.getSelectedItem().toString()).document(Hall_name1.getSelectedItem().toString())
                                                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                storageReference.child(Hall_Type1.getSelectedItem().toString()+"/"+Hall_name1.getSelectedItem().toString()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(admin_mainPage.this, "DELECTION SUCCESSFULL"+" "+Hall_name1.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                                        Hall_name1.setSelection(0);
                                                        Hall_Type1.setSelection(0);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(admin_mainPage.this, "DELECTION FAILED"+" "+Hall_name1.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(admin_mainPage.this, "DELECTION FAILED"+" "+Hall_name1.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }
                        }).setNegativeButton("cancle",null).setCancelable(false);
                AlertDialog alert=builder.create();
                alert.show();
            }
        });

        StatusHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Hall_Type1.getSelectedItem().toString()=="SELECT THE HALL TYPE"))
                {
                    Toast.makeText(getApplicationContext(),"SELECT PROPERLY",Toast.LENGTH_SHORT).show();
                }else {
                    if ((Hall_name1.getSelectedItem().toString() == "SELECT THE HALL NAME"))
                        Toast.makeText(getApplicationContext(), "SELECT PROPERLY", Toast.LENGTH_SHORT).show();
                    else {
                        Intent intent = new Intent(admin_mainPage.this, Status_of_booking.class);
                        intent.putExtra("user","admin");
                        intent.putExtra("hall_type", Hall_Type1.getSelectedItem().toString());
                        intent.putExtra("hall_name", Hall_name1.getSelectedItem().toString());
                        startActivity(intent);
                    }
                }
            }
        });
    }
}