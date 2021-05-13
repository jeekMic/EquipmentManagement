package com.tencent.cloud.ai.equipmentmanagement.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tencent.cloud.ai.equipmentmanagement.R;
import com.tencent.cloud.ai.equipmentmanagement.model.Mechine;

import java.util.List;

public class MechineAdapter extends BaseAdapter {
    private List<Mechine.AllApplyDTO>  data;
    private Context context;

    public MechineAdapter(Context context,List<Mechine.AllApplyDTO> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
         Mechine.AllApplyDTO item = data.get(position);//获取当前项的实例
        ViewHolder viewHolder;
        View view = null;
        if (convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.mechine_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.id = view.findViewById(R.id.mechine_id);
            viewHolder.teacherId = view.findViewById(R.id.teacherId);
            viewHolder.realname= view.findViewById(R.id.realname);
            viewHolder.name= view.findViewById(R.id.name);
            viewHolder.createTime= view.findViewById(R.id.createTime);
            viewHolder.buyStatus= view.findViewById(R.id.buyStatus);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.id.setText(item.getId().toString());
        viewHolder.teacherId.setText(item.getTeacherId().toString());
        viewHolder.realname.setText(item.getRealname());
        viewHolder.name.setText(item.getName());
        viewHolder.createTime.setText(item.getCreateTime());
        viewHolder. buyStatus.setText(item.getBuyStatus().toString());
        return view;
    }

    // 内部类
    static class ViewHolder{
        TextView id;
        TextView teacherId;
        TextView realname;
        TextView name;
        TextView createTime;
        TextView buyStatus;
    }
}
