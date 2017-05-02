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
public class NextOfKin extends Fragment {

    Bundle args;
    EditText name, number, lang, relationship;
    Spinner rel, sex;
    String relcontent[] = {"Christianity", "Islam", "Others"};
    String sexcontent[] = {"Male", "Female"};
    Button next;
    public NextOfKin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_next_of_kin, container, false);

        //retirieve bundle;
        args = getArguments();

        name = (EditText) view.findViewById(R.id.registerSecCare2);
        number = (EditText) view.findViewById(R.id.registerSecCareNum2);
        lang = (EditText) view.findViewById(R.id.registerSecCareLang2);
        rel = (Spinner) view.findViewById(R.id.registerSecCarereligion2);
        sex = (Spinner) view.findViewById(R.id.registernextkinsex);
        next = (Button) view.findViewById(R.id.nextregisterbutton);
        relationship = (EditText) view.findViewById(R.id.registerSecCarerelationship);

        //set adapters for spinners
        ArrayAdapter reladapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, relcontent);
        reladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter sexaddapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, sexcontent);
        sexaddapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rel.setAdapter(reladapter);
        sex.setAdapter(sexaddapter);

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
        String nextname, nextnumber, nextrel, nextlang, nextsex, nextrelation;

        if(name.getText().toString().isEmpty()){
            nextname = "Next of Kin Name";
        }else{
            nextname = name.getText().toString();
        }

        if(number.getText().toString().isEmpty()){
            nextnumber = "Next of Kin number";
        }else{
            nextnumber = number.getText().toString();
        }

        if(lang.getText().toString().isEmpty()){
            nextlang = "Next of kin Language";
        }else{
            nextlang = lang.getText().toString();
        }

        if(relationship.getText().toString().isEmpty()){
            nextrelation = "Relationship to child";
        }else{
            nextrelation = relationship.getText().toString();
        }

        nextrel = rel.getSelectedItem().toString();
        nextsex = sex.getSelectedItem().toString();

        args.putString("Nextname", nextname);
        args.putString("Nextnumber", nextnumber);
        args.putString("Nextlang", nextlang);
        args.putString("Nextsex", nextsex);
        args.putString("Nextrel", nextrel);
        args.putString("Nextrelationship", nextrelation);

        AddressRfid addressRfid = new AddressRfid();
        addressRfid.setArguments(args);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack("Address")
                .add(android.R.id.content, addressRfid)
                .hide(NextOfKin.this)
                .commit();
    }
}
