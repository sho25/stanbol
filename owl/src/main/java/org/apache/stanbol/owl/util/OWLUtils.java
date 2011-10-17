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
name|owl
operator|.
name|util
package|;
end_package

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
name|UriRef
name|guessOntologyIdentifier
parameter_list|(
name|Object
name|g
parameter_list|)
block|{
if|if
condition|(
name|g
operator|instanceof
name|TripleCollection
condition|)
return|return
name|guessOntologyIdentifier
argument_list|(
operator|(
name|TripleCollection
operator|)
name|g
argument_list|)
return|;
elseif|else
if|if
condition|(
name|g
operator|instanceof
name|OWLOntology
condition|)
return|return
name|URIUtils
operator|.
name|createUriRef
argument_list|(
name|guessOntologyIdentifier
argument_list|(
operator|(
name|OWLOntology
operator|)
name|g
argument_list|)
argument_list|)
return|;
else|else
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot guess ontology identifier for objects of type "
operator|+
name|g
operator|.
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
comment|/**      * If the ontology is named, this method will return its logical ID, otherwise it will return the location      * it was retrieved from (which is still unique).      *       * @param o      * @return      */
specifier|public
specifier|static
name|IRI
name|guessOntologyIdentifier
parameter_list|(
name|OWLOntology
name|o
parameter_list|)
block|{
name|String
name|iri
decl_stmt|;
comment|// For named OWL ontologies it is their ontology ID. For anonymous ontologies, it is the URI they were
comment|// fetched from, if any.
if|if
condition|(
name|o
operator|.
name|isAnonymous
argument_list|()
condition|)
name|iri
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
name|iri
operator|=
name|o
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
comment|// Strip fragment or query tokens. TODO do proper URL Encoding.
while|while
condition|(
name|iri
operator|.
name|endsWith
argument_list|(
literal|"#"
argument_list|)
operator|||
name|iri
operator|.
name|endsWith
argument_list|(
literal|"?"
argument_list|)
condition|)
name|iri
operator|=
name|iri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|iri
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
comment|// try {
comment|// if (originalIri.endsWith("#")) originalIri = originalIri.substring(0,
comment|// originalIri.length() - 1) + URLEncoder.encode("#", "UTF-8");
comment|// else if (originalIri.endsWith("?")) originalIri = originalIri.substring(0,
comment|// originalIri.length() - 1)
comment|// + URLEncoder.encode("?", "UTF-8");
comment|// } catch (UnsupportedEncodingException e) {
comment|// // That cannot be.
comment|// }
return|return
name|IRI
operator|.
name|create
argument_list|(
name|iri
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|UriRef
name|guessOntologyIdentifier
parameter_list|(
name|TripleCollection
name|g
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|g
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
name|log
operator|.
name|warn
argument_list|(
literal|"RDF Graph {} has multiple OWL ontology definitions! Ignoring all but {}"
argument_list|,
name|g
argument_list|,
name|subj
argument_list|)
expr_stmt|;
if|if
condition|(
name|subj
operator|instanceof
name|UriRef
condition|)
return|return
operator|(
name|UriRef
operator|)
name|subj
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

