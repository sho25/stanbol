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
name|web
package|;
end_package

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
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
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
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
import|;
end_import

begin_comment
comment|/**  * Tracks all registered {@link ModelWriter} services and provides an API for  * accessing those based on the requested {@link MediaType} and native type.<p>  * See   *<a href="https://issues.apache.org/jira/browse/STANBOL-1237">STANBOL-1237</a>  * for details.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ModelWriterRegistry
block|{
comment|/**      * Getter for a sorted list of References to {@link ModelWriter} that can      * serialise Representations to the parsed {@link MediaType}. If a      * nativeType of the Representation is given {@link ModelWriter} for that      * specific type will be preferred.      * @param mediaType The {@link MediaType}. Wildcards are supported      * @param nativeType optionally the native type of the {@link Representation}      * @return A sorted collection of references to compatible {@link ModelWriter}.      * Use {@link #getService()} to receive the actual service. However note that      * such calls may return<code>null</code> if the service was deactivated in      * the meantime.      */
specifier|public
name|Collection
argument_list|<
name|ServiceReference
argument_list|>
name|getModelWriters
parameter_list|(
name|MediaType
name|mediaType
parameter_list|,
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|nativeType
parameter_list|)
function_decl|;
comment|/**      * Getter for the {@link ModelWriter} service for the parsed ServiceReference      * @param ref the {@link ServiceReference}      * @return The {@link ModelWriter} or<code>null<code> if the referenced      * service was deactivated.      */
specifier|public
name|ModelWriter
name|getService
parameter_list|(
name|ServiceReference
name|ref
parameter_list|)
function_decl|;
comment|/**      * Checks if the parsed mediaType is writeable      * @param mediaType the mediaType      * @param nativeType optionally the native type of the {@link Representation}      * @return the state      */
specifier|public
name|boolean
name|isWriteable
parameter_list|(
name|MediaType
name|mediaType
parameter_list|,
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|nativeType
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

