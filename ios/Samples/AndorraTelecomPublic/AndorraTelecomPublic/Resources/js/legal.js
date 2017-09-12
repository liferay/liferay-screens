function buildLegal(){
    var newBody = document.createElement('body');
    var legalText = document.getElementById('portlet_com_liferay_journal_content_web_portlet_JournalContentPortlet_INSTANCE_NtIwOgLD7Naz');
    
    document.body = newBody;
    document.body.appendChild(legalText);    
}

buildLegal()
