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
name|api
operator|.
name|rules
operator|.
name|KReSRuleAtom
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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|rules
operator|.
name|SPARQLObject
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
name|api
operator|.
name|rules
operator|.
name|URIResource
import|;
end_import

begin_class
specifier|public
class|class
name|LetAtom
implements|implements
name|KReSRuleAtom
block|{
specifier|private
name|URIResource
name|variable
decl_stmt|;
specifier|private
name|StringFunctionAtom
name|parameterFunctionAtom
decl_stmt|;
specifier|public
name|LetAtom
parameter_list|(
name|URIResource
name|variable
parameter_list|,
name|StringFunctionAtom
name|parameterFunctionAtom
parameter_list|)
block|{
name|this
operator|.
name|variable
operator|=
name|variable
expr_stmt|;
name|this
operator|.
name|parameterFunctionAtom
operator|=
name|parameterFunctionAtom
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Parameter Function : "
operator|+
name|parameterFunctionAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
name|String
name|variableArgument
init|=
name|variable
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|String
name|let
init|=
literal|"LET (?"
operator|+
name|variableArgument
operator|+
literal|" := "
operator|+
name|parameterFunctionAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
operator|+
literal|")"
decl_stmt|;
name|SPARQLObject
name|sparqlObject
init|=
operator|new
name|SPARQLFunction
argument_list|(
name|let
argument_list|)
decl_stmt|;
return|return
name|sparqlObject
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
name|arg1
init|=
literal|"?"
operator|+
name|variable
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|String
name|syntax
init|=
literal|"let("
operator|+
name|arg1
operator|+
literal|", "
operator|+
name|parameterFunctionAtom
operator|.
name|toKReSSyntax
argument_list|()
operator|+
literal|")"
decl_stmt|;
return|return
name|syntax
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLConstruct
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDelete
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDeleteData
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

