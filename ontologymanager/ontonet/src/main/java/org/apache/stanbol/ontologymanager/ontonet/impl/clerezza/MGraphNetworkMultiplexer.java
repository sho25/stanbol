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
name|clerezza
package|;
end_package

begin_import
import|import static
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
name|Vocabulary
operator|.
name|APPENDED_TO
import|;
end_import

begin_import
import|import static
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
name|Vocabulary
operator|.
name|ENTRY
import|;
end_import

begin_import
import|import static
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
name|Vocabulary
operator|.
name|HAS_APPENDED
import|;
end_import

begin_import
import|import static
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
name|Vocabulary
operator|.
name|HAS_ONTOLOGY_IRI
import|;
end_import

begin_import
import|import static
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
name|Vocabulary
operator|.
name|HAS_VERSION_IRI
import|;
end_import

begin_import
import|import static
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
name|Vocabulary
operator|.
name|IS_MANAGED_BY
import|;
end_import

begin_import
import|import static
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
name|Vocabulary
operator|.
name|MANAGES
import|;
end_import

begin_import
import|import static
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
name|Vocabulary
operator|.
name|SESSION
import|;
end_import

begin_import
import|import static
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
name|Vocabulary
operator|.
name|SIZE_IN_TRIPLES
import|;
end_import

