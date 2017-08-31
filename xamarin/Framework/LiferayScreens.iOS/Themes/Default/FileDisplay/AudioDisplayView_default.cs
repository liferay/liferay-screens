using AVFoundation;
using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface AudioDisplayView_default : BaseScreenletView <FileDisplayViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface AudioDisplayView_default : IFileDisplayViewModel
    {
        // @property (nonatomic) float volume;
        [Export("volume")]
        float Volume { get; set; }

        // @property (nonatomic) NSInteger numberOfLoops;
        [Export("numberOfLoops")]
        nint NumberOfLoops { get; set; }

        // @property (copy, nonatomic) NSURL * _Nullable url;
        [NullAllowed, Export("url", ArgumentSemantic.Copy)]
        NSUrl Url { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable title;
        [NullAllowed, Export("title")]
        string Title { get; set; }

        // @property (nonatomic, strong) AVAudioPlayer * _Nullable audio;
        [NullAllowed, Export("audio", ArgumentSemantic.Strong)]
        AVAudioPlayer Audio { get; set; }

        // @property (nonatomic, strong) NSTimer * _Nullable timer;
        [NullAllowed, Export("timer", ArgumentSemantic.Strong)]
        NSTimer Timer { get; set; }

        // @property (nonatomic, strong) NSTimer * _Nullable duration;
        [NullAllowed, Export("duration", ArgumentSemantic.Strong)]
        NSTimer Duration { get; set; }

        // -(void)onHide;
        [Export("onHide")]
        void OnHide();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
