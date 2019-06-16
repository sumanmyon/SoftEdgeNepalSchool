package www.softedgenepal.com.softedgenepalschool;

import org.junit.Test;

import www.softedgenepal.com.softedgenepalschool.Activities.View.Activities.LeaveApplicationActivity;

import static org.junit.Assert.*;

public class LeaveApplicationActivityTest {

    //todo year
    @Test
    public void comapreDate_FromAndToday_IsSame(){
        String from[] = {"1/3/2075"};
        String to[] = {"1/3/2075"};

        LeaveApplicationActivity activity = new LeaveApplicationActivity();
        String compare = activity.calculateLeaveDaysOrTodayPickedDate(from[0], to[0]);

        assertEquals(compare,"success");
    }

    @Test
    public void comapreDate_FromYearLessThanTodayYear(){
        String from[] = {"1/3/2074"};
        String to[] = {"1/3/2075"};

        LeaveApplicationActivity activity = new LeaveApplicationActivity();
        String compare = activity.calculateLeaveDaysOrTodayPickedDate(from[0], to[0]);

        assertEquals(compare,"success");
    }

    //todo month
    @Test
    public void comapreDate_FromYearEqualTodayYear(){
        String from[] = {"1/3/2075"};
        String to[] = {"1/3/2075"};

        LeaveApplicationActivity activity = new LeaveApplicationActivity();
        String compare = activity.calculateLeaveDaysOrTodayPickedDate(from[0], to[0]);

        assertEquals(compare,"success");
    }

    @Test
    public void comapreDate_FromMonthLessThanTodayMonth(){
        String from[] = {"1/3/2074"};
        String to[] = {"1/4/2075"};

        LeaveApplicationActivity activity = new LeaveApplicationActivity();
        String compare = activity.calculateLeaveDaysOrTodayPickedDate(from[0], to[0]);

        assertEquals(compare,"success");
    }


    //todo day
    @Test
    public void comapreDate_FromMonthEqualToTodayMonth(){
        String from[] = {"1/3/2074"};
        String to[] = {"1/3/2075"};

        LeaveApplicationActivity activity = new LeaveApplicationActivity();
        String compare = activity.calculateLeaveDaysOrTodayPickedDate(from[0], to[0]);

        assertEquals(compare,"success");
    }

    @Test
    public void comapreDate_FromDayLessThanTodayDay(){
        String from[] = {"1/3/2074"};
        String to[] = {"2/4/2075"};

        LeaveApplicationActivity activity = new LeaveApplicationActivity();
        String compare = activity.calculateLeaveDaysOrTodayPickedDate(from[0], to[0]);

        assertEquals(compare,"success");
    }

    @Test
    public void comapreDate_FromDayEqualToTodayDay(){
        String from[] = {"1/3/2074"};
        String to[] = {"1/4/2075"};

        LeaveApplicationActivity activity = new LeaveApplicationActivity();
        String compare = activity.calculateLeaveDaysOrTodayPickedDate(from[0], to[0]);

        assertEquals(compare,"success");
    }


    //todo random date and year
    @Test
    public void comapreDate_FromToRandom(){
        String from[] = {"30/3/2074"};
        String to[] = {"31/3/2074"};

        LeaveApplicationActivity activity = new LeaveApplicationActivity();
        String compare = activity.calculateLeaveDaysOrTodayPickedDate(from[0], to[0]);

        assertEquals(compare,"success");
    }
}