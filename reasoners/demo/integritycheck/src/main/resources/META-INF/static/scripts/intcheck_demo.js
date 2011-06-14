function showReengineer(dataSourceType){
	var dataSourceDiv = document.getElementById("data-source-form");
	if(dataSourceDiv != null){
		if(dataSourceType == 1){
			dataSourceDiv.innerHTML = "<form id=\"intcheck\" action=\"/reengineer/reengineer/db/schema\" method=\"POST\" enctype=\"application/x-www-form-urlencoded\" " +
					"accept=\"application/rdf+xml\"> " +
					"Base URI for schema<br/><input type=\"text\" class=\"url\" name=\"output-graph\" value=\"\" /><br/> " +
					"Physical DB Name<br/><input type=\"text\" class=\"url\" name=\"db\" value=\"\" /><br/> " +
					"JDBC Driver<br/><input type=\"text\" class=\"url\" name=\"jdbc\" value=\"\" /><br/> " +
					"Protocol<br/><input type=\"text\" class=\"url\" name=\"protocol\" value=\"\" /><br/> " +
					"Host<br/><input type=\"text\" class=\"url\" name=\"host\" value=\"\" /><br/> " +
					"Port<br/><input type=\"text\" class=\"url\" name=\"port\" value=\"\" /><br/> " +
					"Username<br/><input type=\"text\" class=\"url\" name=\"username\" value=\"\" /><br/> " +
					"Password<br/><input type=\"text\" class=\"url\" name=\"password\" value=\"\" /><br/> " +
					"<br/><br/> " +
					"<input type=\"submit\" class=\"submit\" value=\"RDB Reengineering\"/>" +
					"</form>";
		}
		else if(dataSourceType == 2){
			dataSourceDiv.innerHTML = "<form id=\"intcheck\" action=\"/reengineer/reengineer\" method=\"POST\" " +
					"enctype=\"multipart/form-data\" " +
					"accept=\"application/rdf+xml\"> " +
					"Output graph URI<br/><input type=\"text\" class=\"url\" name=\"output-graph\" value=\"\" /><br/> " +
					"Input type<br/><select name=\"input-type\"><option value=\"xml\">XML</select><br/> " +
					"Input<br/><input type=\"file\" class=\"url\" name=\"input\" /><br/><br/> " +
					"<input type=\"submit\" class=\"submit\" value=\"XML Reengineering\"/>" +
					"</form>";
		}
		else{
			dataSourceDiv.innerHTML = "";
		}
	}
	
	
}


function getXMLHttpRequest() {

	
	var XHR = null,

	userAgent = navigator.userAgent.toUpperCase();

	if(typeof(XMLHttpRequest) === "function" || typeof(XMLHttpRequest) === "object")
		XHR = new XMLHttpRequest();

	else if(window.ActiveXObject && userAgent.indexOf("MSIE 4") < 0) {

		if(userAgent.indexOf("MSIE 5") < 0)
			XHR = new ActiveXObject("Msxml2.XMLHTTP");
		else
			XHR = new ActiveXObject("Microsoft.XMLHTTP");
	}

	return XHR;
}


function expandMenu(requester) {
	var semionMenu = document.getElementById(requester);
	var display = semionMenu.style.display;
	if(display == '' || display == 'none' || display == null){
		semionMenu.style.display = 'block';
	}
	else{
		semionMenu.style.display = 'none';
	}
	
}

function listScopes(withInactive){
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		if(withInactive){
			ajax.open("get", "/ontonet/ontology?with-inactive=true", true);
		}
		else{
			ajax.open("get", "/ontonet/ontology", true);
		}
		ajax.setRequestHeader("Accept", "application/rdf+json");
		
		var contentDIV = document.getElementById("content");
		
		ajax.onreadystatechange = function() {

		   	// se le operazioni sono state effettuate
		    if(ajax.readyState == 4) {
		    	if (ajax.status == 200) {
		    		if(contentDIV != null){
		    			var jsonObj = ajax.responseText;
		    			
		    			var databank = $.rdf.databank().load(JSON.parse(jsonObj));
		    			databank.prefix('onm', 'http://kres.iks-project.eu/ontology/onm/meta.owl#');
		    			databank.prefix('rdf', 'http://www.w3.org/1999/02/22-rdf-syntax-ns#');
		    			databank.prefix('owl', 'http://www.w3.org/2002/07/owl#');
		    			
		    			var rdf = $.rdf({databank:databank});
		    			
		    			
		    			var scopes = rdf.prefix('onm', 'http://kres.iks-project.eu/ontology/onm/meta.owl#')
		    							.where('?scope a onm:Scope')
		    							.select();
		    			
		    			
		    			var text = "<h3 class=\"menuLeft\">Ontology Network scopes</h3>" +
		    						"<a class=\"menuRight\" href=\"javascript:addScope(true)\"><img alt=\"Add a scope\" src=\"/intcheck/static/images/add.gif\"</a>" +
		    						"<div><ul id=\"kresScopes\" class=\"kressList\">";
                                                              
		    			text += "<br><br>";
		    			for(var scope in scopes){
		    				var scopeID = scopes[scope].scope.toString().replace('<', '').replace('>', '');
		    				var splitSlash = scopeID.split("/");
		    				var scopeIRI = splitSlash[splitSlash.length-1];
		    				text += "<li>"+scopeID +
		    						"<a href=\"javascript:showScopeConfigurationOptions('"+scopeIRI+"')\"><img class=\"configure\" alt=\"Configure\" src=\"/intcheck/static/images/new_configure_16.gif\"></a>" +
		    						"<div id=\""+scopeIRI+"\" class=\"scopeDIV\">" +
		    							"<a href=\"javascript:deleteScope("+scopeID+")\"><img src=\"/intcheck/static/images/delete.gif\" alt=\"delete scope\"></a>" +
		    							"<div id=\"description-"+scopeIRI+"\"></div>" +
		    						"</div>";
		    			}
		    			text += "</ul></div>";
		    			if(withInactive){
		    				text += "<input class=\"contentAlign\" type=\"checkbox\" onClick=\"javascript:listScopes(false)\" CHECKED>Show also disabled scopes"
		    			}
		    			else{
		    				text += "<input class=\"contentAlign\" type=\"checkbox\" onClick=\"javascript:listScopes(true)\">Show also disabled scopes"
		    			}
		    			
		    			contentDIV.innerHTML = text;
		    		}
		        }
		        
		    }
	    }
		
		contentDIV.innerHTML = "<img src=\"/intcheck/static/images/loading.gif\">";
		
		ajax.send(null);
	}
}

function showScopeConfigurationOptions(scopeID) {
	var scopeDIV = document.getElementById(scopeID);
	
	if(scopeDIV != null){
		var d = scopeDIV.style.display;
		if(d==null || d=='none' || d==''){
			scopeDIV.style.display = 'block';
			var scopeDescription = document.getElementById("description-"+scopeID);
			if(scopeDescription != null){
				if(scopeDescription.innerHTML == null || scopeDescription.innerHTML == ''){
					var scope = new Scope();
					scope.describe(scopeID);
				}
			}
			
		}
		else{
			scopeDIV.style.display = 'none';
		}
	}
}

