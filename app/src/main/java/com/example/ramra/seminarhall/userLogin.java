package com.example.ramra.seminarhall;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class userLogin  extends OptionMenuControl {

    TextView SignText;
    EditText UserName,Pass;
    Button LoginBtn;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        firebaseFirestore=FirebaseFirestore.getInstance();
        LoginBtn=findViewById(R.id.Login_button);
        UserName=findViewById(R.id.Login_UserName);
        Pass=findViewById(R.id.Login_Password);
        final OptionMenuControl optionMenuControl=new OptionMenuControl();
        SignText=findViewById(R.id.Login_Signup);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserName.getText().toString().equals("admin")&&Pass.getText().toString().equals("admin"))
                {
                    optionMenuControl.OnUserChange("login",UserName.getText().toString());
                    Intent intent=new Intent(userLogin.this,admin_mainPage.class);
                    startActivity(intent);
                }
                else{
                    firebaseFirestore.collection("USERDETIALS").document(UserName.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if(Pass.getText().toString().equals(documentSnapshot.getString("userPass")))
                            {
                                Toast.makeText(userLogin.this, "login successfull", Toast.LENGTH_SHORT).show();
                                optionMenuControl.OnUserChange("login",UserName.getText().toString());
                                Intent intent=new Intent(userLogin.this,home.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(userLogin.this, "ENTER THE VALID PASSWORD", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(userLogin.this, "ENTER THE VALID USERNAME", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        SignText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(userLogin.this,login_SignUp.class);
                startActivity(intent);
            }
        });
    }

}