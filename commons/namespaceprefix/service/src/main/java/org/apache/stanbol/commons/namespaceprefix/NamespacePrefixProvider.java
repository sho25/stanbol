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
name|namespaceprefix
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_interface
specifier|public
interface|interface
name|NamespacePrefixProvider
block|{
comment|/**      * Getter for the namespace for the parsed prefix      * @param prefix the prefix. '' for the default namepace      * @return the namespace or<code>null</code> if the prefix is not known      */
name|String
name|getNamespace
parameter_list|(
name|String
name|prefix
parameter_list|)
function_decl|;
comment|/**      * Getter for the prefix for a namespace. Note that a namespace might be       * mapped to multiple prefixes      * @param namespace the namespace      * @return the prefix or<code>null</code>      */
name|String
name|getPrefix
parameter_list|(
name|String
name|namespace
parameter_list|)
function_decl|;
comment|/**      * Getter for all prefixes for the parsed namespace      * @param namespace the namespace      * @return the prefixes. An empty list if none      */
name|List
argument_list|<
name|String
argument_list|>
name|getPrefixes
parameter_list|(
name|String
name|namespace
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