function addScope(displayModule) {
	if(displayModule){
		var content2 = "<div id=\"popupbox\">" +
						"<form name=\"login\" action=\"\" method=\"post\">" +
						"Scope ID:" +
						"<center><input id=\"scopeid\" name=\"scopeid\" type=\"text\" size=\"34\" /></center>" +
						"Core registry:" +
						"<center><input id=\"corereg\" name=\"corereg\" type=\"text\" size=\"34\" /></center>" +
						"Core ontology:" +
						"<center><input id=\"coreont\" name=\"coreont\" type=\"text\" size=\"34\" /></center>" +
						"Custom registry:" +
						"<center><input id=\"customreg\" name=\"customreg\" type=\"text\" size=\"34\" /></center>" +
						"Custom ontology:" +
						"<center><input id=\"customont\" name=\"customont\" type=\"text\" size=\"34\" /></center>" +
						"Activate scope:" +
						"<center><input id=\"activate\" name=\"activate\" type=\"checkbox\" /></center>" +
						"<center><input type=\"button\" name=\"submit\" value=\"add\" onclick=\"javascript:addScope()\"/></center></form><br />";

		TINY.box.show(content2,0,0,0,1);
	}
	else{
		var scopeID = document.getElementById("scopeid").value;
		var corereg = document.getElementById("corereg").value;
		var coreont = document.getElementById("coreont").value;
		var customreg = document.getElementById("customreg").value;
		var customont = document.getElementById("customont").value;
		var activate = document.getElementById("activate");
		
		var ajax = getXMLHttpRequest();
		if(ajax != null){
			ajax.onreadystatechange = function() {

			   	if(ajax.readyState == 4) {
			    	if (ajax.status == 200) {
			    		TINY.box.hide();
			    		var kresScopes = document.getElementById("kresScopes");
			    		if(kresScopes != null){
			    			var txt = document.createTextNode(scopeid);
			    			
			    			var newScope = document.createElement("li");
			    			newScope.appendChild(txt);
			    		}
			    	}
			   	}
			}
			
			var parameters = "";
			
			if(corereg != ''){
				parameters += "corereg="+corereg;
			}
			if(coreont != ''){
				if(parameters != ''){
					parameters += "&";
				}
				parameters += "coreont="+coreont;
			}
			if(customreg != ''){
				if(parameters != ''){
					parameters += "&";
				}
				parameters += "customreg="+customreg;
			}
			if(customont != ''){
				if(parameters != ''){
					parameters += "&";
				}
				parameters += "customont="+customont;
			}
			
			if(parameters != ''){
				parameters += "&";
			}
			if(activate.checked){
				parameters += "activate=true";
			}
			else{
				parameters += "activate=false";
			}
			ajax.open("put", "/ontonet/ontology/"+scopeID+"?"+parameters, true);
			
			//console.log(parameters);
			ajax.send(null);
		}
	}
}

function discoveryLinks(){
	var confIn = document.getElementById("confIn")
	
	if(confIn != null){
		var ajax = getXMLHttpRequest();
		
		if(ajax != null){
			
			ajax.open("post", "/intcheck/link-discovery", true);
			
			var parameter = "configuration="+confIn.value;
			
			ajax.onreadystatechange = function() {
				if(ajax.readyState == 4) {
			    	if (ajax.status == 200) {
			    	}
				}
			}
		}
		
		ajax.send(parameter);
	}
}




function getGraphs(id, ns){
	var content = "";
	
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		ajax.open("get", "/ontonet/graphs/resume", false);
		ajax.setRequestHeader("Accept", "application/rdf+json");
		ajax.send(null);
		
		content += "Select a graph from the store: <br>";
		content += "<select id=\""+id+"\" class=\"refactor\" name=\""+id+"\">";
		
		var jsonObj = ajax.responseText;
		
		var databank = $.rdf.databank().load(JSON.parse(jsonObj));
		
		var rdf = $.rdf({databank:databank});
		
		
		var graphs = rdf.prefix('kres', ns)
						.where('kres:Storage kres:hasGraph ?graph')
						.select();
		
		for(var graph in graphs){
			var graphURI = graphs[graph].graph.toString();
			var g = graphURI.replace("<", "").replace(">", "");
			
			content += "<option value='"+ g +"'>"+ g;
		}
		
		content += "</select>";
	}
	
	return content;
}


function getRecipies(){
	var content = "";
	
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		ajax.open("get", "/recipe/all", false);
		ajax.setRequestHeader("Accept", "application/rdf+json");
		ajax.send(null);
		
		content += "<br>Select a recipe from the rule store: <br>";
		content += "<select id=\"recipe\" class=\"refactor\" name=\"recipe\">";
		
		var jsonObj = ajax.responseText;
		
		var databank = $.rdf.databank().load(JSON.parse(jsonObj));
		
		var rdf = $.rdf({databank:databank});
		
		
		var recipes = rdf.prefix('rmi', 'http://kres.iks-project.eu/ontology/meta/rmi.owl#')
						.where('?recipe a rmi:Recipe')
						.select();
		
		for(var recipe in recipes){
			var recipeURI = recipes[recipe].recipe.toString();
			var r = recipeURI.replace("<", "").replace(">", "");
			content += "<option value='"+ r +"'>"+ r;
		}
		
		content += "</select>";
	}
	
	return content;
}


/*
 * Refactoring
 */

function runRefactoringStore(graph, recipe){
	
}

function listRecipes(){
	var recipeListDIV = document.getElementById("recipeList");
	if(recipeListDIV != null){
			
		var ajax = getXMLHttpRequest();
		if(ajax != null){
			ajax.open("get", "/recipe/all", true);
			ajax.setRequestHeader("Accept", "application/rdf+json");
			
			ajax.onreadystatechange = function() {
				if(ajax.readyState == 4) {
			    	if (ajax.status == 200) {
			    		
			    		
		    			var content = "<ul class=\"kressList\">";
		    			
		    			var jsonObj = ajax.responseText;
		    			
		    			var databank = $.rdf.databank().load(JSON.parse(jsonObj));
		    			
		    			var rdf = $.rdf({databank:databank});
		    			
		    			
		    			var recipes = rdf.prefix('rmi', 'http://kres.iks-project.eu/ontology/meta/rmi.owl#')
										 .where('?recipe a rmi:Recipe')
										 .select();
		
		    			var content = "<br><br>";
		    			for(var recipe in recipes){
		    				var recipeURI = recipes[recipe].recipe.toString();
		    				var r = recipeURI.replace("<", "").replace(">", "");
		    				content += "<li>"+ r;
		    				content += "<a href=\"javascript:var rule = new Rule(); rule.listRulesOfRecipe('"+r+"');\"><img class=\"configure\" alt=\"Configure\" src=\"/intcheck/static/images/new_configure_16.gif\"></a>"
		    				content += "<a href=\"javascript:var rule = new Rule(); rule.displayAddBox('"+r+"');\" alt=\"add rule to scope\">" +
										"<img src=\"/intcheck/static/images/addRule.gif\" alt=\"add rule to scope\"></a>";
		    				content += "<div id=\""+r+"\" class=\"scopeDIV\"></div>";
		    			}
		    			
		    			
		    			content += "</ul>";
		    			
		    			
		    			recipeListDIV.innerHTML = content;
		    		
			    	}
			   	}
			}
			
			ajax.send(null);
		}
		
		recipeListDIV.style.display = 'block';
		
		var addRecipeElement = document.getElementById("addRecipe");
		if(addRecipeElement != null){
			addRecipeElement.style.display = 'block';
		}
		
		var action = document.getElementById("action");
		if(action != null){
			action.href = "javascript:hideRecipes()";
			action.innerHTML = "hide";
		}
	}
}

function hideRecipes(){
	var recipeListDIV = document.getElementById("recipeList");
	if(recipeListDIV != null){
		recipeListDIV.style.display = 'none';
	}
	
	var addRecipeElement = document.getElementById("addRecipe");
	if(addRecipeElement != null){
		addRecipeElement.style.display = 'none';
	}
	
	var action = document.getElementById("action");
	if(action != null){
		action.href = "javascript:listRecipes()";
		action.innerHTML = "view";
	}
}

function Rule(){
	return this;
}

Rule.prototype.addRule = function(){
	var recipeIDEl = document.getElementById("recipeid");
	var ruleIDEl = document.getElementById("ruleid");
	var ruleEl = document.getElementById("rule");
	var descriptionEl =	document.getElementById("description");
	
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		ajax.open("post", "/rule", true);
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
		
		ajax.onreadystatechange = function() {
			if(ajax.readyState == 4) {
		    	if (ajax.status == 200) {
		    		TINY.box.hide();
		    		listRecipes();
		    	}
			}
		}
	}
	
	if(recipeIDEl != null && ruleIDEl != null && ruleEl != null && descriptionEl != null){
		var recipeID = recipeIDEl.value;
		var ruleID = ruleIDEl.value;
		var rule = ruleEl.value;
		var description = descriptionEl.value;
		
		var parameter = "recipe="+recipeID+"&rule="+ruleID+"&kres-syntax="+rule+"&description="+description;
		//alert("INSERISCO "+parameter);
		ajax.send(parameter);
	}
}

