begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|ldpath
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
name|ENHANCER_ENTITY_REFERENCE
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
name|ENHANCER_EXTRACTED_FROM
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
name|RDF_TYPE
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
name|TechnicalClasses
operator|.
name|ENHANCER_ENHANCEMENT
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
name|TechnicalClasses
operator|.
name|ENHANCER_ENTITYANNOTATION
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
name|TechnicalClasses
operator|.
name|ENHANCER_TEXTANNOTATION
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
name|TechnicalClasses
operator|.
name|ENHANCER_TOPICANNOTATION
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
name|commons
operator|.
name|rdf
operator|.
name|RDFTerm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|api
operator|.
name|functions
operator|.
name|SelectorFunction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|api
operator|.
name|selectors
operator|.
name|NodeSelector
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|model
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|parser
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|parser
operator|.
name|DefaultConfiguration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|parser
operator|.
name|ParseException
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
name|ldpath
operator|.
name|function
operator|.
name|ContentFunction
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
name|ldpath
operator|.
name|function
operator|.
name|PathFunction
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
name|ldpath
operator|.
name|function
operator|.
name|SuggestionFunction
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
name|ldpath
operator|.
name|utils
operator|.
name|Utils
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
name|NamespaceEnum
import|;
end_import

begin_comment
comment|/**  * Defines defaults for LDPath  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|EnhancerLDPath
block|{
specifier|private
name|EnhancerLDPath
parameter_list|()
block|{}
specifier|private
specifier|static
name|Configuration
argument_list|<
name|RDFTerm
argument_list|>
name|CONFIG
decl_stmt|;
comment|/**      * The LDPath configuration including the<ul>      *<li> Namespaces defined by the {@link NamespaceEnum}      *<li> the LDPath functions for the Stanbol Enhancement Structure      *</ul>      * @return the LDPath configuration for the Stanbol Enhancer      */
specifier|public
specifier|static
specifier|final
name|Configuration
argument_list|<
name|RDFTerm
argument_list|>
name|getConfig
parameter_list|()
block|{
if|if
condition|(
name|CONFIG
operator|==
literal|null
condition|)
block|{
name|CONFIG
operator|=
operator|new
name|DefaultConfiguration
argument_list|<
name|RDFTerm
argument_list|>
argument_list|()
expr_stmt|;
comment|//add the namespaces
for|for
control|(
name|NamespaceEnum
name|ns
range|:
name|NamespaceEnum
operator|.
name|values
argument_list|()
control|)
block|{
name|CONFIG
operator|.
name|addNamespace
argument_list|(
name|ns
operator|.
name|getPrefix
argument_list|()
argument_list|,
name|ns
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//now add the functions
name|addFunction
argument_list|(
name|CONFIG
argument_list|,
operator|new
name|ContentFunction
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|path
decl_stmt|;
name|NodeSelector
argument_list|<
name|RDFTerm
argument_list|>
name|selector
decl_stmt|;
comment|//TextAnnotations
name|path
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"^%s[%s is %s]"
argument_list|,
name|ENHANCER_EXTRACTED_FROM
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_TEXTANNOTATION
argument_list|)
expr_stmt|;
try|try
block|{
name|selector
operator|=
name|Utils
operator|.
name|parseSelector
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse the ld-path selector '"
operator|+
name|path
operator|+
literal|"'used to select all TextAnnotations of a contentItem!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|addFunction
argument_list|(
name|CONFIG
argument_list|,
operator|new
name|PathFunction
argument_list|<
name|RDFTerm
argument_list|>
argument_list|(
literal|"textAnnotation"
argument_list|,
name|selector
argument_list|)
argument_list|)
expr_stmt|;
comment|//EntityAnnotations
name|path
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"^%s[%s is %s]"
argument_list|,
name|ENHANCER_EXTRACTED_FROM
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_ENTITYANNOTATION
argument_list|)
expr_stmt|;
try|try
block|{
name|selector
operator|=
name|Utils
operator|.
name|parseSelector
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse the ld-path selector '"
operator|+
name|path
operator|+
literal|"'used to select all EntityAnnotations of a contentItem!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|addFunction
argument_list|(
name|CONFIG
argument_list|,
operator|new
name|PathFunction
argument_list|<
name|RDFTerm
argument_list|>
argument_list|(
literal|"entityAnnotation"
argument_list|,
name|selector
argument_list|)
argument_list|)
expr_stmt|;
comment|//TopicAnnotations
name|path
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"^%s[%s is %s]"
argument_list|,
name|ENHANCER_EXTRACTED_FROM
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_TOPICANNOTATION
argument_list|)
expr_stmt|;
try|try
block|{
name|selector
operator|=
name|Utils
operator|.
name|parseSelector
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse the ld-path selector '"
operator|+
name|path
operator|+
literal|"'used to select all TopicAnnotations of a contentItem!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|addFunction
argument_list|(
name|CONFIG
argument_list|,
operator|new
name|PathFunction
argument_list|<
name|RDFTerm
argument_list|>
argument_list|(
literal|"topicAnnotation"
argument_list|,
name|selector
argument_list|)
argument_list|)
expr_stmt|;
comment|//Enhancements
name|path
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"^%s[%s is %s]"
argument_list|,
name|ENHANCER_EXTRACTED_FROM
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_ENHANCEMENT
argument_list|)
expr_stmt|;
try|try
block|{
name|selector
operator|=
name|Utils
operator|.
name|parseSelector
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse the ld-path selector '"
operator|+
name|path
operator|+
literal|"'used to select all Enhancements of a contentItem!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|addFunction
argument_list|(
name|CONFIG
argument_list|,
operator|new
name|PathFunction
argument_list|<
name|RDFTerm
argument_list|>
argument_list|(
literal|"enhancement"
argument_list|,
name|selector
argument_list|)
argument_list|)
expr_stmt|;
comment|//Suggested EntityAnnotations for Text/TopicAnnotations
comment|//(1) to select the suggestions
name|NodeSelector
argument_list|<
name|RDFTerm
argument_list|>
name|linkedEntityAnnotations
decl_stmt|;
name|path
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"^%s[%s is %s]"
argument_list|,
name|DC_RELATION
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_ENTITYANNOTATION
argument_list|,
name|ENHANCER_CONFIDENCE
argument_list|)
expr_stmt|;
try|try
block|{
name|linkedEntityAnnotations
operator|=
name|Utils
operator|.
name|parseSelector
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse the ld-path selector '"
operator|+
name|path
operator|+
literal|"'used to select all entity suggestions for an Enhancement!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
comment|//(2) to select the confidence value of Enhancements
name|NodeSelector
argument_list|<
name|RDFTerm
argument_list|>
name|confidenceSelector
decl_stmt|;
name|path
operator|=
name|ENHANCER_CONFIDENCE
operator|.
name|toString
argument_list|()
expr_stmt|;
try|try
block|{
name|confidenceSelector
operator|=
name|Utils
operator|.
name|parseSelector
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse the ld-path selector '"
operator|+
name|path
operator|+
literal|"'used to select the confidence of suggestions!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
comment|//The resultSelector is NULL because this directly returns the EntityAnnotations
name|addFunction
argument_list|(
name|CONFIG
argument_list|,
operator|new
name|SuggestionFunction
argument_list|(
literal|"suggestion"
argument_list|,
name|linkedEntityAnnotations
argument_list|,
name|confidenceSelector
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|//Suggested Entities for Text/TopicAnnotations
comment|//The suggestion and confidence selectors can be the same as above,
comment|//but we need an additional result selector
name|NodeSelector
argument_list|<
name|RDFTerm
argument_list|>
name|entityReferenceSelector
decl_stmt|;
name|path
operator|=
name|ENHANCER_ENTITY_REFERENCE
operator|.
name|toString
argument_list|()
expr_stmt|;
try|try
block|{
name|entityReferenceSelector
operator|=
name|Utils
operator|.
name|parseSelector
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse the ld-path selector '"
operator|+
name|path
operator|+
literal|"'used to select the entity referenced by a EntityAnnotation!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|addFunction
argument_list|(
name|CONFIG
argument_list|,
operator|new
name|SuggestionFunction
argument_list|(
literal|"suggestedEntity"
argument_list|,
name|linkedEntityAnnotations
argument_list|,
name|confidenceSelector
argument_list|,
name|entityReferenceSelector
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|CONFIG
return|;
block|}
specifier|private
specifier|static
parameter_list|<
name|Node
parameter_list|>
name|void
name|addFunction
parameter_list|(
name|Configuration
argument_list|<
name|Node
argument_list|>
name|config
parameter_list|,
name|SelectorFunction
argument_list|<
name|Node
argument_list|>
name|function
parameter_list|)
block|{
name|config
operator|.
name|addFunction
argument_list|(
name|Constants
operator|.
name|NS_LMF_FUNCS
operator|+
name|function
operator|.
name|getPathExpression
argument_list|(
literal|null
argument_list|)
argument_list|,
name|function
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

