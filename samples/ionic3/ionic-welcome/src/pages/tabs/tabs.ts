import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

import { AboutPage } from '../about/about';
import { ContactPage } from '../contact/contact';
import { HomePage } from '../home/home';
import { Welcome } from '../welcome/welcome';
import {Platform} from 'ionic-angular';

@Component({
  templateUrl: 'tabs.html'
})
export class TabsPage {

  tab1Root = HomePage;
  tab2Root = AboutPage;
  tab3Root = ContactPage;

  constructor(public navCtrl: NavController, public navParams: NavParams, public platform : Platform) {
 
    platform.ready().then(() => {
      
    applozic.isLogged((result) =>{
if(result === "true"){
  console.log("usertest : User is logged in : " + result);
this.navCtrl.push(HomePage);
}else{
  console.log("usertest : User is not logged in : " + result);
this.navCtrl.push(Welcome);
}
    },()=>{

    });
  });
  }
}

declare var applozic: any;
