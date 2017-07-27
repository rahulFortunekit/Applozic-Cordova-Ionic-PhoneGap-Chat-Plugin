package com.applozic.mobicomkit.uiwidgets.conversation.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.applozic.mobicomkit.uiwidgets.R;

import java.util.ArrayList;

public class MobicomPresetMessageActivity extends AppCompatActivity {

    ListView presetListView;
    public static String PRESET_MESSAGE = "presetMessage";
    public static String PRESET_MESSAGE_LIST = "presetMessageList";
    ArrayList<String> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobicom_preset_message);

        Toolbar presetToolbar = (Toolbar) findViewById(R.id.presetToolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        presetToolbar.setNavigationIcon(R.drawable.applozic_ic_action_cancel);
        toolbarTitle.setText("Preset Messages");
        setSupportActionBar(presetToolbar);
        getSupportActionBar().setTitle("");

        messageList = getIntent().getStringArrayListExtra(PRESET_MESSAGE_LIST);
        if (messageList == null) {
            messageList = new ArrayList<>();
        }

        presetListView = (ListView) findViewById(R.id.presetMessageList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, messageList);
        presetListView.setAdapter(adapter);

        presetToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        presetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ConversationActivity.ACTION_RECEIVE_PRESET_MESSAGE);
                intent.putExtra(PRESET_MESSAGE, messageList.get(position));
                sendBroadcast(intent);
                finish();
            }
        });
    }
}
