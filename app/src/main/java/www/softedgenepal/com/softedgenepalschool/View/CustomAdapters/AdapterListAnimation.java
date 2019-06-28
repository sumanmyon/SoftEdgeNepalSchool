package www.softedgenepal.com.softedgenepalschool.View.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;
import www.softedgenepal.com.softedgenepalschool.R;

public class AdapterListAnimation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<LeaveApplicationDataCache> items;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private int animation_type = 0;

    public interface OnItemClickListener {
        void onItemClick(View view, LeaveApplicationDataCache obj, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, LeaveApplicationDataCache obj, int positio);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener mOnItemLongClickListener){
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public AdapterListAnimation(Context context, List<LeaveApplicationDataCache> items, int animation_type) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private TextView subjectText, messageText, createDateText, isActiveText;//, fromToText;
        private CardView cardView;

        public OriginalViewHolder(View v) {
            super(v);
            subjectText = v.findViewById(R.id.show_leave_app_recycle_subject);
            messageText = v.findViewById(R.id.show_leave_app_recycle_message);
            createDateText = v.findViewById(R.id.show_leave_app_recycle_createDate);
            isActiveText = v.findViewById(R.id.show_leave_app_recycle_isActive);
            cardView = v.findViewById(R.id.show_leave_app_recycle_cardView);
            //fromToText = itemView.findViewById(R.id.show_leave_app_recycle_from_to);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_leave_application_recyclerview, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.e("onBindViewHolder", "onBindViewHolder : " + position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            final LeaveApplicationDataCache cache = items.get(position);

            view.subjectText.setText(cache.Subject);
            view.messageText.setText(cache.Message);
            view.createDateText.setText(cache.CreateDate);

            TextView isActive = view.isActiveText;
            Log.d("Showall_Leave",position+"\t"+cache.IsActive);

            if(cache.IsActive.equals("false")) {
                ViewGroup.LayoutParams params = isActive.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                isActive.setLayoutParams(params);
                //isActiveText.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                //view.cardView.setBackgroundColor(ctx.getResources().getColor(R.color.grey_20));
                //view.cardView.setCardBackgroundColor(ctx.getResources().getColor(R.color.backgroundLightGray));
                //fromToText.setText("From: "+cache.From+"\nTo: "+cache.To);
            }else {
                ViewGroup.LayoutParams params = isActive.getLayoutParams();
                params.height = 0;
                isActive.setLayoutParams(params);
            }

            view.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

            view.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongClick(v, items.get(position), position);
                    }
                    return true;
                }
            });
            setAnimation(view.itemView, position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }

}