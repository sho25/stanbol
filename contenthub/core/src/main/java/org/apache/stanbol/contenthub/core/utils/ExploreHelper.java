begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|core
operator|.
name|utils
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
name|HashMap
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
name|query
operator|.
name|QueryExecutionFactory
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
name|query
operator|.
name|QuerySolution
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
name|query
operator|.
name|ResultSet
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
name|RDFNode
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
name|impl
operator|.
name|Util
import|;
end_import

begin_comment
comment|/**  * This class is constructed with an rdf model that will be queried and extracts  * semantically related entities according to the entity type's  *   * @author srdc  *   */
end_comment

begin_class
specifier|public
class|class
name|ExploreHelper
block|{
specifier|private
specifier|static
specifier|final
name|String
name|DBPEDIA_PLACE
init|=
literal|"http://dbpedia.org/ontology/place"
decl_stmt|;
specifier|private
name|OntModel
name|entityModel
decl_stmt|;
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
name|ExploreHelper
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|ExploreHelper
parameter_list|(
name|OntModel
name|model
parameter_list|)
block|{
name|entityModel
operator|=
name|model
expr_stmt|;
block|}
comment|/** 	 * finds the all rdf:type property value of the entity 	 *  	 * @return the list of all rdf:type property values 	 */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|extractTypes
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|types
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|entityModel
operator|!=
literal|null
condition|)
block|{
name|String
name|queryString
init|=
name|ExploreQueryHelper
operator|.
name|entityTypeExtracterQuery
argument_list|()
decl_stmt|;
name|ResultSet
name|resultSet
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|queryString
argument_list|,
name|entityModel
argument_list|)
operator|.
name|execSelect
argument_list|()
decl_stmt|;
while|while
condition|(
name|resultSet
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|QuerySolution
name|solution
init|=
name|resultSet
operator|.
name|next
argument_list|()
decl_stmt|;
name|RDFNode
name|node
init|=
name|solution
operator|.
name|get
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
name|types
operator|.
name|add
argument_list|(
name|node
operator|.
name|toString
argument_list|()
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
literal|"There is no entity model to query"
argument_list|)
expr_stmt|;
block|}
return|return
name|types
return|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|getSuggestedKeywords
parameter_list|()
block|{
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|suggestedKeywords
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|place
init|=
name|findRelatedPlaceEntities
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|person
init|=
name|findRelatedPersonEntities
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|organization
init|=
name|findRelatedOrganizationEntities
argument_list|()
decl_stmt|;
name|suggestedKeywords
operator|.
name|put
argument_list|(
literal|"place"
argument_list|,
name|place
argument_list|)
expr_stmt|;
name|suggestedKeywords
operator|.
name|put
argument_list|(
literal|"organization"
argument_list|,
name|organization
argument_list|)
expr_stmt|;
name|suggestedKeywords
operator|.
name|put
argument_list|(
literal|"person"
argument_list|,
name|person
argument_list|)
expr_stmt|;
return|return
name|suggestedKeywords
return|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|findRelatedPlaceEntities
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|entityModel
operator|!=
literal|null
condition|)
block|{
name|String
name|query
init|=
name|ExploreQueryHelper
operator|.
name|relatedPlaceQuery
argument_list|()
decl_stmt|;
name|ResultSet
name|resultSet
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|entityModel
argument_list|)
operator|.
name|execSelect
argument_list|()
decl_stmt|;
while|while
condition|(
name|resultSet
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|QuerySolution
name|sol
init|=
name|resultSet
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
index|[]
name|variables
init|=
name|ExploreQueryHelper
operator|.
name|placeTypedProperties
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|variables
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|variable
init|=
name|variables
index|[
name|i
index|]
decl_stmt|;
name|RDFNode
name|resultNode
init|=
name|sol
operator|.
name|get
argument_list|(
name|variable
argument_list|)
decl_stmt|;
if|if
condition|(
name|resultNode
operator|!=
literal|null
condition|)
block|{
name|String
name|resultURI
init|=
name|resultNode
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|entityName
init|=
name|resultURI
operator|.
name|substring
argument_list|(
name|Util
operator|.
name|splitNamespace
argument_list|(
name|resultURI
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|entityName
operator|!=
literal|null
operator|&&
operator|!
name|entityName
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|entityName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
return|return
name|result
return|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|findRelatedPersonEntities
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|entityModel
operator|!=
literal|null
condition|)
block|{
name|String
name|query
init|=
name|ExploreQueryHelper
operator|.
name|relatedPersonQuery
argument_list|()
decl_stmt|;
name|ResultSet
name|resultSet
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|entityModel
argument_list|)
operator|.
name|execSelect
argument_list|()
decl_stmt|;
while|while
condition|(
name|resultSet
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|QuerySolution
name|sol
init|=
name|resultSet
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
index|[]
name|variables
init|=
name|ExploreQueryHelper
operator|.
name|personTypedProperties
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|variables
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|variable
init|=
name|variables
index|[
name|i
index|]
decl_stmt|;
name|RDFNode
name|resultNode
init|=
name|sol
operator|.
name|get
argument_list|(
name|variable
argument_list|)
decl_stmt|;
if|if
condition|(
name|resultNode
operator|!=
literal|null
condition|)
block|{
name|String
name|resultURI
init|=
name|resultNode
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|entityName
init|=
name|resultURI
operator|.
name|substring
argument_list|(
name|Util
operator|.
name|splitNamespace
argument_list|(
name|resultURI
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|entityName
operator|!=
literal|null
operator|&&
operator|!
name|entityName
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|entityName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
return|return
name|result
return|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|findRelatedOrganizationEntities
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|entityModel
operator|!=
literal|null
condition|)
block|{
name|String
name|query
init|=
name|ExploreQueryHelper
operator|.
name|relatedOrganizationQuery
argument_list|()
decl_stmt|;
name|ResultSet
name|resultSet
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|entityModel
argument_list|)
operator|.
name|execSelect
argument_list|()
decl_stmt|;
while|while
condition|(
name|resultSet
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|QuerySolution
name|sol
init|=
name|resultSet
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
index|[]
name|variables
init|=
name|ExploreQueryHelper
operator|.
name|organizationTypedProperties
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|variables
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|variable
init|=
name|variables
index|[
name|i
index|]
decl_stmt|;
name|RDFNode
name|resultNode
init|=
name|sol
operator|.
name|get
argument_list|(
name|variable
argument_list|)
decl_stmt|;
if|if
condition|(
name|resultNode
operator|!=
literal|null
condition|)
block|{
name|String
name|resultURI
init|=
name|resultNode
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|entityName
init|=
name|resultURI
operator|.
name|substring
argument_list|(
name|Util
operator|.
name|splitNamespace
argument_list|(
name|resultURI
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|entityName
operator|!=
literal|null
operator|&&
operator|!
name|entityName
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|entityName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit
