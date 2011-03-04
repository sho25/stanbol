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
name|SPARQLComparison
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
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|apibinding
operator|.
name|OWLManager
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
name|OWLLiteral
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
name|GreaterThanAtom
extends|extends
name|ComparisonAtom
block|{
specifier|private
name|Object
name|argument1
decl_stmt|;
specifier|private
name|Object
name|argument2
decl_stmt|;
specifier|public
name|GreaterThanAtom
parameter_list|(
name|Object
name|argument1
parameter_list|,
name|Object
name|argument2
parameter_list|)
block|{
name|this
operator|.
name|argument1
operator|=
name|argument1
expr_stmt|;
name|this
operator|.
name|argument2
operator|=
name|argument2
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
name|arg1
init|=
name|argument1
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|arg2
init|=
name|argument2
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|arg1
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|arg1
operator|=
literal|"str(?"
operator|+
name|arg1
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
operator|+
literal|")"
expr_stmt|;
block|}
else|else
block|{
name|arg1
operator|=
literal|"str("
operator|+
name|arg1
operator|+
literal|")"
expr_stmt|;
block|}
if|if
condition|(
name|arg2
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|arg2
operator|=
literal|"str(?"
operator|+
name|arg2
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
operator|+
literal|")"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|arg2
operator|.
name|startsWith
argument_list|(
literal|"<"
argument_list|)
operator|&&
operator|!
name|arg2
operator|.
name|endsWith
argument_list|(
literal|">"
argument_list|)
condition|)
block|{
name|OWLLiteral
name|literal
init|=
name|getOWLTypedLiteral
argument_list|(
name|argument2
argument_list|)
decl_stmt|;
name|arg2
operator|=
literal|"str("
operator|+
name|literal
operator|.
name|getLiteral
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
else|else
block|{
name|arg2
operator|=
literal|"str("
operator|+
name|arg2
operator|+
literal|")"
expr_stmt|;
block|}
return|return
operator|new
name|SPARQLComparison
argument_list|(
name|arg1
operator|+
literal|"> "
operator|+
name|arg2
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
name|arg1
init|=
literal|null
decl_stmt|;
name|String
name|arg2
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|argument1
operator|.
name|toString
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|arg1
operator|=
literal|"?"
operator|+
name|argument1
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
expr_stmt|;
block|}
else|else
block|{
name|arg1
operator|=
name|argument1
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|argument2
operator|.
name|toString
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|arg2
operator|=
literal|"?"
operator|+
name|argument2
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
expr_stmt|;
return|return
literal|"gt("
operator|+
name|arg1
operator|+
literal|", "
operator|+
name|arg2
operator|+
literal|")"
return|;
block|}
else|else
block|{
return|return
literal|"gt("
operator|+
name|arg1
operator|+
literal|", "
operator|+
name|argument2
operator|.
name|toString
argument_list|()
operator|+
literal|")"
return|;
block|}
block|}
specifier|private
name|OWLLiteral
name|getOWLTypedLiteral
parameter_list|(
name|Object
name|argument
parameter_list|)
block|{
name|OWLDataFactory
name|factory
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLLiteral
name|owlLiteral
decl_stmt|;
if|if
condition|(
name|argument
operator|instanceof
name|String
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
name|String
operator|)
name|argument
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Integer
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Integer
operator|)
name|argument
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Double
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Double
operator|)
name|argument
operator|)
operator|.
name|doubleValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Float
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Float
operator|)
name|argument
operator|)
operator|.
name|floatValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument
operator|instanceof
name|Boolean
condition|)
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLTypedLiteral
argument_list|(
operator|(
operator|(
name|Boolean
operator|)
name|argument
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|owlLiteral
operator|=
name|factory
operator|.
name|getOWLStringLiteral
argument_list|(
name|argument
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|owlLiteral
return|;
block|}
block|}
end_class

end_unit

