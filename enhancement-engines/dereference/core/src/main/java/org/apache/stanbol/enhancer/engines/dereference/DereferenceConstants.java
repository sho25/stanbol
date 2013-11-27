begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|dereference
package|;
end_package

begin_comment
comment|/**  * Define configuration parameters for Dereference engines  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|DereferenceConstants
block|{
comment|/**      * Property used to configure the fields that should be dereferenced.<p>      * DereferenceEngines need to support a list of URIs but may also support more      * complex syntax (such as the Entityhub FiedMapping). However parsing a      * list of properties URIs MUST BE still valid.<p>      * Support for Namespace prefixes via the Stanbol Namespace Prefix Service      * is optional. If unknown prefixes are used or prefixes are not supported      * the Engine is expected to throw a       * {@link org.osgi.service.cm.ConfigurationException} during activation      */
name|String
name|DEREFERENCE_ENTITIES_FIELDS
init|=
literal|"enhancer.engines.dereference.fields"
decl_stmt|;
comment|/**      * Property used to configure LDPath statements. Those are applied using      * each referenced Entity as Context.<p>      * DereferenceEngines that can not support LDPath are expected to throw a      * {@link org.osgi.service.cm.ConfigurationException} if values are set      * for this property.      */
name|String
name|DEREFERENCE_ENTITIES_LDPATH
init|=
literal|"enhancer.engines.dereference.ldpath"
decl_stmt|;
block|}
end_interface

end_unit

