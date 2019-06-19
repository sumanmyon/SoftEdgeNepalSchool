package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender;

public class DateCompare {
    public String ComapareStartDateAndEndDate(String start, String end){
        String from = start;
        String to = end;

        //getting day, month and years from
        int fDay, fMonth, fYear;
        int tDay, tMonth, tYear;

        String[] f = from.split("/");
        fDay = Integer.parseInt(f[0]);
        fMonth = Integer.parseInt(f[1]);
        fYear = Integer.parseInt(f[2]);

        String[] t = to.split("/");
        tDay = Integer.parseInt(t[0]);
        tMonth = Integer.parseInt(t[1]);
        tYear = Integer.parseInt(t[2]);

        //todo calculate date for from and to dates.....
        //todo make sure user wont select past date in from
        if(from.equals(to)){
            return "success";
        }else{
            if(tYear-fYear>=0){
                if(tMonth-fMonth>=0){
                    int differenceInYear = (tMonth - fMonth) * 30;
                    if(tDay+differenceInYear-fDay>=0){
                        return "success";
                    }
                }
            }
        }
        return "failed";
    }

    //todo Calculate Total Leave Days
    private void calculateTotalLeaveDays(){

    }
}
