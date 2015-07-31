package com.rpolicante.myAppBus.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rpolicante.myAppBus.domain.Bus;
import com.rpolicante.myAppBus.R;
import com.rpolicante.myAppBus.Utils.ImageHelper;
import com.rpolicante.myAppBus.interfaces.RecyclerViewOnClickListenerHack;

import java.util.List;

/**
 * Created by policante on 7/29/15.
 */
public class BusAdapter extends RecyclerView.Adapter<BusAdapter.MyViewHolder> {

    private static final String TAG = "BusAdapter";

    private Context mContext;
    private List<Bus> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;

    public BusAdapter(Context c, List<Bus> mList) {
        this.mContext = c;
        this.mList = mList;
        this.mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scale  = this.mContext.getResources().getDisplayMetrics().density;
        width  = this.mContext.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
        height = (width / 16) * 9;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i(TAG, "onCreateViewHolder");

        View v = mLayoutInflater.inflate(R.layout.item_bus_card, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");


        holder.tv_itembus_number.setText(mList.get(position).getNumber());
        holder.tv_itembus_name.setText( mList.get(position).getName() );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            holder.iv_itembus_bus.setImageResource(R.drawable.bus_img);
        }else{
            Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bus_img);
            b = Bitmap.createScaledBitmap(b, width, height, false);
            b = ImageHelper.getRoundedCornerBitmap(mContext, b, 10, width, height, false, false, true, true);

            holder.iv_itembus_bus.setImageBitmap(b);
        }

        try {
            YoYo.with(Techniques.Wave)
                    .duration(700)
                    .playOn(holder.itemView);
        }catch (Exception e){
            Log.e(TAG,"YoYo Exception "+e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addListItem(Bus b, int position){
        mList.add(b);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }


        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public ImageView iv_itembus_bus;
            public TextView tv_itembus_number;
            public TextView tv_itembus_name;

            public MyViewHolder(View itemView) {
                super(itemView);

                iv_itembus_bus    = (ImageView) itemView.findViewById(R.id.iv_itembus_bus);
                tv_itembus_number = (TextView)  itemView.findViewById(R.id.tv_itembus_number);
                tv_itembus_name   = (TextView)  itemView.findViewById(R.id.tv_itembus_name);

                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                if (mRecyclerViewOnClickListenerHack != null){
                    mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
                }
            }
        }
}
