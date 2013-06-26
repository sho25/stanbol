begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|source
operator|.
name|jenatdb
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
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

begin_comment
comment|/**  * Allows to filter Tiples based on the language of the value. Triples with  * values other than<code>{@link Node#isLiteral()} == true</code> are accepted.  * This is also true for all Literals that do not have a language assigned.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|LiteralLanguageFilter
implements|implements
name|RdfImportFilter
block|{
comment|/**      * Allows to configure the literal languages included/excluded during the      * import of RDF data<p>      *<b>Syntax:</b><code>{lang1},!{lang2},*</code>      *<ul>      *<li>'{lang}' includes an language      *<li>'!{lang}'excludes an language      *<li>',' is the separator, additional spaces are trimmed      *<li>'*' will include all properties not explicitly excluded      *</ul>      */
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_LITERAL_LANGUAGES
init|=
literal|"if-literal-language"
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|configuredLanguages
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|excludedLanguages
decl_stmt|;
specifier|private
name|boolean
name|includeAll
decl_stmt|;
specifier|public
name|LiteralLanguageFilter
parameter_list|()
block|{}
comment|/**      * For unit tests      * @param config the test config      */
specifier|protected
name|LiteralLanguageFilter
parameter_list|(
name|String
name|config
parameter_list|)
block|{
name|parseLanguages
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|PARAM_LITERAL_LANGUAGES
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|includeAll
operator|=
literal|true
expr_stmt|;
name|excludedLanguages
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
name|configuredLanguages
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|parseLanguages
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|parseLanguages
parameter_list|(
name|String
name|config
parameter_list|)
block|{
name|configuredLanguages
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|excludedLanguages
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|String
index|[]
name|languages
init|=
name|config
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|languages
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|languages
index|[
name|i
index|]
operator|=
name|languages
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
expr_stmt|;
if|if
condition|(
name|includeAll
operator|==
literal|false
operator|&&
name|languages
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
name|includeAll
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|lang
range|:
name|languages
control|)
block|{
if|if
condition|(
name|lang
operator|.
name|isEmpty
argument_list|()
operator|||
name|lang
operator|.
name|equals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
continue|continue;
comment|//ignore null values and * is already processed
block|}
comment|//lang = lang.toLowerCase(); //country codes are upper case
if|if
condition|(
name|lang
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'!'
condition|)
block|{
comment|//exclude
name|lang
operator|=
name|lang
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|lang
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
comment|//only a '!' without an lanugage
block|}
if|if
condition|(
name|configuredLanguages
operator|.
name|contains
argument_list|(
name|lang
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Langauge '"
operator|+
name|lang
operator|+
literal|"' is both included and excluded (config: "
operator|+
name|config
operator|+
literal|")"
argument_list|)
throw|;
block|}
name|excludedLanguages
operator|.
name|add
argument_list|(
name|lang
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|excludedLanguages
operator|.
name|contains
argument_list|(
name|lang
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Langauge '"
operator|+
name|lang
operator|+
literal|"' is both included and excluded (config: "
operator|+
name|config
operator|+
literal|")"
argument_list|)
throw|;
block|}
name|configuredLanguages
operator|.
name|add
argument_list|(
name|lang
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|needsInitialisation
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|initialise
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|Node
name|s
parameter_list|,
name|Node
name|p
parameter_list|,
name|Node
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|.
name|isLiteral
argument_list|()
condition|)
block|{
if|if
condition|(
name|includeAll
operator|&&
name|excludedLanguages
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
comment|//deactivated
block|}
name|String
name|lang
init|=
name|o
operator|.
name|getLiteralLanguage
argument_list|()
decl_stmt|;
if|if
condition|(
name|lang
operator|!=
literal|null
operator|&&
operator|!
name|lang
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|includeAll
condition|)
block|{
return|return
operator|!
name|excludedLanguages
operator|.
name|contains
argument_list|(
name|lang
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|configuredLanguages
operator|.
name|contains
argument_list|(
name|lang
argument_list|)
return|;
block|}
block|}
else|else
block|{
comment|//no plain literal (null) or default language (empty)
return|return
literal|true
return|;
comment|//accept it
block|}
block|}
else|else
block|{
return|return
literal|true
return|;
comment|//accept all none literals
block|}
block|}
block|}
end_class

end_unit

