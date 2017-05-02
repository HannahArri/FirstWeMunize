package com.example.hannah.wemunize;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildDetails extends Fragment {
    EditText fname, lname, oname, dob, palbum;
    Spinner sex, immhist;
    DatePickerDialog datePickerDialog;
    String sexcontent [] = {"Male", "Female", "14 years"};
    String immhistcontent [] = {"0", "1", "2", "3", "4"};
    Button next;
    CheckBox checkBox;
    Bundle details;
    public ChildDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // instantiate bundle
        details = new Bundle();

        fname = (EditText) view.findViewById(R.id.registerFirstName);
        lname =  (EditText) view.findViewById(R.id.registerLastName);
        oname = (EditText) view.findViewById(R.id.registerOtherName);
        dob = (EditText) view.findViewById(R.id.registerdate);
        sex = (Spinner) view.findViewById(R.id.registergender);
        immhist = (Spinner) view.findViewById(R.id.registerhistory);
        next = (Button) view.findViewById(R.id.nextbutton);
        checkBox = (CheckBox) view.findViewById(R.id.atbirth);
        palbum = (EditText) view.findViewById(R.id.registerphotonum1);

        //set onclick listener for checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dob.setHint("Expected Delivery Date");
                    details.putBoolean("Ifchecked", true );
                }
                if(!isChecked){
                    dob.setHint("dd/mm/yyyy");
                    details.putBoolean("Ifchecked", false);
                }
            }
        });

        //set on clicl listener on button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New();
            }
        });

        //set adapters for spinners
        ArrayAdapter sexadapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, sexcontent);
        sexadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(sexadapter);
        ArrayAdapter histadapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, immhistcontent);
        histadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        immhist.setAdapter(histadapter);

        //set onclick listener on to display date picker
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear  = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                // date Picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dob.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        return view;
    }

    //function called when button is clicked
    public void New(){
        String firstname, lastname, othername, dateofbirth, childsex, photoalbum;
        int hist;
        if(fname.getText().toString().isEmpty()){
            firstname = "Firstname";
        }else{
            firstname = fname.getText().toString();
        }
        if(lname.getText().toString().isEmpty()){
            lastname = "LastName";
        }else{
            lastname = lname.getText().toString();
        }

        if(oname.getText().toString().isEmpty()){
            othername = "Othername";
        }else{
            othername = oname.getText().toString();
        }
        childsex = sex.getSelectedItem().toString();
        hist = Integer.parseInt(immhist.getSelectedItem().toString());
        if(dob.getText().toString().isEmpty()){
            dateofbirth = "DD/MM/YYYY";
        }else{
            dateofbirth = dob.getText().toString();
        }
        photoalbum = palbum.getText().toString();

        details.putString("Firstname", firstname);
        details.putString("Lastname", lastname);
        details.putString("Othername", othername);
        details.putString("sex", childsex);
        details.putString("Dateofbirth", dateofbirth);
        details.putInt("history", hist);
        details.putString("PhotoAlbumNum1", photoalbum);

        ParentDetails parentDetails = new ParentDetails();
        parentDetails.setArguments(details);

        FragmentTransaction manager = getFragmentManager().beginTransaction();
        manager.addToBackStack("Child")
                .hide(ChildDetails.this)
                .add(android.R.id.content, parentDetails)
                .commit();

    }
}
