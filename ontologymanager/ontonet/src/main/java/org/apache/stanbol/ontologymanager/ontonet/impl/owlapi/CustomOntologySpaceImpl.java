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
name|impl
operator|.
name|owlapi
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
name|ontology
operator|.
name|CoreOntologySpace
import|;
end_import

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
name|ontology
operator|.
name|CustomOntologySpace
import|;
end_import

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
name|ontology
operator|.
name|SpaceType
import|;
end_import

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
name|ontology
operator|.
name|UnmodifiableOntologyCollectorException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologyManager
import|;
end_import

begin_comment
comment|/**  * Default implementation of the custom ontology space.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|CustomOntologySpaceImpl
extends|extends
name|AbstractOntologySpaceImpl
implements|implements
name|CustomOntologySpace
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SUFFIX
init|=
name|SpaceType
operator|.
name|CUSTOM
operator|.
name|getIRISuffix
argument_list|()
decl_stmt|;
specifier|protected
specifier|static
name|String
name|buildId
parameter_list|(
name|String
name|scopeID
parameter_list|)
block|{
return|return
operator|(
name|scopeID
operator|!=
literal|null
condition|?
name|scopeID
else|:
literal|""
operator|)
operator|+
literal|"/"
operator|+
name|SUFFIX
return|;
block|}
specifier|public
name|CustomOntologySpaceImpl
parameter_list|(
name|String
name|scopeID
parameter_list|,
name|IRI
name|namespace
parameter_list|)
block|{
name|super
argument_list|(
name|buildId
argument_list|(
name|scopeID
argument_list|)
argument_list|,
name|namespace
argument_list|,
name|SpaceType
operator|.
name|CUSTOM
argument_list|)
expr_stmt|;
block|}
specifier|public
name|CustomOntologySpaceImpl
parameter_list|(
name|String
name|scopeID
parameter_list|,
name|IRI
name|namespace
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|)
block|{
name|super
argument_list|(
name|buildId
argument_list|(
name|scopeID
argument_list|)
argument_list|,
name|namespace
argument_list|,
name|SpaceType
operator|.
name|CUSTOM
argument_list|,
name|ontologyManager
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
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
block|{
comment|// OWLOntology o = coreSpace.getTopOntology();
comment|// // This does the append thingy
comment|// log.debug("Attaching " + o + " TO " + getTopOntology() + " ...");
comment|// try {
comment|// // It is in fact the addition of the core space top ontology to the
comment|// // custom space, with import statements and all.
comment|// addOntology(new RootOntologySource(o, null));
comment|// // log.debug("ok");
comment|// } catch (Exception ex) {
comment|// log.error("FAILED", ex);
comment|// }
block|}
comment|/**      * Once it is set up, a custom space is write-locked.      */
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|setUp
parameter_list|()
block|{
name|locked
operator|=
literal|true
expr_stmt|;
block|}
comment|/**      * Once it is torn down, a custom space is write-unlocked.      */
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|tearDown
parameter_list|()
block|{
name|locked
operator|=
literal|false
expr_stmt|;
block|}
block|}
end_class

end_unit
