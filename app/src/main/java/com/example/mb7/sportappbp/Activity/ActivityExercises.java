package com.example.mb7.sportappbp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.mb7.sportappbp.Adapters.ExerciseViewAdapter;
import com.example.mb7.sportappbp.Objects.Exercise;
import com.example.mb7.sportappbp.Objects.LeistungstestsExercise;
import com.example.mb7.sportappbp.Objects.ReinerAufenthaltExercise;
import com.example.mb7.sportappbp.Objects.TrainingExercise;
import com.example.mb7.sportappbp.Objects.WellnessExercise;
import com.example.mb7.sportappbp.R;

import java.util.ArrayList;

public class ActivityExercises extends AppCompatActivity {


    private ListView listview;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> exLst;
    private ArrayList<Exercise> exerciseList;

    private ListView listViewSelected;
    private ExerciseViewAdapter exerciseViewAdapter;

    private int timeMinutes;
    private int timeHours;

    private String chosenExercise;
    private String selectedCategory;

    private boolean finalResult = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        selectedCategory = receiveCategory();
        exerciseList = receiveExerciseList();

        //Set title of the label
        setTitle(selectedCategory);
        //get the list of the activity for the chosen category
        exLst = getListOfActivities(selectedCategory);

        listview = (ListView) findViewById(R.id.listviewExercises);
        arrayAdapter = new ArrayAdapter<String>(ActivityExercises.this, android.R.layout.simple_list_item_1, exLst);
        listview.setAdapter(arrayAdapter);

        listViewSelected = (ListView) findViewById(R.id.listviewExercisesSelected);
        exerciseViewAdapter = new ExerciseViewAdapter(ActivityExercises.this, exerciseList);
        listViewSelected.setAdapter(exerciseViewAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                chosenExercise = (String) adapterView.getItemAtPosition(position);
                numberPicker();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //set the menu with a save icon
        inflater.inflate(R.menu.menu_save, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //check which icon was hidden in the toolbar
        switch (item.getItemId()){
            case android.R.id.home:
                //save the selected items and send them to the previous activity
                //returnResult();
                //close the activity
                finish();
                return true;
            case R.id.icon_save:
                finalResult = true;
                returnFinalResult();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList getListOfActivities(String category){

        ArrayList<String> result = new ArrayList<>();

        switch(category){
            case "Leistungstests":
                result.add("Spiroergometrie");
                result.add("Laktattest");
                result.add("Beweglichkeitstest");
                result.add("Krafttest");
                result.add("Anderer Leistungstest");
                return result;

            case "Training":
                result.add("Krafttraining");
                result.add("Laufen");
                result.add("Cycling");
                result.add("Beweglichkeit/Flexibilität");
                result.add("Yoga");
                result.add("Rückentraining");
                result.add("Progressive");
                result.add("Muskelentspannung");
                result.add("Autogenes Training");
                result.add("Meditation");
                result.add("Anderes Training");

            return result;

            case "Wellness":
                result.add("Sauna");
                result.add("Dampfbad");
                result.add("Massage");
                result.add("Solarium");
                result.add("Andere Wellnessaktivität");
                return result;

            case "Reiner Aufenthalt":
                result.add("Soziale Kontakte");
                result.add("Bistro");
                result.add("Andere");
                return result;
            default:
                result.add("Kategorie falsch ausgeählt");
                return result;
        }

    }

    private String receiveCategory(){
        String category = "";

        final Bundle extra = getIntent().getExtras();
        if (extra != null) {
            category = extra.getString("category");
        }
        return category;
    }

    private ArrayList<Exercise> receiveExerciseList(){

        ArrayList<Exercise> result;
        final Bundle extra = getIntent().getExtras();

        if (extra != null) {
            result = extra.getParcelableArrayList("oldExercises");
            return result;
        }
        else
            return result = new ArrayList<Exercise>();
    }

    /**
     * This method returns the result of the request. The result is the chosen activity
     */
    private void returnResult(){

        Exercise result = selectedExerciseCategory();

        if(result != null) {
            result.setExercise(chosenExercise);
            result.setTimeMinutes(timeMinutes);
            result.setTimeHours(timeHours);
            exerciseList.add(result);

            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("newExercises", exerciseList);
            intent.putExtra("finalResult", finalResult);
            setResult(RESULT_OK, intent);
            exerciseViewAdapter.notifyDataSetChanged();
        }
        else
            Toast.makeText(ActivityExercises.this, "Kategorie wurde nicht richtig gewählt!", Toast.LENGTH_SHORT).show();

    }

    private void returnFinalResult(){

        if(exerciseList != null) {

            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("newExercises", exerciseList);
            intent.putExtra("finalResult", finalResult);
            setResult(RESULT_OK, intent);
            exerciseViewAdapter.notifyDataSetChanged();
        }
        else
            Toast.makeText(ActivityExercises.this, "Kategorie wurde nicht richtig gewählt!", Toast.LENGTH_SHORT).show();

    }

    private Exercise selectedExerciseCategory(){

        if(selectedCategory.equals("Leistungstests")){
            return new LeistungstestsExercise();
        }
        else if(selectedCategory.equals("Training")){
            return new TrainingExercise();
        }
        else if(selectedCategory.equals("Wellness")){
            return new WellnessExercise();
        }
        else if(selectedCategory.equals("Reiner Aufenthalt")){
            return new ReinerAufenthaltExercise();
        }//todo overthink else case
        else return null;

    }


    /**
     * Creates a dialog window with two number pickers for hours and minutes. When the ok button
     * will be pressed, the data will be saved. The cancel button is going to close the dialog
     * window
     */
    private void numberPicker(){

        //create dialog window
        final Dialog dialog = new Dialog(ActivityExercises.this);

        //set the layout for the dialog window
        dialog.setContentView(R.layout.dialog_two_numberpicker);


        final String[] nums = new String[30];
        for(int i=0; i<nums.length; i++) {
            nums[i] = Integer.toString(i);
        }

        //create the number picker for hours und set the possible values
        final NumberPicker npHoures = (NumberPicker)dialog.findViewById(R.id.numberPickerHours);
        npHoures.setMaxValue(24);
        npHoures.setMinValue(0);
        npHoures.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        //create the number picker for minutes und set the possible values
        final NumberPicker npMinutes = (NumberPicker)dialog.findViewById(R.id.numberPickerMinutes);
        npMinutes.setMaxValue(59);
        npMinutes.setMinValue(0);
        npMinutes.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        //set the action for ok button
        Button btnOk = (Button)dialog.findViewById(R.id.npOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //save the picked numbers
                timeHours = npHoures.getValue();
                timeMinutes = npMinutes.getValue();
                dialog.dismiss();
                returnResult();
            }
        });

        //set the action for cancel button
        Button btnCancel = (Button) dialog.findViewById(R.id.npCancle);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //close the dialog windows without doing anything
                dialog.dismiss();
            }
        });

        //show the dialog window
        dialog.show();

    }
}