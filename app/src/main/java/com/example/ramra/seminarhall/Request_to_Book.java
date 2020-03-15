package com.example.ramra.seminarhall;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Request_to_Book extends OptionMenuControl {


    Bundle bundle;
    String HallName,HallType;
    RadioGroup Session;
    RadioButton SessionSelected;
    DatePickerDialog.OnDateSetListener onstartDateSetListener,onendDateSetListener;
    FirebaseFirestore firebaseFirestore;
    static int tmp=0;
    EditText NameOfHall,EvenName,EventCodinator;
    TextView STARTdateTEXTVIEW,StartDATE;
    Button bookNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_to__book);
        bundle=getIntent().getExtras();
        HallName=bundle.getString("HallName");
        HallType=bundle.getString("HallType");
        NameOfHall=findViewById(R.id.HallName_Text);
        EvenName=findViewById(R.id.EventName);
        EventCodinator=findViewById(R.id.EventCoordinator);
        STARTdateTEXTVIEW=findViewById(R.id.StartDataTEXTVIEW);
        StartDATE=findViewById(R.id.StartData);
        bookNow=findViewById(R.id.ButtonHallBook);
        Session=findViewById(R.id.sesison);
        firebaseFirestore=FirebaseFirestore.getInstance();
        NameOfHall.setText(HallName);
        StartDATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker("StartDATE");
            }
        });


        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tmp=0;
                int days=1;
                if(days==1)
                {
                    firebaseFirestore.collection("BOOKINGDATE").document(HallName).collection("DATE").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            int Val=Session.getCheckedRadioButtonId();
                            SessionSelected=findViewById(Val);
                           for(DocumentSnapshot documentSnapshot:task.getResult())
                           {
                               if(StartDATE.getText().toString().equals(documentSnapshot.getString("DATE"))
                                       &&(documentSnapshot.getString("SESSION").equals("FULLDAY")
                                       ||documentSnapshot.getString("SESSION").equals(SessionSelected.getText().toString()))
                                       ||(SessionSelected.getText().toString().equals("FULLDAY")
                                       &&(documentSnapshot.getString("SESSION").equals("FORENOON")
                                       ||documentSnapshot.getString("SESSION").equals("AFTERNOON")))
                                       )
                               {
                                       tmp=1;
                                       Toast.makeText(Request_to_Book.this, "THIS DATE IS NOT AVAILABLE", Toast.LENGTH_SHORT).show();
                                       break;
                               }

                           }
                            insertDateToTheDataBase();
                        }
                    });
                }
            }
        });


        onstartDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date=month+"."+dayOfMonth+"."+year;
                StartDATE.setText(date);
            }
        };

    }


    void datePicker(String TextviewId)
    {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        if(TextviewId.equals("StartDATE")) {
            DatePickerDialog dialog = new DatePickerDialog(
                    Request_to_Book.this
                    , android.R.style.Theme_Material_Light_Dialog,
                    onstartDateSetListener, year, month, day);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        else if(TextviewId.equals("endDATE")){
            DatePickerDialog dialog=new DatePickerDialog(
                    Request_to_Book.this
                    ,android.R.style.Theme_Material_Light_Dialog_MinWidth,
                    onendDateSetListener,year,month,day);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    void insertDateToTheDataBase()
    {
        if(tmp==0)
        {
            Map map=new HashMap();
            int Val=Session.getCheckedRadioButtonId();
            SessionSelected=findViewById(Val);
            map.put("EVENT NAME",EvenName.getText().toString());
            map.put("EVENT COORDINATOR",EventCodinator.getText().toString());
            map.put("USER",OptionMenuControl.userName);
            map.put("DATE",StartDATE.getText().toString());
            map.put("SESSION",SessionSelected.getText().toString());
            firebaseFirestore.collection("BOOKINGDATE").document(HallName).collection("DATE").document().set(map).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Request_to_Book.this, "FAILED TRY AGAIN......", Toast.LENGTH_SHORT).show();
                }
            });
            firebaseFirestore.collection("BOOKING").document(HallType).collection(HallName).document().set(map).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    Toast.makeText(Request_to_Book.this, "SUCESSFULL", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Request_to_Book.this, "FAILED TRY AGAIN.......", Toast.LENGTH_SHORT).show();
                }
            });

            firebaseFirestore.collection("USERDETIALS").document(OptionMenuControl.userName).collection("HALLBOOKED").document().set(map).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                }
            });
        }
    }
}
