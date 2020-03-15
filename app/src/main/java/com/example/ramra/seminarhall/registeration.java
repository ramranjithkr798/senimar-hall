package com.example.ramra.seminarhall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class registeration  extends OptionMenuControl {
    static final int pick_img_request=1;
    Button submit,Update;
    ImageView img_up1;
    RadioGroup projector_r,marker_board_r,guest_room_r,ac;
    RadioButton val;
    TextView img_t;
    static int pos;
    EditText hall_name_t,capacity_t,location_t,incharge_t,in_charge_no_t,non_teaching_t,non_teaching_no_t,mick_wire,mick_hand,mick_coller;
    Uri mImageUri;
    Bundle bundle;
    static String Url;
    Spinner type_of_hall_t;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        bundle=getIntent().getExtras();
        hall_name_t=findViewById(R.id.hall_name);
        capacity_t=findViewById(R.id.capacity);
        location_t=findViewById(R.id.location);
        incharge_t=findViewById(R.id.incharge);
        in_charge_no_t=findViewById(R.id.incharge_no);
        mick_wire=findViewById(R.id.wire_mic);
        mick_hand=findViewById(R.id.hand_mic);
        mick_coller=findViewById(R.id.coller_mic);
        ac=findViewById(R.id.AC);
        non_teaching_no_t=findViewById(R.id.non_teaching_no);
        non_teaching_t=findViewById(R.id.non_teaching);
        img_up1=findViewById(R.id.up_img);
        img_t=findViewById(R.id.Upload_img_txt);
        submit=findViewById(R.id.submit);
        Update=findViewById(R.id.UpdateHall);
        type_of_hall_t=findViewById(R.id.type_of_hall);
        String halls[]={"MINI-SEMINAR HALL","SEMINAR HALL","AUDITORIUM"};
        ArrayAdapter Hall_Int=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,halls);
        type_of_hall_t.setAdapter(Hall_Int);
        projector_r=findViewById(R.id.projector);
        marker_board_r=findViewById(R.id.marker_board);
        guest_room_r=findViewById(R.id.guest_room);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("UPLOADING....!");
        img_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        if(bundle.getString("update").equals("update")){
            Update.setVisibility(View.VISIBLE);
            firebaseFirestore.collection(bundle.getString("hall_type")).document(bundle.getString("hall_name")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    hall_name_t.setText(documentSnapshot.getString("hall_name"));
                    capacity_t.setText(documentSnapshot.getString("capacity"));
                    String name=documentSnapshot.getString("hall_type");
                    if(name.equals("MINI-SEMINAR HALL"))
                        pos=0;
                    else if(name.equals("SEMINAR HALL"))
                        pos=1;
                    else if(name.equals("AUDITORIUM"))
                        pos=2;
                    type_of_hall_t.setSelection(pos);
                    location_t.setText(documentSnapshot.getString("location"));
                    mick_hand.setText(documentSnapshot.getString("hand_mic"));
                    mick_wire.setText(documentSnapshot.getString("wire_mic"));
                    mick_coller.setText(documentSnapshot.getString("coller_mic"));
                    String opt=documentSnapshot.getString("projector");
                    if(opt.equals("YES")){
                        val=findViewById(R.id.projector_yes);
                        val.toggle();}
                    else{
                        val=findViewById(R.id.projector_no);
                        val.toggle();
                    }
                    incharge_t.setText(documentSnapshot.getString("incharge"));
                    in_charge_no_t.setText(documentSnapshot.getString("in_charge_no"));

                    opt=documentSnapshot.getString("guest_room");
                    if(opt.equals("YES")){
                        val=findViewById(R.id.guest_room_yes);
                        val.toggle();}
                    else{
                        val=findViewById(R.id.guest_room_no);
                        val.toggle();
                    }
                    opt=documentSnapshot.getString("marker_board");
                    if(opt.equals("YES")){
                        val=findViewById(R.id.marker_board_yes);
                        val.toggle();}
                    else{
                        val=findViewById(R.id.marker_board_no);
                        val.toggle();
                    }
                    opt=documentSnapshot.getString("ac");
                    if(opt.equals("YES")){
                        val=findViewById(R.id.ac_yes);
                        val.toggle();}
                    else if(opt.equals("NO")){
                        val=findViewById(R.id.ac_no);
                        val.toggle();
                    }
                    non_teaching_t.setText(documentSnapshot.getString("non_teaching"));
                    non_teaching_no_t.setText(documentSnapshot.getString("non_teaching_no"));
                    Url=documentSnapshot.getString("img_url");
                    Picasso.with(getApplicationContext()).load(Url).into(img_up1);
                    img_up1.setVisibility(View.VISIBLE);
                    img_t.setVisibility(View.GONE);
                    img_up1.setVisibility(View.GONE);
                }
            });
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(check_valid()==0)
                    {
                        Toast.makeText(getApplicationContext(),"ENTER THE DETIALS PROPERLY",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        update_upload();
                    }
                    }
            });
        }
        else {
            submit.setVisibility(View.VISIBLE);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(check_valid()==0)
                    {
                        Toast.makeText(getApplicationContext(),"ENTER THE DETIALS PROPERLY",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        new_upload();
                    }   }
            });}
    }
    public void openFileChooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,pick_img_request);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick_img_request &&resultCode==RESULT_OK
                &&data!=null &&data.getData()!=null){
            mImageUri=data.getData();
            Picasso.with(this).load(mImageUri).into(img_up1);
            img_up1.setVisibility(View.VISIBLE);
        }
    }
    public int check_valid(){
        if (TextUtils.isEmpty(hall_name_t.getText())) {
            hall_name_t.setError("Enter the Hall Name");
            hall_name_t.requestFocus();
            return 0;
        }
        else if (TextUtils.isEmpty(capacity_t.getText())) {
            capacity_t.setError("Enter the Hall Capicity");
            capacity_t.requestFocus();
            return 0;
        } else if (TextUtils.isEmpty(location_t.getText())) {
            location_t.setError("Enter the Hall Location");
            location_t.requestFocus();
            return 0;
        }
        else if (TextUtils.isEmpty(incharge_t.getText())) {
            incharge_t.setError("Enter the Hall Incharge Name");
            incharge_t.requestFocus();
            return 0;
        }
        else if (TextUtils.isEmpty(in_charge_no_t.getText())&&in_charge_no_t.length()!=10) {
            in_charge_no_t.setError("Enter the Hall Incharge No");
            in_charge_no_t.requestFocus();
            return 0;
        }
        else if (TextUtils.isEmpty(mick_wire.getText())) {
            mick_wire.setError("Enter The On Of Wired Mick");
            mick_wire.requestFocus();
            return 0;
        }
        else if (TextUtils.isEmpty(mick_hand.getText())) {
            mick_hand.setError("Enter The On Of Hand Mick");
            mick_hand.requestFocus();
            return 0;
        }
        else if (TextUtils.isEmpty(mick_coller.getText())) {
            mick_coller.setError("Enter The On Of Coller Mick");
            mick_coller.requestFocus();
            return 0;
        }
        else if (TextUtils.isEmpty(non_teaching_t.getText())) {
            non_teaching_t.setError("Enter the Hall Assitant Name");
            non_teaching_t.requestFocus();
            return 0;
        }else if (TextUtils.isEmpty(non_teaching_no_t.getText())&&non_teaching_no_t.length()!=10) {
            non_teaching_no_t.setError("Enter the Hall Assitant No");
            non_teaching_no_t.requestFocus();
            return 0;
        }
        return 1;
    }

    public void new_upload(){

        final Map m_upload = new HashMap();
        storageReference = FirebaseStorage.getInstance().getReference(type_of_hall_t.getSelectedItem().toString());
        m_upload.put("hall_name", hall_name_t.getText().toString());
        m_upload.put("capacity", capacity_t.getText().toString());
        m_upload.put("hall_type", type_of_hall_t.getSelectedItem().toString());
        m_upload.put("location", location_t.getText().toString());
        m_upload.put("hand_mic",mick_hand.getText().toString());
        m_upload.put("wire_mic",mick_wire.getText().toString());
        m_upload.put("coller_mic",mick_coller.getText().toString());
        int pro_ch = projector_r.getCheckedRadioButtonId();
        val = findViewById(pro_ch);
        m_upload.put("projector", val.getText().toString());
        m_upload.put("incharge", incharge_t.getText().toString());
        m_upload.put("in_charge_no", in_charge_no_t.getText().toString());
        pro_ch = guest_room_r.getCheckedRadioButtonId();
        val = findViewById(pro_ch);
        m_upload.put("guest_room", val.getText().toString());
        pro_ch = marker_board_r.getCheckedRadioButtonId();
        val = findViewById(pro_ch);
        m_upload.put("marker_board", val.getText().toString());
        pro_ch=ac.getCheckedRadioButtonId();
        val=findViewById(pro_ch);
        m_upload.put("ac",val.getText().toString());
        m_upload.put("non_teaching", non_teaching_t.getText().toString());
        m_upload.put("non_teaching_no", non_teaching_no_t.getText().toString());
        storageReference.child(hall_name_t.getText().toString()).putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                storageReference.child(hall_name_t.getText().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        m_upload.put("img_url", uri.toString());
                        firebaseFirestore.collection(type_of_hall_t.getSelectedItem().toString()).document(hall_name_t.getText().toString()).set(m_upload).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                    Toast.makeText(getApplicationContext(), "DATA-SUCCESSFULLY-INSERTER", Toast.LENGTH_SHORT).show();
                                    hall_name_t.setText("");
                                    capacity_t.setText("");
                                    type_of_hall_t.setSelection(0);
                                    location_t.setText("");
                                    projector_r.clearCheck();
                                    incharge_t.setText("");
                                    in_charge_no_t.setText("");
                                    mick_wire.setText("");
                                    mick_hand.setText("");
                                    mick_coller.setText("");
                                    guest_room_r.clearCheck();
                                    marker_board_r.clearCheck();
                                    ac.clearCheck();
                                    non_teaching_t.setText("");
                                    non_teaching_no_t.setText("");
                                    img_up1.setImageResource(0);
                                    img_up1.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                storageReference.child(hall_name_t.getText().toString()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "DATA-INSERTION-FAILED", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        storageReference.child(hall_name_t.getText().toString()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "DATA-INSERTION-FAILED", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "DATA-INSERTION-FAILED", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setMessage((int)progress+" % UPLOADED");
            }
        });
    }

    public void update_upload(){
        firebaseFirestore.collection(bundle.getString("hall_type")).document(bundle.getString("hall_name")).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                final Map m_upload = new HashMap();
                m_upload.put("hall_name", hall_name_t.getText().toString());
                m_upload.put("capacity", capacity_t.getText().toString());
                m_upload.put("hall_type", type_of_hall_t.getSelectedItem().toString());
                m_upload.put("location", location_t.getText().toString());
                m_upload.put("hand_mic",mick_hand.getText().toString());
                m_upload.put("wire_mic",mick_wire.getText().toString());
                m_upload.put("coller_mic",mick_coller.getText().toString());
                int pro_ch = projector_r.getCheckedRadioButtonId();
                val = findViewById(pro_ch);
                m_upload.put("projector", val.getText().toString());
                m_upload.put("incharge", incharge_t.getText().toString());
                m_upload.put("in_charge_no", in_charge_no_t.getText().toString());
                pro_ch = guest_room_r.getCheckedRadioButtonId();
                val = findViewById(pro_ch);
                m_upload.put("guest_room", val.getText().toString());
                pro_ch = marker_board_r.getCheckedRadioButtonId();
                val = findViewById(pro_ch);
                m_upload.put("marker_board", val.getText().toString());
                pro_ch=ac.getCheckedRadioButtonId();
                val=findViewById(pro_ch);
                m_upload.put("ac",val.getText().toString());
                m_upload.put("non_teaching", non_teaching_t.getText().toString());
                m_upload.put("non_teaching_no", non_teaching_no_t.getText().toString());
                m_upload.put("img_url",Url);

                firebaseFirestore.collection(type_of_hall_t.getSelectedItem().toString()).document(hall_name_t.getText().toString()).set(m_upload).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Toast.makeText(registeration.this, "UPDATION SUCCESSFULL", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(registeration.this,admin_mainPage.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registeration.this, "UPDATION FAILED", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}