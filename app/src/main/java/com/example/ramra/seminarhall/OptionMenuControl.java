package com.example.ramra.seminarhall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class OptionMenuControl extends AppCompatActivity
{

    Menu menuName;
    static Boolean user=false;
    static String userName="";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        menuName=menu;
        if(user==true){
            MenuItem login=menu.findItem(R.id.login);
            login.setTitle(userName);
            MenuItem signup=menu.findItem(R.id.signUp);
            signup.setVisible(false);
            MenuItem logout=menu.findItem(R.id.logout);
            logout.setVisible(true);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem i=menuName.findItem(R.id.login);
        if(item.getItemId()==R.id.login)
        {
            if(i.getTitle().toString().equals("login"))
            {
                Intent intent=new Intent(OptionMenuControl.this,userLogin.class);
                startActivity(intent);
            }
            else
                Toast.makeText(this, i.getTitle().toString(), Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.exit)
        {
            finish();
            moveTaskToBack(true);
        }
        else if(item.getItemId()==R.id.logout)
        {
            user=false;
            Intent intent = new Intent(OptionMenuControl.this, userLogin.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.signUp)
        {
            Intent intent = new Intent(OptionMenuControl.this, login_SignUp.class);
            startActivity(intent);
        }
        else
            return super.onOptionsItemSelected(item);
        return true;

    }
    void OnUserChange(String name,String uName){
        if(name=="login")
        {
            user=true;
            userName=uName;
        }
    }

}