Rule.prototype.addNewRule = function(scopeID, recipeID, ruleID, rule, description){
	
	       //Elimino la regola dal recipe
		var ajax = getXMLHttpRequest();
		if(ajax != null){
			var time_scopeID = scopeID.replace("linkeddata","");
			var ruleName = "http://localhost:9191/rule/demo_"+time_scopeID;	
			ajax.open("delete", "/rule?rule="+ruleID+"&recipe="+recipeID, true);
			window.ruleName=ruleName;
			ajax.onreadystatechange = function() {
                        if(ajax.readyState == 4) {
                            if (ajax.status == 200) {
                                //Elimino la regola dall'ontologia
                                var ajax2 = getXMLHttpRequest();
                                if(ajax2 != null){
                                    ajax2.open("delete", "/rule?rule="+ruleID, true);
                                    ajax2.onreadystatechange = function() {
                                        if(ajax2.readyState == 4) {
                                            if (ajax2.status == 200) {
                                                //Aggiungo nuova regola al recipe 
                                                var ajax3 = getXMLHttpRequest();
                                                if(ajax3 != null){
                                                        ajax3.open("post", "/rule", true);
                                                        ajax3.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                                                        ajax3.onreadystatechange = function() {
                                                                if(ajax3.readyState == 4) {
                                                                if (ajax3.status == 200) {

                                                                        var div = document.getElementById("ruleUL");
                                                                        if(div != null){
                                                                                rule = rule.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                                                                                div.innerHTML = "<li>" + rule;
                                                                        }
                                                                        //var integrityDIV = document.getElementById("integrityDemoDIV");
                                                                        //integrityDIV.style.display = 'block';
                                                                        $("#integrityDemoDIV").show();
                                                                        //$("integrityDemoDIV").html("<input type=\"button\" value=\"integrity check\">");
                                                                }
                                                                }
                                                        }
                                                }

                                                if(recipeID != null && ruleName != null && rule != null){
                                                        var descriptionText = "";
                                                        if(description != null){
                                                                description = description;
                                                        }

                                                        var parameter = "recipe="+recipeID+"&rule="+ruleName+"&kres-syntax="+rule+"&description="+descriptionText;
                                                        //alert("POSTO 1 : "+parameter);
                                                        ajax3.send(parameter);
                                                }
                                            }  
                                        }
                                    }
                                }
                                ajax2.send();
                            }else{
                                //Cancello se c'è la regola
                                var ajax2 = getXMLHttpRequest();
                                if(ajax2 != null){
                                    ajax2.open("delete", "/rule?rule="+ruleID, true);
                                    ajax2.onreadystatechange = function() {
                                        if(ajax2.readyState == 4) {
                                            if (ajax2.status == 200 || ajax2.status == 404) {
                                                //Aggiungo regola al recipe 
                                                var ajax3 = getXMLHttpRequest();
                                                if(ajax3 != null){
                                                        ajax3.open("post", "/rule", true);
                                                        ajax3.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                                                        ajax3.onreadystatechange = function() {
                                                                if(ajax3.readyState == 4) {
                                                                if (ajax3.status == 200) {

                                                                        var div = document.getElementById("ruleUL");
                                                                        if(div != null){
                                                                                rule = rule.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                                                                                div.innerHTML = "<li>" + rule;
                                                                        }
                                                                        //var integrityDIV = document.getElementById("integrityDemoDIV");
                                                                        //integrityDIV.style.display = 'block';
                                                                        $("#integrityDemoDIV").show();
                                                                        //$("integrityDemoDIV").html("<input type=\"button\" value=\"integrity check\">");
                                                                }
                                                                }
                                                        }
                                                }

                                                if(recipeID != null && ruleName != null && rule != null){
                                                        var descriptionText = "";
                                                        if(description != null){
                                                                description = description;
                                                        }

                                                        var parameter = "recipe="+recipeID+"&rule="+ruleName+"&kres-syntax="+rule+"&description="+descriptionText;
                                                        //alert("POSTO 2 : "+parameter);
                                                        ajax3.send(parameter);
                                                }
                                            }  
                                        }
                                    }
                                }
                                ajax2.send();               
                            }
                        }
                    }
                }
                ajax.send();  
             
}

Rule.prototype.displayAddBox = function(recipe){
	var content2 = "<div id=\"popupbox\">" +
	"<form name=\"login\" action=\"\" method=\"post\">" +
	"Recipe ID" +
	"<center><input id=\"recipeid\" name=\"recipeid\" type=\"text\" value=\""+recipe+"\" size=\"34\" READONLY/></center>" +
	"Rule ID:"+
	"<center><input id=\"ruleid\" name=\"ruleid\" type=\"text\" size=\"34\"/></center>" +
	"Rule:"+
	"<center><textarea id=\"rule\" name=\"rule\" cols=25 row=36/></textarea></center><br><br>" +
	"Description:"+
	"<center><input id=\"description\" name=\"description\" type=\"text\" size=\"34\" /></center>" +
	"<center><input type=\"button\" name=\"submit\" value=\"add\" onclick=\"javascript:var rule = new Rule(); rule.addRule()\"/></center></form><br />";

	TINY.box.show(content2,0,0,0,1);
}

Rule.prototype.listRulesOfRecipe = function(recipe){
	
	var div = document.getElementById(recipe);
	if(div != null){
	
		var ajax = getXMLHttpRequest();
		if(ajax != null){
			recipe = recipe.replace("#", "%23");
			ajax.open("get", "/rule/of-recipe/"+recipe, true);
			ajax.setRequestHeader("Accept", "application/rdf+json");
			ajax.onreadystatechange = function() {
				if(ajax.readyState == 4) {
			    	if (ajax.status == 200) {
			    		var jsonObj = ajax.responseText;
			    		
			    		var databank = $.rdf.databank().load(JSON.parse(jsonObj));
		    			
		    			var rdf = $.rdf({databank:databank});
		    			
		    			recipe = recipe.replace("%23", "#");
		    			var rules = rdf.prefix('rmi', 'http://kres.iks-project.eu/ontology/meta/rmi.owl#')
										 .where('<' + recipe + '> rmi:hasRule ?rule')
										 .select();
		    			
		    			
		    			var content = "<div id=\"rulesOfrecipe"+recipe+"\">";
		    			
		    			for(var rule in rules){
		    			
		    				var ruleURI = rules[rule].rule.toString();
		    				var r = ruleURI.replace("<", "").replace(">", "");
		    				content += "<li>"+ r;
		    				content += "<a href=\"javascript:var rule=new Rule(); rule.getRuleCode('"+r+"')\"><img class=\"configure\" alt=\"Configure\" src=\"/intcheck/static/images/new_configure_16.gif\"></a>";
		    				content += "<div id=\""+r+"\" class=\"scopeDIV\">" +
		    							"</div>";
		    			}
		    			
		    			
		    			content += "</ul>";
		    			
		    			content += "<div id='\"visualization"+recipe+"\'></div>";
		    			content += "<div id='\"visualization"+recipe+"-details\'></div>";
		    			
		    			content += "</div>";
		    			
		    			div.innerHTML = content;
		    			
		    			div.style.display = 'block';
		    			
		    			loadGraph("visualization"+recipe, jsonObj);
			    	}
				}
			}
			
			ajax.send(null);
		}
	}
	
}


Rule.prototype.getRuleCode = function(rule){
	
	var div = document.getElementById(rule);
	if(div != null){
	
		var ajax = getXMLHttpRequest();
		if(ajax != null){
			rule = rule.replace("#", "%23");
			ajax.open("get", "/rule/"+rule, true);
			ajax.setRequestHeader("Accept", "application/rdf+json");
			ajax.onreadystatechange = function() {
				if(ajax.readyState == 4) {
			    	if (ajax.status == 200) {
			    		var jsonObj = ajax.responseText;
			    		
			    		var databank = $.rdf.databank().load(JSON.parse(jsonObj));
		    			
		    			var rdf = $.rdf({databank:databank});
		    			
		    			rule = rule.replace("%23", "#");
		    			var rules = rdf.prefix('rmi', 'http://kres.iks-project.eu/ontology/meta/rmi.owl#')
										 .where('<' + rule + '> rmi:hasBodyAndHead ?code')
										 .select();
		    			
		    			
		    			var content = "<div id=\"rulescode-"+rule+"\">";
		    			
		    			for(var code in rules){
		    			
		    				var code = rules[code].code.toString();
		    				//var r = ruleURI.replace("<", "").replace(">", "");
		    				code = code.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		    				content += code;
		    			}
		    			
		    			div.innerHTML = content;
		    			
		    			div.style.display = 'block';
		    			
		    			//loadGraph("visualization"+recipe, jsonObj);
			    	}
				}
			}
			
			ajax.send(null);
		}
	}
	
}

