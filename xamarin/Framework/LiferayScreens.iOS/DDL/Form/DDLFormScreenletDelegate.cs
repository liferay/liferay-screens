using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol DDLFormScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface DDLFormScreenletDelegate
    {
        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onFormLoaded:(DDLRecord * _Nonnull)record;
        [Export("screenlet:onFormLoaded:")]
        void OnFormLoaded(DDLFormScreenlet screenlet, DDLRecord record);

        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onFormLoadError:(NSError * _Nonnull)error;
        [Export("screenlet:onFormLoadError:")]
        void OnFormLoadError(DDLFormScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onRecordLoaded:(DDLRecord * _Nonnull)record;
        [Export("screenlet:onRecordLoaded:")]
        void OnRecordLoaded(DDLFormScreenlet screenlet, DDLRecord record);

        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onRecordLoadError:(NSError * _Nonnull)error;
        [Export("screenlet:onRecordLoadError:")]
        void OnRecordLoadError(DDLFormScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onFormSubmitted:(DDLRecord * _Nonnull)record;
        [Export("screenlet:onFormSubmitted:")]
        void OnFormSubmitted(DDLFormScreenlet screenlet, DDLRecord record);

        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onFormSubmitError:(NSError * _Nonnull)error;
        [Export("screenlet:onFormSubmitError:")]
        void OnFormSubmitError(DDLFormScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onDocumentFieldUploadStarted:(DDMFieldDocument * _Nonnull)field;
        [Export("screenlet:onDocumentFieldUploadStarted:")]
        void OnDocumentFieldUploadStarted(DDLFormScreenlet screenlet, DDMFieldDocument field);

        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onDocumentField:(DDMFieldDocument * _Nonnull)field uploadedBytes:(uint64_t)bytes totalBytes:(uint64_t)total;
        [Export("screenlet:onDocumentField:uploadedBytes:totalBytes:")]
        void OnDocumentField(DDLFormScreenlet screenlet, DDMFieldDocument field, ulong bytes, ulong total);

        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onDocumentField:(DDMFieldDocument * _Nonnull)field uploadResult:(NSDictionary<NSString *,id> * _Nonnull)result;
        [Export("screenlet:onDocumentField:uploadResult:")]
        void OnDocumentField(DDLFormScreenlet screenlet, DDMFieldDocument field, NSDictionary<NSString, NSObject> result);

        // @optional -(void)screenlet:(DDLFormScreenlet * _Nonnull)screenlet onDocumentField:(DDMFieldDocument * _Nonnull)field uploadError:(NSError * _Nonnull)error;
        [Export("screenlet:onDocumentField:uploadError:")]
        void OnDocumentField(DDLFormScreenlet screenlet, DDMFieldDocument field, NSError error);
    }
}
