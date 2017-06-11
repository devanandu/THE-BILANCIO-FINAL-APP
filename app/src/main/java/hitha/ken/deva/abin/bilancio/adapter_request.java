package hitha.ken.deva.abin.bilancio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by deva on 27/04/17.
 */

public class adapter_request extends RecyclerView.Adapter<adapter_request.Viewholder> {
    private List<String> array;
    public class Viewholder extends RecyclerView.ViewHolder{
        public TextView n;
        public Viewholder(View itemView) {
            super(itemView);
            n=(TextView)itemView.findViewById(R.id.lnk_member_name);
        }
    }
    public adapter_request.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rec_memlist, parent, false);
        Viewholder vh=new Viewholder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder.n.setText(array.get(position));
        holder.n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public adapter_request(List<String> s){array=s;}

}
