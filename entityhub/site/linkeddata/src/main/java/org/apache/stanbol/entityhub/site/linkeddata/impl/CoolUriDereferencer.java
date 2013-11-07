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
name|entityhub
operator|.
name|site
operator|.
name|linkeddata
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
name|URLConnection
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
name|SimpleMGraph
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
name|Reference
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
name|indexedgraph
operator|.
name|IndexedMGraph
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
name|entityhub
operator|.
name|core
operator|.
name|site
operator|.
name|AbstractEntityDereferencer
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
name|entityhub
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfValueFactory
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|EntityDereferencer
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

begin_class
annotation|@
name|Component
argument_list|(
name|name
operator|=
literal|"org.apache.stanbol.entityhub.dereferencer.CoolUriDereferencer"
argument_list|,
name|factory
operator|=
literal|"org.apache.stanbol.entityhub.dereferencer.CoolUriDereferencerFactory"
argument_list|,
name|specVersion
operator|=
literal|"1.1"
argument_list|)
specifier|public
class|class
name|CoolUriDereferencer
extends|extends
name|AbstractEntityDereferencer
implements|implements
name|EntityDereferencer
block|{
annotation|@
name|Reference
specifier|private
name|Parser
name|parser
decl_stmt|;
specifier|private
specifier|final
name|RdfValueFactory
name|valueFactory
init|=
name|RdfValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|public
name|CoolUriDereferencer
parameter_list|()
block|{
name|super
argument_list|(
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|CoolUriDereferencer
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|InputStream
name|dereference
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|contentType
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|uri
operator|!=
literal|null
condition|)
block|{
specifier|final
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|uri
argument_list|)
decl_stmt|;
specifier|final
name|URLConnection
name|con
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|con
operator|.
name|addRequestProperty
argument_list|(
literal|"Accept"
argument_list|,
name|contentType
argument_list|)
expr_stmt|;
return|return
name|con
operator|.
name|getInputStream
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Representation
name|dereference
parameter_list|(
name|String
name|uri
parameter_list|)
throws|throws
name|IOException
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|String
name|format
init|=
name|SupportedFormat
operator|.
name|RDF_XML
decl_stmt|;
name|InputStream
name|in
init|=
name|dereference
argument_list|(
name|uri
argument_list|,
name|format
argument_list|)
decl_stmt|;
name|long
name|queryEnd
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> DereferenceTime: "
operator|+
operator|(
name|queryEnd
operator|-
name|start
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|in
operator|!=
literal|null
condition|)
block|{
name|MGraph
name|rdfData
init|=
operator|new
name|IndexedMGraph
argument_list|(
name|parser
operator|.
name|parse
argument_list|(
name|in
argument_list|,
name|format
argument_list|,
operator|new
name|UriRef
argument_list|(
name|getBaseUri
argument_list|()
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|long
name|parseEnd
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> ParseTime: "
operator|+
operator|(
name|parseEnd
operator|-
name|queryEnd
operator|)
argument_list|)
expr_stmt|;
return|return
name|valueFactory
operator|.
name|createRdfRepresentation
argument_list|(
operator|new
name|UriRef
argument_list|(
name|uri
argument_list|)
argument_list|,
name|rdfData
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

