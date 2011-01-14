begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|defaults
package|;
end_package

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigDecimal
import|;
end_import

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
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|EnumSet
import|;
end_import

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
name|HashSet
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|datatype
operator|.
name|Duration
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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Text
import|;
end_import

begin_comment
comment|/**  * Defines the data types that need to be supported by the model   * implementation.<p>  * Each data type defines:<ul>  *<li><b>short name:</b> An short and human readable ID that is unique within   * the list of data types. Currently<code>prefix:localName</code> is used as   * short name and prefixes are used as defined by the {@link NamespaceEnum}.  *<li><b>uri:</b> Ths is the global unique UD of the namespace. If possible the  * URI defined by XSD is used (e.g. http://www.w3.org/2001/XMLSchema#string for  * strings).   *<li><b>java class:</b> Each data type is mapped to exactly one preferred   * and 0..n optional java representations. Note that  * different data types may use the same preferred as well as optional java class   * meaning. This means that the java class can not be used to uniquely identify   * a data type.  *</ul>  * The {@link #name()} is not used, but typically the local name with an capital  * first letter is used. The URI of the data type is used by the implementation  * of {@link #toString()}.<p>  * In addition the the definition of all the data types this class also provides  * several utilities for getting the data type by short name, URI as well as  * preferred or all defined java class mappings.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|DataTypeEnum
block|{
comment|//Rick specific
name|Reference
argument_list|(
name|NamespaceEnum
operator|.
name|rickModel
argument_list|,
literal|"ref"
argument_list|,
name|Reference
operator|.
name|class
argument_list|)
block|,
name|Text
argument_list|(
name|NamespaceEnum
operator|.
name|rickModel
argument_list|,
literal|"text"
argument_list|,
name|Text
operator|.
name|class
argument_list|)
block|,
comment|//xsd types
comment|/**      * currently URIs are preferable mapped to {@link Reference}, because there      * may be RDF URIs that are not valid {@link URI}s nor {@link URL}s.      */
name|AnyUri
argument_list|(
literal|"anyURI"
argument_list|,
name|Reference
operator|.
name|class
argument_list|,
name|URI
operator|.
name|class
argument_list|,
name|URL
operator|.
name|class
argument_list|)
block|,
name|Boolean
argument_list|(
literal|"boolean"
argument_list|,
name|Boolean
operator|.
name|class
argument_list|)
block|,
name|Byte
argument_list|(
literal|"byte"
argument_list|,
name|Byte
operator|.
name|class
argument_list|)
block|,
name|Short
argument_list|(
literal|"short"
argument_list|,
name|Short
operator|.
name|class
argument_list|)
block|,
name|Integer
argument_list|(
literal|"integer"
argument_list|,
name|BigInteger
operator|.
name|class
argument_list|)
block|,
name|Decimal
argument_list|(
literal|"decimal"
argument_list|,
name|BigDecimal
operator|.
name|class
argument_list|)
block|,
name|Int
argument_list|(
literal|"int"
argument_list|,
name|Integer
operator|.
name|class
argument_list|)
block|,
name|Long
argument_list|(
literal|"long"
argument_list|,
name|Long
operator|.
name|class
argument_list|)
block|,
name|Float
argument_list|(
literal|"float"
argument_list|,
name|Float
operator|.
name|class
argument_list|)
block|,
name|Double
argument_list|(
literal|"double"
argument_list|,
name|Double
operator|.
name|class
argument_list|)
block|,
name|String
argument_list|(
literal|"string"
argument_list|,
name|String
operator|.
name|class
argument_list|,
name|Text
operator|.
name|class
argument_list|)
block|,
name|Time
argument_list|(
literal|"time"
argument_list|,
name|Date
operator|.
name|class
argument_list|)
block|,
comment|//TODO: check if the XML calendar would be better
name|Date
argument_list|(
literal|"date"
argument_list|,
name|Date
operator|.
name|class
argument_list|)
block|,
name|DateTime
argument_list|(
literal|"dateTime"
argument_list|,
name|Date
operator|.
name|class
argument_list|)
block|,
name|Duration
argument_list|(
literal|"duration"
argument_list|,
name|Duration
operator|.
name|class
argument_list|)
block|,     ;
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
name|javaType
decl_stmt|;
specifier|final
name|QName
name|qName
decl_stmt|;
specifier|final
name|String
name|shortName
decl_stmt|;
specifier|final
name|String
name|uri
decl_stmt|;
specifier|final
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|additional
decl_stmt|;
specifier|private
name|DataTypeEnum
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|javaType
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
modifier|...
name|additional
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
name|javaType
argument_list|,
name|additional
argument_list|)
expr_stmt|;
block|}
specifier|private
name|DataTypeEnum
parameter_list|(
name|String
name|localName
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|javaType
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
modifier|...
name|additional
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|localName
argument_list|,
name|javaType
argument_list|,
name|additional
argument_list|)
expr_stmt|;
block|}
specifier|private
name|DataTypeEnum
parameter_list|(
name|NamespaceEnum
name|namespace
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|javaType
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
modifier|...
name|additional
parameter_list|)
block|{
name|this
argument_list|(
name|namespace
argument_list|,
literal|null
argument_list|,
name|javaType
argument_list|,
name|additional
argument_list|)
expr_stmt|;
block|}
specifier|private
name|DataTypeEnum
parameter_list|(
name|NamespaceEnum
name|namespace
parameter_list|,
name|String
name|localName
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|javaType
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
modifier|...
name|additional
parameter_list|)
block|{
if|if
condition|(
name|namespace
operator|==
literal|null
condition|)
block|{
name|namespace
operator|=
name|NamespaceEnum
operator|.
name|xsd
expr_stmt|;
block|}
if|if
condition|(
name|localName
operator|==
literal|null
condition|)
block|{
name|localName
operator|=
name|name
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|additional
operator|!=
literal|null
operator|&&
name|additional
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|this
operator|.
name|additional
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
operator|new
name|HashSet
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|additional
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|additional
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
name|this
operator|.
name|javaType
operator|=
name|javaType
expr_stmt|;
name|this
operator|.
name|qName
operator|=
operator|new
name|QName
argument_list|(
name|namespace
operator|.
name|getNamespace
argument_list|()
argument_list|,
name|localName
argument_list|,
name|namespace
operator|.
name|getPrefix
argument_list|()
argument_list|)
expr_stmt|;
comment|//a lot of accesses will be based on the Uri and the shortName.
comment|// -> so store the IDs and the shortName as local Vars.
name|this
operator|.
name|shortName
operator|=
name|qName
operator|.
name|getPrefix
argument_list|()
operator|+
literal|':'
operator|+
name|qName
operator|.
name|getLocalPart
argument_list|()
expr_stmt|;
name|this
operator|.
name|uri
operator|=
name|qName
operator|.
name|getNamespaceURI
argument_list|()
operator|+
name|qName
operator|.
name|getLocalPart
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|final
name|String
name|getLocalName
parameter_list|()
block|{
return|return
name|qName
operator|.
name|getLocalPart
argument_list|()
return|;
block|}
specifier|public
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
name|getJavaType
parameter_list|()
block|{
return|return
name|javaType
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|getAdditionalJavaTypes
parameter_list|()
block|{
return|return
name|additional
return|;
block|}
specifier|public
specifier|final
name|NamespaceEnum
name|getNamespace
parameter_list|()
block|{
return|return
name|NamespaceEnum
operator|.
name|forNamespace
argument_list|(
name|qName
operator|.
name|getNamespaceURI
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|final
name|String
name|getUri
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
specifier|public
specifier|final
name|String
name|getShortName
parameter_list|()
block|{
return|return
name|shortName
return|;
block|}
specifier|public
specifier|final
name|QName
name|getQName
parameter_list|()
block|{
return|return
name|qName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getUri
argument_list|()
return|;
block|}
specifier|static
specifier|private
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|class2DataTypeMap
decl_stmt|;
specifier|static
specifier|private
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|interface2DataTypeMap
decl_stmt|;
specifier|static
specifier|private
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|allClass2DataTypeMap
decl_stmt|;
specifier|static
specifier|private
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|allInterface2DataTypeMap
decl_stmt|;
specifier|static
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|DataTypeEnum
argument_list|>
name|uri2DataType
decl_stmt|;
specifier|static
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|DataTypeEnum
argument_list|>
name|shortName2DataType
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|c2d
init|=
operator|new
name|HashMap
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|i2d
init|=
operator|new
name|HashMap
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|ac2d
init|=
operator|new
name|HashMap
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|ai2d
init|=
operator|new
name|HashMap
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|DataTypeEnum
argument_list|>
name|u2d
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|DataTypeEnum
argument_list|>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|DataTypeEnum
argument_list|>
name|s2d
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|DataTypeEnum
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|DataTypeEnum
name|dataType
range|:
name|DataTypeEnum
operator|.
name|values
argument_list|()
control|)
block|{
comment|//add primary javaType -> data type mappings
if|if
condition|(
name|dataType
operator|.
name|javaType
operator|.
name|isInterface
argument_list|()
condition|)
block|{
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|dataTypes
init|=
name|i2d
operator|.
name|get
argument_list|(
name|dataType
operator|.
name|javaType
argument_list|)
decl_stmt|;
if|if
condition|(
name|dataTypes
operator|==
literal|null
condition|)
block|{
name|dataTypes
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|DataTypeEnum
operator|.
name|class
argument_list|)
expr_stmt|;
name|i2d
operator|.
name|put
argument_list|(
name|dataType
operator|.
name|javaType
argument_list|,
name|dataTypes
argument_list|)
expr_stmt|;
block|}
name|dataTypes
operator|.
name|add
argument_list|(
name|dataType
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//a class
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|dataTypes
init|=
name|c2d
operator|.
name|get
argument_list|(
name|dataType
operator|.
name|javaType
argument_list|)
decl_stmt|;
if|if
condition|(
name|dataTypes
operator|==
literal|null
condition|)
block|{
name|dataTypes
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|DataTypeEnum
operator|.
name|class
argument_list|)
expr_stmt|;
name|c2d
operator|.
name|put
argument_list|(
name|dataType
operator|.
name|javaType
argument_list|,
name|dataTypes
argument_list|)
expr_stmt|;
block|}
name|dataTypes
operator|.
name|add
argument_list|(
name|dataType
argument_list|)
expr_stmt|;
block|}
comment|//add additional javaType -> data type mappings
for|for
control|(
name|Class
argument_list|<
name|?
argument_list|>
name|additionalClass
range|:
name|dataType
operator|.
name|additional
control|)
block|{
if|if
condition|(
name|additionalClass
operator|.
name|isInterface
argument_list|()
condition|)
block|{
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|dataTypes
init|=
name|ai2d
operator|.
name|get
argument_list|(
name|additionalClass
argument_list|)
decl_stmt|;
if|if
condition|(
name|dataTypes
operator|==
literal|null
condition|)
block|{
name|dataTypes
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|DataTypeEnum
operator|.
name|class
argument_list|)
expr_stmt|;
name|ai2d
operator|.
name|put
argument_list|(
name|additionalClass
argument_list|,
name|dataTypes
argument_list|)
expr_stmt|;
block|}
name|dataTypes
operator|.
name|add
argument_list|(
name|dataType
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//a class
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|dataTypes
init|=
name|ac2d
operator|.
name|get
argument_list|(
name|additionalClass
argument_list|)
decl_stmt|;
if|if
condition|(
name|dataTypes
operator|==
literal|null
condition|)
block|{
name|dataTypes
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|DataTypeEnum
operator|.
name|class
argument_list|)
expr_stmt|;
name|ac2d
operator|.
name|put
argument_list|(
name|additionalClass
argument_list|,
name|dataTypes
argument_list|)
expr_stmt|;
block|}
name|dataTypes
operator|.
name|add
argument_list|(
name|dataType
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|u2d
operator|.
name|containsKey
argument_list|(
name|dataType
operator|.
name|getUri
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|java
operator|.
name|lang
operator|.
name|String
operator|.
name|format
argument_list|(
literal|"Invalid configuration in DataTypeEnum because dataType uri %s is used for %s and %s!"
argument_list|,
name|dataType
operator|.
name|getUri
argument_list|()
argument_list|,
name|dataType
operator|.
name|name
argument_list|()
argument_list|,
name|u2d
operator|.
name|get
argument_list|(
name|dataType
operator|.
name|getUri
argument_list|()
argument_list|)
operator|.
name|name
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
name|u2d
operator|.
name|put
argument_list|(
name|dataType
operator|.
name|getUri
argument_list|()
argument_list|,
name|dataType
argument_list|)
expr_stmt|;
if|if
condition|(
name|s2d
operator|.
name|containsKey
argument_list|(
name|dataType
operator|.
name|getShortName
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|java
operator|.
name|lang
operator|.
name|String
operator|.
name|format
argument_list|(
literal|"Invalid configuration in DataTypeEnum because dataType short name (prefix:localname) %s is used for %s and %s!"
argument_list|,
name|dataType
operator|.
name|getShortName
argument_list|()
argument_list|,
name|dataType
operator|.
name|name
argument_list|()
argument_list|,
name|s2d
operator|.
name|get
argument_list|(
name|dataType
operator|.
name|getShortName
argument_list|()
argument_list|)
operator|.
name|name
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
name|s2d
operator|.
name|put
argument_list|(
name|dataType
operator|.
name|getShortName
argument_list|()
argument_list|,
name|dataType
argument_list|)
expr_stmt|;
block|}
name|class2DataTypeMap
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|c2d
argument_list|)
expr_stmt|;
name|interface2DataTypeMap
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|i2d
argument_list|)
expr_stmt|;
name|allClass2DataTypeMap
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|ac2d
argument_list|)
expr_stmt|;
name|allInterface2DataTypeMap
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|ai2d
argument_list|)
expr_stmt|;
name|uri2DataType
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|u2d
argument_list|)
expr_stmt|;
name|shortName2DataType
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|s2d
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|getPrimaryDataTypes
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|javaClass
parameter_list|)
block|{
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|dataTypes
init|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|DataTypeEnum
operator|.
name|class
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|classesTypes
init|=
name|class2DataTypeMap
operator|.
name|get
argument_list|(
name|javaClass
argument_list|)
decl_stmt|;
if|if
condition|(
name|classesTypes
operator|!=
literal|null
condition|)
block|{
name|dataTypes
operator|.
name|addAll
argument_list|(
name|classesTypes
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Entry
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|entry
range|:
name|interface2DataTypeMap
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|isAssignableFrom
argument_list|(
name|javaClass
argument_list|)
condition|)
block|{
name|dataTypes
operator|.
name|addAll
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|dataTypes
return|;
block|}
specifier|public
specifier|static
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|getAllDataTypes
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|javaClass
parameter_list|)
block|{
comment|//start with the primary
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|all
init|=
name|getPrimaryDataTypes
argument_list|(
name|javaClass
argument_list|)
decl_stmt|;
comment|//now add the additional types
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|additionalClassesTypes
init|=
name|allClass2DataTypeMap
operator|.
name|get
argument_list|(
name|javaClass
argument_list|)
decl_stmt|;
if|if
condition|(
name|additionalClassesTypes
operator|!=
literal|null
condition|)
block|{
name|all
operator|.
name|addAll
argument_list|(
name|additionalClassesTypes
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Entry
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|,
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
argument_list|>
name|entry
range|:
name|allInterface2DataTypeMap
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|isAssignableFrom
argument_list|(
name|javaClass
argument_list|)
condition|)
block|{
name|all
operator|.
name|addAll
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|all
return|;
block|}
comment|//    public static DataTypeEnum getDataType(Class<?> javaClass){
comment|//        List<DataTypeEnum> dataTypes = getAllDataTypes(javaClass);
comment|//        return dataTypes.isEmpty()?null:dataTypes.get(0);
comment|//    }
specifier|public
specifier|static
name|DataTypeEnum
name|getDataType
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
return|return
name|uri2DataType
operator|.
name|get
argument_list|(
name|uri
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|DataTypeEnum
name|getDataTypeByShortName
parameter_list|(
name|String
name|shortName
parameter_list|)
block|{
return|return
name|shortName2DataType
operator|.
name|get
argument_list|(
name|shortName
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

