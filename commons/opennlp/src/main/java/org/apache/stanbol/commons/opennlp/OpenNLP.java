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
name|commons
operator|.
name|opennlp
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
name|lang
operator|.
name|reflect
operator|.
name|Constructor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
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
name|HashMap
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
name|opennlp
operator|.
name|tools
operator|.
name|chunker
operator|.
name|Chunker
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|chunker
operator|.
name|ChunkerME
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|chunker
operator|.
name|ChunkerModel
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|namefind
operator|.
name|NameFinderME
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|namefind
operator|.
name|TokenNameFinder
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|namefind
operator|.
name|TokenNameFinderModel
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|postag
operator|.
name|POSModel
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|postag
operator|.
name|POSTagger
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|postag
operator|.
name|POSTaggerME
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|sentdetect
operator|.
name|SentenceDetector
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|sentdetect
operator|.
name|SentenceDetectorME
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|sentdetect
operator|.
name|SentenceModel
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|tokenize
operator|.
name|SimpleTokenizer
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|tokenize
operator|.
name|Tokenizer
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|tokenize
operator|.
name|TokenizerME
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|tokenize
operator|.
name|TokenizerModel
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|util
operator|.
name|InvalidFormatException
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
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProvider
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
comment|/**  * OSGI service that let you load OpenNLP Models via the Stanbol   * {@link DataFileProvider} infrastructure. This allows users to copy models  * to the 'datafiles' directory or developer to provide models via via OSGI  * bundles.<p>  * This service also provides methods that directly return the OpenNLP component  * wrapping the model.  */
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
argument_list|(
name|value
operator|=
name|OpenNLP
operator|.
name|class
argument_list|)
specifier|public
class|class
name|OpenNLP
block|{
comment|/**      * added as link to the download location for requested model files      * Will show up in the DataFilePorivder tab in the Apache Felix Web Console      */
specifier|private
specifier|static
specifier|final
name|String
name|DOWNLOAD_ROOT
init|=
literal|"http://opennlp.sourceforge.net/models-1.5/"
decl_stmt|;
comment|/**      * The logger      */
specifier|private
specifier|final
name|Logger
name|log
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
name|Reference
specifier|private
name|DataFileProvider
name|dataFileProvider
decl_stmt|;
comment|/**      * Map holding the already built models      * TODO: change to use a WeakReferenceMap      */
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|models
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
comment|/**      * Default constructor      */
specifier|public
name|OpenNLP
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**      * Constructor intended to be used when running outside an OSGI environment      * (e.g. when used for UnitTests)      * @param dataFileProvider the dataFileProvider used to load Model data.      */
specifier|public
name|OpenNLP
parameter_list|(
name|DataFileProvider
name|dataFileProvider
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|this
operator|.
name|dataFileProvider
operator|=
name|dataFileProvider
expr_stmt|;
block|}
comment|/**      * Getter for the sentence detection model of the parsed language.       * If the model is not yet available a new one is built. The required data      * are loaded by using the {@link DataFileProvider} service.        * @param language the language      * @return the model or<code>null</code> if no model data are found      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
name|SentenceModel
name|getSentenceModel
parameter_list|(
name|String
name|language
parameter_list|)
throws|throws
name|InvalidFormatException
throws|,
name|IOException
block|{
return|return
name|initModel
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s-sent.bin"
argument_list|,
name|language
argument_list|)
argument_list|,
name|SentenceModel
operator|.
name|class
argument_list|)
return|;
block|}
comment|/**      * Getter for the sentence detector of the parsed language.       * @param language the language      * @return the model or<code>null</code> if no model data are found      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
name|SentenceDetector
name|getSentenceDetector
parameter_list|(
name|String
name|language
parameter_list|)
throws|throws
name|IOException
block|{
name|SentenceModel
name|sentModel
init|=
name|getSentenceModel
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|sentModel
operator|!=
literal|null
condition|)
block|{
return|return
operator|new
name|SentenceDetectorME
argument_list|(
name|sentModel
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"No Sentence Detection Model for language '{}'"
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Getter for the named entity finder model for the parsed entity type and language.      * If the model is not yet available a new one is built. The required data      * are loaded by using the {@link DataFileProvider} service.        * @param type the type of the named entities to find (person, organization)      * @param language the language      * @return the model or<code>null</code> if no model data are found      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
name|TokenNameFinderModel
name|getNameModel
parameter_list|(
name|String
name|type
parameter_list|,
name|String
name|language
parameter_list|)
throws|throws
name|InvalidFormatException
throws|,
name|IOException
block|{
return|return
name|initModel
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s-ner-%s.bin"
argument_list|,
name|language
argument_list|,
name|type
argument_list|)
argument_list|,
name|TokenNameFinderModel
operator|.
name|class
argument_list|)
return|;
block|}
comment|/**      * Getter for the {@link TokenNameFinder} for the parsed entity type and language.      * @param type the type of the named entities to find (person, organization)      * @param language the language      * @return the model or<code>null</code> if no model data are found      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
name|TokenNameFinder
name|getNameFinder
parameter_list|(
name|String
name|type
parameter_list|,
name|String
name|language
parameter_list|)
throws|throws
name|IOException
block|{
name|TokenNameFinderModel
name|model
init|=
name|getNameModel
argument_list|(
name|type
argument_list|,
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|model
operator|!=
literal|null
condition|)
block|{
return|return
operator|new
name|NameFinderME
argument_list|(
name|model
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"TokenNameFinder model for type {} and langauge {} not present"
argument_list|,
name|type
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Getter for the tokenizer model for the parsed language.      * If the model is not yet available a new one is built. The required data      * are loaded by using the {@link DataFileProvider} service.        * @param language the language      * @return the model or<code>null</code> if no model data are found      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
name|TokenizerModel
name|getTokenizerModel
parameter_list|(
name|String
name|language
parameter_list|)
throws|throws
name|InvalidFormatException
throws|,
name|IOException
block|{
return|return
name|initModel
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s-token.bin"
argument_list|,
name|language
argument_list|)
argument_list|,
name|TokenizerModel
operator|.
name|class
argument_list|)
return|;
block|}
comment|/**      * Getter for the Tokenizer of a given language. This first tries to      * create an {@link TokenizerME} instance if the required       * {@link TokenizerModel} for the parsed language is available. if such a      * model is not available it returns the {@link SimpleTokenizer} instance.      * @param language the language or<code>null</code> to build a       * {@link SimpleTokenizer}      * @return the {@link Tokenizer} for the parsed language.      */
specifier|public
name|Tokenizer
name|getTokenizer
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|Tokenizer
name|tokenizer
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|language
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|TokenizerModel
name|model
init|=
name|getTokenizerModel
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|model
operator|!=
literal|null
condition|)
block|{
name|tokenizer
operator|=
operator|new
name|TokenizerME
argument_list|(
name|getTokenizerModel
argument_list|(
name|language
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|InvalidFormatException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to load Tokenizer Model for "
operator|+
name|language
operator|+
literal|": "
operator|+
literal|"Will use Simple Tokenizer instead"
argument_list|,
name|e
argument_list|)
expr_stmt|;
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
literal|"Unable to load Tokenizer Model for "
operator|+
name|language
operator|+
literal|": "
operator|+
literal|"Will use Simple Tokenizer instead"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|tokenizer
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Use Simple Tokenizer for language {}"
argument_list|,
name|language
argument_list|)
expr_stmt|;
name|tokenizer
operator|=
name|SimpleTokenizer
operator|.
name|INSTANCE
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Use ME Tokenizer for language {}"
argument_list|,
name|language
argument_list|)
expr_stmt|;
block|}
return|return
name|tokenizer
return|;
block|}
comment|/**      * Getter for the "part-of-speech" model for the parsed language.      * If the model is not yet available a new one is built. The required data      * are loaded by using the {@link DataFileProvider} service.        * @param language the language      * @return the model or<code>null</code> if no model data are found      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
name|POSModel
name|getPartOfSpeachModel
parameter_list|(
name|String
name|language
parameter_list|)
throws|throws
name|IOException
throws|,
name|InvalidFormatException
block|{
comment|//typically there are two versions
comment|//we prefer the perceptron variant but if not available try to build the other
name|IOException
name|first
init|=
literal|null
decl_stmt|;
name|POSModel
name|model
decl_stmt|;
try|try
block|{
name|model
operator|=
name|initModel
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s-pos-perceptron.bin"
argument_list|,
name|language
argument_list|)
argument_list|,
name|POSModel
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|first
operator|=
name|e
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to laod preceptron based POS model for "
operator|+
name|language
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|model
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|model
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"No perceptron based POS model for language "
operator|+
name|language
operator|+
literal|"available. Will try to load maxent model"
argument_list|)
expr_stmt|;
try|try
block|{
name|model
operator|=
name|initModel
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s-pos-maxent.bin"
argument_list|,
name|language
argument_list|)
argument_list|,
name|POSModel
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
if|if
condition|(
name|first
operator|!=
literal|null
condition|)
block|{
throw|throw
name|first
throw|;
block|}
else|else
block|{
throw|throw
name|e
throw|;
block|}
block|}
block|}
return|return
name|model
return|;
block|}
comment|/**      * Getter for the "part-of-speech" tagger for the parsed language.      * @param language the language      * @return the model or<code>null</code> if no model data are found      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
name|POSTagger
name|getPartOfSpeechTagger
parameter_list|(
name|String
name|language
parameter_list|)
throws|throws
name|IOException
block|{
name|POSModel
name|posModel
init|=
name|getPartOfSpeachModel
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|posModel
operator|!=
literal|null
condition|)
block|{
return|return
operator|new
name|POSTaggerME
argument_list|(
name|posModel
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"No POS Model for language '{}'"
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Getter for the Model with the parsed type, name and properties.      * @param modelType the type of the Model (e.g. {@link ChunkerModel})      * @param modelName the name of the model file. MUST BE available via the      * {@link DataFileProvider}.      * @param properties additional properties about the model (parsed to the      * {@link DataFileProvider}. NOTE that "Description", "Model Type" and      * "Download Location" are set to default values if not defined in the      * parsed value.      * @return the loaded (or cached) model      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
parameter_list|<
name|T
parameter_list|>
name|T
name|getModel
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|modelType
parameter_list|,
name|String
name|modelName
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|properties
parameter_list|)
throws|throws
name|InvalidFormatException
throws|,
name|IOException
block|{
return|return
name|initModel
argument_list|(
name|modelName
argument_list|,
name|modelType
argument_list|,
name|properties
argument_list|)
return|;
block|}
comment|/**      * Getter for the chunker model for the parsed language.      * If the model is not yet available a new one is built. The required data      * are loaded by using the {@link DataFileProvider} service.        * @param language the language      * @return the model or<code>null</code> if no model data are present      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
name|ChunkerModel
name|getChunkerModel
parameter_list|(
name|String
name|language
parameter_list|)
throws|throws
name|InvalidFormatException
throws|,
name|IOException
block|{
return|return
name|initModel
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s-chunker.bin"
argument_list|,
name|language
argument_list|)
argument_list|,
name|ChunkerModel
operator|.
name|class
argument_list|)
return|;
block|}
comment|/**      * Getter for the {@link Chunker} for a given language      * @param language the language      * @return the {@link Chunker} or<code>null</code> if no model is present      * @throws InvalidFormatException in case the found model data are in the wrong format      * @throws IOException on any error while reading the model data      */
specifier|public
name|Chunker
name|getChunker
parameter_list|(
name|String
name|language
parameter_list|)
throws|throws
name|IOException
block|{
name|ChunkerModel
name|chunkerModel
init|=
name|getChunkerModel
argument_list|(
name|language
argument_list|)
decl_stmt|;
if|if
condition|(
name|chunkerModel
operator|!=
literal|null
condition|)
block|{
return|return
operator|new
name|ChunkerME
argument_list|(
name|chunkerModel
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"No Chunker Model for language {}"
argument_list|,
name|language
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|//    /**
comment|//     * Activates the component and re-enables all {@link DataFileProvider}s
comment|//     * previously {@link #registerModelLocation(BundleContext, String...) registered}.
comment|//     * @param context the context
comment|//     */
comment|//    @Activate
comment|//    protected void activate(ComponentContext context){
comment|//        synchronized (modelLocations) {
comment|//            for(ModelLocation modelLocation : modelLocations.values()){
comment|//                if(modelLocation.provider == null){
comment|//                    modelLocation.provider = new BundleResourceProvider(
comment|//                        modelLocation.bundleContext,
comment|//                        modelLocation.paths == null ? null : Arrays.asList(modelLocation.paths));
comment|//                } // still registered -> should never happen unless activate is called twice
comment|//            }
comment|//        }
comment|//    }
comment|//    /**
comment|//     * Deactivates this component. Deactivates all {@link DataFileProvider}s for
comment|//     * {@link #registerModelLocation(BundleContext, String...) registered}
comment|//     * locations to search for OpenNLP models and also
comment|//     * {@link Map#clear() clears} the {@link #models model cache}.
comment|//     * @param context the context
comment|//     */
comment|//    @Deactivate
comment|//    protected void deactivate(ComponentContext context){
comment|//        synchronized (modelLocations) {
comment|//            for(ModelLocation modelLocation : modelLocations.values()){
comment|//                if(modelLocation.provider != null){
comment|//                    modelLocation.provider.close();
comment|//                    modelLocation.provider = null;
comment|//                }
comment|//            }
comment|//        }
comment|//        //clear the model cache
comment|//        models.clear();
comment|//    }
comment|//    /**
comment|//     * Registers the parsed paths as locations to lookup openNLP models.<p>
comment|//     * This Method is a convenience for manually registering a
comment|//     * {@link DataFileProvider} that provides the openNLP model classes such as:
comment|//     *<pre><code>
comment|//     *    protected void activate(ComponentContext context){
comment|//     *        this.modelProvider = new BundleResourceProvider(
comment|//     *            context.getBundleContext, Arrays.asList("openNLP/models"));
comment|//     *        ...
comment|//     *    }
comment|//     *
comment|//     *    protected void deactivate(ComponentContext context){
comment|//     *        if(this.modelProvider != null){
comment|//     *            modelProvider.close();
comment|//     *            modelProvider = null;
comment|//     *        }
comment|//     *        ...
comment|//     *    }
comment|//     *</code></pre><p>
comment|//     * Note that multiple calls with the same bundleContext will cause previous
comment|//     * registration for the same {@link BundleContext} to be removed.<p>
comment|//     * {@link DataFileProvider}s created by this will be removed/added as this
comment|//     * Component is activated/deactivated. However registrations are not
comment|//     * persisted and will be gone after an restart of the OSGI environment
comment|//     * @param bundleContext The context of the bundle used to load openNLP models
comment|//     * @param searchPaths The paths used to search openNLP models (via the
comment|//     * bundles classpath).
comment|//     */
comment|//    public void registerModelLocation(BundleContext bundleContext, String...searchPaths){
comment|//        if(bundleContext == null){
comment|//            throw new IllegalArgumentException("The parsed BundleContext MUST NOT be NULL!");
comment|//        }
comment|//        String bundleSymbolicName = bundleContext.getBundle().getSymbolicName();
comment|//        synchronized (modelLocations) {
comment|//            ModelLocation current = modelLocations.get(bundleSymbolicName);
comment|//            if(current != null){
comment|//                if(Arrays.equals(searchPaths, current.paths)) {
comment|//                    log.debug("ModelLocations for Bundle {} and Paths {} already registered");
comment|//                    return;
comment|//                } else { //remove current registration
comment|//                    log.debug("remove existing ModelLocations for Bundle {} and Paths {}",
comment|//                        bundleSymbolicName,current.paths);
comment|//                    if(current.provider != null){
comment|//                        current.provider.close();
comment|//                    }
comment|//                }
comment|//            } else {
comment|//                current = new ModelLocation();
comment|//                current.bundleContext = bundleContext;
comment|//            }
comment|//            current.paths = searchPaths;
comment|//            current.provider = new BundleResourceProvider(bundleContext,
comment|//                searchPaths == null ? null : Arrays.asList(searchPaths));
comment|//            modelLocations.put(bundleSymbolicName, current);
comment|//        }
comment|//
comment|//    }
comment|//    /**
comment|//     * Removes previously registerd openNLP model locations for the parsed bundle
comment|//     * context.
comment|//     * @param bundleContext
comment|//     */
comment|//    public void unregisterModelLocation(BundleContext bundleContext){
comment|//        if(bundleContext == null){
comment|//            throw new IllegalArgumentException("The parsed BundleContext MUST NOT be NULL!");
comment|//        }
comment|//        String bundleSymbolicName = bundleContext.getBundle().getSymbolicName();
comment|//        synchronized (modelLocations) {
comment|//            ModelLocation current = modelLocations.remove(bundleSymbolicName);
comment|//            if(current != null){
comment|//                log.debug("remove modelLocation for Bundle {} and paths {}",
comment|//                    bundleSymbolicName,current.paths);
comment|//                if(current.provider != null){
comment|//                    current.provider.close();
comment|//                }
comment|//            }
comment|//        }
comment|//    }
comment|/**      * Uses generics to build models of the parsed type. The {@link #models}      * map is used to lookup already created models.      * @param<T> the type of the model to create      * @param name the name of the file with the model data      * @param modelType the class object representing the model to create      * @return the model or<code>null</code> if the model data where not found      * @throws InvalidFormatException if the model data are in an invalid format      * @throws IOException on any error while loading the model data      * @throws IllegalStateException on any Exception while creating the model      */
specifier|private
parameter_list|<
name|T
parameter_list|>
name|T
name|initModel
parameter_list|(
name|String
name|name
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|modelType
parameter_list|)
throws|throws
name|InvalidFormatException
throws|,
name|IOException
block|{
return|return
name|initModel
argument_list|(
name|name
argument_list|,
name|modelType
argument_list|,
literal|null
argument_list|)
return|;
block|}
comment|/**      * Uses generics to build models of the parsed type. The {@link #models}      * map is used to lookup already created models.      * @param<T> the type of the model to create      * @param name the name of the file with the model data      * @param modelType the class object representing the model to create      * @param modelProperties additional metadata about the requested model      * @return the model or<code>null</code> if the model data where not found      * @throws InvalidFormatException if the model data are in an invalid format      * @throws IOException on any error while loading the model data      * @throws IllegalStateException on any Exception while creating the model      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|private
parameter_list|<
name|T
parameter_list|>
name|T
name|initModel
parameter_list|(
name|String
name|name
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|modelType
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|modelProperties
parameter_list|)
throws|throws
name|InvalidFormatException
throws|,
name|IOException
block|{
name|Object
name|model
init|=
name|models
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|model
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|modelType
operator|.
name|isAssignableFrom
argument_list|(
name|model
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
return|return
operator|(
name|T
operator|)
name|model
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Incompatible Model Types for name '%s': present=%s | requested=%s"
argument_list|,
name|name
argument_list|,
name|model
operator|.
name|getClass
argument_list|()
argument_list|,
name|modelType
argument_list|)
argument_list|)
throw|;
block|}
block|}
else|else
block|{
comment|//create new model
if|if
condition|(
name|modelProperties
operator|!=
literal|null
condition|)
block|{
comment|//copy the data to avoid external modifications
name|modelProperties
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|(
name|modelProperties
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|modelProperties
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|modelProperties
operator|.
name|containsKey
argument_list|(
literal|"Description"
argument_list|)
condition|)
block|{
name|modelProperties
operator|.
name|put
argument_list|(
literal|"Description"
argument_list|,
literal|"Statistical model for OpenNLP"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|modelProperties
operator|.
name|containsKey
argument_list|(
literal|"Model Type"
argument_list|)
condition|)
block|{
name|modelProperties
operator|.
name|put
argument_list|(
literal|"Model Type"
argument_list|,
name|modelType
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|modelProperties
operator|.
name|containsKey
argument_list|(
literal|"Download Location"
argument_list|)
condition|)
block|{
name|modelProperties
operator|.
name|put
argument_list|(
literal|"Download Location"
argument_list|,
name|DOWNLOAD_ROOT
operator|+
name|name
argument_list|)
expr_stmt|;
block|}
name|InputStream
name|modelDataStream
decl_stmt|;
try|try
block|{
name|modelDataStream
operator|=
name|lookupModelStream
argument_list|(
name|name
argument_list|,
name|modelProperties
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Unable to load Resource {} via the DataFileProvider"
argument_list|,
name|name
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|modelDataStream
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Unable to load Resource {} via the DataFileProvider"
argument_list|,
name|name
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|T
name|built
decl_stmt|;
try|try
block|{
name|Constructor
argument_list|<
name|T
argument_list|>
name|constructor
decl_stmt|;
name|constructor
operator|=
name|modelType
operator|.
name|getConstructor
argument_list|(
name|InputStream
operator|.
name|class
argument_list|)
expr_stmt|;
name|built
operator|=
name|constructor
operator|.
name|newInstance
argument_list|(
name|modelDataStream
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SecurityException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to create %s for %s!"
argument_list|,
name|modelType
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|name
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to create %s for %s!"
argument_list|,
name|modelType
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|name
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to create %s for %s!"
argument_list|,
name|modelType
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|name
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InstantiationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to create %s for %s!"
argument_list|,
name|modelType
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|name
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to create %s for %s!"
argument_list|,
name|modelType
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|name
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
name|e
parameter_list|)
block|{
comment|//this indicates an exception while creating the instance
comment|//for InvalidFormatException and IO Exceptions we shall
comment|//directly throw the cause. for all others wrap the thrown one
comment|//in an IllegalStateException
name|Throwable
name|checked
init|=
name|e
operator|.
name|getCause
argument_list|()
decl_stmt|;
if|if
condition|(
name|checked
operator|instanceof
name|InvalidFormatException
condition|)
block|{
throw|throw
operator|(
name|InvalidFormatException
operator|)
name|checked
throw|;
block|}
elseif|else
if|if
condition|(
name|checked
operator|instanceof
name|IOException
condition|)
block|{
throw|throw
operator|(
name|IOException
operator|)
name|checked
throw|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to create %s for %s!"
argument_list|,
name|modelType
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|name
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
finally|finally
block|{
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|modelDataStream
argument_list|)
expr_stmt|;
block|}
name|models
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|built
argument_list|)
expr_stmt|;
return|return
name|built
return|;
block|}
block|}
comment|/**      * Lookup an openNLP data file via the {@link #dataFileProvider}      * @param modelName the name of the model      * @return the stream or<code>null</code> if not found      * @throws IOException an any error while opening the model file      */
specifier|protected
name|InputStream
name|lookupModelStream
parameter_list|(
name|String
name|modelName
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|properties
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|dataFileProvider
operator|.
name|getInputStream
argument_list|(
literal|null
argument_list|,
name|modelName
argument_list|,
name|properties
argument_list|)
return|;
block|}
comment|/**      * Remove non UTF-8 compliant characters (typically control characters) so has to avoid polluting the      * annotation graph with snippets that are not serializable as XML.      */
specifier|protected
specifier|static
name|String
name|removeNonUtf8CompliantCharacters
parameter_list|(
specifier|final
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|text
condition|)
block|{
return|return
literal|null
return|;
block|}
name|Charset
name|UTF8
init|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|byte
index|[]
name|bytes
init|=
name|text
operator|.
name|getBytes
argument_list|(
name|UTF8
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bytes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|byte
name|ch
init|=
name|bytes
index|[
name|i
index|]
decl_stmt|;
comment|// remove any characters outside the valid UTF-8 range as well as all control characters
comment|// except tabs and new lines
if|if
condition|(
operator|!
operator|(
operator|(
name|ch
operator|>
literal|31
operator|&&
name|ch
operator|<
literal|253
operator|)
operator|||
name|ch
operator|==
literal|'\t'
operator|||
name|ch
operator|==
literal|'\n'
operator|||
name|ch
operator|==
literal|'\r'
operator|)
condition|)
block|{
name|bytes
index|[
name|i
index|]
operator|=
literal|' '
expr_stmt|;
block|}
block|}
return|return
operator|new
name|String
argument_list|(
name|bytes
argument_list|,
name|UTF8
argument_list|)
return|;
block|}
block|}
end_class

end_unit

