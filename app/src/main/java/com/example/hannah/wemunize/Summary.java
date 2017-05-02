package com.example.hannah.wemunize;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Summary extends Fragment {
    Bundle args;
    TextView childname, childsex, childdob, childhist, mothername, motherlang, motherrel, mothernum,
        fathername, fatherlang, fatherrel, fathernum, nextname, nextlang, nextrel, nextsex, nextnum,
        address, rfid;
    Button save;
    public Summary() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        args = getArguments();
        save = (Button) view.findViewById(R.id.savebutton);
        childname = (TextView) view.findViewById(R.id.child_name);
        childsex = (TextView) view.findViewById(R.id.child_sex);
        childdob = (TextView) view.findViewById(R.id.child_age);
        childhist = (TextView) view.findViewById(R.id.child_history);
        mothername = (TextView) view.findViewById(R.id.child_mothername);
        motherlang = (TextView) view.findViewById(R.id.child_motherlang);
        motherrel = (TextView) view.findViewById(R.id.child_motherrel);
        mothernum = (TextView) view.findViewById(R.id.child_mothernum);
        fathername = (TextView) view.findViewById(R.id.child_fathername);
        fatherlang = (TextView) view.findViewById(R.id.child_fatherlang);
        fatherrel = (TextView) view.findViewById(R.id.child_fatherrel);
        fathernum = (TextView) view.findViewById(R.id.child_fathernum);
        nextname = (TextView) view.findViewById(R.id.child_nextname);
        nextlang = (TextView) view.findViewById(R.id.child_nextlang);
        nextrel = (TextView) view.findViewById(R.id.child_nextrel);
        nextsex = (TextView) view.findViewById(R.id.child_nextsex);
        nextnum = (TextView) view.findViewById(R.id.child_nextnum);
        address = (TextView) view.findViewById(R.id.child_address);
        rfid = (TextView) view.findViewById(R.id.child_rfid);
        populate();
        return view;
    }

    //method to populate texviews with data
    public void populate(){
        childname.setText(args.getString("Firstname") + " " + args.getString("Othername") + " " + args.getString("Lastname"));
        childsex.setText(args.getString("sex"));
        childdob.setText(args.getString("Dateofbirth"));
        childhist.setText(Integer.toString(args.getInt("history")));
        mothername.setText(args.getString("Mothername"));
        motherlang.setText(args.getString("Motherlang"));
        motherrel.setText(args.getString("Motherrel"));
        mothernum.setText(args.getString("Mothernum"));
        fathername.setText(args.getString("Fathername"));
        fatherrel.setText(args.getString("Fatherrel"));
        fatherlang.setText(args.getString("Fatherlang"));
        fathernum.setText(args.getString("Fathernum"));
        nextsex.setText(args.getString("Nextname"));
        nextlang.setText(args.getString("Nextlang"));
        nextsex.setText(args.getString("Nextsex"));
        nextrel.setText(args.getString("Nextrel"));
        nextnum.setText(args.getString("Nextnumber"));
        address.setText(args.getString("Address"));
        rfid.setText(args.getString("RFID"));
    }


}
