begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (c) 2012 Sebastian Schaffert  *  *  Licensed under the Apache License, Version 2.0 (the "License");  *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
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
name|sentiment
operator|.
name|classifiers
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
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
name|InputStreamReader
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
name|Hashtable
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
name|Reference
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
name|DataFileListener
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
name|DataFileTracker
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
name|enhancer
operator|.
name|engines
operator|.
name|sentiment
operator|.
name|api
operator|.
name|LexicalCategoryClassifier
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
name|enhancer
operator|.
name|engines
operator|.
name|sentiment
operator|.
name|api
operator|.
name|SentimentClassifier
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
name|ServiceRegistration
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

begin_comment
comment|/**  * A German word classifier based on SentiWS. Reads the SentiWS positive and negative word lists and parses them  * into an appropriate hash table, so lookups should be extremely fast.  *<p/>  * @author Sebastian Schaffert  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
specifier|public
class|class
name|SentiWSComponent
block|{
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SentiWSComponent
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|modelProperties
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
static|static
block|{
name|modelProperties
operator|.
name|put
argument_list|(
literal|"Description"
argument_list|,
literal|"Sentiment Word List (German)"
argument_list|)
expr_stmt|;
name|modelProperties
operator|.
name|put
argument_list|(
literal|"Download Location"
argument_list|,
literal|"http://wortschatz.informatik.uni-leipzig.de/download/"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Reference
specifier|private
name|DataFileTracker
name|dataFileProvider
decl_stmt|;
specifier|protected
name|BundleContext
name|bundleContext
decl_stmt|;
comment|/**      * Registration for the {@link SentiWsClassifierDE}. Will be created as      * soon as both required data files are available and successfully loaded      */
specifier|protected
name|ServiceRegistration
name|sentiWsClassifierService
decl_stmt|;
specifier|protected
name|SentiWsClassifierDE
name|sentiWsClassifier
decl_stmt|;
specifier|private
name|ModelListener
name|modelListener
init|=
operator|new
name|ModelListener
argument_list|()
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|String
argument_list|>
name|sentiWsFileNames
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|String
argument_list|>
name|loadedSentiWsFiles
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|SentiWSComponent
parameter_list|()
block|{}
comment|/**      * Tracks the SentiWS files and triggers the registration of the service      */
specifier|private
class|class
name|ModelListener
implements|implements
name|DataFileListener
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|available
parameter_list|(
name|String
name|resourceName
parameter_list|,
name|InputStream
name|is
parameter_list|)
block|{
if|if
condition|(
name|sentiWsFileNames
operator|.
name|contains
argument_list|(
name|resourceName
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"sentiWs resource {} available"
argument_list|,
name|resourceName
argument_list|)
expr_stmt|;
try|try
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
if|if
condition|(
name|sentiWsClassifier
operator|!=
literal|null
condition|)
block|{
name|sentiWsClassifier
operator|.
name|parseSentiWS
argument_list|(
name|is
argument_list|)
expr_stmt|;
name|loadedSentiWsFiles
operator|.
name|add
argument_list|(
name|resourceName
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"   ... loaded in {} ms"
argument_list|,
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
operator|)
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
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to load sentiWs resource '"
operator|+
name|resourceName
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
comment|//keep tracking
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"RuntimeException while loading sentiWs resource '"
operator|+
name|resourceName
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
comment|//keep tracking
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Tracker notified event for non-tracked resource '{}'"
operator|+
literal|"(tracked: {})!"
argument_list|,
name|resourceName
argument_list|,
name|sentiWsFileNames
argument_list|)
expr_stmt|;
block|}
comment|//all resources available ... start the service
if|if
condition|(
name|loadedSentiWsFiles
operator|.
name|equals
argument_list|(
name|sentiWsFileNames
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"register Sentiment Classifier for SentiWs (german)"
argument_list|)
expr_stmt|;
name|registerService
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"loaded {} (required: {})"
argument_list|,
name|loadedSentiWsFiles
argument_list|,
name|sentiWsFileNames
argument_list|)
expr_stmt|;
block|}
comment|//remove registration
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|unavailable
parameter_list|(
name|String
name|resourceName
parameter_list|)
block|{
comment|//not used;
return|return
literal|false
return|;
block|}
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
block|{
name|bundleContext
operator|=
name|ctx
operator|.
name|getBundleContext
argument_list|()
expr_stmt|;
comment|//TODO: make Filenames configurable
name|sentiWsFileNames
operator|.
name|add
argument_list|(
literal|"SentiWS_v1.8b_Negative.txt"
argument_list|)
expr_stmt|;
name|sentiWsFileNames
operator|.
name|add
argument_list|(
literal|"SentiWS_v1.8b_Positive.txt"
argument_list|)
expr_stmt|;
comment|//register files with the DataFileTracker
for|for
control|(
name|String
name|sentiWsFile
range|:
name|sentiWsFileNames
control|)
block|{
name|dataFileProvider
operator|.
name|add
argument_list|(
name|modelListener
argument_list|,
name|sentiWsFile
argument_list|,
name|modelProperties
argument_list|)
expr_stmt|;
block|}
name|sentiWsClassifier
operator|=
operator|new
name|SentiWsClassifierDE
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|registerService
parameter_list|()
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|serviceProperties
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|serviceProperties
operator|.
name|put
argument_list|(
literal|"language"
argument_list|,
literal|"de"
argument_list|)
expr_stmt|;
comment|//set the language
name|BundleContext
name|bc
init|=
name|bundleContext
decl_stmt|;
if|if
condition|(
name|bc
operator|!=
literal|null
operator|&&
name|sentiWsClassifierService
operator|==
literal|null
condition|)
block|{
name|sentiWsClassifierService
operator|=
name|bc
operator|.
name|registerService
argument_list|(
name|SentimentClassifier
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|sentiWsClassifier
argument_list|,
name|serviceProperties
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
comment|//end datafile tracking
name|dataFileProvider
operator|.
name|removeAll
argument_list|(
name|modelListener
argument_list|)
expr_stmt|;
name|sentiWsFileNames
operator|.
name|clear
argument_list|()
expr_stmt|;
name|loadedSentiWsFiles
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|//remove service registration
if|if
condition|(
name|sentiWsClassifierService
operator|!=
literal|null
condition|)
block|{
name|sentiWsClassifierService
operator|.
name|unregister
argument_list|()
expr_stmt|;
block|}
comment|//free up resources
if|if
condition|(
name|sentiWsClassifier
operator|!=
literal|null
condition|)
block|{
name|sentiWsClassifier
operator|.
name|close
argument_list|()
expr_stmt|;
name|sentiWsClassifier
operator|=
literal|null
expr_stmt|;
block|}
name|bundleContext
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * The OSGI service registered as soon as the required DataFiles are      * available      */
specifier|public
specifier|static
class|class
name|SentiWsClassifierDE
extends|extends
name|LexicalCategoryClassifier
implements|implements
name|SentimentClassifier
block|{
specifier|private
name|ReadWriteLock
name|lock
init|=
operator|new
name|ReentrantReadWriteLock
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|wordMap
init|=
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|SentiWsClassifierDE
parameter_list|()
block|{}
specifier|protected
name|void
name|parseSentiWS
parameter_list|(
name|InputStream
name|is
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"parsing SentiWS word lists ..."
argument_list|)
expr_stmt|;
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|)
argument_list|)
decl_stmt|;
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
for|for
control|(
name|String
name|line
init|=
name|in
operator|.
name|readLine
argument_list|()
init|;
name|line
operator|!=
literal|null
condition|;
name|line
operator|=
name|in
operator|.
name|readLine
argument_list|()
control|)
block|{
comment|// input file will have a space- or tab-separated list per line:
comment|// - first component is the main word with a specification what kind of word it is (can be result from POS tagging)
comment|// - second component is the positive or negative sentiment associated with the word
comment|// - third argument is a comma-separated list of deflections of the word as they might also occur in text
name|String
index|[]
name|components
init|=
name|line
operator|.
name|split
argument_list|(
literal|"\\s"
argument_list|)
decl_stmt|;
comment|// parse the weight
name|Double
name|weight
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|components
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
comment|// get the main word
name|String
index|[]
name|mainWord
init|=
name|components
index|[
literal|0
index|]
operator|.
name|split
argument_list|(
literal|"\\|"
argument_list|)
decl_stmt|;
name|wordMap
operator|.
name|put
argument_list|(
name|mainWord
index|[
literal|0
index|]
argument_list|,
name|weight
argument_list|)
expr_stmt|;
comment|// get the remaining words (deflections)
if|if
condition|(
name|components
operator|.
name|length
operator|>
literal|2
condition|)
block|{
for|for
control|(
name|String
name|word
range|:
name|components
index|[
literal|2
index|]
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|String
name|lcWord
init|=
name|word
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|GERMAN
argument_list|)
decl_stmt|;
name|Double
name|current
init|=
name|wordMap
operator|.
name|put
argument_list|(
name|lcWord
argument_list|,
name|weight
argument_list|)
decl_stmt|;
if|if
condition|(
name|current
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Multiple sentiments [{},{}] for word {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|current
block|,
name|weight
block|,
name|lcWord
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
finally|finally
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|getWordCount
parameter_list|()
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|wordMap
operator|.
name|size
argument_list|()
return|;
block|}
finally|finally
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getLanguage
parameter_list|()
block|{
return|return
literal|"de"
return|;
block|}
comment|/**          * Given the word passed as argument, return a value between -1 and 1 indicating its sentiment value from          * very negative to very positive. Unknown words should return the value 0.          *          * @param word          * @return          */
annotation|@
name|Override
specifier|public
name|double
name|classifyWord
parameter_list|(
name|String
name|word
parameter_list|)
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|Double
name|sentiment
init|=
name|wordMap
operator|.
name|get
argument_list|(
name|word
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|GERMAN
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|sentiment
operator|!=
literal|null
condition|?
name|sentiment
operator|.
name|doubleValue
argument_list|()
else|:
literal|0.0
return|;
block|}
finally|finally
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**          * Internally used to free up resources when the service is          * unregistered          */
specifier|protected
name|void
name|close
parameter_list|()
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|wordMap
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

