begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|Arrays
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
name|commons
operator|.
name|lang
operator|.
name|RandomStringUtils
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
name|NamingStrategy
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
name|ObjectTypeDefinition
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
name|PropertyDefinition
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
name|rdf
operator|.
name|model
operator|.
name|RDFList
import|;
end_import

begin_class
specifier|public
class|class
name|DefaultNamingStrategy
implements|implements
name|NamingStrategy
block|{
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
name|DefaultNamingStrategy
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Used to separate any path segment      */
specifier|public
specifier|static
specifier|final
name|Character
name|PATH_DELIMITER
init|=
literal|'/'
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TYPE_SEPARATOR
init|=
literal|"-"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CLASS_DELIMITER
init|=
literal|"class"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|INDIVIDUAL_DELIMITER
init|=
literal|"individual"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PROPERTY_DELIMITER
init|=
literal|"prop"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|OBJECT_PROPERTY_DELIMITER
init|=
literal|"oprop"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|DATA_PROPERTY_DELIMITER
init|=
literal|"dprop"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|UNION_CLASS_DELIMITER
init|=
literal|"unions"
decl_stmt|;
specifier|private
name|RepositoryAccess
name|repositoryAccess
decl_stmt|;
specifier|private
name|Object
name|session
decl_stmt|;
specifier|private
name|OntModel
name|processedModel
decl_stmt|;
specifier|public
name|DefaultNamingStrategy
parameter_list|(
name|RepositoryAccess
name|repositoryAccess
parameter_list|,
name|Object
name|session
parameter_list|,
name|OntModel
name|processedModel
parameter_list|)
block|{
name|this
operator|.
name|repositoryAccess
operator|=
name|repositoryAccess
expr_stmt|;
name|this
operator|.
name|session
operator|=
name|session
expr_stmt|;
name|this
operator|.
name|processedModel
operator|=
name|processedModel
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getClassName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|CMSObject
name|cmsObject
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|cmsObject
operator|.
name|getLocalname
argument_list|()
block|,
name|cmsObject
operator|.
name|getPath
argument_list|()
block|,
name|cmsObject
operator|.
name|getUniqueRef
argument_list|()
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|CLASS_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getClassName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|ObjectTypeDefinition
name|objectTypeDefinition
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|objectTypeDefinition
operator|.
name|getLocalname
argument_list|()
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|CLASS_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getClassName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|String
name|reference
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|reference
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|CLASS_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getIndividualName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|CMSObject
name|cmsObject
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|cmsObject
operator|.
name|getLocalname
argument_list|()
block|,
name|cmsObject
operator|.
name|getPath
argument_list|()
block|,
name|cmsObject
operator|.
name|getUniqueRef
argument_list|()
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|INDIVIDUAL_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getObjectPropertyName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|PropertyDefinition
name|propertyDefinition
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|propertyDefinition
operator|.
name|getLocalname
argument_list|()
block|,
name|propertyDefinition
operator|.
name|getUniqueRef
argument_list|()
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|OBJECT_PROPERTY_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDataPropertyName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|PropertyDefinition
name|propertyDefinition
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|propertyDefinition
operator|.
name|getLocalname
argument_list|()
block|,
name|propertyDefinition
operator|.
name|getUniqueRef
argument_list|()
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|DATA_PROPERTY_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getUnionClassURI
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|RDFList
name|list
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|RandomStringUtils
operator|.
name|randomAlphanumeric
argument_list|(
literal|4
argument_list|)
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|UNION_CLASS_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getIndividualName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|String
name|reference
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|reference
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|INDIVIDUAL_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPropertyName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|String
name|reference
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|reference
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|PROPERTY_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getObjectPropertyName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|String
name|reference
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|reference
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|OBJECT_PROPERTY_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDataPropertyName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|String
name|reference
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|candidateNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
name|reference
block|}
argument_list|)
decl_stmt|;
return|return
name|getAvailableResourceName
argument_list|(
name|ontologyURI
argument_list|,
name|candidateNames
argument_list|,
name|DATA_PROPERTY_DELIMITER
argument_list|)
return|;
block|}
specifier|private
name|String
name|getAvailableResourceName
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|candidates
parameter_list|,
name|String
name|resourceDelimiter
parameter_list|)
block|{
name|ResourceNameRepresentation
name|nameRep
decl_stmt|;
name|StringBuilder
name|sb
decl_stmt|;
name|String
name|name
decl_stmt|;
for|for
control|(
name|String
name|candidate
range|:
name|candidates
control|)
block|{
name|nameRep
operator|=
name|getPrecedingBaseURI
argument_list|(
name|candidate
argument_list|,
name|ontologyURI
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuilder
argument_list|(
name|nameRep
operator|.
name|getResourceBaseURI
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|resourceDelimiter
argument_list|)
operator|.
name|append
argument_list|(
name|TYPE_SEPARATOR
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|nameRep
operator|.
name|getResourceName
argument_list|()
argument_list|)
expr_stmt|;
name|name
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|processedModel
operator|.
name|getOntResource
argument_list|(
name|name
argument_list|)
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Generated resource name {} from {}."
argument_list|,
name|name
argument_list|,
name|candidate
argument_list|)
expr_stmt|;
return|return
name|name
return|;
block|}
block|}
name|log
operator|.
name|warn
argument_list|(
literal|"No suitable URI produced for candidates {}."
argument_list|,
name|candidates
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
specifier|private
name|ResourceNameRepresentation
name|getPrecedingBaseURI
parameter_list|(
name|String
name|resourceName
parameter_list|,
name|String
name|ontologyURI
parameter_list|)
block|{
name|ResourceNameRepresentation
name|resourceNameRep
init|=
operator|new
name|ResourceNameRepresentation
argument_list|()
decl_stmt|;
if|if
condition|(
name|resourceName
operator|.
name|contains
argument_list|(
literal|":"
argument_list|)
condition|)
block|{
name|String
name|newURI
decl_stmt|;
name|String
name|prefix
init|=
name|detectPrefix
argument_list|(
name|resourceName
argument_list|)
decl_stmt|;
name|resourceNameRep
operator|.
name|setResourceName
argument_list|(
name|resourceName
argument_list|)
expr_stmt|;
comment|// first try to resolve prefix through processed OntModel
name|newURI
operator|=
name|processedModel
operator|.
name|getNsPrefixURI
argument_list|(
name|prefix
argument_list|)
expr_stmt|;
if|if
condition|(
name|newURI
operator|!=
literal|null
condition|)
block|{
name|resourceNameRep
operator|.
name|setResourceBaseURI
argument_list|(
name|OntologyResourceHelper
operator|.
name|addResourceDelimiter
argument_list|(
name|newURI
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|resourceNameRep
return|;
block|}
comment|// second try to resolve prefix through content repository itself
if|if
condition|(
name|repositoryAccess
operator|!=
literal|null
operator|&&
name|session
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|newURI
operator|=
name|repositoryAccess
operator|.
name|getNamespaceURI
argument_list|(
name|prefix
argument_list|,
name|session
argument_list|)
expr_stmt|;
if|if
condition|(
name|newURI
operator|!=
literal|null
condition|)
block|{
name|resourceNameRep
operator|.
name|setResourceBaseURI
argument_list|(
name|OntologyResourceHelper
operator|.
name|addResourceDelimiter
argument_list|(
name|newURI
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|resourceNameRep
return|;
block|}
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Cannot access repository."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|// prefix still cannot be resolved, create a dummy URI
name|newURI
operator|=
name|CMSAdapterVocabulary
operator|.
name|DEFAULT_NS_URI
operator|+
name|PATH_DELIMITER
operator|+
name|prefix
expr_stmt|;
name|resourceNameRep
operator|.
name|setResourceBaseURI
argument_list|(
name|OntologyResourceHelper
operator|.
name|addResourceDelimiter
argument_list|(
name|newURI
argument_list|)
argument_list|)
expr_stmt|;
name|processedModel
operator|.
name|setNsPrefix
argument_list|(
name|prefix
argument_list|,
name|newURI
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"Cannot find URI for prefix {}. Created dummy URI {}."
argument_list|,
name|prefix
argument_list|,
name|newURI
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|resourceNameRep
operator|.
name|setResourceName
argument_list|(
name|resourceName
argument_list|)
expr_stmt|;
name|resourceNameRep
operator|.
name|setResourceBaseURI
argument_list|(
name|OntologyResourceHelper
operator|.
name|addResourceDelimiter
argument_list|(
name|ontologyURI
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|resourceNameRep
return|;
block|}
specifier|private
name|String
name|detectPrefix
parameter_list|(
name|String
name|resourceName
parameter_list|)
block|{
name|int
name|colonIndex
init|=
name|resourceName
operator|.
name|lastIndexOf
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
name|String
name|prefix
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|colonIndex
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
operator|&&
name|resourceName
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|!=
name|PATH_DELIMITER
condition|;
name|i
operator|--
control|)
block|{
name|prefix
operator|=
name|resourceName
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|+
name|prefix
expr_stmt|;
block|}
return|return
name|prefix
return|;
block|}
block|}
end_class

begin_class
class|class
name|ResourceNameRepresentation
block|{
specifier|private
name|String
name|resourceName
decl_stmt|;
specifier|private
name|String
name|resourceBaseURI
decl_stmt|;
specifier|public
name|void
name|setResourceName
parameter_list|(
name|String
name|resourceName
parameter_list|)
block|{
name|this
operator|.
name|resourceName
operator|=
name|resourceName
expr_stmt|;
block|}
specifier|public
name|String
name|getResourceName
parameter_list|()
block|{
return|return
name|this
operator|.
name|resourceName
return|;
block|}
specifier|public
name|void
name|setResourceBaseURI
parameter_list|(
name|String
name|resourceBaseURI
parameter_list|)
block|{
name|this
operator|.
name|resourceBaseURI
operator|=
name|resourceBaseURI
expr_stmt|;
block|}
specifier|public
name|String
name|getResourceBaseURI
parameter_list|()
block|{
return|return
name|this
operator|.
name|resourceBaseURI
return|;
block|}
block|}
end_class

end_unit

