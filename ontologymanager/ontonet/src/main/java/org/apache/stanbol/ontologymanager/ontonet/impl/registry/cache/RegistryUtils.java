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
name|registry
operator|.
name|cache
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
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
name|Map
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
name|java
operator|.
name|util
operator|.
name|TreeMap
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
name|ontonet
operator|.
name|api
operator|.
name|registry
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
name|ontonet
operator|.
name|api
operator|.
name|registry
operator|.
name|models
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
name|api
operator|.
name|registry
operator|.
name|models
operator|.
name|RegistryItem
operator|.
name|Type
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
name|ontonet
operator|.
name|impl
operator|.
name|registry
operator|.
name|RegistryItemFactoryImpl
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
name|xd
operator|.
name|vocabulary
operator|.
name|CODOVocabulary
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
name|OWLClass
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
name|OWLClassExpression
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
name|OWLDataFactory
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
name|OWLIndividual
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
name|OWLNamedIndividual
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
name|OWLObjectProperty
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

begin_class
specifier|public
class|class
name|RegistryUtils
block|{
specifier|private
specifier|static
specifier|final
name|OWLClass
name|cRegistryLibrary
decl_stmt|,
name|cOntology
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLObjectProperty
name|hasPart
decl_stmt|,
name|hasOntology
decl_stmt|,
name|isPartOf
decl_stmt|,
name|isOntologyOf
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RegistryUtils
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Map
argument_list|<
name|IRI
argument_list|,
name|RegistryItem
argument_list|>
name|population
init|=
operator|new
name|TreeMap
argument_list|<
name|IRI
argument_list|,
name|RegistryItem
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|RegistryItemFactory
name|riFactory
decl_stmt|;
static|static
block|{
name|riFactory
operator|=
operator|new
name|RegistryItemFactoryImpl
argument_list|()
expr_stmt|;
name|OWLDataFactory
name|factory
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|cOntology
operator|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|CODK_Ontology
argument_list|)
argument_list|)
expr_stmt|;
name|cRegistryLibrary
operator|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|CODD_OntologyLibrary
argument_list|)
argument_list|)
expr_stmt|;
name|isPartOf
operator|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|PARTOF_IsPartOf
argument_list|)
argument_list|)
expr_stmt|;
name|isOntologyOf
operator|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|ODPM_IsOntologyOf
argument_list|)
argument_list|)
expr_stmt|;
name|hasPart
operator|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|PARTOF_HasPart
argument_list|)
argument_list|)
expr_stmt|;
name|hasOntology
operator|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|ODPM_HasOntology
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Utility method to recurse into registry items.      *       * TODO: move this to main?      *       * @param item      * @param ontologyId      * @return      */
specifier|public
specifier|static
name|boolean
name|containsOntologyRecursive
parameter_list|(
name|RegistryItem
name|item
parameter_list|,
name|IRI
name|ontologyId
parameter_list|)
block|{
name|boolean
name|result
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|item
operator|instanceof
name|RegistryOntology
condition|)
block|{
comment|// An Ontology MUST have a non-null URI.
try|try
block|{
name|IRI
name|iri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|item
operator|.
name|getURL
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator||=
name|iri
operator|.
name|equals
argument_list|(
name|ontologyId
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|item
operator|instanceof
name|Library
operator|||
name|item
operator|instanceof
name|Registry
condition|)
comment|// Inspect children
for|for
control|(
name|RegistryItem
name|child
range|:
operator|(
operator|(
name|RegistryItem
operator|)
name|item
operator|)
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|result
operator||=
name|containsOntologyRecursive
argument_list|(
name|child
argument_list|,
name|ontologyId
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
condition|)
break|break;
block|}
return|return
name|result
return|;
block|}
comment|/**      * Simulates a classifier.      *       * @param ind      * @param o      * @return      */
specifier|public
specifier|static
name|Type
name|getType
parameter_list|(
name|OWLIndividual
name|ind
parameter_list|,
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
parameter_list|)
block|{
comment|// TODO also use property values
name|Set
argument_list|<
name|OWLClassExpression
argument_list|>
name|types
init|=
name|ind
operator|.
name|getTypes
argument_list|(
name|ontologies
argument_list|)
decl_stmt|;
if|if
condition|(
name|types
operator|.
name|contains
argument_list|(
name|cOntology
argument_list|)
operator|&&
operator|!
name|types
operator|.
name|contains
argument_list|(
name|cRegistryLibrary
argument_list|)
condition|)
return|return
name|Type
operator|.
name|ONTOLOGY
return|;
if|if
condition|(
operator|!
name|types
operator|.
name|contains
argument_list|(
name|cOntology
argument_list|)
operator|&&
name|types
operator|.
name|contains
argument_list|(
name|cRegistryLibrary
argument_list|)
condition|)
return|return
name|Type
operator|.
name|LIBRARY
return|;
return|return
literal|null
return|;
block|}
comment|//    public static Library populateLibrary(OWLNamedIndividual ind, Set<OWLOntology> registries) throws RegistryContentException {
comment|//        IRI id = ind.getIRI();
comment|//        RegistryItem lib = null;
comment|//        if (population.containsKey(id)) {
comment|//            // We are not allowing multityping either.
comment|//            lib = population.get(id);
comment|//            if (!(lib instanceof Library)) throw new RegistryContentException(
comment|//                    "Inconsistent multityping: for item " + id + " : {" + Library.class + ", "
comment|//                            + lib.getClass() + "}");
comment|//        } else {
comment|//            lib = riFactory.createLibrary(ind.asOWLNamedIndividual());
comment|//            try {
comment|//                population.put(IRI.create(lib.getURL()), lib);
comment|//            } catch (URISyntaxException e) {
comment|//                log.error("Invalid identifier for library item " + lib, e);
comment|//                return null;
comment|//            }
comment|//        }
comment|//        // EXIT nodes.
comment|//        Set<OWLIndividual> ronts = new HashSet<OWLIndividual>();
comment|//        for (OWLOntology o : registries)
comment|//            ronts.addAll(ind.getObjectPropertyValues(hasOntology, o));
comment|//        for (OWLIndividual iont : ronts) {
comment|//            if (iont.isNamed())
comment|//                lib.addChild(populateOntology(iont.asOWLNamedIndividual(), registries));
comment|//        }
comment|//        return (Library) lib;
comment|//    }
comment|//
comment|//    public static RegistryOntology populateOntology(OWLNamedIndividual ind, Set<OWLOntology> registries) throws RegistryContentException {
comment|//        IRI id = ind.getIRI();
comment|//        RegistryItem ront = null;
comment|//        if (population.containsKey(id)) {
comment|//            // We are not allowing multityping either.
comment|//            ront = population.get(id);
comment|//            if (!(ront instanceof RegistryOntology)) throw new RegistryContentException(
comment|//                    "Inconsistent multityping: for item " + id + " : {" + RegistryOntology.class + ", "
comment|//                            + ront.getClass() + "}");
comment|//        } else {
comment|//            ront = riFactory.createRegistryOntology(ind);
comment|//            try {
comment|//                population.put(IRI.create(ront.getURL()), ront);
comment|//            } catch (URISyntaxException e) {
comment|//                log.error("Invalid identifier for library item " + ront, e);
comment|//                return null;
comment|//            }
comment|//        }
comment|//        // EXIT nodes.
comment|//        Set<OWLIndividual> libs = new HashSet<OWLIndividual>();
comment|//        for (OWLOntology o : registries)
comment|//            libs.addAll(ind.getObjectPropertyValues(isOntologyOf, o));
comment|//        for (OWLIndividual ilib : libs) {
comment|//            if (ilib.isNamed())
comment|//                ront.addContainer(populateLibrary(ilib.asOWLNamedIndividual(), registries));
comment|//        }
comment|//        return (RegistryOntology) ront;
comment|//    }
comment|//
comment|//    public static Registry populateRegistry(OWLOntology registry) throws RegistryContentException {
comment|//
comment|//        Registry reg = riFactory.createRegistry(registry);
comment|//        Set<OWLOntology> closure = registry.getOWLOntologyManager().getImportsClosure(registry);
comment|//
comment|//        // Just scan all individuals. Recurse in case the registry imports more registries.
comment|//        for (OWLIndividual ind : registry.getIndividualsInSignature(true)) {
comment|//            // We do not allow anonymous registry items.
comment|//            if (ind.isAnonymous()) continue;
comment|//            RegistryItem item = null;
comment|//            // IRI id = ind.asOWLNamedIndividual().getIRI();
comment|//            Type t = getType(ind, closure);
comment|//            if (t==null) {
comment|//                log.warn("Undetermined type for registry ontology individual {}",ind);
comment|//                continue;
comment|//            }
comment|//            switch (getType(ind, closure)) {
comment|//                case LIBRARY:
comment|//                    // // Create the library and attach to parent and children
comment|//                    item = populateLibrary(ind.asOWLNamedIndividual(), closure);
comment|//                    reg.addChild(item);
comment|//                    break;
comment|//                case ONTOLOGY:
comment|//                    // Create the ontology and attach to parent
comment|//                    item = populateOntology(ind.asOWLNamedIndividual(), closure);
comment|//                    // We don't know where to attach it to in this method.
comment|//                    break;
comment|//                default:
comment|//                    break;
comment|//            }
comment|//        }
comment|//
comment|//        population = new TreeMap<IRI,RegistryItem>();
comment|//        return reg;
comment|//    }
block|}
end_class

end_unit

