using System;
using ObjCRuntime;
using Foundation;
using UIKit;
using CoreGraphics;

namespace BindingLibrary
{
    // @protocol BaseScreenletDelegate <NSObject>
    [Protocol, Model]
    [BaseType(typeof(NSObject), Name = "_TtP14LiferayScreens21BaseScreenletDelegate_")]
    interface BaseScreenletDelegate
    {
        // @optional -(Interactor * _Nullable)screenlet:(BaseScreenlet * _Nonnull)screenlet customInteractorForAction:(NSString * _Nonnull)customInteractorForAction withSender:(id _Nullable)withSender __attribute__((warn_unused_result));
        [Export("screenlet:customInteractorForAction:withSender:")]
        [return: NullAllowed]
        Interactor CustomInteractorForAction(BaseScreenlet screenlet, string customInteractorForAction, [NullAllowed] NSObject withSender);
    }
}
