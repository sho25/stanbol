begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|//
end_comment

begin_comment
comment|// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs
end_comment

begin_comment
comment|// See<a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
end_comment

begin_comment
comment|// Any modifications to this file will be lost upon recompilation of the source schema.
end_comment

begin_comment
comment|// Generated on: 2011.04.29 at 11:44:48 AM EEST
end_comment

begin_comment
comment|//
end_comment

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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigInteger
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|JAXBElement
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|annotation
operator|.
name|XmlElementDecl
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|annotation
operator|.
name|XmlRegistry
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|namespace
operator|.
name|QName
import|;
end_import

begin_comment
comment|/**  * This object contains factory methods for each   * Java content interface and Java element interface   * generated in the org.apache.stanbol.cmsadapter.servicesapi.model.web package.   *<p>An ObjectFactory allows you to programatically   * construct new instances of the Java representation   * for XML content. The Java representation of XML   * content can consist of schema derived interfaces   * and classes representing the binding of schema   * type definitions, element declarations and model   * groups.  Factory methods for each of these are   * provided in this class.  *   */
end_comment

begin_class
annotation|@
name|XmlRegistry
specifier|public
class|class
name|ObjectFactory
block|{
specifier|private
specifier|final
specifier|static
name|QName
name|_UniqueRef_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"uniqueRef"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_Namespace_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"namespace"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_ContainerObjectRef_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"containerObjectRef"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_ContentObject_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"contentObject"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_Password_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"password"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_Localname_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"localname"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_ConnectionType_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"connectionType"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_ClassificationObject_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"classificationObject"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_ValueConstraint_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"valueConstraint"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_AllowedObjectTypeDefRef_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"allowedObjectTypeDefRef"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_ParentRef_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"parentRef"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_ObjectTypeRef_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"objectTypeRef"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_OntologyURI_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"ontologyURI"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_Cardinality_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"cardinality"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_WorkspaceName_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"workspaceName"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_Required_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"required"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_Value_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"value"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_Path_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"path"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_SourceObjectTypeRef_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"sourceObjectTypeRef"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_RepositoryURL_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"repositoryURL"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_PropertyType_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"propertyType"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_Username_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"username"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|QName
name|_Annotation_QNAME
init|=
operator|new
name|QName
argument_list|(
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
literal|"annotation"
argument_list|)
decl_stmt|;
comment|/**      * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.apache.stanbol.cmsadapter.servicesapi.model.web      *       */
specifier|public
name|ObjectFactory
parameter_list|()
block|{     }
comment|/**      * Create an instance of {@link ObjectTypeDefinitions }      *       */
specifier|public
name|ObjectTypeDefinitions
name|createObjectTypeDefinitions
parameter_list|()
block|{
return|return
operator|new
name|ObjectTypeDefinitions
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link PropertySemantics }      *       */
specifier|public
name|PropertySemantics
name|createPropertySemantics
parameter_list|()
block|{
return|return
operator|new
name|PropertySemantics
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link CMSObjects }      *       */
specifier|public
name|CMSObjects
name|createCMSObjects
parameter_list|()
block|{
return|return
operator|new
name|CMSObjects
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link ChildObjectDefinition }      *       */
specifier|public
name|ChildObjectDefinition
name|createChildObjectDefinition
parameter_list|()
block|{
return|return
operator|new
name|ChildObjectDefinition
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link Property }      *       */
specifier|public
name|Property
name|createProperty
parameter_list|()
block|{
return|return
operator|new
name|Property
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link ContentObjects }      *       */
specifier|public
name|ContentObjects
name|createContentObjects
parameter_list|()
block|{
return|return
operator|new
name|ContentObjects
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link PropertyDefinition }      *       */
specifier|public
name|PropertyDefinition
name|createPropertyDefinition
parameter_list|()
block|{
return|return
operator|new
name|PropertyDefinition
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link ObjectTypeDefinition }      *       */
specifier|public
name|ObjectTypeDefinition
name|createObjectTypeDefinition
parameter_list|()
block|{
return|return
operator|new
name|ObjectTypeDefinition
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link CMSObject }      *       */
specifier|public
name|CMSObject
name|createCMSObject
parameter_list|()
block|{
return|return
operator|new
name|CMSObject
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link ConnectionInfo }      *       */
specifier|public
name|ConnectionInfo
name|createConnectionInfo
parameter_list|()
block|{
return|return
operator|new
name|ConnectionInfo
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link ClassificationObjects }      *       */
specifier|public
name|ClassificationObjects
name|createClassificationObjects
parameter_list|()
block|{
return|return
operator|new
name|ClassificationObjects
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link PropertyAnnotation }      *       */
specifier|public
name|PropertyAnnotation
name|createPropertyAnnotation
parameter_list|()
block|{
return|return
operator|new
name|PropertyAnnotation
argument_list|()
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"uniqueRef"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createUniqueRef
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_UniqueRef_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"namespace"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createNamespace
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_Namespace_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"containerObjectRef"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createContainerObjectRef
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_ContainerObjectRef_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link CMSObject }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"contentObject"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|CMSObject
argument_list|>
name|createContentObject
parameter_list|(
name|CMSObject
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|CMSObject
argument_list|>
argument_list|(
name|_ContentObject_QNAME
argument_list|,
name|CMSObject
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"password"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createPassword
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_Password_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"localname"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createLocalname
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_Localname_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"connectionType"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createConnectionType
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_ConnectionType_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link CMSObject }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"classificationObject"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|CMSObject
argument_list|>
name|createClassificationObject
parameter_list|(
name|CMSObject
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|CMSObject
argument_list|>
argument_list|(
name|_ClassificationObject_QNAME
argument_list|,
name|CMSObject
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"valueConstraint"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createValueConstraint
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_ValueConstraint_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"allowedObjectTypeDefRef"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createAllowedObjectTypeDefRef
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_AllowedObjectTypeDefRef_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"parentRef"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createParentRef
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_ParentRef_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"objectTypeRef"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createObjectTypeRef
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_ObjectTypeRef_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"ontologyURI"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createOntologyURI
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_OntologyURI_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link BigInteger }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"cardinality"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|BigInteger
argument_list|>
name|createCardinality
parameter_list|(
name|BigInteger
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|BigInteger
argument_list|>
argument_list|(
name|_Cardinality_QNAME
argument_list|,
name|BigInteger
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"workspaceName"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createWorkspaceName
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_WorkspaceName_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link Boolean }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"required"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|Boolean
argument_list|>
name|createRequired
parameter_list|(
name|Boolean
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|Boolean
argument_list|>
argument_list|(
name|_Required_QNAME
argument_list|,
name|Boolean
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"value"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_Value_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"path"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createPath
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_Path_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"sourceObjectTypeRef"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createSourceObjectTypeRef
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_SourceObjectTypeRef_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"repositoryURL"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createRepositoryURL
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_RepositoryURL_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link PropType }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"propertyType"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|PropType
argument_list|>
name|createPropertyType
parameter_list|(
name|PropType
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|PropType
argument_list|>
argument_list|(
name|_PropertyType_QNAME
argument_list|,
name|PropType
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link String }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"username"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|String
argument_list|>
name|createUsername
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|String
argument_list|>
argument_list|(
name|_Username_QNAME
argument_list|,
name|String
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
comment|/**      * Create an instance of {@link JAXBElement }{@code<}{@link AnnotationType }{@code>}}      *       */
annotation|@
name|XmlElementDecl
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|name
operator|=
literal|"annotation"
argument_list|)
specifier|public
name|JAXBElement
argument_list|<
name|AnnotationType
argument_list|>
name|createAnnotation
parameter_list|(
name|AnnotationType
name|value
parameter_list|)
block|{
return|return
operator|new
name|JAXBElement
argument_list|<
name|AnnotationType
argument_list|>
argument_list|(
name|_Annotation_QNAME
argument_list|,
name|AnnotationType
operator|.
name|class
argument_list|,
literal|null
argument_list|,
name|value
argument_list|)
return|;
block|}
block|}
end_class

end_unit

