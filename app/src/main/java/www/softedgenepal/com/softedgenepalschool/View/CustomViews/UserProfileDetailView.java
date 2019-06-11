package www.softedgenepal.com.softedgenepalschool.View.CustomViews;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import www.softedgenepal.com.softedgenepalschool.R;

public abstract class UserProfileDetailView {
    private Activity activity;
    private LinearLayout linearLayout;
    private View view;
    public UserProfileDetailView(Activity activity) {
        this.activity=activity;
        inflateLayout();
    }

    private void inflateLayout() {
        linearLayout = activity.findViewById(R.id.profile_detail_linearLayout);
//        ViewGroup.LayoutParams params =  linearLayout.getLayoutParams();
//        params.width =  LinearLayout.LayoutParams.MATCH_PARENT;
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setLayoutParams(params);
        view = activity.getLayoutInflater().inflate(R.layout.profile_detail_recyclerlist,null);
        linearLayout.addView(view);
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public View getView() {
        return view;
    }

    abstract void method();

    public void start() {
        method();
    }
}
