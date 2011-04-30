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
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|MappingEngine
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
name|NamingStrategy
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
name|model
operator|.
name|mapping
operator|.
name|BridgeDefinitions
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
name|model
operator|.
name|web
operator|.
name|ConnectionInfo
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
name|model
operator|.
name|web
operator|.
name|ObjectFactory
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
name|model
operator|.
name|web
operator|.
name|CMSObject
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
name|model
operator|.
name|web
operator|.
name|ObjectTypeDefinition
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
name|model
operator|.
name|web
operator|.
name|PropType
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
name|model
operator|.
name|web
operator|.
name|PropertyDefinition
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
name|repository
operator|.
name|RepositoryAccessException
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
name|ontologymanager
operator|.
name|store
operator|.
name|rest
operator|.
name|client
operator|.
name|RestClient
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
name|ontologymanager
operator|.
name|store
operator|.
name|rest
operator|.
name|client
operator|.
name|RestClientException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
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
name|enhanced
operator|.
name|UnsupportedPolymorphismException
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
name|ontology
operator|.
name|DatatypeProperty
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
name|ontology
operator|.
name|Individual
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
name|ontology
operator|.
name|ObjectProperty
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
name|ontology
operator|.
name|OntClass
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
name|ontology
operator|.
name|OntModel
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
name|ontology
operator|.
name|OntModelSpec
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
name|ontology
operator|.
name|OntProperty
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
name|ModelFactory
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
name|RDFList
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
name|ResIterator
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
name|vocabulary
operator|.
name|XSD
import|;
end_import

