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
name|commons
operator|.
name|owl
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
import|;
end_import

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
name|io
operator|.
name|InputStream
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
name|NonLiteral
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
name|clerezza
operator|.
name|rdf
operator|.
name|ontologies
operator|.
name|OWL
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
name|commons
operator|.
name|owl
operator|.
name|OntologyLookaheadMGraph
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
comment|/**  * A set of utility methods for the manipulation of OWL API objects.  */
end_comment

begin_class
specifier|public
class|class
name|OWLUtils
block|{
specifier|private
specifier|static
name|int
name|_LOOKAHEAD_LIMIT_DEFAULT
init|=
literal|1024
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
name|OWLUtils
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|NS_STANBOL
init|=
literal|"http://stanbol.apache.org/"
decl_stmt|;
comment|/**      * If the ontology is named, this method will return its logical ID, otherwise it will return the location      * it was retrieved from (which is still unique).      *       * @param o      * @return      */
specifier|public
specifier|static
name|OWLOntologyID
name|extractOntologyID
parameter_list|(
name|OWLOntology
name|o
parameter_list|)
block|{
name|String
name|oiri
decl_stmt|;
name|IRI
name|viri
init|=
literal|null
decl_stmt|;
comment|// For named OWL ontologies it is their ontology ID.
comment|// For anonymous ontologies, it is the URI they were fetched from, if any.
if|if
condition|(
name|o
operator|.
name|isAnonymous
argument_list|()
condition|)
name|oiri
operator|=
name|o
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|o
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
else|else
block|{
name|OWLOntologyID
name|id
init|=
name|o
operator|.
name|getOntologyID
argument_list|()
decl_stmt|;
name|oiri
operator|=
name|id
operator|.
name|getOntologyIRI
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|viri
operator|=
name|id
operator|.
name|getVersionIRI
argument_list|()
expr_stmt|;
block|}
comment|// Strip fragment or query tokens. TODO do proper URL Encoding.
while|while
condition|(
name|oiri
operator|.
name|endsWith
argument_list|(
literal|"#"
argument_list|)
operator|||
name|oiri
operator|.
name|endsWith
argument_list|(
literal|"?"
argument_list|)
condition|)
name|oiri
operator|=
name|oiri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|oiri
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|viri
operator|!=
literal|null
condition|)
return|return
operator|new
name|OWLOntologyID
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|oiri
argument_list|)
argument_list|,
name|viri
argument_list|)
return|;
else|else
return|return
operator|new
name|OWLOntologyID
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|oiri
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Returns the logical identifier of the supplied RDF graph, which is interpreted as an OWL ontology.      *       * @param graph      *            the RDF graph      * @return the OWL ontology ID of the supplied graph, or null if it denotes an anonymous ontology.      */
specifier|public
specifier|static
name|OWLOntologyID
name|extractOntologyID
parameter_list|(
name|TripleCollection
name|graph
parameter_list|)
block|{
name|IRI
name|ontologyIri
init|=
literal|null
decl_stmt|,
name|versionIri
init|=
literal|null
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|graph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
name|OWL
operator|.
name|Ontology
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
name|NonLiteral
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
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Multiple OWL ontology definitions found."
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Ignoring all but {}"
argument_list|,
name|subj
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|subj
operator|instanceof
name|UriRef
condition|)
block|{
name|ontologyIri
operator|=
name|IRI
operator|.
name|create
argument_list|(
operator|(
operator|(
name|UriRef
operator|)
name|subj
operator|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it2
init|=
name|graph
operator|.
name|filter
argument_list|(
operator|(
name|UriRef
operator|)
name|subj
argument_list|,
operator|new
name|UriRef
argument_list|(
name|OWL2Constants
operator|.
name|OWL_VERSION_IRI
argument_list|)
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|it2
operator|.
name|hasNext
argument_list|()
condition|)
name|versionIri
operator|=
name|IRI
operator|.
name|create
argument_list|(
operator|(
operator|(
name|UriRef
operator|)
name|it2
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
operator|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|ontologyIri
operator|==
literal|null
condition|)
block|{
comment|// Note that OWL 2 does not allow ontologies with a version IRI and no ontology IRI.
name|log
operator|.
name|debug
argument_list|(
literal|"Ontology is anonymous. Returning null ID."
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|versionIri
operator|==
literal|null
condition|)
return|return
operator|new
name|OWLOntologyID
argument_list|(
name|ontologyIri
argument_list|)
return|;
else|else
return|return
operator|new
name|OWLOntologyID
argument_list|(
name|ontologyIri
argument_list|,
name|versionIri
argument_list|)
return|;
block|}
comment|/**      * Performs lookahead with a 100 kB limit.      *       * @param content      * @param parser      * @param format      * @return      * @throws IOException      */
specifier|public
specifier|static
name|OWLOntologyID
name|guessOntologyID
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|Parser
name|parser
parameter_list|,
name|String
name|format
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|guessOntologyID
argument_list|(
name|content
argument_list|,
name|parser
argument_list|,
name|format
argument_list|,
name|_LOOKAHEAD_LIMIT_DEFAULT
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|OWLOntologyID
name|guessOntologyID
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|Parser
name|parser
parameter_list|,
name|String
name|format
parameter_list|,
name|int
name|limit
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|guessOntologyID
argument_list|(
name|content
argument_list|,
name|parser
argument_list|,
name|format
argument_list|,
name|limit
argument_list|,
name|Math
operator|.
name|max
argument_list|(
literal|10
argument_list|,
name|limit
operator|/
literal|10
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|OWLOntologyID
name|guessOntologyID
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|Parser
name|parser
parameter_list|,
name|String
name|format
parameter_list|,
name|int
name|limit
parameter_list|,
name|int
name|versionIriOffset
parameter_list|)
throws|throws
name|IOException
block|{
name|long
name|before
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Guessing ontology ID. Read limit = {} triples; offset = {} triples."
argument_list|,
name|limit
argument_list|,
name|versionIriOffset
argument_list|)
expr_stmt|;
name|BufferedInputStream
name|bIn
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|content
argument_list|)
decl_stmt|;
name|bIn
operator|.
name|mark
argument_list|(
name|limit
operator|*
literal|512
argument_list|)
expr_stmt|;
comment|// set an appropriate limit
name|OntologyLookaheadMGraph
name|graph
init|=
operator|new
name|OntologyLookaheadMGraph
argument_list|(
name|limit
argument_list|,
name|versionIriOffset
argument_list|)
decl_stmt|;
try|try
block|{
name|parser
operator|.
name|parse
argument_list|(
name|graph
argument_list|,
name|bIn
argument_list|,
name|format
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Parsing failed for format {}. Returning null."
argument_list|,
name|format
argument_list|)
expr_stmt|;
block|}
name|OWLOntologyID
name|result
decl_stmt|;
if|if
condition|(
name|graph
operator|.
name|getOntologyIRI
argument_list|()
operator|==
literal|null
condition|)
block|{
comment|// No Ontology ID found
name|log
operator|.
name|warn
argument_list|(
literal|" *** No ontology ID found, ontology has a chance of being anonymous."
argument_list|)
expr_stmt|;
name|result
operator|=
operator|new
name|OWLOntologyID
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// bIn.reset(); // reset set the stream to the start
name|IRI
name|oiri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|graph
operator|.
name|getOntologyIRI
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator|=
name|graph
operator|.
name|getVersionIRI
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|OWLOntologyID
argument_list|(
name|oiri
argument_list|)
else|:
operator|new
name|OWLOntologyID
argument_list|(
name|oiri
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
name|graph
operator|.
name|getVersionIRI
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|" *** Guessed ID : {}"
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|" ... Triples scanned : {}; filtered in : {}"
argument_list|,
name|graph
operator|.
name|getScannedTripleCount
argument_list|()
argument_list|,
name|graph
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|" ... Time : {} ms"
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|before
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

