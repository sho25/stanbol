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
name|metaxa
operator|.
name|core
operator|.
name|html
package|;
end_package

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
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|TransformerFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|aifbcommons
operator|.
name|collection
operator|.
name|ClosableIterator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|Statement
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|Syntax
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|node
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|node
operator|.
name|Variable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|util
operator|.
name|RDFTool
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|extractor
operator|.
name|ExtractorException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|rdf
operator|.
name|RDFContainer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|vocabulary
operator|.
name|NCO
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|vocabulary
operator|.
name|NIE
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

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_comment
comment|/**  * Utility class that provides core HTML text and metadata extraction independent of the configuration of Metaxa's main HTML extractor  *   * @author<a href="mailto:kasper@dfki.de">Walter Kasper</a>  *   */
end_comment

begin_class
specifier|public
class|class
name|HtmlTextExtractUtil
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|LOG
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|HtmlTextExtractUtil
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|HtmlParser
name|htmlParser
init|=
operator|new
name|HtmlParser
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|XsltExtractor
name|htmlExtractor
decl_stmt|;
specifier|public
name|HtmlTextExtractUtil
parameter_list|()
throws|throws
name|InitializationException
block|{
if|if
condition|(
name|HtmlTextExtractUtil
operator|.
name|htmlExtractor
operator|==
literal|null
condition|)
block|{
name|TransformerFactory
name|transFac
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|transFac
operator|.
name|setURIResolver
argument_list|(
operator|new
name|BundleURIResolver
argument_list|()
argument_list|)
expr_stmt|;
name|HtmlTextExtractUtil
operator|.
name|htmlExtractor
operator|=
operator|new
name|XsltExtractor
argument_list|(
literal|"any"
argument_list|,
literal|"xslt/htmlmetadata.xsl"
argument_list|,
name|transFac
argument_list|)
expr_stmt|;
name|HtmlTextExtractUtil
operator|.
name|htmlExtractor
operator|.
name|setSyntax
argument_list|(
name|Syntax
operator|.
name|RdfXml
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getTitle
parameter_list|(
name|Model
name|meta
parameter_list|)
block|{
name|Statement
name|stmt
init|=
name|RDFTool
operator|.
name|findStatement
argument_list|(
name|meta
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|,
name|NIE
operator|.
name|title
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|)
decl_stmt|;
if|if
condition|(
name|stmt
operator|!=
literal|null
condition|)
block|{
return|return
name|stmt
operator|.
name|getObject
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getAuthor
parameter_list|(
name|Model
name|meta
parameter_list|)
block|{
name|Statement
name|stmt
init|=
name|RDFTool
operator|.
name|findStatement
argument_list|(
name|meta
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|,
name|NCO
operator|.
name|creator
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|)
decl_stmt|;
if|if
condition|(
name|stmt
operator|!=
literal|null
condition|)
block|{
name|stmt
operator|=
name|RDFTool
operator|.
name|findStatement
argument_list|(
name|meta
argument_list|,
name|stmt
operator|.
name|getSubject
argument_list|()
argument_list|,
name|NCO
operator|.
name|fullname
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|)
expr_stmt|;
if|if
condition|(
name|stmt
operator|!=
literal|null
condition|)
block|{
return|return
name|stmt
operator|.
name|getObject
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getDescription
parameter_list|(
name|Model
name|meta
parameter_list|)
block|{
name|Statement
name|stmt
init|=
name|RDFTool
operator|.
name|findStatement
argument_list|(
name|meta
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|,
name|NIE
operator|.
name|description
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|)
decl_stmt|;
if|if
condition|(
name|stmt
operator|!=
literal|null
condition|)
block|{
return|return
name|stmt
operator|.
name|getObject
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getKeywords
parameter_list|(
name|Model
name|meta
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|kws
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|ClosableIterator
argument_list|<
name|Statement
argument_list|>
name|it
init|=
name|meta
operator|.
name|findStatements
argument_list|(
name|Variable
operator|.
name|ANY
argument_list|,
name|NIE
operator|.
name|keyword
argument_list|,
name|Variable
operator|.
name|ANY
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
name|kws
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|it
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|kws
return|;
block|}
specifier|public
name|String
name|getText
parameter_list|(
name|Model
name|meta
parameter_list|)
block|{
name|Statement
name|stmt
init|=
name|RDFTool
operator|.
name|findStatement
argument_list|(
name|meta
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|,
name|NIE
operator|.
name|plainTextContent
argument_list|,
name|Variable
operator|.
name|ANY
argument_list|)
decl_stmt|;
if|if
condition|(
name|stmt
operator|!=
literal|null
condition|)
block|{
return|return
name|stmt
operator|.
name|getObject
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|void
name|extract
parameter_list|(
name|URI
name|id
parameter_list|,
name|String
name|charset
parameter_list|,
name|InputStream
name|input
parameter_list|,
name|RDFContainer
name|result
parameter_list|)
throws|throws
name|ExtractorException
block|{
name|String
name|encoding
init|=
name|charset
decl_stmt|;
if|if
condition|(
name|charset
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|encoding
operator|=
name|CharsetRecognizer
operator|.
name|detect
argument_list|(
name|input
argument_list|,
literal|"html"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOG
operator|.
name|error
argument_list|(
literal|"Charset detection problem: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|ExtractorException
argument_list|(
literal|"Charset detection problem: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
name|Document
name|doc
init|=
name|htmlParser
operator|.
name|getDOM
argument_list|(
name|input
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
name|htmlExtractor
operator|.
name|extract
argument_list|(
name|id
operator|.
name|toString
argument_list|()
argument_list|,
name|doc
argument_list|,
literal|null
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

