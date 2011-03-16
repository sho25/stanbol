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
name|arqextention
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
name|ontologies
operator|.
name|XML_OWL
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
name|graph
operator|.
name|Node
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
name|Resource
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
name|sparql
operator|.
name|core
operator|.
name|Var
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
name|sparql
operator|.
name|engine
operator|.
name|ExecutionContext
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
name|sparql
operator|.
name|engine
operator|.
name|QueryIterator
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
name|sparql
operator|.
name|engine
operator|.
name|binding
operator|.
name|Binding
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
name|sparql
operator|.
name|engine
operator|.
name|binding
operator|.
name|Binding0
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
name|sparql
operator|.
name|engine
operator|.
name|binding
operator|.
name|Binding1
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
name|sparql
operator|.
name|pfunction
operator|.
name|PropFuncArg
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
name|sparql
operator|.
name|pfunction
operator|.
name|PropFuncArgType
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
name|sparql
operator|.
name|pfunction
operator|.
name|PropertyFunctionEval
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
name|sparql
operator|.
name|util
operator|.
name|IterLib
import|;
end_import

begin_class
specifier|public
class|class
name|CreateURI
extends|extends
name|PropertyFunctionEval
block|{
specifier|public
name|CreateURI
parameter_list|()
block|{
name|super
argument_list|(
name|PropFuncArgType
operator|.
name|PF_ARG_SINGLE
argument_list|,
name|PropFuncArgType
operator|.
name|PF_ARG_SINGLE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|QueryIterator
name|execEvaluated
parameter_list|(
name|Binding
name|binding
parameter_list|,
name|PropFuncArg
name|argumentSubject
parameter_list|,
name|Node
name|predicate
parameter_list|,
name|PropFuncArg
name|argumentObject
parameter_list|,
name|ExecutionContext
name|execCxt
parameter_list|)
block|{
name|Binding
name|b
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|argumentObject
operator|.
name|getArg
argument_list|()
operator|.
name|isLiteral
argument_list|()
condition|)
block|{
name|Node
name|ref
init|=
name|argumentSubject
operator|.
name|getArg
argument_list|()
decl_stmt|;
if|if
condition|(
name|ref
operator|.
name|isVariable
argument_list|()
condition|)
block|{
name|String
name|argumentString
init|=
name|argumentObject
operator|.
name|getArg
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\""
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|b
operator|=
operator|new
name|Binding1
argument_list|(
name|binding
argument_list|,
name|Var
operator|.
name|alloc
argument_list|(
name|ref
argument_list|)
argument_list|,
name|Node
operator|.
name|createURI
argument_list|(
name|argumentString
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|b
operator|==
literal|null
condition|)
block|{
name|b
operator|=
name|binding
expr_stmt|;
block|}
return|return
name|IterLib
operator|.
name|result
argument_list|(
name|b
argument_list|,
name|execCxt
argument_list|)
return|;
block|}
block|}
end_class

end_unit

