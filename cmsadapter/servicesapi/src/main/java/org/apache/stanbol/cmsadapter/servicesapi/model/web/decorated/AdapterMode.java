begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
package|;
end_package

begin_enum
specifier|public
enum|enum
name|AdapterMode
block|{
comment|/**      * In<b>ONLINE</b> mode all the requests that require repository access is expected to successfully connect to      * repository. Underlying objects from package {@linkplain org.apache.stanbol.cmsadapter.servicesapi.model.web}      * are not used for accessing.      */
name|ONLINE
block|,
comment|/**      * In<b>TOLERATED OFFLINE</b> mode, a repository connection error will not be thrown instead       * existing underlying objects from package {@linkplain org.apache.stanbol.cmsadapter.servicesapi.model.web}      * will be used.      */
name|TOLERATED_OFFLINE
block|,
comment|/**      * In<b>STRICT OFFLINE</b> repository is never accessed. All the information is expected to provided by      * underlying objects from package {@linkplain org.apache.stanbol.cmsadapter.servicesapi.model.web}.      */
name|STRICT_OFFLINE
block|}
end_enum

end_unit

