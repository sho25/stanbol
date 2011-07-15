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
<h4>Subresource reasoners/check-consistency</h4>
<p>This service provides consistency check.</p>


<h4> GET reasoners/check-consistency</h4>
<table>
  <tbody>
    <tr>
      <th>Description</th>
      <td>Service to check the consistency of the dereferenciable resource IRI provided.</td>
    </tr>
    <tr>
      <th>Request</th>
      <td>GET <code>/reasoners/check-consistency/{resource IRI}</code></td>
    </tr>
    <tr>
      <th>Parameter</th>
      <td>
      <span style="font-style:italic">none</span>
      </td>
    </tr>
    <tr>
      <th>Produces</th>
      <td><span style="font-style:italic">nothing</span></td>
    </tr>
  </tbody>
</table>

<h5>Example</h5>

<pre>INSERIRE ESEMPIO1</pre>

<h4> POST reasoners/check-consistency</h4>
<table>
  <tbody>
    <tr>
      <th>Description</th>
      <td>Service to check the consistency with relation to a scope, a session and a recipe, providing an input file or a input graph IRI.</td>
    </tr>
    <tr>
      <th>Request</th>
      <td>POST <code>/reasoners/check-consistency</code></td>
    </tr>
    <tr>
      <th>Parameter</th>
      <td>
      <ul>
      	<li><code>session</code>: the session IRI used to classify the input. (Cannot be used in conjunction with <code>scope</code>.)</li>
      	<li><code>scope</code>: the scope IRI used to classify the input. (Cannot be used in conjunction with <code>session</code>.)</li>
      	<li><code>recipe</code>: the recipe IRI from the service.</li>
      	<li><code>file</code>: a file to be given as input classified. (Cannot be used in conjunction with <code>input-graph</code>.)</li>
      	<li><code>input-graph</code>: a reference to a graph IRI in the knowledge store. (Cannot be used in conjunction with <code>file</code>.)</li>
      	<li><code>owllink-endpoint</code>: reasoner server end-point URL. (If this parameter is not provided, the system will use the HermiT reasoner HermiT).</li>
      </ul>
      </td>
    </tr>
    <tr>
      <th>Consumes</th>
      <td><code>Content-type:multipart/form-data</code</td>
    </tr>
    <tr>
      <th>Produces</th>
      <td>An ontology. Format depends on requested media type</td>
    </tr>
  </tbody>
</table>

<h5>Example</h5>

<pre>INSERIRE ESEMPIO1</pre>
<pre>INSERIRE ESEMPIO2</pre>
<pre>INSERIRE ESEMPIO3</pre>

