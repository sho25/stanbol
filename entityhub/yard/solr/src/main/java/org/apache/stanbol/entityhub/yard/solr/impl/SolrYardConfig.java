begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|yard
operator|.
name|solr
operator|.
name|impl
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
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServer
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
name|yard
operator|.
name|AbstractYard
operator|.
name|YardConfig
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
name|yard
operator|.
name|solr
operator|.
name|provider
operator|.
name|SolrServerProvider
operator|.
name|Type
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

begin_comment
comment|/**  * Used for the configuration of a SolrYard. Especially if the SolrYard is  * not running within an OSGI context, than an instance of this class must  * be configured and than parsed to the constructor of {@link SolrYard}.<p>  * When running within an OSGI context, the configuration is provided by the  * OSGI environment. I that case this class is used as a wrapper for easy  * access to the configuration.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|SolrYardConfig
extends|extends
name|YardConfig
block|{
comment|/**      * Creates a new config with the minimal set of required properties      * @param id the ID of the Yard      * @param solrServer the base URL of the {@link SolrServer}      * @throws IllegalArgumentException if the parsed valued do not fulfil the      * requirements.      */
specifier|public
name|SolrYardConfig
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|solrServer
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|super
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|setSolrServerLocation
argument_list|(
name|solrServer
argument_list|)
expr_stmt|;
try|try
block|{
name|isValid
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
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
comment|/**      * Initialise the Yard configuration based on a parsed configuration. Usually      * used on the context of an OSGI environment in the activate method.      * @param config the configuration usually parsed within an OSGI activate      * method      * @throws ConfigurationException if the configuration is incomplete of      * some values are not valid      * @throws IllegalArgumentException if<code>null</code> is parsed as      * configuration      */
specifier|protected
name|SolrYardConfig
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
name|IllegalArgumentException
throws|,
name|ConfigurationException
block|{
name|super
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
comment|/**      * Setter for the type of the SolrServer client to by used by the SolrYard.      * Setting the type to<code>null</code> will activate the default value.      * The default is determined based on the configured {@link #getSolrServerLocation()}      * @param type The type to use      */
specifier|public
name|void
name|setSolrServerType
parameter_list|(
name|Type
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|SolrYard
operator|.
name|SOLR_SERVER_TYPE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|SolrYard
operator|.
name|SOLR_SERVER_TYPE
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|Type
name|getSolrServerType
parameter_list|()
block|{
name|Object
name|serverType
init|=
name|config
operator|.
name|get
argument_list|(
name|SolrYard
operator|.
name|SOLR_SERVER_TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|serverType
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|serverType
operator|instanceof
name|Type
condition|)
block|{
return|return
operator|(
name|Type
operator|)
name|serverType
return|;
block|}
else|else
block|{
try|try
block|{
return|return
name|Type
operator|.
name|valueOf
argument_list|(
name|serverType
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
comment|//invalid value set!
name|config
operator|.
name|remove
argument_list|(
name|SolrYard
operator|.
name|SOLR_SERVER_TYPE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|//guess type based on Server Location
name|String
name|serverLocation
init|=
name|getSolrServerLocation
argument_list|()
decl_stmt|;
comment|//TODO: maybe we need to improve this detection code.
if|if
condition|(
name|serverLocation
operator|.
name|startsWith
argument_list|(
literal|"http"
argument_list|)
condition|)
block|{
return|return
name|Type
operator|.
name|HTTP
return|;
block|}
else|else
block|{
return|return
name|Type
operator|.
name|EMBEDDED
return|;
block|}
block|}
comment|/**      * Setter for the location of the SolrServer. Might be a URL or a file.      * @param url the base URL of the SolrServer. Required, NOT NULL.      */
specifier|public
name|void
name|setSolrServerLocation
parameter_list|(
name|String
name|url
parameter_list|)
block|{
if|if
condition|(
name|url
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|SolrYard
operator|.
name|SOLR_SERVER_LOCATION
argument_list|,
name|url
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|SolrYard
operator|.
name|SOLR_SERVER_LOCATION
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the Location of the SolrServer. In case of an remote server      * this will be the base URL of the RESTful interface. In case of an      * embedded Server it is the directory containing the solr.xml or the      * directory of the core in case of a multi-core setup.      * @return the URL or path to the SolrServer      */
specifier|public
name|String
name|getSolrServerLocation
parameter_list|()
throws|throws
name|IllegalStateException
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|SolrYard
operator|.
name|SOLR_SERVER_LOCATION
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
return|return
name|value
operator|.
name|toString
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
comment|/**      * Setter for the multi yard index layout state.<p>      * The multi layout state. If data of multiple yards are stored in the same      * Solr Index, than the YardID MUST be stored within all indexed documents.      * In addition the to all queries a fq (filterQuery) must be added that      * restricts results to the current yard      */
specifier|public
name|void
name|setMultiYardIndexLayout
parameter_list|(
name|Boolean
name|multiYardIndexLayoutState
parameter_list|)
block|{
if|if
condition|(
name|multiYardIndexLayoutState
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|SolrYard
operator|.
name|MULTI_YARD_INDEX_LAYOUT
argument_list|,
name|multiYardIndexLayoutState
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|SolrYard
operator|.
name|MULTI_YARD_INDEX_LAYOUT
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the multi yard index layout state.<p>      * If data of multiple yards are stored in the same      * Solr Index, than the YardID MUST be stored within all indexed documents.      * In addition the to all queries a fq (filterQuery) must be added that      * restricts results to the current yard.<p>      * The default value is<code>false</code>      * @return the multi yard index layout state      */
specifier|public
name|boolean
name|isMultiYardIndexLayout
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|SolrYard
operator|.
name|MULTI_YARD_INDEX_LAYOUT
argument_list|)
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
name|Boolean
condition|)
block|{
return|return
operator|(
name|Boolean
operator|)
name|value
return|;
block|}
else|else
block|{
return|return
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Getter for the maximum number of boolean clauses allowed for queries      * @return The configured number of<code>null</code> if not configured or      * the configured value is not an valid Integer.      */
specifier|public
name|Integer
name|getMaxBooleanClauses
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|SolrYard
operator|.
name|MAX_BOOLEAN_CLAUSES
argument_list|)
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
return|return
operator|(
name|Integer
operator|)
name|value
return|;
block|}
else|else
block|{
try|try
block|{
return|return
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|void
name|setMaxBooleanClauses
parameter_list|(
name|Integer
name|integer
parameter_list|)
block|{
if|if
condition|(
name|integer
operator|==
literal|null
operator|||
name|integer
operator|.
name|intValue
argument_list|()
operator|<=
literal|0
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|SolrYard
operator|.
name|MAX_BOOLEAN_CLAUSES
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|SolrYard
operator|.
name|MAX_BOOLEAN_CLAUSES
argument_list|,
name|integer
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setDocumentBoostFieldName
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
if|if
condition|(
name|fieldName
operator|==
literal|null
operator|||
name|fieldName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|SolrYard
operator|.
name|DOCUMENT_BOOST_FIELD
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|SolrYard
operator|.
name|DOCUMENT_BOOST_FIELD
argument_list|,
name|fieldName
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getDocumentBoostFieldName
parameter_list|()
block|{
name|Object
name|name
init|=
name|config
operator|.
name|get
argument_list|(
name|SolrYard
operator|.
name|DOCUMENT_BOOST_FIELD
argument_list|)
decl_stmt|;
return|return
name|name
operator|==
literal|null
condition|?
literal|null
else|:
name|name
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setFieldBoosts
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Float
argument_list|>
name|fieldBoosts
parameter_list|)
block|{
if|if
condition|(
name|fieldBoosts
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|SolrYard
operator|.
name|FIELD_BOOST_MAPPINGS
argument_list|,
name|fieldBoosts
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|SolrYard
operator|.
name|FIELD_BOOST_MAPPINGS
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Float
argument_list|>
name|getFieldBoosts
parameter_list|()
block|{
name|Object
name|fieldBoosts
init|=
name|config
operator|.
name|get
argument_list|(
name|SolrYard
operator|.
name|FIELD_BOOST_MAPPINGS
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldBoosts
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|fieldBoosts
operator|instanceof
name|Map
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
condition|)
block|{
return|return
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|Float
argument_list|>
operator|)
name|fieldBoosts
return|;
block|}
else|else
block|{
comment|//TODO: add support for parsing from String[] and Collection<String>
return|return
literal|null
return|;
block|}
block|}
comment|/**      * checks for the {@link SolrYard#SOLR_SERVER_LOCATION}      */
annotation|@
name|Override
specifier|protected
name|void
name|validateConfig
parameter_list|()
throws|throws
name|ConfigurationException
block|{
try|try
block|{
name|String
name|solrServer
init|=
name|getSolrServerLocation
argument_list|()
decl_stmt|;
if|if
condition|(
name|solrServer
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SolrYard
operator|.
name|SOLR_SERVER_LOCATION
argument_list|,
literal|"The URL of the Solr server MUST NOT be NULL!"
argument_list|)
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SolrYard
operator|.
name|SOLR_SERVER_LOCATION
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

