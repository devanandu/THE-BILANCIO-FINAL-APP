package hitha.ken.deva.abin.bilancio;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by deva on 27/04/17.
 */

public class adapter_memberlist extends RecyclerView.Adapter<adapter_memberlist.ViewHolder>{
    private List<members> array;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView n,s;
        public ViewHolder(View t)
        {
            super(t);
            n=(TextView) t.findViewById(R.id.lnk_member_name);
            s=(TextView) t.findViewById(R.id.request_status);
        }
    }
    public adapter_memberlist.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rec_memlist, parent, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(array.get(position).status.equals("false"))
        {
            holder.s.setTextColor(Color.RED);
            holder.s.setText("------ Request Send...");
        }
        else
        {
            holder.s.setTextColor(Color.GREEN);
            holder.s.setText("------ Linked");
        }
        Log.e("db",array.get(position).phno);
        String s="  * "+array.get(position).name+"("+array.get(position).phno+")";
        holder.n.setText(s);
    }


    @Override
    public int getItemCount() {

        return array.size();
    }


    public adapter_memberlist(List<members>s)
    {
        array=s;
    }
}


