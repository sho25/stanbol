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
name|servicesapi
operator|.
name|query
package|;
end_package

begin_comment
comment|/**  * This type of Query is intended to search entities that have a similar  * content to the example provided with the query. The Idea is to resemble the  * MoreLikeThisQuery provided by Lucene.  * TODO: define this Query Interface!  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|MoreLikeThisQuery
extends|extends
name|Query
block|{  }
end_interface

end_unit

