package replicant

case class Result[Value](call: Call, callHandler: CallHandler[Value]) extends Replicant[Result[Value]] {

  def response: Value = callHandler(call)
  def response_=(response: => Value) { callHandler.expect(call, response) }
  def assertCalled                   { callHandler.assertCalled(call)     }
  def assertCalledOnce               { callHandler.assertCalledOnce       } 
  def assertNotCalled                { callHandler.assertNotCalled        } 
  def assertAllResponsesUsed         { callHandler.assertExpectationsMet  } 

  private[replicant] def withArgs[NewArgs](args: NewArgs): Result[Value] = new Result(call(args), callHandler)

}
