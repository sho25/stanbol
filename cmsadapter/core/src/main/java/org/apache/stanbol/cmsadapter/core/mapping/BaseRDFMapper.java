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
name|core
operator|.
name|mapping
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|NonLiteral
import|;
end_import

begin_import
import|import
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
name|helper
operator|.
name|CMSAdapterVocabulary
import|;
end_import

begin_import
import|import
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
name|mapping
operator|.
name|RDFMapper
import|;
end_import

begin_comment
comment|/**  * This class contains common methods to be used in {@link RDFMapper} implementations  *   * @author suat  *   */
end_comment

begin_class
specifier|public
class|class
name|BaseRDFMapper
block|{
comment|/**      * Obtains the name for the CMS object based on the RDF data provided. If      * {@link CMSAdapterVocabulary#CMS_OBJECT_NAME} assertion is already provided, its value is returned;      * otherwise the local name is extracted from the URI given.      *       * @param subject      *            {@link NonLiteral} representing the URI of the CMS object resource      * @param graph      *            {@link MGraph} holding the resources      * @return the name for the CMS object to be created/updated in the repository      */
specifier|protected
name|String
name|getObjectName
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|String
name|objectName
init|=
name|RDFBridgeHelper
operator|.
name|getResourceStringValue
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|graph
argument_list|)
decl_stmt|;
if|if
condition|(
name|objectName
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
name|RDFBridgeHelper
operator|.
name|extractLocalNameFromURI
argument_list|(
name|subject
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|objectName
return|;
block|}
block|}
comment|/**      * Obtains the path for the CMS object based on the name of the object and RDF provided. If      * {@link CMSAdapterVocabulary#CMS_OBJECT_PATH} assertion is already provided, its value is returned;      * otherwise its name is returned together with a preceding "/" character. This means CMS object will be      * searched under the root path      *       * @param subject      *            {@link NonLiteral} representing the URI of the CMS object resource      * @param name      *            name of the CMS object to be created in the repository      * @param graph      *            {@link MGraph} holding the resource      * @return the path for the CMS object to be created/updated in the repository      */
specifier|protected
name|String
name|getObjectPath
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|String
name|name
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|String
name|objectPath
init|=
name|RDFBridgeHelper
operator|.
name|getResourceStringValue
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PATH
argument_list|,
name|graph
argument_list|)
decl_stmt|;
if|if
condition|(
name|objectPath
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
literal|"/"
operator|+
name|name
return|;
block|}
else|else
block|{
return|return
name|objectPath
return|;
block|}
block|}
block|}
end_class

end_unit

