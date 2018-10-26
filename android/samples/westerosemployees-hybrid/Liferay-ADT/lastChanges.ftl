<#if entries?has_content>
	<#list entries as entry>
		<#assign assetRenderer = entry.getAssetRenderer()>
		<#if assetRenderer.getClassName() == 'com.liferay.blogs.kernel.model.BlogsEntry' >
	<div class="flex-container item-last-changes" data-class-name="${assetRenderer.getClassName()}"
         data-extension="blog" data-id="${entry.getAssetRenderer().getClassPK()}">
        <div class="flex-item-center item-img-container">
            <img alt="" class="item-img" style="width:150px"
                 src="${assetRenderer.getAssetObject().getSmallImageURL(themeDisplay)}"/>
        </div>
        <div class="flex-item-expand flex-item-center item-text">
            <normal class="item-description">New blog</normal>
            </br>
            <normal class="item-title">${entry.getTitle()} </normal>
        </div>
    </div>
		<#elseif assetRenderer.getClassName() == 'com.liferay.document.library.kernel.model.DLFileEntry'>
        <div class="flex-container item-last-changes" data-class-name="${assetRenderer.getClassName()}"
             data-extension="${entry.getAssetRenderer().getAssetObject().getExtension()}"
             data-id="${entry.getClassPK()}" data-url="${entry.getUrl()}">
            <div class="flex-item-center item-img-container">
                <img alt="" class="item-icon"
                     src="/documents/33300/39232/file.png/c48b31b9-e94a-866a-19d5-f8debacc9ec8"/>
            </div>
            <div class="flex-item-expand flex-item-center item-text">
                <normal class="item-description">New doc</normal>
                </br>
                <normal class="item-title">${entry.getTitle()} </normal>
            </div>
        </div>
		</#if>
	</#list>
</#if>