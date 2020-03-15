package com.example.ramra.seminarhall;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class detials_of_auditorium  extends OptionMenuControl {

    ImageView Hall_image;
    TextView Hall_name_t,Hall_type,Hall_capicity,Hall_location,Hall_projector,Hall_inCharge,Hall_inCharge_no,Hall_gestRoom,Hall_Marker_bord
            ,Hall_assigent_name,Hall_assigent_no,micHand,micColler,micWire,aircool;
    Button ok;
    FirebaseFirestore firebaseFirestore;
    Bundle get_Data;
    String hall_type,hall_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detials_auditorium);
        Hall_image=findViewById(R.id.Hall_img);
        Hall_name_t=findViewById(R.id.Hall_name);
        Hall_type=findViewById(R.id.Hall_type);
        Hall_capicity=findViewById(R.id.Hall_cap);
        Hall_location=findViewById(R.id.Hall_loc);
        Hall_projector=findViewById(R.id.Hall_pro);
        Hall_inCharge=findViewById(R.id.Hall_incharge);
        Hall_inCharge_no=findViewById(R.id.Hall_incharge_no);
        micColler=findViewById(R.id.Mic_coller);
        micHand=findViewById(R.id.Mic_hand);
        micWire=findViewById(R.id.Mic_wire);
        aircool=findViewById(R.id.ac);
        Hall_gestRoom=findViewById(R.id.Hall_guest_room);
        Hall_Marker_bord=findViewById(R.id.Hall_Marker_board);
        Hall_assigent_name=findViewById(R.id.Hall_non_teaching_name);
        Hall_assigent_no=findViewById(R.id.Hall_non_teaching_no);
        ok=findViewById(R.id.ok);
        firebaseFirestore= FirebaseFirestore.getInstance();
        get_Data=getIntent().getExtras();
        hall_type=get_Data.getString("hall_type");
        hall_name =get_Data.getString("hall_name");
       firebaseFirestore.collection(hall_type).document(hall_name).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
           DocumentSnapshot documentSnapshot=task.getResult();
           String uri=documentSnapshot.getString("img_url");
           Picasso.with(getApplicationContext()).load(uri).into(Hall_image);
           Hall_name_t.setText(documentSnapshot.getString("hall_name"));
           Hall_capicity.setText(documentSnapshot.getString("capacity"));
           Hall_type.setText(documentSnapshot.getString("hall_type"));
           Hall_location.setText(documentSnapshot.getString("location"));
           Hall_projector.setText(documentSnapshot.getString("projector"));
           Hall_inCharge.setText(documentSnapshot.getString("incharge"));
           Hall_inCharge_no.setText(documentSnapshot.getString("in_charge_no"));
           Hall_gestRoom.setText(documentSnapshot.getString("guest_room"));
           Hall_Marker_bord.setText(documentSnapshot.getString("marker_board"));
           Hall_assigent_name.setText(documentSnapshot.getString("non_teaching"));
           Hall_assigent_no.setText(documentSnapshot.getString("non_teaching_no"));
           micColler.setText(documentSnapshot.getString("coller_mic"));
           micHand.setText(documentSnapshot.getString("hand_mic"));
           micWire.setText(documentSnapshot.getString("wire_mic"));
           aircool.setText(documentSnapshot.getString("ac"));
            }
        });

ok.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       if(OptionMenuControl.user)
        {
            Intent intent = new Intent(detials_of_auditorium.this, Request_to_Book.class);
            intent.putExtra("HallName", Hall_name_t.getText().toString());
            intent.putExtra("HallType", hall_type);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(detials_of_auditorium.this, "LOGIN YOUR ACCOUNT......", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(detials_of_auditorium.this,userLogin.class);
            startActivity(intent);
        }
    }
});



    }
}
