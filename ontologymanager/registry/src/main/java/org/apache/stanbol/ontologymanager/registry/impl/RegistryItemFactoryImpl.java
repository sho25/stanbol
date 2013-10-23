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
name|registry
operator|.
name|impl
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
name|registry
operator|.
name|api
operator|.
name|RegistryItemFactory
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
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|Library
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
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|Registry
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
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|RegistryOntology
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
name|registry
operator|.
name|impl
operator|.
name|model
operator|.
name|LibraryImpl
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
name|registry
operator|.
name|impl
operator|.
name|model
operator|.
name|RegistryImpl
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
name|registry
operator|.
name|impl
operator|.
name|model
operator|.
name|RegistryOntologyImpl
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
name|servicesapi
operator|.
name|ontology
operator|.
name|OntologyProvider
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
name|OWLNamedObject
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
name|OWLOntology
import|;
end_import

begin_comment
comment|/**  * Default implementation of a registry item factory.  *   * @author alexdma  */
end_comment

begin_class
specifier|public
class|class
name|RegistryItemFactoryImpl
implements|implements
name|RegistryItemFactory
block|{
specifier|private
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|cache
decl_stmt|;
comment|/**      * Creates a new instance of {@link RegistryItemFactoryImpl}.      */
specifier|public
name|RegistryItemFactoryImpl
parameter_list|(
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|provider
parameter_list|)
block|{
name|this
operator|.
name|cache
operator|=
name|provider
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Library
name|createLibrary
parameter_list|(
name|OWLNamedObject
name|ind
parameter_list|)
block|{
return|return
operator|new
name|LibraryImpl
argument_list|(
name|ind
operator|.
name|getIRI
argument_list|()
argument_list|,
name|ind
operator|.
name|getIRI
argument_list|()
operator|.
name|getFragment
argument_list|()
argument_list|,
name|cache
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Registry
name|createRegistry
parameter_list|(
name|OWLOntology
name|o
parameter_list|)
block|{
return|return
name|o
operator|.
name|isAnonymous
argument_list|()
condition|?
literal|null
else|:
operator|new
name|RegistryImpl
argument_list|(
name|o
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|,
name|o
operator|.
name|getOntologyID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|RegistryOntology
name|createRegistryOntology
parameter_list|(
name|OWLNamedObject
name|ind
parameter_list|)
block|{
return|return
operator|new
name|RegistryOntologyImpl
argument_list|(
name|ind
operator|.
name|getIRI
argument_list|()
argument_list|,
name|ind
operator|.
name|getIRI
argument_list|()
operator|.
name|getFragment
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

