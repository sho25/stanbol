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
name|entityhub
operator|.
name|indexing
operator|.
name|source
operator|.
name|jenatdb
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
name|io
operator|.
name|InputStream
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
name|GZIPInputStream
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
name|compressors
operator|.
name|bzip2
operator|.
name|BZip2CompressorInputStream
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
name|FilenameUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|jena
operator|.
name|riot
operator|.
name|Lang
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|jena
operator|.
name|riot
operator|.
name|RDFLanguages
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|jena
operator|.
name|riot
operator|.
name|RiotReader
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
name|source
operator|.
name|ResourceImporter
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
name|source
operator|.
name|ResourceState
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
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|tdb
operator|.
name|store
operator|.
name|DatasetGraphTDB
import|;
end_import

begin_class
specifier|public
class|class
name|RdfResourceImporter
implements|implements
name|ResourceImporter
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
name|RdfResourceImporter
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// private final DatasetGraphTDB indexingDataset;
specifier|private
specifier|final
name|DestinationTripleGraph
name|destination
decl_stmt|;
specifier|public
name|RdfResourceImporter
parameter_list|(
name|DatasetGraphTDB
name|indexingDataset
parameter_list|,
name|RdfImportFilter
name|importFilter
parameter_list|)
block|{
if|if
condition|(
name|indexingDataset
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed DatasetGraphTDB instance MUST NOT be NULL!"
argument_list|)
throw|;
block|}
comment|//this.indexingDataset = indexingDataset;
name|this
operator|.
name|destination
operator|=
operator|new
name|DestinationTripleGraph
argument_list|(
name|indexingDataset
argument_list|,
name|importFilter
argument_list|,
name|log
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|ResourceState
name|importResource
parameter_list|(
name|InputStream
name|is
parameter_list|,
name|String
name|resourceName
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|name
init|=
name|FilenameUtils
operator|.
name|getName
argument_list|(
name|resourceName
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"gz"
operator|.
name|equalsIgnoreCase
argument_list|(
name|FilenameUtils
operator|.
name|getExtension
argument_list|(
name|name
argument_list|)
argument_list|)
condition|)
block|{
name|is
operator|=
operator|new
name|GZIPInputStream
argument_list|(
name|is
argument_list|)
expr_stmt|;
name|name
operator|=
name|FilenameUtils
operator|.
name|removeExtension
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"   - from GZIP Archive"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"bz2"
operator|.
name|equalsIgnoreCase
argument_list|(
name|FilenameUtils
operator|.
name|getExtension
argument_list|(
name|name
argument_list|)
argument_list|)
condition|)
block|{
name|is
operator|=
operator|new
name|BZip2CompressorInputStream
argument_list|(
name|is
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|//use true as 2nd param (see http://s.apache.org/QbK)
name|name
operator|=
name|FilenameUtils
operator|.
name|removeExtension
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"   - from BZip2 Archive"
argument_list|)
expr_stmt|;
block|}
comment|// TODO: No Zip Files inside Zip Files supported :o( ^^
name|Lang
name|format
init|=
name|RDFLanguages
operator|.
name|filenameToLang
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|format
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"ignore File {} because of unknown extension "
argument_list|)
expr_stmt|;
return|return
name|ResourceState
operator|.
name|IGNORED
return|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"    - bulk loading File {} using Format {}"
argument_list|,
name|resourceName
argument_list|,
name|format
argument_list|)
expr_stmt|;
try|try
block|{
name|destination
operator|.
name|startBulk
argument_list|()
expr_stmt|;
name|RiotReader
operator|.
name|parse
argument_list|(
name|is
argument_list|,
name|format
argument_list|,
literal|null
argument_list|,
name|destination
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
return|return
name|ResourceState
operator|.
name|ERROR
return|;
block|}
finally|finally
block|{
name|destination
operator|.
name|finishBulk
argument_list|()
expr_stmt|;
block|}
block|}
comment|// old code - just keep it in case the above else does not support any of the below RDF formats.
comment|//        if (format == Lang.NTRIPLES) {
comment|//            BulkLoader.
comment|//            TDBLoader.load(indexingDataset, is, true);
comment|//        } else if(format == Lang.NQUADS || format == Lang.TRIG){ //quads
comment|//            TDBLoader loader = new TDBLoader();
comment|//            loader.setShowProgress(true);
comment|//            RDFSt dest = createQuad2TripleDestination();
comment|//            dest.start();
comment|//            RiotReader.parseQuads(is,format,null, dest);
comment|//            dest.finish();
comment|//        } else if (format != Lang.RDFXML) {
comment|//            // use RIOT to parse the format but with a special configuration
comment|//            // RiotReader!
comment|//            TDBLoader loader = new TDBLoader();
comment|//            loader.setShowProgress(true);
comment|//            Destination<Triple> dest = createDestination();
comment|//            dest.start();
comment|//            RiotReader.parseTriples(is, format, null, dest);
comment|//            dest.finish();
comment|//        } else { // RDFXML
comment|//            // in that case we need to use ARP
comment|//            Model model = ModelFactory.createModelForGraph(indexingDataset.getDefaultGraph());
comment|//            model.read(is, null);
comment|//        }
return|return
name|ResourceState
operator|.
name|LOADED
return|;
block|}
block|}
end_class

end_unit

