
package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.implementation.comm.data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * @author  Shu Zhang
 *
 * Description:
 * the fragment for displaying the settings of all air conditioners, the data source is
 * com.example.implementation.comm.data.acs
 * */
public class MyHomeFragment extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> groupList;
    HashMap<String, List<String>> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_home,container,false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        itemList = data.getData();
        groupList = new ArrayList<String>(itemList.keySet());
        expandableListAdapter = new ComExpandableListAdapter(view.getContext(), groupList, itemList);
        expandableListView.setAdapter(expandableListAdapter);

        int count = expandableListView.getCount();
        for(int i = 0;i<count;i++){
            if (data.acs.get(groupList.get(i)).getOn_off()==1) {
                expandableListView.expandGroup(i);
            }
        }

        return view;
    }
}

