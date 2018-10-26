<#if entries?has_content>
	<#list entries as entry>
	<div class="item-doc flex-container" data-extension="${entry.getAssetRenderer().getAssetObject().getExtension()}"
         data-id="${entry.getClassPK()}">
        <img id="docs-image" class="flex-item-center docs-image"
             src="/documents/33300/39232/file.png/c48b31b9-e94a-866a-19d5-f8debacc9ec8"/>
        <normal class="flex-item-expand flex-item-center name-doc">${entry.getTitle(locale)}</normal>
        <svg class="lexicon-icon flex-item-center" focusable="false" role="img" title="">
            <use data-href="/o/classic-theme/images/lexicon/icons.svg#angle-right"/>
        </svg>
    </div>
	</#list>
</#if>