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
name|nlp
operator|.
name|json
operator|.
name|valuetype
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|FilenameUtils
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
name|enhancer
operator|.
name|nlp
operator|.
name|NlpAnnotations
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
name|enhancer
operator|.
name|nlp
operator|.
name|dependency
operator|.
name|DependencyRelation
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
name|enhancer
operator|.
name|nlp
operator|.
name|dependency
operator|.
name|GrammaticalRelation
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
name|enhancer
operator|.
name|nlp
operator|.
name|dependency
operator|.
name|GrammaticalRelationTag
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Sentence
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Token
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|annotation
operator|.
name|Value
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|DependencyRelationSupportTest
extends|extends
name|ValueTypeSupportTest
block|{
specifier|private
specifier|static
specifier|final
name|String
name|text
init|=
literal|"Obama visited China."
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|jsonCheckObama
init|=
literal|"{"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"type\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"start\" : 0,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"end\" : 5,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"stanbol.enhancer.nlp.dependency\" : {"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"tag\" : \"nsubj\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"relationType\" : 32,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"isDependent\" : true,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerType\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerStart\" : 6,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerEnd\" : 13,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"class\" : \"org.apache.stanbol.enhancer.nlp.dependency.DependencyRelation\""
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    }"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"  }"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|jsonCheckVisited
init|=
literal|"{"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"type\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"start\" : 6,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"end\" : 13,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"stanbol.enhancer.nlp.dependency\" : [ {"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"tag\" : \"root\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"relationType\" : 56,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"isDependent\" : true,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerType\" : \"ROOT\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerStart\" : 0,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerEnd\" : 0,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"class\" : \"org.apache.stanbol.enhancer.nlp.dependency.DependencyRelation\""
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    }, {"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"tag\" : \"nsubj\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"relationType\" : 32,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"isDependent\" : false,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerType\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerStart\" : 0,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerEnd\" : 5,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"class\" : \"org.apache.stanbol.enhancer.nlp.dependency.DependencyRelation\""
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    }, {"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"tag\" : \"dobj\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"relationType\" : 24,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"isDependent\" : false,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerType\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerStart\" : 14,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerEnd\" : 19,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"class\" : \"org.apache.stanbol.enhancer.nlp.dependency.DependencyRelation\""
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    } ]"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"  }"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|jsonCheckChina
init|=
literal|"{"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"type\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"start\" : 14,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"end\" : 19,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    \"stanbol.enhancer.nlp.dependency\" : {"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"tag\" : \"dobj\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"relationType\" : 24,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"isDependent\" : true,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerType\" : \"Token\","
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerStart\" : 6,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"partnerEnd\" : 13,"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"      \"class\" : \"org.apache.stanbol.enhancer.nlp.dependency.DependencyRelation\""
operator|+
name|LINE_SEPARATOR
operator|+
literal|"    }"
operator|+
name|LINE_SEPARATOR
operator|+
literal|"  }"
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
throws|throws
name|IOException
block|{
name|setupAnalysedText
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|initDepTreeAnnotations
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSerializationAndParse
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|serialized
init|=
name|getSerializedString
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
name|jsonCheckObama
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
name|jsonCheckVisited
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|serialized
operator|.
name|contains
argument_list|(
name|jsonCheckChina
argument_list|)
argument_list|)
expr_stmt|;
name|AnalysedText
name|parsedAt
init|=
name|getParsedAnalysedText
argument_list|(
name|serialized
argument_list|)
decl_stmt|;
name|assertAnalysedTextEquality
argument_list|(
name|parsedAt
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|void
name|initDepTreeAnnotations
parameter_list|()
block|{
name|Sentence
name|sentence
init|=
name|at
operator|.
name|addSentence
argument_list|(
literal|0
argument_list|,
name|text
operator|.
name|indexOf
argument_list|(
literal|"."
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
name|Token
name|obama
init|=
name|sentence
operator|.
name|addToken
argument_list|(
literal|0
argument_list|,
literal|"Obama"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|visitedStartIdx
init|=
name|sentence
operator|.
name|getSpan
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"visited"
argument_list|)
decl_stmt|;
name|Token
name|visited
init|=
name|sentence
operator|.
name|addToken
argument_list|(
name|visitedStartIdx
argument_list|,
name|visitedStartIdx
operator|+
literal|"visited"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|chinaStartIdx
init|=
name|sentence
operator|.
name|getSpan
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|"China"
argument_list|)
decl_stmt|;
name|Token
name|china
init|=
name|sentence
operator|.
name|addToken
argument_list|(
name|chinaStartIdx
argument_list|,
name|chinaStartIdx
operator|+
literal|"China"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|GrammaticalRelationTag
name|nSubjGrammRelTag
init|=
operator|new
name|GrammaticalRelationTag
argument_list|(
literal|"nsubj"
argument_list|,
name|GrammaticalRelation
operator|.
name|NominalSubject
argument_list|)
decl_stmt|;
name|obama
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|DEPENDENCY_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|DependencyRelation
argument_list|(
name|nSubjGrammRelTag
argument_list|,
literal|true
argument_list|,
name|visited
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|GrammaticalRelationTag
name|rootGrammRelTag
init|=
operator|new
name|GrammaticalRelationTag
argument_list|(
literal|"root"
argument_list|,
name|GrammaticalRelation
operator|.
name|Root
argument_list|)
decl_stmt|;
name|GrammaticalRelationTag
name|dobjGrammRelTag
init|=
operator|new
name|GrammaticalRelationTag
argument_list|(
literal|"dobj"
argument_list|,
name|GrammaticalRelation
operator|.
name|DirectObject
argument_list|)
decl_stmt|;
name|visited
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|DEPENDENCY_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|DependencyRelation
argument_list|(
name|rootGrammRelTag
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|visited
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|DEPENDENCY_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|DependencyRelation
argument_list|(
name|nSubjGrammRelTag
argument_list|,
literal|false
argument_list|,
name|obama
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|visited
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|DEPENDENCY_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|DependencyRelation
argument_list|(
name|dobjGrammRelTag
argument_list|,
literal|false
argument_list|,
name|china
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|china
operator|.
name|addAnnotation
argument_list|(
name|NlpAnnotations
operator|.
name|DEPENDENCY_ANNOTATION
argument_list|,
name|Value
operator|.
name|value
argument_list|(
operator|new
name|DependencyRelation
argument_list|(
name|dobjGrammRelTag
argument_list|,
literal|true
argument_list|,
name|visited
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

