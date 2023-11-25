package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import layer.service.APIGatewayService;
import layer.service.APIGatewayServiceImpl;
import layer.service.DynamoDBService;
import layer.service.DynamoDBServiceImpl;


public class UpdateUserFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final DynamoDBService dynamoDBService = new DynamoDBServiceImpl();
    private static final APIGatewayService apiGatewayService = new APIGatewayServiceImpl();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
                                                        final Context context) {
        try {
            String userId = input.getPathParameters().get("userId");
            String requestBody = input.getBody();

            dynamoDBService.updateUser(userId, requestBody);

            return apiGatewayService.createResponse(200, "User information updated successfully");
        } catch (Exception e) {
            return apiGatewayService.createResponse(503, "Error updating user information: " + e.getMessage());
        }
    }
    }
}

