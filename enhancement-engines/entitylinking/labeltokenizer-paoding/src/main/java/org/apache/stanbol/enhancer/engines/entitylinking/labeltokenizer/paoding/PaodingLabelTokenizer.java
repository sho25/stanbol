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
name|engines
operator|.
name|entitylinking
operator|.
name|labeltokenizer
operator|.
name|paoding
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
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|AccessController
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|PrivilegedActionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|PrivilegedExceptionAction
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
name|List
import|;
end_import

begin_import
import|import
name|net
operator|.
name|paoding
operator|.
name|analysis
operator|.
name|analyzer
operator|.
name|PaodingAnalyzer
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
name|Activate
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
name|ConfigurationPolicy
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
name|Deactivate
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
name|Properties
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
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|TokenStream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|tokenattributes
operator|.
name|OffsetAttribute
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
name|engines
operator|.
name|entitylinking
operator|.
name|LabelTokenizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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

begin_class
annotation|@
name|Service
annotation|@
name|Component
argument_list|(
name|configurationFactory
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|OPTIONAL
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
literal|200
argument_list|)
comment|//the smartcn one uses 100
block|}
argument_list|)
specifier|public
class|class
name|PaodingLabelTokenizer
implements|implements
name|LabelTokenizer
block|{
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|PaodingAnalyzer
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|EMPTY
init|=
operator|new
name|String
index|[]
block|{}
decl_stmt|;
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... activating {}"
argument_list|,
name|PaodingLabelTokenizer
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ... deactivating {}"
argument_list|,
name|PaodingLabelTokenizer
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|tokenize
parameter_list|(
name|String
name|label
parameter_list|,
name|String
name|language
parameter_list|)
block|{
if|if
condition|(
name|label
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Label MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
literal|"zh"
operator|.
name|equals
argument_list|(
name|language
argument_list|)
operator|||
operator|(
name|language
operator|!=
literal|null
operator|&&
name|language
operator|.
name|length
argument_list|()
operator|>
literal|4
operator|&&
name|language
operator|.
name|charAt
argument_list|(
literal|2
argument_list|)
operator|==
literal|'-'
operator|&&
name|language
operator|.
name|startsWith
argument_list|(
literal|"zh"
argument_list|)
operator|)
condition|)
block|{
if|if
condition|(
name|label
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|EMPTY
return|;
block|}
name|PaodingAnalyzer
name|pa
decl_stmt|;
try|try
block|{
name|pa
operator|=
name|AccessController
operator|.
name|doPrivileged
argument_list|(
operator|new
name|PrivilegedExceptionAction
argument_list|<
name|PaodingAnalyzer
argument_list|>
argument_list|()
block|{
specifier|public
name|PaodingAnalyzer
name|run
parameter_list|()
throws|throws
name|Exception
block|{
return|return
operator|new
name|PaodingAnalyzer
argument_list|()
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PrivilegedActionException
name|pae
parameter_list|)
block|{
name|Exception
name|e
init|=
name|pae
operator|.
name|getException
argument_list|()
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
literal|"Unable to initialise PoadingAnalyzer"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|TokenStream
name|ts
init|=
name|pa
operator|.
name|tokenStream
argument_list|(
literal|"dummy"
argument_list|,
operator|new
name|StringReader
argument_list|(
name|label
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|tokens
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
literal|8
argument_list|)
decl_stmt|;
name|int
name|lastEnd
init|=
literal|0
decl_stmt|;
try|try
block|{
while|while
condition|(
name|ts
operator|.
name|incrementToken
argument_list|()
condition|)
block|{
name|OffsetAttribute
name|offset
init|=
name|ts
operator|.
name|addAttribute
argument_list|(
name|OffsetAttribute
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//when tokenizing labels we need to preserve all chars
if|if
condition|(
name|offset
operator|.
name|startOffset
argument_list|()
operator|>
name|lastEnd
condition|)
block|{
comment|//add token for stopword
name|tokens
operator|.
name|add
argument_list|(
name|label
operator|.
name|substring
argument_list|(
name|lastEnd
argument_list|,
name|offset
operator|.
name|startOffset
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|tokens
operator|.
name|add
argument_list|(
name|label
operator|.
name|substring
argument_list|(
name|offset
operator|.
name|startOffset
argument_list|()
argument_list|,
name|offset
operator|.
name|endOffset
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|lastEnd
operator|=
name|offset
operator|.
name|endOffset
argument_list|()
expr_stmt|;
block|}
return|return
name|tokens
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|tokens
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"IOException while tokenizing label '"
operator|+
name|label
operator|+
literal|"'"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

