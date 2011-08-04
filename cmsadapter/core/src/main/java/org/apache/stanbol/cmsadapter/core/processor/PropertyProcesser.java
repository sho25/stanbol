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
name|processor
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
name|List
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
name|OntologyResourceHelper
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
name|MappingEngine
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
name|model
operator|.
name|mapping
operator|.
name|PropertyBridge
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
name|model
operator|.
name|web
operator|.
name|AnnotationType
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
name|model
operator|.
name|web
operator|.
name|CMSObject
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
name|model
operator|.
name|web
operator|.
name|ClassificationObject
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
name|model
operator|.
name|web
operator|.
name|ContentObject
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
name|model
operator|.
name|web
operator|.
name|PropType
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
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DObject
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
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DProperty
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
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DPropertyDefinition
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
name|processor
operator|.
name|Processor
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
name|repository
operator|.
name|RepositoryAccess
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
name|repository
operator|.
name|RepositoryAccessException
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

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|enhanced
operator|.
name|UnsupportedPolymorphismException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|ConversionException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|DatatypeProperty
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|Individual
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|ObjectProperty
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntClass
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntResource
import|;
end_import

begin_comment
comment|/**  * This class does not implement {@link Processor} interface. However other processors uses this class to  * process instances of {@link PropertyBridge}s. If the property belongs to a {@link ContentObject} the  * following annotations are processed.  *<ul>  *<li>{@link AnnotationType#INSTANCE_OF}: The OWL Individual that represents source ClassificationObject's  * type becomes the OWL Class that represents target value of the property.</li>  *<li>{@link AnnotationType#FUNCTIONAL}: The property becomes a functional property.</li>  *<li>{@link AnnotationType#SYMMETRIC}: The property becomes a symmetric property.</li>  *<li>{@link AnnotationType#TRANSITIVE}: The property becomes a transitive property.</li>  *<li>{@link AnnotationType#INVERSE_FUNCTIONAL}: The property becomes an inverse functional property.</li>  *</ul>  * If the property belongs to a {@link ClassificationObject} the following annotations are processed.  *<ul>  *<li>{@link AnnotationType#SUBSUMPTION}: The OWL class that represents target ClassificationObject is  * considered as subclass of OWL Class that represents source ClassificationObject</li>  *<li>{@link AnnotationType#EQUIVALENT_CLASS} The OWL class that represents target ClassificationObject is  * considered as equivalent of OWL Class that represents source ClassificationObject</li>  *<li>{@link AnnotationType#DISJOINT_WITH} The OWL class that represents source ClassificationObject is  * considered as disjoint with OWL Class that represents target ClassificationObject</li>  *</ul>  *   * @author Suat  *   */
end_comment

