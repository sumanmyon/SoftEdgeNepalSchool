package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.TeacherClassRoutineModel2;
import www.softedgenepal.com.softedgenepalschool.R;

public class TeacherClassRoutineDaysAdapter extends RecyclerView.Adapter<TeacherClassRoutineDaysAdapter.ViewHolder> {
    private Activity activity;
    private TeacherClassRoutineModel2 model;
    private int size;
    private int SpanCount;

    public TeacherClassRoutineDaysAdapter(Activity activity, TeacherClassRoutineModel2 model) {
        this.activity = activity;
        this.model = model;
        size = model.Detail.size() + 1;
        SpanCount =  model.Detail.get(0).Detail.size() + 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int row) {
        TeacherClassRoutineAdapter2 adapter = new TeacherClassRoutineAdapter2(activity, model, row, size, SpanCount);
        viewHolder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView_list);
            recyclerView.setLayoutManager(new GridLayoutManager(activity, SpanCount));
            recyclerView.setHasFixedSize(true);
            ViewCompat.setNestedScrollingEnabled(recyclerView, false);
            recyclerView.setLayoutFrozen(true);
        }
    }
}
