package hitha.ken.deva.abin.bilancio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by deva on 28/04/17.
 */

public class listadapter extends RecyclerView.Adapter<listadapter.ViewHolder>{
    private List<message> array;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView sndr;
        public TextView msg;
        public ViewHolder(View t)
        {
            super(t);
            sndr=(TextView)t.findViewById(R.id.sender);
            msg=(TextView)t.findViewById(R.id.message);

        }
    }
    public listadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buylist_recycler, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(listadapter.ViewHolder holder, int position) {
        String s="From:"+(array.get(position).sender)+"         "+(array.get(position).time);
        holder.sndr.setText(s);
        holder.msg.setText((array.get(position)).msg);
    }

    @Override
    public int getItemCount() {

        return array.size();
    }


    public listadapter(List<message>s)
    {
        array=s;
    }
}