Rule.prototype.hideRulesOfRecipe = function(recipe){
	var div = document.getElementById("rulesOfrecipe"+recipe);
	if(div != null){
		div.style.display = 'none';
	}
	
	var listRulesA = document.getElementById("listRulesA");
	if(listRulesA != null){
		listRulesA.href = "javascript: var rule = new Rule(); rule.listRulesOfRecipe('"+recipe+"');";
	}
}


function Recipe(){
	return this;
}

Recipe.prototype.addRecipe = function(){
	
	var recipeIDEl = document.getElementById("recipeid");
	var descriptionEl =	document.getElementById("description");
	
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		ajax.open("post", "/recipe", true);
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
		ajax.setRequestHeader("Accept", "application/rdf+json");
		
		ajax.onreadystatechange = function() {
			if(ajax.readyState == 4) {
		    	if (ajax.status == 200) {
		    		TINY.box.hide();
		    		listRecipes();
		    	}
			}
		}
	}
	
	if(recipeIDEl != null && descriptionEl != null){
		var recipeID = recipeIDEl.value;
		
		var description = descriptionEl.value;
		
		var parameter = "recipe="+recipeID+"&description="+description;
		
		ajax.send(parameter);
	}
	
	
}


Recipe.prototype.addNewRecipe = function(scopeID,recipeID, recipeDescription, fun, ruleID, rule, ruledescription, async){
	
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		ajax.open("post", "/recipe", true);
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
		ajax.setRequestHeader("Accept", "application/rdf+json");
		
		if(async){
			ajax.onreadystatechange = function() {
				if(ajax.readyState == 4) {
			    	if (ajax.status == 200 || ajax.status == 409) {
			    		
			    		TINY.box.hide();
                                        
			    		fun(scopeID,recipeID, ruleID, rule, ruledescription);
			    		
			    		
			    	}
				}
			}
		}
	}
	
	if(recipeID != null){
		
		var descriptionText = "";
		if(recipeDescription != null){
			descriptionText = recipeDescription
		}
		
		var parameter = "recipe="+recipeID+"&description="+descriptionText;
		
		ajax.send(parameter);
	}
	
	
}

Recipe.prototype.displayAddBox = function(){
	var content2 = "<div id=\"popupbox\">" +
	"<form name=\"login\" action=\"\" method=\"post\">" +
	"Recipe ID:" +
	"<center><input id=\"recipeid\" name=\"recipeid\" type=\"text\" size=\"34\" /></center>" +
	"Recipe description:" +
	"<center><input id=\"description\" name=\"description\" type=\"text\" size=\"34\" /></center>" +
	"<center><input type=\"button\" name=\"submit\" value=\"add\" onclick=\"javascript:var recipe = new Recipe(); recipe.addRecipe()\"/></center></form><br />";

	TINY.box.show(content2,0,0,0,1);
}

function loadGraph(element, json) {
	var ht = new $jit.Hypertree({  
		  //id of the visualization container  
		  injectInto: element,  
		  //canvas width and height  
		  width: 50,  
		  height: 50,  
		  //Change node and edge styles such as  
		  //color, width and dimensions.  
		  Node: {  
		      dim: 9,  
		      color: "#f00"  
		  },  
		  Edge: {  
		      lineWidth: 2,  
		      color: "#088"  
		  },  
		  onBeforeCompute: function(node){  
		      Log.write("centering");  
		  },  
		  //Attach event handlers and add text to the  
		  //labels. This method is only triggered on label  
		  //creation  
		  onCreateLabel: function(domElement, node){  
		      domElement.innerHTML = node.name;  
		      $jit.util.addEvent(domElement, 'click', function () {  
		          ht.onClick(node.id);  
		      });  
		  },  
		  //Change node styles when labels are placed  
		  //or moved.  
		  onPlaceLabel: function(domElement, node){  
		      var style = domElement.style;  
		      style.display = '';  
		      style.cursor = 'pointer';  
		      if (node._depth <= 1) {  
		          style.fontSize = "0.8em";  
		          style.color = "#ddd";  
		  
		      } else if(node._depth == 2){  
		          style.fontSize = "0.7em";  
		          style.color = "#555";  
		  
		      } else {  
		          style.display = 'none';  
		      }  
		  
		      var left = parseInt(style.left);  
		      var w = domElement.offsetWidth;  
		      style.left = (left - w / 2) + 'px';  
		  },  
		    
		  onAfterCompute: function(){  
		      Log.write("done");  
		        
		      //Build the right column relations list.  
		      //This is done by collecting the information (stored in the data property)   
		      //for all the nodes adjacent to the centered node.  
		      var node = ht.graph.getClosestNodeToOrigin("current");  
		      var html = "<h4>" + node.name + "</h4><b>Connections:</b>";  
		      html += "<ul>";  
		      node.eachAdjacency(function(adj){  
		          var child = adj.nodeTo;  
		          if (child.data) {  
		              var rel = (child.data.band == node.name) ? child.data.relation : node.data.relation;  
		              html += "<li>" + child.name + " " + "<div class=\"relation\">(relation: " + rel + ")</div></li>";  
		          }  
		      });  
		      html += "</ul>";  
		      $jit.id(element+"-details").innerHTML = html;  
		  }  
		});  
		//load JSON data.  
		ht.loadJSON(json);  
		//compute positions and plot.  
		ht.refresh();
}



function Refactorer(){
	return this;
}

Refactorer.prototype.runRefactoringStoreLazy = function() {
	var recipeEl = document.getElementById("recipe");
	var inputGraphEl = document.getElementById("input-graph");
	var outputGraphEl = document.getElementById("output-graph");
	
	if(recipeEl!=null && inputGraphEl!=null && outputGraphEl!=null){
		var ajax = getXMLHttpRequest();
		if(ajax != null){
			
			var recipe = recipeEl.value.replace("#", "%23");
			var inputGraph = inputGraphEl.value.replace("#", "%23");
			var outputGraph = outputGraphEl.value.replace("#", "%23");
			ajax.open("get", "/refactorer/lazy?recipe="+recipe+"&input-graph="+inputGraph+"&output-graph="+outputGraph, true);
			ajax.setRequestHeader("Accept", "application/rdf+json");
			ajax.onreadystatechange = function() {
				if(ajax.readyState == 4) {
			    	if (ajax.status == 200) {
			    		
			    	}
				}
			}
			ajax.send(null);
		}
	}
}

Refactorer.prototype.runRefactoringFileLazy = function() {
	
	var formEl = document.getElementById("iForm");
	if(formEl != null){
		formEl.submit();
	}
	/*var recipeEl = document.getElementById("recipe");
	var inputGraphEl = document.getElementById("graph");
	
	if(recipeEl!=null && inputGraphEl!=null){
		var ajax = getXMLHttpRequest();
		if(ajax != null){
			
			var recipe = recipeEl.value;
			var inputGraph = inputGraphEl.value;
			
			console.log("recipe: "+recipe+" - input: "+inputGraph);
			ajax.open("post", "/kres/refactorer/lazy", true);
			ajax.setRequestHeader("Accept", "application/rdf+json");
			ajax.onreadystatechange = function() {
				if(ajax.readyState == 4) {
			    	if (ajax.status == 200) {
			    		console.log("Refactoring completed");
			    	}
				}
			}
			
			var parameters = "recipe='"+recipe+"'&input="+inputGraph
			ajax.send(parameters);
		}
	}*/
}

Refactorer.prototype.showRefactoring = function(type, ns){
	var contentTag = document.getElementById("refactoring");
	
	var content = "";
	if(contentTag != null){
		var submit = "";
		if(type == 0){
			content += getGraphs("input-graph", ns);
			content += "Output graph ID<br><input type=\"text\" id=\"output-graph\" name=\"output-graph\">";
			
			submit = "<input type=\"submit\" value=\"run refactoring\" onClick=\"javascript:var refactorer = new Refactorer(); refactorer.runRefactoringStoreLazy()\">";
		}
		else{
			content += "<form id=\"iForm\" action=\"/refactorer/lazy\" method=\"post\" enctype=\"multipart/form-data\" onsubmit=\"alert(\"finito\")\">";
			content += "Select a graph from file: <input id=\"graph\" type=\"file\" name=\"input\"><br>";
			
			submit += "<input type=\"button\" value=\"run refactoring\" onclick=\"var refactorer=new Refactorer(); refactorer.runRefactoringFileLazy();\"></form>";
			
		}
		
		content += getRecipies();
		content += submit;
		
		contentTag.style.display = 'block';
		contentTag.innerHTML = content;
	}
}

function Storage(){
	return this;
}

