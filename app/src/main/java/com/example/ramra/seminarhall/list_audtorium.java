package com.example.ramra.seminarhall;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class list_audtorium  extends OptionMenuControl implements Adapater_layout.OnClickHall{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Bundle get_Data;
    FirebaseFirestore firebaseFirestore;
    String hall_type;
    ArrayList name_src=new ArrayList();
    ArrayList img_src=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_audtorium);
        recyclerView=findViewById(R.id.content);
        get_Data=getIntent().getExtras();
        firebaseFirestore=FirebaseFirestore.getInstance();
        hall_type=get_Data.getString("name");
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        firebaseFirestore.collection(hall_type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
           for (DocumentSnapshot documentSnapshot:task.getResult()){
              name_src.add(documentSnapshot.getId().toString());
              img_src.add(documentSnapshot.getString("img_url"));
           }
                adapter=new Adapater_layout(name_src,img_src,getApplicationContext(),list_audtorium.this);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void OnClick(int position) {
        Intent intent=new Intent(getApplicationContext(),detials_of_auditorium.class);
        intent.putExtra("hall_type",hall_type);
        intent.putExtra("hall_name",name_src.get(position).toString());
        startActivity(intent);
    }
}