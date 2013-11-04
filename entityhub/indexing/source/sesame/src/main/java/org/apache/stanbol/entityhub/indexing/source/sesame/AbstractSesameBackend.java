begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  * Licensed to the Apache Software Foundation (ASF) under one  * or more contributor license agreements. See the NOTICE file  * distributed with this work for additional information  * regarding copyright ownership. The ASF licenses this file  * to you under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance  * with the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|source
operator|.
name|sesame
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
name|javax
operator|.
name|xml
operator|.
name|datatype
operator|.
name|XMLGregorianCalendar
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|BNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Literal
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Statement
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Value
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|ValueFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|repository
operator|.
name|RepositoryConnection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|repository
operator|.
name|RepositoryException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|repository
operator|.
name|RepositoryResult
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

begin_comment
comment|/**  * Sesame Backend based on the code of   *<code>org.apache.marmotta.ldpath.backend.sesame.AbstractSesameBackend</code>  * (module<code>org.apache.marmotta:ldpath-backend-sesame:3.1.0-incubating</code>.  *<p>  * TODO: as soon as the LDPath dependency is updated to the current   * Marmotta version this should be removed and extend the current Marmotta version  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractSesameBackend
implements|implements
name|RDFBackend
argument_list|<
name|Value
argument_list|>
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
name|AbstractSesameBackend
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|URI
name|createURIInternal
parameter_list|(
specifier|final
name|ValueFactory
name|valueFactory
parameter_list|,
name|String
name|uri
parameter_list|)
block|{
return|return
name|valueFactory
operator|.
name|createURI
argument_list|(
name|uri
argument_list|)
return|;
block|}
specifier|protected
name|Literal
name|createLiteralInternal
parameter_list|(
specifier|final
name|ValueFactory
name|valueFactory
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"creating literal with content \"{}\""
argument_list|,
name|content
argument_list|)
expr_stmt|;
return|return
name|valueFactory
operator|.
name|createLiteral
argument_list|(
name|content
argument_list|)
return|;
block|}
specifier|protected
name|Literal
name|createLiteralInternal
parameter_list|(
specifier|final
name|ValueFactory
name|valueFactory
parameter_list|,
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
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
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
block|}
if|if
condition|(
name|language
operator|==
literal|null
operator|&&
name|type
operator|==
literal|null
condition|)
block|{
return|return
name|valueFactory
operator|.
name|createLiteral
argument_list|(
name|content
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
return|return
name|valueFactory
operator|.
name|createLiteral
argument_list|(
name|content
argument_list|,
name|language
operator|.
name|getLanguage
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|valueFactory
operator|.
name|createLiteral
argument_list|(
name|content
argument_list|,
name|valueFactory
operator|.
name|createURI
argument_list|(
name|type
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
block|}
specifier|protected
name|Collection
argument_list|<
name|Value
argument_list|>
name|listObjectsInternal
parameter_list|(
name|RepositoryConnection
name|connection
parameter_list|,
name|Resource
name|subject
parameter_list|,
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|URI
name|property
parameter_list|,
name|boolean
name|includeInferred
parameter_list|,
name|Resource
modifier|...
name|context
parameter_list|)
throws|throws
name|RepositoryException
block|{
name|ValueFactory
name|valueFactory
init|=
name|connection
operator|.
name|getValueFactory
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Value
argument_list|>
name|result
init|=
operator|new
name|HashSet
argument_list|<
name|Value
argument_list|>
argument_list|()
decl_stmt|;
name|RepositoryResult
argument_list|<
name|Statement
argument_list|>
name|qResult
init|=
name|connection
operator|.
name|getStatements
argument_list|(
name|merge
argument_list|(
name|subject
argument_list|,
name|connection
operator|.
name|getValueFactory
argument_list|()
argument_list|)
argument_list|,
name|merge
argument_list|(
name|property
argument_list|,
name|connection
operator|.
name|getValueFactory
argument_list|()
argument_list|)
argument_list|,
literal|null
argument_list|,
name|includeInferred
argument_list|,
name|context
argument_list|)
decl_stmt|;
try|try
block|{
while|while
condition|(
name|qResult
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|qResult
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|qResult
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
specifier|protected
name|Collection
argument_list|<
name|Value
argument_list|>
name|listSubjectsInternal
parameter_list|(
specifier|final
name|RepositoryConnection
name|connection
parameter_list|,
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|URI
name|property
parameter_list|,
name|Value
name|object
parameter_list|,
name|boolean
name|includeInferred
parameter_list|,
name|Resource
modifier|...
name|context
parameter_list|)
throws|throws
name|RepositoryException
block|{
name|Set
argument_list|<
name|Value
argument_list|>
name|result
init|=
operator|new
name|HashSet
argument_list|<
name|Value
argument_list|>
argument_list|()
decl_stmt|;
name|RepositoryResult
argument_list|<
name|Statement
argument_list|>
name|qResult
init|=
name|connection
operator|.
name|getStatements
argument_list|(
literal|null
argument_list|,
name|merge
argument_list|(
name|property
argument_list|,
name|connection
operator|.
name|getValueFactory
argument_list|()
argument_list|)
argument_list|,
name|merge
argument_list|(
name|object
argument_list|,
name|connection
operator|.
name|getValueFactory
argument_list|()
argument_list|)
argument_list|,
name|includeInferred
argument_list|,
name|context
argument_list|)
decl_stmt|;
try|try
block|{
while|while
condition|(
name|qResult
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|qResult
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|qResult
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/**      * Merge the value given as argument into the value factory given as argument      * @param value      * @param vf      * @param<T>      * @return      */
specifier|protected
parameter_list|<
name|T
extends|extends
name|Value
parameter_list|>
name|T
name|merge
parameter_list|(
name|T
name|value
parameter_list|,
name|ValueFactory
name|vf
parameter_list|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|URI
condition|)
block|{
return|return
operator|(
name|T
operator|)
name|vf
operator|.
name|createURI
argument_list|(
name|value
operator|.
name|stringValue
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|BNode
condition|)
block|{
return|return
operator|(
name|T
operator|)
name|vf
operator|.
name|createBNode
argument_list|(
operator|(
operator|(
name|BNode
operator|)
name|value
operator|)
operator|.
name|getID
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|value
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|abstract
name|Literal
name|createLiteral
parameter_list|(
name|String
name|content
parameter_list|)
function_decl|;
annotation|@
name|Override
specifier|public
specifier|abstract
name|Literal
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
function_decl|;
annotation|@
name|Override
specifier|public
specifier|abstract
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|URI
name|createURI
parameter_list|(
name|String
name|uri
parameter_list|)
function_decl|;
annotation|@
name|Override
specifier|public
specifier|abstract
name|Collection
argument_list|<
name|Value
argument_list|>
name|listObjects
parameter_list|(
name|Value
name|subject
parameter_list|,
name|Value
name|property
parameter_list|)
function_decl|;
annotation|@
name|Override
specifier|public
specifier|abstract
name|Collection
argument_list|<
name|Value
argument_list|>
name|listSubjects
parameter_list|(
name|Value
name|property
parameter_list|,
name|Value
name|object
parameter_list|)
function_decl|;
annotation|@
name|Override
annotation|@
name|Deprecated
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
annotation|@
name|Deprecated
specifier|public
name|ThreadPoolExecutor
name|getThreadPool
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
comment|/**      * Test whether the node passed as argument is a literal      *      * @param n the node to check      * @return true if the node is a literal      */
annotation|@
name|Override
specifier|public
name|boolean
name|isLiteral
parameter_list|(
name|Value
name|n
parameter_list|)
block|{
return|return
name|n
operator|instanceof
name|Literal
return|;
block|}
comment|/**      * Test whether the node passed as argument is a URI      *      * @param n the node to check      * @return true if the node is a URI      */
annotation|@
name|Override
specifier|public
name|boolean
name|isURI
parameter_list|(
name|Value
name|n
parameter_list|)
block|{
return|return
name|n
operator|instanceof
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|URI
return|;
block|}
comment|/**      * Test whether the node passed as argument is a blank node      *      * @param n the node to check      * @return true if the node is a blank node      */
annotation|@
name|Override
specifier|public
name|boolean
name|isBlank
parameter_list|(
name|Value
name|n
parameter_list|)
block|{
return|return
name|n
operator|instanceof
name|BNode
return|;
block|}
comment|/**      * Return the language of the literal node passed as argument.      *      * @param n the literal node for which to return the language      * @return a Locale representing the language of the literal, or null if the literal node has no language      * @throws IllegalArgumentException in case the node is no literal      */
annotation|@
name|Override
specifier|public
name|Locale
name|getLiteralLanguage
parameter_list|(
name|Value
name|n
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
operator|(
operator|(
name|Literal
operator|)
name|n
operator|)
operator|.
name|getLanguage
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
operator|new
name|Locale
argument_list|(
operator|(
operator|(
name|Literal
operator|)
name|n
operator|)
operator|.
name|getLanguage
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|n
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|n
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * Return the URI of the type of the literal node passed as argument.      *      * @param n the literal node for which to return the typer      * @return a URI representing the type of the literal content, or null if the literal is untyped      * @throws IllegalArgumentException in case the node is no literal      */
annotation|@
name|Override
specifier|public
name|URI
name|getLiteralType
parameter_list|(
name|Value
name|n
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
operator|(
operator|(
name|Literal
operator|)
name|n
operator|)
operator|.
name|getDatatype
argument_list|()
operator|!=
literal|null
condition|)
block|{
try|try
block|{
return|return
operator|new
name|URI
argument_list|(
operator|(
operator|(
name|Literal
operator|)
name|n
operator|)
operator|.
name|getDatatype
argument_list|()
operator|.
name|stringValue
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"literal datatype was not a valid URI: {}"
argument_list|,
operator|(
operator|(
name|Literal
operator|)
name|n
operator|)
operator|.
name|getDatatype
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|n
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|n
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * Return the string value of a node. For a literal, this will be the content, for a URI node it will be the      * URI itself, and for a blank node it will be the identifier of the node.      *      * @param value      * @return      */
annotation|@
name|Override
specifier|public
name|String
name|stringValue
parameter_list|(
name|Value
name|value
parameter_list|)
block|{
return|return
name|value
operator|.
name|stringValue
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|BigDecimal
name|decimalValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
try|try
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|node
operator|)
operator|.
name|decimalValue
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|node
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|node
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|BigInteger
name|integerValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
try|try
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|node
operator|)
operator|.
name|integerValue
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|node
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|node
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|booleanValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
try|try
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|node
operator|)
operator|.
name|booleanValue
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|node
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|node
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Date
name|dateTimeValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
try|try
block|{
name|XMLGregorianCalendar
name|cal
init|=
operator|(
operator|(
name|Literal
operator|)
name|node
operator|)
operator|.
name|calendarValue
argument_list|()
decl_stmt|;
comment|//TODO: check if we need to deal with timezone and Local here
return|return
name|cal
operator|.
name|toGregorianCalendar
argument_list|()
operator|.
name|getTime
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|node
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|node
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Date
name|dateValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
try|try
block|{
name|XMLGregorianCalendar
name|cal
init|=
operator|(
operator|(
name|Literal
operator|)
name|node
operator|)
operator|.
name|calendarValue
argument_list|()
decl_stmt|;
return|return
name|cal
operator|.
name|toGregorianCalendar
argument_list|()
operator|.
name|getTime
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|node
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|node
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Date
name|timeValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
comment|//TODO: Unless someone knows how to create a Date that only has the time
comment|//      from a XMLGregorianCalendar
return|return
name|dateTimeValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Long
name|longValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
try|try
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|node
operator|)
operator|.
name|longValue
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|node
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|node
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Double
name|doubleValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
try|try
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|node
operator|)
operator|.
name|doubleValue
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|node
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|node
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Float
name|floatValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
try|try
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|node
operator|)
operator|.
name|floatValue
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|node
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|node
argument_list|)
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Integer
name|intValue
parameter_list|(
name|Value
name|node
parameter_list|)
block|{
try|try
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|node
operator|)
operator|.
name|intValue
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Value "
operator|+
name|node
operator|.
name|stringValue
argument_list|()
operator|+
literal|" is not a literal"
operator|+
literal|"but of type "
operator|+
name|debugType
argument_list|(
name|node
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * Prints the type (URI,bNode,literal) by inspecting the parsed {@link Value}      * to improve error messages and other loggings. In case of literals       * also the {@link #getLiteralType(Value) literal type} is printed      * @param value the value or<code>null</code>       * @return the type as string.      */
specifier|protected
name|String
name|debugType
parameter_list|(
name|Value
name|value
parameter_list|)
block|{
return|return
name|value
operator|==
literal|null
condition|?
literal|"null"
else|:
name|isURI
argument_list|(
name|value
argument_list|)
condition|?
literal|"URI"
else|:
name|isBlank
argument_list|(
name|value
argument_list|)
condition|?
literal|"bNode"
else|:
literal|"literal ("
operator|+
name|getLiteralType
argument_list|(
name|value
argument_list|)
operator|+
literal|")"
return|;
block|}
block|}
end_class

end_unit

