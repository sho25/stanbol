<#macro processConstraint original constraint>
	<#assign seqs = constraint.classConstraintOrClassMetaInformationOrPropertyMetaInformation/>  
	<#assign type = constraint.type/>
	  <fieldset>
	  	<legend>${type?replace("_", " ")?capitalize}</legend>	
	  <ul>
    
	<#if type == "COMPLEMENT_OF" || type =="ENUMERATION_OF" || type =="INTERSECTION_OF"|| type =="UNION_OF">
	 <!-- ClassMetaInf or IndividualMetaInf or ClassConstraint or Literal -->
	  <!--<li><b>Type:&nbsp;&nbsp;</b> ${constraint.type}</li>-->
	 <#list seqs as seq>
		 	<#if seq.class.simpleName == "ClassMetaInformation">
		 		<li><b>Class: </b> <a href='${seq.href}'>${seq.URI}</a></li>	
		 	<#elseif seq.class.simpleName == "ClassConstraint">
		 		<li><b>Range: </b> <@processConstraint original=constraint constraint=seq/></li>
		 	<#elseif seq.class.simpleName == "IndividualMetaInformation">
		 		<li><b>Individual: </b> <a href='${seq.href}'>${seq.URI}</a></li>
		 		
	 		<#elseif seq.class.simpleName == "String">
	 			<li><b>Resource: </b> ${seq}</li>
		 	<#else>
		 	</#if>
		 </#list>
	<#elseif type=="ALL_VALUES_FROM" || type=="SOME_VALUES_FROM" || type=="HAS_VALUE">
	<!--[0] Property:(Clickable : PropertyMetaInf | Unclickable : ResourceMetaInf) -->
	<!-- [>0] ((ClassMetaInformation | Class Constraint) | DataRange : (IndividualMetaInformation | Literal) )-->
	 <!--<li><b>Type:&nbsp;&nbsp;</b> ${constraint.type}</li>-->
		 <#if seqs?first.class.simpleName == "ResourceMetaInformationType" >
	 	 		<li><b>On:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>${seqs?first.URI}</li>		
	 	<#else>
	 		<li><b>On:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><a href='${seqs?first.href}'>${seqs?first.URI}</a></li>
	 	</#if>
	 	<#if (seqs?size >1)>
	 	<#if seqs[1].class.simpleName == "IndividualMetaInformation" || seqs[1].class.simpleName	== "String">
 	 		<li class="bordered">Data Range
	 			<ul>
	 	 </#if>
	 	 </#if>
		 <#list seqs as seq>
		 	<#if seq_index != 0>
			 	<#if seq.class.simpleName == "ClassMetaInformation">
			 		<li><b>On Class: </b> <a href='${seq.href}'>${seq.URI}</a></li>	
			 	<#elseif seq.class.simpleName == "ClassConstraint">
			 		<li><b>On Class: </b> <@processConstraint original=constraint constraint=seq/></li>
			 	<#elseif seq.class.simpleName == "IndividualMetaInformation">
			 		<li><b>Value: </b> <a href='${seq.href}'>${seq.URI}</a></li>
			 	<#elseif seq.class.simpleName == "String">
			 		<li><b>Value: </b> ${seq}</li>
			 	<#else>
			 	</#if>
			 </#if>
		 </#list>
		 <#if (seqs?size >1)>
			 <#if seqs[1].class.simpleName == "IndividualMetaInformation" || seqs[1].class.simpleName	== "String">
		 			</ul>
		 	 </#if>
		 </#if>
	<#elseif type=="CARDINALITY" || type="MAX_CARDINALITY" || type=="MIN_CARDINALITY">
	<!--[0] Property:(Clickable : PropertyMetaInf | Unclickable : ResourceMetaInf) -->
	<!--[1] Cardinality : literal-->
	 <!--<li><b>Type:&nbsp;&nbsp;</b> ${constraint.type}</li>-->
	 	<#if seqs?first.class.simpleName == "ResourceMetaInformationType" >
	 		<li><b>On:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>${seqs?first.URI}</li>		
	 	<#else>
	 		<li><b>On:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b><a href='${seqs?first.href}'>${seqs?first.URI}</a></li>
	 	</#if>
	 <li><b>Value:&nbsp;</b>${seqs[1]}</li>
	
	<#else>
	</#if>
  </ul>
  </fieldset>
  <#assign constraint = original>
</#macro>