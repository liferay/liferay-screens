using System;
using ObjCRuntime;
using Foundation;

namespace LiferayScreens
{
    // @interface Interactor : NSObject
    [BaseType(typeof(NSObject))]
    interface Interactor
    {
        // @property (copy, nonatomic) NSString * _Nullable actionName;
        [NullAllowed, Export("actionName")]
        string ActionName { get; set; }

        // @property (copy, nonatomic) void (^ _Nullable)(void) onSuccess;
        [NullAllowed, Export("onSuccess", ArgumentSemantic.Copy)]
        Action OnSuccess { get; set; }

        // @property (copy, nonatomic) void (^ _Nullable)(NSError * _Nonnull) onFailure;
        [NullAllowed, Export("onFailure", ArgumentSemantic.Copy)]
        Action<NSError> OnFailure { get; set; }

        // @property (nonatomic, strong) NSError * _Nullable lastError;
        [NullAllowed, Export("lastError", ArgumentSemantic.Strong)]
        NSError LastError { get; set; }

        // @property (readonly, nonatomic, strong) BaseScreenlet * _Nullable screenlet;
        [NullAllowed, Export("screenlet", ArgumentSemantic.Strong)]
        BaseScreenlet Screenlet { get; }

        // -(instancetype _Nonnull)initWithScreenlet:(BaseScreenlet * _Nullable)screenlet __attribute__((objc_designated_initializer));
        [Export("initWithScreenlet:")]
        [DesignatedInitializer]
        IntPtr Constructor([NullAllowed] BaseScreenlet screenlet);

        // -(void)callOnSuccess;
        [Export("callOnSuccess")]
        void CallOnSuccess();

        // -(void)callOnFailure:(NSError * _Nonnull)error;
        [Export("callOnFailure:")]
        void CallOnFailure(NSError error);

        // -(BOOL)start __attribute__((warn_unused_result));
        [Export("start")]
        bool Start();

        // -(void)cancel;
        [Export("cancel")]
        void Cancel();

        // -(id _Nullable)interactionResult __attribute__((warn_unused_result));
        [NullAllowed, Export("interactionResult")]
        NSObject InteractionResult();
    }
}
