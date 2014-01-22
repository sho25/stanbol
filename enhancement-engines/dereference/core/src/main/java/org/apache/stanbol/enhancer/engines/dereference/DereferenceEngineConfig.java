begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|dereference
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
name|engines
operator|.
name|dereference
operator|.
name|DereferenceConstants
operator|.
name|DEREFERENCE_ENTITIES_FIELDS
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
name|engines
operator|.
name|dereference
operator|.
name|DereferenceConstants
operator|.
name|DEREFERENCE_ENTITIES_LDPATH
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
name|java
operator|.
name|util
operator|.
name|List
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
name|lang
operator|.
name|StringUtils
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
name|EnhancementEngine
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

begin_class
specifier|public
class|class
name|DereferenceEngineConfig
implements|implements
name|DereferenceConstants
block|{
specifier|private
specifier|final
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
decl_stmt|;
comment|/**      * Creates a DereferenceEngine configuration based on a Dictionary. Typically      * the dictionary will contain keys as defined by {@link DereferenceConstants}      * and {@link EnhancementEngine}      * @param config the config - typically as parsed in the activate method of      * an OSGI component.      */
specifier|public
name|DereferenceEngineConfig
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
block|{
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
name|validateRequired
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructor that parses the config (ATM only used by unit tests)      * @param name      * @param filterContentLang      * @param filterAcceptLang      * @throws ConfigurationException      */
specifier|protected
name|DereferenceEngineConfig
parameter_list|(
name|String
name|name
parameter_list|,
name|boolean
name|filterContentLang
parameter_list|,
name|boolean
name|filterAcceptLang
parameter_list|)
throws|throws
name|ConfigurationException
block|{
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
name|config
operator|.
name|put
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
name|name
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|FILTER_CONTENT_LANGUAGES
argument_list|,
name|filterContentLang
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|FILTER_ACCEPT_LANGUAGES
argument_list|,
name|filterAcceptLang
argument_list|)
expr_stmt|;
name|validateRequired
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
comment|/**      * If filtering for non content language literals is active      * @return the {@link DereferenceConstants#FILTER_CONTENT_LANGUAGES} state      */
specifier|public
name|boolean
name|isFilterContentLanguages
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|FILTER_CONTENT_LANGUAGES
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
condition|?
name|DEFAULT_FILTER_CONTENT_LANGUAGES
else|:
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
comment|/**      * if filtering for non accept language literals is active      * @return the {@link DereferenceConstants#FILTER_ACCEPT_LANGUAGES} state      */
specifier|public
name|boolean
name|isFilterAcceptLanguages
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|FILTER_ACCEPT_LANGUAGES
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
condition|?
name|DEFAULT_FILTER_ACCEPT_LANGUAGES
else|:
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
specifier|private
name|void
name|validateRequired
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
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|StringUtils
operator|.
name|isBlank
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
literal|"The EnhancementEngine name MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|value
operator|=
name|config
operator|.
name|get
argument_list|(
name|DEREFERENCE_ENTITIES_FIELDS
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
operator|(
name|value
operator|instanceof
name|String
operator|||
name|value
operator|instanceof
name|String
index|[]
operator|||
name|value
operator|instanceof
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|DEREFERENCE_ENTITIES_FIELDS
argument_list|,
literal|"Dereference Entities Fields MUST BE parsed as String[], Collection<String> or "
operator|+
literal|"String (single value). The actual value '"
operator|+
name|value
operator|+
literal|"'(type: '"
operator|+
name|value
operator|.
name|getClass
argument_list|()
operator|+
literal|"') is NOT supported"
argument_list|)
throw|;
block|}
name|value
operator|=
name|config
operator|.
name|get
argument_list|(
name|DEREFERENCE_ENTITIES_LDPATH
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
operator|(
name|value
operator|instanceof
name|String
operator|||
name|value
operator|instanceof
name|String
index|[]
operator|||
name|value
operator|instanceof
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|DEREFERENCE_ENTITIES_LDPATH
argument_list|,
literal|"Dereference LDPath statements MUST BE parsed as String, String[] or "
operator|+
literal|"Collection<String>. The actual value '"
operator|+
name|value
operator|+
literal|"'(type: '"
operator|+
name|value
operator|.
name|getClass
argument_list|()
operator|+
literal|"') is NOT supported"
argument_list|)
throw|;
block|}
block|}
comment|/**      * Getter for the name of the EnhancementEngine      * @return the configured {@link EnhancementEngine#PROPERTY_NAME}      */
specifier|public
name|String
name|getEngineName
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
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
comment|/**      * The Integer service ranking for the engine      * @return the configured {@link Constants#SERVICE_RANKING}      */
specifier|public
name|Integer
name|getServiceRanking
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|)
decl_stmt|;
return|return
name|value
operator|instanceof
name|Integer
condition|?
operator|(
name|Integer
operator|)
name|value
else|:
name|value
operator|instanceof
name|Number
condition|?
operator|(
operator|(
name|Number
operator|)
name|value
operator|)
operator|.
name|intValue
argument_list|()
else|:
name|value
operator|!=
literal|null
condition|?
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
else|:
literal|null
return|;
block|}
comment|/**      * Parsed the {@link DereferenceConstants#DEREFERENCE_ENTITIES_FIELDS}      * config from the parsed Dictionary regardless if it is defined as       *<code>String[]</code>,<code>Collection&lt;String&gt;</code> or      *<code>String</code> (single value).<p>      * This returns the fields as parsed by the configuration.<p>      *<b>NOTE:</b> This does not check/convert<code>{prefix}:{localname}</code>      * configurations to URIs. The receiver of the list is responsible for      * that       * @return the {@link List} with the unprocessed dereference fields as list      */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getDereferenceFields
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|DEREFERENCE_ENTITIES_FIELDS
argument_list|)
decl_stmt|;
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|fields
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
index|[]
condition|)
block|{
name|fields
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
index|[]
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|Collection
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|fields
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
operator|(
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
operator|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
name|field
operator|:
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
control|)
block|{
if|if
condition|(
name|field
operator|==
literal|null
condition|)
block|{
name|fields
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fields
operator|.
name|add
argument_list|(
name|field
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|fields
operator|=
name|Collections
operator|.
name|singletonList
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//value == null or of unsupported type
name|fields
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
return|return
name|fields
return|;
block|}
end_class

begin_comment
comment|/**      * Parses the LdPath program from the value of the       * {@link DereferenceConstants#DEREFERENCE_ENTITIES_LDPATH} property.<p>      * This supports<code>String</code> (the program as a single String),       *<code>String[]</code> and<code>Collection&lt;String&gt;</code> (one      * statement per line).<p>      *<b>NOTE:</b> This does not parse the LDPath program as this can only be      * done by the LdPath repository used by the dereferencer.      * @return the unparsed LDPath program as String       */
end_comment

begin_function
specifier|public
name|String
name|getLdPathProgram
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|DEREFERENCE_ENTITIES_LDPATH
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
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
name|value
operator|instanceof
name|String
condition|)
block|{
return|return
name|StringUtils
operator|.
name|isBlank
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
condition|?
literal|null
else|:
operator|(
name|String
operator|)
name|value
return|;
block|}
name|StringBuilder
name|sb
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
if|if
condition|(
name|value
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
name|line
operator|:
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
control|)
block|{
if|if
condition|(
name|line
operator|!=
literal|null
operator|&&
operator|!
name|StringUtils
operator|.
name|isBlank
argument_list|(
name|line
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|line
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_function

begin_elseif
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
index|[]
condition|)
block|{
for|for
control|(
name|String
name|line
range|:
operator|(
name|String
index|[]
operator|)
name|value
control|)
block|{
if|if
condition|(
name|line
operator|!=
literal|null
operator|&&
operator|!
name|StringUtils
operator|.
name|isBlank
argument_list|(
name|line
argument_list|)
condition|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_elseif

begin_comment
comment|//else unsupported type - ignore
end_comment

begin_comment
comment|//if first == false we we have not found any non blank line -> return null!
end_comment

begin_return
return|return
operator|!
name|first
condition|?
name|sb
operator|.
name|toString
argument_list|()
else|:
literal|null
return|;
end_return

begin_comment
unit|}
comment|/**      * The dictionary holding the config      * @return the dictionary holding the config      */
end_comment

begin_function
unit|public
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getDict
parameter_list|()
block|{
return|return
name|config
return|;
block|}
end_function

begin_comment
comment|/**      * If the {@link DereferenceConstants#FALLBACK_MODE} is active or inactive      * @return the fallback mode state      */
end_comment

begin_function
specifier|public
name|boolean
name|isFallbackMode
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|FALLBACK_MODE
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
condition|?
name|DereferenceConstants
operator|.
name|DEFAULT_FALLBACK_MODE
else|:
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
end_function

begin_comment
comment|/**      * The configured {@link DereferenceConstants#URI_PATTERN}      * @return the URI patterns. An empty List if none      */
end_comment

begin_function
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getUriPatterns
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|DereferenceConstants
operator|.
name|URI_PATTERN
argument_list|)
decl_stmt|;
return|return
name|getStrValues
argument_list|(
name|value
argument_list|)
return|;
block|}
end_function

begin_comment
comment|/**      * The configured {@link DereferenceConstants#URI_PREFIX}      * @return the URI prefixes. An empty List if none      */
end_comment

begin_function
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getUriPrefixes
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|DereferenceConstants
operator|.
name|URI_PREFIX
argument_list|)
decl_stmt|;
return|return
name|getStrValues
argument_list|(
name|value
argument_list|)
return|;
block|}
end_function

begin_comment
comment|/** 	 * Extracts String values from the parsed value. 	 * @param value the value (String, String[] or Collection<?> 	 * @return the values as List in the parsed order 	 */
end_comment

begin_function
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|getStrValues
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|values
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|values
operator|=
name|StringUtils
operator|.
name|isBlank
argument_list|(
operator|(
operator|(
name|String
operator|)
name|value
operator|)
argument_list|)
condition|?
name|Collections
operator|.
expr|<
name|String
operator|>
name|emptyList
argument_list|()
else|:
name|Collections
operator|.
name|singletonList
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
index|[]
condition|)
block|{
name|values
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|String
name|pattern
range|:
operator|(
name|String
index|[]
operator|)
name|value
control|)
block|{
if|if
condition|(
operator|!
name|StringUtils
operator|.
name|isBlank
argument_list|(
name|pattern
argument_list|)
condition|)
block|{
name|values
operator|.
name|add
argument_list|(
name|pattern
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|Collection
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|values
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Object
name|pattern
operator|:
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
control|)
block|{
if|if
condition|(
name|pattern
operator|!=
literal|null
operator|&&
name|StringUtils
operator|.
name|isBlank
argument_list|(
name|pattern
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
name|values
operator|.
name|add
argument_list|(
name|pattern
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_function

begin_else
else|else
block|{
name|values
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
end_else

begin_return
return|return
name|values
return|;
end_return

unit|} }
end_unit

