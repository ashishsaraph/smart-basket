package com.example.ashish.smartbasket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    Button register;
    EditText uname,email,mob,pass,cpass;

    FirebaseDatabase database1;
    DatabaseReference myRef1;
    FirebaseUser currentFirebaseUser;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uname=(EditText)findViewById(R.id.uname);
        email=(EditText)findViewById(R.id.email);
        mob=(EditText)findViewById(R.id.mob);
        pass=(EditText)findViewById(R.id.pass);
        cpass=(EditText)findViewById(R.id.cpass);
        register=(Button)findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();

        final ProgressDialog dialog = new ProgressDialog(this); // this = YourActivity


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String vuname=uname.getText().toString();
                final String vemail=email.getText().toString();
                final String vmob=mob.getText().toString();
                String vpass=pass.getText().toString();
                String vcpass=cpass.getText().toString();

                if (vuname.isEmpty())
                {
                    uname.setError("User Name Cannot Be Left Blank");
                    uname.requestFocus();
                    return;
                }
                if (vemail.isEmpty())
                {
                    email.setError("Email Cannot Be Left Blank");
                    email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(vemail).matches())
                {
                    email.setError("Please Enter a valid email");
                    email.requestFocus();
                    return;
                }
                if (vmob.isEmpty())
                {
                    mob.setError("Mobile number Cannot Be Left Blank");
                    mob.requestFocus();
                    return;
                }


                if (vpass.isEmpty())
                {
                    pass.setError("Password Cannot Be Left Blank");
                    pass.requestFocus();
                    return;
                }
                if (vpass.length()<6)
                {
                    pass.setError("Password must more tha 6 digits");
                    pass.requestFocus();
                    return;
                }
                if (vcpass.length()<6)
                {
                    cpass.setError("Confirm Password must more tha 6 digits");
                    cpass.requestFocus();
                    return;
                }
                if (vcpass.isEmpty())
                {
                    cpass.setError("Confirm Password Cannot Be Left Blank");
                    cpass.requestFocus();
                    return;
                }

                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Loading. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                mAuth.createUserWithEmailAndPassword(vemail, vpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            finish();
                            Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();

                            database1 = FirebaseDatabase.getInstance();
                            myRef1 = database1.getReference("Users");
                            currentFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();

                            Log.d("uid","cuid"+currentFirebaseUser.getUid());

                            myRef1.child(currentFirebaseUser.getUid()).child("name").setValue(vuname);
                            myRef1.child(currentFirebaseUser.getUid()).child("number").setValue(vmob);
                            myRef1.child(currentFirebaseUser.getUid()).child("emailid").setValue(vemail);

                            Intent reg=new Intent(register.this,MainActivity.class);
                            startActivity(reg);
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });




            }
        });

    }
}
