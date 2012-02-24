begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|ldpath
operator|.
name|function
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|ldpath
operator|.
name|utils
operator|.
name|Utils
operator|.
name|parseSelector
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|DC_TYPE
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_EXTRACTED_FROM
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|RDF_TYPE
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|TechnicalClasses
operator|.
name|ENHANCER_TEXTANNOTATION
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|Set
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
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|functions
operator|.
name|SelectorFunction
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|selectors
operator|.
name|NodeSelector
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|model
operator|.
name|selectors
operator|.
name|PropertySelector
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|parser
operator|.
name|ParseException
import|;
end_import

begin_class
specifier|public
class|class
name|TextAnnotationFunction
implements|implements
name|SelectorFunction
argument_list|<
name|Resource
argument_list|>
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|TextAnnotationFunction
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|FUNCTION_NAME
init|=
literal|"textAnnotation"
decl_stmt|;
specifier|private
specifier|static
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|selector
decl_stmt|;
static|static
block|{
name|String
name|path
init|=
name|String
operator|.
name|format
argument_list|(
literal|"^%s[%s is %s]"
argument_list|,
name|ENHANCER_EXTRACTED_FROM
argument_list|,
name|RDF_TYPE
argument_list|,
name|ENHANCER_TEXTANNOTATION
argument_list|)
decl_stmt|;
try|try
block|{
name|selector
operator|=
name|parseSelector
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse the ld-path selector '"
operator|+
name|path
operator|+
literal|"'used by the 'fn:"
operator|+
name|FUNCTION_NAME
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|private
specifier|static
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|dcTypeSelector
init|=
operator|new
name|PropertySelector
argument_list|<
name|Resource
argument_list|>
argument_list|(
name|DC_TYPE
argument_list|)
decl_stmt|;
specifier|public
name|TextAnnotationFunction
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Resource
argument_list|>
name|apply
parameter_list|(
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|backend
parameter_list|,
name|Collection
argument_list|<
name|Resource
argument_list|>
modifier|...
name|args
parameter_list|)
block|{
if|if
condition|(
name|args
operator|==
literal|null
operator|||
name|args
operator|.
name|length
operator|<
literal|1
operator|||
name|args
index|[
literal|0
index|]
operator|==
literal|null
operator|||
name|args
index|[
literal|0
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The 'fn:"
operator|+
name|FUNCTION_NAME
operator|+
literal|"' function "
operator|+
literal|"requires at least a single none empty parameter (the context). Use 'fn:"
operator|+
name|FUNCTION_NAME
operator|+
literal|"(.)' to execute it on the path context!"
argument_list|)
throw|;
block|}
name|Set
argument_list|<
name|Resource
argument_list|>
name|textAnnotations
init|=
operator|new
name|HashSet
argument_list|<
name|Resource
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Resource
name|context
range|:
name|args
index|[
literal|0
index|]
control|)
block|{
name|textAnnotations
operator|.
name|addAll
argument_list|(
name|selector
operator|.
name|select
argument_list|(
name|backend
argument_list|,
name|context
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// NOTE: parsing of the dc:type as parameter is deactivated for now, because
comment|//       See the NOTES within this commented seciton for details why.
comment|//        final UriRef dcTypeConstraint;
comment|//        if(args.length< 2 || args[1].isEmpty()){
comment|//            dcTypeConstraint = null;
comment|//        } else {
comment|//            /*
comment|//             * NOTES:
comment|//             *
comment|//             *  * Parameters MUST BE parsed as Literals, because otherwise LDPATH
comment|//             *    would execute them rather than directly parsing them
comment|//             *  * Namespace prefixes can not be supported for URIs parsed as
comment|//             *    Literals, because the prefix mappings are only known by the
comment|//             *    ldpath parser and not available to this component.
comment|//             */
comment|//            Resource value = args[1].iterator().next();
comment|//            if(value instanceof Literal){
comment|//                dcTypeConstraint = new UriRef(((Literal)value).getLexicalForm());
comment|//            } else {
comment|//                log.warn("Unable to use dc:type constraint {} (value MUST BE a Literal)!",value);
comment|//                dcTypeConstraint = null;
comment|//            }
comment|//        }
comment|//        if(dcTypeConstraint != null){
comment|//            NodeTest<Resource> dcTypeFilter = new PathEqualityTest<Resource>(dcTypeSelector, dcTypeConstraint);
comment|//            Iterator<Resource> it = textAnnotations.iterator();
comment|//            while(it.hasNext()){
comment|//                if(!dcTypeFilter.apply(backend, Collections.singleton(it.next()))){
comment|//                    it.remove();
comment|//                }
comment|//            }
comment|//        }
return|return
name|textAnnotations
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPathExpression
parameter_list|(
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|backend
parameter_list|)
block|{
return|return
name|FUNCTION_NAME
return|;
block|}
block|}
end_class

end_unit

