<#macro relatedKeywordMacro relatedKeywordList source>
	<#assign limit = 4/>
		Related ${source} Keywords
		<ul id="${source}list" class="spadded">
			<#assign x = 0/>
			<#list relatedKeywordList as related>
				<#assign relatedName = related.localName?replace(' ','_')>
				<#if x == limit><#break/></#if>
				<li><a href=javascript:getResults(null,null,"${relatedName}","explore")>${relatedName?replace('_',' ')}</a></li>
				<#assign x = x + 1>
			</#list>
		</ul>
		<#if relatedKeywordList?size &gt; limit>
			<a id="${source}button" href="">more</a><br>
		</#if>
		<br/>
		
	<script language="javascript">
		function moreLessButtonHandler() {
		   $("#${source}button", this).click(function(e) {
		     // disable regular form click
		     e.preventDefault();
		     if(document.getElementById("${source}button").innerHTML == "more")
		     {
		     	var a="<#list relatedKeywordList as related><#assign relatedName = related.localName?replace(' ','_')><li><a href=javascript:getResults(null,null,'${relatedName}','explore')>${relatedName?replace('_', ' ')}</a></li></#list>";
		     	document.getElementById("${source}list").innerHTML=a;
		     	$(this).attr({ 'innerHTML': 'less' });
			 }
			 else
			 {
			 	var a="<#assign x=0><#list relatedKeywordList as related><#assign relatedName = related.localName?replace('_',' ')><#if x == limit><#break/></#if><li><a href=javascript:getResults(null,null,'${relatedName}','explore')>${relatedName?replace('_', ' ')}</a></li><#assign x=x+1 /></#list>";
			 	document.getElementById("${source}list").innerHTML=a;
			 	$(this).attr({ 'innerHTML': 'more' });		 	
			 }    
		     });
		 }		 
		 
		 $(document).ready(moreLessButtonHandler);
	</script>
</#macro>