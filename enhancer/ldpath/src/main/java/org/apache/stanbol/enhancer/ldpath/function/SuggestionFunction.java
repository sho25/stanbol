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
name|ldpath
operator|.
name|function
package|;
end_package

begin_import
import|import static
name|java
operator|.
name|util
operator|.
name|Collections
operator|.
name|singletonMap
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
name|Comparator
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
operator|.
name|Entry
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
name|Resource
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
name|api
operator|.
name|functions
operator|.
name|SelectorFunction
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
name|selectors
operator|.
name|NodeSelector
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
name|transformers
operator|.
name|IntTransformer
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
name|transformers
operator|.
name|StringTransformer
import|;
end_import

begin_class
specifier|public
class|class
name|SuggestionFunction
implements|implements
name|SelectorFunction
argument_list|<
name|Resource
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|Comparator
argument_list|<
name|Entry
argument_list|<
name|Double
argument_list|,
name|Resource
argument_list|>
argument_list|>
name|SUGGESTION_COMPARATOR
init|=
operator|new
name|Comparator
argument_list|<
name|Entry
argument_list|<
name|Double
argument_list|,
name|Resource
argument_list|>
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Entry
argument_list|<
name|Double
argument_list|,
name|Resource
argument_list|>
name|e1
parameter_list|,
name|Entry
argument_list|<
name|Double
argument_list|,
name|Resource
argument_list|>
name|e2
parameter_list|)
block|{
return|return
name|e2
operator|.
name|getKey
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e1
operator|.
name|getKey
argument_list|()
argument_list|)
return|;
block|}
block|}
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|MISSING_CONFIDENCE_FIRST
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|MISSING_CONFIDENCE_FILTER
init|=
literal|0
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|MISSING_CONFIDENCE_LAST
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|DEFAULT_MISSING_CONFIDENCE_MODE
init|=
name|MISSING_CONFIDENCE_FILTER
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Double
name|MAX
init|=
name|Double
operator|.
name|valueOf
argument_list|(
name|Double
operator|.
name|POSITIVE_INFINITY
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Double
name|MIN
init|=
name|Double
operator|.
name|valueOf
argument_list|(
name|Double
operator|.
name|NEGATIVE_INFINITY
argument_list|)
decl_stmt|;
comment|//    private static final String ANNOTATION_PROCESSING_MODE_SINGLE = "single";
comment|//    private static final String ANNOTATION_PROCESSING_MODE_UNION = "union";
comment|//    private static final String DEFAULT_ANNOTATION_PROCESSING_MODE = ANNOTATION_PROCESSING_MODE_SINGLE;
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SuggestionFunction
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
specifier|private
specifier|final
name|IntTransformer
argument_list|<
name|Resource
argument_list|>
name|intTransformer
decl_stmt|;
specifier|private
specifier|final
name|StringTransformer
argument_list|<
name|Resource
argument_list|>
name|stringTransformer
decl_stmt|;
specifier|private
specifier|final
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|suggestionSelector
decl_stmt|;
specifier|private
specifier|final
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|confidenceSelector
decl_stmt|;
specifier|private
specifier|final
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|resultSelector
decl_stmt|;
specifier|public
name|SuggestionFunction
parameter_list|(
name|String
name|name
parameter_list|,
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|suggestionSelector
parameter_list|,
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|confidenceSelector
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
literal|null
argument_list|,
name|suggestionSelector
argument_list|,
name|confidenceSelector
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SuggestionFunction
parameter_list|(
name|String
name|name
parameter_list|,
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|suggestionSelector
parameter_list|,
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|confidenceSelector
parameter_list|,
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|resultSelector
parameter_list|)
block|{
name|intTransformer
operator|=
operator|new
name|IntTransformer
argument_list|<
name|Resource
argument_list|>
argument_list|()
expr_stmt|;
name|stringTransformer
operator|=
operator|new
name|StringTransformer
argument_list|<
name|Resource
argument_list|>
argument_list|()
expr_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed function name MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
if|if
condition|(
name|suggestionSelector
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The NodeSelector used to select the Suggestions for the parsed Context MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|suggestionSelector
operator|=
name|suggestionSelector
expr_stmt|;
if|if
condition|(
name|confidenceSelector
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The NodeSelector used to select the Confidence for Suggestions MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|confidenceSelector
operator|=
name|confidenceSelector
expr_stmt|;
name|this
operator|.
name|resultSelector
operator|=
name|resultSelector
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Resource
argument_list|>
name|apply
parameter_list|(
specifier|final
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|backend
parameter_list|,
name|Collection
argument_list|<
name|Resource
argument_list|>
modifier|...
name|args
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|Integer
name|limit
init|=
name|parseParamLimit
argument_list|(
name|backend
argument_list|,
name|args
argument_list|,
literal|1
argument_list|)
decl_stmt|;
comment|//        final String processingMode = parseParamProcessingMode(backend, args,2);
specifier|final
name|int
name|missingConfidenceMode
init|=
name|parseParamMissingConfidenceMode
argument_list|(
name|backend
argument_list|,
name|args
argument_list|,
literal|2
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Resource
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|Resource
argument_list|>
argument_list|()
decl_stmt|;
comment|//        if(processingMode.equals(ANNOTATION_PROCESSING_MODE_UNION)){
name|processAnnotations
argument_list|(
name|backend
argument_list|,
name|args
index|[
literal|0
index|]
argument_list|,
name|limit
argument_list|,
name|missingConfidenceMode
argument_list|,
name|result
argument_list|)
expr_stmt|;
comment|//        } else {
comment|//            for(Resource context : args[0]){
comment|//                processAnnotations(backend, singleton(context),
comment|//                    limit, missingConfidenceMode, result);
comment|//            }
comment|//        }
return|return
name|result
return|;
block|}
comment|/**      * Suggestions are selected by all Annotations returned by the parsed      * {@link #annotationSelector}.      * @param backend      * @param annotations suggestions are selected for the union of the parsed      * annotations - the {limit} most linked entities for the parsed      * list of annotations.      * @param limit the maximum number of suggestions for the parsed collection      * of annotations.      * @param missingConfidenceMode      * @param result results are added to this list.      */
specifier|private
name|void
name|processAnnotations
parameter_list|(
specifier|final
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|backend
parameter_list|,
name|Collection
argument_list|<
name|Resource
argument_list|>
name|annotations
parameter_list|,
name|Integer
name|limit
parameter_list|,
specifier|final
name|int
name|missingConfidenceMode
parameter_list|,
name|List
argument_list|<
name|Resource
argument_list|>
name|result
parameter_list|)
block|{
name|List
argument_list|<
name|Entry
argument_list|<
name|Double
argument_list|,
name|Resource
argument_list|>
argument_list|>
name|suggestions
init|=
operator|new
name|ArrayList
argument_list|<
name|Entry
argument_list|<
name|Double
argument_list|,
name|Resource
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Resource
name|annotation
range|:
name|annotations
control|)
block|{
for|for
control|(
name|Resource
name|suggestion
range|:
name|suggestionSelector
operator|.
name|select
argument_list|(
name|backend
argument_list|,
name|annotation
argument_list|)
control|)
block|{
name|Collection
argument_list|<
name|Resource
argument_list|>
name|cs
init|=
name|confidenceSelector
operator|.
name|select
argument_list|(
name|backend
argument_list|,
name|suggestion
argument_list|)
decl_stmt|;
name|Double
name|confidence
init|=
operator|!
name|cs
operator|.
name|isEmpty
argument_list|()
condition|?
name|backend
operator|.
name|doubleValue
argument_list|(
name|cs
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
else|:
name|missingConfidenceMode
operator|==
name|MISSING_CONFIDENCE_FILTER
condition|?
literal|null
else|:
name|missingConfidenceMode
operator|==
name|MISSING_CONFIDENCE_FIRST
condition|?
name|MAX
else|:
name|MIN
decl_stmt|;
if|if
condition|(
name|confidence
operator|!=
literal|null
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
name|singletonMap
argument_list|(
name|confidence
argument_list|,
name|suggestion
argument_list|)
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|suggestions
argument_list|,
name|SUGGESTION_COMPARATOR
argument_list|)
expr_stmt|;
name|int
name|resultSize
init|=
name|limit
operator|!=
literal|null
condition|?
name|Math
operator|.
name|min
argument_list|(
name|limit
argument_list|,
name|suggestions
operator|.
name|size
argument_list|()
argument_list|)
else|:
name|suggestions
operator|.
name|size
argument_list|()
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|Double
argument_list|,
name|Resource
argument_list|>
name|suggestion
range|:
name|suggestions
operator|.
name|subList
argument_list|(
literal|0
argument_list|,
name|resultSize
argument_list|)
control|)
block|{
if|if
condition|(
name|resultSelector
operator|==
literal|null
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|suggestion
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|addAll
argument_list|(
name|resultSelector
operator|.
name|select
argument_list|(
name|backend
argument_list|,
name|suggestion
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/*      * Helper Method to parse the parameter      */
comment|/**      * @param backend      * @param args      * @return      */
specifier|private
name|int
name|parseParamMissingConfidenceMode
parameter_list|(
specifier|final
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|backend
parameter_list|,
name|Collection
argument_list|<
name|Resource
argument_list|>
index|[]
name|args
parameter_list|,
name|int
name|index
parameter_list|)
block|{
specifier|final
name|int
name|missingConfidenceMode
decl_stmt|;
if|if
condition|(
name|args
operator|.
name|length
operator|>
name|index
operator|&&
operator|!
name|args
index|[
name|index
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|mode
init|=
name|stringTransformer
operator|.
name|transform
argument_list|(
name|backend
argument_list|,
name|args
index|[
name|index
index|]
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"first"
operator|.
name|equalsIgnoreCase
argument_list|(
name|mode
argument_list|)
condition|)
block|{
name|missingConfidenceMode
operator|=
name|MISSING_CONFIDENCE_FIRST
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"last"
operator|.
name|equalsIgnoreCase
argument_list|(
name|mode
argument_list|)
condition|)
block|{
name|missingConfidenceMode
operator|=
name|MISSING_CONFIDENCE_LAST
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"filter"
operator|.
name|equalsIgnoreCase
argument_list|(
name|mode
argument_list|)
condition|)
block|{
name|missingConfidenceMode
operator|=
name|MISSING_CONFIDENCE_FILTER
expr_stmt|;
block|}
else|else
block|{
name|missingConfidenceMode
operator|=
name|DEFAULT_MISSING_CONFIDENCE_MODE
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Unknown value for parameter 'missing confidence value mode' '{}'"
operator|+
literal|"(supported: 'first','last','filter') use default: 'filter')"
argument_list|,
name|mode
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|missingConfidenceMode
operator|=
name|DEFAULT_MISSING_CONFIDENCE_MODE
expr_stmt|;
block|}
return|return
name|missingConfidenceMode
return|;
block|}
comment|//    /**
comment|//     * @param backend
comment|//     * @param args
comment|//     * @return
comment|//     */
comment|//    private String parseParamProcessingMode(final RDFBackend<Resource> backend, Collection<Resource>[] args, int index) {
comment|//        final String processingMode;
comment|//        if(args.length> index&& !args[index].isEmpty()){
comment|//            String mode = stringTransformer.transform(backend, args[index].iterator().next());
comment|//            if(ANNOTATION_PROCESSING_MODE_SINGLE.equalsIgnoreCase(mode)){
comment|//                processingMode = ANNOTATION_PROCESSING_MODE_SINGLE;
comment|//            } else if(ANNOTATION_PROCESSING_MODE_UNION.equalsIgnoreCase(mode)) {
comment|//                processingMode = ANNOTATION_PROCESSING_MODE_UNION;
comment|//            } else {
comment|//                processingMode = DEFAULT_ANNOTATION_PROCESSING_MODE;
comment|//                log.warn("Unknown value for parameter 'annotation processing mode' '{}'" +
comment|//                        "(supported: 'single','union') default: 'single')",mode);
comment|//            }
comment|//        } else {
comment|//            processingMode = DEFAULT_ANNOTATION_PROCESSING_MODE;
comment|//        }
comment|//        return processingMode;
comment|//    }
comment|/**      * @param backend      * @param args      * @return      */
specifier|private
name|Integer
name|parseParamLimit
parameter_list|(
specifier|final
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|backend
parameter_list|,
name|Collection
argument_list|<
name|Resource
argument_list|>
index|[]
name|args
parameter_list|,
name|int
name|index
parameter_list|)
block|{
name|Integer
name|limit
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|args
operator|.
name|length
operator|>
name|index
operator|&&
operator|!
name|args
index|[
name|index
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Resource
name|value
init|=
name|args
index|[
name|index
index|]
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
name|limit
operator|=
name|intTransformer
operator|.
name|transform
argument_list|(
name|backend
argument_list|,
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
name|limit
operator|<
literal|1
condition|)
block|{
name|limit
operator|=
literal|null
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse parameter 'limit' form the 2nd argument '{}'"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|limit
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPathExpression
parameter_list|(
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|backend
parameter_list|)
block|{
return|return
name|name
return|;
block|}
block|}
end_class

end_unit
