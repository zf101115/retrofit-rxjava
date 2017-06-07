package com.zx.rx;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.rx.module.Area;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zx on 2017/6/6.
 */

public class AreaAdapter extends BaseListAdapter {
    public AreaAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(null == convertView){
            convertView=mInflater.inflate(R.layout.layout_area,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        Area area = (Area) items.get(position);
        holder.tv.setText(area.getName());


        return convertView;
    }
    class ViewHolder{
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
        @BindView(R.id.tv)
        TextView tv;
    }
}
