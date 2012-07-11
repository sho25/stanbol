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
name|impl
package|;
end_package

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
name|Dictionary
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
name|java
operator|.
name|util
operator|.
name|TreeMap
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
name|CopyOnWriteArrayList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Deactivate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferenceCardinality
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferencePolicy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ReferenceStrategy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|query
operator|.
name|QueryResultListImpl
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
name|Representation
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
name|Entity
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
name|QueryResultList
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
name|site
operator|.
name|Site
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
name|site
operator|.
name|SiteException
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
name|site
operator|.
name|SiteManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{ }
argument_list|)
specifier|public
class|class
name|SiteManagerImpl
implements|implements
name|SiteManager
block|{
specifier|private
specifier|final
name|Logger
name|log
decl_stmt|;
comment|//    private ComponentContext context;
specifier|public
name|SiteManagerImpl
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|log
operator|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SiteManagerImpl
operator|.
name|class
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|" create instance of "
operator|+
name|SiteManagerImpl
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_MULTIPLE
argument_list|,
name|referenceInterface
operator|=
name|Site
operator|.
name|class
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|bind
operator|=
literal|"bindReferencedSites"
argument_list|,
name|unbind
operator|=
literal|"unbindReferencedSites"
argument_list|)
name|List
argument_list|<
name|Site
argument_list|>
name|referencedSites
init|=
operator|new
name|CopyOnWriteArrayList
argument_list|<
name|Site
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * Map holding the mapping of the site ID to the referencedSite Object      * TODO: in principle it could be possible that two instances of      * {@link Site} could be configured to use the same ID      */
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Site
argument_list|>
name|idMap
init|=
name|Collections
operator|.
name|synchronizedMap
argument_list|(
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Site
argument_list|>
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * Map holding the mappings between entityPrefixes and referenced sites      */
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Site
argument_list|>
argument_list|>
name|prefixMap
init|=
name|Collections
operator|.
name|synchronizedMap
argument_list|(
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Site
argument_list|>
argument_list|>
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * This List is used for binary searches within the prefixes to find the      * {@link Site} to search for a {@link #getEntity(String)}      * request.<b>      * NOTE: Every access to this list MUST BE synchronised to {@link #prefixMap}      * TODO: I am quite sure, that there is some ioUtils class that provides      * both a Map and an sorted List over the keys!      */
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|prefixList
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|Site
argument_list|>
name|noPrefixSites
init|=
name|Collections
operator|.
name|synchronizedSet
argument_list|(
operator|new
name|HashSet
argument_list|<
name|Site
argument_list|>
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Activate ReferenceManager"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Deactivate ReferenceManager"
argument_list|)
expr_stmt|;
synchronized|synchronized
init|(
name|prefixMap
init|)
block|{
name|this
operator|.
name|prefixList
operator|.
name|clear
argument_list|()
expr_stmt|;
name|this
operator|.
name|prefixMap
operator|.
name|clear
argument_list|()
expr_stmt|;
name|this
operator|.
name|noPrefixSites
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
name|this
operator|.
name|idMap
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|bindReferencedSites
parameter_list|(
name|Site
name|referencedSite
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ... binding ReferencedSite {}"
argument_list|,
name|referencedSite
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|referencedSites
operator|.
name|add
argument_list|(
name|referencedSite
argument_list|)
expr_stmt|;
name|idMap
operator|.
name|put
argument_list|(
name|referencedSite
operator|.
name|getId
argument_list|()
argument_list|,
name|referencedSite
argument_list|)
expr_stmt|;
name|addEntityPrefixes
argument_list|(
name|referencedSite
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|unbindReferencedSites
parameter_list|(
name|Site
name|referencedSite
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ... unbinding ReferencedSite {}"
argument_list|,
name|referencedSite
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|referencedSites
operator|.
name|remove
argument_list|(
name|referencedSite
argument_list|)
expr_stmt|;
name|idMap
operator|.
name|remove
argument_list|(
name|referencedSite
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|removeEntityPrefixes
argument_list|(
name|referencedSite
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds the prefixes of the parsed Site to the Map holding the according mappings      * @param referencedSite      */
specifier|private
name|void
name|addEntityPrefixes
parameter_list|(
name|Site
name|referencedSite
parameter_list|)
block|{
name|String
index|[]
name|prefixArray
init|=
name|referencedSite
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getEntityPrefixes
argument_list|()
decl_stmt|;
if|if
condition|(
name|prefixArray
operator|==
literal|null
operator|||
name|prefixArray
operator|.
name|length
operator|<
literal|1
condition|)
block|{
synchronized|synchronized
init|(
name|prefixMap
init|)
block|{
name|noPrefixSites
operator|.
name|add
argument_list|(
name|referencedSite
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//use a set to iterate to remove possible duplicates
for|for
control|(
name|String
name|prefix
range|:
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|prefixArray
argument_list|)
argument_list|)
control|)
block|{
synchronized|synchronized
init|(
name|prefixMap
init|)
block|{
name|Collection
argument_list|<
name|Site
argument_list|>
name|sites
init|=
name|prefixMap
operator|.
name|get
argument_list|(
name|prefix
argument_list|)
decl_stmt|;
if|if
condition|(
name|sites
operator|==
literal|null
condition|)
block|{
name|sites
operator|=
operator|new
name|CopyOnWriteArrayList
argument_list|<
name|Site
argument_list|>
argument_list|()
expr_stmt|;
name|prefixMap
operator|.
name|put
argument_list|(
name|prefix
argument_list|,
name|sites
argument_list|)
expr_stmt|;
comment|//this also means that the prefix is not part of the prefixList
name|int
name|pos
init|=
name|Collections
operator|.
name|binarySearch
argument_list|(
name|prefixList
argument_list|,
name|prefix
argument_list|)
decl_stmt|;
if|if
condition|(
name|pos
operator|<
literal|0
condition|)
block|{
name|prefixList
operator|.
name|add
argument_list|(
name|Math
operator|.
name|abs
argument_list|(
name|pos
argument_list|)
operator|-
literal|1
argument_list|,
name|prefix
argument_list|)
expr_stmt|;
block|}
comment|//prefixList.add(Collections.binarySearch(prefixList, prefix)+1,prefix);
block|}
comment|//TODO: Sort the referencedSites based on the ServiceRanking!
name|sites
operator|.
name|add
argument_list|(
name|referencedSite
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**      * Removes the prefixes of the parsed Site to the Map holding the according mappings      * @param referencedSite      */
specifier|private
name|void
name|removeEntityPrefixes
parameter_list|(
name|Site
name|referencedSite
parameter_list|)
block|{
name|String
index|[]
name|prefixes
init|=
name|referencedSite
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getEntityPrefixes
argument_list|()
decl_stmt|;
if|if
condition|(
name|prefixes
operator|==
literal|null
operator|||
name|prefixes
operator|.
name|length
operator|<
literal|1
condition|)
block|{
synchronized|synchronized
init|(
name|prefixMap
init|)
block|{
name|noPrefixSites
operator|.
name|remove
argument_list|(
name|referencedSite
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|String
name|prefix
range|:
name|prefixes
control|)
block|{
synchronized|synchronized
init|(
name|prefixMap
init|)
block|{
name|Collection
argument_list|<
name|Site
argument_list|>
name|sites
init|=
name|prefixMap
operator|.
name|get
argument_list|(
name|prefix
argument_list|)
decl_stmt|;
if|if
condition|(
name|sites
operator|!=
literal|null
condition|)
block|{
name|sites
operator|.
name|remove
argument_list|(
name|referencedSite
argument_list|)
expr_stmt|;
if|if
condition|(
name|sites
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//remove key from the Map
name|prefixMap
operator|.
name|remove
argument_list|(
name|prefix
argument_list|)
expr_stmt|;
comment|//remove also the prefix from the List
name|prefixList
operator|.
name|remove
argument_list|(
name|prefix
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|Site
name|getSite
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
name|idMap
operator|.
name|get
argument_list|(
name|id
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getSiteIds
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|idMap
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isReferred
parameter_list|(
name|String
name|id
parameter_list|)
block|{
return|return
name|idMap
operator|.
name|containsKey
argument_list|(
name|id
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Site
argument_list|>
name|getSitesByEntityPrefix
parameter_list|(
name|String
name|entityUri
parameter_list|)
block|{
if|if
condition|(
name|entityUri
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"NULL value parsed for Parameter entityUri -> return emptyList!"
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
synchronized|synchronized
init|(
name|prefixMap
init|)
block|{
comment|//sync is done via the Map (for both the list and the map)!
name|int
name|pos
init|=
name|Collections
operator|.
name|binarySearch
argument_list|(
name|prefixList
argument_list|,
name|entityUri
argument_list|)
decl_stmt|;
specifier|final
name|int
name|prefixPos
decl_stmt|;
if|if
condition|(
name|pos
operator|<
literal|0
condition|)
block|{
comment|/**                  * Example:                  * ["a","b"]<- "bc"                  * binary search returns -3 (because insert point would be +2)                  * to find the prefix we need the insert point-1 -> pos 1                  *                  * Example2:                  * []<- "bc"                  * binary search returns -1 (because insert point would be 0)                  * to find the prefix we need the insert point-1 -> pos -1                  * therefore we need to check for negative prefixPos and return                  * an empty list!                  */
name|prefixPos
operator|=
name|Math
operator|.
name|abs
argument_list|(
name|pos
argument_list|)
operator|-
literal|2
expr_stmt|;
block|}
else|else
block|{
name|prefixPos
operator|=
name|pos
expr_stmt|;
comment|//entityUri found in list
block|}
if|if
condition|(
name|prefixPos
operator|<
literal|0
condition|)
block|{
return|return
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|noPrefixSites
argument_list|)
return|;
block|}
else|else
block|{
name|String
name|prefix
init|=
name|prefixList
operator|.
name|get
argument_list|(
name|prefixPos
argument_list|)
decl_stmt|;
if|if
condition|(
name|entityUri
operator|.
name|startsWith
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Found prefix {} for Entity {}"
argument_list|,
name|prefix
argument_list|,
name|entityUri
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|Site
argument_list|>
name|prefixSites
init|=
name|prefixMap
operator|.
name|get
argument_list|(
name|prefix
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|Site
argument_list|>
name|sites
init|=
operator|new
name|ArrayList
argument_list|<
name|Site
argument_list|>
argument_list|(
name|noPrefixSites
operator|.
name|size
argument_list|()
operator|+
name|prefixSites
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|sites
operator|.
name|addAll
argument_list|(
name|prefixSites
argument_list|)
expr_stmt|;
name|sites
operator|.
name|addAll
argument_list|(
name|noPrefixSites
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|sites
argument_list|)
return|;
block|}
comment|//else the parsed entityPrefix does not start with the found prefix
comment|// this may only happen, when the prefixPos == prefixList.size()
block|}
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"No registered prefix found for entity {} "
operator|+
literal|"-> return sites that accept all entities"
argument_list|,
name|entityUri
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|noPrefixSites
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findIds
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"findIds for query{}"
argument_list|,
name|query
argument_list|)
expr_stmt|;
comment|// We need to search all referenced Sites
name|Set
argument_list|<
name|String
argument_list|>
name|entityIds
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|//TODO: The QueryResultList expects that the query as executed is added
comment|//to the response. However when executing queries on multiple site they
comment|//might support a different set of features and therefore execute
comment|//different variants. For now I return simple the query as executed by
comment|//the first Site that contributes results
name|FieldQuery
name|processedQuery
init|=
literal|null
decl_stmt|;
name|FieldQuery
name|queryWithResults
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Site
name|site
range|:
name|referencedSites
control|)
block|{
if|if
condition|(
name|site
operator|.
name|supportsSearch
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> query site {}"
argument_list|,
name|site
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|results
init|=
name|site
operator|.
name|findReferences
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|processedQuery
operator|==
literal|null
condition|)
block|{
name|processedQuery
operator|=
name|results
operator|.
name|getQuery
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|results
operator|.
name|isEmpty
argument_list|()
operator|&&
name|queryWithResults
operator|==
literal|null
condition|)
block|{
name|processedQuery
operator|=
name|results
operator|.
name|getQuery
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|String
name|entityId
range|:
name|results
control|)
block|{
name|entityIds
operator|.
name|add
argument_list|(
name|entityId
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|SiteException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to access Site "
operator|+
name|site
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" (id = "
operator|+
name|site
operator|.
name|getId
argument_list|()
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> Site {} does not support queries"
argument_list|,
name|site
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|QueryResultListImpl
argument_list|<
name|String
argument_list|>
argument_list|(
name|queryWithResults
operator|!=
literal|null
condition|?
name|queryWithResults
else|:
comment|//use the query with results
name|processedQuery
operator|!=
literal|null
condition|?
name|processedQuery
else|:
comment|//if not a processed
name|query
argument_list|,
comment|//else the parsed one
name|entityIds
operator|.
name|iterator
argument_list|()
argument_list|,
name|String
operator|.
name|class
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|find
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"find with query{}"
argument_list|,
name|query
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Representation
argument_list|>
name|representations
init|=
operator|new
name|HashSet
argument_list|<
name|Representation
argument_list|>
argument_list|()
decl_stmt|;
comment|//TODO: The QueryResultList expects that the query as executed is added
comment|//to the response. However when executing queries on multiple site they
comment|//might support a different set of features and therefore execute
comment|//different variants. For now I return simple the query as executed by
comment|//the first Site that contributes results
name|FieldQuery
name|processedQuery
init|=
literal|null
decl_stmt|;
name|FieldQuery
name|queryWithResults
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Site
name|site
range|:
name|referencedSites
control|)
block|{
if|if
condition|(
name|site
operator|.
name|supportsSearch
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> query site {}"
argument_list|,
name|site
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|results
init|=
name|site
operator|.
name|find
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|processedQuery
operator|==
literal|null
condition|)
block|{
name|processedQuery
operator|=
name|results
operator|.
name|getQuery
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|results
operator|.
name|isEmpty
argument_list|()
operator|&&
name|queryWithResults
operator|==
literal|null
condition|)
block|{
name|processedQuery
operator|=
name|results
operator|.
name|getQuery
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Representation
name|rep
range|:
name|results
control|)
block|{
if|if
condition|(
operator|!
name|representations
operator|.
name|contains
argument_list|(
name|rep
argument_list|)
condition|)
block|{
comment|//do not override
name|representations
operator|.
name|add
argument_list|(
name|rep
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Entity {} found on more than one Referenced Site"
operator|+
literal|" -> Representation of Site {} is ignored"
argument_list|,
name|rep
operator|.
name|getId
argument_list|()
argument_list|,
name|site
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SiteException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to access Site "
operator|+
name|site
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" (id = "
operator|+
name|site
operator|.
name|getId
argument_list|()
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> Site {} does not support queries"
argument_list|,
name|site
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|QueryResultListImpl
argument_list|<
name|Representation
argument_list|>
argument_list|(
name|queryWithResults
operator|!=
literal|null
condition|?
name|queryWithResults
else|:
comment|//use the query with results
name|processedQuery
operator|!=
literal|null
condition|?
name|processedQuery
else|:
comment|//if not a processed
name|query
argument_list|,
comment|//else the parsed one
name|representations
argument_list|,
name|Representation
operator|.
name|class
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|Entity
argument_list|>
name|findEntities
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"findEntities for query{}"
argument_list|,
name|query
argument_list|)
expr_stmt|;
comment|//TODO: The QueryResultList expects that the query as executed is added
comment|//to the response. However when executing queries on multiple site they
comment|//might support a different set of features and therefore execute
comment|//different variants. For now I return simple the query as executed by
comment|//the first Site that contributes results
name|FieldQuery
name|processedQuery
init|=
literal|null
decl_stmt|;
name|FieldQuery
name|queryWithResults
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|Entity
argument_list|>
name|entities
init|=
operator|new
name|HashSet
argument_list|<
name|Entity
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Site
name|site
range|:
name|referencedSites
control|)
block|{
if|if
condition|(
name|site
operator|.
name|supportsSearch
argument_list|()
condition|)
block|{
comment|//do not search on sites that do not support it
name|log
operator|.
name|debug
argument_list|(
literal|"> query site {}"
argument_list|,
name|site
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|QueryResultList
argument_list|<
name|Entity
argument_list|>
name|results
init|=
name|site
operator|.
name|findEntities
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|processedQuery
operator|==
literal|null
condition|)
block|{
name|processedQuery
operator|=
name|results
operator|.
name|getQuery
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|results
operator|.
name|isEmpty
argument_list|()
operator|&&
name|queryWithResults
operator|==
literal|null
condition|)
block|{
name|processedQuery
operator|=
name|results
operator|.
name|getQuery
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Entity
name|rep
range|:
name|results
control|)
block|{
if|if
condition|(
operator|!
name|entities
operator|.
name|contains
argument_list|(
name|rep
argument_list|)
condition|)
block|{
comment|//do not override
name|entities
operator|.
name|add
argument_list|(
name|rep
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//TODO: find a solution for this problem
comment|//      e.g. allow to add the site for entities
name|log
operator|.
name|info
argument_list|(
literal|"Entity {} found on more than one Referenced Site"
operator|+
literal|" -> Representation of Site {} is ignored"
argument_list|,
name|rep
operator|.
name|getId
argument_list|()
argument_list|,
name|site
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SiteException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to access Site "
operator|+
name|site
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" (id = "
operator|+
name|site
operator|.
name|getId
argument_list|()
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> Site {} does not support queries"
argument_list|,
name|site
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|QueryResultListImpl
argument_list|<
name|Entity
argument_list|>
argument_list|(
name|queryWithResults
operator|!=
literal|null
condition|?
name|queryWithResults
else|:
comment|//use the query with results
name|processedQuery
operator|!=
literal|null
condition|?
name|processedQuery
else|:
comment|//if not a processed
name|query
argument_list|,
comment|//else the parsed one
name|entities
argument_list|,
name|Entity
operator|.
name|class
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|InputStream
name|getContent
parameter_list|(
name|String
name|entityId
parameter_list|,
name|String
name|contentType
parameter_list|)
block|{
name|Collection
argument_list|<
name|Site
argument_list|>
name|sites
init|=
name|getSitesByEntityPrefix
argument_list|(
name|entityId
argument_list|)
decl_stmt|;
if|if
condition|(
name|sites
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"No Referenced Site registered for Entity {}"
argument_list|,
name|entityId
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Registered Prefixes {}"
argument_list|,
name|prefixList
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
for|for
control|(
name|Site
name|site
range|:
name|sites
control|)
block|{
name|InputStream
name|content
decl_stmt|;
try|try
block|{
name|content
operator|=
name|site
operator|.
name|getContent
argument_list|(
name|entityId
argument_list|,
name|contentType
argument_list|)
expr_stmt|;
if|if
condition|(
name|content
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Return Content of type {} for Entity {} from referenced site {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|contentType
block|,
name|entityId
block|,
name|site
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getName
argument_list|()
block|}
argument_list|)
expr_stmt|;
return|return
name|content
return|;
block|}
block|}
catch|catch
parameter_list|(
name|SiteException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to access Site "
operator|+
name|site
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" (id = "
operator|+
name|site
operator|.
name|getId
argument_list|()
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Entity {} not found on any of the following Sites {}"
argument_list|,
name|entityId
argument_list|,
name|sites
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|getEntity
parameter_list|(
name|String
name|entityId
parameter_list|)
block|{
name|Collection
argument_list|<
name|Site
argument_list|>
name|sites
init|=
name|getSitesByEntityPrefix
argument_list|(
name|entityId
argument_list|)
decl_stmt|;
if|if
condition|(
name|sites
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"No Referenced Site registered for Entity {}"
argument_list|,
name|entityId
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Registered Prefixes {}"
argument_list|,
name|prefixList
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
for|for
control|(
name|Site
name|site
range|:
name|sites
control|)
block|{
name|Entity
name|entity
decl_stmt|;
try|try
block|{
name|entity
operator|=
name|site
operator|.
name|getEntity
argument_list|(
name|entityId
argument_list|)
expr_stmt|;
if|if
condition|(
name|entity
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Return Representation of Site {} for Entity {}"
argument_list|,
name|site
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|entityId
argument_list|)
expr_stmt|;
return|return
name|entity
return|;
block|}
block|}
catch|catch
parameter_list|(
name|SiteException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to access Site "
operator|+
name|site
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" (id = "
operator|+
name|site
operator|.
name|getId
argument_list|()
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Entity {} not found on any of the following Sites {}"
argument_list|,
name|entityId
argument_list|,
name|sites
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit
