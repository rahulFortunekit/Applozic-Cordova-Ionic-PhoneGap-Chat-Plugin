package com.applozic.phonegap;

import android.content.Context;
import android.content.Intent;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.register.RegisterUserClientService;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.contact.AppContactService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.api.account.user.UserClientService;
import com.applozic.mobicomkit.uiwidgets.people.activity.MobiComKitPeopleActivity;
import com.applozic.mobicommons.json.GsonUtils;
import com.applozic.mobicommons.people.contact.Contact;

import java.util.ArrayList;
import java.util.List;

public class ApplozicCordovaPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        Context context = cordova.getActivity().getApplicationContext();
        String response = "success";

        if (action.equals("login")) {
            String userJson = data.getString(0);
            User user = (User) GsonUtils.getObjectFromJson(userJson, User.class);
            Applozic.init(context, user.getApplicationId());

            final CallbackContext callback = callbackContext;

            UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

                @Override
                public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                    //After successful registration with Applozic server the callback will come here
                    callback.success(GsonUtils.getJsonFromObject(registrationResponse, RegistrationResponse.class));
                }

                @Override
                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                    //If any failure in registration the callback  will come here
                    callback.error(GsonUtils.getJsonFromObject(registrationResponse, RegistrationResponse.class));
                }};

            new UserLoginTask(user, listener, context).execute((Void) null);
        } else if (action.equals("isLoggedIn")) {
            callbackContext.success(String.valueOf(MobiComUserPreference.getInstance(context).isLoggedIn()));
        } else if (action.equals("updatePushNotificationToken")) {
            if (MobiComUserPreference.getInstance(context).isRegistered()) {
                try {
                    new RegisterUserClientService(context).updatePushNotificationId(data.getString(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (action.equals("launchChat")) {
            Intent intent = new Intent(context, ConversationActivity.class);
            cordova.getActivity().startActivity(intent);
        } else if (action.equals("launchChatWithUserId")) {
            Intent intent = new Intent(context, ConversationActivity.class);
            intent.putExtra(ConversationUIService.USER_ID, data.getString(0));
            cordova.getActivity().startActivity(intent);
        } else if (action.equals("launchChatWithGroupId")) {
            Intent intent = new Intent(context, ConversationActivity.class);
            intent.putExtra(ConversationUIService.GROUP_ID, data.getString(0));
            cordova.getActivity().startActivity(intent);
        } else if (action.equals("launchChatWithClientGroupId")) {
            Intent intent = new Intent(context, ConversationActivity.class);
            intent.putExtra(ConversationUIService.CLIENT_GROUP_ID, data.getString(0));
            cordova.getActivity().startActivity(intent);
        }  else if (action.equals("startNew")) {
            Intent intent = new Intent(context, MobiComKitPeopleActivity.class);
            cordova.getActivity().startActivity(intent);
        } else if (action.equals("addContact")) {
            String contactJson = data.getString(0);
            Contact contact = (Contact) GsonUtils.getObjectFromJson(contactJson, Contact.class);
            AppContactService appContactService = new AppContactService(context);
            appContactService.add(contact);
        } else if (action.equals("updateContact")) {
            String contactJson = data.getString(0);
            Contact contact = (Contact) GsonUtils.getObjectFromJson(contactJson, Contact.class);
            AppContactService appContactService = new AppContactService(context);
            appContactService.updateContact(contact);
        } else if (action.equals("removeContact")) {
            String contactJson = data.getString(0);
            Contact contact = (Contact) GsonUtils.getObjectFromJson(contactJson, Contact.class);
            AppContactService appContactService = new AppContactService(context);
            appContactService.deleteContact(contact);
        } else if (action.equals("addContacts")) {
            String contactJson = data.getString(0);
            List<Contact> contactList = (ArrayList<Contact>) GsonUtils.getObjectFromJson(contactJson, List.class);
            AppContactService appContactService = new AppContactService(context);
            appContactService.addAll(contactList);
        }  else if (action.equals("logout")) {
            new UserClientService(cordova.getActivity()).logout();
            callbackContext.success(response);
        } else {
            return false;
        }

        return true;
    }

}