package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.CalenderDate;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Tools;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ViewAnimation;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.AssignmentActivity;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    AssignmentActivity context;
    List<AssignmentCache> assignmentCacheList;
    int animationType;

    public AssignmentAdapter(AssignmentActivity assignmentActivity, List<AssignmentCache> assignmentCacheList, int animationType) {
        this.context = assignmentActivity;
        this.assignmentCacheList = assignmentCacheList;
        this.animationType = animationType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.assignment_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final AssignmentCache cache = assignmentCacheList.get(position);

        holder.assignmentSubjectName.setText(cache.SubjectNameEng);

        String[] startD = cache.CreateDate.split("-");
        CalenderDate startDate = DateTime.convertToNepali(startD);
        String[] endD = cache.Deadline.split("-");
        CalenderDate endDate = DateTime.convertToNepali(endD);
        holder.assignmanetDate.setText("Date: "+startDate+"\nDeadline: "+endDate);

        //todo show in web view
        String homewrok = cache.Homework;
        if(!homewrok.equals("") || !homewrok.equals("null")) {
            holder.homeWorkWebView.loadData(cache.Homework, "text/html", null);
        }

        setAnimation(holder.itemView, position);

        holder.bt_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean show = toggleLayoutExpand(!cache.expand, v, holder.lyt_expand);
                //cache.expand = show;
            }
        });

//        // void recycling view
//        if (cache.expand) {
//            holder.lyt_expand.setVisibility(View.VISIBLE);
//        } else {
//            holder.lyt_expand.setVisibility(View.GONE);
//        }
//        Tools.toggleArrow(cache.expand, holder.bt_expand, false);
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
        return assignmentCacheList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentSubjectName, assignmanetDate;
        WebView homeWorkWebView;
        ImageView bt_expand;
        View lyt_expand;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentSubjectName = itemView.findViewById(R.id.assignment_title);
            assignmanetDate = itemView.findViewById(R.id.assignment_date);
            homeWorkWebView = itemView.findViewById(R.id.assignment_homeWorkWebView);

            bt_expand = itemView.findViewById(R.id.assignment_expand);
            bt_expand.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
            lyt_expand = itemView.findViewById(R.id.lyt_expand);
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
