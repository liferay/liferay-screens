using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface IDDLFormViewModel {}

    // @protocol DDLFormViewModel
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface DDLFormViewModel
    {
        // @required @property (nonatomic) BOOL showSubmitButton;
        [Abstract]
        [Export("showSubmitButton")]
        bool ShowSubmitButton { get; set; }

        // @required @property (nonatomic, strong) DDLRecord * _Nullable record;
        [Abstract]
        [NullAllowed, Export("record", ArgumentSemantic.Strong)]
        DDLRecord Record { get; set; }

        // @required @property (readonly, nonatomic) BOOL isRecordEmpty;
        [Abstract]
        [Export("isRecordEmpty")]
        bool IsRecordEmpty { get; }

        // @required -(ValidationError * _Nullable)validateFormWithAutoscroll:(BOOL)autoscroll __attribute__((warn_unused_result));
        //[Abstract]
        //[Export("validateFormWithAutoscroll:")]
        //[return: NullAllowed]
        //ValidationError ValidateFormWithAutoscroll(bool autoscroll);
    }
}
