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

begin_comment
comment|/**  * Namespace prefix mapping service. The {@link #getNamespace(String)} and  * {@link #getPrefix(String)} methods are intended to be used for  * exact mappings. The {@link #getFullName(String)} and   * {@link #getShortName(String)} to their best to map between URIs and shortNames  * but do have reasonable defaults if no mappings are present.  * Users that do want more control over such conversions should use the  * {@link NamespaceMappingUtils#getNamespace(String)} and   * {@link NamespaceMappingUtils#getPrefix(String)} methods and than use the  * {@link #getNamespace(String)} and {@link #getPrefix(String)} methods on the  * results.  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|NamespacePrefixService
extends|extends
name|NamespacePrefixProvider
block|{
comment|/**      * Setter for a prefix namespace mapping. If the prefix was already present      * than the previous mapped namespace is returned. Parsed prefixes      * are validated using {@link NamespaceMappingUtils#checkPrefix(String)}      * and namespaces using {@link NamespaceMappingUtils#checkNamespace(String)}      * @param prefix the prefix. Checked against the {@link #PREFIX_VALIDATION_PATTERN}      * @param namespace the namespace. Parsed namespaces MUST end with '/' or '#'      * or ':' if starting with 'urn:'. Additional validations are optional      * @return the previous mapped namespace or<code>null</code> if none      * @throws IllegalArgumentException if the parsed prefix and namespaces are      * not valid. This is checked by using {@link NamespaceMappingUtils#checkPrefix(String)}      * and {@link NamespaceMappingUtils#checkNamespace(String)}      */
name|String
name|setPrefix
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|namespace
parameter_list|)
function_decl|;
comment|/**       * Converts an sort name '{prefix}:{localname}' to the full URI. If an      * URI is parsed the parsed value is returned unchanged. If the parsed      * prefix is not known, than<code>null</code> is returned.<p>      * The detection if a '{prefix}:{localname}' was parsed uses the following rules:<ul>      *<li> shortname.indexOf(':')> 0 || shortname.charAt(0) != '/'      *<li> {prefix} != 'urn'      *<li> {localname}.charAt(0) != '/'      *<ul>      * In case a '{prefix}:{localname}' was detected the parsed value is processed      * otherwise the parsed value is returned.      * @param shortName or an URI      * @return in case an URI was parsed than the parsed value. Otherwise the      * full URI for the shortName or<code>null</code> if the used {prefix} is      * not defined.      */
name|String
name|getFullName
parameter_list|(
name|String
name|shortNameOrUri
parameter_list|)
function_decl|;
comment|/**      * The short name for the parsed URI.<p>For the detection of the       * namespace the last index of '#' or '/' is used. If the namespace is      * not mapped or the parsed uri does not contain '#' or '/' than the      * parsed value is returned      * @param uri the full uri      * @return the short name in case a prefix mapping for the namespace of the      * parsed URI was defined. Otherwise the parsed URI is returned.      */
name|String
name|getShortName
parameter_list|(
name|String
name|uri
parameter_list|)
function_decl|;
block|}
end_interface

end_unit
