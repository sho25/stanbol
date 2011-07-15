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
package|;
end_package

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * Physical and logical IRIs for unit tests.  */
end_comment

begin_class
specifier|public
class|class
name|Locations
block|{
comment|/**      * Default physical location of the ontology registry for testing.      */
specifier|public
specifier|static
specifier|final
name|IRI
name|_CP_NAMESPACE
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/cp/owl/"
argument_list|)
decl_stmt|;
comment|/**      * Default physical location of the ontology registry for testing.      */
specifier|public
specifier|static
specifier|final
name|IRI
name|_REGISTRY_TEST
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/registry/krestest.owl"
argument_list|)
decl_stmt|;
comment|/**      * Identifier of test ontology library 1.      */
specifier|public
specifier|static
specifier|final
name|IRI
name|LIBRARY_TEST1
init|=
name|IRI
operator|.
name|create
argument_list|(
name|_REGISTRY_TEST
operator|+
literal|"#TestRegistry"
argument_list|)
decl_stmt|;
comment|/**      * Identifier of test ontology library 2.      */
specifier|public
specifier|static
specifier|final
name|IRI
name|LIBRARY_TEST2
init|=
name|IRI
operator|.
name|create
argument_list|(
name|_REGISTRY_TEST
operator|+
literal|"#TestLibrary2"
argument_list|)
decl_stmt|;
comment|/**      * An ontology in test libraries 1 and 2.      */
specifier|public
specifier|static
specifier|final
name|IRI
name|ODP_OBJECTROLE
init|=
name|IRI
operator|.
name|create
argument_list|(
name|_CP_NAMESPACE
operator|+
literal|"objectrole.owl"
argument_list|)
decl_stmt|;
comment|/**      * An ontology in test library 2 but not in test library 1.      */
specifier|public
specifier|static
specifier|final
name|IRI
name|ODP_SITUATION
init|=
name|IRI
operator|.
name|create
argument_list|(
name|_CP_NAMESPACE
operator|+
literal|"situation.owl"
argument_list|)
decl_stmt|;
comment|/**      * An ontology in test library 1 but not in test library 2.      */
specifier|public
specifier|static
specifier|final
name|IRI
name|ODP_TYPESOFENTITIES
init|=
name|IRI
operator|.
name|create
argument_list|(
name|_CP_NAMESPACE
operator|+
literal|"typesofentities.owl"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

