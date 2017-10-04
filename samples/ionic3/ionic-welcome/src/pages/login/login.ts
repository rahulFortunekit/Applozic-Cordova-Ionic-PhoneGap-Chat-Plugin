import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { AlertController, Platform } from 'ionic-angular';
import { HomePage } from '../home/home';
import { Push, PushObject, PushOptions } from '@ionic-native/push';

/**
 * Generated class for the Login page.
 *
 * See http://ionicframework.com/docs/components/#navigation for more info
 * on Ionic pages and navigation.
 */
@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})

export class Login {

  userId:string = '';
  password:string = '';

  constructor(public navCtrl: NavController, 
    public navParams: NavParams, 
    public platform: Platform, 
    public alertCtrl : AlertController,
    public push : Push) {

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad Login');
  }

  login(){
    // Your app login API web service call triggers
    var alUser = {
        'userId' : this.userId,   //Replace it with the userId of the logged in user
        'password' : this.password,  //Put password here
        'authenticationTypeId' : 1,
        'applicationId' : 'applozic-sample-app',  //replace "applozic-sample-app" with Application Key from Applozic Dashboard
        'deviceApnsType' : 0    //Set 0 for Development and 1 for Distribution (Release)
    };

    applozic.login(alUser, ()=> {
    console.log("In js onsuccess of login");
       /*applozic.registerPushNotification(function() {
       console.log("in js onsuccess of register");
       }, function(){
       console.log("in js on faliure of register");
       });*/
       this.initPushNotification();
       //this.initPush();
       //applozic.registerPushNotification(null,()=>{},()=>{});
       this.navCtrl.push(HomePage);
       //applozic.launchChat(function() {}, function() {});
    }, function() {});
    //this.navCtrl.push(TabsPage, {}, {animate: false});
  }

  initPushNotification() {
    if (!this.platform.is('cordova')) {
      console.warn('Push notifications not initialized. Cordova is not available - Run in physical device');
      return;
    }
    const options: PushOptions = {
      android: {
        senderID: '195932243324',
        forceShow:true,
      },
      ios: {
        alert: 'true',
        badge: false,
        sound: 'true'
      },
      windows: {}
    };
    const pushObject: PushObject = this.push.init(options);

    pushObject.on('registration').subscribe((data: any) => {
      console.log('device token -> ' + data.registrationId);
      applozic.updatePushNotificationToken(data.registrationId,()=>{},()=>{});
      //applozic.registerPushNotification(data.registrationId,()=>{},()=>{});
    });


    pushObject.on('notification').subscribe((data: any) => {
      console.log('pushtestmessage -> ' + JSON.stringify(data));
      applozic.processPushNotification(JSON.stringify(data.additionalData.message),()=>{},()=>{});

      //if user using app and push notification comes

      if (data.additionalData.foreground) {
        // if application open, show popup
        applozic.processPushNotification(JSON.stringify(data),()=>{},()=>{});
      } else {
        //if user NOT using app and push notification comes
        //TODO: Your logic on click of push notification directly
        console.log('message background -> ' + JSON.stringify(data));
        console.log('Push notification clicked');
      }
    });

    pushObject.on('error').subscribe(error => console.error('Error with Push plugin' + error));
  }

 /* initPush(){
    fcmPlugin.onTokenRefresh(function(token){
      applozic.updatePushNotificationToken(token,()=>{},()=>{});
      console.log("Token : " + token);
      alert( token );
  });

  fcmPlugin.onNotification(function(data){
    console.log("Recieved notification : " + JSON.stringify(data));
    if(data.wasTapped){
      //Notification was received on device tray and tapped by the user.
      alert( JSON.stringify(data) );
    }else{
      //Notification was received in foreground. Maybe the user needs to be notified.
      alert( JSON.stringify(data) );
    }
});

}*/
}

declare var applozic: any;
