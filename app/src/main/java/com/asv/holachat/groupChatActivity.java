package com.asv.holachat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.asv.holachat.Adapter.chatAdapter;
import com.asv.holachat.Models.messageModel;
import com.asv.holachat.databinding.ActivityChatDetailBinding;
import com.asv.holachat.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class groupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(groupChatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<messageModel> messageModels = new ArrayList<>();
        final String senderId = FirebaseAuth.getInstance().getUid();
        binding.username.setText("GroupChat");
        final chatAdapter adapter = new chatAdapter(messageModels, this);
        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("GroupChat")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messageModels.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    messageModel model = dataSnapshot.getValue(messageModel.class);
                                    messageModels.add(model);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = binding.enterMessage.getText().toString();
                final messageModel model = new messageModel(senderId, message);
                model.setTimestamp( new Date().getTime() );
                binding.enterMessage.setText("");
                database.getReference().child("GroupChat").push().setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(groupChatActivity.this, "Message Sent!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}