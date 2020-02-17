package com.example.mis_internee.atendence_app_android.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mis_internee.atendence_app_android.Model.DataModel;
import com.example.mis_internee.atendence_app_android.R;

public class CustomAdapter extends ArrayAdapter {

        private String dataSet;
        Context mContext;

        // View lookup cache
        private static class ViewHolder {
                TextView txtName;
                CheckBox checkBox;
        }

        public CustomAdapter(String data, Context context) {
                super(context, R.layout.row, Integer.parseInt(data));
                this.dataSet = data;
                this.mContext = context;

        }
//        @Override
//        public int getCount() {
//                return dataSet.size();
//        }
//
//        @Override
//        public DataModel getItem(int position) {
//                return (DataModel) dataSet.get();
//        }


        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                ViewHolder viewHolder;
                final View result;

                if (convertView == null) {
                        viewHolder = new ViewHolder();
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
//                        viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
//                        viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

                        result=convertView;
                        convertView.setTag(viewHolder);

                } else {
                        viewHolder = (ViewHolder) convertView.getTag();
                        result=convertView;
                }

                DataModel item = (DataModel) getItem(position);


                viewHolder.txtName.setText(item.name);
                viewHolder.checkBox.setChecked(item.checked);


                return result;
        }
}