package com.mahmoudjoe333.alakhwhmilk.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mahmoudjoe333.alakhwhmilk.Model.Item_Entity;
import com.mahmoudjoe333.alakhwhmilk.R;
import com.mahmoudjoe333.alakhwhmilk.view.logic.Dialog;
import com.mahmoudjoe333.alakhwhmilk.view.logic.Item_Adapter;
import com.mahmoudjoe333.alakhwhmilk.viewModel.MainActivity_viewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private MainActivity_viewModel mViewModel;
    RecyclerView mRecycleView;
    Item_Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton mAddNewItem_BTN,mundo_BTN;
    Stack<Item_Entity> mStack;
    Spinner mSpiner,mDaysSpinner,mTimeSpinner;
    int mRegain,mTime;
    String mday;
    List<Item_Entity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        AddNewItem();
        Update();
        Delete();
        Undo();
    }



    private void init() {

        mViewModel=new ViewModelProvider(this).get(MainActivity_viewModel.class);
        setTitle("الاخوه");
        FindView();
        mRegain=0;
        mTime=0;
        mday="كل يوم";
        setSpinnerSelected();
        BuildAdapter();
        observe();
        mStack=new Stack<>();
    }

    private void setSpinnerSelected() {

        mSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRegain=position;

                mAdapter.setList(getList(mViewModel.GetAllList().getValue()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDaysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mday=mDaysSpinner.getSelectedItem().toString();

                mAdapter.setList(getList(mViewModel.GetAllList().getValue()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTime=position;
                mAdapter.setList(getList(mViewModel.GetAllList().getValue()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void FindView() {
        mSpiner=findViewById(R.id.main_spinner);
        mTimeSpinner=findViewById(R.id.day_spinner);
        mDaysSpinner=findViewById(R.id.time_spinner);
        mundo_BTN=findViewById(R.id.undo);
        mAddNewItem_BTN=findViewById(R.id.addNew);
        mundo_BTN=findViewById(R.id.undo);
    }

    private void observe() {

        mViewModel.GetAllList().observe(this, new Observer<List<Item_Entity>>() {
            @Override
            public void onChanged(List<Item_Entity> item_entities) {
                mAdapter.setRes(getResources());

                mAdapter.setList(item_entities);
                if(item_entities.isEmpty())
                    findViewById(R.id.noNote).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.noNote).setVisibility(View.GONE);
            }
        });
    }

    private List<Item_Entity> getList(List<Item_Entity> list) {
        List<Item_Entity> l;
        l=searchByRegain(mRegain,list);
        //List<Item_Entity>ll=searchByDay(mday,l);
        //List<Item_Entity>ll=searchByTime(mTime,l);
        return l;
    }

    public List<Item_Entity> searchByRegain(int regain,List<Item_Entity> list) {
        if (regain==0) return list;
        List<Item_Entity> indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (regain==list.get(i).getRegain()) {
                indexList.add(list.get(i));
            }
        }
        return indexList;
    }
    public List<Item_Entity> searchByTime(int time,List<Item_Entity> list) {

        List<Item_Entity> indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (time==list.get(i).getTime()) {
                indexList.add(list.get(i));
            }
        }
        return indexList;
    }
    public List<Item_Entity> searchByDay(String day,List<Item_Entity> list) {
        if (day.equals("كل يوم")) return list;
        List<Item_Entity> indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String[] s=list.get(i).getDay().split(" ");
            for(String d: s)
                if (day.equals(d)) {
                    indexList.add(list.get(i));
                    break;
                }
        }
        return indexList;
    }

    private void BuildAdapter() {
        mRecycleView=findViewById(R.id.Container);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new Item_Adapter();
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(mLayoutManager);
    }
    private void AddNewItem() {
        mAddNewItem_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog();
                dialog.show(getSupportFragmentManager(),"Insert");
            }
        });
    }
    private void Update() {
        mAdapter.setOnItemClickListener(new Item_Adapter.OnItemClickListener() {
            @Override
            public void OnClick(Item_Entity item) {
                Dialog dialog=new Dialog(item);
                dialog.show(getSupportFragmentManager(),"Update");
            }

            @Override
            public void OnitemDelete(Item_Entity item) {
                SetDone(item);
            }
        });
    }
    private void Delete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Item_Entity item=mAdapter.getItemAt(position);
                mStack.push(item);
                mViewModel.delete(item);
                mundo_BTN.setVisibility(View.VISIBLE);
            }
        }).attachToRecyclerView(mRecycleView);
    }
    private void Undo() {
        mundo_BTN.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if(!mStack.isEmpty()) {
                    mViewModel.insert(mStack.pop());
                    mAdapter.notifyDataSetChanged();
                }
                if(mStack.isEmpty())
                    mundo_BTN.setVisibility(View.GONE);
            }
        });
    }
    private void SetDone(Item_Entity item){
        if(item.isDone())
            item.setDone(false);
        else
            item.setDone(true);
        mViewModel.update(item);
        mAdapter.notifyDataSetChanged();
    }
}
