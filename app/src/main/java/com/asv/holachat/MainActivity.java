package com.asv.holachat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.asv.holachat.Adapter.FragmentAdapter;
import com.asv.holachat.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bannerBackground)));
        mAuth = FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ){
            case R.id.settings:
                Intent intent2 = new Intent(MainActivity.this, settingsActivity.class);
                Toast.makeText(this, "Opening Settings!!", Toast.LENGTH_SHORT).show();
                startActivity(intent2);
                break;
            case R.id.logOut:
                Toast.makeText(this, "SigningOut!!", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                Toast.makeText(this, "LoggedOut Successfully!!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.groupChat:
                Intent intent1 = new Intent(MainActivity.this, groupChatActivity.class);
                Toast.makeText(this, "Opening GroupChat!!", Toast.LENGTH_SHORT).show();
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}