package com.example.stuadminlogin.adapters;

import android.content.Context;
import android.view.*;
import android.widget.*;
import com.example.stuadminlogin.R;

import java.util.List;

public class QueryListAdapter extends BaseAdapter {

    private Context context;
    private List<String> queryTexts;
    private List<String> studentInfos;

    public QueryListAdapter(Context context, List<String> queryTexts, List<String> studentInfos) {
        this.context = context;
        this.queryTexts = queryTexts;
        this.studentInfos = studentInfos;
    }

    @Override
    public int getCount() {
        return queryTexts.size();
    }

    @Override
    public Object getItem(int i) {
        return queryTexts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_query, parent, false);
        }

        TextView queryText = view.findViewById(R.id.query_text);
        TextView studentInfo = view.findViewById(R.id.student_info);

        queryText.setText(queryTexts.get(position));
        studentInfo.setText(studentInfos.get(position));

        return view;
    }
}
