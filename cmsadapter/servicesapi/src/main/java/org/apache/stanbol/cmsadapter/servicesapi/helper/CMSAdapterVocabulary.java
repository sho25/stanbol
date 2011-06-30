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
name|helper
package|;
end_package

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|ResourceFactory
import|;
end_import

begin_comment
comment|/**  * This class contains necessary {@link Resource}s and {@link Property}ies that are used in the scope of CMS  * Adapter component.  *   * @author srdc  *   */
end_comment

begin_class
specifier|public
class|class
name|CMSAdapterVocabulary
block|{
specifier|private
specifier|static
specifier|final
name|String
name|RESOURCE_DELIMITER
init|=
literal|"#"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PATH_DELIMITER
init|=
literal|"/"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_NS_URI
init|=
literal|"http://org.apache.stanbol"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CMS_ADAPTER_VOCABULARY_PREFIX
init|=
literal|"cmsad"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CMS_ADAPTER_VOCABULARY_URI
init|=
name|DEFAULT_NS_URI
operator|+
name|PATH_DELIMITER
operator|+
name|CMS_ADAPTER_VOCABULARY_PREFIX
operator|+
name|RESOURCE_DELIMITER
decl_stmt|;
comment|/*      * Property to represent the path of the CMS item      */
specifier|public
specifier|static
specifier|final
name|String
name|CMSAD_PATH_PROP_NAME
init|=
literal|"path"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|CMSAD_PATH_PROP
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CMSAD_PATH_PROP_NAME
argument_list|)
decl_stmt|;
comment|/*      * Property to keep mapping between resource name and its unique reference      */
specifier|private
specifier|static
specifier|final
name|String
name|CMSAD_RESOURCE_REF_PROP_NAME
init|=
literal|"resourceUniqueRef"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|CMSAD_RESOURCE_REF_PROP
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CMSAD_RESOURCE_REF_PROP_NAME
argument_list|)
decl_stmt|;
comment|/*      * Property to keep source object type definition of a datatype property or object property      */
specifier|private
specifier|static
specifier|final
name|String
name|CMSAD_PROPERTY_SOURCE_OBJECT_PROP_NAME
init|=
literal|"sourceObject"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|CMSAD_PROPERTY_SOURCE_OBJECT_PROP
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CMSAD_PROPERTY_SOURCE_OBJECT_PROP_NAME
argument_list|)
decl_stmt|;
comment|/**      * Property to access metadata of a specific cms object.      */
specifier|private
specifier|static
specifier|final
name|String
name|CMSAD_PROPERTY_CONTENT_ITEM_REF_NAME
init|=
literal|"contentItemRef"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|CMSAD_PROPERTY_CONTENT_ITEM_REF
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CMSAD_PROPERTY_CONTENT_ITEM_REF_NAME
argument_list|)
decl_stmt|;
comment|/*      * Properties to store connection info in the ontology      */
comment|// connection info resource
specifier|private
specifier|static
specifier|final
name|String
name|CONNECTION_INFO_RES_NAME
init|=
literal|"connectionInfo"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|CONNECTION_INFO_RES
init|=
name|resource
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CONNECTION_INFO_RES_NAME
argument_list|)
decl_stmt|;
comment|// workspace property
specifier|private
specifier|static
specifier|final
name|String
name|CONNECTION_WORKSPACE_PROP_NAME
init|=
literal|"workspace"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|CONNECTION_WORKSPACE_PROP
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CONNECTION_WORKSPACE_PROP_NAME
argument_list|)
decl_stmt|;
comment|// username property
specifier|private
specifier|static
specifier|final
name|String
name|CONNECTION_USERNAME_PROP_NAME
init|=
literal|"username"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|CONNECTION_USERNAME_PROP
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CONNECTION_USERNAME_PROP_NAME
argument_list|)
decl_stmt|;
comment|// password property
specifier|private
specifier|static
specifier|final
name|String
name|CONNECTION_PASSWORD_PROP_NAME
init|=
literal|"password"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|CONNECTION_PASSWORD_PROP
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CONNECTION_PASSWORD_PROP_NAME
argument_list|)
decl_stmt|;
comment|// workspace url property
specifier|private
specifier|static
specifier|final
name|String
name|CONNECTION_WORKSPACE_URL_PROP_NAME
init|=
literal|"workspaceURL"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|CONNECTION_WORKSPACE_URL_PROP
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CONNECTION_WORKSPACE_URL_PROP_NAME
argument_list|)
decl_stmt|;
comment|// connection type property
specifier|private
specifier|static
specifier|final
name|String
name|CONNECTION_TYPE_PROP_NAME
init|=
literal|"connectionType"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|CONNECTION_TYPE_PROP
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|CONNECTION_TYPE_PROP_NAME
argument_list|)
decl_stmt|;
comment|/*      * Properties to store bridge definitions      */
comment|// bridge definitions resource
specifier|private
specifier|static
specifier|final
name|String
name|BRIDGE_DEFINITIONS_RES_NAME
init|=
literal|"bridgeDefinitions"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|BRIDGE_DEFINITIONS_RES
init|=
name|resource
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|BRIDGE_DEFINITIONS_RES_NAME
argument_list|)
decl_stmt|;
comment|// property to keep bridge definitions
specifier|private
specifier|static
specifier|final
name|String
name|BRIDGE_DEFINITIONS_CONTENT_PROP_NAME
init|=
literal|"content"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|BRIDGE_DEFINITIONS_CONTENT_PROP
init|=
name|property
argument_list|(
name|CMS_ADAPTER_VOCABULARY_URI
argument_list|,
name|BRIDGE_DEFINITIONS_CONTENT_PROP_NAME
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Property
name|property
parameter_list|(
name|String
name|URI
parameter_list|,
name|String
name|local
parameter_list|)
block|{
name|URI
operator|=
name|OntologyResourceHelper
operator|.
name|addResourceDelimiter
argument_list|(
name|URI
argument_list|)
expr_stmt|;
return|return
name|ResourceFactory
operator|.
name|createProperty
argument_list|(
name|URI
argument_list|,
name|local
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|Resource
name|resource
parameter_list|(
name|String
name|URI
parameter_list|,
name|String
name|local
parameter_list|)
block|{
name|URI
operator|=
name|OntologyResourceHelper
operator|.
name|addResourceDelimiter
argument_list|(
name|URI
argument_list|)
expr_stmt|;
return|return
name|ResourceFactory
operator|.
name|createResource
argument_list|(
name|URI
operator|+
name|local
argument_list|)
return|;
block|}
block|}
end_class

end_unit

