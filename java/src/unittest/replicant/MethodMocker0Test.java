// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class MethodMocker0Test extends TestCase {

  private static final Mock         MOCK          = new Mock();
  private static final String       METHOD        = "method";
  private static final List<Object> ARGUMENTS     = list();
  private static final X            RETURN_VALUE  = new X(7);
  private static final Response<X>  RESPONSE      = new TestResponse<X>();
  
  private MockBaseMethodMocker<X> baseMethodMocker;
  
  private MethodMocker0<X> methodMocker;

  public void setUp() throws Exception {
    baseMethodMocker = new MockBaseMethodMocker<X>(MOCK, METHOD);
    methodMocker    = new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker);
  }
  
  public void testExpect() throws Exception {
    baseMethodMocker.onCallToExpectReturn(RESPONSE);
    
    Response<X> response = methodMocker.expect();
    
    baseMethodMocker.assertCalledExpect(ARGUMENTS);
    assertEquals(RESPONSE, response);
  }

  public void testCall() throws Exception {
    baseMethodMocker.onCallToCallReturn(RETURN_VALUE);
    
    X returnValue = methodMocker.call();
    
    assertEquals(RETURN_VALUE, returnValue);
    baseMethodMocker.assertCalledCall(ARGUMENTS);
  }
  
  public void testAssertCalled() throws Exception {
    RuntimeException exception = new RuntimeException("test");
    baseMethodMocker.onCallToAssertCalledThrow(exception);
    
    try {
      methodMocker.assertCalled();
      fail("Expected an exception.");
    } catch (RuntimeException e) {
      assertSame(exception, e);
    }
    
    baseMethodMocker.assertCalledAssertCalled(ARGUMENTS);
  }
  
  public void testAssertNotCalled() throws Exception {
    RuntimeException exception = new RuntimeException("test");
    baseMethodMocker.onCallToAssertNotCalledThrow(exception);
    
    try {
      methodMocker.assertNotCalled();
      fail("Expected an exception.");
    } catch (RuntimeException e) {
      assertSame(exception, e);
    }
  }
  
  public void testAssertCalledOnce() throws Exception {
    RuntimeException exception = new RuntimeException("test");
    baseMethodMocker.onCallToAssertCalledOnceThrow(exception);
    
    try {
      methodMocker.assertCalledOnce();
      fail("Expected an exception.");
    } catch (RuntimeException e) {
      assertSame(exception, e);
    }
  }
  
  public void testAssertExpectationsMet() throws Exception {
    RuntimeException exception = new RuntimeException("test");
    baseMethodMocker.onCallToAssertExpectationsMetThrow(exception);
    
    try {
      methodMocker.assertExpectationsMet();
      fail("Expected an exception.");
    } catch (RuntimeException e) {
      assertSame(exception, e);
    }
  }

  public void testAssertExpectedCallsMade() throws Exception {
    RuntimeException exception = new RuntimeException("test");
    baseMethodMocker.onCallToAssertExpectedCallsMadeThrow(exception);
    
    try {
      methodMocker.assertExpectedCallsMade();
      fail("Expected an exception.");
    } catch (RuntimeException e) {
      assertSame(exception, e);
    }
  }
  
  public void testEquality() throws Exception {
    BaseMethodMocker<X> baseMethodMocker1 = new MockBaseMethodMocker<X>(MOCK, METHOD); 
    BaseMethodMocker<X> baseMethodMocker2 = new MockBaseMethodMocker<X>(MOCK, METHOD); 
    
    MethodMocker0<X> mocker = new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker1);
    
    assertEquals(false, mocker.equals(null));
    assertEquals(false, mocker.equals("not a MethodMocker1"));
    assertEquals(false, mocker.equals(new MethodMocker0<X>(new Mock(), METHOD,  baseMethodMocker1)));
    assertEquals(false, mocker.equals(new MethodMocker0<X>(MOCK,       "other", baseMethodMocker1)));
    assertEquals(false, mocker.equals(new MethodMocker0<X>(MOCK,       METHOD,  baseMethodMocker2)));
    assertEquals(true,  mocker.equals(mocker));
  }
  
  private static class TestResponse<ReturnValue> implements Response<ReturnValue> {
    public Response<ReturnValue> checking(Check assertion) {
      return null;
    }
    public void returning(ReturnValue result) {
    }
    public void throwing(Exception exception) {
    }
    public Response<ReturnValue> triggering(Event event) {
      return null;
    }
  }
  
}
