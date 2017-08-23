using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface DDMStructure : NSObject <NSCoding>
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface DDMStructure : INSCoding
    {
        // @property (copy, nonatomic) NSArray<DDMField *> * _Nonnull fields;
        [Export("fields", ArgumentSemantic.Copy)]
        DDMField[] Fields { get; set; }

        // @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
        [Export("attributes", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> Attributes { get; }

        // @property (readonly, copy, nonatomic) NSLocale * _Nonnull locale;
        [Export("locale", ArgumentSemantic.Copy)]
        NSLocale Locale { get; }

        // -(DDMField * _Nullable)objectForKeyedSubscript:(NSString * _Nonnull)fieldName __attribute__((warn_unused_result));
        [Export("objectForKeyedSubscript:")]
        [return: NullAllowed]
        DDMField ObjectForKeyedSubscript(string fieldName);

        // @property (readonly, copy, nonatomic) NSString * _Nonnull debugDescription;
        [Export("debugDescription")]
        string DebugDescription { get; }

        // -(instancetype _Nonnull)initWithFields:(NSArray<DDMField *> * _Nonnull)fields locale:(NSLocale * _Nonnull)locale attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithFields:locale:attributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(DDMField[] fields, NSLocale locale, NSDictionary<NSString, NSObject> attributes);

        // -(instancetype _Nullable)initWithXsd:(NSString * _Nonnull)xsd locale:(NSLocale * _Nonnull)locale attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
        [Export("initWithXsd:locale:attributes:")]
        IntPtr Constructor(string xsd, NSLocale locale, NSDictionary<NSString, NSObject> attributes);

        // -(instancetype _Nullable)initWithJson:(NSString * _Nonnull)json locale:(NSLocale * _Nonnull)locale attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
        [Export("initWithJson:locale:attributes:")]
        IntPtr ConstructorJson(string json, NSLocale locale, NSDictionary<NSString, NSObject> attributes);

        // -(instancetype _Nullable)initWithStructureData:(NSDictionary<NSString *,id> * _Nonnull)structureData locale:(NSLocale * _Nonnull)locale;
        [Export("initWithStructureData:locale:")]
        IntPtr Constructor(NSDictionary<NSString, NSObject> structureData, NSLocale locale);

        // -(DDMField * _Nullable)fieldByName:(NSString * _Nonnull)name __attribute__((warn_unused_result));
        [Export("fieldByName:")]
        [return: NullAllowed]
        DDMField FieldByName(string name);

        // -(NSArray<DDMField *> * _Nonnull)fieldsByType:(Class _Nonnull)type __attribute__((warn_unused_result));
        [Export("fieldsByType:")]
        DDMField[] FieldsByType(Class type);
    }
}
