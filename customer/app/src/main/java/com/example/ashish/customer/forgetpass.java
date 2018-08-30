package com.example.ashish.customer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgetpass extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emaild;
    Button submit;
    FirebaseUser currentFirebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        emaild=(EditText)findViewById(R.id.emailid);
        submit=(Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                currentFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                String emailAddress = emaild.getText().toString();
                if(emailAddress.equals(""))
                {
                    emaild.setError("Email is required");
                    emaild.requestFocus();

                    Toast.makeText(getApplicationContext(),"Email address cannot be left blank",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    emaild.setError("Please enter a valid email");
                    emaild.requestFocus();
                    return;
                }

                else {
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Password link sent to your mail", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(forgetpass.this, logincustomer.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "User Email Does not Exist", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(forgetpass.this, logincustomer.class);
//                                        startActivity(i);
                                    }
                                }
                            });
                }

            }
        });
    }
}
