package com.morpho.demo.customs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morpho.demo.R;
import com.morpho.demo.constant.Customer;
import com.morpho.demo.tools.Base64;
import com.morpho.demo.tools.NumericTool;

import java.util.List;


/**
 * Created by Alejandro Ruiz on 08/03/2016.
 */
public class ListItemCustAdapter extends ArrayAdapter<Customer>{

    private List<Customer> customers;
    private final Context context;

    public ListItemCustAdapter(Context context, int textViewResourceId, List<Customer> customers) {
        super(context, textViewResourceId, customers);
        this.context = context;
        this.customers = customers;
    }

    @Override
    public int getCount() {
        if(customers != null)
            return customers.size();
        else
            return 0;
    }

    @Override
    public Customer getItem(int position) {
        return customers.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_cust_item, parent, false);

        TextView textInfo = (TextView)rowView.findViewById(R.id.textInfo);
        ImageView imageCust = (ImageView)rowView.findViewById(R.id.imageCust);
        RelativeLayout item = (RelativeLayout) rowView.findViewById(R.id.item);

        int mod = position % 2;

        if(mod == 0){
            item.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_curner_good_question));
        }

        Customer customer = getItem(position);
        if(customer != null){
            StringBuilder restBuild = new StringBuilder("Información de cliente:\n");
            restBuild.append("ID: " + customer.ID + "\n");
            restBuild.append("Nombre: " + customer.Name + "\n");
            restBuild.append("Apellidos: " + customer.Surname + "\n");
            //restBuild.append("Fecha Nacimiento: " + customer.BirthDay + "\n");
            restBuild.append("Saldo: " + customer.Balance + "\n");
            //restBuild.append("Genero: " + (customer.Genre == 1 ? "Masculino" : "Femenino") + "\n");

            textInfo.setText(restBuild.toString());

            if (!NumericTool.isStringNunnable(customer.imgSrc)) {
                byte[] data = Base64.decode(customer.imgSrc);
                Bitmap bmp;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                if (bmp != null)
                    imageCust.setImageBitmap(bmp);
            }
        }

        return rowView;
    }
}
