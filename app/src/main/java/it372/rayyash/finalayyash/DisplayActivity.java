package it372.rayyash.finalayyash;
//Rheema Ayyash
//June 3 2025
//It372 final
//displays the list of submitted internship app from sql database
//also has a back button to go back to the main activity to submit a new app
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    //user input
    DatabaseHelper databaseHelper;
    TextView txtDisplay;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // links input to layout
        txtDisplay = findViewById(R.id.txt_display);
        btnBack = findViewById(R.id.btn_back_to_app);

        //initialize the database to access info
        databaseHelper = new DatabaseHelper(this);

        //retrieves submitted info
        List<String> applications = databaseHelper.getAllApplications(); // assuming this method exists
        StringBuilder displayText = new StringBuilder();

        //displays text from list of apps
        for (String app : applications) {
            displayText.append(app).append("\n\n");
        }

        //shows submitted apps or message if empty
        txtDisplay.setText(displayText.toString().isEmpty() ? getString(R.string.no_applications_found) : displayText.toString());

        //back button to return to main app page
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // optional: closes DisplayActivity
            }
        });
    }
}
