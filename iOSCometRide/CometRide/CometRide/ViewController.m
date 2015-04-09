//
//  ViewController.m
//  CometRide
//
//  Created by Danny Matthew Sundaresan on 4/8/15.
//  Copyright (c) 2015 Danny Matthew Sundaresan. All rights reserved.
//

#import "ViewController.h"
#define URL_COMETRIDE @"http://104.154.93.11/CometRide/"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    CGRect myFrame = self.view.frame;
    myFrame.origin.y = myFrame.origin.y + 20;
    myFrame.size.height = myFrame.size.height - 20;
    UIWebView *webView = [[UIWebView alloc] initWithFrame:myFrame];
    NSURLRequest *requestObj = [NSURLRequest requestWithURL:[NSURL URLWithString:URL_COMETRIDE]];
    [webView loadRequest:requestObj];
    
    [self.view addSubview:webView];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
