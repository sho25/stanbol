begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Generated By:JavaCC: Do not edit this line. ParseException.java Version 5.0 */
end_comment

begin_comment
comment|/* JavaCCOptions:KEEP_LINE_COL=null */
end_comment

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
name|parser
package|;
end_package

begin_comment
comment|/**  * This exception is thrown when parse errors are encountered.  * You can explicitly create objects of this exception type by  * calling the method generateParseException in the generated  * parser.  *  * You can modify this class to customize your error reporting  * mechanisms so long as you retain the public fields.  */
end_comment

begin_class
specifier|public
class|class
name|ParseException
extends|extends
name|Exception
block|{
comment|/**    * The version identifier for this Serializable class.    * Increment only if the<i>serialized</i> form of the    * class changes.    */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/**    * This constructor is used by the method "generateParseException"    * in the generated parser.  Calling this constructor generates    * a new object of this type with the fields "currentToken",    * "expectedTokenSequences", and "tokenImage" set.    */
specifier|public
name|ParseException
parameter_list|(
name|Token
name|currentTokenVal
parameter_list|,
name|int
index|[]
index|[]
name|expectedTokenSequencesVal
parameter_list|,
name|String
index|[]
name|tokenImageVal
parameter_list|)
block|{
name|super
argument_list|(
name|initialise
argument_list|(
name|currentTokenVal
argument_list|,
name|expectedTokenSequencesVal
argument_list|,
name|tokenImageVal
argument_list|)
argument_list|)
expr_stmt|;
name|currentToken
operator|=
name|currentTokenVal
expr_stmt|;
name|expectedTokenSequences
operator|=
name|expectedTokenSequencesVal
expr_stmt|;
name|tokenImage
operator|=
name|tokenImageVal
expr_stmt|;
block|}
comment|/**    * The following constructors are for use by you for whatever    * purpose you can think of.  Constructing the exception in this    * manner makes the exception behave in the normal way - i.e., as    * documented in the class "Throwable".  The fields "errorToken",    * "expectedTokenSequences", and "tokenImage" do not contain    * relevant information.  The JavaCC generated code does not use    * these constructors.    */
specifier|public
name|ParseException
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** Constructor with message. */
specifier|public
name|ParseException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/**    * This is the last token that has been consumed successfully.  If    * this object has been created due to a parse error, the token    * followng this token will (therefore) be the first error token.    */
specifier|public
name|Token
name|currentToken
decl_stmt|;
comment|/**    * Each entry in this array is an array of integers.  Each array    * of integers represents a sequence of tokens (by their ordinal    * values) that is expected at this point of the parse.    */
specifier|public
name|int
index|[]
index|[]
name|expectedTokenSequences
decl_stmt|;
comment|/**    * This is a reference to the "tokenImage" array of the generated    * parser within which the parse error occurred.  This array is    * defined in the generated ...Constants interface.    */
specifier|public
name|String
index|[]
name|tokenImage
decl_stmt|;
comment|/**    * It uses "currentToken" and "expectedTokenSequences" to generate a parse    * error message and returns it.  If this object has been created    * due to a parse error, and you do not catch it (it gets thrown    * from the parser) the correct error message    * gets displayed.    */
specifier|private
specifier|static
name|String
name|initialise
parameter_list|(
name|Token
name|currentToken
parameter_list|,
name|int
index|[]
index|[]
name|expectedTokenSequences
parameter_list|,
name|String
index|[]
name|tokenImage
parameter_list|)
block|{
name|String
name|eol
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|,
literal|"\n"
argument_list|)
decl_stmt|;
name|StringBuffer
name|expected
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|int
name|maxSize
init|=
literal|0
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
name|expectedTokenSequences
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|maxSize
operator|<
name|expectedTokenSequences
index|[
name|i
index|]
operator|.
name|length
condition|)
block|{
name|maxSize
operator|=
name|expectedTokenSequences
index|[
name|i
index|]
operator|.
name|length
expr_stmt|;
block|}
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|expectedTokenSequences
index|[
name|i
index|]
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|expected
operator|.
name|append
argument_list|(
name|tokenImage
index|[
name|expectedTokenSequences
index|[
name|i
index|]
index|[
name|j
index|]
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|expectedTokenSequences
index|[
name|i
index|]
index|[
name|expectedTokenSequences
index|[
name|i
index|]
operator|.
name|length
operator|-
literal|1
index|]
operator|!=
literal|0
condition|)
block|{
name|expected
operator|.
name|append
argument_list|(
literal|"..."
argument_list|)
expr_stmt|;
block|}
name|expected
operator|.
name|append
argument_list|(
name|eol
argument_list|)
operator|.
name|append
argument_list|(
literal|"    "
argument_list|)
expr_stmt|;
block|}
name|String
name|retval
init|=
literal|"Encountered \""
decl_stmt|;
name|Token
name|tok
init|=
name|currentToken
operator|.
name|next
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
name|maxSize
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|!=
literal|0
condition|)
name|retval
operator|+=
literal|" "
expr_stmt|;
if|if
condition|(
name|tok
operator|.
name|kind
operator|==
literal|0
condition|)
block|{
name|retval
operator|+=
name|tokenImage
index|[
literal|0
index|]
expr_stmt|;
break|break;
block|}
name|retval
operator|+=
literal|" "
operator|+
name|tokenImage
index|[
name|tok
operator|.
name|kind
index|]
expr_stmt|;
name|retval
operator|+=
literal|" \""
expr_stmt|;
name|retval
operator|+=
name|add_escapes
argument_list|(
name|tok
operator|.
name|image
argument_list|)
expr_stmt|;
name|retval
operator|+=
literal|" \""
expr_stmt|;
name|tok
operator|=
name|tok
operator|.
name|next
expr_stmt|;
block|}
name|retval
operator|+=
literal|"\" at line "
operator|+
name|currentToken
operator|.
name|next
operator|.
name|beginLine
operator|+
literal|", column "
operator|+
name|currentToken
operator|.
name|next
operator|.
name|beginColumn
expr_stmt|;
name|retval
operator|+=
literal|"."
operator|+
name|eol
expr_stmt|;
if|if
condition|(
name|expectedTokenSequences
operator|.
name|length
operator|==
literal|1
condition|)
block|{
name|retval
operator|+=
literal|"Was expecting:"
operator|+
name|eol
operator|+
literal|"    "
expr_stmt|;
block|}
else|else
block|{
name|retval
operator|+=
literal|"Was expecting one of:"
operator|+
name|eol
operator|+
literal|"    "
expr_stmt|;
block|}
name|retval
operator|+=
name|expected
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|retval
return|;
block|}
comment|/**    * The end of line string for this machine.    */
specifier|protected
name|String
name|eol
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|,
literal|"\n"
argument_list|)
decl_stmt|;
comment|/**    * Used to convert raw characters to their escaped version    * when these raw version cannot be used as part of an ASCII    * string literal.    */
specifier|static
name|String
name|add_escapes
parameter_list|(
name|String
name|str
parameter_list|)
block|{
name|StringBuffer
name|retval
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|char
name|ch
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
name|str
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
switch|switch
condition|(
name|str
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
condition|)
block|{
case|case
literal|0
case|:
continue|continue;
case|case
literal|'\b'
case|:
name|retval
operator|.
name|append
argument_list|(
literal|"\\b"
argument_list|)
expr_stmt|;
continue|continue;
case|case
literal|'\t'
case|:
name|retval
operator|.
name|append
argument_list|(
literal|"\\t"
argument_list|)
expr_stmt|;
continue|continue;
case|case
literal|'\n'
case|:
name|retval
operator|.
name|append
argument_list|(
literal|"\\n"
argument_list|)
expr_stmt|;
continue|continue;
case|case
literal|'\f'
case|:
name|retval
operator|.
name|append
argument_list|(
literal|"\\f"
argument_list|)
expr_stmt|;
continue|continue;
case|case
literal|'\r'
case|:
name|retval
operator|.
name|append
argument_list|(
literal|"\\r"
argument_list|)
expr_stmt|;
continue|continue;
case|case
literal|'\"'
case|:
name|retval
operator|.
name|append
argument_list|(
literal|"\\\""
argument_list|)
expr_stmt|;
continue|continue;
case|case
literal|'\''
case|:
name|retval
operator|.
name|append
argument_list|(
literal|"\\\'"
argument_list|)
expr_stmt|;
continue|continue;
case|case
literal|'\\'
case|:
name|retval
operator|.
name|append
argument_list|(
literal|"\\\\"
argument_list|)
expr_stmt|;
continue|continue;
default|default:
if|if
condition|(
operator|(
name|ch
operator|=
name|str
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|)
operator|<
literal|0x20
operator|||
name|ch
operator|>
literal|0x7e
condition|)
block|{
name|String
name|s
init|=
literal|"0000"
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|ch
argument_list|,
literal|16
argument_list|)
decl_stmt|;
name|retval
operator|.
name|append
argument_list|(
literal|"\\u"
operator|+
name|s
operator|.
name|substring
argument_list|(
name|s
operator|.
name|length
argument_list|()
operator|-
literal|4
argument_list|,
name|s
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|retval
operator|.
name|append
argument_list|(
name|ch
argument_list|)
expr_stmt|;
block|}
continue|continue;
block|}
block|}
return|return
name|retval
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

begin_comment
comment|/* JavaCC - OriginalChecksum=76b2464affe4de36a94ffcfb8477b129 (do not edit this line) */
end_comment

end_unit

