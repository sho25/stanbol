begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

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
name|tika
operator|.
name|metadata
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|tika
operator|.
name|metadata
operator|.
name|DublinCore
operator|.
name|DATE
import|;
end_import

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
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|Collection
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedHashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedHashSet
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
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|BlankNode
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
name|InvalidLiteralTypeException
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
name|LiteralFactory
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
name|commons
operator|.
name|rdf
operator|.
name|Graph
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
name|NoConvertorException
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
name|commons
operator|.
name|rdf
operator|.
name|BlankNodeOrIRI
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
name|commons
operator|.
name|rdf
operator|.
name|RDFTerm
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|commons
operator|.
name|rdf
operator|.
name|Literal
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
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
operator|.
name|PlainLiteralImpl
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
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
operator|.
name|TypedLiteralImpl
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
name|ontologies
operator|.
name|RDFS
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
name|ontologies
operator|.
name|XSD
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|tika
operator|.
name|metadata
operator|.
name|DublinCore
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|tika
operator|.
name|metadata
operator|.
name|Metadata
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|tika
operator|.
name|metadata
operator|.
name|Property
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

begin_comment
comment|/**  * Used as value for Apache Tika {@link Metadata} mappings. Holds the  * ontology property as {@link IRI} and optionally a Tika {@link Property}.  * Later can be used to parse the correct datatype for values contained in the  * {@link Metadata}  *   * @author westei  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Mapping
block|{
specifier|private
specifier|final
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|Mapping
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|LiteralFactory
name|lf
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
comment|/**      * List with allowed DataTypes.<ul>      *<li><code>null</code> is used for {@link PlainLiteral}s      *<li> {@link XSD} datatyoes are used for {@link TypedLiteral}s      *<li> {@link RDFS#RDFTerm} is used for {@link BlankNodeOrIRI} values. Note      * that only {@link IRI} is supported, because for Tika {@link BlankNode}s      * do not make sense.      *</ul>      */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|IRI
argument_list|>
name|ONT_TYPES
decl_stmt|;
comment|/**      * Map with the same keys as contained in {@link #ONT_TYPES}. The values      * are the java types.      */
specifier|protected
specifier|static
specifier|final
name|Map
argument_list|<
name|IRI
argument_list|,
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|ONT_TYPE_MAP
decl_stmt|;
static|static
block|{
comment|//use a linked HasSetMap to have the nice ordering (mainly for logging)
name|Map
argument_list|<
name|IRI
argument_list|,
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|map
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|IRI
argument_list|,
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
comment|//Plain Literal values
name|map
operator|.
name|put
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|//Typed Literal values
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|anyURI
argument_list|,
name|URI
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|base64Binary
argument_list|,
name|byte
index|[]
operator|.
expr|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|boolean_
argument_list|,
name|Boolean
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|byte_
argument_list|,
name|Byte
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|date
argument_list|,
name|Date
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|dateTime
argument_list|,
name|Date
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|decimal
argument_list|,
name|BigDecimal
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|double_
argument_list|,
name|Double
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|float_
argument_list|,
name|Float
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|int_
argument_list|,
name|Integer
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|integer
argument_list|,
name|BigInteger
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|long_
argument_list|,
name|Long
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|short_
argument_list|,
name|Short
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|string
argument_list|,
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|XSD
operator|.
name|time
argument_list|,
name|Date
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//Data Types for BlankNodeOrIRI values
name|map
operator|.
name|put
argument_list|(
name|RDFS
operator|.
name|Resource
argument_list|,
name|URI
operator|.
name|class
argument_list|)
expr_stmt|;
name|ONT_TYPE_MAP
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|map
argument_list|)
expr_stmt|;
name|ONT_TYPES
operator|=
name|ONT_TYPE_MAP
operator|.
name|keySet
argument_list|()
expr_stmt|;
comment|//NOTE: The following XSD types are not included
comment|//XSD.gDay,XSD.gMonth,XSD.gMonthDay,XSD.gYearMonth,XSD.hexBinary,XSD.language,
comment|//XSD.Name,XSD.NCName,XSD.negativeInteger,XSD.NMTOKEN,XSD.nonNegativeInteger,
comment|//XSD.normalizedString,XSD.positiveInteger,
comment|//XSD.token,XSD.unsignedByte,XSD.unsignedInt,XSD.unsignedLong,XSD.unsignedShort,
block|}
specifier|protected
specifier|final
name|IRI
name|ontProperty
decl_stmt|;
specifier|protected
specifier|final
name|Converter
name|converter
decl_stmt|;
comment|/**      * Getter for the OntologyProperty for this mapping      * @return the ontProperty      */
specifier|public
specifier|final
name|IRI
name|getOntologyProperty
parameter_list|()
block|{
return|return
name|ontProperty
return|;
block|}
comment|/**      * Getter for the set of Tika {@link Metadata} key names that are used      * by this mapping. This is typically used to determine if based on the       * present {@link Metadata#names()} a mapping need to be processed or not.      *<p>Mappings need to be called if any of the returned keys is present in      * the {@link Metadata}. Mappings that return an empty list MUST BE      * called.      * @return the Tika {@link Metadata} key names that are used by this mapping.      * If no keys are mapped than it MUST return an empty list.      */
specifier|public
specifier|abstract
name|Set
argument_list|<
name|String
argument_list|>
name|getMappedTikaProperties
parameter_list|()
function_decl|;
specifier|protected
specifier|final
name|IRI
name|ontType
decl_stmt|;
specifier|protected
name|Mapping
parameter_list|(
name|IRI
name|ontProperty
parameter_list|,
name|IRI
name|ontType
parameter_list|)
block|{
name|this
argument_list|(
name|ontProperty
argument_list|,
name|ontType
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|Mapping
parameter_list|(
name|IRI
name|ontProperty
parameter_list|,
name|IRI
name|ontType
parameter_list|,
name|Converter
name|converter
parameter_list|)
block|{
if|if
condition|(
name|ontProperty
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ontology property MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|ontProperty
operator|=
name|ontProperty
expr_stmt|;
if|if
condition|(
operator|!
name|ONT_TYPES
operator|.
name|contains
argument_list|(
name|ontType
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The ontology type '"
operator|+
name|ontType
operator|+
literal|"' is not supported. (supported: "
operator|+
name|ONT_TYPES
operator|+
literal|")"
argument_list|)
throw|;
block|}
name|this
operator|.
name|ontType
operator|=
name|ontType
expr_stmt|;
name|this
operator|.
name|converter
operator|=
name|converter
expr_stmt|;
block|}
comment|/**      * Applies this mapping based on the parsed {@link Metadata} and stores the       * results to {@link Graph}      * @param graph the ImmutableGraph to store the mapping results      * @param subject the subject (context) to add the mappings      * @param metadata the metadata used for applying the mapping      * @return<code>true</code> if the mapping could be applied based on the      * parsed data. Otherwise<code>false</code>. This is intended to be used      * by components that need to check if required mappings could be applied.      */
specifier|public
specifier|abstract
name|boolean
name|apply
parameter_list|(
name|Graph
name|graph
parameter_list|,
name|BlankNodeOrIRI
name|subject
parameter_list|,
name|Metadata
name|metadata
parameter_list|)
function_decl|;
comment|/**      * Converts the parsed value based on the mapping information to an RDF      * {@link RDFTerm}. Optionally supports also validation if the parsed      * value is valid for the {@link Mapping#ontType ontology type} specified by      * the parsed mapping.      * @param value the value      * @param mapping the mapping      * @param validate       * @return the {@link RDFTerm} or<code>null</code> if the parsed value is      *<code>null</code> or {@link String#isEmpty() empty}.      * @throws IllegalArgumentException if the parsed {@link Mapping} is       *<code>null</code>      */
specifier|protected
name|RDFTerm
name|toResource
parameter_list|(
name|String
name|value
parameter_list|,
name|boolean
name|validate
parameter_list|)
block|{
name|Metadata
name|dummy
init|=
literal|null
decl_stmt|;
comment|//used for date validation
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
comment|//ignore null and empty values
block|}
name|RDFTerm
name|object
decl_stmt|;
if|if
condition|(
name|ontType
operator|==
literal|null
condition|)
block|{
name|object
operator|=
operator|new
name|PlainLiteralImpl
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|ontType
operator|==
name|RDFS
operator|.
name|Resource
condition|)
block|{
try|try
block|{
if|if
condition|(
name|validate
condition|)
block|{
operator|new
name|URI
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
name|object
operator|=
operator|new
name|IRI
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to create Reference for value {} (not a valid URI)"
operator|+
literal|" -> create a literal instead"
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|object
operator|=
operator|new
name|PlainLiteralImpl
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//typed literal
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
init|=
name|Mapping
operator|.
name|ONT_TYPE_MAP
operator|.
name|get
argument_list|(
name|ontType
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|equals
argument_list|(
name|Date
operator|.
name|class
argument_list|)
condition|)
block|{
comment|//special handling for dates :(
comment|//Dates are special, because Clerezza requires W3C date format
comment|//and Tika uses the iso8601 variants.
comment|//Because of that here is Tika used to get the Date object for
comment|//the parsed value and than the LiteralFactory of Clerezza to
comment|//create the TypedLiteral.
comment|//Note that because of that no validation is required for
comment|//Dates.
comment|//Need a dummy metadata object to get access to the private
comment|//parseDate(..) method
if|if
condition|(
name|dummy
operator|==
literal|null
condition|)
block|{
name|dummy
operator|=
operator|new
name|Metadata
argument_list|()
expr_stmt|;
block|}
comment|//any Property with the Date type could be used here
name|dummy
operator|.
name|add
argument_list|(
name|DATE
operator|.
name|getName
argument_list|()
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|Date
name|date
init|=
name|dummy
operator|.
name|getDate
argument_list|(
name|DublinCore
operator|.
name|DATE
argument_list|)
decl_stmt|;
comment|//access parseDate(..)
if|if
condition|(
name|date
operator|!=
literal|null
condition|)
block|{
comment|//now use the Clerezza Literal factory
name|object
operator|=
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|date
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//fall back to xsd:string
name|object
operator|=
operator|new
name|TypedLiteralImpl
argument_list|(
name|value
argument_list|,
name|XSD
operator|.
name|string
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|object
operator|=
operator|new
name|TypedLiteralImpl
argument_list|(
name|value
argument_list|,
name|ontType
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|validate
operator|&&
name|clazz
operator|!=
literal|null
operator|&&
operator|!
name|clazz
operator|.
name|equals
argument_list|(
name|Date
operator|.
name|class
argument_list|)
condition|)
block|{
comment|//we need not to validate dates
try|try
block|{
name|lf
operator|.
name|createObject
argument_list|(
name|clazz
argument_list|,
operator|(
name|Literal
operator|)
name|object
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoConvertorException
name|e
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Unable to validate typed literals of type {} because"
operator|+
literal|"there is no converter for Class {} registered with Clerezza"
argument_list|,
name|ontType
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvalidLiteralTypeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"The value '{}' is not valid for dataType {}!"
operator|+
literal|"create literal with type 'xsd:string' instead"
argument_list|,
name|value
argument_list|,
name|ontType
argument_list|)
expr_stmt|;
name|object
operator|=
operator|new
name|TypedLiteralImpl
argument_list|(
name|value
argument_list|,
name|XSD
operator|.
name|string
argument_list|)
expr_stmt|;
block|}
block|}
comment|//else no validation needed
block|}
if|if
condition|(
name|converter
operator|!=
literal|null
condition|)
block|{
name|object
operator|=
name|converter
operator|.
name|convert
argument_list|(
name|object
argument_list|)
expr_stmt|;
block|}
return|return
name|object
return|;
block|}
comment|/**      * Used by subclasses to log mapped information      */
specifier|protected
specifier|final
specifier|static
name|MappingLogger
name|mappingLogger
init|=
operator|new
name|MappingLogger
argument_list|()
decl_stmt|;
comment|/**      * Allows nicely formatted logging of mapped properties      * @author Rupert Westenthaler      *      */
specifier|protected
specifier|static
specifier|final
class|class
name|MappingLogger
block|{
specifier|private
name|List
argument_list|<
name|BlankNodeOrIRI
argument_list|>
name|subjects
init|=
operator|new
name|ArrayList
argument_list|<
name|BlankNodeOrIRI
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|IRI
name|predicate
decl_stmt|;
specifier|private
specifier|final
name|int
name|intendSize
init|=
literal|2
decl_stmt|;
specifier|private
specifier|final
name|char
index|[]
name|intnedArray
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|MAX_INTEND
init|=
literal|5
decl_stmt|;
specifier|private
name|MappingLogger
parameter_list|()
block|{
name|intnedArray
operator|=
operator|new
name|char
index|[
name|MAX_INTEND
operator|*
name|intendSize
index|]
expr_stmt|;
name|Arrays
operator|.
name|fill
argument_list|(
name|intnedArray
argument_list|,
literal|' '
argument_list|)
expr_stmt|;
block|}
specifier|private
name|String
name|getIntend
parameter_list|(
name|int
name|intend
parameter_list|)
block|{
return|return
name|String
operator|.
name|copyValueOf
argument_list|(
name|intnedArray
argument_list|,
literal|0
argument_list|,
name|Math
operator|.
name|min
argument_list|(
name|MAX_INTEND
argument_list|,
name|intend
argument_list|)
operator|*
name|intendSize
argument_list|)
return|;
block|}
specifier|protected
name|void
name|log
parameter_list|(
name|BlankNodeOrIRI
name|subject
parameter_list|,
name|IRI
name|predicate
parameter_list|,
name|String
name|prop
parameter_list|,
name|RDFTerm
name|object
parameter_list|)
block|{
if|if
condition|(
operator|!
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
return|return;
block|}
name|int
name|intendCount
init|=
name|subjects
operator|.
name|indexOf
argument_list|(
name|subject
argument_list|)
operator|+
literal|1
decl_stmt|;
specifier|final
name|String
name|intend
decl_stmt|;
if|if
condition|(
name|intendCount
operator|<
literal|1
condition|)
block|{
name|subjects
operator|.
name|add
argument_list|(
name|subject
argument_list|)
expr_stmt|;
name|intendCount
operator|=
name|subjects
operator|.
name|size
argument_list|()
expr_stmt|;
name|intend
operator|=
name|getIntend
argument_list|(
name|intendCount
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"{}context: {}"
argument_list|,
name|intend
argument_list|,
name|subject
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|intendCount
operator|<
name|subjects
operator|.
name|size
argument_list|()
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|intendCount
init|;
name|i
operator|<
name|subjects
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|subjects
operator|.
name|remove
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
name|intend
operator|=
name|getIntend
argument_list|(
name|intendCount
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|intend
operator|=
name|getIntend
argument_list|(
name|intendCount
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|predicate
operator|.
name|equals
argument_list|(
name|this
operator|.
name|predicate
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"{}  {}"
argument_list|,
name|intend
argument_list|,
name|predicate
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"{}    {} {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|intend
block|,
name|object
block|,
name|prop
operator|!=
literal|null
condition|?
operator|(
literal|"(from: '"
operator|+
name|prop
operator|+
literal|')'
operator|)
else|:
literal|""
block|}
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
interface|interface
name|Converter
block|{
name|RDFTerm
name|convert
parameter_list|(
name|RDFTerm
name|value
parameter_list|)
function_decl|;
block|}
block|}
end_class

end_unit

