package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

/*
 * @author  Shu Zhang
 *
 * Description:
 * A component used to display air conditioners' settings.
 *
 * */
public class ComExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;
    private HashMap<String, List<String>> itemList;

    public ComExpandableListAdapter(Context context, List<String> groupList, HashMap<String, List<String>> itemList){
        this.context = context;
        this.groupList = groupList;
        this.itemList = itemList;
    }

    @Override
    public long getGroupId(int groupPos) {
        return groupPos;
    }

    @Override
    public int getGroupCount() {
        return this.groupList.size();
    }

    @Override
    public Object getGroup(int groupPos) {
        return this.groupList.get(groupPos);
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {
        String listGroupText = (String) getGroup(groupPos);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_group, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.expandedListGroup);
        textView.setText(listGroupText);
        return convertView;
    }

    @Override
    public long getChildId(int groupPos, int itemPos) {
        return itemPos;
    }

    @Override
    public int getChildrenCount(int groupPos) {
        return this.itemList.get(this.groupList.get(groupPos)).size();
    }

    @Override
    public Object getChild(int groupPos, int itemPos) {
        return this.itemList.get(this.groupList.get(groupPos)).get(itemPos);
    }

    @Override
    public View getChildView(int groupPos, final int itemPos, boolean isLastChild, View convertView, ViewGroup parent) {
        final String listItemText = (String) getChild(groupPos, itemPos);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.expandedListItem);
        textView.setText(listItemText);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPos, int itemPos) {
        return true;
    }
}
