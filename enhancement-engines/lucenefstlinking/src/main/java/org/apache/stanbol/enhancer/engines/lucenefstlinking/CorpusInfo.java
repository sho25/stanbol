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
name|lucenefstlinking
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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|ref
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|ref
operator|.
name|SoftReference
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|ref
operator|.
name|WeakReference
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
name|concurrent
operator|.
name|Future
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|FileUtils
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
name|lang
operator|.
name|ObjectUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|Analyzer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|schema
operator|.
name|FieldType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|opensextant
operator|.
name|solrtexttagger
operator|.
name|TaggerFstCorpus
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
comment|/**  * Holds the information required for Lucene FST based tagging in a specific  * language by using a given field.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|CorpusInfo
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
name|CorpusInfo
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The language      */
specifier|public
specifier|final
name|String
name|language
decl_stmt|;
comment|/**      * The Corpus FST      */
specifier|protected
specifier|final
name|File
name|fst
decl_stmt|;
comment|/**      * used to detect fst file changes      */
specifier|private
name|Date
name|fstDate
decl_stmt|;
comment|/**      * The Solr field used for FST indexing (already encoded)      */
specifier|public
specifier|final
name|String
name|indexedField
decl_stmt|;
comment|/**      * The Solr stored field holding the labels indexed in the FST corpus       */
specifier|public
specifier|final
name|String
name|storedField
decl_stmt|;
comment|/**      * TODO: partial matches are currently deactivated      */
specifier|public
specifier|final
name|boolean
name|partialMatches
init|=
literal|false
decl_stmt|;
comment|/**      * if the FST corpus can be created on the fly      */
specifier|public
specifier|final
name|boolean
name|allowCreation
decl_stmt|;
comment|/**      * The Solr {@link Analyzer} used for the field      */
specifier|public
specifier|final
name|Analyzer
name|analyzer
decl_stmt|;
specifier|public
specifier|final
name|Analyzer
name|taggingAnalyzer
decl_stmt|;
specifier|protected
name|Reference
argument_list|<
name|TaggerFstCorpus
argument_list|>
name|taggerCorpusRef
decl_stmt|;
specifier|protected
name|long
name|enqueued
init|=
operator|-
literal|1
decl_stmt|;
comment|/**      * Allows to store an error message encountered while loading/creating the      * FST corpus.      */
specifier|private
name|String
name|errorMessage
decl_stmt|;
comment|/**      * Indicated an Error during loading the {@link #fst} file      */
specifier|private
name|boolean
name|fstFileError
init|=
literal|false
decl_stmt|;
comment|/**      * Indicates an Error during the runtime creation      */
specifier|private
name|boolean
name|creationError
init|=
literal|false
decl_stmt|;
comment|/**       * @param language      * @param indexField      * @param analyzer      * @param fst      * @param allowCreation      */
specifier|protected
name|CorpusInfo
parameter_list|(
name|String
name|language
parameter_list|,
name|String
name|indexField
parameter_list|,
name|String
name|storeField
parameter_list|,
name|FieldType
name|fieldType
parameter_list|,
name|File
name|fst
parameter_list|,
name|boolean
name|allowCreation
parameter_list|)
block|{
name|this
operator|.
name|language
operator|=
name|language
expr_stmt|;
name|this
operator|.
name|indexedField
operator|=
name|indexField
expr_stmt|;
name|this
operator|.
name|storedField
operator|=
name|storeField
expr_stmt|;
name|this
operator|.
name|fst
operator|=
name|fst
expr_stmt|;
name|this
operator|.
name|allowCreation
operator|=
name|allowCreation
expr_stmt|;
name|this
operator|.
name|analyzer
operator|=
name|fieldType
operator|.
name|getAnalyzer
argument_list|()
expr_stmt|;
name|this
operator|.
name|taggingAnalyzer
operator|=
name|fieldType
operator|.
name|getQueryAnalyzer
argument_list|()
expr_stmt|;
name|this
operator|.
name|fstDate
operator|=
name|fst
operator|.
name|isFile
argument_list|()
condition|?
operator|new
name|Date
argument_list|(
name|fst
operator|.
name|lastModified
argument_list|()
argument_list|)
else|:
literal|null
expr_stmt|;
block|}
comment|/**      * Allows to set an error occurring during the creation of       * @param message      */
specifier|protected
name|void
name|setError
parameter_list|(
name|long
name|enqueued
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|this
operator|.
name|errorMessage
operator|=
name|message
expr_stmt|;
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|creationError
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|this
operator|.
name|enqueued
operator|==
name|enqueued
condition|)
block|{
name|this
operator|.
name|enqueued
operator|=
operator|-
literal|1
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|isFstFile
parameter_list|()
block|{
return|return
name|fst
operator|!=
literal|null
operator|&&
name|fst
operator|.
name|isFile
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isFstFileError
parameter_list|()
block|{
return|return
name|fstFileError
return|;
block|}
specifier|public
name|boolean
name|isFstCreationError
parameter_list|()
block|{
return|return
name|creationError
return|;
block|}
specifier|public
name|String
name|getErrorMessage
parameter_list|()
block|{
return|return
name|errorMessage
return|;
block|}
comment|/**      * Allows to explicitly set the corpus after runtime creation has finished.      * The corpus will be linked by using a {@link WeakReference} to allow the      * GC to free the memory it consumes. If this happens the corpus will be      * loaded from the {@link #fst} file.      * @param enqueued the version of the corpus      * @param corpus the corpus      */
specifier|protected
specifier|final
name|void
name|setCorpus
parameter_list|(
name|long
name|enqueued
parameter_list|,
name|TaggerFstCorpus
name|corpus
parameter_list|)
block|{
if|if
condition|(
name|taggerCorpusRef
operator|!=
literal|null
condition|)
block|{
name|taggerCorpusRef
operator|.
name|clear
argument_list|()
expr_stmt|;
name|taggerCorpusRef
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|corpus
operator|!=
literal|null
condition|)
block|{
comment|//reset any error
name|this
operator|.
name|errorMessage
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|creationError
operator|=
literal|false
expr_stmt|;
comment|//we set the corpus as a weak reference. This allows the
comment|//GC to free the corpus earlier.
comment|//This is done, because here the corpus was just built and not
comment|//yet requested. So we want those to be GCed earlier.
name|taggerCorpusRef
operator|=
operator|new
name|WeakReference
argument_list|<
name|TaggerFstCorpus
argument_list|>
argument_list|(
name|corpus
argument_list|)
expr_stmt|;
block|}
comment|//check if the set version is the most current one
if|if
condition|(
name|enqueued
operator|==
name|this
operator|.
name|enqueued
condition|)
block|{
comment|//if so
name|this
operator|.
name|enqueued
operator|=
operator|-
literal|1
expr_stmt|;
comment|//mark this one as up-to-date
block|}
block|}
specifier|public
name|TaggerFstCorpus
name|getCorpus
parameter_list|()
block|{
name|TaggerFstCorpus
name|corpus
init|=
name|taggerCorpusRef
operator|==
literal|null
condition|?
literal|null
else|:
name|taggerCorpusRef
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|corpus
operator|!=
literal|null
condition|)
block|{
comment|//on first usage replace a WeakReference with a SoftReference
if|if
condition|(
name|taggerCorpusRef
operator|instanceof
name|WeakReference
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|taggerCorpusRef
operator|.
name|clear
argument_list|()
expr_stmt|;
name|taggerCorpusRef
operator|=
operator|new
name|SoftReference
argument_list|<
name|TaggerFstCorpus
argument_list|>
argument_list|(
name|corpus
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|taggerCorpusRef
operator|!=
literal|null
condition|)
block|{
name|taggerCorpusRef
operator|=
literal|null
expr_stmt|;
comment|//reset to null as the reference was taken
block|}
comment|//if we do not have a corpus try to load from file
if|if
condition|(
name|corpus
operator|==
literal|null
operator|&&
name|fst
operator|.
name|exists
argument_list|()
operator|&&
comment|//if the file exists
comment|//AND the file was not yet failing to load OR the file is newer
comment|//as the last version failing to load
operator|(
operator|!
name|fstFileError
operator|||
name|FileUtils
operator|.
name|isFileNewer
argument_list|(
name|fst
argument_list|,
name|fstDate
argument_list|)
operator|)
condition|)
block|{
try|try
block|{
name|corpus
operator|=
name|TaggerFstCorpus
operator|.
name|load
argument_list|(
name|fst
argument_list|)
expr_stmt|;
name|fstFileError
operator|=
literal|false
expr_stmt|;
name|fstDate
operator|=
operator|new
name|Date
argument_list|(
name|fst
operator|.
name|lastModified
argument_list|()
argument_list|)
expr_stmt|;
name|taggerCorpusRef
operator|=
operator|new
name|SoftReference
argument_list|<
name|TaggerFstCorpus
argument_list|>
argument_list|(
name|corpus
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|this
operator|.
name|errorMessage
operator|=
operator|new
name|StringBuilder
argument_list|(
literal|"Unable to load FST corpus from "
operator|+
literal|"FST file: '"
argument_list|)
operator|.
name|append
argument_list|(
name|fst
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"' (Message: "
argument_list|)
operator|.
name|append
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|")!"
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
name|errorMessage
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|fstFileError
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|corpus
return|;
block|}
comment|/**      * Called when a {@link CorpusInfo} object is enqueued for runtime generation.      * This is used to prevent multiple FST generation in cases where the      * FstInfo is enqueued a 2nd time before the first one was processed.      * @return the {@link System#currentTimeMillis() current time} when calling      * this method.      */
specifier|protected
name|long
name|enqueue
parameter_list|()
block|{
name|enqueued
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
return|return
name|enqueued
return|;
block|}
specifier|protected
name|long
name|getEnqueued
parameter_list|()
block|{
return|return
name|enqueued
return|;
block|}
comment|/**      * Returns if the FST corpus described by this FST info is queued for      * generation. NOTE: that {@link #getCorpus()} might still return a       * {@link TaggerCorpus}, but in this case it will be based on an outdated      * version of the index.      * @return<code>true</code> if the FST corpus is enqueued for (re)generation.      */
specifier|public
name|boolean
name|isEnqueued
parameter_list|()
block|{
return|return
name|enqueued
operator|>
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"FST Info[language: "
argument_list|)
operator|.
name|append
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|indexedField
operator|.
name|equals
argument_list|(
name|storedField
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" | field: "
argument_list|)
operator|.
name|append
argument_list|(
name|indexedField
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" | fields(index:"
argument_list|)
operator|.
name|append
argument_list|(
name|indexedField
argument_list|)
operator|.
name|append
argument_list|(
literal|", stored:"
argument_list|)
operator|.
name|append
argument_list|(
name|storedField
argument_list|)
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|" | file: "
argument_list|)
operator|.
name|append
argument_list|(
name|fst
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"(exists: "
argument_list|)
operator|.
name|append
argument_list|(
name|fst
operator|.
name|isFile
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
operator|.
name|append
argument_list|(
literal|" | runtime creation: "
argument_list|)
operator|.
name|append
argument_list|(
name|allowCreation
argument_list|)
operator|.
name|append
argument_list|(
literal|"]"
argument_list|)
expr_stmt|;
return|return
name|sb
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
name|indexedField
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
name|obj
parameter_list|)
block|{
return|return
name|obj
operator|instanceof
name|CorpusInfo
operator|&&
operator|(
operator|(
name|CorpusInfo
operator|)
name|obj
operator|)
operator|.
name|indexedField
operator|.
name|equals
argument_list|(
name|indexedField
argument_list|)
operator|&&
operator|(
operator|(
name|CorpusInfo
operator|)
name|obj
operator|)
operator|.
name|storedField
operator|.
name|equals
argument_list|(
name|storedField
argument_list|)
operator|&&
name|ObjectUtils
operator|.
name|equals
argument_list|(
name|language
argument_list|,
name|language
argument_list|)
return|;
block|}
block|}
end_class

end_unit