Storage.prototype.loadGraph = function(graphID) {
	graphID = graphID.replace("<", "").replace(">", "").replace("#", "%23");
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		
		
		ajax.open("get", "/ontonet/graphs/"+graphID, true);
		ajax.setRequestHeader("Accept", "application/rdf+xml");
		ajax.onreadystatechange = function() {
			if(ajax.readyState == 4) {
		    	if (ajax.status == 200) {
		    		var response = ajax.responseText;
		    		if(response != null){
		    			var div = document.getElementById("graphDIV");
		    			if(div != null){
		    				response = response.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		    				div.innerHTML = "<code class=\"code\">"+response+"</code>";
		    				div.style.display = 'block';
		    			}
		    		}
		    	}
			}
		}
		
		ajax.send(null);
	}
}

function Demo(){
	return this;
}


Demo.prototype.ruleListRemove  = function(scopeID){
	var time_scopeID = scopeID.replace("linkeddata","");
	//alert("ruleListRemove: "+time_scopeID);
	var ajaxrules = getXMLHttpRequest();
	if(ajaxrules!=null){
		//alert("RULE LIST");
	//GET ALL RULES ASSOCIATED TO THE RECIPE:
		//get all rules for the recipeI
		ajaxrules.open("get", "/recipe/http://kres.iks-project.eu/recipe/integrity_"+time_scopeID, true);
		//ajaxrules.open("get", "/recipe/http://kres.iks-project.eu/recipe/integrity", true);
		ajaxrules.setRequestHeader("Accept", "application/rdf+json");
		ajaxrules.onreadystatechange = function() {
			if(ajaxrules.readyState == 4) {
				//alert("STATE 4");
			if (ajaxrules.status == 200 || ajaxrules.status == 409) {
				//alert("STATE 200");
				var jsonObjscope = ajaxrules.responseText;   			
				var databank = $.rdf.databank().load(JSON.parse(jsonObjscope));
				databank.prefix('onm', 'http://kres.iks-project.eu/ontology/onm/meta.owl#');
				databank.prefix('rdf', 'http://www.w3.org/1999/02/22-rdf-syntax-ns#');
				databank.prefix('owl', 'http://www.w3.org/2002/07/owl#');
				databank.prefix('rmi','http://kres.iks-project.eu/ontology/meta/rmi.owl#');
				
				var rdfrules = $.rdf({databank:databank});	    									
				var rules = rdfrules.prefix('rmi', 'http://kres.iks-project.eu/ontology/meta/rmi.owl#').where('?recipe rmi:hasRule ?rule').select();
		
				//alert(rules);
				for(var subject in rules){
					singlerule = rules[subject].rule.toString().replace('<', '').replace('>', '');;
					if(singlerule.search("demo_")!=-1)
					if(singlerule.search("string")==-1){
						var ajax = getXMLHttpRequest();
						if(ajax != null){
							//alert(singlerule);
							ajax.open("delete", "/rule?rule="+singlerule+"&recipe=http://kres.iks-project.eu/recipe/integrity_"+time_scopeID, true);
							ajax.onreadystatechange = function() {
								if(ajax.readyState == 4) {
									//alert("DELETE 4");
									if (ajax.status == 200 || ajax.status == 404) {
										//alert("DELETE RULE FOR RECIPE: "+singlerule);
									}
								}
							}
							ajax.send(null);
						}
					}
					
				}
			}
		}	
		}
		ajaxrules.send(null);
	}
	
}

/*
 Function to reset the scope, the session and the ontology.
 This function will run every time a new scope is created.
*/
Demo.prototype.reset = function(){
	//alert("::::::: provo reset :::::::::::::");
	
	var ajaxscope = getXMLHttpRequest();
	
	if(ajaxscope != null){
		//get all the scope
		ajaxscope.open("get", "/ontonet/ontology", true);
		ajaxscope.setRequestHeader("Accept", "application/rdf+json");
		//alert("::::::: ajaxscope :::::::::");
		ajaxscope.onreadystatechange = function() {
			if(ajaxscope.readyState == 4) {
			if (ajaxscope.status == 200 || ajaxscope.status == 409) {
				//alert("::::::::::: dentro ajaxscope ::::::::::");
				//var contentDIV = document.getElementById("content");
				var jsonObjscope = ajaxscope.responseText;   			
				var databank = $.rdf.databank().load(JSON.parse(jsonObjscope));
				databank.prefix('onm', 'http://kres.iks-project.eu/ontology/onm/meta.owl#');
				databank.prefix('rdf', 'http://www.w3.org/1999/02/22-rdf-syntax-ns#');
				databank.prefix('owl', 'http://www.w3.org/2002/07/owl#');
				
				var rdfscope = $.rdf({databank:databank});	    									
				var scopes = rdfscope.prefix('onm', 'http://kres.iks-project.eu/ontology/onm/meta.owl#').where('?scope a onm:Scope').select();
				
				//get all sessions those are related to scope linkeddata				
				var ajaxsession = getXMLHttpRequest();
				if(ajaxsession != null){
					//alert(":::::::: ajaxsession ::::::::");
					ajaxsession.open("get", "/ontonet/session", true);
					ajaxsession.setRequestHeader("Accept", "application/rdf+json");				
					
					ajaxsession.onreadystatechange = function() {
							
						if(ajaxsession.readyState == 4) {
						if (ajaxsession.status == 200 || ajaxsession.status == 409) {
							//alert(":::::: dentro ajaxsession ::::::::");
							var jsonObjsession = ajaxsession.responseText;
							var databank = $.rdf.databank().load(JSON.parse(jsonObjsession));
		    							
		    					var rdfsession = $.rdf({databank:databank});
							
							var sessionMeta = rdfsession.prefix('meta', 'http://kres.iks-project.eu/ontology/onm/meta.owl#').where('?session ?p ?id').select();
							
							//Delete sessions								
							var sessionID;
							for(var subject in sessionMeta){
								sessionID = sessionMeta[subject].id.toString().replace('<', '').replace('>', '');
								if(sessionID.search("session")!=-1)
								if(sessionID.search("refactor")==-1){
									try{
									for(var scope in scopes){
										var scopeID = scopes[scope].scope.toString().replace('<', '').replace('>', '');

										if(scopeID.search("linkeddata")!=-1){
											var ajaxdeletesession = getXMLHttpRequest();
											if(ajaxdeletesession!=null){
												ajaxdeletesession.open("delete","/ontonet/session?scope="+scopeID+"&session="+sessionID, true);
												/*ajaxdeletesession.onreadystatechange = function() {
													if(ajaxdeletesession.readyState == 4) {
													if (ajaxdeletesession.status == 200 || ajaxdeletesession.status == 409) {
														//alert("::::: reset session with scope :::::::::\n/ontonet/session?scope="+scopeID+"&session="+sessionID);
													}
													}
												}*/
												ajaxdeletesession.send(null);
												//alert("::::: reset session with scope :::::::::\n/kres/session?scope="+scopeID+"&session="+sessionID);
											}
										}
									}
									}catch(err){}//alert("err delete session\n"+err)}
								}
							}
							
							//Delete scope
							try{
							for(var scope in scopes){
								var scopeID = scopes[scope].scope.toString().replace('<', '').replace('>', '');
								if(scopeID.search("linkeddata")!=-1){
									var ajaxdelete = getXMLHttpRequest();
									if(ajaxdelete!=null){
										ajaxdelete.open("delete",scopeID, true);
										/*ajaxdelete.onreadystatechange = function() {
											if(ajaxdelete.readyState == 4) {
											if (ajaxdelete.status == 200 || ajaxdelete.status == 409 || ajaxdelete.status == 204) {
												//alert("::::: unregister scope ::::::: "+scopeID);
											}
											}
										}*/
										ajaxdelete.send(null);
										//alert("::::: unregister scope ::::::: "+scopeID);
									}
								}			    				
							}
							}catch(err){}//alert("err delete scope\n"+err)}
							
							}			
						}	
						}
						}
					ajaxsession.send(null);
					}
			}
			}
		ajaxscope.send(null);
	}
}
	
