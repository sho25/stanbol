begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|scope
package|;
end_package

begin_comment
comment|/**  * An ontology collector that maintains references to core ontologies that will be imported by any ontology in  * the custom space of a scope. They are used for boosting reasoning operations. Core spaces should only be  * created and populated upon creation of the corresponding ontology scope and not modified afterwards.  *   * @deprecated Packages, class names etc. containing "ontonet" in any capitalization are being phased out. In  *             addition, the distinction between core and custom spaces will disappear at the operational  *             level. Please switch to  *             {@link org.apache.stanbol.ontologymanager.servicesapi.scope.OntologySpace} as soon as possible.  *   * @see org.apache.stanbol.ontologymanager.servicesapi.scope.OntologySpace  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|CoreOntologySpace
extends|extends
name|OntologySpace
block|{  }
end_interface

end_unit

