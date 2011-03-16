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
name|core
operator|.
name|yard
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|stanbol
operator|.
name|entityhub
operator|.
name|core
operator|.
name|query
operator|.
name|DefaultQueryFactory
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
name|utils
operator|.
name|ModelUtils
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
name|model
operator|.
name|ValueFactory
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
name|query
operator|.
name|FieldQueryFactory
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
name|yard
operator|.
name|Yard
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
name|yard
operator|.
name|YardException
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

begin_class
annotation|@
name|Component
argument_list|(
name|componentAbstract
operator|=
literal|true
argument_list|)
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
name|Yard
operator|.
name|ID
argument_list|,
name|value
operator|=
literal|"entityhubYard"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Yard
operator|.
name|NAME
argument_list|,
name|value
operator|=
literal|"Entityhub Yard"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Yard
operator|.
name|DESCRIPTION
argument_list|,
name|value
operator|=
literal|"Default values for configuring the Yard used by the Entityhub without editing"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|AbstractYard
operator|.
name|DEFAULT_QUERY_RESULT_NUMBER
argument_list|,
name|intValue
operator|=
operator|-
literal|1
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|AbstractYard
operator|.
name|MAX_QUERY_RESULT_NUMBER
argument_list|,
name|intValue
operator|=
operator|-
literal|1
argument_list|)
block|}
argument_list|)
specifier|public
specifier|abstract
class|class
name|AbstractYard
implements|implements
name|Yard
block|{
comment|/**      * Key used to configure maximum number of query results supported by      * this yard.<br>      * The default (if not set) is set to {@link #SolrQueryFactoy#MAX_QUERY_RESULT_NUMBER}      * ({@value #SolrQueryFactoy#MAX_QUERY_RESULT_NUMBER})      */
specifier|public
specifier|static
specifier|final
name|String
name|MAX_QUERY_RESULT_NUMBER
init|=
literal|"org.apache.stanbol.entityhub.yard.maxQueryResultNumber"
decl_stmt|;
comment|/**      * Key used to configure the default number of query results supported by      * this yard.<br>      * The default (if not set) is set to {@link #SolrQueryFactoy#DEFAULT_QUERY_RESULT_NUMBER}      * ({@value #SolrQueryFactoy#DEFAULT_QUERY_RESULT_NUMBER})      */
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_QUERY_RESULT_NUMBER
init|=
literal|"org.apache.stanbol.entityhub.yard.defaultQueryResultNumber"
decl_stmt|;
comment|/**      * This Yard uses the default in-memory implementation of the Entityhub model.      */
specifier|private
name|ValueFactory
name|valueFactory
decl_stmt|;
comment|/**      * The QueryFactory as required by {@link Yard#getQueryFactory()}. This      * Yard uses the default implementation as provided by the      * {@link DefaultQueryFactory}.      */
specifier|private
name|FieldQueryFactory
name|queryFactory
decl_stmt|;
comment|/**      * Holds the configuration of the Yard.      */
specifier|private
name|YardConfig
name|config
decl_stmt|;
comment|/**      * The default prefix used for created URIs.      * @see #getUriPrefix()      */
specifier|private
name|String
name|defaultPrefix
decl_stmt|;
comment|/**      * Default constructor to create an uninitialised Yard. Typically used      * within an OSGI environment      */
specifier|protected
name|AbstractYard
parameter_list|()
block|{}
comment|//    /**
comment|//     * Constructor to create an initialised Yard.
comment|//     * @param valueFactory The value factory for the yard
comment|//     * @param queryFactory The query factory for the yard
comment|//     * @param config The configuration of the yard
comment|//     * @throws IllegalArgumentException if any of the three parameter is<code>null</code>
comment|//     */
comment|//    protected AbstractYard(ValueFactory valueFactory,FieldQueryFactory queryFactory, YardConfig config) throws IllegalArgumentException{
comment|//        activate(valueFactory, queryFactory, config);
comment|//    }
comment|/**      * Activates the Yard based on the parsed parameter. Typically called within      * an OSGI environment by the activate method. Internally called by the      * {@link #AbstractYard(ValueFactory, FieldQueryFactory, YardConfig)}      * constructor.      * @param valueFactory The value factory for the yard      * @param queryFactory The query factory for the yard      * @param config The configuration of the yard      */
specifier|protected
specifier|final
name|void
name|activate
parameter_list|(
name|ValueFactory
name|valueFactory
parameter_list|,
name|FieldQueryFactory
name|queryFactory
parameter_list|,
name|YardConfig
name|config
parameter_list|)
block|{
if|if
condition|(
name|valueFactory
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to activate: The ValueFactory MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|queryFactory
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to activate: The QueryFactory MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to activate: The YardConfig MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|queryFactory
operator|=
name|queryFactory
expr_stmt|;
name|this
operator|.
name|valueFactory
operator|=
name|valueFactory
expr_stmt|;
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
name|this
operator|.
name|defaultPrefix
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"urn:org.apache.stanbol:entityhub.yard.%s:%s."
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|config
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Deactivates this yard instance. Typically called within an OSGI environment      * by the deacivate method.      *      */
specifier|protected
specifier|final
name|void
name|deactivate
parameter_list|()
block|{
name|this
operator|.
name|queryFactory
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|valueFactory
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|config
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|defaultPrefix
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Creates a new representation with a random uuid by using the pattern:      *<code><pre>      *   urn:org.apache.stanbol:entityhub.yard.&lt;getClass().getSimpleName()&gt;:&lt;getId()&gt;.&lt;uuid&gt;      *</pre></code>      * @see Yard#create()      */
annotation|@
name|Override
specifier|public
specifier|final
name|Representation
name|create
parameter_list|()
throws|throws
name|IllegalArgumentException
throws|,
name|YardException
block|{
return|return
name|create
argument_list|(
literal|null
argument_list|)
return|;
block|}
comment|/**      * Creates a representation with the parsed ID. If<code>null</code> is      * parsed a random UUID is generated as describe in {@link #create()}.<p>      * Note that {@link #store(Representation)} is called for the newly created      * representation and the Representation returned by this Method is returned.      * @param id The id or<code>null</code> to create a random uuid.      * @return The newly created, empty and stored representation      * @see Yard#create(String)      * @see Yard#store(Representation)      */
annotation|@
name|Override
specifier|public
specifier|final
name|Representation
name|create
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|YardException
block|{
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"This Yard is not activated"
argument_list|)
throw|;
block|}
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
comment|//create a new ID
do|do
block|{
name|id
operator|=
name|createRandomEntityUri
argument_list|()
expr_stmt|;
block|}
do|while
condition|(
name|isRepresentation
argument_list|(
name|id
argument_list|)
condition|)
do|;
block|}
elseif|else
if|if
condition|(
name|isRepresentation
argument_list|(
name|id
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"An representation with the parsed ID %s is already present in this Yard"
argument_list|,
name|id
argument_list|)
argument_list|)
throw|;
block|}
return|return
name|store
argument_list|(
name|valueFactory
operator|.
name|createRepresentation
argument_list|(
name|id
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getDescription
parameter_list|()
block|{
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"This Yard is not activated"
argument_list|)
throw|;
block|}
return|return
name|config
operator|.
name|getDescription
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getId
parameter_list|()
block|{
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"This Yard is not activated"
argument_list|)
throw|;
block|}
return|return
name|config
operator|.
name|getId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getName
parameter_list|()
block|{
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"This Yard is not activated"
argument_list|)
throw|;
block|}
return|return
name|config
operator|.
name|getName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|FieldQueryFactory
name|getQueryFactory
parameter_list|()
block|{
if|if
condition|(
name|queryFactory
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"This Yard is not activated"
argument_list|)
throw|;
block|}
return|return
name|queryFactory
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|ValueFactory
name|getValueFactory
parameter_list|()
block|{
if|if
condition|(
name|valueFactory
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"This Yard is not activated"
argument_list|)
throw|;
block|}
return|return
name|valueFactory
return|;
block|}
comment|/**      * This provides the prefix for URIs created by this Yard. This is used for      * creating new unique URIs for Representation if {@link #create()} is      * called.<p>      * By default this implementation uses:<br>      *<code>"urn:org.apache.stanbol:entityhub.yard."+this.getClass.getSimpleName()+":"+getId()+"."</code>      *<p>      * Subclasses can override this Method to use a different namespace for entities.      * @return The UriPrefix used by this Yard instance for creating URIs      */
specifier|protected
name|String
name|getUriPrefix
parameter_list|()
block|{
return|return
name|defaultPrefix
return|;
block|}
specifier|protected
specifier|final
name|YardConfig
name|getConfig
parameter_list|()
block|{
return|return
name|config
return|;
block|}
specifier|protected
specifier|final
name|void
name|setConfig
parameter_list|(
name|YardConfig
name|config
parameter_list|)
block|{
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
block|}
comment|/**      * Creates an unique ID by using the {@link #getUriPrefix()} the parsed      * separator (non if<code>null</code>) and an uuid created by using       * {@link ModelUtils#randomUUID()}.      *<p>      * This Method is used for the {@link #create()} and the {@link #create(String)}      * - if<code>null</code> is parsed - to generate an unique URI for the      * created Representation.      *<p>      * Subclasses can override this Method to use other algorithms for generating      * URIs for entities.      * @return the created URI as string.      */
specifier|protected
specifier|final
name|String
name|createRandomEntityUri
parameter_list|()
block|{
return|return
name|getUriPrefix
argument_list|()
operator|+
name|ModelUtils
operator|.
name|randomUUID
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** ------------------------------------------------------------------------      *    Methods that need to be implemented by Sub-Classes      *  ------------------------------------------------------------------------      */
comment|//    @Override
comment|//    public abstract QueryResultList<Representation> find(FieldQuery query);
comment|//    @Override
comment|//    public abstract QueryResultList<String> findReferences(FieldQuery query);
comment|//    @Override
comment|//    public abstract QueryResultList<Representation> findRepresentation(FieldQuery query);
comment|//    @Override
comment|//    public abstract Representation getRepresentation(String id);
comment|//    @Override
comment|//    public abstract boolean isRepresentation(String id);
comment|//    @Override
comment|//    public abstract void remove(String id) throws IllegalArgumentException;
comment|//    @Override
comment|//    public abstract void store(Representation representation) throws IllegalArgumentException;
comment|//    @Override
comment|//    public abstract void update(Representation represnetation) throws IllegalArgumentException;
specifier|public
specifier|static
specifier|abstract
class|class
name|YardConfig
block|{
specifier|protected
specifier|final
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
decl_stmt|;
comment|/**          * Creates a new config with the minimal set of required properties          * @param id the ID of the Yard          * @throws IllegalArgumentException if the parsed valued do not fulfil the          * requirements.          */
specifier|protected
name|YardConfig
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|this
operator|.
name|config
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
name|setId
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
comment|/**          * Initialise the Yard configuration based on a parsed configuration. Usually          * used on the context of an OSGI environment in the activate method.          * @param config the configuration usually parsed within an OSGI activate          * method          * @throws ConfigurationException if the configuration is incomplete of          * some values are not valid          * @throws IllegalArgumentException if<code>null</code> is parsed as          * configuration          */
specifier|protected
name|YardConfig
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
throws|throws
name|ConfigurationException
throws|,
name|IllegalArgumentException
block|{
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed configuration MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
name|isValid
argument_list|()
expr_stmt|;
block|}
comment|/**          * Setter for the ID of the yard. The id is usually a sort name such as          * "dbPedia", "freebase", "geonames.org", "my.projects" ...<p>          * If {@link #isMultiYardIndexLayout()} than this ID is used to identify          * Representations of this Yard within the SolrIndex.          * @param the id of the yard. Required, not null, not empty!          */
specifier|public
specifier|final
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|Yard
operator|.
name|ID
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|Yard
operator|.
name|ID
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**          * Getter for the ID of the yard          * @return the id of the yard          */
specifier|public
specifier|final
name|String
name|getId
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|Yard
operator|.
name|ID
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
condition|?
literal|null
else|:
name|value
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**          * Setter for the name of this yard. If not set the {@link #getId(String)}          * is used as default          * @param name The name or<code>null</code> to use {@link #getId()}.          */
specifier|public
specifier|final
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|name
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|Yard
operator|.
name|NAME
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|Yard
operator|.
name|NAME
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**          * Getter for the human readable name of the Yard          * @return the name          */
specifier|public
specifier|final
name|String
name|getName
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|Yard
operator|.
name|NAME
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
condition|?
name|getId
argument_list|()
else|:
name|value
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**          * Setter for the description of this Yard          * @param description the description. Optional parameter          */
specifier|public
specifier|final
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
if|if
condition|(
name|description
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|Yard
operator|.
name|DESCRIPTION
argument_list|,
name|description
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|Yard
operator|.
name|DESCRIPTION
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**          * Getter for the description          * @return description The description or<code>null</code> if not defined          */
specifier|public
specifier|final
name|String
name|getDescription
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|Yard
operator|.
name|DESCRIPTION
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
condition|?
literal|null
else|:
name|value
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**          * Setter for the default number of query results. This is used if parsed          * queries do not define a limit for the maximum number of results.          * @param defaultQueryResults the default number of query results.          *<code>null</code> or a negative number to use the default value defined          * by the Yard.          */
specifier|public
specifier|final
name|void
name|setDefaultQueryResultNumber
parameter_list|(
name|Integer
name|defaultQueryResults
parameter_list|)
block|{
if|if
condition|(
name|defaultQueryResults
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|DEFAULT_QUERY_RESULT_NUMBER
argument_list|,
name|defaultQueryResults
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|DEFAULT_QUERY_RESULT_NUMBER
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**          * Getter for the default number of query results. This is used if parsed          * queries do not define a limit for the maximum number of results.<p>          * If {@link #getMaxQueryResultNumber()} is defines (>0), than this          * method returns the minimum of the two configured values.          * @return the default number used as the maximum number of results per          * query if not otherwise set by the parsed query. Returns<code>0</code>          * if the value was set to a number lower or equals 0 and -1 if the          * value is not configured at all.          */
specifier|public
specifier|final
name|int
name|getDefaultQueryResultNumber
parameter_list|()
throws|throws
name|NumberFormatException
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|DEFAULT_QUERY_RESULT_NUMBER
argument_list|)
decl_stmt|;
name|Integer
name|number
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|Integer
condition|)
block|{
name|number
operator|=
operator|(
name|Integer
operator|)
name|value
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|number
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
block|}
else|else
block|{
return|return
operator|-
literal|1
return|;
block|}
if|if
condition|(
name|number
operator|.
name|intValue
argument_list|()
operator|<=
literal|0
condition|)
block|{
return|return
literal|0
return|;
block|}
else|else
block|{
if|if
condition|(
name|getMaxQueryResultNumber
argument_list|()
operator|>
literal|0
condition|)
block|{
return|return
name|Math
operator|.
name|min
argument_list|(
name|getMaxQueryResultNumber
argument_list|()
argument_list|,
name|number
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|number
return|;
block|}
block|}
block|}
comment|/**          * Setter for the maximum number of query results. This is used to limit the          * maximum number of results when parsed queries define limits that are          * greater this value.          * @param maxQueryResults The maximum number of results for queries.          *<code>null</code> or a negative number to use the default as defined by          * the Yard implementation.          */
specifier|public
specifier|final
name|void
name|setMaxQueryResultNumber
parameter_list|(
name|Integer
name|maxQueryResults
parameter_list|)
block|{
if|if
condition|(
name|maxQueryResults
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|MAX_QUERY_RESULT_NUMBER
argument_list|,
name|maxQueryResults
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|MAX_QUERY_RESULT_NUMBER
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**          * Getter for the maximum number of query results.This is used to limit the          * maximum number of results when parsed queries define limits that are          * greater this value.          * @return the maximum number of query results. Returns<code>0</code>          * if the value was set to a number lower or equals 0 and -1 if the          * value is not configured at all.          */
specifier|public
specifier|final
name|int
name|getMaxQueryResultNumber
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|MAX_QUERY_RESULT_NUMBER
argument_list|)
decl_stmt|;
name|Integer
name|number
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|Integer
condition|)
block|{
name|number
operator|=
operator|(
name|Integer
operator|)
name|value
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|number
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
block|}
else|else
block|{
return|return
operator|-
literal|1
return|;
block|}
if|if
condition|(
name|number
operator|.
name|intValue
argument_list|()
operator|<=
literal|0
condition|)
block|{
return|return
literal|0
return|;
block|}
else|else
block|{
return|return
name|number
return|;
block|}
block|}
comment|/**          * Checks if the configuration is valid and throws a {@link ConfigurationException}          * if not.<p>          * This method checks the {@link Yard#ID} property and than calls          * {@link #validateConfig()} to check additional constraints of specific          * Yard configurations (subclasses of this class)          * @return returns true if valid          * @throws ConfigurationException          */
specifier|protected
specifier|final
name|boolean
name|isValid
parameter_list|()
throws|throws
name|ConfigurationException
block|{
name|String
name|id
init|=
name|getId
argument_list|()
decl_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
operator|||
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|Yard
operator|.
name|ID
argument_list|,
literal|"The ID of the Yard MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|validateConfig
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|/**          * Needs to be implemented by Subclasses to check required configurations of          * specific Yard Implementations.          * @throws ConfigurationException In case of a missing or invalid configuration          * for one of the properties.          */
specifier|protected
specifier|abstract
name|void
name|validateConfig
parameter_list|()
throws|throws
name|ConfigurationException
function_decl|;
block|}
block|}
end_class

end_unit

