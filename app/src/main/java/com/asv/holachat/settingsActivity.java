package com.asv.holachat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asv.holachat.Models.Users;
import com.asv.holachat.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class settingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        binding.backArraow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        binding.txtView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsActivity.this, privacyPolicy.class);
                startActivity(intent);
                //textView2.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
        binding.txtView3.setMovementMethod(LinkMovementMethod.getInstance());





        binding.txtView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType( "text/plain" );
                intent.putExtra( Intent.EXTRA_SUBJECT, "Check out this chatting application !!" );
                intent.putExtra( Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.asv.holachat" );
                startActivity( Intent.createChooser( intent,"Share with" ) );
                //textView2.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!binding.enterStatus.getText().toString().equals("") && !binding.txtUsername.getText().toString().equals("")) {


                    String status = binding.enterStatus.getText().toString();
                    String username = binding.txtUsername.getText().toString();

                    HashMap<String, Object> obj = new HashMap<>();
                    obj.put("userName", username);
                    obj.put("status", status);

                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                            .updateChildren(obj);

                    Toast.makeText(settingsActivity.this, "Profile Updated !!", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(settingsActivity.this, "Please enter the username and about !!", Toast.LENGTH_SHORT).show();
                }
                }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Users users = snapshot.getValue(Users.class);
                                Picasso.get().load(users.getProfilePic())
                                        .placeholder(R.drawable.avatar)
                                        .into(binding.profileImage);

                                binding.enterStatus.setText(users.getStatus());
                                binding.txtUsername.setText(users.getUserName());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT); // to fetch the file
                intent.setType("image/*"); // to fetch the image
                startActivityForResult(intent, 25); // to fetch the image from the gallery
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null) {
            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

            final StorageReference reference = storage.getReference().child("profile_pic")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                      .child("profilePic").setValue(uri.toString());
                          }
                     });
                }
            });
        }

    }
}