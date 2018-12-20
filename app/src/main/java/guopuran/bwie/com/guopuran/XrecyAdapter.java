package guopuran.bwie.com.guopuran;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import guopuran.bwie.com.guopuran.bean.Bean;

public class XrecyAdapter extends RecyclerView.Adapter<XrecyAdapter.ViewHolder> {
    private List<Bean.DataBean> list;
    private Context context;
    private boolean flag;

    public XrecyAdapter(Context context, boolean flag) {
        this.context = context;
        this.flag = flag;
        //初始化
        list=new ArrayList<>();
    }
    //刷新

    public void setList(List<Bean.DataBean> slist) {
       list.clear();
        if (slist!=null){
            list.addAll(slist);
        }
        notifyDataSetChanged();
    }

    public List<Bean.DataBean> getList() {
        return list;
    }

    //加载
    public void addList(List<Bean.DataBean> slist) {
        if (slist!=null){
            list.addAll(slist);
        }
        notifyDataSetChanged();
    }
    public Bean.DataBean getitem(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                (flag) ? R.layout.item_linear : R.layout.item_grid
                , viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getdata(getitem(i),context,i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.item_image);
            title=itemView.findViewById(R.id.item_title);
            price=itemView.findViewById(R.id.item_price);
        }

        public void getdata(Bean.DataBean getitem, Context context, final int i) {
            Glide.with(context).load(getitem.icon()).into(image);
            title.setText(getitem.getTitle());
            price.setText("￥"+getitem.getPrice());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monClick.itemonclick(i);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    monlongClick.itemonlongclick(v,i);
                    return true;
                }
            });
        }
    }
    public void del(View view ,int position){
        list.remove(position);

//        notifyItemRemoved(position);
//        notifyItemRangeRemoved(position,list.size());
        notifyDataSetChanged();
    }
    private onClick monClick;
    public void setonclick(onClick monClick){
        this.monClick=monClick;
    }
    public interface onClick{
        void itemonclick(int position);
    }
    private onlongClick monlongClick;
    public void setonlongclick(onlongClick monlongClick){
        this.monlongClick=monlongClick;
    }
    public interface onlongClick{
        void itemonlongclick(View view,int position);
    }
}
