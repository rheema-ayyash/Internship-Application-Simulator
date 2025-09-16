package it372.rayyash.finalayyash;
//Rheema Ayyash
//June 3,2025
//It372 final
//main activity that takes users input from application page, validates it, saves it to the
//sql database and make sure it is saved on the second activity which is the display activity
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //required user input
    EditText edName, edEmail, edOtherMajor, edOtherSkills, edGpa;
    Spinner spinnerMajor;
    RadioGroup radioGroupYear;
    CheckBox cbJava, cbPython, cbCpp;
    TextView txtDate;
    String selectedDate = "";
    DatabaseHelper databaseHelper;

    //for saving state
    private static final String KEY_NAME = "key_name";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_MAJOR = "key_major";
    private static final String KEY_OTHER_MAJOR = "key_other_major";
    private static final String KEY_YEAR_ID = "key_year_id";
    private static final String KEY_SKILLS = "key_skills";
    private static final String KEY_OTHER_SKILLS = "key_other_skills";
    private static final String KEY_GPA = "key_gpa";
    private static final String KEY_DATE = "key_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialized input and database
        edName = findViewById(R.id.edtxt_name);
        edEmail = findViewById(R.id.edtxt_email);
        spinnerMajor = findViewById(R.id.spinner_major);
        edOtherMajor = findViewById(R.id.other);
        radioGroupYear = findViewById(R.id.radio_group_year);
        cbJava = findViewById(R.id.cb_java);
        cbPython = findViewById(R.id.cb_python);
        cbCpp = findViewById(R.id.cb_cpp);
        edOtherSkills = findViewById(R.id.otherSkills);
        edGpa = findViewById(R.id.gpa);
        txtDate = findViewById(R.id.txt_date);

        //adds to database
        databaseHelper = new DatabaseHelper(this);

        //spinner options
        spinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //when major is selected it shows, if other it adds a text area to type in major
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                if (selected.equalsIgnoreCase("Other")) {
                    edOtherMajor.setVisibility(View.VISIBLE);
                } else {
                    edOtherMajor.setVisibility(View.GONE);
                }
            }

            //does nothing if nothing is selected
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        //button to pick date

        findViewById(R.id.btn_pick_date).setOnClickListener(v -> showDatePicker());

        // save from state
        if (savedInstanceState != null) {
            edName.setText(savedInstanceState.getString(KEY_NAME));
            edEmail.setText(savedInstanceState.getString(KEY_EMAIL));
            selectedDate = savedInstanceState.getString(KEY_DATE, "");
            txtDate.setText(selectedDate);
            edOtherMajor.setText(savedInstanceState.getString(KEY_OTHER_MAJOR));
            edOtherSkills.setText(savedInstanceState.getString(KEY_OTHER_SKILLS));
            edGpa.setText(savedInstanceState.getString(KEY_GPA));
            int yearId = savedInstanceState.getInt(KEY_YEAR_ID, -1);
            if (yearId != -1) {
                radioGroupYear.check(yearId);
            }
            ArrayList<String> skills = savedInstanceState.getStringArrayList(KEY_SKILLS);
            if (skills != null) {
                cbJava.setChecked(skills.contains("Java"));
                cbPython.setChecked(skills.contains("Python"));
                cbCpp.setChecked(skills.contains("C++"));
            }

            // Set major in spinner
            String savedMajor = savedInstanceState.getString(KEY_MAJOR);
            if (savedMajor != null) {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.majors_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMajor.setAdapter(adapter);
                int position = adapter.getPosition(savedMajor);
                if (position != -1) {
                    spinnerMajor.setSelection(position);
                } else {
                    spinnerMajor.setSelection(adapter.getPosition("Other"));
                    edOtherMajor.setVisibility(View.VISIBLE);
                    edOtherMajor.setText(savedMajor);
                }
            }
        }
    }

    //shows calender for user to pick date
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    selectedDate = (month1 + 1) + "/" + dayOfMonth + "/" + year1;
                    txtDate.setText(selectedDate);
                }, year, month, day);
        dialog.show();
    }

    //save from state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_NAME, edName.getText().toString());
        outState.putString(KEY_EMAIL, edEmail.getText().toString());
        outState.putString(KEY_MAJOR, spinnerMajor.getSelectedItem().toString());
        outState.putString(KEY_OTHER_MAJOR, edOtherMajor.getText().toString());
        outState.putInt(KEY_YEAR_ID, radioGroupYear.getCheckedRadioButtonId());

        ArrayList<String> skills = new ArrayList<>();
        if (cbJava.isChecked()) skills.add("Java");
        if (cbPython.isChecked()) skills.add("Python");
        if (cbCpp.isChecked()) skills.add("C++");
        outState.putStringArrayList(KEY_SKILLS, skills);

        outState.putString(KEY_OTHER_SKILLS, edOtherSkills.getText().toString());
        outState.putString(KEY_GPA, edGpa.getText().toString());
        outState.putString(KEY_DATE, selectedDate);
    }

    //submits app + collects input
    public void onClickSubmit(View view) {
        String name = edName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();

        String major = spinnerMajor.getSelectedItem().toString();
        String otherMajorInput = edOtherMajor.getText().toString().trim();
        if (major.equalsIgnoreCase("Other") && !otherMajorInput.isEmpty()) {
            major = otherMajorInput;
        }

        int selectedYearId = radioGroupYear.getCheckedRadioButtonId();
        RadioButton selectedYearBtn = findViewById(selectedYearId);
        String year = selectedYearBtn != null ? selectedYearBtn.getText().toString() : "";

        List<String> skillsList = new ArrayList<>();
        if (cbJava.isChecked()) skillsList.add("Java");
        if (cbPython.isChecked()) skillsList.add("Python");
        if (cbCpp.isChecked()) skillsList.add("C++");
        String skills = String.join(", ", skillsList);

        String otherSkills = edOtherSkills.getText().toString().trim();
        String gpa = edGpa.getText().toString().trim();
        String date = selectedDate;

        if (name.isEmpty() || email.isEmpty() || major.isEmpty() || year.isEmpty() || gpa.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean inserted = databaseHelper.insertApplication(
                name, email, major, year, skills, gpa, date, otherSkills);

        if (inserted) {
            Toast.makeText(this, "Application submitted!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Failed to submit application.", Toast.LENGTH_SHORT).show();
        }
    }

    //button to view submitted apps
    public void onClickViewApplications(View view) {
        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        startActivity(intent);
    }
}
