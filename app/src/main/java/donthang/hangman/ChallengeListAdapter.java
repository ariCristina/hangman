package donthang.hangman;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChallengeListAdapter extends BaseAdapter {
    Context context;
    List<Challenge> challengeList;

    public ChallengeListAdapter(Context context, List<Challenge> challengeList) {
        this.context = context;
        this.challengeList = challengeList;
    }

    @Override
    public int getCount() {
        return challengeList.size();
    }

    @Override
    public Challenge getItem(int position) {
        return challengeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Challenge challenge = challengeList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.layout_list_challenge,parent,false);

        TextView message = (TextView) convertView.findViewById(R.id.layout_challenge_message);
        message.setText(challenge.getMessage() + challenge.getChallengeId());

        return convertView;
    }
}