/*
 Function to reset the scope, the session and the ontology.
 This function will run every time a new scope is created.
*/
Demo.prototype.reset2 = function(){
	//alert("::::::: provo reset :::::::::::::");	
	
	var scopes;
	var sessionMeta;
	try{
		//get all the scope linkeddata
		var ajaxscope = getXMLHttpRequest();
		if(ajaxscope != null){
			ajaxscope.open("get", "/ontonet/ontology", true);
			ajaxscope.setRequestHeader("Accept", "application/rdf+json");
			ajaxscope.onreadystatechange = function() {							
				if(ajaxscope.readyState == 4) {
				if (ajaxscope.status == 200 || ajaxscope.status == 409) {
					var jsonObj = ajaxscope.responseText;  			
					var databank = $.rdf.databank().load(JSON.parse(jsonObj));
					databank.prefix('onm', 'http://kres.iks-project.eu/ontology/onm/meta.owl#');
					databank.prefix('rdf', 'http://www.w3.org/1999/02/22-rdf-syntax-ns#');
					databank.prefix('owl', 'http://www.w3.org/2002/07/owl#');
								
					var rdf = $.rdf({databank:databank});	    						
								
					scopes = rdf.prefix('onm', 'http://kres.iks-project.eu/ontology/onm/meta.owl#').where('?scope a onm:Scope').select();
					
				}
				}
			}
			ajaxscope.send(null);	
		}	
			//alert(scopes);
		//Get all the session related to the linkeddata			
		var ajaxsession = getXMLHttpRequest();
		if(ajaxsession != null){
			ajaxsession.open("get", "/ontonet/session", true);
			ajaxsession.setRequestHeader("Accept", "application/rdf+json");				
			
			ajaxsession.onreadystatechange = function() {
					
				if(ajaxsession.readyState == 4) {
				if (ajaxsession.status == 200 || ajaxsession.status == 409) {
					var jsonObjsession = ajaxsession.responseText;
					var databank = $.rdf.databank().load(JSON.parse(jsonObjsession));
					databank.prefix('meta', 'http://kres.iks-project.eu/ontology/onm/meta.owl#');		
					var rdfsession = $.rdf({databank:databank});
					
					sessionMeta = rdfsession.prefix('meta', 'http://kres.iks-project.eu/ontology/onm/meta.owl#').where('?session ?p ?id').select();
				}
				}
			}
			ajaxsession.send(null);
		}
		//alert(sessionMeta);
		//Delete sessions								
		var sessionID;
		for(var subject in sessionMeta){
			sessionID = sessionMeta[subject].id.toString().replace('<', '').replace('>', '');
			if(sessionID.search("session")!=-1)
			if(sessionID.search("refactor")==-1){
				try{
				for(var scope in scopes){
					var scopeID = scopes[scope].scope.toString().replace('<', '').replace('>', '');
					if(scopeID.search("linkeddata")!=-1){
						var ajaxdeletesession = getXMLHttpRequest();
						if(ajaxdeletesession!=null){
							//alert("::::\n"+scopeID+" "+sessionID+"\n::::");
							ajaxdeletesession.open("delete","/ontonet/session?scope="+scopeID+"&session="+sessionID, true);
							ajaxdeletesession.onreadystatechange = function() {
								if(ajaxdeletesession.readyState == 4) {
									if (ajaxdeletesession.status == 200 || ajaxdeletesession.status == 409) {
										//alert("::::: reset session with scope :::::::::\n/ontonet/session?scope="+scopeID+"&session="+sessionID);
									}
								}
							}
							ajaxdeletesession.send(null);
						}
					}
				}
				}catch(err){}//alert("err delete session\n"+err)}
			}
		}
			
		//Delete scope
		try{
		for(var scope in scopes){
			var scopeID = scopes[scope].scope.toString().replace('<', '').replace('>', '');
			if(scopeID.search("linkeddata")!=-1){
				var ajaxdelete = getXMLHttpRequest();
				if(ajaxdelete!=null){
					ajaxdelete.open("delete",scopeID, true);
					ajaxdelete.onreadystatechange = function() {
						if(ajaxdelete.readyState == 4) {
							if (ajaxdelete.status == 200 || ajaxdelete.status == 409 || ajaxdelete.status == 204) {
								//alert("::::: unregister scope ::::::: "+scopeID);
							}
						}
					}
					ajaxdelete.send(null);
				}
			}			    				
		}
		}catch(err){}//alert("err delete scope\n"+err)}	
	
	}catch(err){}//alert("::: reset error :::: "+err);}
}

Demo.prototype.enhance = function() {
	//alert("ENHANCE ZERO");
	var data = {
		content: $("#textInput").val(),
		ajax: false,
		format:  "application/rdf+json"
        };
	
	$.ajax({
	       type: "POST",
	       url: "/engines",
	       data: data,
	       dataType: "html",
	       cache: false,
	       success: function(result) {
	        
		//alert(result);
                     //alert("ENHANCE BEFORE");
                     //alert(JSON.parse(result));
		        var databank = $.rdf.databank().load(JSON.parse(result));
	 			
		        var rdf = $.rdf({databank:databank});
 			
 			
		        var references = rdf.prefix('fise', 'http://fise.iks-project.eu/ontology/')
								 .where('?subject fise:entity-reference ?reference')
								 .select();
	 			var content = "<br><br><ul class=\"indent\">";
	 			
	 			var arrRefs = new Array();
	 			
	 			for(var subject in references){
	 				var referenceURI = references[subject].reference.toString().replace("<", "").replace(">", "");
                                 //alert("ENHANCE referencrURI: "+referenceURI);       
	 				arrRefs.push(referenceURI);
	 			}
	 			
	 			arrRefs = unique(arrRefs);
	 			
	 			for(var uriRef in arrRefs){
	 				var uri = arrRefs[uriRef];
	 				content += "<li id=\""+uri+"\">"+uri+"<input type=\"hidden\" name=\"hasReference\" value=\""+uri+"\">";
	 			}
	 			content += "</ul>";
	 			
	 			content += "<br><br>";
	 			content += "<div id=\"creating-scope\" class=\"hide\">Creating scope <img class=\"small\" src=\"/intcheck/static/images/loadingSmall.gif\"></div>";
	 			content += "<br><br>";
	 			
	 			
	 			content += "<input id=\"addToONM\" type=\"button\" value=\"Load linked data about these entities\" onClick=\"javascript: var timestamp = new Date().getTime(); scopeName = 'linkeddata' + timestamp;var demo=new Demo(); demo.createScopeAndSession(scopeName, null, 'http://ontologydesignpatterns.org/ont/iks/kres/dbpedia_demo.owl', null, null, true)\">";
	 			content += "<div id=\"addSessionWait\" class=\"hide\" stype=\"margin-top:20px;\">";
	 			content += "<center><img src=\"/intcheck/static/images/loadingSmall.gif\"></center>";
	 			content += "</div>";
	 			
	 			//content += "<input type=\"button\" value=\"integrity check\" onClick=\"javascript:var demo=new Demo; demo.integrityCheckFISE()\">";
	 			//alert("ENHANCE AFTER");
	 			$("#fiseResult").show();
		        $("#fiseResult").html(content);
	       },
	       error: function(result) {	
	    	   $("#fiseResult").show();
		       $("#fiseResult").html("Problem to connect to the Geonames Server. It could be necessary to try several times.");
	       }
	     });
	$("#fiseResult").show();
    $("#fiseResult").html("<center><img src=\"/intcheck/static/images/loadingSmall.gif\"></center>");
	
} 

