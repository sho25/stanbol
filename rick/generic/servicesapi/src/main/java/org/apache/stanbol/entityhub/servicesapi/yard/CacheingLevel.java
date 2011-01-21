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
name|yard
package|;
end_package

begin_enum
specifier|public
enum|enum
name|CacheingLevel
block|{
comment|/**      * This indicated that a document in the cache confirms to the specification      * as stored within the cache. This configuration is stored within the cache      * and only be changed for an empty cache.<p>      */
name|base
block|,
comment|/**      * If a Document is updated in the cache, than there may be more information      * be stored as defined by the initial creation of a cache.<p> This level indicates      * that a document includes all the field defined for {@link #base} but      * also some additional information.      */
name|special
block|}
end_enum

end_unit

