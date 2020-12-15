package codebind.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
DatabaseHelper myDb;
EditText name,email,password;
TextView lnkLogin;
Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DatabaseHelper(this);
        name=findViewById(R.id.txtName);
        email=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPwd);
        btnLogin=findViewById(R.id.btnLogin);
        lnkLogin=findViewById(R.id.lnkLogin);
        lnkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        AddData();


    }
    public  void AddData() {
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                           myDb.insertData(name.getText().toString(),
                                    email.getText().toString() ,
                                    password.getText().toString());
                            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        }
                        StringBuffer buffer = new StringBuffer();
                        int flag=0;
                        boolean isInserted = false;
                        while (res.moveToNext()) {
                            if (res.getString(2).equals(email.getText().toString()))
                            {
                                flag=1;
                                Toast.makeText(RegisterActivity.this, "email already taken", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (flag!=1)
                        {
                             isInserted = myDb.insertData(name.getText().toString(),
                                    email.getText().toString() ,
                                    password.getText().toString());
                             startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        }
                        Log.i("TAG", "onClick: "+buffer.toString());
                        if(isInserted == true)
                            Toast.makeText(RegisterActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(RegisterActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}