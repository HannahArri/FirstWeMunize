package com.example.hannah.wemunize;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParentDetails extends Fragment {

    String fname, lname, oname, sex, dob;
    int hist;
    EditText mname, mlang, faname, flang, mnum, fnum;
    Spinner mrel, frel;
    String spinnercontent[] = {"Christianity", "Islam", "Others"};
    Button next;
    Bundle args;
    Bundle details;
    boolean checked;
    public ParentDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent_details, container, false);

        //collect data passed through bundle
        args = getArguments();
        details = getArguments();
        if(args != null){
            fname = args.getString("Firstname");
            lname = args.getString("Lastname");
            oname = args.getString("Othername");
            sex = args.getString("sex");
            dob = args.getString("Dateofbirth");
            hist = args.getInt("history");
        }


        mname = (EditText) view.findViewById(R.id.registerpri1);
        mrel = (Spinner) view.findViewById(R.id.registerprireligion);
        mlang = (EditText) view.findViewById(R.id.registerpriLang);
        mnum = (EditText) view.findViewById(R.id.registerprinum);
        faname = (EditText) view.findViewById(R.id.registerSecCare1);
        frel = (Spinner) view.findViewById(R.id.registerSecCarereligion1);
        flang = (EditText) view.findViewById(R.id.registerSecCareLang1);
        fnum = (EditText) view.findViewById(R.id.registerSecCareNum1);
        next = (Button) view.findViewById(R.id.parentregisterbutton);

        // set father to become husband if at birth is checked
        if(getArguments().getBoolean("Ifchecked")){
            faname.setHint("Husband's Name");
        }else{
            faname.setHint("Father's Name");
        }
        //set adapter for spinner
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, spinnercontent);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mrel.setAdapter(adapter);
        frel.setAdapter(adapter);

        //set onclick listener on button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        return view;
    }

    //method called on click of button
    public void register(){
        String mothername, motherrel, motherlang, mothernumber, fathername, fatherrel, fatherlang, fathernum;
        if(faname.getText().toString().isEmpty()){
            fathername = "Father's Name";
        }else{
            fathername = faname.getText().toString();
        }

        if(mname.getText().toString().isEmpty()){
            mothername = "Mother's Name";
        }else{
            mothername = mname.getText().toString();
        }

        if(mlang.getText().toString().isEmpty()){
            motherlang = "Mother's Language";
        }else{
            motherlang = mlang.getText().toString();
        }

        if(flang.getText().toString().isEmpty()){
            fatherlang = "Father's Language";
        }else{
            fatherlang = flang.getText().toString();
        }

        if(mnum.getText().toString().isEmpty()){
            mothernumber = "080__________";
        }else{
            mothernumber = mnum.getText().toString();
        }

        if(fnum.getText().toString().isEmpty()){
            fathernum = "080_________";
        }else{
            fathernum = fnum.getText().toString();
        }

        motherrel = mrel.getSelectedItem().toString();
        fatherrel = frel.getSelectedItem().toString();

        //seting values into bundle
        details.putString("Mothername", mothername);
        details.putString("Motherlang", motherlang);
        details.putString("Motherrel", motherrel);
        details.putString("Mothernum", mothernumber);
        details.putString("Fathername",fathername );
        details.putString("Fatherlang", fatherlang);
        details.putString("Fatherrel", fatherrel);
        details.putString("Fathernum", fathernum);

        NextOfKin nextOfKin = new NextOfKin();
        nextOfKin.setArguments(details);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack("Next of Kin")
                .add(android.R.id.content, nextOfKin)
                .hide(ParentDetails.this)
                .commit();
    }

    public void ifchecked(){
        checked = true;
    }
}
