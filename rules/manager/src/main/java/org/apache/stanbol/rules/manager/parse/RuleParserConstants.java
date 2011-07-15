begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_comment
comment|/* Generated By:JavaCC: Do not edit this line. RuleParserConstants.java */
end_comment

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
name|parse
package|;
end_package

begin_comment
comment|/**  * Token literal values and constants.  * Generated by org.javacc.parser.OtherFilesGen#start()  */
end_comment

begin_interface
specifier|public
interface|interface
name|RuleParserConstants
block|{
comment|/** End of File. */
name|int
name|EOF
init|=
literal|0
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|LARROW
init|=
literal|5
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|COLON
init|=
literal|6
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|EQUAL
init|=
literal|7
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|AND
init|=
literal|8
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|COMMA
init|=
literal|9
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|REFLEXIVE
init|=
literal|10
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|SAME
init|=
literal|11
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|DIFFERENT
init|=
literal|12
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|LESSTHAN
init|=
literal|13
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|GREATERTHAN
init|=
literal|14
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|IS
init|=
literal|15
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|NEW_NODE
init|=
literal|16
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|LENGTH
init|=
literal|17
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|SUBSTRING
init|=
literal|18
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|UPPERCASE
init|=
literal|19
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|LOWERCASE
init|=
literal|20
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|STARTS_WITH
init|=
literal|21
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|ENDS_WITH
init|=
literal|22
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|LET
init|=
literal|23
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|CONCAT
init|=
literal|24
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|HAS
init|=
literal|25
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|VALUES
init|=
literal|26
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|NOTEX
init|=
literal|27
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|PLUS
init|=
literal|28
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|MINUS
init|=
literal|29
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|NOT
init|=
literal|30
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|NAMESPACE
init|=
literal|31
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|LOCALNAME
init|=
literal|32
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|STR
init|=
literal|33
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|APOX
init|=
literal|34
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|UNION
init|=
literal|35
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|CREATE_LABEL
init|=
literal|36
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|SPARQL_C
init|=
literal|37
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|SPARQL_D
init|=
literal|38
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|SPARQL_DD
init|=
literal|39
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|PROP
init|=
literal|40
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|IS_BLANK
init|=
literal|41
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|FORWARD_CHAIN
init|=
literal|42
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|LPAR
init|=
literal|43
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|RPAR
init|=
literal|44
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|DQUOT
init|=
literal|45
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|LQUAD
init|=
literal|46
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|RQUAD
init|=
literal|47
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|NUM
init|=
literal|48
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|VAR
init|=
literal|49
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|VARIABLE
init|=
literal|50
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|URI
init|=
literal|51
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|STRING
init|=
literal|52
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|SPARQL_STRING
init|=
literal|53
decl_stmt|;
comment|/** RegularExpression Id. */
name|int
name|BNODE
init|=
literal|54
decl_stmt|;
comment|/** Lexical state. */
name|int
name|DEFAULT
init|=
literal|0
decl_stmt|;
comment|/** Literal token values. */
name|String
index|[]
name|tokenImage
init|=
block|{
literal|"<EOF>"
block|,
literal|"\" \""
block|,
literal|"\"\\r\""
block|,
literal|"\"\\t\""
block|,
literal|"\"\\n\""
block|,
literal|"\"->\""
block|,
literal|"\":\""
block|,
literal|"\"=\""
block|,
literal|"\".\""
block|,
literal|"\",\""
block|,
literal|"\"+\""
block|,
literal|"\"same\""
block|,
literal|"\"different\""
block|,
literal|"\"lt\""
block|,
literal|"\"gt\""
block|,
literal|"\"is\""
block|,
literal|"\"newNode\""
block|,
literal|"\"length\""
block|,
literal|"\"substring\""
block|,
literal|"\"upperCase\""
block|,
literal|"\"lowerCase\""
block|,
literal|"\"startsWith\""
block|,
literal|"\"endsWith\""
block|,
literal|"\"let\""
block|,
literal|"\"concat\""
block|,
literal|"\"has\""
block|,
literal|"\"values\""
block|,
literal|"\"notex\""
block|,
literal|"\"sum\""
block|,
literal|"\"sub\""
block|,
literal|"\"not\""
block|,
literal|"\"namespace\""
block|,
literal|"\"localname\""
block|,
literal|"\"str\""
block|,
literal|"\"^\""
block|,
literal|"\"union\""
block|,
literal|"\"createLabel\""
block|,
literal|"\"sparql-c\""
block|,
literal|"\"sparql-d\""
block|,
literal|"\"sparql-dd\""
block|,
literal|"\"prop\""
block|,
literal|"\"isBlank\""
block|,
literal|"\"!\""
block|,
literal|"\"(\""
block|,
literal|"\")\""
block|,
literal|"\"\\\"\""
block|,
literal|"\"[\""
block|,
literal|"\"]\""
block|,
literal|"<NUM>"
block|,
literal|"<VAR>"
block|,
literal|"<VARIABLE>"
block|,
literal|"<URI>"
block|,
literal|"<STRING>"
block|,
literal|"<SPARQL_STRING>"
block|,
literal|"<BNODE>"
block|,   }
decl_stmt|;
block|}
end_interface

end_unit

