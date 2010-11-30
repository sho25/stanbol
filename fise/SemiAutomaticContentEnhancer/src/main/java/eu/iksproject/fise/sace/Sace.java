begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|sace
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStreamWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLConnection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jdom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jdom
operator|.
name|Element
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
name|sace
operator|.
name|ui
operator|.
name|SaceGUI
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
name|sace
operator|.
name|util
operator|.
name|DocumentAnnotation
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
name|sace
operator|.
name|util
operator|.
name|EntityAnnotation
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
name|sace
operator|.
name|util
operator|.
name|IAnnotation
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
name|sace
operator|.
name|util
operator|.
name|ImageAnnotation
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
name|sace
operator|.
name|util
operator|.
name|TextAnnotation
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
name|sace
operator|.
name|util
operator|.
name|Util
import|;
end_import

begin_class
specifier|public
class|class
name|Sace
block|{
specifier|private
name|String
name|serverUrl
decl_stmt|;
specifier|private
name|SaceGUI
name|gui
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_URL
init|=
literal|"http://localhost:8080/engines"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|WINDOW_TITLE
init|=
literal|"FISE - S.A.C.E"
decl_stmt|;
specifier|public
name|Sace
parameter_list|()
block|{
try|try
block|{
name|this
operator|.
name|serverUrl
operator|=
name|getServerUrl
argument_list|()
expr_stmt|;
name|this
operator|.
name|gui
operator|=
operator|new
name|SaceGUI
argument_list|(
name|this
argument_list|,
name|WINDOW_TITLE
operator|+
literal|" ("
operator|+
name|serverUrl
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|ise
parameter_list|)
block|{
comment|// user entered wrong or missing url -> shutdown
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|ise
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
specifier|static
name|String
name|getServerUrl
parameter_list|()
block|{
specifier|final
name|String
name|result
init|=
operator|(
name|String
operator|)
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
literal|null
argument_list|,
literal|"Please indicate the FISE server URL"
argument_list|,
name|WINDOW_TITLE
argument_list|,
name|JOptionPane
operator|.
name|PLAIN_MESSAGE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|DEFAULT_URL
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
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Missing server URL"
argument_list|)
throw|;
block|}
return|return
name|result
operator|.
name|trim
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|IAnnotation
argument_list|>
name|annotateTextWithFISE
parameter_list|(
specifier|final
name|String
name|text
parameter_list|)
block|{
comment|// get stuff from FISE
name|String
name|result
init|=
name|sendTextToFISE
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|result
argument_list|)
expr_stmt|;
comment|// transform FISE-annotations to SACE-annotations
return|return
name|transformRDFXMLToAnnotations
argument_list|(
name|result
argument_list|)
return|;
block|}
specifier|public
name|String
name|sendTextToFISE
parameter_list|(
name|String
name|text
parameter_list|)
block|{
try|try
block|{
comment|// Construct data
name|String
name|data
init|=
name|URLEncoder
operator|.
name|encode
argument_list|(
literal|"format"
argument_list|,
literal|"UTF-8"
argument_list|)
operator|+
literal|"="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
literal|"application/rdf+xml"
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|data
operator|+=
literal|"&"
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
literal|"content"
argument_list|,
literal|"UTF-8"
argument_list|)
operator|+
literal|"="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|text
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
comment|// Send data
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|serverUrl
argument_list|)
decl_stmt|;
name|URLConnection
name|conn
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|conn
operator|.
name|setDoOutput
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|OutputStreamWriter
name|wr
init|=
operator|new
name|OutputStreamWriter
argument_list|(
name|conn
operator|.
name|getOutputStream
argument_list|()
argument_list|)
decl_stmt|;
name|wr
operator|.
name|write
argument_list|(
name|data
argument_list|)
expr_stmt|;
name|wr
operator|.
name|flush
argument_list|()
expr_stmt|;
comment|// Get the response
name|String
name|resultText
init|=
literal|""
decl_stmt|;
name|BufferedReader
name|rd
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|conn
operator|.
name|getInputStream
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|rd
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
comment|// Process line...
name|resultText
operator|+=
operator|(
name|line
operator|+
literal|"\n"
operator|)
expr_stmt|;
block|}
name|wr
operator|.
name|close
argument_list|()
expr_stmt|;
name|rd
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|resultText
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|private
name|List
argument_list|<
name|IAnnotation
argument_list|>
name|transformRDFXMLToAnnotations
parameter_list|(
name|String
name|rdfXml
parameter_list|)
block|{
name|List
argument_list|<
name|IAnnotation
argument_list|>
name|retVal
init|=
operator|new
name|LinkedList
argument_list|<
name|IAnnotation
argument_list|>
argument_list|()
decl_stmt|;
name|Document
name|doc
init|=
name|Util
operator|.
name|transformToXML
argument_list|(
name|rdfXml
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|TextAnnotation
argument_list|>
name|textAnnotations
init|=
operator|new
name|LinkedList
argument_list|<
name|TextAnnotation
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|DocumentAnnotation
argument_list|>
name|documentAnnotations
init|=
operator|new
name|LinkedList
argument_list|<
name|DocumentAnnotation
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|EntityAnnotation
argument_list|>
name|entityAnnotations
init|=
operator|new
name|LinkedList
argument_list|<
name|EntityAnnotation
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|o1
range|:
name|doc
operator|.
name|getRootElement
argument_list|()
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|Element
name|node
init|=
operator|(
name|Element
operator|)
name|o1
decl_stmt|;
name|boolean
name|isDocumentAnnotation
init|=
literal|false
decl_stmt|;
name|boolean
name|isTextAnnotation
init|=
literal|false
decl_stmt|;
name|boolean
name|isEntityAnnotation
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Object
name|o2
range|:
name|node
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|Element
name|subject
init|=
operator|(
name|Element
operator|)
name|o2
decl_stmt|;
name|String
name|name
init|=
name|subject
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|ns
init|=
name|subject
operator|.
name|getNamespace
argument_list|()
operator|.
name|getURI
argument_list|()
decl_stmt|;
name|isDocumentAnnotation
operator||=
name|name
operator|.
name|equals
argument_list|(
literal|"doc-lang"
argument_list|)
operator|&&
name|ns
operator|.
name|equals
argument_list|(
literal|"http://iksproject.eu/ns/extraction/"
argument_list|)
expr_stmt|;
name|isTextAnnotation
operator||=
name|name
operator|.
name|equals
argument_list|(
literal|"type"
argument_list|)
operator|&&
name|ns
operator|.
name|equals
argument_list|(
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#"
argument_list|)
operator|&&
name|subject
operator|.
name|getAttribute
argument_list|(
literal|"resource"
argument_list|,
name|subject
operator|.
name|getNamespace
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"TextAnnotation"
argument_list|)
expr_stmt|;
name|isEntityAnnotation
operator||=
name|name
operator|.
name|equals
argument_list|(
literal|"type"
argument_list|)
operator|&&
name|ns
operator|.
name|equals
argument_list|(
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#"
argument_list|)
operator|&&
name|subject
operator|.
name|getAttribute
argument_list|(
literal|"resource"
argument_list|,
name|subject
operator|.
name|getNamespace
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"EntityAnnotation"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isDocumentAnnotation
condition|)
block|{
name|DocumentAnnotation
name|docAnnot
init|=
operator|new
name|DocumentAnnotation
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|node
operator|.
name|getAttribute
argument_list|(
literal|"about"
argument_list|,
name|node
operator|.
name|getNamespace
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|docAnnot
operator|.
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
name|o2
range|:
name|node
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|Element
name|subject
init|=
operator|(
name|Element
operator|)
name|o2
decl_stmt|;
if|if
condition|(
name|subject
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"doc-lang"
argument_list|)
operator|&&
name|subject
operator|.
name|getNamespace
argument_list|()
operator|.
name|getURI
argument_list|()
operator|.
name|equals
argument_list|(
literal|"http://iksproject.eu/ns/extraction/"
argument_list|)
condition|)
block|{
name|docAnnot
operator|.
name|addAttribute
argument_list|(
literal|"lang"
argument_list|,
name|subject
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// TODO: fill with other stuff once available
block|}
name|documentAnnotations
operator|.
name|add
argument_list|(
name|docAnnot
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|isTextAnnotation
condition|)
block|{
name|TextAnnotation
name|textAnnot
init|=
operator|new
name|TextAnnotation
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|node
operator|.
name|getAttribute
argument_list|(
literal|"about"
argument_list|,
name|node
operator|.
name|getNamespace
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|textAnnot
operator|.
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
name|o2
range|:
name|node
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|Element
name|subject
init|=
operator|(
name|Element
operator|)
name|o2
decl_stmt|;
name|String
name|sName
init|=
name|subject
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|sNs
init|=
name|subject
operator|.
name|getNamespaceURI
argument_list|()
decl_stmt|;
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"creator"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://purl.org/dc/terms/"
argument_list|)
condition|)
block|{
name|String
name|creator
init|=
name|subject
operator|.
name|getText
argument_list|()
decl_stmt|;
name|textAnnot
operator|.
name|setCreator
argument_list|(
name|creator
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"created"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://purl.org/dc/terms/"
argument_list|)
condition|)
block|{
name|String
name|created
init|=
name|subject
operator|.
name|getText
argument_list|()
decl_stmt|;
name|textAnnot
operator|.
name|setCreated
argument_list|(
name|created
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"selection-context"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
condition|)
block|{
name|String
name|selectionContext
init|=
name|subject
operator|.
name|getText
argument_list|()
decl_stmt|;
name|textAnnot
operator|.
name|setSelectionContext
argument_list|(
name|selectionContext
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"selected-text"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
condition|)
block|{
name|String
name|selectedText
init|=
name|subject
operator|.
name|getText
argument_list|()
decl_stmt|;
name|textAnnot
operator|.
name|setSelectedText
argument_list|(
name|selectedText
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"type"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://purl.org/dc/terms/"
argument_list|)
condition|)
block|{
name|String
name|type
init|=
name|subject
operator|.
name|getAttribute
argument_list|(
literal|"resource"
argument_list|,
name|node
operator|.
name|getNamespace
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|textAnnot
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"start"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
condition|)
block|{
name|int
name|index
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|subject
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|textAnnot
operator|.
name|setStartIndex
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"end"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
condition|)
block|{
name|int
name|index
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|subject
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|textAnnot
operator|.
name|setEndIndex
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
comment|// TODO: once they are available
name|double
name|confidence
init|=
literal|0.0
decl_stmt|;
block|}
name|textAnnotations
operator|.
name|add
argument_list|(
name|textAnnot
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|isEntityAnnotation
condition|)
block|{
name|EntityAnnotation
name|entityAnnot
init|=
operator|new
name|EntityAnnotation
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|node
operator|.
name|getAttribute
argument_list|(
literal|"about"
argument_list|,
name|node
operator|.
name|getNamespace
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|entityAnnot
operator|.
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
name|o2
range|:
name|node
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|Element
name|subject
init|=
operator|(
name|Element
operator|)
name|o2
decl_stmt|;
name|String
name|sName
init|=
name|subject
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|sNs
init|=
name|subject
operator|.
name|getNamespaceURI
argument_list|()
decl_stmt|;
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"creator"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://purl.org/dc/terms/"
argument_list|)
condition|)
block|{
name|String
name|creator
init|=
name|subject
operator|.
name|getText
argument_list|()
decl_stmt|;
name|entityAnnot
operator|.
name|setCreator
argument_list|(
name|creator
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"created"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://purl.org/dc/terms/"
argument_list|)
condition|)
block|{
name|String
name|created
init|=
name|subject
operator|.
name|getText
argument_list|()
decl_stmt|;
name|entityAnnot
operator|.
name|setCreated
argument_list|(
name|created
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"entity-type"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
condition|)
block|{
name|String
name|entityType
init|=
name|subject
operator|.
name|getAttribute
argument_list|(
literal|"resource"
argument_list|,
name|node
operator|.
name|getNamespace
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|entityAnnot
operator|.
name|addAttribute
argument_list|(
literal|"entity-type"
argument_list|,
name|entityType
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"entity-reference"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
condition|)
block|{
name|String
name|entityRef
init|=
name|subject
operator|.
name|getAttribute
argument_list|(
literal|"resource"
argument_list|,
name|node
operator|.
name|getNamespace
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|entityAnnot
operator|.
name|addAttribute
argument_list|(
literal|"entity-reference"
argument_list|,
name|entityRef
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"entity-label"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
condition|)
block|{
name|String
name|entityLabel
init|=
name|subject
operator|.
name|getText
argument_list|()
decl_stmt|;
name|entityAnnot
operator|.
name|addAttribute
argument_list|(
literal|"entity-label"
argument_list|,
name|entityLabel
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sName
operator|.
name|equals
argument_list|(
literal|"relation"
argument_list|)
operator|&&
name|sNs
operator|.
name|equals
argument_list|(
literal|"http://purl.org/dc/terms/"
argument_list|)
condition|)
block|{
name|String
name|relation
init|=
name|subject
operator|.
name|getAttribute
argument_list|(
literal|"resource"
argument_list|,
name|node
operator|.
name|getNamespace
argument_list|()
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|entityAnnot
operator|.
name|setRelation
argument_list|(
name|relation
argument_list|)
expr_stmt|;
block|}
comment|// TODO: once they are available
name|double
name|confidence
init|=
literal|0.0
decl_stmt|;
block|}
name|entityAnnotations
operator|.
name|add
argument_list|(
name|entityAnnot
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|TextAnnotation
name|ta
range|:
name|textAnnotations
control|)
block|{
name|String
name|taName
init|=
name|ta
operator|.
name|getName
argument_list|()
decl_stmt|;
for|for
control|(
name|EntityAnnotation
name|ea
range|:
name|entityAnnotations
control|)
block|{
if|if
condition|(
name|ea
operator|.
name|getRelation
argument_list|()
operator|.
name|equals
argument_list|(
name|taName
argument_list|)
condition|)
block|{
name|ta
operator|.
name|addEntityAnnotation
argument_list|(
name|ea
argument_list|)
expr_stmt|;
block|}
block|}
name|retVal
operator|.
name|add
argument_list|(
name|ta
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|DocumentAnnotation
name|da
range|:
name|documentAnnotations
control|)
block|{
name|retVal
operator|.
name|add
argument_list|(
name|da
argument_list|)
expr_stmt|;
block|}
return|return
name|retVal
return|;
block|}
specifier|public
name|void
name|submitTextAnnotationToFISE
parameter_list|(
name|TextAnnotation
name|ta
parameter_list|)
block|{
comment|// TODO!
block|}
specifier|public
name|void
name|submitEntityAnnotationToFISE
parameter_list|(
name|EntityAnnotation
name|ea
parameter_list|)
block|{
comment|// TODO!
block|}
specifier|public
name|void
name|submitDocumentAnnotationToFISE
parameter_list|(
name|DocumentAnnotation
name|da
parameter_list|)
block|{
comment|// TODO!
block|}
specifier|public
name|void
name|submitImageAnnotationToFISE
parameter_list|(
name|ImageAnnotation
name|ia
parameter_list|)
block|{
comment|// TODO!
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
operator|new
name|Sace
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

