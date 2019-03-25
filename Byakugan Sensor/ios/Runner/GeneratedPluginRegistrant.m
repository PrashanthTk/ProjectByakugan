//
//  Generated file. Do not edit.
//

#import "GeneratedPluginRegistrant.h"
#import <camera/CameraPlugin.h>
#import <flutter_blue/FlutterBluePlugin.h>
#import <vibrate/VibratePlugin.h>

@implementation GeneratedPluginRegistrant

+ (void)registerWithRegistry:(NSObject<FlutterPluginRegistry>*)registry {
  [CameraPlugin registerWithRegistrar:[registry registrarForPlugin:@"CameraPlugin"]];
  [FlutterBluePlugin registerWithRegistrar:[registry registrarForPlugin:@"FlutterBluePlugin"]];
  [VibratePlugin registerWithRegistrar:[registry registrarForPlugin:@"VibratePlugin"]];
}

@end
