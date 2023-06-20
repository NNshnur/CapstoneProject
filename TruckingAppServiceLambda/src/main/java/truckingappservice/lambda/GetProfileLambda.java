package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import truckingappservice.activity.request.GetProfileRequest;
import truckingappservice.activity.results.GetProfileResult;

public class GetProfileLambda extends LambdaActivityRunner<GetProfileRequest, GetProfileResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetProfileRequest>, LambdaResponse> {


    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetProfileRequest> input, Context context) {
        return runActivity(
                () -> input.fromPath(path ->
                        GetProfileRequest.builder()
                                .withId(path.get("id"))
                                .build()),
                (request,serviceComponent)->
                        serviceComponent.provideGetProfileActivity().handleRequest(request));
    }
}