begin_import
import|import static
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
name|Vocabulary
operator|.
name|_NS_STANBOL_INTERNAL
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
name|Iterator
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
name|Literal
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
name|MGraph
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
name|Resource
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
name|Triple
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
name|impl
operator|.
name|TripleImpl
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
name|ontologies
operator|.
name|RDF
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
name|collector
operator|.
name|OntologyCollector
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
name|OntologyNetworkMultiplexer
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
name|scope
operator|.
name|OntologyScope
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
name|scope
operator|.
name|OntologySpace
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
name|session
operator|.
name|Session
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
name|session
operator|.
name|SessionEvent
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
name|OWLOntologyID
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
comment|/**  * TODO make this object update its sizes as a graph changes.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|MGraphNetworkMultiplexer
implements|implements
name|OntologyNetworkMultiplexer
block|{
specifier|private
class|class
name|InvalidMetaGraphStateException
extends|extends
name|RuntimeException
block|{
comment|/**          *           */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3915817349833358738L
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|InvalidMetaGraphStateException
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
name|InvalidMetaGraphStateException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
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
name|MGraph
name|meta
decl_stmt|;
specifier|public
name|MGraphNetworkMultiplexer
parameter_list|(
name|MGraph
name|metaGraph
parameter_list|)
block|{
name|this
operator|.
name|meta
operator|=
name|metaGraph
expr_stmt|;
block|}
comment|/**      * Creates an {@link OWLOntologyID} object by combining the ontologyIRI and the versionIRI, where      * applicable, of the stored graph.      *       * @param resource      *            the ontology      * @return      */
specifier|protected
name|OWLOntologyID
name|buildPublicKey
parameter_list|(
specifier|final
name|UriRef
name|resource
parameter_list|)
block|{
comment|// TODO desanitize?
name|IRI
name|oiri
init|=
literal|null
decl_stmt|,
name|viri
init|=
literal|null
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|meta
operator|.
name|filter
argument_list|(
name|resource
argument_list|,
name|HAS_ONTOLOGY_IRI
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|obj
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|obj
operator|instanceof
name|UriRef
condition|)
name|oiri
operator|=
name|IRI
operator|.
name|create
argument_list|(
operator|(
operator|(
name|UriRef
operator|)
name|obj
operator|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Anonymous ontology? Decode the resource itself (which is not null)
return|return
name|OntologyUtils
operator|.
name|decode
argument_list|(
name|resource
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
return|;
block|}
name|it
operator|=
name|meta
operator|.
name|filter
argument_list|(
name|resource
argument_list|,
name|HAS_VERSION_IRI
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|obj
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|obj
operator|instanceof
name|UriRef
condition|)
name|viri
operator|=
name|IRI
operator|.
name|create
argument_list|(
operator|(
operator|(
name|UriRef
operator|)
name|obj
operator|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|viri
operator|==
literal|null
condition|)
return|return
operator|new
name|OWLOntologyID
argument_list|(
name|oiri
argument_list|)
return|;
else|else
return|return
operator|new
name|OWLOntologyID
argument_list|(
name|oiri
argument_list|,
name|viri
argument_list|)
return|;
block|}
comment|/**      * Creates an {@link UriRef} out of an {@link OWLOntologyID}, so it can be used as an identifier. This      * does NOT necessarily correspond to the UriRef that identifies the stored graph. In order to obtain      * that, check the objects of any MAPS_TO_GRAPH assertions.      *       * @param publicKey      * @return      */
specifier|protected
name|UriRef
name|buildResource
parameter_list|(
specifier|final
name|OWLOntologyID
name|publicKey
parameter_list|)
block|{
if|if
condition|(
name|publicKey
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot build a UriRef resource on a null public key!"
argument_list|)
throw|;
comment|// The UriRef is of the form ontologyIRI[:::versionIRI] (TODO use something less conventional?)
comment|// XXX should versionIRI also include the version IRI set by owners? Currently not
comment|// Remember not to sanitize logical identifiers.
name|IRI
name|ontologyIri
init|=
name|publicKey
operator|.
name|getOntologyIRI
argument_list|()
decl_stmt|,
name|versionIri
init|=
name|publicKey
operator|.
name|getVersionIRI
argument_list|()
decl_stmt|;
if|if
condition|(
name|ontologyIri
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot build a UriRef resource on an anonymous public key!"
argument_list|)
throw|;
name|log
operator|.
name|debug
argument_list|(
literal|"Searching for a meta graph entry for public key:"
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" -- {}"
argument_list|,
name|publicKey
argument_list|)
expr_stmt|;
name|UriRef
name|match
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|meta
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|HAS_ONTOLOGY_IRI
argument_list|,
operator|new
name|UriRef
argument_list|(
name|ontologyIri
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Resource
name|subj
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" -- Ontology IRI match found. Scanning"
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" -- Resource : {}"
argument_list|,
name|subj
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
operator|(
name|subj
operator|instanceof
name|UriRef
operator|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ---- (uncomparable: skipping...)"
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|versionIri
operator|!=
literal|null
condition|)
block|{
comment|// Must find matching versionIRI
if|if
condition|(
name|meta
operator|.
name|contains
argument_list|(
operator|new
name|TripleImpl
argument_list|(
operator|(
name|UriRef
operator|)
name|subj
argument_list|,
name|HAS_VERSION_IRI
argument_list|,
operator|new
name|UriRef
argument_list|(
name|versionIri
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ---- Version IRI match!"
argument_list|)
expr_stmt|;
name|match
operator|=
operator|(
name|UriRef
operator|)
name|subj
expr_stmt|;
break|break;
comment|// Found
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ---- Expected version IRI match not found."
argument_list|)
expr_stmt|;
continue|continue;
comment|// There could be another with the right versionIRI.
block|}
block|}
else|else
block|{
comment|// Must find unversioned resource
if|if
condition|(
name|meta
operator|.
name|filter
argument_list|(
operator|(
name|UriRef
operator|)
name|subj
argument_list|,
name|HAS_VERSION_IRI
argument_list|,
literal|null
argument_list|)
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ---- Unexpected version IRI found. Skipping."
argument_list|)
expr_stmt|;
continue|continue;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ---- Unversioned match!"
argument_list|)
expr_stmt|;
name|match
operator|=
operator|(
name|UriRef
operator|)
name|subj
expr_stmt|;
break|break;
comment|// Found
block|}
block|}
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Matching UriRef in graph : {}"
argument_list|,
name|match
argument_list|)
expr_stmt|;
if|if
condition|(
name|match
operator|==
literal|null
condition|)
return|return
operator|new
name|UriRef
argument_list|(
name|OntologyUtils
operator|.
name|encode
argument_list|(
name|publicKey
argument_list|)
argument_list|)
return|;
else|else
return|return
name|match
return|;
block|}
specifier|private
name|UriRef
name|getIRIforScope
parameter_list|(
name|String
name|scopeId
parameter_list|)
block|{
comment|// Use the Stanbol-internal namespace, so that the whole configuration can be ported.
return|return
operator|new
name|UriRef
argument_list|(
name|_NS_STANBOL_INTERNAL
operator|+
name|OntologyScope
operator|.
name|shortName
operator|+
literal|"/"
operator|+
name|scopeId
argument_list|)
return|;
block|}
specifier|private
name|UriRef
name|getIRIforSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
comment|// Use the Stanbol-internal namespace, so that the whole configuration can be ported.
return|return
operator|new
name|UriRef
argument_list|(
name|_NS_STANBOL_INTERNAL
operator|+
name|Session
operator|.
name|shortName
operator|+
literal|"/"
operator|+
name|session
operator|.
name|getID
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntologyID
name|getPublicKey
parameter_list|(
name|String
name|stringForm
parameter_list|)
block|{
if|if
condition|(
name|stringForm
operator|==
literal|null
operator|||
name|stringForm
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"String form must not be null or empty."
argument_list|)
throw|;
return|return
name|buildPublicKey
argument_list|(
operator|new
name|UriRef
argument_list|(
name|stringForm
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntologyID
argument_list|>
name|getPublicKeys
parameter_list|()
block|{
name|Set
argument_list|<
name|OWLOntologyID
argument_list|>
name|result
init|=
operator|new
name|HashSet
argument_list|<
name|OWLOntologyID
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|meta
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
name|ENTRY
argument_list|)
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|obj
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
if|if
condition|(
name|obj
operator|instanceof
name|UriRef
condition|)
name|result
operator|.
name|add
argument_list|(
name|buildPublicKey
argument_list|(
operator|(
name|UriRef
operator|)
name|obj
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getSize
parameter_list|(
name|OWLOntologyID
name|publicKey
parameter_list|)
block|{
name|UriRef
name|subj
init|=
name|buildResource
argument_list|(
name|publicKey
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|meta
operator|.
name|filter
argument_list|(
name|subj
argument_list|,
name|SIZE_IN_TRIPLES
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|obj
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|obj
operator|instanceof
name|Literal
condition|)
block|{
name|String
name|s
init|=
operator|(
operator|(
name|Literal
operator|)
name|obj
operator|)
operator|.
name|getLexicalForm
argument_list|()
decl_stmt|;
try|try
block|{
return|return
name|Integer
operator|.
name|parseInt
argument_list|(
name|s
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Not a valid integer value {} for size of {}"
argument_list|,
name|s
argument_list|,
name|publicKey
argument_list|)
expr_stmt|;
return|return
operator|-
literal|1
return|;
block|}
block|}
block|}
return|return
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onOntologyAdded
parameter_list|(
name|OntologyCollector
name|collector
parameter_list|,
name|OWLOntologyID
name|addedOntology
parameter_list|)
block|{
comment|// When the ontology provider hears an ontology has been added to a collector, it has to register this
comment|// into the metadata graph.
comment|// log.info("Heard addition of ontology {} to collector {}", addedOntology, collector.getID());
comment|// log.info("This ontology is stored as {}", getKey(addedOntology));
name|String
name|colltype
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|collector
operator|instanceof
name|OntologyScope
condition|)
name|colltype
operator|=
name|OntologyScope
operator|.
name|shortName
operator|+
literal|"/"
expr_stmt|;
comment|// Cannot be
elseif|else
if|if
condition|(
name|collector
operator|instanceof
name|OntologySpace
condition|)
name|colltype
operator|=
name|OntologySpace
operator|.
name|shortName
operator|+
literal|"/"
expr_stmt|;
elseif|else
if|if
condition|(
name|collector
operator|instanceof
name|Session
condition|)
name|colltype
operator|=
name|Session
operator|.
name|shortName
operator|+
literal|"/"
expr_stmt|;
name|UriRef
name|c
init|=
operator|new
name|UriRef
argument_list|(
name|_NS_STANBOL_INTERNAL
operator|+
name|colltype
operator|+
name|collector
operator|.
name|getID
argument_list|()
argument_list|)
decl_stmt|;
name|UriRef
name|u
init|=
comment|// new UriRef(prefix + "::" + keymap.buildResource(addedOntology).getUnicodeString());
comment|// keymap.getMapping(addedOntology);
name|buildResource
argument_list|(
name|addedOntology
argument_list|)
decl_stmt|;
comment|// TODO OntologyProvider should not be aware of scopes, spaces or sessions. Move elsewhere.
name|boolean
name|hasValues
init|=
literal|false
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Ontology {}"
argument_list|,
name|addedOntology
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"-- is already managed by the following collectors :"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|meta
operator|.
name|filter
argument_list|(
name|u
argument_list|,
name|IS_MANAGED_BY
argument_list|,
literal|null
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|hasValues
operator|=
literal|true
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"-- {}"
argument_list|,
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|meta
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|MANAGES
argument_list|,
name|u
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|hasValues
operator|=
literal|true
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"-- {} (inverse)"
argument_list|,
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|hasValues
condition|)
name|log
operator|.
name|debug
argument_list|(
literal|"--<none>"
argument_list|)
expr_stmt|;
comment|// Add both inverse triples. This graph has to be traversed efficiently, no need for reasoners.
name|UriRef
name|predicate1
init|=
literal|null
decl_stmt|,
name|predicate2
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|collector
operator|instanceof
name|OntologySpace
condition|)
block|{
name|predicate1
operator|=
name|MANAGES
expr_stmt|;
name|predicate2
operator|=
name|IS_MANAGED_BY
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|collector
operator|instanceof
name|Session
condition|)
block|{
comment|// TODO implement model for sessions.
name|predicate1
operator|=
name|MANAGES
expr_stmt|;
name|predicate2
operator|=
name|IS_MANAGED_BY
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unrecognized ontology collector type {} for \"{}\". Aborting."
argument_list|,
name|collector
operator|.
name|getClass
argument_list|()
argument_list|,
name|collector
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|u
operator|!=
literal|null
condition|)
synchronized|synchronized
init|(
name|meta
init|)
block|{
name|Triple
name|t
decl_stmt|;
if|if
condition|(
name|predicate1
operator|!=
literal|null
condition|)
block|{
name|t
operator|=
operator|new
name|TripleImpl
argument_list|(
name|c
argument_list|,
name|predicate1
argument_list|,
name|u
argument_list|)
expr_stmt|;
name|boolean
name|b
init|=
name|meta
operator|.
name|add
argument_list|(
name|t
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
operator|(
name|b
condition|?
literal|"Successful"
else|:
literal|"Redundant"
operator|)
operator|+
literal|" addition of meta triple"
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"-- {} "
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|predicate2
operator|!=
literal|null
condition|)
block|{
name|t
operator|=
operator|new
name|TripleImpl
argument_list|(
name|u
argument_list|,
name|predicate2
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|boolean
name|b
init|=
name|meta
operator|.
name|add
argument_list|(
name|t
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
operator|(
name|b
condition|?
literal|"Successful"
else|:
literal|"Redundant"
operator|)
operator|+
literal|" addition of meta triple"
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"-- {} "
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|onOntologyRemoved
parameter_list|(
name|OntologyCollector
name|collector
parameter_list|,
name|OWLOntologyID
name|removedOntology
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Heard removal of ontology {} from collector {}"
argument_list|,
name|removedOntology
argument_list|,
name|collector
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|colltype
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|collector
operator|instanceof
name|OntologyScope
condition|)
name|colltype
operator|=
name|OntologyScope
operator|.
name|shortName
operator|+
literal|"/"
expr_stmt|;
comment|// Cannot be
elseif|else
if|if
condition|(
name|collector
operator|instanceof
name|OntologySpace
condition|)
name|colltype
operator|=
name|OntologySpace
operator|.
name|shortName
operator|+
literal|"/"
expr_stmt|;
elseif|else
if|if
condition|(
name|collector
operator|instanceof
name|Session
condition|)
name|colltype
operator|=
name|Session
operator|.
name|shortName
operator|+
literal|"/"
expr_stmt|;
name|UriRef
name|c
init|=
operator|new
name|UriRef
argument_list|(
name|_NS_STANBOL_INTERNAL
operator|+
name|colltype
operator|+
name|collector
operator|.
name|getID
argument_list|()
argument_list|)
decl_stmt|;
name|UriRef
name|u
init|=
comment|// new UriRef(prefix + "::" + keymap.buildResource(removedOntology).getUnicodeString());
comment|// keymap.getMapping(removedOntology);
name|buildResource
argument_list|(
name|removedOntology
argument_list|)
decl_stmt|;
comment|// XXX condense the following code
name|boolean
name|badState
init|=
literal|true
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Checking ({},{}) pattern"
argument_list|,
name|c
argument_list|,
name|u
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|meta
operator|.
name|filter
argument_list|(
name|c
argument_list|,
literal|null
argument_list|,
name|u
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|UriRef
name|property
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getPredicate
argument_list|()
decl_stmt|;
if|if
condition|(
name|collector
operator|instanceof
name|OntologySpace
operator|||
name|collector
operator|instanceof
name|Session
condition|)
block|{
if|if
condition|(
name|property
operator|.
name|equals
argument_list|(
name|MANAGES
argument_list|)
condition|)
name|badState
operator|=
literal|false
expr_stmt|;
block|}
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Checking ({},{}) pattern"
argument_list|,
name|u
argument_list|,
name|c
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|meta
operator|.
name|filter
argument_list|(
name|u
argument_list|,
literal|null
argument_list|,
name|c
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|UriRef
name|property
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getPredicate
argument_list|()
decl_stmt|;
if|if
condition|(
name|collector
operator|instanceof
name|OntologySpace
operator|||
name|collector
operator|instanceof
name|Session
condition|)
block|{
if|if
condition|(
name|property
operator|.
name|equals
argument_list|(
name|IS_MANAGED_BY
argument_list|)
condition|)
name|badState
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|if
condition|(
name|badState
condition|)
throw|throw
operator|new
name|InvalidMetaGraphStateException
argument_list|(
literal|"No relationship found for ontology-collector pair {"
operator|+
name|u
operator|+
literal|" , "
operator|+
name|c
operator|+
literal|"}"
argument_list|)
throw|;
synchronized|synchronized
init|(
name|meta
init|)
block|{
if|if
condition|(
name|collector
operator|instanceof
name|OntologySpace
condition|)
block|{
name|meta
operator|.
name|remove
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|c
argument_list|,
name|MANAGES
argument_list|,
name|u
argument_list|)
argument_list|)
expr_stmt|;
name|meta
operator|.
name|remove
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|u
argument_list|,
name|IS_MANAGED_BY
argument_list|,
name|c
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|scopeAppended
parameter_list|(
name|Session
name|session
parameter_list|,
name|String
name|scopeId
parameter_list|)
block|{
specifier|final
name|UriRef
name|sessionur
init|=
name|getIRIforSession
argument_list|(
name|session
argument_list|)
decl_stmt|,
name|scopeur
init|=
name|getIRIforScope
argument_list|(
name|scopeId
argument_list|)
decl_stmt|;
if|if
condition|(
name|sessionur
operator|==
literal|null
operator|||
name|scopeur
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"UriRefs for scope and session cannot be null."
argument_list|)
throw|;
if|if
condition|(
name|meta
operator|instanceof
name|MGraph
condition|)
synchronized|synchronized
init|(
name|meta
init|)
block|{
name|meta
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|sessionur
argument_list|,
name|HAS_APPENDED
argument_list|,
name|scopeur
argument_list|)
argument_list|)
expr_stmt|;
name|meta
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|scopeur
argument_list|,
name|APPENDED_TO
argument_list|,
name|sessionur
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|scopeDetached
parameter_list|(
name|Session
name|session
parameter_list|,
name|String
name|scopeId
parameter_list|)
block|{
specifier|final
name|UriRef
name|sessionur
init|=
name|getIRIforSession
argument_list|(
name|session
argument_list|)
decl_stmt|,
name|scopeur
init|=
name|getIRIforScope
argument_list|(
name|scopeId
argument_list|)
decl_stmt|;
if|if
condition|(
name|sessionur
operator|==
literal|null
operator|||
name|scopeur
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"UriRefs for scope and session cannot be null."
argument_list|)
throw|;
if|if
condition|(
name|meta
operator|instanceof
name|MGraph
condition|)
synchronized|synchronized
init|(
name|meta
init|)
block|{
comment|// TripleImpl implements equals() and hashCode() ...
name|meta
operator|.
name|remove
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|sessionur
argument_list|,
name|HAS_APPENDED
argument_list|,
name|scopeur
argument_list|)
argument_list|)
expr_stmt|;
name|meta
operator|.
name|remove
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|scopeur
argument_list|,
name|APPENDED_TO
argument_list|,
name|sessionur
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|sessionChanged
parameter_list|(
name|SessionEvent
name|event
parameter_list|)
block|{
switch|switch
condition|(
name|event
operator|.
name|getOperationType
argument_list|()
condition|)
block|{
case|case
name|CREATE
case|:
name|updateSessionRegistration
argument_list|(
name|event
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|KILL
case|:
name|updateSessionUnregistration
argument_list|(
name|event
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
specifier|private
name|void
name|updateSessionRegistration
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
specifier|final
name|UriRef
name|sesur
init|=
name|getIRIforSession
argument_list|(
name|session
argument_list|)
decl_stmt|;
comment|// If this method was called after a session rebuild, the following will have little to no effect.
synchronized|synchronized
init|(
name|meta
init|)
block|{
comment|// The only essential triple to add is typing
name|meta
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|sesur
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
name|SESSION
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Ontology collector information triples added for session \"{}\"."
argument_list|,
name|sesur
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|updateSessionUnregistration
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|long
name|before
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|boolean
name|removable
init|=
literal|false
decl_stmt|,
name|conflict
init|=
literal|false
decl_stmt|;
specifier|final
name|UriRef
name|sessionur
init|=
name|getIRIforSession
argument_list|(
name|session
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Triple
argument_list|>
name|removeUs
init|=
operator|new
name|HashSet
argument_list|<
name|Triple
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|meta
operator|.
name|filter
argument_list|(
name|sessionur
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Triple
name|t
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|RDF
operator|.
name|type
operator|.
name|equals
argument_list|(
name|t
operator|.
name|getPredicate
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|SESSION
operator|.
name|equals
argument_list|(
name|t
operator|.
name|getObject
argument_list|()
argument_list|)
condition|)
name|removable
operator|=
literal|true
expr_stmt|;
else|else
name|conflict
operator|=
literal|true
expr_stmt|;
block|}
name|removeUs
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|removable
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Cannot write session deregistration to persistence:"
argument_list|)
expr_stmt|;
name|log
operator|.
name|error
argument_list|(
literal|"-- resource {}"
argument_list|,
name|sessionur
argument_list|)
expr_stmt|;
name|log
operator|.
name|error
argument_list|(
literal|"-- is not typed as a {} in the meta-graph."
argument_list|,
name|SESSION
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|conflict
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Conflict upon session deregistration:"
argument_list|)
expr_stmt|;
name|log
operator|.
name|error
argument_list|(
literal|"-- resource {}"
argument_list|,
name|sessionur
argument_list|)
expr_stmt|;
name|log
operator|.
name|error
argument_list|(
literal|"-- has incompatible types in the meta-graph."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Removing all triples for session \"{}\"."
argument_list|,
name|session
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
decl_stmt|;
for|for
control|(
name|it
operator|=
name|meta
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
name|sessionur
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|removeUs
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|it
operator|=
name|meta
operator|.
name|filter
argument_list|(
name|sessionur
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|removeUs
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|meta
operator|.
name|removeAll
argument_list|(
name|removeUs
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Done; removed {} triples in {} ms."
argument_list|,
name|removeUs
operator|.
name|size
argument_list|()
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|before
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OntologyCollector
argument_list|>
name|getHandles
parameter_list|(
name|OWLOntologyID
name|publicKey
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Not implemented yet."
argument_list|)
throw|;
block|}
block|}
end_class

end_unit
