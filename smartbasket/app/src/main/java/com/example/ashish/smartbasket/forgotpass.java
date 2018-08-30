package com.example.ashish.smartbasket;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotpass extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emaild;
    Button submit;
    FirebaseUser currentFirebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        emaild=(EditText)findViewById(R.id.emailid);
        submit=(Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                currentFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                String emailAddress = emaild.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Password link sent to your mail",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(forgotpass.this,MainActivity.class);
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"User Email Does not Exist",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(forgotpass.this,MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        });

            }
        });
    }
}
