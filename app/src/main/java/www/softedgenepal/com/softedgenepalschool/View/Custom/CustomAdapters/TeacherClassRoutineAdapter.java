package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.Teacher.TeacherClassRoutineModel;
import www.softedgenepal.com.softedgenepalschool.R;

public class TeacherClassRoutineAdapter extends RecyclerView.Adapter<TeacherClassRoutineAdapter.ViewHolder> {
    private Context context;
    private List<TeacherClassRoutineModel> classRoutineModel;

    public TeacherClassRoutineAdapter(Context context, List<TeacherClassRoutineModel> classRoutineModel) {
        this.context = context;
        this.classRoutineModel = classRoutineModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacher_classroutine_recyclerview_body, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TeacherClassRoutineModel model = classRoutineModel.get(i);

        setText(viewHolder.SN, String.valueOf(i + 1)+". ");
        setText(viewHolder.Faculty, model.FacultyName);
        setText(viewHolder.Subject, model.SubjectNameEng);
        setText(viewHolder.Class, model.ClassName + ", "+model.Section);
        setText(viewHolder.Time, model.StartTime+" - "+model.EndTime);
        setText(viewHolder.Shift, model.ShiftName);
        setText(viewHolder.Day, model.EnglishDay);
    }

    @Override
    public int getItemCount() {
        return classRoutineModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView SN, Faculty, Subject, Class, Time,Shift, Day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SN = itemView.findViewById(R.id.recyclerClassRoutineSN);
            Faculty = itemView.findViewById(R.id.recyclerClassRoutineFaculty);
            Subject = itemView.findViewById(R.id.recyclerClassRoutineSubject);
            Class = itemView.findViewById(R.id.recyclerClassRoutineClass);
            Time = itemView.findViewById(R.id.recyclerClassRoutineTime);
            Shift = itemView.findViewById(R.id.recyclerClassRoutineShift);
            Day = itemView.findViewById(R.id.recyclerClassRoutineDay);
        }
    }

    private void setText(TextView textView, String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            textView.setText(Objects.toString(s, ""));
        }
    }
}
