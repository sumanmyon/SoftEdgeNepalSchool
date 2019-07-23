package www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

import static www.softedgenepal.com.softedgenepalschool.AppCustomPackages.DatePickerAndCalender.DateTimeFormaterChecker.DateTimeFormateCheckerType2.DateOrTimeFormate2;


/**
 *  ruling plant bujuli bazzar 11 sarp
 *
 * Adapter to set contents of ic_calendar_white grid view.
 */
public class CalendarAdapter extends BaseAdapter {
    private CalenderDate mDate;
    private CalenderDate mToday;
    private int mExtraDays = 0;
    private final Context mContext;
    List<CalenderCache> cacheList;
    List<CalenderCache> calenderCaches;
    RecyclerView recyclerView;
    /**
     * Create an adapter with given context and date.
     * @param context Context containing the grid view.
     * @param date CalenderDate containing year and month to display.
     * @param cacheList
     * @param recyclerView
     */
    public CalendarAdapter(Context context, CalenderDate date, List<CalenderCache> cacheList, RecyclerView recyclerView) {
        mContext = context;
        this.cacheList = cacheList;
        calenderCaches = new ArrayList<>();
        this.recyclerView = recyclerView;
        changeDate(date);
    }

    /**
     * Change ic_calendar_white to another month.
     * @param date CalenderDate containing year and month to display.
     */
    public void changeDate(CalenderDate date) {
        mDate = date;

        CalenderDate temp = new CalenderDate(mDate.year, mDate.month, 1);
        Log.d("TAG_CALENDER", temp.toString());

        Calendar engCalendar = temp.convertToEnglish().getCalendar();
        mExtraDays = engCalendar.get(Calendar.DAY_OF_WEEK)-1;
        notifyDataSetInvalidated();

        mToday = new CalenderDate(Calendar.getInstance()).convertToNepali();
    }

    @Override
    public int getCount() {
        return DateUtils.getNumDays(mDate.year, mDate.month)
                + mExtraDays;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView nepDate;
        TextView engDate;
        ImageView imageView;
        ImageView circleImageView;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_date, parent, false);
            holder = new ViewHolder();

