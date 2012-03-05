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
name|ByteArrayInputStream
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
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|nu
operator|.
name|xom
operator|.
name|Attribute
import|;
end_import

begin_import
import|import
name|nu
operator|.
name|xom
operator|.
name|Builder
import|;
end_import

begin_import
import|import
name|nu
operator|.
name|xom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|nu
operator|.
name|xom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|nu
operator|.
name|xom
operator|.
name|Nodes
import|;
end_import

begin_import
import|import
name|nu
operator|.
name|xom
operator|.
name|ParsingException
import|;
end_import

begin_import
import|import
name|nu
operator|.
name|xom
operator|.
name|Serializer
import|;
end_import

begin_import
import|import
name|nu
operator|.
name|xom
operator|.
name|ValidityException
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
name|compress
operator|.
name|archivers
operator|.
name|ArchiveException
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
name|compress
operator|.
name|archivers
operator|.
name|ArchiveInputStream
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
name|compress
operator|.
name|archivers
operator|.
name|ArchiveStreamFactory
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
name|compress
operator|.
name|archivers
operator|.
name|tar
operator|.
name|TarArchiveEntry
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
name|compress
operator|.
name|archivers
operator|.
name|tar
operator|.
name|TarArchiveOutputStream
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
name|compress
operator|.
name|archivers
operator|.
name|zip
operator|.
name|ZipArchiveEntry
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
name|compress
operator|.
name|archivers
operator|.
name|zip
operator|.
name|ZipArchiveInputStream
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|Constants
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
name|site
operator|.
name|ReferencedSiteManager
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
name|api
operator|.
name|backend
operator|.
name|RDFBackend
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
name|fields
operator|.
name|FieldMapping
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

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|parser
operator|.
name|ParseException
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
name|parser
operator|.
name|RdfPathParser
import|;
end_import

begin_comment
comment|/**  * Class containing utility methods for LDPath functionalities.  *   * @author anil.sinaci  *   */
end_comment

