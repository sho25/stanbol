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
name|io
package|;
end_package

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
name|OWLOntologyCreationException
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

begin_class
annotation|@
name|Deprecated
specifier|public
class|class
name|RootOntologyIRISource
extends|extends
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|sources
operator|.
name|owlapi
operator|.
name|RootOntologySource
block|{
specifier|public
name|RootOntologyIRISource
parameter_list|(
name|IRI
name|rootPhysicalIri
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|super
argument_list|(
name|rootPhysicalIri
argument_list|)
expr_stmt|;
block|}
specifier|public
name|RootOntologyIRISource
parameter_list|(
name|IRI
name|rootPhysicalIri
parameter_list|,
name|OWLOntologyManager
name|manager
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|super
argument_list|(
name|rootPhysicalIri
argument_list|,
name|manager
argument_list|)
expr_stmt|;
block|}
specifier|public
name|RootOntologyIRISource
parameter_list|(
name|IRI
name|rootPhysicalIri
parameter_list|,
name|OWLOntologyManager
name|manager
parameter_list|,
name|boolean
name|ignoreIriMappers
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|this
argument_list|(
name|rootPhysicalIri
argument_list|,
name|manager
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

