package com.anglll.beelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.anglll.library.BeeAdapter;
import com.anglll.library.BeeLayout;
import com.anglll.library.BeeViewHolder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BeeLayout beeLayout = (BeeLayout) findViewById(R.id.bee_layout);
        BeeAdapter adapter = new BeeAdapter() {
            @Override
            public BeeViewHolder onCreateViewHolder(ViewGroup parent) {
                return new BeeHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bee_item_layout, parent, false));
            }

            @Override
            public void onBindViewHolder(BeeViewHolder viewHolder, int position) {

            }
        };
        beeLayout.setAdapter(adapter);
    }

    public class BeeHolder extends BeeViewHolder {

        public BeeHolder(View itemView) {
            super(itemView);
        }
    }
}
