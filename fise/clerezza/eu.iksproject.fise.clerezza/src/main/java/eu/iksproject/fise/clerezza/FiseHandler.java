begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|clerezza
package|;
end_package

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|Store
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|FormParam
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|POST
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|GET
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|QueryParam
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
name|Reference
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|NoSuchEntityException
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
name|access
operator|.
name|TcManager
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
name|sparql
operator|.
name|ParseException
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
name|sparql
operator|.
name|QueryParser
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
name|sparql
operator|.
name|ResultSet
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
name|sparql
operator|.
name|query
operator|.
name|SelectQuery
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
name|utils
operator|.
name|UnionMGraph
import|;
end_import

begin_comment
comment|/**  *  * @author mir  */
end_comment

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|Object
operator|.
name|class
argument_list|)
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"javax.ws.rs"
argument_list|,
name|boolValue
operator|=
literal|true
argument_list|)
annotation|@
name|Path
argument_list|(
literal|"/fise"
argument_list|)
specifier|public
class|class
name|FiseHandler
block|{
annotation|@
name|Reference
name|Store
name|store
decl_stmt|;
annotation|@
name|Reference
name|TcManager
name|tcManager
decl_stmt|;
annotation|@
name|GET
specifier|public
name|MGraph
name|getFiseMetadata
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"uri"
argument_list|)
name|UriRef
name|uri
parameter_list|)
block|{
return|return
name|store
operator|.
name|get
argument_list|(
name|uri
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
operator|.
name|getMetadata
argument_list|()
return|;
block|}
annotation|@
name|POST
specifier|public
name|ResultSet
name|sparql
parameter_list|(
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"query"
argument_list|)
name|String
name|sqarqlQuery
parameter_list|)
throws|throws
name|ParseException
block|{
name|SelectQuery
name|query
init|=
operator|(
name|SelectQuery
operator|)
name|QueryParser
operator|.
name|getInstance
argument_list|()
operator|.
name|parse
argument_list|(
name|sqarqlQuery
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|UriRef
argument_list|>
name|graphUris
init|=
name|tcManager
operator|.
name|listTripleCollections
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|TripleCollection
argument_list|>
name|tripleCollections
init|=
operator|new
name|ArrayList
argument_list|<
name|TripleCollection
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|UriRef
name|uriRef
range|:
name|graphUris
control|)
block|{
try|try
block|{
name|tripleCollections
operator|.
name|add
argument_list|(
name|tcManager
operator|.
name|getTriples
argument_list|(
name|uriRef
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchEntityException
name|ex
parameter_list|)
block|{
continue|continue;
block|}
block|}
name|MGraph
name|unionGraph
init|=
operator|new
name|UnionMGraph
argument_list|(
name|tripleCollections
operator|.
name|toArray
argument_list|(
operator|new
name|TripleCollection
index|[
literal|0
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|ResultSet
name|resultSet
init|=
name|tcManager
operator|.
name|executeSparqlQuery
argument_list|(
name|query
argument_list|,
name|unionGraph
argument_list|)
decl_stmt|;
return|return
name|resultSet
return|;
block|}
block|}
end_class

end_unit

