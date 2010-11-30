begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|engines
operator|.
name|autotagging
operator|.
name|impl
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
name|Writer
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
name|HashMap
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
name|Map
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServlet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
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
name|Reference
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
name|json
operator|.
name|JSONException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|json
operator|.
name|JSONObject
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
name|osgi
operator|.
name|service
operator|.
name|http
operator|.
name|HttpService
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
name|eu
operator|.
name|iksproject
operator|.
name|autotagging
operator|.
name|Autotagger
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|autotagging
operator|.
name|TagInfo
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|engines
operator|.
name|autotagging
operator|.
name|AutotaggerProvider
import|;
end_import

begin_comment
comment|/**  * Main FISE standalone servlet, registers with the OSGi HttpService to process  * requests on /fise.  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
annotation|@
name|SuppressWarnings
argument_list|(
literal|"serial"
argument_list|)
specifier|public
class|class
name|AutotaggingServlet
extends|extends
name|HttpServlet
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
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ALIAS
init|=
literal|"/suggest"
decl_stmt|;
annotation|@
name|Reference
name|HttpService
name|httpService
decl_stmt|;
annotation|@
name|Reference
name|AutotaggerProvider
name|provider
decl_stmt|;
annotation|@
name|Override
comment|/** Create a ContentItem and queue for enhancement */
specifier|protected
name|void
name|doPost
parameter_list|(
name|HttpServletRequest
name|req
parameter_list|,
name|HttpServletResponse
name|resp
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|doGet
argument_list|(
name|req
argument_list|,
name|resp
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|doGet
parameter_list|(
name|HttpServletRequest
name|req
parameter_list|,
name|HttpServletResponse
name|resp
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|String
name|labelParam
init|=
name|getEntityName
argument_list|(
name|req
argument_list|)
decl_stmt|;
name|String
name|typeParam
init|=
name|getEntityType
argument_list|(
name|req
argument_list|)
decl_stmt|;
name|Autotagger
name|autotagger
init|=
name|provider
operator|.
name|getAutotagger
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|TagInfo
argument_list|>
name|suggestions
decl_stmt|;
if|if
condition|(
name|typeParam
operator|==
literal|null
condition|)
block|{
name|suggestions
operator|=
name|autotagger
operator|.
name|suggest
argument_list|(
name|labelParam
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|suggestions
operator|=
name|autotagger
operator|.
name|suggestForType
argument_list|(
name|labelParam
argument_list|,
name|typeParam
argument_list|)
expr_stmt|;
block|}
comment|//encode the results and sent it back to the client
name|resp
operator|.
name|setContentType
argument_list|(
literal|"text/json"
argument_list|)
expr_stmt|;
name|resp
operator|.
name|setCharacterEncoding
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|Writer
name|writer
init|=
name|resp
operator|.
name|getWriter
argument_list|()
decl_stmt|;
name|JSONObject
name|suggestionList
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|>
name|suggestionObjects
init|=
operator|new
name|ArrayList
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|>
argument_list|(
name|suggestions
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|TagInfo
name|suggestion
range|:
name|suggestions
control|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|map
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"uri"
argument_list|,
name|suggestion
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"label"
argument_list|,
name|suggestion
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|suggestion
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"confidence"
argument_list|,
name|suggestion
operator|.
name|getConfidence
argument_list|()
argument_list|)
expr_stmt|;
name|suggestionObjects
operator|.
name|add
argument_list|(
name|map
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|suggestionList
operator|.
name|put
argument_list|(
literal|"suggestion"
argument_list|,
name|suggestionObjects
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to encode suggestions as JSON"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|resp
operator|.
name|sendError
argument_list|(
literal|500
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|writer
operator|.
name|append
argument_list|(
name|suggestionList
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|String
name|getEntityName
parameter_list|(
name|HttpServletRequest
name|r
parameter_list|)
block|{
specifier|final
name|String
name|result
init|=
name|r
operator|.
name|getParameter
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
operator|||
name|result
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Missing Parameter name, request should include parameter \"name\""
argument_list|)
throw|;
block|}
return|return
name|result
return|;
block|}
specifier|private
name|String
name|getEntityType
parameter_list|(
name|HttpServletRequest
name|r
parameter_list|)
block|{
specifier|final
name|String
name|result
init|=
name|r
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
comment|//    	if(result == null){
comment|//    		return null;
comment|//    	} else {
comment|//    		//convert the Type to the Ontology
comment|//    	}
return|return
name|result
return|;
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|Exception
block|{
name|httpService
operator|.
name|registerServlet
argument_list|(
name|ALIAS
argument_list|,
name|this
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Servlet registered at {}"
argument_list|,
name|ALIAS
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
throws|throws
name|Exception
block|{
name|httpService
operator|.
name|unregister
argument_list|(
name|ALIAS
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Servlet unregistered from {}"
argument_list|,
name|ALIAS
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

