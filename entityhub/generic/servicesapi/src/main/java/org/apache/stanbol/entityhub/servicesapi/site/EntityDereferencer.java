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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
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

begin_comment
comment|/**  * Service used by {@link ReferencedSite} to dereference {@link Representation}  * for entity ids. Implementations of this interface are dependent on the  * service provided by the referenced site.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityDereferencer
block|{
comment|/**      * The key used to define the baseUri of the service used for the      * implementation of this interface.<br>      * This constants actually uses the value of {@link SiteConfiguration#ACCESS_URI}      */
name|String
name|ACCESS_URI
init|=
name|SiteConfiguration
operator|.
name|ACCESS_URI
decl_stmt|;
comment|/**      * The base uri used to access this site      * @return      */
name|String
name|getAccessUri
parameter_list|()
function_decl|;
comment|/**      * Whether the parsed entity ID can be dereferenced by this Dereferencer or      * not.<br>      * The implementation may not directly check if the parsed URI is present by      * a query to the site, but only check some patterns of the parsed URI.      * @param uri the URI to be checked      * @return<code>true</code> of URIs of that kind can be typically dereferenced      * by this service instance.      */
name|boolean
name|canDereference
parameter_list|(
name|String
name|uri
parameter_list|)
function_decl|;
comment|/**      * Generic getter for the data of the parsed entity id      * @param uri the entity to dereference      * @param contentType the content type of the data      * @return the data or<code>null</code> if not present or wrong data type      * TODO: we should use exceptions instead of returning null!      */
name|InputStream
name|dereference
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|contentType
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Dereferences the Representation of the referred Entity      * @param uri the uri of the referred entity      * @return the representation of<code>null</code> if no Entity was found      * for the parsed entity reference.      */
name|Representation
name|dereference
parameter_list|(
name|String
name|uri
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|//    /**
comment|//     * NOTE Moved to ReferencedSite
comment|//     * @return
comment|//     */
comment|//    Dictionary<String, ?> getSiteConfiguration();
block|}
end_interface

end_unit

