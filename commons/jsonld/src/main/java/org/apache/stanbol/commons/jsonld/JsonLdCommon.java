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
name|jsonld
package|;
end_package

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

begin_class
specifier|public
specifier|abstract
class|class
name|JsonLdCommon
block|{
specifier|public
specifier|static
specifier|final
name|String
name|CONTEXT
init|=
literal|"@context"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|COERCION
init|=
literal|"@coercion"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|IRI
init|=
literal|"@iri"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROFILE
init|=
literal|"@profile"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SUBJECT
init|=
literal|"@"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TYPES
init|=
literal|"#types"
decl_stmt|;
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|namespacePrefixMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * Flag to control whether the namespace prefix map should be used to shorten URIs to CURIEs during      * serialization. Default value is<code>true</code>.      */
specifier|protected
name|boolean
name|applyNamespaces
init|=
literal|true
decl_stmt|;
comment|/**      * Get the known namespace to prefix mapping.      *       * @return A {@link Map} from namespace String to prefix String.      */
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getNamespacePrefixMap
parameter_list|()
block|{
return|return
name|this
operator|.
name|namespacePrefixMap
return|;
block|}
comment|/**      * Sets the known namespaces for the serializer.      *       * @param namespacePrefixMap      *            A {@link Map} from namespace String to prefix String.      */
specifier|public
name|void
name|setNamespacePrefixMap
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|namespacePrefixMap
parameter_list|)
block|{
name|this
operator|.
name|namespacePrefixMap
operator|=
name|namespacePrefixMap
expr_stmt|;
block|}
comment|/**      * Adds a new namespace and its prefix to the list of used namespaces for this JSON-LD instance.      *       * @param namespace      *            A namespace IRI.      * @param prefix      *            A prefix to use and identify this namespace in serialized JSON-LD.      */
specifier|public
name|void
name|addNamespacePrefix
parameter_list|(
name|String
name|namespace
parameter_list|,
name|String
name|prefix
parameter_list|)
block|{
name|namespacePrefixMap
operator|.
name|put
argument_list|(
name|namespace
argument_list|,
name|prefix
argument_list|)
expr_stmt|;
block|}
comment|/**      * Flag to control whether the namespace prefix map should be used to shorten IRIs to prefix notation      * during serialization. Default value is<code>true</code>.      *<p>      * If you already put values into this JSON-LD instance with prefix notation, you should set this to      *<code>false</code> before starting the serialization.      *       * @return<code>True</code> if namespaces are applied during serialization,<code>false</code> otherwise.      */
specifier|public
name|boolean
name|isApplyNamespaces
parameter_list|()
block|{
return|return
name|applyNamespaces
return|;
block|}
comment|/**      * Control whether namespaces from the namespace prefix map are applied to URLs during serialization.      *<p>      * Set this to<code>false</code> if you already have shortened IRIs with prefixes.      *       * @param applyNamespaces      */
specifier|public
name|void
name|setApplyNamespaces
parameter_list|(
name|boolean
name|applyNamespaces
parameter_list|)
block|{
name|this
operator|.
name|applyNamespaces
operator|=
name|applyNamespaces
expr_stmt|;
block|}
comment|/**      * Convert URI to CURIE if namespaces should be applied and CURIEs to URIs if namespaces should not be      * applied.      *       * @param uri      *            That may be in CURIE form.      * @return      */
specifier|protected
name|String
name|handleCURIEs
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|applyNamespaces
condition|)
block|{
name|uri
operator|=
name|doCURIE
argument_list|(
name|uri
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|uri
operator|=
name|unCURIE
argument_list|(
name|uri
argument_list|)
expr_stmt|;
block|}
return|return
name|uri
return|;
block|}
specifier|public
name|String
name|doCURIE
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
for|for
control|(
name|String
name|namespace
range|:
name|namespacePrefixMap
operator|.
name|keySet
argument_list|()
control|)
block|{
name|String
name|prefix
init|=
name|namespacePrefixMap
operator|.
name|get
argument_list|(
name|namespace
argument_list|)
operator|+
literal|":"
decl_stmt|;
if|if
condition|(
operator|!
name|uri
operator|.
name|startsWith
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|uri
operator|=
name|uri
operator|.
name|replace
argument_list|(
name|namespace
argument_list|,
name|prefix
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|uri
return|;
block|}
specifier|public
name|String
name|unCURIE
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
for|for
control|(
name|String
name|namespace
range|:
name|namespacePrefixMap
operator|.
name|keySet
argument_list|()
control|)
block|{
name|String
name|prefix
init|=
name|namespacePrefixMap
operator|.
name|get
argument_list|(
name|namespace
argument_list|)
operator|+
literal|":"
decl_stmt|;
if|if
condition|(
name|uri
operator|.
name|startsWith
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|uri
operator|=
name|uri
operator|.
name|replace
argument_list|(
name|prefix
argument_list|,
name|namespace
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|uri
return|;
block|}
block|}
end_class

end_unit

