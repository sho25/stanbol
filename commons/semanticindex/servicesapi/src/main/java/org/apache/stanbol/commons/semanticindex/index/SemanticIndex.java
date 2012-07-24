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
name|semanticindex
operator|.
name|index
package|;
end_package

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
name|stanbol
operator|.
name|commons
operator|.
name|semanticindex
operator|.
name|store
operator|.
name|ChangeSet
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
name|Constants
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
comment|/**  * A Semantic Index for Items  *  * @param<Item>  */
end_comment

begin_interface
specifier|public
interface|interface
name|SemanticIndex
parameter_list|<
name|Item
parameter_list|>
block|{
comment|/**      * Name property for a SemanticIndex      */
specifier|public
specifier|static
specifier|final
name|String
name|PROP_NAME
init|=
literal|"Semantic-Index-Name"
decl_stmt|;
comment|/**      * Description property for a SemanticIndex      */
specifier|public
specifier|static
specifier|final
name|String
name|PROP_DESCRIPTION
init|=
literal|"Semantic-Index-Description"
decl_stmt|;
comment|/**      * Revision property for a Semantic Index. With this property, the last persisted revision of a Semantic      * Index is indicated.      */
specifier|public
specifier|static
specifier|final
name|String
name|PROP_REVISION
init|=
literal|"Semantic-Index-Revision"
decl_stmt|;
comment|/**      * State property for a Semantic Index      */
specifier|public
specifier|static
specifier|final
name|String
name|PROP_STATE
init|=
literal|"Semantic-Index-State"
decl_stmt|;
comment|/**      * The name of the Semantic Index. The same as configured by the {@link #PROP_NAME} property in the OSGI      * component configuration      *       * @return the name;      */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * The type of Items indexed in this semantic Index      * @return the java {@link Class} of the Items provided by this index      */
name|Class
argument_list|<
name|Item
argument_list|>
name|getIntdexType
parameter_list|()
function_decl|;
comment|/**      * The description for the Semantic Index. The same as configured by the {@link #PROP_DESCRIPTION}      * property in the OSGI component configuration      *       * @return the name;      */
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/**      * The state of the semantic index      */
name|IndexState
name|getState
parameter_list|()
function_decl|;
comment|/**      * Indexes the parsed ContentItem      *       * @param ci      *            the contentItem      * @return<code>true</true> if the ConentItem was included in the index.      *<code>false</code> if the ContentItem was ignored (e.g. filtered based on the indexing rules).      * @throws IndexException      *             On any error while accessing the semantic index      */
name|boolean
name|index
parameter_list|(
name|Item
name|item
parameter_list|)
throws|throws
name|IndexException
function_decl|;
comment|/**      * Removes the {@link ContentItem} with the parsed {@code uri} from this index. If the no content item      * with the parsed uri is present in this index the call can be ignored.      *       * @param uri      *            the uri of the item to remove      * @throws IndexException      *             On any error while accessing the semantic index      */
name|void
name|remove
parameter_list|(
name|String
name|uri
parameter_list|)
throws|throws
name|IndexException
function_decl|;
comment|/**      * Persists all changes to the index and sets the revision to the parsed value if the operation succeeds.      *<p>      * TODO: The {@link ChangeSet} interface does NOT provide revisions for each changed ContentItem but only      * for the whole Set. So this means that this method can only be called after indexing the whole      * {@link ChangeSet}. This might be OK, but needs further investigation (rwesten)      *       * @param revision      *            the revision      * @throws IndexException      *             On any error while accessing the semantic index      */
name|void
name|persist
parameter_list|(
name|long
name|revision
parameter_list|)
throws|throws
name|IndexException
function_decl|;
comment|/**      * Getter for the current revision of this SemanticIndex      *       * @return the revision number or {@link Long#MIN_VALUE} if none.      */
name|long
name|getRevision
parameter_list|()
function_decl|;
comment|/**      * Getter for the list of fields supported by this semantic index. This information is optional.      * Implementations that does not support this can indicate that by returning<code>null</code>.      *       * @return the list of filed names or<code>null</code> if not available      * @throws IndexException      */
name|List
argument_list|<
name|String
argument_list|>
name|getFieldsNames
parameter_list|()
throws|throws
name|IndexException
function_decl|;
comment|/**      * Getter for the properties describing a specific field supported by this index. Names can be retrieved      * by using {@link #getFieldsNames()}. This information is optional. Implementations that do not support      * this can indicate this by returning<code>null</code>.      *<p>      * The keys of the returned map represent the properties. Values the actual configuration of the property.      *       * @param name      *            the field name      * @return the field properties or<code>null</code> if not available.      * @throws IndexException      */
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getFieldProperties
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|IndexException
function_decl|;
comment|/**      * Getter for the RESTful search interfaces supported by this semantic index.       * The keys represent the types of the RESTful Interfaces.       * See the {@link EndpointTypeEnum} enumeration for knows keys. The value is the      * URL of that service relative to to the Stanbol base URI      *       * @return the RESTful search interfaces supported by this semantic index.      */
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getRESTSearchEndpoints
parameter_list|()
function_decl|;
comment|/**      * Getter for the Java search APIs supported by this semantic index. The keys are the java interfaces and      * values are OSGI {@link ServiceReference}s. This also means that instead of using this method such      * components can be accesses by using a filter on      *<ul>      *<li>{@link #PROP_NAME} = {@link #getName()}      *<li> {@link Constants#OBJECTCLASS} = {@link Class#getName()}      *</ul>      *       * @return the Java search APIs supported by this semantic index.       * Also registered as OSGI services. The key is equals to the      * {@link Class#getName()} and {@link Constants#OBJECTCLASS}.      */
name|Map
argument_list|<
name|String
argument_list|,
name|ServiceReference
argument_list|>
name|getSearchEndPoints
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

