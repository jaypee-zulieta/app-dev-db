package com.example.myapplication;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MyValueEventListener implements ValueEventListener {

    private final TextView emailTextView;
    private final TextView welcomeTextView;
    private final AppCompatActivity appCompatActivity;

    public MyValueEventListener(TextView emailTextView, TextView welcomeTextView, AppCompatActivity appCompatActivity) {
        this.emailTextView = emailTextView;
        this.welcomeTextView = welcomeTextView;
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        User user = snapshot.getValue(User.class);
        if(user != null) {
            emailTextView.setText(user.getEmail());
            welcomeTextView.setText(String.format("Welcome, %s!", user.getName()));
        }
        else Toast.makeText(appCompatActivity, "No data found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(appCompatActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
