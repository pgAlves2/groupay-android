package com.alves.pedro.groupay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alves.pedro.groupay.adapter.GroupAdapter;
import com.alves.pedro.groupay.model.User;

public class GroupListActivity extends AppCompatActivity {

    public static final String USER_PARAM = "USER_PARAM";

    private User mUser;

    private RecyclerView mRvGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        if (!getIntent().hasExtra(USER_PARAM))
            return;

        mUser = (User) getIntent().getSerializableExtra(USER_PARAM);

        RecyclerView.LayoutManager layoutRvGroups = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvGroups = findViewById(R.id.rvGroups);
        mRvGroups.setLayoutManager(layoutRvGroups);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GroupAdapter groupAdapter = new GroupAdapter(mUser.getGroupList(), this, false);
        groupAdapter.setClickListener((view, position) -> {

        });
        mRvGroups.setAdapter(groupAdapter);
    }
}
