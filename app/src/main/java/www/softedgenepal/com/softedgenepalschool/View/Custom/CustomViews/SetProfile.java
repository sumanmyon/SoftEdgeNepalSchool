package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomViews;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters.RecyclerAdapter;

public class SetProfile extends UserProfileDetailView {
    private Activity activity;
    private String title;
    private String[] keys;
    private List<String> values;
    public SetProfile(Activity activity, String title, String[] keys, List<String> values) {
        super(activity);
        this.activity = activity;
        this.title = title;
        this.keys = keys;
        this. values = values;
    }

    @Override
    public void method() {
        final RecyclerAdapter adapter = new RecyclerAdapter(activity, keys.length) {
            private TextView keyTextView, valueTextView;

            @Override
            public void inflateUIFields(View itemView) {
                keyTextView = itemView.findViewById(R.id.profile_list_key_TextView);
                valueTextView = itemView.findViewById(R.id.profile_list_value_TextView);
            }

            @Override
            public RecyclerAdapter.ViewHolder onCreate(ViewGroup viewGroup, int position) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                View view = inflater.inflate(R.layout.profile_detail_recycler_textview_, null);
                ViewHolder viewHolder = new ViewHolder(view);
                animation_type =  ItemAnimation.NONE;
                return viewHolder;
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                setText(keyTextView, (keys[position]));
                setText(valueTextView, (values.get(position)));
            }
        };

        TextView titleTextView = getView().findViewById(R.id.profile_recyclerView_title_textView);
        titleTextView.setText(title);

        RecyclerView recyclerView = getView().findViewById(R.id.profile_personal_recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
    }

    private void setText(TextView textView, String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            textView.setText(Objects.toString(s, ""));
        }
    }
}