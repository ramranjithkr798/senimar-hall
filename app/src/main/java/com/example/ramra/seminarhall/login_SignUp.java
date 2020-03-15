package com.example.ramra.seminarhall;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class login_SignUp  extends OptionMenuControl{


    Button RegiButton;
    EditText UserId,UserName,UserDept,UserPass,UserRePass;
    RadioGroup SignInBy;
    RadioButton SignInBy_Name;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__sign_up);
        RegiButton=findViewById(R.id.sign_up_by_submit);
        UserId=findViewById(R.id.sign_up_id);
        UserDept=findViewById(R.id.sign_up_dept);
        UserName=findViewById(R.id.sign_up_name);
        UserPass=findViewById(R.id.sign_up_password);
        UserRePass=findViewById(R.id.sign_up_retypePass);
        firebaseFirestore=FirebaseFirestore.getInstance();
        SignInBy=findViewById(R.id.sign_up_by);

        RegiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserPass.getText().toString().equals(UserRePass.getText().toString()))
                {
                    int Selected=SignInBy.getCheckedRadioButtonId();
                    SignInBy_Name=findViewById(Selected);
                    Map m=new HashMap();
                    m.put("userId",UserId.getText().toString());
                    m.put("userName",UserName.getText().toString());
                    m.put("userDept",UserDept.getText().toString());
                    m.put("userPass",UserPass.getText().toString());
                    m.put("SignUpBy",SignInBy_Name.getText().toString());
                    firebaseFirestore.collection("USERDETIALS").document(UserId.getText().toString()).set(m).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            Toast.makeText(login_SignUp.this, "SUCCESSFULLY INSERTED", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(login_SignUp.this,userLogin.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(login_SignUp.this, "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    {
                    Toast.makeText(login_SignUp.this, "check the password", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
