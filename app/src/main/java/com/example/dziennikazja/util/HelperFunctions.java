package com.example.dziennikazja.util;

import android.text.TextUtils;
import android.widget.ImageView;

import com.example.dziennikazja.R;
import com.google.android.material.textfield.TextInputEditText;

public class HelperFunctions {
    public static boolean areInputsValid(TextInputEditText... editTexts) {
        for (TextInputEditText et : editTexts) {
            if (TextUtils.isEmpty(et.getText().toString().trim())) {
                return false;
            }
        }
        return true;
    }

    public static int convertDayNameToDayNumber(String dayName) {
        int dayNumber = 1;
        switch (dayName) {
            case "Poniedziałek":
                dayNumber = 1;
                break;
            case "Wtorek":
                dayNumber = 2;
                break;
            case "Środa":
                dayNumber = 3;
                break;
            case "Czwartek":
                dayNumber = 4;
                break;
            case "Piątek":
                dayNumber = 5;
                break;
            case "Sobota":
                dayNumber = 6;
                break;
            case "Niedziela":
                dayNumber = 7;
                break;
        }
        return dayNumber;
    }

    public static void setBeltIconColor(ImageView beltIcon, String tkdGrade) {
        switch (tkdGrade) {
            case "9 kup":
                beltIcon.setImageResource(R.drawable.ic_belt_yellow_tag);
                break;
            case "8 kup":
                beltIcon.setImageResource(R.drawable.ic_belt_yellow);
                break;
            case "7 kup":
                beltIcon.setImageResource(R.drawable.ic_belt_green_tag);
                break;
            case "6 kup":
                beltIcon.setImageResource(R.drawable.ic_belt_green);
                break;
            case "5 kup":
                beltIcon.setImageResource(R.drawable.ic_belt_blue_tag);
                break;
            case "4 kup":
                beltIcon.setImageResource(R.drawable.ic_belt_blue);
                break;
            case "3 kup":
                beltIcon.setImageResource(R.drawable.ic_belt_red_tag);
                break;
            case "2 kup":
                beltIcon.setImageResource(R.drawable.ic_belt_red);
                break;
            case "1 kup":
                beltIcon.setImageResource(R.drawable.ic_belt_black_tag);
                break;
            case "I dan":
            case "II dan":
            case "III dan":
            case "IV dan":
            case "V dan":
            case "VI dan":
            case "VII dan":
            case "VIII dan":
            case "IX dan":
                beltIcon.setImageResource(R.drawable.ic_belt_black);
                break;
            default:
                beltIcon.setImageResource(R.drawable.ic_belt_white);
        }
    }
}
