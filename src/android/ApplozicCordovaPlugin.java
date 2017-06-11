package com.applozic.phonegap;

import android.content.Context;
import android.content.Intent;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegisterUserClientService;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.MobiComUserPreference;
import com.applozic.mobicomkit.api.account.user.PushNotificationTask;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserClientService;
import com.applozic.mobicomkit.api.account.user.UserDetail;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.api.account.user.UserService;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicomkit.api.notification.MobiComPushReceiver;
import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.contact.AppContactService;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicomkit.uiwidgets.people.activity.MobiComKitPeopleActivity;
import com.applozic.mobicommons.json.AnnotationExclusionStrategy;
import com.applozic.mobicommons.json.GsonUtils;
import com.applozic.mobicommons.people.channel.Channel;
import com.applozic.mobicommons.people.contact.Contact;
import com.applozic.mobicomkit.uiwidgets.ApplozicSetting;
import com.applozic.audiovideo.activity.AudioCallActivityV2;
import com.applozic.audiovideo.activity.VideoActivity;
import com.applozic.mobicomkit.ApplozicClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ApplozicCordovaPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        Context context = cordova.getActivity().getApplicationContext();
        String response = "success";

        final CallbackContext callback = callbackContext;

        if (action.equals("login")) {
            String userJson = data.getString(0);
            User user = (User) GsonUtils.getObjectFromJson(userJson, User.class);

            Applozic.init(context, user.getApplicationId());

            /*List<String> featureList =  new ArrayList<>();
            featureList.add(User.Features.IP_AUDIO_CALL.getValue());// FOR AUDIO
            featureList.add(User.Features.IP_VIDEO_CALL.getValue());// FOR VIDEO
            user.setFeatures(featureList); // ADD FEATURES*/

            UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

                @Override
                public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                    //After successful registration with Applozic server the callback will come her

                    ApplozicClient.getInstance(context).setHandleDial(true).setIPCallEnabled(true);
                    Map<ApplozicSetting.RequestCode, String> activityCallbacks = new HashMap<ApplozicSetting.RequestCode, String>();
                    activityCallbacks.put(ApplozicSetting.RequestCode.AUDIO_CALL, AudioCallActivityV2.class.getName());
                    activityCallbacks.put(ApplozicSetting.RequestCode.VIDEO_CALL, VideoActivity.class.getName());
                    ApplozicSetting.getInstance(context).setActivityCallbacks(activityCallbacks);


                    callback.success(GsonUtils.getJsonFromObject(registrationResponse, RegistrationResponse.class));
                }

                @Override
                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                    //If any failure in registration the callback  will come here
                    callback.error(GsonUtils.getJsonFromObject(registrationResponse, RegistrationResponse.class));
                }};

            new UserLoginTask(user, listener, context).execute((Void) null);
        } else if (action.equals("registerPushNotification")) {
            PushNotificationTask pushNotificationTask = null;
            PushNotificationTask.TaskListener listener=  new PushNotificationTask.TaskListener() {
                @Override
                public void onSuccess(RegistrationResponse registrationResponse) {
                    callback.success(GsonUtils.getJsonFromObject(registrationResponse, RegistrationResponse.class));
                }
                @Override
                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                    callback.error(GsonUtils.getJsonFromObject(registrationResponse, RegistrationResponse.class));
                }
            };
            pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(), listener, context);
            pushNotificationTask.execute((Void)null);
        } else if (action.equals("isLoggedIn")) {
            callbackContext.success(String.valueOf(MobiComUserPreference.getInstance(context).isLoggedIn()));
        } else if (action.equals("getUnreadCount")) {
            callback.success(String.valueOf(new MessageDatabaseService(context).getTotalUnreadCount()));
        } else if (action.equals("updatePushNotificationToken")) {
            if (MobiComUserPreference.getInstance(context).isRegistered()) {
                try {
                    new RegisterUserClientService(context).updatePushNotificationId(data.getString(0));
                    callback.success(response);
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
        } else if (action.equals("showAllRegisteredUsers")) {
            if ("true".equals(data.getString(0))) {
                ApplozicSetting.getInstance(context).enableRegisteredUsersContactCall();
            }
        } else if (action.equals("addContact")) {
            String contactJson = data.getString(0);
            UserDetail userDetail = (UserDetail) GsonUtils.getObjectFromJson(contactJson, UserDetail.class);
            UserService.getInstance(context).processUser(userDetail);
            callback.success(response);
        } else if (action.equals("updateContact")) {
            String contactJson = data.getString(0);
            Contact contact = (Contact) GsonUtils.getObjectFromJson(contactJson, Contact.class);
            AppContactService appContactService = new AppContactService(context);
            appContactService.updateContact(contact);
            callback.success(response);
        } else if (action.equals("removeContact")) {
            String contactJson = data.getString(0);
            Contact contact = (Contact) GsonUtils.getObjectFromJson(contactJson, Contact.class);
            AppContactService appContactService = new AppContactService(context);
            appContactService.deleteContact(contact);
            callback.success(response);
        } else if (action.equals("addContacts")) {
            String contactJson = data.getString(0);
            Gson gson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
            UserDetail[] userDetails = (UserDetail[]) gson.fromJson(contactJson, UserDetail[].class);
            for (UserDetail userDetail: userDetails) {
                UserService.getInstance(context).processUser(userDetail);
            }
            callback.success(response);
        } else if (action.equals("processPushNotification")) {
            Map<String, String> pushData = new HashMap<String, String>();
            //convert data to pushData
            System.out.println(data);
            if (MobiComPushReceiver.isMobiComPushNotification(pushData)) {
                MobiComPushReceiver.processMessageAsync(context, pushData);
            }
            callback.success(response);
        } else if (action.equals("createGroup")) {
            ChannelInfo channelInfo = (ChannelInfo) GsonUtils.getObjectFromJson(data.getString(0), ChannelInfo.class);
            Channel channel = ChannelService.getInstance(context).createChannel(channelInfo);
            callback.success(GsonUtils.getJsonFromObject(channel, Channel.class));
        } else if (action.equals("logout")) {
            new UserClientService(cordova.getActivity()).logout();
            callbackContext.success(response);
        } else {
            return false;
        }

        return true;
    }

}