<#--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<#import "/imports/common.ftl" as common>

<#escape x as x?html>
  <@common.page title="${it.scope.ID} : Apache Stanbol OntoNet scope" hasrestapi=false>
	
	<a href="${it.publicBaseUri}ontonet/ontology">Scope Manager</a> &gt; Scope <tt>${it.scope.ID}</tt>
	
    <div class="panel" id="webview">
  
    <h3>Load ontologies</h3>
    <form method="POST" enctype="multipart/form-data" accept-charset="utf-8">
    <fieldset>
      <legend>From a local file</legend>
      <p><b>File:</b> <input type="file" name="file"/> 
        Input format:
        <select name="format">
          <option value="auto">Auto</option>
          <option value="application/rdf+xml">RDF/XML</option>
          <option value="application/rdf+json">RDF/JSON</option>
          <option value="text/turtle">Turtle</option>
          <option value="text/rdf+nt">N-TRIPLE</option>
          <option value="text/rdf+n3">N3</option>
          <!--
          <option value="application/owl+xml">OWL/XML</option>
          <option value="text/owl-manchester">Manchester OWL</option>
          <option value="text/owl-functional">OWL Functional</option>
          -->
        </select>
        <input type="submit" value="Send"/>
      </p>
    </fieldset>
    </form>
  
    <form method="POST" enctype="multipart/form-data" accept-charset="utf-8">
    <fieldset>
      <legend>From a URL</legend>
      <p>
        <b>URL:</b> <input type="text" name="url" size="80" value="http://"/> 
        <input type="submit" value="Fetch"/>
      </p>
    </fieldset>
    </form>
    
    <form method="POST" enctype="multipart/form-data" accept-charset="utf-8">
    <fieldset>
      <legend>From a whole ontology library</legend>
      <p><b>Library ID:</b> 
        <select name="library">  
        <option value="null">&lt;please select a library&gt;</option>
        <#list it.libraries as lib>
        <option value="${lib.IRI}">${lib.name}</option>
        </#list>
        </select>
        <input type="submit" value="Load"/>
      </p>
    </fieldset>
    </form>
  
  Note: OWL import targets will be included. Ontology loading is set to fail on missing imports.

  <h3>Stored ontologies</h3>
  <h4>Custom</h4>
  <#assign ontologies = it.customOntologies>
  <div class="storeContents">
    <table id="customOnt">
      <div>
        <tr>
          <th>Name</th>
        </tr>
        <#list ontologies as ontology>
          <tr>
            <td><a href="/ontonet/ontology/${it.scope.ID}/${ontology}">${ontology}</a></td>
          </tr>
        </#list>
      </div>
    </table> <!-- allOntologies -->
  </div>
  <h4>Core</h4>
  <#assign ontologies2 = it.coreOntologies>
  <div class="storeContents">
    <table id="coreOnt">
      <div>
        <tr>
          <th>Name</th>
        </tr>
        <#list ontologies2 as ontology2>
          <tr>
            <td><a href="/ontonet/ontology/${it.scope.ID}/${ontology2}">${ontology2}</a></td>
          </tr>
        </#list>
      </div>
    </table> <!-- allOntologies -->
  </div>
  
    </div> <!-- web view -->

  </@common.page>
</#escape>