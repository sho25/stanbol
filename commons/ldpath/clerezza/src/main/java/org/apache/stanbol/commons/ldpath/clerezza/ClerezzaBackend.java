begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

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
name|ldpath
operator|.
name|clerezza
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
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|ParseException
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
name|Collection
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
name|Locale
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
name|concurrent
operator|.
name|ThreadPoolExecutor
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
name|BNode
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
name|Language
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|PlainLiteral
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
name|Resource
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
name|Triple
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
name|TripleCollection
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
name|TypedLiteral
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
name|UriRef
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
name|impl
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
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|SimpleLiteralFactory
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
name|impl
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
name|core
operator|.
name|impl
operator|.
name|util
operator|.
name|W3CDateFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|collections
operator|.
name|BidiMap
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|collections
operator|.
name|bidimap
operator|.
name|DualHashBidiMap
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
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|model
operator|.
name|backend
operator|.
name|AbstractBackend
import|;
end_import

begin_comment
comment|/**  * Clerezza based implementation of {@link RDFBackend} interface. This implementation uses the  * {@link Resource} objects of Clerezza as processing unit RDFBackend.<p>  *   * For type conversions of {@link TypedLiteral}s the {@link LiteralFactory}  * of Clerezza is used. In case parsed nodes are not {@link TypedLiteral} the  * super implementations of {@link AbstractBackend} are called as such also  * support converting values based on the string representation.  *   * @author anil.sinaci  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|ClerezzaBackend
extends|extends
name|AbstractBackend
argument_list|<
name|Resource
argument_list|>
implements|implements
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ClerezzaBackend
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Enumeration containing supported XSD dataTypes including<ul>      *<li> local name      *<li> uri string      *<li> {@link URI}      *<li> {@link UriRef}      *</ul>      * {@link #toString()} returns the uri.      */
specifier|public
specifier|static
enum|enum
name|XSD
block|{
name|INTEGER
block|,
name|INT
block|,
name|SHORT
block|,
name|BYTE
block|,
name|LONG
block|,
name|DOUBLE
block|,
name|FLOAT
block|,
name|ANY_URI
argument_list|(
literal|"anyURI"
argument_list|)
block|,
name|DATE_TIME
argument_list|(
literal|"dateTime"
argument_list|)
block|,
name|BOOLEAN
block|,
name|STRING
block|;
specifier|static
specifier|final
name|String
name|namespace
init|=
literal|"http://www.w3.org/2001/XMLSchema#"
decl_stmt|;
name|String
name|localName
decl_stmt|;
name|String
name|uriString
decl_stmt|;
name|URI
name|uri
decl_stmt|;
name|UriRef
name|uriRef
decl_stmt|;
comment|/**          * uses<code>{@link #name()}{@link String#toLowerCase() .toLoverCase()}          *</code> to generate the {@link #getLocalName()}          */
specifier|private
name|XSD
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**          * Constructor that allows to parse the local name. if<code>null</code>          * it uses<code>{@link #name()}{@link String#toLowerCase() .toLoverCase()}          *</code> to generate the {@link #getLocalName() localName}          * @param localName the local name or<code>null</code> to use           *<code>{@link #name()}{@link String#toLowerCase() .toLoverCase()}          *</code>          */
specifier|private
name|XSD
parameter_list|(
name|String
name|localName
parameter_list|)
block|{
name|this
operator|.
name|localName
operator|=
name|localName
operator|!=
literal|null
condition|?
name|localName
else|:
name|name
argument_list|()
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|this
operator|.
name|uriString
operator|=
name|namespace
operator|+
name|this
operator|.
name|localName
expr_stmt|;
name|this
operator|.
name|uri
operator|=
name|URI
operator|.
name|create
argument_list|(
name|uriString
argument_list|)
expr_stmt|;
name|this
operator|.
name|uriRef
operator|=
operator|new
name|UriRef
argument_list|(
name|uriString
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getLocalName
parameter_list|()
block|{
return|return
name|localName
return|;
block|}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|uriString
return|;
block|}
specifier|public
name|URI
name|getURI
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
specifier|public
name|UriRef
name|getUriRef
parameter_list|()
block|{
return|return
name|uriRef
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
name|uriString
return|;
block|}
specifier|private
specifier|static
name|BidiMap
name|xsdURI2UriRef
init|=
operator|new
name|DualHashBidiMap
argument_list|()
decl_stmt|;
static|static
block|{
for|for
control|(
name|XSD
name|type
range|:
name|XSD
operator|.
name|values
argument_list|()
control|)
block|{
name|xsdURI2UriRef
operator|.
name|put
argument_list|(
name|type
operator|.
name|getURI
argument_list|()
argument_list|,
name|type
operator|.
name|getUriRef
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|URI
name|getXsdURI
parameter_list|(
name|UriRef
name|uri
parameter_list|)
block|{
return|return
operator|(
name|URI
operator|)
name|xsdURI2UriRef
operator|.
name|getKey
argument_list|(
name|uri
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|UriRef
name|getXsdUriRef
parameter_list|(
name|URI
name|uri
parameter_list|)
block|{
return|return
operator|(
name|UriRef
operator|)
name|xsdURI2UriRef
operator|.
name|get
argument_list|(
name|uri
argument_list|)
return|;
block|}
block|}
specifier|private
name|TripleCollection
name|graph
decl_stmt|;
specifier|private
specifier|static
name|LiteralFactory
name|lf
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|public
name|ClerezzaBackend
parameter_list|(
name|TripleCollection
name|graph
parameter_list|)
block|{
name|this
operator|.
name|graph
operator|=
name|graph
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|createLiteral
parameter_list|(
name|String
name|content
parameter_list|)
block|{
return|return
name|createLiteral
argument_list|(
name|content
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|createLiteral
parameter_list|(
name|String
name|content
parameter_list|,
name|Locale
name|language
parameter_list|,
name|URI
name|type
parameter_list|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"creating literal with content \"{}\", language {}, datatype {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|content
block|,
name|language
block|,
name|type
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|language
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|PlainLiteralImpl
argument_list|(
name|content
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|PlainLiteralImpl
argument_list|(
name|content
argument_list|,
operator|new
name|Language
argument_list|(
name|language
operator|.
name|getLanguage
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
block|}
else|else
block|{
return|return
operator|new
name|TypedLiteralImpl
argument_list|(
name|content
argument_list|,
name|XSD
operator|.
name|getXsdUriRef
argument_list|(
name|type
argument_list|)
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|createURI
parameter_list|(
name|String
name|uriref
parameter_list|)
block|{
return|return
operator|new
name|UriRef
argument_list|(
name|uriref
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Double
name|doubleValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createObject
argument_list|(
name|Double
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|resource
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|doubleValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Locale
name|getLiteralLanguage
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|PlainLiteral
condition|)
block|{
name|Language
name|lang
init|=
operator|(
operator|(
name|PlainLiteral
operator|)
name|resource
operator|)
operator|.
name|getLanguage
argument_list|()
decl_stmt|;
return|return
name|lang
operator|!=
literal|null
condition|?
operator|new
name|Locale
argument_list|(
name|lang
operator|.
name|toString
argument_list|()
argument_list|)
else|:
literal|null
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Resource "
operator|+
name|resource
operator|.
name|toString
argument_list|()
operator|+
literal|" is not a PlainLiteral"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|URI
name|getLiteralType
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
name|UriRef
name|type
init|=
operator|(
operator|(
name|TypedLiteral
operator|)
name|resource
operator|)
operator|.
name|getDataType
argument_list|()
decl_stmt|;
return|return
name|type
operator|!=
literal|null
condition|?
name|XSD
operator|.
name|getXsdURI
argument_list|(
name|type
argument_list|)
else|:
literal|null
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|resource
operator|.
name|toString
argument_list|()
operator|+
literal|" is not a literal"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isBlank
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
return|return
name|resource
operator|instanceof
name|BNode
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isLiteral
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
return|return
name|resource
operator|instanceof
name|Literal
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isURI
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
return|return
name|resource
operator|instanceof
name|UriRef
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Resource
argument_list|>
name|listObjects
parameter_list|(
name|Resource
name|subject
parameter_list|,
name|Resource
name|property
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|property
operator|instanceof
name|UriRef
operator|)
operator|||
operator|!
operator|(
name|subject
operator|instanceof
name|NonLiteral
operator|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Subject needs to be a URI or blank node, property a URI node"
argument_list|)
throw|;
block|}
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|Resource
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|triples
init|=
name|graph
operator|.
name|filter
argument_list|(
operator|(
name|NonLiteral
operator|)
name|subject
argument_list|,
operator|(
name|UriRef
operator|)
name|property
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|triples
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|triples
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Resource
argument_list|>
name|listSubjects
parameter_list|(
name|Resource
name|property
parameter_list|,
name|Resource
name|object
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|property
operator|instanceof
name|UriRef
operator|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Property needs to be a URI node"
argument_list|)
throw|;
block|}
name|Collection
argument_list|<
name|Resource
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|Resource
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|triples
init|=
name|graph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
operator|(
name|UriRef
operator|)
name|property
argument_list|,
name|object
argument_list|)
decl_stmt|;
while|while
condition|(
name|triples
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|triples
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
annotation|@
name|Override
specifier|public
name|Long
name|longValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|lf
operator|.
name|createObject
argument_list|(
name|Long
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|resource
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|longValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|stringValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|UriRef
condition|)
block|{
return|return
operator|(
operator|(
name|UriRef
operator|)
name|resource
operator|)
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|resource
operator|instanceof
name|Literal
condition|)
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|resource
operator|)
operator|.
name|getLexicalForm
argument_list|()
return|;
block|}
else|else
block|{
comment|//BNode
return|return
name|resource
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|booleanValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|lf
operator|.
name|createObject
argument_list|(
name|Boolean
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|resource
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|booleanValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Date
name|dateTimeValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|lf
operator|.
name|createObject
argument_list|(
name|Date
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|resource
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|dateTimeValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Date
name|dateValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|lf
operator|.
name|createObject
argument_list|(
name|Date
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|resource
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|dateValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Date
name|timeValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|lf
operator|.
name|createObject
argument_list|(
name|Date
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|resource
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|timeValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Float
name|floatValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|lf
operator|.
name|createObject
argument_list|(
name|Float
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|resource
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|floatValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Integer
name|intValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|lf
operator|.
name|createObject
argument_list|(
name|Integer
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|resource
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|intValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|BigInteger
name|integerValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
if|if
condition|(
name|resource
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|lf
operator|.
name|createObject
argument_list|(
name|BigInteger
operator|.
name|class
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|resource
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|integerValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|BigDecimal
name|decimalValue
parameter_list|(
name|Resource
name|resource
parameter_list|)
block|{
comment|//currently there is no converter for BigDecimal in clerezza
comment|//so as a workaround use the lexical form (as provided by the super
comment|//implementation
return|return
name|super
operator|.
name|decimalValue
argument_list|(
name|resource
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|supportsThreading
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|ThreadPoolExecutor
name|getThreadPool
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

