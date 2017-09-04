package com.morpho.demo.frament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.morpho.demo.R;
import com.morpho.demo.constant.Customer;
import com.morpho.demo.customs.ListItemCustAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Alejandro Ruiz on 08/03/2016.
 */
public class CustomerListFragment extends ParentFragment {

    private View root;
    private ListView listContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.list_customers_layout, container,
                false);

        root = layoutView;

        listContent = (ListView) layoutView.findViewById(R.id.listContent);

        return layoutView;
    }

    public void setCustomers(Vector<Customer> customers){
        final List<Customer> customers1 = new LinkedList<>(customers);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListItemCustAdapter adapter = new ListItemCustAdapter(getActivity(),R.id.listContent,customers1);
                listContent.setAdapter(adapter);
            }
        });

    }
}
