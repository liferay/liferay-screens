<#if entries?has_content>
	<#list entries as entry>
		<#assign assetRenderer = entry.getAssetRenderer()>
  <div class="flex-container item-blog" data-id="${entry.getAssetRenderer().getClassPK()}">
      <div class="flex-item-center item-img-container">
          <img class="item-img" alt="" style="width:150px"
               src="${assetRenderer.getAssetObject().getSmallImageURL(themeDisplay)}"/>
      </div>
      <div class="flex-item-expand flex-item-center item-text">
          <normal class="item-author">${dateUtil.getDate(entry.getPublishDate(), "MMMM dd, yyyy", locale)}
              - ${entry.getUserName()} </normal>
          </br>
          <normal class="item-title">${entry.getTitle()} </normal>
      </div>
      <svg class="lexicon-icon flex-item-center" focusable="false" role="img" title="">
          <use data-href="/o/classic-theme/images/lexicon/icons.svg#angle-right"/>
      </svg>
  </div>
	</#list>
</#if>