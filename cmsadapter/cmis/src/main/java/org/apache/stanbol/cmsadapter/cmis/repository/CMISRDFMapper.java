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
name|cmsadapter
operator|.
name|cmis
operator|.
name|repository
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
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigInteger
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
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|client
operator|.
name|api
operator|.
name|CmisObject
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|client
operator|.
name|api
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|client
operator|.
name|api
operator|.
name|Folder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|client
operator|.
name|api
operator|.
name|Session
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|commons
operator|.
name|PropertyIds
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|commons
operator|.
name|data
operator|.
name|ContentStream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|commons
operator|.
name|enums
operator|.
name|BaseTypeId
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|commons
operator|.
name|enums
operator|.
name|VersioningState
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|commons
operator|.
name|exceptions
operator|.
name|CmisObjectNotFoundException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|chemistry
operator|.
name|opencmis
operator|.
name|commons
operator|.
name|impl
operator|.
name|dataobjects
operator|.
name|ContentStreamImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|NonLiteral
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Triple
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|SimpleMGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|Serializer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|SupportedFormat
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
name|cmsadapter
operator|.
name|core
operator|.
name|mapping
operator|.
name|RDFBridgeHelper
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|CMSAdapterVocabulary
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFBridgeException
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFMapper
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
comment|/**  * Implementation of {@link RDFMapper} for CMIS repositories. While transforming annotated RDF data to  * repository, this class first takes root objects, i.e object having no  * {@link CMSAdapterVocabulary#CMS_OBJECT_PARENT_REF} annotations. All children of root objects are created as  * documents in the same folder with the top root object as CMIS allows setting a hierarchy with Folders only.<br>  *<br>  * For example, if an object has path /a/b/c/object path and has child objects as cobject1 and cobject2. First  * a, b, c folders are tried to be created and then all three object are created in c folder.<br>  *<br>  * Custom properties to be mapped, child and parent annotations for a document are selected from the annotated  * graph collected in a separate graph, serialized as RDF/XML and serialized RDF is set as  * {@link ContentStream} of document.  *   * @author suat  *   */
end_comment

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
specifier|public
class|class
name|CMISRDFMapper
implements|implements
name|RDFMapper
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|CMISRDFMapper
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|DOCUMENT_RDF
init|=
literal|"document_RDF"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|DOCUMENT_RDF_MIME_TYPE
init|=
literal|"text/plain"
decl_stmt|;
annotation|@
name|Reference
name|Serializer
name|serializer
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|storeRDFinRepository
parameter_list|(
name|Object
name|session
parameter_list|,
name|String
name|rootPath
parameter_list|,
name|MGraph
name|annotatedGraph
parameter_list|)
throws|throws
name|RDFBridgeException
block|{
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|rootObjects
init|=
name|RDFBridgeHelper
operator|.
name|getRootObjetsOfGraph
argument_list|(
name|annotatedGraph
argument_list|)
decl_stmt|;
for|for
control|(
name|NonLiteral
name|root
range|:
name|rootObjects
control|)
block|{
name|String
name|documentName
init|=
name|RDFBridgeHelper
operator|.
name|getResourceStringValue
argument_list|(
name|root
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|annotatedGraph
argument_list|)
decl_stmt|;
name|Folder
name|rootFolder
init|=
name|checkCreateParentNodes
argument_list|(
name|rootPath
argument_list|,
operator|(
name|Session
operator|)
name|session
argument_list|)
decl_stmt|;
name|createDocument
argument_list|(
name|rootFolder
argument_list|,
name|root
argument_list|,
name|documentName
argument_list|,
name|annotatedGraph
argument_list|,
operator|(
name|Session
operator|)
name|session
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|createDocument
parameter_list|(
name|Folder
name|parent
parameter_list|,
name|NonLiteral
name|documentURI
parameter_list|,
name|String
name|documentName
parameter_list|,
name|MGraph
name|graph
parameter_list|,
name|Session
name|session
parameter_list|)
throws|throws
name|RDFBridgeException
block|{
name|String
name|documentPath
decl_stmt|;
name|String
name|parentPath
init|=
name|parent
operator|.
name|getPath
argument_list|()
decl_stmt|;
if|if
condition|(
name|parentPath
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
name|documentPath
operator|=
name|parentPath
operator|+
name|documentName
expr_stmt|;
block|}
else|else
block|{
name|documentPath
operator|=
name|parentPath
operator|+
literal|"/"
operator|+
name|documentName
expr_stmt|;
block|}
name|Document
name|d
init|=
literal|null
decl_stmt|;
name|CmisObject
name|o
init|=
literal|null
decl_stmt|;
try|try
block|{
name|o
operator|=
name|session
operator|.
name|getObjectByPath
argument_list|(
name|documentPath
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasType
argument_list|(
name|o
argument_list|,
name|BaseTypeId
operator|.
name|CMIS_DOCUMENT
argument_list|)
condition|)
block|{
name|d
operator|=
operator|(
name|Document
operator|)
name|o
expr_stmt|;
name|d
operator|.
name|setContentStream
argument_list|(
name|getDocumentContentStream
argument_list|(
name|documentURI
argument_list|,
name|graph
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Object having path: {} does not have Folder base type. It should have Folder base type to allow create documents in it"
argument_list|,
name|documentPath
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|RDFBridgeException
argument_list|(
literal|"Existing object having path: "
operator|+
name|documentPath
operator|+
literal|" which does not have Folder base type"
argument_list|)
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|CmisObjectNotFoundException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Object having path: {} does not exists, a new one will be created"
argument_list|,
name|documentPath
argument_list|)
expr_stmt|;
name|d
operator|=
name|parent
operator|.
name|createDocument
argument_list|(
name|getProperties
argument_list|(
name|BaseTypeId
operator|.
name|CMIS_DOCUMENT
operator|.
name|value
argument_list|()
argument_list|,
name|documentName
argument_list|)
argument_list|,
name|getDocumentContentStream
argument_list|(
name|documentURI
argument_list|,
name|graph
argument_list|)
argument_list|,
name|VersioningState
operator|.
name|NONE
argument_list|)
expr_stmt|;
block|}
comment|// create child objects of root object in the same folder with parent
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|graph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PARENT_REF
argument_list|,
name|documentURI
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
name|NonLiteral
name|childSubject
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|String
name|childName
init|=
name|RDFBridgeHelper
operator|.
name|getResourceStringValue
argument_list|(
name|childSubject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|graph
argument_list|)
decl_stmt|;
name|createDocument
argument_list|(
name|parent
argument_list|,
name|childSubject
argument_list|,
name|childName
argument_list|,
name|graph
argument_list|,
name|session
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|ContentStream
name|getDocumentContentStream
parameter_list|(
name|NonLiteral
name|documentURI
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|MGraph
name|documentMGraph
init|=
name|collectedDocumentResources
argument_list|(
name|documentURI
argument_list|,
name|graph
argument_list|)
decl_stmt|;
name|ByteArrayOutputStream
name|baos
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|serializer
operator|.
name|serialize
argument_list|(
name|baos
argument_list|,
name|documentMGraph
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
expr_stmt|;
name|byte
index|[]
name|serializedGraph
init|=
name|baos
operator|.
name|toByteArray
argument_list|()
decl_stmt|;
name|InputStream
name|stream
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|baos
operator|.
name|toByteArray
argument_list|()
argument_list|)
decl_stmt|;
name|BigInteger
name|length
init|=
operator|new
name|BigInteger
argument_list|(
name|serializedGraph
operator|.
name|length
operator|+
literal|""
argument_list|)
decl_stmt|;
name|ContentStream
name|contentStream
init|=
operator|new
name|ContentStreamImpl
argument_list|(
name|DOCUMENT_RDF
argument_list|,
name|length
argument_list|,
name|DOCUMENT_RDF_MIME_TYPE
argument_list|,
name|stream
argument_list|)
decl_stmt|;
return|return
name|contentStream
return|;
block|}
specifier|private
name|MGraph
name|collectedDocumentResources
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|MGraph
name|documentMGraph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
comment|// put selected properties to the graph
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|graph
operator|.
name|filter
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_HAS_PROPERTY
argument_list|,
literal|null
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
name|UriRef
name|tempPropURI
init|=
operator|new
name|UriRef
argument_list|(
name|RDFBridgeHelper
operator|.
name|removeEndCharacters
argument_list|(
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|UriRef
name|propURI
init|=
name|RDFBridgeHelper
operator|.
name|getResourceURIValue
argument_list|(
name|tempPropURI
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PROPERTY_URI
argument_list|,
name|graph
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|propTriples
init|=
name|graph
operator|.
name|filter
argument_list|(
name|subject
argument_list|,
name|propURI
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|propTriples
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|documentMGraph
operator|.
name|add
argument_list|(
name|propTriples
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// put selected children annotations to the graph
comment|// The process below may be improved by changing RDF annotation mechanism.
name|it
operator|=
name|graph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PARENT_REF
argument_list|,
name|subject
argument_list|)
expr_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|NonLiteral
name|childSubject
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|itt
init|=
name|graph
operator|.
name|filter
argument_list|(
name|subject
argument_list|,
literal|null
argument_list|,
name|childSubject
argument_list|)
decl_stmt|;
if|if
condition|(
name|itt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|documentMGraph
operator|.
name|add
argument_list|(
name|itt
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// put parent annotations to the graph
name|it
operator|=
name|graph
operator|.
name|filter
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PARENT_REF
argument_list|,
literal|null
argument_list|)
expr_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|documentMGraph
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|documentMGraph
return|;
block|}
comment|/**      * Takes a path and tries to check nodes that forms that path. If nodes do not exist, they are created.      *       * @param rootPath      *            path in which root objects will be created or existing one will be searched      * @param session      *            session to access repository      * @return      * @throws RDFBridgeException      *             when another object which is not a folder in the specified path      */
specifier|private
name|Folder
name|checkCreateParentNodes
parameter_list|(
name|String
name|rootPath
parameter_list|,
name|Session
name|session
parameter_list|)
throws|throws
name|RDFBridgeException
block|{
name|Folder
name|f
init|=
name|session
operator|.
name|getRootFolder
argument_list|()
decl_stmt|;
name|String
index|[]
name|pathSections
init|=
name|rootPath
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
name|String
name|currentPath
init|=
literal|"/"
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|pathSections
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|folderName
init|=
name|pathSections
index|[
name|i
index|]
decl_stmt|;
name|currentPath
operator|+=
name|folderName
expr_stmt|;
name|CmisObject
name|o
decl_stmt|;
try|try
block|{
name|o
operator|=
name|session
operator|.
name|getObjectByPath
argument_list|(
name|currentPath
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasType
argument_list|(
name|o
argument_list|,
name|BaseTypeId
operator|.
name|CMIS_FOLDER
argument_list|)
condition|)
block|{
name|f
operator|=
operator|(
name|Folder
operator|)
name|o
expr_stmt|;
name|currentPath
operator|+=
literal|"/"
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Object having path: {} does not have Folder base type. It should have Folder base type to allow create documents in it"
argument_list|,
name|currentPath
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|RDFBridgeException
argument_list|(
literal|"Existing object having path: "
operator|+
name|currentPath
operator|+
literal|" which does not have Folder base type"
argument_list|)
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|CmisObjectNotFoundException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Object having path: {} does not exists, a new one will be created"
argument_list|,
name|currentPath
argument_list|)
expr_stmt|;
name|f
operator|=
name|f
operator|.
name|createFolder
argument_list|(
name|getProperties
argument_list|(
name|BaseTypeId
operator|.
name|CMIS_FOLDER
operator|.
name|value
argument_list|()
argument_list|,
name|folderName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|f
return|;
block|}
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getProperties
parameter_list|(
name|String
modifier|...
name|properties
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|propMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|propMap
operator|.
name|put
argument_list|(
name|PropertyIds
operator|.
name|OBJECT_TYPE_ID
argument_list|,
name|properties
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|propMap
operator|.
name|put
argument_list|(
name|PropertyIds
operator|.
name|NAME
argument_list|,
name|properties
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
return|return
name|propMap
return|;
block|}
specifier|private
name|boolean
name|hasType
parameter_list|(
name|CmisObject
name|o
parameter_list|,
name|BaseTypeId
name|type
parameter_list|)
block|{
return|return
name|o
operator|.
name|getBaseTypeId
argument_list|()
operator|.
name|equals
argument_list|(
name|type
argument_list|)
return|;
block|}
block|}
end_class

end_unit

