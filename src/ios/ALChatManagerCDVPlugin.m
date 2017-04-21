//
//  ALChatManager.m
//  applozicdemo
//
//  Created by Adarsh on 28/12/15.
//  Copyright © 2015 applozic Inc. All rights reserved.
//

#import "ALChatManagerCDVPlugin.h"
#import "ALChatManager.h"
#import <Applozic/ALUserDefaultsHandler.h>
#import <Applozic/ALMessageClientService.h>
#import <Applozic/ALApplozicSettings.h>
#import <Applozic/ALChatViewController.h>
#import <Applozic/ALMessage.h>
#import <Applozic/ALNewContactsViewController.h>
#import <Applozic/ALPushAssist.h>



@implementation ALChatManagerCDVPlugin

- (void)registerALUser:(CDVInvokedUrlCommand*)command
{
    NSString *jsonStr = [[command arguments] objectAtIndex:0];
    jsonStr = [jsonStr stringByReplacingOccurrencesOfString:@"\\\"" withString:@"\""];
    jsonStr = [NSString stringWithFormat:@"%@",jsonStr];
    //NSObject *arg = [jsonStr JSONValue];

    ALUser * alUser = [[ALUser alloc] initWithJSONString:jsonStr];
    self.alChatManager = [[ALChatManager alloc] initWithApplicationKey:alUser.applicationId];

    [[self alChatManager] registerUser:alUser];

    //Todo: replace msg with the server response
    NSString* msg = [NSString stringWithFormat: @"Hello, %@", jsonStr];
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:msg];

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)openChat:(CDVInvokedUrlCommand*)command
{
    self.alChatManager = [[ALChatManager alloc] init];

    NSString* name = [[command arguments] objectAtIndex:0];
    NSString* msg = [NSString stringWithFormat: @"Hello, %@", name];

    ALPushAssist * assitant = [[ALPushAssist alloc] init];
    [[self alChatManager] launchChat:[assitant topViewController]];

    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:msg];

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)chatDemo:(CDVInvokedUrlCommand*)command
{
    self.alChatManager = [[ALChatManager alloc] init];

    NSString* name = [[command arguments] objectAtIndex:0];
    NSString* msg = [NSString stringWithFormat: @"Hello, %@", name];

    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:msg];

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end