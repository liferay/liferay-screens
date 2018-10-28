<#if entries?has_content>
	<#list entries as entry>
		<#if entry.getAssetRenderer().getClassName() == 'com.liferay.blogs.kernel.model.BlogsEntry'>
			<#if entry.getAssetRenderer().getClassPK()?string == request.getParameter("id")>
			<#-- START - BLOG ENTRY -->
        <div class="img-content-blog" style="text-align: center;">
            <img alt="" class="img-blog" id="img-header"
                 src="${entry.getAssetRenderer().getAssetObject().getSmallImageURL(themeDisplay)}"/>
        </div> </br>
        <div class="flex-container author-content">
            <div class="flex-item-center" style="width:50px; height:50px; overflow: hidden; border-radius: 10%;">
                <@liferay_ui["user-portrait"]
                userId=entry.getUserId()
                />
            </div>
            <div class="flex-item-expand flex-item-center"
                 style="font-style:normal; margin-left:15px; margin-right:15px">
                <div style="font-weight:900">${dateUtil.getDate(entry.getPublishDate(), "MMMM dd, yyyy", locale)}</div>
		        ${entry.getUserName()}
            </div>
        </div> </br>
        <p style="font-weight:900">${entry.getTitle(locale)}</p>
        <p>${entry.getAssetRenderer().getAssetObject().getContent()}</p>
			<#-- FINISH - BLOG ENTRY -->
			</#if>
		<#elseif entry.getAssetRenderer().getClassName() == 'com.liferay.document.library.kernel.model.DLFileEntry'>
		<#-- START - DLFILE ENTRY -->
			<#if entry.getAssetRenderer().getClassPK()?string == request.getParameter("id")>
				<#if (entry.getMimeType() == 'image/jpeg') || (entry.getMimeType() == 'image/jpg') || (entry.getMimeType() == 'image/png')>
                <div class="img-content">
                    <img alt="" src="${entry.getAssetRenderer().getURLImagePreview(renderRequest)}"/>
                </div>
					<@liferay_ui["ratings"]
					className=entry.getClassName()
					classPK=entry.getClassPK()
					/>
				<#elseif  entry.getMimeType() == 'application/pdf'>
            <img width="90%" height=50% src="${entry.getAssetRenderer().getURLDownload(themeDisplay)}"/>
            <form>
                <button formaction="${entry.getAssetRenderer().getURLDownload(themeDisplay)}">View PDF</button>
            </form>
				<#elseif  entry.getMimeType() == 'video/mp4'>
            <video poster preload="true" controls autoplay id="video" autobuffer height="100%" width="100%">
                <source src="${entry.getAssetRenderer().getURLDownload(themeDisplay)}" type="video/mp4">
            </video>
					<@liferay_ui["ratings"]
					className=entry.getClassName()
					classPK=entry.getClassPK()
					/>
				<#else>
					${entry.getMimeType()}
				</#if>
			</#if>
		<#-- FINISH - DLFILE ENTRY -->
		</#if>
	</#list>
</#if>