package org.oyvang.friendlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class main extends Activity {

    ArrayList<Person> friendList = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((savedInstanceState != null) && (savedInstanceState.getSerializable("friends") != null)) {
            friendList = (ArrayList<Person>) savedInstanceState.getSerializable("friends");
        }
        viewFriendList();
    }

    public void onClickAddFriend(View v) {
        Intent i = new Intent(this, addFriend.class);
        i.putExtra("friendList", friendList);
        startActivityForResult(i, 1);
    }

    public void viewFriendList() {
        Log.d("MyDebug", "FrienList.Size() on viewFriendList()" + friendList.size());
        if (friendList.size() == 0) ;
        else {
            ListView friendsView = (ListView) findViewById(R.id.listView);
            ArrayAdapter<Person> arrayAdapter =
                    new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, friendList);
            friendsView.setAdapter(arrayAdapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                String name = data.getStringExtra("name");
                int d = data.getIntExtra("day", 0);
                int m = data.getIntExtra("month", 0);
                int y = data.getIntExtra("year", 0);
                friendList.add(new Person(name, d, m, y));
                Log.d("MyDebug", "FrienList.Size()  -  " + friendList.size());
                viewFriendList();
                break;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("friends", friendList);
    }

}
