package com.example.yashashvi.emergency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class crimeRate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    InputStream inputStream;

    String[] ids;
    TextView tv;
    String selected_state;
    String selected_district;
    String selected_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_rate);

        tv = (TextView)findViewById(R.id.state);
        inputStream = getResources().openRawResource(R.raw.crimes);

        int count = 0;
        String prev_s = "";
        String s = "";
        ArrayList state = new ArrayList();
        ArrayList district = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {


                if(count == 0)
                {
                    count++;
                    continue;
                }

                //if(count == 5)
                //{
                //break;
                //}
                //count++;
                ids=csvLine.split(",");
                try{

                    if(ids[1].equals("Total"))
                        continue;

                    //if(ids[0].equals("Andhra Pradesh"))
                    //district.add(ids[1]);

                    //if(prev_s.equals(ids[0]))
                    //{
                    //district.add(ids[1]);
                    //prev_s = ids[0];
                    //continue;

                    district.add(ids[1]);
                    //}
                    //else {
                    //state.add(ids[0]);
                    //s += ids[0] + " ";
                    //prev_s = ids[0];
                    //district.add(ids[1]);
                    //}

                    //Log.e("Collumn 1 ",""+ids[0]);



                }catch (Exception e){
                    Log.e("Unknown fuck",e.toString());
                }
            }

            Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
            spinner1.setOnItemSelectedListener(this);
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, district);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(dataAdapter1);

            //Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            //spinner2.setOnItemSelectedListener(this);
            //ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, district);
            //dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //spinner2.setAdapter(dataAdapter2);













        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }


        tv.setText(s);



    }



   /*public void populate_district(String selected_state) throws IOException
    {
    inputStream = getResources().openRawResource(R.raw.sample);
        int match = 0;
        int count = 0;
        String prev_s = "";
        String s = "";
        //tv.setText("State selected. Inside Function.");

        ArrayList district = new ArrayList();
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader1.readLine()) != null) {




                if(count == 0)
                {
                    count++;
                    continue;
                }

                /*if(count == 5)
                {
                    break;
                }
                count++;
                ids=csvLine.split(",");
                try{

                    if(selected_state.equals(ids[0]))
                    {
                        match++;
                        district.add(ids[1]);
                        s += ids[1] + " ";
                        //prev_s = ids[0];
                        //continue;
                    }
                    else {
                        //district.add(ids[1]);
                        //s += ids[0] + " ";
                        //prev_s = ids[0];
                        continue;
                    }

                    //Log.e("Collumn 1 ",""+ids[0]);



                }catch (Exception e){
                    Log.e("Unknown fuck",e.toString());
                }
            }

            Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            //spinner2.setOnItemSelectedListener(this);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, district);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(dataAdapter);


        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }


        //tv.setText(s);

    }*/

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {




        selected_item = adapterView.getItemAtPosition(i).toString();

        show_data();


        /*Spinner spinner = (Spinner)adapterView;

        if(spinner.getId() == R.id.spinner1)
        {
            selected_state = selected_item;
            try
            {
                //populate_district(selected_state);
            }
            catch(Exception e)
            {}
        }

        if(spinner.getId() == R.id.spinner2)
        {
            selected_district = selected_item;
            show_data();
        }*/



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        selected_state = "Andhra Pradesh";
        return;

    }



    public void show_data()
    {
        //murder : 3
        //attempt to murder :4
        //rape : 7
        //attempt to rape :14
        //kidnapping and abduction : 15
        //Robbery : 25

        inputStream = getResources().openRawResource(R.raw.crimes);
        String murder = "For the year 2016 : \nMurder : ";
        String rape = "\nRape : ";
        String kidnap = "\nKidnapping and Abduction : ";
        String robbery = "\nRobbery : ";

        String s = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {



                ids=csvLine.split(",");
                try{

                    //Log.e("Collumn 1 ",""+ids[0]) ;

                    if(ids[1].equals(selected_item))
                    {
                        //murder : 3
                        //attempt to murder :4
                        //rape : 7
                        //attempt to rape :14
                        //kidnapping and abduction : 15
                        //Robbery : 25
                        murder+=ids[3];
                        rape+=ids[7];
                        kidnap+=ids[15];
                        robbery+=ids[25];
                        s  = murder + rape + kidnap + robbery;
                    }



                }catch (Exception e){
                    Log.e("Unknown fuck",e.toString());
                }
            }




        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }



        tv.setText(s);



    }
}





/*
import java.util.ArrayList;
        import java.util.List;

        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Spinner;
        import android.widget.AdapterView.OnItemSelectedListener;
        import android.widget.Toast;

public class MainActivity extends Activity implements
        OnItemSelectedListener{
    Spinner s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = (Spinner)findViewById(R.id.spinner1);
        s2 = (Spinner)findViewById(R.id.spinner2);
        s1.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        // TODO Auto-generated method stub
        String sp1= String.valueOf(s1.getSelectedItem());
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if(sp1.contentEquals("Income")) {
            List<String> list = new ArrayList<String>();
            list.add("Salary");
            list.add("Sales");
            list.add("Others");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            s2.setAdapter(dataAdapter);
        }
        if(sp1.contentEquals("Expense")) {
            List<String> list = new ArrayList<String>();
            list.add("Conveyance");
            list.add("Breakfast");
            list.add("Purchase");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            s2.setAdapter(dataAdapter2);
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
*/