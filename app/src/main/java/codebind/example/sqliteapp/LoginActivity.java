package codebind.example.sqliteapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
EditText email,password;
Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         email=findViewById(R.id.txtEmail);
         password=findViewById(R.id.txtPwd);
         loginBtn=findViewById(R.id.btnLogin);
         DatabaseHelper myDb;
         myDb =new DatabaseHelper(this);
         loginBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Cursor res = myDb.getAllData();
                 if (res.getCount() == 0) {
                     // show message
                     //showMessage("Error","Nothing found");
                     return;
                 }
                 StringBuffer buffer = new StringBuffer();
                 int flag = 0;
                 boolean isInserted = false;
                 while (res.moveToNext()) {
                     if (res.getString(2).equals(email.getText().toString())&&res.getString(3).equals(password.getText().toString())) {
                         flag = 1;
                         Toast.makeText(LoginActivity.this, "email already taken", Toast.LENGTH_SHORT).show();
                         User.setName(res.getString(1));
                         User.setEmail(res.getString(2));
                         startActivity(new Intent(LoginActivity.this,CalendarActivity.class));
                     }
                 }
                 if (flag != 1) {

                 }
             }
         });
    }
}