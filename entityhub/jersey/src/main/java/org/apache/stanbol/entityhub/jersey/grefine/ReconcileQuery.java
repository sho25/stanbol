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
name|entityhub
operator|.
name|jersey
operator|.
name|grefine
package|;
end_package

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
name|Iterator
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
name|Map
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
name|java
operator|.
name|util
operator|.
name|Set
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
name|Response
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
name|entityhub
operator|.
name|core
operator|.
name|model
operator|.
name|InMemoryValueFactory
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|defaults
operator|.
name|NamespaceEnum
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
name|entityhub
operator|.
name|servicesapi
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
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONArray
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONObject
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
comment|/**  * Java Representation for<a href="http://code.google.com/p/google-refine/wiki/ReconciliationServiceApi#Query_Request">  * Google Refine Reconciliation queries</a>.<p>  * {@link #getTypes()} and {@link Value#getId()} do support 'prefix:localname'  * syntax for prefixes defined in the {@link NamespaceEnum}.  * Also defines methods for parsing single and multiple request strings.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|ReconcileQuery
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
name|ReconcileQuery
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Integer
name|DEFAULT_LIMIT
init|=
literal|5
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|TYPE_STRICT
name|DEFAULT_TYPE_STRICT
init|=
name|TYPE_STRICT
operator|.
name|any
decl_stmt|;
specifier|private
specifier|final
name|String
name|query
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|types
decl_stmt|;
specifier|private
name|Integer
name|limit
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Value
argument_list|>
argument_list|>
name|properties
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Value
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|TYPE_STRICT
name|typeStrict
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|ValueFactory
name|vf
init|=
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
comment|/**      * @return the limit      */
specifier|public
specifier|final
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|limit
return|;
block|}
comment|/**      * @param limit the limit to set      */
specifier|public
specifier|final
name|void
name|setLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
name|this
operator|.
name|limit
operator|=
name|limit
expr_stmt|;
block|}
comment|/**      * @return the typeStrict      */
specifier|public
specifier|final
name|TYPE_STRICT
name|getTypeStrict
parameter_list|()
block|{
return|return
name|typeStrict
return|;
block|}
comment|/**      * @param typeStrict the typeStrict to set      */
specifier|public
specifier|final
name|void
name|setTypeStrict
parameter_list|(
name|TYPE_STRICT
name|typeStrict
parameter_list|)
block|{
name|this
operator|.
name|typeStrict
operator|=
name|typeStrict
expr_stmt|;
block|}
comment|/**      * @return the query      */
specifier|public
specifier|final
name|String
name|getQuery
parameter_list|()
block|{
return|return
name|query
return|;
block|}
comment|/**      * @return the types      */
specifier|public
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|getTypes
parameter_list|()
block|{
return|return
name|types
return|;
block|}
specifier|public
specifier|static
enum|enum
name|TYPE_STRICT
block|{
name|any
block|,
name|all
block|,
name|should
block|}
empty_stmt|;
specifier|public
name|ReconcileQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|types
parameter_list|)
block|{
if|if
condition|(
name|query
operator|==
literal|null
operator|||
name|query
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed query string MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
if|if
condition|(
name|types
operator|==
literal|null
operator|||
name|types
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|types
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|t
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|types
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|type
range|:
name|types
control|)
block|{
if|if
condition|(
name|type
operator|!=
literal|null
operator|&&
operator|!
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|t
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
name|this
operator|.
name|types
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|Collection
argument_list|<
name|Value
argument_list|>
name|putProperty
parameter_list|(
name|String
name|field
parameter_list|,
name|Collection
argument_list|<
name|Value
argument_list|>
name|values
parameter_list|)
block|{
if|if
condition|(
name|field
operator|==
literal|null
operator|||
name|field
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The field for an property MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|values
operator|==
literal|null
operator|||
name|values
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|properties
operator|.
name|remove
argument_list|(
name|values
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|properties
operator|.
name|put
argument_list|(
name|field
argument_list|,
name|values
argument_list|)
return|;
block|}
block|}
specifier|public
name|Collection
argument_list|<
name|Value
argument_list|>
name|removeProperty
parameter_list|(
name|String
name|field
parameter_list|)
block|{
return|return
name|properties
operator|.
name|remove
argument_list|(
name|field
argument_list|)
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|Value
argument_list|>
name|getProperty
parameter_list|(
name|String
name|field
parameter_list|)
block|{
return|return
name|properties
operator|.
name|get
argument_list|(
name|field
argument_list|)
return|;
block|}
specifier|public
name|Iterable
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Value
argument_list|>
argument_list|>
argument_list|>
name|getProperties
parameter_list|()
block|{
return|return
name|properties
operator|.
name|entrySet
argument_list|()
return|;
block|}
comment|/**      * Values can be simple JSON values or JSON objects with an 'id' and a      * 'name'. This is mapped to {@link Value} objects with an optional       * {@link #getId()} and a required {@link #getValue()}.<p>      * The 'id' supports prefix:localname syntax for prefixes defined within the      * {@link NamespaceEnum}      * @author Rupert Westenthaler      *      */
specifier|public
specifier|static
class|class
name|Value
block|{
specifier|private
specifier|final
name|String
name|id
decl_stmt|;
specifier|private
specifier|final
name|Object
name|value
decl_stmt|;
specifier|private
name|Value
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|private
name|Value
parameter_list|(
name|String
name|id
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
operator|==
literal|null
condition|?
literal|null
else|:
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|id
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed value MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
comment|/**          * The getter for the value of the 'id' property of the 'v' object          * if present. This represents the value of fields that are already          * successfully linked (reconciled) with some entity.          * @return the id          */
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
comment|/**          * @return the value          */
specifier|public
name|Object
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/**          * Calls the {@link #toString()} method of the {@link #getValue()}          */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|value
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|id
operator|!=
literal|null
condition|?
name|id
operator|.
name|hashCode
argument_list|()
else|:
name|value
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|instanceof
name|Value
operator|&&
operator|(
comment|//other is value
operator|(
name|id
operator|!=
literal|null
operator|&&
name|id
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Value
operator|)
name|o
operator|)
operator|.
name|id
argument_list|)
operator|)
operator|||
comment|//ids are equals or
operator|(
name|id
operator|==
literal|null
operator|&&
operator|(
operator|(
name|Value
operator|)
name|o
operator|)
operator|.
name|id
operator|==
literal|null
comment|//ids are null and
operator|&&
name|value
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Value
operator|)
name|o
operator|)
operator|.
name|value
argument_list|)
operator|)
operator|)
return|;
comment|//values are equals
block|}
block|}
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|ReconcileQuery
argument_list|>
name|parseQueries
parameter_list|(
name|String
name|queriesString
parameter_list|)
throws|throws
name|WebApplicationException
block|{
name|JSONObject
name|jQueries
decl_stmt|;
try|try
block|{
name|jQueries
operator|=
operator|new
name|JSONObject
argument_list|(
name|queriesString
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|status
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The parsed query is illegal formatted! \n query: \n"
operator|+
name|queriesString
operator|+
literal|"\n"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
throw|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|Iterator
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|jQueries
operator|.
name|keys
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|ReconcileQuery
argument_list|>
name|queries
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ReconcileQuery
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|keys
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|key
init|=
name|keys
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
name|ReconcileQuery
name|query
init|=
name|parseQuery
argument_list|(
name|jQueries
operator|.
name|getJSONObject
argument_list|(
name|key
argument_list|)
argument_list|)
decl_stmt|;
name|queries
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|query
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|status
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The query of key '"
operator|+
name|key
operator|+
literal|"is illegal formatted! \n query: \n"
operator|+
name|queriesString
operator|+
literal|"\n"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
throw|;
block|}
block|}
return|return
name|queries
return|;
block|}
comment|/**      * Parses a Google Refine Reconcile Query from the parsed String.      * @param queryString the string representation of the reconcile query      * @return the parsed {@link ReconcileQuery} object      * @throws WebApplicationException {@link Response.Status#BAD_REQUEST} in      * case of the parsed string is not a well formated query. Unsupported      * Properties are silently ignored (warnings are still logged).      */
specifier|public
specifier|static
name|ReconcileQuery
name|parseQuery
parameter_list|(
name|String
name|queryString
parameter_list|)
throws|throws
name|WebApplicationException
block|{
name|JSONObject
name|jQuery
decl_stmt|;
try|try
block|{
if|if
condition|(
name|queryString
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'{'
condition|)
block|{
name|jQuery
operator|=
operator|new
name|JSONObject
argument_list|(
name|queryString
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|jQuery
operator|=
operator|new
name|JSONObject
argument_list|()
expr_stmt|;
name|jQuery
operator|.
name|put
argument_list|(
literal|"query"
argument_list|,
name|queryString
argument_list|)
expr_stmt|;
comment|//simple string query
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|status
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The parsed query is illegal formatted! \n query: \n"
operator|+
name|queryString
operator|+
literal|"\n"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
throw|;
block|}
return|return
name|parseQuery
argument_list|(
name|jQuery
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|ReconcileQuery
name|parseQuery
parameter_list|(
name|JSONObject
name|jQuery
parameter_list|)
throws|throws
name|WebApplicationException
block|{
comment|//query (string)
comment|//limit (integer), optional
comment|//type (string| [string]), optional
comment|//type_strict ("any","all","should"), optional
comment|//properties ([Property]), optional
comment|//    Property:
comment|//        p (string)  -> ignore
comment|//        pid (string) -> uri
comment|//        v (string/Value, [string/Value]), required
comment|//    Value
comment|//        id (uri)
name|String
name|value
init|=
name|jQuery
operator|.
name|optString
argument_list|(
literal|"query"
argument_list|)
decl_stmt|;
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
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|status
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"The parsed query is illegal formatted! \n query: \n"
operator|+
name|jQuery
operator|.
name|toString
argument_list|()
operator|+
literal|"\n"
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
throw|;
block|}
name|JSONArray
name|jTypes
init|=
literal|null
decl_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|types
decl_stmt|;
if|if
condition|(
operator|!
name|jQuery
operator|.
name|has
argument_list|(
literal|"type"
argument_list|)
condition|)
block|{
name|types
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|jTypes
operator|=
name|jQuery
operator|.
name|optJSONArray
argument_list|(
literal|"type"
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|types
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|jTypes
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jTypes
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|type
init|=
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|jTypes
operator|.
name|optString
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
operator|&&
operator|!
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|types
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|String
name|type
init|=
name|jQuery
operator|.
name|optString
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
operator|&&
operator|!
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|types
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|types
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
block|}
name|ReconcileQuery
name|reconcileQuery
init|=
operator|new
name|ReconcileQuery
argument_list|(
name|value
argument_list|,
name|types
argument_list|)
decl_stmt|;
comment|//TYPE_STRICT typeStrict = null;
name|String
name|jTypeStrict
init|=
name|jQuery
operator|.
name|optString
argument_list|(
literal|"type_strict"
argument_list|)
decl_stmt|;
if|if
condition|(
name|jTypeStrict
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|reconcileQuery
operator|.
name|setTypeStrict
argument_list|(
name|TYPE_STRICT
operator|.
name|valueOf
argument_list|(
name|jTypeStrict
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unknown \"type_strict\" value in Google Refine Reconcile"
operator|+
literal|" Request (use default '{}')\n {}"
argument_list|,
name|DEFAULT_TYPE_STRICT
argument_list|,
name|jQuery
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|reconcileQuery
operator|.
name|setTypeStrict
argument_list|(
name|DEFAULT_TYPE_STRICT
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|reconcileQuery
operator|.
name|setTypeStrict
argument_list|(
name|DEFAULT_TYPE_STRICT
argument_list|)
expr_stmt|;
block|}
name|reconcileQuery
operator|.
name|setLimit
argument_list|(
name|jQuery
operator|.
name|optInt
argument_list|(
literal|"limit"
argument_list|,
name|DEFAULT_LIMIT
argument_list|)
argument_list|)
expr_stmt|;
name|JSONArray
name|jProperties
init|=
name|jQuery
operator|.
name|optJSONArray
argument_list|(
literal|"properties"
argument_list|)
decl_stmt|;
if|if
condition|(
name|jProperties
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jProperties
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|parseProperty
argument_list|(
name|reconcileQuery
argument_list|,
name|jProperties
operator|.
name|optJSONObject
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|reconcileQuery
return|;
block|}
comment|/**      * Internally used to parse a Property of a Google Refine Reconcile Query      * @param reconcileQuery the query to add the property      * @param jProperty the JSON formatted property      */
specifier|private
specifier|static
name|void
name|parseProperty
parameter_list|(
name|ReconcileQuery
name|reconcileQuery
parameter_list|,
name|JSONObject
name|jProperty
parameter_list|)
block|{
if|if
condition|(
name|jProperty
operator|!=
literal|null
condition|)
block|{
comment|//parse property
name|String
name|property
init|=
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|jProperty
operator|.
name|optString
argument_list|(
literal|"pid"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|property
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Ignore Property because of missing 'pid'! \n{}"
argument_list|,
name|jProperty
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//property keys may appear multiple times in queries
comment|//so we need to initialise the property values with already
comment|//existing values
name|Collection
argument_list|<
name|Value
argument_list|>
name|values
init|=
name|reconcileQuery
operator|.
name|getProperty
argument_list|(
name|property
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
condition|)
block|{
comment|//if not create a new Set
comment|//maybe the order is important (e.g. for similarity alg)
comment|//   ... so try to keep it
name|values
operator|=
operator|new
name|LinkedHashSet
argument_list|<
name|Value
argument_list|>
argument_list|()
expr_stmt|;
block|}
comment|//parse the value
name|Object
name|jValue
init|=
name|jProperty
operator|.
name|opt
argument_list|(
literal|"v"
argument_list|)
decl_stmt|;
if|if
condition|(
name|jValue
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Ignore Property '{}' because it has no value! \n {}"
argument_list|,
name|property
argument_list|,
name|jProperty
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|jValue
operator|instanceof
name|JSONObject
condition|)
block|{
comment|//Reconciliation data available!
name|Value
name|value
init|=
name|parseValueFromV
argument_list|(
name|jValue
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|values
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"ignore value for property {} because no name is present (value: {})!"
argument_list|,
name|property
argument_list|,
name|jValue
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|jValue
operator|instanceof
name|JSONArray
condition|)
block|{
comment|//parse value list
name|JSONArray
name|jValueArray
init|=
operator|(
name|JSONArray
operator|)
name|jValue
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|jValueArray
operator|.
name|length
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|jValue
operator|=
name|jValueArray
operator|.
name|opt
argument_list|(
name|j
argument_list|)
expr_stmt|;
if|if
condition|(
name|jValue
operator|instanceof
name|JSONObject
condition|)
block|{
comment|//Reconciliation data available!
name|Value
name|value
init|=
name|parseValueFromV
argument_list|(
name|jValue
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|values
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"ignore value for property {} because no name is present (value: {})!"
argument_list|,
name|property
argument_list|,
name|jValue
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|jValue
operator|!=
literal|null
condition|)
block|{
name|values
operator|.
name|add
argument_list|(
operator|new
name|Value
argument_list|(
name|jValue
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|values
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Ignore Property '{}' because it does not define a valid value! \n {}"
argument_list|,
name|property
argument_list|,
name|jProperty
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//number or String
name|values
operator|.
name|add
argument_list|(
operator|new
name|Value
argument_list|(
name|jValue
argument_list|)
argument_list|)
expr_stmt|;
comment|//directly use the value
block|}
if|if
condition|(
operator|!
name|values
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|reconcileQuery
operator|.
name|putProperty
argument_list|(
name|property
argument_list|,
name|values
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**      * Parses a Value from a JSON Object by reading the 'id' and 'name' keys      * @param jValue      * @return The value or<code>null</code> if the parsed json object does not      * contain the required information.      */
specifier|private
specifier|static
name|Value
name|parseValueFromV
parameter_list|(
name|Object
name|jValue
parameter_list|)
block|{
name|String
name|id
init|=
operator|(
operator|(
name|JSONObject
operator|)
name|jValue
operator|)
operator|.
name|optString
argument_list|(
literal|"id"
argument_list|)
decl_stmt|;
name|String
name|value
init|=
operator|(
operator|(
name|JSONObject
operator|)
name|jValue
operator|)
operator|.
name|optString
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
return|return
name|value
operator|!=
literal|null
condition|?
operator|new
name|Value
argument_list|(
name|id
argument_list|,
name|value
argument_list|)
else|:
literal|null
return|;
block|}
block|}
end_class

end_unit

