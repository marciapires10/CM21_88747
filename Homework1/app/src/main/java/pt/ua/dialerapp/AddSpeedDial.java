package pt.ua.dialerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSpeedDial extends AppCompatActivity {

    // Defining edit texts
    EditText name, phone_number;

    // Defining button
    Button submit, speed_dial1, speed_dial2, speed_dial3;

    // Shared Preferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_speed_dial);

        sharedPreferences = this.getSharedPreferences("pt.ua.dialerapp", MODE_PRIVATE);

        String button_speed_dial = sharedPreferences.getString("button", "");

        // edit texts inputs
        name = findViewById(R.id.name);
        phone_number = findViewById(R.id.phone);

        // buttons
        submit = findViewById(R.id.submitSD);


        submit.setOnClickListener(v -> {
            sharedPreferences.edit().putString("button", button_speed_dial).apply();
            sharedPreferences.edit().putString("name_" + button_speed_dial, name.getEditableText().toString()).apply();
            sharedPreferences.edit().putString("phone_number_" + button_speed_dial, phone_number.getEditableText().toString()).apply();

            Toast.makeText(AddSpeedDial.this, "Speed dial saved successfully!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume(){
        super.onResume();

        sharedPreferences = this.getSharedPreferences("pt.ua.dialerapp", MODE_PRIVATE);

        String button_speed_dial = sharedPreferences.getString("button", "");
        String name_sh = sharedPreferences.getString("name_" + button_speed_dial, "");
        String phone_number_sh = sharedPreferences.getString("phone_number_" + button_speed_dial, "");

        name.setText(name_sh);
        phone_number.setText(phone_number_sh);
    }
}
