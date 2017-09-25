<#list entries as entry>
	<div class="item_gallery" data-id="${entry.getFileEntryId()}" style="background-image:url(${dlUtil.getPreviewURL(entry, entry.getFileVersion(), themeDisplay, "")})">
	</div>
</#list>