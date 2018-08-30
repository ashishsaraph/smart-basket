package com.example.ashish.smartbasket;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ashish on 12-Apr-18.
 */

public class Profile_fragment extends Fragment {

    DatabaseReference myRef;
    FirebaseUser currentFirebaseUser;
    FirebaseDatabase database1;

    EditText user,con,emailid;
    Button update;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View myview = inflater.inflate(R.layout.profile_fragment, container, false);

        database1 = FirebaseDatabase.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database1.getReference("Users").child(currentFirebaseUser.getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String contact=dataSnapshot.child("number").getValue().toString();
                String email=dataSnapshot.child("emailid").getValue().toString();

                Toast.makeText(myview.getContext(), email, Toast.LENGTH_SHORT).show();

                //Run this now okay
                // Log.i("address,name",address+name);

                user=(EditText)myview.findViewById(R.id.username);
                con=(EditText)myview.findViewById(R.id.contact);
                emailid=(EditText)myview.findViewById(R.id.email);

                Log.i("user",user.toString());

                user.setText(name);
                emailid.setText(email);
                con.setText(contact);

                update=(Button)myview.findViewById(R.id.updatebutton);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(myview.getContext(),ProfileUpdateActivity.class);
                        startActivity(i);
                    }


                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return myview;
    }
}
