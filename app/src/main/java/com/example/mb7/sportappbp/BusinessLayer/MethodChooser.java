package com.example.mb7.sportappbp.BusinessLayer;

import android.app.Activity;

import com.example.mb7.sportappbp.MotivationMethods.MotivationMessage;
import com.example.mb7.sportappbp.MotivationMethods.MotivationMethod;
import com.example.mb7.sportappbp.MotivationMethods.TrainingReminder;
import com.firebase.client.DataSnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Aziani on 22.02.2017.
 * class to choose the used motivation methods from the settings of the webapp
 */

public class MethodChooser {

    /**
     * choose methods depending on the settings in the database
     * @param dataSnapshot snapshot representing entries in database
     * @param fixMotivationMethods list for fix motivation methods
     * @param variableMotivationMethods list for random choosen motivation methods
     * @param activity the calling activity
     */
    public static void chooseMethods(
            DataSnapshot dataSnapshot,
            List<MotivationMethod> fixMotivationMethods,
            List<MotivationMethod> variableMotivationMethods,
            Activity activity) {
        // get current time
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(dataSnapshot == null) {
            return;
        }

        // iterate through the setting options
        for(DataSnapshot d : dataSnapshot.getChildren()){
            // check if current date is part of this setting option
            if(checkTimeSpan(d,calendar) && (boolean)d.child("active").getValue()) {
                // determine the scheme of distribution is wanted
                switch(d.getKey()) {
                    case "sameForAll":
                        sameForAll(d,fixMotivationMethods,variableMotivationMethods,activity);
                        break;
                    case "randomised":
                        randomised(d,fixMotivationMethods,variableMotivationMethods,activity);
                        break;
                    case "alternating":
                        alternating(d,fixMotivationMethods,variableMotivationMethods,activity);
                        break;
                }
            }
        }
    }

    /**
     * checks if current date is within time span of setting
     * @param dataSnapshot snapshot representing entries in database
     * @param calendar calendar containing current date
     * @return true if date is within time span, else false
     */
    private static boolean checkTimeSpan(DataSnapshot dataSnapshot, Calendar calendar) {

        // parse current date into comparable number
        String currentTime = String.valueOf(calendar.get(Calendar.YEAR));
        if(calendar.get(Calendar.MONTH) + 1 < 10) {
            currentTime += "0";
        }
        currentTime += String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            currentTime += "0";
        }
        currentTime += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        // get the start and end of the time span
        String start = parseTimeEntry((String)dataSnapshot.child("from").getValue());
        String end = parseTimeEntry((String)dataSnapshot.child("to").getValue());

        if(start == null || end == null) {
            return false;
        }
        return Integer.valueOf(start) <= Integer.valueOf(currentTime) && Integer.valueOf(currentTime) <= Integer.valueOf(end);
    }

    /**
     * parse date into comparable number
     * @param s string representing the date
     * @return number representing date
     */
    private static String parseTimeEntry(String s) {

        if(s == null) {
            return null;
        }

        String[] splitString = s.split(" ");
        String month;

        // parse date
        String parsedString = splitString[3];
        // get month
        switch(splitString[1]) {
            case "Jan":
                month = "01";
                break;
            case "Feb":
                month = "02";
                break;
            case "Mar":
                month = "03";
                break;
            case "Apr":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "Jun":
                month = "06";
                break;
            case "Jul":
                month = "07";
                break;
            case "Aug":
                month = "08";
                break;
            case "Sep":
                month = "09";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
            default:
                month = "01";
        }
        parsedString += month;
        parsedString += splitString[2];

        return parsedString;
    }

    /**
     * all users get the same methods
     * @param dataSnapshot snapshot representing entries in database
     * @param fixMotivationMethods list for fix motivation methods
     * @param variableMotivationMethods list for random chosen motivation methods
     * @param activity the calling activity
     */
    private static void sameForAll(
            DataSnapshot dataSnapshot,
            List<MotivationMethod> fixMotivationMethods,
            List<MotivationMethod> variableMotivationMethods,
            Activity activity) {
        // iterate through all methods and put them into the list
        for(DataSnapshot d : dataSnapshot.getChildren()) {
            if(d.getValue() instanceof Boolean && (boolean)d.getValue()) {
                putMethodInList(d.getKey(),
                        fixMotivationMethods,
                        variableMotivationMethods,
                        activity);
            }
        }
    }

    /**
     * users get random sets of methods
     * @param dataSnapshot snapshot representing entries in database
     * @param fixMotivationMethods list for fix motivation methods
     * @param variableMotivationMethods list for random chosen motivation methods
     * @param activity the calling activity
     */
    private static void randomised(
            DataSnapshot dataSnapshot,
            List<MotivationMethod> fixMotivationMethods,
            List<MotivationMethod> variableMotivationMethods,
            Activity activity) {
        // iterate through all methods and save them
        LinkedList<String> list = new LinkedList<>();
        for(DataSnapshot d : dataSnapshot.getChildren()) {
            if(d.getValue() instanceof Boolean && (boolean)d.getValue() && !d.getKey().equals("active")) {
                list.add(d.getKey());
            }
        }

        // put random methods into lists
        int noOfUsedMethods = (int)Math.ceil(list.size() * 0.75);
        int listIndex;
        Random random = new Random();
        for(int i = 0; i < noOfUsedMethods; i++) {
            // get random index
            listIndex = random.nextInt(list.size());
            putMethodInList(list.get(listIndex),
                    fixMotivationMethods,
                    variableMotivationMethods,
                    activity);
            // remove method from list
            list.remove(listIndex);
        }
    }

    /**
     * users get alternating sets of methods
     * @param dataSnapshot snapshot representing entries in database
     * @param fixMotivationMethods list for fix motivation methods
     * @param variableMotivationMethods list for random chosen motivation methods
     * @param activity the calling activity
     */
    private static void alternating(
            DataSnapshot dataSnapshot,
            List<MotivationMethod> fixMotivationMethods,
            List<MotivationMethod> variableMotivationMethods,
            Activity activity) {
        // TODO: 23.02.2017
        // alternating distribution
    }

    /**
     * puts the method represented by s into it's list
     * @param s string representing the method
     * @param fixMotivationMethods list for fix motivation methods
     * @param variableMotivationMethods list for random choosen motivation methods
     * @param activity the calling activity
     */
    private static void putMethodInList(
            String s,
            List<MotivationMethod> fixMotivationMethods,
            List<MotivationMethod> variableMotivationMethods,
            Activity activity) {
        // check which method is represented by s and put it in it's list
        switch(s) {
            // TODO adjust string to database entries
            case "Trainingserinnerung":
                fixMotivationMethods.add(new TrainingReminder(activity));
                break;
            case "Motivationsnachricht":
                variableMotivationMethods.add(new MotivationMessage(activity));
                break;
        }
    }
}
