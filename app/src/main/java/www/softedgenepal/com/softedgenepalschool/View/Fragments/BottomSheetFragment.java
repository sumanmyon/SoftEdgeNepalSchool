package www.softedgenepal.com.softedgenepalschool.View.Fragments;


import android.app.Dialog;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.Tools;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.AssignmentCache;
import www.softedgenepal.com.softedgenepalschool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    private AppBarLayout app_bar_layout;
    private LinearLayout lyt_profile, homeWorkLinearLayout, homeWorkLinearLayoutDown;

    private TextView subjectTextView, createDateTextView, deadlineTextView;
    private WebView homeWorkTextView;
    private ImageButton homeWorkDownload;

    private AssignmentCache cache;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        final View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);

        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //casting
        casting(view);

        setHomeWorkInVisibilty();

        //setData to view
        setData();

        hideView(app_bar_layout);

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    showView(app_bar_layout, getActionBarSize());
                    //hideView(lyt_profile);
                }
                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    hideView(app_bar_layout);
                    //showView(lyt_profile, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        ((ImageButton) view.findViewById(R.id.assignment_bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }

    private void casting(View view) {
        app_bar_layout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        lyt_profile = (LinearLayout) view.findViewById(R.id.lyt_profile);
        subjectTextView = view.findViewById(R.id.assignment_subject);
        createDateTextView = view.findViewById(R.id.assignment_createdDate);
        deadlineTextView = view.findViewById(R.id.assignment_deadline);
        homeWorkTextView = view.findViewById(R.id.assignment_homeWorkWebView);
        homeWorkLinearLayout = view.findViewById(R.id.assignment_homwWork_lyl);
        homeWorkLinearLayoutDown = view.findViewById(R.id.assignment_homwWork_lyl_down);
        ((View) view.findViewById(R.id.lyt_spacer)).setMinimumHeight(Tools.getScreenHeight() / 2);
        homeWorkDownload = view.findViewById(R.id.assignment_download);
    }

    private void setHomeWorkInVisibilty(){
        homeWorkLinearLayout.setVisibility(View.GONE);
        homeWorkLinearLayoutDown.setVisibility(View.GONE);
    }
    private void setHomeWorkVisibilty(){
        homeWorkLinearLayout.setVisibility(View.VISIBLE);
        homeWorkLinearLayoutDown.setVisibility(View.VISIBLE);
    }

    public void setAssignment(final AssignmentCache cache) {
        this.cache=cache;
    }

    private void setData(){
        subjectTextView.setText(cache.SubjectNameEng);
        createDateTextView.setText(cache.CreateDate);
        deadlineTextView.setText(cache.Deadline);

        String test = "<table>\n" + " <caption>A complex table</caption>\n" + " <thead>\n" + "  <tr>\n" + "   <th colspan=\"3\">Invoice #123456789</th>\n" + "   <th>14 January 2025\n" + "  </tr>\n" + "  <tr>\n" + "   <td colspan=\"2\">\n" + "    <strong>Pay to:</strong><br>\n" + "    Acme Billing Co.<br>\n" + "    123 Main St.<br>\n" + "    Cityville, NA 12345\n" + "   </td>\n" + "   <td colspan=\"2\">\n" + "    <strong>Customer:</strong><br>\n" + "    John Smith<br>\n" + "    321 Willow Way<br>\n" + "    Southeast Northwestershire, MA 54321\n" + "   </td>\n" + "  </tr>\n" + " </thead>\n" + " <tbody>\n" + "  <tr>\n" + "   <th>Name / Description</th>\n" + "   <th>Qty.</th>\n" + "   <th>@</th>\n" + "   <th>Cost</th>\n" + "  </tr>\n" + "  <tr>\n" + "   <td>Paperclips</td>\n" + "   <td>1000</td>\n" + "   <td>0.01</td>\n" + "   <td>10.00</td>\n" + "  </tr>\n" + "  <tr>\n" + "   <td>Staples (box)</td>\n" + "   <td>100</td>\n" + "   <td>1.00</td>\n" + "   <td>100.00</td>\n" + "  </tr>\n" + " </tbody>\n" + " <tfoot>\n" + "  <tr>\n" + "   <th colspan=\"3\">Subtotal</th>\n" + "   <td> 110.00</td>\n" + "  </tr>\n" + "  <tr>\n" + "   <th colspan=\"2\">Tax</th>\n" + "   <td> 8% </td>\n" + "   <td>8.80</td>\n" + "  </tr>\n" + "  <tr>\n" + "   <th colspan=\"3\">Grand Total</th>\n" + "   <td>$ 118.80</td>\n" + "  </tr>\n" + " </tfoot>\n" + "</table>";

        String homeWork = new String(cache.Homework);

        //Toast.makeText(getContext(), homeWork, Toast.LENGTH_LONG).show();

        if(!homeWork.equals("") || !homeWork.equals(null)){
            setHomeWorkVisibilty();

            String[] strings = new String[]{"\b","\t","\n","\f","\r"};//,"\\\'","\\\"", "\\\\"};
            for (String s:strings) {
                homeWork = homeWork.replaceAll(s, "");//Matcher.quoteReplacement(""));
                Log.d("TAG_WebView",homeWork);
            }

            homeWorkTextView.getSettings().setDomStorageEnabled(true);
            homeWorkTextView.getSettings().setLoadsImagesAutomatically(true);
            homeWorkTextView.getSettings().setAppCacheEnabled(true);
            homeWorkTextView.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                homeWorkTextView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            homeWorkTextView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            homeWorkTextView.requestFocus();
            homeWorkTextView.loadData(homeWork, "text/html", "utf-8");

            boolean isHTML = homeWork.matches(".*\\<[^>]+>.*");
           Log.d("TAG_HTML",cache.Homework+"\t"+isHTML);
        }

        String downloadLink = cache.ImageUrl;
        Log.d("TAG_downloadLink",downloadLink);
        if(downloadLink.equals("")){
            homeWorkDownload.setVisibility(View.GONE);
        }else {
            //todo download file
            homeWorkDownload.setOnClickListener(new DownloadFile());
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void hideView(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);
    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int size = (int) styledAttributes.getDimension(0, 0);
        return size;
    }

    private class DownloadFile implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }
}
