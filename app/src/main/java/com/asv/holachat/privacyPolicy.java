package com.asv.holachat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.asv.holachat.databinding.ActivityPrivacyPolicyBinding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class privacyPolicy extends AppCompatActivity {

   // ActivityPrivacyPolicyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_privacy_policy);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bannerBackground)));
//
//        binding.privacyPolicy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(privacyPolicy.this, MainActivity.class);
//                try {
//                    FileInputStream fis = openFileInput("privacyPolicy.txt");
//                    InputStreamReader isr = new InputStreamReader(fis);
//                    BufferedReader br = new BufferedReader(isr);
//                    StringBuilder sb = new StringBuilder();
//                    String line;
//
//                    while ((line = br.readLine()) != null) {
//                        sb.append(line);
//                        sb.append("\n"); // Add a line break after each line
//                    }
//
//                    br.close();
//                    isr.close();
//                    fis.close();
//                    System.out.println(sb.toString());
//                    binding.privacyPolicy.setText(sb.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            });
//
//
    }
}