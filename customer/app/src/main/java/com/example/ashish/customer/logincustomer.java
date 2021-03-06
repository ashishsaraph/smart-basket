package com.example.ashish.customer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logincustomer extends AppCompatActivity {
    EditText edittextemail,edittextpassword;
    Button login;
    TextView register,forgot;
    private FirebaseAuth mAuth;
    Activity contect;
    FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logincustomer);
        final ProgressDialog dialog = new ProgressDialog(this); // this = YourActivity
        contect=this;
        edittextemail=(EditText)findViewById(R.id.uname);
        edittextpassword=(EditText)findViewById(R.id.pass);
        register=(TextView)findViewById(R.id.register);
        login=(Button)findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        forgot=(TextView)findViewById(R.id.fgpass);



        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(logincustomer.this,forgetpass.class);
//                startActivity(i);

                final EditText taskEditText = new EditText(contect);
                AlertDialog dialog = new AlertDialog.Builder(contect)
                        .setTitle("Forgot Password")
                        .setMessage("Please Enter Email Address")
                        .setView(taskEditText)
                        .setPositiveButton("Send Mail", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                currentFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();


                                    auth.sendPasswordResetEmail(task)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Password link sent to your mail", Toast.LENGTH_SHORT).show();
//                                                        Intent i = new Intent(forgetpass.this, logincustomer.class);
//                                                        startActivity(i);
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "User Email Does not Exist", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(forgetpass.this, logincustomer.class);
//                                        startActivity(i);
                                                    }
                                                }
                                            });
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email= edittextemail.getText().toString().trim();
                String password= edittextpassword.getText().toString().trim();

                if (email.isEmpty()) {
                    edittextemail.setError("Email is required");
                    edittextemail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edittextemail.setError("Please enter a valid email");
                    edittextemail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    edittextpassword.setError("Password is required");
                    edittextpassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    edittextpassword.setError("Minimum lenght of password should be 6");
                    edittextpassword.requestFocus();
                    return;
                }


                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Loading. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Valid User",Toast.LENGTH_LONG).show();
                            Intent reg=new Intent(logincustomer.this,RecycleView.class);
                            startActivity(reg);
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg=new Intent(logincustomer.this,registration.class);
                startActivity(reg);
            }
        });



    }
}
