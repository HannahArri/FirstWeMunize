package com.example.hannah.wemunize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by hannah on 3/9/17.
 */

public class missingadapter extends ArrayAdapter<ParseObject> {
    private ArrayList<ParseObject> list;
    private Context mcontext;
    public missingadapter(Context context, int textViewResourceID, ArrayList<ParseObject> list){
        super(context, textViewResourceID, list);
        this.list = list;
        mcontext = context;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater vi = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_view_layout_single, null);
        }
       ParseObject model = list.get(position);
        if(model != null){
            TextView name = (TextView) v.findViewById(R.id.childname);
            TextView carename = (TextView) v.findViewById(R.id.carename);
            TextView number1 = (TextView) v.findViewById(R.id.phonenum1);
            TextView number2 = (TextView) v.findViewById(R.id.phonenum2);
            String fname = model.getString("FirstName");
            String lname = model.getString("LastName");
            String mname = model.getString("OtherName");
            String childname = fname +" "+ mname +" "+lname;
            String caregiver = model.getString("PriCareGiver1");
            String pnum1 = model.getString("PhoneNum2");
            String pnum2 = model.getString("PhoneNum3");
            name.setText(childname);
            carename.setText(caregiver);
            number1.setText(pnum1);
            number2.setText(pnum2);
        }
        return v;
    }
}
