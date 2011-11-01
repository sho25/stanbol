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
name|cmsadapter
operator|.
name|core
operator|.
name|mapping
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Graph
import|;
end_import

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
name|LiteralFactory
import|;
end_import

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
name|MGraph
import|;
end_import

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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Resource
import|;
end_import

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
name|Triple
import|;
end_import

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
name|UriRef
import|;
end_import

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
name|impl
operator|.
name|SimpleMGraph
import|;
end_import

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
name|impl
operator|.
name|TripleImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|CMSAdapterVocabulary
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFBridge
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
name|cm
operator|.
name|ConfigurationException
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
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Default implementation of {@link RDFBridge} interface. It provides annotation of raw RDF data and  * generating RDF from the content repository based on the configurations described below:  *   *<ul>  *<li><b>Resource selector:</b></li> This property is used to filter resources from the RDF data. It should  * have the syntax:<br>  *<b> rdf:Type> skos:Concept</b>. This example states that triples having value<b>skos:Concept</b> of  *<b>rdf:type</b> predicate will be filtered. And subject of selected triples indicates the resource to be  * created/updated as node/object in the repository. It is also acceptable to pass full URIs such as<br>  *<b> http://www.w3.org/1999/02/22-rdf-syntax-ns#type> http://www.w3.org/2004/02/skos/core#Concept</b><br>  *<li><b>Name:</b></li> This configuration indicates the predicate which points to the name of node/object to  * be created in the repository. It should indicate a single URI such as<b>rdfs:label</b> or  *<b>http://www.w3.org/2000/01/rdf-schema#label</b>. Actually name value is obtained through the triple  * (s,p,o) where<b>s</b> is one of the subjects filtered by the "Resource Selector" configuration,<b>p</b>  * is this parameter. This configuration is optional. If an empty configuration is passed name of the CMS  * objects will be set as the local name of the URI represented by<b>s</b><br>  *<li><b>Children:</b></li> This property specifies the children of nodes/objects to be created in the  * repository. Value of this configuration should be like<b>skos:narrower> narrowerObject</b> or  *<b>skos:narrower> rdfs:label</b>. First option has same logic with the previous parameter. It determines  * the name of the child CMS object to be created/updated. In the second case, value rdfs:label predicate of  * resource representing child object will be set as the name of child object/node in the repository. This  * option would be useful to create hierarchies.  *<p>  * It is also possible to set only predicate indicating the subsumption relations such as only  *<b>skos:narrower</b>. In this case name of the child resource will be obtained from the local name of URI  * representing this CMS object. This configuration is optional.  *<li><b>Default child predicate:</b></li> First of all this configuration is used only when generating an  * RDF from the repository. If there are more than one child selector in previous configuration, it is not  * possible to detect the predicate that will be used as the child assertion. In that case, this configuration  * is used to set child assertion between parent and child objects. This configuration is optional. But if  * there is a case in which this configuration should be used and if it is not set, this causes missing  * assertions in the generated RDF.  *<li><b>Content repository path:</b></li> This property specifies the content repository path in which the  * new CMS objects will be created or existing ones will be updated.  *</ul>  *   * @author suat  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|configurationFactory
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DefaultRDFBridgeImpl
operator|.
name|PROP_RESOURCE_SELECTOR
argument_list|,
name|value
operator|=
literal|"rdf:type> skos:Concept"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DefaultRDFBridgeImpl
operator|.
name|PROP_NAME
argument_list|,
name|value
operator|=
literal|"rdfs:label"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DefaultRDFBridgeImpl
operator|.
name|PROP_CHILDREN
argument_list|,
name|cardinality
operator|=
literal|1000
argument_list|,
name|value
operator|=
block|{
literal|"skos:narrower"
block|}
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DefaultRDFBridgeImpl
operator|.
name|PROP_DEFAULT_CHILD_PREDICATE
argument_list|,
name|value
operator|=
literal|"skos:narrower"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|DefaultRDFBridgeImpl
operator|.
name|PROP_CMS_PATH
argument_list|,
name|value
operator|=
literal|"/rdfmaptest"
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|DefaultRDFBridgeImpl
implements|implements
name|RDFBridge
block|{
specifier|public
specifier|static
specifier|final
name|String
name|PROP_RESOURCE_SELECTOR
init|=
literal|"org.apache.stanbol.cmsadapter.core.mapping.DefaultRDFBridgeImpl.resourceSelector"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROP_NAME
init|=
literal|"org.apache.stanbol.cmsadapter.core.mapping.DefaultRDFBridgeImpl.resourceNamePredicate"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROP_CHILDREN
init|=
literal|"org.apache.stanbol.cmsadapter.core.mapping.DefaultRDFBridgeImpl.childrenPredicates"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROP_DEFAULT_CHILD_PREDICATE
init|=
literal|"org.apache.stanbol.cmsadapter.core.mapping.DefaultRDFBridgeImpl.defaultChildPredicate"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROP_CMS_PATH
init|=
literal|"org.apache.stanbol.cmsadapter.core.mapping.DefaultRDFBridgeImpl.contentRepositoryPath"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|CMSAdapterVocabulary
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|UriRef
name|targetResourcePredicate
decl_stmt|;
specifier|private
name|UriRef
name|targetResourceValue
decl_stmt|;
specifier|private
name|UriRef
name|nameResource
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|UriRef
argument_list|,
name|Object
argument_list|>
name|targetChildrenMappings
init|=
operator|new
name|HashMap
argument_list|<
name|UriRef
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|UriRef
name|defaultChildPredicate
decl_stmt|;
specifier|private
name|String
name|cmsPath
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
init|=
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|parseTargetResourceConfig
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|parseURIConfig
argument_list|(
name|PROP_NAME
argument_list|,
name|properties
argument_list|)
expr_stmt|;
name|parseChildrenMappings
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|parseURIConfig
argument_list|(
name|PROP_DEFAULT_CHILD_PREDICATE
argument_list|,
name|properties
argument_list|)
expr_stmt|;
name|this
operator|.
name|cmsPath
operator|=
operator|(
name|String
operator|)
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|PROP_CMS_PATH
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|MGraph
name|annotateGraph
parameter_list|(
name|Graph
name|rawRDF
parameter_list|)
block|{
name|MGraph
name|graph
init|=
operator|new
name|SimpleMGraph
argument_list|(
name|rawRDF
argument_list|)
decl_stmt|;
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|tripleIterator
init|=
name|graph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|targetResourcePredicate
argument_list|,
name|targetResourceValue
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|processedURIs
init|=
operator|new
name|ArrayList
argument_list|<
name|NonLiteral
argument_list|>
argument_list|()
decl_stmt|;
comment|// add cms object annotations
while|while
condition|(
name|tripleIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|t
init|=
name|tripleIterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|NonLiteral
name|subject
init|=
name|t
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|getObjectName
argument_list|(
name|subject
argument_list|,
name|nameResource
argument_list|,
name|graph
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|// There should be a valid name for CMS Object
if|if
condition|(
operator|!
name|name
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|subject
argument_list|,
name|RDFBridgeHelper
operator|.
name|RDF_TYPE
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT
argument_list|)
argument_list|)
expr_stmt|;
name|processedURIs
operator|.
name|add
argument_list|(
name|subject
argument_list|)
expr_stmt|;
comment|/*                  * if this object has already has name and path annotations, it means that it's already                  * processed as child of another object. So, don't put new name annotations                  */
if|if
condition|(
operator|!
name|graph
operator|.
name|filter
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
literal|null
argument_list|)
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|name
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// check children and add child and parent annotations
name|checkChildren
argument_list|(
name|subject
argument_list|,
name|processedURIs
argument_list|,
name|graph
argument_list|)
expr_stmt|;
block|}
block|}
name|RDFBridgeHelper
operator|.
name|addPathAnnotations
argument_list|(
name|cmsPath
argument_list|,
name|processedURIs
argument_list|,
name|graph
argument_list|)
expr_stmt|;
return|return
name|graph
return|;
block|}
specifier|private
name|void
name|checkChildren
parameter_list|(
name|NonLiteral
name|objectURI
parameter_list|,
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|processedURIs
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
for|for
control|(
name|UriRef
name|childPropURI
range|:
name|targetChildrenMappings
operator|.
name|keySet
argument_list|()
control|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|childrenIt
init|=
name|graph
operator|.
name|filter
argument_list|(
name|objectURI
argument_list|,
name|childPropURI
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|childNames
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|childrenIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|child
init|=
name|childrenIt
operator|.
name|next
argument_list|()
decl_stmt|;
name|NonLiteral
name|childSubject
init|=
operator|new
name|UriRef
argument_list|(
name|RDFBridgeHelper
operator|.
name|removeEndCharacters
argument_list|(
name|child
operator|.
name|getObject
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|childName
init|=
name|getChildName
argument_list|(
name|childSubject
argument_list|,
name|targetChildrenMappings
operator|.
name|get
argument_list|(
name|childPropURI
argument_list|)
argument_list|,
name|graph
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|childName
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|RDFBridgeHelper
operator|.
name|removeExistingTriple
argument_list|(
name|childSubject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|graph
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|childSubject
argument_list|,
name|RDFBridgeHelper
operator|.
name|RDF_TYPE
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|childSubject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PARENT_REF
argument_list|,
name|objectURI
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|childSubject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|checkDuplicateChildName
argument_list|(
name|childName
argument_list|,
name|childNames
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Failed to obtain a name for child property: {}"
argument_list|,
name|childPropURI
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
name|String
name|getObjectName
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|UriRef
name|namePredicate
parameter_list|,
name|MGraph
name|graph
parameter_list|,
name|boolean
name|tryTargetResourceNamePredicate
parameter_list|)
block|{
name|String
name|name
init|=
literal|""
decl_stmt|;
comment|// try to get name through default CMS Vocabulary predicate
name|name
operator|=
name|RDFBridgeHelper
operator|.
name|getResourceStringValue
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|graph
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|name
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
name|name
return|;
block|}
comment|// if there is a configuration specifying the name try to obtain through it
if|if
condition|(
name|namePredicate
operator|!=
literal|null
condition|)
block|{
name|name
operator|=
name|RDFBridgeHelper
operator|.
name|getResourceStringValue
argument_list|(
name|subject
argument_list|,
name|namePredicate
argument_list|,
name|graph
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|name
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
name|name
return|;
block|}
block|}
comment|// if this method is called from a child node try to obtain name by target resource name predicate
if|if
condition|(
name|nameResource
operator|!=
literal|null
condition|)
block|{
name|name
operator|=
name|RDFBridgeHelper
operator|.
name|getResourceStringValue
argument_list|(
name|subject
argument_list|,
name|nameResource
argument_list|,
name|graph
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|name
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
name|name
return|;
block|}
block|}
comment|// failed to obtain name by the specified property assign local name from the URI
name|name
operator|=
name|RDFBridgeHelper
operator|.
name|extractLocalNameFromURI
argument_list|(
name|subject
argument_list|)
expr_stmt|;
return|return
name|name
return|;
block|}
specifier|private
name|String
name|getChildName
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|Object
name|nameProp
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
if|if
condition|(
name|nameProp
operator|instanceof
name|String
condition|)
block|{
if|if
condition|(
operator|(
operator|(
name|String
operator|)
name|nameProp
operator|)
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
name|getObjectName
argument_list|(
name|subject
argument_list|,
literal|null
argument_list|,
name|graph
argument_list|,
literal|true
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|String
operator|)
name|nameProp
return|;
block|}
block|}
else|else
block|{
return|return
name|getObjectName
argument_list|(
name|subject
argument_list|,
operator|(
name|UriRef
operator|)
name|nameProp
argument_list|,
name|graph
argument_list|,
literal|true
argument_list|)
return|;
block|}
block|}
specifier|private
specifier|static
name|String
name|checkDuplicateChildName
parameter_list|(
name|String
name|candidateName
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|childNames
parameter_list|)
block|{
name|Integer
name|childNameCount
init|=
name|childNames
operator|.
name|get
argument_list|(
name|candidateName
argument_list|)
decl_stmt|;
if|if
condition|(
name|childNameCount
operator|!=
literal|null
condition|)
block|{
name|candidateName
operator|+=
operator|(
name|childNameCount
operator|+
literal|1
operator|)
expr_stmt|;
name|childNames
operator|.
name|put
argument_list|(
name|candidateName
argument_list|,
operator|(
name|childNameCount
operator|+
literal|1
operator|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|childNames
operator|.
name|put
argument_list|(
name|candidateName
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
return|return
name|candidateName
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|annotateCMSGraph
parameter_list|(
name|MGraph
name|cmsGraph
parameter_list|)
block|{
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|roots
init|=
name|RDFBridgeHelper
operator|.
name|getRootObjectsOfGraph
argument_list|(
name|cmsPath
argument_list|,
name|cmsGraph
argument_list|)
decl_stmt|;
for|for
control|(
name|NonLiteral
name|rootObjectURI
range|:
name|roots
control|)
block|{
name|applyReverseBridgeSettings
argument_list|(
name|rootObjectURI
argument_list|,
name|cmsGraph
argument_list|)
expr_stmt|;
if|if
condition|(
name|defaultChildPredicate
operator|!=
literal|null
condition|)
block|{
name|addChildrenAnnotations
argument_list|(
name|rootObjectURI
argument_list|,
name|cmsGraph
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|addChildrenAnnotations
parameter_list|(
name|NonLiteral
name|parentURI
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|children
init|=
name|graph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PARENT_REF
argument_list|,
name|parentURI
argument_list|)
decl_stmt|;
while|while
condition|(
name|children
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|NonLiteral
name|childURI
init|=
name|children
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|UriRef
name|childPredicate
decl_stmt|;
if|if
condition|(
name|targetChildrenMappings
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|childPredicate
operator|=
name|targetChildrenMappings
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|childPredicate
operator|=
name|defaultChildPredicate
expr_stmt|;
block|}
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|parentURI
argument_list|,
name|childPredicate
argument_list|,
name|childURI
argument_list|)
argument_list|)
expr_stmt|;
name|applyReverseBridgeSettings
argument_list|(
name|childURI
argument_list|,
name|graph
argument_list|)
expr_stmt|;
name|addChildrenAnnotations
argument_list|(
name|childURI
argument_list|,
name|graph
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|applyReverseBridgeSettings
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
comment|// add subsumption assertion
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|subject
argument_list|,
name|targetResourcePredicate
argument_list|,
name|targetResourceValue
argument_list|)
argument_list|)
expr_stmt|;
comment|// add name assertion
name|revertObjectName
argument_list|(
name|subject
argument_list|,
name|graph
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|revertObjectName
parameter_list|(
name|NonLiteral
name|objectURI
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
if|if
condition|(
name|nameResource
operator|!=
literal|null
condition|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|graph
operator|.
name|filter
argument_list|(
name|objectURI
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|nameProp
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|Resource
name|name
init|=
name|nameProp
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|objectURI
argument_list|,
name|nameResource
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Failed to find name property for URI: {}"
argument_list|,
name|objectURI
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getCMSPath
parameter_list|()
block|{
return|return
name|this
operator|.
name|cmsPath
return|;
block|}
comment|/*      * Methods to parse configurations      */
specifier|private
name|void
name|parseURIConfig
parameter_list|(
name|String
name|config
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
parameter_list|)
block|{
name|Object
name|value
init|=
literal|null
decl_stmt|;
try|try
block|{
name|value
operator|=
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|config
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
operator|(
operator|(
name|String
operator|)
name|value
operator|)
operator|.
name|trim
argument_list|()
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
if|if
condition|(
name|config
operator|.
name|contentEquals
argument_list|(
name|PROP_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|nameResource
operator|=
name|parseUriRefFromConfig
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|config
operator|.
name|contentEquals
argument_list|(
name|PROP_DEFAULT_CHILD_PREDICATE
argument_list|)
condition|)
block|{
name|this
operator|.
name|defaultChildPredicate
operator|=
name|parseUriRefFromConfig
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"This configuration should be either empty or has one of the following formats:"
operator|+
literal|"\nskos:narrower"
operator|+
literal|"\nhttp://www.w3.org/2004/02/skos/core#narrower"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|parseTargetResourceConfig
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|String
name|targetResourceConfig
init|=
operator|(
name|String
operator|)
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|PROP_RESOURCE_SELECTOR
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|String
index|[]
name|configParts
init|=
name|parsePropertyConfig
argument_list|(
name|targetResourceConfig
argument_list|)
decl_stmt|;
name|this
operator|.
name|targetResourcePredicate
operator|=
name|parseUriRefFromConfig
argument_list|(
name|configParts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|this
operator|.
name|targetResourceValue
operator|=
name|parseUriRefFromConfig
argument_list|(
name|configParts
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|parseChildrenMappings
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
parameter_list|)
block|{
name|Object
name|value
init|=
literal|null
decl_stmt|;
try|try
block|{
name|value
operator|=
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|PROP_CHILDREN
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{
comment|// not the case
block|}
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|getChildConfiguration
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
index|[]
condition|)
block|{
for|for
control|(
name|String
name|config
range|:
operator|(
name|String
index|[]
operator|)
name|value
control|)
block|{
name|getChildConfiguration
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
name|UriRef
name|parseUriRefFromConfig
parameter_list|(
name|String
name|config
parameter_list|)
block|{
return|return
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|config
argument_list|)
argument_list|)
return|;
block|}
specifier|private
name|void
name|getChildConfiguration
parameter_list|(
name|String
name|config
parameter_list|)
block|{
try|try
block|{
name|String
index|[]
name|configParts
init|=
name|parseChildrenConfig
argument_list|(
name|config
argument_list|)
decl_stmt|;
name|int
name|configLength
init|=
name|configParts
operator|.
name|length
decl_stmt|;
name|Object
name|name
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|configLength
operator|==
literal|2
condition|)
block|{
if|if
condition|(
name|configParts
index|[
literal|1
index|]
operator|.
name|contains
argument_list|(
literal|":"
argument_list|)
condition|)
block|{
if|if
condition|(
name|RDFBridgeHelper
operator|.
name|isShortNameResolvable
argument_list|(
name|configParts
index|[
literal|1
index|]
argument_list|)
condition|)
block|{
name|name
operator|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|configParts
index|[
literal|1
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|name
operator|=
literal|null
expr_stmt|;
block|}
block|}
else|else
block|{
name|name
operator|=
name|configParts
index|[
literal|1
index|]
expr_stmt|;
block|}
block|}
name|this
operator|.
name|targetChildrenMappings
operator|.
name|put
argument_list|(
name|parseUriRefFromConfig
argument_list|(
name|configParts
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Failed to parse configuration value: {}"
argument_list|,
name|config
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Configuration value should be in the format e.g skos:Definition> definition"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|Object
name|checkProperty
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
parameter_list|,
name|String
name|key
parameter_list|,
name|boolean
name|required
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Object
name|value
init|=
name|properties
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|required
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|key
argument_list|,
literal|"Failed to get value for this property"
argument_list|)
throw|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
return|return
name|value
return|;
block|}
block|}
specifier|private
name|String
index|[]
name|parseChildrenConfig
parameter_list|(
name|String
name|config
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|String
index|[]
name|configParts
init|=
name|config
operator|.
name|split
argument_list|(
literal|">"
argument_list|)
decl_stmt|;
name|int
name|parts
init|=
name|configParts
operator|.
name|length
decl_stmt|;
if|if
condition|(
name|parts
operator|==
literal|1
operator|||
name|parts
operator|==
literal|2
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|parts
condition|;
name|i
operator|++
control|)
block|{
name|configParts
index|[
name|i
index|]
operator|=
name|configParts
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROP_CHILDREN
argument_list|,
literal|"Children resource configuration should have a value like one of the following three alternatives: "
operator|+
literal|"\nskos:narrower"
operator|+
literal|"\nskos:narrower> narrower"
operator|+
literal|"\nskos:narrawer> rdfs:label"
argument_list|)
throw|;
block|}
return|return
name|configParts
return|;
block|}
specifier|private
name|String
index|[]
name|parsePropertyConfig
parameter_list|(
name|String
name|config
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|String
index|[]
name|configParts
init|=
name|config
operator|.
name|split
argument_list|(
literal|">"
argument_list|)
decl_stmt|;
name|int
name|parts
init|=
name|configParts
operator|.
name|length
decl_stmt|;
if|if
condition|(
name|parts
operator|==
literal|1
operator|||
name|parts
operator|==
literal|2
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|parts
condition|;
name|i
operator|++
control|)
block|{
name|configParts
index|[
name|i
index|]
operator|=
name|configParts
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROP_RESOURCE_SELECTOR
argument_list|,
literal|"Target resource and resource value should be seperated by a single'>' sign"
argument_list|)
throw|;
block|}
return|return
name|configParts
return|;
block|}
block|}
end_class

end_unit

