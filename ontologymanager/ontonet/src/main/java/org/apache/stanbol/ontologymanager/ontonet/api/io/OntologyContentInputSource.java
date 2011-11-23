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
name|java
operator|.
name|io
operator|.
name|InputStream
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
name|owl
operator|.
name|util
operator|.
name|OWLUtils
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
name|apibinding
operator|.
name|OWLManager
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
specifier|public
class|class
name|OntologyContentInputSource
extends|extends
name|AbstractOWLOntologyInputSource
block|{
specifier|public
name|OntologyContentInputSource
parameter_list|(
name|InputStream
name|content
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|this
argument_list|(
name|content
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|OntologyContentInputSource
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|OWLOntologyManager
name|manager
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|bindPhysicalIri
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|bindRootOntology
argument_list|(
name|manager
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|content
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"<ONTOLOGY_CONTENT>"
operator|+
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|getRootOntology
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit
