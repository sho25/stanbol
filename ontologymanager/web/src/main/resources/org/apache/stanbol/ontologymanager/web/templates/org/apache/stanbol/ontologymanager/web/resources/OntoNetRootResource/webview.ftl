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

  <h3>Load an ontology</h3>
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
  
  Note: OWL import targets will be included. Ontology loading is set to fail on missing imports.

  <h3>Stored ontologies</h3>
  <#assign ontologies = it.ontologies>
  <div class="storeContents">
    <table id="allOntologies">
      <div>
        <tr>
          <th>Name</th>
        </tr>
        <#list it.ontologies as ontology>
          <tr>
            <td><a href="${it.publicBaseUri}ontonet/${ontology}">${ontology}</a></td>
          </tr>
        </#list>
      </div>
    </table> <!-- allScopes -->
  </div>
  
  <hr>
  
  <p>
  The following concepts have been introduced along with the Ontology Network Manager:
  <ul>
    <li>
      <u><em>Scope</em></u>: a "logical realm" for all the ontologies that encompass a certain CMS-related set of concepts (such as "User", "ACL", "Event", "Content", "Domain", "Reengineering", "Community", "Travelling" etc.). Scopes never inherit from each other, though they can load the same ontologies if need be.
    </li>
    <li>
      <u><em>Space</em></u>: an access-restricted container for synchronized access to ontologies within a scope. The ontologies in a scope are loaded within its set of spaces. An ontology scope contains: (a) exactly one <em>core space</em>, which contains the immutable set of essential ontologies that describe the scope; (b) exactly one (possibly empty) <em>custom space</em>, which extends the core space according to specific CMS needs (e.g. the core space for the User scope may contains alignments to FOAF).
    </li>
    <li>
      <em><u>Session</u></em>: a collector of volatile semantic data, not intended for persistent storage. Sessions can be used for stateful management of ontology networks. It is not equivalent to an HTTP session (since it can live persistently across multiple HTTP sessions), although its behaviour can reflect the one of the HTTP session that created it, if required by the implementation.
    </li>
  </ul>
  </p>
