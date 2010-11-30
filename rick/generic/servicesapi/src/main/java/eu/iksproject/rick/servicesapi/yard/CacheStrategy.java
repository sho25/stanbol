begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  *   */
end_comment

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
name|yard
package|;
end_package

begin_enum
specifier|public
enum|enum
name|CacheStrategy
block|{
comment|/** 	 * All entities of this site should be cached 	 */
name|all
block|,
comment|/** 	 * Only entities are cached that where retrieved by some past request 	 */
name|used
block|,
comment|/** 	 * Entities of this site are not cached 	 */
name|none
block|}
end_enum

end_unit

