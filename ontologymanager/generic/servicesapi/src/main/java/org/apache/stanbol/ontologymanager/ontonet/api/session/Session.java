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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|session
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|collector
operator|.
name|Lockable
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|collector
operator|.
name|OntologyCollector
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OWLExportable
import|;
end_import

begin_comment
comment|/**  * An ontology collector that can be used by client applications to store volatile data, e.g. for the duration  * of a service call. It has an aggressive severance policy and tries to delete as many managed ontologies as  * possible when it goes down.<br>  *<br>  * Note that sessions are generally disjoint with HTTP sessions or the like, but can be used in conjunction  * with them, or manipulated to mimic their behaviour.  *   * @deprecated Packages, class names etc. containing "ontonet" in any capitalization are being phased out.  *             Please switch to {@link org.apache.stanbol.ontologymanager.servicesapi.session.Session} as soon  *             as possible.  *   * @see org.apache.stanbol.ontologymanager.servicesapi.session.Session  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Session
extends|extends
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|session
operator|.
name|Session
extends|,
name|OntologyCollector
extends|,
name|OWLExportable
extends|,
name|Lockable
extends|,
name|SessionListenable
block|{  }
end_interface

end_unit

