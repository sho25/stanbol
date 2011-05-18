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
name|core
operator|.
name|query
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
name|query
operator|.
name|Constraint
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
name|query
operator|.
name|FieldQuery
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
comment|/**  * Default implementation of the FieldQuery interface.  * Note that the getter methods are defined as final. So implementations that  * need to overwrite some functionality need to use the sets provided by this  * implementation to store selected fields and field constraints.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|FieldQueryImpl
implements|implements
name|Cloneable
implements|,
name|FieldQuery
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
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
name|FieldQueryImpl
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Constraint
argument_list|>
name|queryConstraint
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Constraint
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Constraint
argument_list|>
name|unmodQueryElements
init|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|queryConstraint
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|selected
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|unmodSelected
init|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|selected
argument_list|)
decl_stmt|;
specifier|private
name|Integer
name|limit
decl_stmt|;
specifier|private
name|int
name|offset
decl_stmt|;
specifier|public
name|FieldQueryImpl
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|addSelectedField
parameter_list|(
name|String
name|field
parameter_list|)
block|{
if|if
condition|(
name|field
operator|!=
literal|null
condition|)
block|{
name|selected
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|addSelectedFields
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
block|{
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
block|{
name|selected
operator|.
name|addAll
argument_list|(
name|fields
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|removeSelectedField
parameter_list|(
name|String
name|field
parameter_list|)
block|{
if|if
condition|(
name|field
operator|!=
literal|null
condition|)
block|{
name|selected
operator|.
name|remove
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|removeSelectedFields
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
block|{
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
block|{
name|selected
operator|.
name|removeAll
argument_list|(
name|fields
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|getSelectedFields
parameter_list|()
block|{
return|return
name|unmodSelected
return|;
block|}
specifier|public
name|void
name|setConstraint
parameter_list|(
name|String
name|field
parameter_list|,
name|Constraint
name|constraint
parameter_list|)
block|{
if|if
condition|(
name|field
operator|!=
literal|null
operator|&&
operator|!
name|field
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|constraint
operator|==
literal|null
condition|)
block|{
name|queryConstraint
operator|.
name|remove
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|queryConstraint
operator|.
name|put
argument_list|(
name|field
argument_list|,
name|constraint
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parameter Field MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
block|}
comment|/**      * Calls {@link #setConstraint(String, Constraint)} with<code>null</code>      * as {@link Constraint}. So overwrite the setConstraint Method if needed.      * @see org.apache.stanbol.entityhub.core.query.FieldConstraint#removeConstraint(java.lang.String)      */
specifier|public
specifier|final
name|void
name|removeConstraint
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|setConstraint
argument_list|(
name|field
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.core.query.FieldQuery#isConstraint(java.lang.String)      */
specifier|public
specifier|final
name|boolean
name|isConstrained
parameter_list|(
name|String
name|field
parameter_list|)
block|{
return|return
name|queryConstraint
operator|.
name|containsKey
argument_list|(
name|field
argument_list|)
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.core.query.FieldQuery#getConstraint(java.lang.String)      */
specifier|public
specifier|final
name|Constraint
name|getConstraint
parameter_list|(
name|String
name|field
parameter_list|)
block|{
return|return
name|queryConstraint
operator|.
name|get
argument_list|(
name|field
argument_list|)
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.entityhub.core.query.FieldQuery#getConstraints()      */
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|Constraint
argument_list|>
argument_list|>
name|getConstraints
parameter_list|()
block|{
return|return
name|unmodQueryElements
operator|.
name|entrySet
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Iterator
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|Constraint
argument_list|>
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|unmodQueryElements
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
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
literal|"Query constraints:"
operator|+
name|queryConstraint
operator|+
literal|" selectedFields:"
operator|+
name|selected
return|;
block|}
annotation|@
name|Override
specifier|public
name|FieldQuery
name|clone
parameter_list|()
block|{
return|return
name|copyTo
argument_list|(
operator|new
name|FieldQueryImpl
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Uses the public API to clone the state of this instance to the instance      * provided as parameter.      * @param<C> An implementation of the FieldQuery interface      * @param copyTo An instance to copy the state of this on.      * @return The parsed instance      */
specifier|public
parameter_list|<
name|C
extends|extends
name|FieldQuery
parameter_list|>
name|C
name|copyTo
parameter_list|(
name|C
name|copyTo
parameter_list|)
block|{
name|copyTo
operator|.
name|removeAllConstraints
argument_list|()
expr_stmt|;
name|copyTo
operator|.
name|removeAllSelectedFields
argument_list|()
expr_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|Constraint
argument_list|>
name|entry
range|:
name|queryConstraint
operator|.
name|entrySet
argument_list|()
control|)
block|{
comment|//we need not to copy keys or values, because everything is immutable
name|copyTo
operator|.
name|setConstraint
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|copyTo
operator|.
name|addSelectedFields
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|copyTo
operator|.
name|setLimit
argument_list|(
name|limit
argument_list|)
expr_stmt|;
name|copyTo
operator|.
name|setOffset
argument_list|(
name|offset
argument_list|)
expr_stmt|;
return|return
name|copyTo
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeAllConstraints
parameter_list|()
block|{
name|selected
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeAllSelectedFields
parameter_list|()
block|{
name|queryConstraint
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getQueryType
parameter_list|()
block|{
return|return
name|FieldQuery
operator|.
name|TYPE
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
specifier|final
name|int
name|getOffset
parameter_list|()
block|{
return|return
name|offset
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|setLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
if|if
condition|(
name|limit
operator|!=
literal|null
operator|&&
name|limit
operator|.
name|intValue
argument_list|()
operator|<
literal|1
condition|)
block|{
name|limit
operator|=
literal|null
expr_stmt|;
block|}
name|this
operator|.
name|limit
operator|=
name|limit
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|setOffset
parameter_list|(
name|int
name|offset
parameter_list|)
block|{
if|if
condition|(
name|offset
operator|<
literal|0
condition|)
block|{
name|offset
operator|=
literal|0
expr_stmt|;
block|}
name|this
operator|.
name|offset
operator|=
name|offset
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|queryConstraint
operator|.
name|hashCode
argument_list|()
operator|+
name|selected
operator|.
name|hashCode
argument_list|()
operator|+
name|offset
operator|+
operator|(
name|limit
operator|!=
literal|null
condition|?
name|limit
else|:
literal|0
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|obj
operator|instanceof
name|FieldQuery
operator|&&
operator|(
operator|(
name|FieldQuery
operator|)
name|obj
operator|)
operator|.
name|getConstraints
argument_list|()
operator|.
name|equals
argument_list|(
name|getConstraints
argument_list|()
argument_list|)
operator|&&
operator|(
operator|(
name|FieldQuery
operator|)
name|obj
operator|)
operator|.
name|getSelectedFields
argument_list|()
operator|.
name|equals
argument_list|(
name|getSelectedFields
argument_list|()
argument_list|)
operator|&&
operator|(
operator|(
name|FieldQuery
operator|)
name|obj
operator|)
operator|.
name|getOffset
argument_list|()
operator|==
name|getOffset
argument_list|()
condition|)
block|{
if|if
condition|(
name|limit
operator|!=
literal|null
condition|)
block|{
return|return
name|limit
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|FieldQuery
operator|)
name|obj
operator|)
operator|.
name|getLimit
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|(
operator|(
name|FieldQuery
operator|)
name|obj
operator|)
operator|.
name|getLimit
argument_list|()
operator|==
literal|null
return|;
block|}
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

