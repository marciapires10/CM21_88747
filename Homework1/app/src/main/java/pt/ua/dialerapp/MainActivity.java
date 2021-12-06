package pt.ua.dialerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Defining buttons
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9,
            button_star, button0, button_hash, button_memory1, button_memory2, button_memory3;

    // Defining image buttons
    ImageButton clear_number, call;

    // Defining text view
    TextView phone_number;

    // Defining Permission codes.
    // We can give any value
    // but unique for each permission.
    private static final int REQUEST_PHONE_CALL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("pt.ua.dialerapp", MODE_PRIVATE);

        // buttons
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button_star = findViewById(R.id.buttonstar);
        button0 = findViewById(R.id.button0);
        button_hash = findViewById(R.id.buttonhash);
        button_memory1 = findViewById(R.id.memory1);
        button_memory2 = findViewById(R.id.memory2);
        button_memory3 = findViewById(R.id.memory3);

        button_memory1.setText(sharedPreferences.getString("name_memory1", ""));
        button_memory2.setText(sharedPreferences.getString("name_memory2", ""));
        button_memory3.setText(sharedPreferences.getString("name_memory3", ""));

        // image buttons
        clear_number = findViewById(R.id.clearnumber);
        call = findViewById(R.id.call);

        // text view
        phone_number = findViewById(R.id.phonenumber);


        // Set buttons on Click Listeners
        button1.setOnClickListener(v -> addText("1"));

        button2.setOnClickListener(v -> addText("2"));

        button3.setOnClickListener(v -> addText("3"));

        button4.setOnClickListener(v -> addText("4"));

        button5.setOnClickListener(v -> addText("5"));

        button6.setOnClickListener(v -> addText("6"));

        button7.setOnClickListener(v -> addText("7"));

        button8.setOnClickListener(v -> addText("8"));

        button9.setOnClickListener(v -> addText("9"));

        button_star.setOnClickListener(v -> addText("*"));

        button0.setOnClickListener(v -> addText("0"));

        button_hash.setOnClickListener(v -> addText("#"));

        // Click Listener to make a phone call
        call.setOnClickListener(v -> makePhoneCall(false, ""));

        // Click Listener to delete numbers on text view
        clear_number.setOnClickListener(v -> {
            if(phone_number.length() != 0){
                StringBuilder stringBuilder = new StringBuilder(phone_number.getText());
                stringBuilder.deleteCharAt(phone_number.getText().length()-1);

                String new_string = stringBuilder.toString();
                phone_number.setText(new_string);
            }
            else {
                phone_number.setText("");
            }
        });


        // Long Click Listener on memory buttons to open new Activity and add or edit a speed dial.
        button_memory1.setOnLongClickListener(v -> {
            //SharedPreferences sharedPreferences1 = getSharedPreferences("pt.ua.dialerapp", MODE_PRIVATE);
            sharedPreferences.edit().putString("button", "memory1").apply();
            startActivity(new Intent(getApplicationContext(), AddSpeedDial.class));
            return true;
        });

        button_memory2.setOnLongClickListener(v -> {
            //SharedPreferences sharedPreferences13 = getSharedPreferences("pt.ua.dialerapp", MODE_PRIVATE);
            sharedPreferences.edit().putString("button", "memory2").apply();
            startActivity(new Intent(getApplicationContext(), AddSpeedDial.class));
            return true;
        });

        button_memory3.setOnLongClickListener(v -> {
            //SharedPreferences sharedPreferences15 = getSharedPreferences("pt.ua.dialerapp", MODE_PRIVATE);
            sharedPreferences.edit().putString("button", "memory3").apply();
            startActivity(new Intent(getApplicationContext(), AddSpeedDial.class));
            return true;
        });

        // Click Listener on memory buttons to make a direct call.
        button_memory1.setOnClickListener(v -> {
            //SharedPreferences sharedPreferences12 = getSharedPreferences("pt.ua.dialerapp", MODE_PRIVATE);
            String number = sharedPreferences.getString("phone_number_memory1", "");
            makePhoneCall(true, number);
        });

        button_memory2.setOnClickListener(v -> {
            //SharedPreferences sharedPreferences14 = getSharedPreferences("pt.ua.dialerapp", MODE_PRIVATE);
            String number = sharedPreferences.getString("phone_number_memory2", "");
            makePhoneCall(true, number);
        });

        button_memory3.setOnClickListener(v -> {
            //SharedPreferences sharedPreferences16 = getSharedPreferences("pt.ua.dialerapp", MODE_PRIVATE);
            String number = sharedPreferences.getString("phone_number_memory3", "");
            makePhoneCall(true, number);
        });

    }

    public void addText(String value){
        phone_number.setText(phone_number.getText().toString()+value);
    }

    // This function is called when the user wants to make a call.
    // It can be from a speed dial or an input phone number.
    public void makePhoneCall(boolean speed_dial, String number){

        if (!speed_dial) {
            number = phone_number.getText().toString();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
        }
        else{
            number = number.replace("#", "%23");
            if (number.length() == 0){
                Toast.makeText(MainActivity.this, "Please insert a valid phone number", Toast.LENGTH_SHORT).show();
            }
            else {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number)));
            }
        }

    }
    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String number = phone_number.getText().toString();

        if (requestCode == REQUEST_PHONE_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                else {
                    number = number.replace("#", "%23");
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number)));
                }
            }
            else {
                Toast.makeText(MainActivity.this, "Call Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}