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
name|enhancer
operator|.
name|engines
operator|.
name|opennlp
operator|.
name|impl
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|DC_RELATION
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|DC_TYPE
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_CONFIDENCE
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_END
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_SELECTED_TEXT
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_SELECTION_CONTEXT
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_START
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedHashMap
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
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|namefind
operator|.
name|NameFinderME
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|namefind
operator|.
name|TokenNameFinderModel
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|sentdetect
operator|.
name|SentenceDetectorME
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|sentdetect
operator|.
name|SentenceModel
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|tokenize
operator|.
name|SimpleTokenizer
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|tokenize
operator|.
name|Tokenizer
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|util
operator|.
name|InvalidFormatException
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|util
operator|.
name|Span
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
name|LiteralFactory
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
name|commons
operator|.
name|io
operator|.
name|IOUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|StringUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProvider
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItem
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EngineException
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|InvalidContentException
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|OntologicalClasses
import|;
end_import

begin_comment
comment|/**  * Core of our EnhancementEngine, separated from the OSGi service to make it easier to test this.  */
end_comment

begin_class
specifier|public
class|class
name|EngineCore
implements|implements
name|EnhancementEngine
block|{
specifier|protected
specifier|static
specifier|final
name|String
name|TEXT_PLAIN_MIMETYPE
init|=
literal|"text/plain"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Log
name|log
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|NamedEntityExtractionEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|DataFileProvider
name|dataFileProvider
decl_stmt|;
specifier|private
specifier|final
name|String
name|bundleSymbolicName
decl_stmt|;
specifier|protected
specifier|final
name|SentenceModel
name|sentenceModel
decl_stmt|;
specifier|protected
specifier|final
name|TokenNameFinderModel
name|personNameModel
decl_stmt|;
specifier|protected
specifier|final
name|TokenNameFinderModel
name|locationNameModel
decl_stmt|;
specifier|protected
specifier|final
name|TokenNameFinderModel
name|organizationNameModel
decl_stmt|;
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|Object
index|[]
argument_list|>
name|entityTypes
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
index|[]
argument_list|>
argument_list|()
decl_stmt|;
comment|/** Comments about our models */
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|DATA_FILE_COMMENTS
decl_stmt|;
static|static
block|{
name|DATA_FILE_COMMENTS
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|DATA_FILE_COMMENTS
operator|.
name|put
argument_list|(
literal|"Default data files"
argument_list|,
literal|"provided by the org.apache.stanbol.defaultdata bundle"
argument_list|)
expr_stmt|;
block|}
name|EngineCore
parameter_list|(
name|DataFileProvider
name|dfp
parameter_list|,
name|String
name|bundleSymbolicName
parameter_list|)
throws|throws
name|InvalidFormatException
throws|,
name|IOException
block|{
name|dataFileProvider
operator|=
name|dfp
expr_stmt|;
name|this
operator|.
name|bundleSymbolicName
operator|=
name|bundleSymbolicName
expr_stmt|;
name|sentenceModel
operator|=
operator|new
name|SentenceModel
argument_list|(
name|lookupModelStream
argument_list|(
literal|"en-sent.bin"
argument_list|)
argument_list|)
expr_stmt|;
name|personNameModel
operator|=
name|buildNameModel
argument_list|(
literal|"person"
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PERSON
argument_list|)
expr_stmt|;
name|locationNameModel
operator|=
name|buildNameModel
argument_list|(
literal|"location"
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_PLACE
argument_list|)
expr_stmt|;
name|organizationNameModel
operator|=
name|buildNameModel
argument_list|(
literal|"organization"
argument_list|,
name|OntologicalClasses
operator|.
name|DBPEDIA_ORGANISATION
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|InputStream
name|lookupModelStream
parameter_list|(
name|String
name|modelRelativePath
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|dataFileProvider
operator|.
name|getInputStream
argument_list|(
name|bundleSymbolicName
argument_list|,
name|modelRelativePath
argument_list|,
name|DATA_FILE_COMMENTS
argument_list|)
return|;
block|}
specifier|protected
name|TokenNameFinderModel
name|buildNameModel
parameter_list|(
name|String
name|name
parameter_list|,
name|UriRef
name|typeUri
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|modelRelativePath
init|=
name|String
operator|.
name|format
argument_list|(
literal|"en-ner-%s.bin"
argument_list|,
name|name
argument_list|)
decl_stmt|;
name|TokenNameFinderModel
name|model
init|=
operator|new
name|TokenNameFinderModel
argument_list|(
name|lookupModelStream
argument_list|(
name|modelRelativePath
argument_list|)
argument_list|)
decl_stmt|;
comment|// register the name finder instances for matching owl class
name|entityTypes
operator|.
name|put
argument_list|(
name|name
argument_list|,
operator|new
name|Object
index|[]
block|{
name|typeUri
block|,
name|model
block|}
argument_list|)
expr_stmt|;
return|return
name|model
return|;
block|}
specifier|public
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
name|String
name|text
decl_stmt|;
try|try
block|{
name|text
operator|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|ci
operator|.
name|getStream
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InvalidContentException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|e
argument_list|)
throw|;
block|}
if|if
condition|(
name|text
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|// TODO: make the length of the data a field of the ContentItem
comment|// interface to be able to filter out empty items in the canEnhance
comment|// method
name|log
operator|.
name|warn
argument_list|(
literal|"nothing to extract knowledge from"
argument_list|)
expr_stmt|;
return|return;
block|}
try|try
block|{
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|Object
index|[]
argument_list|>
name|type
range|:
name|entityTypes
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|typeLabel
init|=
name|type
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|Object
index|[]
name|typeInfo
init|=
name|type
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|UriRef
name|typeUri
init|=
operator|(
name|UriRef
operator|)
name|typeInfo
index|[
literal|0
index|]
decl_stmt|;
name|TokenNameFinderModel
name|nameFinderModel
init|=
operator|(
name|TokenNameFinderModel
operator|)
name|typeInfo
index|[
literal|1
index|]
decl_stmt|;
name|findNamedEntities
argument_list|(
name|ci
argument_list|,
name|text
argument_list|,
name|typeUri
argument_list|,
name|typeLabel
argument_list|,
name|nameFinderModel
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// TODO: makes it sense to catch Exception here?
throw|throw
operator|new
name|EngineException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|void
name|findNamedEntities
parameter_list|(
specifier|final
name|ContentItem
name|ci
parameter_list|,
specifier|final
name|String
name|text
parameter_list|,
specifier|final
name|UriRef
name|typeUri
parameter_list|,
specifier|final
name|String
name|typeLabel
parameter_list|,
specifier|final
name|TokenNameFinderModel
name|nameFinderModel
parameter_list|)
block|{
if|if
condition|(
name|ci
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parsed ContentItem MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"NULL was parsed as text for content item "
operator|+
name|ci
operator|.
name|getId
argument_list|()
operator|+
literal|"! -> call ignored"
argument_list|)
expr_stmt|;
return|return;
block|}
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|MGraph
name|g
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
name|entityNames
init|=
name|extractNameOccurrences
argument_list|(
name|nameFinderModel
argument_list|,
name|text
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|UriRef
argument_list|>
name|previousAnnotations
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|String
argument_list|,
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
name|nameInContext
range|:
name|entityNames
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|name
init|=
name|nameInContext
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|NameOccurrence
argument_list|>
name|occurrences
init|=
name|nameInContext
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|UriRef
name|firstOccurrenceAnnotation
init|=
literal|null
decl_stmt|;
for|for
control|(
name|NameOccurrence
name|occurrence
range|:
name|occurrences
control|)
block|{
name|UriRef
name|textAnnotation
init|=
name|EnhancementEngineHelper
operator|.
name|createTextEnhancement
argument_list|(
name|ci
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTED_TEXT
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|name
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTION_CONTEXT
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|occurrence
operator|.
name|context
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|DC_TYPE
argument_list|,
name|typeUri
argument_list|)
argument_list|)
expr_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_CONFIDENCE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|occurrence
operator|.
name|confidence
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|occurrence
operator|.
name|start
operator|!=
literal|null
operator|&&
name|occurrence
operator|.
name|end
operator|!=
literal|null
condition|)
block|{
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_START
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|occurrence
operator|.
name|start
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|ENHANCER_END
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|occurrence
operator|.
name|end
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// add the subsumption relationship among occurrences of the same
comment|// name
if|if
condition|(
name|firstOccurrenceAnnotation
operator|==
literal|null
condition|)
block|{
comment|// check already extracted annotations to find a first most
comment|// specific occurrence
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|UriRef
argument_list|>
name|entry
range|:
name|previousAnnotations
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|contains
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// we have found a most specific previous
comment|// occurrence, use it as subsumption target
name|firstOccurrenceAnnotation
operator|=
name|entry
operator|.
name|getValue
argument_list|()
expr_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|DC_RELATION
argument_list|,
name|firstOccurrenceAnnotation
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|firstOccurrenceAnnotation
operator|==
literal|null
condition|)
block|{
comment|// no most specific previous occurrence, I am the first,
comment|// most specific occurrence to be later used as a target
name|firstOccurrenceAnnotation
operator|=
name|textAnnotation
expr_stmt|;
name|previousAnnotations
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|textAnnotation
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// I am referring to a most specific first occurrence of the
comment|// same name
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|DC_RELATION
argument_list|,
name|firstOccurrenceAnnotation
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|extractPersonNames
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
name|extractNames
argument_list|(
name|personNameModel
argument_list|,
name|text
argument_list|)
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|extractLocationNames
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
name|extractNames
argument_list|(
name|locationNameModel
argument_list|,
name|text
argument_list|)
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|extractOrganizationNames
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
name|extractNames
argument_list|(
name|organizationNameModel
argument_list|,
name|text
argument_list|)
return|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
name|extractPersonNameOccurrences
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
name|extractNameOccurrences
argument_list|(
name|personNameModel
argument_list|,
name|text
argument_list|)
return|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
name|extractLocationNameOccurrences
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
name|extractNameOccurrences
argument_list|(
name|locationNameModel
argument_list|,
name|text
argument_list|)
return|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
name|extractOrganizationNameOccurrences
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
name|extractNameOccurrences
argument_list|(
name|organizationNameModel
argument_list|,
name|text
argument_list|)
return|;
block|}
specifier|protected
name|Collection
argument_list|<
name|String
argument_list|>
name|extractNames
parameter_list|(
name|TokenNameFinderModel
name|nameFinderModel
parameter_list|,
name|String
name|text
parameter_list|)
block|{
return|return
name|extractNameOccurrences
argument_list|(
name|nameFinderModel
argument_list|,
name|text
argument_list|)
operator|.
name|keySet
argument_list|()
return|;
block|}
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
name|extractNameOccurrences
parameter_list|(
name|TokenNameFinderModel
name|nameFinderModel
parameter_list|,
name|String
name|text
parameter_list|)
block|{
comment|// version with explicit sentence endings to reflect heading / paragraph
comment|// structure of an HTML or PDF document converted to text
name|String
name|textWithDots
init|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"\\n\\n"
argument_list|,
literal|".\n"
argument_list|)
decl_stmt|;
name|SentenceDetectorME
name|sentenceDetector
init|=
operator|new
name|SentenceDetectorME
argument_list|(
name|sentenceModel
argument_list|)
decl_stmt|;
name|Span
index|[]
name|sentenceSpans
init|=
name|sentenceDetector
operator|.
name|sentPosDetect
argument_list|(
name|textWithDots
argument_list|)
decl_stmt|;
name|NameFinderME
name|finder
init|=
operator|new
name|NameFinderME
argument_list|(
name|nameFinderModel
argument_list|)
decl_stmt|;
name|Tokenizer
name|tokenizer
init|=
name|SimpleTokenizer
operator|.
name|INSTANCE
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
name|nameOccurrences
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sentenceSpans
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|sentence
init|=
name|sentenceSpans
index|[
name|i
index|]
operator|.
name|getCoveredText
argument_list|(
name|text
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// build a context by concatenating three sentences to be used for
comment|// similarity ranking / disambiguation + contextual snippet in the
comment|// extraction structure
name|List
argument_list|<
name|String
argument_list|>
name|contextElements
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|i
operator|>
literal|0
condition|)
block|{
name|CharSequence
name|previousSentence
init|=
name|sentenceSpans
index|[
name|i
operator|-
literal|1
index|]
operator|.
name|getCoveredText
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|contextElements
operator|.
name|add
argument_list|(
name|previousSentence
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|contextElements
operator|.
name|add
argument_list|(
name|sentence
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|+
literal|1
operator|<
name|sentenceSpans
operator|.
name|length
condition|)
block|{
name|CharSequence
name|nextSentence
init|=
name|sentenceSpans
index|[
name|i
operator|+
literal|1
index|]
operator|.
name|getCoveredText
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|contextElements
operator|.
name|add
argument_list|(
name|nextSentence
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|String
name|context
init|=
name|StringUtils
operator|.
name|join
argument_list|(
name|contextElements
argument_list|,
literal|" "
argument_list|)
decl_stmt|;
comment|// extract the names in the current sentence and
comment|// keep them store them with the current context
name|String
index|[]
name|tokens
init|=
name|tokenizer
operator|.
name|tokenize
argument_list|(
name|sentence
argument_list|)
decl_stmt|;
name|Span
index|[]
name|nameSpans
init|=
name|finder
operator|.
name|find
argument_list|(
name|tokens
argument_list|)
decl_stmt|;
name|double
index|[]
name|probs
init|=
name|finder
operator|.
name|probs
argument_list|()
decl_stmt|;
name|String
index|[]
name|names
init|=
name|Span
operator|.
name|spansToStrings
argument_list|(
name|nameSpans
argument_list|,
name|tokens
argument_list|)
decl_stmt|;
name|int
name|lastStartPosition
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|names
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|String
name|name
init|=
name|names
index|[
name|j
index|]
decl_stmt|;
name|Double
name|confidence
init|=
literal|1.0
decl_stmt|;
for|for
control|(
name|int
name|k
init|=
name|nameSpans
index|[
name|j
index|]
operator|.
name|getStart
argument_list|()
init|;
name|k
operator|<
name|nameSpans
index|[
name|j
index|]
operator|.
name|getEnd
argument_list|()
condition|;
name|k
operator|++
control|)
block|{
name|confidence
operator|*=
name|probs
index|[
name|k
index|]
expr_stmt|;
block|}
name|int
name|start
init|=
name|sentence
operator|.
name|substring
argument_list|(
name|lastStartPosition
argument_list|)
operator|.
name|indexOf
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|Integer
name|absoluteStart
init|=
literal|null
decl_stmt|;
name|Integer
name|absoluteEnd
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|start
operator|!=
operator|-
literal|1
condition|)
block|{
comment|/*                      * NOTE (rw, issue 19, 20100615) Here we need to set the new start position, by adding the                      * current start to the lastStartPosion. we need also to use the lastStartPosition to                      * calculate the start of the element. The old code had not worked if names contains more                      * than a single element!                      */
name|lastStartPosition
operator|+=
name|start
expr_stmt|;
name|absoluteStart
operator|=
name|sentenceSpans
index|[
name|i
index|]
operator|.
name|getStart
argument_list|()
operator|+
name|lastStartPosition
expr_stmt|;
name|absoluteEnd
operator|=
name|absoluteStart
operator|+
name|name
operator|.
name|length
argument_list|()
expr_stmt|;
block|}
name|NameOccurrence
name|occurrence
init|=
operator|new
name|NameOccurrence
argument_list|(
name|name
argument_list|,
name|absoluteStart
argument_list|,
name|absoluteEnd
argument_list|,
name|context
argument_list|,
name|confidence
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|NameOccurrence
argument_list|>
name|occurrences
init|=
name|nameOccurrences
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|occurrences
operator|==
literal|null
condition|)
block|{
name|occurrences
operator|=
operator|new
name|ArrayList
argument_list|<
name|NameOccurrence
argument_list|>
argument_list|()
expr_stmt|;
block|}
name|occurrences
operator|.
name|add
argument_list|(
name|occurrence
argument_list|)
expr_stmt|;
name|nameOccurrences
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|occurrences
argument_list|)
expr_stmt|;
block|}
block|}
name|finder
operator|.
name|clearAdaptiveData
argument_list|()
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
for|for
control|(
name|List
argument_list|<
name|NameOccurrence
argument_list|>
name|occurrences
range|:
name|nameOccurrences
operator|.
name|values
argument_list|()
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Occurrences found: "
operator|+
name|StringUtils
operator|.
name|join
argument_list|(
name|occurrences
argument_list|,
literal|", "
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|nameOccurrences
return|;
block|}
specifier|public
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
comment|// in case text/pain;charSet=UTF8 is parsed
name|String
name|mimeType
init|=
name|ci
operator|.
name|getMimeType
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|,
literal|2
argument_list|)
index|[
literal|0
index|]
decl_stmt|;
if|if
condition|(
name|TEXT_PLAIN_MIMETYPE
operator|.
name|equalsIgnoreCase
argument_list|(
name|mimeType
argument_list|)
condition|)
block|{
return|return
name|ENHANCE_SYNCHRONOUS
return|;
block|}
return|return
name|CANNOT_ENHANCE
return|;
block|}
block|}
end_class

end_unit

