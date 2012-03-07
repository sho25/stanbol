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

begin_import
import|import
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
name|collector
operator|.
name|UnmodifiableOntologyCollectorException
import|;
end_import

begin_comment
comment|/**  * An ontology space that wraps the components that can be customized by CMS developers, IKS customizers and  * the like. The custom ontology space becomes read-only after bootstrapping (i.e. after a call to  *<code>setUp()</code>).  *   * The ontologies in a custom space typically depend on those in the core space. However, a custom space does  *<i>not</i> know which is the core space, it only imports its ontologies. The core-custom-session  * relationship between spaces is a scope is handled by external objects.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|CustomOntologySpace
extends|extends
name|OntologySpace
block|{
comment|/**      * Logically links this custom space with the supplied core ontology space, so that the top ontology in      * the former will import those in the latter.<br>      *<br>      * This relationship is expected to hold at all times once the space is active, however the method to set      * it is available in case implementations require to perform other operations between the creation of      * ontology spaces and their linking.      *       * @deprecated space linking is performed by the parent scope at OWL export time. Implementations do      *             nothing.      * @param coreSpace      *            the core ontology space to be linked      * @param skipRoot      *            if true, the custom root ontology will not import the core root ontology straight away, but      *            instead all of its axioms and import statements will be copied. Useful for implementations      *            that construct root ontologies in memory but do not store them.      */
name|void
name|attachCoreSpace
parameter_list|(
name|CoreOntologySpace
name|coreSpace
parameter_list|,
name|boolean
name|skipRoot
parameter_list|)
throws|throws
name|UnmodifiableOntologyCollectorException
function_decl|;
block|}
end_interface

end_unit
