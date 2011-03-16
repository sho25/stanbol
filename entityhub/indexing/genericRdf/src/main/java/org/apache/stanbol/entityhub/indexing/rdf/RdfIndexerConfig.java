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
name|indexing
operator|.
name|rdf
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_CHUNK_SIZE
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_DEFAULT_ENTITY_RANKING
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_ENTITY_RANKINGS
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_FIELD_MAPPINGS
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_IGNORE_ENTITIES_WITHOUT_ENTITY_RANKING
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_INDEXING_MODE
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_MODEL_NAME
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_RDF_FILES
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_RDF_STORE_DIR
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_REQUIRED_ENTITY_RANKING
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_SKIP_READ
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
name|entityhub
operator|.
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|KEY_YARD
import|;
end_import

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
name|entityhub
operator|.
name|core
operator|.
name|mapping
operator|.
name|FieldMappingUtils
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
name|indexing
operator|.
name|rdf
operator|.
name|RdfIndexer
operator|.
name|IndexingMode
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
name|mapping
operator|.
name|FieldMapper
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
name|mapping
operator|.
name|FieldMapping
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
name|rdf
operator|.
name|RdfResourceEnum
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
name|yard
operator|.
name|Yard
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
comment|/**  * API based Configuration for the {@link Dictionary} based configuration of the   * {@link RdfIndexer} class.  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
class|class
name|RdfIndexerConfig
block|{
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RdfIndexerConfig
operator|.
name|class
argument_list|)
decl_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
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
comment|/**      * Constructs a minimal configuration for a Yard and a list of RDF Files      * to be indexed. Parsed files are checked for existences and read access.<p>      * Note that:<ul>      *<li><code>{@link Yard#getId()}+"_indexingData"</code> is used as the      * directory for indexing (use {@link #setIndexingDir(File)} to change this      * default).      *<li><code>{@link Yard#getId()}+"_model"</code> is used as modelName for      * the name of the Model (use {@link #setModelName(String)} to change this      * default).      *<li> if the list of the parsed files is<code>null</code> or empty, than      * the {@link #setSkipRead(Boolean) is set to<code>true</code>. This means      * that it is assumed that the RDF Model (name: #getModelName() and part of      * the RDF triple store located at #getIndexingDir()) already contains all      * the RDF data needed for the indexing. Otherwise the all parsed files are      * checked for existence and read access and #setSkipRead(Boolean) is set to      *<code>false</code>. This will trigger to read the RDF data of that files      * to be stored in the RDF model with the configured name and the Triple      * Store with the configured location.      *</ul>      * @param yard The {@link Yard} used to store the RDF data      * @param rdfFiles the RDF files to index.      */
specifier|public
name|RdfIndexerConfig
parameter_list|(
name|Yard
name|yard
parameter_list|,
name|File
modifier|...
name|rdfFiles
parameter_list|)
block|{
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Yard MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|config
operator|.
name|put
argument_list|(
name|KEY_YARD
argument_list|,
name|yard
argument_list|)
expr_stmt|;
name|setIndexingDir
argument_list|(
operator|new
name|File
argument_list|(
name|yard
operator|.
name|getId
argument_list|()
operator|+
literal|"_indexingData"
argument_list|)
argument_list|)
expr_stmt|;
name|setModelName
argument_list|(
name|yard
operator|.
name|getId
argument_list|()
operator|+
literal|"_model"
argument_list|)
expr_stmt|;
if|if
condition|(
name|rdfFiles
operator|==
literal|null
operator|||
name|rdfFiles
operator|.
name|length
operator|<
literal|1
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"no RDF Files parsed -> set skipRead to TRUE"
argument_list|)
expr_stmt|;
name|setSkipRead
argument_list|(
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|addSourceFile
argument_list|(
name|rdfFiles
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Adds the parsed source files. Checks for {@link File#exists()}, {@link File#isFile()} and      * {@link File#canRead()}. Files that do not pase this test are ignored.      *       * @param sourceFiles      *            the source files to add      */
specifier|public
specifier|final
name|void
name|addSourceFile
parameter_list|(
name|File
modifier|...
name|sourceFiles
parameter_list|)
block|{
if|if
condition|(
name|sourceFiles
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|File
name|sourceFile
range|:
name|sourceFiles
control|)
block|{
name|checkAndAddSrouceFile
argument_list|(
name|sourceFile
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Remove the parsed source files      *       * @param sourceFile      *            the source files to remove      */
specifier|public
specifier|final
name|void
name|removeSourceFile
parameter_list|(
name|File
modifier|...
name|sourceFile
parameter_list|)
block|{
if|if
condition|(
name|sourceFile
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|Set
argument_list|<
name|File
argument_list|>
name|files
init|=
operator|(
name|Set
argument_list|<
name|File
argument_list|>
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_RDF_FILES
argument_list|)
decl_stmt|;
if|if
condition|(
name|files
operator|!=
literal|null
condition|)
block|{
name|files
operator|.
name|removeAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|sourceFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the list of RDF files used as source for indexing.      *       * @return the unmodifiable list of files or<code>null</code>if no source files are present.      */
specifier|public
specifier|final
name|Collection
argument_list|<
name|File
argument_list|>
name|getSourceFiles
parameter_list|()
block|{
name|Collection
argument_list|<
name|File
argument_list|>
name|sourceFiles
init|=
operator|(
name|Collection
argument_list|<
name|File
argument_list|>
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_RDF_FILES
argument_list|)
decl_stmt|;
return|return
name|sourceFiles
operator|==
literal|null
condition|?
literal|null
else|:
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
operator|(
name|Collection
argument_list|<
name|File
argument_list|>
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_RDF_FILES
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Setter for the directory used to store the data of the RDF triple store used for indexing.      *       * @param file      *            the directory used to store the data (created if not exist)      * @return<code>true</code> if the parsed file can be used as indexing      *     directory (exists and is a directory or !exists)       */
specifier|public
specifier|final
name|boolean
name|setIndexingDir
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
if|if
condition|(
name|checkFile
argument_list|(
name|file
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
condition|)
block|{
comment|// isDirectory or !exists
name|config
operator|.
name|put
argument_list|(
name|KEY_RDF_STORE_DIR
argument_list|,
name|file
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Getter for the indexing directory      * @return the directory used for indexing      */
specifier|public
specifier|final
name|File
name|getIndexingDir
parameter_list|()
block|{
return|return
operator|(
name|File
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_RDF_STORE_DIR
argument_list|)
return|;
block|}
comment|/**      * Setter for the RDF model name used to store/access the RDF data used      * for indexing      * @param modelName the RDF model name      */
specifier|public
specifier|final
name|void
name|setModelName
parameter_list|(
name|String
name|modelName
parameter_list|)
block|{
if|if
condition|(
name|modelName
operator|!=
literal|null
operator|&&
operator|!
name|modelName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|KEY_MODEL_NAME
argument_list|,
name|modelName
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to set modelName to NULL or an empty String"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the RDF model name used to store/access the RDF data used      * for indexing      * @return the RDF model name      */
specifier|public
specifier|final
name|String
name|getModelName
parameter_list|()
block|{
return|return
operator|(
name|String
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_MODEL_NAME
argument_list|)
return|;
block|}
comment|/**      * Setter for the (optional) map that uses entity ids as key and there       * ranking as value. see {@link RdfResourceEnum#signRank} for more information      * about ranking of entities)      * @param entityRankings the entity ranking map      */
specifier|public
specifier|final
name|void
name|setEntityRankings
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Float
argument_list|>
name|entityRankings
parameter_list|)
block|{
if|if
condition|(
name|entityRankings
operator|==
literal|null
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|KEY_ENTITY_RANKINGS
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|KEY_ENTITY_RANKINGS
argument_list|,
name|entityRankings
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the (optional) map that uses entity ids as key and there       * ranking as value. see {@link RdfResourceEnum#signRank} for more information      * about ranking of entities)      * @return the entity rankings or<code>null</code> if no entity rankings      * are present      */
specifier|public
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Float
argument_list|>
name|getNetityRankings
parameter_list|()
block|{
return|return
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|Float
argument_list|>
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_ENTITY_RANKINGS
argument_list|)
return|;
block|}
comment|/**      * Setter for the indexing mode (expert use only). Please carefully read      * {@link RdfIndexer#KEY_INDEXING_MODE} before setting this property.      * @param mode the indexing mode      */
specifier|public
specifier|final
name|void
name|setIndexingMode
parameter_list|(
name|IndexingMode
name|mode
parameter_list|)
block|{
if|if
condition|(
name|mode
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|KEY_INDEXING_MODE
argument_list|,
name|mode
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|KEY_INDEXING_MODE
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the Indexing Mode      * @return the indexing mode or<code>null</code> if not set by this      * configuration.      */
specifier|public
specifier|final
name|IndexingMode
name|getIndexingMode
parameter_list|()
block|{
return|return
operator|(
name|IndexingMode
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_INDEXING_MODE
argument_list|)
return|;
block|}
comment|/**      * Setter for the skip reading mode. If set to<code>true</code> no RDF      * data are read. This can be useful if the RDF data are      * already available as an Jena TDB store (e.g. when interrupting an      * indexing session that has already completed with reading the RDF data.      * @param state the state or<code>null</code> to remove any present config      */
specifier|public
specifier|final
name|void
name|setSkipRead
parameter_list|(
name|Boolean
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
name|config
operator|.
name|remove
argument_list|(
name|KEY_SKIP_READ
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|KEY_SKIP_READ
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the skip reading state.      * @return the state or<code>null</code> if not set      */
specifier|public
specifier|final
name|Boolean
name|getSkipRead
parameter_list|()
block|{
return|return
operator|(
name|Boolean
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_SKIP_READ
argument_list|)
return|;
block|}
comment|/**      * This is the number of documents stored in the {@link Yard} at once. During      * indexing {@link Representation} are created based on the RDF data of the      * configured RDF source files. As soon as chink size Representations are      * created they are stored by a single call to {@link Yard#store(Iterable)}.      * @param size the number of {@link Representation} stored at in the {@link Yard}      * at once. Parse<code>null</code> or a value smaller equals zero to remove      * this optional configuration.      */
specifier|public
specifier|final
name|void
name|setChunkSize
parameter_list|(
name|Integer
name|size
parameter_list|)
block|{
if|if
condition|(
name|size
operator|==
literal|null
operator|||
name|size
operator|<
literal|1
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|KEY_CHUNK_SIZE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|KEY_CHUNK_SIZE
argument_list|,
name|size
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the chunk size (number of {@link Representation} sotred at once      * in the {@link Yard}.      * @return the chunk size or<code>null</code> if not present      */
specifier|public
specifier|final
name|Integer
name|getChunkSize
parameter_list|()
block|{
return|return
operator|(
name|Integer
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_CHUNK_SIZE
argument_list|)
return|;
block|}
comment|/**      * Allows to set the state if Entities without ranking information should be      * ignored by the indexer.      * @param state the state or<code>null</code> to remove the configuration      * and go with the default.      */
specifier|public
specifier|final
name|void
name|setIgnoreEntitiesWithoutRanking
parameter_list|(
name|Boolean
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
name|config
operator|.
name|remove
argument_list|(
name|KEY_IGNORE_ENTITIES_WITHOUT_ENTITY_RANKING
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|KEY_IGNORE_ENTITIES_WITHOUT_ENTITY_RANKING
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the state if entities without available ranking should be      * ignored.      * @return the state or<code>null</code> if not present      */
specifier|public
specifier|final
name|Boolean
name|getIgnoreEntitiesWithoutRanking
parameter_list|()
block|{
return|return
operator|(
name|Boolean
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_IGNORE_ENTITIES_WITHOUT_ENTITY_RANKING
argument_list|)
return|;
block|}
comment|/**      * Setter for the minimal ranking required by an entity to be processed      * by the indexer.      * @param minRanking the minimum ranking. Parsed<code>null</code> or a      * value smaller equals 0 to remove this configuration and go with the      * default.      */
specifier|public
specifier|final
name|void
name|setMinEntityRanking
parameter_list|(
name|Float
name|minRanking
parameter_list|)
block|{
if|if
condition|(
name|minRanking
operator|==
literal|null
operator|||
name|minRanking
operator|<=
literal|0
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|KEY_REQUIRED_ENTITY_RANKING
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|KEY_REQUIRED_ENTITY_RANKING
argument_list|,
name|minRanking
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * The minimum required ranking required by an entity to be indexed.      * @return the minimum required ranking or<code>null</code> if not defined      * by this configuration      */
specifier|public
specifier|final
name|Float
name|getMinEntityRanking
parameter_list|()
block|{
return|return
operator|(
name|Float
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_REQUIRED_ENTITY_RANKING
argument_list|)
return|;
block|}
comment|/**      * Setter for the ranking used for entities for them no ranking is      * available. The value need to be greater than zero to be accepted.      * @param defaultRanking the ranking used for entities without ranking      * information. Parse<code>null</code> or a value smaller equals zero to      * remove this configuration.      */
specifier|public
specifier|final
name|void
name|setDefaultEntityRanking
parameter_list|(
name|Float
name|defaultRanking
parameter_list|)
block|{
if|if
condition|(
name|defaultRanking
operator|==
literal|null
operator|||
name|defaultRanking
operator|<=
literal|0
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|defaultRanking
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|KEY_DEFAULT_ENTITY_RANKING
argument_list|,
name|defaultRanking
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the default ranking value that is used for entities no ranking      * information are available.      * @return the default ranking or<code>null</code> if not present within      * this configuration.      */
specifier|public
specifier|final
name|Float
name|getDefaultEntityRanking
parameter_list|()
block|{
return|return
operator|(
name|Float
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_DEFAULT_ENTITY_RANKING
argument_list|)
return|;
block|}
comment|/**      * Tests if a RDF source file is valid (exists, isFile and canRead) and      * if OK add them to the configuration.      * @param sourceFile the file to add      */
specifier|private
specifier|final
name|void
name|checkAndAddSrouceFile
parameter_list|(
name|File
name|sourceFile
parameter_list|)
block|{
if|if
condition|(
name|checkFile
argument_list|(
name|sourceFile
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|Set
argument_list|<
name|File
argument_list|>
name|files
init|=
operator|(
name|Set
argument_list|<
name|File
argument_list|>
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_RDF_FILES
argument_list|)
decl_stmt|;
if|if
condition|(
name|files
operator|==
literal|null
condition|)
block|{
name|files
operator|=
operator|new
name|HashSet
argument_list|<
name|File
argument_list|>
argument_list|()
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|KEY_RDF_FILES
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"> add source file "
operator|+
name|sourceFile
argument_list|)
expr_stmt|;
name|files
operator|.
name|add
argument_list|(
name|sourceFile
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Setter for the field mappings used by the indexer to create the      * {@link Representation}s based on the RDF input.<br>      * Parsd strings must represent valid field mappings.      * @param mappings A collection field mappings in the string representation.      * If<code>null</code> or an empty collection is parsed the configuration      * is removed.      */
specifier|public
specifier|final
name|void
name|setMappings
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|mappings
parameter_list|)
block|{
if|if
condition|(
name|mappings
operator|==
literal|null
operator|||
name|mappings
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|KEY_FIELD_MAPPINGS
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|KEY_FIELD_MAPPINGS
argument_list|,
name|mappings
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Setter for the field mappings that allows to parse an existing      * {@link FieldMapper} instance. Note that the string representation of all      * the {@link FieldMapping}s part of the FieldMapper will be stored within      * the configuration      * @param mapper the FieldMapper instance with the FieldMappings to be used      * for the configuration of the indexer.      */
specifier|public
specifier|final
name|void
name|setMappings
parameter_list|(
name|FieldMapper
name|mapper
parameter_list|)
block|{
if|if
condition|(
name|mapper
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|mappingStrings
init|=
name|FieldMappingUtils
operator|.
name|serialiseFieldMapper
argument_list|(
name|mapper
argument_list|)
decl_stmt|;
name|setMappings
argument_list|(
name|mappingStrings
operator|!=
literal|null
condition|?
name|Arrays
operator|.
name|asList
argument_list|(
name|mappingStrings
argument_list|)
else|:
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setMappings
argument_list|(
operator|(
name|Collection
argument_list|<
name|String
argument_list|>
operator|)
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the field mappings      * @return the field mappings or<code>null</code> if no are defined for      * this configuration.      */
specifier|public
specifier|final
name|Collection
argument_list|<
name|String
argument_list|>
name|getMappings
parameter_list|()
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|mappings
init|=
operator|(
name|Collection
argument_list|<
name|String
argument_list|>
operator|)
name|config
operator|.
name|get
argument_list|(
name|KEY_FIELD_MAPPINGS
argument_list|)
decl_stmt|;
return|return
name|mappings
operator|==
literal|null
condition|?
literal|null
else|:
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|mappings
argument_list|)
return|;
block|}
comment|/**      * checks a files against the parsed parameter      * @param file the file to check      * @param isFile if<code>true</code> than the parsed {@link File} is tested      * to be a file, otherwise it is test to be a directory      * @param exists if<code>null</code> it is indicated that the file/directory      * can bee created if necessary.<code>true</code> indicated that the parsed      * file must exist where<code>false</code> indicate that the file MUST NOT      * exists.      * @return<code>true</code> if the parsed {@link File} fulfils the stated      * requirements.      */
specifier|private
specifier|final
name|boolean
name|checkFile
parameter_list|(
name|File
name|file
parameter_list|,
name|boolean
name|isFile
parameter_list|,
name|Boolean
name|exists
parameter_list|)
block|{
comment|//exists null means that it will be created if not existence
comment|//therefore we need only to check the state if not null.
if|if
condition|(
name|exists
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
operator|!=
name|exists
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"parsed File %s does %s exist, but the other state was requested"
argument_list|,
name|file
argument_list|,
name|file
operator|.
name|exists
argument_list|()
condition|?
literal|""
else|:
literal|"not"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
comment|//in case of exists == null&& file does not exist we assume that
comment|//the File (or Directory) will be created. Therefore no further
comment|//checks are required
return|return
literal|true
return|;
block|}
comment|//else the parsed file exists -> perform the other checks
if|if
condition|(
name|file
operator|.
name|isFile
argument_list|()
operator|!=
name|isFile
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"parsed File %s is a %s but %s was requested!"
argument_list|,
name|file
argument_list|,
name|file
operator|.
name|isFile
argument_list|()
condition|?
literal|"File"
else|:
literal|"Directory"
argument_list|,
name|isFile
condition|?
literal|"File"
else|:
literal|"Directory"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
name|file
operator|.
name|canRead
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to read parsed File %s"
argument_list|,
name|file
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

