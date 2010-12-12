begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
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
name|Date
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
name|model
operator|.
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_interface
specifier|public
interface|interface
name|EntityMapping
block|{
comment|/**      * The default state for newly created instances if not otherwise configured      */
name|MappingState
name|DEFAULT_MAPPING_STATE
init|=
name|MappingState
operator|.
name|proposed
decl_stmt|;
comment|/**      * Enumeration that defines the different states of {@link EntityMapping}      * instances.      * @author Rupert Westenthaler      *      */
enum|enum
name|MappingState
block|{
comment|/**          * Mapping the entity to the symbol was rejected by some user/process.          * Such mappings MUST NOT be used in any application context other than          * some administrative interfaces.          */
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
comment|/**          * Indicated, that a mapping of the entity to the symbol is proposed.          * Such mappings still wait for some kind of confirmation to be fully          * established. Based on the application context it might already be          * OK to used them.          */
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
comment|/**          * This indicates that this mapping has expired. This indicated, that          * this mapping was once {@link MappingState#confirmed} but now waits for          * some confirmation activity. Based on the application context it might          * still be OK to use mappings with that state.          */
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
comment|/**          * Indicated, that this mapping is fully valied and can be used in any          * application context          */
name|confirmed
argument_list|(
name|RdfResourceEnum
operator|.
name|mappingStateConfirmed
operator|.
name|getUri
argument_list|()
argument_list|)
block|,         ;
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
block|}
empty_stmt|;
comment|/**      * Getter for the identifier.      * @return the identifier      */
name|String
name|getId
parameter_list|()
function_decl|;
comment|/**      * The key to be used for the id of the mapped entity      */
name|String
name|ENTITY_ID
init|=
name|RdfResourceEnum
operator|.
name|mappedEntity
operator|.
name|toString
argument_list|()
decl_stmt|;
empty_stmt|;
comment|/**      * Getter for the ID of the entity      * @return the mapped entity      */
name|String
name|getEntityId
parameter_list|()
function_decl|;
comment|/**      * The key to be used for the id of the mapped symbol      */
name|String
name|SYMBOL_ID
init|=
name|RdfResourceEnum
operator|.
name|mappedSymbol
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|/**      * Getter for the ID of the symbol      * @return the symbol the entity is mapped to      */
name|String
name|getSymbolId
parameter_list|()
function_decl|;
comment|/**      * The key to be used for the state of the MappedEntity instance      */
name|String
name|STATE
init|=
name|RdfResourceEnum
operator|.
name|hasMappingState
operator|.
name|toString
argument_list|()
decl_stmt|;
empty_stmt|;
comment|/**      * The state of this mapping      * @return the state      */
name|MappingState
name|getState
parameter_list|()
function_decl|;
comment|/**      * Setter for the mapping state      * @param state the new state      * @throws IllegalArgumentException if the parsed state is<code>null</code>      */
name|void
name|setState
parameter_list|(
name|MappingState
name|state
parameter_list|)
throws|throws
name|IllegalArgumentException
function_decl|;
comment|/**      * The property used to hold the expires date of the representation (if any)      */
name|String
name|EXPIRES
init|=
name|RdfResourceEnum
operator|.
name|expires
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/**      * Getter for the date this representation expires. If this representation      * does not expire this method returns<code>null</code>.      * @return the expire date or<code>null</code> if not applicable.      */
name|Date
name|getExpires
parameter_list|()
function_decl|;
comment|/**      * Setter for the expire date for this representation.      * @param date the date or<code>null</code> if this representation does not      * expire      */
name|void
name|setExpires
parameter_list|(
name|Date
name|date
parameter_list|)
function_decl|;
comment|/**      * Getter for the Representation of this EntityMapping      * @return The representation      */
name|Representation
name|getRepresentation
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

