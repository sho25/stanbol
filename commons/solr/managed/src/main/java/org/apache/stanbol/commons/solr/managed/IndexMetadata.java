begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_comment
comment|/**  *   */
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
name|solr
operator|.
name|managed
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|ManagedIndexConstants
operator|.
name|INDEX_ARCHIVES
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|ManagedIndexConstants
operator|.
name|INDEX_NAME
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|ManagedIndexConstants
operator|.
name|SERVER_NAME
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|ManagedIndexConstants
operator|.
name|SYNCHRONIZED
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
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
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Writer
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|InvalidPropertiesFormatException
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
name|Properties
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
name|stanbol
operator|.
name|commons
operator|.
name|solr
operator|.
name|IndexReference
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
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProvider
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
comment|/**  * Extends {@link Properties} with getter and setter for the metadata used  * by managed Solr indexes.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|IndexMetadata
extends|extends
name|Properties
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|IndexMetadata
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * generated      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5831848470486994628L
decl_stmt|;
comment|/**      * The Archive currently used for the index      */
specifier|protected
specifier|static
specifier|final
name|String
name|ARCHIVE
init|=
literal|"Archive"
decl_stmt|;
comment|/**      * The current state of this index      */
specifier|protected
specifier|static
specifier|final
name|String
name|STATE
init|=
literal|"State"
decl_stmt|;
comment|/**      * The Directory of this index on the local file system      */
specifier|protected
specifier|static
specifier|final
name|String
name|DIRECTORY
init|=
literal|"Directory"
decl_stmt|;
comment|/**      * If the current {@link #getState()} == {@link ManagedIndexState#ERROR}      * this property is used to store the {@link Exception#printStackTrace() stack trace}      * as reported by the the {@link Exception} that caused the Error      */
specifier|protected
specifier|static
specifier|final
name|String
name|STACK_TRACE
init|=
literal|"Stack-Trace"
decl_stmt|;
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|EMPTY_LIST_OF_STRING
init|=
name|Collections
operator|.
name|emptyList
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|load
parameter_list|(
name|Reader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|load
argument_list|(
name|reader
argument_list|)
expr_stmt|;
name|validate
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|load
parameter_list|(
name|InputStream
name|inStream
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|load
argument_list|(
name|inStream
argument_list|)
expr_stmt|;
name|validate
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|loadFromXML
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|InvalidPropertiesFormatException
block|{
name|super
operator|.
name|loadFromXML
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|validate
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|store
parameter_list|(
name|OutputStream
name|out
parameter_list|,
name|String
name|comments
parameter_list|)
throws|throws
name|IOException
block|{
name|validate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|super
operator|.
name|store
argument_list|(
name|out
argument_list|,
name|comments
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|store
parameter_list|(
name|Writer
name|writer
parameter_list|,
name|String
name|comments
parameter_list|)
throws|throws
name|IOException
block|{
name|validate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|super
operator|.
name|store
argument_list|(
name|writer
argument_list|,
name|comments
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|storeToXML
parameter_list|(
name|OutputStream
name|os
parameter_list|,
name|String
name|comment
parameter_list|)
throws|throws
name|IOException
block|{
name|validate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|super
operator|.
name|storeToXML
argument_list|(
name|os
argument_list|,
name|comment
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|storeToXML
parameter_list|(
name|OutputStream
name|os
parameter_list|,
name|String
name|comment
parameter_list|,
name|String
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
name|validate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|super
operator|.
name|storeToXML
argument_list|(
name|os
argument_list|,
name|comment
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|Deprecated
specifier|public
specifier|synchronized
name|void
name|save
parameter_list|(
name|OutputStream
name|out
parameter_list|,
name|String
name|comments
parameter_list|)
throws|throws
name|UnsupportedOperationException
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"deprecated Method not supported"
argument_list|)
throw|;
block|}
comment|/**      * validates the values of the IndexProperties      * @throws IOException      */
specifier|private
name|void
name|validate
parameter_list|(
name|boolean
name|store
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|isSynchronized
argument_list|()
operator|&&
name|getIndexArchives
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to "
operator|+
operator|(
name|store
condition|?
literal|"store"
else|:
literal|"read"
operator|)
operator|+
literal|" IndexPropertis where Synchronized=true and no Index-Archives are defined!"
argument_list|)
throw|;
block|}
name|ManagedIndexState
name|state
init|=
name|getState
argument_list|()
decl_stmt|;
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to "
operator|+
operator|(
name|store
condition|?
literal|"store"
else|:
literal|"read"
operator|)
operator|+
literal|" IndexMetadata without the required key '"
operator|+
name|STATE
operator|+
literal|"' set to one of the values '"
operator|+
name|Arrays
operator|.
name|toString
argument_list|(
name|ManagedIndexState
operator|.
name|values
argument_list|()
argument_list|)
operator|+
literal|"'!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|isActive
argument_list|()
condition|)
block|{
if|if
condition|(
name|getDirectory
argument_list|()
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to "
operator|+
operator|(
name|store
condition|?
literal|"store"
else|:
literal|"read"
operator|)
operator|+
literal|" IndexPropertis where Active=true and no Directory is defined!"
argument_list|)
throw|;
block|}
block|}
name|String
name|name
init|=
name|getIndexName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to "
operator|+
operator|(
name|store
condition|?
literal|"store"
else|:
literal|"read"
operator|)
operator|+
literal|" IndexPropertis where the required key '"
operator|+
name|INDEX_NAME
operator|+
literal|"' is not defined or empty!"
argument_list|)
throw|;
block|}
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getIndexArchives
parameter_list|()
block|{
name|String
name|value
init|=
name|getProperty
argument_list|(
name|INDEX_ARCHIVES
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|?
name|EMPTY_LIST_OF_STRING
else|:
name|Arrays
operator|.
name|asList
argument_list|(
name|value
operator|.
name|split
argument_list|(
literal|","
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|void
name|setIndexArchives
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|archives
parameter_list|)
block|{
name|StringBuilder
name|value
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|String
name|archive
range|:
name|archives
control|)
block|{
if|if
condition|(
name|archive
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|first
condition|)
block|{
name|value
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
name|value
operator|.
name|append
argument_list|(
name|archive
argument_list|)
expr_stmt|;
block|}
block|}
name|setProperty
argument_list|(
name|INDEX_ARCHIVES
argument_list|,
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSynchronized
parameter_list|()
block|{
name|String
name|value
init|=
name|getProperty
argument_list|(
name|SYNCHRONIZED
argument_list|)
decl_stmt|;
return|return
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|value
argument_list|)
return|;
block|}
specifier|public
name|void
name|setSynchronized
parameter_list|(
name|boolean
name|state
parameter_list|)
block|{
name|setProperty
argument_list|(
name|SYNCHRONIZED
argument_list|,
name|Boolean
operator|.
name|toString
argument_list|(
name|state
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getArchive
parameter_list|()
block|{
return|return
name|getProperty
argument_list|(
name|ARCHIVE
argument_list|)
return|;
block|}
specifier|public
name|void
name|setArchive
parameter_list|(
name|String
name|archive
parameter_list|)
block|{
if|if
condition|(
name|archive
operator|==
literal|null
condition|)
block|{
name|remove
argument_list|(
name|ARCHIVE
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|archive
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed archive MUST NOT be empty!"
argument_list|)
throw|;
block|}
else|else
block|{
name|setProperty
argument_list|(
name|ARCHIVE
argument_list|,
name|archive
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getIndexName
parameter_list|()
block|{
return|return
name|getProperty
argument_list|(
name|INDEX_NAME
argument_list|)
return|;
block|}
specifier|public
name|void
name|setIndexName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The Index-Name MUST NOT be NULL nor empty"
argument_list|)
throw|;
block|}
name|setProperty
argument_list|(
name|INDEX_NAME
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getServerName
parameter_list|()
block|{
return|return
name|getProperty
argument_list|(
name|SERVER_NAME
argument_list|)
return|;
block|}
specifier|public
name|void
name|setServerName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The Server-Name MUST NOT be NULL nor empty"
argument_list|)
throw|;
block|}
name|setProperty
argument_list|(
name|SERVER_NAME
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getDirectory
parameter_list|()
block|{
return|return
name|getProperty
argument_list|(
name|DIRECTORY
argument_list|)
return|;
block|}
specifier|public
name|void
name|setDirectory
parameter_list|(
name|String
name|directory
parameter_list|)
block|{
if|if
condition|(
name|directory
operator|==
literal|null
condition|)
block|{
name|remove
argument_list|(
name|DIRECTORY
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|directory
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed directory MUST NOT be empty!"
argument_list|)
throw|;
block|}
else|else
block|{
name|setProperty
argument_list|(
name|DIRECTORY
argument_list|,
name|directory
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|ManagedIndexState
name|getState
parameter_list|()
block|{
name|String
name|state
init|=
name|getProperty
argument_list|(
name|STATE
argument_list|)
decl_stmt|;
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"No ManagedIndexState (key: '"
operator|+
name|STATE
operator|+
literal|"') present in the"
operator|+
literal|"IndexMetadata for '"
operator|+
name|getIndexReference
argument_list|()
operator|+
literal|"'! -> return null"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
try|try
block|{
return|return
name|ManagedIndexState
operator|.
name|valueOf
argument_list|(
name|state
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to parse ManagedIndexState from value '"
operator|+
name|state
operator|+
literal|"'! -> return null"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
comment|/**      * Checks if this index is in the {@link ManagedIndexState#ACTIVE} state      * @return if this index is active or not      * @see #getState()      */
specifier|public
name|boolean
name|isActive
parameter_list|()
block|{
name|ManagedIndexState
name|state
init|=
name|getState
argument_list|()
decl_stmt|;
return|return
name|state
operator|!=
literal|null
operator|&&
name|state
operator|==
name|ManagedIndexState
operator|.
name|ACTIVE
return|;
block|}
comment|/**      * Checks if this index is in the {@link ManagedIndexState#INACTIVE} state      * @return if this index is inactive or not      * @see #getState()      */
specifier|public
name|boolean
name|isInactive
parameter_list|()
block|{
name|ManagedIndexState
name|state
init|=
name|getState
argument_list|()
decl_stmt|;
return|return
name|state
operator|!=
literal|null
operator|&&
name|state
operator|==
name|ManagedIndexState
operator|.
name|INACTIVE
return|;
block|}
comment|/**      * Checks if this index is in the {@link ManagedIndexState#ERROR} state      * @return if this index has an error or not      * @see #getState()      */
specifier|public
name|boolean
name|isError
parameter_list|()
block|{
name|ManagedIndexState
name|state
init|=
name|getState
argument_list|()
decl_stmt|;
return|return
name|state
operator|!=
literal|null
operator|&&
name|state
operator|==
name|ManagedIndexState
operator|.
name|ERROR
return|;
block|}
comment|/**      * Checks if this index is in the {@link ManagedIndexState#UNINITIALISED} state      * @return if this index is still not initialised      * @see #getState()      */
specifier|public
name|boolean
name|isUninitialised
parameter_list|()
block|{
name|ManagedIndexState
name|state
init|=
name|getState
argument_list|()
decl_stmt|;
return|return
name|state
operator|!=
literal|null
operator|&&
name|state
operator|==
name|ManagedIndexState
operator|.
name|UNINITIALISED
return|;
block|}
specifier|public
name|void
name|setState
parameter_list|(
name|ManagedIndexState
name|state
parameter_list|)
block|{
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ManagedIndexState MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|setProperty
argument_list|(
name|STATE
argument_list|,
name|state
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|state
operator|!=
name|ManagedIndexState
operator|.
name|ERROR
condition|)
block|{
name|remove
argument_list|(
name|STACK_TRACE
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the {@link IndexReference} based on the {@link #getServerName()} and      * {@link #getIndexName()} values      * @return the {@link IndexReference} to the index described by this metadata      */
specifier|public
name|IndexReference
name|getIndexReference
parameter_list|()
block|{
return|return
operator|new
name|IndexReference
argument_list|(
name|getServerName
argument_list|()
argument_list|,
name|getIndexName
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Sets the {@link #getState()} to {@link ManagedIndexState#ERROR} and also      * stores the stack trace of the parsed {@link Exception} to {@link #STACK_TRACE}.      * @param e The Exception or<code>null</code> it none      */
specifier|public
name|void
name|setError
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|setState
argument_list|(
name|ManagedIndexState
operator|.
name|ERROR
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|!=
literal|null
condition|)
block|{
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|PrintWriter
name|writer
init|=
operator|new
name|PrintWriter
argument_list|(
name|out
argument_list|)
decl_stmt|;
comment|//writer.append(e.getMessage());
comment|//writer.append('\n');
name|e
operator|.
name|printStackTrace
argument_list|(
name|writer
argument_list|)
expr_stmt|;
name|writer
operator|.
name|close
argument_list|()
expr_stmt|;
comment|//close and flush the writer
name|setProperty
argument_list|(
name|STACK_TRACE
argument_list|,
name|out
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|writer
argument_list|)
expr_stmt|;
name|out
operator|=
literal|null
expr_stmt|;
block|}
block|}
comment|/**      * The stack trace of the Exception caused this index to be in the      * {@link ManagedIndexState#ERROR} state or<code>null</code> if not present      * @return The stack trace or<code>null</code> if not present      */
specifier|public
name|String
name|getErrorStackTrace
parameter_list|()
block|{
return|return
name|getProperty
argument_list|(
name|STACK_TRACE
argument_list|)
return|;
block|}
comment|/**      * Converts the parsed {@link IndexMetadata} to an {@link Map} with       * {@link String} keys and values as used by the  {@link DataFileProvider}       * interface      * @return the Map with {@link String} as key and values      */
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|toStringMap
parameter_list|(
name|IndexMetadata
name|metadata
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|entry
range|:
name|metadata
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|map
operator|.
name|put
argument_list|(
operator|(
name|String
operator|)
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|map
return|;
block|}
block|}
end_class

end_unit

