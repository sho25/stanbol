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
name|contenthub
operator|.
name|ldpath
operator|.
name|solr
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
name|io
operator|.
name|Reader
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
name|Set
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
name|solr
operator|.
name|managed
operator|.
name|ManagedSolrServer
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|ldpath
operator|.
name|LDPathException
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|ldpath
operator|.
name|LDProgramCollection
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|ldpath
operator|.
name|SemanticIndexManager
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|store
operator|.
name|StoreException
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
name|contenthub
operator|.
name|store
operator|.
name|solr
operator|.
name|manager
operator|.
name|SolrCoreManager
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
name|ldpath
operator|.
name|EntityhubLDPath
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
name|ldpath
operator|.
name|backend
operator|.
name|SiteManagerBackend
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
name|ValueFactory
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

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|exception
operator|.
name|LDPathParseException
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|model
operator|.
name|programs
operator|.
name|Program
import|;
end_import

begin_comment
comment|/**  * Implementation of the LDProgramManager which managed the LDPath Programs submitted to the system. This  * manager creates Solr core for every uploaded program and manages them along with their programs.  *   * @author anil.pacaci  * @author anil.sinaci  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|false
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|SemanticIndexManagerImpl
implements|implements
name|SemanticIndexManager
block|{
specifier|private
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SemanticIndexManagerImpl
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|String
name|DEFAULT_ROOT_PATH
init|=
literal|"datafiles/contenthub"
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|String
name|DEFAULT_FOLDER_NAME
init|=
literal|"ldpath"
decl_stmt|;
specifier|private
name|File
name|managedProgramsDir
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|nameProgramMap
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
specifier|private
name|LDPathUtils
name|ldPathUtils
decl_stmt|;
specifier|private
name|Bundle
name|bundle
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|ManagedSolrServer
name|managedSolrServer
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|SiteManager
name|referencedSiteManager
decl_stmt|;
annotation|@
name|Activate
specifier|public
name|void
name|activator
parameter_list|(
name|ComponentContext
name|cc
parameter_list|)
throws|throws
name|LDPathException
throws|,
name|IOException
block|{
name|bundle
operator|=
name|cc
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getBundle
argument_list|()
expr_stmt|;
name|ldPathUtils
operator|=
operator|new
name|LDPathUtils
argument_list|(
name|bundle
argument_list|,
name|referencedSiteManager
argument_list|)
expr_stmt|;
name|String
name|slingHome
init|=
name|cc
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"sling.home"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|slingHome
operator|.
name|endsWith
argument_list|(
name|File
operator|.
name|separator
argument_list|)
condition|)
name|slingHome
operator|+=
name|File
operator|.
name|separator
expr_stmt|;
name|managedProgramsDir
operator|=
operator|new
name|File
argument_list|(
name|slingHome
operator|+
name|DEFAULT_ROOT_PATH
argument_list|,
name|DEFAULT_FOLDER_NAME
argument_list|)
expr_stmt|;
comment|// if directory for programs does not exist, create it
if|if
condition|(
operator|!
name|managedProgramsDir
operator|.
name|exists
argument_list|()
condition|)
block|{
if|if
condition|(
name|managedProgramsDir
operator|.
name|mkdirs
argument_list|()
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Directory for programs created succesfully"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Directory for programs COULD NOT be created"
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LDPathException
argument_list|(
literal|"Directory : "
operator|+
name|managedProgramsDir
operator|.
name|getAbsolutePath
argument_list|()
operator|+
literal|" cannot be created"
argument_list|)
throw|;
block|}
block|}
comment|// means that directory for programs created succesfully or it already exists
comment|// now need to get all programs to the memory
name|File
index|[]
name|programs
init|=
name|managedProgramsDir
operator|.
name|listFiles
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|programFile
range|:
name|programs
control|)
block|{
if|if
condition|(
name|SolrCoreManager
operator|.
name|getInstance
argument_list|(
name|bundle
operator|.
name|getBundleContext
argument_list|()
argument_list|,
name|managedSolrServer
argument_list|)
operator|.
name|isManagedSolrCore
argument_list|(
name|programFile
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
comment|// consider only if the corresponding solr core exists
name|String
name|program
init|=
name|FileUtils
operator|.
name|readFileToString
argument_list|(
name|programFile
argument_list|)
decl_stmt|;
name|nameProgramMap
operator|.
name|put
argument_list|(
name|programFile
operator|.
name|getName
argument_list|()
argument_list|,
name|program
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|programFile
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|submitProgram
parameter_list|(
name|String
name|programName
parameter_list|,
name|String
name|ldPathProgram
parameter_list|)
throws|throws
name|LDPathException
block|{
if|if
condition|(
name|programName
operator|==
literal|null
operator|||
name|programName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|msg
init|=
literal|"Program name cannot be null or empty"
decl_stmt|;
name|logger
operator|.
name|error
argument_list|(
name|msg
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LDPathException
argument_list|(
name|msg
argument_list|)
throw|;
block|}
if|if
condition|(
name|ldPathProgram
operator|==
literal|null
operator|||
name|ldPathProgram
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|msg
init|=
literal|"LDPath program cannot be null or empty"
decl_stmt|;
name|logger
operator|.
name|error
argument_list|(
name|msg
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LDPathException
argument_list|(
name|msg
argument_list|)
throw|;
block|}
comment|// Checks whether there is already a program with the same name
if|if
condition|(
name|nameProgramMap
operator|.
name|containsKey
argument_list|(
name|programName
argument_list|)
condition|)
block|{
name|String
name|msg
init|=
name|String
operator|.
name|format
argument_list|(
literal|"There is already an LDProgram with name :  "
operator|+
name|programName
argument_list|)
decl_stmt|;
name|logger
operator|.
name|error
argument_list|(
name|msg
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LDPathException
argument_list|(
name|msg
argument_list|)
throw|;
block|}
try|try
block|{
name|SolrCoreManager
operator|.
name|getInstance
argument_list|(
name|bundle
operator|.
name|getBundleContext
argument_list|()
argument_list|,
name|managedSolrServer
argument_list|)
operator|.
name|createSolrCore
argument_list|(
name|programName
argument_list|,
name|ldPathUtils
operator|.
name|createSchemaArchive
argument_list|(
name|programName
argument_list|,
name|ldPathProgram
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|LDPathException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Solr Core Service could NOT initialize Solr core with name : {}"
argument_list|,
name|programName
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|StoreException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|LDPathException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|String
name|programFilePath
init|=
name|managedProgramsDir
operator|.
name|getAbsolutePath
argument_list|()
operator|+
name|File
operator|.
name|separator
operator|+
name|programName
decl_stmt|;
try|try
block|{
name|FileUtils
operator|.
name|writeStringToFile
argument_list|(
operator|new
name|File
argument_list|(
name|programFilePath
argument_list|)
argument_list|,
name|ldPathProgram
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"Cannot write the LDPath program to file. The state is inconsistent now. "
operator|+
literal|"The Solr core is created, but corresponding LDPath program does not exist."
decl_stmt|;
name|logger
operator|.
name|error
argument_list|(
name|msg
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LDPathException
argument_list|(
name|msg
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|nameProgramMap
operator|.
name|put
argument_list|(
name|programName
argument_list|,
name|ldPathProgram
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"SolrCore for the {} created succesfully and corresponding LDPath program saved "
argument_list|,
name|programName
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|submitProgram
parameter_list|(
name|String
name|programName
parameter_list|,
name|Reader
name|ldPathProgramReader
parameter_list|)
throws|throws
name|LDPathException
block|{
name|String
name|ldPathProgram
init|=
literal|null
decl_stmt|;
try|try
block|{
name|ldPathProgram
operator|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|ldPathProgramReader
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"Cannot convert to String from Reader (ldPathProgramReader) in submitProgram"
decl_stmt|;
name|logger
operator|.
name|error
argument_list|(
name|msg
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LDPathException
argument_list|(
name|msg
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|submitProgram
argument_list|(
name|programName
argument_list|,
name|ldPathProgram
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getProgramByName
parameter_list|(
name|String
name|programName
parameter_list|)
block|{
if|if
condition|(
name|programName
operator|==
literal|null
operator|||
name|programName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Should be given a valid program name"
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Program name cannot be null or empty"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|nameProgramMap
operator|.
name|containsKey
argument_list|(
name|programName
argument_list|)
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"No program named {}"
argument_list|,
name|programName
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
name|nameProgramMap
operator|.
name|get
argument_list|(
name|programName
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Program
argument_list|<
name|Object
argument_list|>
name|getParsedProgramByName
parameter_list|(
name|String
name|programName
parameter_list|)
block|{
name|SiteManagerBackend
name|backend
init|=
operator|new
name|SiteManagerBackend
argument_list|(
name|referencedSiteManager
argument_list|)
decl_stmt|;
name|String
name|ldPathProgram
init|=
name|getProgramByName
argument_list|(
name|programName
argument_list|)
decl_stmt|;
name|ValueFactory
name|vf
init|=
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|EntityhubLDPath
name|ldPath
init|=
operator|new
name|EntityhubLDPath
argument_list|(
name|backend
argument_list|,
name|vf
argument_list|)
decl_stmt|;
name|Program
argument_list|<
name|Object
argument_list|>
name|program
init|=
literal|null
decl_stmt|;
try|try
block|{
name|program
operator|=
name|ldPath
operator|.
name|parseProgram
argument_list|(
name|LDPathUtils
operator|.
name|constructReader
argument_list|(
name|ldPathProgram
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|LDPathParseException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"Should never happen!!!!! Cannot parse the already stored LDPath program."
decl_stmt|;
name|logger
operator|.
name|error
argument_list|(
name|msg
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|LDPathException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"Should never happen!!!!! Cannot parse the already stored LDPath program."
decl_stmt|;
name|logger
operator|.
name|error
argument_list|(
name|msg
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|program
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|deleteProgram
parameter_list|(
name|String
name|programName
parameter_list|)
block|{
if|if
condition|(
name|isManagedProgram
argument_list|(
name|programName
argument_list|)
condition|)
block|{
name|SolrCoreManager
operator|.
name|getInstance
argument_list|(
name|bundle
operator|.
name|getBundleContext
argument_list|()
argument_list|,
name|managedSolrServer
argument_list|)
operator|.
name|deleteSolrCore
argument_list|(
name|programName
argument_list|)
expr_stmt|;
name|nameProgramMap
operator|.
name|remove
argument_list|(
name|programName
argument_list|)
expr_stmt|;
name|String
name|programFilePath
init|=
name|managedProgramsDir
operator|.
name|getAbsolutePath
argument_list|()
operator|+
name|File
operator|.
name|separator
operator|+
name|programName
decl_stmt|;
name|File
name|programFile
init|=
operator|new
name|File
argument_list|(
name|programFilePath
argument_list|)
decl_stmt|;
name|programFile
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isManagedProgram
parameter_list|(
name|String
name|programName
parameter_list|)
block|{
if|if
condition|(
name|nameProgramMap
operator|.
name|containsKey
argument_list|(
name|programName
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|LDProgramCollection
name|retrieveAllPrograms
parameter_list|()
block|{
return|return
operator|new
name|LDProgramCollection
argument_list|(
name|nameProgramMap
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|executeProgram
parameter_list|(
name|String
name|programName
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|contexts
parameter_list|)
throws|throws
name|LDPathException
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|results
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|SiteManagerBackend
name|backend
init|=
operator|new
name|SiteManagerBackend
argument_list|(
name|referencedSiteManager
argument_list|)
decl_stmt|;
name|String
name|ldPathProgram
init|=
name|getProgramByName
argument_list|(
name|programName
argument_list|)
decl_stmt|;
name|ValueFactory
name|vf
init|=
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|EntityhubLDPath
name|ldPath
init|=
operator|new
name|EntityhubLDPath
argument_list|(
name|backend
argument_list|,
name|vf
argument_list|)
decl_stmt|;
name|Program
argument_list|<
name|Object
argument_list|>
name|program
init|=
literal|null
decl_stmt|;
try|try
block|{
name|program
operator|=
name|ldPath
operator|.
name|parseProgram
argument_list|(
name|LDPathUtils
operator|.
name|constructReader
argument_list|(
name|ldPathProgram
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|LDPathParseException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Should never happen!!!!!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyMap
argument_list|()
return|;
block|}
name|Representation
name|representation
decl_stmt|;
for|for
control|(
name|String
name|context
range|:
name|contexts
control|)
block|{
name|representation
operator|=
name|ldPath
operator|.
name|execute
argument_list|(
name|vf
operator|.
name|createReference
argument_list|(
name|context
argument_list|)
argument_list|,
name|program
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|String
argument_list|>
name|fieldNames
init|=
name|representation
operator|.
name|getFieldNames
argument_list|()
decl_stmt|;
while|while
condition|(
name|fieldNames
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|fieldName
init|=
name|fieldNames
operator|.
name|next
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Object
argument_list|>
name|valueIterator
init|=
name|representation
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Object
argument_list|>
name|values
init|=
operator|new
name|HashSet
argument_list|<
name|Object
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|valueIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|values
operator|.
name|add
argument_list|(
name|valueIterator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|results
operator|.
name|containsKey
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|Collection
argument_list|<
name|Object
argument_list|>
name|resultCollection
init|=
operator|(
name|Collection
argument_list|<
name|Object
argument_list|>
operator|)
name|results
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|Object
argument_list|>
name|tmpCol
init|=
operator|(
name|Collection
argument_list|<
name|Object
argument_list|>
operator|)
name|values
decl_stmt|;
for|for
control|(
name|Object
name|o
range|:
name|tmpCol
control|)
block|{
name|resultCollection
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|results
operator|.
name|put
argument_list|(
name|fieldName
argument_list|,
name|values
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|results
return|;
block|}
block|}
end_class

end_unit

