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
name|jobmanager
operator|.
name|event
package|;
end_package

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
name|NonLiteral
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
name|enhancer
operator|.
name|jobmanager
operator|.
name|event
operator|.
name|impl
operator|.
name|EnhancementJob
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|ExecutionMetadata
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|event
operator|.
name|Event
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|event
operator|.
name|EventConstants
import|;
end_import

begin_comment
comment|/**  * Defines constants such as the used {@link EventConstants#EVENT_TOPIC} and the  * properties used by the sent {@link Event}s.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Constants
block|{
comment|/**      * The topic used to report the completion the execution of an      * EnhancementEngine back to the event job manager      */
name|String
name|TOPIC_JOB_MANAGER
init|=
literal|"stanbol/enhancer/jobmanager/event/topic"
decl_stmt|;
comment|/**      * Property used to provide the {@link EnhancementJob} instance      */
name|String
name|PROPERTY_JOB_MANAGER
init|=
literal|"stanbol.enhancer.jobmanager.event.job"
decl_stmt|;
comment|/**      * Property used to provide the {@link NonLiteral} describing the      * {@link ExecutionMetadata#EXECUTION} instance      */
name|String
name|PROPERTY_EXECUTION
init|=
literal|"stanbol.enhancer.jobmanager.event.execution"
decl_stmt|;
block|}
end_interface

end_unit

