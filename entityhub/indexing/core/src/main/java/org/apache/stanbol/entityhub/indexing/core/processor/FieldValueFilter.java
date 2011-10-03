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
name|indexing
operator|.
name|core
operator|.
name|processor
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityProcessor
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
name|indexing
operator|.
name|core
operator|.
name|config
operator|.
name|IndexingConfig
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
name|defaults
operator|.
name|NamespaceEnum
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
comment|/**  * A simple Processor that allows to filter {@link Representation} based on  * {@link Reference#getReference()} values of a configured Field.<p>  * Typically used to filter Representations based on the type (rdf:type)<p>  * Parsing '*' as value for the field deactivates filtering. A missing  * field configuration is assumed as Error and will cause an   * {@link IllegalArgumentException} during {@link #setConfiguration(Map)}  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|FieldValueFilter
implements|implements
name|EntityProcessor
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FieldValueFilter
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_FIELD
init|=
literal|"field"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_VALUES
init|=
literal|"values"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_FIELD
init|=
literal|"rdf:type"
decl_stmt|;
specifier|public
name|String
name|field
decl_stmt|;
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|values
decl_stmt|;
comment|/**      * Parsing 'null' or '' as value can be used to include entities that do not      * define any values for the configured {@link #field}      */
name|boolean
name|includeEmpty
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Representation
name|process
parameter_list|(
name|Representation
name|source
parameter_list|)
block|{
if|if
condition|(
operator|!
name|includeEmpty
operator|&&
name|values
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//no filter set
return|return
name|source
return|;
block|}
name|Iterator
argument_list|<
name|Reference
argument_list|>
name|refs
init|=
name|source
operator|.
name|getReferences
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
name|includeEmpty
operator|&&
operator|!
name|refs
operator|.
name|hasNext
argument_list|()
condition|)
block|{
comment|//no values and includeNull
return|return
name|source
return|;
block|}
while|while
condition|(
name|refs
operator|.
name|hasNext
argument_list|()
condition|)
block|{
if|if
condition|(
name|values
operator|.
name|contains
argument_list|(
name|refs
operator|.
name|next
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|source
return|;
block|}
block|}
comment|//not found -> filter
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|void
name|initialise
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|boolean
name|needsInitialisation
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|PARAM_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|field
operator|=
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|DEFAULT_FIELD
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Using default Field %s"
argument_list|,
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|field
operator|=
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|DEFAULT_FIELD
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"configured Field: %s"
argument_list|,
name|field
argument_list|)
expr_stmt|;
block|}
name|value
operator|=
name|config
operator|.
name|get
argument_list|(
name|PARAM_VALUES
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Missing required Parameter "
operator|+
name|PARAM_VALUES
operator|+
literal|". Set to '*' to deactivate Filtering"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|String
name|stringValue
init|=
name|value
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|stringValue
operator|.
name|equals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
comment|// * -> deactivate Filtering
name|this
operator|.
name|values
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|values
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|fieldValue
range|:
name|stringValue
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
control|)
block|{
if|if
condition|(
name|fieldValue
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|fieldValue
operator|.
name|isEmpty
argument_list|()
operator|||
name|fieldValue
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"null"
argument_list|)
condition|)
block|{
name|this
operator|.
name|includeEmpty
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|values
operator|.
name|add
argument_list|(
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|fieldValue
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|values
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|includeEmpty
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parameter "
operator|+
name|PARAM_VALUES
operator|+
literal|'='
operator|+
name|value
operator|+
literal|" does not contain a valid field value!"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|values
operator|=
name|values
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
index|[]
condition|)
block|{
name|String
index|[]
name|typeArray
init|=
operator|(
name|String
index|[]
operator|)
name|value
decl_stmt|;
if|if
condition|(
name|typeArray
operator|.
name|length
operator|==
literal|0
operator|||
comment|//if an empty array or
name|typeArray
operator|.
name|length
operator|==
literal|1
operator|&&
name|typeArray
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
comment|//only a * is parsed
name|this
operator|.
name|values
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
comment|// than deactivate filtering
block|}
else|else
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|values
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|filterString
range|:
name|typeArray
control|)
block|{
if|if
condition|(
name|filterString
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|filterString
operator|.
name|isEmpty
argument_list|()
operator|||
name|filterString
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"null"
argument_list|)
condition|)
block|{
name|this
operator|.
name|includeEmpty
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|values
operator|.
name|add
argument_list|(
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|filterString
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|values
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|this
operator|.
name|includeEmpty
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parameter "
operator|+
name|PARAM_VALUES
operator|+
literal|'='
operator|+
name|value
operator|+
literal|" does not contain a valid field value!"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|values
operator|=
name|values
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Type of parameter "
operator|+
name|PARAM_VALUES
operator|+
literal|'='
operator|+
name|value
operator|+
literal|"(type:"
operator|+
name|value
operator|.
name|getClass
argument_list|()
operator|+
literal|") is not supported MUST be String or String[]!"
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit
