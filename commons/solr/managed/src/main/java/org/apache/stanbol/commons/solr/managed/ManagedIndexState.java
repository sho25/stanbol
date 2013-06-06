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
name|commons
operator|.
name|solr
operator|.
name|managed
package|;
end_package

begin_comment
comment|/**  * Enumeration for the different states of a managed index  * @author Rupert Westenthaler  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|ManagedIndexState
block|{
comment|/**      * The index was registered, but required data are still missing      */
name|UNINITIALISED
block|,
comment|/**      * The index is ready, but currently not activated      */
name|INACTIVE
block|,
comment|/**      * The index is active and can be used      */
name|ACTIVE
block|,
comment|/**      * The index encountered an error during Initialisation. The kind of the      * Error is available via the {@link IndexMetadata}.      */
name|ERROR
block|,  }
end_enum

end_unit

