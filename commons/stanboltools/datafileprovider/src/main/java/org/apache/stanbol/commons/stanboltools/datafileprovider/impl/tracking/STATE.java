begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|impl
operator|.
name|tracking
package|;
end_package

begin_comment
comment|/**  * The state of a DataFile. UNKNOWN indicates that this DataFile was  * never tracked before.  * @author Rupert Westenthaler  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|STATE
block|{
comment|/**      * never checked      */
name|UNKNOWN
block|,
comment|/**      * not available on the last check      */
name|UNAVAILABLE
block|,
comment|/**      * available on the last check      */
name|AVAILABLE
block|,
comment|/**      * Indicates that an ERROR was encountered while notifying an change      * in the Event state      */
name|ERROR
block|}
end_enum

end_unit

