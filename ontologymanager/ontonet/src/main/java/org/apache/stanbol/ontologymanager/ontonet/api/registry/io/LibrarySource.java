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
name|ontonet
operator|.
name|api
operator|.
name|io
operator|.
name|AbstractOntologyInputSource
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
name|registry
operator|.
name|RegistryLoader
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
name|registry
operator|.
name|models
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
name|ontonet
operator|.
name|api
operator|.
name|registry
operator|.
name|models
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
name|ontonet
operator|.
name|impl
operator|.
name|registry
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
name|owl
operator|.
name|util
operator|.
name|URIUtils
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
name|AbstractOntologyInputSource
block|{
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
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|)
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|URIUtils
operator|.
name|upOne
argument_list|(
name|libraryID
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library.      *       * @param libraryID      * @param registryLocation      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|IRI
name|registryLocation
parameter_list|)
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|registryLocation
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library.      *       * @param libraryID      *            the identifier of the ontology library.      * @param registryLocation      * @param ontologyManager      * @param loader      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|IRI
name|registryLocation
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|,
name|RegistryLoader
name|loader
parameter_list|)
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|registryLocation
argument_list|,
name|ontologyManager
argument_list|,
name|loader
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library.      *       * @param libraryID      *            the identifier of the ontology library.      * @param registryLocation      * @param ontologyManager      * @param loader      * @param parentSrc      *            the source of the ontology that will import all the ontologies in the registry. If null, a      *            new blank ontology will be used.      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|IRI
name|registryLocation
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|,
name|RegistryLoader
name|loader
parameter_list|,
name|OntologyInputSource
name|parentSrc
parameter_list|)
block|{
name|this
operator|.
name|libraryID
operator|=
name|libraryID
expr_stmt|;
comment|// The ontology that imports the whole network is created in-memory, therefore it has no physical IRI.
name|bindPhysicalIri
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|subtrees
init|=
operator|new
name|HashSet
argument_list|<
name|OWLOntology
argument_list|>
argument_list|()
decl_stmt|;
name|Registry
name|reg
init|=
name|loader
operator|.
name|loadLibrary
argument_list|(
name|registryLocation
argument_list|,
name|libraryID
argument_list|)
decl_stmt|;
for|for
control|(
name|RegistryItem
name|ri
range|:
name|reg
operator|.
name|getChildren
argument_list|()
control|)
block|{
if|if
condition|(
name|ri
operator|.
name|isLibrary
argument_list|()
condition|)
try|try
block|{
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|adds
init|=
name|loader
operator|.
name|gatherOntologies
argument_list|(
name|ri
argument_list|,
name|ontologyManager
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|subtrees
operator|.
name|addAll
argument_list|(
name|adds
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyAlreadyExistsException
name|e
parameter_list|)
block|{
comment|// Chettefreca
continue|continue;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Failed to load ontology library "
operator|+
name|ri
operator|.
name|getName
argument_list|()
operator|+
literal|". Skipping."
argument_list|,
name|e
argument_list|)
expr_stmt|;
comment|// If we can't load this library at all, scrap it.
comment|// TODO : not entirely convinced of this step.
continue|continue;
block|}
block|}
comment|// We always construct a new root now, even if there's just one subtree.
comment|// Set<OWLOntology> subtrees = mgr.getOntologies();
comment|// if (subtrees.size() == 1)
comment|// rootOntology = subtrees.iterator().next();
comment|// else
try|try
block|{
if|if
condition|(
name|parentSrc
operator|!=
literal|null
condition|)
name|bindRootOntology
argument_list|(
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
argument_list|)
expr_stmt|;
else|else
name|bindRootOntology
argument_list|(
name|OntologyUtils
operator|.
name|buildImportTree
argument_list|(
name|subtrees
argument_list|,
name|ontologyManager
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
literal|"Failed to build import tree for registry source "
operator|+
name|registryLocation
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Creates a new ontology source from a library.      *       * @param libraryID      *            the identifier of the ontology library.      * @param registryLocation      * @param loader      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|IRI
name|registryLocation
parameter_list|,
name|RegistryLoader
name|loader
parameter_list|)
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|registryLocation
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|,
name|loader
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library.      *       * @param libraryID      *            the identifier of the ontology library.      * @param registryLocation      * @param loader      * @param parentSrc      *            the source of the ontology that will import all the ontologies in the registry. If null, a      *            new blank ontology will be used.      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|IRI
name|registryLocation
parameter_list|,
name|RegistryLoader
name|loader
parameter_list|,
name|OntologyInputSource
name|parentSrc
parameter_list|)
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|registryLocation
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|,
name|loader
argument_list|,
name|parentSrc
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param ontologyManager      * @param loader      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|,
name|RegistryLoader
name|loader
parameter_list|)
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|URIUtils
operator|.
name|upOne
argument_list|(
name|libraryID
argument_list|)
argument_list|,
name|ontologyManager
argument_list|,
name|loader
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param ontologyManager      * @param loader      * @param parentSrc      *            the source of the ontology that will import all the ontologies in the registry. If null, a      *            new blank ontology will be used.      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|,
name|RegistryLoader
name|loader
parameter_list|,
name|OntologyInputSource
name|parentSrc
parameter_list|)
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|URIUtils
operator|.
name|upOne
argument_list|(
name|libraryID
argument_list|)
argument_list|,
name|ontologyManager
argument_list|,
name|loader
argument_list|,
name|parentSrc
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param loader      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|RegistryLoader
name|loader
parameter_list|)
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|URIUtils
operator|.
name|upOne
argument_list|(
name|libraryID
argument_list|)
argument_list|,
name|loader
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param loader      * @param parentSrc      *            the source of the ontology that will import all the ontologies in the registry. If null, a      *            new blank ontology will be used.      */
specifier|public
name|LibrarySource
parameter_list|(
name|IRI
name|libraryID
parameter_list|,
name|RegistryLoader
name|loader
parameter_list|,
name|OntologyInputSource
name|parentSrc
parameter_list|)
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|URIUtils
operator|.
name|upOne
argument_list|(
name|libraryID
argument_list|)
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|,
name|loader
argument_list|,
name|parentSrc
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

