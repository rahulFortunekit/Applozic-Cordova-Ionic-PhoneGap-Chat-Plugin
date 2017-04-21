//
//  ALChatManager.m
//  applozicdemo
//
//  Created by Adarsh on 28/12/15.
//  Copyright © 2015 applozic Inc. All rights reserved.
//

#import "ALChatManagerCDVPlugin.h"
#import "alChatManager.h"
#import <Applozic/ALUserDefaultsHandler.h>
#import <Applozic/ALMessageClientService.h>
#import <Applozic/ALApplozicSettings.h>
#import <Applozic/ALChatViewController.h>
#import <Applozic/ALMessage.h>
#import <Applozic/ALNewContactsViewController.h>
#import <Applozic/ALPushAssist.h>



@implementation ALChatManagerCDVPlugin

-(instancetype)init {
    return [self initWithApplicationKey:APPLICATION_ID];
}

-(instancetype)initWithApplicationKey:(NSString *)applicationKey;
{
    self = [super init];
    if (self)
    {
        self.alChatManager = [[ALChatManager alloc] initWithApplicationId:[self getApplicationKey]];
    }
    
    return self;
}

-(NSString *)getApplicationKey
{
    NSString * appKey = [ALUserDefaultsHandler getApplicationKey];
    NSLog(@"APPLICATION_KEY :: %@",appKey);
    return appKey ? appKey : APPLICATION_ID;
}

- (void)registerALUser:(CDVInvokedUrlCommand*)command
{
    NSString *jsonStr = [[command arguments] objectAtIndex:0];
    jsonStr = [jsonStr stringByReplacingOccurrencesOfString:@"\\\"" withString:@"\""];
    jsonStr = [NSString stringWithFormat:@"%@",jsonStr];
    //NSObject *arg = [jsonStr JSONValue];

    ALUser * alUser = [[ALUser alloc] initWithJSONString:jsonStr];
    [alChatManager registerUser: alUser];

    //Todo: replace msg with the server response
    NSString* msg = [NSString stringWithFormat: @"Hello, %@", jsonStr];
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:msg];

    [alChatManager.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)openChat:(CDVInvokedUrlCommand*)command
{
    NSString* name = [[command arguments] objectAtIndex:0];
    NSString* msg = [NSString stringWithFormat: @"Hello, %@", name];

    ALPushAssist * assitant = [[ALPushAssist alloc] init];
    [alChatManager launchChat:[assitant topViewController]];

    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:msg];

    [alChatManager.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)chatDemo:(CDVInvokedUrlCommand*)command
{

    NSString* name = [[command arguments] objectAtIndex:0];
    NSString* msg = [NSString stringWithFormat: @"Hello, %@", name];

    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:msg];

    [alChatManager.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end