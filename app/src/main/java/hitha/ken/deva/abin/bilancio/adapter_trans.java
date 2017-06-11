package hitha.ken.deva.abin.bilancio;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by deva on 01/05/17.
 */

public class adapter_trans extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> array;
    static int count=1;
    public class ViewHolder1 extends RecyclerView.ViewHolder {

        private TextView label1, label2;

        public ViewHolder1(View v) {
            super(v);
            label1 = (TextView) v.findViewById(R.id.day);
            label2 = (TextView) v.findViewById(R.id.month);
        }

        public TextView getLabel1() {
            return label1;
        }

        public void setLabel1(TextView label1) {
            this.label1 = label1;
        }

        public TextView getLabel2() {
            return label2;
        }

        public void setLabel2(TextView label2) {
            this.label2 = label2;
        }
    }
    public class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView label1, label2,label3;

        public ViewHolder2(View v) {
            super(v);
            label1 = (TextView) v.findViewById(R.id.Category);
            label2 = (TextView) v.findViewById(R.id.note);
            label3 = (TextView) v.findViewById(R.id.amount);
        }

        public TextView getcat() {
            return label1;
        }

        public void setcat(TextView label1) {
            this.label1 = label1;
        }
        public TextView getnote() {
            return label2;
        }

        public void setnote(TextView label1) {
            this.label2 = label1;
        }


        public TextView getamt() {
            return label3;
        }

        public void setamt(TextView label2) {
            this.label3 = label2;
        }
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       if(viewType==0)
       {
           View v1 = inflater.inflate(R.layout.rec_dayheading,parent, false);
            viewHolder = new ViewHolder1(v1);
       }
       else
       {
           View v2 = inflater.inflate(R.layout.rec_translist,parent, false);
           viewHolder = new ViewHolder2(v2);
       }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()==0)
        {
            ViewHolder1 v1=(ViewHolder1)holder;
            String s=array.get(position).toString();
            String[] parts = s.split(" ");
            v1.getLabel2().setText(parts[1]+"\n"+parts[5]);
            v1.getLabel1().setText(parts[2]);
        }
        else
        {
           ViewHolder2 v2=(ViewHolder2)holder;
            record rec=(record) array.get(position);
           v2.getcat().setText(rec.cat);
            v2.getnote().setText("("+rec.time+")"+rec.note);
            v2.getamt().setText("\u20B9"+rec.amt);
            if(rec.type.equals("Expense"))
                v2.getamt().setTextColor(Color.RED);
            if(rec.type.equals("Income"))
                v2.getamt().setTextColor(Color.BLUE);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (array.get(position) instanceof Date) {
            return 0;
        } else if (array.get(position) instanceof record) {
            return 1;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public adapter_trans(List<Object> s)
    {array=s;}
}
