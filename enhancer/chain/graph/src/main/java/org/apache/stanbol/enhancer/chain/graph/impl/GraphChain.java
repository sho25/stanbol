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
name|chain
operator|.
name|graph
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
name|commons
operator|.
name|io
operator|.
name|FilenameUtils
operator|.
name|getExtension
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
name|helper
operator|.
name|ConfigUtils
operator|.
name|getParameters
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
name|helper
operator|.
name|ConfigUtils
operator|.
name|getState
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
name|helper
operator|.
name|ConfigUtils
operator|.
name|getValue
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
name|helper
operator|.
name|ConfigUtils
operator|.
name|guessRdfFormat
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
name|helper
operator|.
name|ConfigUtils
operator|.
name|parseConfig
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
name|helper
operator|.
name|ExecutionPlanHelper
operator|.
name|createExecutionPlan
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
name|helper
operator|.
name|ExecutionPlanHelper
operator|.
name|validateExecutionPlan
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
name|helper
operator|.
name|ExecutionPlanHelper
operator|.
name|writeExecutionNode
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
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
name|Arrays
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
name|Collections
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
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
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
name|Properties
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
name|Property
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
name|ReferenceCardinality
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
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileListener
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
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileTracker
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
name|Chain
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
name|ChainException
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
name|AbstractChain
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
name|ExecutionPlan
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
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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
comment|/**  * Chain implementation that allows to configure the execution graph. Two ways  * to configure the graph are supported:<ol>  *<li> Configuration by pointing to an RDF file containing the execution graph.  * The name of the file needs to be provided by {@link #PROPERTY_GRAPH_RESOURCE}.  * The file is tracked by using the {@link DataFileListener} service. Typically  * users will need to copy this file to the /datafiles directory of the  * Stanbol Environment. However datafiles may be also provided by other means  * such as bundles or even the default configuration of the Stanbol Environment.  *<li> Configuration directly provided by the properties of a component instance.  * The {@link #PROPERTY_CHAIN_LIST} is used for this option. See the java doc for  * this property for details on the syntax. Users that want to use this option  * MUST make sure that {@link #PROPERTY_GRAPH_RESOURCE} is not present or  * empty. Otherwise the {@link #PROPERTY_CHAIN_LIST} will be ignored regardless  * if the graph resource is available or not.  *</ol>  * TODO: Maybe split this up into two chains - one for each configuration  * possibility.  *   * @author Rupert Westenthaler  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|inherit
operator|=
literal|true
argument_list|,
name|configurationFactory
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|REQUIRE
argument_list|)
annotation|@
name|Service
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Chain
operator|.
name|PROPERTY_NAME
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|GraphChain
operator|.
name|PROPERTY_GRAPH_RESOURCE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|GraphChain
operator|.
name|PROPERTY_CHAIN_LIST
argument_list|,
name|cardinality
operator|=
literal|1000
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
literal|0
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|GraphChain
extends|extends
name|AbstractChain
implements|implements
name|Chain
block|{
specifier|protected
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|GraphChain
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Property used to configure the Graph by using the line based       * representation with the following Syntax:      *<code><pre>      *&lt;engineName&gt;;&lt;parm1&gt;=&lt;value1&gt;,&lt;value2&gt;;&lt;parm2&gt;=&lt;value1&gt;...      *</pre></code>      * Note that this property is ignored in case {@link #PROPERTY_GRAPH_RESOURCE}      * is defined.      *<p>      * Example:      *<code><pre>      *   metaxa      *   langId;dependsOn=metaxa      *   ner;dependsOn=langId      *   zemanta;optional      *   dbpedia-linking;dependsOn=ner      *   geonames;optional;dependsOn=ner      *   refactor;dependsOn=geonames,dbpedia-linking,zemanta      *</pre></code>       */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_CHAIN_LIST
init|=
literal|"stanbol.enhancer.chain.graph.chainlist"
decl_stmt|;
comment|/**      * Property used to link to a resource available via the {@link DataFileProvider}      * utility. The RDF format of the file can be configured by the format parameter.      * However for well known file extensions (e.g. 'rdf','xml','json','nt')      * this is not required.<p>      * Both example will result in "application/rdf+xml" to be used as format to      * parse the configured resource:<code><pre>      *     myExecutionPlan.rdf      *     myExecutionPlan;format=application/rdf+xml      *</pre></code>      * Compressed file are not supported because execution plans should be      * reasonable small.<p>      * NOTE that if this property is present the {@link #PROPERTY_CHAIN_LIST} is       * ignored regardless if the referenced resource is available or not.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_GRAPH_RESOURCE
init|=
literal|"stanbol.enhancer.chain.graph.graphresource"
decl_stmt|;
comment|/**      * Enum with the different configuration modes      */
specifier|private
enum|enum
name|MODE
block|{
comment|/**          * Configuration provided by {@link GraphChain#PROPERTY_GRAPH_RESOURCE}          */
name|RESOURCE
block|,
comment|/**          * Configuration provided by {@link GraphChain#PROPERTY_CHAIN_LIST}          */
name|LIST
block|}
empty_stmt|;
comment|/**      * The configuration mode.      */
specifier|private
name|MODE
name|mode
decl_stmt|;
comment|/**      * The internal chain. Different implementation depending on the {@link #mode}      */
specifier|private
name|Chain
name|internalChain
init|=
literal|null
decl_stmt|;
comment|/**      * The {@link DataFileTracker}. Optional because only required in       * {@link MODE#RESOURCE}.      */
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|)
specifier|protected
name|DataFileTracker
name|tracker
decl_stmt|;
comment|/**      * The {@link Parser}. Optional because only required by      * {@link ExecutionPlanListerner}.      */
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|)
specifier|protected
name|Parser
name|parser
decl_stmt|;
annotation|@
name|Activate
annotation|@
name|Override
specifier|public
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|super
operator|.
name|activate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
name|Object
name|resource
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROPERTY_GRAPH_RESOURCE
argument_list|)
decl_stmt|;
name|Object
name|list
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROPERTY_CHAIN_LIST
argument_list|)
decl_stmt|;
if|if
condition|(
name|resource
operator|!=
literal|null
operator|&&
operator|!
name|resource
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
index|[]
name|config
init|=
name|resource
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
name|String
name|resourceName
init|=
name|config
index|[
literal|0
index|]
decl_stmt|;
name|String
name|format
init|=
name|getValue
argument_list|(
name|getParameters
argument_list|(
name|config
argument_list|,
literal|1
argument_list|)
argument_list|,
literal|"format"
argument_list|)
decl_stmt|;
if|if
condition|(
name|format
operator|==
literal|null
condition|)
block|{
name|format
operator|=
name|guessRdfFormat
argument_list|(
name|getExtension
argument_list|(
name|resourceName
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|format
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_GRAPH_RESOURCE
argument_list|,
literal|"The configured value for the 'format' parameter MUST NOT be"
operator|+
literal|"empty (configured: '"
operator|+
name|resource
operator|+
literal|"')!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|format
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_GRAPH_RESOURCE
argument_list|,
literal|"RDF formant for extension '"
operator|+
name|getExtension
argument_list|(
name|resourceName
argument_list|)
operator|+
literal|"' is not known. Please use the 'format' parameter to specify"
operator|+
literal|"it manually (configured: '"
operator|+
name|resource
operator|+
literal|"')!"
argument_list|)
throw|;
block|}
name|ExecutionPlanListerner
name|epl
init|=
operator|new
name|ExecutionPlanListerner
argument_list|(
name|resourceName
argument_list|,
name|format
argument_list|)
decl_stmt|;
if|if
condition|(
name|tracker
operator|!=
literal|null
condition|)
block|{
name|tracker
operator|.
name|add
argument_list|(
name|epl
argument_list|,
name|resourceName
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|internalChain
operator|=
name|epl
expr_stmt|;
name|mode
operator|=
name|MODE
operator|.
name|RESOURCE
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|list
operator|!=
literal|null
condition|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|configuredChain
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|list
operator|instanceof
name|String
index|[]
condition|)
block|{
name|configuredChain
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
index|[]
operator|)
name|list
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|list
operator|instanceof
name|Collection
argument_list|<
name|?
argument_list|>
condition|)
block|{
for|for
control|(
name|Object
name|o
else|:
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|list
block|)
block|{
if|if
condition|(
name|o
operator|instanceof
name|String
condition|)
block|{
name|configuredChain
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

begin_else
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_CHAIN_LIST
argument_list|,
literal|"The list based configuration of a Graph Chain MUST BE "
operator|+
literal|"configured as a Array or Collection of Strings (parsed: "
operator|+
operator|(
name|list
operator|!=
literal|null
condition|?
name|list
operator|.
name|getClass
argument_list|()
else|:
literal|"null"
operator|)
operator|+
literal|"). NOTE you can also "
operator|+
literal|"configure the Graph by pointing to a resource with the graph as"
operator|+
literal|"value of the property '"
operator|+
name|PROPERTY_GRAPH_RESOURCE
operator|+
literal|"'."
argument_list|)
throw|;
block|}
end_else

begin_decl_stmt
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|>
name|config
decl_stmt|;
end_decl_stmt

begin_try
try|try
block|{
name|config
operator|=
name|parseConfig
argument_list|(
name|configuredChain
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_CHAIN_LIST
argument_list|,
literal|"Unable to parse the execution plan configuraiton (message: '"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"')!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
end_try

begin_if
if|if
condition|(
name|config
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_CHAIN_LIST
argument_list|,
literal|"The configured execution plan MUST at least contain a single "
operator|+
literal|"valid execution node!"
argument_list|)
throw|;
block|}
end_if

begin_expr_stmt
name|internalChain
operator|=
operator|new
name|ListConfigExecutionPlan
argument_list|(
name|config
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|mode
operator|=
name|MODE
operator|.
name|LIST
expr_stmt|;
end_expr_stmt

begin_block
unit|} else
block|{
comment|//both PROPERTY_CHAIN_LIST and PROPERTY_GRAPH_RESOURCE are null
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_GRAPH_RESOURCE
argument_list|,
literal|"The Execution Plan is a required property. It MUST BE configured"
operator|+
literal|"by one of the properties :"
operator|+
name|Arrays
operator|.
name|asList
argument_list|(
name|PROPERTY_GRAPH_RESOURCE
argument_list|,
name|PROPERTY_GRAPH_RESOURCE
argument_list|)
argument_list|)
throw|;
block|}
end_block

begin_function
unit|}     @
name|Deactivate
annotation|@
name|Override
specifier|public
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
if|if
condition|(
name|mode
operator|==
name|MODE
operator|.
name|RESOURCE
operator|&&
name|tracker
operator|!=
literal|null
condition|)
block|{
comment|//we need to remove the ExecutionPlanListerner
name|tracker
operator|.
name|removeAll
argument_list|(
operator|(
name|DataFileListener
operator|)
name|internalChain
argument_list|)
expr_stmt|;
block|}
name|internalChain
operator|=
literal|null
expr_stmt|;
name|mode
operator|=
literal|null
expr_stmt|;
name|tracker
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|deactivate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
annotation|@
name|Override
specifier|public
name|Graph
name|getExecutionPlan
parameter_list|()
throws|throws
name|ChainException
block|{
return|return
name|internalChain
operator|.
name|getExecutionPlan
argument_list|()
return|;
block|}
end_function

begin_function
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getEngines
parameter_list|()
throws|throws
name|ChainException
block|{
return|return
name|internalChain
operator|.
name|getEngines
argument_list|()
return|;
block|}
end_function

begin_comment
comment|/**      * {@link GraphChain#internalChain} implementation used if the execution      * plan is provided form a RDF file provided by the {@link DataFileProvider}      * infrastructure.<p>      * This implementation requires the optional {@link GraphChain#tracker} and      * {@link GraphChain#parser} services. If they are not available the      * {@link #getExecutionPlan()} and {@link #getEngines()} methods will throw      * {@link ChainException}s.<p>      * This class implements {@link DataFileListener} to track the configured      * {@link #resourceName}.      *       * @author Rupert Westenthaler      *      */
end_comment

begin_class
specifier|private
class|class
name|ExecutionPlanListerner
implements|implements
name|DataFileListener
implements|,
name|Chain
block|{
comment|/**          * The data for the resource as retrieved from           * {@link #available(String, InputStream)}. This variable is only          * set after a call to {@link #available(String, InputStream)} up to the          * next call to {@link #updateExecutionPlan()}.          */
name|byte
index|[]
name|resourceData
decl_stmt|;
comment|/**          * The execution Plan. Use the {@link #resourceName} to sync access.<p>          * The executionPlan is parsed and validated within          * {@link #updateExecutionPlan()}          */
specifier|private
name|Graph
name|executionPlan
decl_stmt|;
comment|/**          * The referenced engine names. Use the {@link #resourceName} to sync           * access.<p>          * This variable is initialised in {@link #updateExecutionPlan()}          */
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|engineNames
decl_stmt|;
comment|/**          * Parser used to parse the RDF {@link Graph} from the {@link InputStream}          * provided to the {@link #available(String, InputStream)} method by the          * {@link DataFileTracker}.          */
specifier|private
specifier|final
name|String
name|format
decl_stmt|;
comment|/**          * The name of the resource. Used to ignore events for other resources          * within the {@link #available(String, InputStream)} and          * {@link #unavailable(String)} methods.<p>          * This final variable is also used as lock to sync access to          * {@link #resourceData},{@link #executionPlan} and {@link #engineNames}          */
specifier|private
specifier|final
name|String
name|resourceName
decl_stmt|;
comment|/**          * Constructs an instance          * @param rdfParser the parser          * @param resourceName the name of the resource          * @throws IllegalArgumentException if any of the two parameter is           *<code>null</code> or the resourceName is empty.          */
specifier|public
name|ExecutionPlanListerner
parameter_list|(
name|String
name|resourceName
parameter_list|,
name|String
name|format
parameter_list|)
block|{
if|if
condition|(
name|format
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed rdf format MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|resourceName
operator|==
literal|null
operator|||
name|resourceName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed name of the resource MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|resourceName
operator|=
name|resourceName
expr_stmt|;
name|this
operator|.
name|format
operator|=
name|format
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|available
parameter_list|(
name|String
name|resourceName
parameter_list|,
name|InputStream
name|is
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|resourceName
operator|.
name|equals
argument_list|(
name|resourceName
argument_list|)
condition|)
block|{
comment|//update ExectionPlan
synchronized|synchronized
init|(
name|resourceName
init|)
block|{
try|try
block|{
name|resourceData
operator|=
name|IOUtils
operator|.
name|toByteArray
argument_list|(
name|is
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to read data from InputStream provided"
operator|+
literal|"by the DataFileTracker for resource "
operator|+
name|resourceName
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|is
argument_list|)
expr_stmt|;
name|executionPlan
operator|=
literal|null
expr_stmt|;
name|engineNames
operator|=
literal|null
expr_stmt|;
block|}
return|return
literal|false
return|;
comment|//keep tracking
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"received Event for unexpected resource '"
operator|+
name|resourceName
operator|+
literal|"' (tracked: '"
operator|+
name|this
operator|.
name|resourceName
operator|+
literal|"') -> "
operator|+
literal|"ignored and disabled listening for this resource!"
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|unavailable
parameter_list|(
name|String
name|resource
parameter_list|)
block|{
synchronized|synchronized
init|(
name|resourceName
init|)
block|{
name|executionPlan
operator|=
literal|null
expr_stmt|;
name|engineNames
operator|=
literal|null
expr_stmt|;
name|resourceData
operator|=
literal|null
expr_stmt|;
block|}
return|return
literal|false
return|;
comment|//keep tracking
block|}
annotation|@
name|Override
specifier|public
name|Graph
name|getExecutionPlan
parameter_list|()
throws|throws
name|ChainException
block|{
synchronized|synchronized
init|(
name|resourceName
init|)
block|{
if|if
condition|(
name|executionPlan
operator|==
literal|null
condition|)
block|{
name|updateExecutionPlan
argument_list|()
expr_stmt|;
block|}
return|return
name|executionPlan
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getEngines
parameter_list|()
throws|throws
name|ChainException
block|{
synchronized|synchronized
init|(
name|resourceName
init|)
block|{
if|if
condition|(
name|engineNames
operator|==
literal|null
condition|)
block|{
name|updateExecutionPlan
argument_list|()
expr_stmt|;
block|}
return|return
name|engineNames
return|;
block|}
block|}
comment|/**          * Updates the {@link #executionPlan} and {@link #engineNames}          * based on {@link #resourceData}.<p>          * This method assumes to be called within a sync on {@link #resourceName}          * @throws ChainException if {@link #resourceData} is<code>null</code>,          * {@link GraphChain#parser} is not available, any exception during          * parsing or if the parsed RDF data are not a valid execution plan          */
specifier|private
name|void
name|updateExecutionPlan
parameter_list|()
throws|throws
name|ChainException
block|{
if|if
condition|(
name|resourceData
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ChainException
argument_list|(
literal|"The configured resource '"
operator|+
name|resourceName
operator|+
literal|"' for the execution plan is not available via the"
operator|+
literal|"DataFileProvider infrastructure"
argument_list|)
throw|;
block|}
if|if
condition|(
name|parser
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ChainException
argument_list|(
literal|"Unable to parse RDF data from resource '"
operator|+
name|resourceName
operator|+
literal|"' because the RDF parser service is not available!"
argument_list|)
throw|;
block|}
try|try
block|{
name|executionPlan
operator|=
name|parser
operator|.
name|parse
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|resourceData
argument_list|)
argument_list|,
name|format
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ChainException
argument_list|(
literal|"Unable to parse RDF from resource '"
operator|+
name|resourceName
operator|+
literal|"' using format '"
operator|+
name|format
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|resourceData
operator|=
literal|null
expr_stmt|;
comment|//we have the graph no need to keep the raw data
comment|//we need still to parse the engines and to validate the plan
name|engineNames
operator|=
name|validateExecutionPlan
argument_list|(
name|executionPlan
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|GraphChain
operator|.
name|this
operator|.
name|getName
argument_list|()
return|;
block|}
block|}
end_class

begin_comment
comment|/**      * Implementation the parsed the execution plan form a config as described      * in the java doc of {@link GraphChain#PROPERTY_CHAIN_LIST}.<p>      * An instance of this class will be set to {@link GraphChain#internalChain}      * during activation.      * @author Rupert Westenthaler      *      */
end_comment

begin_class
specifier|private
class|class
name|ListConfigExecutionPlan
implements|implements
name|Chain
block|{
specifier|private
specifier|final
name|Graph
name|executionPlan
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|engines
decl_stmt|;
comment|/**          * Parses the execution plan form the configuration.          * @param config          */
specifier|private
name|ListConfigExecutionPlan
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|>
name|config
parameter_list|)
block|{
if|if
condition|(
name|config
operator|==
literal|null
operator|||
name|config
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed execution plan "
operator|+
literal|"confiuguration MUST NOT be NULL nor empty"
argument_list|)
throw|;
block|}
if|if
condition|(
name|config
operator|.
name|remove
argument_list|(
literal|null
argument_list|)
operator|!=
literal|null
operator|||
name|config
operator|.
name|remove
argument_list|(
literal|""
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"ExecutionNode configurations with NULL or an empty "
operator|+
literal|"engine name where removed form the configuration of the"
operator|+
literal|"GraphChain '{}'"
argument_list|,
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|engines
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|config
operator|.
name|keySet
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|MGraph
name|graph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|NonLiteral
name|epNode
init|=
name|createExecutionPlan
argument_list|(
name|graph
argument_list|,
name|getName
argument_list|()
argument_list|)
decl_stmt|;
comment|//caches the String name -> {NonLiteral node, List<Stirng> dependsOn} mappings
name|Map
argument_list|<
name|String
argument_list|,
name|Object
index|[]
argument_list|>
name|name2nodes
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
comment|//1. write the nodes (without dependencies)
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|>
name|node
range|:
name|config
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|name2nodes
operator|.
name|put
argument_list|(
name|node
operator|.
name|getKey
argument_list|()
argument_list|,
operator|new
name|Object
index|[]
block|{
name|writeExecutionNode
argument_list|(
name|graph
argument_list|,
name|epNode
argument_list|,
name|node
operator|.
name|getKey
argument_list|()
argument_list|,
name|getState
argument_list|(
name|node
operator|.
name|getValue
argument_list|()
argument_list|,
literal|"optional"
argument_list|)
argument_list|,
literal|null
argument_list|)
block|,
name|node
operator|.
name|getValue
argument_list|()
operator|.
name|get
argument_list|(
literal|"dependsOn"
argument_list|)
block|}
argument_list|)
expr_stmt|;
comment|//dependsOn
block|}
comment|//2. write the dependencies
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|Object
index|[]
argument_list|>
name|info
range|:
name|name2nodes
operator|.
name|entrySet
argument_list|()
control|)
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|List
argument_list|<
name|String
argument_list|>
name|dependsOn
init|=
operator|(
name|List
argument_list|<
name|String
argument_list|>
operator|)
name|info
operator|.
name|getValue
argument_list|()
index|[
literal|1
index|]
decl_stmt|;
if|if
condition|(
name|dependsOn
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|target
range|:
name|dependsOn
control|)
block|{
name|Object
index|[]
name|targetInfo
init|=
name|name2nodes
operator|.
name|get
argument_list|(
name|target
argument_list|)
decl_stmt|;
if|if
condition|(
name|targetInfo
operator|!=
literal|null
condition|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
operator|(
name|NonLiteral
operator|)
name|info
operator|.
name|getValue
argument_list|()
index|[
literal|0
index|]
argument_list|,
name|ExecutionPlan
operator|.
name|DEPENDS_ON
argument_list|,
operator|(
name|NonLiteral
operator|)
name|targetInfo
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//reference to a undefined engine :(
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The Engine '"
operator|+
name|info
operator|.
name|getKey
argument_list|()
operator|+
literal|"' defines a ex:dependOn to Engine '"
operator|+
name|target
operator|+
literal|"' that is not define in the configuration"
operator|+
literal|"(defined Engines: "
operator|+
name|engines
operator|+
literal|")!"
argument_list|)
throw|;
block|}
block|}
block|}
comment|//this node has no dependencies
block|}
name|this
operator|.
name|executionPlan
operator|=
name|graph
operator|.
name|getGraph
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Graph
name|getExecutionPlan
parameter_list|()
throws|throws
name|ChainException
block|{
return|return
name|executionPlan
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getEngines
parameter_list|()
throws|throws
name|ChainException
block|{
return|return
name|engines
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|GraphChain
operator|.
name|this
operator|.
name|getName
argument_list|()
return|;
block|}
block|}
end_class

unit|}
end_unit

