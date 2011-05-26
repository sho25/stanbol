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
name|core
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Dictionary
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
name|PropertyOption
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
name|entityhub
operator|.
name|core
operator|.
name|utils
operator|.
name|OsgiUtils
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
name|EntityhubConfiguration
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
name|ManagedEntityState
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
name|MappingState
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
comment|/**  * Implementation of the Entityhub Configuration as an own component.  * TODO: Currently the {@link EntityhubImpl} has a 1..1 dependency to this one.  * One could also just extend the {@link EntityhubImpl} from this class.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|EntityhubConfigurationImpl
implements|implements
name|EntityhubConfiguration
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|EntityhubConfigurationImpl
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityhubConfiguration
operator|.
name|ID
argument_list|,
name|value
operator|=
literal|"entityhub"
argument_list|)
specifier|private
name|String
name|entityhubID
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityhubConfiguration
operator|.
name|NAME
argument_list|,
name|value
operator|=
literal|"<organisations> Entityhub"
argument_list|)
specifier|private
name|String
name|entityhubName
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityhubConfiguration
operator|.
name|DESCRIPTION
argument_list|,
name|value
operator|=
literal|"The entityhub holding all entities of<organisation>"
argument_list|)
specifier|private
name|String
name|entityhubDescription
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityhubConfiguration
operator|.
name|PREFIX
argument_list|,
name|value
operator|=
literal|"urn:org.apache.stanbol:entityhub:"
argument_list|)
specifier|private
name|String
name|entityhubPrefix
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityhubConfiguration
operator|.
name|ENTITYHUB_YARD_ID
argument_list|,
name|value
operator|=
name|EntityhubConfiguration
operator|.
name|DEFAULT_ENTITYHUB_YARD_ID
argument_list|)
specifier|private
name|String
name|entityhubYardId
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityhubConfiguration
operator|.
name|FIELD_MAPPINGS
argument_list|,
name|value
operator|=
block|{
comment|//This is the default config for well known Ontologies
comment|// --- Define the Languages for all fields ---
comment|//NOTE: the leading space is required for the global filter!
literal|" | @=null;en;de;fr;it"
block|,
comment|//will filter all labels with other languages
comment|// --- RDF, RDFS and OWL Mappings ---
literal|"rdfs:label"
block|,
comment|//rdf:label
literal|"rdfs:label> entityhub:label"
block|,
literal|"rdfs:comment"
block|,
comment|//rdf:comment
literal|"rdfs:comment> entityhub:description"
block|,
literal|"rdf:type | d=entityhub:ref"
block|,
comment|//The types
literal|"owl:sameAs | d=entityhub:ref"
block|,
comment|//used by LOD to link to URIs used to identify the same Entity
comment|// --- Dublin Core ---
literal|"dc:*"
block|,
comment|//all DC Terms properties
literal|"dc:title> entityhub:label"
block|,
literal|"dc:description> entityhub:description"
block|,
literal|"dc-elements:*"
block|,
comment|//all DC Elements (one could also define the mappings to the DC Terms counterparts here
literal|"dc-elements:title> entityhub:label"
block|,
literal|"dc-elements:description> entityhub:description"
block|,
comment|// --- Spatial Things ---
literal|"geo:lat | d=xsd:double"
block|,
literal|"geo:long | d=xsd:double"
block|,
literal|"geo:alt | d=xsd:int;xsd:float"
block|,
comment|//also allow floating point if one needs to use fractions of meters
comment|// --- Thesaurus (via SKOS) ---
comment|//SKOS can be used to define hierarchical terminologies
literal|"skos:*"
block|,
literal|"skos:prefLabel> entityhub:label"
block|,
literal|"skos:definition> entityhub:description"
block|,
literal|"skos:note> entityhub:description"
block|,
literal|"skos:broader | d=entityhub:ref"
block|,
literal|"skos:narrower | d=entityhub:ref"
block|,
literal|"skos:related | d=entityhub:ref"
block|,
comment|//                "skos:member | d=entityhub:ref",
literal|"skos:subject | d=entityhub:ref"
block|,
literal|"skos:inScheme | d=entityhub:ref"
block|,
comment|//                "skos:hasTopConcept | d=entityhub:ref",
comment|//                "skos:topConceptOf | d=entityhub:ref",
comment|// --- Social Networks (via foaf) ---
literal|"foaf:*"
block|,
comment|//The Friend of a Friend schema often used to describe social relations between people
literal|"foaf:name> entityhub:label"
block|,
comment|//                "foaf:knows | d=entityhub:ref",
comment|//                "foaf:made | d=entityhub:ref",
comment|//                "foaf:maker | d=entityhub:ref",
comment|//                "foaf:member | d=entityhub:ref",
literal|"foaf:homepage | d=xsd:anyURI"
block|,
literal|"foaf:depiction | d=xsd:anyURI"
block|,
literal|"foaf:img | d=xsd:anyURI"
block|,
literal|"foaf:logo | d=xsd:anyURI"
block|,
literal|"foaf:page | d=xsd:anyURI"
comment|//page about the entity
block|}
argument_list|)
specifier|private
name|String
index|[]
name|fieldMappingConfig
decl_stmt|;
comment|//NOTE: there is no other way than hard coding the names there!
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityhubConfiguration
operator|.
name|DEFAULT_MAPPING_STATE
argument_list|,
name|options
operator|=
block|{
annotation|@
name|PropertyOption
argument_list|(
comment|//seems, that name and value are exchanged ...
name|value
operator|=
literal|'%'
operator|+
name|EntityhubConfiguration
operator|.
name|DEFAULT_MAPPING_STATE
operator|+
literal|".option.proposed"
argument_list|,
name|name
operator|=
literal|"proposed"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|EntityhubConfiguration
operator|.
name|DEFAULT_MAPPING_STATE
operator|+
literal|".option.confirmed"
argument_list|,
name|name
operator|=
literal|"confirmed"
argument_list|)
block|}
argument_list|,
name|value
operator|=
literal|"proposed"
argument_list|)
specifier|private
name|String
name|defaultMappingStateString
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EntityhubConfiguration
operator|.
name|DEFAULT_SYMBOL_STATE
argument_list|,
name|options
operator|=
block|{
annotation|@
name|PropertyOption
argument_list|(
comment|//seems, that name and value are exchanged ...
name|value
operator|=
literal|'%'
operator|+
name|EntityhubConfiguration
operator|.
name|DEFAULT_SYMBOL_STATE
operator|+
literal|".option.proposed"
argument_list|,
name|name
operator|=
literal|"proposed"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|'%'
operator|+
name|EntityhubConfiguration
operator|.
name|DEFAULT_SYMBOL_STATE
operator|+
literal|".option.active"
argument_list|,
name|name
operator|=
literal|"active"
argument_list|)
block|}
argument_list|,
name|value
operator|=
literal|"proposed"
argument_list|)
specifier|private
name|String
name|defaultSymblStateString
decl_stmt|;
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
name|?
argument_list|,
name|?
argument_list|>
name|properties
init|=
name|context
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Activate Entityhub Configuration:"
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"entityhubID:{}"
argument_list|,
name|entityhubID
argument_list|)
expr_stmt|;
comment|//TODO remove ... just there to check if property annotations do actually set the property value
name|log
operator|.
name|info
argument_list|(
literal|"entityhubName:{}"
argument_list|,
name|entityhubName
argument_list|)
expr_stmt|;
name|this
operator|.
name|entityhubID
operator|=
name|OsgiUtils
operator|.
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|EntityhubConfiguration
operator|.
name|ID
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|this
operator|.
name|entityhubName
operator|=
name|OsgiUtils
operator|.
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|EntityhubConfiguration
operator|.
name|NAME
argument_list|,
name|this
operator|.
name|entityhubID
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|Object
name|entityhubDescriptionObject
init|=
name|properties
operator|.
name|get
argument_list|(
name|EntityhubConfiguration
operator|.
name|DESCRIPTION
argument_list|)
decl_stmt|;
name|this
operator|.
name|entityhubDescription
operator|=
name|entityhubDescriptionObject
operator|==
literal|null
condition|?
literal|null
else|:
name|entityhubDescriptionObject
operator|.
name|toString
argument_list|()
expr_stmt|;
name|this
operator|.
name|entityhubPrefix
operator|=
name|OsgiUtils
operator|.
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|EntityhubConfiguration
operator|.
name|PREFIX
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|this
operator|.
name|entityhubYardId
operator|=
name|OsgiUtils
operator|.
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|EntityhubConfiguration
operator|.
name|ENTITYHUB_YARD_ID
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|Object
name|defaultSymbolState
init|=
name|properties
operator|.
name|get
argument_list|(
name|EntityhubConfiguration
operator|.
name|DEFAULT_SYMBOL_STATE
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultSymbolState
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|defaultSymblStateString
operator|=
name|ManagedEntity
operator|.
name|DEFAULT_SYMBOL_STATE
operator|.
name|name
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|defaultSymblStateString
operator|=
name|defaultSymbolState
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|Object
name|defaultMappingState
init|=
name|properties
operator|.
name|get
argument_list|(
name|EntityhubConfiguration
operator|.
name|DEFAULT_MAPPING_STATE
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultMappingState
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|defaultMappingStateString
operator|=
name|EntityMapping
operator|.
name|DEFAULT_MAPPING_STATE
operator|.
name|name
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|defaultMappingStateString
operator|=
name|defaultMappingState
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|Object
name|fieldMappingConfigObject
init|=
name|OsgiUtils
operator|.
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|EntityhubConfiguration
operator|.
name|FIELD_MAPPINGS
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldMappingConfigObject
operator|instanceof
name|String
index|[]
condition|)
block|{
name|this
operator|.
name|fieldMappingConfig
operator|=
operator|(
name|String
index|[]
operator|)
name|fieldMappingConfigObject
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|EntityhubConfiguration
operator|.
name|FIELD_MAPPINGS
argument_list|,
literal|"Values for this property must be of type Stirng[]!"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getID
parameter_list|()
block|{
return|return
name|entityhubID
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getEntityhubYardId
parameter_list|()
block|{
return|return
name|entityhubYardId
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|entityhubDescription
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getEntityhubPrefix
parameter_list|()
block|{
return|return
name|entityhubPrefix
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|entityhubName
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|getFieldMappingConfig
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|fieldMappingConfig
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|MappingState
name|getDefaultMappingState
parameter_list|()
block|{
try|try
block|{
return|return
name|MappingState
operator|.
name|valueOf
argument_list|(
name|defaultMappingStateString
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The value \""
operator|+
name|defaultMappingStateString
operator|+
literal|"\" configured as default MappingState does not match any value of the Enumeration! "
operator|+
literal|"Return the default state as defined by the "
operator|+
name|EntityMapping
operator|.
name|class
operator|+
literal|"."
argument_list|)
expr_stmt|;
return|return
name|EntityMapping
operator|.
name|DEFAULT_MAPPING_STATE
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|ManagedEntityState
name|getDefaultManagedEntityState
parameter_list|()
block|{
try|try
block|{
return|return
name|ManagedEntityState
operator|.
name|valueOf
argument_list|(
name|defaultSymblStateString
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"The value \""
operator|+
name|defaultSymblStateString
operator|+
literal|"\" configured as default SymbolState does not match any value of the Enumeration! "
operator|+
literal|"Return the default state as defined by the "
operator|+
name|ManagedEntity
operator|.
name|class
operator|+
literal|"."
argument_list|)
expr_stmt|;
return|return
name|ManagedEntity
operator|.
name|DEFAULT_SYMBOL_STATE
return|;
block|}
block|}
block|}
end_class

end_unit

