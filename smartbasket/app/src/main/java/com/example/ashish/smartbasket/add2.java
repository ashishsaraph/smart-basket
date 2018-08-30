package com.example.ashish.smartbasket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class add2 extends AppCompatActivity {
    EditText pid,pname,desp,price;
    Button submit;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);
       pid = (EditText) findViewById(R.id.pid);
        pname = (EditText) findViewById(R.id.pname);
        desp = (EditText) findViewById(R.id.des);
        price = (EditText) findViewById(R.id.price);
        submit=(Button)findViewById(R.id.submit);
        Intent intent=new Intent();
        final String content=getIntent().getExtras().getString("Contents");
        pid.setText(content);

         databaseReference = FirebaseDatabase.getInstance().getReference().child("addproduct");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pids = pid.getText().toString().trim();
                final long pidi = Long.parseLong(pids);
                String pnames = pname.getText().toString().trim();
                final String des = desp.getText().toString().trim();
                //int quantityi = Integer.parseInt(quantitys);
                String prices = price.getText().toString().trim();
                int pricei = Integer.parseInt(prices);

                final addproduct addproduct = new addproduct(pidi, pnames, des, pricei);
                //databaseReference.push().setValue(addproduct);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(String.valueOf(pidi)).exists()) {
                            // run some code
                            Toast.makeText(getApplicationContext(), "Product Already Exists", Toast.LENGTH_SHORT).show();
                        }else{
                            databaseReference.child(String.valueOf(pidi)).setValue(addproduct);

                            Toast.makeText(getApplicationContext(), "Product Added successfully", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(add2.this,nav.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });


    }
    @Override
    public void onBackPressed() {

    }


}
