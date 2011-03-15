begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|rules
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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|rules
operator|.
name|SPARQLFunction
import|;
end_import

begin_class
specifier|public
class|class
name|SubstringAtom
extends|extends
name|StringFunctionAtom
block|{
specifier|private
name|StringFunctionAtom
name|stringFunctionAtom
decl_stmt|;
specifier|private
name|NumericFunctionAtom
name|start
decl_stmt|;
specifier|private
name|NumericFunctionAtom
name|length
decl_stmt|;
specifier|public
name|SubstringAtom
parameter_list|(
name|StringFunctionAtom
name|stringFunctionAtom
parameter_list|,
name|NumericFunctionAtom
name|start
parameter_list|,
name|NumericFunctionAtom
name|length
parameter_list|)
block|{
name|this
operator|.
name|stringFunctionAtom
operator|=
name|stringFunctionAtom
expr_stmt|;
name|this
operator|.
name|start
operator|=
name|start
expr_stmt|;
name|this
operator|.
name|length
operator|=
name|length
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
name|uriResourceString
init|=
name|stringFunctionAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|uriResourceString
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|uriResourceString
operator|=
literal|"?"
operator|+
name|uriResourceString
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
literal|"<http://jena.hpl.hp.com/ARQ/function#substr> ("
operator|+
name|uriResourceString
operator|+
literal|", "
operator|+
name|start
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
operator|+
literal|", "
operator|+
name|length
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
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
name|uriResourceString
init|=
name|stringFunctionAtom
operator|.
name|toKReSSyntax
argument_list|()
decl_stmt|;
if|if
condition|(
name|uriResourceString
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|uriResourceString
operator|=
literal|"?"
operator|+
name|uriResourceString
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
literal|"substring("
operator|+
name|uriResourceString
operator|+
literal|", "
operator|+
name|start
operator|.
name|toKReSSyntax
argument_list|()
operator|+
literal|", "
operator|+
name|length
operator|.
name|toKReSSyntax
argument_list|()
operator|+
literal|")"
return|;
block|}
block|}
end_class

end_unit

