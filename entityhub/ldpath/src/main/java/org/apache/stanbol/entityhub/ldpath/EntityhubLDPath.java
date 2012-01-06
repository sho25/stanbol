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
name|ldpath
package|;
end_package

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
name|ValueConverterFactory
operator|.
name|AnyUriConverter
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
name|ValueConverterFactory
operator|.
name|ReferenceConverter
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
name|ValueConverterFactory
operator|.
name|TextConverter
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
name|ValueConverterFactory
operator|.
name|ValueConverter
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
name|transformer
operator|.
name|ValueConverterTransformerAdapter
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
name|defaults
operator|.
name|DataTypeEnum
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
name|defaults
operator|.
name|NamespaceEnum
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
name|Text
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
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|LDPath
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
name|transformers
operator|.
name|NodeTransformer
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
name|Configuration
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
name|DefaultConfiguration
import|;
end_import

begin_comment
comment|/**  * {@link LDPath} with Entityhub specific configurations.  * In detail this registers {@link NodeTransformer} for:<ul>  *<li> {@link DataTypeEnum#Reference} returning {@link Reference} instances  *<li> xsd:anyURI also returning {@link Reference} instances  *<li> {@link DataTypeEnum#Text} returning {@link Text} instances  *<li> xsd:string also returning {@link Text} instances  *</ul><p>  * It adds also support for returning {@link Representation} instances as  * result of executing {@link Program}s on a context. This is important because  * it allows a seamless integration of LDPath with existing Entityhub  * functionality/interfaces  *   *<p>Because there is currently  * no way to get the LDPath parser to instantiate an extension of {@link Program}  * this feature is currently implemented by {@link #execute(Reference, Program)}  * of this class.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|EntityhubLDPath
extends|extends
name|LDPath
argument_list|<
name|Object
argument_list|>
block|{
specifier|private
specifier|final
name|ValueFactory
name|vf
decl_stmt|;
specifier|private
specifier|final
name|RDFBackend
argument_list|<
name|Object
argument_list|>
name|backend
decl_stmt|;
comment|/**      * Creates a {@link LDPath} instance configured as used with the Entityhub.      * This means support for<ul>      *<li> namespaces defined by the {@link NamespaceEnum}      *<li> {@link NodeTransformer} for {@link DataTypeEnum#Text} and       * {@link DataTypeEnum#Reference}      *<li> and the usage of {@link Text} for<code>xsd:string</code> and      * {@link Reference} for<code>xsd:anyURI</code>      * @param backend the {@link RDFBackend}      */
specifier|public
name|EntityhubLDPath
parameter_list|(
name|RDFBackend
argument_list|<
name|Object
argument_list|>
name|backend
parameter_list|)
block|{
name|this
argument_list|(
name|backend
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a {@link LDPath} instance configured as used with the Entityhub.      * This means support for<ul>      *<li> namespaces defined by the {@link NamespaceEnum}      *<li> {@link NodeTransformer} for {@link DataTypeEnum#Text} and       * {@link DataTypeEnum#Reference}      *<li> and the usage of {@link Text} for<code>xsd:string</code> and      * {@link Reference} for<code>xsd:anyURI</code>      * @param backend the {@link RDFBackend}      * @param vf the {@link ValueFactory} or<code>null</code> to use the default.      */
specifier|public
name|EntityhubLDPath
parameter_list|(
name|RDFBackend
argument_list|<
name|Object
argument_list|>
name|backend
parameter_list|,
name|ValueFactory
name|vf
parameter_list|)
block|{
name|super
argument_list|(
name|backend
argument_list|,
operator|new
name|EntityhubConfiguration
argument_list|(
name|vf
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|vf
operator|=
name|vf
operator|==
literal|null
condition|?
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
else|:
name|vf
expr_stmt|;
name|this
operator|.
name|backend
operator|=
name|backend
expr_stmt|;
block|}
comment|/**      * Executes the parsed {@link Program} and stores the       * {@link Program#getFields() fields} in a {@link Representation}. The actual      * implementation used for the {@link Representation} depends on the      * {@link ValueFactory} of this EntityhubLDPath instance      * @param context the context      * @param program the program       * @return the {@link Representation} holding the results of the execution      * @throws IllegalArgumentException if the parsed context or the program is      *<code>null</code>      */
specifier|public
name|Representation
name|execute
parameter_list|(
name|Reference
name|context
parameter_list|,
name|Program
argument_list|<
name|Object
argument_list|>
name|program
parameter_list|)
block|{
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed context MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|program
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed program MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|Representation
name|result
init|=
name|vf
operator|.
name|createRepresentation
argument_list|(
name|context
operator|.
name|getReference
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|FieldMapping
argument_list|<
name|?
argument_list|,
name|Object
argument_list|>
name|mapping
range|:
name|program
operator|.
name|getFields
argument_list|()
control|)
block|{
name|result
operator|.
name|add
argument_list|(
name|mapping
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|mapping
operator|.
name|getValues
argument_list|(
name|backend
argument_list|,
name|context
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/**      * The default configuration for the Entityhub      * @author Rupert Westenthaler      *      */
specifier|private
specifier|static
class|class
name|EntityhubConfiguration
extends|extends
name|DefaultConfiguration
argument_list|<
name|Object
argument_list|>
block|{
specifier|public
name|EntityhubConfiguration
parameter_list|(
name|ValueFactory
name|vf
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|vf
operator|=
name|vf
operator|==
literal|null
condition|?
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
else|:
name|vf
expr_stmt|;
comment|//register special Entutyhub Transformer for
comment|// * entityhub:reference
name|ValueConverter
argument_list|<
name|Reference
argument_list|>
name|referenceConverter
init|=
operator|new
name|ReferenceConverter
argument_list|()
decl_stmt|;
name|addTransformer
argument_list|(
name|referenceConverter
operator|.
name|getDataType
argument_list|()
argument_list|,
operator|new
name|ValueConverterTransformerAdapter
argument_list|<
name|Reference
argument_list|>
argument_list|(
name|referenceConverter
argument_list|,
name|vf
argument_list|)
argument_list|)
expr_stmt|;
comment|// * xsd:anyURI
name|ValueConverter
argument_list|<
name|Reference
argument_list|>
name|uriConverter
init|=
operator|new
name|AnyUriConverter
argument_list|()
decl_stmt|;
name|addTransformer
argument_list|(
name|uriConverter
operator|.
name|getDataType
argument_list|()
argument_list|,
operator|new
name|ValueConverterTransformerAdapter
argument_list|<
name|Reference
argument_list|>
argument_list|(
name|uriConverter
argument_list|,
name|vf
argument_list|)
argument_list|)
expr_stmt|;
comment|// * entityhub:text
name|ValueConverter
argument_list|<
name|Text
argument_list|>
name|literalConverter
init|=
operator|new
name|TextConverter
argument_list|()
decl_stmt|;
name|addTransformer
argument_list|(
name|literalConverter
operator|.
name|getDataType
argument_list|()
argument_list|,
operator|new
name|ValueConverterTransformerAdapter
argument_list|<
name|Text
argument_list|>
argument_list|(
name|literalConverter
argument_list|,
name|vf
argument_list|)
argument_list|)
expr_stmt|;
comment|// xsd:string (use also the literal converter for xsd:string
name|addTransformer
argument_list|(
name|DataTypeEnum
operator|.
name|String
operator|.
name|getUri
argument_list|()
argument_list|,
operator|new
name|ValueConverterTransformerAdapter
argument_list|<
name|Text
argument_list|>
argument_list|(
name|literalConverter
argument_list|,
name|vf
argument_list|)
argument_list|)
expr_stmt|;
comment|//Register the default namespaces
for|for
control|(
name|NamespaceEnum
name|ns
range|:
name|NamespaceEnum
operator|.
name|values
argument_list|()
control|)
block|{
name|addNamespace
argument_list|(
name|ns
operator|.
name|getPrefix
argument_list|()
argument_list|,
name|ns
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

