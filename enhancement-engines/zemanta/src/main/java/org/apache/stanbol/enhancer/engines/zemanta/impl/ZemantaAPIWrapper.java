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
name|zemanta
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataOutputStream
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
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpURLConnection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|EnumMap
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
name|ImmutableGraph
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
name|Graph
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
name|impl
operator|.
name|utils
operator|.
name|simple
operator|.
name|SimpleGraph
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
name|SupportedFormat
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
name|jena
operator|.
name|parser
operator|.
name|JenaParserProvider
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
comment|/**  * This class wraps the Zemanta API into one method.  * Zemanta is able to return RDF-XML so parsing the response into  * a ImmutableGraph object is simple.  *  * @author michaelmarth  * @author westei (Rupert Westenthaler)  */
end_comment

begin_class
specifier|public
class|class
name|ZemantaAPIWrapper
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ZemantaEnhancementEngine
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|String
name|apiKey
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|URL
init|=
literal|"http://api.zemanta.com/services/rest/0.0/"
decl_stmt|;
specifier|public
name|ZemantaAPIWrapper
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|apiKey
operator|=
name|key
expr_stmt|;
block|}
specifier|public
name|ImmutableGraph
name|enhance
parameter_list|(
name|String
name|textToAnalyze
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStream
name|is
init|=
name|sendRequest
argument_list|(
name|textToAnalyze
argument_list|)
decl_stmt|;
name|ImmutableGraph
name|zemantaResponseGraph
init|=
name|parseResponse
argument_list|(
name|is
argument_list|)
decl_stmt|;
return|return
name|zemantaResponseGraph
return|;
block|}
specifier|private
name|InputStream
name|sendRequest
parameter_list|(
name|String
name|textToAnalyze
parameter_list|)
throws|throws
name|IOException
block|{
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|URL
argument_list|)
decl_stmt|;
name|HttpURLConnection
name|httpURLConnection
init|=
operator|(
name|HttpURLConnection
operator|)
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|httpURLConnection
operator|.
name|setRequestMethod
argument_list|(
literal|"POST"
argument_list|)
expr_stmt|;
name|httpURLConnection
operator|.
name|setDoOutput
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|ZemantaPropertyEnum
argument_list|,
name|String
argument_list|>
name|requestProperties
init|=
operator|new
name|EnumMap
argument_list|<
name|ZemantaPropertyEnum
argument_list|,
name|String
argument_list|>
argument_list|(
name|ZemantaPropertyEnum
operator|.
name|class
argument_list|)
decl_stmt|;
name|requestProperties
operator|.
name|put
argument_list|(
name|ZemantaPropertyEnum
operator|.
name|api_key
argument_list|,
name|apiKey
argument_list|)
expr_stmt|;
name|requestProperties
operator|.
name|put
argument_list|(
name|ZemantaPropertyEnum
operator|.
name|text
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|textToAnalyze
argument_list|,
literal|"UTF8"
argument_list|)
argument_list|)
expr_stmt|;
comment|//added an method that adds the default parameters
name|StringBuilder
name|data
init|=
name|getRequestData
argument_list|(
name|requestProperties
argument_list|)
decl_stmt|;
comment|//            StringBuilder data = new StringBuilder();
comment|//            data.append("method=zemanta.suggest&").append("api_key=").append(
comment|//                    apiKey).append("&").append("format=rdfxml&").append(
comment|//                    "freebase=1").append("&text=").append(
comment|//                    URLEncoder.encode(textToAnalyze, "UTF8"));
name|httpURLConnection
operator|.
name|addRequestProperty
argument_list|(
literal|"Content-Length"
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|data
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"sending data to Zemanta: "
operator|+
name|data
argument_list|)
expr_stmt|;
name|DataOutputStream
name|dataOutputStream
init|=
operator|new
name|DataOutputStream
argument_list|(
name|httpURLConnection
operator|.
name|getOutputStream
argument_list|()
argument_list|)
decl_stmt|;
name|dataOutputStream
operator|.
name|write
argument_list|(
name|data
operator|.
name|toString
argument_list|()
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
name|dataOutputStream
operator|.
name|close
argument_list|()
expr_stmt|;
name|InputStream
name|is
init|=
name|httpURLConnection
operator|.
name|getInputStream
argument_list|()
decl_stmt|;
return|return
name|is
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|private
name|StringBuilder
name|getRequestData
parameter_list|(
name|Map
argument_list|<
name|ZemantaPropertyEnum
argument_list|,
name|String
argument_list|>
name|requestProperties
parameter_list|)
block|{
name|StringBuilder
name|data
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|ZemantaPropertyEnum
name|property
range|:
name|ZemantaPropertyEnum
operator|.
name|values
argument_list|()
control|)
block|{
name|String
name|value
init|=
name|requestProperties
operator|.
name|get
argument_list|(
name|property
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|&&
name|property
operator|.
name|hasDefault
argument_list|()
condition|)
block|{
name|value
operator|=
name|property
operator|.
name|defaultValue
argument_list|()
expr_stmt|;
block|}
comment|//NOTE: value == null may still be OK
if|if
condition|(
name|property
operator|.
name|allowedValue
argument_list|(
name|value
argument_list|)
condition|)
block|{
comment|//NOTE: also this may still say that NULL is OK
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|first
condition|)
block|{
name|data
operator|.
name|append
argument_list|(
literal|'&'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
name|data
operator|.
name|append
argument_list|(
name|property
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|data
operator|.
name|append
argument_list|(
literal|'='
argument_list|)
expr_stmt|;
name|data
operator|.
name|append
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
comment|//else property is not present
block|}
else|else
block|{
comment|//Illegal parameter
name|log
operator|.
name|warn
argument_list|(
literal|"Value "
operator|+
name|value
operator|+
literal|" is not valied for property "
operator|+
name|property
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|data
return|;
block|}
specifier|private
name|ImmutableGraph
name|parseResponse
parameter_list|(
name|InputStream
name|is
parameter_list|)
block|{
name|JenaParserProvider
name|jenaParserProvider
init|=
operator|new
name|JenaParserProvider
argument_list|()
decl_stmt|;
comment|//NOTE(rw): the new third parameter is the base URI used to resolve relative paths
name|Graph
name|g
init|=
operator|new
name|SimpleGraph
argument_list|()
decl_stmt|;
name|jenaParserProvider
operator|.
name|parse
argument_list|(
name|g
argument_list|,
name|is
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"graph: "
operator|+
name|g
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|g
operator|.
name|getImmutableGraph
argument_list|()
return|;
block|}
block|}
end_class

end_unit

