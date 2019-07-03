package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;


public abstract class DatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private FragmentManager supportFragmentManager;
    private DateConverter converter;
    private Model convertDate;
    private View view;
    public DatePicker(FragmentManager supportFragmentManager) {
        this.supportFragmentManager=supportFragmentManager;
    }

    @Override
    public void onClick(View v) {
       show(v);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = String.valueOf(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        getValue(date);
    }

    public void pickDate() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(this);
        dpd.show(supportFragmentManager,"DatePicker");
        dpd.setAccentColor("#D81B60");

        dpd.setTitle("Pick CalenderDate");
    }

    public String getTodayDate(){
        converter = new DateConverter();
        convertDate = converter.getTodayNepaliDate();
        return convertDate.getDay()+"/"+(convertDate.getMonth()+1)+"/"+convertDate.getYear();
    }

    public String convertNepToEng(String s){
        String[] date = s.split("/");
        converter = new DateConverter();
        convertDate = converter.getEnglishDate(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
        return (convertDate.getMonth()+1)+"/"+convertDate.getDay()+"/"+convertDate.getYear();
    }

    public View getView() {
        return view;
    }
    public abstract void show(View v);
    public abstract void getValue(String date);

}
