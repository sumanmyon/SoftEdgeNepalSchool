package www.softedgenepal.com.softedgenepalschool.View.Custom.CustomAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private int keysLength;
    private int size;

    protected RecyclerAdapter(Context context, int keysLength) {
        this.context=context;
        this.keysLength=keysLength;
        size = getSize();
    }

    private int getSize() {
        return keysLength;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return onCreate(viewGroup,position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        onBind(viewHolder, position);
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
    public abstract ViewHolder onCreate(ViewGroup viewGroup, int position);
    public abstract void inflateUIFields(View itemView);
    public abstract void onBind(ViewHolder viewHolder, int position);
}
