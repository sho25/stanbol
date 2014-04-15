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
name|servicesapi
operator|.
name|util
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|MANCHESTER_OWL
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|N3
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|N_TRIPLE
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|OWL_XML
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|RDF_JSON
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|RDF_XML
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|TURTLE
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
operator|.
name|KRFormat
operator|.
name|X_TURTLE
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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

begin_class
specifier|public
specifier|final
class|class
name|OntologyUtils
block|{
comment|/**      * Restrict instantiation      */
specifier|private
name|OntologyUtils
parameter_list|()
block|{}
specifier|private
specifier|static
name|String
index|[]
name|preferredFormats
init|=
block|{
name|RDF_XML
block|,
name|TURTLE
block|,
name|X_TURTLE
block|,
name|RDF_JSON
block|,
name|N3
block|,
name|N_TRIPLE
block|,
name|MANCHESTER_OWL
block|,
name|FUNCTIONAL_OWL
block|,
name|OWL_XML
block|}
decl_stmt|;
comment|/**      * Extracts an OWL Ontology ID from its standard string form. The string must be of type      *<tt>ontologyIRI[:::versionIRI]</tt>. Any substring<tt>"%3A%3A%3A"</tt> present in<tt>ontologyIRI</tt>      * or<tt>versionIRI</tt> will be URL-decoded (i.e. converted to<tt>":::"</tt>).<br/>      *<br/>      * Also note that both<tt>ontologyIRI</tt> and<tt>versionIRI</tt> are desanitized in the process.      *       * @param stringForm      *            the string to decode      * @return the string form of this ID.      * @see URIUtils#desanitize(IRI)      */
specifier|public
specifier|static
name|OWLOntologyID
name|decode
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
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Supplied string form must be non-null and non-empty."
argument_list|)
throw|;
name|IRI
name|oiri
decl_stmt|,
name|viri
decl_stmt|;
name|String
index|[]
name|split
init|=
name|stringForm
operator|.
name|split
argument_list|(
literal|":::"
argument_list|)
decl_stmt|;
if|if
condition|(
name|split
operator|.
name|length
operator|>=
literal|1
condition|)
block|{
name|oiri
operator|=
name|URIUtils
operator|.
name|desanitize
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|split
index|[
literal|0
index|]
operator|.
name|replace
argument_list|(
literal|"%3A%3A%3A"
argument_list|,
literal|":::"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|viri
operator|=
operator|(
name|split
operator|.
name|length
operator|>
literal|1
operator|)
condition|?
name|URIUtils
operator|.
name|desanitize
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|split
index|[
literal|1
index|]
operator|.
name|replace
argument_list|(
literal|"%3A%3A%3A"
argument_list|,
literal|":::"
argument_list|)
argument_list|)
argument_list|)
else|:
literal|null
expr_stmt|;
return|return
operator|(
name|viri
operator|!=
literal|null
operator|)
condition|?
operator|new
name|OWLOntologyID
argument_list|(
name|oiri
argument_list|,
name|viri
argument_list|)
else|:
operator|new
name|OWLOntologyID
argument_list|(
name|oiri
argument_list|)
return|;
block|}
else|else
return|return
literal|null
return|;
comment|// Anonymous but versioned ontologies are not acceptable.
block|}
comment|/**      * Provides a standardized string format for an OWL Ontology ID. The string returned is of type      *<tt>ontologyIRI[:::versionIRI]</tt>. Any substring<tt>":::"</tt> present in<tt>ontologyIRI</tt> or      *<tt>versionIRI</tt> will be URL-encoded (i.e. converted to<tt>"%3A%3A%3A"</tt>).<br/>      *<br/>      * Also note that both<tt>ontologyIRI</tt> and<tt>versionIRI</tt> are sanitized in the process. No other      * URL encoding occurs.      *       * @param id      *            the OWL ontology ID to encode      * @return the string form of this ID.      * @see URIUtils#sanitize(IRI)      */
specifier|public
specifier|static
name|String
name|encode
parameter_list|(
name|OWLOntologyID
name|id
parameter_list|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot encode a null OWLOntologyID."
argument_list|)
throw|;
if|if
condition|(
name|id
operator|.
name|getOntologyIRI
argument_list|()
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot encode an OWLOntologyID that is missing an ontologyIRI."
argument_list|)
throw|;
name|String
name|s
init|=
literal|""
decl_stmt|;
name|s
operator|+=
name|URIUtils
operator|.
name|sanitize
argument_list|(
name|id
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|":::"
argument_list|,
literal|"%3A%3A%3A"
argument_list|)
expr_stmt|;
if|if
condition|(
name|id
operator|.
name|getVersionIRI
argument_list|()
operator|!=
literal|null
condition|)
name|s
operator|+=
operator|(
literal|":::"
operator|)
operator|+
name|URIUtils
operator|.
name|sanitize
argument_list|(
name|id
operator|.
name|getVersionIRI
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|":::"
argument_list|,
literal|"%3A%3A%3A"
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getPreferredFormats
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|f
range|:
name|preferredFormats
control|)
name|result
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getPreferredSupportedFormats
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|supported
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|f
range|:
name|preferredFormats
control|)
if|if
condition|(
name|supported
operator|.
name|contains
argument_list|(
name|f
argument_list|)
condition|)
name|result
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
comment|// The non-preferred supported formats on the tail in any order
for|for
control|(
name|String
name|f
range|:
name|supported
control|)
if|if
condition|(
operator|!
name|result
operator|.
name|contains
argument_list|(
name|f
argument_list|)
condition|)
name|result
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

