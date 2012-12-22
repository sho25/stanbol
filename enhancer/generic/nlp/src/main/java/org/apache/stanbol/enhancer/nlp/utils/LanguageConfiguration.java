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
name|nlp
operator|.
name|utils
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|collections
operator|.
name|map
operator|.
name|CompositeMap
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
name|collections
operator|.
name|map
operator|.
name|CompositeMap
operator|.
name|MapMutator
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
name|ServiceReference
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
comment|/**  * Utility that supports the configuration of languages and language  * specific parameters.  *<h3>Language configuration</h3>  * Languages are configured as follows:  *<pre>  *     de,en</pre>  * or   *<pre>  *     !fr,!cn,*</pre>  * The '<code>!{lang}</code>' is used to {@link #getExplicitlyExcluded()   * explicitly exclude} an language. '<code>*</code>' can be used to  * specify that all languages are allowed. '<code>{lang}</code>'  * {@link #getExplicitlyIncluded() explicitly includes} a language.  * '<code>,</code>' is used as separator between multiple configurations  * however this class also supports the usage of<code>String[]</code> and  * {@link Collection<?>} (in case of Collections the  * {@link Object#toString()} method is used to obtain the configuration).  * If an array or a collection is used for the configuration, than comma  * is NOT used as separator!  *<p>  *<h3>Parameter Support</h3>  * This class supports the parsing of language specific parameters by  * the followng syntax  *<pre>  *    {language};{param-name}={param-value};{param-name}={param-value}</pre>  * Parameters that apply to all {languages} with no configuration can be  * either set for the '<code>*</code>' or an empty language tag. Here  * is an example  *<pre>  *     *;myParam=myValue  *     ;myParam=myValue</pre>  * Multiple default configurations will cause a {@link ConfigurationException}.  *<p>  * The {@link #getParameters(String)} and {@link #getParameters(String,String)}  * will return values of the {@link #getDefaultParameters()} if no  * language specific parameters are present for the requested language. However  * the default configuration is not merged but replaced by language specific  * parameter declarations. Applications that want to use the default configuration  * as fallback to language specific settings can implement this by  * using the properties provided by {@link #getDefaultParameters()}.  *<p>  *<b>Notes</b><ul>  *<li>only the first occurrence of '<code>=</code>' within an  * parameter is used as separator between the param name and value. This  * means that the {param-name} is allowed to contain '='.  *<li>in case a comma separated string is used for the lanugage  * configuration parameter declaration MUST NOT contain   * '<code>,</code>' (comma) values. In case a<code>String[]</code> or an  * {@link Collection} is used this is not the case.  *</ul>  *  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|LanguageConfiguration
block|{
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|EMPTY_PARAMS
init|=
name|Collections
operator|.
name|emptyMap
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|String
name|property
decl_stmt|;
specifier|private
specifier|final
name|Collection
argument_list|<
name|String
argument_list|>
name|defaultConfig
decl_stmt|;
comment|//Langauge configuration
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|configuredLanguages
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|excludedLanguages
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|allowAll
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|defaultParameters
init|=
name|EMPTY_PARAMS
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|LanguageConfiguration
parameter_list|(
name|String
name|property
parameter_list|,
name|String
index|[]
name|defaultConfig
parameter_list|)
block|{
if|if
condition|(
name|property
operator|==
literal|null
operator|||
name|property
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed property MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|property
operator|=
name|property
expr_stmt|;
name|this
operator|.
name|defaultConfig
operator|=
name|defaultConfig
operator|!=
literal|null
condition|?
name|Arrays
operator|.
name|asList
argument_list|(
name|defaultConfig
argument_list|)
else|:
name|Collections
operator|.
name|EMPTY_LIST
expr_stmt|;
try|try
block|{
name|parseConfiguration
argument_list|(
name|this
operator|.
name|defaultConfig
argument_list|)
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
literal|"Inalied default configuration "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|public
name|String
name|getProperty
parameter_list|()
block|{
return|return
name|property
return|;
block|}
comment|/**      * Reads the config for the configured {@link #getProperty() property}      * from the parsed configuration.<p>      * This implementation supports      *<code>null</code> (sets the default),<code>String[]</code>,      *<code>Collections<?></code> (Object{@link #toString() toString()} is called      * on members) and comma separated {@link String}.      * @param configuration the configuration      */
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|Dictionary
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|configuration
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|processConfiguration
argument_list|(
name|configuration
operator|.
name|get
argument_list|(
name|property
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Reads the configuration for the configured {@link #getProperty()} from      * the properties of the parsed {@link ServiceReference}.<p>      * This implementation supports      *<code>null</code> (sets the default),<code>String[]</code>,      *<code>Collections<?></code> (Object{@link #toString() toString()} is called      * on members) and comma separated {@link String}.      * @param ref the SerivceRefernece      * @throws ConfigurationException      */
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|ServiceReference
name|ref
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|processConfiguration
argument_list|(
name|ref
operator|.
name|getProperty
argument_list|(
name|property
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Reads the configuration for the parsed value.<p>      * This implementation supports      *<code>null</code> (sets the default),<code>String[]</code>,      *<code>Collections<?></code> (Object{@link #toString() toString()} is called      * on members) and comma separated {@link String}.      * @param value the value      * @throws ConfigurationException if the configuration of is invalid      */
specifier|protected
name|void
name|processConfiguration
parameter_list|(
name|Object
name|value
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Collection
argument_list|<
name|?
argument_list|>
name|config
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|config
operator|=
name|defaultConfig
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
name|config
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
name|config
operator|=
operator|(
name|Collection
argument_list|<
name|?
argument_list|>
operator|)
name|value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|config
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|value
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|","
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Values of type '"
operator|+
name|value
operator|.
name|getClass
argument_list|()
operator|+
literal|"' are not supported (supported are "
operator|+
literal|"String[], Collection<?>, comma separated String and "
operator|+
literal|"NULL to reset to the default configuration)!"
argument_list|)
throw|;
block|}
name|parseConfiguration
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|parseConfiguration
parameter_list|(
name|Collection
argument_list|<
name|?
argument_list|>
name|config
parameter_list|)
throws|throws
name|ConfigurationException
block|{
if|if
condition|(
name|config
operator|==
literal|null
condition|)
block|{
name|config
operator|=
name|defaultConfig
expr_stmt|;
block|}
comment|//rest values
name|configuredLanguages
operator|.
name|clear
argument_list|()
expr_stmt|;
name|excludedLanguages
operator|.
name|clear
argument_list|()
expr_stmt|;
name|defaultParameters
operator|=
name|EMPTY_PARAMS
expr_stmt|;
comment|//do not change values in multi threaded environments
for|for
control|(
name|Object
name|value
range|:
name|config
control|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
continue|continue;
comment|//ignore null values
block|}
name|String
name|line
init|=
name|value
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|int
name|sepIndex
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
decl_stmt|;
name|String
name|lang
init|=
name|sepIndex
operator|<
literal|0
condition|?
name|line
else|:
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|sepIndex
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|//lang = lang.toLowerCase(); //country codes are upper case
if|if
condition|(
name|lang
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|lang
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'!'
condition|)
block|{
comment|//exclude
name|lang
operator|=
name|lang
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|configuredLanguages
operator|.
name|containsKey
argument_list|(
name|lang
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Langauge '"
operator|+
name|lang
operator|+
literal|"' is both included and excluded (config: "
operator|+
name|config
operator|+
literal|")"
argument_list|)
throw|;
block|}
if|if
condition|(
name|sepIndex
operator|>=
literal|0
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"The excluded Langauge '"
operator|+
name|lang
operator|+
literal|"' MUST NOT define parameters (config: "
operator|+
name|config
operator|+
literal|")"
argument_list|)
throw|;
block|}
name|excludedLanguages
operator|.
name|add
argument_list|(
name|lang
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"*"
operator|.
name|equals
argument_list|(
name|lang
argument_list|)
condition|)
block|{
name|allowAll
operator|=
literal|true
expr_stmt|;
name|parsedDefaultParameters
argument_list|(
name|line
argument_list|,
name|sepIndex
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|lang
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|excludedLanguages
operator|.
name|contains
argument_list|(
name|lang
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Langauge '"
operator|+
name|lang
operator|+
literal|"' is both included and excluded (config: "
operator|+
name|config
operator|+
literal|")"
argument_list|)
throw|;
block|}
name|configuredLanguages
operator|.
name|put
argument_list|(
name|lang
argument_list|,
name|sepIndex
operator|>=
literal|0
operator|&&
name|sepIndex
operator|<
name|line
operator|.
name|length
argument_list|()
operator|-
literal|2
condition|?
name|parseParameters
argument_list|(
name|line
operator|.
name|substring
argument_list|(
name|sepIndex
operator|+
literal|1
argument_list|,
name|line
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
else|:
name|EMPTY_PARAMS
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//language tag is empty (line starts with an ';'
comment|//this indicates that this is used to configure the default parameters
name|parsedDefaultParameters
argument_list|(
name|line
argument_list|,
name|sepIndex
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Parsed the {@link #defaultParameters} and also checks that not multiple       * (non empty) of such configurations are present      * @param line the current line      * @param sepIndex the index of first ';' in the configuration line      * @throws ConfigurationException if multiple default configurations are present or      * if the parameters are illegal formatted.      */
specifier|private
name|void
name|parsedDefaultParameters
parameter_list|(
name|String
name|line
parameter_list|,
name|int
name|sepIndex
parameter_list|)
throws|throws
name|ConfigurationException
block|{
if|if
condition|(
operator|!
name|defaultParameters
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Language Configuration MUST NOT "
operator|+
literal|"contain multiple default property configurations. This are configurations "
operator|+
literal|"of properties for the wildcard '*;{properties}' or the empty language "
operator|+
literal|"';{properties}'."
argument_list|)
throw|;
block|}
name|defaultParameters
operator|=
name|sepIndex
operator|>=
literal|0
operator|&&
name|sepIndex
operator|<
name|line
operator|.
name|length
argument_list|()
operator|-
literal|2
condition|?
name|parseParameters
argument_list|(
name|line
operator|.
name|substring
argument_list|(
name|sepIndex
argument_list|,
name|line
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
else|:
name|EMPTY_PARAMS
expr_stmt|;
block|}
comment|/**      * Parses optional parameters<code>{key}[={value}];{key2}[={value2}]</code>. Using      * the same key multiple times will override the previouse value      * @param paramString      * @return      * @throws ConfigurationException      */
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parseParameters
parameter_list|(
name|String
name|paramString
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|params
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|param
range|:
name|paramString
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
control|)
block|{
name|param
operator|=
name|param
operator|.
name|trim
argument_list|()
expr_stmt|;
name|int
name|equalsPos
init|=
name|param
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
decl_stmt|;
if|if
condition|(
name|equalsPos
operator|==
literal|0
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|property
argument_list|,
literal|"Parameter '"
operator|+
name|param
operator|+
literal|"' has empty key!"
argument_list|)
throw|;
block|}
name|String
name|key
init|=
name|equalsPos
operator|>
literal|0
condition|?
name|param
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|equalsPos
argument_list|)
operator|.
name|trim
argument_list|()
else|:
name|param
decl_stmt|;
name|String
name|value
decl_stmt|;
if|if
condition|(
name|equalsPos
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|equalsPos
operator|<
name|param
operator|.
name|length
argument_list|()
operator|-
literal|2
condition|)
block|{
name|value
operator|=
name|param
operator|.
name|substring
argument_list|(
name|equalsPos
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|value
operator|=
literal|""
expr_stmt|;
block|}
block|}
else|else
block|{
name|value
operator|=
literal|null
expr_stmt|;
block|}
name|params
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
return|return
name|params
operator|.
name|isEmpty
argument_list|()
condition|?
name|EMPTY_PARAMS
else|:
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|params
argument_list|)
return|;
block|}
specifier|private
class|class
name|LangState
block|{
specifier|protected
specifier|final
name|boolean
name|state
decl_stmt|;
specifier|protected
specifier|final
name|String
name|lang
decl_stmt|;
specifier|protected
name|LangState
parameter_list|(
name|boolean
name|state
parameter_list|,
name|String
name|lang
parameter_list|)
block|{
name|this
operator|.
name|state
operator|=
name|state
expr_stmt|;
name|this
operator|.
name|lang
operator|=
name|lang
expr_stmt|;
block|}
block|}
specifier|private
name|LangState
name|getLanguageState
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|int
name|countrySepPos
init|=
name|language
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|language
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
decl_stmt|;
name|boolean
name|excluded
init|=
name|excludedLanguages
operator|.
name|contains
argument_list|(
name|language
argument_list|)
decl_stmt|;
name|boolean
name|included
init|=
name|configuredLanguages
operator|.
name|containsKey
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|countrySepPos
operator|>=
literal|2
operator|&&
operator|!
name|excluded
operator|&&
operator|!
name|included
condition|)
block|{
comment|//search without language specific part
name|String
name|baseLang
init|=
name|language
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|countrySepPos
argument_list|)
decl_stmt|;
return|return
operator|new
name|LangState
argument_list|(
name|allowAll
condition|?
operator|!
name|excludedLanguages
operator|.
name|contains
argument_list|(
name|baseLang
argument_list|)
else|:
name|configuredLanguages
operator|.
name|containsKey
argument_list|(
name|baseLang
argument_list|)
argument_list|,
name|baseLang
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|LangState
argument_list|(
name|allowAll
condition|?
operator|!
name|excluded
else|:
name|included
argument_list|,
name|language
argument_list|)
return|;
block|}
block|}
comment|/**      * Checks if the parsed language is included in the configuration      * @param language the language      * @return the state      */
specifier|public
name|boolean
name|isLanguage
parameter_list|(
name|String
name|language
parameter_list|)
block|{
return|return
name|getLanguageState
argument_list|(
name|language
argument_list|)
operator|.
name|state
return|;
block|}
comment|/**      * The explicitly configured languages      * @return      */
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getExplicitlyIncluded
parameter_list|()
block|{
return|return
name|configuredLanguages
operator|.
name|keySet
argument_list|()
return|;
block|}
comment|/**      * The explicitly excluded (e.g. !de) languages      * @return      */
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getExplicitlyExcluded
parameter_list|()
block|{
return|return
name|excludedLanguages
return|;
block|}
comment|/**      * If the '*' was used in the configuration to allow      * all lanugages.       * @return      */
specifier|public
name|boolean
name|useWildcard
parameter_list|()
block|{
return|return
name|allowAll
return|;
block|}
comment|/**      * Returns configured parameters if<code>{@link #isLanguage(String)} == true</code>.      * The returned map contains {@link #getLanguageParams(String) language specific parameters}       * merged with {@link #getDefaultParameters()}      * @param language the language      * @return the parameters or<code>null</code> if none or the parsed language      * is not active.      */
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getParameters
parameter_list|(
name|String
name|parsedLang
parameter_list|)
block|{
name|LangState
name|ls
init|=
name|getLanguageState
argument_list|(
name|parsedLang
argument_list|)
decl_stmt|;
if|if
condition|(
name|ls
operator|.
name|state
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|params
init|=
name|configuredLanguages
operator|.
name|get
argument_list|(
name|ls
operator|.
name|lang
argument_list|)
decl_stmt|;
if|if
condition|(
name|params
operator|!=
literal|null
condition|)
block|{
name|params
operator|=
operator|new
name|CompositeMap
argument_list|(
name|params
argument_list|,
name|defaultParameters
argument_list|,
name|CONFIGURATION_MERGER
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|params
operator|=
name|defaultParameters
expr_stmt|;
block|}
return|return
name|params
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
comment|//to indicate the parsed language is not active
block|}
block|}
comment|/**      * Getter for the language specific parameters. This does NOT include      * default parameters.      * @param language the language      * @return the language specific parameters or<code>null</code> if no      * parameters are configured.      */
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getLanguageParams
parameter_list|(
name|String
name|parsedLang
parameter_list|)
block|{
name|LangState
name|ls
init|=
name|getLanguageState
argument_list|(
name|parsedLang
argument_list|)
decl_stmt|;
return|return
name|ls
operator|.
name|state
condition|?
name|configuredLanguages
operator|.
name|get
argument_list|(
name|ls
operator|.
name|lang
argument_list|)
else|:
literal|null
return|;
block|}
comment|/**      * Getter for the default parameters      * @return the default parameters, an empty map if none.      */
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getDefaultParameters
parameter_list|()
block|{
return|return
name|defaultParameters
return|;
block|}
comment|/**      * Resets the configuration to the default (as parsed in the constructor)      */
specifier|public
name|void
name|setDefault
parameter_list|()
block|{
try|try
block|{
name|parseConfiguration
argument_list|(
name|defaultConfig
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{
comment|// can not happen else the default config is already validated
comment|// within the constructor
block|}
block|}
comment|/**      * Returns the value of the parameter for the language (if present and the      * langage is active). This merges language specific parameters with      * default parameters.      * @param language the language      * @param paramName the name of the param      * @return the param or<code>null</code> if not present OR the language      * is not active.      */
specifier|public
name|String
name|getParameter
parameter_list|(
name|String
name|language
parameter_list|,
name|String
name|paramName
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|params
init|=
name|getParameters
argument_list|(
name|language
argument_list|)
decl_stmt|;
name|int
name|countrySepPos
init|=
name|language
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|language
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
decl_stmt|;
comment|//we need to fallback to the language specific config if
comment|// * there is a country code
comment|// * no country specific params OR
comment|// * param not present in country specific config
if|if
condition|(
name|countrySepPos
operator|>=
literal|2
operator|&&
operator|(
name|params
operator|==
literal|null
operator|||
operator|!
name|params
operator|.
name|containsKey
argument_list|(
name|paramName
argument_list|)
operator|)
condition|)
block|{
name|params
operator|=
name|getParameters
argument_list|(
name|language
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|countrySepPos
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|params
operator|==
literal|null
condition|?
literal|null
else|:
name|params
operator|.
name|get
argument_list|(
name|paramName
argument_list|)
return|;
block|}
name|MapMutator
name|CONFIGURATION_MERGER
init|=
operator|new
name|MapMutator
argument_list|()
block|{
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
literal|"rawtypes"
argument_list|)
specifier|public
name|void
name|resolveCollision
parameter_list|(
name|CompositeMap
name|composite
parameter_list|,
name|Map
name|existing
parameter_list|,
name|Map
name|added
parameter_list|,
name|Collection
name|intersect
parameter_list|)
block|{
comment|//nothing to do as we want the value of the first map
block|}
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
block|{
literal|"rawtypes"
block|,
literal|"unchecked"
block|}
argument_list|)
specifier|public
name|void
name|putAll
parameter_list|(
name|CompositeMap
name|map
parameter_list|,
name|Map
index|[]
name|composited
parameter_list|,
name|Map
name|mapToAdd
parameter_list|)
block|{
comment|//add to the first
name|composited
index|[
literal|0
index|]
operator|.
name|putAll
argument_list|(
name|mapToAdd
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
block|{
literal|"rawtypes"
block|,
literal|"unchecked"
block|}
argument_list|)
specifier|public
name|Object
name|put
parameter_list|(
name|CompositeMap
name|map
parameter_list|,
name|Map
index|[]
name|composited
parameter_list|,
name|Object
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|Object
name|prevResult
init|=
name|map
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|Object
name|result
init|=
name|composited
index|[
literal|0
index|]
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
decl_stmt|;
return|return
name|result
operator|==
literal|null
condition|?
name|prevResult
else|:
name|result
return|;
block|}
block|}
decl_stmt|;
block|}
end_class

end_unit

