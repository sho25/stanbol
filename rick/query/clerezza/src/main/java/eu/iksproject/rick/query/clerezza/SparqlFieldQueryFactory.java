begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|query
operator|.
name|clerezza
package|;
end_package

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
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
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQueryFactory
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|SparqlFieldQueryFactory
implements|implements
name|FieldQueryFactory
block|{
specifier|private
specifier|static
name|SparqlFieldQueryFactory
name|instance
decl_stmt|;
specifier|public
specifier|static
name|SparqlFieldQueryFactory
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|instance
operator|==
literal|null
condition|)
block|{
name|instance
operator|=
operator|new
name|SparqlFieldQueryFactory
argument_list|()
expr_stmt|;
block|}
return|return
name|instance
return|;
block|}
specifier|private
name|SparqlFieldQueryFactory
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|SparqlFieldQuery
name|createFieldQuery
parameter_list|()
block|{
return|return
operator|new
name|SparqlFieldQuery
argument_list|()
return|;
block|}
comment|/** 	 * Utility Method to create an {@link SparqlFieldQuery} based on the parse {@link FieldQuery}      * @param parsedQuery the parsed Query 	 */
specifier|public
specifier|static
name|SparqlFieldQuery
name|getSparqlFieldQuery
parameter_list|(
name|FieldQuery
name|parsedQuery
parameter_list|)
block|{
if|if
condition|(
name|parsedQuery
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
name|parsedQuery
operator|instanceof
name|SparqlFieldQuery
condition|)
block|{
return|return
operator|(
name|SparqlFieldQuery
operator|)
name|parsedQuery
return|;
block|}
else|else
block|{
return|return
name|parsedQuery
operator|.
name|copyTo
argument_list|(
operator|new
name|SparqlFieldQuery
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

