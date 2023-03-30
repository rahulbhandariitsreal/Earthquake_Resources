package com.example.equakeacitvity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.equakeacitvity.R;
import com.example.equakeacitvity.model.Earthquake;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    public static final String LOCATION_OFFSETTER=" of ";



    public EarthquakeAdapter(@NonNull Context context, ArrayList<Earthquake> quakes, int resource) {
        super(context, 0, quakes);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Earthquake earthquake=getItem(position);

        View view=convertView;
        if(view == null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.quakelist,parent,false);
        }
        TextView place,magnitude,primary_location;

        primary_location=view.findViewById(R.id.primary_location);
        place=view.findViewById(R.id.Place_text);
        magnitude=view.findViewById(R.id.magnitude);

        String location=earthquake.getPlace();

        String startLOC;
        String endLOC;


        if(location.contains(LOCATION_OFFSETTER)){

         String [] parts =location.split(LOCATION_OFFSETTER);
         startLOC=parts[0] + LOCATION_OFFSETTER;
         endLOC=parts[1] ;

        }else{
            startLOC ="Near of ";
            endLOC=location;
        }

place.setText(startLOC);
        primary_location.setText(endLOC);
        String formatted_magnitude=formate_mag(earthquake.getMagnitude());
magnitude.setText( formatted_magnitude);

//set the proper backgrounf color on the magnitude circle
        //fetch the background from the text view

        GradientDrawable magnitudeCircle=(GradientDrawable) magnitude.getBackground();

        //get the appropirate background color based on the current earthquk=ake magnitude

        int magnitudecolor=getMagnitudecolor(earthquake.getMagnitude());

        //set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudecolor);

TextView dateview,timeview;
long timeinmillies=earthquake.getTimeinmiliseconds();
Date dateObject=new Date(timeinmillies);

dateview=view.findViewById(R.id.date_text);
String formated_date=format_date(dateObject);
dateview.setText(formated_date);

timeview=view.findViewById(R.id.time_text);
String formated_time=format_time(dateObject);
timeview.setText(formated_time);

        return view;
    }

    private int getMagnitudecolor(double mag){
        int magnitudecolorResourceID;
        //Math.floor retures the value ex 6.7=6
        int magnitudeFlor=(int) Math.floor(mag);

        //switch cant accept double values

        switch (magnitudeFlor){
            case 0:
            case 1:
                //it only gives the address of color1 not the value
                magnitudecolorResourceID=R.color.magnitude1;
                break;

            case 2:
                magnitudecolorResourceID=R.color.magnitude2;
                break;

            case 3:
                magnitudecolorResourceID=R.color.magnitude3;
                break;

            case 4:
                magnitudecolorResourceID=R.color.magnitude4;
                break;

            case 5:
                magnitudecolorResourceID=R.color.magnitude5;
                break;

            case 6:
                magnitudecolorResourceID=R.color.magnitude6;
                break;

            case 7:
                magnitudecolorResourceID=R.color.magnitude7;
                break;

            case 8:
                magnitudecolorResourceID=R.color.magnitude8;
                break;

            case 9:
                magnitudecolorResourceID=R.color.magnitude9;
                break;

            default:
                magnitudecolorResourceID=R.color.magnitude10plus;

        }
        //it retures to return the color resource id or value  from the color folder by using the addrsss

        return ContextCompat.getColor(getContext(),magnitudecolorResourceID);
    }

    private String formate_mag(double mag){
        DecimalFormat decimalFormat=new DecimalFormat("0.0");
        return  decimalFormat.format(mag);
    }

    private String format_date(Date date){
        SimpleDateFormat dateformat=new SimpleDateFormat("LLL dd,yyyy");
        return dateformat.format(date);
    }
    private String format_time(Date date){
        SimpleDateFormat timeformate=new SimpleDateFormat("h:mm a");
        return timeformate.format(date);
    }
}
