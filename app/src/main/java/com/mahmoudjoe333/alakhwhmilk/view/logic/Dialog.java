package com.mahmoudjoe333.alakhwhmilk.view.logic;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.mahmoudjoe333.alakhwhmilk.Model.Item_Entity;
import com.mahmoudjoe333.alakhwhmilk.R;
import com.mahmoudjoe333.alakhwhmilk.viewModel.MainActivity_viewModel;

import java.util.ArrayList;

public class Dialog extends AppCompatDialogFragment {
    private MainActivity_viewModel mViewModel;
    private String name,address,number;
    private int regain,time;
    private float mquantity;
    private String mdays;
    private Item_Entity entity=null;
    public Dialog()
    {

    }
    public Dialog(Item_Entity entity)
    {
        this.entity=entity;

    }
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view=inflater.inflate(R.layout.layout_dailog,null);
        if(entity!=null)
            AssignValue(view);

        builder.setView(view).setTitle("اضافه").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mViewModel=new ViewModelProvider(Dialog.this).get(MainActivity_viewModel.class);
                if(entity==null) {  //insert
                    Item_Entity item = BuildItem(view);
                    if (item != null) {
                        mViewModel.insert(item);
                    }
                }
                else {  //update

                    Item_Entity item = BuildItem(view);
                    if (item != null) {
                        item.setId(entity.getId());
                        mViewModel.update(item);
                    }
                }
            }
        });


        return builder.create();
    }

    private void AssignValue(View view) {
        ((EditText)view.findViewById(R.id.d_name)).setText(entity.getName());
        ((EditText)view.findViewById(R.id.d_address)).setText(entity.getAddress());
        ((EditText)view.findViewById(R.id.d_number)).setText(entity.getNumber());
        ((Spinner)view.findViewById(R.id.d_spinner)).setSelection(entity.getRegain());

        ((EditText)view.findViewById(R.id.d_days)).append(entity.getDay());
        ((EditText)view.findViewById(R.id.d_quantity)).setText(String.valueOf(entity.getQuantity()));
        ((Spinner)view.findViewById(R.id.d_time_spinner)).setSelection(entity.getTime());
    }

    private Item_Entity BuildItem(View view) {
        name=((EditText)view.findViewById(R.id.d_name)).getText().toString();
        address=((EditText)view.findViewById(R.id.d_address)).getText().toString();
        number=((EditText)view.findViewById(R.id.d_number)).getText().toString();
        regain=((Spinner)view.findViewById(R.id.d_spinner)).getSelectedItemPosition();

        mdays=((EditText)view.findViewById(R.id.d_days)).getText().toString();
        mquantity=Float.parseFloat(((EditText)view.findViewById(R.id.d_quantity)).getText().toString());
        time=((Spinner)view.findViewById(R.id.d_time_spinner)).getSelectedItemPosition();
        if(name.isEmpty()||address.isEmpty()||number.isEmpty())
        {
            return null;
        }
        else return new Item_Entity(name,address,number,regain,false,time,mdays,mquantity);
    }
}
