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
name|factstore
operator|.
name|model
package|;
end_package

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
name|stanbol
operator|.
name|commons
operator|.
name|jsonld
operator|.
name|JsonLd
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
name|commons
operator|.
name|jsonld
operator|.
name|JsonLdIRI
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
name|commons
operator|.
name|jsonld
operator|.
name|JsonLdResource
import|;
end_import

begin_class
specifier|public
class|class
name|Query
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SELECT
init|=
literal|"SELECT"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|FROM
init|=
literal|"FROM"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|WHERE
init|=
literal|"WHERE"
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|roles
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
name|fromSchemaURN
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|WhereClause
argument_list|>
name|whereClauses
init|=
operator|new
name|HashSet
argument_list|<
name|WhereClause
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getRoles
parameter_list|()
block|{
return|return
name|roles
return|;
block|}
specifier|public
name|String
name|getFromSchemaURN
parameter_list|()
block|{
return|return
name|fromSchemaURN
return|;
block|}
specifier|public
name|Set
argument_list|<
name|WhereClause
argument_list|>
name|getWhereClauses
parameter_list|()
block|{
return|return
name|whereClauses
return|;
block|}
specifier|public
specifier|static
name|Query
name|toQueryFromJsonLd
parameter_list|(
name|JsonLd
name|jsonLdQuery
parameter_list|)
throws|throws
name|Exception
block|{
name|Query
name|query
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|jsonLdQuery
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|jsonLdQuery
operator|.
name|getResourceSubjects
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|jsonLdQuery
operator|.
name|getResourceSubjects
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|JsonLdResource
name|subject
init|=
name|jsonLdQuery
operator|.
name|getResource
argument_list|(
name|jsonLdQuery
operator|.
name|getResourceSubjects
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|subject
operator|.
name|hasPropertyIgnorecase
argument_list|(
name|SELECT
argument_list|)
condition|)
block|{
if|if
condition|(
name|subject
operator|.
name|hasPropertyIgnorecase
argument_list|(
name|FROM
argument_list|)
condition|)
block|{
if|if
condition|(
name|subject
operator|.
name|hasPropertyIgnorecase
argument_list|(
name|WHERE
argument_list|)
condition|)
block|{
name|query
operator|=
operator|new
name|Query
argument_list|()
expr_stmt|;
name|handleSelect
argument_list|(
name|subject
argument_list|,
name|query
argument_list|)
expr_stmt|;
name|handleFrom
argument_list|(
name|jsonLdQuery
argument_list|,
name|subject
argument_list|,
name|query
argument_list|)
expr_stmt|;
name|handleWhere
argument_list|(
name|jsonLdQuery
argument_list|,
name|subject
argument_list|,
name|query
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Query does not define a WHERE"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Query does not define a FROM"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Query does not define a SELECT"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Query does not consist of exactly 1 JSON-LD subject"
argument_list|)
throw|;
block|}
block|}
block|}
return|return
name|query
return|;
block|}
specifier|private
specifier|static
name|void
name|handleSelect
parameter_list|(
name|JsonLdResource
name|subject
parameter_list|,
name|Query
name|query
parameter_list|)
throws|throws
name|Exception
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|List
argument_list|<
name|String
argument_list|>
name|selects
init|=
operator|(
name|List
argument_list|<
name|String
argument_list|>
operator|)
name|subject
operator|.
name|getPropertyValueIgnoreCase
argument_list|(
name|SELECT
argument_list|)
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|role
range|:
name|selects
control|)
block|{
name|query
operator|.
name|roles
operator|.
name|add
argument_list|(
name|role
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|void
name|handleFrom
parameter_list|(
name|JsonLd
name|jsonLd
parameter_list|,
name|JsonLdResource
name|subject
parameter_list|,
name|Query
name|query
parameter_list|)
block|{
name|query
operator|.
name|fromSchemaURN
operator|=
operator|(
name|String
operator|)
name|subject
operator|.
name|getPropertyValueIgnoreCase
argument_list|(
name|FROM
argument_list|)
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getValue
argument_list|()
expr_stmt|;
empty_stmt|;
name|query
operator|.
name|fromSchemaURN
operator|=
name|jsonLd
operator|.
name|unCURIE
argument_list|(
name|query
operator|.
name|fromSchemaURN
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|void
name|handleWhere
parameter_list|(
name|JsonLd
name|jsonLd
parameter_list|,
name|JsonLdResource
name|subject
parameter_list|,
name|Query
name|query
parameter_list|)
throws|throws
name|Exception
block|{
annotation|@
name|SuppressWarnings
argument_list|(
block|{
literal|"unchecked"
block|,
literal|"rawtypes"
block|}
argument_list|)
name|List
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|>
argument_list|>
name|wheres
init|=
operator|(
name|List
operator|)
name|subject
operator|.
name|getPropertyValueIgnoreCase
argument_list|(
name|WHERE
argument_list|)
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
empty_stmt|;
for|for
control|(
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|>
name|whereObj
range|:
name|wheres
control|)
block|{
for|for
control|(
name|String
name|operator
range|:
name|whereObj
operator|.
name|keySet
argument_list|()
control|)
block|{
if|if
condition|(
name|operator
operator|.
name|equalsIgnoreCase
argument_list|(
name|CompareOperator
operator|.
name|EQ
operator|.
name|getLiteral
argument_list|()
argument_list|)
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|compareValues
init|=
name|whereObj
operator|.
name|get
argument_list|(
name|operator
argument_list|)
decl_stmt|;
if|if
condition|(
name|compareValues
operator|.
name|keySet
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|String
name|comparedRole
init|=
operator|(
name|String
operator|)
name|compareValues
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|searchedValue
init|=
literal|null
decl_stmt|;
name|Object
name|searchedValueObj
init|=
name|compareValues
operator|.
name|get
argument_list|(
name|comparedRole
argument_list|)
decl_stmt|;
if|if
condition|(
name|searchedValueObj
operator|instanceof
name|JsonLdIRI
condition|)
block|{
name|JsonLdIRI
name|iri
init|=
operator|(
name|JsonLdIRI
operator|)
name|searchedValueObj
decl_stmt|;
name|searchedValue
operator|=
name|jsonLd
operator|.
name|unCURIE
argument_list|(
name|iri
operator|.
name|getIRI
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|searchedValue
operator|=
name|searchedValueObj
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|WhereClause
name|wc
init|=
operator|new
name|WhereClause
argument_list|()
decl_stmt|;
name|wc
operator|.
name|setCompareOperator
argument_list|(
name|CompareOperator
operator|.
name|EQ
argument_list|)
expr_stmt|;
name|wc
operator|.
name|setComparedRole
argument_list|(
name|comparedRole
argument_list|)
expr_stmt|;
name|wc
operator|.
name|setSearchedValue
argument_list|(
name|searchedValue
argument_list|)
expr_stmt|;
name|query
operator|.
name|whereClauses
operator|.
name|add
argument_list|(
name|wc
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Unknown compare operator '"
operator|+
name|operator
operator|+
literal|"' in WHERE clause"
argument_list|)
throw|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