begin_class
specifier|public
class|class
name|PropertyProcesser
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|PropertyProcesser
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|void
name|processConceptPropertyBridgeCreate
parameter_list|(
name|OntClass
name|subjectClass
parameter_list|,
name|PropertyBridge
name|propertyBridge
parameter_list|,
name|DObject
name|propertySubject
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|AnnotationType
name|annotation
init|=
name|getAnnotation
argument_list|(
name|propertyBridge
operator|.
name|getPropertyAnnotation
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|predicateName
init|=
name|propertyBridge
operator|.
name|getPredicateName
argument_list|()
decl_stmt|;
for|for
control|(
name|DProperty
name|prop
range|:
name|propertySubject
operator|.
name|getProperties
argument_list|()
control|)
block|{
name|DPropertyDefinition
name|propDef
init|=
name|prop
operator|.
name|getDefinition
argument_list|()
decl_stmt|;
comment|// propDef returns null if a * named property comes
comment|// TODO after handling * named properties, remove the null check
if|if
condition|(
name|propDef
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Property definition could not be got for property {}"
argument_list|,
name|prop
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|String
name|propName
init|=
name|propDef
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|propFullName
init|=
name|propDef
operator|.
name|getNamespace
argument_list|()
operator|+
literal|":"
operator|+
name|propDef
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|propName
operator|.
name|equals
argument_list|(
name|predicateName
argument_list|)
operator|||
name|propName
operator|.
name|contains
argument_list|(
name|predicateName
argument_list|)
operator|||
name|propFullName
operator|.
name|equals
argument_list|(
name|predicateName
argument_list|)
condition|)
block|{
if|if
condition|(
name|annotation
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|CMSObject
argument_list|>
name|referencedNodes
init|=
name|resolveReferenceNodes
argument_list|(
name|prop
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|processPropertyAnnotation
argument_list|(
name|annotation
argument_list|,
name|subjectClass
argument_list|,
name|referencedNodes
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
comment|// property found. break the loop
break|break;
block|}
block|}
block|}
specifier|public
name|void
name|processClassificationObjectProperty
parameter_list|(
name|DProperty
name|prop
parameter_list|,
name|AnnotationType
name|annotation
parameter_list|,
name|OntClass
name|subjectClass
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|List
argument_list|<
name|CMSObject
argument_list|>
name|referencedNodes
init|=
name|resolveReferenceNodes
argument_list|(
name|prop
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|processPropertyAnnotation
argument_list|(
name|annotation
argument_list|,
name|subjectClass
argument_list|,
name|referencedNodes
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|CMSObject
argument_list|>
name|resolveReferenceNodes
parameter_list|(
name|DProperty
name|prop
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|RepositoryAccess
name|accessor
init|=
name|engine
operator|.
name|getRepositoryAccess
argument_list|()
decl_stmt|;
name|Object
name|session
init|=
name|engine
operator|.
name|getSession
argument_list|()
decl_stmt|;
comment|// TODO consider other property types
name|List
argument_list|<
name|CMSObject
argument_list|>
name|referencedNodes
init|=
operator|new
name|ArrayList
argument_list|<
name|CMSObject
argument_list|>
argument_list|()
decl_stmt|;
name|PropType
name|type
init|=
name|prop
operator|.
name|getDefinition
argument_list|()
operator|.
name|getPropertyType
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|PropType
operator|.
name|REFERENCE
argument_list|)
condition|)
block|{
comment|// Resolve references
comment|// TODO need a better value representation than string
comment|// For example reference types may be resolved an put as an
comment|// objecttype
for|for
control|(
name|String
name|value
range|:
name|prop
operator|.
name|getValue
argument_list|()
control|)
block|{
try|try
block|{
name|referencedNodes
operator|.
name|addAll
argument_list|(
name|accessor
operator|.
name|getNodeById
argument_list|(
name|value
argument_list|,
name|session
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error resolving reference value {}"
argument_list|,
name|value
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|PropType
operator|.
name|NAME
argument_list|)
condition|)
block|{
for|for
control|(
name|String
name|name
range|:
name|prop
operator|.
name|getValue
argument_list|()
control|)
block|{
try|try
block|{
name|referencedNodes
operator|.
name|addAll
argument_list|(
name|accessor
operator|.
name|getNodeByName
argument_list|(
name|name
argument_list|,
name|session
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error at getting name nodes."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|PropType
operator|.
name|PATH
argument_list|)
condition|)
block|{
for|for
control|(
name|String
name|value
range|:
name|prop
operator|.
name|getValue
argument_list|()
control|)
block|{
try|try
block|{
name|referencedNodes
operator|.
name|addAll
argument_list|(
name|accessor
operator|.
name|getNodeByPath
argument_list|(
name|value
argument_list|,
name|session
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error at getting node by path "
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|referencedNodes
return|;
block|}
specifier|private
name|void
name|processPropertyAnnotation
parameter_list|(
name|AnnotationType
name|annotation
parameter_list|,
name|OntClass
name|subjectClass
parameter_list|,
name|List
argument_list|<
name|CMSObject
argument_list|>
name|referencedNodes
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
block|{
if|if
condition|(
name|annotation
operator|!=
literal|null
condition|)
block|{
name|OntologyResourceHelper
name|orh
init|=
name|engine
operator|.
name|getOntologyResourceHelper
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|OntClass
argument_list|>
name|referencedClasses
init|=
operator|new
name|ArrayList
argument_list|<
name|OntClass
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|CMSObject
name|referenceObject
range|:
name|referencedNodes
control|)
block|{
name|OntClass
name|c
init|=
name|orh
operator|.
name|createOntClassByCMSObject
argument_list|(
name|referenceObject
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed create OntClass for CMS object {}"
argument_list|,
name|referenceObject
operator|.
name|getLocalname
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|referencedClasses
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
comment|// process the annotations
if|if
condition|(
name|annotation
operator|.
name|equals
argument_list|(
name|AnnotationType
operator|.
name|SUBSUMPTION
argument_list|)
condition|)
block|{
for|for
control|(
name|OntClass
name|c
range|:
name|referencedClasses
control|)
block|{
name|orh
operator|.
name|addSubsumptionAssertion
argument_list|(
name|subjectClass
argument_list|,
name|c
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|annotation
operator|.
name|equals
argument_list|(
name|AnnotationType
operator|.
name|EQUIVALENT_CLASS
argument_list|)
condition|)
block|{
for|for
control|(
name|OntClass
name|c
range|:
name|referencedClasses
control|)
block|{
name|orh
operator|.
name|addEquivalentClassAssertion
argument_list|(
name|subjectClass
argument_list|,
name|c
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|annotation
operator|.
name|equals
argument_list|(
name|AnnotationType
operator|.
name|DISJOINT_WITH
argument_list|)
condition|)
block|{
for|for
control|(
name|OntClass
name|c
range|:
name|referencedClasses
control|)
block|{
name|orh
operator|.
name|addDisjointWithAssertion
argument_list|(
name|subjectClass
argument_list|,
name|c
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"{} annotation is not supported for this property"
argument_list|,
name|annotation
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
block|}
specifier|public
name|void
name|processInstancePropertyBridgeCreate
parameter_list|(
name|Individual
name|individual
parameter_list|,
name|DObject
name|contentObject
parameter_list|,
name|PropertyBridge
name|propertyBridge
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|String
name|predicateName
init|=
name|propertyBridge
operator|.
name|getPredicateName
argument_list|()
decl_stmt|;
for|for
control|(
name|DProperty
name|property
range|:
name|contentObject
operator|.
name|getProperties
argument_list|()
control|)
block|{
name|DPropertyDefinition
name|propDef
init|=
name|property
operator|.
name|getDefinition
argument_list|()
decl_stmt|;
comment|// propDef returns null if a * named property comes
comment|// TODO after handling * named properties, remove the null check
if|if
condition|(
name|propDef
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Property definition could not be got for property {}"
argument_list|,
name|property
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|String
name|propName
init|=
name|propDef
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|propFullName
init|=
name|propDef
operator|.
name|getNamespace
argument_list|()
operator|+
literal|":"
operator|+
name|propDef
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|propName
operator|.
name|equals
argument_list|(
name|predicateName
argument_list|)
operator|||
name|propName
operator|.
name|contains
argument_list|(
name|predicateName
argument_list|)
operator|||
name|propFullName
operator|.
name|equals
argument_list|(
name|predicateName
argument_list|)
condition|)
block|{
name|AnnotationType
name|annotation
init|=
name|getAnnotation
argument_list|(
name|propertyBridge
operator|.
name|getPropertyAnnotation
argument_list|()
argument_list|)
decl_stmt|;
name|processContentObjectProperty
argument_list|(
name|property
argument_list|,
name|propDef
argument_list|,
name|contentObject
argument_list|,
name|individual
argument_list|,
name|annotation
argument_list|,
name|engine
argument_list|)
expr_stmt|;
comment|// property found break the loop
break|break;
block|}
block|}
block|}
specifier|public
name|void
name|processContentObjectProperty
parameter_list|(
name|DProperty
name|property
parameter_list|,
name|DPropertyDefinition
name|propDef
parameter_list|,
name|DObject
name|contentObject
parameter_list|,
name|Individual
name|individual
parameter_list|,
name|AnnotationType
name|annotation
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
if|if
condition|(
name|objectPropertyCheck
argument_list|(
name|property
argument_list|)
condition|)
block|{
name|processObjectProperty
argument_list|(
name|individual
argument_list|,
name|contentObject
argument_list|,
name|annotation
argument_list|,
name|property
argument_list|,
name|propDef
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|datatypePropertyCheck
argument_list|(
name|property
argument_list|)
condition|)
block|{
name|processDataTypeProperty
argument_list|(
name|individual
argument_list|,
name|property
argument_list|,
name|propDef
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"{} property type is not supported yet"
argument_list|,
name|propDef
operator|.
name|getPropertyType
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|processObjectProperty
parameter_list|(
name|Individual
name|individual
parameter_list|,
name|DObject
name|contentObject
parameter_list|,
name|AnnotationType
name|annotation
parameter_list|,
name|DProperty
name|property
parameter_list|,
name|DPropertyDefinition
name|propDef
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
block|{
name|RepositoryAccess
name|accessor
init|=
name|engine
operator|.
name|getRepositoryAccess
argument_list|()
decl_stmt|;
name|Object
name|session
init|=
name|engine
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|OntologyResourceHelper
name|orh
init|=
name|engine
operator|.
name|getOntologyResourceHelper
argument_list|()
decl_stmt|;
comment|// get referenced values
name|List
argument_list|<
name|CMSObject
argument_list|>
name|referencedObjects
init|=
operator|new
name|ArrayList
argument_list|<
name|CMSObject
argument_list|>
argument_list|()
decl_stmt|;
name|CMSObject
name|parentObject
init|=
literal|null
decl_stmt|;
name|PropType
name|type
init|=
name|propDef
operator|.
name|getPropertyType
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|==
name|PropType
operator|.
name|REFERENCE
condition|)
block|{
for|for
control|(
name|String
name|referencedObject
range|:
name|property
operator|.
name|getValue
argument_list|()
control|)
block|{
try|try
block|{
name|parentObject
operator|=
name|accessor
operator|.
name|getFirstNodeById
argument_list|(
name|referencedObject
argument_list|,
name|session
argument_list|)
expr_stmt|;
if|if
condition|(
name|parentObject
operator|!=
literal|null
condition|)
block|{
name|referencedObjects
operator|.
name|add
argument_list|(
name|parentObject
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error while getting referenced value {} "
argument_list|,
name|referencedObject
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|PropType
operator|.
name|NAME
condition|)
block|{
for|for
control|(
name|String
name|referencedName
range|:
name|property
operator|.
name|getValue
argument_list|()
control|)
block|{
name|List
argument_list|<
name|CMSObject
argument_list|>
name|names
decl_stmt|;
try|try
block|{
name|names
operator|=
name|accessor
operator|.
name|getNodeByName
argument_list|(
name|referencedName
argument_list|,
name|session
argument_list|)
expr_stmt|;
name|referencedObjects
operator|.
name|addAll
argument_list|(
name|names
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error while getting referenced value {} "
argument_list|,
name|referencedName
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|PropType
operator|.
name|PATH
condition|)
block|{
for|for
control|(
name|String
name|referencedPath
range|:
name|property
operator|.
name|getValue
argument_list|()
control|)
block|{
try|try
block|{
name|parentObject
operator|=
name|accessor
operator|.
name|getFirstNodeByPath
argument_list|(
name|referencedPath
argument_list|,
name|session
argument_list|)
expr_stmt|;
if|if
condition|(
name|parentObject
operator|!=
literal|null
condition|)
block|{
name|referencedObjects
operator|.
name|add
argument_list|(
name|parentObject
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error while getting referenced value {} "
argument_list|,
name|referencedPath
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|ObjectProperty
name|objectProperty
init|=
literal|null
decl_stmt|;
try|try
block|{
name|objectProperty
operator|=
name|orh
operator|.
name|getObjectPropertyByReference
argument_list|(
name|propDef
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedPolymorphismException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Another type of resource has been created for the property definition: {}"
argument_list|,
name|propDef
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConversionException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Another type of resource has been created for the property definition: {}"
argument_list|,
name|propDef
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|objectProperty
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|annotation
operator|!=
literal|null
condition|)
block|{
name|processInstancePropertyBridgeAnnotation
argument_list|(
name|individual
argument_list|,
name|contentObject
argument_list|,
name|annotation
argument_list|,
name|objectProperty
argument_list|,
name|referencedObjects
argument_list|,
name|orh
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|createReferencedIndividuals
argument_list|(
name|individual
argument_list|,
name|contentObject
argument_list|,
name|objectProperty
argument_list|,
name|referencedObjects
argument_list|,
name|orh
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"There is no object property create property ref {}"
argument_list|,
name|propDef
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|createReferencedIndividuals
parameter_list|(
name|Individual
name|individual
parameter_list|,
name|DObject
name|contentObject
parameter_list|,
name|ObjectProperty
name|objectProperty
parameter_list|,
name|List
argument_list|<
name|CMSObject
argument_list|>
name|referencedObjects
parameter_list|,
name|OntologyResourceHelper
name|ontologyResourceHelper
parameter_list|)
block|{
if|if
condition|(
name|objectProperty
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"There is no suitable object property for reference"
argument_list|)
expr_stmt|;
return|return;
block|}
for|for
control|(
name|CMSObject
name|referencedObject
range|:
name|referencedObjects
control|)
block|{
name|OntResource
name|targetIndividualType
init|=
name|ontologyResourceHelper
operator|.
name|createOntClassByReference
argument_list|(
name|referencedObject
operator|.
name|getObjectTypeRef
argument_list|()
argument_list|)
decl_stmt|;
name|Individual
name|ind
init|=
name|ontologyResourceHelper
operator|.
name|createIndividualByCMSObject
argument_list|(
name|referencedObject
argument_list|,
name|targetIndividualType
argument_list|)
decl_stmt|;
if|if
condition|(
name|ind
operator|!=
literal|null
condition|)
block|{
name|individual
operator|.
name|addProperty
argument_list|(
name|objectProperty
argument_list|,
name|ind
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to create individual for referenced value {} while creating referenced individuals for {}"
argument_list|,
name|referencedObject
operator|.
name|getLocalname
argument_list|()
argument_list|,
name|contentObject
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|processDataTypeProperty
parameter_list|(
name|Individual
name|individual
parameter_list|,
name|DProperty
name|property
parameter_list|,
name|DPropertyDefinition
name|propDef
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
block|{
name|OntologyResourceHelper
name|orh
init|=
name|engine
operator|.
name|getOntologyResourceHelper
argument_list|()
decl_stmt|;
name|OntModel
name|ontModel
init|=
name|engine
operator|.
name|getOntModel
argument_list|()
decl_stmt|;
name|DatatypeProperty
name|datatypeProperty
init|=
literal|null
decl_stmt|;
try|try
block|{
name|datatypeProperty
operator|=
name|orh
operator|.
name|getDatatypePropertyByReference
argument_list|(
name|propDef
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedPolymorphismException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Another type of resource has been created for the property definition: {}"
argument_list|,
name|propDef
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
catch|catch
parameter_list|(
name|ConversionException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Another type of resource has been created for the property definition: {}"
argument_list|,
name|propDef
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|datatypeProperty
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|value
range|:
name|property
operator|.
name|getValue
argument_list|()
control|)
block|{
name|individual
operator|.
name|setPropertyValue
argument_list|(
name|datatypeProperty
argument_list|,
name|ontModel
operator|.
name|createTypedLiteral
argument_list|(
name|value
argument_list|,
name|datatypeProperty
operator|.
name|getRange
argument_list|()
operator|.
name|getURI
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"There is no datatype property create property ref {}"
argument_list|,
name|propDef
operator|.
name|getUniqueRef
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|processInstancePropertyBridgeAnnotation
parameter_list|(
name|Individual
name|containerIndividual
parameter_list|,
name|DObject
name|contentObject
parameter_list|,
name|AnnotationType
name|annotation
parameter_list|,
name|ObjectProperty
name|property
parameter_list|,
name|List
argument_list|<
name|CMSObject
argument_list|>
name|referencedObjects
parameter_list|,
name|OntologyResourceHelper
name|orh
parameter_list|)
block|{
comment|// process the annotations
if|if
condition|(
operator|!
name|annotation
operator|.
name|equals
argument_list|(
name|AnnotationType
operator|.
name|INSTANCE_OF
argument_list|)
condition|)
block|{
if|if
condition|(
name|annotation
operator|.
name|equals
argument_list|(
name|AnnotationType
operator|.
name|FUNCTIONAL
argument_list|)
condition|)
block|{
name|property
operator|.
name|convertToFunctionalProperty
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|annotation
operator|.
name|equals
argument_list|(
name|AnnotationType
operator|.
name|INVERSE_FUNCTIONAL
argument_list|)
condition|)
block|{
name|property
operator|.
name|convertToInverseFunctionalProperty
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|annotation
operator|.
name|equals
argument_list|(
name|AnnotationType
operator|.
name|SYMMETRIC
argument_list|)
condition|)
block|{
name|property
operator|.
name|convertToSymmetricProperty
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|annotation
operator|.
name|equals
argument_list|(
name|AnnotationType
operator|.
name|TRANSITIVE
argument_list|)
condition|)
block|{
name|property
operator|.
name|convertToTransitiveProperty
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"{} annotation is not supported for this property"
argument_list|,
name|annotation
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|createReferencedIndividuals
argument_list|(
name|containerIndividual
argument_list|,
name|contentObject
argument_list|,
name|property
argument_list|,
name|referencedObjects
argument_list|,
name|orh
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|annotation
operator|.
name|equals
argument_list|(
name|AnnotationType
operator|.
name|INSTANCE_OF
argument_list|)
condition|)
block|{
name|OntClass
name|parentClass
decl_stmt|;
for|for
control|(
name|CMSObject
name|parent
range|:
name|referencedObjects
control|)
block|{
name|parentClass
operator|=
name|orh
operator|.
name|createOntClassByCMSObject
argument_list|(
name|parent
argument_list|)
expr_stmt|;
if|if
condition|(
name|parentClass
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to create OntClass for CMS Object {}"
argument_list|,
name|parent
operator|.
name|getLocalname
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|containerIndividual
operator|.
name|addOntClass
argument_list|(
name|parentClass
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
specifier|static
name|Boolean
name|objectPropertyCheck
parameter_list|(
name|DProperty
name|prop
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|PropType
name|propType
init|=
name|prop
operator|.
name|getDefinition
argument_list|()
operator|.
name|getPropertyType
argument_list|()
decl_stmt|;
comment|// TODO consider all object properties
if|if
condition|(
name|propType
operator|==
name|PropType
operator|.
name|REFERENCE
operator|||
name|propType
operator|==
name|PropType
operator|.
name|PATH
operator|||
name|propType
operator|==
name|PropType
operator|.
name|NAME
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|private
specifier|static
name|Boolean
name|datatypePropertyCheck
parameter_list|(
name|DProperty
name|prop
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|PropType
name|propType
init|=
name|prop
operator|.
name|getDefinition
argument_list|()
operator|.
name|getPropertyType
argument_list|()
decl_stmt|;
comment|// TODO consider all data type properties
if|if
condition|(
name|propType
operator|==
name|PropType
operator|.
name|STRING
operator|||
name|propType
operator|==
name|PropType
operator|.
name|BOOLEAN
operator|||
name|propType
operator|==
name|PropType
operator|.
name|BINARY
operator|||
name|propType
operator|==
name|PropType
operator|.
name|DATE
operator|||
name|propType
operator|==
name|PropType
operator|.
name|DOUBLE
operator|||
name|propType
operator|==
name|PropType
operator|.
name|LONG
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|private
specifier|static
name|AnnotationType
name|getAnnotation
parameter_list|(
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
name|model
operator|.
name|mapping
operator|.
name|PropertyAnnotation
name|propertyAnnotation
parameter_list|)
block|{
if|if
condition|(
name|propertyAnnotation
operator|==
literal|null
operator|||
name|propertyAnnotation
operator|.
name|getAnnotation
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
name|AnnotationType
operator|.
name|fromValue
argument_list|(
name|propertyAnnotation
operator|.
name|getAnnotation
argument_list|()
operator|.
name|value
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

