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
name|ontology
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
name|impl
operator|.
name|io
operator|.
name|ClerezzaOntologyStorage
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
comment|/**  * Default implementation of the core ontology space.  *   * @author alexdma  */
end_comment

begin_class
specifier|public
class|class
name|CoreOntologySpaceImpl
extends|extends
name|AbstractOntologySpaceImpl
implements|implements
name|CoreOntologySpace
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SUFFIX
init|=
name|SpaceType
operator|.
name|CORE
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
name|CoreOntologySpaceImpl
parameter_list|(
name|String
name|scopeID
parameter_list|,
name|IRI
name|namespace
parameter_list|,
name|ClerezzaOntologyStorage
name|storage
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
name|CORE
argument_list|,
name|storage
argument_list|)
expr_stmt|;
block|}
specifier|public
name|CoreOntologySpaceImpl
parameter_list|(
name|String
name|scopeID
parameter_list|,
name|IRI
name|namespace
parameter_list|,
name|ClerezzaOntologyStorage
name|storage
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
name|CORE
argument_list|,
name|storage
argument_list|,
name|ontologyManager
argument_list|)
expr_stmt|;
block|}
comment|/**      * When set up, a core space is write-locked.      */
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
comment|/**      * When torn down, a core space releases its write-lock.      */
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

