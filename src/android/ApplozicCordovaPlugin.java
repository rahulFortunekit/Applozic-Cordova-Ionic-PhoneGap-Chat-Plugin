package com.applozic.phonegap;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

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


        } else if (action.equals("launchChat")) {
            Intent intent = new Intent(this, ConversationActivity.class);            
            startActivity(intent);
        } else if (action.equals("launchChatWithUserId")) {
            Intent intent = new Intent(this, ConversationActivity.class);            
            intent.putExtra(ConversationUIService.USER_ID, data.getString(0));             
            startActivity(intent);
        } else if (action.equals("launchChatWithGroupId")) {
            Intent intent = new Intent(this, ConversationActivity.class);            
            intent.putExtra(ConversationUIService.GROUP_ID, 12);      //Pass group id here.       
            startActivity(intent);
        } else if (action.equals("launchChatWithClientGroupId")) {


        }  else if (action.equals("logout")) {
            new UserClientService(this).logout();
        } else {
            return false;
        }

        callbackContext.success(response);
        return true;
    }

}
