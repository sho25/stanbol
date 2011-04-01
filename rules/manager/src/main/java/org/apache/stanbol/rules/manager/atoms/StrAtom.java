begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|manager
operator|.
name|atoms
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|SPARQLObject
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|URIResource
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
name|rules
operator|.
name|manager
operator|.
name|SPARQLFunction
import|;
end_import

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
name|OWLDataFactory
import|;
end_import

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
name|SWRLAtom
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
name|Model
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
name|Resource
import|;
end_import

begin_class
specifier|public
class|class
name|StrAtom
extends|extends
name|StringFunctionAtom
block|{
specifier|private
name|URIResource
name|uriResource
decl_stmt|;
specifier|public
name|StrAtom
parameter_list|(
name|URIResource
name|uriResource
parameter_list|)
block|{
name|this
operator|.
name|uriResource
operator|=
name|uriResource
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|toSWRL
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|SPARQLObject
name|toSPARQL
parameter_list|()
block|{
name|String
name|argument
init|=
name|uriResource
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|argument
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|argument
operator|=
literal|"?"
operator|+
name|argument
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
name|String
name|sparql
init|=
literal|"str("
operator|+
name|argument
operator|+
literal|")"
decl_stmt|;
return|return
operator|new
name|SPARQLFunction
argument_list|(
name|sparql
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|SWRLAtom
name|toSWRL
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toKReSSyntax
parameter_list|()
block|{
name|String
name|argument
init|=
name|uriResource
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|argument
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|argument
operator|=
literal|"?"
operator|+
name|argument
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
name|String
name|kReSSyntax
init|=
literal|"str("
operator|+
name|argument
operator|+
literal|")"
decl_stmt|;
return|return
name|kReSSyntax
return|;
block|}
block|}
end_class

end_unit

