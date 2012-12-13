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
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

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
name|HashMap
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
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|Source
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
name|Transformer
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
name|TransformerConfigurationException
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
name|TransformerException
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
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|dom
operator|.
name|DOMSource
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
name|stream
operator|.
name|StreamResult
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
name|stream
operator|.
name|StreamSource
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
name|exception
operator|.
name|ModelRuntimeException
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
comment|/**  * XsltExtractor.java  *  * @author<a href="mailto:kasper@dfki.de">Walter Kasper</a>  */
end_comment

begin_class
specifier|public
class|class
name|XsltExtractor
implements|implements
name|HtmlExtractionComponent
block|{
comment|/**      * This contains the logger.      */
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
name|XsltExtractor
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|Syntax
name|N3
init|=
operator|new
name|Syntax
argument_list|(
literal|"N3"
argument_list|,
literal|"application/rdf+n3"
argument_list|,
literal|".n3"
argument_list|,
literal|true
argument_list|)
decl_stmt|;
specifier|private
name|String
name|uriParameter
init|=
literal|"uri"
decl_stmt|;
specifier|private
name|Transformer
name|transformer
decl_stmt|;
specifier|private
name|String
name|id
decl_stmt|;
specifier|private
name|URI
name|source
decl_stmt|;
specifier|private
name|Syntax
name|syntax
init|=
name|Syntax
operator|.
name|RdfXml
decl_stmt|;
specifier|public
name|XsltExtractor
parameter_list|()
block|{     }
specifier|public
name|XsltExtractor
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|fileName
parameter_list|,
name|TransformerFactory
name|factory
parameter_list|)
throws|throws
name|InitializationException
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
try|try
block|{
name|URI
name|location
init|=
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
name|fileName
argument_list|)
operator|.
name|toURI
argument_list|()
decl_stmt|;
name|source
operator|=
name|location
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|initialize
argument_list|(
name|factory
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getUriParameter
parameter_list|()
block|{
return|return
name|uriParameter
return|;
block|}
specifier|public
name|void
name|setUriParameter
parameter_list|(
name|String
name|uriParameter
parameter_list|)
block|{
name|this
operator|.
name|uriParameter
operator|=
name|uriParameter
expr_stmt|;
block|}
specifier|public
name|Syntax
name|getSyntax
parameter_list|()
block|{
return|return
name|syntax
return|;
block|}
specifier|public
name|void
name|setSyntax
parameter_list|(
name|Syntax
name|syntax
parameter_list|)
block|{
name|this
operator|.
name|syntax
operator|=
name|syntax
expr_stmt|;
block|}
specifier|public
name|Transformer
name|getTransformer
parameter_list|()
block|{
return|return
name|transformer
return|;
block|}
specifier|public
name|void
name|setTransformer
parameter_list|(
name|Transformer
name|transformer
parameter_list|)
block|{
name|this
operator|.
name|transformer
operator|=
name|transformer
expr_stmt|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|URI
name|getSource
parameter_list|()
block|{
return|return
name|source
return|;
block|}
specifier|public
name|void
name|setSource
parameter_list|(
name|URI
name|source
parameter_list|)
block|{
name|this
operator|.
name|source
operator|=
name|source
expr_stmt|;
block|}
specifier|public
specifier|synchronized
name|void
name|extract
parameter_list|(
name|String
name|id
parameter_list|,
name|Document
name|doc
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|params
parameter_list|,
name|RDFContainer
name|result
parameter_list|)
throws|throws
name|ExtractorException
block|{
if|if
condition|(
name|params
operator|==
literal|null
condition|)
block|{
name|params
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
block|}
name|params
operator|.
name|put
argument_list|(
name|this
operator|.
name|uriParameter
argument_list|,
name|id
argument_list|)
expr_stmt|;
name|initTransformerParameters
argument_list|(
name|params
argument_list|)
expr_stmt|;
name|Source
name|source
init|=
operator|new
name|DOMSource
argument_list|(
name|doc
argument_list|)
decl_stmt|;
name|StringWriter
name|writer
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|StreamResult
name|output
init|=
operator|new
name|StreamResult
argument_list|(
name|writer
argument_list|)
decl_stmt|;
try|try
block|{
name|this
operator|.
name|transformer
operator|.
name|transform
argument_list|(
name|source
argument_list|,
name|output
argument_list|)
expr_stmt|;
name|String
name|rdf
init|=
name|writer
operator|.
name|toString
argument_list|()
decl_stmt|;
name|LOG
operator|.
name|debug
argument_list|(
name|rdf
argument_list|)
expr_stmt|;
name|StringReader
name|reader
init|=
operator|new
name|StringReader
argument_list|(
name|rdf
argument_list|)
decl_stmt|;
name|result
operator|.
name|getModel
argument_list|()
operator|.
name|readFrom
argument_list|(
name|reader
argument_list|,
name|this
operator|.
name|syntax
argument_list|)
expr_stmt|;
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|TransformerException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ExtractorException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|ModelRuntimeException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ExtractorException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ExtractorException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|public
name|void
name|initialize
parameter_list|(
name|TransformerFactory
name|factory
parameter_list|)
throws|throws
name|InitializationException
block|{
if|if
condition|(
name|source
operator|==
literal|null
operator|||
name|id
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
literal|"Missing source or id"
argument_list|)
throw|;
block|}
if|if
condition|(
name|factory
operator|==
literal|null
condition|)
block|{
name|factory
operator|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
expr_stmt|;
name|factory
operator|.
name|setURIResolver
argument_list|(
operator|new
name|BundleURIResolver
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|StreamSource
name|xsltSource
init|=
operator|new
name|StreamSource
argument_list|(
name|source
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|xsltSource
operator|.
name|setSystemId
argument_list|(
name|source
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|transformer
operator|=
name|factory
operator|.
name|newTransformer
argument_list|(
name|xsltSource
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|TransformerConfigurationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|public
name|void
name|initTransformerParameters
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|params
parameter_list|)
block|{
name|transformer
operator|.
name|clearParameters
argument_list|()
expr_stmt|;
if|if
condition|(
name|params
operator|!=
literal|null
condition|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|parms
init|=
name|params
operator|.
name|keySet
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|piter
range|:
name|parms
control|)
block|{
name|transformer
operator|.
name|setParameter
argument_list|(
name|piter
argument_list|,
name|params
operator|.
name|get
argument_list|(
name|piter
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit
