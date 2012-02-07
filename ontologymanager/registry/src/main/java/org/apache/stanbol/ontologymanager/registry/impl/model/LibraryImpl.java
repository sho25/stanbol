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
name|io
operator|.
name|IOException
import|;
end_import

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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|TripleCollection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|TcManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|TcProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|WeightedTcProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|Parser
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
name|commons
operator|.
name|owl
operator|.
name|transformation
operator|.
name|OWLAPIToClerezzaConverter
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
name|OntologyProvider
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
name|clerezza
operator|.
name|ClerezzaOntologyProvider
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
name|IllegalRegistryCycleException
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
name|RegistryOperation
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
name|OntologyProvider
argument_list|<
name|?
argument_list|>
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
comment|/**      * Creates a new instance of {@link LibraryImpl}.      *       * @param iri      *            the library identifier and possible physical location.      * @param cache      *            the {@link OWLOntologyManager} to be used for caching ontologies in-memory.      */
specifier|public
name|LibraryImpl
parameter_list|(
name|IRI
name|iri
parameter_list|,
name|OntologyProvider
argument_list|<
name|?
argument_list|>
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
comment|/**      * Creates a new instance of {@link LibraryImpl}.      *       * @param iri      *            the library identifier and possible physical location.      * @param name      *            the short name of this library.      * @param cache      *            the {@link OWLOntologyManager} to be used for caching ontologies in-memory.      */
specifier|public
name|LibraryImpl
parameter_list|(
name|IRI
name|iri
parameter_list|,
name|String
name|name
parameter_list|,
name|OntologyProvider
argument_list|<
name|?
argument_list|>
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
name|void
name|addChild
parameter_list|(
name|RegistryItem
name|child
parameter_list|)
throws|throws
name|RegistryContentException
block|{
if|if
condition|(
name|child
operator|instanceof
name|Registry
operator|||
name|child
operator|instanceof
name|Library
condition|)
throw|throw
operator|new
name|IllegalRegistryCycleException
argument_list|(
name|this
argument_list|,
name|child
argument_list|,
name|RegistryOperation
operator|.
name|ADD_CHILD
argument_list|)
throw|;
name|super
operator|.
name|addChild
argument_list|(
name|child
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addParent
parameter_list|(
name|RegistryItem
name|parent
parameter_list|)
throws|throws
name|RegistryContentException
block|{
if|if
condition|(
name|parent
operator|instanceof
name|RegistryOntology
operator|||
name|parent
operator|instanceof
name|Library
condition|)
throw|throw
operator|new
name|IllegalRegistryCycleException
argument_list|(
name|this
argument_list|,
name|parent
argument_list|,
name|RegistryOperation
operator|.
name|ADD_PARENT
argument_list|)
throw|;
name|super
operator|.
name|addParent
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|OntologyProvider
argument_list|<
name|?
argument_list|>
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
name|OWLOntology
operator|)
name|getCache
argument_list|()
operator|.
name|getStoredOntology
argument_list|(
operator|(
operator|(
name|RegistryOntology
operator|)
name|child
operator|)
operator|.
name|getIRI
argument_list|()
argument_list|,
name|OWLOntology
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// OWLOntology o = ((RegistryOntology) child).getRawOntology(this.getIRI());
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
name|OWLOntology
name|getOntology
parameter_list|(
name|IRI
name|id
parameter_list|)
throws|throws
name|RegistryContentException
block|{
name|Object
name|store
init|=
name|cache
operator|.
name|getStore
argument_list|()
decl_stmt|;
if|if
condition|(
name|store
operator|instanceof
name|WeightedTcProvider
condition|)
block|{
name|WeightedTcProvider
name|wtcp
init|=
operator|(
name|WeightedTcProvider
operator|)
name|store
decl_stmt|;
name|TripleCollection
name|tc
init|=
name|wtcp
operator|.
name|getTriples
argument_list|(
operator|new
name|UriRef
argument_list|(
name|id
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|OWLAPIToClerezzaConverter
operator|.
name|clerezzaGraphToOWLOntology
argument_list|(
name|tc
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|store
operator|instanceof
name|OWLOntologyManager
condition|)
block|{
name|OWLOntologyManager
name|omgr
init|=
operator|(
name|OWLOntologyManager
operator|)
name|store
decl_stmt|;
return|return
name|omgr
operator|.
name|getOntology
argument_list|(
name|id
argument_list|)
return|;
block|}
else|else
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Library implementation was assigned an unsupported cache type."
argument_list|)
throw|;
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
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|loader
parameter_list|)
block|{
if|if
condition|(
name|loader
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"A null loader is not allowed."
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
comment|// No preferred key, we don't have a prefix here.
name|String
name|key
init|=
name|loader
operator|.
name|loadInStore
argument_list|(
name|id
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|key
operator|==
literal|null
operator|||
name|key
operator|.
name|isEmpty
argument_list|()
condition|)
name|log
operator|.
name|error
argument_list|(
literal|"Empty storage key. Ontology {} was apparently not stored."
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"I/O error occurred loading {}"
argument_list|,
name|id
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
name|removeChild
parameter_list|(
name|RegistryItem
name|child
parameter_list|)
block|{
name|super
operator|.
name|removeChild
argument_list|(
name|child
argument_list|)
expr_stmt|;
comment|// Also unload the ontology version that comes from this library.
block|}
annotation|@
name|Override
specifier|public
name|void
name|setCache
parameter_list|(
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|cache
parameter_list|)
block|{
if|if
condition|(
name|cache
operator|==
literal|null
condition|)
name|cache
operator|=
operator|new
name|ClerezzaOntologyProvider
argument_list|(
name|TcManager
operator|.
name|getInstance
argument_list|()
argument_list|,
literal|null
argument_list|,
name|Parser
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
else|else
block|{
name|Object
name|store
init|=
name|cache
operator|.
name|getStore
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|store
operator|instanceof
name|TcProvider
operator|||
name|store
operator|instanceof
name|OWLOntologyManager
operator|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Type "
operator|+
name|store
operator|.
name|getClass
argument_list|()
operator|+
literal|"is not supported. This ontology library implementation only supports caches based on either "
operator|+
name|TcProvider
operator|.
name|class
operator|+
literal|" or "
operator|+
name|OWLOntologyManager
operator|.
name|class
argument_list|)
throw|;
block|}
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

