using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface DDLFormView : BaseScreenletView <DDLFormViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface DDLFormView : IDDLFormViewModel
    {
        // @property (nonatomic) BOOL showSubmitButton;
        [Export("showSubmitButton")]
        bool ShowSubmitButton { get; set; }

        // @property (nonatomic, strong) DDLRecord * _Nullable record;
        [NullAllowed, Export("record", ArgumentSemantic.Strong)]
        DDLRecord Record { get; set; }

        // @property (readonly, nonatomic) BOOL isRecordEmpty;
        [Export("isRecordEmpty")]
        bool IsRecordEmpty { get; }

        // -(void)refresh;
        [Export("refresh")]
        void Refresh();

        // -(ValidationError * _Nullable)validateFormWithAutoscroll:(BOOL)autoscroll __attribute__((warn_unused_result));
        //[Export("validateFormWithAutoscroll:")]
        //[return: NullAllowed]
        //ValidationError ValidateFormWithAutoscroll(bool autoscroll);

        // -(DDMField * _Nullable)getField:(NSInteger)index __attribute__((warn_unused_result));
        [Export("getField:")]
        [return: NullAllowed]
        DDMField GetField(nint index);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
