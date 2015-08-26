begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to you under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License. You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|jsonld
operator|.
name|clerezza
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
name|serializedform
operator|.
name|ParsingProvider
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ConfigurationPolicy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|clerezza
operator|.
name|ClerezzaTripleCallback
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|core
operator|.
name|JsonLdError
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|core
operator|.
name|JsonLdProcessor
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|utils
operator|.
name|JsonUtils
import|;
end_import

begin_comment
comment|/**  * A {@link org.apache.clerezza.rdf.core.serializedform.ParsingProvider} for   * JSON-LD (application/ld+json) based on the java-jsonld library  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|OPTIONAL
argument_list|)
annotation|@
name|Service
annotation|@
name|SupportedFormat
argument_list|(
literal|"application/ld+json"
argument_list|)
specifier|public
class|class
name|JsonLdParsingProvider
implements|implements
name|ParsingProvider
block|{
specifier|private
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|parse
parameter_list|(
name|MGraph
name|target
parameter_list|,
name|InputStream
name|serializedGraph
parameter_list|,
name|String
name|formatIdentifier
parameter_list|,
name|UriRef
name|baseUri
parameter_list|)
block|{
comment|//The callback will add parsed triples to the target MGraph
name|ClerezzaTripleCallback
name|ctc
init|=
operator|new
name|ClerezzaTripleCallback
argument_list|()
decl_stmt|;
name|ctc
operator|.
name|setMGraph
argument_list|(
name|target
argument_list|)
expr_stmt|;
name|Object
name|input
decl_stmt|;
name|int
name|startSize
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|logger
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|startSize
operator|=
name|target
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
try|try
block|{
name|input
operator|=
name|JsonUtils
operator|.
name|fromInputStream
argument_list|(
name|serializedGraph
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
name|logger
operator|.
name|error
argument_list|(
literal|"Unable to read from the parsed input stream"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|RuntimeException
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
try|try
block|{
name|JsonLdProcessor
operator|.
name|toRDF
argument_list|(
name|input
argument_list|,
name|ctc
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JsonLdError
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Unable to parse JSON-LD from the parsed input stream"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|RuntimeException
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
if|if
condition|(
name|logger
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|" - parsed {} triples in {}ms"
argument_list|,
name|target
operator|.
name|size
argument_list|()
operator|-
name|startSize
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

