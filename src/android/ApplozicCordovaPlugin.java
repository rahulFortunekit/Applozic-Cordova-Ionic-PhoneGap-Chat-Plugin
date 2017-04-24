package com.applozic.phonegap;

import android.content.Context;
import android.content.Intent;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.api.account.user.UserClientService;

// http://stackoverflow.com/a/18609901/1122828
public class ApplozicCordovaPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        String response = "success";

        if (action.equals("login")) {
            String userJson = data.getString(0);


        } else if (action.equals("isLoggedIn")) {
            Context context = cordova.getActivity().getApplicationContext();
            callbackContext.success(String.valueOf(MobiComUserPreference.getInstance(context).isLoggedIn()));
        } else if (action.equals("launchChat")) {
            Context context = cordova.getActivity().getApplicationContext();
            Intent intent = new Intent(context, ConversationActivity.class);
            cordova.getActivity().startActivity(intent);
        } else if (action.equals("launchChatWithUserId")) {
            Context context = cordova.getActivity().getApplicationContext();
            Intent intent = new Intent(context, ConversationActivity.class);
            intent.putExtra(ConversationUIService.USER_ID, data.getString(0));
            cordova.getActivity().startActivity(intent);
        } else if (action.equals("launchChatWithGroupId")) {
            Context context = cordova.getActivity().getApplicationContext();
            Intent intent = new Intent(context, ConversationActivity.class);
            intent.putExtra(ConversationUIService.GROUP_ID, 12);      //Pass group id here.       
            cordova.getActivity().startActivity(intent);
        } else if (action.equals("launchChatWithClientGroupId")) {


        }  else if (action.equals("logout")) {
            new UserClientService(cordova.getActivity()).logout();
            callbackContext.success(response);
        } else {
            return false;
        }

        return true;
    }

}