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
name|uimalocal
package|;
end_package

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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|UUID
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|lucene
operator|.
name|analysis
operator|.
name|uima
operator|.
name|ae
operator|.
name|AEProvider
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
name|uima
operator|.
name|ae
operator|.
name|AEProviderFactory
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
name|caslight
operator|.
name|Feature
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
name|caslight
operator|.
name|FeatureStructure
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
name|caslight
operator|.
name|FeatureStructureListHolder
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
name|servicesapi
operator|.
name|Blob
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
name|servicesapi
operator|.
name|ContentItem
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
name|servicesapi
operator|.
name|EngineException
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
name|servicesapi
operator|.
name|EnhancementEngine
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
name|servicesapi
operator|.
name|InvalidContentException
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
name|servicesapi
operator|.
name|NoSuchPartException
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
name|servicesapi
operator|.
name|ServiceProperties
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
name|servicesapi
operator|.
name|helper
operator|.
name|ContentItemHelper
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
name|servicesapi
operator|.
name|impl
operator|.
name|AbstractEnhancementEngine
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|analysis_engine
operator|.
name|AnalysisEngine
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|analysis_engine
operator|.
name|AnalysisEngineProcessException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|cas
operator|.
name|FSIterator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|cas
operator|.
name|Type
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|jcas
operator|.
name|JCas
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|jcas
operator|.
name|tcas
operator|.
name|Annotation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|resource
operator|.
name|ResourceInitializationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|resource
operator|.
name|metadata
operator|.
name|TypeDescription
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
name|cm
operator|.
name|ConfigurationException
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
comment|/**  * @author Mihaly Heder  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|inherit
operator|=
literal|true
argument_list|,
name|label
operator|=
literal|"UIMA Local Enhancement Engine"
argument_list|,
name|description
operator|=
literal|"Runs UIMA Analysis Engine and retuns values."
argument_list|)
annotation|@
name|Service
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
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
name|value
operator|=
literal|"uimalocal"
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|UIMALocal
extends|extends
name|AbstractEnhancementEngine
argument_list|<
name|RuntimeException
argument_list|,
name|RuntimeException
argument_list|>
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
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
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"sourceName"
argument_list|,
name|label
operator|=
literal|"UIMA source name"
argument_list|,
name|description
operator|=
literal|"The name of this UIMA source which will be used for referring internally to the UIMA endpoint"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|UIMA_SOURCENAME
init|=
literal|"stanbol.engine.uimalocal.sourcename"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"/path/to/descriptor"
argument_list|,
name|label
operator|=
literal|"UIMA descriptor file path"
argument_list|,
name|description
operator|=
literal|"The file path to the UIMA descriptor XML to load"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|UIMA_DESCRIPTOR_PATH
init|=
literal|"stanbol.engine.uimalocal.descriptorpath"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"uima.apache.org"
argument_list|,
name|label
operator|=
literal|"Content Part URI reference"
argument_list|,
name|description
operator|=
literal|"The URI Reference of the UIMA content part to be created. This content part will "
operator|+
literal|"contain Annotations from all the resources above."
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|UIMA_CONTENTPART_URIREF
init|=
literal|"stanbol.engine.uimalocal.contentpart.uriref"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|cardinality
operator|=
literal|1000
argument_list|,
name|value
operator|=
literal|"text/plain"
argument_list|,
name|label
operator|=
literal|"Supported Mime Types"
argument_list|,
name|description
operator|=
literal|"Mime Types supported by this client. This should be aligned to the capabilities of the UIMA Endpoints."
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|UIMA_SUPPORTED_MIMETYPES
init|=
literal|"stanbol.engine.uimalocal.contentpart.mimetypes"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ServiceProperties
operator|.
name|ORDERING_PRE_PROCESSING
decl_stmt|;
specifier|private
name|AEProvider
name|aeProvider
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|SUPPORTED_MIMETYPES
decl_stmt|;
specifier|private
name|String
name|uimaUri
decl_stmt|;
specifier|private
name|String
name|uimaSourceName
decl_stmt|;
specifier|private
name|String
name|uimaDescriptorPath
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|uimaTypeNames
decl_stmt|;
annotation|@
name|Override
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|super
operator|.
name|activate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|props
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|this
operator|.
name|uimaUri
operator|=
operator|(
name|String
operator|)
name|props
operator|.
name|get
argument_list|(
name|UIMA_CONTENTPART_URIREF
argument_list|)
expr_stmt|;
name|this
operator|.
name|uimaSourceName
operator|=
operator|(
name|String
operator|)
name|props
operator|.
name|get
argument_list|(
name|UIMA_SOURCENAME
argument_list|)
expr_stmt|;
name|this
operator|.
name|uimaDescriptorPath
operator|=
operator|(
name|String
operator|)
name|props
operator|.
name|get
argument_list|(
name|UIMA_DESCRIPTOR_PATH
argument_list|)
expr_stmt|;
name|SUPPORTED_MIMETYPES
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
index|[]
operator|)
name|props
operator|.
name|get
argument_list|(
name|UIMA_SUPPORTED_MIMETYPES
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|aeProvider
operator|=
name|AEProviderFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|getAEProvider
argument_list|(
name|uimaSourceName
argument_list|,
name|uimaDescriptorPath
argument_list|,
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|AnalysisEngine
name|ae
init|=
name|aeProvider
operator|.
name|getAE
argument_list|()
decl_stmt|;
name|TypeDescription
index|[]
name|aeTypes
init|=
name|ae
operator|.
name|getAnalysisEngineMetaData
argument_list|()
operator|.
name|getTypeSystem
argument_list|()
operator|.
name|getTypes
argument_list|()
decl_stmt|;
name|uimaTypeNames
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|TypeDescription
name|aeType
range|:
name|aeTypes
control|)
block|{
name|String
name|aeTypeName
init|=
name|aeType
operator|.
name|getName
argument_list|()
decl_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Configuring Analysis Engine Type:"
operator|+
name|aeTypeName
argument_list|)
expr_stmt|;
name|uimaTypeNames
operator|.
name|add
argument_list|(
name|aeTypeName
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|ResourceInitializationException
name|ex
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Cannot retrieve AE from AEProvider. "
argument_list|,
name|ex
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|uimaDescriptorPath
argument_list|,
literal|"Cannot retreive AE from AEProvider"
argument_list|,
name|ex
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|super
operator|.
name|deactivate
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
if|if
condition|(
name|ContentItemHelper
operator|.
name|getBlob
argument_list|(
name|ci
argument_list|,
name|SUPPORTED_MIMETYPES
argument_list|)
operator|!=
literal|null
condition|)
block|{
return|return
name|ENHANCE_ASYNC
return|;
block|}
return|return
name|CANNOT_ENHANCE
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
name|Entry
argument_list|<
name|IRI
argument_list|,
name|Blob
argument_list|>
name|contentPart
init|=
name|ContentItemHelper
operator|.
name|getBlob
argument_list|(
name|ci
argument_list|,
name|SUPPORTED_MIMETYPES
argument_list|)
decl_stmt|;
if|if
condition|(
name|contentPart
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No ContentPart with an supported Mimetype '"
operator|+
name|SUPPORTED_MIMETYPES
operator|+
literal|"' found for ContentItem "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
operator|+
literal|": This is also checked in the canEnhance method! -> This "
operator|+
literal|"indicated an Bug in the implementation of the "
operator|+
literal|"EnhancementJobManager!"
argument_list|)
throw|;
block|}
name|String
name|text
decl_stmt|;
try|try
block|{
name|text
operator|=
name|ContentItemHelper
operator|.
name|getText
argument_list|(
name|contentPart
operator|.
name|getValue
argument_list|()
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
name|InvalidContentException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|JCas
name|jcas
decl_stmt|;
try|try
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Processing text with UIMA AE..."
argument_list|)
expr_stmt|;
name|jcas
operator|=
name|processText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ResourceInitializationException
name|ex
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error initializing UIMA AE"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Error initializing UIMA AE"
argument_list|,
name|ex
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|AnalysisEngineProcessException
name|ex
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error running UIMA AE"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|EngineException
argument_list|(
literal|"Error running UIMA AE"
argument_list|,
name|ex
argument_list|)
throw|;
block|}
comment|//just for being sure
if|if
condition|(
name|jcas
operator|==
literal|null
condition|)
block|{
return|return;
block|}
for|for
control|(
name|String
name|typeName
range|:
name|uimaTypeNames
control|)
block|{
name|List
argument_list|<
name|FeatureStructure
argument_list|>
name|featureSetList
init|=
name|concertToCasLight
argument_list|(
name|jcas
argument_list|,
name|typeName
argument_list|)
decl_stmt|;
name|IRI
name|uimaIRI
init|=
operator|new
name|IRI
argument_list|(
name|uimaUri
argument_list|)
decl_stmt|;
name|FeatureStructureListHolder
name|holder
decl_stmt|;
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|holder
operator|=
name|ci
operator|.
name|getPart
argument_list|(
name|uimaIRI
argument_list|,
name|FeatureStructureListHolder
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e
parameter_list|)
block|{
name|holder
operator|=
operator|new
name|FeatureStructureListHolder
argument_list|()
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Adding FeatureSet List Holder content part with uri:"
operator|+
name|uimaUri
argument_list|)
expr_stmt|;
name|ci
operator|.
name|addPart
argument_list|(
name|uimaIRI
argument_list|,
name|holder
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
name|uimaUri
operator|+
literal|" content part added."
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|holder
operator|.
name|addFeatureStructureList
argument_list|(
name|uimaSourceName
argument_list|,
name|featureSetList
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
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
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getServiceProperties
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|Collections
operator|.
name|singletonMap
argument_list|(
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
operator|(
name|Object
operator|)
name|defaultOrder
argument_list|)
argument_list|)
return|;
block|}
comment|/*      * process a field value executing UIMA the CAS containing it as document      * text - From SOLR.      */
specifier|private
name|JCas
name|processText
parameter_list|(
name|String
name|textFieldValue
parameter_list|)
throws|throws
name|ResourceInitializationException
throws|,
name|AnalysisEngineProcessException
block|{
name|logger
operator|.
name|info
argument_list|(
operator|new
name|StringBuffer
argument_list|(
literal|"Analazying text"
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|/*          * get the UIMA analysis engine          */
name|AnalysisEngine
name|ae
init|=
name|aeProvider
operator|.
name|getAE
argument_list|()
decl_stmt|;
comment|/*          * create a JCas which contain the text to analyze          */
name|JCas
name|jcas
init|=
name|ae
operator|.
name|newJCas
argument_list|()
decl_stmt|;
name|jcas
operator|.
name|setDocumentText
argument_list|(
name|textFieldValue
argument_list|)
expr_stmt|;
comment|/*          * perform analysis on text field          */
name|ae
operator|.
name|process
argument_list|(
name|jcas
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
operator|new
name|StringBuilder
argument_list|(
literal|"Text processing completed"
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|jcas
return|;
block|}
specifier|private
name|List
argument_list|<
name|FeatureStructure
argument_list|>
name|concertToCasLight
parameter_list|(
name|JCas
name|jcas
parameter_list|,
name|String
name|typeName
parameter_list|)
block|{
name|List
argument_list|<
name|FeatureStructure
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|FeatureStructure
argument_list|>
argument_list|()
decl_stmt|;
name|Type
name|type
init|=
name|jcas
operator|.
name|getTypeSystem
argument_list|()
operator|.
name|getType
argument_list|(
name|typeName
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|cas
operator|.
name|Feature
argument_list|>
name|featList
init|=
name|type
operator|.
name|getFeatures
argument_list|()
decl_stmt|;
for|for
control|(
name|FSIterator
argument_list|<
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|cas
operator|.
name|FeatureStructure
argument_list|>
name|iterator
init|=
name|jcas
operator|.
name|getFSIndexRepository
argument_list|()
operator|.
name|getAllIndexedFS
argument_list|(
name|type
argument_list|)
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|cas
operator|.
name|FeatureStructure
name|casFs
init|=
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|logger
operator|.
name|debug
argument_list|(
literal|"Processing UIMA CAS FeatureSet:"
operator|+
name|casFs
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|FeatureStructure
name|newFs
init|=
operator|new
name|FeatureStructure
argument_list|(
name|UUID
operator|.
name|randomUUID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|type
operator|.
name|getShortName
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|cas
operator|.
name|Feature
name|casF
range|:
name|featList
control|)
block|{
name|String
name|fName
init|=
name|casF
operator|.
name|getShortName
argument_list|()
decl_stmt|;
name|logger
operator|.
name|debug
argument_list|(
literal|"Feature Name:"
operator|+
name|fName
argument_list|)
expr_stmt|;
if|if
condition|(
name|casF
operator|.
name|getRange
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"uima.cas.Sofa"
argument_list|)
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|casF
operator|.
name|getRange
argument_list|()
operator|.
name|isPrimitive
argument_list|()
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"Getting primitive value..."
argument_list|)
expr_stmt|;
if|if
condition|(
name|casF
operator|.
name|getRange
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"uima.cas.String"
argument_list|)
condition|)
block|{
name|String
name|fVal
init|=
name|casFs
operator|.
name|getStringValue
argument_list|(
name|casF
argument_list|)
decl_stmt|;
name|newFs
operator|.
name|addFeature
argument_list|(
operator|new
name|Feature
argument_list|<
name|String
argument_list|>
argument_list|(
name|fName
argument_list|,
name|fVal
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|casF
operator|.
name|getRange
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"uima.cas.Integer"
argument_list|)
condition|)
block|{
name|int
name|fVal
init|=
name|casFs
operator|.
name|getIntValue
argument_list|(
name|casF
argument_list|)
decl_stmt|;
name|newFs
operator|.
name|addFeature
argument_list|(
operator|new
name|Feature
argument_list|<
name|Integer
argument_list|>
argument_list|(
name|fName
argument_list|,
name|fVal
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|casF
operator|.
name|getRange
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"uima.cas.Short"
argument_list|)
condition|)
block|{
name|short
name|fVal
init|=
name|casFs
operator|.
name|getShortValue
argument_list|(
name|casF
argument_list|)
decl_stmt|;
name|newFs
operator|.
name|addFeature
argument_list|(
operator|new
name|Feature
argument_list|<
name|Integer
argument_list|>
argument_list|(
name|fName
argument_list|,
operator|(
name|int
operator|)
name|fVal
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|casF
operator|.
name|getRange
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"uima.cas.Byte"
argument_list|)
condition|)
block|{
name|byte
name|fVal
init|=
name|casFs
operator|.
name|getByteValue
argument_list|(
name|casF
argument_list|)
decl_stmt|;
name|newFs
operator|.
name|addFeature
argument_list|(
operator|new
name|Feature
argument_list|<
name|Integer
argument_list|>
argument_list|(
name|fName
argument_list|,
operator|(
name|int
operator|)
name|fVal
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|casF
operator|.
name|getRange
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"uima.cas.Double"
argument_list|)
condition|)
block|{
name|double
name|fVal
init|=
name|casFs
operator|.
name|getDoubleValue
argument_list|(
name|casF
argument_list|)
decl_stmt|;
name|newFs
operator|.
name|addFeature
argument_list|(
operator|new
name|Feature
argument_list|<
name|Double
argument_list|>
argument_list|(
name|fName
argument_list|,
name|fVal
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|casF
operator|.
name|getRange
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"uima.cas.Float"
argument_list|)
condition|)
block|{
name|float
name|fVal
init|=
name|casFs
operator|.
name|getFloatValue
argument_list|(
name|casF
argument_list|)
decl_stmt|;
name|newFs
operator|.
name|addFeature
argument_list|(
operator|new
name|Feature
argument_list|<
name|Double
argument_list|>
argument_list|(
name|fName
argument_list|,
operator|(
name|double
operator|)
name|fVal
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Object
name|fVal
init|=
name|casFs
operator|.
name|clone
argument_list|()
decl_stmt|;
name|newFs
operator|.
name|addFeature
argument_list|(
operator|new
name|Feature
argument_list|<
name|Object
argument_list|>
argument_list|(
name|fName
argument_list|,
name|fVal
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"Getting FeatureStructure value..."
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"This client cannot handle FeatureStructure features"
argument_list|)
throw|;
block|}
if|if
condition|(
name|casFs
operator|instanceof
name|Annotation
operator|&&
literal|"coveredText"
operator|.
name|equals
argument_list|(
name|fName
argument_list|)
condition|)
block|{
name|newFs
operator|.
name|setCoveredText
argument_list|(
operator|(
operator|(
name|Annotation
operator|)
name|casFs
operator|)
operator|.
name|getCoveredText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|logger
operator|.
name|debug
argument_list|(
literal|"FeatureStructure:"
operator|+
name|newFs
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|newFs
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

