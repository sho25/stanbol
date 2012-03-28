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
name|ldpath
operator|.
name|backend
package|;
end_package

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
name|model
operator|.
name|InMemoryValueFactory
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
name|FieldQueryImpl
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
name|QueryResultListImpl
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
name|EntityhubException
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
name|FieldQuery
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
name|QueryResultList
import|;
end_import

begin_comment
comment|/**  * Allows to execute ldpath on the data of a single Representation. Will not  * support paths longer that<code>1</code> but might still be very usefull  * to select/filter specific values of fields.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|SingleRepresentationBackend
extends|extends
name|AbstractBackend
block|{
name|Representation
name|representation
decl_stmt|;
specifier|private
specifier|final
name|ValueFactory
name|valueFactory
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|FieldQuery
name|DUMMY_QUERY
init|=
operator|new
name|FieldQueryImpl
argument_list|()
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|private
specifier|static
specifier|final
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|EMPTY_RESULT
init|=
operator|new
name|QueryResultListImpl
argument_list|<
name|String
argument_list|>
argument_list|(
name|DUMMY_QUERY
argument_list|,
name|Collections
operator|.
name|EMPTY_LIST
argument_list|,
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|SingleRepresentationBackend
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SingleRepresentationBackend
parameter_list|(
name|ValueFactory
name|vf
parameter_list|)
block|{
if|if
condition|(
name|vf
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|valueFactory
operator|=
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|valueFactory
operator|=
name|vf
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setRepresentation
parameter_list|(
name|Representation
name|r
parameter_list|)
block|{
if|if
condition|(
name|r
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|representation
operator|=
name|r
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Representation MUST NOT be NULL!"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|ValueFactory
name|getValueFactory
parameter_list|()
block|{
return|return
name|valueFactory
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Representation
name|getRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|EntityhubException
block|{
return|return
name|representation
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|?
name|representation
else|:
literal|null
return|;
block|}
annotation|@
name|Override
specifier|protected
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|query
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|EntityhubException
block|{
return|return
name|EMPTY_RESULT
return|;
block|}
annotation|@
name|Override
specifier|protected
name|FieldQuery
name|createQuery
parameter_list|()
block|{
return|return
name|DUMMY_QUERY
return|;
block|}
block|}
end_class

end_unit