Demo.prototype.createScopeAndSession = function(scopeID, corereg, coreont, customreg, customont, activate){
	
	//alert(":::::::::: createScopeAndSession :::::::::::: SCOPE: "+scopeID);
	
	if(scopeID != null){
		var ajax = getXMLHttpRequest();
		if(ajax != null){
			
			var parameters = "";
			
			if(corereg != null && corereg != ''){
				parameters += "corereg="+corereg;
			}
			if(coreont != null && coreont != ''){
				if(parameters != ''){
					parameters += "&";
				}
				parameters += "coreont="+coreont;
			}
			if(customreg != null && customreg != ''){
				if(parameters != ''){
					parameters += "&";
				}
				parameters += "customreg="+customreg;
			}
			if(customont != null && customont != ''){
				if(parameters != ''){
					parameters += "&";
				}
				parameters += "customont="+customont;
			}
			
			if(parameters != ''){
				parameters += "&";
			}
			if(activate!=null){
				if(activate){
					parameters += "activate=true";
				}
				else{
					parameters += "activate=false";
				}
			}
			else{
				parameters += "activate=false";
			}
			
			ajax.onreadystatechange = function() {
	
			   	if(ajax.readyState == 4) {
			    	if (ajax.status == 200 || ajax.status == 409) {
			    		
			    		
			    		//alert("parameters: "+parameters);
			    		var ajax2 = getXMLHttpRequest();
                                 //alert(ajax2 != null);       
			    		if(ajax2 != null){
			    			ajax2.open("post", "/ontonet/session", true);
			    			ajax2.setRequestHeader("Accept", "application/rdf+json");
			    			ajax2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			    			//alert("ajax2 not null");
			    			ajax2.onreadystatechange = function() {
			    			       		
		    				   	if(ajax2.readyState == 4) {
		    				    	if (ajax2.status == 200 || ajax2.status == 409) {
                                                     //alert("ajax2.status 200");	
                                                     //alert("ajax2.status 409");
                                                     //alert("ajax2.readyState 4");
		    				    		var sessionJSON = ajax2.responseText;
                                                                
                                                     //alert(":::::\n"+sessionJSON);
                                                     
		    				    		var databank = $.rdf.databank().load(JSON.parse(sessionJSON));//JSON.parse(sessionJSON));
		    					       //alert("DATABANK 1 DONE");		
                                                    var rdf = $.rdf({databank:databank});

                                                    //alert("PRIMA DI META");
                                                    var sessionMeta = rdf.prefix('meta', 'http://kres.iks-project.eu/ontology/onm/meta.owl#')
                                                                                            .where('?session meta:hasID ?id')
                                                                                            .select();
                                                    var sessionID;
                                                    //alert("META: \n"+sessionMeta);            
                                                    for(var subject in sessionMeta){
                                                            sessionID = sessionMeta[subject].id;
                                                            //alert(":::::::::::: createScopeAndSession ::::::::::::: SESSION: "+sessionID);
                                                    }
		    				    		
		    				    		var resources = document.getElementsByName("hasReference");
		    				    		
		    				    		var count = 0;
		    				    		
		    				    		for(var i=0; i<resources.length; i++){
		    				    			var resource = resources[i].value;	
		    				    			//dataset += "<owl:imports rdf:resource=\""+resource+"\"/>";
		    				    			count++;
		    				    			
		    				    			var demo = new Demo();
		    				    			
		    				    			demo.addSession(scopeID, sessionID, resource, count, resources.length);
		    				    			
		    				    		}
		    				    		
		    				    		$("#creating-scope").hide();
		    				    		
		    				    		var divIMG = document.getElementById("addSessionWait");
		    				    		
		    				    		var content = "<fieldset><legend> Integrity constraints</legend>";
									content += "<legend>RULES WITH DATATYPE PROPERTY ARE NOT SUPPORTED</legend>"
		    				 			content += "<ul id=\"ruleUL\" class=\"indent\"></ul>";
		    				 			content += "<a href=\"javascript:var demo=new Demo(); demo.ruleListRemove('"+scopeID+"'); demo.addIntegrityRule(true,'"+scopeID+"');\">add new rule for the integrity check</a>";
		    				 			content += "</fieldset>";
		    				 			content += "<div id=\"integrityDemoDIV\" class=\"hide\" stype=\"margin-top:20px;\">";
		    				 			content += "<input type=\"button\" id=\"runIntegrity\" value=\"check integrity\" onClick=\"javascript: var demo=new Demo(); demo.integrityCheck('"+scopeID+"')\">";
		    				 			content += "<br><br>";
		    				 			//content += "<h3>Valid contents</h3>";
		    				 			content += "<ul id=\"validContent\" class=\"indent\"></ul>";
		    				    		
		    				    		divIMG.innerHTML = "<br><br><center>Resources added to the session space</center><br><br>"+content;
		    				    		divIMG.style.display = 'block';
		    				    		
		    				    	}
		    				   	}
		    				}
			    			
			    			//alert(scopeID);
			    			
			    			ajax2.send("scope=http://localhost:9191/ontonet/ontology/"+scopeID);
			    		}
			    		
			    	}
			   	}
			}
			
				
			ajax.open("put", "/ontonet/ontology/"+scopeID+"?"+parameters, true);
			
			$("#creating-scope").show();
			
			ajax.send(null);
		
			
		}
	}
	
	
	
	
}

Demo.prototype.addSession = function(scopeID, sessionID, resource, count, length){
	
	//alert(":::::::::::: addSessions :::::::::::: "+sessionID);
	var ajax3 = getXMLHttpRequest();
	if(ajax3 != null){
		
		var session = sessionID.toString().split("^")[0].replace("\"", "").replace("\"", "");
		var scopeIRI = "http://localhost:9191/ontonet/ontology/"+scopeID;
		ajax3.open("put", "/ontonet/session?scope="+scopeIRI+"&session="+session+"&location="+resource, true);
		ajax3.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		
		var resourceElement = document.getElementById(resource);
		
		ajax3.onreadystatechange = function() {
			
		   	if(ajax3.readyState == 4) {
		   		var resourceElement = document.getElementById("load-"+resource);
		    	if (ajax3.status == 200) {
		    		
		    		resourceElement.innerHTML = "<img src=\"/intcheck/static/images/ok.gif\">";
					
		    	}
		    	else if(ajax3.status == 500){
		    		resourceElement.innerHTML = "<img src=\"/intcheck/static/images/delete.gif\">";
		    	}
		   	}
		}
		resourceElement.innerHTML = resourceElement.innerHTML + " <span id=\"load-"+resource+"\"><img class=\"small\" src=\"/intcheck/static/images/loadingSmall.gif\"></span>";
		
		ajax3.send(null);
	}
}

Demo.prototype.addIntegrityRule = function(showForm,scopeID){
	var time_scopeID = scopeID.replace("linkeddata","");
       window.ruleName = "http://localhost:9191/rule/demo_"+time_scopeID;
	if(showForm){//style=\"position:absolute\" 
		var content2 = "<div id=\"popupbox\">" +
						"Rule Syntax:" +
						"<center><textarea id=\"rulearea\">dbpedia = <http://dbpedia.org/ontology/> . ruleIntegrity[has(dbpedia:product, ?x, ?product) . is(dbpedia:Organisation, ?x) -> is(dbpedia:ValidContent, ?x)]</textarea></center><br><br>" +
						"<center><input type=\"button\" value=\"add\" onClick=\"javascript: var demo=new Demo(); demo.addIntegrityRule(false,'"+scopeID+"');\"></center>"+
						"</div>";

		TINY.box.show(content2,0,0,0,1);
		TINY.box.resize;
	}
	else{
		var ruleText=document.getElementById('rulearea').value;
		var recipe=new Recipe(); 
		var rule=new Rule();
		
		//recipe.addNewRecipe('http://kres.iks-project.eu/recipe/integrity', 'Integrity recipe', rule.addNewRule, 'http://kres.iks-project.eu/rule/integrity', ruleText, 'Integrity rule', rule);
		recipe.addNewRecipe(scopeID,'http://kres.iks-project.eu/recipe/integrity_'+time_scopeID, 'Integrity recipe', rule.addNewRule, window.ruleName, ruleText, 'Integrity rule', rule);
	}
	
}


Demo.prototype.integrityCheck = function(scopeID) {
	var ajax = getXMLHttpRequest();
	ajax.open("post", "http://localhost:9191/intcheck/classify/demo", true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.setRequestHeader("Accept", "application/rdf+json");
	var time_scopeID = scopeID.replace("linkeddata","");
	//alert(time_scopeID);
	ajax.onreadystatechange = function() {
		
	   	if(ajax.readyState == 4) {
			if (ajax.status == 200 || ajax.status == 409) {
				var json = ajax.responseText;
				
				var databank = $.rdf.databank().load(JSON.parse(json));
					
					var rdf = $.rdf({databank:databank});
					var validContents = null;
					validContents = rdf.prefix('dbpedia', 'http://dbpedia.org/ontology/').where('?content a dbpedia:ValidContent').select();
					var content="";					
					//alert("FIRST "+content);
					for(var validContent in validContents){
						content += "<li>"+validContents[validContent].content.toString().replace('<', '').replace('>', '');
					}
					//alert("AFTER "+content);
					//alert(content!="");
					if(content!=""){
						$("#validContent").html("<h3>Valid contents</h3>"+content);
					}else{
						$("#validContent").html("<h3>No valid contents</h3><br/><h3>Check the rule and if necessary change it</h3>");	
					}
			}
                        if(ajax.status == 500) {
                                   $("#validContent").html("<h3>Rule Syntax Error</h3><br/><h3>Check the rule syntax</h3>");
                        }
		}
	}
	$("#validContent").html("<center><img src=\"/intcheck/static/images/loadingSmall.gif\"></center>");
	var params = "scope=http://localhost:9191/ontonet/ontology/" + scopeID + "&rule=http://localhost:9191/rule/http://localhost:9191/rule/demo_"+time_scopeID;
	//var params = "scope=" + scopeID + "&recipe=http://kres.iks-project.eu/recipe/integrity_"+time_scopeID;
        ajax.send(params);
	  		
	    		
}

Demo.prototype.integrityCheckFISE = function() {
	
	var resources = document.getElementsByName("hasReference");
		
	var scope = new Scope();
	scope.create(scopeName, null, "http://ontologydesignpatterns.org/ont/iks/kres/dbpedia_3.5.1.owl", null, null, true, true);
	
	
	
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		
		ajax.open("get", "http://localhost:9191/ontonet/ontology/" + scopeName + "/http://dbpedia.org/ontology/", true);
		ajax.onreadystatechange = function() {
			
		   	if(ajax.readyState == 4) {
		    	if (ajax.status == 200) {
		    		var code = "<pre>"+ajax.responseText+"</pre>";
		    		var el = document.getElementById("fiseResult");
		    		el.innerHTML = el.innerHTML + code;
		    	}
		   	}
		}
		ajax.send(null);
	}
}

