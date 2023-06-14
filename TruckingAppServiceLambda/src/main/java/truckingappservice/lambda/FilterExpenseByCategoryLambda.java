package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import truckingappservice.activity.request.FilterExpenseByCategoryRequest;
import truckingappservice.activity.results.FilterExpenseByCategoryResult;

public class FilterExpenseByCategoryLambda extends LambdaActivityRunner<FilterExpenseByCategoryRequest, FilterExpenseByCategoryResult>
        implements RequestHandler<AuthenticatedLambdaRequest<FilterExpenseByCategoryRequest>,LambdaResponse> {
    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<FilterExpenseByCategoryRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    FilterExpenseByCategoryRequest unauthenticatedRequest = input.fromBody(FilterExpenseByCategoryRequest.class);
                    return input.fromUserClaims(claims ->
                            FilterExpenseByCategoryRequest.builder()
                                    .withId(claims.get("email"))
                                    .withCategory(unauthenticatedRequest.getCategory())
                                    .build());

                },
                (request,serviceComponent) ->
                        serviceComponent.provideFilterExpenseByCategoryActivity().handleRequest(request)
        );
    }
}
