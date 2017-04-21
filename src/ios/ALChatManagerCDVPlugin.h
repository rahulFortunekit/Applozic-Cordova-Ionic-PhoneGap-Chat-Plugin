//
//  ALChatManager.h
//  applozicdemo
//
//  Created by Devashish on 28/12/15.
//  Copyright © 2015 applozic Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Applozic/ALChatLauncher.h>
#import <Applozic/ALUser.h>
#import <Applozic/ALConversationService.h>
#import <Applozic/ALRegisterUserClientService.h>
#import <Cordova/CDV.h>
#import "alChatManager.h"

#define APPLICATION_ID @"applozic-sample-app"


@interface ALChatManagerCDVPlugin : CDVPlugin

@property(nonatomic,strong) ALChatManager * alChatManager;

-(NSString *)getApplicationKey;

- (void) chatDemo:(CDVInvokedUrlCommand*)command;

- (void) registerALUser:(CDVInvokedUrlCommand*)command;

- (void) openChat:(CDVInvokedUrlCommand*)command;

@end