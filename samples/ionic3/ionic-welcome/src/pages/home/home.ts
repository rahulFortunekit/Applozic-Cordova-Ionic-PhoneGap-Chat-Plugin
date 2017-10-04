import { Component } from '@angular/core';
import { NavController, App } from 'ionic-angular';
import {Platform} from 'ionic-angular';


@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  constructor(public navCtrl: NavController, public app: App, platform: Platform) {

  }

  logout(){
    //Api Token Logout
    applozic.logout(()=>{
      const root = this.app.getRootNav();
      root.popToRoot();
    },()=>{});
    
  }

  launchChat(){
    applozic.launchChat(function() {}, function() {});
  }

}

declare var applozic: any;