String.prototype.startsWith = function(str){
    return (this.indexOf(str) === 0);
}


function Graph() {
	return this;
}

Graph.prototype.load = function(uri, asynchronous) {
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		if(asynchronous){
			ajax.onreadystatechange = function() {
				
			   	if(ajax.readyState == 4) {
			    	if (ajax.status == 200) {
			    		
			    	}
			   	}
			}
		}
		else{
			ajax.open("get", uri,false);
			ajax.send(null);

			if (ajax.status==200){
				return ajax.responseXML;
			}

		}
	}
}

function Scope() {
	return this;
}

Scope.prototype.create = function(scopeID, corereg, coreont, customreg, customont, activate, asynchronous) {
	
	//alert(":::::::::::::: scope careate ::::::::::::::::: "+scopeID);

	if(scopeID != null){
		var ajax = getXMLHttpRequest();
		if(ajax != null){
			
			var parameters = "";
			
			if(corereg != null && corereg != ''){
				parameters += "corereg="+corereg;
			}
			if(coreont != null && coreont != ''){
				if(parameters != ''){
					parameters += "&";
				}
				parameters += "coreont="+coreont;
			}
			if(customreg != null && customreg != ''){
				if(parameters != ''){
					parameters += "&";
				}
				parameters += "customreg="+customreg;
			}
			if(customont != null && customont != ''){
				if(parameters != ''){
					parameters += "&";
				}
				parameters += "customont="+customont;
			}
			
			if(parameters != ''){
				parameters += "&";
			}
			if(activate!=null){
				if(activate){
					parameters += "activate=true";
				}
				else{
					parameters += "activate=false";
				}
			}
			else{
				parameters += "activate=false";
			}
			
			if(asynchronous){
				ajax.onreadystatechange = function() {
		
				   	if(ajax.readyState == 4) {
				    	if (ajax.status == 200) {
				    		
				    		
				    	}
				   	}
				}
				
				
				ajax.open("put", "/ontonet/ontology/"+scopeID+"?"+parameters, true);
				//console.log(parameters);
				ajax.send(null);
			}
			else{
				ajax.open("put", "/ontonet/ontology/"+scopeID+"?"+parameters, false);
				//console.log(parameters);
				ajax.send(null);
				if (ajax.status==200){
					
				}
				else if(ajax.status==409){
					
				}
				
			}
			
		}
	}
}
	
function Session() {
	return this;
}

Session.prototype.create = function(scopeID, asynchronous) {
	
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		ajax.open("post", "/ontonet/session", false);
		ajax.setRequestHeader("Accept", "application/rdf+json");
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
		
		
		if(asynchronous){
			
			ajax.onreadystatechange = function() {
				
			   	if(ajax.readyState == 4) {
			    	if (ajax.status == 200) {
			
						var databank = $.rdf.databank().load(JSON.parse(sessionJSON));
						
						var rdf = $.rdf({databank:databank});
						
						
						var sessionMeta = rdf.prefix('meta', 'http://kres.iks-project.eu/ontology/onm/meta.owl#')
											.where('?session meta:hasID ?id')
											.select();
						var sessionID;
						for(var subject in sessionMeta){
							sessionID = sessionMeta[subject].id;
						}
						//alert(" :::::::::: session create :::::::::::: "+sessionID);
						window.sessionID = sessionID;
						
						
						for(var i=0; i<resources.length; i++){
							var resource = resources[i].value;	
							//dataset += "<owl:imports rdf:resource=\""+resource+"\"/>";
							session.addOntology("http://localhost:9191/ontonet/ontology/"+scopeName, sessionID, resource);
						}
			    	}
			   	}
			}
		}
		
		ajax.send("scope=http://localhost:9191/ontonet/ontology/"+scopeID);
	}
}

Session.prototype.addOntology = function(scopeID, sessionID, ontology) {
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		
		var session = sessionID.toString().split("^")[0].replace("\"", "").replace("\"", "");
		ajax.open("put", "/ontonet/session?scope="+scopeID+"&session="+session+"&location="+ontology, false);
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		
		ajax.onreadystatechange = function() {
			
		   	if(ajax.readyState == 4) {
		    	if (ajax.status == 200) {
					var reasoner = new Reasoner();
					reasoner.classify(scopeName);
		    	}
		   	}
		}
		
		
		ajax.send(null);
		
	}
}

function Reasoner() {
	return this;
}

Reasoner.prototype.classify = function(scopeID, sessionID, recipe) {
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		ajax.open("post", "/reasoner/classify", false);
		ajax.setRequestHeader("Accept", "multipart/form-data");
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
		ajax.onreadystatechange = function() {
			
		   	if(ajax.readyState == 4) {
		    	if (ajax.status == 200) {
		    	}
		   	}
		}
		ajax.send("session="+sessionID+"&scope="+scopeID+"&recipe="+recipe);
	}
}


function Scope() {
	return this;
}

Scope.prototype.describe = function(scopeID) {
	var ajax = getXMLHttpRequest();
	if(ajax != null){
		ajax.open("get", "/ontonet/ontology/"+scopeID, false);
		ajax.setRequestHeader("Accept", "application/rdf+json");
		ajax.onreadystatechange = function() {
			
		   	if(ajax.readyState == 4) {
		    	if (ajax.status == 200) {
		    		var json = ajax.responseText;
		    		
		    		var databank = $.rdf.databank().load(JSON.parse(json));
					
					var rdf = $.rdf({databank:databank});
					
					
					
					var cores = rdf.prefix('kres', 'http://kres.iks-project.eu/scopes#')
										.where('?scope kres:hasCoreOntology ?core')
										.select();
					var content = "<div class=\"indent\"><p>Core ontologies</p>" +
								  "<ul class=\"indent\">";
					for(var core in cores){
						content += "<li>"+cores[core].core.toString().replace('<', '').replace('>', '');
					}
					content += "</ul></div>";
					
					customs = rdf.prefix('kres', 'http://kres.iks-project.eu/scopes#')
										.where('?scope kres:hasCustomOntology ?custom')
										.select();
					content += "<div class=\"indent\"><p>Custom ontologies</p>" +
								  "<ul class=\"indent\">";
					for(var custom in customs){
						content += "<li>"+customs[custom].custom.toString().replace('<', '').replace('>', '');
					}
					content += "</ul></div>";
					
					var div = document.getElementById("description-"+scopeID);
					if(div != null){
						div.innerHTML = div.innerHTML + content; 
					}
		    	}
		   	}
		}
		ajax.send(null);
	}
}


String.prototype.replaceAll = function(stringToFind,stringToReplace){
var temp = this;
var index = temp.indexOf(stringToFind);
    while(index != -1){
        temp = temp.replace(stringToFind,stringToReplace);
        index = temp.indexOf(stringToFind);
    }
    return temp;
}

function unique(arr) {
	var r = new Array();
	o:for(var i = 0, n = arr.length; i < n; i++)
	{
		for(var x = 0, y = r.length; x < y; x++)
		{
			if(r[x]==arr[i])
			{
				continue o;
			}
		}
		r[r.length] = arr[i];
	}
	return r;
}
