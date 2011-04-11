begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|annotation
operator|.
name|Annotation
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Type
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
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Produces
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|WebApplicationException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MultivaluedMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|ext
operator|.
name|MessageBodyReader
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|ext
operator|.
name|MessageBodyWriter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|ext
operator|.
name|Provider
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|XMLConstants
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
name|JAXBContext
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
name|JAXBException
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
name|Marshaller
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
name|Unmarshaller
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|stream
operator|.
name|StreamSource
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|validation
operator|.
name|Schema
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|validation
operator|.
name|SchemaFactory
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
name|model
operator|.
name|AdministeredOntologies
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
name|model
operator|.
name|BuiltInResource
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
name|model
operator|.
name|ClassConstraint
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
name|model
operator|.
name|ClassContext
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
name|model
operator|.
name|ClassMetaInformation
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
name|model
operator|.
name|ClassesForOntology
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
name|model
operator|.
name|ConstraintType
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
name|model
operator|.
name|ContainerClasses
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
name|model
operator|.
name|DatatypePropertiesForOntology
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
name|model
operator|.
name|DatatypePropertyContext
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
name|model
operator|.
name|DisjointClasses
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
name|model
operator|.
name|Domain
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
name|model
operator|.
name|EquivalentClasses
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
name|model
operator|.
name|EquivalentProperties
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
name|model
operator|.
name|ImportsForOntology
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
name|model
operator|.
name|IndividualContext
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
name|model
operator|.
name|IndividualMetaInformation
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
name|model
operator|.
name|IndividualsForOntology
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
name|model
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
name|ontologymanager
operator|.
name|store
operator|.
name|model
operator|.
name|ObjectPropertiesForOntology
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
name|model
operator|.
name|ObjectPropertyContext
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
name|model
operator|.
name|OntologyImport
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
name|model
operator|.
name|OntologyMetaInformation
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
name|model
operator|.
name|PropertyAssertions
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
name|model
operator|.
name|PropertyMetaInformation
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
name|model
operator|.
name|Range
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
name|model
operator|.
name|ResourceMetaInformationType
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
name|model
operator|.
name|SuperProperties
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
name|model
operator|.
name|Superclasses
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|spi
operator|.
name|resource
operator|.
name|Singleton
import|;
end_import

