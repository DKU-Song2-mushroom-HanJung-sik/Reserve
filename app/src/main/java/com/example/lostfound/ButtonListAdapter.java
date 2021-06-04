package com.example.lostfound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
//MyLost에서 불러온 LostDetail 정보 회수를 위한 클래스. 나중에 쓸지 안쓸지는 모르겠음.
public class ButtonListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> data;
    public ButtonListAdapter(Context context, ArrayList<String> data){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_list, null);

        TextView textView = view.findViewById(R.id.textView_list_id);
        textView.setText(data.get(position));

        View bodyView = view.findViewById(R.id.body);
        Button btn_list_Retrieve = view.findViewById(R.id.btn_list_Retrieve);

        bodyView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, "회수하려면 회수 버튼을 클릭하세요.", Toast.LENGTH_SHORT).show();
            }
        });
/*
        btn_list_Retrieve.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                textTextView.setText(Integer.toString(pos + 1) + "번 아이템 선택.");
            }
        });

 */
/*
        btn_list_Retrieve.setOnCheckedChangeListener(new Button.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(context, "click switch view", Toast.LENGTH_SHORT).show();}
        });

 */
        return view;
    }
}
