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
name|io
package|;
end_package

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
name|commons
operator|.
name|owl
operator|.
name|OWLOntologyManagerFactory
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
name|OfflineConfiguration
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
name|io
operator|.
name|AbstractOWLOntologyInputSource
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
name|io
operator|.
name|OntologyInputSource
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
name|io
operator|.
name|OntologySetInputSource
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
name|io
operator|.
name|RootOntologySource
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
name|util
operator|.
name|OntologyUtils
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
name|RegistryManager
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
comment|/**  * An ontology input source that loads all the ontologies in a given library and attaches them to a parent  * ontology, either new or supplied by the developer. This input source can either accept an already built  * {@link LibraryImpl} object, or parse a library OWL file from its logical URI.  */
end_comment

begin_class
specifier|public
class|class
name|LibrarySource
extends|extends
name|AbstractOWLOntologyInputSource
implements|implements
name|OntologySetInputSource
block|{
comment|/**      * Creates a new ontology manager that shares the same offline configuration as the registry manager.      *       * @param registryManager      * @return      */
specifier|private
specifier|static
name|OWLOntologyManager
name|checkOntologyManager
parameter_list|(
name|RegistryManager
name|registryManager
parameter_list|)
block|{
name|OfflineConfiguration
name|offline
init|=
name|registryManager
operator|.
name|getOfflineConfiguration
argument_list|()
decl_stmt|;
if|if
condition|(
name|offline
operator|==
literal|null
condition|)
return|return
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
return|;
return|return
name|OWLOntologyManagerFactory
operator|.
name|createOWLOntologyManager
argument_list|(
name|offline
operator|.
name|getOntologySourceLocations
argument_list|()
operator|.
name|toArray
argument_list|(
operator|new
name|IRI
index|[
literal|0
index|]
argument_list|)
argument_list|)
return|;
block|}
specifier|private
name|IRI
name|libraryID
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
specifier|private
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
decl_stmt|;
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param registryManager      *            the registry manager that should contain the library data. Must not be null.      * @throws OWLOntologyCreationException      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|RegistryManager
name|registryManager
parameter_list|)
throws|throws
name|RegistryContentException
throws|,
name|OWLOntologyCreationException
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|registryManager
argument_list|,
name|checkOntologyManager
argument_list|(
name|registryManager
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param registryManager      *            the registry manager that should contain the library data. Must not be null.      * @param parentSrc      *            the source of the ontology that will import all the ontologies in the registry. If null, a      *            new blank ontology will be used.      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|RegistryManager
name|registryManager
parameter_list|,
name|OntologyInputSource
argument_list|<
name|OWLOntology
argument_list|,
name|OWLOntologyManager
argument_list|>
name|parentSrc
parameter_list|)
throws|throws
name|RegistryContentException
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|registryManager
argument_list|,
name|checkOntologyManager
argument_list|(
name|registryManager
argument_list|)
argument_list|,
name|parentSrc
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param registryManager      *            the registry manager that should contain the library data. Must not be null.      * @param ontologyManager      *            the ontology manager to be used for constructing the import tree. if null, a new one will be      *            used.      * @throws OWLOntologyCreationException      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|RegistryManager
name|registryManager
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|)
throws|throws
name|RegistryContentException
throws|,
name|OWLOntologyCreationException
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|registryManager
argument_list|,
name|ontologyManager
argument_list|,
operator|new
name|RootOntologySource
argument_list|(
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|createOntology
argument_list|(
name|libraryID
comment|/* IRI.create(libraryID.toString().replace("#", "%23")) */
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param registryManager      *            the registry manager that should contain the library data. Must not be null.      * @param ontologyManager      *            the ontology manager to be used for constructing the import tree. if null, a new one will be      *            used.      * @param parentSrc      *            the source of the ontology that will import all the ontologies in the registry. If null, a      *            new blank ontology will be used.      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|RegistryManager
name|registryManager
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|,
name|OntologyInputSource
argument_list|<
name|OWLOntology
argument_list|,
name|OWLOntologyManager
argument_list|>
name|parentSrc
parameter_list|)
throws|throws
name|RegistryContentException
block|{
if|if
condition|(
name|registryManager
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"A null registry manager is not allowed"
argument_list|)
throw|;
name|this
operator|.
name|libraryID
operator|=
name|libraryID
expr_stmt|;
comment|// The ontology that imports the whole network is created in-memory, therefore it has no physical IRI
comment|// unless it is borrowed from the supplied parent.
name|bindPhysicalIri
argument_list|(
name|parentSrc
operator|!=
literal|null
condition|?
name|parentSrc
operator|.
name|getPhysicalIRI
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
name|Library
name|lib
init|=
name|registryManager
operator|.
name|getLibrary
argument_list|(
name|libraryID
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Got library {}, expected {}"
argument_list|,
name|lib
argument_list|,
name|libraryID
argument_list|)
expr_stmt|;
comment|// If the manager is set to
if|if
condition|(
name|lib
operator|!=
literal|null
condition|)
block|{
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|subtrees
init|=
name|lib
operator|.
name|getOntologies
argument_list|(
name|OWLOntology
operator|.
name|class
argument_list|)
decl_stmt|;
name|this
operator|.
name|ontologies
operator|=
name|subtrees
expr_stmt|;
for|for
control|(
name|OWLOntology
name|o
range|:
name|subtrees
control|)
name|log
operator|.
name|debug
argument_list|(
literal|"\tGot ontology {}"
argument_list|,
name|o
argument_list|)
expr_stmt|;
comment|// We always construct a new root now, even if there's just one subtree.
comment|// if (subtrees.size() == 1)
comment|// rootOntology = subtrees.iterator().next();
comment|// else
try|try
block|{
name|OWLOntology
name|parent
decl_stmt|;
if|if
condition|(
name|parentSrc
operator|!=
literal|null
condition|)
name|parent
operator|=
name|OntologyUtils
operator|.
name|buildImportTree
argument_list|(
name|parentSrc
argument_list|,
name|subtrees
argument_list|,
name|ontologyManager
argument_list|)
expr_stmt|;
else|else
name|parent
operator|=
name|OntologyUtils
operator|.
name|buildImportTree
argument_list|(
name|subtrees
argument_list|,
name|ontologyManager
argument_list|)
expr_stmt|;
name|bindRootOntology
argument_list|(
name|parent
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
literal|"Failed to build import tree for library source "
operator|+
name|libraryID
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
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
block|{
return|return
name|ontologies
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"LIBRARY<"
operator|+
name|libraryID
operator|+
literal|">"
return|;
block|}
block|}
end_class

end_unit

