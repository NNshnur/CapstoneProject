package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.DeleteExpenseRequest;
import truckingappservice.activity.results.DeleteExpenseResult;

public class DeleteExpenseLambda extends LambdaActivityRunner<DeleteExpenseRequest, DeleteExpenseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteExpenseRequest>, LambdaResponse>

    {
        private final Logger log = LogManager.getLogger();
        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteExpenseRequest> input, Context context) {
            log.error("in Lambda");
        return super.runActivity(
                () -> {
                    DeleteExpenseRequest unauthenticatedRequest = input.fromBody(DeleteExpenseRequest.class);
                    return input.fromUserClaims(claims ->
                            DeleteExpenseRequest.builder()
                                    .withExpenseId(unauthenticatedRequest.getExpenseId())
                                    .build());
                },

                (request, serviceComponent) -> {
                    try {
                        return serviceComponent.provideDeleteExpenseActivity().handleRequest(request);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                });
        }
    }

//            log.info("handleRequest");
//            return super.runActivity( //this returns lambda response
//                    () -> input.fromPath(path ->
//                            DeleteExpenseRequest.builder()
//                                    .withExpenseId(path.get("expenseId"))
//                                    .build()),
//                    (request, serviceComponent) ->
//                            serviceComponent.provideDeleteExpenseActivity().handleRequest(request)
//            );
//        }
//    }


