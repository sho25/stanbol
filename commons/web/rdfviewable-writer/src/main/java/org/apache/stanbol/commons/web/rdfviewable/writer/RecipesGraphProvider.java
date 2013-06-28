begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright 2013 The Apache Software Foundation.  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|web
operator|.
name|rdfviewable
operator|.
name|writer
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
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|Activate
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
name|Deactivate
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
name|osgi
operator|.
name|framework
operator|.
name|Bundle
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleEvent
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleListener
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

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|RecipesGraphProvider
operator|.
name|class
argument_list|)
specifier|public
class|class
name|RecipesGraphProvider
implements|implements
name|BundleListener
block|{
specifier|private
specifier|static
specifier|final
name|String
name|RECIPES_PATH_IN_BUNDLE
init|=
literal|"META-INF/graphs/recipes/"
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|Parser
name|parser
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
name|RecipesGraphProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|MGraph
name|recipesGraph
init|=
literal|null
decl_stmt|;
specifier|public
name|TripleCollection
name|getRecipesGraph
parameter_list|()
block|{
return|return
name|recipesGraph
return|;
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|BundleContext
name|context
parameter_list|)
block|{
comment|//Work around because of STANBOL-1130
name|recipesGraph
operator|=
operator|new
name|SimpleMGraph
argument_list|()
expr_stmt|;
comment|//new IndexedMGraph();
name|context
operator|.
name|addBundleListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
for|for
control|(
name|Bundle
name|b
range|:
name|context
operator|.
name|getBundles
argument_list|()
control|)
block|{
if|if
condition|(
name|b
operator|.
name|getState
argument_list|()
operator|==
name|Bundle
operator|.
name|ACTIVE
condition|)
block|{
name|loadRecipesData
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|BundleContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|removeBundleListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|recipesGraph
operator|=
literal|null
expr_stmt|;
block|}
specifier|private
name|void
name|loadRecipesData
parameter_list|(
name|Bundle
name|b
parameter_list|)
block|{
name|Enumeration
name|e
init|=
name|b
operator|.
name|findEntries
argument_list|(
name|RECIPES_PATH_IN_BUNDLE
argument_list|,
literal|"*"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|e
operator|!=
literal|null
condition|)
block|{
while|while
condition|(
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|URL
name|rdfResource
init|=
operator|(
name|URL
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
try|try
block|{
name|parser
operator|.
name|parse
argument_list|(
name|recipesGraph
argument_list|,
name|rdfResource
operator|.
name|openStream
argument_list|()
argument_list|,
name|guessFormat
argument_list|(
name|rdfResource
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Couldn't parse recipe data "
operator|+
name|e
operator|+
literal|" in bundle"
operator|+
name|b
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
name|String
name|guessFormat
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
specifier|final
name|String
name|path
init|=
name|url
operator|.
name|getPath
argument_list|()
decl_stmt|;
if|if
condition|(
name|path
operator|.
name|endsWith
argument_list|(
literal|"ttl"
argument_list|)
operator|||
name|path
operator|.
name|endsWith
argument_list|(
literal|"turtle"
argument_list|)
condition|)
block|{
return|return
name|SupportedFormat
operator|.
name|TURTLE
return|;
block|}
if|if
condition|(
name|path
operator|.
name|endsWith
argument_list|(
literal|"rdf"
argument_list|)
operator|||
name|path
operator|.
name|endsWith
argument_list|(
literal|"xml"
argument_list|)
condition|)
block|{
return|return
name|SupportedFormat
operator|.
name|RDF_XML
return|;
block|}
if|if
condition|(
name|path
operator|.
name|endsWith
argument_list|(
literal|"nt"
argument_list|)
operator|||
name|path
operator|.
name|endsWith
argument_list|(
literal|"n3"
argument_list|)
condition|)
block|{
return|return
name|SupportedFormat
operator|.
name|N3
return|;
block|}
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Don't know the mediatype of "
operator|+
name|path
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|bundleChanged
parameter_list|(
name|BundleEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getType
argument_list|()
operator|==
name|BundleEvent
operator|.
name|STARTED
condition|)
block|{
name|loadRecipesData
argument_list|(
name|event
operator|.
name|getBundle
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

