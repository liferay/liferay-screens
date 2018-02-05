using System;

namespace AndorraTelecomiOS.Util
{
    public static class LanguageHelper
    {
        public static string[] ListLanguages = { "esp", "cat", "fra", "eng" };

        public static string LanguageApp {
            get
            {
                var Language = Java.Util.Locale.Default.Language;
                switch (Language)
                {
                    case "es":
                        return "es_ES";
                    case "ca":
                        return "ca_ES";
                    case "fr":
                        return "fr_FR";
                    default:
                        return "en_US";
                }
            }
        }

        public enum Pages { Index, Mobile, Roaming, Paquete69, Optima, Legal, Map };

        /* Methods */

        public static string GetPlid(Pages Page)
        {
            switch (Page)
            {
                case Pages.Mobile:
                    return "839763";
                case Pages.Roaming:
                    return "841024";
                case Pages.Paquete69:
                    return "108620";
                case Pages.Optima:
                    return "106754";
                case Pages.Legal:
                    return "34691";
                case Pages.Map:
                    return "33998";
                default:
                    return "34684";
            }
        }

        public static string GetPathName(Pages Page)
        {
            switch (Page)
            {
                case Pages.Mobile:
                    return "/particulars/telefonia-mobil/forfets-mobiland/contracte/";
                case Pages.Roaming:
                    return "/particulars/telefonia-mobil/serveis-complementaris/forfet-roaming/";
                case Pages.Paquete69:
                    return "/particulars/internet-fixa/internet-i-telefonia-fixa/paquet-69/";
                case Pages.Optima:
                    return "/particulars/internet-fixa/internet-i-telefonia-fixa/optima/";
                case Pages.Legal:
                    return "/avis-legal/";
                case Pages.Map:
                    return "/web/comercial/on-som";
                default:
                    return "/particulars/inici/";
            }
        }

        public static string Url(Pages Page)
        {
            var url = "https://www.andorratelecom.ad/c/portal/update_language?p_l_id=" 
                + GetPlid(Page) + "&redirect="
                + GetPathName(Page) + "&languageId=" 
                + LanguageApp + "";
            
            Console.WriteLine("URL: " + url);

            return url;
        }
    }
}