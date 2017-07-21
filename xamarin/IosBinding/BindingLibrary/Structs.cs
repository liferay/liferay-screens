using System;
using ObjCRuntime;

namespace LiferayScreens
{
    [Native]
    public enum AuthType : ulong
    {
        Basic = 1,
        OAuth = 2,
        Cookie = 3
    }

    [Native]
    public enum LiferayMediaType : ulong
    {
        Camera = 0,
        Video = 1,
        Image = 2,
        ImageEdited = 3
    }

    [Native]
    public enum LiferayServerVersion : ulong
    {
        LiferayServerVersionV62 = 62,
        LiferayServerVersionV70 = 70
    }

    [Native]
    public enum ProgressCloseMode : ulong
    {
        ManualClose = 0,
        ManualClose_TouchClosable = 1,
        Autoclose = 2,
        Autoclose_TouchClosable = 3
    }

    [Native]
    public enum ProgressMessageType : ulong
    {
        Working = 0,
        Failure = 1,
        Success = 2
    }

    [Native]
    public enum ProgressSpinnerMode : ulong
    {
        IndeterminateSpinner = 0,
        DeterminateSpinner = 1,
        NoSpinner = 2
    }

    [Native]
    public enum SyncConflictResolution : ulong
    {
        UseRemote = 0,
        UseLocal = 1,
        Discard = 2,
        Ignore = 3
    }

}

