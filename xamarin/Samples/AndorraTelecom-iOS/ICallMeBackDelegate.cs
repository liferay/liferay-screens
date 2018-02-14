namespace AndorraTelecomiOS
{
    public interface ICallMeBackDelegate
    {
        void ShowAlertLegalNotAccepted(CallMeBackView CallMeBackView, string Title, string Message);

        void ShowLegalConditions(CallMeBackView CallMeBackView);
    }
}
