package com.alves.pedro.groupay;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alves.pedro.groupay.adapter.GroupAdapter;
import com.alves.pedro.groupay.model.User;

public class GroupListActivity extends AppCompatActivity {

    public static final String USER_PARAM = "USER_PARAM";

    public static final String GROUP_OUT_PARAM = "GROUP_OUT_PARAM";

    public static final int REQUEST_GROUP = 1;

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

        FloatingActionButton fabAddGroup = findViewById(R.id.fabAddGroup);
        fabAddGroup.setOnClickListener(v -> {
            Intent groupRegisterIntent = new Intent(this, GroupRegisterActivity.class);
            groupRegisterIntent.putExtra(GroupRegisterActivity.USER_PARAM, mUser);
            startActivityForResult(groupRegisterIntent, REQUEST_GROUP);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GroupAdapter groupAdapter = new GroupAdapter(mUser.getGroupList(), this, false);
        groupAdapter.setClickListener((view, position) -> {
            Intent intent = new Intent();
            intent.putExtra(GROUP_OUT_PARAM, mUser.getGroupList().get(position));
            setResult(RESULT_OK, intent);
            finish();
        });
        mRvGroups.setAdapter(groupAdapter);
    }
}
