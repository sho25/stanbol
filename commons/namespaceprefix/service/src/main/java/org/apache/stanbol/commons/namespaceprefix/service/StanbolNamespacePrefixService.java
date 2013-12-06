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
name|commons
operator|.
name|namespaceprefix
operator|.
name|service
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
import|;
end_import

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
name|GregorianCalendar
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
name|ServiceLoader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|SortedMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TimeZone
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
name|locks
operator|.
name|ReadWriteLock
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
name|locks
operator|.
name|ReentrantReadWriteLock
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
name|io
operator|.
name|IOUtils
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
name|io
operator|.
name|LineIterator
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
name|Property
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
name|commons
operator|.
name|namespaceprefix
operator|.
name|NamespaceMappingUtils
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
name|namespaceprefix
operator|.
name|NamespacePrefixProvider
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
name|namespaceprefix
operator|.
name|NamespacePrefixService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Bundle
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
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
name|osgi
operator|.
name|util
operator|.
name|tracker
operator|.
name|ServiceTracker
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|util
operator|.
name|tracker
operator|.
name|ServiceTrackerCustomizer
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
argument_list|(
name|value
operator|=
block|{
name|NamespacePrefixProvider
operator|.
name|class
block|,
name|NamespacePrefixService
operator|.
name|class
block|}
argument_list|)
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|StanbolNamespacePrefixService
implements|implements
name|NamespacePrefixService
implements|,
name|NamespacePrefixProvider
block|{
specifier|protected
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|StanbolNamespacePrefixService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PREFIX_MAPPINGS
init|=
literal|"namespaceprefix.mappings"
decl_stmt|;
specifier|private
specifier|static
name|NamespacePrefixService
name|INSTANCE
init|=
literal|null
decl_stmt|;
specifier|private
name|File
name|mappingsFile
decl_stmt|;
comment|// OSGI service references
specifier|private
name|ServiceTracker
name|providersTracker
decl_stmt|;
specifier|protected
name|ServiceReference
index|[]
name|__sortedProviderRef
init|=
literal|null
decl_stmt|;
comment|//non-OSGI service references
name|ServiceLoader
argument_list|<
name|NamespacePrefixProvider
argument_list|>
name|loader
decl_stmt|;
specifier|private
name|ReadWriteLock
name|mappingsLock
init|=
operator|new
name|ReentrantReadWriteLock
argument_list|()
decl_stmt|;
specifier|private
name|SortedMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|prefixMap
init|=
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|SortedMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|namespaceMap
init|=
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|BundleContext
name|bundleContext
decl_stmt|;
comment|/**      * OSGI constructor<b> DO NOT USE</b> outside of an OSGI environment as this      * will not initialise the {@link NamespacePrefixProvider} using the      * {@link ServiceLoader} utility!      */
specifier|public
name|StanbolNamespacePrefixService
parameter_list|()
block|{}
comment|/**      * Constructs an Stanbol NamespacePrefixService and initialises other      * {@link NamespacePrefixProvider} implementations using the      * Java {@link ServiceLoader} utility.      * @param mappingFile the mapping file used to manage local mappings. If      *<code>null</code> no president local mappings are supported.      * @throws IOException      */
specifier|public
name|StanbolNamespacePrefixService
parameter_list|(
name|File
name|mappingFile
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|mappingsFile
operator|=
name|mappingFile
expr_stmt|;
if|if
condition|(
name|mappingsFile
operator|!=
literal|null
operator|&&
name|mappingsFile
operator|.
name|isFile
argument_list|()
condition|)
block|{
name|readPrefixMappings
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|mappingsFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//else no mappings yet ... nothing todo
name|loader
operator|=
name|ServiceLoader
operator|.
name|load
argument_list|(
name|NamespacePrefixProvider
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
comment|/**      * Imports tab separated prefix mappings from the parsed Stream      * @param in      * @throws IOException      */
specifier|public
name|void
name|importPrefixMappings
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|readPrefixMappings
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|NamespacePrefixService
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|INSTANCE
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|INSTANCE
operator|=
operator|new
name|StanbolNamespacePrefixService
argument_list|(
operator|new
name|File
argument_list|(
name|PREFIX_MAPPINGS
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to read "
operator|+
name|PREFIX_MAPPINGS
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
return|return
name|INSTANCE
return|;
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|FileNotFoundException
throws|,
name|IOException
block|{
name|bundleContext
operator|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
expr_stmt|;
comment|//(1) read the mappings
name|mappingsFile
operator|=
name|bundleContext
operator|.
name|getDataFile
argument_list|(
name|PREFIX_MAPPINGS
argument_list|)
expr_stmt|;
if|if
condition|(
name|mappingsFile
operator|.
name|isFile
argument_list|()
condition|)
block|{
name|readPrefixMappings
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|mappingsFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//else no mappings yet ... nothing todo
block|}
comment|/**      *       */
specifier|private
name|void
name|openTracker
parameter_list|()
block|{
name|providersTracker
operator|=
operator|new
name|ServiceTracker
argument_list|(
name|bundleContext
argument_list|,
name|NamespacePrefixProvider
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
operator|new
name|ServiceTrackerCustomizer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|removedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
name|bundleContext
operator|.
name|ungetService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
name|__sortedProviderRef
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|modifiedService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|,
name|Object
name|service
parameter_list|)
block|{
name|__sortedProviderRef
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|addingService
parameter_list|(
name|ServiceReference
name|reference
parameter_list|)
block|{
name|Object
name|service
init|=
name|bundleContext
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|StanbolNamespacePrefixService
operator|.
name|this
operator|.
name|equals
argument_list|(
name|service
argument_list|)
condition|)
block|{
comment|//we need not to track this instance
name|bundleContext
operator|.
name|ungetService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|__sortedProviderRef
operator|=
literal|null
expr_stmt|;
return|return
name|service
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|providersTracker
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
comment|/**      * Expected to be called only during activation      * @param in      * @throws IOException      */
specifier|private
name|void
name|readPrefixMappings
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|LineIterator
name|it
init|=
name|IOUtils
operator|.
name|lineIterator
argument_list|(
name|in
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|mapping
init|=
name|it
operator|.
name|nextLine
argument_list|()
decl_stmt|;
if|if
condition|(
name|mapping
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|'#'
condition|)
block|{
name|int
name|sep
init|=
name|mapping
operator|.
name|indexOf
argument_list|(
literal|'\t'
argument_list|)
decl_stmt|;
if|if
condition|(
name|sep
operator|<
literal|0
operator|||
name|mapping
operator|.
name|length
argument_list|()
operator|<=
name|sep
operator|+
literal|1
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Illegal prefix mapping '{}'"
argument_list|,
name|mapping
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|old
init|=
name|addMapping
argument_list|(
name|mapping
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|sep
argument_list|)
argument_list|,
name|mapping
operator|.
name|substring
argument_list|(
name|sep
operator|+
literal|1
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|old
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Duplicate mention of prefix {}. Override mapping from {} to {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|mapping
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|sep
argument_list|)
block|,
name|old
block|,
name|mapping
operator|.
name|substring
argument_list|(
name|sep
operator|+
literal|1
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|//comment
name|log
operator|.
name|debug
argument_list|(
name|mapping
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|writePrefixMappings
parameter_list|(
name|OutputStream
name|os
parameter_list|)
throws|throws
name|IOException
block|{
name|mappingsLock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|lines
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|prefixMap
operator|.
name|size
argument_list|()
operator|+
literal|1
argument_list|)
decl_stmt|;
name|lines
operator|.
name|add
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"# %d mappings written at %2$tY-%2$tm-%2$teT%2$TH:%2$TM:%2$TS"
argument_list|,
name|prefixMap
operator|.
name|size
argument_list|()
argument_list|,
operator|new
name|GregorianCalendar
argument_list|(
name|TimeZone
operator|.
name|getTimeZone
argument_list|(
literal|"GMT"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|mapping
range|:
name|prefixMap
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|lines
operator|.
name|add
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s/t%s"
argument_list|,
name|mapping
operator|.
name|getKey
argument_list|()
argument_list|,
name|mapping
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|IOUtils
operator|.
name|writeLines
argument_list|(
name|lines
argument_list|,
literal|null
argument_list|,
name|os
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|mappingsLock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Internally used to add an mapping      * @param prefix the prefix      * @param namespace the namespace      * @param store if the added mapping should be stored to the {@link #mappingsFile}      * @return the previouse mapping or<code>null</code> if none.      */
specifier|private
name|String
name|addMapping
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|namespace
parameter_list|,
name|boolean
name|store
parameter_list|)
throws|throws
name|IOException
block|{
name|mappingsLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|String
name|old
init|=
name|prefixMap
operator|.
name|put
argument_list|(
name|prefix
argument_list|,
name|namespace
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|namespace
operator|.
name|equals
argument_list|(
name|old
argument_list|)
condition|)
block|{
comment|//if the mapping changed
name|boolean
name|failed
init|=
literal|false
decl_stmt|;
comment|//used for rollback in case of an exception
try|try
block|{
comment|//(1) persist the mapping
if|if
condition|(
name|store
condition|)
block|{
if|if
condition|(
name|mappingsFile
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|mappingsFile
operator|.
name|isFile
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|mappingsFile
operator|.
name|createNewFile
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to create mapping file "
operator|+
name|mappingsFile
argument_list|)
throw|;
block|}
block|}
name|writePrefixMappings
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|mappingsFile
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//else do not persist mappings
block|}
comment|//(2) update the inverse mappings (ensure read only lists!)
name|List
argument_list|<
name|String
argument_list|>
name|prefixes
init|=
name|namespaceMap
operator|.
name|get
argument_list|(
name|namespace
argument_list|)
decl_stmt|;
if|if
condition|(
name|prefixes
operator|==
literal|null
condition|)
block|{
name|namespaceMap
operator|.
name|put
argument_list|(
name|namespace
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|prefix
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
index|[]
name|ps
init|=
operator|new
name|String
index|[
name|prefixes
operator|.
name|size
argument_list|()
operator|+
literal|1
index|]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
init|;
name|i
operator|<
name|prefixes
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|ps
index|[
name|i
index|]
operator|=
name|prefixes
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
name|ps
index|[
name|i
index|]
operator|=
name|prefix
expr_stmt|;
name|namespaceMap
operator|.
name|put
argument_list|(
name|namespace
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|ps
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|failed
operator|=
literal|true
expr_stmt|;
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|failed
operator|=
literal|true
expr_stmt|;
throw|throw
name|e
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|failed
condition|)
block|{
comment|//rollback
if|if
condition|(
name|old
operator|==
literal|null
condition|)
block|{
name|prefixMap
operator|.
name|remove
argument_list|(
name|prefix
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|prefixMap
operator|.
name|put
argument_list|(
name|prefix
argument_list|,
name|old
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|old
return|;
block|}
finally|finally
block|{
name|mappingsLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
name|ServiceReference
index|[]
name|getSortedProviderReferences
parameter_list|()
block|{
name|ServiceReference
index|[]
name|refs
init|=
name|__sortedProviderRef
decl_stmt|;
if|if
condition|(
name|bundleContext
operator|!=
literal|null
condition|)
block|{
comment|//OSGI variant
if|if
condition|(
name|providersTracker
operator|==
literal|null
condition|)
block|{
comment|//lazy initialisation of the service tracker
synchronized|synchronized
init|(
name|this
init|)
block|{
if|if
condition|(
name|providersTracker
operator|==
literal|null
operator|&&
name|bundleContext
operator|!=
literal|null
condition|)
block|{
name|openTracker
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|//the check for the size ensures that registered/unregistered services
comment|//are not overlooked when that happens during this method is executed
comment|//by an other thread.
if|if
condition|(
name|refs
operator|==
literal|null
operator|||
name|refs
operator|.
name|length
operator|!=
name|providersTracker
operator|.
name|size
argument_list|()
condition|)
block|{
name|ServiceReference
index|[]
name|r
init|=
name|providersTracker
operator|.
name|getServiceReferences
argument_list|()
decl_stmt|;
name|refs
operator|=
name|Arrays
operator|.
name|copyOf
argument_list|(
name|r
argument_list|,
name|r
operator|.
name|length
argument_list|)
expr_stmt|;
comment|//copy
name|Arrays
operator|.
name|sort
argument_list|(
name|refs
argument_list|)
expr_stmt|;
name|this
operator|.
name|__sortedProviderRef
operator|=
name|refs
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|refs
operator|==
literal|null
condition|)
block|{
comment|//non OSGI variant
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|refList
init|=
operator|new
name|ArrayList
argument_list|<
name|ServiceReference
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|NamespacePrefixProvider
argument_list|>
name|it
init|=
name|loader
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|refList
operator|.
name|add
argument_list|(
operator|new
name|NonOsgiServiceRef
argument_list|<
name|NamespacePrefixProvider
argument_list|>
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|refs
operator|=
name|refList
operator|.
name|toArray
argument_list|(
operator|new
name|ServiceReference
index|[
name|refList
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|this
operator|.
name|__sortedProviderRef
operator|=
name|refs
expr_stmt|;
block|}
return|return
name|refs
return|;
block|}
specifier|protected
name|NamespacePrefixProvider
name|getService
parameter_list|(
name|ServiceReference
name|ref
parameter_list|)
block|{
if|if
condition|(
name|ref
operator|instanceof
name|NonOsgiServiceRef
argument_list|<
name|?
argument_list|>
condition|)
block|{
return|return
operator|(
operator|(
name|NonOsgiServiceRef
argument_list|<
name|NamespacePrefixProvider
argument_list|>
operator|)
name|ref
operator|)
operator|.
name|getService
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|providersTracker
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|NamespacePrefixProvider
operator|)
name|providersTracker
operator|.
name|getService
argument_list|(
name|ref
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
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|bundleContext
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|providersTracker
operator|!=
literal|null
condition|)
block|{
name|providersTracker
operator|.
name|close
argument_list|()
expr_stmt|;
name|providersTracker
operator|=
literal|null
expr_stmt|;
block|}
name|mappingsFile
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getNamespace
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
name|String
name|namespace
decl_stmt|;
name|mappingsLock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|namespace
operator|=
name|prefixMap
operator|.
name|get
argument_list|(
name|prefix
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|mappingsLock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|namespace
operator|==
literal|null
condition|)
block|{
name|ServiceReference
index|[]
name|refs
init|=
name|getSortedProviderReferences
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|namespace
operator|==
literal|null
operator|&&
name|i
operator|<
name|refs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|NamespacePrefixProvider
name|provider
init|=
name|getService
argument_list|(
name|refs
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|provider
operator|!=
literal|null
condition|)
block|{
name|namespace
operator|=
name|provider
operator|.
name|getNamespace
argument_list|(
name|prefix
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|namespace
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPrefix
parameter_list|(
name|String
name|namespace
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|prefixes
init|=
name|getPrefixes
argument_list|(
name|namespace
argument_list|)
decl_stmt|;
return|return
name|prefixes
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|prefixes
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getPrefixes
parameter_list|(
name|String
name|namespace
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|prefixes
decl_stmt|;
name|mappingsLock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|prefixes
operator|=
name|namespaceMap
operator|.
name|get
argument_list|(
name|namespace
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|mappingsLock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|prefixes
operator|==
literal|null
condition|)
block|{
name|ServiceReference
index|[]
name|refs
init|=
name|getSortedProviderReferences
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|prefixes
operator|==
literal|null
operator|&&
name|i
operator|<
name|refs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|NamespacePrefixProvider
name|provider
init|=
name|getService
argument_list|(
name|refs
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|provider
operator|!=
literal|null
condition|)
block|{
name|prefixes
operator|=
name|provider
operator|.
name|getPrefixes
argument_list|(
name|namespace
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|prefixes
operator|==
literal|null
condition|?
name|Collections
operator|.
name|EMPTY_LIST
else|:
name|prefixes
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|setPrefix
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|namespace
parameter_list|)
block|{
name|boolean
name|validPrefix
init|=
name|NamespaceMappingUtils
operator|.
name|checkPrefix
argument_list|(
name|prefix
argument_list|)
decl_stmt|;
name|boolean
name|validNamespace
init|=
name|NamespaceMappingUtils
operator|.
name|checkNamespace
argument_list|(
name|namespace
argument_list|)
decl_stmt|;
if|if
condition|(
name|validPrefix
operator|&&
name|validNamespace
condition|)
block|{
try|try
block|{
return|return
name|addMapping
argument_list|(
name|prefix
argument_list|,
name|namespace
argument_list|,
literal|true
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to add Mapping because of an "
operator|+
name|e
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"while setting the parsed mapping"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"The parsed Mapping is not Valid: "
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|validPrefix
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The parsed prefix '%s' MUST only contain "
operator|+
literal|"alpha numeric chars including '_' and '-'"
argument_list|,
name|prefix
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|validNamespace
condition|)
block|{
if|if
condition|(
operator|!
name|validPrefix
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"| "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The parsed namespace '%s' MUST end with"
operator|+
literal|"'/' or '#' in case of an URI or ':' in case of an URN"
argument_list|,
name|namespace
argument_list|)
argument_list|)
expr_stmt|;
block|}
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getFullName
parameter_list|(
name|String
name|shortNameOrUri
parameter_list|)
block|{
name|String
name|prefix
init|=
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
name|shortNameOrUri
argument_list|)
decl_stmt|;
if|if
condition|(
name|prefix
operator|!=
literal|null
condition|)
block|{
name|String
name|namespace
init|=
name|getNamespace
argument_list|(
name|prefix
argument_list|)
decl_stmt|;
if|if
condition|(
name|namespace
operator|!=
literal|null
condition|)
block|{
return|return
name|namespace
operator|+
name|shortNameOrUri
operator|.
name|substring
argument_list|(
name|prefix
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
return|;
block|}
else|else
block|{
comment|//no mapping return null
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
comment|//not a shortName ... return the parsed
return|return
name|shortNameOrUri
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getShortName
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
name|String
name|namespace
init|=
name|NamespaceMappingUtils
operator|.
name|getNamespace
argument_list|(
name|uri
argument_list|)
decl_stmt|;
if|if
condition|(
name|namespace
operator|!=
literal|null
condition|)
block|{
name|String
name|prefix
init|=
name|getPrefix
argument_list|(
name|namespace
argument_list|)
decl_stmt|;
if|if
condition|(
name|prefix
operator|!=
literal|null
condition|)
block|{
return|return
name|prefix
operator|+
name|uri
operator|.
name|substring
argument_list|(
name|namespace
operator|.
name|length
argument_list|()
argument_list|)
return|;
block|}
comment|//else no mapping -> return the full URI
block|}
comment|//no namespace -> return the full URI
return|return
name|uri
return|;
block|}
comment|/**      * Internally used to mimic ServiceReferences when used outside OSGI      * @param<T>      */
specifier|private
class|class
name|NonOsgiServiceRef
parameter_list|<
name|T
parameter_list|>
implements|implements
name|ServiceReference
block|{
specifier|private
name|T
name|service
decl_stmt|;
specifier|private
name|NonOsgiServiceRef
parameter_list|(
name|T
name|service
parameter_list|)
block|{
name|this
operator|.
name|service
operator|=
name|service
expr_stmt|;
block|}
specifier|public
name|T
name|getService
parameter_list|()
block|{
return|return
name|service
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getProperty
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getPropertyKeys
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{}
return|;
block|}
annotation|@
name|Override
specifier|public
name|Bundle
name|getBundle
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Bundle
index|[]
name|getUsingBundles
parameter_list|()
block|{
return|return
operator|new
name|Bundle
index|[]
block|{}
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAssignableTo
parameter_list|(
name|Bundle
name|bundle
parameter_list|,
name|String
name|className
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|reference
parameter_list|)
block|{
return|return
literal|0
return|;
block|}
block|}
block|}
end_class

end_unit

