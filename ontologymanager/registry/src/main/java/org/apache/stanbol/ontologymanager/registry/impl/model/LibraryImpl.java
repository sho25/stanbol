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
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|LibraryContentNotLoadedException
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
name|RegistryContentException
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
name|RegistryOntologyNotLoadedException
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
name|RegistryItem
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
name|OWLOntology
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
name|OWLOntologyAlreadyExistsException
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
name|OWLOntologyDocumentAlreadyExistsException
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

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Default implementation of the ontology library model.  */
end_comment

begin_class
specifier|public
class|class
name|LibraryImpl
extends|extends
name|AbstractRegistryItem
implements|implements
name|Library
block|{
specifier|private
name|OWLOntologyManager
name|cache
decl_stmt|;
specifier|private
name|boolean
name|loaded
init|=
literal|false
decl_stmt|;
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
name|LibraryImpl
parameter_list|(
name|IRI
name|iri
parameter_list|)
block|{
name|this
argument_list|(
name|iri
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|LibraryImpl
parameter_list|(
name|IRI
name|iri
parameter_list|,
name|OWLOntologyManager
name|cache
parameter_list|)
block|{
name|super
argument_list|(
name|iri
argument_list|)
expr_stmt|;
name|setCache
argument_list|(
name|cache
argument_list|)
expr_stmt|;
block|}
specifier|public
name|LibraryImpl
parameter_list|(
name|IRI
name|iri
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|this
argument_list|(
name|iri
argument_list|,
name|name
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|LibraryImpl
parameter_list|(
name|IRI
name|iri
parameter_list|,
name|String
name|name
parameter_list|,
name|OWLOntologyManager
name|cache
parameter_list|)
block|{
name|super
argument_list|(
name|iri
argument_list|,
name|name
argument_list|)
expr_stmt|;
name|setCache
argument_list|(
name|cache
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntologyManager
name|getCache
parameter_list|()
block|{
return|return
name|cache
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
throws|throws
name|RegistryContentException
block|{
comment|/*          * Note that this implementation is not synchronized. Listeners may indefinitely be notified before or          * after the rest of this method is executed. If listeners call loadOntologies(), they could still get          * a RegistryContentException, which however they can catch by calling loadOntologies() and          * getOntologies() in sequence.          */
name|fireContentRequested
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// If no listener has saved the day by loading the ontologies by now, an exception will be thrown.
if|if
condition|(
operator|!
name|loaded
condition|)
throw|throw
operator|new
name|LibraryContentNotLoadedException
argument_list|(
name|this
argument_list|)
throw|;
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|<
name|OWLOntology
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RegistryItem
name|child
range|:
name|getChildren
argument_list|()
control|)
block|{
if|if
condition|(
name|child
operator|instanceof
name|RegistryOntology
condition|)
block|{
name|OWLOntology
name|o
init|=
operator|(
operator|(
name|RegistryOntology
operator|)
name|child
operator|)
operator|.
name|asOWLOntology
argument_list|()
decl_stmt|;
comment|// Should never be null if the library was loaded correctly (an error should have already been
comment|// thrown when loading it), but just in case.
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
name|ontologies
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
else|else
throw|throw
operator|new
name|RegistryOntologyNotLoadedException
argument_list|(
operator|(
name|RegistryOntology
operator|)
name|child
argument_list|)
throw|;
block|}
block|}
return|return
name|ontologies
return|;
block|}
annotation|@
name|Override
specifier|public
name|Type
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isLoaded
parameter_list|()
block|{
return|return
name|loaded
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|loadOntologies
parameter_list|(
name|OWLOntologyManager
name|mgr
parameter_list|)
block|{
if|if
condition|(
name|mgr
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"A null ontology manager is not allowed."
argument_list|)
throw|;
for|for
control|(
name|RegistryItem
name|item
range|:
name|getChildren
argument_list|()
control|)
block|{
if|if
condition|(
name|item
operator|instanceof
name|RegistryOntology
condition|)
block|{
name|RegistryOntology
name|o
init|=
operator|(
name|RegistryOntology
operator|)
name|item
decl_stmt|;
name|IRI
name|id
init|=
name|o
operator|.
name|getIRI
argument_list|()
decl_stmt|;
try|try
block|{
name|o
operator|.
name|setOWLOntology
argument_list|(
name|mgr
operator|.
name|loadOntology
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyAlreadyExistsException
name|e
parameter_list|)
block|{
name|o
operator|.
name|setOWLOntology
argument_list|(
name|mgr
operator|.
name|getOntology
argument_list|(
name|e
operator|.
name|getOntologyID
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyDocumentAlreadyExistsException
name|e
parameter_list|)
block|{
name|o
operator|.
name|setOWLOntology
argument_list|(
name|mgr
operator|.
name|getOntology
argument_list|(
name|e
operator|.
name|getOntologyDocumentIRI
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to load ontology "
operator|+
name|id
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|loaded
operator|=
literal|true
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setCache
parameter_list|(
name|OWLOntologyManager
name|cache
parameter_list|)
block|{
comment|// TODO use the ontology manager factory.
if|if
condition|(
name|cache
operator|==
literal|null
condition|)
name|cache
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
expr_stmt|;
name|this
operator|.
name|cache
operator|=
name|cache
expr_stmt|;
block|}
block|}
end_class

end_unit

