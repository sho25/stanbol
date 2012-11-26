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
name|Hashtable
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
name|Reference
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
name|en
operator|.
name|EnglishMinimalStemmer
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
comment|/**  * A word classifier for the english language based on SentiWordNet. Reads in a SentiWordNet file and  * represents mappings from word to sentiment score between -1 and 1 in a hashmap.  *<p/>  * Future versions might make use of a disk-based storage of the hashmap to improve memory performance.  *<p/>  * Note that a license for SentiWordNet is required if you intend to use the classifier in commercial  * settings.  *<p/>  * @author Sebastian Schaffert  */
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
name|SentiWordNet
block|{
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
literal|"http://wordnet.princeton.edu/"
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SentiWordNet
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SENTIWORDNET_RESOURCE
init|=
literal|"SentiWordNet_3.0.0_20120206.txt"
decl_stmt|;
specifier|protected
name|String
name|sentiWordNetFile
decl_stmt|;
specifier|private
name|ModelListener
name|modelListener
init|=
operator|new
name|ModelListener
argument_list|()
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|DataFileTracker
name|dataFileTracker
decl_stmt|;
specifier|private
name|BundleContext
name|bundleContext
decl_stmt|;
specifier|protected
name|SentiWordNetClassifierEN
name|classifier
decl_stmt|;
specifier|protected
name|ServiceRegistration
name|classifierRegistration
decl_stmt|;
specifier|public
name|SentiWordNet
parameter_list|()
block|{}
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
comment|//TODO: make configurable
name|sentiWordNetFile
operator|=
name|SENTIWORDNET_RESOURCE
expr_stmt|;
name|classifier
operator|=
operator|new
name|SentiWordNetClassifierEN
argument_list|()
expr_stmt|;
name|dataFileTracker
operator|.
name|add
argument_list|(
name|modelListener
argument_list|,
name|sentiWordNetFile
argument_list|,
name|modelProperties
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
name|ctx
parameter_list|)
block|{
if|if
condition|(
name|classifierRegistration
operator|!=
literal|null
condition|)
block|{
name|classifierRegistration
operator|.
name|unregister
argument_list|()
expr_stmt|;
name|classifierRegistration
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|classifier
operator|!=
literal|null
condition|)
block|{
name|classifier
operator|.
name|close
argument_list|()
expr_stmt|;
name|classifier
operator|=
literal|null
expr_stmt|;
block|}
name|dataFileTracker
operator|.
name|removeAll
argument_list|(
name|modelListener
argument_list|)
expr_stmt|;
name|sentiWordNetFile
operator|=
literal|null
expr_stmt|;
block|}
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
name|sentiWordNetFile
operator|.
name|equals
argument_list|(
name|resourceName
argument_list|)
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"{} resource available"
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
name|classifier
operator|!=
literal|null
condition|)
block|{
name|classifier
operator|.
name|parseSentiWordNet
argument_list|(
name|is
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
name|registerService
argument_list|()
expr_stmt|;
comment|//register the service
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
literal|"Unable to load '"
operator|+
name|resourceName
operator|+
literal|"'!"
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
literal|"RuntimeException while loading '"
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
name|sentiWordNetFile
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
literal|"en"
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
condition|)
block|{
name|classifierRegistration
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
name|classifier
argument_list|,
name|serviceProperties
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * The OSGI service registered as soon as the required DataFiles are      * available      */
specifier|public
specifier|static
class|class
name|SentiWordNetClassifierEN
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
specifier|private
name|EnglishMinimalStemmer
name|stemmer
init|=
operator|new
name|EnglishMinimalStemmer
argument_list|()
decl_stmt|;
specifier|protected
name|SentiWordNetClassifierEN
parameter_list|()
block|{}
specifier|protected
name|void
name|parseSentiWordNet
parameter_list|(
name|InputStream
name|is
parameter_list|)
throws|throws
name|IOException
block|{
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
comment|// read line by line:
comment|// - lines starting with # are ignored
comment|// - valid lines have the format POS ID POSSCORE NEGSCORE SYNONYMS GLOSS separated by tags
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
name|line
operator|=
name|line
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|line
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|line
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|'#'
condition|)
block|{
name|String
index|[]
name|components
init|=
name|line
operator|.
name|split
argument_list|(
literal|"\t"
argument_list|)
decl_stmt|;
try|try
block|{
name|double
name|posScore
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|components
index|[
literal|2
index|]
argument_list|)
decl_stmt|;
name|double
name|negScore
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|components
index|[
literal|3
index|]
argument_list|)
decl_stmt|;
name|String
name|synonyms
init|=
name|components
index|[
literal|4
index|]
decl_stmt|;
name|Double
name|score
init|=
name|posScore
operator|-
name|negScore
decl_stmt|;
if|if
condition|(
name|score
operator|!=
literal|0.0
condition|)
block|{
for|for
control|(
name|String
name|synonymToken
range|:
name|synonyms
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
control|)
block|{
comment|// synonymTokens are of the form word#position, so we strip out the position
comment|// part
name|String
index|[]
name|synonym
init|=
name|synonymToken
operator|.
name|split
argument_list|(
literal|"#"
argument_list|)
decl_stmt|;
name|wordMap
operator|.
name|put
argument_list|(
name|getStemmed
argument_list|(
name|synonym
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|score
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"could not parse SentiWordNet line '{}': {}"
argument_list|,
name|line
argument_list|,
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
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
comment|/**          * Given the word passed as argument, return a value between -1 and 1 indicating its sentiment value          * from very negative to very positive. Unknown words should return the value 0.          *           * @param word          * @return          */
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
name|String
name|stemmed
init|=
name|getStemmed
argument_list|(
name|word
argument_list|)
decl_stmt|;
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
name|stemmed
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
specifier|private
name|String
name|getStemmed
parameter_list|(
name|String
name|word
parameter_list|)
block|{
return|return
name|word
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|stemmer
operator|.
name|stem
argument_list|(
name|word
operator|.
name|toCharArray
argument_list|()
argument_list|,
name|word
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getLanguage
parameter_list|()
block|{
return|return
literal|"en"
return|;
block|}
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

