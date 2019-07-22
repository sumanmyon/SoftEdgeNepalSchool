package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import www.softedgenepal.com.softedgenepalschool.AppCustomPackages.utils.ItemAnimation;
import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;


public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private int keysLength;
    private int size;
    private int animation_type;

    protected RecyclerAdapter(Context context, int keysLength) {
        this.context=context;
        this.keysLength=keysLength;
        size = getSize();
    }

    public int getSize() {
        return keysLength;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return onCreate(viewGroup,position);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        onBind(viewHolder, position);

        setAnimationType(ItemAnimation.BOTTOM_UP);
        setAnimation(viewHolder.itemView, position);

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onItemClickListener != null) {
//                    onItemClickListener.onItemClick(view, position);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inflateUIFields(itemView);
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

    private int lastPosition = -1;
    private boolean on_attach = true;

    protected void setAnimationType(int animation_type){
        this.animation_type = animation_type;
    }

    protected void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }

    public abstract ViewHolder onCreate(ViewGroup viewGroup, int position);
    public abstract void inflateUIFields(View itemView);
    public abstract void onBind(ViewHolder viewHolder, int position);
}
