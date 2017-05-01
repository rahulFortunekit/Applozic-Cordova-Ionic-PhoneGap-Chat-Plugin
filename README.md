# Applozic Cordova PhoneGap Chat Plugin

Applozic powers real time messaging across any device, any platform & anywhere in the world. Integrate our simple SDK to engage your users with image, file, location sharing and audio/video conversations.

Signup at https://www.applozic.com/signup.html to get the application key.



## Using
    
Install the plugin

    $ cordova plugin add https://github.com/AppLozic/Applozic-Cordova-PhoneGap-Chat-Plugin.git
    

## Android
Open /platforms/android/ folder in Android Studio.
If you see gradle wrapper error then open gradle/wrapper/gradle-wrapper.properties
Change distributionUrl to point to 2.14.1-all
distributionUrl=https\://services.gradle.org/distributions/gradle-2.14.1-all.zip


Create a XML resource file in xml directory as provider_paths and paste the below code

```
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path name="files" path="."/>
</paths>
```



## iOS

Open /platforms/ios/Applozic.xcodeproj in Xcode.
Verify if the Applozic.framework is added in "Embedded Binaries". If not, then add it to the "Embedded Binaries" by clicking the + button under Project General Settings -> Embedded Binaries.
Remove duplicate entry from "Linked Framework and Libraries" if any.



### Steps to integrate:


#### Login/Register User
```js
    var alUser = {
            'userId' : userId,   //Replace it with the userId of the logged in user
            'password' : password,  //Put password here
            'authenticationTypeId' : 1,
            'applicationId' : 'applozic-sample-app'
        };

   applozic.login(alUser, function() {
        		applozic.launchChat(function() {}, function() {});
        	}, function() {});
```

#### Launch Chat


##### Main Chat screen

```
applozic.launchChat(function() {console.log("success");}, function () {console.log("error");});
```

##### Launch Chat with a specific User

```
applozic.launchChatWithUserId(userId, function() {console.log("success");}, function () {console.log("error");});
```

##### Launch Chat with specific Group 

```
applozic.launchChatWithGroupId(groupId, function() {console.log("success");}, function () {console.log("error");});
```


#### Contact List

```
applozic.showAllRegisteredUsers(false, function() {}, function() {});
               
var contacts = [
                    {'userId' : 'adarsh', 'displayName' : 'Adarsh Kumar'}, 
                    {'userId' : 'ranjeet', 'displayName' : 'Ranjeet Priyadarshi'}
                ];
applozic.addContacts(contacts, function() {}, function() {});
```

#### Logout

```
applozic.logout(function() {console.log("success");}, function () {console.log("error");});
```



Install iOS or Android platform

    cordova platform add ios
    cordova platform add android
    
Run the code

    cordova run 

## More Info

For more information on setting up Cordova see [the documentation](http://cordova.apache.org/docs/en/latest/guide/cli/index.html)

For more info on plugins see the [Plugin Development Guide](http://cordova.apache.org/docs/en/latest/guide/hybrid/plugins/index.html)
