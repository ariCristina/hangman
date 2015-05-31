package donthang.hangman;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LobbyListAdapter extends BaseAdapter {
    Context context;
    List<User> userList;

    public LobbyListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = userList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.layout_list_lobby,parent,false);

        TextView nickname = (TextView) convertView.findViewById(R.id.layout_lobby_user);
        nickname.setText(user.getNickname());

        return convertView;
    }
}
