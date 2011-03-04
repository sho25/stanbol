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
name|java
operator|.
name|net
operator|.
name|URI
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
name|IRI
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
name|OWLDataProperty
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
name|OWLIndividual
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
name|OWLObjectProperty
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
name|OWLTypedLiteral
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
name|SWRLArgument
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
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|SWRLDArgument
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
name|SWRLIArgument
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
name|SWRLLiteralArgument
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
name|SWRLVariable
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
name|Literal
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
name|SPARQLNot
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
name|rules
operator|.
name|SPARQLTriple
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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|ontologies
operator|.
name|SWRL
import|;
end_import

begin_class
specifier|public
class|class
name|DatavaluedPropertyAtom
extends|extends
name|KReSCoreAtom
block|{
specifier|private
name|URIResource
name|datatypeProperty
decl_stmt|;
specifier|private
name|URIResource
name|argument1
decl_stmt|;
specifier|private
name|Object
name|argument2
decl_stmt|;
specifier|public
name|DatavaluedPropertyAtom
parameter_list|(
name|URIResource
name|datatypeProperty
parameter_list|,
name|URIResource
name|argument1
parameter_list|,
name|Object
name|argument2
parameter_list|)
block|{
name|this
operator|.
name|datatypeProperty
operator|=
name|datatypeProperty
expr_stmt|;
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
name|String
name|dtP
init|=
name|datatypeProperty
operator|.
name|toString
argument_list|()
decl_stmt|;
name|boolean
name|negativeArg1
init|=
literal|false
decl_stmt|;
name|boolean
name|negativeArg2
init|=
literal|false
decl_stmt|;
name|boolean
name|negativeDtP
init|=
literal|false
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
literal|"?"
operator|+
name|arg1
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|KReSVariable
name|variable
init|=
operator|(
name|KReSVariable
operator|)
name|argument1
decl_stmt|;
name|negativeArg1
operator|=
name|variable
operator|.
name|isNegative
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|dtP
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|dtP
operator|=
literal|"?"
operator|+
name|dtP
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|KReSVariable
name|variable
init|=
operator|(
name|KReSVariable
operator|)
name|datatypeProperty
decl_stmt|;
name|negativeDtP
operator|=
name|variable
operator|.
name|isNegative
argument_list|()
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
literal|"?"
operator|+
name|arg2
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|KReSVariable
name|variable
init|=
operator|(
name|KReSVariable
operator|)
name|argument2
decl_stmt|;
name|negativeArg2
operator|=
name|variable
operator|.
name|isNegative
argument_list|()
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|argument2
operator|instanceof
name|String
condition|)
block|{
name|arg2
operator|=
name|argument2
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument2
operator|instanceof
name|Integer
condition|)
block|{
name|arg2
operator|=
operator|(
operator|(
name|Integer
operator|)
name|argument2
operator|)
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument2
operator|instanceof
name|KReSTypedLiteral
condition|)
block|{
name|KReSTypedLiteral
name|kReSTypeLiteral
init|=
operator|(
name|KReSTypedLiteral
operator|)
name|argument2
decl_stmt|;
name|Object
name|value
init|=
name|kReSTypeLiteral
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|String
name|xsdType
init|=
name|kReSTypeLiteral
operator|.
name|getXsdType
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"TYPED LITERAL : "
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"        value : "
operator|+
name|value
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"        xsd type : "
operator|+
name|xsdType
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|arg2
operator|=
name|value
operator|+
literal|"^^"
operator|+
name|xsdType
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|Integer
condition|)
block|{
name|arg2
operator|=
operator|(
operator|(
name|Integer
operator|)
name|value
operator|)
operator|.
name|toString
argument_list|()
operator|+
literal|"^^"
operator|+
name|xsdType
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"ARG 2 : "
operator|+
name|arg2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|argument2
operator|instanceof
name|StringFunctionAtom
condition|)
block|{
name|arg2
operator|=
operator|(
operator|(
name|StringFunctionAtom
operator|)
name|argument2
operator|)
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
block|}
comment|//return arg1+" "+dtP+" "+literal.getLiteral();
block|}
if|if
condition|(
name|negativeArg1
operator|||
name|negativeArg2
operator|||
name|negativeDtP
condition|)
block|{
name|String
name|optional
init|=
name|arg1
operator|+
literal|" "
operator|+
name|dtP
operator|+
literal|" "
operator|+
name|arg2
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|filters
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
name|negativeArg1
condition|)
block|{
name|filters
operator|.
name|add
argument_list|(
literal|"!bound("
operator|+
name|arg1
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|negativeArg2
condition|)
block|{
name|filters
operator|.
name|add
argument_list|(
literal|"!bound("
operator|+
name|arg2
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|negativeDtP
condition|)
block|{
name|filters
operator|.
name|add
argument_list|(
literal|"!bound("
operator|+
name|dtP
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|filterArray
init|=
operator|new
name|String
index|[
name|filters
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|filterArray
operator|=
name|filters
operator|.
name|toArray
argument_list|(
name|filterArray
argument_list|)
expr_stmt|;
return|return
operator|new
name|SPARQLNot
argument_list|(
name|optional
argument_list|,
name|filterArray
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|SPARQLTriple
argument_list|(
name|arg1
operator|+
literal|" "
operator|+
name|dtP
operator|+
literal|" "
operator|+
name|arg2
argument_list|)
return|;
block|}
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
name|Resource
name|datavaluedPropertyAtom
init|=
name|model
operator|.
name|createResource
argument_list|(
name|SWRL
operator|.
name|DatavaluedPropertyAtom
argument_list|)
decl_stmt|;
name|Resource
name|datatypePropertyResource
init|=
name|datatypeProperty
operator|.
name|createJenaResource
argument_list|(
name|model
argument_list|)
decl_stmt|;
name|Resource
name|argument1Resource
init|=
name|argument1
operator|.
name|createJenaResource
argument_list|(
name|model
argument_list|)
decl_stmt|;
name|Literal
name|argument2Literal
init|=
name|model
operator|.
name|createTypedLiteral
argument_list|(
name|argument2
argument_list|)
decl_stmt|;
name|datavaluedPropertyAtom
operator|.
name|addProperty
argument_list|(
name|SWRL
operator|.
name|propertyPredicate
argument_list|,
name|datatypePropertyResource
argument_list|)
expr_stmt|;
name|datavaluedPropertyAtom
operator|.
name|addProperty
argument_list|(
name|SWRL
operator|.
name|argument1
argument_list|,
name|argument1Resource
argument_list|)
expr_stmt|;
name|datavaluedPropertyAtom
operator|.
name|addLiteral
argument_list|(
name|SWRL
operator|.
name|argument2
argument_list|,
name|argument2Literal
argument_list|)
expr_stmt|;
return|return
name|datavaluedPropertyAtom
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
name|OWLDataProperty
name|owlDataProperty
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|datatypeProperty
operator|.
name|getURI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|SWRLIArgument
name|swrliArgument1
decl_stmt|;
name|SWRLDArgument
name|swrliArgument2
decl_stmt|;
name|OWLIndividual
name|owlIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|argument1
operator|.
name|getURI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|swrliArgument1
operator|=
name|factory
operator|.
name|getSWRLIndividualArgument
argument_list|(
name|owlIndividual
argument_list|)
expr_stmt|;
name|swrliArgument2
operator|=
name|getSWRLTypedLiteral
argument_list|(
name|factory
argument_list|,
name|argument2
argument_list|)
expr_stmt|;
return|return
name|factory
operator|.
name|getSWRLDataPropertyAtom
argument_list|(
name|owlDataProperty
argument_list|,
name|swrliArgument1
argument_list|,
name|swrliArgument2
argument_list|)
return|;
block|}
specifier|public
name|URIResource
name|getDatatypeProperty
parameter_list|()
block|{
return|return
name|datatypeProperty
return|;
block|}
specifier|public
name|URIResource
name|getArgument1
parameter_list|()
block|{
return|return
name|argument1
return|;
block|}
specifier|public
name|Object
name|getArgument2
parameter_list|()
block|{
return|return
name|argument2
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"Individual "
operator|+
name|argument1
operator|.
name|toString
argument_list|()
operator|+
literal|" has datatype property "
operator|+
name|argument1
operator|.
name|toString
argument_list|()
operator|+
literal|" with value "
operator|+
name|argument2
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|SWRLLiteralArgument
name|getSWRLTypedLiteral
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|,
name|Object
name|argument
parameter_list|)
block|{
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
name|factory
operator|.
name|getSWRLLiteralArgument
argument_list|(
name|owlLiteral
argument_list|)
return|;
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
name|String
name|arg3
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
name|KReSVariable
name|variable
init|=
operator|(
name|KReSVariable
operator|)
name|argument1
decl_stmt|;
if|if
condition|(
name|variable
operator|.
name|isNegative
argument_list|()
condition|)
block|{
name|arg1
operator|=
literal|"notex("
operator|+
name|arg1
operator|+
literal|")"
expr_stmt|;
block|}
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
name|datatypeProperty
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
name|arg3
operator|=
literal|"?"
operator|+
name|datatypeProperty
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
name|KReSVariable
name|variable
init|=
operator|(
name|KReSVariable
operator|)
name|datatypeProperty
decl_stmt|;
if|if
condition|(
name|variable
operator|.
name|isNegative
argument_list|()
condition|)
block|{
name|arg3
operator|=
literal|"notex("
operator|+
name|arg3
operator|+
literal|")"
expr_stmt|;
block|}
block|}
else|else
block|{
name|arg3
operator|=
name|datatypeProperty
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
name|KReSVariable
name|variable
init|=
operator|(
name|KReSVariable
operator|)
name|argument2
decl_stmt|;
if|if
condition|(
name|variable
operator|.
name|isNegative
argument_list|()
condition|)
block|{
name|arg2
operator|=
literal|"notex("
operator|+
name|arg2
operator|+
literal|")"
expr_stmt|;
block|}
return|return
literal|"values("
operator|+
name|arg3
operator|+
literal|", "
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
name|OWLLiteral
name|literal
init|=
name|getOWLTypedLiteral
argument_list|(
name|argument2
argument_list|)
decl_stmt|;
return|return
literal|"values("
operator|+
name|arg3
operator|+
literal|", "
operator|+
name|arg1
operator|+
literal|", "
operator|+
name|literal
operator|.
name|getLiteral
argument_list|()
operator|+
literal|")"
return|;
block|}
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
block|}
end_class

end_unit

