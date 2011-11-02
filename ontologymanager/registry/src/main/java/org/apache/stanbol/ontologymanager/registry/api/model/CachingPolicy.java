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
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|model
package|;
end_package

begin_comment
comment|/**  * The possible policies a registry manager can adopt for distributed caching.  *   * @author alexdma  */
end_comment

begin_enum
specifier|public
enum|enum
name|CachingPolicy
block|{
comment|/**      * A single ontology manager will be used for all known registries, which implies that only one possible      * version of each ontology can be loaded at one time.      */
name|CENTRALISED
block|,
comment|/**      * Every registry is assigned its own ontology manager for caching ontologies once they are loaded. If a      * library is referenced across multiple registries, an ontology set will be instantiated for each.      */
name|DISTRIBUTED
block|; }
end_enum

end_unit

