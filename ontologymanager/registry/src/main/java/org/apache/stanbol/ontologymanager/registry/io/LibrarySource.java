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
name|Hashtable
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
name|RegistryManagerImpl
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
name|owl
operator|.
name|OWLOntologyManagerFactory
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
throws|throws
name|RegistryContentException
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
operator|new
name|RegistryManagerImpl
argument_list|(
literal|null
argument_list|,
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param loader      */
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
comment|// /**
comment|// * Creates a new ontology source from a library.
comment|// *
comment|// * @param libraryID
comment|// * @param registryLocation
comment|// */
comment|// public LibrarySource(IRI libraryID, IRI registryLocation) {
comment|// this(libraryID, registryLocation, null);
comment|// }
comment|// /**
comment|// * Creates a new ontology source from a library.
comment|// *
comment|// * @param libraryID
comment|// * the identifier of the ontology library.
comment|// * @param registryLocation
comment|// * @param ontologyManager
comment|// * @param loader
comment|// */
comment|// public LibrarySource(IRI libraryID,
comment|// IRI registryLocation,
comment|// OWLOntologyManager ontologyManager,
comment|// RegistryLoader loader) {
comment|// this(libraryID, registryLocation, ontologyManager, loader, null);
comment|// }
comment|// /**
comment|// * Creates a new ontology source from a library.
comment|// *
comment|// * @param libraryID
comment|// * the identifier of the ontology library.
comment|// * @param registryLocation
comment|// * @param ontologyManager
comment|// * @param loader
comment|// * @param parentSrc
comment|// * the source of the ontology that will import all the ontologies in the registry. If null, a
comment|// * new blank ontology will be used.
comment|// */
comment|// public LibrarySource(IRI libraryID,
comment|// IRI registryLocation,
comment|// OWLOntologyManager ontologyManager,
comment|// RegistryLoader loader,
comment|// OntologyInputSource parentSrc) {
comment|// this.libraryID = libraryID;
comment|//
comment|// // The ontology that imports the whole network is created in-memory, therefore it has no physical IRI.
comment|// bindPhysicalIri(null);
comment|//
comment|// Set<OWLOntology> subtrees = new HashSet<OWLOntology>();
comment|// Registry reg = loader.loadLibrary(registryLocation, libraryID);
comment|// for (RegistryItem ri : reg.getChildren()) {
comment|// if (ri.isLibrary()) try {
comment|// Set<OWLOntology> adds = loader.gatherOntologies(ri, ontologyManager, true);
comment|// subtrees.addAll(adds);
comment|// } catch (OWLOntologyAlreadyExistsException e) {
comment|// // Chettefreca
comment|// continue;
comment|// } catch (OWLOntologyCreationException e) {
comment|// log.warn("Failed to load ontology library " + ri.getName() + ". Skipping.", e);
comment|// // If we can't load this library at all, scrap it.
comment|// // TODO : not entirely convinced of this step.
comment|// continue;
comment|// }
comment|// }
comment|//
comment|// // We always construct a new root now, even if there's just one subtree.
comment|//
comment|// // Set<OWLOntology> subtrees = mgr.getOntologies();
comment|// // if (subtrees.size() == 1)
comment|// // rootOntology = subtrees.iterator().next();
comment|// // else
comment|// try {
comment|// if (parentSrc != null) bindRootOntology(OntologyUtils.buildImportTree(parentSrc, subtrees,
comment|// ontologyManager));
comment|// else bindRootOntology(OntologyUtils.buildImportTree(subtrees, ontologyManager));
comment|// } catch (OWLOntologyCreationException e) {
comment|// log.error("Failed to build import tree for registry source " + registryLocation, e);
comment|// }
comment|// }
comment|// /**
comment|// * Creates a new ontology source from a library.
comment|// *
comment|// * @param libraryID
comment|// * the identifier of the ontology library.
comment|// * @param registryLocation
comment|// * @param loader
comment|// */
comment|// public LibrarySource(IRI libraryID, IRI registryLocation, RegistryLoader loader) {
comment|// this(libraryID, registryLocation, OWLManager.createOWLOntologyManager(), loader);
comment|// }
comment|// /**
comment|// * Creates a new ontology source from a library.
comment|// *
comment|// * @param libraryID
comment|// * the identifier of the ontology library.
comment|// * @param registryLocation
comment|// * @param loader
comment|// * @param parentSrc
comment|// * the source of the ontology that will import all the ontologies in the registry. If null, a
comment|// * new blank ontology will be used.
comment|// */
comment|// public LibrarySource(IRI libraryID,
comment|// IRI registryLocation,
comment|// RegistryLoader loader,
comment|// OntologyInputSource parentSrc) {
comment|// this(libraryID, registryLocation, OWLManager.createOWLOntologyManager(), loader, parentSrc);
comment|// }
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param loader      * @param parentSrc      *            the source of the ontology that will import all the ontologies in the registry. If null, a      *            new blank ontology will be used.      */
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
comment|/**      * Creates a new ontology source from a library. The physical registry location is assumed to be the      * parent URL of<code>libraryID</code>.<br/>      *<br/>      * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry      * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.      *       * @param libraryID      *            the identifier of the ontology library.      * @param ontologyManager      * @param loader      */
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
block|{
name|this
argument_list|(
name|libraryID
argument_list|,
name|registryManager
argument_list|,
name|ontologyManager
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|//
comment|// /**
comment|// * Creates a new ontology source from a library. The physical registry location is assumed to be the
comment|// * parent URL of<code>libraryID</code>.<br/>
comment|// *<br/>
comment|// * Example : if<code>libraryID</code> is<tt>http://foo.bar.baz/registry#library</tt>, the registry
comment|// * location will be<tt>http://foo.bar.baz/registry</tt>. Same goes for slash-URIs.
comment|// *
comment|// * @param libraryID
comment|// * the identifier of the ontology library.
comment|// * @param ontologyManager
comment|// * @param loader
comment|// * @param parentSrc
comment|// * the source of the ontology that will import all the ontologies in the registry. If null, a
comment|// * new blank ontology will be used.
comment|// */
comment|// public LibrarySource(IRI libraryID,
comment|// OWLOntologyManager ontologyManager,
comment|// RegistryLoader loader,
comment|// OntologyInputSource parentSrc) {
comment|// this(libraryID, URIUtils.upOne(libraryID), ontologyManager, loader, parentSrc);
comment|// }
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
argument_list|()
decl_stmt|;
comment|// We always construct a new root now, even if there's just one subtree.
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

