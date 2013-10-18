begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|query
operator|.
name|clerezza
package|;
end_package

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
name|TripleCollection
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
name|stanbol
operator|.
name|entityhub
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfRepresentation
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
name|model
operator|.
name|clerezza
operator|.
name|RdfValueFactory
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
name|query
operator|.
name|sparql
operator|.
name|SparqlQueryUtils
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
name|rdf
operator|.
name|RdfResourceEnum
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
name|util
operator|.
name|AdaptingIterator
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
comment|/**  * Provides Clerezza specific Query utilities that are in addition to those  * provided by {@link SparqlQueryUtils}.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|ClerezzaQueryUtils
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
name|ClerezzaQueryUtils
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|RdfValueFactory
name|valueFavtory
init|=
name|RdfValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
comment|/**      * {@link UriRef} constant for {@link RdfResourceEnum#queryResult}      *       * @see RdfResourceEnum.fieldQueryResult      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FIELD_QUERY_RESULT
init|=
operator|new
name|UriRef
argument_list|(
name|RdfResourceEnum
operator|.
name|queryResult
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * {@link UriRef} constant for {@link RdfResourceEnum#QueryResultSet}      *       * @see RdfResourceEnum.FieldQueryResultSet      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FIELD_QUERY_RESULT_SET
init|=
operator|new
name|UriRef
argument_list|(
name|RdfResourceEnum
operator|.
name|QueryResultSet
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * @param query      * @param resultGraph      * @return      */
specifier|public
specifier|static
name|Iterator
argument_list|<
name|RdfRepresentation
argument_list|>
name|parseQueryResultsFromMGraph
parameter_list|(
specifier|final
name|TripleCollection
name|resultGraph
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|resultTripleIterator
init|=
name|resultGraph
operator|.
name|filter
argument_list|(
name|FIELD_QUERY_RESULT_SET
argument_list|,
name|FIELD_QUERY_RESULT
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|RdfRepresentation
argument_list|>
name|resultIterator
init|=
operator|new
name|AdaptingIterator
argument_list|<
name|Triple
argument_list|,
name|RdfRepresentation
argument_list|>
argument_list|(
name|resultTripleIterator
argument_list|,
operator|new
name|AdaptingIterator
operator|.
name|Adapter
argument_list|<
name|Triple
argument_list|,
name|RdfRepresentation
argument_list|>
argument_list|()
block|{
comment|/*                      * Anonymous implementation of an Adapter that converts the filtered Triples of the                      * resulting graph to RdfRepresentations                      */
annotation|@
name|Override
specifier|public
name|RdfRepresentation
name|adapt
parameter_list|(
name|Triple
name|value
parameter_list|,
name|Class
argument_list|<
name|RdfRepresentation
argument_list|>
name|type
parameter_list|)
block|{
name|Resource
name|object
init|=
name|value
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|object
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|object
operator|instanceof
name|UriRef
condition|)
block|{
return|return
name|valueFavtory
operator|.
name|createRdfRepresentation
argument_list|(
operator|(
name|UriRef
operator|)
name|object
argument_list|,
name|resultGraph
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to create representation for FieldQueryResult "
operator|+
name|object
operator|+
literal|" because this Resource is not of Type UriRef (type: "
operator|+
name|object
operator|.
name|getClass
argument_list|()
operator|+
literal|") -> result gets ignored"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
argument_list|,
name|RdfRepresentation
operator|.
name|class
argument_list|)
decl_stmt|;
return|return
name|resultIterator
return|;
block|}
block|}
end_class

end_unit
