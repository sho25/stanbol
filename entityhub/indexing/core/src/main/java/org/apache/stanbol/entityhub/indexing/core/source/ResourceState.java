begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  *   */
end_comment

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
name|source
package|;
end_package

begin_comment
comment|/**  * State of resources managed by the ResourceLoader  * @author Rupert Westenthaler  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|ResourceState
block|{
comment|/**      * Resources that are registered but not yet processed      */
name|REGISTERED
block|,
comment|/**      * Resources that are currently processed      */
name|LOADING
block|,
comment|/**      * Resources that where successfully loaded      */
name|LOADED
block|,
comment|/**      * Resources that where ignored      */
name|IGNORED
block|,
comment|/**      * Indicates an Error while processing a resource      */
name|ERROR
block|}
end_enum

end_unit

