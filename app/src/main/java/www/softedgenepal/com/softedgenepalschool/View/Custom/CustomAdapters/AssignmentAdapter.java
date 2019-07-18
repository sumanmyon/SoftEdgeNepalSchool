package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.Calender.CalenderDate;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.DateTime;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.HomeWorkActivity;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    HomeWorkActivity context;
    List<AssignmentCache> assignmentCacheList;
    int animationType;
    int size;

    private OnItemClickListener onItemClickListener;

    public AssignmentAdapter(HomeWorkActivity homeWorkActivity, List<AssignmentCache> assignmentCacheList, int animationType) {
        this.context = homeWorkActivity;
        this.assignmentCacheList = assignmentCacheList;
        this.animationType = animationType;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, AssignmentCache cache, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.assignment_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AssignmentCache cache = assignmentCacheList.get(position);

        holder.assignmentSubjectName.setText(cache.SubjectNameEng);
        CalenderDate startDate = null, endDate = null;
        if(!cache.CreateDate.equals("")) {
            String[] startD = cache.CreateDate.split("-");
            startDate = DateTime.convertToNepali(startD);
        }
        if(!cache.Deadline.equals("")) {
            //String[] endD = cache.Deadline.split("-");
            //endDate = DateTime.convertToNepali(endD);
            holder.assignmanetDate.setText("Deadline: "+cache.Deadline);       //"Date: "+startDate+"\n
        }else {
            holder.assignmanetDate.setText("Deadline: ");       //"Date: "+startDate+"\n
        }
        //holder.assignmanetDate.setText("Deadline: "+endDate);       //"Date: "+startDate+"\n

        setAnimation(holder.itemView, position);

        holder.lyt_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener == null) return;
                onItemClickListener.onItemClick(view,cache,position);
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.assignmentHomework.setText(Html.fromHtml(cache.Homework, Html.FROM_HTML_MODE_LEGACY).toString());
        } else {
            holder.assignmentHomework.setText(Html.fromHtml(cache.Homework).toString());
        }

        if(position == size-1){
            holder.assignment_list_vertical_line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        size = assignmentCacheList.size();
        return size;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentSubjectName, assignmanetDate, assignmentHomework;
        View lyt_expand;
        View assignment_list_vertical_line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentSubjectName = itemView.findViewById(R.id.assignment_title);
            assignmentHomework = itemView.findViewById(R.id.assignment_homwWork);
            assignmanetDate = itemView.findViewById(R.id.assignment_date);
            lyt_expand = itemView.findViewById(R.id.bck);
            assignment_list_vertical_line = itemView.findViewById(R.id.assignment_list_vertical_line);
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
