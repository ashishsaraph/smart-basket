package com.example.ashish.smartbasket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class delete2 extends AppCompatActivity {

    EditText pid,pname,desp,price;
    Button submit;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete2);
        pid = (EditText) findViewById(R.id.pid);
        pname = (EditText) findViewById(R.id.pname);
        desp = (EditText) findViewById(R.id.des);

        price = (EditText) findViewById(R.id.price);
        submit = (Button) findViewById(R.id.submit);
        Intent intent = new Intent();
        final String content = getIntent().getExtras().getString("Contents");
        pid.setText(content);

        final String pidi = pid.getText().toString().trim();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("addproduct").child(pidi);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("pname").getValue().toString();
                String prices=dataSnapshot.child("price").getValue().toString();
                String des=dataSnapshot.child("des").getValue().toString();

                int pricei= Integer.valueOf(prices);

                pname.setText(name);
                desp.setText(des);
                price.setText(String.valueOf(pricei));
                Log.i("name",name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pids = pid.getText().toString().trim();
                DatabaseReference ref = database.getReference("addproduct").child(pids);
                ref.removeValue();
                Toast.makeText(getApplicationContext(), "Product Delete successfully", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(delete2.this,nav.class);
                startActivity(i);
            }
        });

    }


}