            holder.nepDate = (TextView)convertView.findViewById(R.id.nepaliDate);
            holder.engDate = (TextView)convertView.findViewById(R.id.englishDate);
            holder.imageView = (ImageView)convertView.findViewById(R.id.circle_back);
            holder.circleImageView = (ImageView) convertView.findViewById(R.id.relative_layout);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder)convertView.getTag();

        TextView nepDate = holder.nepDate;
        TextView engDate = holder.engDate;
        ImageView imageView = holder.imageView;
        ImageView circleImage = holder.circleImageView;
        circleImage.setVisibility(View.INVISIBLE);

        // Set text

        // Days
        if (position >= mExtraDays) {
            int dt = (position + 1 - mExtraDays);
            nepDate.setText(NepaliTranslator.getNumber(dt + ""));

            CalenderDate nepaliDate = new CalenderDate(mDate.year, mDate.month, dt);
            String dt2 = nepaliDate.convertToEnglish().day + "";
            engDate.setText(dt2);
            engDate.setVisibility(View.VISIBLE);

           // show(nepaliDate+"\t\t"+nepaliDate.convertToEnglish());
            // todo set data here
            if(cacheList != null && cacheList.size() > 0) {
                for (CalenderCache cache : cacheList) {
                    String strDate = cache.start;
                    String startCalDate = nepaliDate.convertToEnglish().toString();
                    String endDate = splitDate(cache.end,"date");
                    String[] spliteEndDate = endDate.split("-");
                    String endCalDate = nepaliDate.convertToEnglish().toString();

                    if (strDate.equalsIgnoreCase(startCalDate)) {
                        show(strDate + "\t" + startCalDate + "\t" + endDate+"\t"+endCalDate);
                        //todo set background color
                        //circleImage.setBackgroundColor(Color.parseColor(cache.backgroundColor));
                        circleImage.setVisibility(View.VISIBLE);
                        circleImage.setColorFilter(Color.parseColor(cache.backgroundColor));

                        String startDay = String.valueOf(nepaliDate.day);  //todo setting nepali date - day
                        String endDay = new CalenderDate(
                                Integer.valueOf(spliteEndDate[0]),
                                Integer.valueOf(spliteEndDate[1]),
                                Integer.valueOf(spliteEndDate[2]))
                                .convertToNepali().day+"";
                        calenderCaches.add(new CalenderCache(
                                cache.title, cache.description,
                                startDay, endDay, cache.type,
                                cache.backgroundColor, cache.isActive));
                    }
                }
            }
        }
        else {
            nepDate.setText("");
            engDate.setVisibility(View.GONE);
        }

        // Set background and colors
        if (position % 7 == 6) {
            nepDate.setTextColor(convertView.getResources().getColor(R.color.tertiaryTextAlternate));
            engDate.setTextColor(convertView.getResources().getColor(R.color.tertiaryTextAlternate));
        }
        else {
            nepDate.setTextColor(convertView.getResources().getColor(R.color.primaryTextAlternate));
            engDate.setTextColor(convertView.getResources().getColor(R.color.secondaryTextAlternate));
        }

        //imageView.setColorFilter(ThemeUtils.getThemeColor(mContext, R.attr.colorPrimary));
        imageView.setColorFilter(convertView.getResources().getColor(R.color.colorPrimaryLight));
        //todo Today
        if (mDate.year == mToday.year && mDate.month == mToday.month
                && position == mToday.day-1+mExtraDays) {
            imageView.setVisibility(View.VISIBLE);
//            nepDate.setTextColor(convertView.getResources().getColor(R.color.colorPrimaryDark));
//            engDate.setTextColor(convertView.getResources().getColor(R.color.colorPrimary));
        }else {
            imageView.setVisibility(View.GONE);
        }

        // Set view height equal to width and text size respectively

        final View finalRootView = convertView;
        finalRootView.post(new Runnable() {
            @Override
            public void run() {
                // Set height == width
                ViewGroup.LayoutParams params = finalRootView.getLayoutParams();
                params.height = finalRootView.getWidth()+2;

                finalRootView.setLayoutParams(params);
                finalRootView.invalidate();
            }
        });

        recyclerViewUpdate();

        return convertView;
    }

    private void recyclerViewUpdate() {
        if(calenderCaches.size()>0) {
            recyclerView.setVisibility(View.VISIBLE);

            RecyclerAdapter adapter = new RecyclerAdapter(mContext, calenderCaches.size()) {
                TextView title, message, date;
                CircleImageView imageView;

                @Override
                public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                    View view = inflater.inflate(R.layout.calender_list, null);
                    return new ViewHolder(view);
                }

                @Override
                public void inflateUIFields(View itemView) {
                    title = (TextView) itemView.findViewById(R.id.title);
                    message = (TextView) itemView.findViewById(R.id.message);
                    date = (TextView) itemView.findViewById(R.id.date);
                    imageView = (CircleImageView) itemView.findViewById(R.id.imageView);
                }

                @Override
                public void onBind(ViewHolder viewHolder, int position) {
                    CalenderCache cache = calenderCaches.get(position);
                    title.setText(cache.title);
                    message.setText(cache.description);
                    date.setText(cache.start);
                    imageView.setColorFilter(Color.parseColor(cache.backgroundColor));

                    // todo work here long holidays for eg background_img days.....to a month
                }
            };

            recyclerView.setHasFixedSize(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            //recyclerView.setNestedScrollingEnabled(false);                    //todo here
            ViewCompat.setNestedScrollingEnabled(recyclerView, false);
            recyclerView.setLayoutFrozen(true);

            recyclerView.setAdapter(adapter);

        }else {
            recyclerView.setVisibility(View.INVISIBLE);
            recyclerView.setHasFixedSize(false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    private String splitDate(String date, String type){
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


    //todo show data here
    public void show( String m){
        Log.d("TAG_CALENDER2","date "+m);
    }
}
