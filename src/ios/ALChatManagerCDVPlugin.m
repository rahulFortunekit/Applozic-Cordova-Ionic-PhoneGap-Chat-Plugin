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

-(NSString *)getApplicationKey
{
    NSString * appKey = [ALUserDefaultsHandler getApplicationKey];
    NSLog(@"APPLICATION_KEY :: %@",appKey);
    return appKey ? appKey : APPLICATION_ID;
}

- (ALChatManager *)getALChatManager:(NSString*)applicationId
{
    if (!applicationId) {
        applicationId = [self getApplicationKey];
    }
    return [[ALChatManager alloc] initWithApplicationKey:applicationId];
}

- (void)login:(CDVInvokedUrlCommand*)command
{
    NSString *jsonStr = [[command arguments] objectAtIndex:0];
    jsonStr = [jsonStr stringByReplacingOccurrencesOfString:@"\\\"" withString:@"\""];
    jsonStr = [NSString stringWithFormat:@"%@",jsonStr];
    //NSObject *arg = [jsonStr JSONValue];

    ALUser * alUser = [[ALUser alloc] initWithJSONString:jsonStr];
    ALChatManager *alChatManager = [self getALChatManager:alUser.applicationId];

    [alChatManager registerUser:alUser];
    [alChatManager registerUserWithCompletion:alUser withHandler:^(ALRegistrationResponse *rResponse, NSError *error) {
        NSString* msg = nil;
        if (!error) {
             msg = [NSString stringWithFormat: @"%@", rResponse];
        } else {
            msg = [NSString stringWithFormat: @"%@", error];
        }
        CDVPluginResult* result = [CDVPluginResult
                                   resultWithStatus:CDVCommandStatus_OK
                                   messageAsString:msg];
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }];
}

- (void) launchChatWithUserId:(CDVInvokedUrlCommand*)command
{
    ALChatManager *alChatManager = [self getALChatManager: [self getApplicationKey]];
    NSString* userId = [[command arguments] objectAtIndex:0];
    
    ALPushAssist * assitant = [[ALPushAssist alloc] init];
    [alChatManager launchChatForUserWithDisplayName:userId
                                      withGroupId:nil  //If launched for group, pass groupId(pass userId as nil)
                               andwithDisplayName:nil //Not mandatory, if receiver is not already registered you should pass Displayname.
                            andFromViewController:[assitant topViewController]];
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"success"];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void) launchChatWithGroupId:(CDVInvokedUrlCommand*)command
{
    ALChatManager *alChatManager = [self getALChatManager: [self getApplicationKey]];
    NSNumber* groupId = [[command arguments] objectAtIndex:0];
    
    ALPushAssist * assitant = [[ALPushAssist alloc] init];
    [alChatManager launchChatForUserWithDisplayName:nil
                                        withGroupId:groupId  //If launched for group, pass groupId(pass userId as nil)
                                 andwithDisplayName:nil //Not mandatory, if receiver is not already registered you should pass Displayname.
                              andFromViewController:[assitant topViewController]];
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"success"];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void) launchChatWithClientGroupId:(CDVInvokedUrlCommand*)command
{
    ALChatManager *alChatManager = [self getALChatManager: [self getApplicationKey]];
    NSString* clientGroupId = [[command arguments] objectAtIndex:0];
    
    ALPushAssist * assitant = [[ALPushAssist alloc] init];
    [alChatManager launchChatForUserWithDisplayName:nil
                                        withGroupId:nil  //If launched for group, pass groupId(pass userId as nil)
                                 andwithDisplayName:nil //Not mandatory, if receiver is not already registered you should pass Displayname.
                              andFromViewController:[assitant topViewController]];
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:@"success"];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)logout:(CDVInvokedUrlCommand*)command
{
    ALRegisterUserClientService * alUserClientService = [[ALRegisterUserClientService alloc]init];
    if([ALUserDefaultsHandler getDeviceKeyString]) {
        
        [alUserClientService logoutWithCompletionHandler:^(ALAPIResponse *response, NSError *error) {
            CDVPluginResult* result = [CDVPluginResult
                                       resultWithStatus:CDVCommandStatus_OK
                                       messageAsString:@"success"];
            [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
        }];
    }
}

@end