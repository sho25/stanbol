begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|engines
operator|.
name|geonames
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
name|UnsupportedEncodingException
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
name|HashSet
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

begin_comment
comment|/**  * Defines information for a Property of an GET or POST request such as the name, required or optional,  * default values and a list of possible values.  *<p>  * This class is intended to allow to define meta data about a used web service (e.g. within an  * Enumeration)  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
class|class
name|RequestProperty
block|{
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|required
decl_stmt|;
specifier|private
specifier|final
name|String
name|defaultValue
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|valueList
decl_stmt|;
specifier|private
specifier|final
name|String
name|toString
decl_stmt|;
comment|/**      * Constructs a Property definition for a (RESTful) web service.      *       * @param name the name of the property (MUST NOT be<code>null</code>)      * @param required defines if the property is optional or required      * @param defaultValue the value used if this parameter is not parsed.       *<code>null</code> indicates no default configuration.      * @param valueList the list of allowed values for this parameter.       *<code>null</code> or an empty array indicate that there are no       * restrictions on possible values.      */
specifier|protected
name|RequestProperty
parameter_list|(
name|String
name|name
parameter_list|,
name|boolean
name|required
parameter_list|,
name|String
name|defaultValue
parameter_list|,
name|String
modifier|...
name|valueList
parameter_list|)
block|{
if|if
condition|(
name|name
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The name of an Porperty MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|required
operator|=
name|required
expr_stmt|;
name|this
operator|.
name|defaultValue
operator|=
name|defaultValue
expr_stmt|;
if|if
condition|(
name|valueList
operator|!=
literal|null
operator|&&
name|valueList
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|this
operator|.
name|valueList
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
name|Arrays
operator|.
name|asList
argument_list|(
name|valueList
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|valueList
operator|=
literal|null
expr_stmt|;
block|}
name|StringBuffer
name|b
init|=
operator|new
name|StringBuffer
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|b
operator|.
name|append
argument_list|(
literal|'['
argument_list|)
expr_stmt|;
if|if
condition|(
name|required
condition|)
block|{
name|b
operator|.
name|append
argument_list|(
literal|"required"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|b
operator|.
name|append
argument_list|(
literal|"optional"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|this
operator|.
name|defaultValue
operator|!=
literal|null
condition|)
block|{
name|b
operator|.
name|append
argument_list|(
literal|",default='"
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|this
operator|.
name|defaultValue
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
literal|'\''
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|this
operator|.
name|valueList
operator|!=
literal|null
condition|)
block|{
name|b
operator|.
name|append
argument_list|(
literal|", valueList="
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|this
operator|.
name|valueList
argument_list|)
expr_stmt|;
block|}
name|b
operator|.
name|append
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
name|this
operator|.
name|toString
operator|=
name|b
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|boolean
name|hasDefault
parameter_list|()
block|{
return|return
name|defaultValue
operator|!=
literal|null
return|;
block|}
specifier|public
name|String
name|defaultValue
parameter_list|()
block|{
return|return
name|defaultValue
return|;
block|}
specifier|public
name|boolean
name|hasValueList
parameter_list|()
block|{
return|return
name|valueList
operator|!=
literal|null
return|;
block|}
specifier|public
name|boolean
name|allowedValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
comment|// if no value list is defined
if|if
condition|(
name|valueList
operator|==
literal|null
condition|)
block|{
comment|// return only false if required and value == null
return|return
operator|!
operator|(
name|value
operator|==
literal|null
operator|&&
name|isRequired
argument_list|()
operator|)
return|;
block|}
else|else
block|{
comment|// check if the value is in the value list or null and optional
return|return
name|valueList
operator|.
name|contains
argument_list|(
name|value
argument_list|)
operator|||
operator|(
name|value
operator|==
literal|null
operator|&&
name|isOptional
argument_list|()
operator|)
return|;
block|}
block|}
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getValueList
parameter_list|()
block|{
return|return
name|valueList
return|;
block|}
specifier|public
name|boolean
name|isRequired
parameter_list|()
block|{
return|return
name|required
return|;
block|}
specifier|public
name|boolean
name|isOptional
parameter_list|()
block|{
return|return
operator|!
name|required
return|;
block|}
comment|/**      * Encodes the Property for the given parameters.      *       * @param requestString      *            The string builder used to create the request      * @param first      *            if the property is the first property added to the request      * @param values      *            the value(s) for the property. If<code>null</code> or an empty list, than the      *            {@link #defaultValue()} is added if present. Also if the parsed collection contains the      *<code>null</code> value the {@link #defaultValue()} is added instead.      * @return<code>true</code> if the parsed request string was modified as a result of this call -      *         meaning that parameter was added to the request.      */
specifier|public
name|boolean
name|encode
parameter_list|(
name|StringBuilder
name|requestString
parameter_list|,
name|boolean
name|first
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|values
parameter_list|)
block|{
name|boolean
name|added
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
operator|||
name|values
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// replace with null element to ensure the default value is added
name|values
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|value
range|:
name|values
control|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
operator|&&
name|hasDefault
argument_list|()
condition|)
block|{
name|value
operator|=
name|defaultValue
argument_list|()
expr_stmt|;
block|}
comment|// NOTE: value == null may still be OK
if|if
condition|(
name|allowedValue
argument_list|(
name|value
argument_list|)
condition|)
block|{
comment|// NOTE: also this may still say that NULL is OK
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
name|requestString
operator|.
name|append
argument_list|(
literal|'&'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|requestString
operator|.
name|append
argument_list|(
literal|'?'
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
name|requestString
operator|.
name|append
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|requestString
operator|.
name|append
argument_list|(
literal|'='
argument_list|)
expr_stmt|;
try|try
block|{
name|requestString
operator|.
name|append
argument_list|(
name|URLEncoder
operator|.
name|encode
argument_list|(
name|value
argument_list|,
literal|"UTF8"
argument_list|)
argument_list|)
expr_stmt|;
name|added
operator|=
literal|true
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
comment|// else property is not present
block|}
else|else
block|{
comment|// Illegal parameter
name|GeonamesAPIWrapper
operator|.
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
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|added
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|toString
return|;
block|}
block|}
end_class

end_unit

