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
name|store
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_comment
comment|/**  * This interface represents a set of {@link #size()} changes starting {@link #fromRevision()}  * {@link #toRevision()} affecting items with the URIs returned by {@link #changed()}.  * Instead of getting all changes as a whole, they can be retrieved iteratively through the {@link Store}  * instance.  *<p>  * The intended usage of this class is<code><pre>  *     Store&lt;ContentItem&gt; store; //the store  *     SemanticIndex index; //the index to apply the changes  *     long revision = Long.MIN_VALUE; //start from scratch  *     int batchSize = 1000;  *     ChangeSet cs;  *     do {  *         cs = store.changes(revision, batchSize);  *         for(String changed : cs.changed()){  *             ContentItem ci = store.get(changed);  *             if(ci == null){  *                 index.remove(changed);  *             } else {  *                 index.index(ci);  *             }  *         }  *     while(!cs.changed().isEmpty());  *     index.persist(cs.fromRevision());  *</pre></code>  */
end_comment

begin_interface
specifier|public
interface|interface
name|ChangeSet
parameter_list|<
name|Item
parameter_list|>
block|{
comment|/**      * The lowest revision number included in this ChangeSet      *       * @return the lowest revision number of this set      */
name|long
name|fromRevision
parameter_list|()
function_decl|;
comment|/**      * The highest revision number included in this ChangeSet      *       * @return the highest revision number of this set      */
name|long
name|toRevision
parameter_list|()
function_decl|;
comment|/**      * The read only {@link Set} of changes ContentItems included in this ChangeSet.      *       * @return the URIs of the changed contentItems included in this ChangeSet      */
name|Set
argument_list|<
name|String
argument_list|>
name|changed
parameter_list|()
function_decl|;
comment|/**      * The reference to the {@link Store} of this {@link ChangeSet}. This Store can be used to iterate on the      * changes.      *       * @return      */
name|Store
argument_list|<
name|Item
argument_list|>
name|getStore
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

