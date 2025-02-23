package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.CalenderDate;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.StoreData.CalenderCache;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Tools;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ViewAnimation;
import www.softedgenepal.com.softedgenepalschool.R;

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CalenderCache> eventList;
    List<String> stringsMonth;
    private int animationType;



    public EventAdapter(Context context, List<CalenderCache> eventList, List<String> stringsMonth, int animationType) {
        this.context = context;
        this.eventList = eventList;
        this.animationType = animationType;
        this.stringsMonth = stringsMonth;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int intType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.event_list, viewGroup, false);
        holder = new DetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final CalenderCache eventCache = eventList.get(position);

        // if(viewHolder instanceof DetailViewHolder) {
        final DetailViewHolder holder = (DetailViewHolder) viewHolder;
        holder.subjectTextView.setText(eventCache.title);
        holder.messageTextView.setText(eventCache.description);

        //todo convert date to nepali
        //split dates
        String[] startD = eventCache.start.split("-");
        CalenderDate startDate = DateTime.convertToNepali(startD);
        String[] endD = eventCache.start.split("-");
        CalenderDate endDate = DateTime.convertToNepali(endD);
        if (startDate.toString().equals(endDate.toString())) {
            holder.dateTextView.setText(startDate + "");
        } else {
            holder.dateTextView.setText(startDate + "\n" + endDate);
        }

        setAnimation(holder.itemView, position);


        holder.bt_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayoutExpand(!eventCache.expand, v, holder.lyt_expand);
                eventList.get(position).expand = show;
            }
        });

        // void recycling view
        if (eventCache.expand) {
            holder.lyt_expand.setVisibility(View.VISIBLE);
        } else {
            holder.lyt_expand.setVisibility(View.GONE);
        }
        Tools.toggleArrow(eventCache.expand, holder.bt_expand, false);
    }

    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        Tools.toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView, dateTextView, messageTextView;
        ImageView bt_expand;
        View lyt_expand;

        DetailViewHolder(View view) {
            super(view);
            subjectTextView = view.findViewById(R.id.event_title);
            dateTextView = view.findViewById(R.id.event_date);
            messageTextView = view.findViewById(R.id.event_message);
            bt_expand = view.findViewById(R.id.bt_expand);
            lyt_expand = view.findViewById(R.id.lyt_expand);
        }
    }

    private int lastPosition = -1;
    private boolean onAttached = true;

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(itemView, onAttached ? position : -1, animationType);
            lastPosition = position;
        }
    }
}
