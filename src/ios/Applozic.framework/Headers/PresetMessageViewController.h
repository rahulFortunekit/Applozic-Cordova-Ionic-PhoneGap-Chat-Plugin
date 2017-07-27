//
//  PresetMessageViewController.h
//  Applozic
//
//  Created by Nitin on 26/07/17.
//  Copyright © 2017 applozic Inc. All rights reserved.
//

#import <UIKit/UIKit.h>


@protocol ALPresetMessageDelegate <NSObject>
@required
-(void) proccessMessage:(NSString *)messageText;
@end

@interface PresetMessageViewController : UIViewController<UITableViewDataSource,
UITableViewDelegate>
{
    
    IBOutlet UITableView *myTableView;
    NSMutableArray *myData;
}

@property (weak, nonatomic) IBOutlet UITableView *mTableView;


@property (nonatomic, weak) id <ALPresetMessageDelegate>alPresetMessageDelegate;

-(void)attachSendTextMessage:(NSString *)messageText;
@end
