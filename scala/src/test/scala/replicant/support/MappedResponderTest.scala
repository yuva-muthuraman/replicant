// Copyright 2011 Kiel Hodges
package replicant.support

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._


@RunWith(classOf[JUnitRunner])
class MappedResponderTest extends junit.JUnit3Suite with ShouldMatchers {
 
  private val result1 = A(1)
  private val result2 = A(2)
   
  private case class Mock(name: String) 
  private val mock = Mock("aMock")
  private val call1 = Call(mock, "methodA")(1)
  private val call2 = Call(mock, "methodA")(2)
  private val call3 = Call(mock, "methodA")(3)
  
  @Test def testResponses {
    val responder = new MappedResponder[A]
    
    val f1 = () => result1
    val f2 = () => result2
    responder(call1) = f1
    responder(call2) = f2

    responder(call1) should equal(ValueResponse(f1))
    responder(call2) should equal(ValueResponse(f2))
    responder(call3) should equal(UnknownResponse("No response expected for " + call3))
  } 
  
  @Test def testAssertExpectationsMet {
    val responder = new MappedResponder[A]
                                        
    responder(call1) = () => result1
    responder(call2) = () => result2
    responder(call3) = () => throw new RuntimeException("testing")

    intercept[TestFailedException] { 
      responder.assertExpectationsMet
    }.message.get should equal("Expected but did not receive " + Call.describe(Set(call1, call2, call3))) 

    responder(call2)
    responder(call1)
    responder(call2)
    intercept[TestFailedException] { 
      responder.assertExpectationsMet
    }.message.get should equal("Expected but did not receive " + Call.describe(Set(call3))) 

    responder(call3)
    responder.assertExpectationsMet
  } 
  
  @Test def testMappedResponderEquality {
    testEqualityOf(   new MappedResponder[A] ).
      shouldEqual(    new MappedResponder[A] ).
      shouldNotEqual( "not a MappedResponder" )
  } 

}