begin_class
annotation|@
name|Singleton
annotation|@
name|Provider
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|APPLICATION_XML
argument_list|)
specifier|public
class|class
name|JAXBProvider
implements|implements
name|MessageBodyReader
implements|,
name|MessageBodyWriter
block|{
specifier|private
name|Marshaller
name|marshaller
decl_stmt|;
specifier|private
name|Unmarshaller
name|unmarshaller
decl_stmt|;
specifier|public
name|JAXBProvider
parameter_list|()
throws|throws
name|JAXBException
throws|,
name|SAXException
block|{
name|ClassLoader
name|cl
init|=
name|ObjectFactory
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
decl_stmt|;
name|JAXBContext
name|jc
init|=
name|JAXBContext
operator|.
name|newInstance
argument_list|(
literal|"org.apache.stanbol.ontologymanager.store.model"
argument_list|,
name|cl
argument_list|)
decl_stmt|;
name|marshaller
operator|=
name|jc
operator|.
name|createMarshaller
argument_list|()
expr_stmt|;
name|unmarshaller
operator|=
name|jc
operator|.
name|createUnmarshaller
argument_list|()
expr_stmt|;
name|String
name|schemaLocations
index|[]
init|=
block|{
literal|"model/xlinks.xsd"
block|,
literal|"model/PersistenceStoreRESTfulInterface.xsd"
block|,
literal|"model/SearchRESTfulInterface.xsd"
block|}
decl_stmt|;
name|SchemaFactory
name|schemaFactory
init|=
name|SchemaFactory
operator|.
name|newInstance
argument_list|(
name|XMLConstants
operator|.
name|W3C_XML_SCHEMA_NS_URI
argument_list|,
literal|"com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory"
argument_list|,
name|JAXBProvider
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|StreamSource
argument_list|>
name|streamSourceList
init|=
operator|new
name|Vector
argument_list|<
name|StreamSource
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|schemaLocation
range|:
name|schemaLocations
control|)
block|{
name|InputStream
name|is
init|=
name|cl
operator|.
name|getResourceAsStream
argument_list|(
name|schemaLocation
argument_list|)
decl_stmt|;
name|StreamSource
name|streamSource
init|=
operator|new
name|StreamSource
argument_list|(
name|is
argument_list|)
decl_stmt|;
name|streamSourceList
operator|.
name|add
argument_list|(
name|streamSource
argument_list|)
expr_stmt|;
block|}
name|StreamSource
name|sources
index|[]
init|=
operator|new
name|StreamSource
index|[
name|streamSourceList
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|Schema
name|schema
init|=
name|schemaFactory
operator|.
name|newSchema
argument_list|(
name|streamSourceList
operator|.
name|toArray
argument_list|(
name|sources
argument_list|)
argument_list|)
decl_stmt|;
name|marshaller
operator|.
name|setSchema
argument_list|(
name|schema
argument_list|)
expr_stmt|;
name|marshaller
operator|.
name|setProperty
argument_list|(
name|Marshaller
operator|.
name|JAXB_FORMATTED_OUTPUT
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|unmarshaller
operator|.
name|setSchema
argument_list|(
name|schema
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|(
name|Object
name|t
parameter_list|,
name|Class
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
return|return
operator|-
literal|1
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isWriteable
parameter_list|(
name|Class
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|AdministeredOntologies
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|BuiltInResource
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ClassConstraint
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ClassContext
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ClassesForOntology
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ClassMetaInformation
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ConstraintType
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ContainerClasses
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|DatatypePropertiesForOntology
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|DatatypePropertyContext
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|DisjointClasses
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|Domain
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|EquivalentClasses
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|EquivalentProperties
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|IndividualContext
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|IndividualMetaInformation
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|IndividualsForOntology
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ObjectPropertiesForOntology
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ObjectPropertyContext
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|OntologyMetaInformation
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|PropertyAssertions
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|PropertyMetaInformation
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|Range
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ResourceMetaInformationType
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|Superclasses
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|SuperProperties
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|OntologyImport
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ImportsForOntology
operator|.
name|class
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeTo
parameter_list|(
name|Object
name|t
parameter_list|,
name|Class
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|MultivaluedMap
name|httpHeaders
parameter_list|,
name|OutputStream
name|entityStream
parameter_list|)
throws|throws
name|IOException
throws|,
name|WebApplicationException
block|{
try|try
block|{
name|marshaller
operator|.
name|marshal
argument_list|(
name|t
argument_list|,
name|entityStream
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isReadable
parameter_list|(
name|Class
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|AdministeredOntologies
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|BuiltInResource
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ClassConstraint
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ClassContext
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ClassesForOntology
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ClassMetaInformation
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ConstraintType
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ContainerClasses
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|DatatypePropertiesForOntology
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|DatatypePropertyContext
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|DisjointClasses
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|Domain
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|EquivalentClasses
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|EquivalentProperties
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|IndividualContext
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|IndividualMetaInformation
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|IndividualsForOntology
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ObjectPropertiesForOntology
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ObjectPropertyContext
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|OntologyMetaInformation
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|PropertyAssertions
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|PropertyMetaInformation
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|Range
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ResourceMetaInformationType
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|Superclasses
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|SuperProperties
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|OntologyImport
operator|.
name|class
argument_list|)
operator|||
name|type
operator|.
name|equals
argument_list|(
name|ImportsForOntology
operator|.
name|class
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Object
name|readFrom
parameter_list|(
name|Class
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|MultivaluedMap
name|httpHeaders
parameter_list|,
name|InputStream
name|entityStream
parameter_list|)
throws|throws
name|IOException
throws|,
name|WebApplicationException
block|{
try|try
block|{
return|return
name|unmarshaller
operator|.
name|unmarshal
argument_list|(
name|entityStream
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JAXBException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