begin_class
specifier|public
class|class
name|OntologyResourceHelper
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|OntologyResourceHelper
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|OntModel
name|ontModel
decl_stmt|;
specifier|private
name|String
name|ontologyURI
decl_stmt|;
specifier|private
name|NamingStrategy
name|namingStrategy
decl_stmt|;
specifier|public
name|OntologyResourceHelper
parameter_list|(
name|MappingEngine
name|mappingEngine
parameter_list|)
block|{
name|this
operator|.
name|ontModel
operator|=
name|mappingEngine
operator|.
name|getOntModel
argument_list|()
expr_stmt|;
name|this
operator|.
name|ontologyURI
operator|=
name|mappingEngine
operator|.
name|getOntologyURI
argument_list|()
expr_stmt|;
name|this
operator|.
name|namingStrategy
operator|=
name|mappingEngine
operator|.
name|getNamingStrategy
argument_list|()
expr_stmt|;
block|}
specifier|public
name|OntologyResourceHelper
parameter_list|(
name|OntModel
name|ontModel
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|NamingStrategy
name|namingStrategy
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|this
operator|.
name|ontModel
operator|=
name|ontModel
expr_stmt|;
name|this
operator|.
name|ontologyURI
operator|=
name|ontologyURI
expr_stmt|;
name|this
operator|.
name|namingStrategy
operator|=
name|namingStrategy
expr_stmt|;
block|}
comment|/**      * @param reference      *            Unique reference of object for which the {@link OntClass} is requested      * @return {@link OntClass} if there is an already created class for cms object whose identifier got as a      *         reference, otherwise<code>null</code>.      */
specifier|public
name|OntClass
name|getOntClassByReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|ResIterator
name|it
init|=
name|ontModel
operator|.
name|listResourcesWithProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|reference
argument_list|)
decl_stmt|;
name|Resource
name|resource
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|resource
operator|=
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|resource
operator|.
name|as
argument_list|(
name|OntClass
operator|.
name|class
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedPolymorphismException
name|e
parameter_list|)
block|{              }
block|}
return|return
literal|null
return|;
block|}
comment|/**      * @param cmsObject      *            {@link CMSObject} object for which the {@link OntClass} is requested      * @return {@link OntClass} if there is an already created class for the cms object, otherwise      *<code>null</code>.      */
specifier|public
name|OntClass
name|getOntClassByCMSObject
parameter_list|(
name|CMSObject
name|cmsObject
parameter_list|)
block|{
return|return
name|getOntClassByReference
argument_list|(
name|cmsObject
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Creates an {@link OntClass} for a unique reference or returns the existing one.      *       * @param reference      *            Unique reference of object for which the {@link OntClass} is requested.      * @return {@link OntClass} instance.      */
specifier|public
name|OntClass
name|createOntClassByReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Creating OWL Class for reference {}"
argument_list|,
name|reference
argument_list|)
expr_stmt|;
name|OntClass
name|klass
init|=
name|getOntClassByReference
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|klass
operator|==
literal|null
condition|)
block|{
name|String
name|classURI
init|=
name|namingStrategy
operator|.
name|getClassName
argument_list|(
name|ontologyURI
argument_list|,
name|reference
argument_list|)
decl_stmt|;
name|klass
operator|=
name|ontModel
operator|.
name|createClass
argument_list|(
name|classURI
argument_list|)
expr_stmt|;
name|klass
operator|.
name|addProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|reference
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"OWL Class {} not found for reference. Creating new one..."
argument_list|,
name|reference
argument_list|)
expr_stmt|;
block|}
return|return
name|klass
return|;
block|}
comment|/**      * Creates an {@link OntClass} for a {@link CMSObject} object or returns the existing one.      *       * @param cmsObject      *            {@link CMSObject} object for which the {@link OntClass} is requested.      * @return {@link OntClass} instance.      */
specifier|public
name|OntClass
name|createOntClassByCMSObject
parameter_list|(
name|CMSObject
name|cmsObject
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Getting OWL Class for cms object = {}"
argument_list|,
name|cmsObject
argument_list|)
expr_stmt|;
name|OntClass
name|klass
init|=
name|getOntClassByCMSObject
argument_list|(
name|cmsObject
argument_list|)
decl_stmt|;
if|if
condition|(
name|klass
operator|==
literal|null
condition|)
block|{
name|String
name|classURI
init|=
name|namingStrategy
operator|.
name|getClassName
argument_list|(
name|ontologyURI
argument_list|,
name|cmsObject
argument_list|)
decl_stmt|;
name|klass
operator|=
name|ontModel
operator|.
name|createClass
argument_list|(
name|classURI
argument_list|)
expr_stmt|;
name|klass
operator|.
name|addProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|cmsObject
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"OWL Class {} not found creating..."
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
block|}
return|return
name|klass
return|;
block|}
comment|/**      * Creates an {@link OntClass} for a {@link ObjectTypeDefinition} object or returns the existing one.      *       * @param objectTypeDefinition      *            {@link ObjectTypeDefinition} object for which the {@link OntClass} is requested.      * @return {@link OntClass} instance.      */
specifier|public
name|OntClass
name|createOntClassByObjectTypeDefinition
parameter_list|(
name|ObjectTypeDefinition
name|objectTypeDefinition
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Getting OWL Class for node type {}"
argument_list|,
name|objectTypeDefinition
argument_list|)
expr_stmt|;
name|OntClass
name|klass
init|=
name|getOntClassByReference
argument_list|(
name|objectTypeDefinition
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|klass
operator|==
literal|null
condition|)
block|{
name|String
name|classURI
init|=
name|namingStrategy
operator|.
name|getClassName
argument_list|(
name|ontologyURI
argument_list|,
name|objectTypeDefinition
argument_list|)
decl_stmt|;
name|klass
operator|=
name|ontModel
operator|.
name|createClass
argument_list|(
name|classURI
argument_list|)
expr_stmt|;
name|klass
operator|.
name|addProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|objectTypeDefinition
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"OWL Class {} not found, creating new one..."
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
block|}
return|return
name|klass
return|;
block|}
specifier|public
name|OntProperty
name|getPropertyByReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|ResIterator
name|it
init|=
name|ontModel
operator|.
name|listResourcesWithProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|reference
argument_list|)
decl_stmt|;
name|Resource
name|resource
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|resource
operator|=
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|resource
operator|.
name|as
argument_list|(
name|OntProperty
operator|.
name|class
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedPolymorphismException
name|e
parameter_list|)
block|{              }
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|ObjectProperty
name|getObjectPropertyByReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|ResIterator
name|it
init|=
name|ontModel
operator|.
name|listResourcesWithProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|reference
argument_list|)
decl_stmt|;
name|Resource
name|resource
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|resource
operator|=
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|resource
operator|.
name|as
argument_list|(
name|ObjectProperty
operator|.
name|class
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedPolymorphismException
name|e
parameter_list|)
block|{              }
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|DatatypeProperty
name|getDatatypePropertyByReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|ResIterator
name|it
init|=
name|ontModel
operator|.
name|listResourcesWithProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|reference
argument_list|)
decl_stmt|;
name|Resource
name|resource
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|resource
operator|=
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|resource
operator|.
name|as
argument_list|(
name|DatatypeProperty
operator|.
name|class
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedPolymorphismException
name|e
parameter_list|)
block|{              }
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|ObjectProperty
name|createObjectPropertyByReference
parameter_list|(
name|String
name|reference
parameter_list|,
name|List
argument_list|<
name|Resource
argument_list|>
name|domains
parameter_list|,
name|List
argument_list|<
name|Resource
argument_list|>
name|ranges
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Creating Object property for reference {}"
argument_list|,
name|reference
argument_list|)
expr_stmt|;
name|ObjectProperty
name|objectProperty
init|=
name|getObjectPropertyByReference
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|objectProperty
operator|==
literal|null
condition|)
block|{
name|String
name|propertyURI
init|=
name|namingStrategy
operator|.
name|getObjectPropertyName
argument_list|(
name|ontologyURI
argument_list|,
name|reference
argument_list|)
decl_stmt|;
name|objectProperty
operator|=
name|ontModel
operator|.
name|createObjectProperty
argument_list|(
name|propertyURI
argument_list|)
expr_stmt|;
name|objectProperty
operator|.
name|addProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|reference
argument_list|)
expr_stmt|;
for|for
control|(
name|Resource
name|domain
range|:
name|domains
control|)
block|{
name|objectProperty
operator|.
name|addDomain
argument_list|(
name|domain
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Resource
name|range
range|:
name|ranges
control|)
block|{
name|objectProperty
operator|.
name|addRange
argument_list|(
name|range
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"ObjectProperty {} not found, creating new one..."
argument_list|,
name|propertyURI
argument_list|)
expr_stmt|;
block|}
return|return
name|objectProperty
return|;
block|}
specifier|public
name|ObjectProperty
name|createObjectPropertyByPropertyDefinition
parameter_list|(
name|PropertyDefinition
name|propertyDefinition
parameter_list|,
name|List
argument_list|<
name|Resource
argument_list|>
name|domains
parameter_list|,
name|List
argument_list|<
name|Resource
argument_list|>
name|ranges
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Creating Object property for property {}"
argument_list|,
name|propertyDefinition
argument_list|)
expr_stmt|;
name|ObjectProperty
name|objectProperty
init|=
name|getObjectPropertyByReference
argument_list|(
name|propertyDefinition
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|objectProperty
operator|==
literal|null
condition|)
block|{
name|String
name|propertyURI
init|=
name|namingStrategy
operator|.
name|getObjectPropertyName
argument_list|(
name|ontologyURI
argument_list|,
name|propertyDefinition
argument_list|)
decl_stmt|;
name|objectProperty
operator|=
name|ontModel
operator|.
name|createObjectProperty
argument_list|(
name|propertyURI
argument_list|)
expr_stmt|;
name|objectProperty
operator|.
name|addProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|propertyDefinition
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Resource
name|domain
range|:
name|domains
control|)
block|{
name|objectProperty
operator|.
name|addDomain
argument_list|(
name|domain
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Resource
name|range
range|:
name|ranges
control|)
block|{
name|objectProperty
operator|.
name|addRange
argument_list|(
name|range
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"ObjectProperty {} not found, creating new one..."
argument_list|,
name|propertyURI
argument_list|)
expr_stmt|;
block|}
return|return
name|objectProperty
return|;
block|}
specifier|public
name|DatatypeProperty
name|createDatatypePropertyByReference
parameter_list|(
name|String
name|reference
parameter_list|,
name|List
argument_list|<
name|Resource
argument_list|>
name|domains
parameter_list|,
name|PropType
name|propType
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Creating Datatype property for reference {}"
argument_list|,
name|reference
argument_list|)
expr_stmt|;
name|DatatypeProperty
name|datatypeProperty
init|=
name|getDatatypePropertyByReference
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|datatypeProperty
operator|==
literal|null
condition|)
block|{
name|String
name|propertyURI
init|=
name|namingStrategy
operator|.
name|getDataPropertyName
argument_list|(
name|ontologyURI
argument_list|,
name|reference
argument_list|)
decl_stmt|;
name|Resource
name|range
init|=
name|getDatatypePropertyRange
argument_list|(
name|propType
argument_list|)
decl_stmt|;
name|datatypeProperty
operator|=
name|ontModel
operator|.
name|createDatatypeProperty
argument_list|(
name|propertyURI
argument_list|)
expr_stmt|;
name|datatypeProperty
operator|.
name|addProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|reference
argument_list|)
expr_stmt|;
for|for
control|(
name|Resource
name|domain
range|:
name|domains
control|)
block|{
name|datatypeProperty
operator|.
name|addDomain
argument_list|(
name|domain
argument_list|)
expr_stmt|;
block|}
name|datatypeProperty
operator|.
name|addRange
argument_list|(
name|range
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Datatype property {} not found, creating new one..."
argument_list|,
name|propertyURI
argument_list|)
expr_stmt|;
block|}
return|return
name|datatypeProperty
return|;
block|}
specifier|public
name|DatatypeProperty
name|createDatatypePropertyByPropertyDefinition
parameter_list|(
name|PropertyDefinition
name|propertyDefinition
parameter_list|,
name|List
argument_list|<
name|Resource
argument_list|>
name|domains
parameter_list|)
block|{
name|DatatypeProperty
name|datatypeProperty
init|=
name|getDatatypePropertyByReference
argument_list|(
name|propertyDefinition
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|datatypeProperty
operator|==
literal|null
condition|)
block|{
name|String
name|propertyURI
init|=
name|namingStrategy
operator|.
name|getDataPropertyName
argument_list|(
name|ontologyURI
argument_list|,
name|propertyDefinition
argument_list|)
decl_stmt|;
name|Resource
name|range
init|=
name|getDatatypePropertyRange
argument_list|(
name|propertyDefinition
operator|.
name|getPropertyType
argument_list|()
argument_list|)
decl_stmt|;
name|datatypeProperty
operator|=
name|ontModel
operator|.
name|createDatatypeProperty
argument_list|(
name|propertyURI
argument_list|)
expr_stmt|;
name|datatypeProperty
operator|.
name|addProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|propertyDefinition
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Resource
name|domain
range|:
name|domains
control|)
block|{
name|datatypeProperty
operator|.
name|addDomain
argument_list|(
name|domain
argument_list|)
expr_stmt|;
block|}
name|datatypeProperty
operator|.
name|addRange
argument_list|(
name|range
argument_list|)
expr_stmt|;
block|}
return|return
name|datatypeProperty
return|;
block|}
specifier|public
name|OntClass
name|createUnionClass
parameter_list|(
name|List
argument_list|<
name|OntClass
argument_list|>
name|classes
parameter_list|)
block|{
name|RDFList
name|list
init|=
name|ontModel
operator|.
name|createList
argument_list|()
decl_stmt|;
for|for
control|(
name|OntClass
name|klass
range|:
name|classes
control|)
block|{
name|list
operator|.
name|cons
argument_list|(
name|klass
argument_list|)
expr_stmt|;
block|}
return|return
name|createUnionClass
argument_list|(
name|list
argument_list|)
return|;
block|}
specifier|public
name|OntClass
name|createUnionClass
parameter_list|(
name|RDFList
name|list
parameter_list|)
block|{
name|String
name|unionClassURI
init|=
name|namingStrategy
operator|.
name|getUnionClassURI
argument_list|(
name|ontologyURI
argument_list|,
name|list
argument_list|)
decl_stmt|;
name|OntClass
name|unionClass
init|=
name|ontModel
operator|.
name|createUnionClass
argument_list|(
name|unionClassURI
argument_list|,
name|list
argument_list|)
decl_stmt|;
return|return
name|unionClass
return|;
block|}
specifier|private
name|Resource
name|getDatatypePropertyRange
parameter_list|(
name|PropType
name|propType
parameter_list|)
block|{
name|Resource
name|range
decl_stmt|;
if|if
condition|(
name|propType
operator|==
name|PropType
operator|.
name|STRING
condition|)
block|{
name|range
operator|=
name|XSD
operator|.
name|normalizedString
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|propType
operator|==
name|PropType
operator|.
name|BOOLEAN
condition|)
block|{
name|range
operator|=
name|XSD
operator|.
name|xboolean
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|propType
operator|==
name|PropType
operator|.
name|BINARY
condition|)
block|{
name|range
operator|=
name|XSD
operator|.
name|base64Binary
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|propType
operator|==
name|PropType
operator|.
name|DATE
condition|)
block|{
name|range
operator|=
name|XSD
operator|.
name|dateTime
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|propType
operator|==
name|PropType
operator|.
name|DOUBLE
condition|)
block|{
name|range
operator|=
name|XSD
operator|.
name|unsignedLong
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|propType
operator|==
name|PropType
operator|.
name|LONG
condition|)
block|{
name|range
operator|=
name|XSD
operator|.
name|unsignedLong
expr_stmt|;
block|}
else|else
block|{
name|range
operator|=
name|XSD
operator|.
name|normalizedString
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"{} property type is not supported yet. XSD.normalizedString is set as default range"
argument_list|,
name|propType
argument_list|)
expr_stmt|;
block|}
return|return
name|range
return|;
block|}
specifier|public
name|Individual
name|getIndividualByReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|Individual
name|ind
init|=
name|getLooseIndividualByReference
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|ind
operator|.
name|isClass
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Resource {} is already a class"
argument_list|,
name|ind
operator|.
name|getURI
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|ind
operator|.
name|isIndividual
argument_list|()
condition|)
block|{
return|return
name|ind
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|Individual
name|getLooseIndividualByReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|ResIterator
name|it
init|=
name|ontModel
operator|.
name|listResourcesWithProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|reference
argument_list|)
decl_stmt|;
name|Resource
name|resource
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|resource
operator|=
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|resource
operator|.
name|as
argument_list|(
name|Individual
operator|.
name|class
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedPolymorphismException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Can not cast resource {} to individual"
argument_list|,
name|resource
operator|.
name|getURI
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|Individual
name|createIndividualByReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
comment|/**      *       * @param cmsObject      * @param klass      * @return      */
specifier|public
name|Individual
name|createIndividualByCMSObject
parameter_list|(
name|CMSObject
name|cmsObject
parameter_list|,
name|Resource
name|klass
parameter_list|)
block|{
name|Individual
name|ind
init|=
name|getLooseIndividualByReference
argument_list|(
name|cmsObject
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|ind
operator|==
literal|null
condition|)
block|{
name|String
name|indURI
init|=
name|namingStrategy
operator|.
name|getIndividualName
argument_list|(
name|ontologyURI
argument_list|,
name|cmsObject
argument_list|)
decl_stmt|;
name|ind
operator|=
name|ontModel
operator|.
name|createIndividual
argument_list|(
name|indURI
argument_list|,
name|klass
argument_list|)
expr_stmt|;
name|ind
operator|.
name|addProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CMSAD_RESOURCE_REF_PROP
argument_list|,
name|cmsObject
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ind
return|;
block|}
elseif|else
if|if
condition|(
name|ind
operator|.
name|isClass
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Resource {} is already a class"
argument_list|,
name|ind
operator|.
name|getURI
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
name|ind
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|saveConnectionInfo
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|,
name|OntModel
name|ontModel
parameter_list|)
block|{
name|Resource
name|r
init|=
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_INFO_RES
decl_stmt|;
name|ontModel
operator|.
name|add
argument_list|(
name|r
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_TYPE_PROP
argument_list|,
name|connectionInfo
operator|.
name|getConnectionType
argument_list|()
argument_list|)
expr_stmt|;
name|ontModel
operator|.
name|add
argument_list|(
name|r
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_PASSWORD_PROP
argument_list|,
name|connectionInfo
operator|.
name|getPassword
argument_list|()
argument_list|)
expr_stmt|;
name|ontModel
operator|.
name|add
argument_list|(
name|r
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_USERNAME_PROP
argument_list|,
name|connectionInfo
operator|.
name|getUsername
argument_list|()
argument_list|)
expr_stmt|;
name|ontModel
operator|.
name|add
argument_list|(
name|r
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_WORKSPACE_PROP
argument_list|,
name|connectionInfo
operator|.
name|getWorkspaceName
argument_list|()
argument_list|)
expr_stmt|;
name|ontModel
operator|.
name|add
argument_list|(
name|r
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_WORKSPACE_URL_PROP
argument_list|,
name|connectionInfo
operator|.
name|getRepositoryURL
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|ConnectionInfo
name|getConnectionInfo
parameter_list|(
name|OntModel
name|ontModel
parameter_list|)
block|{
name|ObjectFactory
name|of
init|=
operator|new
name|ObjectFactory
argument_list|()
decl_stmt|;
name|ConnectionInfo
name|ci
init|=
name|of
operator|.
name|createConnectionInfo
argument_list|()
decl_stmt|;
name|Resource
name|ciResource
init|=
name|ontModel
operator|.
name|getResource
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_INFO_RES
operator|.
name|getURI
argument_list|()
argument_list|)
decl_stmt|;
name|ci
operator|.
name|setConnectionType
argument_list|(
name|ciResource
operator|.
name|getProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_TYPE_PROP
argument_list|)
operator|.
name|getString
argument_list|()
argument_list|)
expr_stmt|;
name|ci
operator|.
name|setPassword
argument_list|(
name|ciResource
operator|.
name|getProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_PASSWORD_PROP
argument_list|)
operator|.
name|getString
argument_list|()
argument_list|)
expr_stmt|;
name|ci
operator|.
name|setUsername
argument_list|(
name|ciResource
operator|.
name|getProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_USERNAME_PROP
argument_list|)
operator|.
name|getString
argument_list|()
argument_list|)
expr_stmt|;
name|ci
operator|.
name|setWorkspaceName
argument_list|(
name|ciResource
operator|.
name|getProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_WORKSPACE_PROP
argument_list|)
operator|.
name|getString
argument_list|()
argument_list|)
expr_stmt|;
name|ci
operator|.
name|setRepositoryURL
argument_list|(
name|ciResource
operator|.
name|getProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|CONNECTION_WORKSPACE_URL_PROP
argument_list|)
operator|.
name|getString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ci
return|;
block|}
specifier|public
specifier|static
name|void
name|saveBridgeDefinitions
parameter_list|(
name|BridgeDefinitions
name|bridgeDefinitions
parameter_list|,
name|OntModel
name|ontModel
parameter_list|)
block|{
name|Resource
name|r
init|=
name|CMSAdapterVocabulary
operator|.
name|BRIDGE_DEFINITIONS_RES
decl_stmt|;
name|String
name|bridges
init|=
name|MappingModelParser
operator|.
name|serializeObject
argument_list|(
name|bridgeDefinitions
argument_list|)
decl_stmt|;
name|ontModel
operator|.
name|add
argument_list|(
name|r
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|BRIDGE_DEFINITIONS_CONTENT_PROP
argument_list|,
name|bridges
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|BridgeDefinitions
name|getBridgeDefinitions
parameter_list|(
name|OntModel
name|ontModel
parameter_list|)
block|{
name|Resource
name|r
init|=
name|ontModel
operator|.
name|getResource
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|BRIDGE_DEFINITIONS_RES
operator|.
name|getURI
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|bridgeStr
init|=
name|r
operator|.
name|getProperty
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|BRIDGE_DEFINITIONS_CONTENT_PROP
argument_list|)
operator|.
name|getString
argument_list|()
decl_stmt|;
return|return
name|MappingModelParser
operator|.
name|deserializeObject
argument_list|(
name|bridgeStr
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|OntModel
name|createOntModel
parameter_list|()
block|{
return|return
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|(
name|OntModelSpec
operator|.
name|OWL_DL_MEM
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|OntModel
name|getOntModel
parameter_list|(
name|RestClient
name|storeClient
parameter_list|,
name|String
name|ontologyURI
parameter_list|,
name|String
name|ontologyHref
parameter_list|)
throws|throws
name|RestClientException
throws|,
name|UnsupportedEncodingException
block|{
name|OntModel
name|ontModel
init|=
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|(
name|OntModelSpec
operator|.
name|OWL_DL_MEM
argument_list|)
decl_stmt|;
name|String
name|ontContent
init|=
name|storeClient
operator|.
name|retrieveOntology
argument_list|(
name|ontologyHref
argument_list|,
literal|"RDF/XML"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|InputStream
name|is
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|ontContent
operator|.
name|getBytes
argument_list|(
literal|"UTF-8"
argument_list|)
argument_list|)
decl_stmt|;
name|ontModel
operator|.
name|read
argument_list|(
name|is
argument_list|,
name|ontologyURI
argument_list|,
literal|"RDF/XML"
argument_list|)
expr_stmt|;
return|return
name|ontModel
return|;
block|}
specifier|public
specifier|static
specifier|final
name|String
name|addResourceDelimiter
parameter_list|(
name|String
name|URI
parameter_list|)
block|{
if|if
condition|(
operator|!
name|URI
operator|.
name|endsWith
argument_list|(
literal|"#"
argument_list|)
condition|)
block|{
name|URI
operator|+=
literal|"#"
expr_stmt|;
block|}
return|return
name|URI
return|;
block|}
block|}
end_class

end_unit