begin_class
specifier|public
class|class
name|LDPathUtils
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|LDPathUtils
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|NS_XSD
init|=
literal|"http://www.w3.org/2001/XMLSchema#"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SOLR_CORE_PATH
init|=
literal|"solr/core/"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SOLR_TEMPLATE_NAME
init|=
literal|"template"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SOLR_TEMPLATE_ZIP
init|=
name|SOLR_TEMPLATE_NAME
operator|+
literal|".zip"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SOLR_TEMPLATE_SCHEMA
init|=
name|SOLR_TEMPLATE_NAME
operator|+
literal|"/conf/schema-template.xml"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SOLR_SCHEMA
init|=
literal|"/conf/schema.xml"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|BUFFER_SIZE
init|=
literal|8024
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|SOLR_FIELD_OPTIONS
decl_stmt|;
static|static
block|{
name|HashSet
argument_list|<
name|String
argument_list|>
name|opt
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"default"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"indexed"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"stored"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"compressed"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"compressThreshold"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"multiValued"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"omitNorms"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"omitTermFreqAndPositions"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"termVectors"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"termPositions"
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
literal|"termOffsets"
argument_list|)
expr_stmt|;
name|SOLR_FIELD_OPTIONS
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|opt
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|SOLR_COPY_FIELD_OPTION
init|=
literal|"copy"
decl_stmt|;
comment|/**      * A map mapping from XSD types to SOLR types.      */
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|xsdSolrTypeMap
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|typeMap
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
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"decimal"
argument_list|,
literal|"long"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"integer"
argument_list|,
literal|"int"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"int"
argument_list|,
literal|"int"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"long"
argument_list|,
literal|"long"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"short"
argument_list|,
literal|"int"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"double"
argument_list|,
literal|"double"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"float"
argument_list|,
literal|"float"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"dateTime"
argument_list|,
literal|"date"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"date"
argument_list|,
literal|"date"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"time"
argument_list|,
literal|"date"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"boolean"
argument_list|,
literal|"boolean"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"anyURI"
argument_list|,
literal|"uri"
argument_list|)
expr_stmt|;
name|typeMap
operator|.
name|put
argument_list|(
name|NS_XSD
operator|+
literal|"string"
argument_list|,
literal|"string"
argument_list|)
expr_stmt|;
name|xsdSolrTypeMap
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|typeMap
argument_list|)
expr_stmt|;
block|}
specifier|private
name|Bundle
name|bundle
decl_stmt|;
specifier|private
name|ReferencedSiteManager
name|referencedSiteManager
decl_stmt|;
comment|/**      * Constructor taking a {@link Bundle} parameter. This bundle is used when obtaining Solr schema template.      *       * @param bundle      *            From which the template Solr schema is obtained.      */
specifier|public
name|LDPathUtils
parameter_list|(
name|Bundle
name|bundle
parameter_list|,
name|ReferencedSiteManager
name|referencedSiteManager
parameter_list|)
block|{
name|this
operator|.
name|bundle
operator|=
name|bundle
expr_stmt|;
name|this
operator|.
name|referencedSiteManager
operator|=
name|referencedSiteManager
expr_stmt|;
block|}
comment|/**      * Return the SOLR field type for the XSD type passed as argument. The xsdType needs to be a fully      * qualified URI. If no field type is defined, will return null.      *       * @param xsdType      *            a URI identifying the XML Schema datatype      * @return      */
specifier|public
name|String
name|getSolrFieldType
parameter_list|(
name|String
name|xsdType
parameter_list|)
block|{
name|String
name|result
init|=
name|xsdSolrTypeMap
operator|.
name|get
argument_list|(
name|xsdType
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Could not find SOLR field type for type "
operator|+
name|xsdType
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
name|result
return|;
block|}
block|}
comment|/**      * Creates a {@link Reader} instance from the given program string.      *       * @param program      * @return a {@link InputStreamReader}.      * @throws LDPathException      *             if {@link Constants#DEFAULT_ENCODING} is not supported      */
specifier|public
specifier|static
name|Reader
name|constructReader
parameter_list|(
name|String
name|program
parameter_list|)
throws|throws
name|LDPathException
block|{
try|try
block|{
return|return
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|program
operator|.
name|getBytes
argument_list|(
name|Constants
operator|.
name|DEFAULT_ENCODING
argument_list|)
argument_list|)
argument_list|,
name|Constants
operator|.
name|DEFAULT_ENCODING
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
name|String
operator|.
name|format
argument_list|(
literal|"Encoding {} should be supported by the system"
argument_list|,
name|Constants
operator|.
name|DEFAULT_ENCODING
argument_list|)
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
block|}
specifier|private
name|Program
argument_list|<
name|Object
argument_list|>
name|getLDPathProgram
parameter_list|(
name|String
name|ldPathProgram
parameter_list|)
throws|throws
name|LDPathException
block|{
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
literal|"LDPath Program cannot be null."
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
name|RDFBackend
argument_list|<
name|Object
argument_list|>
name|rdfBackend
init|=
operator|new
name|SiteManagerBackend
argument_list|(
name|referencedSiteManager
argument_list|)
decl_stmt|;
name|RdfPathParser
argument_list|<
name|Object
argument_list|>
name|LDparser
init|=
operator|new
name|RdfPathParser
argument_list|<
name|Object
argument_list|>
argument_list|(
name|rdfBackend
argument_list|,
name|constructReader
argument_list|(
name|ldPathProgram
argument_list|)
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
name|LDparser
operator|.
name|parseProgram
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"Cannot parse LDPath Program"
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
return|return
name|program
return|;
block|}
comment|/**      * This method creates an {@link ArchiveInputStream} containing Solr schema configurations based on the      * provided<code>ldPathProgram</code>. All folders and files except<b>"schema-template.xml"</b> is took      * from a default Solr configuration template which is located in the resources of the bundle specified in      * the constructor of this class i.e {@link LDPathUtils}. Instead of the "schema-template" file, a      *<b>"schema.xml"</b> is created.      *       * @param coreName      *            Name of the Solr core that is used instead of template      * @param ldPathProgram      *            Program for which the Solr core will be created      * @return {@link ArchiveInputStream} containing the Solr configurations for the provided      *<code>ldPathProgram</code>      * @throws LDPathException      */
specifier|public
name|ArchiveInputStream
name|createSchemaArchive
parameter_list|(
name|String
name|coreName
parameter_list|,
name|String
name|ldPathProgram
parameter_list|)
throws|throws
name|LDPathException
block|{
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|(
name|BUFFER_SIZE
argument_list|)
decl_stmt|;
name|ArchiveStreamFactory
name|asf
init|=
operator|new
name|ArchiveStreamFactory
argument_list|()
decl_stmt|;
name|TarArchiveOutputStream
name|tarOutputStream
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tarOutputStream
operator|=
operator|(
name|TarArchiveOutputStream
operator|)
name|asf
operator|.
name|createArchiveOutputStream
argument_list|(
literal|"tar"
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ArchiveException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"Cannot create an empty tar archive"
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
try|try
block|{
name|InputStream
name|is
init|=
name|getSolrTemplateStream
argument_list|()
decl_stmt|;
name|ZipArchiveInputStream
name|zis
init|=
operator|new
name|ZipArchiveInputStream
argument_list|(
name|is
argument_list|)
decl_stmt|;
name|ZipArchiveEntry
name|ze
init|=
literal|null
decl_stmt|;
name|byte
index|[]
name|schemaFile
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|(
name|ze
operator|=
name|zis
operator|.
name|getNextZipEntry
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|SOLR_TEMPLATE_SCHEMA
operator|.
name|equals
argument_list|(
name|ze
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|schemaFile
operator|=
name|createSchemaXML
argument_list|(
name|getLDPathProgram
argument_list|(
name|ldPathProgram
argument_list|)
argument_list|,
name|IOUtils
operator|.
name|toByteArray
argument_list|(
name|zis
argument_list|)
argument_list|)
expr_stmt|;
name|TarArchiveEntry
name|te
init|=
operator|new
name|TarArchiveEntry
argument_list|(
name|coreName
operator|+
name|SOLR_SCHEMA
argument_list|)
decl_stmt|;
name|te
operator|.
name|setSize
argument_list|(
name|schemaFile
operator|.
name|length
argument_list|)
expr_stmt|;
name|tarOutputStream
operator|.
name|putArchiveEntry
argument_list|(
name|te
argument_list|)
expr_stmt|;
name|tarOutputStream
operator|.
name|write
argument_list|(
name|schemaFile
argument_list|)
expr_stmt|;
name|tarOutputStream
operator|.
name|closeArchiveEntry
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|TarArchiveEntry
name|te
init|=
operator|new
name|TarArchiveEntry
argument_list|(
name|ze
operator|.
name|getName
argument_list|()
operator|.
name|replaceAll
argument_list|(
name|SOLR_TEMPLATE_NAME
argument_list|,
name|coreName
argument_list|)
argument_list|)
decl_stmt|;
name|te
operator|.
name|setSize
argument_list|(
name|ze
operator|.
name|getSize
argument_list|()
argument_list|)
expr_stmt|;
name|tarOutputStream
operator|.
name|putArchiveEntry
argument_list|(
name|te
argument_list|)
expr_stmt|;
name|tarOutputStream
operator|.
name|write
argument_list|(
name|IOUtils
operator|.
name|toByteArray
argument_list|(
name|zis
argument_list|)
argument_list|)
expr_stmt|;
name|tarOutputStream
operator|.
name|closeArchiveEntry
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|schemaFile
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|LDPathException
argument_list|(
literal|"Schema template ZIP should include: "
operator|+
name|SOLR_TEMPLATE_SCHEMA
argument_list|)
throw|;
block|}
name|tarOutputStream
operator|.
name|finish
argument_list|()
expr_stmt|;
name|tarOutputStream
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|""
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LDPathException
argument_list|(
name|e
argument_list|)
throw|;
block|}
name|ArchiveInputStream
name|ret
decl_stmt|;
try|try
block|{
name|ret
operator|=
name|asf
operator|.
name|createArchiveInputStream
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|out
operator|.
name|toByteArray
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ArchiveException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"Cannot create a final tar archive while creating an ArchiveInputStream to create a Solr core"
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
return|return
name|ret
return|;
block|}
specifier|private
name|InputStream
name|getSolrTemplateStream
parameter_list|()
throws|throws
name|LDPathException
block|{
name|String
name|solrCorePath
init|=
name|SOLR_CORE_PATH
decl_stmt|;
if|if
condition|(
operator|!
name|solrCorePath
operator|.
name|endsWith
argument_list|(
name|File
operator|.
name|separator
argument_list|)
condition|)
name|solrCorePath
operator|+=
name|File
operator|.
name|separator
expr_stmt|;
name|String
name|templateZip
init|=
name|solrCorePath
operator|+
name|SOLR_TEMPLATE_ZIP
decl_stmt|;
name|URL
name|resource
init|=
name|bundle
operator|.
name|getEntry
argument_list|(
name|templateZip
argument_list|)
decl_stmt|;
name|InputStream
name|is
init|=
literal|null
decl_stmt|;
try|try
block|{
name|is
operator|=
name|resource
operator|!=
literal|null
condition|?
name|resource
operator|.
name|openStream
argument_list|()
else|:
literal|null
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
literal|"Cannot open input stream on URL resource gathered from bundle.getEntry()"
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
if|if
condition|(
name|is
operator|==
literal|null
condition|)
block|{
name|String
name|msg
init|=
literal|"Solr Template ZIP cannot be found in:"
operator|+
name|templateZip
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
return|return
name|is
return|;
block|}
comment|/**      * Creates<b>"schema.xml"</b> file for the Solr configurations to be created for the provided LDPath      * program. Creates<b>Solr fields</b> for each field obtained by calling {@link Program#getFields()} of      * provided<code>program</code>. By default,<i>name</i>,<i>type</i>,<i>stored</i>,<i>indexed</i> and      *<i>multiValued</i> attributes of fields are set. Furthermore, any attribute obtained from the fields of      * the program is also set if it is included in {@link LDPathUtils#SOLR_FIELD_OPTIONS}. Another      * configuration about the fields obtained from the program is {@link LDPathUtils#SOLR_COPY_FIELD_OPTION}.      * If there is a specified configuration about this field,<b>destination</b> of<b>copyField</b> element      * is set accordingly. Otherwise, the destination is set as<b>text_all</b>      *       * @param program      *            LDPath program of which fields will be obtained      * @param template      *            Solr schema template to be populated with the fields based on the provided      *<code>program</code>      * @return created template in an array of bytes.      * @throws LDPathException      */
specifier|private
name|byte
index|[]
name|createSchemaXML
parameter_list|(
name|Program
argument_list|<
name|Object
argument_list|>
name|program
parameter_list|,
name|byte
index|[]
name|template
parameter_list|)
throws|throws
name|LDPathException
block|{
name|Builder
name|xmlParser
init|=
operator|new
name|Builder
argument_list|()
decl_stmt|;
name|ByteArrayInputStream
name|is
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|template
argument_list|)
decl_stmt|;
name|Document
name|doc
init|=
literal|null
decl_stmt|;
try|try
block|{
name|doc
operator|=
name|xmlParser
operator|.
name|build
argument_list|(
name|is
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ValidityException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"SOLR schema-template is not a valid XML"
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
catch|catch
parameter_list|(
name|ParsingException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"SOLR schema-template cannot be parsed"
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
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|""
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LDPathException
argument_list|(
name|e
argument_list|)
throw|;
block|}
name|Nodes
name|fieldsNodes
init|=
name|doc
operator|.
name|query
argument_list|(
literal|"/schema/fields"
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldsNodes
operator|.
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
throw|throw
operator|new
name|LDPathException
argument_list|(
literal|"Template is an invalid SOLR schema. It should be a valid a byte array"
argument_list|)
throw|;
block|}
name|Element
name|fieldsNode
init|=
operator|(
name|Element
operator|)
name|fieldsNodes
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Element
name|schemaNode
init|=
operator|(
name|Element
operator|)
name|fieldsNode
operator|.
name|getParent
argument_list|()
decl_stmt|;
for|for
control|(
name|FieldMapping
argument_list|<
name|?
argument_list|,
name|Object
argument_list|>
name|fieldMapping
range|:
name|program
operator|.
name|getFields
argument_list|()
control|)
block|{
name|String
name|fieldName
init|=
name|fieldMapping
operator|.
name|getFieldName
argument_list|()
decl_stmt|;
name|String
name|solrType
init|=
name|getSolrFieldType
argument_list|(
name|fieldMapping
operator|.
name|getFieldType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|solrType
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"field {} has an invalid field type; ignoring field definition"
argument_list|,
name|fieldName
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Element
name|fieldElement
init|=
operator|new
name|Element
argument_list|(
literal|"field"
argument_list|)
decl_stmt|;
name|fieldElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
literal|"name"
argument_list|,
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
name|fieldElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
literal|"type"
argument_list|,
name|solrType
argument_list|)
argument_list|)
expr_stmt|;
comment|// Set the default properties
name|fieldElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
literal|"stored"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
expr_stmt|;
name|fieldElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
literal|"indexed"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
expr_stmt|;
name|fieldElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
literal|"multiValued"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Handle extra field configuration
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fieldConfig
init|=
name|fieldMapping
operator|.
name|getFieldConfig
argument_list|()
decl_stmt|;
if|if
condition|(
name|fieldConfig
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|attr
range|:
name|fieldConfig
operator|.
name|keySet
argument_list|()
control|)
block|{
if|if
condition|(
name|SOLR_FIELD_OPTIONS
operator|.
name|contains
argument_list|(
name|attr
argument_list|)
condition|)
block|{
name|fieldElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
name|attr
argument_list|,
name|fieldConfig
operator|.
name|get
argument_list|(
name|attr
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|fieldsNode
operator|.
name|appendChild
argument_list|(
name|fieldElement
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldConfig
operator|!=
literal|null
operator|&&
name|fieldConfig
operator|.
name|keySet
argument_list|()
operator|.
name|contains
argument_list|(
name|SOLR_COPY_FIELD_OPTION
argument_list|)
condition|)
block|{
name|String
index|[]
name|copyFields
init|=
name|fieldConfig
operator|.
name|get
argument_list|(
name|SOLR_COPY_FIELD_OPTION
argument_list|)
operator|.
name|split
argument_list|(
literal|",\\s*"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|copyField
range|:
name|copyFields
control|)
block|{
name|Element
name|copyElement
init|=
operator|new
name|Element
argument_list|(
literal|"copyField"
argument_list|)
decl_stmt|;
name|copyElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
literal|"source"
argument_list|,
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
name|copyElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
literal|"dest"
argument_list|,
name|copyField
argument_list|)
argument_list|)
expr_stmt|;
name|schemaNode
operator|.
name|appendChild
argument_list|(
name|copyElement
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|Element
name|copyElement
init|=
operator|new
name|Element
argument_list|(
literal|"copyField"
argument_list|)
decl_stmt|;
name|copyElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
literal|"source"
argument_list|,
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
name|copyElement
operator|.
name|addAttribute
argument_list|(
operator|new
name|Attribute
argument_list|(
literal|"dest"
argument_list|,
literal|"text_all"
argument_list|)
argument_list|)
expr_stmt|;
name|schemaNode
operator|.
name|appendChild
argument_list|(
name|copyElement
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|(
name|BUFFER_SIZE
argument_list|)
decl_stmt|;
name|Serializer
name|serializer
init|=
literal|null
decl_stmt|;
try|try
block|{
name|serializer
operator|=
operator|new
name|Serializer
argument_list|(
name|out
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|String
name|msg
init|=
literal|"Unsupported encoding exception for UTF-8 while serializing constructed schema.xml for Solr"
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
name|serializer
operator|.
name|setIndent
argument_list|(
literal|4
argument_list|)
expr_stmt|;
try|try
block|{
name|serializer
operator|.
name|write
argument_list|(
name|doc
argument_list|)
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|""
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LDPathException
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return
name|out
operator|.
name|toByteArray
argument_list|()
return|;
block|}
block|}
end_class

end_unit

