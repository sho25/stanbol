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
<@common.page title="Jobs" hasrestapi=false> 
		
  <div class="panel">
<p><b>Status: </b> ${it.jobInfo.status}</p>
<p><b>Output location: </b> <a href="${it.jobInfo.outputLocation}">${it.jobInfo.outputLocation}</a></p>
<p><b>Messages:</b> </p>
<ul>
  <#list it.jobInfo.messages as message>
    <li>${message}</li>
  </#list>
</ul>
    
  </div>
</@common.page>
</#escape>