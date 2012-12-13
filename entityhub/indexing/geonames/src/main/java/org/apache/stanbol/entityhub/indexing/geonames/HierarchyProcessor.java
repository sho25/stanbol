begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|geonames
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
name|IOException
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
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|Enumeration
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
name|zip
operator|.
name|ZipEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|ZipFile
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
name|indexing
operator|.
name|core
operator|.
name|EntityProcessor
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
name|core
operator|.
name|config
operator|.
name|IndexingConfig
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
specifier|public
class|class
name|HierarchyProcessor
implements|implements
name|EntityProcessor
block|{
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_ADMIN1
init|=
literal|"admin1"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_ADMIN2
init|=
literal|"admin2"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_HIERARCHY
init|=
literal|"hierarchy"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_COUNTRY_INFO
init|=
literal|"country-info"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_ADMIN1_FILE
init|=
literal|"admin1CodesASCII.txt"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_ADMIN2_FILE
init|=
literal|"admin2Codes.txt"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_HIERARCHY_FILE
init|=
literal|"hierarchy.zip"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_COUNTRY_INFO_FILE
init|=
literal|"countryInfo.txt"
decl_stmt|;
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|HierarchyProcessor
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|File
name|countryInfoFile
decl_stmt|;
specifier|private
name|List
argument_list|<
name|File
argument_list|>
name|adminCodesFiles
decl_stmt|;
specifier|private
name|File
name|hierarchyFile
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|adminCode2featureId
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|Integer
argument_list|,
name|Collection
argument_list|<
name|Integer
argument_list|>
argument_list|>
name|parentFeature
init|=
operator|new
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|Collection
argument_list|<
name|Integer
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|Integer
argument_list|,
name|Collection
argument_list|<
name|Integer
argument_list|>
argument_list|>
name|adminParentFeature
init|=
operator|new
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|Collection
argument_list|<
name|Integer
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|countryCode2featureId
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|IndexingConfig
name|indexingConfig
decl_stmt|;
specifier|private
specifier|final
name|ValueFactory
name|vf
init|=
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|COUNTRY_ID_INDEX
init|=
literal|17
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
block|{
name|indexingConfig
operator|=
operator|(
name|IndexingConfig
operator|)
name|config
operator|.
name|get
argument_list|(
name|IndexingConfig
operator|.
name|KEY_INDEXING_CONFIG
argument_list|)
expr_stmt|;
name|adminCodesFiles
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|getConfiguredFile
argument_list|(
name|config
argument_list|,
name|PARAM_ADMIN1
argument_list|,
name|DEFAULT_ADMIN1_FILE
argument_list|)
argument_list|,
name|getConfiguredFile
argument_list|(
name|config
argument_list|,
name|PARAM_ADMIN2
argument_list|,
name|DEFAULT_ADMIN2_FILE
argument_list|)
argument_list|)
expr_stmt|;
name|hierarchyFile
operator|=
name|getConfiguredFile
argument_list|(
name|config
argument_list|,
name|PARAM_HIERARCHY
argument_list|,
name|DEFAULT_HIERARCHY_FILE
argument_list|)
expr_stmt|;
name|countryInfoFile
operator|=
name|getConfiguredFile
argument_list|(
name|config
argument_list|,
name|PARAM_COUNTRY_INFO
argument_list|,
name|DEFAULT_COUNTRY_INFO_FILE
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param value      */
specifier|private
name|File
name|getConfiguredFile
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|,
name|String
name|param
parameter_list|,
name|String
name|defaultValue
parameter_list|)
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|param
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
comment|//if not set use the default
name|value
operator|=
name|GeonamesConstants
operator|.
name|DEFAULT_SOURCE_FOLDER_NAME
operator|+
name|defaultValue
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"No Geonames.org Admin1 code file configured use the default: {}"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
name|File
name|file
init|=
name|indexingConfig
operator|.
name|getSourceFile
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|file
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|needsInitialisation
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|initialise
parameter_list|()
block|{
for|for
control|(
name|File
name|af
range|:
name|adminCodesFiles
control|)
block|{
if|if
condition|(
operator|!
name|af
operator|.
name|isFile
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The configured AdminCodes file "
operator|+
name|af
operator|+
literal|"does not exist. Change the configureation "
operator|+
literal|"or copy the file to this location!"
argument_list|)
throw|;
block|}
block|}
if|if
condition|(
operator|!
name|hierarchyFile
operator|.
name|isFile
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The configured hierarchy data file "
operator|+
name|hierarchyFile
operator|+
literal|"does not exist. Change the configureation "
operator|+
literal|"or copy the file to this location!"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|countryInfoFile
operator|.
name|isFile
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The configured hierarchy data file "
operator|+
name|countryInfoFile
operator|+
literal|"does not exist. Change the configureation "
operator|+
literal|"or copy the file to this location!"
argument_list|)
throw|;
block|}
try|try
block|{
name|readAdminCodes
argument_list|()
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
literal|"Unable to read geonames.org administration codes"
argument_list|,
name|e
argument_list|)
throw|;
block|}
try|try
block|{
name|readHierarchy
argument_list|()
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
literal|"Unable to read geonames.org hierarchy codes"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**      * There are two sources of hierarchy in the geonames.org dumps.<p>      * First the Admin Region Codes stored in the main table in combination with      * the CountryInfo and the AdminRegion infos for the first two levels. This      * uses  the ISO country code and the additional number for linking the      * Regions. Second the Hierarchy table providing parentID, childId, [type]      * information. This uses featureIDs for linking.<p>      * This Method reads the first data source into memory. For the country      * related information it calls {@link #readCountryInfos()}.      * @throws IOException      */
specifier|private
name|void
name|readAdminCodes
parameter_list|()
throws|throws
name|IOException
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
comment|//first read adminCodes based on the countryInfos
name|int
name|lineCount
init|=
name|readCountryInfos
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|adminCodeFile
range|:
name|adminCodesFiles
control|)
block|{
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|adminCodeFile
argument_list|)
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
literal|"utf-8"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|line
operator|.
name|indexOf
argument_list|(
literal|'#'
argument_list|)
operator|!=
literal|0
operator|&&
name|line
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|//# is used as comment
name|lineCount
operator|++
expr_stmt|;
comment|//no tokenizer this time ... need only first and last column!
name|String
name|code
init|=
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|line
operator|.
name|indexOf
argument_list|(
literal|'\t'
argument_list|)
argument_list|)
decl_stmt|;
name|Integer
name|geonamesId
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|line
operator|.
name|substring
argument_list|(
name|line
operator|.
name|lastIndexOf
argument_list|(
literal|'\t'
argument_list|)
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|adminCode2featureId
operator|.
name|put
argument_list|(
name|code
argument_list|,
name|geonamesId
argument_list|)
expr_stmt|;
block|}
block|}
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
name|reader
operator|=
literal|null
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"read "
operator|+
name|lineCount
operator|+
literal|" AdminCodes in "
operator|+
operator|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
operator|)
operator|+
literal|"ms"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|int
name|readCountryInfos
parameter_list|()
throws|throws
name|IOException
block|{
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|countryInfoFile
argument_list|)
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
literal|"utf-8"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|line
decl_stmt|;
name|int
name|lineCount
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|line
operator|.
name|indexOf
argument_list|(
literal|'#'
argument_list|)
operator|!=
literal|0
operator|&&
name|line
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|//# is used as comment
name|LineTokenizer
name|t
init|=
operator|new
name|LineTokenizer
argument_list|(
name|line
argument_list|)
decl_stmt|;
name|String
name|code
init|=
literal|null
decl_stmt|;
name|Integer
name|geonamesId
init|=
literal|null
decl_stmt|;
name|int
name|i
init|=
literal|1
decl_stmt|;
for|for
control|(
init|;
name|t
operator|.
name|hasNext
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|actToken
init|=
name|t
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|i
operator|==
literal|1
condition|)
block|{
name|code
operator|=
name|actToken
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|==
name|HierarchyProcessor
operator|.
name|COUNTRY_ID_INDEX
operator|&&
name|actToken
operator|!=
literal|null
condition|)
block|{
name|geonamesId
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|actToken
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|i
operator|==
name|HierarchyProcessor
operator|.
name|COUNTRY_ID_INDEX
operator|&&
name|code
operator|!=
literal|null
operator|&&
name|geonamesId
operator|!=
literal|null
condition|)
block|{
name|adminCode2featureId
operator|.
name|put
argument_list|(
name|code
argument_list|,
name|geonamesId
argument_list|)
expr_stmt|;
name|countryCode2featureId
operator|.
name|put
argument_list|(
name|code
argument_list|,
name|geonamesId
argument_list|)
expr_stmt|;
name|lineCount
operator|++
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse countryInfo from Line "
operator|+
name|line
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
name|reader
operator|=
literal|null
expr_stmt|;
return|return
name|lineCount
return|;
block|}
comment|/**      * There are two sources of hierarchy in the geonames.org dumps.<p>      * First the Admin Region Codes stored in the main table in combination with      * the CountryInfo and the AdminRegion infos for the first two levels. This      * uses  the ISO country code and the additional number for linking the      * Regions. Second the Hierarchy table providing parentID, childId, [type]      * information. This uses featureIDs for linking.<p>      * This Method processes the second datasource and stores the child -&gt;      * parents mappings in memory. Administrative hierarchies are stored in a      * different map. Note also that also for Administrative regions there are      * some cases where a child has more than one parent.      * @throws IOException      */
specifier|private
name|void
name|readHierarchy
parameter_list|()
throws|throws
name|IOException
block|{
name|BufferedReader
name|reader
decl_stmt|;
if|if
condition|(
name|hierarchyFile
operator|.
name|getName
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".zip"
argument_list|)
condition|)
block|{
name|ZipFile
name|hierarchyArchive
decl_stmt|;
try|try
block|{
name|hierarchyArchive
operator|=
operator|new
name|ZipFile
argument_list|(
name|hierarchyFile
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|//in the init we check if this is a file, exists and we can read ...
comment|// .. so throw a runtime exception here!
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to access geonames.org DB Dump hirarchy File"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|Enumeration
argument_list|<
name|?
extends|extends
name|ZipEntry
argument_list|>
name|e
init|=
name|hierarchyArchive
operator|.
name|entries
argument_list|()
decl_stmt|;
name|ZipEntry
name|entry
init|=
literal|null
decl_stmt|;
while|while
condition|(
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|ZipEntry
name|cur
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|cur
operator|.
name|isDirectory
argument_list|()
operator|&&
name|cur
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"hierarchy.txt"
argument_list|)
condition|)
block|{
name|entry
operator|=
name|cur
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Archive with alternate Names does not contain the \"alternateNames.txt\" file!"
argument_list|)
throw|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"read hierarchy data fromArchive Entry "
operator|+
name|entry
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|reader
operator|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|hierarchyArchive
operator|.
name|getInputStream
argument_list|(
name|entry
argument_list|)
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
literal|"utf-8"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|reader
operator|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|hierarchyFile
argument_list|)
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
literal|"utf-8"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|line
decl_stmt|;
name|int
name|lineCount
init|=
literal|0
decl_stmt|;
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|lineCount
operator|++
expr_stmt|;
name|LineTokenizer
name|t
init|=
operator|new
name|LineTokenizer
argument_list|(
name|line
argument_list|)
decl_stmt|;
name|Integer
name|parent
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|t
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
name|Integer
name|child
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|t
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|type
decl_stmt|;
if|if
condition|(
name|t
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|type
operator|=
name|t
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
literal|"ADM"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|Collection
argument_list|<
name|Integer
argument_list|>
name|parents
init|=
name|adminParentFeature
operator|.
name|get
argument_list|(
name|child
argument_list|)
decl_stmt|;
if|if
condition|(
name|parents
operator|==
literal|null
condition|)
block|{
name|parents
operator|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|(
literal|1
argument_list|)
expr_stmt|;
comment|//there are only some exceptions with multiple parents
name|adminParentFeature
operator|.
name|put
argument_list|(
name|child
argument_list|,
name|parents
argument_list|)
expr_stmt|;
block|}
name|parents
operator|.
name|add
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Collection
argument_list|<
name|Integer
argument_list|>
name|parents
init|=
name|parentFeature
operator|.
name|get
argument_list|(
name|child
argument_list|)
decl_stmt|;
if|if
condition|(
name|parents
operator|==
literal|null
condition|)
block|{
name|parents
operator|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|parentFeature
operator|.
name|put
argument_list|(
name|child
argument_list|,
name|parents
argument_list|)
expr_stmt|;
block|}
name|parents
operator|.
name|add
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
block|}
name|log
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"read %d hierarchy relations in %dms"
argument_list|,
name|lineCount
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|process
parameter_list|(
name|Representation
name|source
parameter_list|)
block|{
name|Integer
name|id
init|=
name|source
operator|.
name|getFirst
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx_id
operator|.
name|toString
argument_list|()
argument_list|,
name|Integer
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The<{}> field MUST contain the integer ID!"
argument_list|,
name|GeonamesPropertyEnum
operator|.
name|idx_id
argument_list|)
expr_stmt|;
return|return
name|source
return|;
block|}
comment|//now add the parents based on the codes parsed from the main data
name|addParents
argument_list|(
name|source
argument_list|,
name|id
argument_list|,
operator|new
name|String
index|[]
block|{
name|source
operator|.
name|getFirst
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx_CC
operator|.
name|toString
argument_list|()
argument_list|,
name|String
operator|.
name|class
argument_list|)
block|,
name|source
operator|.
name|getFirst
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx_ADM1
operator|.
name|toString
argument_list|()
argument_list|,
name|String
operator|.
name|class
argument_list|)
block|,
name|source
operator|.
name|getFirst
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx_ADM2
operator|.
name|toString
argument_list|()
argument_list|,
name|String
operator|.
name|class
argument_list|)
block|,
name|source
operator|.
name|getFirst
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx_ADM3
operator|.
name|toString
argument_list|()
argument_list|,
name|String
operator|.
name|class
argument_list|)
block|,
name|source
operator|.
name|getFirst
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx_ADM4
operator|.
name|toString
argument_list|()
argument_list|,
name|String
operator|.
name|class
argument_list|)
block|}
argument_list|)
expr_stmt|;
return|return
name|source
return|;
block|}
comment|/**      * Recursive method the finds all parents and adds the childs of the current      * node (not all childs, but only those of the current tree)      * @param id the id of the lower level      * @param parents the set used to add all the parents/child mappings      */
specifier|private
name|void
name|getParents
parameter_list|(
name|Integer
name|id
parameter_list|,
name|Map
argument_list|<
name|Integer
argument_list|,
name|Collection
argument_list|<
name|Integer
argument_list|>
argument_list|>
name|parents
parameter_list|)
block|{
name|Collection
argument_list|<
name|Integer
argument_list|>
name|current
init|=
name|parentFeature
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|current
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Integer
name|parent
range|:
name|current
control|)
block|{
name|Collection
argument_list|<
name|Integer
argument_list|>
name|childs
init|=
name|parents
operator|.
name|get
argument_list|(
name|parent
argument_list|)
decl_stmt|;
if|if
condition|(
name|childs
operator|==
literal|null
condition|)
block|{
name|childs
operator|=
operator|new
name|HashSet
argument_list|<
name|Integer
argument_list|>
argument_list|()
expr_stmt|;
name|parents
operator|.
name|put
argument_list|(
name|parent
argument_list|,
name|childs
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|childs
operator|.
name|add
argument_list|(
name|id
argument_list|)
condition|)
block|{
name|getParents
argument_list|(
name|parent
argument_list|,
name|parents
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|current
operator|=
name|adminParentFeature
operator|.
name|get
argument_list|(
name|id
argument_list|)
expr_stmt|;
if|if
condition|(
name|current
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Integer
name|parent
range|:
name|current
control|)
block|{
name|Collection
argument_list|<
name|Integer
argument_list|>
name|childs
init|=
name|parents
operator|.
name|get
argument_list|(
name|parent
argument_list|)
decl_stmt|;
if|if
condition|(
name|childs
operator|==
literal|null
condition|)
block|{
name|childs
operator|=
operator|new
name|HashSet
argument_list|<
name|Integer
argument_list|>
argument_list|()
expr_stmt|;
name|parents
operator|.
name|put
argument_list|(
name|parent
argument_list|,
name|childs
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|childs
operator|.
name|add
argument_list|(
name|id
argument_list|)
condition|)
block|{
name|getParents
argument_list|(
name|parent
argument_list|,
name|parents
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
name|void
name|addParents
parameter_list|(
name|Representation
name|doc
parameter_list|,
name|Integer
name|id
parameter_list|,
name|String
index|[]
name|adminCodes
parameter_list|)
block|{
name|Integer
index|[]
name|adminIds
init|=
operator|new
name|Integer
index|[
literal|5
index|]
decl_stmt|;
comment|//now process the admin Codes (including the country at index 0)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|adminCodes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|adminCodes
index|[
name|i
index|]
operator|!=
literal|null
operator|&&
operator|!
name|adminCodes
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"00"
argument_list|)
condition|)
block|{
comment|//00 is used to indicate not known
name|StringBuilder
name|parentCode
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|i
condition|;
name|j
operator|++
control|)
block|{
name|parentCode
operator|.
name|append
argument_list|(
name|adminCodes
index|[
name|j
index|]
argument_list|)
expr_stmt|;
comment|//add all the previous
name|parentCode
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
comment|//add the seperator char
block|}
name|parentCode
operator|.
name|append
argument_list|(
name|adminCodes
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|//add the current (last) Element
name|adminIds
index|[
name|i
index|]
operator|=
name|adminCode2featureId
operator|.
name|get
argument_list|(
name|parentCode
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//might also add null!
block|}
block|}
comment|//now get the direct parents
name|Map
argument_list|<
name|Integer
argument_list|,
name|Collection
argument_list|<
name|Integer
argument_list|>
argument_list|>
name|parents
init|=
operator|new
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|Collection
argument_list|<
name|Integer
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|getParents
argument_list|(
name|id
argument_list|,
name|parents
argument_list|)
expr_stmt|;
comment|//add all parents (NOW done by the field mappings configuration)
comment|//doc.add(GeonamesPropertyEnum.gn_parentFeature.toString(), getFeatureReferences(parents.keySet()));
comment|//get admin hierarchy
name|Set
argument_list|<
name|Integer
argument_list|>
name|parentLevel
decl_stmt|;
comment|//add country
if|if
condition|(
name|adminIds
index|[
literal|0
index|]
operator|!=
literal|null
condition|)
block|{
name|doc
operator|.
name|add
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|gn_parentCountry
operator|.
name|toString
argument_list|()
argument_list|,
name|vf
operator|.
name|createReference
argument_list|(
operator|new
name|StringBuilder
argument_list|(
name|GeonamesConstants
operator|.
name|GEONAMES_RESOURCE_NS
argument_list|)
operator|.
name|append
argument_list|(
name|adminIds
index|[
literal|0
index|]
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|parentLevel
operator|=
name|Collections
operator|.
name|singleton
argument_list|(
name|adminIds
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|parentLevel
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
comment|//add the admin codes for the 4 levels
name|parentLevel
operator|=
name|addAdminLevel
argument_list|(
name|doc
argument_list|,
name|GeonamesPropertyEnum
operator|.
name|gn_parentADM1
argument_list|,
name|parents
argument_list|,
name|parentLevel
argument_list|,
name|adminIds
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|parentLevel
operator|=
name|addAdminLevel
argument_list|(
name|doc
argument_list|,
name|GeonamesPropertyEnum
operator|.
name|gn_parentADM2
argument_list|,
name|parents
argument_list|,
name|parentLevel
argument_list|,
name|adminIds
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
name|parentLevel
operator|=
name|addAdminLevel
argument_list|(
name|doc
argument_list|,
name|GeonamesPropertyEnum
operator|.
name|gn_parentADM3
argument_list|,
name|parents
argument_list|,
name|parentLevel
argument_list|,
name|adminIds
index|[
literal|3
index|]
argument_list|)
expr_stmt|;
name|parentLevel
operator|=
name|addAdminLevel
argument_list|(
name|doc
argument_list|,
name|GeonamesPropertyEnum
operator|.
name|gn_parentADM4
argument_list|,
name|parents
argument_list|,
name|parentLevel
argument_list|,
name|adminIds
index|[
literal|4
index|]
argument_list|)
expr_stmt|;
block|}
comment|/**      * This Method combines the information of<ul>      *<li> the adminIds originating form the information in the main feature table of geonames      *<li> hierarchy information originating from the hierarchy table.      *</ul>      * and combines them to the full admin regions hierarchy.<br>      * This code would be much simpler if one would trust one of the two data source.      * However first tests have shown, that both structures contain some errors!      * @param doc The doc to add the data      * @param property the property used for the level      * @param parents the parent->child mappings for the current geonames feature      * @param parentLevel the regions of the parent level (should be only one, but sometimes there are more).      *   This data are based on the hierarchy table.      * @param adminId the region as stored in the geonames main table (only available for level 1 and 2)      * @return the regions of this level (should be only one, but sometimes there are more)      */
specifier|private
name|Set
argument_list|<
name|Integer
argument_list|>
name|addAdminLevel
parameter_list|(
name|Representation
name|doc
parameter_list|,
name|GeonamesPropertyEnum
name|property
parameter_list|,
name|Map
argument_list|<
name|Integer
argument_list|,
name|Collection
argument_list|<
name|Integer
argument_list|>
argument_list|>
name|parents
parameter_list|,
name|Set
argument_list|<
name|Integer
argument_list|>
name|parentLevel
parameter_list|,
name|Integer
name|adminId
parameter_list|)
block|{
name|Set
argument_list|<
name|Integer
argument_list|>
name|currentLevel
init|=
operator|new
name|HashSet
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
comment|//first add the admin1 originating from the admin info file
if|if
condition|(
name|adminId
operator|!=
literal|null
condition|)
block|{
name|currentLevel
operator|.
name|add
argument_list|(
name|adminId
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Integer
name|parent
range|:
name|parentLevel
control|)
block|{
comment|//second add the admin1 via the childs of the country
name|Collection
argument_list|<
name|Integer
argument_list|>
name|tmp
init|=
name|parents
operator|.
name|get
argument_list|(
name|parent
argument_list|)
decl_stmt|;
if|if
condition|(
name|tmp
operator|!=
literal|null
condition|)
block|{
name|currentLevel
operator|.
name|addAll
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|currentLevel
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//now add all the adm1 we found
name|doc
operator|.
name|add
argument_list|(
name|property
operator|.
name|toString
argument_list|()
argument_list|,
name|getFeatureReferences
argument_list|(
name|currentLevel
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|currentLevel
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
comment|//write warning if there are multiple ids
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Multiple %s for ID %s (ids: %s)"
argument_list|,
name|property
operator|.
name|name
argument_list|()
argument_list|,
name|doc
operator|.
name|getId
argument_list|()
argument_list|,
name|currentLevel
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|currentLevel
return|;
block|}
specifier|private
name|Collection
argument_list|<
name|Reference
argument_list|>
name|getFeatureReferences
parameter_list|(
name|Collection
argument_list|<
name|Integer
argument_list|>
name|ids
parameter_list|)
block|{
name|List
argument_list|<
name|Reference
argument_list|>
name|refs
init|=
operator|new
name|ArrayList
argument_list|<
name|Reference
argument_list|>
argument_list|(
name|ids
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Integer
name|id
range|:
name|ids
control|)
block|{
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
block|{
name|refs
operator|.
name|add
argument_list|(
name|vf
operator|.
name|createReference
argument_list|(
operator|new
name|StringBuilder
argument_list|(
name|GeonamesConstants
operator|.
name|GEONAMES_RESOURCE_NS
argument_list|)
operator|.
name|append
argument_list|(
name|id
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|refs
return|;
block|}
block|}
end_class

end_unit
