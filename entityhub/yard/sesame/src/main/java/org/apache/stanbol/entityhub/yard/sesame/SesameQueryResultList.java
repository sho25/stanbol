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
name|sesame
package|;
end_package

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
name|Iterator
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
name|query
operator|.
name|sparql
operator|.
name|SparqlFieldQuery
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
name|query
operator|.
name|QueryResultList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_comment
comment|/**  * {@link QueryResultList} implementation for Sesame. This provides  * access to the Sesame {@link Model} holding the dat. Mainly for the use of  * Sesame specific RDF serializer.  *   * @author Rupert Westenthaler  *  * @param<T>  */
end_comment

begin_class
specifier|public
class|class
name|SesameQueryResultList
implements|implements
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
block|{
specifier|protected
specifier|final
name|Model
name|model
decl_stmt|;
specifier|protected
specifier|final
name|Collection
argument_list|<
name|Representation
argument_list|>
name|representations
decl_stmt|;
specifier|protected
specifier|final
name|SparqlFieldQuery
name|query
decl_stmt|;
specifier|public
name|SesameQueryResultList
parameter_list|(
name|Model
name|model
parameter_list|,
name|SparqlFieldQuery
name|query
parameter_list|,
name|List
argument_list|<
name|Representation
argument_list|>
name|representations
parameter_list|)
block|{
name|this
operator|.
name|model
operator|=
name|model
expr_stmt|;
name|this
operator|.
name|representations
operator|=
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|representations
argument_list|)
expr_stmt|;
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|SparqlFieldQuery
name|getQuery
parameter_list|()
block|{
return|return
name|query
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getSelectedFields
parameter_list|()
block|{
return|return
name|query
operator|.
name|getSelectedFields
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Representation
argument_list|>
name|getType
parameter_list|()
block|{
return|return
name|Representation
operator|.
name|class
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|representations
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Iterator
argument_list|<
name|Representation
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|representations
operator|.
name|iterator
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Representation
argument_list|>
name|results
parameter_list|()
block|{
return|return
name|representations
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|representations
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * The model holding all query results      * @return      */
specifier|public
name|Model
name|getModel
parameter_list|()
block|{
return|return
name|model
return|;
block|}
block|}
end_class

end_unit
