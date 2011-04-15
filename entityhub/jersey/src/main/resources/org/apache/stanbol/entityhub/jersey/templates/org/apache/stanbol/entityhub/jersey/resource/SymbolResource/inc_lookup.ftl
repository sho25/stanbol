<h4>Subresource /symbol/lookup?id={uri}&create={create}</h4>

<table>
<tbody>
	<tr>
		<th>Description</th>
		<td>This service looks-up Symbols (Entities managed by the Entityhub) based on the parsed URI. The
		parsed ID can be the URI of a Symbol or an Entity of any referenced site.
		<ul>
			<li>If the parsed ID is a URI of a Symbol, than the stored information of the Symbol are returned
				in the requested media type ('accept' header field).</li>
			<li>If the parsed ID is a URI of an already mapped entity, then the existing
				mapping is used to get the according Symbol.</li>
			<li>If "create" is enabled, and the parsed URI is not
				already mapped to a Symbol, than all the currently active referenced sites are searched for an
				Entity with the parsed URI.</li>
			<li>If the configuration of the referenced site allows to create new
				symbols, than a the entity is imported in the Entityhub, a new Symbol and EntityMapping is
				created and the newly created Symbol is returned.</li>
			<li>In case the entity is not found (this also includes if the entity would be available via a
				referenced site, but create=false) a 404 "Not Found" is returned.</li>
			<li>In case the entity is found on a referenced site, but the creation of a new Symbol is not
				allowed a 403 "Forbidden" is returned.</li>
		</ul>
		</td>
	</tr>
	<tr>
		<th>Request</th>
		<td>GET /symbol/lookup?id={uri}&create={create} </td>
	</tr>
	<tr>
		<th>Parameter</th>
		<td><ul><li>id: the id of the entity</li>
    			<li>create: if "true" a new symbol is created if necessary and allowed</li>
    		</ul>
    	</td>
	</tr>
	<tr>
		<th>Produces</th>
		<td>Depends on requested media type</td>
	</tr>
</tbody>
</table>

<h5>Example</h5>

<pre>curl "${it.publicBaseUri}entityhub/symbol/lookup/?id=http://dbpedia.org/resource/Paris"</pre>

<h5>Test</h5>

<ul>
	<li><a href="#" onclick="lookupEntity('http://dbpedia.org/resource/Paris',false); return false;">Lookup symbol for
entity 'http://dbpedia.org/resource/Paris' with create=false</a>.</li>
	<li><a href="#" onclick="lookupEntity('http://dbpedia.org/resource/Paris',true); return false;">Lookup symbol for
entity 'http://dbpedia.org/resource/Paris' with create=true</a>.</li>
</ul>

<script language="javascript">
function lookupEntity(entity, create) {
 $("#lookupEntityResult").hide();
 $("#lookupEntityResultText").text("... waiting for results ...");
 $("#lookupEntityResult").show();
 $.ajax({
   type: "GET",
   url: "${it.publicBaseUri}entityhub/symbol/lookup",
   data: "id=" + entity + "&create=" + create,
   dataType: "text/plain",
   cache: false,
   success: function(data) {
     $("#lookupEntityResultText").text(data);
   },
   error: function(jqXHR, textStatus, errorThrown) {
     $("#lookupEntityResultText").text(jqXHR.statusText + " - " + jqXHR.responseText);
   }
 });		  
}
</script>

<div id="lookupEntityResult" style="display: none">
<p><a href="#" onclick="$('#lookupEntityResult').hide(); return false;">Hide results</a>
<pre id="lookupEntityResultText">... waiting for results ...</pre>
</div>
