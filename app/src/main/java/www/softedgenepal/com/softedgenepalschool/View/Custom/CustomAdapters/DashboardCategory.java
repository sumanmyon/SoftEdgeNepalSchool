package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.MaterialColor;
import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Resources;
import www.softedgenepal.com.softedgenepalschool.R;

public abstract class DashboardCategory {
    private Activity ctx;
    private View v, dashboardLinearLayout;
    private TextView textView;
    private RecyclerView recyclerView;

    public String userType = "school";
    public String setTopic = "Catergory";
    public int columnSpan = 3;

    public DashboardCategory(Activity ctx, View v, String userType) {
        this.ctx = ctx;
        this.v = v;
        this.userType = userType;
        inflateView();
    }

    public void setDashboard(List<Resources.Dashboard> data){
        int size = data.size();
        RecyclerAdapter adapter = new RecyclerAdapter(ctx, size) {
            private FloatingActionButton button;
            private TextView textView;

            @Override
            public ViewHolder onCreate(ViewGroup viewGroup, int position) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_list_items, viewGroup, false);
                return new ViewHolder(view);
            }

            @Override
            public void inflateUIFields(View itemView) {
                button = itemView.findViewById(R.id.dashboardFloatingActionButton);
                textView = itemView.findViewById(R.id.dashboardTextView);
            }

            @Override
            public void onBind(ViewHolder viewHolder, int position) {
                Resources.Dashboard dashboard = data.get(position);

                int color = MaterialColor.getColor(ctx, position);
                button.setBackgroundTintList(ColorStateList.valueOf(color));
                button.setRippleColor(ctx.getResources().getColor(R.color.grey_60));

                button.setImageDrawable(dashboard.icon);
                textView.setText(dashboard.topic);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener(dashboard.id);
                    }
                });
            }
        };
        adapter.animation_type = ItemAnimation.NONE;
        recyclerView.setAdapter(adapter);
    }

    private void inflateView(){
        View view = inflateLayout();
        textView = view.findViewById(R.id.dashboardTextView);
        recyclerView = view.findViewById(R.id.dashboardRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(ctx, columnSpan));

        dashboardLinearLayout = view.findViewById(R.id.dashboardLinearLayout);
    }

    private View inflateLayout() {
        LinearLayout linearLayout;
        View view;
        linearLayout = v.findViewById(R.id.userProfileDashBoardLinearLayout);
        view = ctx.getLayoutInflater().inflate(R.layout.dashboard_recyclerview,null);
        linearLayout.addView(view);
        return view;
    }

    public void setCategoryText(){
        textView.setText(setTopic);
    }

    public void setCategoryVisibilityGone(){
        dashboardLinearLayout.setVisibility(View.GONE);
    }

    public void setCategoryVisibilityVisible(){
        dashboardLinearLayout.setVisibility(View.VISIBLE);
    }

    public abstract void onClickListener(int id);
}
