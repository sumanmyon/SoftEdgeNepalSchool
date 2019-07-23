package www.softedgenepal.com.softedgenepalschool.Model.Repositroy.FetchFromOnline.LeaveApplication;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;
import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.LeaveApplication.GetAllUserLeaveApplication;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker.DateTimeFormateCheckerType2.DateOrTimeFormate2;

public class ParseShowAllLeaveApplication {
    private GetAllUserLeaveApplication getAllUserLeaveApplication;
    private Context context;
    private List<LeaveApplicationDataCache> leaveApplicationDataCacheList = null;

    public ParseShowAllLeaveApplication(GetAllUserLeaveApplication getAllUserLeaveApplication, Context context) {
        this.getAllUserLeaveApplication=getAllUserLeaveApplication;
        this.context=context;
    }

    public void parse(JSONObject response) {
        try {
            if(response.equals(null)){
                setMessageInTextView("There is not any Leave Application.");
                return;
            }else {
                if (response.getString("Status").equals("true")) {
                    if (response.getString("Response").equals("Success")) {
                        //getAllUserLeaveApplication.setMessage(response.getString("Response"));
                        //todo parse
                        leaveApplicationDataCacheList = new ArrayList<>();

                        JSONArray data = response.getJSONArray("Data");
                        //getAllUserLeaveApplication.setMessage(data.toString());
                        if (data.length() > 0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);

                                String SystemCode = object.getString("SystemCode");
                                String StudentId = object.getString("StudentId");
                                String Subject = object.getString("Subject");
                                String From = object.getString("From");
                                String To = object.getString("To");
                                String Message = object.getString("Message");
                                String CreatedDate = object.getString("CreatedDate");
                                String IsActive = object.getString("IsActive");

                                leaveApplicationDataCacheList.add(new LeaveApplicationDataCache(SystemCode, StudentId, Subject, Message, splitDate(From, "date"), splitDate(To, "date"), splitDate(CreatedDate, "date"), splitDate(CreatedDate, "time"), IsActive));
                            }

                            getAllUserLeaveApplication.setData(leaveApplicationDataCacheList);
                        } else {
                            getProgressBarInVisibility();
                            setMessageInTextView("You have not taken leave yet.");
                        }
                    } else {
                        setMessage(response.getString("Response"));
                        getProgressBarInVisibility();
                    }
                }
            }
        }catch (Exception e){
            setMessage(e.getMessage());
            getProgressBarInVisibility();
        }
    }

    private String splitDate( String date, String type){
        //removing created time from created date
        String convert = DateOrTimeFormate2(date);
        String split[] = convert.split("T");
        String dateOrTime = "";
        if(type.equals("date"))
             dateOrTime = split[0];
        else if(type.equals("time")){
            dateOrTime =split[1];
        }
        return dateOrTime;
    }

    private void setMessage(String message){
        getAllUserLeaveApplication.setMessage(message);
    }
    private void setMessageInTextView(String message){
        getAllUserLeaveApplication.setMessageInTextView(message);
    }
    private void getProgressBarInVisibility(){
        getAllUserLeaveApplication.leaveApplicationPresenter.getProgressBarInVisibility();
    }
}
