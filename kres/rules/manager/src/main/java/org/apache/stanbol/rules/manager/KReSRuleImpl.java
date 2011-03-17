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
name|HashSet
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
name|Set
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
name|KReSRule
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
name|KReSRuleAtom
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
name|KReSRuleExpressiveness
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
name|util
operator|.
name|AtomList
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
name|SWRLRule
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
name|ModelFactory
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
name|SWRL
import|;
end_import

begin_class
specifier|public
class|class
name|KReSRuleImpl
implements|implements
name|KReSRule
block|{
specifier|private
name|String
name|ruleName
decl_stmt|;
specifier|private
name|String
name|rule
decl_stmt|;
specifier|private
name|AtomList
name|head
decl_stmt|;
specifier|private
name|AtomList
name|body
decl_stmt|;
specifier|private
name|boolean
name|forwardChain
decl_stmt|;
specifier|private
name|boolean
name|reflexive
decl_stmt|;
specifier|private
name|boolean
name|sparqlC
decl_stmt|;
specifier|private
name|boolean
name|sparqlD
decl_stmt|;
name|KReSRuleExpressiveness
name|expressiveness
decl_stmt|;
specifier|public
name|KReSRuleImpl
parameter_list|(
name|String
name|ruleURI
parameter_list|,
name|AtomList
name|body
parameter_list|,
name|AtomList
name|head
parameter_list|,
name|KReSRuleExpressiveness
name|expressiveness
parameter_list|)
block|{
name|this
operator|.
name|ruleName
operator|=
name|ruleURI
expr_stmt|;
name|this
operator|.
name|head
operator|=
name|head
expr_stmt|;
name|this
operator|.
name|body
operator|=
name|body
expr_stmt|;
name|this
operator|.
name|expressiveness
operator|=
name|expressiveness
expr_stmt|;
block|}
specifier|public
name|String
name|getRuleName
parameter_list|()
block|{
return|return
name|ruleName
return|;
block|}
specifier|public
name|void
name|setRuleName
parameter_list|(
name|String
name|ruleName
parameter_list|)
block|{
name|this
operator|.
name|ruleName
operator|=
name|ruleName
expr_stmt|;
block|}
specifier|public
name|String
name|getRule
parameter_list|()
block|{
return|return
name|rule
return|;
block|}
specifier|public
name|void
name|setRule
parameter_list|(
name|String
name|rule
parameter_list|)
block|{
name|this
operator|.
name|rule
operator|=
name|rule
expr_stmt|;
block|}
specifier|public
name|String
name|toSPARQL
parameter_list|()
block|{
name|String
name|sparql
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|isSPARQLConstruct
argument_list|()
operator|||
name|isSPARQLDelete
argument_list|()
condition|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
name|Iterator
argument_list|<
name|KReSRuleAtom
argument_list|>
name|it
init|=
name|body
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
operator|&&
operator|!
name|found
condition|)
block|{
name|KReSRuleAtom
name|kReSRuleAtom
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|sparql
operator|=
name|kReSRuleAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
else|else
block|{
name|sparql
operator|=
literal|"CONSTRUCT {"
expr_stmt|;
name|boolean
name|firstIte
init|=
literal|true
decl_stmt|;
for|for
control|(
name|KReSRuleAtom
name|kReSRuleAtom
range|:
name|head
control|)
block|{
if|if
condition|(
operator|!
name|firstIte
condition|)
block|{
name|sparql
operator|+=
literal|" . "
expr_stmt|;
block|}
name|firstIte
operator|=
literal|false
expr_stmt|;
name|sparql
operator|+=
name|kReSRuleAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
block|}
name|sparql
operator|+=
literal|"} "
expr_stmt|;
name|sparql
operator|+=
literal|"WHERE {"
expr_stmt|;
name|firstIte
operator|=
literal|true
expr_stmt|;
name|ArrayList
argument_list|<
name|SPARQLObject
argument_list|>
name|sparqlObjects
init|=
operator|new
name|ArrayList
argument_list|<
name|SPARQLObject
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|KReSRuleAtom
name|kReSRuleAtom
range|:
name|body
control|)
block|{
name|SPARQLObject
name|sparqlObject
init|=
name|kReSRuleAtom
operator|.
name|toSPARQL
argument_list|()
decl_stmt|;
if|if
condition|(
name|sparqlObject
operator|instanceof
name|SPARQLNot
condition|)
block|{
name|sparqlObjects
operator|.
name|add
argument_list|(
operator|(
name|SPARQLNot
operator|)
name|sparqlObject
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sparqlObject
operator|instanceof
name|SPARQLComparison
condition|)
block|{
name|sparqlObjects
operator|.
name|add
argument_list|(
operator|(
name|SPARQLComparison
operator|)
name|sparqlObject
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|firstIte
condition|)
block|{
name|sparql
operator|+=
literal|" . "
expr_stmt|;
block|}
else|else
block|{
name|firstIte
operator|=
literal|false
expr_stmt|;
block|}
name|sparql
operator|+=
name|kReSRuleAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
block|}
block|}
name|firstIte
operator|=
literal|true
expr_stmt|;
name|String
name|optional
init|=
literal|""
decl_stmt|;
name|String
name|filter
init|=
literal|""
decl_stmt|;
for|for
control|(
name|SPARQLObject
name|sparqlObj
range|:
name|sparqlObjects
control|)
block|{
if|if
condition|(
name|sparqlObj
operator|instanceof
name|SPARQLNot
condition|)
block|{
name|SPARQLNot
name|sparqlNot
init|=
operator|(
name|SPARQLNot
operator|)
name|sparqlObj
decl_stmt|;
if|if
condition|(
operator|!
name|firstIte
condition|)
block|{
name|optional
operator|+=
literal|" . "
expr_stmt|;
block|}
else|else
block|{
name|firstIte
operator|=
literal|false
expr_stmt|;
block|}
name|optional
operator|+=
name|sparqlNot
operator|.
name|getObject
argument_list|()
expr_stmt|;
name|String
index|[]
name|filters
init|=
name|sparqlNot
operator|.
name|getFilters
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|theFilter
range|:
name|filters
control|)
block|{
if|if
condition|(
operator|!
name|filter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|filter
operator|+=
literal|"&& "
expr_stmt|;
block|}
name|filter
operator|+=
name|theFilter
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|sparqlObj
operator|instanceof
name|SPARQLComparison
condition|)
block|{
name|SPARQLComparison
name|sparqlDifferent
init|=
operator|(
name|SPARQLComparison
operator|)
name|sparqlObj
decl_stmt|;
name|String
name|theFilter
init|=
name|sparqlDifferent
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|filter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|filter
operator|+=
literal|"&& "
expr_stmt|;
block|}
name|filter
operator|+=
name|theFilter
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|optional
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sparql
operator|+=
literal|" . OPTIONAL { "
operator|+
name|optional
operator|+
literal|" } "
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|filter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sparql
operator|+=
literal|" . FILTER ( "
operator|+
name|filter
operator|+
literal|" ) "
expr_stmt|;
block|}
name|sparql
operator|+=
literal|"}"
expr_stmt|;
block|}
return|return
name|sparql
return|;
block|}
specifier|public
name|Resource
name|toSWRL
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
name|Resource
name|imp
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|head
operator|!=
literal|null
operator|&&
name|body
operator|!=
literal|null
condition|)
block|{
name|imp
operator|=
name|model
operator|.
name|createResource
argument_list|(
name|ruleName
argument_list|,
name|SWRL
operator|.
name|Imp
argument_list|)
expr_stmt|;
comment|//RDF list for body
name|RDFList
name|list
init|=
name|model
operator|.
name|createList
argument_list|()
decl_stmt|;
for|for
control|(
name|KReSRuleAtom
name|atom
range|:
name|body
control|)
block|{
name|list
operator|=
name|list
operator|.
name|cons
argument_list|(
name|atom
operator|.
name|toSWRL
argument_list|(
name|model
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|imp
operator|.
name|addProperty
argument_list|(
name|SWRL
operator|.
name|body
argument_list|,
name|list
argument_list|)
expr_stmt|;
comment|//RDF list for head
name|list
operator|=
name|model
operator|.
name|createList
argument_list|()
expr_stmt|;
for|for
control|(
name|KReSRuleAtom
name|atom
range|:
name|head
control|)
block|{
name|list
operator|=
name|list
operator|.
name|cons
argument_list|(
name|atom
operator|.
name|toSWRL
argument_list|(
name|model
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|imp
operator|.
name|addProperty
argument_list|(
name|SWRL
operator|.
name|head
argument_list|,
name|list
argument_list|)
expr_stmt|;
block|}
return|return
name|imp
return|;
block|}
specifier|public
name|SWRLRule
name|toSWRL
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|)
block|{
name|Set
argument_list|<
name|SWRLAtom
argument_list|>
name|bodyAtoms
init|=
operator|new
name|HashSet
argument_list|<
name|SWRLAtom
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|SWRLAtom
argument_list|>
name|headAtoms
init|=
operator|new
name|HashSet
argument_list|<
name|SWRLAtom
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|KReSRuleAtom
name|atom
range|:
name|body
control|)
block|{
name|bodyAtoms
operator|.
name|add
argument_list|(
name|atom
operator|.
name|toSWRL
argument_list|(
name|factory
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|KReSRuleAtom
name|atom
range|:
name|head
control|)
block|{
name|headAtoms
operator|.
name|add
argument_list|(
name|atom
operator|.
name|toSWRL
argument_list|(
name|factory
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|factory
operator|.
name|getSWRLRule
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|ruleName
argument_list|)
argument_list|,
name|bodyAtoms
argument_list|,
name|headAtoms
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|rule
init|=
literal|null
decl_stmt|;
name|String
name|tab
init|=
literal|"       "
decl_stmt|;
if|if
condition|(
name|head
operator|!=
literal|null
operator|&&
name|body
operator|!=
literal|null
condition|)
block|{
name|boolean
name|addAnd
init|=
literal|false
decl_stmt|;
name|rule
operator|=
literal|"RULE "
operator|+
name|ruleName
operator|+
literal|" ASSERTS THAT "
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
name|rule
operator|+=
literal|"IF"
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
for|for
control|(
name|KReSRuleAtom
name|atom
range|:
name|body
control|)
block|{
name|rule
operator|+=
name|tab
expr_stmt|;
if|if
condition|(
name|addAnd
condition|)
block|{
name|rule
operator|+=
literal|"AND "
expr_stmt|;
block|}
else|else
block|{
name|addAnd
operator|=
literal|true
expr_stmt|;
block|}
name|rule
operator|+=
name|atom
operator|.
name|toString
argument_list|()
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
block|}
name|rule
operator|+=
literal|"IMPLIES"
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
name|addAnd
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|KReSRuleAtom
name|atom
range|:
name|head
control|)
block|{
name|rule
operator|+=
name|tab
expr_stmt|;
if|if
condition|(
name|addAnd
condition|)
block|{
name|rule
operator|+=
literal|"AND "
expr_stmt|;
block|}
else|else
block|{
name|addAnd
operator|=
literal|true
expr_stmt|;
block|}
name|rule
operator|+=
name|atom
operator|.
name|toString
argument_list|()
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|rule
return|;
block|}
annotation|@
name|Override
specifier|public
name|AtomList
name|getBody
parameter_list|()
block|{
return|return
name|body
return|;
block|}
annotation|@
name|Override
specifier|public
name|AtomList
name|getHead
parameter_list|()
block|{
return|return
name|head
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toKReSSyntax
parameter_list|()
block|{
name|Resource
name|rs
init|=
name|ModelFactory
operator|.
name|createDefaultModel
argument_list|()
operator|.
name|createResource
argument_list|(
name|ruleName
argument_list|)
decl_stmt|;
name|String
name|ruleInKReSSyntax
init|=
name|rs
operator|.
name|getLocalName
argument_list|()
operator|+
literal|"["
decl_stmt|;
if|if
condition|(
name|isSPARQLConstruct
argument_list|()
operator|||
name|isSPARQLDelete
argument_list|()
condition|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
name|Iterator
argument_list|<
name|KReSRuleAtom
argument_list|>
name|it
init|=
name|body
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
operator|&&
operator|!
name|found
condition|)
block|{
name|KReSRuleAtom
name|kReSRuleAtom
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|ruleInKReSSyntax
operator|=
name|kReSRuleAtom
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
else|else
block|{
name|boolean
name|firstLoop
init|=
literal|true
decl_stmt|;
for|for
control|(
name|KReSRuleAtom
name|atom
range|:
name|body
control|)
block|{
if|if
condition|(
operator|!
name|firstLoop
condition|)
block|{
name|ruleInKReSSyntax
operator|+=
literal|" . "
expr_stmt|;
block|}
else|else
block|{
name|firstLoop
operator|=
literal|false
expr_stmt|;
block|}
name|ruleInKReSSyntax
operator|+=
name|atom
operator|.
name|toKReSSyntax
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|head
operator|!=
literal|null
condition|)
block|{
name|ruleInKReSSyntax
operator|+=
literal|" -> "
expr_stmt|;
name|firstLoop
operator|=
literal|true
expr_stmt|;
for|for
control|(
name|KReSRuleAtom
name|atom
range|:
name|head
control|)
block|{
if|if
condition|(
operator|!
name|firstLoop
condition|)
block|{
name|ruleInKReSSyntax
operator|+=
literal|" . "
expr_stmt|;
block|}
else|else
block|{
name|firstLoop
operator|=
literal|false
expr_stmt|;
block|}
name|ruleInKReSSyntax
operator|+=
name|atom
operator|.
name|toKReSSyntax
argument_list|()
expr_stmt|;
block|}
block|}
block|}
name|ruleInKReSSyntax
operator|+=
literal|"]"
expr_stmt|;
return|return
name|ruleInKReSSyntax
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isForwardChain
parameter_list|()
block|{
switch|switch
condition|(
name|expressiveness
condition|)
block|{
case|case
name|ForwardChaining
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
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
switch|switch
condition|(
name|expressiveness
condition|)
block|{
case|case
name|SPARQLConstruct
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDelete
parameter_list|()
block|{
switch|switch
condition|(
name|expressiveness
condition|)
block|{
case|case
name|SPARQLDelete
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDeleteData
parameter_list|()
block|{
switch|switch
condition|(
name|expressiveness
condition|)
block|{
case|case
name|SPARQLDeleteData
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isReflexive
parameter_list|()
block|{
switch|switch
condition|(
name|expressiveness
condition|)
block|{
case|case
name|Reflexive
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|KReSRuleExpressiveness
name|getExpressiveness
parameter_list|()
block|{
return|return
name|expressiveness
return|;
block|}
block|}
end_class

end_unit

