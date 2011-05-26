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
name|servicesapi
operator|.
name|model
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
name|Map
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
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_comment
comment|/**  * Enumeration that defines the different states of {@link EntityMapping}  * instances.  * @author Rupert Westenthaler  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|MappingState
block|{
comment|/**      * Mapping the entity to the symbol was rejected by some user/process.      * Such mappings MUST NOT be used in any application context other than      * some administrative interfaces.      */
name|rejected
argument_list|(
name|RdfResourceEnum
operator|.
name|mappingStateRejected
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**      * Indicated, that a mapping of the entity to the symbol is proposed.      * Such mappings still wait for some kind of confirmation to be fully      * established. Based on the application context it might already be      * OK to used them.      */
name|proposed
argument_list|(
name|RdfResourceEnum
operator|.
name|mappingStateProposed
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**      * This indicates that this mapping has expired. This indicated, that      * this mapping was once {@link MappingState#confirmed} but now waits for      * some confirmation activity. Based on the application context it might      * still be OK to use mappings with that state.      */
name|expired
argument_list|(
name|RdfResourceEnum
operator|.
name|mappingStateExpired
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**      * Indicated, that this mapping is fully valied and can be used in any      * application context      */
name|confirmed
argument_list|(
name|RdfResourceEnum
operator|.
name|mappingStateConfirmed
operator|.
name|getUri
argument_list|()
argument_list|)
block|,     ;
specifier|private
name|String
name|uri
decl_stmt|;
name|MappingState
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
block|}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|uri
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
name|uri
return|;
block|}
comment|// ---- reverse Mapping based on URI ----
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|MappingState
argument_list|>
name|URI_TO_STATE
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|MappingState
argument_list|>
name|mappings
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|MappingState
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|MappingState
name|state
range|:
name|MappingState
operator|.
name|values
argument_list|()
control|)
block|{
name|mappings
operator|.
name|put
argument_list|(
name|state
operator|.
name|getUri
argument_list|()
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
name|URI_TO_STATE
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|mappings
argument_list|)
expr_stmt|;
block|}
comment|/**      * Getter for the State based on the URI.      * @param uri the URI      * @return the State      * @throws IllegalArgumentException if the parsed URI does not represent      * a state      */
specifier|public
specifier|static
name|MappingState
name|getState
parameter_list|(
name|String
name|uri
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|MappingState
name|state
init|=
name|URI_TO_STATE
operator|.
name|get
argument_list|(
name|uri
argument_list|)
decl_stmt|;
if|if
condition|(
name|state
operator|==
literal|null
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
literal|"Unknown MappingState URI %s (supported states URIs: %s)"
argument_list|,
name|uri
argument_list|,
name|URI_TO_STATE
operator|.
name|keySet
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
return|return
name|state
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isState
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
return|return
name|URI_TO_STATE
operator|.
name|containsKey
argument_list|(
name|uri
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

