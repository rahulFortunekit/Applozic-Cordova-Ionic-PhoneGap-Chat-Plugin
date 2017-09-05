package com.applozic.phonegap;

import android.content.Context;
import android.os.AsyncTask;
import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.api.conversation.MobiComConversationService;
import com.applozic.mobicomkit.channel.database.ChannelDatabaseService;
import com.applozic.mobicomkit.contact.database.ContactDatabase;
import com.applozic.phonegap.MessageParamsModel;
import com.applozic.mobicommons.commons.core.utils.Utils;
import com.applozic.mobicommons.json.GsonUtils;
import com.applozic.mobicommons.people.channel.Channel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by reytum on 4/9/17.
 */

public class GetMessageListTask extends AsyncTask<Void, Void, List<Message>> {

    String error;
    String paramsJson;
    GetMessageListListener listener;
    Context context;
    boolean isLatestMessageRequest = false;

    public GetMessageListTask(String paramsJson, GetMessageListListener listener, Context context) {
        this.listener = listener;
        this.paramsJson = paramsJson;
        this.context = new WeakReference<Context>(context).get();
    }

    @Override
    protected List<Message> doInBackground(Void... params) {
        MessageParamsModel model = (MessageParamsModel) GsonUtils.getObjectFromJson(paramsJson, MessageParamsModel.class);
        Channel channel = (model.getChannelKey() == null ? null : ChannelDatabaseService.getInstance(context).getChannelByChannelKey(model.getChannelKey()));

        List<Message> messageList;

        MobiComConversationService mobiComConversationService = new MobiComConversationService(context);

        if (channel == null && model.getContactId() == null) {
            isLatestMessageRequest = true;
            messageList = mobiComConversationService.getLatestMessagesGroupByPeople(model.getCreatedAtTime(), model.getSearchString());
        } else {
            messageList = new MobiComConversationService(context).getMessages(model.getStartTime(), model.getEndTime(), new ContactDatabase(context).getContactById(model.getContactId()), channel, model.getConversationId(), model.isSkipRead());
        }

        Collections.sort(messageList, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o2.getCreatedAtTime().compareTo(o1.getCreatedAtTime());
            }
        });

        return messageList;
    }

    @Override
    protected void onPostExecute(List<Message> messageList) {
        super.onPostExecute(messageList);

        List<String> recList = new ArrayList<String>();
        List<Message> mList = new ArrayList<Message>();

        if (!messageList.isEmpty() && isLatestMessageRequest) {
            for (Message message : messageList) {
                if (message.getGroupId() == null && !recList.contains(message.getContactIds())) {
                    mList.add(message);
                    recList.add(message.getContactIds());
                } else if (message.getGroupId() != null && !recList.contains("group" + message.getGroupId())) {
                    mList.add(message);
                    recList.add("group" + message.getGroupId());
                }
            }
        }

        if (!messageList.isEmpty()) {
            if (isLatestMessageRequest && !mList.isEmpty()) {
                listener.onSuccess(mList.toArray(new Message[mList.size()]), context);
            } else {
                listener.onSuccess(messageList.toArray(new Message[messageList.size()]), context);
            }
        } else {
            listener.onFailure("Some error occurred while fetching messages", context);
        }
    }

    public interface GetMessageListListener {
        void onSuccess(Message[] messageList, Context context);

        void onFailure(String error, Context context);
    }
}
